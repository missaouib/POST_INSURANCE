package com.gdpost.web.entity.main;

import java.sql.Timestamp;
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
 * TConservationDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_conservation_dtl", catalog = "postinsurance")
public class ConservationDtl implements Idable<Long> {

	// Fields

	private Long id;
	private Policy policy;
	private Integer type;
	private String dealNum;
	private String info;
	private Date csDate;
	private Long csUserId;
	private String csRst;
	private Long dealUserId;
	private Integer status;
	private Boolean cancelFlag;
	private Long cancelMan;
	private Timestamp cancelDate;
	private String remark;

	// Constructors

	/** default constructor */
	public ConservationDtl() {
	}

	/** full constructor */
	public ConservationDtl(Policy TPolicy, Integer type, String dealNum, String info, Date csDate, Long csUserId, String csRst, Long dealUserId,
			Integer status, Boolean cancelFlag, Long cancelMan, Timestamp cancelDate, String remark) {
		this.policy = TPolicy;
		this.type = type;
		this.dealNum = dealNum;
		this.info = info;
		this.csDate = csDate;
		this.csUserId = csUserId;
		this.csRst = csRst;
		this.dealUserId = dealUserId;
		this.status = status;
		this.cancelFlag = cancelFlag;
		this.cancelMan = cancelMan;
		this.cancelDate = cancelDate;
		this.remark = remark;
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

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "deal_num", length = 32)
	public String getDealNum() {
		return this.dealNum;
	}

	public void setDealNum(String dealNum) {
		this.dealNum = dealNum;
	}

	@Column(name = "info", length = 256)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "cs_date", length = 10)
	public Date getCsDate() {
		return this.csDate;
	}

	public void setCsDate(Date csDate) {
		this.csDate = csDate;
	}

	@Column(name = "cs_user_id")
	public Long getCsUserId() {
		return this.csUserId;
	}

	public void setCsUserId(Long csUserId) {
		this.csUserId = csUserId;
	}

	@Column(name = "cs_rst", length = 64)
	public String getCsRst() {
		return this.csRst;
	}

	public void setCsRst(String csRst) {
		this.csRst = csRst;
	}

	@Column(name = "deal_user_id")
	public Long getDealUserId() {
		return this.dealUserId;
	}

	public void setDealUserId(Long dealUserId) {
		this.dealUserId = dealUserId;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "cancel_flag")
	public Boolean getCancelFlag() {
		return this.cancelFlag;
	}

	public void setCancelFlag(Boolean cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	@Column(name = "cancel_man")
	public Long getCancelMan() {
		return this.cancelMan;
	}

	public void setCancelMan(Long cancelMan) {
		this.cancelMan = cancelMan;
	}

	@Column(name = "cancel_date", length = 19)
	public Timestamp getCancelDate() {
		return this.cancelDate;
	}

	public void setCancelDate(Timestamp cancelDate) {
		this.cancelDate = cancelDate;
	}

	@Column(name = "remark", length = 256)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}