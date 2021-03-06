/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.controller.IndexController.java
 * Class:			IndexController
 * Date:			2012-8-2
 * Author:			Aming
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.gdpost.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.SecurityConstants;
import com.gdpost.web.entity.insurance.ConservationDtl;
import com.gdpost.web.entity.insurance.RenewedList;
import com.gdpost.web.entity.main.Module;
import com.gdpost.web.entity.main.Notice;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.Permission;
import com.gdpost.web.entity.main.Role;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.entity.main.UserRole;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.ModuleService;
import com.gdpost.web.service.UserRoleService;
import com.gdpost.web.service.UserService;
import com.gdpost.web.service.insurance.HfglService;
import com.gdpost.web.service.insurance.KfglService;
import com.gdpost.web.service.insurance.NoticeService;
import com.gdpost.web.service.insurance.PayListService;
import com.gdpost.web.service.insurance.QyglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;

/** 
 * 	
 * @author 	Aming
 * Version  1.1.0
 * @since   2012-8-2 ??????5:45:57 
 */
@Controller
@RequestMapping("/management/index")
public class IndexController {
	private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private KfglService kfglService;
	
//	@Autowired
//	private BqglService bqglService;
	
	@Autowired
	private QyglService qyglService;
	
//	@Autowired
//	private XqglService xqglService;
	
	@Autowired
	private HfglService hfglService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private PayListService paylistService;
	
	@Autowired
	private NoticeService noticeService;
	
	private static final String WEBINDEX = "index";
	private static final String INDEX = "management/index/index";
	private static final String UPDATE_PASSWORD = "management/index/updatePwd";
	private static final String UPDATE_BASE = "management/index/updateBase";
	
	private static final String TODO_LIST = "insurance/todo/todolist";
	private static final String PANEL_TODO_LIST = "insurance/todo/todopanel";
	
	private static final String VIEW_UPDATE_LOG = "updateLog";
	
