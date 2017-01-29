package com.gdpost.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.basedata.RenewalType;

/**
 * A data access object (DAO) providing persistence and search support for
 * TRenewalType entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see com.gdpost.web.entity.basedata.RenewalType
 * @author MyEclipse Persistence Tools
 */
public interface RenewalTypeDAO extends JpaRepository<RenewalType, Long>, JpaSpecificationExecutor<RenewalType> {
	RenewalType getByTypeName(String typeName);

	List<RenewalType> getByTypeFlag(int typeFlag);

}