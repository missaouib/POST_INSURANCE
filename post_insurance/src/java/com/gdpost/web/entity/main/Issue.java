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
import com.gdpost.web.entity.basedata.IssueType;

/**
 * TIssue entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_issue", catalog = "postinsurance")
public class Issue implements Idable<Long> {

	// Fields

	private Long id;
	private IssueType issueType;
	private Policy policy;
	private Date issueTime;
	private Integer status;
	private String result;
	private String dealMan;
	private Date dealTime;
	private String reopenReason;

	// Constructors

	/** default constructor */
	public Issue() {
	}

	/** full constructor */
	public Issue(IssueType TIssueType, Policy TPolicy, Date issueTime, Integer status, String result, String dealMan, Date dealTime, String reopenReason) {
		this.issueType = TIssueType;
		this.policy = TPolicy;
		this.issueTime = issueTime;
		this.status = status;
		this.result = result;
		this.dealMan = dealMan;
		this.dealTime = dealTime;
		this.reopenReason = reopenReason;
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
	@JoinColumn(name = "type_id")
	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "policy_id")
	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "issue_time", length = 10)
	public Date getIssueTime() {
		return this.issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
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

}