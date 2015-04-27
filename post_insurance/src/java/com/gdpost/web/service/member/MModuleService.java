/**
 * 
 */
package com.gdpost.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.member.TblMemberModule;
import com.gdpost.web.util.dwz.Page;

public interface MModuleService {
	TblMemberModule get(Long id);

	void saveOrUpdate(TblMemberModule module);

	void delete(Long id);
	
	List<TblMemberModule> findAll(Page page);
	
	List<TblMemberModule> findByExample(Specification<TblMemberModule> specification, Page page);
	
	TblMemberModule getTree();
	
	List<TblMemberModule> findAll();
}
