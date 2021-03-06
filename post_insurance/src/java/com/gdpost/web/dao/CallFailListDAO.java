package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.insurance.CallFailList;
import com.gdpost.web.entity.insurance.Policy;

/**
 * A data access object (DAO) providing persistence and search support for
 * TCallFail entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see com.gdpost.web.entity.nouse.CallFail
 * @author MyEclipse Persistence Tools
 */
public interface CallFailListDAO extends JpaRepository<CallFailList, Long>, JpaSpecificationExecutor<CallFailList> {

	CallFailList getByPolicy(Policy policy);
	
}