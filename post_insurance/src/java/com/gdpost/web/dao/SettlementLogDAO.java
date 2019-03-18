/**
 * 
 */
package com.gdpost.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.component.SettlementLog;

public interface SettlementLogDAO extends JpaRepository<SettlementLog, Long>, JpaSpecificationExecutor<SettlementLog> {

	List<SettlementLog> findBySettlementId(Long id);
	
	List<SettlementLog> findBySettlementIdAndIsFollowOrderByIdDesc(Long id, Boolean isFollow);
}