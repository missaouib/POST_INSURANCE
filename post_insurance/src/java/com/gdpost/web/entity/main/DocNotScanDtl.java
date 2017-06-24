package com.gdpost.web.entity.main;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.gdpost.web.entity.Idable;

/**
 * TNotScanDocDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_doc_not_scan_dtl", uniqueConstraints = @UniqueConstraint(columnNames = "policy_no"))

public class DocNotScanDtl implements Idable<Long> {

	// Fields

	private Long id;
	private String organName;
	private String formNo;
	private String policyNo;
	private Date feeDate;
	private Date signDate;
	private Date validDate;
	private String holder;
	private String insured;
	private Double policyFee;
	private String feeType;
	private String salesName;
	private String bankName;
	private Long operateId;

	// Constructors

	/** default constructor */
	public DocNotScanDtl() {
	}

	/** full constructor */
	public DocNotScanDtl(String organName, String formNo, String policyNo, Date feeDate, Date signDate, Date validDate,
			String holder, String insured, Double policyFee, String feeType, String salesName, String bankName) {
		this.organName = organName;
		this.formNo = formNo;
		this.policyNo = policyNo;
		this.feeDate = feeDate;
		this.signDate = signDate;
		this.validDate = validDate;
		this.holder = holder;
		this.insured = insured;
		this.policyFee = policyFee;
		this.feeType = feeType;
		this.salesName = salesName;
		this.bankName = bankName;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "organ_name", length = 64)
	public String getOrganName() {
		return this.organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	@Column(name = "form_no", length = 15)
	public String getFormNo() {
		return this.formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	@Column(name = "policy_no", unique = true, length = 14)
	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fee_date", length = 10)
	public Date getFeeDate() {
		return this.feeDate;
	}

	public void setFeeDate(Date feeDate) {
		this.feeDate = feeDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "sign_date", length = 10)
	public Date getSignDate() {
		return this.signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "valid_date", length = 10)
	public Date getValidDate() {
		return this.validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	@Column(name = "holder", length = 32)
	public String getHolder() {
		return this.holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	@Column(name = "insured", length = 32)
	public String getInsured() {
		return this.insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	@Column(name = "policy_fee", precision = 22, scale = 0)
	public Double getPolicyFee() {
		return this.policyFee;
	}

	public void setPolicyFee(Double policyFee) {
		this.policyFee = policyFee;
	}

	@Column(name = "fee_type", length = 12)
	public String getFeeType() {
		return this.feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	@Column(name = "sales_name", length = 12)
	public String getSalesName() {
		return this.salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	@Column(name = "bank_name", length = 128)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "operate_id")
	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

}