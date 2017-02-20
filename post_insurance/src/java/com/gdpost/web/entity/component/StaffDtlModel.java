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
public class StaffDtlModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3288285191150301593L;
	private Long id;
	private String organName;
	private String policyNo;
	private String holder;
	private Double policyFee;
	private String prodName;
	private Date policyDate;
	private String bankName;
	private String feeFrequency;
	private String perm;
	private String netFlag;
	// Constructors

	/** default constructor */
	public StaffDtlModel() {
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
	
	@Column(name="holder")
	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
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

	@Column(name="bank_name")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name="fee_frequency")
	public String getFeeFrequency() {
		return feeFrequency;
	}

	public void setFeeFrequency(String feeFrequency) {
		this.feeFrequency = feeFrequency;
	}

	@Column(name="perm")
	public String getPerm() {
		return perm;
	}

	public void setPerm(String perm) {
		this.perm = perm;
	}

	@Column(name="net_flag")
	public String getNetFlag() {
		return netFlag;
	}

	public void setNetFlag(String netFlag) {
		this.netFlag = netFlag;
	}

}
