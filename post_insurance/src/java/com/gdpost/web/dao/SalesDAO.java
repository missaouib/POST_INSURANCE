package com.gdpost.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.basedata.Sales;

/**
 * A data access object (DAO) providing persistence and search support for TSales
 * entities. Transaction control of the save(), update() and delete() operations
 * must be handled externally by senders of these methods or must be manually
 * added to each of these methods for data to be persisted to the JPA datastore.
 * 
 * @see com.gdpost.web.entity.basedata.Sales
 * @author MyEclipse Persistence Tools
 */
public interface SalesDAO extends JpaRepository<Sales, Long>, JpaSpecificationExecutor<Sales> {
	List<Sales> getBySalesName(String name);
	List<Sales> getByPhone(String phone);
}