package com.gdpost.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.gdpost.web.entity.insurance.Inquire;

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
public interface InquireDAO extends JpaRepository<Inquire, Long>, JpaSpecificationExecutor<Inquire> {

	Inquire getByInquireNo(String InquireNo);

	@Query(name="getInquireSubtypeList",
			value="select distinct inquire_subtype from t_inquire;",
			nativeQuery=true)
	List<String> getInquireSubtypeList();
}