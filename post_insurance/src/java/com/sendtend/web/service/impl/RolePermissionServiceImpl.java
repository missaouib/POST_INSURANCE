/**
 * 
 */
package	com.sendtend.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendtend.web.dao.RolePermissionDAO;
import com.sendtend.web.entity.main.RolePermission;
import com.sendtend.web.service.RolePermissionService;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.dwz.PageUtils;

@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {
	
	@Autowired
	private RolePermissionDAO rolePermissionDAO;

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.RolePermissionService#get(java.lang.Long)  
	 */ 
	@Override
	public RolePermission get(Long id) {
		return rolePermissionDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.sendtend.web.service.RolePermissionService#saveOrUpdate(com.sendtend.web.entity.main.RolePermission)  
	 */
	@Override
	public void saveOrUpdate(RolePermission rolePermission) {
		rolePermissionDAO.save(rolePermission);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.RolePermissionService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		rolePermissionDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.RolePermissionService#findAll(com.sendtend.web.util.dwz.Page)  
	 */
	@Override
	public List<RolePermission> findAll(Page page) {
		org.springframework.data.domain.Page<RolePermission> springDataPage = rolePermissionDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.RolePermissionService#findByExample(org.springframework.data.jpa.domain.Specification, com.sendtend.web.util.dwz.Page)	
	 */
	@Override
	public List<RolePermission> findByExample(
			Specification<RolePermission> specification, Page page) {
		org.springframework.data.domain.Page<RolePermission> springDataPage = rolePermissionDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/* (non-Javadoc)
	 * @see com.sendtend.web.service.RolePermissionService#findByRoleId(java.lang.Long)
	 */
	@Override
	public List<RolePermission> findByRoleId(Long id) {
		return rolePermissionDAO.findByRoleId(id);
	}

	/* (non-Javadoc)
	 * @see com.sendtend.web.service.RolePermissionService#save(java.util.List)
	 */
	@Override
	public void save(List<RolePermission> newRList) {
		rolePermissionDAO.save(newRList);
	}

	/* (non-Javadoc)
	 * @see com.sendtend.web.service.RolePermissionService#delete(java.util.List)
	 */
	@Override
	public void delete(List<RolePermission> delRList) {
		rolePermissionDAO.delete(delRList);
	}
}
