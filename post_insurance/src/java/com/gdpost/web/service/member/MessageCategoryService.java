/**
 * 
 */
package com.gdpost.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.member.TblMemberMessageCategory;
import com.gdpost.web.util.dwz.Page;

public interface MessageCategoryService {
	TblMemberMessageCategory get(Long id);

	void saveOrUpdate(TblMemberMessageCategory organization);

	void delete(Long id);
	
	List<TblMemberMessageCategory> findAll(Page page);
	
	List<TblMemberMessageCategory> findAllWithCache();
	
	List<TblMemberMessageCategory> findByExample(Specification<TblMemberMessageCategory> specification, Page page);
}
