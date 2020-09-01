/**
 * 
 */
package com.gdpost.web.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.insurance.Settlement;

public interface SettlementDAO extends JpaRepository<Settlement, Long>, JpaSpecificationExecutor<Settlement> {

	Settlement getBySettleTaskPolicyPolicyNo(String policyNo);
	
	Settlement getByCaseManAndCaseDate(String caseMan, Date casedate);

	Settlement getByClaimsNo(String claimsNo);
}