package com.gdpost.web.entity.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnTransformer;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.basedata.Prd;

/**
 * policy entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_policy", catalog = "postinsurance")
public class Policy implements Idable<Long> {

	// Fields

	private Long id;
	private Prd prd;
	private Organization organization;
	private String policyNo;
	private String formNo;
	private String cityName;
	private String areaName;
	private String prodName;
	private Long bankId;
	private String bankName;
	private String salesName;
	private String salesId;
	private Integer status;
	private Integer perm;
	private String policyFee;
	private String copies;
	private String insuredAmount;
	private String holder;
	private String holderAddr;
	private String holderPhone;
	private String holderMobile;
	private Integer holderCardType;
	private String holderCardNum;
	private Integer relation;
	private String insured;
	private Integer insuredCardType;
	private String insuredCardNum;
	private String insuredMobile;
	private String insuredPhone;
	private String insuredAddr;
	private Date policyDate;
	private Integer renewalSucessFlag;
	private Integer resetValidFlag;
	private Long operateId;
	private String operateName;
	private Date operateTime;
	private List<RenewalDtl> renewalDtls = new ArrayList<RenewalDtl>(0);
	private List<ConservationDtl> conservationDtls = new ArrayList<ConservationDtl>(0);
	private List<RenewalFeeDtl> renewalFeeDtls = new ArrayList<RenewalFeeDtl>(0);
	private List<Issue> issues = new ArrayList<Issue>(0);
	private List<CheckRecordDtl> checkRecordDtls = new ArrayList<CheckRecordDtl>(0);
	private List<CheckWriteDtl> checkWriteDtls = new ArrayList<CheckWriteDtl>(0);
	private List<CallFail> callFails = new ArrayList<CallFail>(0);

	// Constructors

	/** default constructor */
	public Policy() {
	}

	/** full constructor */
	public Policy(Prd TPrd, Organization Organization, String cityName, String areaName, String prodName, Long bankId, String bankName, String salesName,
			String salesId, Integer status, Integer perm, String policyFee, String copies, String insuredAmount, String holder, String holderAddr,
			String holderPhone, String holderMobile, Integer holderCardType, String holderCardNum, Integer relation, String insured, Integer insuredCardType,
			String insuredCardNum, String insuredMobile, String insuredPhone, String insuredAddr, Date policyDate, Integer renewalSucessFlag,
			Integer resetValidFlag, List<RenewalDtl> TRenewalDtls, List<ConservationDtl> TConservationDtls, List<RenewalFeeDtl> TRenewalFeeDtls,
			List<Issue> TIssues, List<CheckRecordDtl> TCheckRecordDtls, List<CheckWriteDtl> TCheckWriteDtls, List<CallFail> TCallFails) {
		this.prd = TPrd;
		this.organization = Organization;
		this.cityName = cityName;
		this.areaName = areaName;
		this.prodName = prodName;
		this.bankId = bankId;
		this.bankName = bankName;
		this.salesName = salesName;
		this.salesId = salesId;
		this.status = status;
		this.perm = perm;
		this.policyFee = policyFee;
		this.copies = copies;
		this.insuredAmount = insuredAmount;
		this.holder = holder;
		this.holderAddr = holderAddr;
		this.holderPhone = holderPhone;
		this.holderMobile = holderMobile;
		this.holderCardType = holderCardType;
		this.holderCardNum = holderCardNum;
		this.relation = relation;
		this.insured = insured;
		this.insuredCardType = insuredCardType;
		this.insuredCardNum = insuredCardNum;
		this.insuredMobile = insuredMobile;
		this.insuredPhone = insuredPhone;
		this.insuredAddr = insuredAddr;
		this.policyDate = policyDate;
		this.renewalSucessFlag = renewalSucessFlag;
		this.resetValidFlag = resetValidFlag;
		this.renewalDtls = TRenewalDtls;
		this.conservationDtls = TConservationDtls;
		this.renewalFeeDtls = TRenewalFeeDtls;
		this.issues = TIssues;
		this.checkRecordDtls = TCheckRecordDtls;
		this.checkWriteDtls = TCheckWriteDtls;
		this.callFails = TCallFails;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prod_id")
	public Prd getPrd() {
		return prd;
	}

	public void setPrd(Prd prd) {
		this.prd = prd;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "org_id")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Column(name = "policy_no", length = 12)
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name = "form_no", length = 25)
	public String getFormNo() {
		return formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	@Column(name = "city_name", length = 12)
	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "area_name", length = 12)
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "prod_name", length = 128)
	public String getProdName() {
		return this.prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	@Column(name = "bank_id")
	public Long getBankId() {
		return this.bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	@Column(name = "bank_name", length = 128)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "sales_name", length = 256)
	public String getSalesName() {
		return this.salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	@Column(name = "sales_id", length = 256)
	public String getSalesId() {
		return this.salesId;
	}

	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "perm")
	public Integer getPerm() {
		return this.perm;
	}

	public void setPerm(Integer perm) {
		this.perm = perm;
	}

	@Column(name = "policy_fee", length = 256)
	public String getPolicyFee() {
		return this.policyFee;
	}

	public void setPolicyFee(String policyFee) {
		this.policyFee = policyFee;
	}

	@Column(name = "copies", length = 256)
	public String getCopies() {
		return this.copies;
	}

	public void setCopies(String copies) {
		this.copies = copies;
	}

	@Column(name = "insured_amount", length = 256)
	public String getInsuredAmount() {
		return this.insuredAmount;
	}

	public void setInsuredAmount(String insuredAmount) {
		this.insuredAmount = insuredAmount;
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

	@Column(name = "holder_addr", length = 512)
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

	@Column(name = "relation")
	public Integer getRelation() {
		return this.relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
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

	@Column(name = "insured_card_type")
	public Integer getInsuredCardType() {
		return this.insuredCardType;
	}

	public void setInsuredCardType(Integer insuredCardType) {
		this.insuredCardType = insuredCardType;
	}

	@Column(name = "insured_card_num", length = 256)
	@ColumnTransformer(
			forColumn="insured_card_num",
			read="cast(aes_decrypt(unhex(insured_card_num), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getInsuredCardNum() {
		return this.insuredCardNum;
	}

	public void setInsuredCardNum(String insuredCardNum) {
		this.insuredCardNum = insuredCardNum;
	}

	@Column(name = "insured_mobile", length = 256)
	@ColumnTransformer(
			forColumn="insured_mobile",
			read="cast(aes_decrypt(unhex(insured_mobile), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getInsuredMobile() {
		return this.insuredMobile;
	}

	public void setInsuredMobile(String insuredMobile) {
		this.insuredMobile = insuredMobile;
	}

	@Column(name = "insured_phone", length = 256)
	@ColumnTransformer(
			forColumn="insured_phone",
			read="cast(aes_decrypt(unhex(insured_phone), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getInsuredPhone() {
		return this.insuredPhone;
	}

	public void setInsuredPhone(String insuredPhone) {
		this.insuredPhone = insuredPhone;
	}

	@Column(name = "insured_addr", length = 512)
	@ColumnTransformer(
			forColumn="insured_addr",
			read="cast(aes_decrypt(unhex(insured_addr), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getInsuredAddr() {
		return this.insuredAddr;
	}

	public void setInsuredAddr(String insuredAddr) {
		this.insuredAddr = insuredAddr;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "policy_date", length = 10)
	public Date getPolicyDate() {
		return this.policyDate;
	}

	public void setPolicyDate(Date policyDate) {
		this.policyDate = policyDate;
	}

	@Column(name = "renewal_sucess_flag")
	public Integer getRenewalSucessFlag() {
		return this.renewalSucessFlag;
	}

	public void setRenewalSucessFlag(Integer renewalSucessFlag) {
		this.renewalSucessFlag = renewalSucessFlag;
	}

	@Column(name = "reset_valid_flag")
	public Integer getResetValidFlag() {
		return this.resetValidFlag;
	}

	public void setResetValidFlag(Integer resetValidFlag) {
		this.resetValidFlag = resetValidFlag;
	}

	@Column(name = "operate_id")
	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Column(name = "operate_name")
	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	@Column(name = "operate_time")
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@OneToMany(mappedBy="policy", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	public List<RenewalDtl> getRenewalDtls() {
		return this.renewalDtls;
	}

	public void setRenewalDtls(List<RenewalDtl> renewalDtls) {
		this.renewalDtls = renewalDtls;
	}

	@OneToMany(mappedBy="policy", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	public List<ConservationDtl> getConservationDtls() {
		return this.conservationDtls;
	}

	public void setConservationDtls(List<ConservationDtl> conservationDtls) {
		this.conservationDtls = conservationDtls;
	}

	@OneToMany(mappedBy="policy", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	public List<RenewalFeeDtl> getRenewalFeeDtls() {
		return this.renewalFeeDtls;
	}

	public void setRenewalFeeDtls(List<RenewalFeeDtl> renewalFeeDtls) {
		this.renewalFeeDtls = renewalFeeDtls;
	}

	@OneToMany(mappedBy="policy", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	public List<Issue> getIssues() {
		return this.issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	@OneToMany(mappedBy="policy", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	public List<CheckRecordDtl> getCheckRecordDtls() {
		return this.checkRecordDtls;
	}

	public void setCheckRecordDtls(List<CheckRecordDtl> checkRecordDtls) {
		this.checkRecordDtls = checkRecordDtls;
	}

	@OneToMany(mappedBy="policy", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	public List<CheckWriteDtl> getCheckWriteDtls() {
		return this.checkWriteDtls;
	}

	public void setCheckWriteDtls(List<CheckWriteDtl> checkWriteDtls) {
		this.checkWriteDtls = checkWriteDtls;
	}

	@OneToMany(mappedBy="policy", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	public List<CallFail> getCallFails() {
		return this.callFails;
	}

	public void setCallFails(List<CallFail> callFails) {
		this.callFails = callFails;
	}

}