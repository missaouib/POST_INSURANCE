package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.main.Policy;

/**
 * A data access object (DAO) providing persistence and search support for
 * TPolicy entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see com.gdpost.web.entity.main.Policy
 * @author MyEclipse Persistence Tools
 */
public interface PolicyDAO extends JpaRepository<Policy, Long>, JpaSpecificationExecutor<Policy> {
	Policy getByPolicyNo(String policyNo);
	
	Policy getByPolicyDtlHolderCardNum(String idCardNum);
}