/**
 * 
 * ==========================================================
 * 通用controller
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.entity.basedata.ConservationError;
import com.gdpost.web.entity.basedata.Prd;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.service.insurance.BaseDataService;
import com.gdpost.web.service.insurance.CommonService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/common")
public class CommonController {
	private static final Logger LOG = LoggerFactory.getLogger(CommonController.class);
	
	public static final Map<String, String> ORG_TIPS = new HashMap<String, String>();
	
	static {
		ORG_TIPS.put("gz", "864401");
		ORG_TIPS.put("sg", "864402");
		ORG_TIPS.put("zh", "864404");
		ORG_TIPS.put("st", "864405");
		ORG_TIPS.put("fs", "864406");
		ORG_TIPS.put("jm", "864407");
		ORG_TIPS.put("zj", "864408");
		ORG_TIPS.put("mm", "864409");
		ORG_TIPS.put("zq", "864412");
		ORG_TIPS.put("hz", "864413");
		ORG_TIPS.put("mz", "864414");
		ORG_TIPS.put("sw", "864415");
		ORG_TIPS.put("hy", "864416");
		ORG_TIPS.put("yj", "864417");
		ORG_TIPS.put("qy", "864418");
		ORG_TIPS.put("dg", "864419");
		ORG_TIPS.put("zs", "864420");
		ORG_TIPS.put("cz", "864451");
		ORG_TIPS.put("jy", "864452");
		ORG_TIPS.put("yf", "864453");
	}
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private BaseDataService basedataService;

	private static final String LOOK_CVE = "insurance/common/lookup_cve";
	
	@RequestMapping(value="/lookupPolicysuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupPolicySuggest(ServletRequest request, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();
		Organization userOrg = user.getOrganization();
		Specification<Policy> specification = DynamicSpecifications.bySearchFilter(request, Policy.class, 
				new SearchFilter("organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		
		String policyNo = request.getParameter("policyNo");
		if(policyNo != null && policyNo.trim().length() <= 3) {
			return "{}";
		} if(policyNo != null && policyNo.trim().length() > 3) {
			String pre = ORG_TIPS.get(policyNo.trim().toLowerCase().substring(0, 2)) + "%";
			policyNo = "%" + policyNo.trim().toLowerCase().substring(2);
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(Policy.class, 
					new SearchFilter("policyNo", Operator.LIKE, pre),
					new SearchFilter("policyNo", Operator.LIKE, policyNo));
		}
		Page page = new Page();
		page.setNumPerPage(15);
		List<Policy> org = commonService.findByPolicyExample(specification, page);
		SerializeConfig mapping = new SerializeConfig();
		HashMap<String, String> fm = new HashMap<String, String>();
		fm.put("id", "id");
		fm.put("policyNo", "policyNo");
		fm.put("holder", "holder");
		fm.put("organName", "organName");
		mapping.put(Policy.class, new JavaBeanSerializer(Policy.class, fm));
		String str = JSON.toJSONString(org, mapping);
		return str;
	}
	
	@RequestMapping(value="/lookupBQIssusSuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupBQIssusSuggest(ServletRequest request, Map<String, Object> map) {
		Specification<ConservationError> specification = DynamicSpecifications.bySearchFilter(request, ConservationError.class);
		Page page = new Page();
		page.setNumPerPage(60);
		List<ConservationError> org = commonService.findByConservationErrorExample(specification, page);
		SerializeConfig mapping = new SerializeConfig();
		HashMap<String, String> fm = new HashMap<String, String>();
		fm.put("id", "id");
		fm.put("errorCode", "csRst");
		mapping.put(ConservationError.class, new JavaBeanSerializer(ConservationError.class, fm));
		String str = JSON.toJSONString(org, mapping);
		LOG.debug("---------------- bq issue suggest: " + str);
		return str;
	}
	
	@RequestMapping(value="/lookup2BQIssuesDefine", method={RequestMethod.GET})
	public String lookupCve(ServletRequest request, Map<String, Object> map, Page page) {
		page.setNumPerPage(60);
		Specification<ConservationError> specification = DynamicSpecifications.bySearchFilter(request, ConservationError.class);
		List<ConservationError> org = basedataService.findByConservationErrorExample(specification, page);
		map.put("cvelist", org);
		map.put("page", page);
		return LOOK_CVE;
	}
	
	@RequestMapping(value="/lookupPrdSuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupPrdSuggest(ServletRequest request, Map<String, Object> map) {
		Specification<Prd> specification = DynamicSpecifications.bySearchFilter(request, Prd.class);
		Page page = new Page();
		page.setNumPerPage(60);
		List<Prd> org = commonService.findByPrdExample(specification, page);
		SerializeConfig mapping = new SerializeConfig();
		HashMap<String, String> fm = new HashMap<String, String>();
		fm.put("id", "id");
		fm.put("prdName", "prdName");
		mapping.put(Prd.class, new JavaBeanSerializer(Prd.class, fm));
		String str = JSON.toJSONString(org, mapping);
		LOG.debug("---------------- bq issue suggest: " + str);
		return str;
	}
}
