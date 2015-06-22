/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.controller.LoginController.java
 * Class:			LoginController
 * Date:			2012-8-2
 * Author:			sendtend
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.gdpost.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.Exceptions;
import com.gdpost.web.log.Log;
import com.gdpost.web.shiro.IncorrectCaptchaException;
import com.gdpost.web.util.dwz.AjaxObject;

/**
 * 
 * @author sendtend Version 1.1.0
 * @since 2012-8-2 下午5:29:01
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
	
	private static final String LOGIN_PAGE = "login";
	private static final String WEBINDEX = "index";
	private static final String WEB_LOGIN_PAGE = "weblogin";
	//private static final String WEB_LOGIN_PAGE = "login";
	private static final String LOGIN_DIALOG = "management/index/loginDialog";

	@RequestMapping(method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		String userType = request.getParameter("userType");
		if(userType == null) {
			userType = "member";
		}
		String url = request.getRequestURI();
		String ref = request.getHeader("Referer");
		String tag = request.getParameter("t");
		
//		LOG.debug("----------req url:" + url);
//		LOG.debug("------------login referer:" + ref);
		
		if(url != null && url.equals("/web/index")) {
			//LOG.debug("------------111-----------");
			try {
				response.sendRedirect("/login");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		} else if(url != null && url.equals("/login") && tag != null && tag.equals("web")) {
			//LOG.debug("------------2222-----------");
			return LOGIN_PAGE;
		} else if(url == null || url.equals("/")){
			//LOG.debug("------------333-----------");
			return WEBINDEX;
		}
		
		if(ref != null && (ref.indexOf("web") != -1 || ref.indexOf("toMsgCreate") != -1 || ref.indexOf("toWebUpload") != -1 || ref.indexOf("toWebList") != -1
				|| ref.indexOf("web/index") != -1)) {
			//LOG.debug("------------444-----------");
			try {
				response.sendRedirect("/web/tologin");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		} else if(ref == null) {
			//LOG.debug("------------555-----------");
			return WEBINDEX;
		}
		LOG.debug("------------last-----------");
		if(ref != null && ref.endsWith("/login") && url.equals("/login")) {
			return WEB_LOGIN_PAGE;
		}
		request.setAttribute("userType", userType);
		return WEBINDEX;
		//return LOGIN_PAGE;
	}
	
	@RequestMapping(method = {RequestMethod.GET}, params="ajax=true")
	public @ResponseBody String loginDialog2AJAX() {
		return loginDialog();
	}
		
	@RequestMapping(method = {RequestMethod.GET}, headers = "X-Requested-With=XMLHttpRequest")
	public @ResponseBody String loginDialog() {
		return AjaxObject.newTimeout("会话超时，请重新登录。").toString();
	}

	@RequestMapping(value = "/timeout", method = { RequestMethod.GET })
	public String timeout(ServletRequest request) {
		String userType = request.getParameter("userType");
		String flag = request.getParameter("flag");
		if(userType != null && userType.equals("admin")) {
			request.setAttribute("userType", "admin");
		} else {
			request.setAttribute("userType", "member");
		}
		if(flag != null && flag.trim().equals("web")) {
			return WEB_LOGIN_PAGE;
		}
		return LOGIN_DIALOG;
	}

	@Log(message="会话超时， 该用户重新登录系统。")
	@RequestMapping(value = "/timeout/success", method = {RequestMethod.GET})
	public @ResponseBody String timeoutSuccess() {
		return AjaxObject.newOk("登录成功。").toString();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(
			@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username,
			Map<String, Object> map, ServletRequest request, HttpServletResponse response) {
		String userType = request.getParameter("userType");
		String flag = request.getParameter("flag");
		if(userType != null && userType.equals("admin")) {
			request.setAttribute("userType", "admin");
		} else {
			request.setAttribute("userType", "member");
		}
		String msg = parseException(request);
		
		map.put("msg", msg);
		map.put("username", username);
		if(flag != null && flag.endsWith("web")) {
			//return WEB_LOGIN_PAGE;
			try {
				response.sendRedirect("/web/tologin?code=" + errMsg2Code(msg));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		return LOGIN_PAGE;
	}

	@RequestMapping(method = {RequestMethod.POST}, headers = "x-requested-with=XMLHttpRequest")
	public @ResponseBody String failDialog(ServletRequest request) {
		String msg = parseException(request);
		
		AjaxObject ajaxObject = new AjaxObject(msg);
		ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
		ajaxObject.setCallbackType("");

		return ajaxObject.toString();
	}

	private String parseException(ServletRequest request) {
		String errorString = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		Class<?> error = null;
		try {
			if (errorString != null) {
				error = Class.forName(errorString);
			}
		} catch (ClassNotFoundException e) {
			LOG.error(Exceptions.getStackTraceAsString(e));
		} 
		
		String msg = "其他错误！";
		if (error != null) {
			if (error.equals(UnknownAccountException.class))
				msg = "未知帐号错误！";
			else if (error.equals(IncorrectCredentialsException.class))
				msg = "密码错误！";
			else if (error.equals(IncorrectCaptchaException.class))
				msg = "验证码错误！";
			else if (error.equals(AuthenticationException.class))
				msg = "认证失败！";
			else if (error.equals(DisabledAccountException.class))
				msg = "账号被冻结！";
		}

		return "登录失败，" + msg;
	}
	
	public static String converErrCode(int code) {
		switch(code) {
		case 1:
			return "登录失败，未知帐号错误！";
		case 2:
			return "登录失败，密码错误！";
		case 3:
			return "登录失败，验证码错误！";
		case 4:
			return "登录失败，认证失败！";
		case 5:
			return "登录失败，账号被冻结！";
		default:
			return "登录失败，其他错误！";
		}
	}
	
	public static int errMsg2Code(String msg) {
		if(msg.equalsIgnoreCase("登录失败，未知帐号错误！")) {
			return 1;
		} else if(msg.equalsIgnoreCase("登录失败，密码错误！")) {
			return 2;
		} else if(msg.equalsIgnoreCase("登录失败，验证码错误！")) {
			return 3;
		} else if(msg.equalsIgnoreCase("登录失败，认证失败！")) {
			return 4;
		} else if(msg.equalsIgnoreCase("登录失败，账号被冻结！")) {
			return 5;
		} else {
			return 6;
		}
	}
}
