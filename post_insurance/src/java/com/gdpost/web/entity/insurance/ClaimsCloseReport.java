package com.gdpost.web.entity.insurance;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.Organization;

/**
 * TClaimsCloseReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_claims_close_report")
public class ClaimsCloseReport implements Idable<Long> {

	// Fields

	private Long id;
	private Organization organization;
	private String organName;
	private String claimsNo;
	private Policy policy;
	private String policyNos;
	private String areaName;
	private String prodCode;
	private String prodName;
	private String clientNo;
	private String holder;
	private String insured;
	private String fm;
	private Integer age;
	private String job;
	private String settleType;
	private String claimsType;
	private String reqMan;
	private String reqPhone;
	private Date reportDate;
	private Date settleDate;
	private Date caseDate;
	private String claimsRst;
	private Double totalFee;
	private Double claimsFee;
	private Double giveFee;
	private Double rejectFee;
	private Date closeDate;
	private Long operateId;
	private Date operateTime;

	// Constructors

	/** default constructor */
	public ClaimsCloseReport() {
	}

	/** full constructor */
	public ClaimsCloseReport(Organization organization, String organName, String claimsNo, Policy policy, String areaName,
			String prodCode, String prodName, String clientNo, String holder, String insured, String fm, Integer age,
			String job, String settleType, String claimsType, String reqMan, String reqPhone, Date reportDate,
			Date settleDate, Date caseDate, String claimsRst, Double totalFee, Double claimsFee, Double giveFee,
			Double rejectFee, Date closeDate, Long operateId, Date operateTime) {
		this.organization = organization;
		this.organName = organName;
		this.claimsNo = claimsNo;
		this.policy = policy;
		this.areaName = areaName;
		this.prodCode = prodCode;
		this.prodName = prodName;
		this.clientNo = clientNo;
		this.holder = holder;
		this.insured = insured;
		this.fm = fm;
		this.age = age;
		this.job = job;
		this.settleType = settleType;
		this.claimsType = claimsType;
		this.reqMan = reqMan;
		this.reqPhone = reqPhone;
		this.reportDate = reportDate;
		this.settleDate = settleDate;
		this.caseDate = caseDate;
		this.claimsRst = claimsRst;
		this.totalFee = totalFee;
		this.claimsFee = claimsFee;
		this.giveFee = giveFee;
		this.rejectFee = rejectFee;
		this.closeDate = closeDate;
		this.operateId = operateId;
		this.operateTime = operateTime;
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

	@ManyToOne(optional=true)
	@JoinColumn(name="organ_code", referencedColumnName="org_code")
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Column(name = "organ_name", length = 60)

	public String getOrganName() {
		return this.organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	@Column(name = "claims_no", length = 21)

	public String getClaimsNo() {
		return this.claimsNo;
	}

	public void setClaimsNo(String claimsNo) {
		this.claimsNo = claimsNo;
	}

	@ManyToOne(cascade = CascadeType.DETACH, targetEntity = Policy.class)
	@JoinColumnsOrFormulas(value={
	@JoinColumnOrFormula(column=@JoinColumn(name ="policy_no", referencedColumnName ="policy_no", insertable =false, updatable = false)),
	@JoinColumnOrFormula(formula=@JoinFormula(value="0", referencedColumnName = "attached_flag"))
	})
	public Policy getPolicy() {
		return this.policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Column(name = "policy_nos", length = 120)
	public String getPolicyNos() {
		return policyNos;
	}

	public void setPolicyNos(String policyNos) {
		this.policyNos = policyNos;
	}

	@Column(name = "area_name", length = 120)

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "prod_code", length = 44)

	public String getProdCode() {
		return this.prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	@Column(name = "prod_name", length = 128)

	public String getProdName() {
		return this.prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	@Column(name = "client_no", length = 59)

	public String getClientNo() {
		return this.clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	@Column(name = "holder", length = 21)

	public String getHolder() {
		return this.holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	@Column(name = "insured", length = 21)

	public String getInsured() {
		return this.insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	@Column(name = "fm", length = 2)

	public String getFm() {
		return this.fm;
	}

	public void setFm(String fm) {
		this.fm = fm;
	}

	@Column(name = "age")

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "job", length = 24)

	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Column(name = "settle_type", length = 6)

	public String getSettleType() {
		return this.settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}

	@Column(name = "claims_type", length = 36)

	public String getClaimsType() {
		return this.claimsType;
	}

	public void setClaimsType(String claimsType) {
		this.claimsType = claimsType;
	}

	@Column(name = "req_man", length = 12)

	public String getReqMan() {
		return this.reqMan;
	}

	public void setReqMan(String reqMan) {
		this.reqMan = reqMan;
	}

	@Column(name = "req_phone", length = 11)

	public String getReqPhone() {
		return this.reqPhone;
	}

	public void setReqPhone(String reqPhone) {
		this.reqPhone = reqPhone;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "report_date", length = 10)

	public Date getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "settle_date", length = 10)

	public Date getSettleDate() {
		return this.settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "case_date", length = 10)

	public Date getCaseDate() {
		return this.caseDate;
	}

	public void setCaseDate(Date caseDate) {
		this.caseDate = caseDate;
	}

	@Column(name = "claims_rst", length = 12)

	public String getClaimsRst() {
		return this.claimsRst;
	}

	public void setClaimsRst(String claimsRst) {
		this.claimsRst = claimsRst;
	}

	@Column(name = "total_fee", precision = 22, scale = 0)

	public Double getTotalFee() {
		return this.totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	@Column(name = "claims_fee", precision = 22, scale = 0)

	public Double getClaimsFee() {
		return this.claimsFee;
	}

	public void setClaimsFee(Double claimsFee) {
		this.claimsFee = claimsFee;
	}

	@Column(name = "give_fee", precision = 22, scale = 0)

	public Double getGiveFee() {
		return this.giveFee;
	}

	public void setGiveFee(Double giveFee) {
		this.giveFee = giveFee;
	}

	@Column(name = "reject_fee", precision = 22, scale = 0)

	public Double getRejectFee() {
		return this.rejectFee;
	}

	public void setRejectFee(Double rejectFee) {
		this.rejectFee = rejectFee;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "close_date", length = 10)

	public Date getCloseDate() {
		return this.closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
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