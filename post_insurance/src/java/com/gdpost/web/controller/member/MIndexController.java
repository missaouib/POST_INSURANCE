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
 
package com.gdpost.web.controller.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

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
import com.gdpost.web.entity.member.TblMemberModule;
import com.gdpost.web.entity.member.TblMemberPermission;
import com.gdpost.web.entity.member.TblMemberUser;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.member.MModuleService;
import com.gdpost.web.service.member.MUserService;
import com.gdpost.web.service.member.MemberService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.AjaxObject;

/** 
 * 	
 * @author 	sendtend
 * Version  1.1.0
 * @since   2012-8-2 下午5:45:57 
 */
@Controller
@RequestMapping("/members/index")
public class MIndexController {
	private static final Logger log = LoggerFactory.getLogger(MIndexController.class);
	
	@Autowired
	private MUserService userService;
	
	@Autowired
	private MModuleService moduleService;
	
	@Autowired
	private MemberService memberService;
	
	private static final String INDEX = "members/index/index";
	private static final String UPDATE_PASSWORD = "members/index/updatePwd";
	private static final String UPDATE_BASE = "members/index/updateBase";
	
	@Log(message="{0}登录了系统。")
	@RequiresUser 
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index(ServletRequest request, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		log.debug("--------000000----------" );
		TblMemberModule menuTblMemberModule = getMenuTblMemberModule(SecurityUtils.getSubject());
		
		map.put(SecurityConstants.LOGIN_USER, shiroUser.getMemberUser());
		map.put("menuTblMemberModule", menuTblMemberModule);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getLoginName()}));
		return INDEX;
	}
	
	private TblMemberModule getMenuTblMemberModule(Subject subject) {
		TblMemberModule rootTblMemberModule = moduleService.getTree();
		log.debug("--------1111----------" + rootTblMemberModule.toString());
		log.debug("--------2222----------" + (subject==null?"subject is null":"not null"));
		check(rootTblMemberModule, subject);
		return rootTblMemberModule;
	}
	
	private void check(TblMemberModule module, Subject subject) {
		List<TblMemberModule> list1 = new ArrayList<TblMemberModule>();
		for (TblMemberModule m1 : module.getChildren()) {
			// 只加入拥有show权限的TblMemberModule
			if (subject.isPermitted(m1.getSn() + ":" + TblMemberPermission.PERMISSION_SHOW)) {
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
		TblMemberUser user = (TblMemberUser) SecurityUtils.getLoginUser();
		
		if (newPassword != null && newPassword.equals(rPassword)) {
			user.setPlainPassword(plainPassword);
			try {
				userService.updatePwd(user, newPassword);
			} catch (ServiceException e) {
				LogUitls.putArgs(LogMessageObject.newIgnore());//忽略日志
				return AjaxObject.newError(e.getMessage()).setCallbackType("").toString();
			}
			LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUserName()}));
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
	public @ResponseBody String updateBase(TblMemberUser user, ServletRequest request) {
		TblMemberUser loginUser = (TblMemberUser) SecurityUtils.getLoginUser();
		
		loginUser.setPhone(user.getPhone());
		loginUser.setEmail(user.getEmail());
		loginUser.setRealName(user.getRealName());

		userService.saveOrUpdate(loginUser);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUserName()}));
		return AjaxObject.newOk("修改详细信息成功！").toString();
	}
}
