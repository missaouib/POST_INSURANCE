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

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdpost.web.log.Log;

/** 
 * 	
 * @author 	sendtend
 * Version  1.1.0
 * @since   2012-8-2 下午5:45:57 
 */
@Controller
@RequestMapping("/web")
public class WebIndexController {
	
	//private static final Logger LOG = LoggerFactory.getLogger(WebIndexController.class); 
	
	private static final String INDEX = "index";
	private static final String WEBLOGIN = "weblogin";
	private static final String TODO_LIST = "insurance/web/todoList";
	private static final String PRD_LIST = "insurance/web/prdList";
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index(ServletRequest request, Map<String, Object> map) {
		return INDEX;
	}
	
	@RequestMapping(value="/prdList", method=RequestMethod.GET)
	public String prdList(ServletRequest request, Map<String, Object> map) {
		return PRD_LIST;
	}
	
	@RequestMapping(value="/todoList", method=RequestMethod.GET)
	public String todoList(ServletRequest request, Map<String, Object> map) {
		return TODO_LIST;
	}
	
	@RequestMapping(value="/tologin", method={RequestMethod.GET, RequestMethod.POST})
	public String login(HttpServletRequest request, Map<String, Object> map) {
		String code = request.getParameter("code");
		if(code != null) {
			map.put("msg", LoginController.converErrCode(new Integer(code)));
			request.setAttribute("msg",  LoginController.converErrCode(new Integer(code)));
		}
		return WEBLOGIN;
	}

	@Log(message="会话超时， 该用户重新登录系统。")
	@RequestMapping(value = "/logout", method = {RequestMethod.GET})
	public void webLogout(HttpServletResponse response) {
		Subject user = SecurityUtils.getSubject();
		user.logout();
		try {
			response.sendRedirect("/web/index");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

}
