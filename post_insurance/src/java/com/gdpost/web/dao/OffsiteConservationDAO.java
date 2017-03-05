package com.gdpost.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.main.OffsiteConservation;

/**
 * A data access object (DAO) providing persistence and search support for
 * TConservationDtl entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see com.gdpost.web.entity.main.OffsiteConservation
 * @author MyEclipse Persistence Tools
 */
public interface OffsiteConservationDAO extends JpaRepository<OffsiteConservation, Long>, JpaSpecificationExecutor<OffsiteConservation> {

	OffsiteConservation getByPolicyNo(String policyNo);
	
}