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
public class PolicyStatModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6953817886114363056L;
	private String name;
	private Double policyCount;
	private Double policyFee;
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

	// Constructors

	/** default constructor */
	public PolicyStatModel() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="policy_count")
	public Double getPolicyCount() {
		return policyCount;
	}

	public void setPolicyCount(Double policyCount) {
		this.policyCount = policyCount;
	}

	@Column(name="policy_fee")
	public Double getPolicyFee() {
		return policyFee;
	}

	public void setPolicyFee(Double policyFee) {
		this.policyFee = policyFee;
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

	@Override
	public String toString() {
		return "TuiBaoModel [name=" + name + ", policyFee=" + policyFee + ", levelFlag=" + levelFlag + ", netFlag=" + netFlag + ", prdCode="
				+ prdCode + ", prdName=" + prdName + ", perm=" + perm + ", staffFlag=" + staffFlag + "]";
	}
	
}
