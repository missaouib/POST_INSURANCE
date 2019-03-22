/**
 * 
 */
package	com.gdpost.web.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.dao.UserDAO;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.exception.IncorrectPasswordException;
import com.gdpost.web.exception.NotDeletedException;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.service.UserService;
import com.gdpost.web.shiro.ShiroDbRealm;
import com.gdpost.web.shiro.ShiroDbRealm.HashPassword;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ShiroDbRealm shiroRealm;

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public User get(Long id) {
		return userDAO.findById(id).get();
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.User)  
	 */
	@Override
	public void saveOrUpdate(User user) {
		if (user.getId() == null) {
			if (userDAO.getByUsername(user.getUsername()) != null) {
				throw new ExistedException("登录名：" + user.getUsername() + "已存在。");
			}
			
			//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
			if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
				HashPassword hashPassword = ShiroDbRealm.encryptPassword(user.getPlainPassword());
				user.setSalt(hashPassword.salt);
				user.setPassword(hashPassword.password);
			}
		}
		
		userDAO.save(user);
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}，尝试删除超级管理员用户", SecurityUtils.getSubject()
					.getPrincipal() + "。");
			throw new NotDeletedException("不能删除超级管理员用户。");
		}
		User user = userDAO.findById(id).get();
		userDAO.deleteById(user.getId());
		
		// TODO 从shiro中注销
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<User> findAll(Page page) {
		org.springframework.data.domain.Page<User> springDataPage = userDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<User> findByExample(
			Specification<User> specification, Page page) {
		org.springframework.data.domain.Page<User> springDataPage = userDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<User> findByOrganizationId(Long orgId) {
		List<User> list = userDAO.findByOrganizationId(orgId);
		return list;
	}
	
	@Override
	public List<User> findByRoleName(String roleName, Page page) {
		List<User> list = userDAO.findByUserRolesRoleNameAndStatus(roleName, User.STATUS_ENABLED, PageUtils.createPageable(page));
		return list;
	}
	
	@Override
	public List<User> findByRoleNameAndUserName(String roleName, String realname, Page page) {
		List<User> list = userDAO.findByRealnameLikeAndUserRolesRoleNameAndStatus(realname, roleName, User.STATUS_ENABLED, PageUtils.createPageable(page));
		return list;
	}
	
	@Override
	public List<User> findByRoleIdAndUserName(Long roleId, String realname, Page page) {
		List<User> list = userDAO.findByRealnameLikeAndUserRolesRoleIdAndStatus(realname, roleId, User.STATUS_ENABLED, PageUtils.createPageable(page));
		return list;
	}
	
	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#updatePwd(com.gdpost.web.entity.main.User, java.lang.String)
	 */
	@Override
	public void updatePwd(User user, String newPwd) throws ServiceException {
		//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
		boolean isMatch = ShiroDbRealm.validatePassword(user.getPlainPassword(), user.getPassword(), user.getSalt());
		if (isMatch) {
			HashPassword hashPassword = ShiroDbRealm.encryptPassword(newPwd);
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
			user.setPwdTime(new Date());
			
			userDAO.save(user);
			shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
			
			return; 
		}
		
		throw new IncorrectPasswordException("用户密码错误！");
	}

	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#resetPwd(com.gdpost.web.entity.main.User, java.lang.String)
	 */
	@Override
	public void resetPwd(User user, String newPwd) {
		if (newPwd == null) {
			newPwd = "123456";
		}
		
		HashPassword hashPassword = ShiroDbRealm.encryptPassword(newPwd);
		user.setSalt(hashPassword.salt);
		user.setPassword(hashPassword.password);
		user.setPwdTime(new Date());
		
		userDAO.save(user);
	}

	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getByUsername(java.lang.String)
	 */
	@Override
	public User getByUsername(String username) {
		return userDAO.getByUsername(username);
	}
}
