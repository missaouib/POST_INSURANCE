package com.gdpost.web.entity.main;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gdpost.web.entity.Idable;

/**
 * TRenewedStay entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_renewed_stay")

public class RenewedStay implements Idable<Long> {

	// Fields

	private Long id;
	private String policyNo;
	private Date csDate;
	private Long operatorId;
	private Date operateTime;
	private String remark;
	private Integer stayNum;

	// Constructors

	/** default constructor */
	public RenewedStay() {
	}

	/** full constructor */
	public RenewedStay(String policyNo, Date csDate, Long operatorId, Date operateTime, String remark,
			Integer stayNum) {
		this.policyNo = policyNo;
		this.csDate = csDate;
		this.operatorId = operatorId;
		this.operateTime = operateTime;
		this.remark = remark;
		this.stayNum = stayNum;
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

	@Column(name = "policy_no", length = 18)
	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "cs_date", length = 10)
	public Date getCsDate() {
		return this.csDate;
	}

	public void setCsDate(Date csDate) {
		this.csDate = csDate;
	}

	@Column(name = "operator_id")
	public Long getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	@Column(name = "operate_time", length = 19)
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "remark", length = 256)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "stay_num")
	public Integer getStayNum() {
		return this.stayNum;
	}

	public void setStayNum(Integer stayNum) {
		this.stayNum = stayNum;
	}

}