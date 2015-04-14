/**
 * 
 */
package	com.sendtend.web.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendtend.web.dao.member.MUserRoleDAO;
import com.sendtend.web.entity.member.TblMemberUserRole;
import com.sendtend.web.service.member.MUserRoleService;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.dwz.PageUtils;

@Service
@Transactional
public class MUserRoleServiceImpl implements MUserRoleService {
	
	@Autowired
	private MUserRoleDAO userRoleDAO;

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.UserRoleService#get(java.lang.Long)  
	 */ 
	@Override
	public TblMemberUserRole get(Long id) {
		return userRoleDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.sendtend.web.service.UserRoleService#saveOrUpdate(com.sendtend.web.entity.main.UserRole)  
	 */
	@Override
	public void saveOrUpdate(TblMemberUserRole userRole) {
		userRoleDAO.save(userRole);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.UserRoleService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		userRoleDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.UserRoleService#findAll(com.sendtend.web.util.dwz.Page)  
	 */
	@Override
	public List<TblMemberUserRole> findAll(Page page) {
		org.springframework.data.domain.Page<TblMemberUserRole> springDataPage = userRoleDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.UserRoleService#findByExample(org.springframework.data.jpa.domain.Specification, com.sendtend.web.util.dwz.Page)	
	 */
	@Override
	public List<TblMemberUserRole> findByExample(
			Specification<TblMemberUserRole> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberUserRole> springDataPage = userRoleDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/* (non-Javadoc)
	 * @see com.sendtend.web.service.UserRoleService#findByUserId(java.lang.Long)
	 */
	@Override
	public List<TblMemberUserRole> findByUserId(Long userId) {
		return userRoleDAO.findByTblMemberUserId(userId);
	}
	
}
