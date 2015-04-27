/**
 * 
 */
package	com.gdpost.web.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.member.MemberRoleDAO;
import com.gdpost.web.entity.member.TblMemberRole;
import com.gdpost.web.service.member.MemberRoleService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class MemberRoleServiceImpl implements MemberRoleService {
	
	@Autowired
	private MemberRoleDAO memberRoleDAO;

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.MemberRoleService#get(java.lang.Long)  
	 */ 
	@Override
	public TblMemberRole get(Long id) {
		return memberRoleDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.MemberRoleService#saveOrUpdate(com.gdpost.web.entity.main.MemberRole)  
	 */
	@Override
	public void saveOrUpdate(TblMemberRole memberRole) {
		memberRoleDAO.save(memberRole);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.MemberRoleService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		memberRoleDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.MemberRoleService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<TblMemberRole> findAll(Page page) {
		org.springframework.data.domain.Page<TblMemberRole> springDataPage = memberRoleDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.MemberRoleService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<TblMemberRole> findByExample(
			Specification<TblMemberRole> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberRole> springDataPage = memberRoleDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/* (non-Javadoc)
	 * @see com.gdpost.web.service.MemberRoleService#findByMemberId(java.lang.Long)
	 */
	@Override
	public List<TblMemberRole> findByMemberId(Long memberId) {
		return memberRoleDAO.findById(memberId);
	}
}
