/**
 * 
 */
package com.sendtend.utils;

import org.apache.shiro.subject.Subject;

import com.sendtend.web.entity.member.TblMemberUser;
import com.sendtend.web.shiro.ShiroUser;

/**
 * @author 
 *
 */
public abstract class SecurityUtils {
	public static Object getLoginUser() {
		if(getShiroUser().getUserType().equals("admin")) {
			return getShiroUser().getUser();
		} else {
			return getShiroUser().getMemberUser();
		}
	}
	
	public static TblMemberUser getLoginTblMemberUser() {
		return getShiroUser().getMemberUser();
	}
	
	public static ShiroUser getShiroUser() {
		Subject subject = getSubject();
		ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
		
		return shiroUser;
	}

	public static Subject getSubject() {
		return org.apache.shiro.SecurityUtils.getSubject();
	}
}
