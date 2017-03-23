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
public class UwModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6953817886114363056L;
	private String orgName;
	private Integer l50;
	private Integer l30;
	private Integer l20;
	private Integer l10;
	private Integer sc;

	@Transient
	private String levelFlag;
	// Constructors

	@Transient
	public String getLevelFlag() {
		return levelFlag;
	}

	@Transient
	public void setLevelFlag(String levelFlag) {
		this.levelFlag = levelFlag;
	}

	/** default constructor */
	public UwModel() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="org_name")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String organName) {
		this.orgName = organName;
	}

	@Column(name="l50")
	public Integer getL50() {
		return l50;
	}

	public void setL50(Integer l50) {
		this.l50 = l50;
	}

	@Column(name="l30")
	public Integer getL30() {
		return l30;
	}

	public void setL30(Integer l30) {
		this.l30 = l30;
	}

	@Column(name="l20")
	public Integer getL20() {
		return l20;
	}

	public void setL20(Integer l20) {
		this.l20 = l20;
	}

	@Column(name="l10")
	public Integer getL10() {
		return l10;
	}

	public void setL10(Integer l10) {
		this.l10 = l10;
	}

	@Column(name="sc")
	public Integer getSc() {
		return sc;
	}

	public void setSc(Integer sc) {
		this.sc = sc;
	}

}
