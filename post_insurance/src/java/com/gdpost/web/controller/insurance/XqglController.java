/**
 * 
 * ==========================================================
 * 续期管理
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
import com.gdpost.web.entity.basedata.RenewalType;
import com.gdpost.web.entity.main.RenewedList;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.insurance.XqglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.StatusDefine.XQ_DEAL_STATUS;
import com.gdpost.web.util.StatusDefine.XQ_STATUS;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/xqgl")
public class XqglController {
	private static final Logger LOG = LoggerFactory.getLogger(XqglController.class);
	
	@Autowired
	private XqglService xqglService;

	private static final String VIEW = "insurance/xqgl/wtj/view";
	private static final String UPDATE = "insurance/xqgl/wtj/update";
	private static final String PROV_UPDATE = "insurance/xqgl/wtj/provupdate";
	private static final String LIST = "insurance/xqgl/wtj/list";
	private static final String ISSUE_LIST = "insurance/xqgl/wtj/issuelist";
	private static final String TO_HELP = "insurance/help/xqgl";
	
	@RequestMapping(value="/help", method=RequestMethod.GET)
	public String toHelp() {
		return TO_HELP;
	}
	
	@RequiresPermissions("Renewed:view")
	@RequestMapping(value="/issue/view/{id}", method=RequestMethod.GET)
	public String view(@PathVariable Long id, Map<String, Object> map) {
		RenewedList issue = xqglService.get(id);
		
		map.put("issue", issue);
		//map.put("feeStatus", XQ_STATUS.ReopenStatus);
		return VIEW;
	}
	
	@RequiresPermissions("Renewed:edit")
	@RequestMapping(value="/issue/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		RenewedList issue = xqglService.get(id);
		
		map.put("issue", issue);
		List<RenewalType> cdtList = xqglService.getAllRenewedDealTypeList();
		map.put("orgTypeList", cdtList);
		return UPDATE;
	}
	
	@RequiresPermissions("Renewed:provEdit")
	@RequestMapping(value="/issue/provUpdate/{id}", method=RequestMethod.GET)
	public String preProvUpdate(@PathVariable Long id, Map<String, Object> map) {
		RenewedList issue = xqglService.get(id);
		
		map.put("issue", issue);
		List<RenewalType> cdtList = xqglService.getAllRenewedDealTypeList();
		map.put("orgTypeList", cdtList);
		return PROV_UPDATE;
	}
	
	@Log(message="回复了{0}续期催收件的信息。")
	@RequiresPermissions("Renewed:edit")
	@RequestMapping(value="/issue/update", method=RequestMethod.POST)
	public @ResponseBody String update(RenewedList issue) {
		RenewedList src = xqglService.get(issue.getId());
		src.setDealType(issue.getDealType());
		src.setDealMan(issue.getDealMan());
		src.setDealTime(issue.getDealTime());
		src.setFixDesc(issue.getFixDesc());
		src.setFixStatus(issue.getFixStatus());
		xqglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复续期催收件成功！").toString(); 
	}
	
	@Log(message="回复了{0}续期催收件的信息。")
	@RequiresPermissions("Renewed:provEdit")
	@RequestMapping(value="/issue/provUpdate", method=RequestMethod.POST)
	public @ResponseBody String provUpdate(RenewedList issue) {
		RenewedList src = xqglService.get(issue.getId());
		src.setProvDealRst(issue.getProvDealRst());
		src.setProvDealDate(new Date());
		src.setProvDealRemark(issue.getProvDealRemark());
		src.setProvDealMan(issue.getProvDealMan());
		src.setProvIssueType(issue.getProvIssueType());
		xqglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复续期催收件成功！").toString(); 
	}
	
	@Log(message="结案了{0}续期催收件的信息。")
	@RequiresPermissions("Renewed:edit")
	@RequestMapping(value="/issue/close", method=RequestMethod.POST)
	public @ResponseBody String close(@Valid @ModelAttribute("preload")RenewedList issue) {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		RenewedList src = xqglService.get(issue.getId());
		src.setFixStatus(XQ_STATUS.CloseStatus.getDesc());
		xqglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("结案续期催收件成功！").toString(); 
	}
	
	@RequiresPermissions("Renewed:view")
	@RequestMapping(value="/issue/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		RenewedList issue = new RenewedList();
		//默认返回未处理工单
		String feeStatus = request.getParameter("search_LIKE_feeStatus");
		String hqDealRemark = request.getParameter("search_LIKE_hqDealRemark");
		String dealType = request.getParameter("search_LIKE_dealType");
		if(hqDealRemark == null) {
			hqDealRemark = "";
		}
		issue.setSearch_LIKE_hqDealRemark(hqDealRemark);
		if(dealType == null) {
			dealType = "";
		}
		issue.setSearch_LIKE_dealType(dealType);
		LOG.debug("-------------- feeStatus: " + feeStatus);
		if(feeStatus == null) {
			feeStatus = "";
		}
		issue.setSearch_LIKE_feeStatus(feeStatus);
		
		String orgCode = request.getParameter("policy.orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = user.getOrganization().getOrgCode();
		} else {
			String orgName = request.getParameter("policy.name");
			request.setAttribute("policy_orgCode", orgCode);
			request.setAttribute("policy_name", orgName);
		}
		
		if(page.getOrderField() == null) {
			page.setOrderField("policy.policyDate");
			page.setOrderDirection("ASC");
		}
		
		Specification<RenewedList> specification = null;
		
		specification = DynamicSpecifications.bySearchFilter(request, RenewedList.class,
				new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		
		List<RenewedList> issues = xqglService.findByExample(specification, page);
		
		map.put("issue", issue);
		map.put("xqStatusList", XQ_STATUS.values());
		map.put("xqDealStatusList", XQ_DEAL_STATUS.values());
		List<RenewalType> cdtList = xqglService.getAllRenewedDealTypeList();
		map.put("orgTypeList", cdtList);
		map.put("page", page);
		map.put("issues", issues);
		return LIST;
	}
	
	@RequiresPermissions("Renewed:view")
	@RequestMapping(value="/issuelist", method={RequestMethod.GET, RequestMethod.POST})
	public String issueList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put("issueList", xqglService.getTODOIssueList(shiroUser.getUser()));
		return ISSUE_LIST;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
