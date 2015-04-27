/**
 * 
 */
package com.gdpost.web.dao.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.member.TblMemberRolePermission;

public interface MRolePermissionDAO extends JpaRepository<TblMemberRolePermission, Long>, JpaSpecificationExecutor<TblMemberRolePermission> {

	/**
	 * @param id
	 * @return
	 */
	List<TblMemberRolePermission> findByTblMemberRoleId(Long id);

}