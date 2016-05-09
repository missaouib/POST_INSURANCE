/**
 * 
 * ==========================================================
 * 理赔管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.component.Settlement;
import com.gdpost.web.util.dwz.Page;

public interface LpglService {
	Settlement get(Long id);

	void saveOrUpdate(Settlement user);

	void delete(Long id);
	
	List<Settlement> findAll(Page page);
	
	List<Settlement> findByExample(Specification<Settlement> specification, Page page);
	
	Settlement getBySettlementNo(String username);
}
