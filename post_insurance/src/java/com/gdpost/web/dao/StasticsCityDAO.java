package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.insurance.StasticsCity;

/**
 * Interface for TPolicyDtlDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface StasticsCityDAO extends JpaRepository<StasticsCity, Long>, JpaSpecificationExecutor<StasticsCity> {
	
}