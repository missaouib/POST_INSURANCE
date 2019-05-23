package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.insurance.PayFailList;

/**
 * Interface for TPayFailListDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface PayFailListDAO extends JpaRepository<PayFailList, Long>, JpaSpecificationExecutor<PayFailList> {
}