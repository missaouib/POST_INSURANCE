package com.gdpost.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.main.RenewedStay;

/**
 * A data access object (DAO) providing persistence and search support for
 * TRenewalType entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see com.gdpost.web.entity.basedata.RenewedStay
 * @author MyEclipse Persistence Tools
 */
public interface RenewedStayDAO extends JpaRepository<RenewedStay, Long>, JpaSpecificationExecutor<RenewedStay> {
	List<RenewedStay> getByPolicyPolicyNoOrderByIdDesc(String policyNo);
}