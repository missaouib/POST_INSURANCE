package com.gdpost.web.entity.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnTransformer;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.basedata.Prd;
import com.gdpost.web.entity.nouse.CallFail;

/**
 * policy entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_policy")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.Policy")
public class Policy implements Idable<Long>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1161534787606963177L;
	// Fields

	private Long id;
	private String policyNo;
	private String formNo;
	private String prodName;
	private String bankName;
	private String salesName;
	private String salesId;
	private String status;
	private Integer perm;
	private String policyFee;
	private String copies;
	private String insuredAmount;
	private String holder;
	private String insured;
	private Date policyDate;
	private Date billBackDate;
	private Integer renewalSucessFlag;
	private Integer resetValidFlag;
	private Long operateId;
	private Date operateTime;
	private String agentOrgan;
	private String agentSales;

	//private String organCode;
	private Organization organization;
	private String organName;
	//private String prodCode;
	private Prd prd;
	private String feeFrequency;
	private Date plicyValidDate;
	private String bankCode;
	private String customerManagerCode;
	private String customer_manager;
	
	private List<Issue> issues = new ArrayList<Issue>(0);
	private List<CheckRecordDtl> checkRecordDtls = new ArrayList<CheckRecordDtl>(0);
	private List<CheckWriteDtl> checkWriteDtls = new ArrayList<CheckWriteDtl>(0);
	private List<CallFail> callFails = new ArrayList<CallFail>(0);

	// Constructors

	/** default constructor */
	public Policy() {
	}

	/** full constructor */
	public Policy(String prodName, String bankName, String salesName,
			String salesId, String status, Integer perm, String policyFee, String copies, String insuredAmount, String holder, String insured, Date policyDate, Integer renewalSucessFlag,
			Integer resetValidFlag, List<Issue> TIssues, List<CheckRecordDtl> TCheckRecordDtls, List<CheckWriteDtl> TCheckWriteDtls, List<CallFail> TCallFails) {
		this.prodName = prodName;
		this.bankName = bankName;
		this.salesName = salesName;
		this.salesId = salesId;
		this.status = status;
		this.perm = perm;
		this.policyFee = policyFee;
		this.copies = copies;
		this.insuredAmount = insuredAmount;
		this.holder = holder;
		this.insured = insured;
		this.policyDate = policyDate;
		this.renewalSucessFlag = renewalSucessFlag;
		this.resetValidFlag = resetValidFlag;
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

	@Column(name = "policy_no", length = 18)
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
	
	@ManyToOne(optional=true)
	@JoinColumn(name="organ_code", referencedColumnName="org_code")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
//
//	@Column(name = "organ_code", length = 20)
//	public String getOrganCode() {
//		return this.organCode;
//	}
//
//	public void setOrganCode(String organCode) {
//		this.organCode = organCode;
//	}

	@Column(name = "organ_name", length = 60)
	public String getOrganName() {
		return this.organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}
	
	@ManyToOne
	@JoinColumn(name="prod_code", referencedColumnName="prd_code")
	public Prd getPrd() {
		return prd;
	}

	public void setPrd(Prd prd) {
		this.prd = prd;
	}

//	@Column(name = "prod_code", length = 8)
//	public String getProdCode() {
//		return this.prodCode;
//	}
//
//	public void setProdCode(String prodCode) {
//		this.prodCode = prodCode;
//	}
	
	@Column(name = "prod_name", length = 128)
	public String getProdName() {
		return this.prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
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
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
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

	@Column(name = "agent_organ")
	public String getAgentOrgan() {
		return agentOrgan;
	}

	public void setAgentOrgan(String agentOrgan) {
		this.agentOrgan = agentOrgan;
	}

	@Column(name = "agent_sales")
	public String getAgentSales() {
		return agentSales;
	}

	public void setAgentSales(String agentSales) {
		this.agentSales = agentSales;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "policy_date", length = 10)
	public Date getPolicyDate() {
		return this.policyDate;
	}

	public void setPolicyDate(Date policyDate) {
		this.policyDate = policyDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "bill_back_date", length = 10)
	public Date getBillBackDate() {
		return billBackDate;
	}

	public void setBillBackDate(Date billBackDate) {
		this.billBackDate = billBackDate;
	}

	@Column(name = "fee_frequency", length = 10)
	public String getFeeFrequency() {
		return this.feeFrequency;
	}

	public void setFeeFrequency(String feeFrequency) {
		this.feeFrequency = feeFrequency;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "plicy_valid_date", length = 10)
	public Date getPlicyValidDate() {
		return this.plicyValidDate;
	}

	public void setPlicyValidDate(Date plicyValidDate) {
		this.plicyValidDate = plicyValidDate;
	}
	
	@Column(name = "bank_code", length = 14)
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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

	@Column(name = "operate_time")
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name="customer_manager_code")
	public String getCustomerManagerCode() {
		return customerManagerCode;
	}

	public void setCustomerManagerCode(String customerManagerCode) {
		this.customerManagerCode = customerManagerCode;
	}

	@Column(name="customer_manager")
	public String getCustomer_manager() {
		return customer_manager;
	}

	public void setCustomer_manager(String customer_manager) {
		this.customer_manager = customer_manager;
	}

	@OneToMany(mappedBy="policy", cascade={CascadeType.REMOVE}, orphanRemoval=true)
	public List<Issue> getIssues() {
		return this.issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	@OneToMany(mappedBy="policy", cascade={CascadeType.REMOVE}, orphanRemoval=true)
	public List<CheckRecordDtl> getCheckRecordDtls() {
		return this.checkRecordDtls;
	}

	public void setCheckRecordDtls(List<CheckRecordDtl> checkRecordDtls) {
		this.checkRecordDtls = checkRecordDtls;
	}

	@OneToMany(mappedBy="policy", cascade={CascadeType.REMOVE}, orphanRemoval=true)
	public List<CheckWriteDtl> getCheckWriteDtls() {
		return this.checkWriteDtls;
	}

	public void setCheckWriteDtls(List<CheckWriteDtl> checkWriteDtls) {
		this.checkWriteDtls = checkWriteDtls;
	}

	@OneToMany(mappedBy="policy", cascade={CascadeType.REMOVE}, orphanRemoval=true)
	public List<CallFail> getCallFails() {
		return this.callFails;
	}

	public void setCallFails(List<CallFail> callFails) {
		this.callFails = callFails;
	}

}