/**
 * 
 */
package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.component.GsettleDtl;

public interface GsettleDtlDAO extends JpaRepository<GsettleDtl, Long>, JpaSpecificationExecutor<GsettleDtl> {

	GsettleDtl getByGsettleId(Long id);

	GsettleDtl getByGpolicyNo(String gpolicyNo);
}