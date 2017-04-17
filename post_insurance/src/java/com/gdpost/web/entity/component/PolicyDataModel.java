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
public class PolicyDataModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3288285191150301593L;
	private Long id;
	private String policyNo;
	private String formNo;
	private String holder;
	private String holderPhone;
	private String holderMobile;
	private String holderCardNum;
	private String insured;
	private String insuredPhone;
	private String insuredCardNum;
	private String prodCode;
	private String prodName;
	private Double policyFee;
	private Double insuredAmount;
	private String feeFrequency;
	private Integer perm;
	private Date policyDate;
	private Date plicyValidDate;
	private String status;
	private String bankCode;
	private String feeType;
	private String bankAccount;
	private String csFlag;
	private String uwFlag;
	
	// Constructors

	/** default constructor */
	public PolicyDataModel() {
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

	@Column(name="policy_fee")
	public Double getPolicyFee() {
		return policyFee;
	}

	public void setPolicyFee(Double policyFee) {
		this.policyFee = policyFee;
	}

	@Column(name="holder_phone")
	public String getHolderPhone() {
		return holderPhone;
	}

	public void setHolderPhone(String holderPhone) {
		this.holderPhone = holderPhone;
	}

	@Column(name="holder_mobile")
	public String getHolderMobile() {
		return holderMobile;
	}

	public void setHolderMobile(String holderMobile) {
		this.holderMobile = holderMobile;
	}

	@Column(name="holder_card_num")
	public String getHolderCardNum() {
		return holderCardNum;
	}

	public void setHolderCardNum(String holderCardNum) {
		this.holderCardNum = holderCardNum;
	}

	@Column(name="insured")
	public String getInsured() {
		return insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	@Column(name="insured_phone")
	public String getInsuredPhone() {
		return insuredPhone;
	}

	public void setInsuredPhone(String insuredPhone) {
		this.insuredPhone = insuredPhone;
	}

	@Column(name="insured_card_num")
	public String getInsuredCardNum() {
		return insuredCardNum;
	}

	public void setInsuredCardNum(String insuredCardNum) {
		this.insuredCardNum = insuredCardNum;
	}

	@Column(name="prod_code")
	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	@Column(name="prod_name")
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	@Column(name="insured_amount")
	public Double getInsuredAmount() {
		return insuredAmount;
	}

	public void setInsuredAmount(Double insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	@Column(name="fee_frequency")
	public String getFeeFrequency() {
		return feeFrequency;
	}

	public void setFeeFrequency(String feeFrequency) {
		this.feeFrequency = feeFrequency;
	}

	@Column(name="perm")
	public Integer getPerm() {
		return perm;
	}

	public void setPerm(Integer perm) {
		this.perm = perm;
	}

	@Column(name="policy_date")
	public Date getPolicyDate() {
		return policyDate;
	}

	public void setPolicyDate(Date policyDate) {
		this.policyDate = policyDate;
	}

	@Column(name="plicy_valid_date")
	public Date getPlicyValidDate() {
		return plicyValidDate;
	}

	public void setPlicyValidDate(Date plicyValidDate) {
		this.plicyValidDate = plicyValidDate;
	}

	@Column(name="status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="bank_code")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name="fee_type")
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	@Column(name="bank_account")
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Column(name="cs_flag")
	public String getCsFlag() {
		return csFlag;
	}

	public void setCsFlag(String csFlag) {
		this.csFlag = csFlag;
	}

	@Column(name="uw_flag")
	public String getUwFlag() {
		return uwFlag;
	}

	public void setUwFlag(String uwFlag) {
		this.uwFlag = uwFlag;
	}
}
