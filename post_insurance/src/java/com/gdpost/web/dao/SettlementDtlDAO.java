/**
 * 
 */
package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.insurance.SettlementDtl;

public interface SettlementDtlDAO extends JpaRepository<SettlementDtl, Long>, JpaSpecificationExecutor<SettlementDtl> {

	SettlementDtl getBySettlementId(Long id);

	SettlementDtl getByPolicyNo(String policyNo);
}