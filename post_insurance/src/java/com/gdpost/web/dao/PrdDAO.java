package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.basedata.Prd;

/**
 * A data access object (DAO) providing persistence and search support for TPrd
 * entities. Transaction control of the save(), update() and delete() operations
 * must be handled externally by senders of these methods or must be manually
 * added to each of these methods for data to be persisted to the JPA datastore.
 * 
 * @see com.gdpost.web.entity.basedata.Prd
 * @author MyEclipse Persistence Tools
 */
public interface PrdDAO extends JpaRepository<Prd, Long>, JpaSpecificationExecutor<Prd> {
	Prd getByPrdCode(String prdCode);

}