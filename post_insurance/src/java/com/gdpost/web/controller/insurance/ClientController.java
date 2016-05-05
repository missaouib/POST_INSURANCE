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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.service.insurance.PolicyService;
import com.gdpost.web.util.StatusDefine.FP_STATUS;
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
		User user = SecurityUtils.getShiroUser().getUser();
		String status = request.getParameter("status");
		LOG.debug("-----------------status:" + status);
		Policy req = new Policy();
		if(status == null) {
			req.setStatus(FP_STATUS.NewStatus.name());
			status = FP_STATUS.NewStatus.name();
			if(user.getOrganization().getOrgCode().length()>4) {
				req.setStatus(FP_STATUS.DealStatus.name());
				status = FP_STATUS.DealStatus.name();
			}
		} else if(status.trim().length()>0) {
			req.setStatus(FP_STATUS.valueOf(status).name());
		}
		LOG.debug("-----------------status2:" + status);
		request.setAttribute("status", status);
		
		page.setOrderField("reqDate");
		page.setOrderDirection("DESC");
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, user.getOrganization().getOrgCode()));
		if(status.trim().length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		
		Specification<Policy> specification = DynamicSpecifications.bySearchFilter(request, Policy.class, csf);
		List<Policy> reqs = policyService.findByExample(specification, page);

		map.put("req", req);
		map.put("fpStatusList", FP_STATUS.values());
		
		map.put("page", page);
		map.put("reqs", reqs);
		return LIST;
	}
	
	@RequiresPermissions("Client:view")
	@RequestMapping(value="/toXls", method=RequestMethod.GET)
	public String toXls(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String status = request.getParameter("status");
		if(status == null || status.trim().length()<=0) {
			status = FP_STATUS.NewStatus.name();
			if(user.getOrganization().getOrgCode().length()>4) {
				status = FP_STATUS.DealStatus.name();
			}
		}
		page.setNumPerPage(65564);
		page.setOrderField("policy.organization.orgCode");
		page.setOrderDirection("ASC");
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, user.getOrganization().getOrgCode()));
		if(status.trim().length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		
		Specification<Policy> specification = DynamicSpecifications.bySearchFilter(request, Policy.class, csf);
		List<Policy> reqs = policyService.findByExample(specification, page);
	
		map.put("reqs", reqs);
		return TO_XLS;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
