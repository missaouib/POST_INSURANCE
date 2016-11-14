/**
 * 
 * ==========================================================
 * 续期管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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
import org.springframework.util.Base64Utils;
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
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.OrganizationService;
import com.gdpost.web.service.insurance.XqglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.StatusDefine.XQ_DEAL_STATUS;
import com.gdpost.web.util.StatusDefine.XQ_FEE_STATUS;
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
	
	@Autowired
	private OrganizationService orgService;

	private static final String VIEW = "insurance/xqgl/wtj/view";
	private static final String UPDATE = "insurance/xqgl/wtj/update";
	private static final String PROV_UPDATE = "insurance/xqgl/wtj/provupdate";
	private static final String LIST = "insurance/xqgl/wtj/list";
	private static final String MAX_LIST = "insurance/xqgl/wtj/maxlist";
	private static final String ISSUE_LIST = "insurance/xqgl/wtj/issuelist";
	private static final String TO_HELP = "insurance/help/xqgl";
	
	private static final String TO_XLS = "insurance/xqgl/wtj/toXls";
	
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
	
	@Log(message="回复了{0}续期催收件的信息。", level=LogLevel.WARN, module=LogModule.XQGL)
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
	
	@Log(message="回复了{0}续期催收件的信息。", level=LogLevel.WARN, module=LogModule.XQGL)
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
	
	@Log(message="结案了{0}续期催收件的信息。", level=LogLevel.WARN, module=LogModule.XQGL)
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
		String feeStatus = request.getParameter("search_EQ_feeStatus");
		LOG.debug("-------------- feeStatus: " + feeStatus);
		
		if(feeStatus == null) {
			feeStatus = "";
		}
		
		String encodeStatus = request.getParameter("encodeStatus");
		request.setAttribute("encodeStatus", encodeStatus);
		if((feeStatus != null && feeStatus.trim().equals("null")) || (encodeStatus!= null && encodeStatus.equals("null"))) {
			feeStatus = null;
		}
		if(encodeStatus != null && encodeStatus.trim().length() > 0 ){
			feeStatus = new String(Base64Utils.decodeFromString(encodeStatus));
		} else if(feeStatus != null && feeStatus.trim().length()>0) {
			try {
				encodeStatus = Base64Utils.encodeToString(URLEncoder.encode(feeStatus, "UTF-8").getBytes());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			request.setAttribute("encodeStatus", encodeStatus);
		}
		
		issue.setSearch_EQ_feeStatus(feeStatus);
		String hqIssueType = request.getParameter("hqIssueType");
		String dealType = request.getParameter("dealType");
		String feeFailReason = request.getParameter("feeFailReason");
		String provActivity = request.getParameter("provActivity");
		request.setAttribute("hqIssueType", hqIssueType);
		request.setAttribute("dealType", dealType);
		request.setAttribute("feeFailReason", feeFailReason);
		request.setAttribute("provActivity", provActivity);
		issue.setHqIssueType(hqIssueType);
		issue.setDealType(dealType);
		issue.setProvActivity(provActivity);
		LOG.debug("-------------- feeFailReason: " + feeFailReason);
		issue.setFeeFailReason(feeFailReason);
		
		if(hqIssueType != null && hqIssueType.trim().length()>0) {
			request.setAttribute("encodeHqIssueType", Base64Utils.encodeToString(hqIssueType.getBytes()));
		}
		if(dealType != null && dealType.trim().length()>0) {
			request.setAttribute("encodeDealType", Base64Utils.encodeToString(dealType.getBytes()));
		}
		if(feeFailReason != null && feeFailReason.trim().length()>0) {
			String encodeFeeReason = null;
			try {
				encodeFeeReason = Base64Utils.encodeToString(URLEncoder.encode(feeFailReason, "UTF-8").getBytes());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			request.setAttribute("encodeFeeFailReason", encodeFeeReason);
		}
		
		String orgCode = request.getParameter("policy.orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = user.getOrganization().getOrgCode();
		} else {
			if(!orgCode.contains(user.getOrganization().getOrgCode())){
				orgCode = user.getOrganization().getOrgCode();
			}
			String orgName = request.getParameter("policy.name");
			request.setAttribute("policy_orgCode", orgCode);
			request.setAttribute("policy_name", orgName);
		}
		
		if(page.getOrderField() == null) {
			page.setOrderField("policy.policyDate");
			page.setOrderDirection("ASC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		if (hqIssueType != null && hqIssueType.length() > 0) {
			csf.add(new SearchFilter("hqIssueType", Operator.EQ, hqIssueType));
		}
		if (dealType != null && dealType.length() > 0) {
			csf.add(new SearchFilter("dealType", Operator.EQ, dealType));
		}
		if (feeFailReason != null && feeFailReason.length() > 0) {
			csf.add(new SearchFilter("feeFailReason", Operator.EQ, feeFailReason));
		}
		if (provActivity != null && provActivity.length() > 0) {
			csf.add(new SearchFilter("provActivity", Operator.EQ, provActivity));
		}
		
		Specification<RenewedList> specification = DynamicSpecifications.bySearchFilter(request, RenewedList.class, csf);
		
		List<RenewedList> issues = xqglService.findByExample(specification, page);
		
		List<String> provActivities = xqglService.getProvAcitivity();
		
		map.put("provActivities", provActivities);
		
		map.put("issue", issue);
		map.put("xqStatusList", XQ_STATUS.values());
		map.put("xqDealStatusList", XQ_DEAL_STATUS.values());
		map.put("xqFailReasonList", XQ_FEE_STATUS.values());
		List<RenewalType> cdtList = xqglService.getAllRenewedDealTypeList();
		map.put("orgTypeList", cdtList);
		map.put("page", page);
		map.put("issues", issues);
		return LIST;
	}
	
	@RequiresPermissions("Renewed:view")
	@RequestMapping(value="/issue/maxlist", method={RequestMethod.GET, RequestMethod.POST})
	public String maxlist(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		RenewedList issue = new RenewedList();
		//默认返回未处理工单
		String feeStatus = request.getParameter("feeStatus");
		request.setAttribute("feeStatus", feeStatus);
		String encodeStatus = request.getParameter("encodeStatus");
		request.setAttribute("encodeStatus", encodeStatus);
		if(encodeStatus != null && encodeStatus.trim().length() > 0 ){
			feeStatus = new String(Base64Utils.decodeFromString(encodeStatus));
			try {
				feeStatus = URLDecoder.decode(feeStatus, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else if(feeStatus != null && feeStatus.trim().length()>0) {
			try {
				encodeStatus = Base64Utils.encodeToString(URLEncoder.encode(feeStatus, "UTF-8").getBytes());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			request.setAttribute("encodeStatus", encodeStatus);
		}
		
		String hqIssueType = request.getParameter("hqIssueType");
		String dealType = request.getParameter("dealType");
		String feeFailReason = request.getParameter("feeFailReason");
		String provActivity = request.getParameter("provActivity");
		if(hqIssueType != null && hqIssueType.trim().length()>0) {
			request.setAttribute("encodeHqIssueType", Base64Utils.encodeToString(hqIssueType.getBytes()));
		}
		if(dealType != null && dealType.trim().length()>0) {
			request.setAttribute("encodeDealType", Base64Utils.encodeToString(dealType.getBytes()));
		}
		if(feeFailReason != null && feeFailReason.trim().length()>0) {
			String encodeFeeReason = null;
			try {
				encodeFeeReason = Base64Utils.encodeToString(URLEncoder.encode(feeFailReason, "UTF-8").getBytes());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			request.setAttribute("encodeFeeFailReason", encodeFeeReason);
		}
		
		String encodeHqIssueType = request.getParameter("encodeHqIssueType");
		String encodeDealType = request.getParameter("encodeDealType");
		String encodeFeeFailReason = request.getParameter("encodeFeeFailReason");
		if(encodeHqIssueType != null && encodeHqIssueType.trim().length()>0) {
			hqIssueType = new String(Base64Utils.decodeFromString(encodeHqIssueType));
			request.setAttribute("encodeHqIssueType", encodeHqIssueType);
		}
		if(encodeDealType != null && encodeDealType.trim().length()>0) {
			dealType = new String(Base64Utils.decodeFromString(encodeDealType));
			request.setAttribute("encodeDealType", encodeDealType);
		}
		if(encodeFeeFailReason != null && encodeFeeFailReason.trim().length()>0) {
			try {
				feeFailReason = URLDecoder.decode(new String(Base64Utils.decodeFromString(encodeFeeFailReason)), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			request.setAttribute("encodeFeeFailReason", encodeFeeFailReason);
		}
		
		LOG.debug("-------------- feeStatus: " + feeStatus);
		issue.setFeeStatus(feeStatus);
		issue.setHqIssueType(hqIssueType);
		issue.setDealType(dealType);
		issue.setProvActivity(provActivity);
		LOG.debug("-------------- feeFailReason: " + feeFailReason);
		issue.setFeeFailReason(feeFailReason);
		request.setAttribute("feeStatus", feeStatus);
		request.setAttribute("hqIssueType", hqIssueType);
		request.setAttribute("dealType", dealType);
		request.setAttribute("feeFailReason", feeFailReason);
		request.setAttribute("provActivity", provActivity);
		
		String orgCode = request.getParameter("policy.orgCode");
		boolean hasOrg = true;
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = user.getOrganization().getOrgCode();
			hasOrg = false;
		} else {
			if(!orgCode.contains(user.getOrganization().getOrgCode())){
				orgCode = user.getOrganization().getOrgCode();
			}
		}
		if(hasOrg) {
			String orgName = orgService.getByOrgCode(orgCode).getName();
			request.setAttribute("policy_orgCode", orgCode);
			request.setAttribute("policy_name", orgName);
		}
		
		if(page.getOrderField() == null) {
			page.setOrderField("policy.policyDate");
			page.setOrderDirection("ASC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		if (feeStatus != null && feeStatus.length() > 0) {
			csf.add(new SearchFilter("feeStatus", Operator.EQ, feeStatus));
		}
		if (hqIssueType != null && hqIssueType.length() > 0) {
			csf.add(new SearchFilter("hqIssueType", Operator.EQ, hqIssueType));
		}
		if (dealType != null && dealType.length() > 0) {
			csf.add(new SearchFilter("dealType", Operator.EQ, dealType));
		}
		if (feeFailReason != null && feeFailReason.length() > 0) {
			csf.add(new SearchFilter("feeFailReason", Operator.EQ, feeFailReason));
		}
		if (provActivity != null && provActivity.length() > 0) {
			csf.add(new SearchFilter("provActivity", Operator.EQ, provActivity));
		}
		
		Specification<RenewedList> specification = DynamicSpecifications.bySearchFilter(request, RenewedList.class, csf);
		
		List<RenewedList> issues = xqglService.findByExample(specification, page);
		
		List<String> provActivities = xqglService.getProvAcitivity();
		
		map.put("provActivities", provActivities);
		
		map.put("mxissue", issue);
		map.put("mxxqStatusList", XQ_STATUS.values());
		map.put("mxxqDealStatusList", XQ_DEAL_STATUS.values());
		map.put("mxxqFailReasonList", XQ_FEE_STATUS.values());
		List<RenewalType> cdtList = xqglService.getAllRenewedDealTypeList();
		map.put("mxorgTypeList", cdtList);
		map.put("page", page);
		map.put("issues", issues);
		return MAX_LIST;
	}
	
	@Log(message="下载了续期催收件列表。", level=LogLevel.INFO, module=LogModule.XQGL)
	@RequiresPermissions("Renewed:view")
	@RequestMapping(value="/toXls", method=RequestMethod.GET)
	public String toXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		//默认返回未处理工单
		String feeStatus = request.getParameter("encodeStatus");
		String hqIssueType = request.getParameter("encodeHqIssueType");
		String dealType = request.getParameter("encodeDealType");
		String feeFailReason = request.getParameter("encodeFeeFailReason");
		String provActivity = request.getParameter("provActivity");
		
		if(feeStatus != null && feeStatus.trim().length() > 0 ){
			feeStatus = new String(Base64Utils.decodeFromString(feeStatus));
			try {
				feeStatus = URLDecoder.decode(feeStatus, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(hqIssueType != null && hqIssueType.trim().length()>0) {
			hqIssueType = new String(Base64Utils.decodeFromString(hqIssueType));
		}
		if(dealType != null && dealType.trim().length()>0) {
			dealType = new String(Base64Utils.decodeFromString(dealType));
		}
		if(feeFailReason != null && feeFailReason.trim().length()>0) {
			try {
				feeFailReason = URLDecoder.decode(new String(Base64Utils.decodeFromString(feeFailReason)), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		String orgCode = request.getParameter("policy.orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = user.getOrganization().getOrgCode();
		} else if(!orgCode.contains(user.getOrganization().getOrgCode())){
			orgCode = user.getOrganization().getOrgCode();
		}
		if(page.getOrderField() == null) {
			page.setOrderField("policy.policyDate");
			page.setOrderDirection("ASC");
		}
		page.setNumPerPage(65564);
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		if (feeStatus != null && feeStatus.length() > 0) {
			csf.add(new SearchFilter("feeStatus", Operator.EQ, feeStatus));
		}
		if (hqIssueType != null && hqIssueType.length() > 0) {
			csf.add(new SearchFilter("hqIssueType", Operator.EQ, hqIssueType));
		}
		if (dealType != null && dealType.length() > 0) {
			csf.add(new SearchFilter("dealType", Operator.EQ, dealType));
		}
		if (feeFailReason != null && feeFailReason.length() > 0) {
			csf.add(new SearchFilter("feeFailReason", Operator.EQ, feeFailReason));
		}
		if (provActivity != null && provActivity.length() > 0) {
			csf.add(new SearchFilter("provActivity", Operator.EQ, provActivity));
		}
		
		Specification<RenewedList> specification = DynamicSpecifications.bySearchFilter(request, RenewedList.class, csf);
		
		List<RenewedList> issues = xqglService.findByExample(specification, page);
		
		map.put("reqs", issues);
		return TO_XLS;
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
