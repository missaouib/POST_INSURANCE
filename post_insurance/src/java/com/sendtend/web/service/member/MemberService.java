/**
 * 
 */
package com.sendtend.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.member.TblMember;
import com.sendtend.web.util.dwz.Page;

public interface MemberService {
	TblMember get(Long id);

	TblMember saveOrUpdate(TblMember organization);

	void delete(Long id);
	
	//TblMember add(TblMember m, String mysqlAESKey);
	
	//TblMember update(TblMember m, String mysqlAESKey);
	
	List<TblMember> findAll(Page page);
	
	List<TblMember> findByExample(Specification<TblMember> specification, Page page);

	//List<TblMember> findByNativeSQL(Collection<SearchFilter> idfilters, Map<String, Object> map, Long parentMemberId, Page page);
	
	TblMember getByName(String name);
	
	TblMember getTree();
	
	TblMember getTree(Long id);
}
