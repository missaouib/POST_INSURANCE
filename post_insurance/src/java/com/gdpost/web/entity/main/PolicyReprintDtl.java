package com.gdpost.web.entity.main;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;

/**
 * TPolicyReprintDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_policy_reprint_dtl")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.PolicyReprintDtl")
public class PolicyReprintDtl implements Idable<Long> {

	// Fields

	private Long id;
	private String printNo;
	private String status;
	private String orgCode;
	private String formNo;
	private String policyNo;
	private String emsNo;
	private Date printDate;
	private String flag;
	private Long operateId;
	private Date operateTime;

	// Constructors

	/** default constructor */
	public PolicyReprintDtl() {
	}

	/** full constructor */
	public PolicyReprintDtl(String printNo, String status, String orgCode, String formNo, String policyNo, String emsNo, Timestamp printDate, String flag) {
		this.printNo = printNo;
		this.status = status;
		this.orgCode = orgCode;
		this.formNo = formNo;
		this.policyNo = policyNo;
		this.emsNo = emsNo;
		this.printDate = printDate;
		this.flag = flag;
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

	@Column(name = "print_no", length = 12)
	public String getPrintNo() {
		return this.printNo;
	}

	public void setPrintNo(String printNo) {
		this.printNo = printNo;
	}

	@Column(name = "status", length = 6)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "org_code", length = 8)
	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "form_no", length = 15)
	public String getFormNo() {
		return this.formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	@Column(name = "policy_no", length = 18)
	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name = "ems_no", length = 18)
	public String getEmsNo() {
		return this.emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "print_date", length = 19)
	public Date getPrintDate() {
		return this.printDate;
	}

	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}

	@Column(name = "flag", length = 1)
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Column(name = "operate_id")
	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Column(name = "operate_time")
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

}