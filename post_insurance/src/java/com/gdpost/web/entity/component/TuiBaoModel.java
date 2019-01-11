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
public class TuiBaoModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6953817886114363056L;
	private String organName;
	private Double policyFee;
	private Double sumCsFee;
	private Double sumPolicyFee;
	@Transient
	private String levelFlag;
	@Transient
	private String netFlag;
	@Transient
	private String prdCode;
	@Transient
	private String prdName;
	@Transient
	private String perm;
	@Transient
	private String staffFlag;
	@Transient
	private Integer duration;

	// Constructors

	/** default constructor */
	public TuiBaoModel() {
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

	@Column(name="sum_cs_fee")
	public Double getSumCsFee() {
		return sumCsFee;
	}

	public void setSumCsFee(Double sumCsFee) {
		this.sumCsFee = sumCsFee;
	}

	@Column(name="sum_policy_fee")
	public Double getSumPolicyFee() {
		return sumPolicyFee;
	}

	public void setSumPolicyFee(Double sumPolicyFee) {
		this.sumPolicyFee = sumPolicyFee;
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

	@Transient
	public String getPrdName() {
		return prdName;
	}

	@Transient
	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	@Transient
	public String getPerm() {
		return perm;
	}

	@Transient
	public void setPerm(String perm) {
		this.perm = perm;
	}

	@Transient
	public String getStaffFlag() {
		return staffFlag;
	}

	@Transient
	public void setStaffFlag(String staffFlag) {
		this.staffFlag = staffFlag;
	}

	@Transient
	public Integer getDuration() {
		return duration;
	}

	@Transient
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "TuiBaoModel [organName=" + organName + ", policyFee=" + policyFee + ", sumCsFee=" + sumCsFee
				+ ", sumPolicyFee=" + sumPolicyFee + ", levelFlag=" + levelFlag + ", netFlag=" + netFlag + ", prdCode="
				+ prdCode + ", prdName=" + prdName + ", perm=" + perm + ", staffFlag=" + staffFlag + "]";
	}
	
}
