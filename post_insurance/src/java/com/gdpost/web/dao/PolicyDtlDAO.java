package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.main.PolicyDtl;

/**
 * Interface for TPolicyDtlDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface PolicyDtlDAO extends JpaRepository<PolicyDtl, Long>, JpaSpecificationExecutor<PolicyDtl> {
	
}