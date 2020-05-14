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
public class QyStatModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6446754569354605959L;
	private String organName;
	private Double sumdays;
	private Double policyCounts = 0d;
	private Double job5ds = 0d;
	private Double huixiaoCounts = 0d;
	private Double huixiao5ds = 0d;
	
	@Transient
	private String ratio;
	
	@Transient
	private Integer checkRecordCounts = 0;
	@Transient
	private Integer checkRecordErrCounts = 0;
	
	@Transient
	private String levelFlag;
	@Transient
	private String statFlag;
	@Transient
	private String orgCode;
	@Transient
	private Integer duration;
	@Transient
	private String perm;
	@Transient
	private String fixStatus;
	@Transient
	private String netFlag;
	@Transient
	private String prdCode;
	@Transient
	private String staffFlag;
	@Transient
	private String statType;
	@Transient
	private String csFlag;
	@Transient
	private String saleType;
	@Transient
	private String status;
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
	public String getOrgCode() {
		return orgCode;
	}
	@Transient
	public void setOrgCode(String orgName) {
		this.orgCode = orgName;
	}
	
	@Transient
	public Integer getDuration() {
		return duration;
	}
	@Transient
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	@Transient
	public Double getRatio() {
		if(policyCounts ==0) {
			return 100d;
		}
		Double hgl = 1d;
		Double jsl = 1d;
		Double zgl = 1d;
		return (hgl*0.5 + jsl*0.3 + zgl*0.2)*100;
		
	}
	@Transient
	public void setRatio(String ratio) {
		this.ratio = ratio;
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
	public String getStaffFlag() {
		return staffFlag;
	}
	@Transient
	public void setStaffFlag(String staffFlag) {
		this.staffFlag = staffFlag;
	}
	@Transient
	public String getStatType() {
		return statType;
	}
	@Transient
	public void setStatType(String statType) {
		this.statType = statType;
	}
	@Transient
	public String getCsFlag() {
		return csFlag;
	}
	@Transient
	public void setCsFlag(String csFlag) {
		this.csFlag = csFlag;
	}
	@Transient
	public String getSaleType() {
		return saleType;
	}
	@Transient
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	@Transient
	public String getStatus() {
		return status;
	}
	@Transient
	public void setStatus(String status) {
		this.status = status;
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
	
	@Column(name="policy_counts")
	public Double getPolicyCounts() {
		if(this.policyCounts == null) {
			return 0d;
		}
		return policyCounts;
	}
	public void setPolicyCounts(Double policyCounts) {
		this.policyCounts = policyCounts;
	}
	
	@Column(name="sumdays")
	public Double getSumdays() {
		return sumdays;
	}
	public void setSumdays(Double sumdays) {
		this.sumdays = sumdays;
	}
	
	@Column(name="job5ds")
	public Double getJob5ds() {
		return job5ds;
	}
	public void setJob5ds(Double job5ds) {
		this.job5ds = job5ds;
	}
	
	@Column(name="huixiao_counts")
	public Double getHuixiaoCounts() {
		return huixiaoCounts;
	}
	public void setHuixiaoCounts(Double huixiaoCounts) {
		this.huixiaoCounts = huixiaoCounts;
	}
	
	@Column(name="huixiao5ds")
	public Double getHuixiao5ds() {
		return huixiao5ds;
	}
	public void setHuixiao5ds(Double huixiao5ds) {
		this.huixiao5ds = huixiao5ds;
	}
	
	
}
