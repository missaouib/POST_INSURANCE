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

import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.SecurityConstants;
import com.gdpost.web.log.Log;
import com.gdpost.web.service.insurance.BqglService;
import com.gdpost.web.service.insurance.HfglService;
import com.gdpost.web.service.insurance.KfglService;
import com.gdpost.web.service.insurance.QyglService;
import com.gdpost.web.service.insurance.XqglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.Page;

/** 
 * 	
 * @author 	sendtend
 * Version  1.1.0
 * @since   2012-8-2 下午5:45:57 
 */
@Controller
@RequestMapping("/web")
public class WebIndexController {
	
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
	
	private static final Logger LOG = LoggerFactory.getLogger(WebIndexController.class); 
	
	private static final String INDEX = "index";
	private static final String WEBLOGIN = "weblogin";
	private static final String TODO_LIST = "insurance/web/todoList";
	private static final String PRD_LIST = "insurance/web/prdList";
	private static final String FFY_1 = "insurance/web/prd/ffy1";
	private static final String FFY_3 = "insurance/web/prd/ffy3";
	private static final String BBB = "insurance/web/prd/bbb";
	private static final String DDB = "insurance/web/prd/ddb";
	private static final String NB = "insurance/web/prd/nb";
	private static final String NB_PLUS = "insurance/web/prd/nbplus";
	
	private static final String FFY1_YB = "insurance/web/prd/ffy1_yb";
	private static final String FFY12_YB = "insurance/web/prd/ffy12_yb";
	private static final String FFY1_SELL = "insurance/web/prd/www/sell/fu1";
	private static final String FFY3_YB = "insurance/web/prd/ffy3_yb";
	private static final String FFY32_YB = "insurance/web/prd/ffy32_yb";
	private static final String FFY3_SELL = "insurance/web/prd/www/sell/fu3";
	private static final String BBB_YB = "insurance/web/prd/bbb_yb";
	private static final String BBB2_YB = "insurance/web/prd/bbb2_yb";
	private static final String BBB_SELL = "insurance/web/prd/www/sell/nian_bbb";
	private static final String DDB_YB = "insurance/web/prd/ddb_yb";
	private static final String DDB2_YB = "insurance/web/prd/ddb2_yb";
	private static final String DDB_SELL = "insurance/web/prd/www/sell/ddb";
	private static final String NB_YB = "insurance/web/prd/nb_yb";
	private static final String NB2_YB = "insurance/web/prd/nb2_yb";
	private static final String NB_SELL = "insurance/web/prd/www/sell/nian_b";
	private static final String NB_PLUS_YB = "insurance/web/prd/nbplus_yb";
	private static final String NB_PLUS2_YB = "insurance/web/prd/nbplus2_yb";
	private static final String NB_PLUS_SELL = "insurance/web/prd/www/sell/nian_b_plus";
	private static final String YHZZ = "insurance/web/prd/yyzh_yb";
	
	private static final String FFY1_XE = "insurance/web/prd/ffy1_xe";
	private static final String FFY3_XE = "insurance/web/prd/ffy3_xe";
	private static final String BBB_XE = "insurance/web/prd/bbb_xe";
	private static final String DDB_XE = "insurance/web/prd/ddb_xe";
	private static final String NB_XE = "insurance/web/prd/nb_xe";
	private static final String NB_PLUS_XE = "insurance/web/prd/nb_plus_xe";
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index(ServletRequest request, Map<String, Object> map) {
		return INDEX;
	}
	
	@RequestMapping(value="/prdList", method=RequestMethod.GET)
	public String prdList(ServletRequest request, Map<String, Object> map) {
		return PRD_LIST;
	}
	
	@RequestMapping(value="/prd/{prdName}", method=RequestMethod.GET)
	public String toPrd(@PathVariable String prdName, ServletRequest request, Map<String, Object> map) {
		LOG.debug("--------------prdName: " + prdName);
		switch(prdName) {
		case "ffy1":
			return FFY_1;
		case "ffy3":
			return FFY_3;
		case "ddb":
			return DDB;
		case "bbb":
			return BBB;
		case "nb":
			return NB;
		case "nbplus":
			return NB_PLUS;
			default:
				return YHZZ;
		}
	}
	
	@RequestMapping(value="/prd/{prdName}/xe", method=RequestMethod.POST)
	public String xe(@PathVariable String prdName, ServletRequest request, Map<String, Object> map, Page page) {
		LOG.debug("--------------get the prd xe rst: " + prdName);
		map.put("page", page);
		String rst = null;
		switch(prdName) {
		case "ffy1":
			rst = "ffy 1 xe rst";
			request.setAttribute("rst", rst);
			return FFY1_XE;
		case "ffy3":
			return FFY3_XE;
		case "ddb":
			return DDB_XE;
		case "bbb":
			return BBB_XE;
		case "nb":
			return NB_XE;
		case "nbplus":
			return NB_PLUS_XE;
			default:
				return YHZZ;
		}
	}
	
	@RequestMapping(value="/prd/{prdName}/{func}", method=RequestMethod.GET)
	public String toPrdFunc(@PathVariable String prdName, @PathVariable String func, ServletRequest request, Map<String, Object> map) {
		LOG.debug("--------------prdName: " + prdName + ", func=" + func);
		Page page = new Page();
		page.setPageNum(1);
		page.setTotalPage(1);
		switch(prdName) {
		case "ffy1":
			switch(func){
			case "yb":
				return FFY1_YB;
			case "yb2":
				return FFY12_YB;
			case "sell":
				return FFY1_SELL;
			case "xe":
				map.put("page", page);
				return FFY1_XE;
			}
		case "ffy3":
			switch(func){
			case "yb":
				return FFY3_YB;
			case "yb2":
				return FFY32_YB;
			case "sell":
				return FFY3_SELL;
			}
		case "ddb":
			switch(func){
			case "yb":
				return DDB_YB;
			case "yb2":
				return DDB2_YB;
			case "sell":
				return DDB_SELL;
			}
		case "bbb":
			switch(func){
			case "yb":
				return BBB_YB;
			case "yb2":
				return BBB2_YB;
			case "sell":
				return BBB_SELL;
			}
		case "nb":
			switch(func){
			case "yb":
				return NB_YB;
			case "yb2":
				return NB2_YB;
			case "sell":
				return NB_SELL;
			}
		case "nbplus":
			switch(func){
			case "yb":
				return NB_PLUS_YB;
			case "yb2":
				return NB_PLUS2_YB;
			case "sell":
				return NB_PLUS_SELL;
			}
			default:
				return YHZZ;
		}
	}
	
	@RequestMapping(value="/todoList", method=RequestMethod.GET)
	public String todoList(ServletRequest request, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put(SecurityConstants.LOGIN_USER, shiroUser.getUser());
		
		map.put("issueList", kfglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("bqIssueList", bqglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("checkWriteIssueList", qyglService.getTODOWriteIssueList(shiroUser.getUser()));
		
		map.put("checkRecordIssueList", qyglService.getTODORecordIssueList(shiroUser.getUser()));
		
		map.put("xqIssueList", xqglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("hfIssueList", hfglService.getTODOIssueList(shiroUser.getUser()));
		
		map.put("underwriteList", qyglService.getTODOUnderWriteList(shiroUser.getUser()));
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
