/**
 * 
 * ==========================================================
 * 回访管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.entity.main.CallFailList;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.RenewedList;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.insurance.HfglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.StatusDefine.HF_STATUS;
import com.gdpost.web.util.StatusDefine.XQ_STATUS;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/hfgl")
public class HfglController {
	private static final Logger LOG = LoggerFactory.getLogger(HfglController.class);
	
	@Autowired
	private HfglService hfglService;

	private static final String VIEW = "insurance/hfgl/wtj/view";
	private static final String UPDATE = "insurance/hfgl/wtj/update";
	private static final String PROV_UPDATE = "insurance/hfgl/wtj/provupdate";
	private static final String LIST = "insurance/hfgl/wtj/list";
	private static final String ISSUE_LIST = "insurance/hfgl/wtj/issuelist";
	
	@RequiresPermissions("Callfail:view")
	@RequestMapping(value="/issue/view/{id}", method=RequestMethod.GET)
	public String view(@PathVariable Long id, Map<String, Object> map) {
		CallFailList issue = hfglService.get(id);
		
		map.put("issue", issue);
		//map.put("status", HF_STATUS.ReopenStatus);
		return VIEW;
	}
	
	@RequiresPermissions("Callfail:edit")
	@RequestMapping(value="/issue/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		CallFailList issue = hfglService.get(id);
		
		map.put("issue", issue);
		return UPDATE;
	}
	
	@RequiresPermissions("Callfail:provEdit")
	@RequestMapping(value="/issue/provUpdate/{id}", method=RequestMethod.GET)
	public String preProvUpdate(@PathVariable Long id, Map<String, Object> map) {
		CallFailList issue = hfglService.get(id);
		
		map.put("issue", issue);
		return PROV_UPDATE;
	}
	
	@Log(message="回复了{0}回访不成功件的信息。")
	@RequiresPermissions("Callfail:edit")
	@RequestMapping(value="/issue/update", method=RequestMethod.POST)
	public @ResponseBody String update(CallFailList issue) {
		CallFailList src = hfglService.get(issue.getId());
		src.setDealMan(issue.getDealMan());
		src.setDealTime(issue.getDealTime());
		src.setDealDesc(issue.getDealDesc());
		src.setDealNum(issue.getDealNum() + 1);
		src.setStatus(HF_STATUS.DealStatus.getDesc());
		hfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复回访不成功件成功！").toString(); 
	}
	
	@Log(message="回复了{0}回访不成功件的信息。")
	@RequiresPermissions("Callfail:provEdit")
	@RequestMapping(value="/issue/provUpdate", method=RequestMethod.POST)
	public @ResponseBody String provUpdate(RenewedList issue) {
		CallFailList src = hfglService.get(issue.getId());
		src.setProvDealRst(issue.getProvDealRst());
		src.setProvDealDate(new Date());
		src.setProvDealRemark(issue.getProvDealRemark());
		src.setStatus(XQ_STATUS.DealStatus.getDesc());
		src.setProvDealNum(src.getProvDealNum());
		hfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复回访不成功件成功！").toString(); 
	}
	
	@Log(message="回复了{0}回访不成功件的信息。")
	@RequiresPermissions("Callfail:11185Edit")
	@RequestMapping(value="/issue/hqUpdate", method=RequestMethod.POST)
	public @ResponseBody String hqUpdate(RenewedList issue) {
		CallFailList src = hfglService.get(issue.getId());
		src.setHqDealRst(issue.getProvDealRst());
		src.setHqDealDate(new Date());
		src.setHqDealRemark(issue.getProvDealRemark());
		src.setHqDealNum(src.getHqDealNum()+1);
		src.setStatus(XQ_STATUS.DealStatus.getDesc());
		hfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复回访不成功件成功！").toString(); 
	}
	
	@Log(message="结案了{0}回访不成功件的信息。")
	@RequiresPermissions("Callfail:edit")
	@RequestMapping(value="/issue/close", method=RequestMethod.POST)
	public @ResponseBody String close(@Valid @ModelAttribute("preload")CallFailList issue) {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		CallFailList src = hfglService.get(issue.getId());
		src.setStatus(HF_STATUS.CloseStatus.getDesc());
		hfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("结案回访不成功件成功！").toString(); 
	}
	
	@RequiresPermissions("Callfail:view")
	@RequestMapping(value="/issue/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		String status = request.getParameter("status");
		LOG.debug("-------------- status: " + status + ", user org code:" + userOrg.getOrgCode());
		CallFailList issue = new CallFailList();
		if(status == null) {
			status = HF_STATUS.NewStatus.getDesc();
		} else if(status.trim().length()>0) {
			issue.setStatus(HF_STATUS.valueOf(status).getDesc());
		}
		issue.setStatus(status);
		
		Specification<CallFailList> specification = DynamicSpecifications.bySearchFilter(request, CallFailList.class,
				new SearchFilter("status", Operator.LIKE, status),
				new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		
		List<CallFailList> issues = hfglService.findByExample(specification, page);
		
		map.put("issue", issue);
		map.put("statusList", HF_STATUS.values());
		LOG.debug("---111--" + issues);
		map.put("page", page);
		map.put("issues", issues);
		return LIST;
	}
	
	@RequiresPermissions("Callfail:view")
	@RequestMapping(value="/issuelist", method={RequestMethod.GET, RequestMethod.POST})
	public String issueList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put("issueList", hfglService.getTODOIssueList(shiroUser.getUser()));
		return ISSUE_LIST;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
