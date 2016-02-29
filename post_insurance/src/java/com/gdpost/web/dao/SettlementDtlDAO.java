/**
 * 
 */
package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.component.SettlementDtl;

public interface SettlementDtlDAO extends JpaRepository<SettlementDtl, Long>, JpaSpecificationExecutor<SettlementDtl> {
}