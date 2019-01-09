/**
 * 
 * ==========================================================
 * 发票管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

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
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.entity.main.PolicyReprintDtl;
import com.gdpost.web.entity.main.User;
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
		Boolean staff = null;
		if(staffFlag != null && staffFlag.trim().equals("0")) {
			staff = false;
		} else if(staffFlag != null && staffFlag.trim().equals("1")) {
			staff = true;
		}
		policy.setAttachedFlag((attachedFlag==null||attachedFlag.trim().length()<=0)?null:new Integer(attachedFlag));
		policy.setFeeFrequency(feeFrequency);
		policy.setStaffFlag(staff);
		
		map.put("policy", policy);
		map.put("page", page);
		if(request.getParameterMap().size()<=1) {
			return LIST;
		}
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("policyNo");
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
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE, orgCode));
		if(status != null && status.trim().length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		String prdName = request.getParameter("prd.prdFullName");
		if(prdName != null && prdName.trim().length()>0) {
			csf.add(new SearchFilter("prodName", Operator.EQ, prdName));
			request.setAttribute("prd_name", prdName);
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
		Specification<Policy> specification = DynamicSpecifications.bySearchFilter(request, Policy.class, csf);
		List<Policy> policies = policyService.findByExample(specification, page);
		
		map.put("policies", policies);
		return LIST;
	}
	
	@RequiresPermissions("PolicyReprintDtl:view")
	@RequestMapping(value="/listPolicyReprintDtl", method={RequestMethod.GET, RequestMethod.POST})
	public String listPolicyReprintDtl(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("printDate");
			page.setOrderDirection("DESC");
		}
		String plFlag = request.getParameter("plFlag");
		if(plFlag == null) plFlag = "";
		
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
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("orgCode", Operator.LIKE, orgCode));
		
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
	
	@RequiresPermissions("PolicyReprintDtl:view")
	@RequestMapping(value="/listPolicyReprintDtl/toXls", method={RequestMethod.GET, RequestMethod.POST})
	public String reprintDtlToXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("printDate");
			page.setOrderDirection("DESC");
		}
		
		page.setNumPerPage(Integer.MAX_VALUE);
		
		String plFlag = request.getParameter("plFlag");
		if(plFlag == null) plFlag = "";
		
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
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("orgCode", Operator.LIKE, orgCode));
		
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
		Boolean staff = null;
		if(staffFlag != null && staffFlag.trim().equals("0")) {
			staff = false;
		} else if(staffFlag != null && staffFlag.trim().equals("1")) {
			staff = true;
		}
		String encodeStatus = request.getParameter("encodeStatus");
		if(encodeStatus != null && encodeStatus.trim().equals("null")) {
			status = "";
		}
		
		if(encodeStatus != null && encodeStatus.trim().length() > 0 && !encodeStatus.trim().equals("null")) {
			status = new String(Base64Utils.decodeFromString(encodeStatus));
		}
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("policyNo");
			page.setOrderDirection("DESC");
		}
		
		page.setNumPerPage(Integer.MAX_VALUE);
		
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
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE, orgCode));
		if(status != null && status.trim().length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		String prdName = request.getParameter("prd.prdName");
		if(prdName != null && prdName.trim().length()>0) {
			csf.add(new SearchFilter("prodName", Operator.EQ, prdName));
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
		Specification<Policy> specification = DynamicSpecifications.bySearchFilter(request, Policy.class, csf);
		List<Policy> policies = policyService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("policies", policies);
		return TO_XLS;
	}
	
	@RequiresPermissions("Client:prov")
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
		if(pd1 == null || pd1.trim().length()<=0) {
			pd1 = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		}
		
		List<PolicyDataModel> policies = policyService.getPolicyDate(orgCode + "%", pd1, pd2);
		
		map.put("policies", policies);
		return PD_TO_XLS;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
