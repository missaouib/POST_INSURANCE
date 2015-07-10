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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.basedata.CallDealType;

/**
 * TCallFail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_call_fail")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.CallFail")
public class CallFail implements Idable<Long> {

	// Fields

	private Long id;
	private CallDealType callDealType;
	private Policy policy;
	private Integer status;
	private Boolean isReset;
	private Date resetTime;
	private Date issueDate;
	private Integer successFiag;
	private Date successDate;
	private Integer letterFlag;
	private List<CallFeedback> callFeedbacks = new ArrayList<CallFeedback>(0);
	private List<ConvictedInfo> convictedInfos = new ArrayList<ConvictedInfo>(0);

	// Constructors

	/** default constructor */
	public CallFail() {
	}

	/** full constructor */
	public CallFail(CallDealType TCallDealType, Policy TPolicy, Integer status, Boolean isReset, Date resetTime, Date issueDate, Integer successFiag,
			Date successDate, Integer letterFlag, List<CallFeedback> TCallFeedbacks, List<ConvictedInfo> TConvictedInfos) {
		this.callDealType = TCallDealType;
		this.policy = TPolicy;
		this.status = status;
		this.isReset = isReset;
		this.resetTime = resetTime;
		this.issueDate = issueDate;
		this.successFiag = successFiag;
		this.successDate = successDate;
		this.letterFlag = letterFlag;
		this.callFeedbacks = TCallFeedbacks;
		this.convictedInfos = TConvictedInfos;
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

	@ManyToOne
	@JoinColumn(name = "fail_type_id")
	public CallDealType getCallDealType() {
		return callDealType;
	}

	public void setCallDealType(CallDealType callDealType) {
		this.callDealType = callDealType;
	}

	@ManyToOne
	@JoinColumn(name = "policy_id")
	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "is_reset")
	public Boolean getIsReset() {
		return this.isReset;
	}

	public void setIsReset(Boolean isReset) {
		this.isReset = isReset;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "reset_time", length = 10)
	public Date getResetTime() {
		return this.resetTime;
	}

	public void setResetTime(Date resetTime) {
		this.resetTime = resetTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "issue_date", length = 10)
	public Date getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	@Column(name = "success_fiag")
	public Integer getSuccessFiag() {
		return this.successFiag;
	}

	public void setSuccessFiag(Integer successFiag) {
		this.successFiag = successFiag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "success_date", length = 10)
	public Date getSuccessDate() {
		return this.successDate;
	}

	public void setSuccessDate(Date successDate) {
		this.successDate = successDate;
	}

	@Column(name = "letter_flag")
	public Integer getLetterFlag() {
		return this.letterFlag;
	}

	public void setLetterFlag(Integer letterFlag) {
		this.letterFlag = letterFlag;
	}

	@OneToMany(mappedBy="callFail", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	public List<CallFeedback> getCallFeedbacks() {
		return callFeedbacks;
	}

	public void setCallFeedbacks(List<CallFeedback> callFeedbacks) {
		this.callFeedbacks = callFeedbacks;
	}

	@OneToMany(mappedBy="callFail", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	public List<ConvictedInfo> getConvictedInfos() {
		return convictedInfos;
	}

	public void setConvictedInfos(List<ConvictedInfo> convictedInfos) {
		this.convictedInfos = convictedInfos;
	}

}