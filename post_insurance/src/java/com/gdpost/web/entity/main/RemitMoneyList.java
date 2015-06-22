package com.gdpost.web.entity.main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnTransformer;

import com.gdpost.web.entity.Idable;

/**
 * TCheckRecordDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_remit_money_list")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.RemitMoneyList")
public class RemitMoneyList implements Idable<Long> {

	// Fields

	private Long id;
	private String orgCode;
	private String orgName;
	private String policyNo;
	private Date shouldDate;
	private Date toDate;
	private String policyStatus;
	private String failDesc;
	private String prdCode;
	private String prdName;
	private String insuranceFlag;
	private Integer frequence;
	private String feeFrequency;
	private String policyFee;
	private String holder;
	private String insured;
	private String holderAddr;
	private String holderPhone;
	private String holderMobile;
	private String serverCode;
	private String serverName;
	private String serverPhone;
	private Long operateId;
	private Date operateTime;

	// Constructors

	/** default constructor */
	public RemitMoneyList() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="org_code")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name="org_name")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name="policy_no")
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name="should_date")
	public Date getShouldDate() {
		return shouldDate;
	}

	public void setShouldDate(Date shouldDate) {
		this.shouldDate = shouldDate;
	}

	@Column(name="to_date")
	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@Column(name="policy_status")
	public String getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
	}

	@Column(name="fail_desc")
	public String getFailDesc() {
		return failDesc;
	}

	public void setFailDesc(String failDesc) {
		this.failDesc = failDesc;
	}

	@Column(name="prd_code")
	public String getPrdCode() {
		return prdCode;
	}

	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}

	@Column(name="prd_code")
	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	@Column(name="insurance_flag")
	public String getInsuranceFlag() {
		return insuranceFlag;
	}

	public void setInsuranceFlag(String insuranceFlag) {
		this.insuranceFlag = insuranceFlag;
	}

	@Column(name="frequence")
	public Integer getFrequence() {
		return frequence;
	}

	public void setFrequence(Integer frequence) {
		this.frequence = frequence;
	}

	@Column(name="fee_frequency")
	public String getFeeFrequency() {
		return feeFrequency;
	}

	public void setFeeFrequency(String feeFrequency) {
		this.feeFrequency = feeFrequency;
	}

	@Column(name = "policy_fee", length = 256)
	@ColumnTransformer(
			forColumn="policy_fee",
			read="cast(aes_decrypt(unhex(policy_fee), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getPolicyFee() {
		return policyFee;
	}

	public void setPolicyFee(String policyFee) {
		this.policyFee = policyFee;
	}

	@Column(name = "holder", length = 256)
	@ColumnTransformer(
			forColumn="holder",
			read="cast(aes_decrypt(unhex(holder), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	@Column(name = "policy_fee", length = 256)
	@ColumnTransformer(
			forColumn="policy_fee",
			read="cast(aes_decrypt(unhex(policy_fee), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getInsured() {
		return insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	@Column(name = "holder_addr", length = 256)
	@ColumnTransformer(
			forColumn="holder_addr",
			read="cast(aes_decrypt(unhex(holder_addr), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getHolderAddr() {
		return holderAddr;
	}

	public void setHolderAddr(String holderAddr) {
		this.holderAddr = holderAddr;
	}

	@Column(name = "holder_phone", length = 256)
	@ColumnTransformer(
			forColumn="holder_phone",
			read="cast(aes_decrypt(unhex(holder_phone), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getHolderPhone() {
		return holderPhone;
	}

	public void setHolderPhone(String holderPhone) {
		this.holderPhone = holderPhone;
	}

	@Column(name = "holder_mobile", length = 256)
	@ColumnTransformer(
			forColumn="holder_mobile",
			read="cast(aes_decrypt(unhex(holder_mobile), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getHolderMobile() {
		return holderMobile;
	}

	public void setHolderMobile(String holderMobile) {
		this.holderMobile = holderMobile;
	}

	@Column(name="server_code")
	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	@Column(name="server_name")
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	@Column(name="server_phone")
	public String getServerPhone() {
		return serverPhone;
	}

	public void setServerPhone(String serverPhone) {
		this.serverPhone = serverPhone;
	}

	@Column(name="operate_id")
	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Column(name="operate_time")
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
}