/**
 * 
 * ==========================================================
 * 保全管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.entity.main.ConservationDtl;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.insurance.BqglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.StatusDefine.BQ_STATUS;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/bqgl")
public class BqglController {
	private static final Logger LOG = LoggerFactory.getLogger(BqglController.class);
	
	@Autowired
	private BqglService bqglService;

	private static final String CREATE = "insurance/bqgl/wtj/create";
	private static final String UPDATE = "insurance/bqgl/wtj/update";
	private static final String LIST = "insurance/bqgl/wtj/list";
	private static final String ISSUE_LIST = "insurance/bqgl/wtj/issuelist";
	
	@RequiresPermissions("Cservice:save")
	@RequestMapping(value="/issue/create", method=RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}
	
	@Log(message="添加了{0}保全复核问题。")
	@RequiresPermissions("Cservice:save")
	@RequestMapping(value="/issue/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid ConservationDtl issue) {	
		try {
			issue.setCsDate(new Date());
			issue.setCsUserId(SecurityUtils.getShiroUser().getId());
			issue.setStatus(BQ_STATUS.NewStatus.name());
			bqglService.saveOrUpdate(issue);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加保全复核问题失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return AjaxObject.newOk("添加保全复核问题成功！").toString();
	}
	
	@ModelAttribute("preloadUser")
	public ConservationDtl preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			ConservationDtl issue = bqglService.get(id);
			return issue;
		}
		return null;
	}
	
	@RequiresPermissions("Cservice:edit")
	@RequestMapping(value="/issue/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		ConservationDtl issue = bqglService.get(id);
		
		map.put("issue", issue);
		return UPDATE;
	}
	
	@Log(message="修改了{0}保全复核问题的信息。")
	@RequiresPermissions("Cservice:edit")
	@RequestMapping(value="/issue/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadUser")ConservationDtl issue) {
		bqglService.saveOrUpdate(issue);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("修改保全复核问题成功！").toString(); 
	}
	
	@Log(message="修改了{0}保全复核问题的状态。")
	@RequiresPermissions(value={"Cservice:reset","Cservice:deal"}, logical=Logical.OR)
	@RequestMapping(value="/issue/{status}/{id}", method=RequestMethod.POST)
	public @ResponseBody String updateStatus(@PathVariable("status") String status, @PathVariable("id") Long id) {
		ConservationDtl issue = bqglService.get(id);
		BQ_STATUS bs = BQ_STATUS.DealStatus;
		try {
			bs = BQ_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		switch (bs) {
		case DealStatus:
			issue.setDealUserId(SecurityUtils.getShiroUser().getId());
			break;
		case CancelStatus:
			issue.setCancelDate(new Date());
			issue.setCancelFlag(true);
			issue.setCancelMan(SecurityUtils.getShiroUser().getId());
			break;
		case CloseStatus:
			break;
			default:
				break;
		}
		issue.setStatus(status);
		bqglService.saveOrUpdate(issue);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("修改保全复核问题成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}保全复核问题。")
	@RequiresPermissions("Cservice:delete")
	@RequestMapping(value="/issue/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		ConservationDtl issue = null;
		try {
			issue = bqglService.get(id);
			bqglService.delete(issue.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除保全复核问题失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return AjaxObject.newOk("删除保全复核问题成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}保全复核问题。")
	@RequiresPermissions("Cservice:delete")
	@RequestMapping(value="/issue/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		String[] policys = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				ConservationDtl issue = bqglService.get(ids[i]);
				bqglService.delete(issue.getId());
				
				policys[i] = issue.getPolicy().getPolicyNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除保全复核问题失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("删除保全复核问题成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("Cservice:view")
	@RequestMapping(value="/issue/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		String s = request.getParameter("status");
		LOG.debug("-------------- status: " + s);
		ConservationDtl issue = new ConservationDtl();
		if(s == null) {
			issue.setStatus(BQ_STATUS.NewStatus.name());
			s = BQ_STATUS.NewStatus.name();
		} else if(s.trim().length()>0) {
			issue.setStatus(BQ_STATUS.valueOf(s).name());
		}
		
		Specification<ConservationDtl> specification = DynamicSpecifications.bySearchFilter(request, ConservationDtl.class,
				new SearchFilter("status", Operator.LIKE, s),
				new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		
		List<ConservationDtl> issues = bqglService.findByExample(specification, page);
		
		map.put("issue", issue);
		map.put("baStatusList", BQ_STATUS.values());
		map.put("page", page);
		map.put("issues", issues);
		return LIST;
	}
	
	@RequiresPermissions("Cservice:view")
	@RequestMapping(value="/issuelist", method={RequestMethod.GET, RequestMethod.POST})
	public String issueList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put("issueList", bqglService.getTODOIssueList(shiroUser.getUser()));
		return ISSUE_LIST;
	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
