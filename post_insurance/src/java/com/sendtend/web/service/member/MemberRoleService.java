/**
 * 
 */
package com.sendtend.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.member.TblMemberRole;
import com.sendtend.web.util.dwz.Page;

public interface MemberRoleService {
	TblMemberRole get(Long id);

	void saveOrUpdate(TblMemberRole memberRole);

	void delete(Long id);
	
	List<TblMemberRole> findAll(Page page);
	
	List<TblMemberRole> findByExample(Specification<TblMemberRole> specification, Page page);
	
	/**
	 * 根据memberId，找到已分配的角色。
	 * 描述
	 * @param memberId
	 * @return
	 */
	List<TblMemberRole> findByMemberId(Long memberId);
}
