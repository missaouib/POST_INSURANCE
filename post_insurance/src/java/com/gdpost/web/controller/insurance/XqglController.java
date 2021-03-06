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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
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

import com.gdpost.utils.BeanValidators;
import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.entity.basedata.RenewalType;
import com.gdpost.web.entity.insurance.Policy;
import com.gdpost.web.entity.insurance.RenewedFollow;
import com.gdpost.web.entity.insurance.RenewedList;
import com.gdpost.web.entity.insurance.RenewedStay;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.OrganizationService;
import com.gdpost.web.service.insurance.PolicyService;
import com.gdpost.web.service.insurance.XqglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.StatusDefine.BQ_STATUS;
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
	private PolicyService policyService;
	
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
	
	private static final String CREATE_STAY = "insurance/xqgl/stay/create";
	private static final String UPDATE_STAY = "insurance/xqgl/stay/update";
	private static final String PROV_UPDATE_STAY = "insurance/xqgl/stay/provupdate";
	private static final String LIST_STAY = "insurance/xqgl/stay/list";
	private static final String STAY_TO_XLS = "insurance/xqgl/stay/toXls";
	
	private static final String LIST_FOLLOW = "insurance/xqgl/follow/list";
	private static final String FOLLOW_TO_XLS = "insurance/xqgl/follow/toXls";
	private static final String UPDATE_FOLLOW = "insurance/xqgl/follow/update";
	
	private static final String TO_ELSE = "insurance/xqgl/toElse";
	
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
	
	@RequiresPermissions("Renewed:prov")
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
	@RequiresPermissions("Renewed:prov")
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
	
	@Log(message="批量关闭了{0}续期催收清单。", level=LogLevel.WARN, module=LogModule.XQGL)
	@RequiresPermissions(value={"Renewed:edit"}, logical=Logical.OR)
	@RequestMapping(value="/issue/batchClose", method=RequestMethod.POST)
	public @ResponseBody String batchCloseReneweds(Long[] ids) {
		RenewedList renewed = null;
		String[] policys = new String[ids.length];
		for(int i = 0; i<ids.length; i++) {
			renewed = xqglService.get(ids[i]);
			renewed.setFeeStatus("收费成功");
			renewed.setFixStatus(XQ_STATUS.CloseStatus.getDesc());
			xqglService.saveOrUpdate(renewed);
			policys[i] = renewed.getPolicy().getPolicyNo();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return	AjaxObject.newOk("批量关闭续期催收成功！").setCallbackType("").toString();
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
		String feeMatch = request.getParameter("feeMatch");
		String staffFlag = request.getParameter("staffFlag");
		request.setAttribute("hqIssueType", hqIssueType);
		request.setAttribute("dealType", dealType);
		request.setAttribute("feeFailReason", feeFailReason);
		request.setAttribute("provActivity", provActivity);
		request.setAttribute("feeMatch", feeMatch);
		request.setAttribute("staffFlag", staffFlag);
		
		issue.setHqIssueType(hqIssueType);
		issue.setDealType(dealType);
		issue.setProvActivity(provActivity);
		issue.setFeeMatch(feeMatch);
		issue.setStaffFlag(staffFlag);
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
		
		if(page.getOrderField() == null || page.getOrderField().trim().length()<=0) {
//			page.setOrderField("policy.policyDate");
//			page.setOrderDirection("ASC");
			List<Order> orders=new ArrayList<Order>();
			orders.add(new Order(Direction.DESC, "feeDate"));
			orders.add(new Order(Direction.ASC, "policy.policyDate"));
			page.setOrders(orders);
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
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
		if (feeMatch != null && feeMatch.length() > 0) {
			csf.add(new SearchFilter("feeMatch", Operator.EQ, feeMatch));
		}
		
		if (staffFlag != null && staffFlag.length() > 0) {
			Boolean isSf = staffFlag.equals("1")?Boolean.TRUE:Boolean.FALSE;
			csf.add(new SearchFilter("policy.staffFlag", Operator.EQ, isSf));
		}
		
		Specification<RenewedList> specification = DynamicSpecifications.bySearchFilter(request, RenewedList.class, csf);
		
		List<RenewedList> issues = xqglService.findByExample(specification, page);
		
		List<String> provActivities = xqglService.getProvAcitivity();
		map.put("provActivities", provActivities);
		
		List<String> feeMatchs = xqglService.getFeeMatch();
		map.put("feeMatchs", feeMatchs);
		
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
		String feeMatch = request.getParameter("feeMatch");
		String staffFlag = request.getParameter("staffFlag");
		request.setAttribute("hqIssueType", hqIssueType);
		request.setAttribute("dealType", dealType);
		request.setAttribute("feeFailReason", feeFailReason);
		request.setAttribute("provActivity", provActivity);
		request.setAttribute("feeMatch", feeMatch);
		request.setAttribute("staffFlag", staffFlag);
		
		issue.setHqIssueType(hqIssueType);
		issue.setDealType(dealType);
		issue.setProvActivity(provActivity);
		issue.setFeeMatch(feeMatch);
		issue.setStaffFlag(staffFlag);
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
		
		if(page.getOrderField() == null || page.getOrderField().trim().length()<=0) {
			List<Order> orders=new ArrayList<Order>();
			orders.add(new Order(Direction.DESC, "feeDate"));
			orders.add(new Order(Direction.ASC, "policy.policyDate"));
			page.setOrders(orders);
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
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
		if (feeMatch != null && feeMatch.length() > 0) {
			csf.add(new SearchFilter("feeMatch", Operator.EQ, feeMatch));
		}
		
		if (staffFlag != null && staffFlag.length() > 0) {
			Boolean isSf = staffFlag.equals("1")?Boolean.TRUE:Boolean.FALSE;
			csf.add(new SearchFilter("policy.staffFlag", Operator.EQ, isSf));
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
		String feeMatch = request.getParameter("feeMatch");
		String staffFlag = request.getParameter("staffFlag");
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
		if(page.getOrderField() == null || page.getOrderField().trim().length()<=0) {
			List<Order> orders=new ArrayList<Order>();
			orders.add(new Order(Direction.DESC, "feeDate"));
			orders.add(new Order(Direction.ASC, "policy.policyDate"));
			page.setOrders(orders);
		}
		page.setNumPerPage(65564);
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
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
		if (feeMatch != null && feeMatch.length() > 0) {
			csf.add(new SearchFilter("feeMatch", Operator.EQ, feeMatch));
		}
		if (staffFlag != null && staffFlag.length() > 0) {
			Boolean isSf = staffFlag.equals("1")?Boolean.TRUE:Boolean.FALSE;
			csf.add(new SearchFilter("policy.staffFlag", Operator.EQ, isSf));
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
	
	/*
	 * ===========================================
	 * 退保挽留管理
	 * ===========================================
	 */
	
	@RequiresPermissions("RenewedStay:save")
	@RequestMapping(value="/stay/create", method=RequestMethod.GET)
	public String preCreateRenewedStay() {
		return CREATE_STAY;
	}
	
	@Log(message="添加了{0}退保挽留。", level=LogLevel.WARN, module=LogModule.XQGL)
	@RequiresPermissions("RenewedStay:save")
	@RequestMapping(value="/stay/create", method=RequestMethod.POST)
	public @ResponseBody String createRenewedStay(@Valid RenewedStay stay, HttpServletRequest request) {	
		try {
			String policyNo = request.getParameter("policyNo");
			Policy policy = policyService.getByPolicyNo(policyNo);
			stay.setPolicy(policy);
			stay.setStayNum(xqglService.getRenewedStayNum(policyNo));
			stay.setOperateTime(new Date());
			stay.setStatus(BQ_STATUS.NewStatus.name());
			stay.setUser(SecurityUtils.getShiroUser().getUser());
			xqglService.saveOrUpdateRenewedStay(stay);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加退保挽留失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{stay.getPolicy().getPolicyNo()}));
		return AjaxObject.newOk("添加退保挽留成功！").toString();
	}
	
	@RequiresPermissions("RenewedStay:edit")
	@RequestMapping(value="/stay/update/{id}", method=RequestMethod.GET)
	public String preUpdateRenewedStay(@PathVariable Long id, Map<String, Object> map) {
		RenewedStay stay = xqglService.getRenewedStay(id);
		
		map.put("stay", stay);
		return UPDATE_STAY;
	}
	
	@RequiresPermissions("RenewedStay:prov")
	@RequestMapping(value="/stay/provupdate/{id}", method=RequestMethod.GET)
	public String preProvUpdateRenewedStay(@PathVariable Long id, Map<String, Object> map) {
		RenewedStay stay = xqglService.getRenewedStay(id);
		
		map.put("stay", stay);
		return PROV_UPDATE_STAY;
	}
	
	@Log(message="修改了{0}退保挽留的信息。", level=LogLevel.WARN, module=LogModule.XQGL)
	@RequiresPermissions("RenewedStay:edit")
	@RequestMapping(value="/stay/update", method=RequestMethod.POST)
	public @ResponseBody String updateRenewedStay(RenewedStay src) {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		//LOG.debug("--------------0:" + src.toString());
		RenewedStay stay = xqglService.getRenewedStay(src.getId());
		stay.setStatus(BQ_STATUS.DealStatus.name());
		//LOG.debug("--------------1:" + stay.toString());
		BeanUtils.copyProperties(src, stay, BeanValidators.getNullPropertyNames(src));
		xqglService.saveOrUpdateRenewedStay(stay);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{stay.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("修改退保挽留成功！").toString(); 
	}
	
	@Log(message="修改了{0}退保挽留的状态。", level=LogLevel.WARN, module=LogModule.XQGL)
	@RequiresPermissions(value={"RenewedStay:reset","RenewedStay:deal"}, logical=Logical.OR)
	@RequestMapping(value="/stay/{status}/{id}", method=RequestMethod.POST)
	public @ResponseBody String updateRenewedStayStatus(@PathVariable("status") String status, @PathVariable("id") Long id) {
		RenewedStay stay = xqglService.getRenewedStay(id);
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
		xqglService.saveOrUpdateRenewedStay(stay);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{stay.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("修改退保挽留成功！").setCallbackType("").toString();
	}
	
	@Log(message="批量修改了{0}退保挽留的{1}状态。", level=LogLevel.WARN, module=LogModule.XQGL)
	@RequiresPermissions(value={"RenewedStay:reset","RenewedStay:deal"}, logical=Logical.OR)
	@RequestMapping(value="/stay/{status}", method=RequestMethod.POST)
	public @ResponseBody String batchUpdateRenewedStayStatus(@PathVariable("status") String status, Long[] ids) {
		RenewedStay stay = null;
		BQ_STATUS bs = BQ_STATUS.DealStatus;
		try {
			bs = BQ_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		String[] policys = new String[ids.length];
		for(int i = 0; i<ids.length; i++) {
			stay = xqglService.getRenewedStay(ids[i]);
			switch (bs) {
			case CloseStatus:
				break;
				default:
					break;
			}
			xqglService.saveOrUpdateRenewedStay(stay);
			policys[i] = stay.getPolicy().getPolicyNo();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys), status}));
		return	AjaxObject.newOk("批量" + status + "退保挽留成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}退保挽留。", level=LogLevel.WARN, module=LogModule.XQGL)
	@RequiresPermissions("RenewedStay:delete")
	@RequestMapping(value="/stay/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteRenewedStay(@PathVariable Long id) {
		RenewedStay stay = null;
		try {
			stay = xqglService.getRenewedStay(id);
			xqglService.deleteRenewedStay(stay.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除退保挽留失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{stay.getPolicy().getPolicyNo()}));
		return AjaxObject.newOk("删除退保挽留成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}退保挽留。", level=LogLevel.WARN, module=LogModule.XQGL)
	@RequiresPermissions("RenewedStay:delete")
	@RequestMapping(value="/stay/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyRenewedStay(Long[] ids) {
		String[] policys = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				RenewedStay stay = xqglService.getRenewedStay(ids[i]);
				xqglService.deleteRenewedStay(stay.getId());
				
				policys[i] = stay.getPolicy().getPolicyNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除退保挽留失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("删除退保挽留成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("RenewedStay:view")
	@RequestMapping(value="/stay/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listRenewedStay(ServletRequest request, Page page, Map<String, Object> map) {
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
		String status = request.getParameter("status");
		LOG.debug("-------------- status: " + status + "  orgCode:" + orgCode);
		RenewedStay stay = new RenewedStay();
		if(status == null) {
			status = "";
		} else if(status.trim().length()>0) {
			//stay.setStatus(BQ_STATUS.valueOf(status).name());
		}
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("operateTime");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
		if (status.length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		} else {
			csf.add(new SearchFilter("status", Operator.NEQ, BQ_STATUS.CloseStatus.name()));
		}
		Specification<RenewedStay> specification = DynamicSpecifications.bySearchFilter(request, RenewedStay.class, csf);
		
		List<RenewedStay> stays = xqglService.findByRenewedStayExample(specification, page);
		
		map.put("stay", stay);
		map.put("status", status);
		map.put("baStatusList", BQ_STATUS.values());
		map.put("page", page);
		map.put("stays", stays);
		return LIST_STAY;
	}
	
	@Log(message="下载了退保挽留数据", level=LogLevel.INFO, module=LogModule.XQGL)
	@RequiresPermissions("RenewedStay:view")
	@RequestMapping(value="/stay/toXls", method=RequestMethod.GET)
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
		String status = request.getParameter("status");
		LOG.debug("-------------- status: " + status);
		RenewedStay stay = new RenewedStay();
		if(status == null) {
			status = "";
		} else if(status.trim().length()>0) {
			stay.setStatus(BQ_STATUS.valueOf(status).name());
		}
		
		String orderField = request.getParameter("orderField");
		page.setNumPerPage(Integer.MAX_VALUE);
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("operateTime");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
		if (status.length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		Specification<RenewedStay> specification = DynamicSpecifications.bySearchFilter(request, RenewedStay.class, csf);
		
		List<RenewedStay> stays = xqglService.findByRenewedStayExample(specification, page);
		
		map.put("stays", stays);
		return STAY_TO_XLS;
	}
	
	@RequiresUser
	@Log(message="{0}要进入3年保费丢失率！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/toSys", method = { RequestMethod.GET, RequestMethod.POST })
	public String toElse(HttpServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		
		String url = request.getParameter("url");
		String name = request.getParameter("urlName");
		if(url != null && url.trim().length()>0) {
			url = url.replace("?1", userOrg.getOrgCode());
		}
		if(url != null && url.trim().length()>0) {
			url = url.replace("HashSymbol", "#");
		}
		if(url != null && url.trim().length()>0) {
			url = url.replace("BangSymbol", "!");
		}
		
		if("http".equals(request.getScheme())) {
			request.setAttribute("http", "http");
		}
		if("https".equals(request.getScheme())) {
			request.setAttribute("http", "https");
		}
		request.setAttribute("url", url);
		request.setAttribute("name", name);
		request.setAttribute("userOrgan", userOrg);
		request.setAttribute("loginUser", user);
		
		return TO_ELSE;
	}
	
	/**
	 * follow
	 * 
	 */
	
	@RequestMapping(value="/lookupJobSuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupJob(ServletRequest request, Map<String, Object> map) {
		String str = "[{\"job\":\"内勤人员\"}, {\"job\":\"外勤人员\"}, {\"job\":\"农夫\"}, {\"job\":\"私营个体\"}, {\"job\":\"退休\"}, {\"job\":\"其他（需注明）\"}]";
		LOG.debug("---------------- bq job suggest: " + str);
		return str;
	}
	
	@RequestMapping(value="/lookupWhyBuySuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupObj(ServletRequest request, Map<String, Object> map) {
		String str = "[{\"objectives\":\"理财\"}, {\"objectives\":\"养老\"}, {\"objectives\":\"资产传承\"}, {\"objectives\":\"其他（需注明）\"}]";
		LOG.debug("---------------- bq job suggest: " + str);
		return str;
	}
	
	@RequestMapping(value="/lookupRiskSuggest", method={RequestMethod.POST})
	public @ResponseBody String lookuprisk(ServletRequest request, Map<String, Object> map) {
		String str = "[{\"riskLevel\":\"低风险\"}, {\"riskLevel\":\"中风险\"}, {\"riskLevel\":\"高风险\"}]";
		LOG.debug("---------------- bq job suggest: " + str);
		return str;
	}
	
	@RequiresPermissions("RenewedFollow:view")
	@RequestMapping(value="/follow/view/{id}", method=RequestMethod.GET)
	public String viewFollow(@PathVariable Long id, Map<String, Object> map) {
		RenewedFollow issue = xqglService.getRenewedFollow(id);
		
		map.put("issue", issue);
		return UPDATE_FOLLOW;
	}
	
	@RequiresPermissions("RenewedFollow:edit")
	@RequestMapping(value="/follow/update/{id}", method=RequestMethod.GET)
	public String preUpdateFollow(@PathVariable Long id, Map<String, Object> map) {
		RenewedFollow issue = xqglService.getRenewedFollow(id);
		
		map.put("issue", issue);
		List<RenewalType> cdtList = xqglService.getAllRenewedDealTypeList();
		map.put("orgTypeList", cdtList);
		return UPDATE_FOLLOW;
	}
	
	@Log(message="反馈了大额保单{0}的跟进情况。", level=LogLevel.WARN, module=LogModule.XQGL)
	@RequiresPermissions(value={"RenewedFollow:edit"}, logical=Logical.OR)
	@RequestMapping(value="/follow/update", method=RequestMethod.POST)
	public @ResponseBody String batchCloseFollows(RenewedFollow target) {
		RenewedFollow src = xqglService.getRenewedFollow(target.getId());
		//Policy policy = policyService.getByPolicyNo(src.getPolicy().getPolicyNo())
		BeanUtils.copyProperties(target, src, "id", "policy");
		src.setStatus(XQ_STATUS.FeedStatus.name());
		src.setReporteDate(src.getFollowDate()==null?new Date():src.getFollowDate());
		src.setFollowDate(new Date());
		
		xqglService.saveOrUpdateRenewedFollow(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("成功反馈大额保单跟进成功！").toString(); 
	}
	
	@RequiresPermissions("RenewedFollow:view")
	@RequestMapping(value="/follow/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listFollows(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		String policyStatus = request.getParameter("status");
		String followStatus = request.getParameter("followStatus");
		if(policyStatus != null && policyStatus.trim().length() > 0) {
			request.setAttribute("encodeStatus", Base64Utils.encodeToString(policyStatus.getBytes()));
		}
		LOG.debug("-------------- status: " + policyStatus + ", user org code:" + userOrg.getOrgCode());
		RenewedFollow follow = new RenewedFollow();
		follow.setFollowStatus(followStatus);
		
		Policy policy = new Policy();
		policy.setStatus(policyStatus);
		
		String attachedFlag = request.getParameter("attachedFlag");
		String feeFrequency = request.getParameter("feeFrequency");
		String staffFlag = request.getParameter("staffFlag");
		String duration = request.getParameter("duration");
		String saleChannel = request.getParameter("saleChannel");
		String holderPhone = request.getParameter("holderPhone");
		String ctNum = request.getParameter("ctNum");
		String netFlag = request.getParameter("netFlag");
		
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
		policy.setNetFlag(netFlag);
		
		map.put("policy", policy);
		map.put("follow", follow);
		map.put("page", page);
		if(request.getParameterMap().size()<=1) {
			return LIST_FOLLOW;
		}
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("policy.policyDate");
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
		
		if(netFlag != null && netFlag.trim().length()>0) {
			csf.add(new SearchFilter("policy.bankCode.netFlag", Operator.EQ, netFlag));
			request.setAttribute("netFlag", netFlag);
		}
		
		if(ctNum != null && ctNum.trim().length()>0) {
			csf.add(new SearchFilter("policy.policyDtl.ctNum", Operator.GTE, ctNum));
			request.setAttribute("ctNum", ctNum);
		}
		
		if(policyStatus != null && policyStatus.trim().length() > 0) {
			csf.add(new SearchFilter("policy.status", Operator.EQ, policyStatus));
			try {
				request.setAttribute("jgStatus", URLEncoder.encode(policyStatus, "UTF8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		if(followStatus != null && followStatus.trim().length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, followStatus));
			request.setAttribute("followStatus", followStatus);
		}
		
		String prdName = request.getParameter("prd.prdFullName");
		if(prdName != null && prdName.trim().length()>0) {
			csf.add(new SearchFilter("policy.prodName", Operator.EQ, prdName));
			request.setAttribute("prd_name", prdName);
			try {
				request.setAttribute("prodName", URLEncoder.encode(prdName, "UTF8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(attachedFlag != null && attachedFlag.trim().length()>0) {
			csf.add(new SearchFilter("policy.attachedFlag", Operator.EQ, attachedFlag));
			request.setAttribute("attachedFlag", attachedFlag);
		}
		if(feeFrequency != null && feeFrequency.trim().length()>0) {
			csf.add(new SearchFilter("policy.feeFrequency", Operator.EQ, feeFrequency));
			request.setAttribute("feeFrequency", feeFrequency);
		}
		if(staff != null) {
			csf.add(new SearchFilter("policy.staffFlag", Operator.EQ, staff));
			request.setAttribute("staffFlag", staffFlag);
		}
		if(duration != null && duration.trim().length()>0 && !duration.equals("0")) {
			csf.add(new SearchFilter("policy.duration", Operator.GTE, duration));
			request.setAttribute("duration", duration);
		}
		
		if(saleChannel != null && saleChannel.trim().length()>0) {
			csf.add(new SearchFilter("policy.policyNo", Operator.LIKE_R, saleChannel));
			request.setAttribute("saleChannel", saleChannel);
		}
		
		if(holderPhone == null || holderPhone.trim().length()<=0) {
			csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
		} else {
			csf.add(new SearchFilter("policy.policyDtl.holderMobile", Operator.OR_EQ, holderPhone));
			csf.add(new SearchFilter("policy.policyDtl.holderPhone", Operator.OR_EQ, holderPhone));
			request.setAttribute("holderPhone", holderPhone);
		}
		
		Specification<RenewedFollow> specification = DynamicSpecifications.bySearchFilter(request, RenewedFollow.class, csf);
		List<RenewedFollow> policies = xqglService.findRenewedFollowByExample(specification, page);
		
		map.put("policies", policies);
		map.put("xqStatusList", XQ_STATUS.values());
		List<RenewalType> cdtList = xqglService.getAllRenewedDealTypeList();
		map.put("orgTypeList", cdtList);
		map.put("page", page);
		return LIST_FOLLOW;
	}
	
	@Log(message="下载了续期催收件列表。", level=LogLevel.INFO, module=LogModule.XQGL)
	@RequiresPermissions("RenewedFollow:view")
	@RequestMapping(value="/follow/toXls", method=RequestMethod.GET)
	public String followToXls(ServletRequest request, HttpServletResponse response, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		//默认返回未处理工单
		String policyStatus = request.getParameter("status");
		String followStatus = request.getParameter("followStatus");
		if(policyStatus != null && policyStatus.trim().length() > 0) {
			request.setAttribute("encodeStatus", Base64Utils.encodeToString(policyStatus.getBytes()));
		}
		
		String attachedFlag = request.getParameter("attachedFlag");
		String feeFrequency = request.getParameter("feeFrequency");
		String staffFlag = request.getParameter("staffFlag");
		String duration = request.getParameter("duration");
		String saleChannel = request.getParameter("saleChannel");
		String holderPhone = request.getParameter("holderPhone");
		String ctNum = request.getParameter("ctNum");
		String netFlag = request.getParameter("netFlag");
		
		Boolean staff = null;
		if(staffFlag != null && staffFlag.trim().equals("0")) {
			staff = false;
		} else if(staffFlag != null && staffFlag.trim().equals("1")) {
			staff = true;
		}
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("policy.policyDate");
			page.setOrderDirection("DESC");
		}
		page.setNumPerPage(65564);
		
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
		
		if(netFlag != null && netFlag.trim().length()>0) {
			csf.add(new SearchFilter("policy.bankCode.netFlag", Operator.EQ, netFlag));
		}
		
		if(ctNum != null && ctNum.trim().length()>0) {
			csf.add(new SearchFilter("policy.policyDtl.ctNum", Operator.GTE, ctNum));
		}
		
		if(policyStatus != null && policyStatus.trim().length() > 0) {
			csf.add(new SearchFilter("policy.status", Operator.EQ, policyStatus));
		}
		
		if(followStatus != null && followStatus.trim().length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, followStatus));
		}
		
		String prdName = request.getParameter("prd.prdFullName");
		if(prdName != null && prdName.trim().length()>0) {
			csf.add(new SearchFilter("policy.prodName", Operator.EQ, prdName));
		}
		if(attachedFlag != null && attachedFlag.trim().length()>0) {
			csf.add(new SearchFilter("policy.attachedFlag", Operator.EQ, attachedFlag));
		}
		if(feeFrequency != null && feeFrequency.trim().length()>0) {
			csf.add(new SearchFilter("policy.feeFrequency", Operator.EQ, feeFrequency));
		}
		if(staff != null) {
			csf.add(new SearchFilter("policy.staffFlag", Operator.EQ, staff));
		}
		if(duration != null && duration.trim().length()>0 && !duration.equals("0")) {
			csf.add(new SearchFilter("policy.duration", Operator.GTE, duration));
		}
		
		if(saleChannel != null && saleChannel.trim().length()>0) {
			csf.add(new SearchFilter("policy.policyNo", Operator.LIKE_R, saleChannel));
		}
		
		if(holderPhone == null || holderPhone.trim().length()<=0) {
			csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
		} else {
			csf.add(new SearchFilter("policy.policyDtl.holderMobile", Operator.OR_EQ, holderPhone));
			csf.add(new SearchFilter("policy.policyDtl.holderPhone", Operator.OR_EQ, holderPhone));
		}
		
		Specification<RenewedFollow> specification = DynamicSpecifications.bySearchFilter(request, RenewedFollow.class, csf);
		List<RenewedFollow> policies = xqglService.findRenewedFollowByExample(specification, page);
		
		map.put("policies", policies);
		
		/*
		String dirPath = request.getServletContext() + File.separator+"doc" +File.separator;
        String fileName="renewedFollow.xls";
		//读取excel模板 
		try {
			FileInputStream inStream = new FileInputStream(new File(dirPath+fileName));
			HSSFWorkbook wb = new HSSFWorkbook(inStream);
			//读取excel模板  
			//读取了模板内所有sheet内容 
			HSSFSheet sheet = wb.getSheetAt(0); 
			HSSFCell cell = null;
			HSSFRow hrow = null;
			//在相应的单元格进行赋值
			int countIdx = 2;
			for(RenewedFollow irf:policies){
				hrow = sheet.getRow(countIdx);
				cell = hrow.getCell(1);
			    cell.setCellValue("");
			}
			
			 response.setContentType("application/octet-stream;charset=UTF-8");
			 response.setHeader("Content-Type","application/vnd.ms-excel");
			 response.setHeader( "Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("GB2312"), "8859_1" ));
			 response.addHeader("Pargam", "no-cache"); 
			 response.addHeader("Cache-Control", "no-cache");
			 OutputStream out = response.getOutputStream();   
			 wb.write(out);
			 out.flush();   
			 out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		return FOLLOW_TO_XLS;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
