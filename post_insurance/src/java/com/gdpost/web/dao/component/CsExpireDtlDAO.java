package com.gdpost.web.dao.component;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.component.CsExpireDtl;

/**
 * 
 */
public interface CsExpireDtlDAO extends JpaRepository<CsExpireDtl, Long>, JpaSpecificationExecutor<CsExpireDtl> {

}