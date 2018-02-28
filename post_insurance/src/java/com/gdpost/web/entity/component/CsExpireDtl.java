package com.gdpost.web.entity.component;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gdpost.web.entity.Idable;

/**
 * TCsExpireDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cs_expire_dtl")

public class CsExpireDtl implements Idable<Long> {

	// Fields

	private Long id;
	private CsExpire csExpire;
	private String dealDesc;
	private String dealMan;
	private Date dealTime;
	private Long operateId;
	private Date operateTime;

	// Constructors

	/** default constructor */
	public CsExpireDtl() {
	}

	/** full constructor */
	public CsExpireDtl(CsExpire csExpire, String dealMan, Date dealTime, Long operateId,
			Date operateTime) {
		this.csExpire = csExpire;
		this.dealMan = dealMan;
		this.dealTime = dealTime;
		this.operateId = operateId;
		this.operateTime = operateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cs_expire_id", referencedColumnName="id")
	public CsExpire getCsExpire() {
		return this.csExpire;
	}

	public void setCsExpire(CsExpire csExpire) {
		this.csExpire = csExpire;
	}

	@Column(name = "deal_desc", length = 128)
	public String getDealDesc() {
		return dealDesc;
	}

	public void setDealDesc(String dealDesc) {
		this.dealDesc = dealDesc;
	}

	@Column(name = "deal_man", length = 12)
	public String getDealMan() {
		return this.dealMan;
	}

	public void setDealMan(String dealMan) {
		this.dealMan = dealMan;
	}

	@Column(name = "deal_time", length = 19)
	public Date getDealTime() {
		return this.dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	@Column(name = "operate_id")

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Column(name = "operate_time", length = 19)

	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

}