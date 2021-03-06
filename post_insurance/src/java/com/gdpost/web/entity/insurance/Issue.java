package com.gdpost.web.entity.insurance;

import java.util.Calendar;
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

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import com.gdpost.utils.StringUtil;
import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;

/**
 * TIssue entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_issue")
public class Issue implements Idable<Long> {

	// Fields

	private Long id;
	private Organization organization;
	private String callMan;
	private String issueNo;
	private Policy policy;
	private String issueDesc;
	private String issueType;
	private String issueContent;
	private Date readyDate;
	private Date finishDate;
	private String bankCode;
	private String bankName;
	private Date shouldDate;
	private Date callDate;
	private Date issueDate;
	private Integer resetNum;
	private String recallFlag;
	private Date issueTime;
	private String status;
	private String result;
	private String dealMan;
	private Date dealTime;
	private String reopenReason;
	private User reopenUser;
	private Date reopenDate;
	private Long operateId;
	private Date operateTime;
	
	private String organName;
	private String holderPhone;
	private String holderMobile;
	
	private String addr;
	private String idCard;
	
	private String taskType;
	private String clientSexy;
	//private String issueRemark;
	private String clientName;
	private String issueOrg;
	private String clientNo;
	private String issueProv;
	private String issueCity;
	private String issueArea;
	private String dealDep;
	private String manualStatus;
	private String callType;
	private String fixCallRst;
	private String attrProd;
	private Double attrFee;
	private String attrYear;
	private String attrType;
	private String attrFeeYear;
	//private String autoStatus;
	private Date billBackDate;
	private String policyTerm;
	private String policyFeeType;
	private String policyFeeYear;
	private String checker;
	private Date checkDate;
	private String cityReviewer;
	private String cityReviewRst;
	private String closeUser;
	
	@Transient
	private Date readyDate1;
	@Transient
	private Date readyDate2;
	@Transient
	private Integer lastDateNum = 0;
	
	// Constructors
	@Transient
	public Date getReadyDate1() {
		return readyDate1;
	}
	@Transient
	public void setReadyDate1(Date readyDate1) {
		this.readyDate1 = readyDate1;
	}
	@Transient
	public Date getReadyDate2() {
		return readyDate2;
	}
	@Transient
	public void setReadyDate2(Date readyDate2) {
		this.readyDate2 = readyDate2;
	}

	@Transient
	public Integer getLastDateNum() {
		if(this.operateTime != null) {
			int check = StringUtil.getBetweenDay(this.operateTime, this.finishDate==null?new Date():this.finishDate);
			int c = 5-check;
			if(c < 0) {
				return 0;
			} else {
				return c;
			}
		}
		return lastDateNum;
	}
	
	@Transient
	public void setLastDateNum(Integer lastDateNum) {
		this.lastDateNum = lastDateNum;
	}
	/** default constructor */
	public Issue() {
	}

	/** minimal constructor */
	public Issue(Date readyDate) {
		this.readyDate = readyDate;
	}

	/** full constructor */
	public Issue(Organization organCode, String callMan, String issueNo, Policy policy, String issueDesc, String issueType, String issueContent,
			Date readyDate, Date finishDate, String bankCode, Date shouldDate, Date callDate, Date issueDate, Integer resetNum,
			String recallFlag, Date issueTime, String status, String result, String dealMan, Date dealTime, String reopenReason, Long reopenId, Date reopenDate) {
		this.organization = organCode;
		this.callMan = callMan;
		this.issueNo = issueNo;
		this.policy = policy;
		this.issueDesc = issueDesc;
		this.issueType = issueType;
		this.issueContent = issueContent;
		this.readyDate = readyDate;
		this.finishDate = finishDate;
		this.bankCode = bankCode;
		this.shouldDate = shouldDate;
		this.callDate = callDate;
		this.issueDate = issueDate;
		this.resetNum = resetNum;
		this.recallFlag = recallFlag;
		this.issueTime = issueTime;
		this.status = status;
		this.result = result;
		this.dealMan = dealMan;
		this.dealTime = dealTime;
		this.reopenReason = reopenReason;
		//this.reopenId = reopenId;
		this.reopenDate = reopenDate;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="organ_code", referencedColumnName="org_code")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

