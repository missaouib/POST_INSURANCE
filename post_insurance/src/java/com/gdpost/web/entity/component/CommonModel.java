package com.gdpost.web.entity.component;

import javax.persistence.Entity;

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
	private String policyFee;

	// Constructors

	/** default constructor */
	public CommonModel() {
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getPolicyFee() {
		return policyFee;
	}

	public void setPolicyFee(String policyFee) {
		this.policyFee = policyFee;
	}
	
}
