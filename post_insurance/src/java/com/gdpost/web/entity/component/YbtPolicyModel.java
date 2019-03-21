package com.gdpost.web.entity.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * VCallFailList entity. @author MyEclipse Persistence Tools
 */
@Entity
public class YbtPolicyModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6953817886114363056L;
	private String policyNo;
	private String orgName;
	private String bankName;
	private String formNo;
	private String holder;
	private String prodName;
	private String policyFee;
	private Date policyDate;
	private Boolean hasErr;
	private Boolean hasScan;
	private String fixStatus;
	

	/** default constructor */
	public YbtPolicyModel() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="policy_no")
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name="organ_name")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String organName) {
		this.orgName = organName;
	}

	@Column(name="bank_name")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name="form_no")
	public String getFormNo() {
		return formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	@Column(name="holder")
	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	@Column(name="prod_name")
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	@Column(name="policy_fee")
	public String getPolicyFee() {
		return policyFee;
	}

	public void setPolicyFee(String policyFee) {
		this.policyFee = policyFee;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="policy_date")
	public Date getPolicyDate() {
		return policyDate;
	}

	public void setPolicyDate(Date policyDate) {
		this.policyDate = policyDate;
	}

	@Column(name="has_err")
	public Boolean getHasErr() {
		return hasErr;
	}

	public void setHasErr(Boolean hasErr) {
		this.hasErr = hasErr;
	}

	@Column(name="has_scan")
	public Boolean getHasScan() {
		return hasScan;
	}

	public void setHasScan(Boolean hasScan) {
		this.hasScan = hasScan;
	}

	@Column(name="fix_status")
	public String getFixStatus() {
		return fixStatus;
	}

	public void setFixStatus(String fixStatus) {
		this.fixStatus = fixStatus;
	}

}
