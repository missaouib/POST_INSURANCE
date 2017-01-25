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
public class CommonModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6953817886114363056L;
	private String organName;
	private Double policyFee;
	private Double sumPolicyFee;
	@Transient
	private String flag;
	@Transient
	private String netFlag;
	@Transient
	private String prdCode;

	// Constructors

	/** default constructor */
	public CommonModel() {
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

	@Transient
	public String getFlag() {
		return flag;
	}

	@Transient
	public void setFlag(String flag) {
		this.flag = flag;
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
