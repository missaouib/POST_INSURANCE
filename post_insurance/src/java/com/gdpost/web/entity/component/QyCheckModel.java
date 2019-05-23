package com.gdpost.web.entity.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * QyCheckModel entity
 */
@Entity
public class QyCheckModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6446754569354605959L;
	private String organCode;
	private Integer policyCounts = 0;
	private Integer checkCounts = 0;
	private Integer errCounts = 0;
	
	@Transient
	private Integer checkRecordCounts = 0;
	@Transient
	private Integer checkRecordErrCounts = 0;
	
	@Transient
	private String levelFlag;
	@Transient
	private String statFlag;
	@Transient
	private String orgName;
	
	@Transient
	public String getLevelFlag() {
		return levelFlag;
	}
	@Transient
	public void setLevelFlag(String levelFlag) {
		this.levelFlag = levelFlag;
	}
	@Transient
	public String getStatFlag() {
		return statFlag;
	}
	@Transient
	public void setStatFlag(String statFlag) {
		this.statFlag = statFlag;
	}
	@Transient
	public Integer getCheckRecordCounts() {
		return checkRecordCounts;
	}
	@Transient
	public void setCheckRecordCounts(Integer checkRecordCounts) {
		this.checkRecordCounts = checkRecordCounts;
	}
	@Transient
	public Integer getCheckRecordErrCounts() {
		return checkRecordErrCounts;
	}
	@Transient
	public void setCheckRecordErrCounts(Integer checkRecordErrCounts) {
		this.checkRecordErrCounts = checkRecordErrCounts;
	}
	
	@Transient
	public String getOrgName() {
		return orgName;
	}
	@Transient
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="organ_code")
	public String getOrganCode() {
		return organCode;
	}
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}
	@Column(name="policy_counts")
	public Integer getPolicyCounts() {
		return policyCounts;
	}
	public void setPolicyCounts(Integer policyCounts) {
		this.policyCounts = policyCounts;
	}
	@Column(name="check_counts")
	public Integer getCheckCounts() {
		return checkCounts;
	}
	public void setCheckCounts(Integer checkCounts) {
		this.checkCounts = checkCounts;
	}
	@Column(name="err_counts")
	public Integer getErrCounts() {
		return errCounts;
	}
	public void setErrCounts(Integer errCounts) {
		this.errCounts = errCounts;
	}
	
	

}
