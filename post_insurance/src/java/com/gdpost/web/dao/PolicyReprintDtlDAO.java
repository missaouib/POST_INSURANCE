package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.insurance.PolicyReprintDtl;

/**
 * Interface for TPolicyDtlDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface PolicyReprintDtlDAO extends JpaRepository<PolicyReprintDtl, Long>, JpaSpecificationExecutor<PolicyReprintDtl> {
	
}