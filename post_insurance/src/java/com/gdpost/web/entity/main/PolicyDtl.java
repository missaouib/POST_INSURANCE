package com.gdpost.web.entity.main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnTransformer;

import com.gdpost.web.entity.Idable;

/**
 * TPolicyDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_policy_dtl")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.PolicyDtl")
public class PolicyDtl implements Idable<Long> {

	// Fields

	private Long id;
	private String organCode;
	private String policyNo;
	private String holder;
	private Integer holderSexy;
	private String insured;
	private String prodName;
	private String insuredAmount;
	private String policyFee;
	private String salesName;
	private Date policyDate;
	private String holderPhone;
	private String holderMobile;
	private String holderAddr;
	private String holderPostcode;
	private Integer holderCardType;
	private String holderCardNum;
	private Long operateId;
	private Date operateTime;

	// Constructors

	/** default constructor */
	public PolicyDtl() {
	}

	/** full constructor */
	public PolicyDtl(String organCode, String policyNo, String holder, Integer holderSexy, String insured, String prodName, String insuredAmount,
			String policyFee, String salesName, Date policyDate, String holderPhone, String holderMobile, String holderAddr, String holderPostcode,
			Integer holderCardType, String holderCardNum, Long operateId, Date operateTime) {
		this.organCode = organCode;
		this.policyNo = policyNo;
		this.holder = holder;
		this.holderSexy = holderSexy;
		this.insured = insured;
		this.prodName = prodName;
		this.insuredAmount = insuredAmount;
		this.policyFee = policyFee;
		this.salesName = salesName;
		this.policyDate = policyDate;
		this.holderPhone = holderPhone;
		this.holderMobile = holderMobile;
		this.holderAddr = holderAddr;
		this.holderPostcode = holderPostcode;
		this.holderCardType = holderCardType;
		this.holderCardNum = holderCardNum;
		this.operateId = operateId;
		this.operateTime = operateTime;
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

	@Column(name = "organ_code", length = 60)
	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	@Column(name = "policy_no", length = 18)
	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name = "holder", length = 256)
	@ColumnTransformer(
			forColumn="holder",
			read="cast(aes_decrypt(unhex(holder), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getHolder() {
		return this.holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	@Column(name = "holder_sexy")
	public Integer getHolderSexy() {
		return this.holderSexy;
	}

	public void setHolderSexy(Integer holderSexy) {
		this.holderSexy = holderSexy;
	}

	@Column(name = "insured", length = 256)
	@ColumnTransformer(
			forColumn="insured",
			read="cast(aes_decrypt(unhex(insured), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getInsured() {
		return this.insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	@Column(name = "prod_name", length = 128)
	public String getProdName() {
		return this.prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	@Column(name = "insured_amount", length = 256)
	public String getInsuredAmount() {
		return this.insuredAmount;
	}

	public void setInsuredAmount(String insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	@Column(name = "policy_fee", length = 256)
	@ColumnTransformer(
			forColumn="policy_fee",
			read="cast(aes_decrypt(unhex(policy_fee), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getPolicyFee() {
		return this.policyFee;
	}

	public void setPolicyFee(String policyFee) {
		this.policyFee = policyFee;
	}

	@Column(name = "sales_name", length = 256)
	public String getSalesName() {
		return this.salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "policy_date", length = 10)
	public Date getPolicyDate() {
		return this.policyDate;
	}

	public void setPolicyDate(Date policyDate) {
		this.policyDate = policyDate;
	}

	@Column(name = "holder_phone", length = 256)
	@ColumnTransformer(
			forColumn="holder_phone",
			read="cast(aes_decrypt(unhex(holder_phone), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getHolderPhone() {
		return this.holderPhone;
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
		return this.holderMobile;
	}

	public void setHolderMobile(String holderMobile) {
		this.holderMobile = holderMobile;
	}

	@Column(name = "holder_addr", length = 256)
	@ColumnTransformer(
			forColumn="holder_addr",
			read="cast(aes_decrypt(unhex(holder_addr), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getHolderAddr() {
		return this.holderAddr;
	}

	public void setHolderAddr(String holderAddr) {
		this.holderAddr = holderAddr;
	}

	@Column(name = "holder_postcode", length = 6)
	public String getHolderPostcode() {
		return this.holderPostcode;
	}

	public void setHolderPostcode(String holderPostcode) {
		this.holderPostcode = holderPostcode;
	}

	@Column(name = "holder_card_type")
	public Integer getHolderCardType() {
		return this.holderCardType;
	}

	public void setHolderCardType(Integer holderCardType) {
		this.holderCardType = holderCardType;
	}

	@Column(name = "holder_card_num", length = 256)
	@ColumnTransformer(
			forColumn="holder_card_num",
			read="cast(aes_decrypt(unhex(holder_card_num), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getHolderCardNum() {
		return this.holderCardNum;
	}

	public void setHolderCardNum(String holderCardNum) {
		this.holderCardNum = holderCardNum;
	}

	@Column(name = "operate_id")
	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "operate_time", length = 10)
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

}