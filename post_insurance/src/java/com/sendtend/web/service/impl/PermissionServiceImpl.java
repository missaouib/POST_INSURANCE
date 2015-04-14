/**
 * 
 */
package	com.sendtend.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendtend.web.dao.PermissionDAO;
import com.sendtend.web.entity.main.Permission;
import com.sendtend.web.service.PermissionService;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.dwz.PageUtils;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionDAO permissionDAO;

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.PermissionService#get(java.lang.Long)  
	 */ 
	@Override
	public Permission get(Long id) {
		return permissionDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.sendtend.web.service.PermissionService#saveOrUpdate(com.sendtend.web.entity.main.Permission)  
	 */
	@Override
	public void saveOrUpdate(Permission permission) {
		permissionDAO.save(permission);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.PermissionService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		permissionDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.PermissionService#findAll(com.sendtend.web.util.dwz.Page)  
	 */
	@Override
	public List<Permission> findAll(Page page) {
		org.springframework.data.domain.Page<Permission> springDataPage = permissionDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.PermissionService#findByExample(org.springframework.data.jpa.domain.Specification, com.sendtend.web.util.dwz.Page)	
	 */
	@Override
	public List<Permission> findByExample(
			Specification<Permission> specification, Page page) {
		org.springframework.data.domain.Page<Permission> springDataPage = permissionDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
