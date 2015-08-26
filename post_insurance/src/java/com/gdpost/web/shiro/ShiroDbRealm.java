/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.shiro.ShiroDbRealm.java
 * Class:			ShiroDbRealm
 * Date:			2012-8-2
 * Author:			sendtend
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.gdpost.web.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdpost.utils.Digests;
import com.gdpost.utils.Encodes;
import com.gdpost.web.entity.main.DataControl;
import com.gdpost.web.entity.main.OrganizationRole;
import com.gdpost.web.entity.main.Permission;
import com.gdpost.web.entity.main.Role;
import com.gdpost.web.entity.main.RolePermission;
import com.gdpost.web.entity.main.RolePermissionDataControl;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.entity.main.UserRole;
import com.gdpost.web.service.OrganizationRoleService;
import com.gdpost.web.service.RolePermissionService;
import com.gdpost.web.service.RoleService;
import com.gdpost.web.service.UserRoleService;
import com.gdpost.web.service.UserService;
import com.gdpost.web.util.dwz.Page;

/**
 * 
 * @author sendtend Version 1.1.0
 * @since 2012-8-2 下午3:09:50
 */

public class ShiroDbRealm extends AuthorizingRealm {
	private static final Logger log = LoggerFactory.getLogger(ShiroDbRealm.class);
	
	private static final int INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	private static final String ALGORITHM = "SHA-1";

	// 是否启用超级管理员
	protected boolean activeRoot = false;
	
	// 是否使用验证码
	protected boolean useCaptcha = false;
	
	protected UserService userService;
	
	protected RoleService roleService;

	protected UserRoleService userRoleService;
	
	protected OrganizationRoleService organizationRoleService;
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	/**
	 * 给ShiroDbRealm提供编码信息，用于密码密码比对
	 * 描述
	 */	
	public ShiroDbRealm() {
		super();
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ALGORITHM);
		matcher.setHashIterations(INTERATIONS);

		setCredentialsMatcher(matcher);
	}
	
	
	
	/**
	 * 认证回调函数, 登录时调用.
	 */
	// TODO 对认证进行缓存处理
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		//log.debug("--------------------------------shiro db realm to cache the user's info");
		String userType = "admin";
		if (useCaptcha) {
			CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
			String parm = token.getCaptcha();
			
			userType = token.getUserType();
			
			if (!PatchcaServlet.validate(SecurityUtils
					.getSubject().getSession().getId().toString(), parm.toLowerCase())) {//忽略大小写。
				throw new IncorrectCaptchaException("验证码错误！");
			}
		}
		//log.debug("--------------------------------shiro db realm flag1: " + userType);
		UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
		if(userType != null && userType.equals("admin")) {
			User user = userService.getByUsername(token.getUsername());
			if (user != null) {
				if (user.getStatus().equals(User.STATUS_DISABLED)) {
					throw new DisabledAccountException();
				}
				
				byte[] salt = Encodes.decodeHex(user.getSalt());
				
				ShiroUser shiroUser = new ShiroUser(user.getId(), user.getUsername());
				shiroUser.setUser(user);
				shiroUser.setUserType(userType);
				// 这里可以缓存认证
				return new SimpleAuthenticationInfo(shiroUser, user.getPassword(),
						ByteSource.Util.bytes(salt), getName());
			} else {
				return null;
			}
		} //else {
