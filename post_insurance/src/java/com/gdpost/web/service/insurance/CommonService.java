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

import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.util.dwz.Page;

public interface CommonService {
	
	Policy getPolicyById(Long id);
	
	Policy getByPolicyNo(String policyNo);
	
	List<Policy> findByPolicyExample(Specification<Policy> specification, Page page);

}
