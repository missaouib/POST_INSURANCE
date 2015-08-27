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
import com.gdpost.web.entity.basedata.ConservationError;
import com.gdpost.web.entity.basedata.Prd;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.service.insurance.BaseDataService;
import com.gdpost.web.service.insurance.CommonService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;

@Controller
@RequestMapping("/common")
public class CommonController {
	private static final Logger LOG = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private BaseDataService basedataService;

	private static final String LOOK_CVE = "insurance/common/lookup_cve";
	
	@RequestMapping(value="/lookupPolicysuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupPolicySuggest(ServletRequest request, Map<String, Object> map) {
		Specification<Policy> specification = DynamicSpecifications.bySearchFilter(request, Policy.class);
		Page page = new Page();
		page.setNumPerPage(10);
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
