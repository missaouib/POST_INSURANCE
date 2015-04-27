/**
 * 
 */
package com.gdpost.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.member.TblMemberRole;
import com.gdpost.web.util.dwz.Page;

public interface MRoleService {
	TblMemberRole get(Long id);

	void saveOrUpdate(TblMemberRole role);

	void delete(Long id);
	
	List<TblMemberRole> findAll(Page page);
	
	List<TblMemberRole> findByExample(Specification<TblMemberRole> specification, Page page);
}
