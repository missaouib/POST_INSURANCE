/**
 * 
 */
package com.gdpost.web.dao.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.gdpost.web.entity.member.TblMemberRolePermissionDataControl;

public interface MRolePermissionDataControlDAO extends JpaRepository<TblMemberRolePermissionDataControl, Long>, JpaSpecificationExecutor<TblMemberRolePermissionDataControl> {
	List<TblMemberRolePermissionDataControl> findByTblMemberRolePermissionId(Long id);
	
	@Query("from TblMemberRolePermissionDataControl where tblMemberRolePermission.tblMemberRole.id=?")
	List<TblMemberRolePermissionDataControl> findByTblMemberRolePermissionTblMemberRoleId(Long id);
}