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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.basedata.RenewalType;

/**
 * TRenewalDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_renewal_dtl")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.RenewalDtl")
public class RenewalDtl implements Idable<Long> {

	// Fields

	private Long id;
	private RenewalType renewalType;
	private Policy policy;
	private Integer type;
	private Integer resetValidFlag;
	private String renewalInfo;
	private Date renewalDate;
	private String renewalMan;
	private Boolean isSuccess;

	// Constructors

	/** default constructor */
	public RenewalDtl() {
	}

	/** full constructor */
	public RenewalDtl(RenewalType TRenewalType, Policy TPolicy, Integer type, Integer resetValidFlag, String renewalInfo, Date renewalDate,
			String renewalMan, Boolean isSuccess) {
		this.renewalType = TRenewalType;
		this.policy = TPolicy;
		this.type = type;
		this.resetValidFlag = resetValidFlag;
		this.renewalInfo = renewalInfo;
		this.renewalDate = renewalDate;
		this.renewalMan = renewalMan;
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
	@JoinColumn(name = "type_id")
	public RenewalType getRenewalType() {
		return renewalType;
	}

	public void setRenewalType(RenewalType renewalType) {
		this.renewalType = renewalType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "policy_id")
	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "reset_valid_flag")
	public Integer getResetValidFlag() {
		return this.resetValidFlag;
	}

	public void setResetValidFlag(Integer resetValidFlag) {
		this.resetValidFlag = resetValidFlag;
	}

	@Column(name = "renewal_info", length = 256)
	public String getRenewalInfo() {
		return this.renewalInfo;
	}

	public void setRenewalInfo(String renewalInfo) {
		this.renewalInfo = renewalInfo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "renewal_date", length = 10)
	public Date getRenewalDate() {
		return this.renewalDate;
	}

	public void setRenewalDate(Date renewalDate) {
		this.renewalDate = renewalDate;
	}

	@Column(name = "renewal_man", length = 32)
	public String getRenewalMan() {
		return this.renewalMan;
	}

	public void setRenewalMan(String renewalMan) {
		this.renewalMan = renewalMan;
	}

	@Column(name = "is_success")
	public Boolean getIsSuccess() {
		return this.isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}