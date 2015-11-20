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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.entity.main.ConservationDtl;
import com.gdpost.web.entity.main.OffsiteConservation;
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
	private static final String TO_HELP = "insurance/help/bqgl";

	private static final String TO_XLS = "insurance/bqgl/wtj/toXls";
	
	private static final String CREATE_OC = "insurance/bqgl/offsite/create";
	private static final String UPDATE_OC = "insurance/bqgl/offsite/update";
	private static final String PROV_UPDATE_OC = "insurance/bqgl/offsite/provupdate";
	private static final String LIST_OC = "insurance/bqgl/offsite/list";

	private static final String OC_TO_XLS = "insurance/bqgl/offsite/toXls";
	
	@RequiresPermissions("Cservice:save")
	@RequestMapping(value="/issue/create", method=RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}
	
	@RequestMapping(value="/help", method=RequestMethod.GET)
	public String toHelp() {
		return TO_HELP;
	}
	
	@Log(message="添加了{0}保全复核问题。")
	@RequiresPermissions("Cservice:save")
	@RequestMapping(value="/issue/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid ConservationDtl issue) {	
		try {
			issue.setCsDate(new Date());
			issue.setCsUserId(SecurityUtils.getShiroUser().getId());
			issue.setStatus(BQ_STATUS.NewStatus.name());
			if(issue.getInfo().equals("审核通过")) {
				issue.setStatus(BQ_STATUS.CloseStatus.name());
			}
			bqglService.saveOrUpdate(issue);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加保全复核问题失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return AjaxObject.newOk("添加保全复核问题成功！").toString();
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
	public @ResponseBody String update(ConservationDtl issue) {
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
	
	@Log(message="批量修改了{0}保全复核问题的{1}状态。")
	@RequiresPermissions(value={"Cservice:reset","Cservice:deal"}, logical=Logical.OR)
	@RequestMapping(value="/issue/{status}", method=RequestMethod.POST)
	public @ResponseBody String batchUpdateStatus(@PathVariable("status") String status, Long[] ids) {
		ConservationDtl issue = null;
		BQ_STATUS bs = BQ_STATUS.DealStatus;
		try {
			bs = BQ_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		String[] policys = new String[ids.length];
		for(int i = 0; i<ids.length; i++) {
			issue = bqglService.get(ids[i]);
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
			policys[i] = issue.getPolicy().getPolicyNo();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys), status}));
		return	AjaxObject.newOk("批量" + status + "保全复核问题成功！").setCallbackType("").toString();
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
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		//默认返回未处理工单
		String s = request.getParameter("status");
		LOG.debug("-------------- status: " + s);
		ConservationDtl issue = new ConservationDtl();
		if(s == null) {
			s = "";
		} else if(s.trim().length()>0) {
			issue.setStatus(BQ_STATUS.valueOf(s).name());
		}
		
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("csDate");
			page.setOrderDirection("DESC");
		}
		
		Specification<ConservationDtl> specification = DynamicSpecifications.bySearchFilter(request, ConservationDtl.class,
				new SearchFilter("status", Operator.LIKE, s),
				new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		
		List<ConservationDtl> issues = bqglService.findByExample(specification, page);
		
		map.put("issue", issue);
		map.put("status", s);
		map.put("baStatusList", BQ_STATUS.values());
		map.put("page", page);
		map.put("issues", issues);
		return LIST;
	}
	
	@RequiresPermissions("Cservice:view")
	@RequestMapping(value="/toXls", method=RequestMethod.GET)
	public String toXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		page.setNumPerPage(65564);
		//默认返回未处理工单
		String s = request.getParameter("status");
		LOG.debug("-------------- status: " + s);
		ConservationDtl issue = new ConservationDtl();
		if(s == null) {
			/*
			issue.setStatus(BQ_STATUS.NewStatus.name());
			s = BQ_STATUS.NewStatus.name();
			if (userOrg.getOrgCode().length()<=4) {
				issue.setStatus(BQ_STATUS.DealStatus.name());
				s = BQ_STATUS.DealStatus.name();
			}
			*/
			s = "";
		} else if(s.trim().length()>0) {
			issue.setStatus(BQ_STATUS.valueOf(s).name());
		}
		
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("csDate");
			page.setOrderDirection("DESC");
		}
		
		Specification<ConservationDtl> specification = DynamicSpecifications.bySearchFilter(request, ConservationDtl.class,
				new SearchFilter("status", Operator.LIKE, s),
				new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		
		List<ConservationDtl> reqs = bqglService.findByExample(specification, page);
		
		map.put("reqs", reqs);
		return TO_XLS;
	}
	
	@RequiresPermissions("Cservice:view")
	@RequestMapping(value="/issuelist", method={RequestMethod.GET, RequestMethod.POST})
	public String issueList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put("issueList", bqglService.getTODOIssueList(shiroUser.getUser()));
		return ISSUE_LIST;
	}
	
	/*
	 * ===========================================
	 * 异地保全管理
	 * ===========================================
	 */
	
	@RequiresPermissions("OffsiteConservation:save")
	@RequestMapping(value="/offsite/create", method=RequestMethod.GET)
	public String preCreateOffsiteConservation() {
		return CREATE_OC;
	}
	
	@Log(message="添加了{0}异地保全。")
	@RequiresPermissions("OffsiteConservation:save")
	@RequestMapping(value="/offsite/create", method=RequestMethod.POST)
	public @ResponseBody String createOffsiteConservation(@Valid OffsiteConservation offsite) {	
		try {
			offsite.setOperateTime(new Date());
			offsite.setOperatorId(SecurityUtils.getShiroUser().getId());
			offsite.setStatus(BQ_STATUS.NewStatus.name());
			bqglService.saveOrUpdateOffsiteConservation(offsite);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加异地保全失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{offsite.getPolicyNo()}));
		return AjaxObject.newOk("添加异地保全成功！").toString();
	}
	
	@RequiresPermissions("OffsiteConservation:edit")
	@RequestMapping(value="/offsite/update/{id}", method=RequestMethod.GET)
	public String preUpdateOffsiteConservation(@PathVariable Long id, Map<String, Object> map) {
		OffsiteConservation offsite = bqglService.getOffsiteConservation(id);
		
		map.put("offsite", offsite);
		return UPDATE_OC;
	}
	
	@RequiresPermissions("OffsiteConservation:provEdit")
	@RequestMapping(value="/offsite/provupdate/{id}", method=RequestMethod.GET)
	public String preProvUpdateOffsiteConservation(@PathVariable Long id, Map<String, Object> map) {
		OffsiteConservation offsite = bqglService.getOffsiteConservation(id);
		
		map.put("offsite", offsite);
		return PROV_UPDATE_OC;
	}
	
	@Log(message="修改了{0}异地保全的信息。")
	@RequiresPermissions("OffsiteConservation:edit")
	@RequestMapping(value="/offsite/update", method=RequestMethod.POST)
	public @ResponseBody String updateOffsiteConservation(OffsiteConservation offsite) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();
		Organization userOrg = user.getOrganization();
		if(userOrg.getOrgCode().length()>4) {
			offsite.setStatus(BQ_STATUS.NewStatus.name());
		} else {
			offsite.setStatus(BQ_STATUS.DealStatus.name());
		}
		bqglService.saveOrUpdateOffsiteConservation(offsite);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{offsite.getPolicyNo()}));
		return	AjaxObject.newOk("修改异地保全成功！").toString(); 
	}
	
	@Log(message="修改了{0}异地保全的状态。")
	@RequiresPermissions(value={"OffsiteConservation:reset","OffsiteConservation:deal"}, logical=Logical.OR)
	@RequestMapping(value="/offsite/{status}/{id}", method=RequestMethod.POST)
	public @ResponseBody String updateOffsiteConservationStatus(@PathVariable("status") String status, @PathVariable("id") Long id) {
		OffsiteConservation offsite = bqglService.getOffsiteConservation(id);
		BQ_STATUS bs = BQ_STATUS.DealStatus;
		try {
			bs = BQ_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		switch (bs) {
		case CloseStatus:
			break;
			default:
				break;
		}
		offsite.setStatus(status);
		bqglService.saveOrUpdateOffsiteConservation(offsite);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{offsite.getPolicyNo()}));
		return	AjaxObject.newOk("修改异地保全成功！").setCallbackType("").toString();
	}
	
	@Log(message="批量修改了{0}异地保全的{1}状态。")
	@RequiresPermissions(value={"OffsiteConservation:reset","OffsiteConservation:deal"}, logical=Logical.OR)
	@RequestMapping(value="/offsite/{status}", method=RequestMethod.POST)
	public @ResponseBody String batchUpdateOffsiteConservationStatus(@PathVariable("status") String status, Long[] ids) {
		OffsiteConservation offsite = null;
		BQ_STATUS bs = BQ_STATUS.DealStatus;
		try {
			bs = BQ_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		String[] policys = new String[ids.length];
		for(int i = 0; i<ids.length; i++) {
			offsite = bqglService.getOffsiteConservation(ids[i]);
			switch (bs) {
			case CloseStatus:
				break;
				default:
					break;
			}
			offsite.setStatus(status);
			bqglService.saveOrUpdateOffsiteConservation(offsite);
			policys[i] = offsite.getPolicyNo();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys), status}));
		return	AjaxObject.newOk("批量" + status + "异地保全成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}异地保全。")
	@RequiresPermissions("OffsiteConservation:delete")
	@RequestMapping(value="/offsite/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteOffsiteConservation(@PathVariable Long id) {
		OffsiteConservation offsite = null;
		try {
			offsite = bqglService.getOffsiteConservation(id);
			bqglService.deleteOffsiteConservation(offsite.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除异地保全失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{offsite.getPolicyNo()}));
		return AjaxObject.newOk("删除异地保全成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}异地保全。")
	@RequiresPermissions("OffsiteConservation:delete")
	@RequestMapping(value="/offsite/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyOffsiteConservation(Long[] ids) {
		String[] policys = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				OffsiteConservation offsite = bqglService.getOffsiteConservation(ids[i]);
				bqglService.deleteOffsiteConservation(offsite.getId());
				
				policys[i] = offsite.getPolicyNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除异地保全失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("删除异地保全成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("OffsiteConservation:view")
	@RequestMapping(value="/offsite/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listOffsiteConservation(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(user.getOrganization().getOrgCode())){
			orgCode = user.getOrganization().getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		//默认返回未处理工单
		String s = request.getParameter("status");
		LOG.debug("-------------- status: " + s + "  orgCode:" + orgCode);
		OffsiteConservation offsite = new OffsiteConservation();
		if(s == null) {
			s = "";
		} else if(s.trim().length()>0) {
			offsite.setStatus(BQ_STATUS.valueOf(s).name());
		}
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("dealDate");
			page.setOrderDirection("DESC");
		}
		Specification<OffsiteConservation> specification = DynamicSpecifications.bySearchFilter(request, OffsiteConservation.class,
				new SearchFilter("status", Operator.LIKE, s));
		
		List<OffsiteConservation> offsites = bqglService.findByOffsiteConservationExample(specification, page);
		
		map.put("offsite", offsite);
		map.put("status", s);
		map.put("baStatusList", BQ_STATUS.values());
		map.put("page", page);
		map.put("offsites", offsites);
		return LIST_OC;
	}
	
	@RequiresPermissions("OffsiteConservation:view")
	@RequestMapping(value="/offsite/toXls", method=RequestMethod.GET)
	public String ocToXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(user.getOrganization().getOrgCode())){
			orgCode = user.getOrganization().getOrgCode();
		}
		page.setNumPerPage(65564);
		//默认返回未处理工单
		String s = request.getParameter("status");
		LOG.debug("-------------- status: " + s);
		OffsiteConservation offsite = new OffsiteConservation();
		if(s == null) {
			s = "";
			/*
			offsite.setStatus(BQ_STATUS.NewStatus.name());
			s = BQ_STATUS.NewStatus.name();
			if (userOrg.getOrgCode().length()<=4) {
				offsite.setStatus(BQ_STATUS.DealStatus.name());
				s = BQ_STATUS.DealStatus.name();
			}
			*/
		} else if(s.trim().length()>0) {
			offsite.setStatus(BQ_STATUS.valueOf(s).name());
		}
		
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("operateTime");
			page.setOrderDirection("DESC");
		}
		
		Specification<OffsiteConservation> specification = DynamicSpecifications.bySearchFilter(request, OffsiteConservation.class,
				new SearchFilter("status", Operator.LIKE, s),
				new SearchFilter("organization.orgCode", Operator.LIKE, orgCode));
		
		List<OffsiteConservation> reqs = bqglService.findByOffsiteConservationExample(specification, page);
		
		map.put("reqs", reqs);
		return OC_TO_XLS;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
