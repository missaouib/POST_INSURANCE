package com.gdpost.web.entity.main;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnTransformer;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.component.Staff;

/**
 * TPolicyDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_policy_dtl")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.PolicyDtl")
public class PolicyDtl implements Idable<Long>,Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6965025646998800405L;
	private Long id;
	private String organCode;
	private String policyNo;
	private String holder;
	private String holderSexy;
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
	private String holderCardType;
	private String holderCardNum;
	private Long operateId;
	private Date operateTime;
	
	private String provCode;
	private String cityCode;
	private String saleChannel;
	private String saleType;
	private String bankCode;
	private String bankName;
	private String bankType;
	private String bankAttr;
	private String formNo;
	private String holderId;
	private Date holderCardValid;
	private Integer holderAge;
	private String relation;
	private String holderEmail;
	private String insuredId;
	private Integer insuredAge;
	private String planCode;
	private String planName;
	private String prodCode;
	private String feeType;
	private Integer feeNum;
	private String duration;
	private String durationType;
	private Double ywfx;
	private Double zdjb;
	private Double zjc;
	private Double jtyw;
	private Double sxfx;
	private Double wcnjf;
	private Time policyTime;
	private Date policyValidDate;
	private Date policyInvalidDate;
	private String policyStatus;
	private String giftFlag;
	private String policySendType;
	private Date printDate;
	private String SalesCode;
	
	private Staff staff;

	// Constructors

	/** default constructor */
	public PolicyDtl() {
	}

	/** full constructor */
	public PolicyDtl(String organCode, String policyNo, String holder, String holderSexy, String insured, String prodName, String insuredAmount,
			String policyFee, String salesName, Date policyDate, String holderPhone, String holderMobile, String holderAddr, String holderPostcode,
			String holderCardType, String holderCardNum, Long operateId, Date operateTime) {
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
	public String getHolderSexy() {
		return this.holderSexy;
	}

	public void setHolderSexy(String holderSexy) {
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
	public String getHolderCardType() {
		return this.holderCardType;
	}

	public void setHolderCardType(String holderCardType) {
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

	@OneToOne(optional=true)
	@JoinColumn(name="holder_card_num", referencedColumnName="id_Card", insertable=false, updatable=false, nullable=true)
	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	@Column(name = "prov_code")
	public String getProvCode() {
		return provCode;
	}

	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}

	@Column(name = "city_code")
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(name = "sale_channel")
	public String getSaleChannel() {
		return saleChannel;
	}

	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}

	@Column(name = "sale_type")
	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	@Column(name = "bank_code")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "bank_name")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "bank_type")
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	@Column(name = "bank_attr")
	public String getBankAttr() {
		return bankAttr;
	}

	public void setBankAttr(String bankAttr) {
		this.bankAttr = bankAttr;
	}

	@Column(name = "form_no")
	public String getFormNo() {
		return formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	@Column(name = "holder_id")
	public String getHolderId() {
		return holderId;
	}

	public void setHolderId(String holderId) {
		this.holderId = holderId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "holder_card_valid")
	public Date getHolderCardValid() {
		return holderCardValid;
	}

	public void setHolderCardValid(Date holderCardValid) {
		this.holderCardValid = holderCardValid;
	}

	@Column(name = "holder_age")
	public Integer getHolderAge() {
		return holderAge;
	}

	public void setHolderAge(Integer holderAge) {
		this.holderAge = holderAge;
	}

	@Column(name = "relation")
	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	@Column(name = "holder_email")
	public String getHolderEmail() {
		return holderEmail;
	}

	public void setHolderEmail(String holderEmail) {
		this.holderEmail = holderEmail;
	}

	@Column(name = "insured_id")
	public String getInsuredId() {
		return insuredId;
	}

	public void setInsuredId(String insuredId) {
		this.insuredId = insuredId;
	}

	@Column(name = "insured_age")
	public Integer getInsuredAge() {
		return insuredAge;
	}

	public void setInsuredAge(Integer insuredAge) {
		this.insuredAge = insuredAge;
	}

	@Column(name = "plan_code")
	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	@Column(name = "plan_name")
	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	@Column(name = "prod_code")
	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	@Column(name = "fee_type")
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	@Column(name = "fee_num")
	public Integer getFeeNum() {
		return feeNum;
	}

	public void setFeeNum(Integer feeNum) {
		this.feeNum = feeNum;
	}

	@Column(name = "duration")
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Column(name = "duration_type")
	public String getDurationType() {
		return durationType;
	}

	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}

	@Column(name = "ywfx")
	public Double getYwfx() {
		return ywfx;
	}

	public void setYwfx(Double ywfx) {
		this.ywfx = ywfx;
	}

	@Column(name = "zdjb")
	public Double getZdjb() {
		return zdjb;
	}

	public void setZdjb(Double zdjb) {
		this.zdjb = zdjb;
	}

	@Column(name = "zjc")
	public Double getZjc() {
		return zjc;
	}

	public void setZjc(Double zjc) {
		this.zjc = zjc;
	}

	@Column(name = "jtyw")
	public Double getJtyw() {
		return jtyw;
	}

	public void setJtyw(Double jtyw) {
		this.jtyw = jtyw;
	}

	@Column(name = "sxfx")
	public Double getSxfx() {
		return sxfx;
	}

	public void setSxfx(Double sxfx) {
		this.sxfx = sxfx;
	}

	@Column(name = "wcnjf")
	public Double getWcnjf() {
		return wcnjf;
	}

	public void setWcnjf(Double wcnjf) {
		this.wcnjf = wcnjf;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "policy_time")
	public Time getPolicyTime() {
		return policyTime;
	}

	public void setPolicyTime(Time policyTime) {
		this.policyTime = policyTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "policy_valid_date")
	public Date getPolicyValidDate() {
		return policyValidDate;
	}

	public void setPolicyValidDate(Date policyValidDate) {
		this.policyValidDate = policyValidDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "policy_invalid_date")
	public Date getPolicyInvalidDate() {
		return policyInvalidDate;
	}

	public void setPolicyInvalidDate(Date policyInvalidDate) {
		this.policyInvalidDate = policyInvalidDate;
	}

	@Column(name = "policy_status")
	public String getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
	}

	@Column(name = "gift_flag")
	public String getGiftFlag() {
		return giftFlag;
	}

	public void setGiftFlag(String giftFlag) {
		this.giftFlag = giftFlag;
	}

	@Column(name = "policy_send_type")
	public String getPolicySendType() {
		return policySendType;
	}

	public void setPolicySendType(String policySendType) {
		this.policySendType = policySendType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "print_date")
	public Date getPrintDate() {
		return printDate;
	}

	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}

	@Column(name = "sales_code")
	public String getSalesCode() {
		return SalesCode;
	}

	public void setSalesCode(String salesCode) {
		SalesCode = salesCode;
	}
	
	

}