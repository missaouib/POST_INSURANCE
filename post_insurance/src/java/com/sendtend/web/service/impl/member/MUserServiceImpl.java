/**
 * 
 */
package	com.sendtend.web.service.impl.member;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendtend.web.dao.member.MUserDAO;
import com.sendtend.web.entity.member.TblMember;
import com.sendtend.web.entity.member.TblMemberUser;
import com.sendtend.web.exception.ExistedException;
import com.sendtend.web.exception.IncorrectPasswordException;
import com.sendtend.web.exception.ServiceException;
import com.sendtend.web.service.member.MUserService;
import com.sendtend.web.shiro.ShiroDbRealm;
import com.sendtend.web.shiro.ShiroDbRealm.HashPassword;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.dwz.PageUtils;

@Service
@Transactional
public class MUserServiceImpl implements MUserService {
	//private static final Logger LOG = LoggerFactory.getLogger(MUserServiceImpl.class);
	
	@Autowired
	private MUserDAO userDAO;
	
	@Autowired
	private ShiroDbRealm shiroRealm;

	@PersistenceContext
	private EntityManager em;
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public TblMemberUser get(Long id) {
		return userDAO.findOne(id);
	}
	
	/*
	@Override
	public TblMemberUser add(TblMemberUser user, String key) {
		Session session = em.unwrap(org.hibernate.Session.class);
		if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
			HashPassword hashPassword = ShiroDbRealm.encryptPassword(user.getPlainPassword());
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
		}
		String sql = "insert into tbl_member_user(user_name,real_name,member_id,"
				+ "password,phone,email,status,"
				+ "is_admin,create_date,created_by,salt)"
				+ " values(:userName, AES_ENCRYPT(:srealName,'"+ key + "'), " + user.getTblMember().getId() + ", "
						+ ":password, AES_ENCRYPT(:sphone,'"+ key + "'), "
						+ "AES_ENCRYPT(:semail,'"+ key + "'), :status, "
						+ user.getIsAdmin() + ", now(), " + user.getCreatedBy().toString() + ", :salt)";
		//LOG.debug("------------sql:" + sql);
		Query q = session.createSQLQuery(sql);
		q.setProperties(user);
		q.executeUpdate();
		return user;
	}
	
	@Override
	public TblMemberUser update(TblMemberUser user, String key) {
		Session session = em.unwrap(org.hibernate.Session.class);
		//LOG.debug(session.isConnected()?"---------isConnected":"---------------NOT CONNECTED");
		String sql = "update tbl_member_user set user_name=:userName, "
				+ "real_name=AES_ENCRYPT(:srealName,'"+ key + "'),member_id=" + user.getTblMember().getId() + ","
						+ "phone=AES_ENCRYPT(:sphone,'"+ key + "'),"
						+ "email=AES_ENCRYPT(:semail,'"+ key + "'),:status,"
								+ "description=AES_ENCRYPT(:sdescription,'"+ key + "') where id=:id";
		//LOG.debug("------------sql:" + sql);
		Query q = session.createSQLQuery(sql);
		q.setProperties(user);
		q.executeUpdate();
		return user;
	}
	*/
	
	/*
	 * (non-Javadoc) 
	 * @see com.sendtend.web.service.UserService#saveOrUpdate(com.sendtend.web.entity.main.User)  
	 */
	@Override
	public TblMemberUser saveOrUpdate(TblMemberUser user) {
		if (user.getId() == null) {
			if (userDAO.getByUserName(user.getUserName()) != null) {
				throw new ExistedException("登录名：" + user.getUserName() + "已存在。");
			}
			
			//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
			if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
				HashPassword hashPassword = ShiroDbRealm.encryptPassword(user.getPlainPassword());
				user.setSalt(hashPassword.salt);
				user.setPassword(hashPassword.password);
			}
		}
		
		TblMemberUser rst = userDAO.save(user);
		shiroRealm.clearCachedAuthorizationInfo(user.getUserName());
		return rst;
	}

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		/*
		if (isSupervisor(id)) {
			logger.warn("操作员{}，尝试删除分配管理员用户", SecurityUtils.getSubject()
					.getPrincipal() + "。");
			throw new NotDeletedException("不能删除 管理员用户。");
		}
		*/
		TblMemberUser user = userDAO.findOne(id);
		userDAO.delete(user.getId());
		
		// TODO 从shiro中注销
		shiroRealm.clearCachedAuthorizationInfo(user.getUserName());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.UserService#findAll(com.sendtend.web.util.dwz.Page)  
	 */
	@Override
	public List<TblMemberUser> findAll(Page page) {
		org.springframework.data.domain.Page<TblMemberUser> springDataPage = userDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.sendtend.web.util.dwz.Page)	
	 */
	@Override
	public List<TblMemberUser> findByExample(
			Specification<TblMemberUser> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberUser> springDataPage = userDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<TblMemberUser> findByTblMemberUserAndTblMemberOrTblMemberParent(String userName, String realName, TblMember member, Page page) {
		org.springframework.data.domain.Page<TblMemberUser> springDataPage = userDAO.findByUserNameContainingAndRealNameContainingAndTblMemberIdOrTblMemberParentId(userName, realName, member.getId(), member.getId(), PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/**
	 * 判断是否超级管理员.
	 
	private boolean isSupervisor(Long id) {
		return id == 1;
	}*/

	/* (non-Javadoc)
	 * @see com.sendtend.web.service.UserService#updatePwd(com.sendtend.web.entity.main.User, java.lang.String)
	 */
	@Override
	public void updatePwd(TblMemberUser user, String newPwd) throws ServiceException {
		//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
		boolean isMatch = ShiroDbRealm.validatePassword(user.getPlainPassword(), user.getPassword(), user.getSalt());
		if (isMatch) {
			HashPassword hashPassword = ShiroDbRealm.encryptPassword(newPwd);
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
			
			userDAO.save(user);
			shiroRealm.clearCachedAuthorizationInfo(user.getUserName());
			
			return; 
		}
		
		throw new IncorrectPasswordException("用户原始密码错误！");
	}

	/* (non-Javadoc)
	 * @see com.sendtend.web.service.UserService#resetPwd(com.sendtend.web.entity.main.User, java.lang.String)
	 */
	@Override
	public void resetPwd(TblMemberUser user, String newPwd) {
		if (newPwd == null) {
			newPwd = "123456";
		}
		
		HashPassword hashPassword = ShiroDbRealm.encryptPassword(newPwd);
		user.setSalt(hashPassword.salt);
		user.setPassword(hashPassword.password);
		
		userDAO.save(user);
	}

	/* (non-Javadoc)
	 * @see com.sendtend.web.service.UserService#getByUsername(java.lang.String)
	 */
	@Override
	public TblMemberUser getByUsername(String username) {
		return userDAO.getByUserName(username);
	}
}
