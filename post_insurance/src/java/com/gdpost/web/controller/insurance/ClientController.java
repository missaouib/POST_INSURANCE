/**
 * 
 * ==========================================================
 * 发票管理
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.StringUtil;
import com.gdpost.web.entity.component.PolicyDataModel;
import com.gdpost.web.entity.insurance.Policy;
import com.gdpost.web.entity.insurance.PolicyReprintDtl;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.service.insurance.PolicyService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/client")
public class ClientController {
	private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);
	
	@Autowired
	private PolicyService policyService;

	private static final String VIEW = "insurance/basedata/client/view";
	private static final String LIST = "insurance/basedata/client/list";
	private static final String TO_XLS = "insurance/basedata/client/toXls";
	
	private static final String PD_TO_XLS = "insurance/basedata/client/pdToXls";
	private static final String JG_TO_XLS = "insurance/basedata/client/jgToXls";
	
	private static final String RE_PRINT_LIST = "insurance/search/reprint/list";
	private static final String RE_PRINT_LIST_XLS = "insurance/search/reprint/toXls";
	
	@RequiresPermissions("Client:view")
	@RequestMapping(value="/view/{id}", method=RequestMethod.GET)
	public String view(@PathVariable Long id, Map<String, Object> map) {
		Policy req = policyService.get(id);
		
		map.put("policy", req);
		return VIEW;
	}
	
	@RequiresPermissions("Client:view")
	@RequestMapping(value="/view/byPolicyNo/{policyNo}", method=RequestMethod.GET)
	public String viewByPolicyNo(@PathVariable String policyNo, Map<String, Object> map) {
		Policy req = policyService.getByPolicyNo(policyNo);
		
		map.put("policy", req);
		return VIEW;
	}
	
	
	@RequiresPermissions("Client:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		String status = request.getParameter("status");
		if(status != null && status.trim().length() > 0) {
			request.setAttribute("encodeStatus", Base64Utils.encodeToString(status.getBytes()));
		}
		LOG.debug("-------------- status: " + status + ", user org code:" + userOrg.getOrgCode());
		Policy policy = new Policy();
		policy.setStatus(status);
		
		String attachedFlag = request.getParameter("attachedFlag");
		String feeFrequency = request.getParameter("feeFrequency");
		String staffFlag = request.getParameter("staffFlag");
		String duration = request.getParameter("duration");
		String saleChannel = request.getParameter("saleChannel");
		String isPhone = request.getParameter("search_LIKE_policyDtl.holderPhone");
		Boolean staff = null;
		if(staffFlag != null && staffFlag.trim().equals("0")) {
			staff = false;
		} else if(staffFlag != null && staffFlag.trim().equals("1")) {
			staff = true;
		}
		policy.setAttachedFlag((attachedFlag==null||attachedFlag.trim().length()<=0)?null:new Integer(attachedFlag));
		policy.setFeeFrequency(feeFrequency);
		policy.setStaffFlag(staff);
		policy.setSaleChannel(saleChannel);
		policy.setDuration(Integer.valueOf(duration==null||duration.trim().length()<=0?"0":duration));
		
		map.put("policy", policy);
		map.put("page", page);
		if(request.getParameterMap().size()<=1) {
			return LIST;
		}
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("policyDate");
			page.setOrderDirection("DESC");
		}
		
		String orgCode = request.getParameter("orgCode");
		String orgName = request.getParameter("name");
		if(orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = user.getOrganization().getOrgCode();
			if(orgCode.contains("11185")) {
				orgCode = "8644";
			}
		} else {
			if(!orgCode.contains(user.getOrganization().getOrgCode())){
				orgCode = user.getOrganization().getOrgCode();
				orgName = user.getOrganization().getName();
			}
		}
		
		request.setAttribute("policy_orgCode", orgCode);
		request.setAttribute("policy_name", orgName);
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		if(isPhone != null && isPhone.trim().length()>0) {
			csf.add(new SearchFilter("organization.orgCode", Operator.LIKE_R, orgCode));
		}
		if(status != null && status.trim().length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
			try {
				request.setAttribute("jgStatus", URLEncoder.encode(status, "UTF8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		String prdName = request.getParameter("prd.prdFullName");
		if(prdName != null && prdName.trim().length()>0) {
			csf.add(new SearchFilter("prodName", Operator.EQ, prdName));
			request.setAttribute("prd_name", prdName);
			try {
				request.setAttribute("prodName", URLEncoder.encode(prdName, "UTF8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(attachedFlag != null && attachedFlag.trim().length()>0) {
			csf.add(new SearchFilter("attachedFlag", Operator.EQ, attachedFlag));
			request.setAttribute("attachedFlag", attachedFlag);
		}
		if(feeFrequency != null && feeFrequency.trim().length()>0) {
			csf.add(new SearchFilter("feeFrequency", Operator.EQ, feeFrequency));
			request.setAttribute("feeFrequency", feeFrequency);
		}
		if(staff != null) {
			csf.add(new SearchFilter("staffFlag", Operator.EQ, staff));
			request.setAttribute("staffFlag", staffFlag);
		}
		if(duration != null && duration.trim().length()>0 && !duration.equals("0")) {
			csf.add(new SearchFilter("duration", Operator.GTE, duration));
			request.setAttribute("duration", duration);
		}
		
		if(saleChannel != null && saleChannel.trim().length()>0) {
			csf.add(new SearchFilter("policyNo", Operator.LIKE_R, saleChannel));
			request.setAttribute("saleChannel", saleChannel);
		}
		
		Specification<Policy> specification = DynamicSpecifications.bySearchFilter(request, Policy.class, csf);
		List<Policy> policies = policyService.findByExample(specification, page);
		
		map.put("policies", policies);
		return LIST;
	}
	
	@RequiresPermissions("PolicyReprintDtl:view")
	@RequestMapping(value="/listPolicyReprintDtl", method={RequestMethod.GET, RequestMethod.POST})
	public String listPolicyReprintDtl(ServletRequest request, Page page, Map<String, Object> map) {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		//User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("printDate");
			page.setOrderDirection("DESC");
		}
		String plFlag = request.getParameter("plFlag");
		if(plFlag == null) plFlag = "";
		
		/*
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = user.getOrganization().getOrgCode();
			if(orgCode.contains("11185")) {
				orgCode = "8644";
			}
		} else {
			if(!orgCode.contains(user.getOrganization().getOrgCode())){
				orgCode = user.getOrganization().getOrgCode();
			}
			String orgName = request.getParameter("name");
			request.setAttribute("orgCode", orgCode);
			request.setAttribute("name", orgName);
		}*/
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		//csf.add(new SearchFilter("orgCode", Operator.LIKE, orgCode));
		
		String prdName = request.getParameter("prd.prdFullName");
		if(prdName != null && prdName.trim().length()>0) {
			csf.add(new SearchFilter("prodName", Operator.EQ, prdName));
		}
		if(plFlag != null && plFlag.trim().length()>0) {
			csf.add(new SearchFilter("plFlag", Operator.EQ, plFlag));
		}
		Specification<PolicyReprintDtl> specification = DynamicSpecifications.bySearchFilter(request, PolicyReprintDtl.class, csf);
		List<PolicyReprintDtl> policies = policyService.findByPolicyReprintDtlExample(specification, page);
		
		map.put("plFlag", plFlag);
		map.put("page", page);
		map.put("policies", policies);
		return RE_PRINT_LIST;
	}
	
	@Log(message="下载了保单打印数据", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequiresPermissions("PolicyReprintDtl:view")
	@RequestMapping(value="/listPolicyReprintDtl/toXls", method={RequestMethod.GET, RequestMethod.POST})
	public String reprintDtlToXls(ServletRequest request, Page page, Map<String, Object> map) {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		//User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("printDate");
			page.setOrderDirection("DESC");
		}
		
		page.setNumPerPage(Integer.MAX_VALUE);
		
		String plFlag = request.getParameter("plFlag");
		if(plFlag == null) plFlag = "";
		/*
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = user.getOrganization().getOrgCode();
			if(orgCode.contains("11185")) {
				orgCode = "8644";
			}
		} else {
			if(!orgCode.contains(user.getOrganization().getOrgCode())){
				orgCode = user.getOrganization().getOrgCode();
			}
			String orgName = request.getParameter("name");
			request.setAttribute("orgCode", orgCode);
			request.setAttribute("name", orgName);
		}
		*/
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		//csf.add(new SearchFilter("orgCode", Operator.LIKE, orgCode));
		
		String prdName = request.getParameter("prd.prdFullName");
		if(prdName != null && prdName.trim().length()>0) {
			csf.add(new SearchFilter("prodName", Operator.EQ, prdName));
		}
		if(plFlag != null && plFlag.trim().length()>0) {
			csf.add(new SearchFilter("plFlag", Operator.EQ, plFlag));
		}
		Specification<PolicyReprintDtl> specification = DynamicSpecifications.bySearchFilter(request, PolicyReprintDtl.class, csf);
		List<PolicyReprintDtl> policies = policyService.findByPolicyReprintDtlExample(specification, page);
		
		map.put("policies", policies);
		return RE_PRINT_LIST_XLS;
	}
	
	@Log(message="下载了保单数据！", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("Client:view")
	@RequestMapping(value="/toXls", method=RequestMethod.GET)
	public String toXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		//默认返回未处理工单
		String status = request.getParameter("status");
		
		String attachedFlag = request.getParameter("attachedFlag");
		String feeFrequency = request.getParameter("feeFrequency");
		String staffFlag = request.getParameter("staffFlag");
		String duration = request.getParameter("duration");
		String saleChannel = request.getParameter("saleChannel");
		Boolean staff = null;
		if(staffFlag != null && staffFlag.trim().equals("0")) {
			staff = false;
		} else if(staffFlag != null && staffFlag.trim().equals("1")) {
			staff = true;
		}
		if(request.getParameterMap().size()<=1) {
			return LIST;
		}
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("policyDate");
			page.setOrderDirection("DESC");
		}
		
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = user.getOrganization().getOrgCode();
			if(orgCode.contains("11185")) {
				orgCode = "8644";
			}
		} else {
			if(!orgCode.contains(user.getOrganization().getOrgCode())){
				orgCode = user.getOrganization().getOrgCode();
			}
		}
		
		page.setNumPerPage(Integer.MAX_VALUE);
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE_R, orgCode));
		if(status != null && status.trim().length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		String prdName = request.getParameter("prd.prdFullName");
		if(prdName != null && prdName.trim().length()>0) {
			csf.add(new SearchFilter("prodName", Operator.EQ, prdName));
		}
		if(attachedFlag != null && attachedFlag.trim().length()>0) {
			csf.add(new SearchFilter("attachedFlag", Operator.EQ, attachedFlag));
		}
		if(feeFrequency != null && feeFrequency.trim().length()>0) {
			csf.add(new SearchFilter("feeFrequency", Operator.EQ, feeFrequency));
		}
		if(staff != null) {
			csf.add(new SearchFilter("staffFlag", Operator.EQ, staff));
		}
		if(duration != null && duration.trim().length()>0 && !duration.equals("0")) {
			csf.add(new SearchFilter("duration", Operator.GTE, duration));
		}
		
		if(saleChannel != null && saleChannel.trim().length()>0) {
			csf.add(new SearchFilter("policyNo", Operator.LIKE_R, saleChannel));
		}
		
		Specification<Policy> specification = DynamicSpecifications.bySearchFilter(request, Policy.class, csf);
		List<Policy> policies = policyService.findByExample(specification, page);
		
		map.put("policies", policies);
		return TO_XLS;
	}
	
	@Log(message="下载了保单数据PD！", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("Client:provEdit")
	@RequestMapping(value="/pdtoXls", method=RequestMethod.GET)
	public String pdToXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		//默认返回未处理工单
		String orgCode = request.getParameter("policy.orgCode");
		if(orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = user.getOrganization().getOrgCode();
			if(orgCode.contains("11185")) {
				orgCode = "8644";
			}
		} else {
			if(!orgCode.contains(user.getOrganization().getOrgCode())){
				orgCode = user.getOrganization().getOrgCode();
			}
		}
		String pd1 = request.getParameter("search_GTE_policyDate");
		String pd2 = request.getParameter("search_LTE_policyDate");
		String prodName = request.getParameter("prodName");
		
		if(prodName ==null || prodName.trim().length()<=0) {
			prodName = "%%";
		}
		if(pd1 == null || pd1.trim().length()<=0) {
			pd1 = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		}
		
		List<PolicyDataModel> policies = policyService.getPolicyDate(orgCode + "%", pd1, pd2, prodName);
		
		map.put("policies", policies);
		return PD_TO_XLS;
	}
	
	@Log(message="下载了监管台账数据！", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("Client:jgList")
	@RequestMapping(value="/toJgXls", method=RequestMethod.GET)
	public String toJgXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		//默认返回未处理工单
		String orgCode = request.getParameter("policy.orgCode");
		if(orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = user.getOrganization().getOrgCode();
			if(orgCode.contains("11185")) {
				orgCode = "8644";
			}
		} else {
			if(!orgCode.contains(user.getOrganization().getOrgCode())){
				orgCode = user.getOrganization().getOrgCode();
			}
		}
		String pd1 = request.getParameter("search_GTE_policyDate");
		String pd2 = request.getParameter("search_LTE_policyDate");
		String prodName = request.getParameter("prodName");
		//String status = request.getParameter("status");
		if(prodName ==null || prodName.trim().length()<=0) {
			prodName = "%%";
		} else {
			try {
				prodName = URLDecoder.decode(prodName, "GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(pd1 == null || pd1.trim().length()<=0) {
			pd1 = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		}
		
		List<PolicyDataModel> policies = policyService.getPolicyDate(orgCode + "%", pd1, pd2, prodName);
		
		map.put("policies", policies);
		return JG_TO_XLS;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
