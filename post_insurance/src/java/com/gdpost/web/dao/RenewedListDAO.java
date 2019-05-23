package com.gdpost.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.gdpost.web.entity.insurance.Policy;
import com.gdpost.web.entity.insurance.RenewedList;

/**
 * A data access object (DAO) providing persistence and search support for
 * TIssue entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see com.gdpost.web.entity.insurance.Issue
 * @author MyEclipse Persistence Tools
 */
public interface RenewedListDAO extends JpaRepository<RenewedList, Long>, JpaSpecificationExecutor<RenewedList> {
	RenewedList getByPolicyAndPrdName(Policy policy, String prdName);
	
	@Query(name="RenewedListDAO.getProvActivitys", value="select distinct prov_activity from t_renewed_list", nativeQuery=true)
	List<String> getProvActivitys();
	
	@Query(name="RenewedListDAO.getFeeMatch", value="select distinct fee_match from t_renewed_list", nativeQuery=true)
	List<String> getFeeMatchs();
}