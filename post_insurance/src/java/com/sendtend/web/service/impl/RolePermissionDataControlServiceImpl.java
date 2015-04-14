/**
 * 
 */
package	com.sendtend.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendtend.web.dao.RolePermissionDataControlDAO;
import com.sendtend.web.entity.main.RolePermissionDataControl;
import com.sendtend.web.service.RolePermissionDataControlService;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.dwz.PageUtils;

@Service
@Transactional
public class RolePermissionDataControlServiceImpl implements RolePermissionDataControlService {
	
	@Autowired
	private RolePermissionDataControlDAO rolePermissionDataControlDAO;

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.RolePermissionDataControlService#get(java.lang.Long)  
	 */ 
	@Override
	public RolePermissionDataControl get(Long id) {
		return rolePermissionDataControlDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.sendtend.web.service.RolePermissionDataControlService#saveOrUpdate(com.sendtend.web.entity.main.RolePermissionDataControl)  
	 */
	@Override
	public void saveOrUpdate(RolePermissionDataControl rolePermissionDataControl) {
		rolePermissionDataControlDAO.save(rolePermissionDataControl);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.RolePermissionDataControlService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		rolePermissionDataControlDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.RolePermissionDataControlService#findAll(com.sendtend.web.util.dwz.Page)  
	 */
	@Override
	public List<RolePermissionDataControl> findAll(Page page) {
		org.springframework.data.domain.Page<RolePermissionDataControl> springDataPage = rolePermissionDataControlDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.RolePermissionDataControlService#findByExample(org.springframework.data.jpa.domain.Specification, com.sendtend.web.util.dwz.Page)	
	 */
	@Override
	public List<RolePermissionDataControl> findByExample(
			Specification<RolePermissionDataControl> specification, Page page) {
		org.springframework.data.domain.Page<RolePermissionDataControl> springDataPage = rolePermissionDataControlDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/* (non-Javadoc)
	 * @see com.sendtend.web.service.RolePermissionDataControlService#save(java.util.List)
	 */
	@Override
	public void save(List<RolePermissionDataControl> newRList) {
		rolePermissionDataControlDAO.save(newRList);
	}

	/* (non-Javadoc)
	 * @see com.sendtend.web.service.RolePermissionDataControlService#delete(java.util.List)
	 */
	@Override
	public void delete(List<RolePermissionDataControl> delRList) {
		rolePermissionDataControlDAO.delete(delRList);
	}

	/* (non-Javadoc)
	 * @see com.sendtend.web.service.RolePermissionDataControlService#findByRolePermissionRoleId(java.lang.Long)
	 */
	@Override
	public List<RolePermissionDataControl> findByRolePermissionRoleId(Long id) {
		return rolePermissionDataControlDAO.findByRolePermissionRoleId(id);
	}
	
}
