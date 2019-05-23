package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.insurance.Policy;

/**
 * A data access object (DAO) providing persistence and search support for
 * TPolicy entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see com.gdpost.web.entity.insurance.Policy
 * @author MyEclipse Persistence Tools
 */
public interface PolicyDAO extends JpaRepository<Policy, Long>, JpaSpecificationExecutor<Policy> {
	Policy getByPolicyNoAndAttachedFlag(String policyNo, Integer attachedFlag);
	
	Policy getByPolicyDtlHolderCardNum(String idCardNum);
	
	@Query(value="select tp from Policy tp, BankCode bc where tp.bankCode.bankCode=bc.cpiCode and bc.status=1 and bc.netFlag=2 and tp.attachedFlag=0 and tp.policyNo=:policyNo")
	Policy isBankPolicy(@Param("policyNo") String policyNo);
}