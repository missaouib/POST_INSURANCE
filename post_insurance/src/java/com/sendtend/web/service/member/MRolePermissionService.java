/**
 * 
 */
package com.sendtend.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.member.TblMemberRolePermission;
import com.sendtend.web.util.dwz.Page;

public interface MRolePermissionService {
	TblMemberRolePermission get(Long id);

	void saveOrUpdate(TblMemberRolePermission rolePermission);

	void delete(Long id);
	
	List<TblMemberRolePermission> findAll(Page page);
	
	List<TblMemberRolePermission> findByExample(Specification<TblMemberRolePermission> specification, Page page);

	/**
	 * @param id
	 * @return
	 */
	List<TblMemberRolePermission> findByRoleId(Long id);

	/**
	 * @param newRList
	 */
	void save(List<TblMemberRolePermission> newRList);
	
	void delete(List<TblMemberRolePermission> delRList);
}
