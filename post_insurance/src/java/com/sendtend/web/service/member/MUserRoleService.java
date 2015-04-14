/**
 * 
 */
package com.sendtend.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.member.TblMemberUserRole;
import com.sendtend.web.util.dwz.Page;

public interface MUserRoleService {
	TblMemberUserRole get(Long id);

	void saveOrUpdate(TblMemberUserRole userRole);

	void delete(Long id);
	
	List<TblMemberUserRole> findAll(Page page);
	
	List<TblMemberUserRole> findByExample(Specification<TblMemberUserRole> specification, Page page);
	
	List<TblMemberUserRole> findByUserId(Long userId);
}
