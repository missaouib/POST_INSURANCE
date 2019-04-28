/**
 * 
 */
package com.gdpost.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.component.GsettleLog;

public interface GsettleLogDAO extends JpaRepository<GsettleLog, Long>, JpaSpecificationExecutor<GsettleLog> {

	List<GsettleLog> findByGsettleId(Long id);
	
	List<GsettleLog> findByGsettleIdAndIsFollowOrderByIdDesc(Long id, Boolean isFollow);
}