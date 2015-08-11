package com.gdpost.web.dao.component;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.component.VCallFailList;

/**
 * Interface for VCallFailListDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IVCallFailListDAO extends JpaRepository<VCallFailList, Long>, JpaSpecificationExecutor<VCallFailList> {
	
}