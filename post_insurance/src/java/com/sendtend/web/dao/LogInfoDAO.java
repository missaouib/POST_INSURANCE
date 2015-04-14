/**
 * 
 */
package com.sendtend.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sendtend.web.entity.main.LogInfo;

public interface LogInfoDAO extends JpaRepository<LogInfo, Long>, JpaSpecificationExecutor<LogInfo> {

}