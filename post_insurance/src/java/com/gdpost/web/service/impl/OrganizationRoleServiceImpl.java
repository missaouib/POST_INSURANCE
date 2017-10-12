/**
 * 
 */
package	com.gdpost.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.OrganizationRoleDAO;
import com.gdpost.web.entity.main.OrganizationRole;
import com.gdpost.web.service.OrganizationRoleService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class OrganizationRoleServiceImpl implements OrganizationRoleService {
	
	@Autowired
	private OrganizationRoleDAO organizationRoleDAO;

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.OrganizationRoleService#get(java.lang.Long)  
	 */ 
	@Override
	public OrganizationRole get(Long id) {
		return organizationRoleDAO.findById(id).get();
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.OrganizationRoleService#saveOrUpdate(com.gdpost.web.entity.main.OrganizationRole)  
	 */
	@Override
	public void saveOrUpdate(OrganizationRole organizationRole) {
		organizationRoleDAO.save(organizationRole);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.OrganizationRoleService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		organizationRoleDAO.deleteById(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.OrganizationRoleService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<OrganizationRole> findAll(Page page) {
		org.springframework.data.domain.Page<OrganizationRole> springDataPage = organizationRoleDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.OrganizationRoleService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<OrganizationRole> findByExample(
			Specification<OrganizationRole> specification, Page page) {
		org.springframework.data.domain.Page<OrganizationRole> springDataPage = organizationRoleDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/* (non-Javadoc)
	 * @see com.gdpost.web.service.OrganizationRoleService#findByOrganizationId(java.lang.Long)
	 */
	@Override
	public List<OrganizationRole> findByOrganizationId(Long organizationId) {
		return organizationRoleDAO.findByOrganizationId(organizationId);
	}
}
