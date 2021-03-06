/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.shiro.ShiroDbRealm.java
 * Class:			ShiroDbRealm
 * Date:			2012-8-2
 * Author:			Aming
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
 * @author Aming Version 1.1.0
 * @since 2012-8-2 ??????3:09:50
 */

public class ShiroDbRealm extends AuthorizingRealm {
	private static final Logger log = LoggerFactory.getLogger(ShiroDbRealm.class);
	
	private static final int INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	private static final String ALGORITHM = "SHA-1";

	// ???????????????????????????
	protected boolean activeRoot = false;
	
	// ?????????????????????
	protected boolean useCaptcha = false;
	
	protected UserService userService;
	
	protected RoleService roleService;

	protected UserRoleService userRoleService;
	
	protected OrganizationRoleService organizationRoleService;
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	/**
	 * ???ShiroDbRealm?????????????????????????????????????????????
	 * ??????
	 */	
	public ShiroDbRealm() {
		super();
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ALGORITHM);
		matcher.setHashIterations(INTERATIONS);

		setCredentialsMatcher(matcher);
	}
	
	
	
	/**
	 * ??????????????????, ???????????????.
	 */
	// TODO ???????????????????????????
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		//log.debug("--------------------------------shiro db realm to cache the user's info");
		String userType = "admin";
		if (useCaptcha) {
			CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
			String parm = token.getCaptcha();
			
			userType = token.getUserType();
			
			if (!PatchcaServlet.validate(SecurityUtils.getSubject().getSession().getId().toString(), parm.toLowerCase())) {//??????????????????
				throw new IncorrectCaptchaException("??????????????????");
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
				// ????????????????????????
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
//				// ????????????????????????
//				return new SimpleAuthenticationInfo(shiroUser, user.getPassword(),
//						ByteSource.Util.bytes(salt), getName());
//			} else {
//				return null;
//			}
		//}
		return null;
	}

	/**
	 * ????????????????????????, ?????????????????????????????????????????????????????????.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Collection<?> collection = principals.fromRealm(getName());
		if (collection == null || collection.isEmpty()) {
			return null;
		}
		
		ShiroUser shiroUser = (ShiroUser) collection.iterator().next();
		// ???????????????User
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
			// ??????????????????????????? && id==1??????????????????????????????????????? 
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
					log.info("?????????" + shiroUser.getLoginName() + "????????????????????????:" + "???At " + new Date());
					//log.info(shiroUser.getLoginName() + "???????????????:" + hasRoles);
					//log.info(shiroUser.getLoginName() + "???????????????:" + hasPermissions);
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
	 * ??????????????????
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
			log.debug(shiroUser.getLoginName() + "???????????????:" + hasRoles);
		}
		return hasRoles;
	}
	
	/**
	 * ??????????????????
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
//			log.info(shiroUser.getLoginName() + "???????????????:" + hasRoles);
//		}
//		return hasRoles;
//	}
	
	/**
	 * ????????????????????????
	 * @param roles
	 * @param shiroUser
	 * @return
	 */
	private Collection<String> makePermissions(Collection<Role> roles, ShiroUser shiroUser) {
		// ??????shiroUser???map
		shiroUser.getHasDataControls().clear();
		shiroUser.getHasModules().clear();
		
		Collection<String> stringPermissions = new ArrayList<String>();
		for (Role role : roles) {
			List<RolePermission> rolePermissions = role.getRolePermissions();
			for (RolePermission rolePermission : rolePermissions) {
				Permission permission = rolePermission.getPermission();
				if(permission == null) {
					continue;
				}
				log.debug("----------id:" + permission.getId());
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
			log.debug(shiroUser.getLoginName() + "???????????????:" + stringPermissions);
		}
		return stringPermissions;
	}
	/*
	private Collection<String> makeMemberPermissions(Collection<TblMemberUserRole> roles, ShiroUser shiroUser) {
		// ??????shiroUser???map
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
				 * ?????????????????????datacontrol?????????????????????????????????shiroUser??????datacontrol??????????????????
				 * ????????????dcBuilder
				 *
				//StringBuilder dcBuilder = new StringBuilder();
				for (TblMemberRolePermissionDataControl rpdc : rolePermission.getTblMemberRolePermissionDataControls()) {
					TblMemberDataControl dataControl = rpdc.getTblMemberDataControl();
					*
					 * ?????????????????????datacontrol?????????????????????????????????shiroUser??????datacontrol??????????????????
					 *
					//dcBuilder.append(dataControl.getName() + ",");
					
					shiroUser.getHasTblMemberDataControls().put(dataControl.getName(), dataControl);
				}
				*
				 * ?????????????????????datacontrol?????????????????????????????????shiroUser??????datacontrol??????????????????
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
			log.info(shiroUser.getLoginName() + "???????????????:" + stringPermissions);
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
	 * ????????????
	 * @param plainPassword ????????????
	 * @param password ????????????
	 * @param salt ??????
	 * @return
	 */
	public static boolean validatePassword(String plainPassword, String password, String salt) {
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), INTERATIONS);
		String oldPassword = Encodes.encodeHex(hashPassword);
		return password.equals(oldPassword);
	}
	
	/* 
	 * ???????????????????????????AuthorizationCacheKey???ShiroUser???loginName???????????????????????????shiro???????????????
	 * ??????shiro?????????AuthorizationCacheKey???PrincipalCollection????????????
	 * @see org.apache.shiro.realm.AuthorizingRealm#getAuthorizationCacheKey(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser)principals.getPrimaryPrincipal();
		return shiroUser.getLoginName();
	}

	/**
	 * ??????????????????????????????.
	 */
	public void clearCachedAuthorizationInfo(String loginName) {
		ShiroUser shiroUser = new ShiroUser(loginName);
		
		SimplePrincipalCollection principals = new SimplePrincipalCollection(shiroUser, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * ????????????????????????????????????.
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
	 * ?????? isActiveRoot ??????  
	 * @param isActiveRoot
	 */
	public void setActiveRoot(boolean activeRoot) {
		this.activeRoot = activeRoot;
	}

	/**  
	 * ?????? useCaptcha ??????  
	 * @param useCaptcha
	 */
	public void setUseCaptcha(boolean useCaptcha) {
		this.useCaptcha = useCaptcha;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**  
	 * ?????? userRoleService ??????  
	 * @param userRoleService
	 */
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	/**  
	 * ?????? organizationRoleService ??????  
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