//			TblMemberUser user = mUserService.getByUsername(token.getUsername());
//			if (user != null) {
//				if (user.getStatus().equals(User.STATUS_DISABLED)) {
//					throw new DisabledAccountException();
//				}
//				
//				byte[] salt = Encodes.decodeHex(user.getSalt());
//				
//				ShiroUser shiroUser = new ShiroUser(user.getId(), user.getUserName());
//				shiroUser.setMemberUser(user);
//				shiroUser.setUserType(userType);
//				// 这里可以缓存认证
//				return new SimpleAuthenticationInfo(shiroUser, user.getPassword(),
//						ByteSource.Util.bytes(salt), getName());
//			} else {
//				return null;
//			}
		//}
		return null;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Collection<?> collection = principals.fromRealm(getName());
		if (collection == null || collection.isEmpty()) {
			return null;
		}
		
		ShiroUser shiroUser = (ShiroUser) collection.iterator().next();
		// 设置、更新User
		if(shiroUser.getUserType().equals("admin")) {
			log.debug("--------------------------------shiro db realm doGetAuthorizationInfo: update the user info" + shiroUser.getId());
			User user = userService.get(shiroUser.getId());
			shiroUser.setUser(user);
			//this
		} else {
			//shiroUser.setMemberUser(mUserService.get(shiroUser.getId()));
		}
		SimpleAuthorizationInfo sai = null;
		try {
			sai = newAuthorizationInfo(shiroUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sai;
	}
	
	private SimpleAuthorizationInfo newAuthorizationInfo(ShiroUser shiroUser) {
		Collection<String> hasPermissions = null;
		Collection<String> hasRoles = null;
		
		String userType = shiroUser.getUserType();
		if(userType.equals("admin")) {
			// 是否启用超级管理员 && id==1为超级管理员，构造所有权限 
			if (activeRoot && shiroUser.getId() == 1) {
				hasRoles = new HashSet<String>();
				Page page = new Page();
				page.setNumPerPage(Integer.MAX_VALUE);
				List<Role> roles = roleService.findAll(page);
				
				for (Role role : roles) {
					hasRoles.add(role.getName());
				}
				
				hasPermissions = new HashSet<String>();
				hasPermissions.add("*");
				
				if (log.isInfoEnabled()) {
					log.info("使用了" + shiroUser.getLoginName() + "的超级管理员权限:" + "。At " + new Date());
					//log.info(shiroUser.getLoginName() + "拥有的角色:" + hasRoles);
					//log.info(shiroUser.getLoginName() + "拥有的权限:" + hasPermissions);
				}
			} else {
				List<UserRole> userRoles = userRoleService.findByUserId(shiroUser.getId());
				List<OrganizationRole> organizationRoles = organizationRoleService
						.findByOrganizationId(shiroUser.getUser().getOrganization().getId());
				
				Collection<Role> roles = getUserRoles(userRoles, organizationRoles);
				hasRoles = makeRoles(roles, shiroUser);
				try {
					hasPermissions = makePermissions(roles, shiroUser);
				} catch (Exception ex) {
					for (Role role:roles) {
						role.setRolePermissions(rolePermissionService.findByRoleId(role.getId()));
					}
					hasPermissions = makePermissions(roles, shiroUser);
				}
			}
		} else {
//			List<TblMemberUserRole> userRoles = mUserRoleService.findByUserId(shiroUser.getId());
//			Collection<TblMemberUserRole> roles = getMemberUserRoles(userRoles);
//			hasRoles = makeMemberRoles(roles, shiroUser);
//			hasPermissions = makeMemberPermissions(roles, shiroUser);
		}
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(hasRoles);
		info.addStringPermissions(hasPermissions);
		
		return info;
	}
	
	private Collection<Role> getUserRoles(List<UserRole> userRoles, List<OrganizationRole> organizationRoles) {
		Set<Role> roles = new HashSet<Role>();
		for (UserRole userRole : userRoles) {
			roles.add(userRole.getRole());
		}
		
		for (OrganizationRole organizationRole : organizationRoles) {
			roles.add(organizationRole.getRole());
		}
		
		return roles;
	}
	
//	private Collection<TblMemberUserRole> getMemberUserRoles(List<TblMemberUserRole> userRoles) {
//		Set<TblMemberUserRole> roles = new HashSet<TblMemberUserRole>();
//		for (TblMemberUserRole userRole : userRoles) {
//			roles.add(userRole);
//		}
//		
//		return roles;
//	}
	
	/**
	 * 组装角色权限
	 * @param roles
	 * @param shiroUser
	 * @return
	 */
	private Collection<String> makeRoles(Collection<Role> roles, ShiroUser shiroUser) {
		Collection<String> hasRoles = new HashSet<String>();
		for (Role role : roles) {
			hasRoles.add(role.getName());
		}

		if (log.isDebugEnabled()) {
			log.debug(shiroUser.getLoginName() + "拥有的角色:" + hasRoles);
		}
		return hasRoles;
	}
	
	/**
	 * 组装角色权限
	 * @param roles
	 * @param shiroUser
	 * @return
	 */
//	private Collection<String> makeMemberRoles(Collection<TblMemberUserRole> roles, ShiroUser shiroUser) {
//		Collection<String> hasRoles = new HashSet<String>();
//		for (TblMemberUserRole role : roles) {
//			hasRoles.add(role.getTblMemberRole().getName());
//		}
//
//		if (log.isInfoEnabled()) {
//			log.info(shiroUser.getLoginName() + "拥有的角色:" + hasRoles);
//		}
//		return hasRoles;
//	}
	
	/**
	 * 组装资源操作权限
	 * @param roles
	 * @param shiroUser
	 * @return
	 */
	private Collection<String> makePermissions(Collection<Role> roles, ShiroUser shiroUser) {
		// 清空shiroUser中map
		shiroUser.getHasDataControls().clear();
		shiroUser.getHasModules().clear();
		
		Collection<String> stringPermissions = new ArrayList<String>();
		for (Role role : roles) {
			List<RolePermission> rolePermissions = role.getRolePermissions();
			for (RolePermission rolePermission : rolePermissions) {
				Permission permission = rolePermission.getPermission();
				
				String resource = permission.getModule().getSn();
				String operate = permission.getSn();
		
				StringBuilder builder = new StringBuilder();
				builder.append(resource + ":" + operate);
				
				shiroUser.getHasModules().put(resource, permission.getModule());
				
				StringBuilder dcBuilder = new StringBuilder();
				for (RolePermissionDataControl rpdc : rolePermission.getRolePermissionDataControls()) {
					DataControl dataControl = rpdc.getDataControl();
					dcBuilder.append(dataControl.getName() + ",");
					dataControl.getDescription();
					
					shiroUser.getHasDataControls().put(dataControl.getName(), dataControl);
				}
				
				if (dcBuilder.length() > 0) {
					builder.append(":" + dcBuilder.substring(0, dcBuilder.length() - 1));
				}
				
				stringPermissions.add(builder.toString());
			}
		}

		if (log.isDebugEnabled()) {
			log.debug(shiroUser.getLoginName() + "拥有的权限:" + stringPermissions);
		}
		return stringPermissions;
	}
	/*
	private Collection<String> makeMemberPermissions(Collection<TblMemberUserRole> roles, ShiroUser shiroUser) {
		// 清空shiroUser中map
		shiroUser.getHasDataControls().clear();
		shiroUser.getHasModules().clear();
		
		Collection<String> stringPermissions = new ArrayList<String>();
		for (TblMemberUserRole role : roles) {
			List<TblMemberRolePermission> rolePermissions = role.getTblMemberRole().getTblMemberRolePermissions();
			for (TblMemberRolePermission rolePermission : rolePermissions) {
				TblMemberPermission permission = rolePermission.getTblMemberPermission();
				String resource = permission.getTblMemberModule().getSn();
				String operate = permission.getSn();
		
				StringBuilder builder = new StringBuilder();
				builder.append(resource + ":" + operate);
				
				shiroUser.getHasTblMemberModules().put(resource, permission.getTblMemberModule());
				
				/*
				 * 关闭连锁模块的datacontrol的权限要求，但是存放在shiroUser中的datacontrol的数据保留。
				 * 涉及这个dcBuilder
				 *
				//StringBuilder dcBuilder = new StringBuilder();
				for (TblMemberRolePermissionDataControl rpdc : rolePermission.getTblMemberRolePermissionDataControls()) {
					TblMemberDataControl dataControl = rpdc.getTblMemberDataControl();
					*
					 * 关闭连锁模块的datacontrol的权限要求，但是存放在shiroUser中的datacontrol的数据保留。
					 *
					//dcBuilder.append(dataControl.getName() + ",");
					
					shiroUser.getHasTblMemberDataControls().put(dataControl.getName(), dataControl);
				}
				*
				 * 关闭连锁模块的datacontrol的权限要求，但是存放在shiroUser中的datacontrol的数据保留。
				 *
				*
				if (dcBuilder.length() > 0) {
					builder.append(":" + dcBuilder.substring(0, dcBuilder.length() - 1));
				}
				*
				
				stringPermissions.add(builder.toString());
			}
		}

		if (log.isInfoEnabled()) {
			log.info(shiroUser.getLoginName() + "拥有的权限:" + stringPermissions);
		}
		return stringPermissions;
	}
	*/
	public static class HashPassword {
		public String salt;
		public String password;
	}
	
	public static HashPassword encryptPassword(String plainPassword) {
		HashPassword result = new HashPassword();
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		result.salt = Encodes.encodeHex(salt);

		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, INTERATIONS);
		result.password = Encodes.encodeHex(hashPassword);
		return result;
	}
	
	/**
	 * 
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @param salt 盐值
	 * @return
	 */
	public static boolean validatePassword(String plainPassword, String password, String salt) {
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), INTERATIONS);
		String oldPassword = Encodes.encodeHex(hashPassword);
		return password.equals(oldPassword);
	}
	
	/* 
	 * 覆盖父类方法，设置AuthorizationCacheKey为ShiroUser的loginName，这样才能顺利删除shiro中的缓存。
	 * 因为shiro默认的AuthorizationCacheKey为PrincipalCollection的对象。
	 * @see org.apache.shiro.realm.AuthorizingRealm#getAuthorizationCacheKey(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser)principals.getPrimaryPrincipal();
		return shiroUser.getLoginName();
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String loginName) {
		ShiroUser shiroUser = new ShiroUser(loginName);
		
		SimplePrincipalCollection principals = new SimplePrincipalCollection(shiroUser, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
	
	/**  
	 * 设置 isActiveRoot 的值  
	 * @param isActiveRoot
	 */
	public void setActiveRoot(boolean activeRoot) {
		this.activeRoot = activeRoot;
	}

	/**  
	 * 设置 useCaptcha 的值  
	 * @param useCaptcha
	 */
	public void setUseCaptcha(boolean useCaptcha) {
		this.useCaptcha = useCaptcha;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**  
	 * 设置 userRoleService 的值  
	 * @param userRoleService
	 */
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	/**  
	 * 设置 organizationRoleService 的值  
	 * @param organizationRoleService
	 */
	public void setOrganizationRoleService(
			OrganizationRoleService organizationRoleService) {
		this.organizationRoleService = organizationRoleService;
	}

	/**
	 * @param roleService the roleService to set
	 */
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}
