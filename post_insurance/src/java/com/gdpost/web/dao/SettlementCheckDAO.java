/**
 * 
 */
package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.component.SettlementCheck;

public interface SettlementCheckDAO extends JpaRepository<SettlementCheck, Long>, JpaSpecificationExecutor<SettlementCheck> {
}