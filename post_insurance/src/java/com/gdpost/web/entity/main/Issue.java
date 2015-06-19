package com.gdpost.web.entity.main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gdpost.web.entity.Idable;

/**
 * TIssue entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_issue")
public class Issue implements Idable<Long> {

	// Fields

	private Long id;
	private String organCode;
	private String callMan;
	private String issueNo;
	//private String policyNo;
	private Policy policy;
	private String issueDesc;
	private String issueType;
	private String issueContent;
	private String issueDealRst;
	private Date readyDate;
	private Date finishDate;
	private String bankCode;
	private String bankName;
	private Date shouldDate;
	private Date callDate;
	private Date issueDate;
	private Integer resetNum;
	private String recallFlag;
	private Date issueTime;
	private String status;
	private String result;
	private String dealMan;
	private Date dealTime;
	private String reopenReason;
	private Long reopenId;
	private Date reopenDate;

	// Constructors

	/** default constructor */
	public Issue() {
	}

	/** minimal constructor */
	public Issue(Date readyDate) {
		this.readyDate = readyDate;
	}

	/** full constructor */
	public Issue(String organCode, String callMan, String issueNo, Policy policy, String issueDesc, String issueType, String issueContent,
			String issueDealRst, Date readyDate, Date finishDate, String bankCode, Date shouldDate, Date callDate, Date issueDate, Integer resetNum,
			String recallFlag, Date issueTime, String status, String result, String dealMan, Date dealTime, String reopenReason, Long reopenId, Date reopenDate) {
		this.organCode = organCode;
		this.callMan = callMan;
		this.issueNo = issueNo;
		this.policy = policy;
		this.issueDesc = issueDesc;
		this.issueType = issueType;
		this.issueContent = issueContent;
		this.issueDealRst = issueDealRst;
		this.readyDate = readyDate;
		this.finishDate = finishDate;
		this.bankCode = bankCode;
		this.shouldDate = shouldDate;
		this.callDate = callDate;
		this.issueDate = issueDate;
		this.resetNum = resetNum;
		this.recallFlag = recallFlag;
		this.issueTime = issueTime;
		this.status = status;
		this.result = result;
		this.dealMan = dealMan;
		this.dealTime = dealTime;
		this.reopenReason = reopenReason;
		this.reopenId = reopenId;
		this.reopenDate = reopenDate;
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

	@Column(name = "organ_code", length = 18)
	public String getOrganCode() {
		return this.organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	@Column(name = "call_man", length = 8)
	public String getCallMan() {
		return this.callMan;
	}

	public void setCallMan(String callMan) {
		this.callMan = callMan;
	}

	@Column(name = "issue_no", length = 12)
	public String getIssueNo() {
		return this.issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="policy_no", referencedColumnName="policy_no")
	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

//	@Column(name = "policy_no", length = 18)
//	public String getPolicyNo() {
//		return this.policyNo;
//	}
//
//	public void setPolicyNo(String policyNo) {
//		this.policyNo = policyNo;
//	}

	@Column(name = "issue_desc", length = 6)
	public String getIssueDesc() {
		return this.issueDesc;
	}

	public void setIssueDesc(String issueDesc) {
		this.issueDesc = issueDesc;
	}

	@Column(name = "issue_type", length = 10)
	public String getIssueType() {
		return this.issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	@Column(name = "issue_content", length = 256)
	public String getIssueContent() {
		return this.issueContent;
	}

	public void setIssueContent(String issueContent) {
		this.issueContent = issueContent;
	}

	@Column(name = "issue_deal_rst", length = 256)
	public String getIssueDealRst() {
		return this.issueDealRst;
	}

	public void setIssueDealRst(String issueDealRst) {
		this.issueDealRst = issueDealRst;
	}

	@Column(name = "ready_date", nullable = false, length = 19)
	public Date getReadyDate() {
		return this.readyDate;
	}

	public void setReadyDate(Date readyDate) {
		this.readyDate = readyDate;
	}

	@Column(name = "finish_date", length = 19)
	public Date getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	@Column(name = "bank_code", length = 20)
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "bank_name", length = 256)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "should_date", length = 10)
	public Date getShouldDate() {
		return this.shouldDate;
	}

	public void setShouldDate(Date shouldDate) {
		this.shouldDate = shouldDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "call_date", length = 10)
	public Date getCallDate() {
		return this.callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "issue_date", length = 10)
	public Date getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	@Column(name = "reset_num")
	public Integer getResetNum() {
		return this.resetNum;
	}

	public void setResetNum(Integer resetNum) {
		this.resetNum = resetNum;
	}

	@Column(name = "recall_flag", length = 2)
	public String getRecallFlag() {
		return this.recallFlag;
	}

	public void setRecallFlag(String recallFlag) {
		this.recallFlag = recallFlag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "issue_time", length = 10)
	public Date getIssueTime() {
		return this.issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	@Column(name = "status", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "result", length = 256)
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "deal_man", length = 32)
	public String getDealMan() {
		return this.dealMan;
	}

	public void setDealMan(String dealMan) {
		this.dealMan = dealMan;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "deal_time", length = 10)
	public Date getDealTime() {
		return this.dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	@Column(name = "reopen_reason", length = 256)
	public String getReopenReason() {
		return this.reopenReason;
	}

	public void setReopenReason(String reopenReason) {
		this.reopenReason = reopenReason;
	}

	@Column(name = "reopen_id")
	public Long getReopenId() {
		return this.reopenId;
	}

	public void setReopenId(Long reopenId) {
		this.reopenId = reopenId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "reopen_date", length = 10)
	public Date getReopenDate() {
		return this.reopenDate;
	}

	public void setReopenDate(Date reopenDate) {
		this.reopenDate = reopenDate;
	}

}