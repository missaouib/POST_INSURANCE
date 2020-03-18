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

import com.gdpost.web.entity.basedata.CallDealType;
import com.gdpost.web.entity.insurance.CallFailList;
import com.gdpost.web.entity.insurance.Policy;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.util.dwz.Page;

public interface HfglService {
	CallFailList get(Long id);

	void saveOrUpdate(CallFailList user);

	void delete(Long id);
	
	List<CallFailList> findAll(Page page);
	
	
	List<CallFailList> findByExample(Specification<CallFailList> specification, Page page);
	
	
	CallFailList getByPolicy(Policy policy);
	
	//List<VCallFailList> getTODOIssueList(User user);
	
	List<CallFailList> getTODOIssueList(User user);


	List<CallFailList> batchMail(List<CallFailList> list);
	
	List<CallDealType> getCallDealTypeList(Integer typeFlag);
}
