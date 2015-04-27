/**
 * 
 */
package com.gdpost.web.dao.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.member.TblMemberPermission;

public interface MPermissionDAO extends JpaRepository<TblMemberPermission, Long>, JpaSpecificationExecutor<TblMemberPermission> {

	@Modifying
	@Query("delete from TblMemberPermission where id=:id")
	void delete(@Param("id") Long id);
}