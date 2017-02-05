package com.gdpost.web.entity.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * VCallFailList entity. @author MyEclipse Persistence Tools
 */
@Entity
public class StaffModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6953817886114363056L;
	private String organName;
	private Double policyFee;
	private Double sumPolicyFee;
	private Double staffCount;
	private Double sumStaffCount;
	@Transient
	private String levelFlag;
	@Transient
	private String netFlag;
	@Transient
	private String prdCode;

	// Constructors

	/** default constructor */
	public StaffModel() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="organ_name")
	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	@Column(name="policy_fee")
	public Double getPolicyFee() {
		return policyFee;
	}

	public void setPolicyFee(Double policyFee) {
		this.policyFee = policyFee;
	}

	@Column(name="sum_policy_fee")
	public Double getSumPolicyFee() {
		return sumPolicyFee;
	}

	public void setSumPolicyFee(Double sumPolicyFee) {
		this.sumPolicyFee = sumPolicyFee;
	}

	@Column(name="staff_count")
	public Double getStaffCount() {
		return staffCount;
	}

	public void setStaffCount(Double staffCount) {
		this.staffCount = staffCount;
	}

	@Column(name="sum_staff_count")
	public Double getSumStaffCount() {
		return sumStaffCount;
	}

	public void setSumStaffCount(Double sumStaffCount) {
		this.sumStaffCount = sumStaffCount;
	}

	@Transient
	public String getLevelFlag() {
		return levelFlag;
	}

	@Transient
	public void setLevelFlag(String levelFlag) {
		this.levelFlag = levelFlag;
	}

	@Transient
	public String getNetFlag() {
		return netFlag;
	}

	@Transient
	public void setNetFlag(String netFlag) {
		this.netFlag = netFlag;
	}

	@Transient
	public String getPrdCode() {
		return prdCode;
	}

	@Transient
	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}
	
}