	@Log(message="{0}??????????????????", level=LogLevel.TRACE, module=LogModule.QTCZ)
	@RequiresUser 
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index(HttpServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		
		Module menuModule = getMenuModule(SecurityUtils.getSubject());

		map.put(SecurityConstants.LOGIN_USER, shiroUser.getUser());
		map.put("menuModule", menuModule);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getUser().getRealname()}));
		
		LOG.debug(" ----------- INDEX to get the task");
		map.put("issueList", kfglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("bqIssueList", new ArrayList<ConservationDtl>());//bqglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("checkWriteIssueList", qyglService.getTODOWriteIssueList(shiroUser.getUser()));
		
		map.put("checkRecordIssueList", qyglService.getTODORecordIssueList(shiroUser.getUser()));
		
		map.put("xqIssueList", new ArrayList<RenewedList>());//xqglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("hfIssueList", hfglService.getTODOIssueList(shiroUser.getUser()));
		
		try {
			map.put("underwriteList", qyglService.getTODOUnderWriteList(shiroUser.getUser()));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		map.put("bqtofaillist", paylistService.getBQToFailListTODOIssueList(shiroUser.getUser()));
		
		map.put("bqfromfaillist", paylistService.getBQFromFailListTODOIssueList(shiroUser.getUser()));
		
		map.put("xqfromfaillist", paylistService.getXQFromFailListTODOIssueList(shiroUser.getUser()));
		
		map.put("qyfromfaillist", paylistService.getQYFromFailListTODOIssueList(shiroUser.getUser()));
		
		map.put("lptofaillist", paylistService.getLPToFailListTODOIssueList(shiroUser.getUser()));
		
		User user = shiroUser.getUser();
		Organization org = user.getOrganization();
		List<UserRole> urs = userRoleService.findByUserId(user.getId());
		List<Role> roles = new ArrayList<Role>();
		UserRole ur = null;
		for(int i=0; i<urs.size(); i++) {
			ur = urs.get(i);
			roles.add(ur.getRole());
		}
		request.getSession().setAttribute(SecurityConstants.LOGIN_USER_ROLE, roles);
		page.setNumPerPage(3);
		page.setOrderDirection("DESC");
		page.setOrderField("sendDate");
		List<Notice> basedata = noticeService.findByOwnNoticeList(page, "%" + org.getOrgCode() + "%", roles, user, user);
		StringBuffer msg = new StringBuffer("?????????????????????");
		boolean hasNotice = false;
		for(Notice n:basedata) {
			hasNotice = true;
			msg.append(n.getNoticeTitle() + ";&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		if(!hasNotice) {
			msg = new StringBuffer("???????????????????????????");
		} else {
			msg.append("...");
		}
		request.setAttribute("noticeMsg", msg);
		
		if(shiroUser.getUserType().equals("web")) {
			return WEBINDEX;
		}
		return INDEX;
	}
	
	private Module getMenuModule(Subject subject) {
		Module rootModule = moduleService.getTree();

		check(rootModule, subject);
		return rootModule;
	}
	
	private void check(Module module, Subject subject) {
		List<Module> list1 = new ArrayList<Module>();
		for (Module m1 : module.getChildren()) {
			// ???????????????show?????????Module
			if (subject.isPermitted(m1.getSn() + ":" + Permission.PERMISSION_SHOW)) {
				check(m1, subject);
				list1.add(m1);
			}
		}
		module.setChildren(list1);
	}
	
	@RequiresUser
	@RequestMapping(value="/updatePwd", method=RequestMethod.GET)
	public String preUpdatePassword() {
		return UPDATE_PASSWORD;
	}
	
	@RequiresPermissions("TODO:view")
	@RequestMapping(value="/todolist", method={RequestMethod.GET, RequestMethod.POST})
	public String todoList(HttpServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		LOG.debug(" ----------- INDEX to get the task");
		map.put("issueList", kfglService.getTODOIssueList(shiroUser.getUser()));

		return TODO_LIST;
	}
	
	@RequiresPermissions("PANELTODO:view")
	@RequestMapping(value="/paneltodolist", method={RequestMethod.GET, RequestMethod.POST})
	public String panelTodoList(HttpServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put(SecurityConstants.LOGIN_USER, shiroUser.getUser());
		LOG.debug(" ----------- INDEX to get the task");
		map.put("issueList", kfglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("bqIssueList", new ArrayList<ConservationDtl>());//bqglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("checkWriteIssueList", qyglService.getTODOWriteIssueList(shiroUser.getUser()));
		
		map.put("checkRecordIssueList", qyglService.getTODORecordIssueList(shiroUser.getUser()));
		
		map.put("xqIssueList", new ArrayList<RenewedList>());//xqglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("hfIssueList", hfglService.getTODOIssueList(shiroUser.getUser()));
		
		try {
			map.put("underwriteList", qyglService.getTODOUnderWriteList(shiroUser.getUser()));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		map.put("bqtofaillist", paylistService.getBQToFailListTODOIssueList(shiroUser.getUser()));
		
		map.put("bqfromfaillist", paylistService.getBQFromFailListTODOIssueList(shiroUser.getUser()));
		
		map.put("xqfromfaillist", paylistService.getXQFromFailListTODOIssueList(shiroUser.getUser()));
		
		map.put("qyfromfaillist", paylistService.getQYFromFailListTODOIssueList(shiroUser.getUser()));
		
		map.put("lptofaillist", paylistService.getLPToFailListTODOIssueList(shiroUser.getUser()));
		
		LOG.debug("------------- map: " + map);
		
		return PANEL_TODO_LIST;
	}
	
	@Log(message="{0}??????????????????", level=LogLevel.WARN, module=LogModule.QTCZ)
	@RequiresUser
	@RequestMapping(value="/updatePwd", method=RequestMethod.POST)
	public @ResponseBody String updatePassword(ServletRequest request, String plainPassword, 
			String newPassword, String rPassword) {
		User user = (User) SecurityUtils.getLoginUser();
		
		if (newPassword != null && newPassword.equals(rPassword)) {
			user.setPlainPassword(plainPassword);
			try {
				userService.updatePwd(user, newPassword);
			} catch (ServiceException e) {
				LogUitls.putArgs(LogMessageObject.newIgnore());//????????????
				return AjaxObject.newError(e.getMessage()).setCallbackType("").toString();
			}
			LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername()}));
			return AjaxObject.newOk("?????????????????????").toString();
		}
		
		return AjaxObject.newError("?????????????????????").setCallbackType("").toString();
	}
	
	@RequiresUser
	@RequestMapping(value="/updateBase", method=RequestMethod.GET)
	public String preUpdateBase(Map<String, Object> map) {
		map.put(SecurityConstants.LOGIN_USER, SecurityUtils.getLoginUser());
		return UPDATE_BASE;
	}
	
	@Log(message="{0}??????????????????????????????", level=LogLevel.WARN, module=LogModule.QTCZ)
	@RequiresUser
	@RequestMapping(value="/updateBase", method=RequestMethod.POST)
	public @ResponseBody String updateBase(User user, ServletRequest request) {
		User loginUser = (User) SecurityUtils.getLoginUser();
		
		loginUser.setPhone(user.getPhone());
		loginUser.setEmail(user.getEmail());
		loginUser.setRealname(user.getRealname());

		userService.saveOrUpdate(loginUser);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername()}));
		return AjaxObject.newOk("???????????????????????????").toString();
	}
	
	@RequestMapping(value="/updateLog", method=RequestMethod.GET)
	public String vewUpdateLog(Map<String, Object> map) {
		return VIEW_UPDATE_LOG;
	}
}
