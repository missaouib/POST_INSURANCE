/**
 * 
 */
package com.sendtend.web.dao.member;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sendtend.web.entity.member.TblMemberUser;

public interface MUserDAO extends JpaRepository<TblMemberUser, Long>, JpaSpecificationExecutor<TblMemberUser> {
	TblMemberUser getByUserName(String username);

	List<TblMemberUser> findById(Long id);
	
	org.springframework.data.domain.Page<TblMemberUser> findByUserNameContainingAndRealNameContainingAndTblMemberIdOrTblMemberParentId(String userName, String realName, Long memberId, Long memberParentId, Pageable pageable);
}