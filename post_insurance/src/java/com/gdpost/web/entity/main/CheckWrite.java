package com.gdpost.web.entity.main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;

/**
 * TCheckRecordDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_check_write")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.CheckWrite")
public class CheckWrite implements Idable<Long> {

	// Fields

	private Long id;
	private String checker;
	private String checkBatch;
	private String needFix;
	private Policy policy;
	private Boolean scanError;
	private String formNo;
	private String netType;
	private String prdName;
	private String payMethod;
	private String keyInfo;
	private String importanceInfo;
	private String elseInfo;
	private String docError;
	private String docMiss;
	private String netName;
	private String remark;
	private Long operateId;
	private Date operateTime;
	private String fixStatus;
	private String fixDesc;
	private String dealMan;
	private Date dealTime;
	private String reopenReason;
	private User reopenUser;
	private Date reopenDate;

	@Transient
	private String tag = "填写错误";
	
	// Constructors

	/** default constructor */
	public CheckWrite() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="checker")
	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	@Column(name="check_batch")
	public String getCheckBatch() {
		return checkBatch;
	}

	public void setCheckBatch(String checkBatch) {
		this.checkBatch = checkBatch;
	}

	@Column(name="need_fix")
	public String getNeedFix() {
		return needFix;
	}

	public void setNeedFix(String needFix) {
		this.needFix = needFix;
	}

	@ManyToOne
	@JoinColumn(name = "policy_no", referencedColumnName="policy_no")
	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Column(name="scan_error")
	public Boolean getScanError() {
		return scanError;
	}

	public void setScanError(Boolean scanError) {
		this.scanError = scanError;
	}

	@Column(name="form_no")
	public String getFormNo() {
		return formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	@Column(name="net_type")
	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	@Column(name="prd_name")
	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	@Column(name="pay_method")
	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	@Column(name="key_info")
	public String getKeyInfo() {
		return keyInfo;
	}

	public void setKeyInfo(String keyInfo) {
		this.keyInfo = keyInfo;
	}

	@Column(name="importance_info")
	public String getImportanceInfo() {
		return importanceInfo;
	}

	public void setImportanceInfo(String importanceInfo) {
		this.importanceInfo = importanceInfo;
	}

	@Column(name="else_info")
	public String getElseInfo() {
		return elseInfo;
	}

	public void setElseInfo(String elseInfo) {
		this.elseInfo = elseInfo;
	}

	@Column(name="doc_error")
	public String getDocError() {
		return docError;
	}

	public void setDocError(String docError) {
		this.docError = docError;
	}

	@Column(name="doc_miss")
	public String getDocMiss() {
		return docMiss;
	}

	public void setDocMiss(String docMiss) {
		this.docMiss = docMiss;
	}

	@Column(name="net_name")
	public String getNetName() {
		return netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="operate_id")
	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Column(name="operate_time")
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name="fix_status")
	public String getFixStatus() {
		return fixStatus;
	}

	public void setFixStatus(String fixStatus) {
		this.fixStatus = fixStatus;
	}

	@Column(name="fix_desc")
	public String getFixDesc() {
		return fixDesc;
	}

	public void setFixDesc(String fixDesc) {
		this.fixDesc = fixDesc;
	}
	
	@Column(name="deal_man")
	public String getDealMan() {
		return dealMan;
	}

	public void setDealMan(String dealMan) {
		this.dealMan = dealMan;
	}

	@Column(name="deal_time")
	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	@Column(name = "reopen_reason", length = 256)
	public String getReopenReason() {
		return this.reopenReason;
	}

	public void setReopenReason(String reopenReason) {
		this.reopenReason = reopenReason;
	}
	
	@ManyToOne(optional=true)
	@JoinColumn(name = "reopen_id", referencedColumnName="id")
	public User getReopenUser() {
		return reopenUser;
	}

	public void setReopenUser(User reopenUser) {
		this.reopenUser = reopenUser;
	}

//	@Column(name = "reopen_id")
//	public Long getReopenId() {
//		return this.reopenId;
//	}
//
//	public void setReopenId(Long reopenId) {
//		this.reopenId = reopenId;
//	}

	@Temporal(TemporalType.DATE)
	@Column(name = "reopen_date", length = 10)
	public Date getReopenDate() {
		return this.reopenDate;
	}

	public void setReopenDate(Date reopenDate) {
		this.reopenDate = reopenDate;
	}

	@Transient
	public String getTag() {
		return tag;
	}
	
	@Transient
	public void setTag(String tag) {
		this.tag = tag;
	}
}