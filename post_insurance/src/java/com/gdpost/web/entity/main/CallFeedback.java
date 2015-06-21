package com.gdpost.web.entity.main;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;

/**
 * TCallFeedback entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_call_feedback")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.CallFeedback")
public class CallFeedback implements Idable<Long> {

	// Fields

	private Long id;
	private CallFail callFail;
	private String feedback;
	private Integer type;
	private String woker;
	private Integer isSigned;
	private Timestamp feedbackTime;
	private Boolean isSuccess;

	// Constructors

	/** default constructor */
	public CallFeedback() {
	}

	/** full constructor */
	public CallFeedback(CallFail TCallFail, String feedback, Integer type, String woker, Integer isSigned, Timestamp feedbackTime, Boolean isSuccess) {
		this.callFail = TCallFail;
		this.feedback = feedback;
		this.type = type;
		this.woker = woker;
		this.isSigned = isSigned;
		this.feedbackTime = feedbackTime;
		this.isSuccess = isSuccess;
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
	@JoinColumn(name = "call_fail_id")
	public CallFail getCallFail() {
		return callFail;
	}

	public void setCallFail(CallFail callFail) {
		this.callFail = callFail;
	}

	@Column(name = "feedback")
	public String getFeedback() {
		return this.feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "woker", length = 32)
	public String getWoker() {
		return this.woker;
	}

	public void setWoker(String woker) {
		this.woker = woker;
	}

	@Column(name = "is_signed")
	public Integer getIsSigned() {
		return this.isSigned;
	}

	public void setIsSigned(Integer isSigned) {
		this.isSigned = isSigned;
	}

	@Column(name = "feedback_time", length = 19)
	public Timestamp getFeedbackTime() {
		return this.feedbackTime;
	}

	public void setFeedbackTime(Timestamp feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	@Column(name = "is_success")
	public Boolean getIsSuccess() {
		return this.isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}