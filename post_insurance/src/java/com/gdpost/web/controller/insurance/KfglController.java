/**
 * 
 * ==========================================================
 * 客服管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
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

	private static final String VIEW = "insurance/kfgl/wtj/view";
	private static final String PRINT = "insurance/kfgl/wtj/issue";
	private static final String UPDATE = "insurance/kfgl/wtj/update";
	private static final String LIST = "insurance/kfgl/wtj/list";
	private static final String ISSUE_LIST = "insurance/kfgl/wtj/issuelist";
	
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
	
	@Log(message="回复了{0}问题工单的信息。")
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
	
	@Log(message="重新打开了{0}问题工单的信息。")
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
	
	@Log(message="结案了{0}问题工单的信息。")
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
	
	@RequiresPermissions("Wtgd:view")
	@RequestMapping(value="/issue/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		String status = request.getParameter("status");
		LOG.debug("-------------- status: " + status);
		Issue issue = new Issue();
		if(status == null) {
			status = "待处理";
		}
		issue.setStatus(status);
		
		Specification<Issue> specification = DynamicSpecifications.bySearchFilter(request, Issue.class,
				new SearchFilter("status", Operator.LIKE, status),
				new SearchFilter("organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		
		//如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if (user.getOrganization().getOrgCode().length() > 6) {
			specification = DynamicSpecifications.bySearchFilter(request, Issue.class,
					new SearchFilter("status", Operator.LIKE, status),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		}
		
		List<Issue> issues = kfglService.findByExample(specification, page);
		
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
		
		map.put("issue", issue);
		map.put("statusList", STATUS.values());
		map.put("page", page);
		map.put("issues", issues);
		return LIST;
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
