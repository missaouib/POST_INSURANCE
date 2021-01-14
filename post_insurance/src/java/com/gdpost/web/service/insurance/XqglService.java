/**
 * 
 * ==========================================================
 * 续期管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.basedata.RenewalType;
import com.gdpost.web.entity.insurance.Policy;
import com.gdpost.web.entity.insurance.RenewedFollow;
import com.gdpost.web.entity.insurance.RenewedList;
import com.gdpost.web.entity.insurance.RenewedStay;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.util.dwz.Page;

public interface XqglService {
	RenewedList get(Long id);

	void saveOrUpdate(RenewedList user);

	void delete(Long id);
	
	List<RenewedList> findAll(Page page);
	
	List<RenewedList> findByExample(Specification<RenewedList> specification, Page page);
	
	RenewedList getByPolicyNoAndPrdName(Policy policy, String prdName);
	
	List<RenewedList> getTODOIssueList(User user);

	List<RenewalType> getRenewedDealTypeList(int orgType);
	
	List<RenewalType> getAllRenewedDealTypeList();
	
	List<String> getProvAcitivity();
	
	List<String> getFeeMatch();
	
	RenewedStay getRenewedStay(Long id);
	
	int getRenewedStayNum(String policyNo);
	
	void saveOrUpdateRenewedStay(RenewedStay oc);
	
	void deleteRenewedStay(Long id);
	
	List<RenewedStay> findAllRenewedStay(Page page);
	
	List<RenewedStay> findByRenewedStayExample(Specification<RenewedStay> specification, Page page);
	
	RenewedStay getRenewedStayByPolicyNo(String policyNo);
	
	/*
	 * follow
	 */
	
	RenewedFollow getRenewedFollow(Long id);

	void saveOrUpdateRenewedFollow(RenewedFollow user);
	
	List<RenewedFollow> findAllRenewedFollow(Page page);
	
	List<RenewedFollow> findRenewedFollowByExample(Specification<RenewedFollow> specification, Page page);

}
