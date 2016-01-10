package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.main.PaySuccessList;

/**
 * Interface for TPayFailListDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface PaySuccessListDAO extends JpaRepository<PaySuccessList, Long>, JpaSpecificationExecutor<PaySuccessList> {
}