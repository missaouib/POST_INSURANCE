/**
 * 
 */
package	com.gdpost.web.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.member.MRolePermissionDataControlDAO;
import com.gdpost.web.entity.member.TblMemberRolePermissionDataControl;
import com.gdpost.web.service.member.MRolePermissionDataControlService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class MRolePermissionDataControlServiceImpl implements MRolePermissionDataControlService {
	
	@Autowired
	private MRolePermissionDataControlDAO rolePermissionDataControlDAO;

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.RolePermissionDataControlService#get(java.lang.Long)  
	 */ 
	@Override
	public TblMemberRolePermissionDataControl get(Long id) {
		return rolePermissionDataControlDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.RolePermissionDataControlService#saveOrUpdate(com.gdpost.web.entity.main.RolePermissionDataControl)  
	 */
	@Override
	public void saveOrUpdate(TblMemberRolePermissionDataControl rolePermissionDataControl) {
		rolePermissionDataControlDAO.save(rolePermissionDataControl);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.RolePermissionDataControlService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		rolePermissionDataControlDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.RolePermissionDataControlService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<TblMemberRolePermissionDataControl> findAll(Page page) {
		org.springframework.data.domain.Page<TblMemberRolePermissionDataControl> springDataPage = rolePermissionDataControlDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.RolePermissionDataControlService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<TblMemberRolePermissionDataControl> findByExample(
			Specification<TblMemberRolePermissionDataControl> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberRolePermissionDataControl> springDataPage = rolePermissionDataControlDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/* (non-Javadoc)
	 * @see com.gdpost.web.service.RolePermissionDataControlService#save(java.util.List)
	 */
	@Override
	public void save(List<TblMemberRolePermissionDataControl> newRList) {
		rolePermissionDataControlDAO.save(newRList);
	}

	/* (non-Javadoc)
	 * @see com.gdpost.web.service.RolePermissionDataControlService#delete(java.util.List)
	 */
	@Override
	public void delete(List<TblMemberRolePermissionDataControl> delRList) {
		rolePermissionDataControlDAO.delete(delRList);
	}

	/* (non-Javadoc)
	 * @see com.gdpost.web.service.RolePermissionDataControlService#findByRolePermissionRoleId(java.lang.Long)
	 */
	@Override
	public List<TblMemberRolePermissionDataControl> findByTblMemberRolePermissionTblMemberRoleId(Long id) {
		return rolePermissionDataControlDAO.findByTblMemberRolePermissionTblMemberRoleId(id);
	}
	
}