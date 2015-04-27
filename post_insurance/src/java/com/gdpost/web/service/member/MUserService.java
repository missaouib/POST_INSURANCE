/**
 * 
 */
package com.gdpost.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.member.TblMember;
import com.gdpost.web.entity.member.TblMemberUser;
import com.gdpost.web.util.dwz.Page;

public interface MUserService {
	TblMemberUser get(Long id);

	TblMemberUser saveOrUpdate(TblMemberUser user);

	void delete(Long id);
	
	//TblMemberUser add(TblMemberUser user, String aeskey);
	
	//TblMemberUser update(TblMemberUser user, String aeskey);
	
	List<TblMemberUser> findAll(Page page);
	
	List<TblMemberUser> findByExample(Specification<TblMemberUser> specification, Page page);
	
	List<TblMemberUser> findByTblMemberUserAndTblMemberOrTblMemberParent(String userName, String realName, TblMember member, Page page);
	
	void updatePwd(TblMemberUser user, String newPwd);
	
	void resetPwd(TblMemberUser user, String newPwd);
	
	TblMemberUser getByUsername(String username);
}
