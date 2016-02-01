/**
 * 
 */
package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.component.SettlementReport;

public interface SettlementReportDAO extends JpaRepository<SettlementReport, Long>, JpaSpecificationExecutor<SettlementReport> {
}