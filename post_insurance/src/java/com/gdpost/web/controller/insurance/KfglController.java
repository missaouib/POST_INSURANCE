/**
 * 
 * ==========================================================
 * 客服管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gdpost.utils.FileUtils;
import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.StringUtil;
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.entity.main.Inquire;
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
	
	String strError = "{\"jsonrpc\":\"2.0\",\"result\":\"error\",\"id\":\"id\",\"message\":\"操作失败。\"}";

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
	private static final String TO_DOWN = "insurance/kfgl/wtj/download";

	private static final String ASK_VIEW = "insurance/kfgl/ask/view";
	private static final String ASK_PRINT = "insurance/kfgl/ask/issue";
	private static final String ASK_PRINT_LIST = "insurance/kfgl/ask/issues";
	private static final String ASK_UPDATE = "insurance/kfgl/ask/update";
	private static final String ASK_LIST = "insurance/kfgl/ask/list";

	private static final String ASK_TO_XLS = "insurance/kfgl/ask/toXls";
	private static final String ASK_ISSUES_TO_XLS = "insurance/kfgl/ask/toXlses";
	private static final String ASK_TO_DOWN = "insurance/kfgl/ask/download";

	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String toHelp() {
		return TO_HELP;
	}

	@RequiresPermissions("Wtgd:view")
	@RequestMapping(value = "/issue/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable Long id, Map<String, Object> map) {
		Issue issue = kfglService.get(id);

		map.put("issue", issue);
		map.put("status", STATUS.NewStatus);
		return VIEW;
	}

	@RequiresUser
	@RequestMapping(value = "/issue/print/{id}", method = RequestMethod.GET)
	public String print(@PathVariable Long id, Map<String, Object> map) {
		Issue issue = kfglService.get(id);

		map.put("issue", issue);
		map.put("status", STATUS.NewStatus);
		return PRINT;
	}

	@RequiresUser
	@RequestMapping(value = "/issues/print", method = { RequestMethod.POST, RequestMethod.GET })
	public String prints(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();

		String status = request.getParameter("status");
		LOG.debug("-------------- status: " + status);
		Issue issue = new Issue();
		if (status == null) {
			status = STATUS.CloseStatus.getDesc();
		}
		issue.setStatus(status);

		org.apache.catalina.util.URLEncoder urlEncoder = new org.apache.catalina.util.URLEncoder();
		if (status == null) {
			status = "";
		}
		// request.setAttribute("encodeStatus",
		// Base64Utils.encodeToString(status.getBytes()));
		request.setAttribute("status", urlEncoder.encode(status, Charset.defaultCharset()));
		// request.setAttribute("status", status);

		map.put("issue", issue);
		map.put("statusList", STATUS.values());

		String searchOrg = request.getParameter("orgCode");
		request.setAttribute("orgCode", searchOrg);
		request.setAttribute("name", request.getParameter("name"));
		LOG.debug("------------orgCode:" + searchOrg + ", name=:" + request.getParameter("name"));
		if (searchOrg != null && searchOrg.trim().length() <= 0) {
			if (userOrg.getOrgCode().length() <= 4) {
				// return AjaxObject.newError("请选择机构！").toString();
				return PRINT_LIST;
			} else {
				searchOrg = userOrg.getOrgCode();
			}
		} else if (searchOrg == null || searchOrg.trim().length() <= 0) {
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

	@RequestMapping(value = "/issue/")
	@ModelAttribute("preloadIssue")
	public Issue preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Issue issue = kfglService.get(id);
			if (issue != null) {
				issue.setOrganization(null);
			}
			return issue;
		}
		return null;
	}

	@RequiresPermissions("Wtgd:edit")
	@RequestMapping(value = "/issue/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Issue issue = kfglService.get(id);

		map.put("issue", issue);
		return UPDATE;
	}

	@Log(message = "回复了{0}问题工单的信息。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Wtgd:edit")
	@RequestMapping(value = "/issue/update", method = RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadIssue") Issue issue) {
		Issue src = kfglService.get(issue.getId());
		src.setDealMan(issue.getDealMan());
		src.setDealTime(issue.getDealTime());
		src.setResult(issue.getResult());
		src.setCityReviewRst(issue.getCityReviewRst());
		src.setCityReviewer(issue.getCityReviewer());
		src.setStatus(StatusDefine.STATUS.IngStatus.getDesc());
		kfglService.saveOrUpdate(src);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { issue.getIssueNo() }));
		return AjaxObject.newOk("回复问题工单成功！").toString();
	}

	@Log(message = "重新打开了{0}问题工单的信息。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Wtgd:edit")
	@RequestMapping(value = "/issue/reopen", method = RequestMethod.POST)
	public @ResponseBody String reopen(@Valid @ModelAttribute("preloadIssue") Issue issue) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		Issue src = kfglService.get(issue.getId());
		if (!src.getStatus().equals(STATUS.IngStatus.getDesc())) {
			return AjaxObject.newError("工单状态不足以操作审核：请核查工单状态。").setCallbackType("").toString();
		}
		src.setStatus(StatusDefine.STATUS.NewStatus.getDesc());
		src.setReopenUser(shiroUser.getUser());
		src.setReopenReason(issue.getReopenReason());
		src.setReopenDate(new Date());
		kfglService.saveOrUpdate(src);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { issue.getIssueNo() }));
		return AjaxObject.newOk("重新打开问题工单成功！").toString();
	}

	@Log(message = "审核了{0}问题工单的信息。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Wtgd:edit")
	@RequestMapping(value = "/issue/deal", method = RequestMethod.POST)
	public @ResponseBody String dealIssue(@Valid @ModelAttribute("preloadIssue") Issue issue) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		Issue src = kfglService.get(issue.getId());
		if (!src.getStatus().equals(STATUS.IngStatus.getDesc())) {
			return AjaxObject.newError("工单状态不足以操作审核：请核查工单状态。").setCallbackType("").toString();
		}
		src.setStatus(STATUS.DealStatus.getDesc());
		src.setChecker(shiroUser.getUser().getUsername() + "_" + shiroUser.getUser().getRealname());
		src.setCheckDate(new Date());
		kfglService.saveOrUpdate(src);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { issue.getIssueNo() }));
		return AjaxObject.newOk("审核问题工单成功！").toString();
	}

	@Log(message = "结案了{0}问题工单的信息。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Wtgd:edit")
	@RequestMapping(value = "/issue/close", method = RequestMethod.POST)
	public @ResponseBody String closeIssue(@Valid @ModelAttribute("preloadIssue") Issue issue) {
		// ShiroUser shiroUser = SecurityUtils.getShiroUser();
		Issue src = kfglService.get(issue.getId());
		if (src.getStatus() != STATUS.DealStatus.getDesc()) {
			return AjaxObject.newError("结案关闭工单失败：未完成审核").setCallbackType("").toString();
		}
		src.setStatus(STATUS.CloseStatus.getDesc());
		kfglService.saveOrUpdate(src);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { issue.getIssueNo() }));
		return AjaxObject.newOk("结案问题工单成功！").toString();
	}

	@Log(message = "结案了{0}问题工单的信息。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Wtgd:edit")
	@RequestMapping(value = "/issue/close/{id}", method = RequestMethod.POST)
	public @ResponseBody String closeSingleIssue(@PathVariable Long id) {
		// ShiroUser shiroUser = SecurityUtils.getShiroUser();
		Issue src = kfglService.get(id);
		if (src.getStatus() != STATUS.DealStatus.getDesc()) {
			return AjaxObject.newError("结案关闭工单失败：未完成审核").setCallbackType("").toString();
		}
		src.setStatus(STATUS.CloseStatus.getDesc());
		kfglService.saveOrUpdate(src);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { src.getIssueNo() }));
		return AjaxObject.newOk("结案问题工单成功！").setCallbackType("").toString();
	}

	@Log(message = "对{0}问题工单进行了结案关闭。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Wtgd:provEdit")
	@RequestMapping(value = "/issue/CloseStatus", method = RequestMethod.POST)
	public @ResponseBody String closeMany(Long[] ids) {
		String[] policys = new String[ids.length];
		try {
			Issue issue = null;
			for (int i = 0; i < ids.length; i++) {
				issue = kfglService.get(ids[i]);
				if (issue.getStatus() != STATUS.DealStatus.getDesc()) {
					return AjaxObject.newError("部分结案关闭工单失败：未完成审核").setCallbackType("").toString();
				}
				issue.setStatus(STATUS.CloseStatus.getDesc());
				kfglService.saveOrUpdate(issue);

				policys[i] = issue.getIssueNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("结案关闭工单失败：" + e.getMessage()).setCallbackType("").toString();
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(policys) }));
		return AjaxObject.newOk("成功结案关闭！").setCallbackType("").toString();
	}

	@Log(message = "对{0}问题工单进行了批量审核通过。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Wtgd:provEdit")
	@RequestMapping(value = "/issue/batchDeal", method = RequestMethod.POST)
	public @ResponseBody String batchDeal(Long[] ids) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		String[] policys = new String[ids.length];
		try {
			Issue issue = null;
			for (int i = 0; i < ids.length; i++) {
				issue = kfglService.get(ids[i]);
				issue.setStatus(STATUS.DealStatus.getDesc());
				issue.setChecker(shiroUser.getUser().getUsername() + "_" + shiroUser.getUser().getRealname());
				issue.setCheckDate(new Date());
				kfglService.saveOrUpdate(issue);

				policys[i] = issue.getIssueNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("批量审核工单失败：" + e.getMessage()).setCallbackType("").toString();
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(policys) }));
		return AjaxObject.newOk("成功批量审核通过！").setCallbackType("").toString();
	}

	@RequiresPermissions("Wtgd:view")
	@RequestMapping(value = "/issue/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		boolean hasCon = true;
		if (orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = userOrg.getOrgCode();
			hasCon = false;
		} else if (!orgCode.contains(user.getOrganization().getOrgCode())) {
			orgCode = user.getOrganization().getOrgCode();
		}

		if (hasCon) {
			String orgName = request.getParameter("name");
			request.setAttribute("orgCode", orgCode);
			request.setAttribute("name", orgName);
		}

		// 默认返回未处理工单
		String status = request.getParameter("status");
		String encodeStatus = "";
		org.apache.catalina.util.URLEncoder urlEncoder = new org.apache.catalina.util.URLEncoder();
		if (status != null) {
			encodeStatus = urlEncoder.encode(status, Charset.defaultCharset());
		}
		// request.setAttribute("encodeStatus",
		// Base64Utils.encodeToString(status.getBytes()));
		String issueType = request.getParameter("issueType");
		request.setAttribute("issueType", issueType);
		request.setAttribute("eStatus", encodeStatus);
		String kfstatus_flag = request.getParameter("kfstatus_flag");
		LOG.debug("-------------- status: " + status + ", user org code:" + userOrg.getOrgCode());
		LOG.debug("==----==:" + kfstatus_flag == null ? "is null" : "equals 'null'");
		boolean isNull = false;
		if (status == null || (status != null && status.trim().length() <= 0 && kfstatus_flag != null
				&& kfstatus_flag.equals("null"))) {
			isNull = true;
			request.setAttribute("kfstatus_flag", "null");
		} else {
			request.setAttribute("kfstatus_flag", status);
		}

		request.setAttribute("status", status);

		Issue issue = new Issue();
		issue.setStatus(status);
		issue.setIssueType(issueType);

		if (page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("readyDate");
			page.setOrderDirection("ASC");
		}

		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));

		if (issueType != null && issueType.trim().length() > 0) {
			csf.add(new SearchFilter("issueType", Operator.EQ, issueType));
		}

		// 如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if (user.getOrganization().getOrgCode().length() > 4) {
			if (isNull) {
				LOG.debug("-------------- 111: ");
				csf.add(new SearchFilter("status", Operator.EQ, STATUS.NewStatus.getDesc()));
			} else if (status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		} else {
			if (isNull) {
				LOG.debug("-------------- 333: ");
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.NewStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.IngStatus.getDesc()));
			} else if (status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		}
		Specification<Issue> specification = DynamicSpecifications.bySearchFilter(request, Issue.class, csf);
		List<Issue> issues = kfglService.findByExample(specification, page);

		/*
		 * //如果是非省分级别，加上重打开数据 if(user.getOrganization().getOrgCode().length() >
		 * 4) { LOG.debug("------- 非省分级别，查找重打开数据" + issues); specification =
		 * DynamicSpecifications.bySearchFilterWithoutRequest(Issue.class, new
		 * SearchFilter("status", Operator.LIKE, STATUS.ReopenStatus.getDesc()),
		 * new SearchFilter("organization.orgCode", Operator.LIKE,
		 * userOrg.getOrgCode())); if (userOrg.getOrgCode().length() > 6) {
		 * specification =
		 * DynamicSpecifications.bySearchFilterWithoutRequest(Issue.class, new
		 * SearchFilter("status", Operator.LIKE, STATUS.ReopenStatus.getDesc()),
		 * new SearchFilter("policy.organization.orgCode", Operator.LIKE,
		 * userOrg.getOrgCode())); } List<Issue> tmpList =
		 * kfglService.findByExample(specification, page);
		 * LOG.debug("------------ tmpList:" + tmpList); if(tmpList != null &&
		 * !tmpList.isEmpty()) { issues.addAll(tmpList); } }
		 */
		map.put("issue", issue);
		map.put("statusList", STATUS.values());

		List<String> issueTypes = kfglService.getIssueTypeList();
		map.put("issueTypes", issueTypes);

		map.put("page", page);
		map.put("issues", issues);
		return LIST;
	}

	@RequiresPermissions("Wtgd:view")
	@RequestMapping(value = "/issue/maxlist", method = { RequestMethod.GET, RequestMethod.POST })
	public String maxlist(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		boolean hasConf = true;
		if (orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = userOrg.getOrgCode();
			hasConf = false;
		} else if (!orgCode.contains(user.getOrganization().getOrgCode())) {
			orgCode = user.getOrganization().getOrgCode();
		}

		if (hasConf) {
			Organization org = orgService.getByOrgCode(orgCode);
			// String orgName = request.getParameter("name");
			request.setAttribute("orgCode", orgCode);
			request.setAttribute("name", org.getName());
		}
		// 默认返回未处理工单
		String status = request.getParameter("status");
		org.apache.catalina.util.URLEncoder urlEncoder = new org.apache.catalina.util.URLEncoder();
		String encodeStatus = "";
		if (status != null) {
			encodeStatus = urlEncoder.encode(status, Charset.defaultCharset());
		}
		// request.setAttribute("encodeStatus",
		// Base64Utils.encodeToString(status.getBytes()));
		request.setAttribute("status", status);
		request.setAttribute("eStatus", encodeStatus);
		LOG.debug("-------------- status: " + status + ", user org code:" + userOrg.getOrgCode());
		Issue issue = new Issue();
		issue.setStatus(status);

		if (page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("readyDate");
			page.setOrderDirection("ASC");
		}

		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));

		// 如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if (user.getOrganization().getOrgCode().length() > 4) {
			if (status == null) {
				LOG.debug("-------------- 111: ");
				csf.add(new SearchFilter("status", Operator.EQ, STATUS.NewStatus.getDesc()));
			} else if (status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		} else {
			if (status == null) {
				LOG.debug("-------------- 333: ");
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.NewStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.IngStatus.getDesc()));
			} else if (status.trim().length() > 0) {
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
	@RequestMapping(value = "/toXls", method = RequestMethod.GET)
	public String toXls(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String status = request.getParameter("status");

		String kfstatus_flag = request.getParameter("kfstatus_flag");
		boolean isNull = false;
		if (status == null || (status != null && status.trim().length() <= 0 && kfstatus_flag != null
				&& kfstatus_flag.equals("null"))) {
			isNull = true;
		}

		page.setOrderField("policy.organization.orgCode");
		page.setOrderDirection("ASC");
		page.setNumPerPage(65564);
		String orgCode = request.getParameter("orgCode");
		String issueType = request.getParameter("issueType");

		if (orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = user.getOrganization().getOrgCode();
		} else if (!orgCode.contains(user.getOrganization().getOrgCode())) {
			orgCode = user.getOrganization().getOrgCode();
		}

		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));

		if (issueType != null && issueType.trim().length() > 0) {
			csf.add(new SearchFilter("issueType", Operator.EQ, issueType));
		}

		/*
		 * if(isNull) { LOG.debug("-------------- 111: " ); csf.add(new
		 * SearchFilter("status", Operator.OR_LIKE, status)); } else {
		 * LOG.debug("-------------- 222: " ); csf.add(new
		 * SearchFilter("status", Operator.LIKE, status)); }
		 */
		if (user.getOrganization().getOrgCode().length() > 4) {
			if (isNull) {
				LOG.debug("-------------- 111: ");
				csf.add(new SearchFilter("status", Operator.EQ, STATUS.NewStatus.getDesc()));
			} else if (status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		} else {
			if (isNull) {
				LOG.debug("-------------- 333: ");
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.NewStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.IngStatus.getDesc()));
			} else if (status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		}
		Specification<Issue> specification = DynamicSpecifications.bySearchFilter(request, Issue.class, csf);
		List<Issue> reqs = kfglService.findByExample(specification, page);

		map.put("reqs", reqs);
		return TO_XLS;
	}

	@RequiresPermissions("Wtgd:view")
	@RequestMapping(value = "/issuesToXls", method = RequestMethod.GET)
	public String issuesToXls(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String status = request.getParameter("status");
		if (status == null || (status != null && status.trim().equals("null"))) {
			status = "";
		}

		page.setOrderField("policy.organization.orgCode");
		page.setOrderDirection("ASC");
		page.setNumPerPage(65564);
		String orgCode = request.getParameter("orgCode");
		if (orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = user.getOrganization().getOrgCode();
		} else if (!orgCode.contains(user.getOrganization().getOrgCode())) {
			orgCode = user.getOrganization().getOrgCode();
		}
		/*
		 * Specification<Issue> specification =
		 * DynamicSpecifications.bySearchFilter(request, Issue.class, new
		 * SearchFilter("status", Operator.LIKE, s), new
		 * SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		 */
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));

		// 如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if (user.getOrganization().getOrgCode().length() > 4) {
			if (status.length() <= 0) {
				LOG.debug("-------------- 111: ");
				csf.add(new SearchFilter("status", Operator.EQ, STATUS.NewStatus.getDesc()));
			} else {
				LOG.debug("-------------- 222: ");
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		} else {
			if (status.length() <= 0) {
				LOG.debug("-------------- 333: ");
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.IngStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, STATUS.NewStatus.getDesc()));
			} else {
				LOG.debug("-------------- 444: ");
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		}
		Specification<Issue> specification = DynamicSpecifications.bySearchFilter(request, Issue.class, csf);
		List<Issue> reqs = kfglService.findByExample(specification, page);

		map.put("reqs", reqs);
		return ISSUES_TO_XLS;
	}

	@RequiresPermissions("Wtgd:view")
	@RequestMapping(value = "/issuelist", method = { RequestMethod.GET, RequestMethod.POST })
	public String issueList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put("issueList", kfglService.getTODOIssueList(shiroUser.getUser()));
		return ISSUE_LIST;
	}

	@RequiresUser
	@RequiresPermissions("Wtgd:view")
	@RequestMapping(value = "/issue/toWord", method = { RequestMethod.POST, RequestMethod.GET })
	public String toWord(ServletRequest request, String ids) {
		// 如果是市局登录
		// ShiroUser shiroUser = SecurityUtils.getShiroUser();

		Issue issue = null;
		String policyNo = null;
		String newPath = null;
		InputStream is = null;
		OutputStream os = null;
		HWPFDocument doc = null;
		String issueReq = null;

		String templatePath = request.getServletContext().getRealPath("/doc/issue/issue_template.doc");
		String docPath = request.getServletContext().getRealPath("/doc/issue/");
		String issuePath = docPath + StringUtil.date2Str(new Date(), "yyyyMMddHHmmss");
		File dfile = new File(issuePath);
		if (!dfile.exists()) {
			dfile.mkdirs();
		}
		String[] strIds = ids.split(",");
		String operater = null;
		for (String id : strIds) {
			issue = kfglService.get(new Long(id));
			policyNo = issue.getPolicy().getPolicyNo();
			operater = userService.get(issue.getOperateId()).getRealname();
			newPath = issuePath + File.separator + policyNo + "_" + issue.getIssueNo() + ".doc";
			try {
				is = new FileInputStream(templatePath);
				doc = new HWPFDocument(is);
				Range range = doc.getRange();
				if (issue.getIssueType().contains("退保") || issue.getIssueType().contains("条款解释不清")
						|| issue.getIssueType().contains("其他")) {
					issueReq = "转业务岗处理";
				} else if (issue.getIssueType().contains("保单未送达") || issue.getIssueType().contains("抄写")
						|| issue.getIssueType().contains("告知") || issue.getIssueType().contains("接听")
						|| issue.getIssueType().contains("挂断")) {
					issueReq = "转契约岗处理";
				} else {
					issueReq = "";
				}
				range.replaceText("${issueType}", issue.getIssueType());
				range.replaceText("${issueNo}", issue.getIssueNo());
				range.replaceText("${orgName}",
						issue.getIssueOrg() == null ? issue.getPolicy().getOrganName() : issue.getIssueOrg());
				range.replaceText("${policyNo}", policyNo);
				range.replaceText("${bankName}", issue.getBankName());
				range.replaceText("${policyDate}",
						StringUtil.date2Str(issue.getPolicy().getPolicyDate(), "yyyy-MM-dd"));
				range.replaceText("${policyFee}", new Double(issue.getPolicy().getPolicyFee()).toString());
				range.replaceText("${holder}", issue.getPolicy().getHolder());
				range.replaceText("${holderPhone}", issue.getHolderPhone());
				range.replaceText("${holderMobile}", issue.getHolderMobile());
				range.replaceText("${finishDate}", StringUtil.date2Str(issue.getFinishDate(), "yyyy-MM-dd"));
				range.replaceText("${issueContent}", issue.getIssueContent());
				range.replaceText("${issueReq}", issueReq);
				range.replaceText("${userName}", operater);
				range.replaceText("${shouldDate}", StringUtil.date2Str(issue.getShouldDate(), "yyyy-MM-dd"));
				range.replaceText("${checker}", issue.getChecker() == null ? "" : issue.getChecker());
				range.replaceText("${checkDate}",
						issue.getCheckDate() == null ? "" : StringUtil.date2Str(issue.getCheckDate(), "yyyy-MM-dd"));
				range.replaceText("${issueResult}", issue.getResult());
				range.replaceText("${dealMan}", issue.getDealMan());
				range.replaceText("${dealTime}", StringUtil.date2Str(issue.getDealTime(), "yyyy-MM-dd"));
				range.replaceText("${cityReviewRst}", issue.getCityReviewRst() == null ? "" : issue.getCityReviewRst());
				range.replaceText("${cityReviewer}", issue.getCityReviewer() == null ? "" : issue.getCityReviewer());
				range.replaceText("${reviewTime}", StringUtil.date2Str(issue.getDealTime(), "yyyy-MM-dd"));
				os = new FileOutputStream(newPath);
				// 把doc输出到输出流中
				doc.write(os);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				this.closeSource(is, os, doc);
			}
		}

		FileUtils fileUtil = new FileUtils();
		String filename = StringUtil.date2Str(new Date(), "yyyyMMddHHmmss");
		File zipFileName = new File(docPath + filename + ".zip");

		try {
			zipFileName.deleteOnExit();
			fileUtil.zip(dfile, zipFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("filename", filename);
		return TO_DOWN;
	}

	@RequiresUser
	@RequiresPermissions("Wtgd:view")
	@RequestMapping(value = "/issue/down/{zipfilename}", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity<byte[]> down(ServletRequest request, @PathVariable String zipfilename) {
		String docPath = request.getServletContext().getRealPath("/doc/issue/");
		String zipFileName = docPath + zipfilename + ".zip";
		try {
			File file = new File(zipFileName);
			if (!file.exists()) {
				LOG.error(" =----- 文件不存在。");
				return null;
			}
			HttpHeaders headers = new HttpHeaders();
			// 下载显示的文件名，解决中文名称乱码问题
			String downloadFielName = new String(zipfilename.getBytes("UTF-8"), "iso-8859-1");
			// 通知浏览器以attachment（下载方式）打开图片
			headers.setContentDispositionFormData("attachment", downloadFielName + ".zip");
			// application/octet-stream ： 二进制流数据（最常见的文件下载）。
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(org.apache.commons.io.FileUtils.readFileToByteArray(file), headers,
					HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * =================================================== 客服咨询件
	 * ===================================================
	 */

	@RequiresPermissions("Inquire:view")
	@RequestMapping(value = "/inquire/view/{id}", method = RequestMethod.GET)
	public String viewAsk(@PathVariable Long id, Map<String, Object> map) {
		Inquire inquire = kfglService.getInquire(id);
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put("checker", shiroUser.getUser().getRealname());
		map.put("inquire", inquire);
		map.put("inquireStatus", STATUS.NewStatus);
		return ASK_VIEW;
	}

	@RequiresPermissions("Inquire:view")
	@RequestMapping(value = "/inquire/print/{id}", method = RequestMethod.GET)
	public String printAsk(@PathVariable Long id, Map<String, Object> map) {
		Inquire inquire = kfglService.getInquire(id);

		map.put("inquire", inquire);
		map.put("inquireStatus", STATUS.NewStatus);
		return ASK_PRINT;
	}

	@RequiresPermissions("Inquire:view")
	@RequestMapping(value = "/inquires/print", method = { RequestMethod.POST, RequestMethod.GET })
	public String printAsks(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();

		String inquireStatus = request.getParameter("inquireStatus");
		LOG.debug("-------------- inquireStatus: " + inquireStatus);
		Inquire inquire = new Inquire();
		if (inquireStatus == null) {
			inquireStatus = STATUS.CloseStatus.name();
		}
		inquire.setInquireStatus(inquireStatus);

		org.apache.catalina.util.URLEncoder urlEncoder = new org.apache.catalina.util.URLEncoder();
		if (inquireStatus == null) {
			inquireStatus = "";
		}
		// request.setAttribute("encodeStatus",
		// Base64Utils.encodeToString(inquireStatus.getBytes()));
		request.setAttribute("inquireStatus", urlEncoder.encode(inquireStatus, Charset.defaultCharset()));
		// request.setAttribute("inquireStatus", inquireStatus);

		map.put("inquire", inquire);
		map.put("statusList", STATUS.values());

		String searchOrg = request.getParameter("orgCode");
		request.setAttribute("orgCode", searchOrg);
		request.setAttribute("name", request.getParameter("name"));
		LOG.debug("------------orgCode:" + searchOrg + ", name=:" + request.getParameter("name"));
		if (searchOrg != null && searchOrg.trim().length() <= 0) {
			if (userOrg.getOrgCode().length() <= 4) {
				// return AjaxObject.newError("请选择机构！").toString();
				return ASK_PRINT_LIST;
			} else {
				searchOrg = userOrg.getOrgCode();
			}
		} else if (searchOrg == null || searchOrg.trim().length() <= 0) {
			return ASK_PRINT_LIST;
		}

		page.setNumPerPage(50);

		Specification<Inquire> specification = DynamicSpecifications.bySearchFilter(request, Inquire.class,
				new SearchFilter("inquireStatus", Operator.LIKE, inquireStatus),
				new SearchFilter("policy.organization.orgCode", Operator.LIKE, searchOrg));
		List<Inquire> inquires = kfglService.findByInquireExample(specification, page);
		LOG.debug("-----------------" + inquires);
		map.put("inquires", inquires);

		return ASK_PRINT_LIST;
	}

	@RequestMapping(value = "/inquire/*")
	@ModelAttribute("preloadInquire")
	public Inquire preloadAsk(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Inquire inquire = kfglService.getInquire(id);
			if (inquire != null) {
				inquire.setPolicy(null);
			}
			return inquire;
		}
		return null;
	}

	@RequiresPermissions("Inquire:edit")
	@RequestMapping(value = "/inquire/update/{id}", method = RequestMethod.GET)
	public String preUpdateAsk(@PathVariable Long id, Map<String, Object> map) {
		Inquire inquire = kfglService.getInquire(id);

		map.put("inquire", inquire);
		return ASK_UPDATE;
	}

	@Log(message = "回复了{0}问题工单的信息。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Inquire:edit")
	@RequestMapping(value = "/inquire/update", method = RequestMethod.POST)
	public @ResponseBody String updateAsk(HttpServletRequest request, @RequestParam(value = "file", required = true) MultipartFile file, @Valid @ModelAttribute("preloadInquire") Inquire inquire) {
		int iNY = UploadDataUtils.getNianYue();
        String strPath = UploadDataUtils.getNoticeFileStorePath(request, iNY, "KFGL");
        String updatePath = UploadDataUtils.getNoticeRelateFileStorePath(request, iNY, "KFGL");
		String strNewFileName = null;
		boolean hasFile = false;
        if(file != null && file.getOriginalFilename() != null && file.getOriginalFilename().trim().length()>0) {
	        try {
	        	hasFile = true;
	        	String name = file.getOriginalFilename();
	            Long lFileSize = file.getSize();
	
	            com.gdpost.utils.UploadDataHelper.SessionChunk sessionChunk = new com.gdpost.utils.UploadDataHelper.SessionChunk();
	            com.gdpost.utils.UploadDataHelper.FileChunk fileChunk = sessionChunk.getSessionChunk(request);
	            if(fileChunk == null) {
	                fileChunk = new com.gdpost.utils.UploadDataHelper.FileChunk();
	            }
	            String chunkSize = request.getParameter("chunkSize");
	            int iChunkSize = Integer.parseInt(chunkSize==null?"0":chunkSize); //分块大小
	            String strOriginalFileName = name;
	            String strSessionID = request.getSession().getId();
	            
	            int iCurrentChunk = 1;
	            int iChunks = 1;
	            try {
	            	iCurrentChunk = Integer.parseInt(request.getParameter("chunk")); //当前分块
	            	iChunks = Integer.parseInt(request.getParameter("chunks"));//总的分块数量
	            } catch(Exception e) {
	            }
	            
	            strNewFileName = strOriginalFileName;
	            if (iChunks > 1) {
	            	strNewFileName = iCurrentChunk + "_" + strSessionID + "_" + strOriginalFileName;   //按文件块重命名块文件
	            }
	            
	            String strFileGroup = request.getParameter("fileGroup"); // 当前文件组
	            fileChunk.setChunks(iChunks);
	            fileChunk.setChunkSize(iChunkSize);
	            fileChunk.setCurrentChunk(iCurrentChunk);
	            fileChunk.setFileName(strOriginalFileName);
	            fileChunk.setFileGroup(strFileGroup);
	            fileChunk.setFileSize(lFileSize);
	            fileChunk.setListFileName(strOriginalFileName);
	            
	            sessionChunk.setSessionChunk(request, fileChunk);
	            
	            File uploadedFile = null;
	            if (iChunks > 1) {
	            	uploadedFile = new File(strPath, strNewFileName);
	            	if(uploadedFile.exists()) {
	            		uploadedFile.delete();
	            	}
	            	
	            	uploadedFile = new File(strPath, strNewFileName);
	            	
	            	try {
	    				org.apache.commons.io.FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
	    			} catch (IOException e) {
	    				LOG.error(e.getMessage());
	    				return (strError);
	    			} catch (Exception e) {
	    				LOG.error(e.getMessage());
	    				return (strError);				
	    			}
	            	
	                if(iCurrentChunk + 1 == iChunks) {
	                    // 上传完成转移到完成文件目录
	                    int BUFSIZE = 1024 * 1024 * 8;
	                    FileChannel outChannel = null;
	                    
	                	try {
	                		FileOutputStream fos = new FileOutputStream(strPath + File.separator + strOriginalFileName);
	                		outChannel = fos.getChannel();
	                		String strChunkFile = "";
	                		ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
	                	    for (int i = 0; i < iChunks; i++) {
	                	    	strChunkFile = strPath + File.separator + i + "_" + strSessionID + "_" + strOriginalFileName;
	                	    	FileInputStream fis = new FileInputStream(strChunkFile);
	                	    	FileChannel fc = fis.getChannel();
	                	    	while(fc.read(bb) != -1){
	                	    		bb.flip();
	                	    	    outChannel.write(bb);
	                	    		bb.clear();
	                	    	}
	                	    	
	                	    	fc.close();
	                	    	fis.close();
	                	    
	                	    	java.nio.file.Path path = java.nio.file.Paths.get(strChunkFile);
	                	    	Files.delete(path);
	            	    	}
	
	                	    fos.close();
	            	    	
	                		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{strNewFileName}));
	                	    ShiroUser shiroUser = SecurityUtils.getShiroUser();
	                	    LOG.info(shiroUser.getLoginName() + "上传了" + strOriginalFileName);
	    				
	            		} catch (FileNotFoundException e) {
	            			LOG.error(e.getMessage());
	            			return AjaxObject.newError("发布失败！").toString();
	    				} catch (IOException e) {
	    					LOG.error(e.getMessage());
	    					return AjaxObject.newError("发布失败！").toString();
	    				} catch (Exception e) {
	    					LOG.error(e.getMessage());
	    					return AjaxObject.newError("发布失败！").toString();
	    				}
	                	finally {
	            			try {
	            				if(outChannel != null) {
	            					outChannel.close();
	            				}
	    					} catch (IOException e) {
	    					}
	                	}
	                }
	            } else { 
	                // 单个文件直接保存
	            	LOG.debug("-------------single file name:" + strOriginalFileName);
	            	uploadedFile = new File(strPath, strNewFileName);
	            	try {
	    				org.apache.commons.io.FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
	            	    ShiroUser shiroUser = SecurityUtils.getShiroUser();
	            	    LOG.info(shiroUser.getLoginName() + "上传了" + strNewFileName);
	    				LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{strOriginalFileName}));
	    			} catch (IOException e) {
	    				LOG.error(e.getMessage());
	    				return AjaxObject.newError("发布失败！").toString();
	    			}
	            	
	            }
	        }
	        catch (Exception e)
	        {
	            // 上传失败，IO异常！
	            e.printStackTrace();
	            LOG.error("--- UPLOAD FAIL ---");
	            LOG.error(e.getMessage());
	            return AjaxObject.newError("发布失败！").toString();
	        }
        }
        
		Inquire src = kfglService.getInquire(inquire.getId());
		src.setDealMan(inquire.getDealMan());
		src.setDealTime(inquire.getDealTime());
		src.setInquireRst(inquire.getInquireRst());
		//src.setCityReviewRst(inquire.getCityReviewRst());
		//src.setCityReviewer(inquire.getCityReviewer());
		src.setInquireStatus(StatusDefine.STATUS.IngStatus.name());
		if(src.getAttrLink() != null && src.getAttrLink().trim().length() >0 ) {
			UploadDataUtils.delateFile(request, src.getAttrLink());
		}
		if (hasFile) {
	        src.setAttrLink(updatePath + "/" + strNewFileName);
        }
		kfglService.saveOrUpdateInquire(src);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { inquire.getInquireNo() }));
		return AjaxObject.newOk("回复问题工单成功！").toString();
	}

	@Log(message = "重新打开了{0}问题工单的信息。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Inquire:edit")
	@RequestMapping(value = "/inquire/reopen", method = RequestMethod.POST)
	public @ResponseBody String reopenAsk(@Valid @ModelAttribute("preloadInquire") Inquire inquire) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		Inquire src = kfglService.getInquire(inquire.getId());
		if (!src.getInquireStatus().equals(STATUS.IngStatus.name())) {
			return AjaxObject.newError("工单状态不足以操作审核：请核查工单状态。").setCallbackType("").toString();
		}
		src.setInquireStatus(StatusDefine.STATUS.NewStatus.name());
		src.setReopenUser(shiroUser.getUser());
		src.setReopenReason(inquire.getReopenReason());
		src.setReopenDate(new Date());
		kfglService.saveOrUpdateInquire(src);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { inquire.getInquireNo() }));
		return AjaxObject.newOk("重新打开问题工单成功！").toString();
	}

	@Log(message = "审核了{0}问题工单的信息。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Inquire:edit")
	@RequestMapping(value = "/inquire/deal", method = RequestMethod.POST)
	public @ResponseBody String dealAsk(@Valid @ModelAttribute("preloadInquire") Inquire inquire) {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		Inquire src = kfglService.getInquire(inquire.getId());
		if (!src.getInquireStatus().equals(STATUS.IngStatus.name())) {
			return AjaxObject.newError("工单状态不足以操作审核：请核查咨询件状态。").setCallbackType("").toString();
		}
		src.setInquireStatus(STATUS.DealStatus.name());
		src.setChecker(inquire.getChecker());
		src.setCheckRst(inquire.getCheckRst());
		src.setCheckDate(new Date());
		kfglService.saveOrUpdateInquire(src);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { inquire.getInquireNo() }));
		return AjaxObject.newOk("审核问题工单成功！").toString();
	}
	
	@Log(message = "转办了{0}问题工单的信息。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Inquire:edit")
	@RequestMapping(value = "/inquire/toCity", method = RequestMethod.POST)
	public @ResponseBody String toCity(@Valid @ModelAttribute("preloadInquire") Inquire inquire) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		Inquire src = kfglService.getInquire(inquire.getId());
		src.setCityDealFlag(true);
		src.setToCityUser(shiroUser.getUser());
		src.setToCityDate(new Date());
		kfglService.saveOrUpdateInquire(src);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { inquire.getInquireNo() }));
		return AjaxObject.newOk("咨询工单转办成功！").toString();
	}
	
	@Log(message = "对{0}问题工单进行了批量转办。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Inquire:provEdit")
	@RequestMapping(value = "/inquire/batchToCity", method = RequestMethod.POST)
	public @ResponseBody String batchToCity(Long[] ids) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		String[] policys = new String[ids.length];
		try {
			Inquire inquire = null;
			for (int i = 0; i < ids.length; i++) {
				inquire = kfglService.getInquire(ids[i]);
				inquire.setCityDealFlag(true);
				inquire.setToCityUser(shiroUser.getUser());
				inquire.setToCityDate(new Date());
				kfglService.saveOrUpdateInquire(inquire);

				policys[i] = inquire.getInquireNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("批量转办工单失败：" + e.getMessage()).setCallbackType("").toString();
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(policys) }));
		return AjaxObject.newOk("成功批量转办咨询工单！").setCallbackType("").toString();
	}

	@Log(message = "结案了{0}问题工单的信息。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Inquire:edit")
	@RequestMapping(value = "/inquire/close", method = RequestMethod.POST)
	public @ResponseBody String closeAsk(@Valid @ModelAttribute("preloadInquire") Inquire inquire) {
		// ShiroUser shiroUser = SecurityUtils.getShiroUser();
		Inquire src = kfglService.getInquire(inquire.getId());
		if (src.getInquireStatus() != STATUS.DealStatus.name()) {
			return AjaxObject.newError("结案关闭咨询工单失败：未完成审核").setCallbackType("").toString();
		}
		src.setInquireStatus(STATUS.CloseStatus.name());
		kfglService.saveOrUpdateInquire(src);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { inquire.getInquireNo() }));
		return AjaxObject.newOk("结案问题工单成功！").toString();
	}

	@Log(message = "结案了{0}问题工单的信息。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Inquire:edit")
	@RequestMapping(value = "/inquire/close/{id}", method = RequestMethod.POST)
	public @ResponseBody String closeSingleAsk(@PathVariable Long id) {
		// ShiroUser shiroUser = SecurityUtils.getShiroUser();
		Inquire src = kfglService.getInquire(id);
		if (src.getInquireStatus() != STATUS.DealStatus.name()) {
			return AjaxObject.newError("结案关闭咨询工单失败：未完成审核").setCallbackType("").toString();
		}
		src.setInquireStatus(STATUS.CloseStatus.name());
		kfglService.saveOrUpdateInquire(src);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { src.getInquireNo() }));
		return AjaxObject.newOk("结案问题工单成功！").setCallbackType("").toString();
	}

	@Log(message = "对{0}问题工单进行了结案关闭。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Inquire:provEdit")
	@RequestMapping(value = "/inquire/CloseStatus", method = RequestMethod.POST)
	public @ResponseBody String closeManyAsk(Long[] ids) {
		String[] policys = new String[ids.length];
		try {
			Inquire inquire = null;
			for (int i = 0; i < ids.length; i++) {
				inquire = kfglService.getInquire(ids[i]);
				if (inquire.getInquireStatus() != STATUS.DealStatus.name()) {
					return AjaxObject.newError("部分结案关闭咨询工单失败：未完成审核").setCallbackType("").toString();
				}
				inquire.setInquireStatus(STATUS.CloseStatus.name());
				kfglService.saveOrUpdateInquire(inquire);

				policys[i] = inquire.getInquireNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("结案关闭工单失败：" + e.getMessage()).setCallbackType("").toString();
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(policys) }));
		return AjaxObject.newOk("成功结案关闭！").setCallbackType("").toString();
	}

	@Log(message = "对{0}问题工单进行了批量审核通过。", level = LogLevel.WARN, module = LogModule.KFGL)
	@RequiresPermissions("Inquire:provEdit")
	@RequestMapping(value = "/inquire/batchDeal", method = RequestMethod.POST)
	public @ResponseBody String batchDealAsk(Long[] ids) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		String[] policys = new String[ids.length];
		try {
			Inquire inquire = null;
			for (int i = 0; i < ids.length; i++) {
				inquire = kfglService.getInquire(ids[i]);
				inquire.setInquireStatus(STATUS.DealStatus.name());
				inquire.setChecker(shiroUser.getUser().getUsername() + "_" + shiroUser.getUser().getRealname());
				inquire.setCheckRst("审核通过。");
				inquire.setCheckDate(new Date());
				kfglService.saveOrUpdateInquire(inquire);

				policys[i] = inquire.getInquireNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("批量审核工单失败：" + e.getMessage()).setCallbackType("").toString();
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(policys) }));
		return AjaxObject.newOk("成功批量审核通过！").setCallbackType("").toString();
	}

	@RequiresPermissions("Inquire:view")
	@RequestMapping(value = "/inquire/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String listAsk(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		boolean hasCon = true;
		if (orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = userOrg.getOrgCode();
			hasCon = false;
		} else if (!orgCode.contains(user.getOrganization().getOrgCode())) {
			orgCode = user.getOrganization().getOrgCode();
		}

		if (hasCon) {
			String orgName = request.getParameter("name");
			request.setAttribute("orgCode", orgCode);
			request.setAttribute("name", orgName);
		}

		// 默认返回未处理工单
		String inquireStatus = request.getParameter("inquireStatus");
		String inquireSubtype = request.getParameter("inquireSubtype");
		request.setAttribute("inquireSubtype", inquireSubtype);
		String kfstatus_flag = request.getParameter("kfstatus_flag");
		LOG.debug("-------------- inquireStatus: " + inquireStatus + ", user org code:" + userOrg.getOrgCode());
		LOG.debug("==----==:" + kfstatus_flag == null ? "is null" : "equals 'null'");
		boolean isNull = false;
		if (inquireStatus == null || (inquireStatus != null && inquireStatus.trim().length() <= 0 && kfstatus_flag != null
				&& kfstatus_flag.equals("null"))) {
			isNull = true;
			request.setAttribute("kfstatus_flag", "null");
		} else {
			request.setAttribute("kfstatus_flag", inquireStatus);
		}

		request.setAttribute("inquireStatus", inquireStatus);

		Inquire inquire = new Inquire();
		inquire.setInquireStatus(inquireStatus);
		inquire.setInquireSubtype(inquireSubtype);

		if (page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("operateTime");
			page.setOrderDirection("ASC");
		}

		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));

		//只回显需要地市处理的
		if(orgCode.length() >= 6) {
			csf.add(new SearchFilter("cityDealFlag", Operator.EQ, 1));
		}
		
		if (inquireSubtype != null && inquireSubtype.trim().length() > 0) {
			csf.add(new SearchFilter("inquireSubtype", Operator.EQ, inquireSubtype));
		}

		// 如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if (user.getOrganization().getOrgCode().length() > 4) {
			if (isNull) {
				LOG.debug("-------------- 111: ");
				csf.add(new SearchFilter("inquireStatus", Operator.EQ, STATUS.NewStatus.name()));
			} else if (inquireStatus.trim().length() > 0) {
				csf.add(new SearchFilter("inquireStatus", Operator.EQ, inquireStatus));
			}
		} else {
			if (isNull) {
				LOG.debug("-------------- 333: ");
				csf.add(new SearchFilter("inquireStatus", Operator.OR_EQ, STATUS.NewStatus.name()));
				csf.add(new SearchFilter("inquireStatus", Operator.OR_EQ, STATUS.IngStatus.name()));
			} else if (inquireStatus.trim().length() > 0) {
				csf.add(new SearchFilter("inquireStatus", Operator.EQ, inquireStatus));
			}
		}
		Specification<Inquire> specification = DynamicSpecifications.bySearchFilter(request, Inquire.class, csf);
		List<Inquire> inquires = kfglService.findByInquireExample(specification, page);

		map.put("inquire", inquire);
		map.put("statusList", STATUS.values());

		List<String> inquireSubtypes = kfglService.getInquireSubtypeList();
		map.put("inquireSubtypes", inquireSubtypes);

		map.put("page", page);
		map.put("inquires", inquires);
		return ASK_LIST;
	}

	@RequiresPermissions("Inquire:view")
	@RequestMapping(value="/inquirelist/toXls", method=RequestMethod.GET)
	public String toAskXls(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String inquireStatus = request.getParameter("inquireStatus");
		
		String kfstatus_flag = request.getParameter("kfstatus_flag");
		boolean isNull = false;
		if(inquireStatus == null || (inquireStatus != null && inquireStatus.trim().length()<=0 && kfstatus_flag!=null && kfstatus_flag.equals("null"))) {
			isNull = true;
		}
		
		page.setOrderField("policy.organization.orgCode");
		page.setOrderDirection("ASC");
		page.setNumPerPage(65564);
		String orgCode = request.getParameter("orgCode");
		String inquireType = request.getParameter("inquireType");
		
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = user.getOrganization().getOrgCode();
		} else if(!orgCode.contains(user.getOrganization().getOrgCode())){
			orgCode = user.getOrganization().getOrgCode();
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		
		//只回显需要地市处理的
		if(orgCode.length() >= 6) {
			csf.add(new SearchFilter("cityDealFlag", Operator.EQ, 1));
		}
				
		if(inquireType != null && inquireType.trim().length()>0) {
			csf.add(new SearchFilter("inquireType", Operator.EQ, inquireType));
		}
		
		if (user.getOrganization().getOrgCode().length() > 4) {
			if(isNull) {
				LOG.debug("-------------- 111: " );
				csf.add(new SearchFilter("inquireStatus", Operator.EQ, STATUS.NewStatus.name()));
			} else if(inquireStatus.trim().length() > 0) {
				csf.add(new SearchFilter("inquireStatus", Operator.EQ, inquireStatus));
			}
		} else {
			if(isNull) {
				LOG.debug("-------------- 333: " );
				csf.add(new SearchFilter("inquireStatus", Operator.OR_EQ, STATUS.NewStatus.name()));
				csf.add(new SearchFilter("inquireStatus", Operator.OR_EQ, STATUS.IngStatus.name()));
			} else if(inquireStatus.trim().length() > 0) {
				csf.add(new SearchFilter("inquireStatus", Operator.EQ, inquireStatus));
			}
		}
		Specification<Inquire> specification = DynamicSpecifications.bySearchFilter(request, Inquire.class, csf);
		List<Inquire> reqs = kfglService.findByInquireExample(specification, page);
	
		map.put("reqs", reqs);
		return ASK_TO_XLS;
	}

	@RequiresPermissions("Inquire:view")
	@RequestMapping(value = "/inquiresToXls", method = RequestMethod.GET)
	public String askToXls(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String inquireStatus = request.getParameter("inquireStatus");
		if (inquireStatus == null || (inquireStatus != null && inquireStatus.trim().equals("null"))) {
			inquireStatus = "";
		}

		page.setOrderField("policy.organization.orgCode");
		page.setOrderDirection("ASC");
		page.setNumPerPage(65564);
		String orgCode = request.getParameter("orgCode");
		if (orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = user.getOrganization().getOrgCode();
		} else if (!orgCode.contains(user.getOrganization().getOrgCode())) {
			orgCode = user.getOrganization().getOrgCode();
		}
		/*
		 * Specification<Inquire> specification =
		 * DynamicSpecifications.bySearchFilter(request, Inquire.class, new
		 * SearchFilter("inquireStatus", Operator.LIKE, s), new
		 * SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		 */
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));

		//只回显需要地市处理的
		if(orgCode.length() >= 6) {
			csf.add(new SearchFilter("cityDealFlag", Operator.EQ, 1));
		}
				
		// 如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if (user.getOrganization().getOrgCode().length() > 4) {
			if (inquireStatus.length() <= 0) {
				LOG.debug("-------------- 111: ");
				csf.add(new SearchFilter("inquireStatus", Operator.EQ, STATUS.NewStatus.name()));
			} else {
				LOG.debug("-------------- 222: ");
				csf.add(new SearchFilter("inquireStatus", Operator.EQ, inquireStatus));
			}
		} else {
			if (inquireStatus.length() <= 0) {
				LOG.debug("-------------- 333: ");
				csf.add(new SearchFilter("inquireStatus", Operator.OR_EQ, STATUS.IngStatus.name()));
				csf.add(new SearchFilter("inquireStatus", Operator.OR_EQ, STATUS.NewStatus.name()));
			} else {
				LOG.debug("-------------- 444: ");
				csf.add(new SearchFilter("inquireStatus", Operator.EQ, inquireStatus));
			}
		}
		Specification<Inquire> specification = DynamicSpecifications.bySearchFilter(request, Inquire.class, csf);
		List<Inquire> reqs = kfglService.findByInquireExample(specification, page);

		map.put("reqs", reqs);
		return ASK_ISSUES_TO_XLS;
	}

	@RequiresUser
	@RequiresPermissions("Inquire:view")
	@RequestMapping(value = "/inquire/toWord", method = { RequestMethod.POST, RequestMethod.GET })
	public String askToWord(ServletRequest request, String ids) {
		// 如果是市局登录
		// ShiroUser shiroUser = SecurityUtils.getShiroUser();

		Inquire inquire = null;
		String policyNo = null;
		String newPath = null;
		InputStream is = null;
		OutputStream os = null;
		HWPFDocument doc = null;
		String inquireReq = null;

		String templatePath = request.getServletContext().getRealPath("/doc/inquire/inquire_template.doc");
		String docPath = request.getServletContext().getRealPath("/doc/inquire/");
		String inquirePath = docPath + StringUtil.date2Str(new Date(), "yyyyMMddHHmmss");
		File dfile = new File(inquirePath);
		if (!dfile.exists()) {
			dfile.mkdirs();
		}
		String[] strIds = ids.split(",");
		String operater = null;
		for (String id : strIds) {
			inquire = kfglService.getInquire(new Long(id));
			policyNo = inquire.getPolicy().getPolicyNo();
			operater = userService.get(inquire.getOperateId()).getRealname();
			newPath = inquirePath + File.separator + policyNo + "_" + inquire.getInquireNo() + ".doc";
			try {
				is = new FileInputStream(templatePath);
				doc = new HWPFDocument(is);
				Range range = doc.getRange();
				if (inquire.getInquireType().contains("退保") || inquire.getInquireType().contains("条款解释不清")
						|| inquire.getInquireType().contains("其他")) {
					inquireReq = "转业务岗处理";
				} else if (inquire.getInquireType().contains("保单未送达") || inquire.getInquireType().contains("抄写")
						|| inquire.getInquireType().contains("告知") || inquire.getInquireType().contains("接听")
						|| inquire.getInquireType().contains("挂断")) {
					inquireReq = "转契约岗处理";
				} else {
					inquireReq = "";
				}
				range.replaceText("${inquireType}", inquire.getInquireType());
				range.replaceText("${inquireNo}", inquire.getInquireNo());
				range.replaceText("${orgName}",
						inquire.getOrganName() == null ? inquire.getPolicy().getOrganName() : inquire.getOrganName());
				range.replaceText("${policyNo}", policyNo);
				range.replaceText("${bankName}", inquire.getNetName());
				range.replaceText("${policyDate}",
						StringUtil.date2Str(inquire.getPolicy().getPolicyDate(), "yyyy-MM-dd"));
				range.replaceText("${policyFee}", new Double(inquire.getPolicy().getPolicyFee()).toString());
				range.replaceText("${holder}", inquire.getPolicy().getHolder());
				range.replaceText("${holderPhone}", inquire.getHolderPhone());
				range.replaceText("${holderMobile}", inquire.getHolderMobile());
				range.replaceText("${finishDate}", inquire.getFinishDate());
				range.replaceText("${inquireContent}", inquire.getInquireDesc());
				range.replaceText("${inquireReq}", inquireReq);
				range.replaceText("${userName}", operater);
				range.replaceText("${shouldDate}", StringUtil.date2Str(inquire.getOperateTime(), "yyyy-MM-dd"));
				range.replaceText("${checker}", inquire.getChecker() == null ? "" : inquire.getChecker());
				range.replaceText("${checkDate}", inquire.getCheckDate() == null ? ""
						: StringUtil.date2Str(inquire.getCheckDate(), "yyyy-MM-dd"));
				range.replaceText("${inquireResult}", inquire.getInquireRst());
				range.replaceText("${dealMan}", inquire.getDealMan());
				range.replaceText("${dealTime}", StringUtil.date2Str(inquire.getDealTime(), "yyyy-MM-dd"));
				range.replaceText("${cityReviewRst}", "");
				range.replaceText("${cityReviewer}", "");
				range.replaceText("${reviewTime}", StringUtil.date2Str(inquire.getDealTime(), "yyyy-MM-dd"));
				os = new FileOutputStream(newPath);
				// 把doc输出到输出流中
				doc.write(os);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				this.closeSource(is, os, doc);
			}
		}

		FileUtils fileUtil = new FileUtils();
		String filename = StringUtil.date2Str(new Date(), "yyyyMMddHHmmss");
		File zipFileName = new File(docPath + filename + ".zip");

		try {
			zipFileName.deleteOnExit();
			fileUtil.zip(dfile, zipFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("filename", filename);
		return ASK_TO_DOWN;
	}

	@RequiresUser
	@RequiresPermissions("Inquire:view")
	@RequestMapping(value = "/inquire/down/{zipfilename}", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity<byte[]> downAsk(ServletRequest request, @PathVariable String zipfilename) {
		String docPath = request.getServletContext().getRealPath("/doc/inquire/");
		String zipFileName = docPath + zipfilename + ".zip";
		try {
			File file = new File(zipFileName);
			if (!file.exists()) {
				LOG.error(" =----- 文件不存在。");
				return null;
			}
			HttpHeaders headers = new HttpHeaders();
			// 下载显示的文件名，解决中文名称乱码问题
			String downloadFielName = new String(zipfilename.getBytes("UTF-8"), "iso-8859-1");
			// 通知浏览器以attachment（下载方式）打开图片
			headers.setContentDispositionFormData("attachment", downloadFielName + ".zip");
			// application/octet-stream ： 二进制流数据（最常见的文件下载）。
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(org.apache.commons.io.FileUtils.readFileToByteArray(file), headers,
					HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * ========================================================================
	 * end of 客服咨询
	 * ========================================================================件
	 */

	/**
	 * 关闭输入流
	 * 
	 * @param is
	 */
	private void closeSource(InputStream is, OutputStream os, HWPFDocument doc) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (doc != null) {
			try {
				doc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 使用初始化绑定器, 将参数自动转化为日期类型,即所有日期类型的数据都能自动转化为yyyy-MM-dd格式的Date类型
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
