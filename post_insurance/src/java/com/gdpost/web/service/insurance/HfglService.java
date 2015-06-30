/**
 * 
 * ==========================================================
 * 回访管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.main.CallFail;
import com.gdpost.web.util.dwz.Page;

public interface HfglService {
	CallFail get(Long id);

	void saveOrUpdate(CallFail user);

	void delete(Long id);
	
	List<CallFail> findAll(Page page);
	
	List<CallFail> findByExample(Specification<CallFail> specification, Page page);
	
	CallFail getByPolicyNo(String username);
}
