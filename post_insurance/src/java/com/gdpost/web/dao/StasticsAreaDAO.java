package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.insurance.StasticsArea;

/**
 * Interface for TPolicyDtlDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface StasticsAreaDAO extends JpaRepository<StasticsArea, Long>, JpaSpecificationExecutor<StasticsArea> {
	
}