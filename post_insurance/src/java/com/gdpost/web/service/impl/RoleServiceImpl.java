/**
 * 
 */
package	com.gdpost.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.RoleDAO;
import com.gdpost.web.entity.main.Role;
import com.gdpost.web.service.RoleService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDAO roleDAO;

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.RoleService#get(java.lang.Long)  
	 */ 
	@Override
	public Role get(Long id) {
		return roleDAO.findById(id).get();
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.RoleService#saveOrUpdate(com.gdpost.web.entity.main.Role)  
	 */
	@Override
	public void saveOrUpdate(Role role) {
		roleDAO.save(role);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.RoleService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		roleDAO.deleteById(id);
	}
	
	
	@Override
	public List<Role> findByIds(List<Long> ids) {
		return roleDAO.findByIdIn(ids);
	}
	
	@Override
	public List<Role> findByNameLike(String name) {
		return roleDAO.findByNameLike(name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.RoleService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<Role> findAll(Page page) {
		org.springframework.data.domain.Page<Role> springDataPage = roleDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.RoleService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<Role> findByExample(
			Specification<Role> specification, Page page) {
		org.springframework.data.domain.Page<Role> springDataPage = roleDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
