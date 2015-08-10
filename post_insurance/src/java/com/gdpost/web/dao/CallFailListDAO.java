package com.gdpost.web.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.gdpost.web.entity.main.CallFailList;
import com.gdpost.web.entity.main.Policy;

/**
 * A data access object (DAO) providing persistence and search support for
 * TCallFail entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see com.gdpost.web.entity.main.CallFail
 * @author MyEclipse Persistence Tools
 */
public interface CallFailListDAO extends JpaRepository<CallFailList, Long>, JpaSpecificationExecutor<CallFailList> {

	CallFailList getByPolicy(Policy policy);

	@Query("from CallFailList o where DATE_ADD(policy.policyDate,12) <= current_date "
			+ "and (status like '%待处理%' or status like '%二访失败%') order by policy.policyDate ASC")
	Page<CallFailList> get11185List(Pageable page);
}