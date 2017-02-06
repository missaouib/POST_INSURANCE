/**
 * 
 * ==========================================================
 * 客服管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.entity.main.Issue;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.OrganizationService;
import com.gdpost.web.service.UserService;
import com.gdpost.web.service.insurance.KfglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.StatusDefine;
import com.gdpost.web.util.StatusDefine.STATUS;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/kfgl")
public class KfglController {
	private static final Logger LOG = LoggerFactory.getLogger(KfglController.class);
	
	@Autowired
	private KfglService kfglService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrganizationService orgService;

	private static final String VIEW = "insurance/kfgl/wtj/view";
	private static final String PRINT = "insurance/kfgl/wtj/issue";
	private static final String PRINT_LIST = "insurance/kfgl/wtj/issues";
	private static final String UPDATE = "insurance/kfgl/wtj/update";
	private static final String LIST = "insurance/kfgl/wtj/list";
	private static final String MAX_LIST = "insurance/kfgl/wtj/maxlist";
	private static final String ISSUE_LIST = "insurance/kfgl/wtj/issuelist";
	private static final String TO_HELP = "insurance/help/kfgl";
	
	private static final String TO_XLS = "insurance/kfgl/wtj/toXls";
	private static final String ISSUES_TO_XLS = "insurance/kfgl/wtj/toXlses";
	
	@RequestMapping(value="/help", method=RequestMethod.GET)
	public String toHelp() {
		return TO_HELP;
	}
	
	@RequiresPermissions("Wtgd:view")
	@RequestMapping(value="/issue/view/{id}", method=RequestMethod.GET)
	public String view(@PathVariable Long id, Map<String, Object> map) {
		Issue issue = kfglService.get(id);
		
		map.put("issue", issue);
		map.put("status", STATUS.ReopenStatus);
		return VIEW;
	}
	
	@RequiresUser
	@RequestMapping(value="/issue/print/{id}", method=RequestMethod.GET)
	public String print(@PathVariable Long id, Map<String, Object> map) {
		Issue issue = kfglService.get(id);
		
		map.put("issue", issue);
		map.put("status", STATUS.ReopenStatus);
		return PRINT;
	}
	
	@RequiresUser
	@RequestMapping(value="/issues/print", method={RequestMethod.POST, RequestMethod.GET})
	public String prints(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		
		String status = request.getParameter("status");
		LOG.debug("-------------- status: " + status);
		Issue issue = new Issue();
		if(status == null) {
			status = STATUS.CloseStatus.getDesc();
		}
		issue.setStatus(status);
		
		if(status != null) {
			request.setAttribute("encodeStatus", Base64Utils.encodeToString(status.getBytes()));
		}
		request.setAttribute("status", status);
		
		map.put("issue", issue);
		map.put("statusList", STATUS.values());
		
		String searchOrg = request.getParameter("orgCode");
		request.setAttribute("orgCode", searchOrg);
		request.setAttribute("name", request.getParameter("name"));
		LOG.debug("------------orgCode:" + searchOrg + ", name=:" + request.getParameter("name"));
		if(searchOrg != null && searchOrg.trim().length() <= 0) {
			if(userOrg.getOrgCode().length()<=4) {
				//return	AjaxObject.newError("请选择机构！").toString(); 
				return PRINT_LIST;
			} else {
				searchOrg = userOrg.getOrgCode();
			}
		} else if(searchOrg == null || searchOrg.trim().length() <= 0) {
			return PRINT_LIST;
		}
		
		page.setNumPerPage(50);
		
		Specification<Issue> specification = DynamicSpecifications.bySearchFilter(request, Issue.class,
				new SearchFilter("status", Operator.LIKE, status),
				new SearchFilter("policy.organization.orgCode", Operator.LIKE, searchOrg));
		List<Issue> issues = kfglService.findByExample(specification, page);
		LOG.debug("-----------------" + issues);
		map.put("issues", issues);
		
		return PRINT_LIST;
	}
	
	@ModelAttribute("preloadIssue")
	public Issue preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Issue issue = kfglService.get(id);
			if(issue != null) {
				issue.setOrganization(null);
			}
			return issue;
		}
		return null;
	}
	
	@RequiresPermissions("Wtgd:edit")
	@RequestMapping(value="/issue/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Issue issue = kfglService.get(id);
		
		map.put("issue", issue);
		return UPDATE;
	}
	
	@Log(message="回复了{0}问题工单的信息。", level=LogLevel.WARN, module=LogModule.KFGL)
	@RequiresPermissions("Wtgd:edit")
	@RequestMapping(value="/issue/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadIssue")Issue issue) {
		Issue src = kfglService.get(issue.getId());
		src.setDealMan(issue.getDealMan());
		src.setDealTime(issue.getDealTime());
		src.setResult(issue.getResult());
		src.setStatus(StatusDefine.STATUS.DealStatus.getDesc());
		kfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getIssueNo()}));
		return	AjaxObject.newOk("回复问题工单成功！").toString(); 
	}
	
	@Log(message="重新打开了{0}问题工单的信息。", level=LogLevel.WARN, module=LogModule.KFGL)
	@RequiresPermissions("Wtgd:edit")
	@RequestMapping(value="/issue/reopen", method=RequestMethod.POST)
	public @ResponseBody String reopen(@Valid @ModelAttribute("preloadIssue")Issue issue) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		Issue src = kfglService.get(issue.getId());
		src.setStatus(StatusDefine.STATUS.ReopenStatus.getDesc());
		src.setReopenUser(shiroUser.getUser());
		src.setReopenReason(issue.getReopenReason());
		src.setReopenDate(new Date());
		kfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getIssueNo()}));
		return	AjaxObject.newOk("重新打开问题工单成功！").toString(); 
	}
	
	@Log(message="结案了{0}问题工单的信息。", level=LogLevel.WARN, module=LogModule.KFGL)
	@RequiresPermissions("Wtgd:edit")
	@RequestMapping(value="/issue/close", method=RequestMethod.POST)
	public @ResponseBody String closeIssue(@Valid @ModelAttribute("preloadIssue")Issue issue) {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		Issue src = kfglService.get(issue.getId());
		src.setStatus(STATUS.CloseStatus.getDesc());
		kfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getIssueNo()}));
		return	AjaxObject.newOk("结案问题工单成功！").toString(); 
	}
	
	@Log(message="结案了{0}问题工单的信息。", level=LogLevel.WARN, module=LogModule.KFGL)
	@RequiresPermissions("Wtgd:edit")
	@RequestMapping(value="/issue/close/{id}", method=RequestMethod.POST)
	public @ResponseBody String closeSingleIssue(@PathVariable Long id) {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		Issue src = kfglService.get(id);
		src.setStatus(STATUS.CloseStatus.getDesc());
		kfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getIssueNo()}));
		return	AjaxObject.newOk("结案问题工单成功！").setCallbackType("").toString(); 
	}
	
	@Log(message="对{0}问题工单进行了结案关闭。", level=LogLevel.WARN, module=LogModule.KFGL)
	@RequiresPermissions("Wtgd:provEdit")
	@RequestMapping(value="/issue/CloseStatus", method=RequestMethod.POST)
	public @ResponseBody String closeMany(Long[] ids) {
		String[] policys = new String[ids.length];
		try {
			Issue issue = null;
			for (int i = 0; i < ids.length; i++) {
				issue = kfglService.get(ids[i]);
				issue.setStatus(STATUS.CloseStatus.getDesc());
				kfglService.saveOrUpdate(issue);
				
				policys[i] = issue.getIssueNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("结案关闭工单失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("成功结案关闭！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("Wtgd:view")
	@RequestMapping(value="/issue/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		boolean hasCon = true;
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
			hasCon = false;
		} else if(!orgCode.contains(user.getOrganization().getOrgCode())){
			orgCode = user.getOrganization().getOrgCode();
		}
		
		if(hasCon) {
			String orgName = request.getParameter("name");
			request.setAttribute("orgCode", orgCode);
			request.setAttribute("name", orgName);
		}
		
		//默认返回未处理工单
		String status = request.getParameter("status");
		LOG.debug("-------------- status: " + status);
		if(status != null) {
			request.setAttribute("encodeStatus", Base64Utils.encodeToString(status.getBytes()));
		}
		request.setAttribute("status", status);
		Issue issue = new Issue();
//		if(status == null) {
//			status = "待处理";
//		}
		issue.setStatus(status);
		
		if(page.getOrderField() == null) {
			page.setOrderField("readyDate");
			page.setOrderDirection("ASC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		
		//如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if (user.getOrganization().getOrgCode().length() > 4) {
			if(status == null) {
				LOG.debug("-------------- 111: " );
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.NewStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.IngStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.ReopenStatus.getDesc()));
			} else if(status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		} else {
			if(status == null) {
				LOG.debug("-------------- 333: " );
				issue.setStatus(STATUS.DealStatus.getDesc());
				request.setAttribute("status", STATUS.DealStatus.getDesc());
				request.setAttribute("encodeStatus", Base64Utils.encodeToString(STATUS.DealStatus.getDesc().getBytes()));
				csf.add(new SearchFilter("status", Operator.EQ, STATUS.DealStatus.getDesc()));
			} else if(status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		}
		Specification<Issue> specification = DynamicSpecifications.bySearchFilter(request, Issue.class, csf);
		List<Issue> issues = kfglService.findByExample(specification, page);
		
		/*
		//如果是非省分级别，加上重打开数据
		if(user.getOrganization().getOrgCode().length() > 4) {
			LOG.debug("------- 非省分级别，查找重打开数据" + issues);
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(Issue.class,
					new SearchFilter("status", Operator.LIKE, STATUS.ReopenStatus.getDesc()),
					new SearchFilter("organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
			if (userOrg.getOrgCode().length() > 6) {
				specification = DynamicSpecifications.bySearchFilterWithoutRequest(Issue.class,
						new SearchFilter("status", Operator.LIKE, STATUS.ReopenStatus.getDesc()),
						new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
			}
			List<Issue> tmpList = kfglService.findByExample(specification, page);
			LOG.debug("------------ tmpList:" + tmpList);
			if(tmpList != null && !tmpList.isEmpty()) {
				issues.addAll(tmpList);
			}
		}
		*/
		map.put("issue", issue);
		map.put("statusList", STATUS.values());
		map.put("page", page);
		map.put("issues", issues);
		return LIST;
	}
	
	@RequiresPermissions("Wtgd:view")
	@RequestMapping(value="/issue/maxlist", method={RequestMethod.GET, RequestMethod.POST})
	public String maxlist(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		boolean hasConf = true;
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
			hasConf = false;
		} else if(!orgCode.contains(user.getOrganization().getOrgCode())){
			orgCode = user.getOrganization().getOrgCode();
		}
		
		if(hasConf) {
			Organization org = orgService.getByOrgCode(orgCode);
			//String orgName = request.getParameter("name");
			request.setAttribute("orgCode", orgCode);
			request.setAttribute("name", org.getName());
		}
		//默认返回未处理工单
		String status = request.getParameter("status");
		String encodeStatus = request.getParameter("encodeStatus");
		request.setAttribute("encodeStatus", encodeStatus);
		if((status != null && status.trim().equals("null")) || (encodeStatus!= null && encodeStatus.equals("null"))) {
			status = null;
		}
		if(encodeStatus != null && encodeStatus.trim().length() > 0 ){
			status = new String(Base64Utils.decodeFromString(encodeStatus));
		} else if(status != null && status.trim().length()>0) {
			encodeStatus = Base64Utils.encodeToString(status.getBytes());
			request.setAttribute("encodeStatus", encodeStatus);
		}
		LOG.debug("---------kf max list status" + status);
		Issue issue = new Issue();
		issue.setStatus(status);
		request.setAttribute("status", status);
		
		if(page.getOrderField() == null) {
			page.setOrderField("readyDate");
			page.setOrderDirection("ASC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		
		//如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if (user.getOrganization().getOrgCode().length() > 4) {
			if(status == null) {
				LOG.debug("-------------- 111: " );
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.NewStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.IngStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.ReopenStatus.getDesc()));
			} else if(status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		} else {
			if(status == null) {
				LOG.debug("-------------- 333: " );
				issue.setStatus(STATUS.DealStatus.getDesc());
				request.setAttribute("status", STATUS.DealStatus.getDesc());
				csf.add(new SearchFilter("status", Operator.EQ, STATUS.DealStatus.getDesc()));
			} else if(status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		}
		Specification<Issue> specification = DynamicSpecifications.bySearchFilter(request, Issue.class, csf);
		List<Issue> issues = kfglService.findByExample(specification, page);
		
		map.put("issue", issue);
		map.put("statusList", STATUS.values());
		map.put("page", page);
		map.put("issues", issues);
		return MAX_LIST;
	}
	
	@RequiresPermissions("Wtgd:view")
	@RequestMapping(value="/toXls", method=RequestMethod.GET)
	public String toXls(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String status = request.getParameter("status");
		if(status == null || (status != null && status.trim().equals("null"))) {
			status = "";
		} else if(status != null && status.trim().length() > 0 ){
			status = new String(Base64Utils.decodeFromString(status));
			if(status.equals("null")) {
				status = "";
			}
		}
		
		page.setOrderField("policy.organization.orgCode");
		page.setOrderDirection("ASC");
		page.setNumPerPage(65564);
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = user.getOrganization().getOrgCode();
		} else if(!orgCode.contains(user.getOrganization().getOrgCode())){
			orgCode = user.getOrganization().getOrgCode();
		}
		/*
		Specification<Issue> specification = DynamicSpecifications.bySearchFilter(request, Issue.class,
				new SearchFilter("status", Operator.LIKE, s),
				new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
				*/
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		
//		String startDate = request.getParameter("search_GTE_shouldDate");
//		String endDate = request.getParameter("search_GTE_shouldDate");
//		Date d=new Date();   
//		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   

