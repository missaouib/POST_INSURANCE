package com.gdpost.web.entity.main;

import java.util.Date;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;

/**
 * TConvictedInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_convicted_info")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.ConvictedInfo")
public class ConvictedInfo implements Idable<Long> {

	// Fields

	private Long id;
	private CallFail callFail;
	private Integer type;
	private String convictedInfo;
	private Date convictedTime;
	private Boolean isSigned;
	private Date signDate;

	// Constructors

	/** default constructor */
	public ConvictedInfo() {
	}

	/** full constructor */
	public ConvictedInfo(CallFail TCallFail, Integer type, String convictedInfo, Date convictedTime, Boolean isSigned, Date signDate) {
		this.callFail = TCallFail;
		this.type = type;
		this.convictedInfo = convictedInfo;
		this.convictedTime = convictedTime;
		this.isSigned = isSigned;
		this.signDate = signDate;
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
	@JoinColumn(name = "call_fail_id")
	public CallFail getCallFail() {
		return callFail;
	}

	public void setCallFail(CallFail callFail) {
		this.callFail = callFail;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "convicted_info")
	public String getConvictedInfo() {
		return this.convictedInfo;
	}

	public void setConvictedInfo(String convictedInfo) {
		this.convictedInfo = convictedInfo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "convicted_time", length = 10)
	public Date getConvictedTime() {
		return this.convictedTime;
	}

	public void setConvictedTime(Date convictedTime) {
		this.convictedTime = convictedTime;
	}

	@Column(name = "is_signed")
	public Boolean getIsSigned() {
		return this.isSigned;
	}

	public void setIsSigned(Boolean isSigned) {
		this.isSigned = isSigned;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "sign_date", length = 10)
	public Date getSignDate() {
		return this.signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

}