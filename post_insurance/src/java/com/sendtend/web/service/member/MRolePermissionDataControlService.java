/**
 * 
 */
package com.sendtend.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.member.TblMemberRolePermissionDataControl;
import com.sendtend.web.util.dwz.Page;

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
