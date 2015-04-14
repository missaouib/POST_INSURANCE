/**
 * 
 */
package com.sendtend.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.member.TblMemberPermission;
import com.sendtend.web.util.dwz.Page;

public interface MPermissionService {
	TblMemberPermission get(Long id);

	void saveOrUpdate(TblMemberPermission permission);

	void delete(Long id);
	
	List<TblMemberPermission> findAll(Page page);
	
	List<TblMemberPermission> findByExample(Specification<TblMemberPermission> specification, Page page);
}
