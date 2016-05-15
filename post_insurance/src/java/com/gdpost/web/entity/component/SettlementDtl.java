package com.gdpost.web.entity.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.gdpost.web.entity.Idable;

/**
 * SettlementDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_settlement_dtl")
public class SettlementDtl implements Idable<Long> {

	// Fields

	private Long id;
	private String claimsNo;
	private Settlement settlement;
	private String policyNo;
	private String prodName;
	private Double policyFee;
	private Date policyDate;
	private Date firstCaseTime;
	private String caseMan;
	private Date firstFileDate;
	private String firstSignMan;
	private Date allFileDate;
	private String allSignMan;
	private Date checkDate;
	private String checker;
	private Date checkDoneDate;
	private String checkDoneMan;

	// Constructors

	/** default constructor */
	public SettlementDtl() {
	}

	/** full constructor */
	public SettlementDtl(Settlement settlement, String policyNo,
			String prodName, Double policyFee, Date policyDate, Date firstCaseTime,
			String caseMan, Date firstFileDate, String firstSignMan,
			Date allFileDate, String allSignMan, Date checkDate,
			String checker, Date checkDoneDate, String checkDoneMan) {
		this.settlement = settlement;
		this.policyNo = policyNo;
		this.prodName = prodName;
		this.policyFee = policyFee;
		this.policyDate = policyDate;
		this.firstCaseTime = firstCaseTime;
		this.caseMan = caseMan;
		this.firstFileDate = firstFileDate;
		this.firstSignMan = firstSignMan;
		this.allFileDate = allFileDate;
		this.allSignMan = allSignMan;
		this.checkDate = checkDate;
		this.checker = checker;
		this.checkDoneDate = checkDoneDate;
		this.checkDoneMan = checkDoneMan;
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

	@Column(name="claims_no")
	public String getClaimsNo() {
		return claimsNo;
	}

	public void setClaimsNo(String claimsNo) {
		this.claimsNo = claimsNo;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "settlement_id")
	public Settlement getSettlement() {
		return this.settlement;
	}

	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}

	@Column(name = "policy_no", length = 18)
	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name = "prod_name", length = 64)
	public String getProdName() {
		return this.prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	@Column(name = "policy_fee", precision = 22, scale = 0)
	public Double getPolicyFee() {
		return this.policyFee;
	}

	public void setPolicyFee(Double policyFee) {
		this.policyFee = policyFee;
	}

	@Column(name = "policy_date", length = 10)
	public Date getPolicyDate() {
		return this.policyDate;
	}

	public void setPolicyDate(Date policyDate) {
		this.policyDate = policyDate;
	}

	@Column(name = "first_case_time", length = 10)
	public Date getFirstCaseTime() {
		return this.firstCaseTime;
	}

	public void setFirstCaseTime(Date firstCaseTime) {
		this.firstCaseTime = firstCaseTime;
	}

	@Column(name = "case_man", length = 10)
	public String getCaseMan() {
		return this.caseMan;
	}

	public void setCaseMan(String caseMan) {
		this.caseMan = caseMan;
	}

	@Column(name = "first_file_date", length = 10)
	public Date getFirstFileDate() {
		return this.firstFileDate;
	}

	public void setFirstFileDate(Date firstFileDate) {
		this.firstFileDate = firstFileDate;
	}

	@Column(name = "first_sign_man", length = 32)
	public String getFirstSignMan() {
		return this.firstSignMan;
	}

	public void setFirstSignMan(String firstSignMan) {
		this.firstSignMan = firstSignMan;
	}

	@Column(name = "all_file_date", length = 10)
	public Date getAllFileDate() {
		return this.allFileDate;
	}

	public void setAllFileDate(Date allFileDate) {
		this.allFileDate = allFileDate;
	}

	@Column(name = "all_sign_man", length = 32)
	public String getAllSignMan() {
		return this.allSignMan;
	}

	public void setAllSignMan(String allSignMan) {
		this.allSignMan = allSignMan;
	}

	@Column(name = "check_date", length = 10)
	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name = "checker", length = 32)
	public String getChecker() {
		return this.checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	@Column(name = "check_done_date", length = 10)
	public Date getCheckDoneDate() {
		return this.checkDoneDate;
	}

	public void setCheckDoneDate(Date checkDoneDate) {
		this.checkDoneDate = checkDoneDate;
	}

	@Column(name = "check_done_man", length = 32)
	public String getCheckDoneMan() {
		return this.checkDoneMan;
	}

	public void setCheckDoneMan(String checkDoneMan) {
		this.checkDoneMan = checkDoneMan;
	}

}