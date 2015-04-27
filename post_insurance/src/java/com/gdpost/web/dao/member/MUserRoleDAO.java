/**
 * 
 */
package com.gdpost.web.dao.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.member.TblMemberUserRole;

public interface MUserRoleDAO extends JpaRepository<TblMemberUserRole, Long>, JpaSpecificationExecutor<TblMemberUserRole> {
	List<TblMemberUserRole> findByTblMemberUserId(Long userId);
}