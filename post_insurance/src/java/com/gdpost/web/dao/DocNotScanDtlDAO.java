package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.insurance.DocNotScanDtl;

/**
 * Interface for TPolicyDtlDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface DocNotScanDtlDAO extends JpaRepository<DocNotScanDtl, Long>, JpaSpecificationExecutor<DocNotScanDtl> {
	
}