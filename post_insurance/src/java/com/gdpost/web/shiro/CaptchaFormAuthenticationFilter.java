/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.shiro.CaptchaFormAuthenticationFilter.java
 * Class:			CaptchaFormAuthenticationFilter
 * Date:			2012-8-7
 * Author:			Aming
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.gdpost.web.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

import com.gdpost.web.SecurityConstants;

/**
 * 
 * @author Aming 
 * Version 1.1.0
 * @since 2012-8-7 上午9:20:26
 */

public class CaptchaFormAuthenticationFilter extends BaseFormAuthenticationFilter {

	//private static final Logger LOG = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class); 
	
	private String captchaParam = SecurityConstants.CAPTCHA_KEY;

	public String getCaptchaParam() {
		return captchaParam;
	}
	
	private String userType = SecurityConstants.USER_TYPE;
	
	public String getUserType() {
		return userType;
	}
	
	private String flag = SecurityConstants.FLAG;
	
	public String getFlag() {
		return flag;
	}
	
	/**
     * 默认的成功地址
     */
    private String defaultSuccessUrl;
    /**
     * 管理员默认的成功地址
     */
    private String adminDefaultSuccessUrl;
    
    private String webDefaultSuccessUrl;

    public String getWebDefaultSuccessUrl() {
		return webDefaultSuccessUrl;
	}

	public void setWebDefaultSuccessUrl(String webDefaultSuccessUrl) {
		this.webDefaultSuccessUrl = webDefaultSuccessUrl;
	}

	public void setDefaultSuccessUrl(String defaultSuccessUrl) {
        this.defaultSuccessUrl = defaultSuccessUrl;
    }

    public void setAdminDefaultSuccessUrl(String adminDefaultSuccessUrl) {
        this.adminDefaultSuccessUrl = adminDefaultSuccessUrl;
    }

    public String getDefaultSuccessUrl() {
        return defaultSuccessUrl;
    }

    public String getAdminDefaultSuccessUrl() {
        return adminDefaultSuccessUrl;
    }

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}
	
	protected String getUserType(ServletRequest request) {
		return WebUtils.getCleanParam(request, getUserType());
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		String userType = getUserType(request);
		String flag = getFlag();
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		return new CaptchaUsernamePasswordToken(username, password, rememberMe,
				host, captcha, userType, flag);
	}
	
	/**
	 * 覆盖默认实现，用sendRedirect直接跳出框架，以免造成js框架重复加载js出错。
	 * @param token
	 * @param subject
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception  
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.subject.Subject, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
		//issueSuccessRedirect(request, response);
		//we handled the success redirect directly, prevent the chain from continuing:
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		
		String userType = request.getParameter("userType");
		String memberFlag = request.getParameter("flag");
		if(userType != null && userType.equals("member")) {
			ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
			// 加入ipAddress
			shiroUser.setIpAddress(request.getRemoteAddr());
			
			if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With")) 
					|| request.getParameter("ajax") == null) {// 不是ajax请求
				if(memberFlag != null && memberFlag.equals("web")) {
					httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + this.getWebDefaultSuccessUrl());
					return false;
				}
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + this.getDefaultSuccessUrl());
			} else {
				if(memberFlag != null && memberFlag.equals("web")) {
					httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/web/login/timeout/success");
					return false;
				}
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/members/login/timeout/success");
			}
		} else {
			ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
			// 加入ipAddress
			shiroUser.setIpAddress(request.getRemoteAddr());
			
			if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With")) 
					|| request.getParameter("ajax") == null) {// 不是ajax请求
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + this.getAdminDefaultSuccessUrl());
			} else {
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login/timeout/success");
			}
		}
		return false;
	}

}
