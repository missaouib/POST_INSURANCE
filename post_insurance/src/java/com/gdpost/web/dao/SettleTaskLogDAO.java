/**
 * 
 */
package com.gdpost.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.insurance.SettleTaskLog;

public interface SettleTaskLogDAO extends JpaRepository<SettleTaskLog, Long>, JpaSpecificationExecutor<SettleTaskLog> {

	List<SettleTaskLog> findBySettleTaskId(Long id);
	
	List<SettleTaskLog> findBySettleTaskIdAndIsFollowOrderByIdDesc(Long id, Boolean isFollow);
}