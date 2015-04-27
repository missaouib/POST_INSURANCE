/**
 * 
 */
package	com.gdpost.web.service.impl.member;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.member.MUserDAO;
import com.gdpost.web.dao.member.MemberMessageAssignDAO;
import com.gdpost.web.dao.member.MemberMessageDAO;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.entity.member.TblMemberMessage;
import com.gdpost.web.entity.member.TblMemberMessageAssign;
import com.gdpost.web.entity.member.TblMemberUser;
import com.gdpost.web.service.member.MRolePermissionDataControlService;
import com.gdpost.web.service.member.MessageService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
	
	//private static final Logger LOG = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@Autowired
	private MemberMessageDAO memberMessageDAO;
	
	@Autowired
	private MUserDAO userDAO;
	
	@Autowired
	private MemberMessageAssignDAO assignDAO;
	
	@Autowired
	private MRolePermissionDataControlService mrpdc;
	
	@PersistenceContext
	private EntityManager em;
	
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.MemberService#get(java.lang.Long)  
	 */ 
	@Override
	public TblMemberMessage get(Long id) {
		return memberMessageDAO.findOne(id);
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.MemberService#getByName(java.lang.String)
	 */
	@Override
	public TblMemberMessage getByTitle(String name) {
		return memberMessageDAO.findByTitle(name);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.MemberService#saveOrUpdate(com.gdpost.web.entity.main.TblMemberMessage)  
	 */
	@Override
	public void saveOrUpdate(TblMemberMessage memberMessage) {
		
		memberMessageDAO.save(memberMessage);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.MemberService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		TblMemberMessage memberMessage = this.get(id);
		memberMessageDAO.delete(memberMessage);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.MemberService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<TblMemberMessage> findAll(Page page) {
		org.springframework.data.domain.Page<TblMemberMessage> springDataPage = memberMessageDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.MemberService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<TblMemberMessage> findByExample(
			Specification<TblMemberMessage> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberMessage> springDataPage = memberMessageDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<TblMemberMessageAssign> findAssignListByExample(Specification<TblMemberMessageAssign> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberMessageAssign> springDataPage = assignDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<TblMemberMessageAssign> findAssignListByUserIdOrOrgId(Long userId, Long orgId, Page page) {
		org.springframework.data.domain.Page<TblMemberMessageAssign> springDataPage = assignDAO.findByUserIdOrOrgId(userId, orgId, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public int checkStatus(String userType, TblMemberUser memberUser, User user, int status) {
		if(userType == null || (userType != null && userType.equals("member") && memberUser == null) 
				|| (user != null && userType.equals("admin") && user == null)) {
			return -1;
		}
		int rst = -1;
		if(userType != null && userType.equals("admin")) {
			if(user.getUsername().equals("admin")) {
				return rst;
			}
			rst = memberMessageDAO.getByOrgIdAndStatus(user.getOrganization().getId(), status);
		} else {
			rst = memberMessageDAO.getByMemberUserIdAndStatus(memberUser.getId(), status);
		}
		return rst;
	}
	
	@Override
	public void saveOrUpdateAssign(TblMemberMessageAssign assign) {
		assignDAO.save(assign);
	}
}
