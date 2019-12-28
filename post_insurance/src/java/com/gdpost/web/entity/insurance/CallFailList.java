package com.gdpost.web.entity.insurance;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import com.gdpost.utils.StringUtil;
import com.gdpost.web.entity.Idable;

/**
 * TCallFailList entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_call_fail_list", uniqueConstraints = @UniqueConstraint(columnNames = "policy_no"))
public class CallFailList implements java.io.Serializable, Idable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5221839647252233993L;
	// Fields

	private Long id;
	private String organCode;
	private String callMan;
	private String issueNo;
	private Policy policy;
	// private String policyNo;
	private String issueDesc;
	private String issueType;
	private String issueContent;
	private String result;
	private String idCard;
	private String addr;
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
	private String dealMan;
	private String dealStatus;
	private String dealType;
	private String dealDesc;
	private Integer dealNum;
	private Boolean hasClientSigned;
	private Date dealTime;
	private String hqIssueType;
	private String hqDealType;
	private String hqDealMan;
	private String hqDealRst;
	private String hqDealRemark;
	private Date hqDealDate;
	private Date hqDealDate2;
	private String hqDealType2;
	private String hqDealMan2;
	private String hqDealRst2;
	private Date hqDealDate3;
	private String hqDealType3;
	private String hqDealMan3;
	private String hqDealRst3;
	private Date hqDealDate4;
	private String hqDealType4;
	private String hqDealMan4;
	private String hqDealRst4;
	private Date hqDealDate5;
	private String hqDealType5;
	private String hqDealMan5;
	private String hqDealRst5;
	private Date hqDealDate6;
	private String hqDealType6;
	private String hqDealMan6;
	private String hqDealRst6;
	private String provIssueType;
	private String provDealMan;
	private String provDealRst;
	private String provDealRemark;
	private Date provDealDate;
	private Long operateId;
	private Date operateTime;
	private String organName;
	private Integer hqDealNum;
	private Integer provDealNum;
	private String holderPhone;
	private String holderMobile;
	private Integer orgDealFlag;
	private Integer hqDealFlag;
	private Integer provDealFlag;
	private String resetPhone;
	private String hasLetter;
	private Date letterDate;
	private Boolean canCallAgain;
	private String canCallAgainRemark;
	private Date resetDate;
	private Date doorBackDate;
	private String doorStatsDate;
	private String mailFailReason;
	private Date mailFailDate;
	private String mailSuccess;
	private Date mailBackDate;
	private Date clientSignDate;
	private String clientRemark;
	private String hqDealTypeElse;
	private String phoneNum;
	private String phoneStart;
	private String phoneEnd;
	private String phoneTime;
	private String taskType;
	private String issueOrg;
	private String clientNo;
	private String clientName;
	private String clientSexy;
	private String issueProv;
	private String issueCity;
	private String issueArea;
	private String dealDep;
	private String manualStatus;
	private String callType;
	private Date billBackDate;
	private String policyTerm;
	private String policyFeeType;
	private String policyFeeYear;
	private String fixCallRst;
	private String attrProd;
	private Double attrFee;
	private String attrYear;
	private String attrType;
	private String attrFeeYear;

	@Transient
	private String search_LIKE_hasLetter;

	@Transient
	public String getSearch_LIKE_hasLetter() {
		return search_LIKE_hasLetter;
	}

	@Transient
	public void setSearch_LIKE_hasLetter(String search_LIKE_hasLetter) {
		this.search_LIKE_hasLetter = search_LIKE_hasLetter;
	}

	@Transient
	private Integer lastDateNum = 0;
	@Transient
	private String newStatus;
	@Transient
	private Integer duration;

	@Transient
	public Integer getDuration() {
		return duration;
	}

	@Transient
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Transient
	public String getNewStatus() {
		return this.newStatus;
	}

	@Transient
	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	@Transient
	public Integer getLastDateNum() {
		if (this.policy != null) {
			try {
				int check = StringUtil.getBetweenDay(
						this.policy.getBillBackDate() == null ? this.getBillBackDate() : this.policy.getBillBackDate(),
						new Date());
				return 15 - check;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lastDateNum;
	}

	@Transient
	public void setLastDateNum(Integer lastDateNum) {
		this.lastDateNum = lastDateNum;
	}

	// Constructors

	/** default constructor */
	public CallFailList() {
	}

	/** minimal constructor */
	public CallFailList(Policy policy, Date readyDate, Date finishDate, String status) {
		this.policy = policy;
		this.readyDate = readyDate;
		this.finishDate = finishDate;
		this.status = status;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "organ_code", length = 18, nullable=true)

	public String getOrganCode() {
		return this.organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	@Column(name = "call_man", length = 8, nullable=true)

	public String getCallMan() {
		return this.callMan;
	}

	public void setCallMan(String callMan) {
		this.callMan = callMan;
	}

	@Column(name = "issue_no", length = 21)

	public String getIssueNo() {
		return this.issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	@ManyToOne(cascade = CascadeType.REMOVE, targetEntity = Policy.class, fetch=FetchType.EAGER)
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

	@Column(name = "issue_desc", length = 6, nullable=true)

	public String getIssueDesc() {
		return this.issueDesc;
	}

	public void setIssueDesc(String issueDesc) {
		this.issueDesc = issueDesc;
	}

	@Column(name = "issue_type", length = 16, nullable=true)

	public String getIssueType() {
		return this.issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	@Column(name = "issue_content", length = 256, nullable=true)

	public String getIssueContent() {
		return this.issueContent;
	}

	public void setIssueContent(String issueContent) {
		this.issueContent = issueContent;
	}

	@Column(name = "result", length = 256, nullable=true)

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "id_card")
	@ColumnTransformer(
			forColumn="id_card",
			read="cast(aes_decrypt(unhex(id_card), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Column(name = "addr")
	@ColumnTransformer(
			forColumn="addr",
			read="cast(aes_decrypt(unhex(addr), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ready_date", nullable = false, length = 26)

	public Date getReadyDate() {
		return this.readyDate;
	}

	public void setReadyDate(Date readyDate) {
		this.readyDate = readyDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "finish_date", nullable = false, length = 26)

	public Date getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	@Column(name = "bank_code", length = 20, nullable=true)

	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "bank_name", length = 256, nullable=true)

	public String getBankName() {
		return this.bankName;
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

	@Column(name = "status", nullable = false, length = 16)

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

	@Temporal(TemporalType.DATE)
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

	@Column(name = "hq_deal_type", length = 20)

	public String getHqDealType() {
		return this.hqDealType;
	}

	public void setHqDealType(String hqDealType) {
		this.hqDealType = hqDealType;
	}

	@Column(name = "hq_deal_man", length = 20)

	public String getHqDealMan() {
		return this.hqDealMan;
	}

	public void setHqDealMan(String hqDealMan) {
		this.hqDealMan = hqDealMan;
	}

	@Column(name = "hq_deal_rst", length = 1024)

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

	@Temporal(TemporalType.DATE)
	@Column(name = "hq_deal_date", length = 10)

	public Date getHqDealDate() {
		return this.hqDealDate;
	}

	public void setHqDealDate(Date hqDealDate) {
		this.hqDealDate = hqDealDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "hq_deal_date2", length = 10)

	public Date getHqDealDate2() {
		return this.hqDealDate2;
	}

	public void setHqDealDate2(Date hqDealDate2) {
		this.hqDealDate2 = hqDealDate2;
	}

	@Column(name = "hq_deal_type2", length = 20)

	public String getHqDealType2() {
		return this.hqDealType2;
	}

	public void setHqDealType2(String hqDealType2) {
		this.hqDealType2 = hqDealType2;
	}

	@Column(name = "hq_deal_man2", length = 20)

	public String getHqDealMan2() {
		return this.hqDealMan2;
	}

	public void setHqDealMan2(String hqDealMan2) {
		this.hqDealMan2 = hqDealMan2;
	}

	@Column(name = "hq_deal_rst2", length = 120)

	public String getHqDealRst2() {
		return this.hqDealRst2;
	}

	public void setHqDealRst2(String hqDealRst2) {
		this.hqDealRst2 = hqDealRst2;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "hq_deal_date3", length = 10)

	public Date getHqDealDate3() {
		return this.hqDealDate3;
	}

	public void setHqDealDate3(Date hqDealDate3) {
		this.hqDealDate3 = hqDealDate3;
	}

	@Column(name = "hq_deal_type3", length = 20)

	public String getHqDealType3() {
		return this.hqDealType3;
	}

	public void setHqDealType3(String hqDealType3) {
		this.hqDealType3 = hqDealType3;
	}

	@Column(name = "hq_deal_man3", length = 20)

	public String getHqDealMan3() {
		return this.hqDealMan3;
	}

	public void setHqDealMan3(String hqDealMan3) {
		this.hqDealMan3 = hqDealMan3;
	}

	@Column(name = "hq_deal_rst3", length = 120)

	public String getHqDealRst3() {
		return this.hqDealRst3;
	}

	public void setHqDealRst3(String hqDealRst3) {
		this.hqDealRst3 = hqDealRst3;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "hq_deal_date4", length = 10)

	public Date getHqDealDate4() {
		return this.hqDealDate4;
	}

	public void setHqDealDate4(Date hqDealDate4) {
		this.hqDealDate4 = hqDealDate4;
	}

	@Column(name = "hq_deal_type4", length = 20)

	public String getHqDealType4() {
		return this.hqDealType4;
	}

	public void setHqDealType4(String hqDealType4) {
		this.hqDealType4 = hqDealType4;
	}

	@Column(name = "hq_deal_man4", length = 20)

	public String getHqDealMan4() {
		return this.hqDealMan4;
	}

	public void setHqDealMan4(String hqDealMan4) {
		this.hqDealMan4 = hqDealMan4;
	}

	@Column(name = "hq_deal_rst4", length = 120)

	public String getHqDealRst4() {
		return this.hqDealRst4;
	}

	public void setHqDealRst4(String hqDealRst4) {
		this.hqDealRst4 = hqDealRst4;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "hq_deal_date5", length = 10)

	public Date getHqDealDate5() {
		return this.hqDealDate5;
	}

	public void setHqDealDate5(Date hqDealDate5) {
		this.hqDealDate5 = hqDealDate5;
	}

	@Column(name = "hq_deal_type5", length = 20)

	public String getHqDealType5() {
		return this.hqDealType5;
	}

	public void setHqDealType5(String hqDealType5) {
		this.hqDealType5 = hqDealType5;
	}

	@Column(name = "hq_deal_man5", length = 20)

	public String getHqDealMan5() {
		return this.hqDealMan5;
	}

	public void setHqDealMan5(String hqDealMan5) {
		this.hqDealMan5 = hqDealMan5;
	}

	@Column(name = "hq_deal_rst5", length = 120)

	public String getHqDealRst5() {
		return this.hqDealRst5;
	}

	public void setHqDealRst5(String hqDealRst5) {
		this.hqDealRst5 = hqDealRst5;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "hq_deal_date6", length = 10)

	public Date getHqDealDate6() {
		return this.hqDealDate6;
	}

	public void setHqDealDate6(Date hqDealDate6) {
		this.hqDealDate6 = hqDealDate6;
	}

	@Column(name = "hq_deal_type6", length = 20)

	public String getHqDealType6() {
		return this.hqDealType6;
	}

	public void setHqDealType6(String hqDealType6) {
		this.hqDealType6 = hqDealType6;
	}

	@Column(name = "hq_deal_man6", length = 20)

	public String getHqDealMan6() {
		return this.hqDealMan6;
	}

	public void setHqDealMan6(String hqDealMan6) {
		this.hqDealMan6 = hqDealMan6;
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

	@Column(name = "prov_deal_man", length = 20)

	public String getProvDealMan() {
		return this.provDealMan;
	}

	public void setProvDealMan(String provDealMan) {
		this.provDealMan = provDealMan;
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

	@Temporal(TemporalType.DATE)
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

	@Temporal(TemporalType.DATE)
	@Column(name = "operate_time", length = 26)

	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
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
	@ColumnTransformer(
			forColumn="holder_phone",
			read="cast(aes_decrypt(unhex(holder_phone), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getHolderPhone() {
		return this.holderPhone;
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
		return this.holderMobile;
	}

	public void setHolderMobile(String holderMobile) {
		this.holderMobile = holderMobile;
	}

	@Column(name = "org_deal_flag")

	public Integer getOrgDealFlag() {
		return this.orgDealFlag;
	}

	public void setOrgDealFlag(Integer orgDealFlag) {
		this.orgDealFlag = orgDealFlag;
	}

	@Column(name = "hq_deal_flag")

	public Integer getHqDealFlag() {
		return this.hqDealFlag;
	}

	public void setHqDealFlag(Integer hqDealFlag) {
		this.hqDealFlag = hqDealFlag;
	}

	@Column(name = "prov_deal_flag")

	public Integer getProvDealFlag() {
		return this.provDealFlag;
	}

	public void setProvDealFlag(Integer provDealFlag) {
		this.provDealFlag = provDealFlag;
	}

	@Column(name = "reset_phone", length = 256)
	@ColumnTransformer(
			forColumn="reset_phone",
			read="cast(aes_decrypt(unhex(reset_phone), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getResetPhone() {
		return this.resetPhone;
	}

	public void setResetPhone(String resetPhone) {
		this.resetPhone = resetPhone;
	}

	@Column(name = "has_letter", length = 32)

	public String getHasLetter() {
		return this.hasLetter;
	}

	public void setHasLetter(String hasLetter) {
		this.hasLetter = hasLetter;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "letter_date", length = 10)

	public Date getLetterDate() {
		return this.letterDate;
	}

	public void setLetterDate(Date letterDate) {
		this.letterDate = letterDate;
	}

	@Column(name = "can_call_again")

	public Boolean getCanCallAgain() {
		return this.canCallAgain;
	}

	public void setCanCallAgain(Boolean canCallAgain) {
		this.canCallAgain = canCallAgain;
	}

	@Column(name = "can_call_again_remark", length = 127)

	public String getCanCallAgainRemark() {
		return this.canCallAgainRemark;
	}

	public void setCanCallAgainRemark(String canCallAgainRemark) {
		this.canCallAgainRemark = canCallAgainRemark;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "reset_date", length = 10)

	public Date getResetDate() {
		return this.resetDate;
	}

	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "door_back_date", length = 10)

	public Date getDoorBackDate() {
		return this.doorBackDate;
	}

	public void setDoorBackDate(Date doorBackDate) {
		this.doorBackDate = doorBackDate;
	}

	@Column(name = "door_stats_date", length = 12)

	public String getDoorStatsDate() {
		return this.doorStatsDate;
	}

	public void setDoorStatsDate(String doorStatsDate) {
		this.doorStatsDate = doorStatsDate;
	}

	@Column(name = "mail_fail_reason", length = 128)

	public String getMailFailReason() {
		return this.mailFailReason;
	}

	public void setMailFailReason(String mailFailReason) {
		this.mailFailReason = mailFailReason;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "mail_fail_date", length = 10)

	public Date getMailFailDate() {
		return this.mailFailDate;
	}

	public void setMailFailDate(Date mailFailDate) {
		this.mailFailDate = mailFailDate;
	}

	@Column(name = "mail_success", length = 32)

	public String getMailSuccess() {
		return this.mailSuccess;
	}

	public void setMailSuccess(String mailSuccess) {
		this.mailSuccess = mailSuccess;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "mail_back_date", length = 10)

	public Date getMailBackDate() {
		return this.mailBackDate;
	}

	public void setMailBackDate(Date mailBackDate) {
		this.mailBackDate = mailBackDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "client_sign_date", length = 10)

	public Date getClientSignDate() {
		return this.clientSignDate;
	}

	public void setClientSignDate(Date clientSignDate) {
		this.clientSignDate = clientSignDate;
	}

	@Column(name = "client_remark", length = 254)

	public String getClientRemark() {
		return this.clientRemark;
	}

	public void setClientRemark(String clientRemark) {
		this.clientRemark = clientRemark;
	}

	@Column(name = "hq_deal_type_else", length = 254)

	public String getHqDealTypeElse() {
		return this.hqDealTypeElse;
	}

	public void setHqDealTypeElse(String hqDealTypeElse) {
		this.hqDealTypeElse = hqDealTypeElse;
	}

	@Column(name = "phone_num", length = 23)

	public String getPhoneNum() {
		return this.phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Column(name = "phone_start", length = 23)

	public String getPhoneStart() {
		return this.phoneStart;
	}

	public void setPhoneStart(String phoneStart) {
		this.phoneStart = phoneStart;
	}

	@Column(name = "phone_end", length = 23)

	public String getPhoneEnd() {
		return this.phoneEnd;
	}

	public void setPhoneEnd(String phoneEnd) {
		this.phoneEnd = phoneEnd;
	}

	@Column(name = "phone_time", length = 23)

	public String getPhoneTime() {
		return this.phoneTime;
	}

	public void setPhoneTime(String phoneTime) {
		this.phoneTime = phoneTime;
	}

	@Column(name = "task_type", length = 32)

	public String getTaskType() {
		return this.taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	@Column(name = "issue_org", length = 32)

	public String getIssueOrg() {
		return this.issueOrg;
	}

	public void setIssueOrg(String issueOrg) {
		this.issueOrg = issueOrg;
	}

	@Column(name = "client_no", length = 16)

	public String getClientNo() {
		return this.clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	@Column(name = "client_name", length = 16)

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Column(name = "client_sexy", length = 6)

	public String getClientSexy() {
		return this.clientSexy;
	}

	public void setClientSexy(String clientSexy) {
		this.clientSexy = clientSexy;
	}

	@Column(name = "issue_prov", length = 16)

	public String getIssueProv() {
		return this.issueProv;
	}

	public void setIssueProv(String issueProv) {
		this.issueProv = issueProv;
	}

	@Column(name = "issue_city", length = 16)

	public String getIssueCity() {
		return this.issueCity;
	}

	public void setIssueCity(String issueCity) {
		this.issueCity = issueCity;
	}

	@Column(name = "issue_area", length = 16)

	public String getIssueArea() {
		return this.issueArea;
	}

	public void setIssueArea(String issueArea) {
		this.issueArea = issueArea;
	}

	@Column(name = "deal_dep", length = 64)

	public String getDealDep() {
		return this.dealDep;
	}

	public void setDealDep(String dealDep) {
		this.dealDep = dealDep;
	}

	@Column(name = "manual_status", length = 32)

	public String getManualStatus() {
		return this.manualStatus;
	}

	public void setManualStatus(String manualStatus) {
		this.manualStatus = manualStatus;
	}

	@Column(name = "call_type", length = 32)

	public String getCallType() {
		return this.callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "bill_back_date", length = 10)

	public Date getBillBackDate() {
		return this.billBackDate;
	}

	public void setBillBackDate(Date billBackDate) {
		this.billBackDate = billBackDate;
	}

	@Column(name = "policy_term", length = 6)

	public String getPolicyTerm() {
		return this.policyTerm;
	}

	public void setPolicyTerm(String policyTerm) {
		this.policyTerm = policyTerm;
	}

	@Column(name = "policy_fee_type", length = 6)

	public String getPolicyFeeType() {
		return this.policyFeeType;
	}

	public void setPolicyFeeType(String policyFeeType) {
		this.policyFeeType = policyFeeType;
	}

	@Column(name = "policy_fee_year", length = 6)

	public String getPolicyFeeYear() {
		return this.policyFeeYear;
	}

	public void setPolicyFeeYear(String policyFeeYear) {
		this.policyFeeYear = policyFeeYear;
	}

	@Column(name = "fix_call_rst", length = 32)

	public String getFixCallRst() {
		return this.fixCallRst;
	}

	public void setFixCallRst(String fixCallRst) {
		this.fixCallRst = fixCallRst;
	}

	@Column(name = "attr_prod", length = 32)

	public String getAttrProd() {
		return this.attrProd;
	}

	public void setAttrProd(String attrProd) {
		this.attrProd = attrProd;
	}

	@Column(name = "attr_fee", precision = 22, scale = 0)

	public Double getAttrFee() {
		return this.attrFee;
	}

	public void setAttrFee(Double attrFee) {
		this.attrFee = attrFee;
	}

	@Column(name = "attr_year", length = 32)

	public String getAttrYear() {
		return this.attrYear;
	}

	public void setAttrYear(String attrYear) {
		this.attrYear = attrYear;
	}

	@Column(name = "attr_type", length = 32)

	public String getAttrType() {
		return this.attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	@Column(name = "attr_fee_year", length = 32)

	public String getAttrFeeYear() {
		return this.attrFeeYear;
	}

	public void setAttrFeeYear(String attrFeeYear) {
		this.attrFeeYear = attrFeeYear;
	}

}