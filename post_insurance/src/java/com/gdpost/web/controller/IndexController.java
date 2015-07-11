/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.controller.IndexController.java
 * Class:			IndexController
 * Date:			2012-8-2
 * Author:			sendtend
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
import com.gdpost.web.entity.main.Module;
import com.gdpost.web.entity.main.Permission;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.ModuleService;
import com.gdpost.web.service.OrganizationService;
import com.gdpost.web.service.UserService;
import com.gdpost.web.service.insurance.BqglService;
import com.gdpost.web.service.insurance.HfglService;
import com.gdpost.web.service.insurance.KfglService;
import com.gdpost.web.service.insurance.QyglService;
import com.gdpost.web.service.insurance.XqglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;

/** 
 * 	
 * @author 	sendtend
 * Version  1.1.0
 * @since   2012-8-2 下午5:45:57 
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
	private OrganizationService organizationService;
	
	@Autowired
	private KfglService kfglService;
	
	@Autowired
	private BqglService bqglService;
	
	@Autowired
	private QyglService qyglService;
	
	@Autowired
	private XqglService xqglService;
	
	@Autowired
	private HfglService hfglService;
	
	private static final String WEBINDEX = "index";
	private static final String INDEX = "management/index/index";
	private static final String UPDATE_PASSWORD = "management/index/updatePwd";
	private static final String UPDATE_BASE = "management/index/updateBase";
	
	private static final String TODO_LIST = "insurance/todo/todolist";
	private static final String PANEL_TODO_LIST = "insurance/todo/todopanel";
	
	@Log(message="{0}登录了系统。")
	@RequiresUser 
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index(HttpServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		
		Module menuModule = getMenuModule(SecurityUtils.getSubject());

		map.put(SecurityConstants.LOGIN_USER, shiroUser.getUser());
		map.put("menuModule", menuModule);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getLoginName()}));
		
		LOG.debug(" ----------- INDEX to get the task");
		map.put("issueList", kfglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("bqIssueList", bqglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("checkWriteIssueList", qyglService.getTODOWriteIssueList(shiroUser.getUser()));
		
		map.put("checkRecordIssueList", qyglService.getTODORecordIssueList(shiroUser.getUser()));
		
		map.put("xqIssueList", xqglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("hfIssueList", hfglService.getTODOIssueList(shiroUser.getUser()));
		
		if(shiroUser.getUserType().equals("member")) {
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
			// 只加入拥有show权限的Module
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
	@RequestMapping(value="/todolist", method=RequestMethod.GET)
	public String todoList(HttpServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		LOG.debug(" ----------- INDEX to get the task");
		map.put("issueList", kfglService.getTODOIssueList(shiroUser.getUser()));

		return TODO_LIST;
	}
	
	@RequiresPermissions("PANELTODO:view")
	@RequestMapping(value="/paneltodolist", method=RequestMethod.GET)
	public String panelTodoList(HttpServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put(SecurityConstants.LOGIN_USER, shiroUser.getUser());
		LOG.debug(" ----------- INDEX to get the task");
		map.put("issueList", kfglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("bqIssueList", bqglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("checkWriteIssueList", qyglService.getTODOWriteIssueList(shiroUser.getUser()));
		
		map.put("checkRecordIssueList", qyglService.getTODORecordIssueList(shiroUser.getUser()));
		
		map.put("xqIssueList", xqglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("hfIssueList", hfglService.getTODOIssueList(shiroUser.getUser()));
		
		LOG.debug("------------- map: " + map);
		
		return PANEL_TODO_LIST;
	}
	
	@Log(message="{0}修改了密码。")
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
				LogUitls.putArgs(LogMessageObject.newIgnore());//忽略日志
				return AjaxObject.newError(e.getMessage()).setCallbackType("").toString();
			}
			LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername()}));
			return AjaxObject.newOk("修改密码成功！").toString();
		}
		
		return AjaxObject.newError("修改密码失败！").setCallbackType("").toString();
	}
	
	@RequiresUser
	@RequestMapping(value="/updateBase", method=RequestMethod.GET)
	public String preUpdateBase(Map<String, Object> map) {
		map.put(SecurityConstants.LOGIN_USER, SecurityUtils.getLoginUser());
		return UPDATE_BASE;
	}
	
	@Log(message="{0}修改了详细信息。")
	@RequiresUser
	@RequestMapping(value="/updateBase", method=RequestMethod.POST)
	public @ResponseBody String updateBase(User user, ServletRequest request) {
		User loginUser = (User) SecurityUtils.getLoginUser();
		
		loginUser.setPhone(user.getPhone());
		loginUser.setEmail(user.getEmail());
		loginUser.setRealname(user.getRealname());

		userService.saveOrUpdate(loginUser);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername()}));
		return AjaxObject.newOk("修改详细信息成功！").toString();
	}
}
