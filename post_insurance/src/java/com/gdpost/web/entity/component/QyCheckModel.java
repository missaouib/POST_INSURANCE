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
	private Integer ontimeCounts = 0;
	
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
	private Integer duration;
	@Transient
	private String perm;
	@Transient
	private String fixStatus;
	@Transient
	private String netFlag;
	@Transient
	public String getNetFlag() {
		return netFlag;
	}
	@Transient
	public void setNetFlag(String netFlag) {
		this.netFlag = netFlag;
	}
	@Transient
	public String getFixStatus() {
		return fixStatus;
	}
	@Transient
	public void setFixStatus(String fixStatus) {
		this.fixStatus = fixStatus;
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
	public String getLevelFlag() {
		return levelFlag;
	}
	@Transient
	public void setLevelFlag(String levelFlag) {
		this.levelFlag = levelFlag;
	}
	@Transient
	public String getStatFlag() {
		if(getOntimeCounts() != null) {
			return Double.toString(((1-(double)((double)getCheckCounts()/(double)getPolicyCounts()))*0.5 + ((double)getOntimeCounts()/(double)getCheckCounts())*0.3 + ((double)getErrCounts()/(double)getCheckCounts())*0.2)*100);
		}
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
	
	@Transient
	public Integer getDuration() {
		return duration;
	}
	@Transient
	public void setDuration(Integer duration) {
		this.duration = duration;
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
	@Column(name="ontime_counts")
	public Integer getOntimeCounts() {
		return ontimeCounts;
	}
	public void setOntimeCounts(Integer ontimeCounts) {
		this.ontimeCounts = ontimeCounts;
	}
	@Override
	public String toString() {
		return "QyCheckModel [organCode=" + organCode + ", policyCounts=" + policyCounts + ", checkCounts="
				+ checkCounts + ", errCounts=" + errCounts + ", ontimeCounts=" + ontimeCounts + ", checkRecordCounts="
				+ checkRecordCounts + ", checkRecordErrCounts=" + checkRecordErrCounts + ", levelFlag=" + levelFlag
				+ ", statFlag=" + statFlag + ", orgName=" + orgName + ", duration=" + duration + ", perm=" + perm
				+ ", fixStatus=" + fixStatus + ", netFlag=" + netFlag + "]";
	}
	
	
}
