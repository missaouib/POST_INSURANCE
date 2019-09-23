package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.insurance.PayList;

/**
 * Interface for TPayFailListDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface PayListDAO extends JpaRepository<PayList, Long>, JpaSpecificationExecutor<PayList> {
}