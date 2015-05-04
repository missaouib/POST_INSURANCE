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
 * TRenewalFeeDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_renewal_fee_dtl", catalog = "postinsurance")
public class RenewalFeeDtl implements Idable<Long> {

	// Fields

	private Long id;
	private Policy policy;
	private String feeDtl;
	private Date feeDate;

	// Constructors

	/** default constructor */
	public RenewalFeeDtl() {
	}

	/** full constructor */
	public RenewalFeeDtl(Policy TPolicy, String feeDtl, Date feeDate) {
		this.policy = TPolicy;
		this.feeDtl = feeDtl;
		this.feeDate = feeDate;
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
	@JoinColumn(name = "policy_id")
	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Column(name = "fee_dtl", length = 64)
	public String getFeeDtl() {
		return this.feeDtl;
	}

	public void setFeeDtl(String feeDtl) {
		this.feeDtl = feeDtl;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fee_date", length = 10)
	public Date getFeeDate() {
		return this.feeDate;
	}

	public void setFeeDate(Date feeDate) {
		this.feeDate = feeDate;
	}

}