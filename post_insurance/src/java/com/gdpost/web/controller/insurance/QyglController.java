/**
 * 
 * ==========================================================
 * 契约管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

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
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.StringUtil;
import com.gdpost.web.entity.basedata.CheckFixType;
import com.gdpost.web.entity.component.YbtPolicyModel;
import com.gdpost.web.entity.insurance.CheckRecord;
import com.gdpost.web.entity.insurance.CheckWrite;
import com.gdpost.web.entity.insurance.UnderWrite;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.insurance.QyglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.StatusDefine.QY_STATUS;
import com.gdpost.web.util.StatusDefine.UW_STATUS;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/qygl")
public class QyglController {
	private static final Logger log = LoggerFactory.getLogger(QyglController.class);
	
	@Autowired
	private QyglService qyglService;

	private static final String VIEW_WRITE = "insurance/qygl/wtj/write/view";
	private static final String UPDATE_WRITE = "insurance/qygl/wtj/write/update";
	private static final String LIST_WRITE = "insurance/qygl/wtj/write/list";
	private static final String ISSUE_WRITE_LIST = "insurance/qygl/wtj/write/issuelist";
	
	private static final String VIEW_RECORD = "insurance/qygl/wtj/record/view";
	private static final String UPDATE_RECORD = "insurance/qygl/wtj/record/update";
	private static final String LIST_RECORD = "insurance/qygl/wtj/record/list";
	private static final String ISSUE_RECORD_LIST = "insurance/qygl/wtj/record/issuelist";
	
	private static final String UNDERWRITE_LIST = "insurance/qygl/underwrite/underwritelist";
	private static final String UW_VIEW = "insurance/qygl/underwrite/view";
	private static final String UW_UPDATE = "insurance/qygl/underwrite/update";
	private static final String UW_LIST = "insurance/qygl/underwrite/list";
	private static final String UW_CREATE = "insurance/qygl/underwrite/create";
	private static final String UW_SIGNDATEUPDATE = "insurance/qygl/underwrite/signDateUpdate";
	private static final String UW_MAIL_DATE = "insurance/qygl/underwrite/mailDate";
	private static final String UW_REC_DATE = "insurance/qygl/underwrite/recDate";
	private static final String UW_MAIL_DATES = "insurance/qygl/underwrite/mailDates";
	private static final String UW_REC_DATES = "insurance/qygl/underwrite/recDates";
	private static final String UW_PLAN = "insurance/qygl/underwrite/setPlan";
	private static final String UW_PLAN_LIST = "insurance/qygl/underwrite/planList";
	
	private static final String UW_POP = "insurance/qygl/underwrite/uwPop";
	private static final String UW_CALL = "insurance/qygl/underwrite/uwCall";
	private static final String UW_WEIXIN = "insurance/qygl/underwrite/uwWeixin";
	
	private static final String TO_XLS = "insurance/qygl/underwrite/toXls";
	
	private static final String WRITE_TO_XLS = "insurance/qygl/wtj/write/toXls";
	private static final String RECORD_TO_XLS = "insurance/qygl/wtj/record/toXls";
	
	private static final String TO_HELP = "insurance/help/qygl";
	
	private static final String YBT_LIST = "insurance/qygl/ybt/list";
	
	@RequestMapping(value="/help", method=RequestMethod.GET)
	public String toHelp() {
		return TO_HELP;
	}
	
	/*
	 * =====================================
	 * WRITE
	 * =====================================
	 */
	@RequiresPermissions("CheckWrite:view")
	@RequestMapping(value="/issue/write/view/{id}", method=RequestMethod.GET)
	public String viewCheckWrite(@PathVariable Long id, Map<String, Object> map) {
		CheckWrite issue = qyglService.getCheckWrite(id);
		
		map.put("issue", issue);
		map.put("status", QY_STATUS.NewStatus);
		return VIEW_WRITE;
	}
	
	@RequiresPermissions("CheckWrite:edit")
	@RequestMapping(value="/issue/write/update/{id}", method=RequestMethod.GET)
	public String preUpdateCheckWrite(@PathVariable Long id, Map<String, Object> map) {
		CheckWrite issue = qyglService.getCheckWrite(id);
		List<CheckFixType> cftList = qyglService.getCheckFixTypeList();
		map.put("checkFixList", cftList);
		map.put("issue", issue);
		return UPDATE_WRITE;
	}
	
	@Log(message="回复了{0}新契约填写不合格件的信息。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions("CheckWrite:edit")
	@RequestMapping(value="/issue/write/update", method=RequestMethod.POST)
	public @ResponseBody String updateCheckWrite(CheckWrite issue) {
		CheckWrite src = qyglService.getCheckWrite(issue.getId());
		src.setDealMan(issue.getDealMan());
		src.setDealTime(issue.getDealTime());
		src.setFixType(issue.getFixType());
		src.setFixDesc(issue.getFixDesc());
		if(issue.getFixType().contains("继续跟进") || issue.getFixDesc().contains("继续跟进")) {
			src.setFixStatus(QY_STATUS.FollowStatus.name());
		} else {
			src.setFixStatus(QY_STATUS.IngStatus.name());
		}
		src.setReplyTime(new Date());
		qyglService.saveOrUpdateCheckWrite(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复新契约填写不合格件成功！").toString(); 
	}
	
	@Log(message="修改了{0}客户信息真实性标记。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions("CheckWrite:edit")
	@RequestMapping(value="/issue/write/isTruth", method=RequestMethod.POST)
	public @ResponseBody String cwIsTruth(CheckWrite issue) {
		CheckWrite src = qyglService.getCheckWrite(issue.getId());
		src.setIsTruth(src.getIsTruth()?false:true);
		qyglService.saveOrUpdateCheckWrite(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("成功修改记录的客户信息真实性标记！").setCallbackType("").toString();
	}
	
	@Log(message="对{0}新契约填写不合格件的申诉了，改为登记问题置为已整改。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions("CheckWrite:provEdit")
	@RequestMapping(value="/issue/write/appeal/{id}", method=RequestMethod.POST)
	public @ResponseBody String appealCheckWrite(@PathVariable Long id) {
		CheckWrite src = qyglService.getCheckWrite(id);
		src.setNeedFix("已整改");;
		src.setFixStatus(QY_STATUS.CloseStatus.name());
		qyglService.saveOrUpdateCheckWrite(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("申诉新契约填写不合格件成功！").setCallbackType("").toString();
	}
	
	@Log(message="对{0}新契约填写不合格件进行了删除。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions("CheckWrite:provEdit")
	@RequestMapping(value="/issue/write/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delCheckWrite(@PathVariable Long id) {
		CheckWrite src = qyglService.getCheckWrite(id);
		qyglService.deleteCheckWrite(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("删除新契约填写不合格件成功！").setCallbackType("").toString();
	}
	
	@Log(message="重新打开了{0}新契约填写不合格件的信息。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions("CheckWrite:edit")
	@RequestMapping(value="/issue/write/reopen", method=RequestMethod.POST)
	public @ResponseBody String reopenCheckWrite(CheckWrite issue) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		CheckWrite src = qyglService.getCheckWrite(issue.getId());
		src.setFixStatus(QY_STATUS.NewStatus.name());
		src.setReopenUser(shiroUser.getUser());
		src.setReopenReason(issue.getReopenReason());
		src.setReopenDate(new Date());
		qyglService.saveOrUpdateCheckWrite(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("重新打开新契约填写不合格件成功！").toString(); 
	}
	
	@Log(message="结案了{0}新契约填写不合格件的信息。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions("CheckWrite:edit")
	@RequestMapping(value="/issue/write/close", method=RequestMethod.POST)
	public @ResponseBody String closeCheckWrite(Long[] ids) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		String[] policys = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				CheckWrite src = qyglService.getCheckWrite(ids[i]);
				src.setFixStatus(QY_STATUS.CloseStatus.name());
				src.setCloseDate(new Date());
				src.setCloseUser(shiroUser.getUser().getRealname());
				qyglService.saveOrUpdateCheckWrite(src);
				
				policys[i] = src.getPolicy().getPolicyNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("结案失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("结案新契约填写不合格件成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("CheckWrite:view")
	@RequestMapping(value="/issue/write/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listCheckWrite(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		String status = request.getParameter("fixStatus");
		String orgCode = request.getParameter("orgCode");
		String checker = request.getParameter("checker");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		log.debug("-------------- orgCode: " + orgCode);
		CheckWrite issue = new CheckWrite();
		if(status == null) {
			status = QY_STATUS.NewStatus.name();
		} else if(status.trim().length()>0) {
			issue.setFixStatus(QY_STATUS.valueOf(status).name());
		}
		
		String keyInfo = request.getParameter("keyInfo");
		request.setAttribute("keyInfo", keyInfo);
		issue.setKeyInfo(keyInfo);
		
		request.setAttribute("status", status);
		issue.setFixStatus(status);
		request.setAttribute("checker", checker);
		issue.setChecker(checker);
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("id");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		csf.add(new SearchFilter("needFix", Operator.EQ, "要整改"));
		if(status.trim().length()>0) {
			csf.add(new SearchFilter("fixStatus", Operator.EQ, status));
		}
		if(checker != null && checker.trim().length()>0) {
			if(checker.equals("zhaoyong")) {
				csf.add(new SearchFilter("checker", Operator.EQ, checker));
			} else {
				csf.add(new SearchFilter("isTruth", Operator.EQ, true)); //客户信息真实性
			}
		}
		if(keyInfo != null && keyInfo.trim().length()>0) {
			switch(keyInfo) {
			case "Email":
			case "销售人员":
			case "号码有误":
			case "关系不符逻辑要求":
			case "联系方式":
			case "姓名有误":
				csf.add(new SearchFilter("keyInfo", Operator.LIKE, keyInfo));
				break;
			case "地址":
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址长度"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址含有邮政关键信息"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址疑似不够详细"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址不够详细"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址缺门牌号码"));
				break;
			case "证件":
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "证件号码"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "出生证号码"));
				break;
			}
		}
		
		Specification<CheckWrite> specification = DynamicSpecifications.bySearchFilter(request, CheckWrite.class, csf);
		
		List<CheckWrite> issues = qyglService.findByCheckWriteExample(specification, page);
		
		map.put("issue", issue);
		map.put("qyWriteStatusList", QY_STATUS.values());
		map.put("page", page);
		map.put("issues", issues);
		return LIST_WRITE;
	}
	
	@Log(message="下载了填写不合格件列表", level=LogLevel.INFO, module=LogModule.QYGL)
	@RequiresPermissions("CheckWrite:view")
	@RequestMapping(value="/issue/write/toXls", method={RequestMethod.GET, RequestMethod.POST})
	public String checkWriteToXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		String status = request.getParameter("fixStatus");
		String orgCode = request.getParameter("orgCode");
		String checker = request.getParameter("checker");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		log.debug("-------------- orgCode: " + orgCode);
		CheckWrite issue = new CheckWrite();
		if(status == null) {
			status = QY_STATUS.NewStatus.name();
		} else if(status.trim().length()>0) {
			issue.setFixStatus(QY_STATUS.valueOf(status).name());
		}
		
		String keyInfo = request.getParameter("keyInfo");
		
		page.setPageNum(0);
		page.setNumPerPage(Integer.MAX_VALUE);
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("id");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		csf.add(new SearchFilter("needFix", Operator.EQ, "要整改"));
		if(status.trim().length()>0) {
			csf.add(new SearchFilter("fixStatus", Operator.EQ, status));
		}
		if(checker != null && checker.trim().length()>0) {
			csf.add(new SearchFilter("checker", Operator.EQ, checker));
		}
		if(keyInfo != null && keyInfo.trim().length()>0) {
			switch(keyInfo) {
			case "Email":
			case "销售人员":
			case "号码有误":
			case "关系不符逻辑要求":
			case "联系方式":
			case "姓名有误":
				csf.add(new SearchFilter("keyInfo", Operator.LIKE, keyInfo));
				break;
			case "地址":
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址长度"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址含有邮政关键信息"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址疑似不够详细"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址不够详细"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址缺门牌号码"));
				break;
			case "证件":
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "证件号码"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "出生证号码"));
				break;
			}
		}
		
		Specification<CheckWrite> specification = DynamicSpecifications.bySearchFilter(request, CheckWrite.class, csf);
		
		List<CheckWrite> issues = qyglService.findByCheckWriteExample(specification, page);
		
		map.put("issues", issues);
		return WRITE_TO_XLS;
	}
	
	@RequiresPermissions("CheckWrite:view")
	@RequestMapping(value="/issue/write/issuelist", method={RequestMethod.GET, RequestMethod.POST})
	public String writeIssueList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put("issueList", qyglService.getTODOWriteIssueList(shiroUser.getUser()));
		return ISSUE_WRITE_LIST;
	}

	/*
	 * =====================================
	 * RECORD
	 * =====================================
	 */
	@RequiresPermissions("CheckRecord:view")
	@RequestMapping(value="/issue/record/view/{id}", method=RequestMethod.GET)
	public String viewCheckRecord(@PathVariable Long id, Map<String, Object> map) {
		CheckRecord issue = qyglService.getCheckRecord(id);
		
		map.put("issue", issue);
		map.put("status", QY_STATUS.NewStatus);
		return VIEW_RECORD;
	}
	
	@RequiresPermissions("CheckRecord:edit")
	@RequestMapping(value="/issue/record/update/{id}", method=RequestMethod.GET)
	public String preUpdateCheckRecord(@PathVariable Long id, Map<String, Object> map) {
		CheckRecord issue = qyglService.getCheckRecord(id);
		List<CheckFixType> cftList = qyglService.getCheckFixTypeList();
		map.put("checkFixList", cftList);
		map.put("issue", issue);
		return UPDATE_RECORD;
	}
	
	@Log(message="回复了{0}新契约录入不合格件的信息。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions("CheckRecord:edit")
	@RequestMapping(value="/issue/record/update", method=RequestMethod.POST)
	public @ResponseBody String updateCheckRecord(CheckRecord issue) {
		CheckRecord src = qyglService.getCheckRecord(issue.getId());
		src.setDealMan(issue.getDealMan());
		src.setDealTime(issue.getDealTime());
		src.setFixType(issue.getFixType());
		src.setFixDesc(issue.getFixDesc());
		if(issue.getFixType().contains("继续跟进") || issue.getFixDesc().contains("继续跟进")) {
			//nothing
		} else {
			src.setFixStatus(QY_STATUS.IngStatus.name());
		}
		src.setReplyTime(new Date());
		qyglService.saveOrUpdateCheckRecord(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复新契约录入不合格件成功！").toString(); 
	}
	
	@Log(message="重新打开了{0}新契约录入不合格件的信息。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions("CheckRecord:edit")
	@RequestMapping(value="/issue/record/reopen", method=RequestMethod.POST)
	public @ResponseBody String reopenCheckRecord(CheckRecord issue) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		CheckRecord src = qyglService.getCheckRecord(issue.getId());
		src.setFixStatus(QY_STATUS.NewStatus.name());
		src.setReopenUser(shiroUser.getUser());
		src.setReopenReason(issue.getReopenReason());
		src.setReopenDate(new Date());
		qyglService.saveOrUpdateCheckRecord(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("重新打开新契约录入不合格件成功！").toString(); 
	}
	
	@Log(message="结案了{0}新契约录入不合格件的信息。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions("CheckRecord:edit")
	@RequestMapping(value="/issue/record/close", method=RequestMethod.POST)
	public @ResponseBody String closeCheckRecord(CheckRecord issue) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		CheckRecord src = qyglService.getCheckRecord(issue.getId());
		src.setCloseDate(new Date());
		src.setCloseUser(shiroUser.getUser().getRealname());
		src.setFixStatus(QY_STATUS.CloseStatus.name());
		qyglService.saveOrUpdateCheckRecord(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("结案新契约录入不合格件成功！").toString(); 
	}
	
	@RequiresPermissions("CheckRecord:view")
	@RequestMapping(value="/issue/record/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listCheckRecord(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		String status = request.getParameter("fixStatus");
		String orgCode = request.getParameter("orgCode");
		String checker = request.getParameter("checker");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		log.debug("-------------- orgCode: " + orgCode);
		CheckWrite issue = new CheckWrite();
		if(status == null) {
			status = QY_STATUS.NewStatus.name();
		} else if(status.trim().length()>0) {
			issue.setFixStatus(QY_STATUS.valueOf(status).name());
		}
		
		String keyInfo = request.getParameter("keyInfo");
		request.setAttribute("keyInfo", keyInfo);
		issue.setKeyInfo(keyInfo);
		
		request.setAttribute("status", status);
		issue.setFixStatus(status);
		request.setAttribute("checker", checker);
		issue.setChecker(checker);
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("id");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		csf.add(new SearchFilter("needFix", Operator.EQ, "要整改"));
		if(status.trim().length()>0) {
			csf.add(new SearchFilter("fixStatus", Operator.EQ, status));
		}
		if(checker != null && checker.trim().length()>0) {
			csf.add(new SearchFilter("checker", Operator.EQ, checker));
		}
		if(keyInfo != null && keyInfo.trim().length()>0) {
			switch(keyInfo) {
			case "Email":
			case "销售人员":
			case "号码有误":
			case "关系不符逻辑要求":
			case "联系方式":
			case "姓名有误":
				csf.add(new SearchFilter("keyInfo", Operator.LIKE, keyInfo));
				break;
			case "地址":
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址长度"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址含有邮政关键信息"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址疑似不够详细"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址不够详细"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址缺门牌号码"));
				break;
			case "证件":
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "证件号码"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "出生证号码"));
				break;
			}
		}
		
		
		Specification<CheckRecord> specification = DynamicSpecifications.bySearchFilter(request, CheckRecord.class, csf);
		
		List<CheckRecord> issues = qyglService.findByCheckRecordExample(specification, page);
		
		map.put("issue", issue);
		map.put("qyRecordStatusList", QY_STATUS.values());
		map.put("page", page);
		map.put("issues", issues);
		return LIST_RECORD;
	}
	
	@Log(message="下载了录入不合格件列表", level=LogLevel.INFO, module=LogModule.QYGL)
	@RequiresPermissions("CheckRecord:view")
	@RequestMapping(value="/issue/record/toXls", method={RequestMethod.GET, RequestMethod.POST})
	public String checkRecordToXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		String status = request.getParameter("fixStatus");
		String orgCode = request.getParameter("orgCode");
		String checker = request.getParameter("checker");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		log.debug("-------------- orgCode: " + orgCode);
		CheckWrite issue = new CheckWrite();
		if(status == null) {
			status = QY_STATUS.NewStatus.name();
		} else if(status.trim().length()>0) {
			issue.setFixStatus(QY_STATUS.valueOf(status).name());
		}
		
		String keyInfo = request.getParameter("keyInfo");
		
		page.setPageNum(0);
		page.setNumPerPage(Integer.MAX_VALUE);
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("id");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		csf.add(new SearchFilter("needFix", Operator.EQ, "要整改"));
		if(status.trim().length()>0) {
			csf.add(new SearchFilter("fixStatus", Operator.EQ, status));
		}
		if(checker != null && checker.trim().length()>0) {
			csf.add(new SearchFilter("checker", Operator.EQ, checker));
		}
		if(keyInfo != null && keyInfo.trim().length()>0) {
			switch(keyInfo) {
			case "Email":
			case "销售人员":
			case "号码有误":
			case "关系不符逻辑要求":
			case "联系方式":
			case "姓名有误":
				csf.add(new SearchFilter("keyInfo", Operator.LIKE, keyInfo));
				break;
			case "地址":
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址长度"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址含有邮政关键信息"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址疑似不够详细"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址不够详细"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "地址缺门牌号码"));
				break;
			case "证件":
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "证件号码"));
				csf.add(new SearchFilter("keyInfo", Operator.OR_LIKE, "出生证号码"));
				break;
			}
		}
		
		Specification<CheckRecord> specification = DynamicSpecifications.bySearchFilter(request, CheckRecord.class, csf);
		
		List<CheckRecord> issues = qyglService.findByCheckRecordExample(specification, page);
		
		map.put("issues", issues);
		return RECORD_TO_XLS;
	}
	
	@RequiresPermissions("CheckRecord:view")
	@RequestMapping(value="/issue/record/issuelist", method={RequestMethod.GET, RequestMethod.POST})
	public String recordIssueList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put("issueList", qyglService.getTODORecordIssueList(shiroUser.getUser()));
		return ISSUE_RECORD_LIST;
	}
	
	/*
	 * =====================================
	 * UNDER WRITE
	 * =====================================
	 */
	@Log(message="下载了人核件数据", level=LogLevel.INFO, module=LogModule.QYGL)
	@RequiresPermissions("UnderWrite:view")
	@RequestMapping(value="/underwrite/toXls", method=RequestMethod.GET)
	public String toXls(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		
		List<Order> orders=new ArrayList<Order>();
		orders.add(new Order(Direction.ASC, "organization.orgCode"));
		orders.add(new Order(Direction.ASC, "holder"));
		page.setOrders(orders);
		
		//page.setOrderField("organization.orgCode");
		//page.setOrderDirection("ASC");
		page.setNumPerPage(65564);
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = user.getOrganization().getOrgCode();
		} else if(!orgCode.contains(user.getOrganization().getOrgCode())){
			orgCode = user.getOrganization().getOrgCode();
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE, orgCode));
		String status_flag = request.getParameter("status_flag");
		String status = request.getParameter("status");
		if(status_flag != null && status_flag.equals("null")) {
			csf.add(new SearchFilter("status", Operator.OR_EQ, UW_STATUS.NewStatus.name()));
			csf.add(new SearchFilter("status", Operator.OR_EQ, UW_STATUS.SendStatus.name()));
		} else if(status == null || status.trim().length()<=0) {
			csf.add(new SearchFilter("status", Operator.NEQ, UW_STATUS.DelStatus.name()));
		} else {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		Specification<UnderWrite> specification = DynamicSpecifications.bySearchFilter(request, UnderWrite.class, csf);
		List<UnderWrite> reqs = qyglService.findByUnderWriteExample(specification, page);

		map.put("reqs", reqs);
		return TO_XLS;
	}
	
	@RequiresPermissions("UnderWrite:save")
	@RequestMapping(value="/underwrite/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		UnderWrite underwrite = new UnderWrite();
		
		map.put("underwrite", underwrite);
		return UW_CREATE;
	}
	
	@Log(message="添加了{0}人核件信息。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions("UnderWrite:save")
	@RequestMapping(value="/underwrite/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid UnderWrite underwrite) {	
		User user = SecurityUtils.getShiroUser().getUser();
		try {
			UnderWrite uw = qyglService.getUnderWriteByFormNo(underwrite.getFormNo());
			if(uw != null && uw.getId()>0) {
				return AjaxObject.newError("该人核件信息已登记过了").toString();
			}
			underwrite.setStatus(UW_STATUS.NewStatus.name());
			underwrite.setDealMan(user.getRealname());
			qyglService.saveOrUpdateUnderWrite(underwrite);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加人核件信息失败：" + e.getMessage()).toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{underwrite.getFormNo()}));
		return AjaxObject.newOk("添加人核件信息成功！").toString();
	}
	
	@RequiresPermissions("UnderWrite:view")
	@RequestMapping(value="/underwrite/view/{id}", method=RequestMethod.GET)
	public String viewUnderWrite(@PathVariable Long id, Map<String, Object> map) {
		UnderWrite underwrite = qyglService.getUnderWrite(id);
		
		map.put("underwrite", underwrite);
		return UW_VIEW;
	}
	
	@RequiresPermissions("UnderWrite:edit")
	@RequestMapping(value="/underwrite/update/{id}", method=RequestMethod.GET)
	public String preUpdateUnderWrite(@PathVariable Long id, Map<String, Object> map) {
		UnderWrite underwrite = qyglService.getUnderWrite(id);
		
		map.put("underwrite", underwrite);
		return UW_UPDATE;
	}
	
	@Log(message="更新了{0}人核件的信息。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions("UnderWrite:edit")
	@RequestMapping(value="/underwrite/update", method=RequestMethod.POST)
	public @ResponseBody String updateUnderWrite(UnderWrite underwrite) {
		UnderWrite src = qyglService.getUnderWrite(underwrite.getId());
		src.setDealMan(underwrite.getDealMan());
		src.setIsLetter(underwrite.getIsLetter());
		src.setUnderwriteDate(underwrite.getUnderwriteDate());
		src.setSignDate(underwrite.getSignDate());
		if(underwrite.getPolicyNo() != null && underwrite.getPolicyNo().trim().length()>0) {
			src.setPolicyNo(underwrite.getPolicyNo().trim());
			src.setStatus(UW_STATUS.SendStatus.name());
		}
		src.setProvReceiveDate(underwrite.getProvReceiveDate());
		src.setProvEmsNo(underwrite.getProvEmsNo().trim());
		src.setFormNo(underwrite.getFormNo().trim());
		src.setHolder(underwrite.getHolder().trim());
		src.setInsured(underwrite.getInsured().trim());
		src.setRelation(underwrite.getRelation().trim());
		src.setUnderwriteReason(underwrite.getUnderwriteReason().trim());
		src.setPolicyFee(underwrite.getPolicyFee());
		src.setOrganization(underwrite.getOrganization());
		src.setHolderAge(underwrite.getHolderAge().trim());
		qyglService.saveOrUpdateUnderWrite(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getFormNo()}));
		return	AjaxObject.newOk("更新人核件成功！").toString(); 
	}
	
	@Log(message="作废了人核件{0}。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions("UnderWrite:edit")
	@RequestMapping(value="/underwrite/DelStatus/{id}", method=RequestMethod.POST)
	public @ResponseBody String delStatus(ServletRequest request, @PathVariable Long id) {
		UnderWrite uw = qyglService.getUnderWrite(id);
		uw.setStatus(UW_STATUS.DelStatus.name());
		AjaxObject ajaxObject = new AjaxObject();
		ajaxObject.setCallbackType("");
		ajaxObject.setMessage("作废" + uw.getFormNo() + "成功！");
		
		qyglService.saveOrUpdateUnderWrite(uw);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{uw.getFormNo()}));
		return ajaxObject.toString();
	}
	
	@Log(message="删除了{0}人核件记录申请。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions("UnderWrite:delete")
	@RequestMapping(value="/underwrite/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		String[] policys = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				UnderWrite issue = qyglService.getUnderWrite(ids[i]);
				qyglService.deleteUnderWrite(issue.getId());
				
				policys[i] = issue.getFormNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除人核件信息失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("成功删除人核件信息！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("UnderWrite:edit")
	@RequestMapping(value="/underwrite/signDateUpdate/{id}", method=RequestMethod.GET)
	public String preSignDateUpdate(@PathVariable Long id, Map<String, Object> map) {
		UnderWrite underwrite = qyglService.getUnderWrite(id);
		
		map.put("underwrite", underwrite);
		return UW_SIGNDATEUPDATE;
	}
		
		
	@Log(message="更新了{0}人核件的信息billBackDate。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions("UnderWrite:edit")
	@RequestMapping(value="/underwrite/signDateUpdateHadDeleted", method=RequestMethod.POST)
	public @ResponseBody String signDateUpdate(UnderWrite underwrite) {
		UnderWrite src = qyglService.getUnderWrite(underwrite.getId());
		src.setClientReceiveDate(underwrite.getClientReceiveDate());
		src.setBillBackDate(underwrite.getBillBackDate());
		src.setStatus(UW_STATUS.CloseStatus.name());
		qyglService.saveOrUpdateUnderWrite(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getFormNo()}));
		return	AjaxObject.newOk("更新人核件成功！").toString(); 
	}
	
	@RequiresPermissions(value={"UnderWrite:edit","UnderWrite:provEdit","UnderWrite:cityEdit","UnderWrite:areaEdit"}, logical=Logical.OR)
	@RequestMapping(value="/underwrite/{mailFlag}/{id}", method=RequestMethod.GET)
	public String preMailRecDateUpdate(@PathVariable String mailFlag, @PathVariable Long id, Map<String, Object> map) {
		UnderWrite underwrite = qyglService.getUnderWrite(id);
		
		map.put("underwrite", underwrite);
		map.put("mailFlag", mailFlag);
		if(mailFlag.contains("Send")) {
			switch(mailFlag) {
			case "provSend":
				underwrite.setSendDate(underwrite.getProvSendDate());
				underwrite.setEmsNo(underwrite.getProvEmsNo());
				break;
			case "citySend":
				underwrite.setSendDate(underwrite.getCitySendDate());
				underwrite.setEmsNo(underwrite.getCityEmsNo());
				break;
			case "areaSend":
				underwrite.setSendDate(underwrite.getAreaSendDate());
				underwrite.setEmsNo(underwrite.getAreaEmsNo());
				break;
			}
			return UW_MAIL_DATE;
		} else {
			switch(mailFlag) {
			case "provRec":
				underwrite.setReceiveDate(underwrite.getProvReceiveDate());
				break;
			case "cityRec":
				underwrite.setReceiveDate(underwrite.getCityReceiveDate());
				break;
			case "areaRec":
				underwrite.setReceiveDate(underwrite.getAreaReceiveDate());
				break;
			}
			return UW_REC_DATE;
		}
	}
		
	@Log(message="更新了{0}人核件的信息。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions(value={"UnderWrite:edit","UnderWrite:provEdit","UnderWrite:cityEdit","UnderWrite:areaEdit"}, logical=Logical.OR)
	@RequestMapping(value="/underwrite/sendRecUpdate", method=RequestMethod.POST)
	public @ResponseBody String mailDateUpdate(ServletRequest request, UnderWrite underwrite) {
		UnderWrite src = qyglService.getUnderWrite(underwrite.getId());
		String mailFlag = request.getParameter("mailFlag");
		switch(mailFlag) {
		case "provSend":
			src.setProvSendDate(underwrite.getSendDate());
			src.setProvEmsNo(underwrite.getEmsNo());
			break;
		case "citySend":
			src.setCitySendDate(underwrite.getSendDate());
			src.setCityEmsNo(underwrite.getEmsNo());
			break;
		case "areaSend":
			src.setAreaSendDate(underwrite.getSendDate());
			src.setAreaEmsNo(underwrite.getEmsNo());
			break;
		case "provRec":
			src.setProvReceiveDate(underwrite.getReceiveDate());
			break;
		case "cityRec":
			src.setCityReceiveDate(underwrite.getReceiveDate());
			break;
		case "areaRec":
			src.setAreaReceiveDate(underwrite.getReceiveDate());
			break;
			default:
				log.warn("-------------人核件的寄发标记缺失!");
				break;
		}
		
		qyglService.saveOrUpdateUnderWrite(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicyNo()}));
		return	AjaxObject.newOk("更新人核件成功！").toString(); 
	}
	
	@RequiresPermissions(value={"UnderWrite:edit","UnderWrite:provEdit","UnderWrite:cityEdit","UnderWrite:areaEdit"}, logical=Logical.OR)
	@RequestMapping(value="/underwrite/{mailFlag}", method=RequestMethod.GET)
	public String preManyMailRecDateUpdate(@PathVariable String mailFlag, String ids, Map<String, Object> map) {
		map.put("ids", ids);
		map.put("mailFlag", mailFlag);
		if(mailFlag.contains("Send")) {
			return UW_MAIL_DATES;
		} else {
			return UW_REC_DATES;
		}
	}
	
	@Log(message="批量更新了{0}人核件记录接收时间。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions(value={"UnderWrite:edit","UnderWrite:provEdit","UnderWrite:cityEdit","UnderWrite:areaEdit"}, logical=Logical.OR)
	@RequestMapping(value="/underwrite/manySendRecUpdate", method=RequestMethod.POST)
	public @ResponseBody String batchUpdateMailDate(ServletRequest request) {
		String sids = request.getParameter("ids");
		String[] ids = sids.split(",");
		String[] policys = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				UnderWrite src = qyglService.getUnderWrite(new Long(ids[i]));
				String mailFlag = request.getParameter("mailFlag");
				Date sendDate = StringUtil.str2Date(request.getParameter("sendDate"),"yyyy-MM-dd");
				String emsNo = request.getParameter("emsNo");
				String toNet = request.getParameter("toNet");
				boolean isToNet = false;
				if(toNet != null && toNet.equals("1")) {
					isToNet = true;
				}
				switch(mailFlag) {
				case "provSend":
					src.setProvSendDate(sendDate);
					src.setProvEmsNo(emsNo);
					src.setToNet(isToNet);
					break;
				case "citySend":
					src.setCitySendDate(sendDate);
					src.setCityEmsNo(emsNo);
					src.setToNet(isToNet);
					break;
				case "areaSend":
					src.setAreaSendDate(sendDate);
					src.setAreaEmsNo(emsNo);
					src.setToNet(isToNet);
					break;
				case "provRec":
					src.setProvReceiveDate(sendDate);
					break;
				case "cityRec":
					src.setCityReceiveDate(sendDate);
					break;
				case "areaRec":
					src.setAreaReceiveDate(sendDate);
					break;
					default:
						log.warn("-------------人核件的寄发标记缺失!");
						break;
				}
				
				qyglService.saveOrUpdateUnderWrite(src);
				
				policys[i] = src.getFormNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("更新人核件信息失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("成功更新人核件信息！").toString();
	}
	
	@RequiresPermissions("UnderWrite:view")
	@RequestMapping(value="/underwrite/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listUnderWrite(HttpServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		//默认返回未处理工单
		String status = request.getParameter("status");
		String status_flag = request.getParameter("status_flag");
		
		boolean isEmpty = false;
		boolean isNull = false;
		if(status == null || (status != null && status.trim().length()<=0 && status_flag!=null && status_flag.equals("null"))) {
			isNull = true;
			//status = UW_STATUS.NewStatus.name();
			if(orgCode.length()>4) {
				//status = UW_STATUS.SendStatus.name();
			}
			request.setAttribute("status", status);
			request.setAttribute("status_flag", "null");
		} else {
			request.setAttribute("status_flag", status);
			if(status.trim().length()<=0) {
				isEmpty = true;
			}
		}
		UnderWrite uw = new UnderWrite();
		uw.setStatus(status);
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("ybtDate");
			page.setOrderDirection("ASC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE, orgCode));
		if(isNull) {
			csf.add(new SearchFilter("status", Operator.OR_EQ, UW_STATUS.NewStatus.name()));
			csf.add(new SearchFilter("status", Operator.OR_EQ, UW_STATUS.SendStatus.name()));
		} else if(isEmpty) {
			csf.add(new SearchFilter("status", Operator.NEQ, UW_STATUS.DelStatus.name()));
		} else {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		Specification<UnderWrite> specification = DynamicSpecifications.bySearchFilter(request, UnderWrite.class, csf);
		
		List<UnderWrite> underwrites = qyglService.findByUnderWriteExample(specification, page);
		
		map.put("UWStatusList", UW_STATUS.values());
		map.put("underwrite", uw);
		map.put("status", status);
		map.put("page", page);
		map.put("underwrites", underwrites);
		
		Integer odc = qyglService.getOverdueUWCount(request, user);
		Integer odp = qyglService.getOverdueUWCall(request, user);
		Integer odw = qyglService.getOverdueUWWeixin(request, user);
		String ods = odc + "|" + odp + "|" + odw;
		//String rst = "<a href=\"/qygl/overdueList\">共有" + list.get(0) + "件人核件超时处理，请查看</a>";
		request.setAttribute("noticeMsg", ods);
		log.debug(" ------------------ " + ods);
		
		return UW_LIST;
	}
	
	@RequiresPermissions(value={"UnderWrite:edit","UnderWrite:provEdit","UnderWrite:cityEdit","UnderWrite:areaEdit"}, logical=Logical.OR)
	@RequestMapping(value="/underwrite/plan/{id}", method=RequestMethod.GET)
	public String preUwPlan(@PathVariable Long id, Map<String, Object> map) {
		map.put("id", id);
		UnderWrite uw = qyglService.getUnderWrite(id);
		map.put("UnderWrite", uw);
		return UW_PLAN;
	}
	
	@Log(message="设置了{0}人核件的跟进信息。", level=LogLevel.WARN, module=LogModule.QYGL)
	@RequiresPermissions("UnderWrite:edit")
	@RequestMapping(value="/underwrite/plan", method=RequestMethod.POST)
	public @ResponseBody String planUnderWrite(UnderWrite underwrite) {
		UnderWrite src = qyglService.getUnderWrite(underwrite.getId());
		src.setPlanDate(underwrite.getPlanDate());
		if(underwrite.getPlanDate() == null || underwrite.getPlanDate().before(new Date())) {
			src.setPlanFlag(false);
		} else {
			src.setPlanFlag(true);
		}
		if(underwrite.getClientReceiveDate() != null) {
			src.setClientReceiveDate(underwrite.getClientReceiveDate());
			src.setPlanFlag(false);
			src.setStatus(UW_STATUS.CloseStatus.name());
		}
		src.setRemark(underwrite.getRemark());
		qyglService.saveOrUpdateUnderWrite(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getFormNo()}));
		return	AjaxObject.newOk("成功设置人核件跟进信息！").toString(); 
	}
	
	@RequiresPermissions("PlanList:view")
	@RequestMapping(value="/underwrite/planList", method={RequestMethod.GET, RequestMethod.POST})
	public String listUwPlan(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("planDate");
			page.setOrderDirection("ASC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE, orgCode));
		csf.add(new SearchFilter("status", Operator.OR_EQ, UW_STATUS.SendStatus.name()));
		csf.add(new SearchFilter("status", Operator.OR_EQ, UW_STATUS.NewStatus.name()));
		csf.add(new SearchFilter("planFlag", Operator.EQ, 1));
		
		Specification<UnderWrite> specification = DynamicSpecifications.bySearchFilter(request, UnderWrite.class, csf);
		
		List<UnderWrite> underwrites = qyglService.findByUnderWriteExample(specification, page);
		
		map.put("underwrites", underwrites);
		map.put("page", page);
		
		return UW_PLAN_LIST;
	}
	
	@RequiresPermissions("UnderWrite:view")
	@RequestMapping(value="/uwlist2pop", method={RequestMethod.GET, RequestMethod.POST})
	public String underWriteList2Pop(Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put("underwriteList", qyglService.getOverdueUWList2Pop(shiroUser.getUser(), page));
		map.put("page", page);
		return UW_POP;
	}
	
	@RequiresPermissions("UnderWrite:view")
	@RequestMapping(value="/uwlist2call", method={RequestMethod.GET, RequestMethod.POST})
	public String underWriteList2Call(Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put("underwriteList", qyglService.getOverdueUWList2Call(shiroUser.getUser(), page));
		map.put("page", page);
		return UW_CALL;
	}
	
	@RequiresPermissions("UnderWrite:view")
	@RequestMapping(value="/uwlist2weixin", method={RequestMethod.GET, RequestMethod.POST})
	public String underWriteList2Weixin(Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put("underwriteList", qyglService.getOverdueUWList2Weixin(shiroUser.getUser(), page));
		map.put("page", page);
		return UW_WEIXIN;
	}
	
	@RequiresPermissions("UnderWrite:view")
	@RequestMapping(value="/underwritelist", method={RequestMethod.GET, RequestMethod.POST})
	public String underWriteList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put("underwriteList", qyglService.getTODOUnderWriteList(shiroUser.getUser()));
		map.put("page", page);
		return UNDERWRITE_LIST;
	}
	
	@RequiresPermissions("YBT:view")
	@RequestMapping(value="/ybt/list", method={RequestMethod.GET, RequestMethod.POST})
	public String ybtList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		
		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date2");
		if(date1 == null || date1.trim().length()<=0) {
			date1 = StringUtil.date2Str(StringUtil.dateAdd(new Date(), -15), "yyyy-MM-dd");
		}
		request.setAttribute("date1", date1);
		if(date2 == null || date2.trim().length()<=0) {
			date2 = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		}
		request.setAttribute("date2", date2);
		List<YbtPolicyModel> ybts = qyglService.listYBTPolicys(orgCode + "%", date1, date2, page);
		
		map.put("ybtPolicys", ybts);
		map.put("page", page);
		
		return YBT_LIST;
	}
	
	// 使用初始化绑定器, 将参数自动转化为日期类型,即所有日期类型的数据都能自动转化为yyyy-MM-dd格式的Date类型
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
