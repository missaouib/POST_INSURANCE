/**
 * 
 */
package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.main.LogInfo;

public interface LogInfoDAO extends JpaRepository<LogInfo, Long>, JpaSpecificationExecutor<LogInfo> {

}