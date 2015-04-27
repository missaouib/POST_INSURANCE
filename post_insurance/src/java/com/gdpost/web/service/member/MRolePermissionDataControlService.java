/**
 * 
 */
package com.gdpost.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.member.TblMemberRolePermissionDataControl;
import com.gdpost.web.util.dwz.Page;

public interface MRolePermissionDataControlService {
	TblMemberRolePermissionDataControl get(Long id);

	void saveOrUpdate(TblMemberRolePermissionDataControl rolePermissionDataControl);

	void delete(Long id);
	
	List<TblMemberRolePermissionDataControl> findAll(Page page);
	
	List<TblMemberRolePermissionDataControl> findByExample(Specification<TblMemberRolePermissionDataControl> specification, Page page);

	/**
	 * @param newRList
	 */
	void save(List<TblMemberRolePermissionDataControl> newRList);

	/**
	 * @param delRList
	 */
	void delete(List<TblMemberRolePermissionDataControl> delRList);

	/**
	 * @param id
	 * @return
	 */
	List<TblMemberRolePermissionDataControl> findByTblMemberRolePermissionTblMemberRoleId(Long id);
}
