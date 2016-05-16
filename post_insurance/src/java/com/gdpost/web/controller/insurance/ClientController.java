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
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.Policy;
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
	
	@RequiresPermissions("Client:view")
	@RequestMapping(value="/view/{id}", method=RequestMethod.GET)
	public String view(@PathVariable Long id, Map<String, Object> map) {
		Policy req = policyService.get(id);
		
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
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("policyNo");
			page.setOrderDirection("DESC");
		}
		
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
			String orgName = request.getParameter("policy.name");
			request.setAttribute("policy_orgCode", orgCode);
			request.setAttribute("policy_name", orgName);
		}
		
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
		
		Specification<Policy> specification = DynamicSpecifications.bySearchFilter(request, Policy.class, csf);
		List<Policy> policies = policyService.findByExample(specification, page);
		
		map.put("policy", policy);
		map.put("page", page);
		map.put("policies", policies);
		return LIST;
	}
	
	@RequiresPermissions("Client:view")
	@RequestMapping(value="/toXls", method=RequestMethod.GET)
	public String toXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		//默认返回未处理工单
		String status = request.getParameter("status");
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
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE, orgCode));
		if(status != null && status.trim().length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		String prdName = request.getParameter("prd.prdFullName");
		if(prdName != null && prdName.trim().length()>0) {
			csf.add(new SearchFilter("prodName", Operator.EQ, prdName));
		}
		
		Specification<Policy> specification = DynamicSpecifications.bySearchFilter(request, Policy.class, csf);
		List<Policy> policies = policyService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("policies", policies);
		return TO_XLS;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}