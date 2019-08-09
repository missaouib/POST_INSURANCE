package com.gdpost.web.entity.insurance;

import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import com.gdpost.utils.StringUtil;
import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.User;

/**
 * TCheckRecordDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_check_write", uniqueConstraints={@UniqueConstraint(columnNames={"policy_no", "prd_name"})})
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
	private String fixType;
	private String isPass;
	private Date replyTime;
	private Date closeDate;
	private String closeUser;

	private Boolean isTruth;
	
	@Transient
	private String tag = "填写错误";
	
	@Transient
	private Integer timeConsuming = 1;
	
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
	
	@ManyToOne(cascade = CascadeType.REMOVE, targetEntity = Policy.class)
	@JoinColumnsOrFormulas(value={
	@JoinColumnOrFormula(column=@JoinColumn(name ="policy_no", referencedColumnName ="policy_no", insertable =false, updatable = false)),
	@JoinColumnOrFormula(formula=@JoinFormula(value="0", referencedColumnName = "attached_flag"))
	})
	@Size(max=1)
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
	
	@Column(name="fix_type")
	public String getFixType() {
		return fixType;
	}

	public void setFixType(String fixType) {
		this.fixType = fixType;
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

	@Column(name="is_pass")
	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}

	@Column(name="reply_time")
	@Temporal(TemporalType.DATE)
	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	@Transient
	public Integer getTimeConsuming() {
		return StringUtil.getBetweenDay(this.operateTime, this.closeDate==null?new Date():this.closeDate);
	}

	@Transient
	public void setTimeConsuming(Integer timeConsuming) {
		this.timeConsuming = timeConsuming;
	}

	@Column(name="close_date")
	@Temporal(TemporalType.DATE)
	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	@Column(name="close_user")
	public String getCloseUser() {
		return closeUser;
	}

	public void setCloseUser(String closeUser) {
		this.closeUser = closeUser;
	}

	@Column(name="is_truth")
	public Boolean getIsTruth() {
		return isTruth;
	}

	public void setIsTruth(Boolean isTruth) {
		this.isTruth = isTruth;
	}
	
	
}