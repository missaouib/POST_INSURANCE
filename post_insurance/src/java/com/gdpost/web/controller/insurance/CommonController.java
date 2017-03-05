/**
 * 
 * ==========================================================
 * 通用controller
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.entity.basedata.ConservationError;
import com.gdpost.web.entity.basedata.Prd;
import com.gdpost.web.entity.component.CsAddr;
import com.gdpost.web.entity.main.ConservationType;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.entity.main.ProvOrgCode;
import com.gdpost.web.entity.main.Role;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.service.RoleService;
import com.gdpost.web.service.UserService;
import com.gdpost.web.service.insurance.BaseDataService;
import com.gdpost.web.service.insurance.BqglService;
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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private BqglService bqglService;
	
	private static final String LOOK_CVE = "insurance/common/lookup_cve";
	
	private static final String LOOK_USER = "insurance/common/lookup_user";
	
	private static final String LOOK_ROLE = "insurance/common/lookup_role";
	
	@RequestMapping(value="/lookupPolicysuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupPolicySuggest(ServletRequest request, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();
		Organization userOrg = user.getOrganization();
		//boolean isOffsite = false;
		Specification<Policy> specification = DynamicSpecifications.bySearchFilter(request, Policy.class, 
				new SearchFilter("organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		
		String policyNo = request.getParameter("policyNo");
		if(policyNo != null && policyNo.trim().length() <= 3) {
			return "[{}]";
		} if(policyNo != null && policyNo.trim().length() > 3) {
			if(policyNo.startsWith("86")) {
				if(policyNo.trim().length()>9) {
					specification = DynamicSpecifications.bySearchFilterWithoutRequest(Policy.class, 
							new SearchFilter("policyNo", Operator.LIKE, policyNo));
				} else {
					return "[{}]";
				}
			} else {
				String pre = ORG_TIPS.get(policyNo.trim().toLowerCase().substring(0, 2)) + "%";
				policyNo = "%" + policyNo.trim().toLowerCase().substring(2);
				specification = DynamicSpecifications.bySearchFilterWithoutRequest(Policy.class, 
						new SearchFilter("policyNo", Operator.LIKE, pre),
						new SearchFilter("policyNo", Operator.LIKE, policyNo));
				//isOffsite = true;
			}
		}
		Page page = new Page();
		page.setNumPerPage(15);
		List<Policy> org = commonService.findByPolicyExample(specification, page);
		SerializeConfig mapping = new SerializeConfig();
		HashMap<String, String> fm = new HashMap<String, String>();
		
		//if(!isOffsite) {
		fm.put("id", "id");
		//}
		
		fm.put("policyNo", "policyNo");
		fm.put("holder", "holder");
		fm.put("organName", "organName");
		fm.put("prodName", "prodName");
		fm.put("policyFee", "policyFee");
		fm.put("plicyValidDate", "policyDate");
		String dateFormat = "yyyy-MM-dd";  
	    mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
		mapping.put(Policy.class, new JavaBeanSerializer(Policy.class, fm));
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
		String str = JSON.toJSONString(org, mapping, SerializerFeature.WriteDateUseDateFormat);
		
		return str;
	}
	
	@RequestMapping(value="/lookupBQIssusSuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupBQIssusSuggest(ServletRequest request, Map<String, Object> map) {
		Specification<ConservationError> specification = DynamicSpecifications.bySearchFilter(request, ConservationError.class);
		Page page = new Page();
		page.setNumPerPage(60);
		List<ConservationError> org = basedataService.findByConservationErrorExample(specification, page);
		SerializeConfig mapping = new SerializeConfig();
		HashMap<String, String> fm = new HashMap<String, String>();
		fm.put("id", "BqIssueid");
		fm.put("errorCode", "info");
		mapping.put(ConservationError.class, new JavaBeanSerializer(ConservationError.class, fm));
		String str = JSON.toJSONString(org, mapping);
		LOG.debug("---------------- bq issue suggest: " + str);
		return str;
	}
	
	@RequestMapping(value="/lookupPolicyProvsuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupPolicyNoProvSuggest(ServletRequest request, Map<String, Object> map) {
		String policyNo = request.getParameter("policyNo");
		if(policyNo == null || policyNo.trim().length() <12 || !policyNo.trim().startsWith("86")) {
			return "[{}]";
		}
		String pre = policyNo.substring(0, 4);
		Page page = new Page();
		page.setNumPerPage(1);
		ProvOrgCode org = basedataService.getByProvOrgCodeOrgCode(pre);
		String str = "[{\"policyNo\":\"" + policyNo + "\", \"orginProv\":\"" + org.getOrgName() + "\"}]";
		LOG.debug("---------------- prov policy prov suggest: " + str);
		return str;
	}
	
	@RequestMapping(value="/lookupByCsAddrSuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupByCsAddrSuggest(ServletRequest request, Map<String, Object> map) {
		String csAddr = request.getParameter("csAddr");
		if(csAddr == null || csAddr.trim().length() < 2) {
			return "[{}]";
		}
		Page page = new Page();
		page.setNumPerPage(15);
		List<CsAddr> org = bqglService.findCsAddrByExample("%" + csAddr + "%");
		SerializeConfig mapping = new SerializeConfig();
		HashMap<String, String> fm = new HashMap<String, String>();
		fm.put("addr", "csAddr");
		fm.put("city", "mailAddr");
		mapping.put(CsAddr.class, new JavaBeanSerializer(CsAddr.class, fm));
		String str = JSON.toJSONString(org, mapping);
		LOG.debug("---------------- prov policy prov suggest: " + str);
		return str;
	}
	
	@RequestMapping(value="/lookup2BQIssuesDefine", method={RequestMethod.GET, RequestMethod.POST})
	public String lookupCve(ServletRequest request, Map<String, Object> map, Page page) {
		page.setNumPerPage(60);
		Specification<ConservationError> specification = DynamicSpecifications.bySearchFilter(request, ConservationError.class);
		List<ConservationError> org = basedataService.findByConservationErrorExample(specification, page);
		map.put("cvelist", org);
		map.put("page", page);
		return LOOK_CVE;
	}
	
	@RequestMapping(value="/lookup4User", method={RequestMethod.GET, RequestMethod.POST})
	public String lookupUser(ServletRequest request, Map<String, Object> map, Page page) {
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("status", Operator.EQ, User.STATUS_ENABLED));
		String role = request.getParameter("role");
		if(role != null && role.trim().length()>0) {
			csf.add(new SearchFilter("userRoles.role.name", Operator.EQ, role));
			request.setAttribute("role", role);
		}
		Specification<User> specification = DynamicSpecifications.bySearchFilter(request, User.class, csf);
		List<User> org = userService.findByExample(specification, page);
		map.put("userlist", org);
		map.put("page", page);
		return LOOK_USER;
	}
	
	@RequestMapping(value="/lookup4RoleUser", method={RequestMethod.GET, RequestMethod.POST})
	public String lookupRoleUser(ServletRequest request, Map<String, Object> map, Page page) {
		String role = request.getParameter("role");
		if(role != null && role.trim().length()>0) {
			request.setAttribute("role", role);
		}
		List<User> org = userService.findByRoleName(role, page);
		map.put("userlist", org);
		map.put("page", page);
		return LOOK_USER;
	}
	
	@RequestMapping(value="/lookupUserSuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupUserSuggest(ServletRequest request, Map<String, Object> map) {
		String realname = request.getParameter("realname");
		if(realname == null || realname.trim().length() <2 ) {
			return "[{}]";
		}
		Page page = new Page();
		page.setNumPerPage(5);
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("realname", Operator.LIKE, realname));
		csf.add(new SearchFilter("status", Operator.EQ, User.STATUS_ENABLED));
		Specification<User> specification = DynamicSpecifications.bySearchFilterWithoutRequest(User.class, csf);
		List<User> user = userService.findByExample(specification, page);
		SerializeConfig mapping = new SerializeConfig();
		HashMap<String, String> fm = new HashMap<String, String>();
		fm.put("id", "id");
		fm.put("realname", "checker");
		mapping.put(User.class, new JavaBeanSerializer(User.class, fm));
		String str = JSON.toJSONString(user, mapping);
		LOG.debug("---------------- common user suggest: " + str);
		return str;
	}
	
	@RequestMapping(value="/lookupClaimUserSuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupClaimUserSuggest(ServletRequest request, Map<String, Object> map) {
		String role = request.getParameter("role");
		Page page = new Page();
		page.setNumPerPage(25);
		//Specification<User> specification = DynamicSpecifications.bySearchFilterWithoutRequest(User.class, csf);
		List<User> user = userService.findByRoleName(role, page);
		SerializeConfig mapping = new SerializeConfig();
		HashMap<String, String> fm = new HashMap<String, String>();
		fm.put("id", "id");
		fm.put("realname", "realname");
		mapping.put(User.class, new JavaBeanSerializer(User.class, fm));
		String str = JSON.toJSONString(user, mapping);
		LOG.debug("---------------- common user suggest: " + str);
		return str;
	}
	
	@RequestMapping(value="/lookup4Role", method={RequestMethod.GET, RequestMethod.POST})
	public String lookupRole(ServletRequest request, Map<String, Object> map, Page page) {
		Specification<Role> specification = DynamicSpecifications.bySearchFilter(request, Role.class);
		List<Role> org = roleService.findByExample(specification, page);
		map.put("rolelist", org);
		map.put("page", page);
		return LOOK_ROLE;
	}
	
	@RequestMapping(value="/lookup2BQTypeSuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupCvType(ServletRequest request, Map<String, Object> map) {
		Specification<ConservationType> specification = DynamicSpecifications.bySearchFilter(request, ConservationType.class);
		Page page = new Page();
		page.setNumPerPage(60);
		List<ConservationType> org = basedataService.findByConservationTypeExample(specification, page);
		LOG.debug("---------------- bq cvType suggest: " + org);
		SerializeConfig mapping = new SerializeConfig();
		HashMap<String, String> fm = new HashMap<String, String>();
		fm.put("csType", "conservationCode");
		fm.put("csName", "conservationType");
		mapping.put(ConservationType.class, new JavaBeanSerializer(ConservationType.class, fm));
		String str = JSON.toJSONString(org, mapping);
		LOG.debug("---------------- bq cvType suggest: " + str);
		return str;
	}
	
	@RequestMapping(value="/lookupPrdSuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupPrdSuggest(ServletRequest request, Map<String, Object> map) {
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("prdStatus", Operator.EQ, "1"));
		Specification<Prd> specification = DynamicSpecifications.bySearchFilter(request, Prd.class, csf);
		Page page = new Page();
		page.setNumPerPage(60);
		List<Prd> org = basedataService.findByPrdExample(specification, page);
		SerializeConfig mapping = new SerializeConfig();
		HashMap<String, String> fm = new HashMap<String, String>();
		fm.put("id", "id");
		fm.put("prdCode", "prdCode");
		fm.put("prdName", "prdName");
		fm.put("prdFullName", "prdFullName");
		mapping.put(Prd.class, new JavaBeanSerializer(Prd.class, fm));
		String str = JSON.toJSONString(org, mapping);
		LOG.debug("---------------- bq issue suggest: " + str);
		return str;
	}
	
	@RequestMapping(value="/lookupCsAddrSuggest", method={RequestMethod.POST})
	public @ResponseBody String lookuCsAddrSuggest(ServletRequest request, Map<String, Object> map) {
		String policyNo = request.getParameter("policyNo");
		if(policyNo.trim().length()<6) {
			return "[{}]";
		}
		String organCode = policyNo.substring(0,6);
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organCode", Operator.EQ, organCode));
		Specification<CsAddr> specification = DynamicSpecifications.bySearchFilter(request, CsAddr.class, csf);
		Page page = new Page();
		page.setNumPerPage(1);
		List<CsAddr> css = basedataService.findByCsAddrExample(specification, page);
		if(css == null || css.size() <=0) {
			String pre = policyNo.substring(0, 4);
			ProvOrgCode org = basedataService.getByProvOrgCodeOrgCode(pre);
			String str = "[{\"policyNo\":\"" + policyNo + "\", \"orginProv\":\"" + org.getOrgName() + "\"}]";
			LOG.debug("---------------- prov policy prov suggest: " + str);
			return str;
		}
		CsAddr csAddr = css.get(0);
//		SerializeConfig mapping = new SerializeConfig();
//		HashMap<String, String> fm = new HashMap<String, String>();
//		fm.put("id", "id");
//		fm.put("prov", "prov");
//		fm.put("linker", "linker");
//		fm.put("phone", "phone");
//		fm.put("addr", "addr");
//		fm.put("postCode", "postCode");
//		mapping.put(Prd.class, new JavaBeanSerializer(CsAddr.class, fm));
//		String str = JSON.toJSONString(org, mapping);
		
		String str = "[{\"policyNo\":\"" + policyNo + "\", \"orginProv\":\"" + csAddr.getProv() + "\",\"mailAddr\":\"" + csAddr.getAddr() + "，" + csAddr.getPostCode() + "，" + csAddr.getLinker() + "，" + csAddr.getPhone()  + "\"}]";
		LOG.debug("----------------cs addr suggest: " + str);
		return str;
	}
}
