/**
 * 
 */
package com.gdpost.web.dao.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.member.TblMemberRole;

public interface MemberRoleDAO extends JpaRepository<TblMemberRole, Long>, JpaSpecificationExecutor<TblMemberRole> {
	List<TblMemberRole> findById(Long id);
}