//		if(startDate == null || startDate.trim().length()<=0) {
//			csf.add(new SearchFilter("shouldDate", Operator.GTE, df.format(new Date(d.getTime() - (long)30 * 24 * 60 * 60 * 1000))));
//		}
//		
//		if(endDate == null || endDate.trim().length()<=0) {
//			csf.add(new SearchFilter("shouldDate", Operator.LTE, new Date()));
//		}
		
		//如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if (user.getOrganization().getOrgCode().length() > 4) {
			if(status.length() <= 0) {
				LOG.debug("-------------- 111: " );
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.NewStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.IngStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.ReopenStatus.getDesc()));
			} else {
				LOG.debug("-------------- 222: " );
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		} else {
			if(status.length() <= 0) {
				LOG.debug("-------------- 333: " );
				csf.add(new SearchFilter("status", Operator.EQ, STATUS.DealStatus.getDesc()));
			} else {
				LOG.debug("-------------- 444: " );
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		}
		Specification<Issue> specification = DynamicSpecifications.bySearchFilter(request, Issue.class, csf);
		List<Issue> reqs = kfglService.findByExample(specification, page);
	
		map.put("reqs", reqs);
		return TO_XLS;
	}

	@RequiresPermissions("Wtgd:view")
	@RequestMapping(value="/issuesToXls", method=RequestMethod.GET)
	public String issuesToXls(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String status = request.getParameter("status");
		if(status == null || (status != null && status.trim().equals("null"))) {
			status = "";
		} else if(status != null && status.trim().length() > 0 ){
			status = new String(Base64Utils.decodeFromString(status));
			if(status.equals("null")) {
				status = "";
			}
		}
		
		page.setOrderField("policy.organization.orgCode");
		page.setOrderDirection("ASC");
		page.setNumPerPage(65564);
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = user.getOrganization().getOrgCode();
		} else if(!orgCode.contains(user.getOrganization().getOrgCode())){
			orgCode = user.getOrganization().getOrgCode();
		}
		/*
		Specification<Issue> specification = DynamicSpecifications.bySearchFilter(request, Issue.class,
				new SearchFilter("status", Operator.LIKE, s),
				new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
				*/
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		
		//如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if (user.getOrganization().getOrgCode().length() > 4) {
			if(status.length() <= 0) {
				LOG.debug("-------------- 111: " );
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.NewStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.IngStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.ReopenStatus.getDesc()));
			} else {
				LOG.debug("-------------- 222: " );
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		} else {
			if(status.length() <= 0) {
				LOG.debug("-------------- 333: " );
				csf.add(new SearchFilter("status", Operator.EQ, STATUS.DealStatus.getDesc()));
			} else {
				LOG.debug("-------------- 444: " );
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		}
		Specification<Issue> specification = DynamicSpecifications.bySearchFilter(request, Issue.class, csf);
		List<Issue> reqs = kfglService.findByExample(specification, page);
	
		map.put("reqs", reqs);
		return ISSUES_TO_XLS;
	}

	@RequiresPermissions("Wtgd:view")
	@RequestMapping(value="/issuelist", method={RequestMethod.GET, RequestMethod.POST})
	public String issueList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put("issueList", kfglService.getTODOIssueList(shiroUser.getUser()));
		return ISSUE_LIST;
	}

	// 使用初始化绑定器, 将参数自动转化为日期类型,即所有日期类型的数据都能自动转化为yyyy-MM-dd格式的Date类型
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
