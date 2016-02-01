/**
 * 
 */
package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.component.Settlement;

public interface SettlementDAO extends JpaRepository<Settlement, Long>, JpaSpecificationExecutor<Settlement> {
}