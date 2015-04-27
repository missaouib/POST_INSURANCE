/**
 * 
 */
package	com.gdpost.web.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.member.MPermissionDAO;
import com.gdpost.web.entity.member.TblMemberPermission;
import com.gdpost.web.service.member.MPermissionService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class MPermissionServiceImpl implements MPermissionService {
	
	@Autowired
	private MPermissionDAO permissionDAO;

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.PermissionService#get(java.lang.Long)  
	 */ 
	@Override
	public TblMemberPermission get(Long id) {
		return permissionDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.PermissionService#saveOrUpdate(com.gdpost.web.entity.main.Permission)  
	 */
	@Override
	public void saveOrUpdate(TblMemberPermission permission) {
		permissionDAO.save(permission);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.PermissionService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		permissionDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.PermissionService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<TblMemberPermission> findAll(Page page) {
		org.springframework.data.domain.Page<TblMemberPermission> springDataPage = permissionDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.PermissionService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<TblMemberPermission> findByExample(
			Specification<TblMemberPermission> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberPermission> springDataPage = permissionDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
