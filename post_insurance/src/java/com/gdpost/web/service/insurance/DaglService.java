/**
 * 
 * ==========================================================
 * 档案管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.util.dwz.Page;

public interface DaglService {
	Policy get(Long id);

	void saveOrUpdate(Policy user);

	void delete(Long id);
	
	List<Policy> findAll(Page page);
	
	List<Policy> findByExample(Specification<Policy> specification, Page page);
	
	Policy getByPolicyNo(String username);
}
