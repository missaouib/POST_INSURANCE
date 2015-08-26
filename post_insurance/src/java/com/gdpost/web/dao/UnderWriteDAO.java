package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.main.UnderWrite;

/**
 * Interface for TUnderWriteDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface UnderWriteDAO extends JpaRepository<UnderWrite, Long>, JpaSpecificationExecutor<UnderWrite> {
}