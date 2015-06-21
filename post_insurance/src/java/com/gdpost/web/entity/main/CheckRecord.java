package com.gdpost.web.entity.main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;

/**
 * TCheckRecordDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_check_record_dtl")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.CheckRecordDtl")
public class CheckRecord implements Idable<Long> {

	// Fields

	private Long id;
	private String checker;
	private String checkBatch;
	private String needFix;
	private Policy policy;
	private Boolean scanError;
	private String formNo;
	private String netType;
	private String prdName;
	private String payMethod;
	private String keyInfo;
	private String importanceInfo;
	private String elseInfo;
	private String docError;
	private String docMiss;
	private String netName;
	private String remark;

	// Constructors

	/** default constructor */
	public CheckRecord() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="checker")
	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	@Column(name="check_batch")
	public String getCheckBatch() {
		return checkBatch;
	}

	public void setCheckBatch(String checkBatch) {
		this.checkBatch = checkBatch;
	}

	@Column(name="need_fix")
	public String getNeedFix() {
		return needFix;
	}

	public void setNeedFix(String needFix) {
		this.needFix = needFix;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "policy_no", referencedColumnName="policy_no")
	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Column(name="scan_error")
	public Boolean getScanError() {
		return scanError;
	}

	public void setScanError(Boolean scanError) {
		this.scanError = scanError;
	}

	@Column(name="form_no")
	public String getFormNo() {
		return formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	@Column(name="net_type")
	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	@Column(name="prd_name")
	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	@Column(name="pay_method")
	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	@Column(name="key_info")
	public String getKeyInfo() {
		return keyInfo;
	}

	public void setKeyInfo(String keyInfo) {
		this.keyInfo = keyInfo;
	}

	@Column(name="importance_info")
	public String getImportanceInfo() {
		return importanceInfo;
	}

	public void setImportanceInfo(String importanceInfo) {
		this.importanceInfo = importanceInfo;
	}

	@Column(name="else_info")
	public String getElseInfo() {
		return elseInfo;
	}

	public void setElseInfo(String elseInfo) {
		this.elseInfo = elseInfo;
	}

	@Column(name="doc_error")
	public String getDocError() {
		return docError;
	}

	public void setDocError(String docError) {
		this.docError = docError;
	}

	@Column(name="doc_miss")
	public String getDocMiss() {
		return docMiss;
	}

	public void setDocMiss(String docMiss) {
		this.docMiss = docMiss;
	}

	@Column(name="net_name")
	public String getNetName() {
		return netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}