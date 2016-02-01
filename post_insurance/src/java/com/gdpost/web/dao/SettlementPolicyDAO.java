/**
 * 
 */
package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.component.SettlementPolicy;

public interface SettlementPolicyDAO extends JpaRepository<SettlementPolicy, Long>, JpaSpecificationExecutor<SettlementPolicy> {
}