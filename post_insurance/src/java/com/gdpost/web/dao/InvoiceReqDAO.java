package com.gdpost.web.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.main.InvoiceReq;

/**
 * A data access object (DAO) providing persistence and search support for
 * TIssue entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see com.gdpost.web.entity.main.Issue
 * @author MyEclipse Persistence Tools
 */
public interface InvoiceReqDAO extends JpaRepository<InvoiceReq, Long>, JpaSpecificationExecutor<InvoiceReq> {
	InvoiceReq getByPolicyPolicyNoAndFeeDateBetween(String policyNo, Date feeDate1, Date feeDate2);
}