/**
 * 
 */
package	com.sendtend.web.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendtend.web.dao.member.MRoleDAO;
import com.sendtend.web.entity.member.TblMemberRole;
import com.sendtend.web.service.member.MRoleService;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.dwz.PageUtils;

@Service
@Transactional
public class MRoleServiceImpl implements MRoleService {
	
	@Autowired
	private MRoleDAO roleDAO;

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.RoleService#get(java.lang.Long)  
	 */ 
	@Override
	public TblMemberRole get(Long id) {
		return roleDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.sendtend.web.service.RoleService#saveOrUpdate(com.sendtend.web.entity.main.Role)  
	 */
	@Override
	public void saveOrUpdate(TblMemberRole role) {
		roleDAO.save(role);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.RoleService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		roleDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.RoleService#findAll(com.sendtend.web.util.dwz.Page)  
	 */
	@Override
	public List<TblMemberRole> findAll(Page page) {
		org.springframework.data.domain.Page<TblMemberRole> springDataPage = roleDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.RoleService#findByExample(org.springframework.data.jpa.domain.Specification, com.sendtend.web.util.dwz.Page)	
	 */
	@Override
	public List<TblMemberRole> findByExample(
			Specification<TblMemberRole> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberRole> springDataPage = roleDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
