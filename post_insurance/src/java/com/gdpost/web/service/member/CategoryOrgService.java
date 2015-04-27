/**
 * 
 */
package com.gdpost.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.member.TblCategoryOrg;
import com.gdpost.web.util.dwz.Page;

public interface CategoryOrgService {
	TblCategoryOrg get(Long id);

	void saveOrUpdate(TblCategoryOrg co);

	void delete(Long id);
	
	TblCategoryOrg findByCategoryId(Long categoryId);
	
	List<TblCategoryOrg> findAll(Page page);
	
	List<TblCategoryOrg> findAllWithCache();
	
	List<TblCategoryOrg> findByExample(Specification<TblCategoryOrg> specification, Page page);
}
