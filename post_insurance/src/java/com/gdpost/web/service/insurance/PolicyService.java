/**
 * 
 * ==========================================================
 * 发票管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.component.PolicyDataModel;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.entity.main.PolicyReprintDtl;
import com.gdpost.web.util.dwz.Page;

public interface PolicyService {
	Policy get(Long id);

	void saveOrUpdate(Policy req);

	List<Policy> findAll(Page page);
	
	List<Policy> findByExample(Specification<Policy> specification, Page page);
	
	Policy getByPolicyNo(String policyNo);
	
	Policy getByHolderIdCardNum(String idCardNum);
	
	PolicyReprintDtl getPolicyReprintDtl(Long id);

	void saveOrUpdatePolicyReprintDtl(PolicyReprintDtl req);

	List<PolicyReprintDtl> findAllPolicyReprintDtl(Page page);
	
	List<PolicyReprintDtl> findByPolicyReprintDtlExample(Specification<PolicyReprintDtl> specification, Page page);
	
	List<PolicyDataModel> getPolicyDate(String organCode, String pd1, String pd2);
	
	boolean isBankPolicy(String policyNo);
}
