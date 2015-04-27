/**
 * 
 */
package com.gdpost.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.member.TblMemberUserRole;
import com.gdpost.web.util.dwz.Page;

public interface MUserRoleService {
	TblMemberUserRole get(Long id);

	void saveOrUpdate(TblMemberUserRole userRole);

	void delete(Long id);
	
	List<TblMemberUserRole> findAll(Page page);
	
	List<TblMemberUserRole> findByExample(Specification<TblMemberUserRole> specification, Page page);
	
	List<TblMemberUserRole> findByUserId(Long userId);
}
