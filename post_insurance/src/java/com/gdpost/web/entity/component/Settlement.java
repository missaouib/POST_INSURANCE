package com.gdpost.web.entity.component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gdpost.web.entity.Idable;

/**
 * settlement entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_settlement")
public class Settlement implements Idable<Long> {

	// Fields

	private Long id;
	private Long orgId;
	private String insured;
	private String reporter;
	private String reporterPhone;
	private Date caseDate;
	private String caseType;
	private Date reporteDate;
	private Date recordDate;
	private Date closeDate;
	private Double payFee;
	private String caseStatus;
	private String remark;
	private Set<SettlementPolicy> settlementPolicies = new HashSet<SettlementPolicy>(
			0);
	private Set<SettlementReport> settlementReports = new HashSet<SettlementReport>(
			0);
	private Set<SettlementCheck> settlementChecks = new HashSet<SettlementCheck>(
			0);

	// Constructors

	/** default constructor */
	public Settlement() {
	}

	/** full constructor */
	public Settlement(Long orgId, String insured, String reporter,
			String reporterPhone, Date caseDate, String caseType,
			Date reporteDate, Date recordDate, Date closeDate, Double payFee,
			String caseStatus, String remark,
			Set<SettlementPolicy> settlementPolicies,
			Set<SettlementReport> settlementReports,
			Set<SettlementCheck> settlementChecks) {
		this.orgId = orgId;
		this.insured = insured;
		this.reporter = reporter;
		this.reporterPhone = reporterPhone;
		this.caseDate = caseDate;
		this.caseType = caseType;
		this.reporteDate = reporteDate;
		this.recordDate = recordDate;
		this.closeDate = closeDate;
		this.payFee = payFee;
		this.caseStatus = caseStatus;
		this.remark = remark;
		this.settlementPolicies = settlementPolicies;
		this.settlementReports = settlementReports;
		this.settlementChecks = settlementChecks;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "org_id")
	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Column(name = "insured", length = 32)
	public String getInsured() {
		return this.insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	@Column(name = "reporter", length = 32)
	public String getReporter() {
		return this.reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	@Column(name = "reporter_phone", length = 32)
	public String getReporterPhone() {
		return this.reporterPhone;
	}

	public void setReporterPhone(String reporterPhone) {
		this.reporterPhone = reporterPhone;
	}

	@Column(name = "case_date", length = 10)
	public Date getCaseDate() {
		return this.caseDate;
	}

	public void setCaseDate(Date caseDate) {
		this.caseDate = caseDate;
	}

	@Column(name = "case_type", length = 16)
	public String getCaseType() {
		return this.caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	@Column(name = "reporte_date", length = 10)
	public Date getReporteDate() {
		return this.reporteDate;
	}

	public void setReporteDate(Date reporteDate) {
		this.reporteDate = reporteDate;
	}

	@Column(name = "record_date", length = 10)
	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	@Column(name = "close_date", length = 10)
	public Date getCloseDate() {
		return this.closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	@Column(name = "pay_fee", precision = 22, scale = 0)
	public Double getPayFee() {
		return this.payFee;
	}

	public void setPayFee(Double payFee) {
		this.payFee = payFee;
	}

	@Column(name = "case_status", length = 16)
	public String getCaseStatus() {
		return this.caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	@Column(name = "remark", length = 1024)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "settlement")
	public Set<SettlementPolicy> getSettlementPolicies() {
		return this.settlementPolicies;
	}

	public void setSettlementPolicies(
			Set<SettlementPolicy> settlementPolicies) {
		this.settlementPolicies = settlementPolicies;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "settlement")
	public Set<SettlementReport> getSettlementReports() {
		return this.settlementReports;
	}

	public void setSettlementReports(Set<SettlementReport> settlementReports) {
		this.settlementReports = settlementReports;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "settlement")
	public Set<SettlementCheck> getSettlementChecks() {
		return this.settlementChecks;
	}

	public void setSettlementChecks(Set<SettlementCheck> settlementChecks) {
		this.settlementChecks = settlementChecks;
	}

}