package com.gdpost.web.entity.component;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.Policy;

/**
 * VCallFailList entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "v_call_fail_list")
public class VCallFailList implements Idable<Long>, java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3695716813981946939L;
	private Long id;
	private Organization organization;
	private String callMan;
	private String issueNo;
	private Policy policy;
	private String issueDesc;
	private String issueType;
	private String issueContent;
	private String result;
	private Timestamp readyDate;
	private Timestamp finishDate;
	private String bankCode;
	private String bankName;
	private Date shouldDate;
	private Date callDate;
	private Date issueDate;
	private Integer resetNum;
	private String recallFlag;
	private Date issueTime;
	private String status;
	private String dealMan;
	private String dealStatus;
	private String dealType;
	private String dealDesc;
	private Integer dealNum;
	private Boolean hasClientSigned;
	private Date dealTime;
	private String hqIssueType;
	private String hqDealRst;
	private String hqDealRemark;
	private Date hqDealDate;
	private Date hqDealDate2;
	private String hqDealRst2;
	private Date hqDealDate3;
	private String hqDealRst3;
	private Date hqDealDate4;
	private String hqDealRst4;
	private Date hqDealDate5;
	private String hqDealRst5;
	private Date hqDealDate6;
	private String hqDealRst6;
	private String provIssueType;
	private String provDealRst;
	private String provDealRemark;
	private Date provDealDate;
	private Long operateId;
	private Timestamp operateTime;
	private String resetPhone;
	private String organName;
	private Integer hqDealNum;
	private Integer provDealNum;
	private String holderPhone;
	private String holderMobile;
	private Short orgDealFlag;
	private Short hqDealFlag;
	private Short provDealFlag;
	private String hasLetter;
	private Date letterDate;
	private Date checkDate;
	private Date endDate;
	private Integer lastDateNum;

	// Constructors

	/** default constructor */
	public VCallFailList() {
	}

	// Property accessors
	@Id
	@Column(name = "id", nullable = false)
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

	@ManyToOne
	@JoinColumn(name="policy_no", referencedColumnName="policy_no")
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

	@Column(name = "result", length = 256)
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "ready_date", nullable = false, length = 19)
	public Timestamp getReadyDate() {
		return this.readyDate;
	}

	public void setReadyDate(Timestamp readyDate) {
		this.readyDate = readyDate;
	}

	@Column(name = "finish_date", nullable = false, length = 19)
	public Timestamp getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(Timestamp finishDate) {
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
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "should_date", length = 10)
	public Date getShouldDate() {
		return this.shouldDate;
	}

	public void setShouldDate(Date shouldDate) {
		this.shouldDate = shouldDate;
	}

	@Column(name = "call_date", length = 10)
	public Date getCallDate() {
		return this.callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

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

	@Column(name = "deal_man", length = 32)
	public String getDealMan() {
		return this.dealMan;
	}

	public void setDealMan(String dealMan) {
		this.dealMan = dealMan;
	}

	@Column(name = "deal_status", length = 12)
	public String getDealStatus() {
		return this.dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	@Column(name = "deal_type", length = 20)
	public String getDealType() {
		return this.dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	@Column(name = "deal_desc", length = 256)
	public String getDealDesc() {
		return this.dealDesc;
	}

	public void setDealDesc(String dealDesc) {
		this.dealDesc = dealDesc;
	}

	@Column(name = "deal_num")
	public Integer getDealNum() {
		return this.dealNum;
	}

	public void setDealNum(Integer dealNum) {
		this.dealNum = dealNum;
	}

	@Column(name = "has_client_signed")
	public Boolean getHasClientSigned() {
		return this.hasClientSigned;
	}

	public void setHasClientSigned(Boolean hasClientSigned) {
		this.hasClientSigned = hasClientSigned;
	}

	@Column(name = "deal_time", length = 10)
	public Date getDealTime() {
		return this.dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	@Column(name = "hq_issue_type", length = 20)
	public String getHqIssueType() {
		return this.hqIssueType;
	}

	public void setHqIssueType(String hqIssueType) {
		this.hqIssueType = hqIssueType;
	}

	@Column(name = "hq_deal_rst", length = 250)
	public String getHqDealRst() {
		return this.hqDealRst;
	}

	public void setHqDealRst(String hqDealRst) {
		this.hqDealRst = hqDealRst;
	}

	@Column(name = "hq_deal_remark", length = 20)
	public String getHqDealRemark() {
		return this.hqDealRemark;
	}

	public void setHqDealRemark(String hqDealRemark) {
		this.hqDealRemark = hqDealRemark;
	}

	@Column(name = "hq_deal_date", length = 10)
	public Date getHqDealDate() {
		return this.hqDealDate;
	}

	public void setHqDealDate(Date hqDealDate) {
		this.hqDealDate = hqDealDate;
	}

	@Column(name = "hq_deal_date2", length = 10)
	public Date getHqDealDate2() {
		return this.hqDealDate2;
	}

	public void setHqDealDate2(Date hqDealDate2) {
		this.hqDealDate2 = hqDealDate2;
	}

	@Column(name = "hq_deal_rst2", length = 120)
	public String getHqDealRst2() {
		return this.hqDealRst2;
	}

	public void setHqDealRst2(String hqDealRst2) {
		this.hqDealRst2 = hqDealRst2;
	}

	@Column(name = "hq_deal_date3", length = 10)
	public Date getHqDealDate3() {
		return this.hqDealDate3;
	}

	public void setHqDealDate3(Date hqDealDate3) {
		this.hqDealDate3 = hqDealDate3;
	}

	@Column(name = "hq_deal_rst3", length = 120)
	public String getHqDealRst3() {
		return this.hqDealRst3;
	}

	public void setHqDealRst3(String hqDealRst3) {
		this.hqDealRst3 = hqDealRst3;
	}

	@Column(name = "hq_deal_date4", length = 10)
	public Date getHqDealDate4() {
		return this.hqDealDate4;
	}

	public void setHqDealDate4(Date hqDealDate4) {
		this.hqDealDate4 = hqDealDate4;
	}

	@Column(name = "hq_deal_rst4", length = 120)
	public String getHqDealRst4() {
		return this.hqDealRst4;
	}

	public void setHqDealRst4(String hqDealRst4) {
		this.hqDealRst4 = hqDealRst4;
	}

	@Column(name = "hq_deal_date5", length = 10)
	public Date getHqDealDate5() {
		return this.hqDealDate5;
	}

	public void setHqDealDate5(Date hqDealDate5) {
		this.hqDealDate5 = hqDealDate5;
	}

	@Column(name = "hq_deal_rst5", length = 120)
	public String getHqDealRst5() {
		return this.hqDealRst5;
	}

	public void setHqDealRst5(String hqDealRst5) {
		this.hqDealRst5 = hqDealRst5;
	}

	@Column(name = "hq_deal_date6", length = 10)
	public Date getHqDealDate6() {
		return this.hqDealDate6;
	}

	public void setHqDealDate6(Date hqDealDate6) {
		this.hqDealDate6 = hqDealDate6;
	}

	@Column(name = "hq_deal_rst6", length = 120)
	public String getHqDealRst6() {
		return this.hqDealRst6;
	}

	public void setHqDealRst6(String hqDealRst6) {
		this.hqDealRst6 = hqDealRst6;
	}

	@Column(name = "prov_issue_type", length = 20)
	public String getProvIssueType() {
		return this.provIssueType;
	}

	public void setProvIssueType(String provIssueType) {
		this.provIssueType = provIssueType;
	}

	@Column(name = "prov_deal_rst", length = 250)
	public String getProvDealRst() {
		return this.provDealRst;
	}

	public void setProvDealRst(String provDealRst) {
		this.provDealRst = provDealRst;
	}

	@Column(name = "prov_deal_remark", length = 20)
	public String getProvDealRemark() {
		return this.provDealRemark;
	}

	public void setProvDealRemark(String provDealRemark) {
		this.provDealRemark = provDealRemark;
	}

	@Column(name = "prov_deal_date", length = 10)
	public Date getProvDealDate() {
		return this.provDealDate;
	}

	public void setProvDealDate(Date provDealDate) {
		this.provDealDate = provDealDate;
	}

	@Column(name = "operate_id")
	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Column(name = "operate_time", length = 19)
	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "reset_phone", length = 256)
	public String getResetPhone() {
		return this.resetPhone;
	}

	public void setResetPhone(String resetPhone) {
		this.resetPhone = resetPhone;
	}

	@Column(name = "organ_name", length = 20)
	public String getOrganName() {
		return this.organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	@Column(name = "hq_deal_num")
	public Integer getHqDealNum() {
		return this.hqDealNum;
	}

	public void setHqDealNum(Integer hqDealNum) {
		this.hqDealNum = hqDealNum;
	}

	@Column(name = "prov_deal_num")
	public Integer getProvDealNum() {
		return this.provDealNum;
	}

	public void setProvDealNum(Integer provDealNum) {
		this.provDealNum = provDealNum;
	}

	@Column(name = "holder_phone", length = 256)
	public String getHolderPhone() {
		return this.holderPhone;
	}

	public void setHolderPhone(String holderPhone) {
		this.holderPhone = holderPhone;
	}

	@Column(name = "holder_mobile", length = 256)
	public String getHolderMobile() {
		return this.holderMobile;
	}

	public void setHolderMobile(String holderMobile) {
		this.holderMobile = holderMobile;
	}

	@Column(name = "org_deal_flag")
	public Short getOrgDealFlag() {
		return this.orgDealFlag;
	}

	public void setOrgDealFlag(Short orgDealFlag) {
		this.orgDealFlag = orgDealFlag;
	}

	@Column(name = "hq_deal_flag")
	public Short getHqDealFlag() {
		return this.hqDealFlag;
	}

	public void setHqDealFlag(Short hqDealFlag) {
		this.hqDealFlag = hqDealFlag;
	}

	@Column(name = "prov_deal_flag")
	public Short getProvDealFlag() {
		return this.provDealFlag;
	}

	public void setProvDealFlag(Short provDealFlag) {
		this.provDealFlag = provDealFlag;
	}

	@Column(name = "has_letter", length = 32)
	public String getHasLetter() {
		return this.hasLetter;
	}

	public void setHasLetter(String hasLetter) {
		this.hasLetter = hasLetter;
	}

	@Column(name = "letter_date", length = 10)
	public Date getLetterDate() {
		return this.letterDate;
	}

	public void setLetterDate(Date letterDate) {
		this.letterDate = letterDate;
	}

	@Column(name = "check_date", length = 10)
	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name = "end_date", length = 10)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "last_date_num")
	public Integer getLastDateNum() {
		return this.lastDateNum;
	}

	public void setLastDateNum(Integer lastDateNum) {
		this.lastDateNum = lastDateNum;
	}

}