//	@Column(name = "organ_code", length = 18)
//	public String getOrganCode() {
//		return this.organCode;
//	}
//
//	public void setOrganCode(String organCode) {
//		this.organCode = organCode;
//	}

	@Column(name = "call_man", length = 8)
	public String getCallMan() {
		return this.callMan;
	}

	public void setCallMan(String callMan) {
		this.callMan = callMan;
	}

	@Column(name = "issue_no", length = 12)
	public String getIssueNo() {
		return this.issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

//	@ManyToOne(optional=true)
//	@JoinColumn(name="policy_no", referencedColumnName="policy_no", updatable=false)
	@ManyToOne(optional=true)
	@JoinColumnsOrFormulas(value={
	@JoinColumnOrFormula(column=@JoinColumn(name ="policy_no", referencedColumnName ="policy_no", insertable =false, updatable = false)),
	@JoinColumnOrFormula(formula=@JoinFormula(value="0", referencedColumnName = "attached_flag"))
	})
	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Column(name = "issue_desc", length = 6)
	public String getIssueDesc() {
		return this.issueDesc;
	}

	public void setIssueDesc(String issueDesc) {
		this.issueDesc = issueDesc;
	}

	@Column(name = "issue_type", length = 10)
	public String getIssueType() {
		return this.issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	@Column(name = "issue_content", length = 256)
	public String getIssueContent() {
		return this.issueContent;
	}

	public void setIssueContent(String issueContent) {
		this.issueContent = issueContent;
	}

	@Column(name = "ready_date", nullable = false, length = 19)
	public Date getReadyDate() {
		return this.readyDate;
	}

	public void setReadyDate(Date readyDate) {
		this.readyDate = readyDate;
	}

	@Column(name = "finish_date", length = 19)
	public Date getFinishDate() {
		if(this.finishDate != null && this.finishDate.after(this.readyDate)) {
			return this.finishDate;
		}
		Calendar ca = Calendar.getInstance();
		ca.setTime(this.readyDate);
		ca.add(Calendar.DATE, 5);
		return ca.getTime();
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	@Column(name = "bank_code", length = 20)
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "bank_name", length = 256)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "should_date", length = 10)
	public Date getShouldDate() {
		return this.shouldDate;
	}

	public void setShouldDate(Date shouldDate) {
		this.shouldDate = shouldDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "call_date", length = 10)
	public Date getCallDate() {
		return this.callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "issue_date", length = 10)
	public Date getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	@Column(name = "reset_num")
	public Integer getResetNum() {
		return this.resetNum;
	}

	public void setResetNum(Integer resetNum) {
		this.resetNum = resetNum;
	}

	@Column(name = "recall_flag", length = 2)
	public String getRecallFlag() {
		return this.recallFlag;
	}

	public void setRecallFlag(String recallFlag) {
		this.recallFlag = recallFlag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "issue_time", length = 10)
	public Date getIssueTime() {
		return this.issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	@Column(name = "status", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "result", length = 256)
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "deal_man", length = 32)
	public String getDealMan() {
		return this.dealMan;
	}

	public void setDealMan(String dealMan) {
		this.dealMan = dealMan;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "deal_time", length = 10)
	public Date getDealTime() {
		return this.dealTime;
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

	@Column(name = "operate_id")
	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "operate_time")
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name="organ_name")
	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	@Column(name = "holder_phone", length = 256)
	@ColumnTransformer(
			forColumn="holder_phone",
			read="cast(aes_decrypt(unhex(holder_phone), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getHolderPhone() {
		return holderPhone;
	}

	public void setHolderPhone(String holderPhone) {
		this.holderPhone = holderPhone;
	}

	@Column(name = "holder_mobile", length = 256)
	@ColumnTransformer(
			forColumn="holder_mobile",
			read="cast(aes_decrypt(unhex(holder_mobile), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getHolderMobile() {
		return holderMobile;
	}

	public void setHolderMobile(String holderMobile) {
		this.holderMobile = holderMobile;
	}

	@Column(name = "addr")
	@ColumnTransformer(
			forColumn="addr",
			read="cast(aes_decrypt(unhex(addr), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	@Column(name = "id_card")
	@ColumnTransformer(
			forColumn="id_card",
			read="cast(aes_decrypt(unhex(id_card), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	@Column(name="task_type")
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	/*
	@Column(name="issue_remark")
	public String getIssueRemark() {
		return issueRemark;
	}

	public void setIssueRemark(String issueRemark) {
		this.issueRemark = issueRemark;
	}
	*/
	@Column(name="issue_org")
	public String getIssueOrg() {
		return issueOrg;
	}

	public void setIssueOrg(String issueOrg) {
		this.issueOrg = issueOrg;
	}

	@Column(name="client_no")
	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	@Column(name="issue_prov")
	public String getIssueProv() {
		return issueProv;
	}

	public void setIssueProv(String issueProv) {
		this.issueProv = issueProv;
	}

	@Column(name="issue_city")
	public String getIssueCity() {
		return issueCity;
	}

	public void setIssueCity(String issueCity) {
		this.issueCity = issueCity;
	}

	@Column(name="issue_area")
	public String getIssueArea() {
		return issueArea;
	}

	public void setIssueArea(String issueArea) {
		this.issueArea = issueArea;
	}

	@Column(name="deal_dep")
	public String getDealDep() {
		return dealDep;
	}

	public void setDealDep(String dealDep) {
		this.dealDep = dealDep;
	}

	@Column(name="manual_status")
	public String getManualStatus() {
		return manualStatus;
	}

	public void setManualStatus(String manualStatus) {
		this.manualStatus = manualStatus;
	}

	@Column(name="call_type")
	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	@Column(name="fix_call_rst")
	public String getFixCallRst() {
		return fixCallRst;
	}

	public void setFixCallRst(String fixCallRst) {
		this.fixCallRst = fixCallRst;
	}

	@Column(name="attr_prod")
	public String getAttrProd() {
		return attrProd;
	}

	public void setAttrProd(String attrProd) {
		this.attrProd = attrProd;
	}

	@Column(name="attr_fee")
	public Double getAttrFee() {
		return attrFee;
	}

	public void setAttrFee(Double attrFee) {
		this.attrFee = attrFee;
	}

	@Column(name="attr_year")
	public String getAttrYear() {
		return attrYear;
	}

	public void setAttrYear(String attrYear) {
		this.attrYear = attrYear;
	}

	@Column(name="attr_type")
	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	@Column(name="attr_fee_year")
	public String getAttrFeeYear() {
		return attrFeeYear;
	}

	public void setAttrFeeYear(String attrFeeYear) {
		this.attrFeeYear = attrFeeYear;
	}
	/*
	@Column(name="auto_status")
	public String getAutoStatus() {
		return autoStatus;
	}

	public void setAutoStatus(String autoStatus) {
		this.autoStatus = autoStatus;
	}
	*/
	@Column(name="client_name")
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Column(name="client_sexy")
	public String getClientSexy() {
		return clientSexy;
	}

	public void setClientSexy(String clientSexy) {
		this.clientSexy = clientSexy;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="bill_back_date")
	public Date getBillBackDate() {
		return billBackDate;
	}

	public void setBillBackDate(Date billBackDate) {
		this.billBackDate = billBackDate;
	}

	@Column(name="policy_term")
	public String getPolicyTerm() {
		return policyTerm;
	}

	public void setPolicyTerm(String policyTerm) {
		this.policyTerm = policyTerm;
	}

	@Column(name="policy_fee_type")
	public String getPolicyFeeType() {
		return policyFeeType;
	}

	public void setPolicyFeeType(String policyFeeType) {
		this.policyFeeType = policyFeeType;
	}

	@Column(name="policy_fee_year")
	public String getPolicyFeeYear() {
		return policyFeeYear;
	}

	public void setPolicyFeeYear(String policyFeeYear) {
		this.policyFeeYear = policyFeeYear;
	}
	
	@Column(name="checker")
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="check_date")
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	@Column(name="city_reviewer")
	public String getCityReviewer() {
		return cityReviewer;
	}
	public void setCityReviewer(String cityReviewer) {
		this.cityReviewer = cityReviewer;
	}
	
	@Column(name="city_review_rst")
	public String getCityReviewRst() {
		return cityReviewRst;
	}
	public void setCityReviewRst(String cityReviewRst) {
		this.cityReviewRst = cityReviewRst;
	}
	
	@Column(name="close_user")
	public String getCloseUser() {
		return closeUser;
	}
	
	public void setCloseUser(String closeUser) {
		this.closeUser = closeUser;
	}
	
}