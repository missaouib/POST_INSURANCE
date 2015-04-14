/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.sendtend.web.controller.IndexController.java
 * Class:			IndexController
 * Date:			2012-8-2
 * Author:			sendtend
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.sendtend.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sendtend.utils.SecurityUtils;
import com.sendtend.web.SecurityConstants;
import com.sendtend.web.entity.main.Module;
import com.sendtend.web.entity.main.Permission;
import com.sendtend.web.entity.main.User;
import com.sendtend.web.exception.ServiceException;
import com.sendtend.web.log.Log;
import com.sendtend.web.log.LogMessageObject;
import com.sendtend.web.log.impl.LogUitls;
import com.sendtend.web.service.ModuleService;
import com.sendtend.web.service.OrganizationService;
import com.sendtend.web.service.UserService;
import com.sendtend.web.shiro.ShiroUser;
import com.sendtend.web.util.dwz.AjaxObject;

/** 
 * 	
 * @author 	sendtend
 * Version  1.1.0
 * @since   2012-8-2 下午5:45:57 
 */
@Controller
@RequestMapping("/management/index")
public class IndexController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private OrganizationService organizationService;
	
	private static final String WEBINDEX = "index";
	private static final String INDEX = "management/index/index";
	private static final String UPDATE_PASSWORD = "management/index/updatePwd";
	private static final String UPDATE_BASE = "management/index/updateBase";
	
	@Log(message="{0}登录了系统。")
	@RequiresUser 
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index(HttpServletRequest request, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		
		Module menuModule = getMenuModule(SecurityUtils.getSubject());

		map.put(SecurityConstants.LOGIN_USER, shiroUser.getUser());
		map.put("menuModule", menuModule);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getLoginName()}));
		
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
