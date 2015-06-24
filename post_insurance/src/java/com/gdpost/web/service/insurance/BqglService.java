/**
 * 
 * ==========================================================
 * 保全管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.main.ConservationDtl;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.util.dwz.Page;

public interface BqglService {
	ConservationDtl get(Long id);

	void saveOrUpdate(ConservationDtl user);

	void delete(Long id);
	
	List<ConservationDtl> findAll(Page page);
	
	List<ConservationDtl> findByExample(Specification<ConservationDtl> specification, Page page);
	
	ConservationDtl getByPolicyNo(String policyNo);
	
	List<ConservationDtl> getTODOIssueList(User user);
}
