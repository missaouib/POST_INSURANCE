/**
 * 
 */
package com.sendtend.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.member.TblMemberRole;
import com.sendtend.web.util.dwz.Page;

public interface MRoleService {
	TblMemberRole get(Long id);

	void saveOrUpdate(TblMemberRole role);

	void delete(Long id);
	
	List<TblMemberRole> findAll(Page page);
	
	List<TblMemberRole> findByExample(Specification<TblMemberRole> specification, Page page);
}
