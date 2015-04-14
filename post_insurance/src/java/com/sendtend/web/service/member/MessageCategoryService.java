/**
 * 
 */
package com.sendtend.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.member.TblMemberMessageCategory;
import com.sendtend.web.util.dwz.Page;

public interface MessageCategoryService {
	TblMemberMessageCategory get(Long id);

	void saveOrUpdate(TblMemberMessageCategory organization);

	void delete(Long id);
	
	List<TblMemberMessageCategory> findAll(Page page);
	
	List<TblMemberMessageCategory> findAllWithCache();
	
	List<TblMemberMessageCategory> findByExample(Specification<TblMemberMessageCategory> specification, Page page);
}
