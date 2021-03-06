package com.gdpost.web.entity.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * VCallFailList entity. @author MyEclipse Persistence Tools
 */
@Entity
public class TuiBaoDtlModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3288285191150301593L;
	private Long id;
	private String organName;
	private String policyNo;
	private Double policyFee;
	private String prodName;
	private Date policyDate;
	private Date csDate;
	private String csCode;
	private String csNo;
	private String csFee;
	private String holder;
	private String netFlag;
	private String bankName;
	private String staffFlag;
	private String selBankCode;
	private String perm;
	private String duration;
	// Constructors

	/** default constructor */
	public TuiBaoDtlModel() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="policy_no")
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	
	@Column(name="organ_name")
	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	@Column(name="policy_fee")
	public Double getPolicyFee() {
		return policyFee;
	}

	public void setPolicyFee(Double policyFee) {
		this.policyFee = policyFee;
	}

	@Column(name="prod_name")
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	@Column(name="policy_date")
	public Date getPolicyDate() {
		return policyDate;
	}

	public void setPolicyDate(Date policyDate) {
		this.policyDate = policyDate;
	}

	@Column(name="cs_date")
	public Date getCsDate() {
		return csDate;
	}

	public void setCsDate(Date csDate) {
		this.csDate = csDate;
	}

	@Column(name="cs_code")
	public String getCsCode() {
		return csCode;
	}

	public void setCsCode(String csCode) {
		this.csCode = csCode;
	}

	@Column(name="cs_no")
	public String getCsNo() {
		return csNo;
	}

	public void setCsNo(String csNo) {
		this.csNo = csNo;
	}

	@Column(name="cs_fee")
	public String getCsFee() {
		return csFee;
	}

	public void setCsFee(String csFee) {
		this.csFee = csFee;
	}

	@Column(name="holder")
	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	@Column(name="net_flag")
	public String getNetFlag() {
		return netFlag;
	}

	public void setNetFlag(String netFlag) {
		this.netFlag = netFlag;
	}
	@Column(name="bank_name")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name="staff_flag")
	public String getStaffFlag() {
		return staffFlag;
	}

	public void setStaffFlag(String staffFlag) {
		this.staffFlag = staffFlag;
	}

	@Column(name="sel_bank_code")
	public String getSelBankCode() {
		return selBankCode;
	}

	public void setSelBankCode(String selBankCode) {
		this.selBankCode = selBankCode;
	}

	@Column(name="perm")
	public String getPerm() {
		return perm;
	}

	public void setPerm(String perm) {
		this.perm = perm;
	}

	@Column(name="duration")
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
}
