/**
 * 
 */
package com.sendtend.web.dao.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sendtend.web.entity.member.TblMemberRole;

public interface MRoleDAO extends JpaRepository<TblMemberRole, Long>, JpaSpecificationExecutor<TblMemberRole> {

}