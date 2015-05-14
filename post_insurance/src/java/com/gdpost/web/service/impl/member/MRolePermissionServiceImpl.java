/**
 * 
 */
package	com.gdpost.web.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.member.MRolePermissionDAO;
import com.gdpost.web.entity.member.TblMemberRolePermission;
import com.gdpost.web.service.member.MRolePermissionService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class MRolePermissionServiceImpl implements MRolePermissionService {
	
	@Autowired
	private MRolePermissionDAO rolePermissionDAO;

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.RolePermissionService#get(java.lang.Long)  
	 */ 
	@Override
	public TblMemberRolePermission get(Long id) {
		return rolePermissionDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.RolePermissionService#saveOrUpdate(com.gdpost.web.entity.main.RolePermission)  
	 */
	@Override
	public void saveOrUpdate(TblMemberRolePermission rolePermission) {
		rolePermissionDAO.save(rolePermission);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.RolePermissionService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		rolePermissionDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.RolePermissionService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<TblMemberRolePermission> findAll(Page page) {
		org.springframework.data.domain.Page<TblMemberRolePermission> springDataPage = rolePermissionDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.RolePermissionService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<TblMemberRolePermission> findByExample(
			Specification<TblMemberRolePermission> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberRolePermission> springDataPage = rolePermissionDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/* (non-Javadoc)
	 * @see com.gdpost.web.service.RolePermissionService#findByRoleId(java.lang.Long)
	 */
	@Override
	public List<TblMemberRolePermission> findByRoleId(Long id) {
		return rolePermissionDAO.findByTblMemberRoleId(id);
	}

	/* (non-Javadoc)
	 * @see com.gdpost.web.service.RolePermissionService#save(java.util.List)
	 */
	@Override
	public void save(List<TblMemberRolePermission> newRList) {
		rolePermissionDAO.save(newRList);
	}

	/* (non-Javadoc)
	 * @see com.gdpost.web.service.RolePermissionService#delete(java.util.List)
	 */
	@Override
	public void delete(List<TblMemberRolePermission> delRList) {
		rolePermissionDAO.delete(delRList);
	}
}