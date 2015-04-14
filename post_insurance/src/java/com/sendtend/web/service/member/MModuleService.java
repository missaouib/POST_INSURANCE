/**
 * 
 */
package com.sendtend.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.member.TblMemberModule;
import com.sendtend.web.util.dwz.Page;

public interface MModuleService {
	TblMemberModule get(Long id);

	void saveOrUpdate(TblMemberModule module);

	void delete(Long id);
	
	List<TblMemberModule> findAll(Page page);
	
	List<TblMemberModule> findByExample(Specification<TblMemberModule> specification, Page page);
	
	TblMemberModule getTree();
	
	List<TblMemberModule> findAll();
}
