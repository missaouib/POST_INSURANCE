/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.SecurityConstants.java
 * Class:			SecurityConstants
 * Date:			2012-8-9
 * Author:			Aming
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.gdpost.web;


/** 
 * 	
 * @author 	Aming
 * Version  1.1.0
 * @since   2012-8-9 上午10:47:08 
 */

public interface SecurityConstants {
	/**
	 * 登录用户
	 */
	public final static String LOGIN_USER = "login_user";    
	
	/**
	 * 验证码
	 */
	public final static String CAPTCHA_KEY = "captcha_key";
	
	public final static String USER_TYPE = "userType";
	
	public final static String FLAG = "flag";
	
	/**
	 * 日志参数
	 */
	public final static String LOG_ARGUMENTS = "log_arguments";
	
	/**
	 * 动态查询，参数前缀
	 */
	public final static String SEARCH_PREFIX = "search_";
	
	/**
	 * 内部动态查询参数常量
	 */
	public final static String NEST_DYNAMIC_SEARCH = "$nest_dynamic_search$";
	
	/**
	 * 内部动态查询参数常量，logical
	 */
	public final static String NEST_DYNAMIC_SEARCH_LOGICAL = "$nest_dynamic_search_logical$";
	
	public final static String LOGIN_USER_ROLE = "LOGIN_USER_ROLE";
	
}
