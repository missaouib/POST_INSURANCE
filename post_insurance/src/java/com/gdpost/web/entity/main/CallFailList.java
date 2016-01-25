package com.gdpost.web.entity.main;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnTransformer;

import com.gdpost.utils.StringUtil;
import com.gdpost.web.entity.Idable;

@Entity
@Table(name="t_call_fail_list")
public class CallFailList implements Idable<Long> {

	// Fields

	private Long id;
	private Organization organization;
	private String callMan;
	private String issueNo;
	private Policy policy;
	private String issueDesc;
	private String issueType;
	private String issueContent;
	private String addr;
	private String result;
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
	
	private String hqDealType;
	private String hqDealMan;
	private String hqDealType2;
	private String hqDealMan2;
	private String hqDealType3;
	private String hqDealMan3;
	private String hqDealType4;
	private String hqDealMan4;
	private String hqDealType5;
	private String hqDealMan5;
	private String hqDealType6;
	private String hqDealMan6;
	
	private String provIssueType;
	private String provDealRst;
	private String provDealRemark;
	private Date provDealDate;

	private Integer hqDealNum;
	private Integer provDealNum;
	private String organName;
	private String holderPhone;
	private String holderMobile;
	
	private String resetPhone; 
	private Integer orgDealFlag;
	private Integer hqDealFlag;
	private Integer provDealFlag;
	private String provDealMan;
	
	private String hasLetter;
	private Date letterDate;
	private String idCard;
	
	private Boolean canCallAgain;
	private String canCallAgainRemark;
	private Date resetDate;
	
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
	public String getNewStatus() {
		return newStatus;
	}
	@Transient
	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	@Transient
	public Integer getLastDateNum() {
		if(this.policy != null) {
			try {
//				Calendar c1 = Calendar.getInstance();
//				c1.setTime(this.policy.getPolicyDate());
//				Calendar now = Calendar.getInstance();
//				now.setTime(new Date());
				int check = StringUtil.getBetweenDay(this.policy.getPolicyDate(), new Date());
				int c = 15-check;
				if(c<0) {
					return 0;
				} else {
					return c;
				}
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
	public CallFailList(Policy policy, Date readyDate) {
		this.policy = policy;
		this.readyDate = readyDate;
	}

	/** full constructor */
	public CallFailList(Organization organization, String callMan, String issueNo, Policy policy, String issueDesc, String issueType, String issueContent,
			String result, Date readyDate, Date finishDate, String bankCode, String bankName, Date shouldDate, Date callDate, Date issueDate,
			Integer resetNum, String recallFlag, Date issueTime, String status, String dealMan, String dealStatus, String dealType, String dealDesc,
			Integer dealNum, Boolean hasClientSigned, Date dealTime, String hqIssueType, String hqDealRst, String hqDealRemark, Date hqDealDate,
			Date hqDealDate2, String hqDealRst2, Date hqDealDate3, String hqDealRst3, Date hqDealDate4, String hqDealRst4, Date hqDealDate5, String hqDealRst5,
			String provIssueType, String provDealRst, String provDealRemark, Date provDealDate) {
		this.organization = organization;
		this.callMan = callMan;
		this.issueNo = issueNo;
		this.policy = policy;
		this.issueDesc = issueDesc;
		this.issueType = issueType;
		this.issueContent = issueContent;
		this.result = result;
		this.readyDate = readyDate;
		this.finishDate = finishDate;
		this.bankCode = bankCode;
		this.bankName = bankName;
		this.shouldDate = shouldDate;
		this.callDate = callDate;
		this.issueDate = issueDate;
		this.resetNum = resetNum;
		this.recallFlag = recallFlag;
		this.issueTime = issueTime;
		this.status = status;
		this.dealMan = dealMan;
		this.dealStatus = dealStatus;
		this.dealType = dealType;
		this.dealDesc = dealDesc;
		this.dealNum = dealNum;
		this.hasClientSigned = hasClientSigned;
		this.dealTime = dealTime;
		this.hqIssueType = hqIssueType;
		this.hqDealRst = hqDealRst;
		this.hqDealRemark = hqDealRemark;
		this.hqDealDate = hqDealDate;
		this.hqDealDate2 = hqDealDate2;
		this.hqDealRst2 = hqDealRst2;
		this.hqDealDate3 = hqDealDate3;
		this.hqDealRst3 = hqDealRst3;
		this.hqDealDate4 = hqDealDate4;
		this.hqDealRst4 = hqDealRst4;
		this.hqDealDate5 = hqDealDate5;
		this.hqDealRst5 = hqDealRst5;
		this.provIssueType = provIssueType;
		this.provDealRst = provDealRst;
		this.provDealRemark = provDealRemark;
		this.provDealDate = provDealDate;
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
		return this.policy;
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
	public Date getReadyDate() {
		return this.readyDate;
	}

	public void setReadyDate(Date readyDate) {
		this.readyDate = readyDate;
	}

	@Column(name = "finish_date", length = 19)
	public Date getFinishDate() {
		return this.finishDate;
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

	@Column(name = "hq_deal_date6")
	public Date getHqDealDate6() {
		return hqDealDate6;
	}
	
	public void setHqDealDate6(Date hqDealDate6) {
		this.hqDealDate6 = hqDealDate6;
	}
	
	@Column(name = "hq_deal_rst6", length = 120)
	public String getHqDealRst6() {
		return hqDealRst6;
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

	@Column(name="hq_deal_num")
	public Integer getHqDealNum() {
		return hqDealNum;
	}

	public void setHqDealNum(Integer hqDealNum) {
		this.hqDealNum = hqDealNum;
	}

	@Column(name="prov_deal_num")
	public Integer getProvDealNum() {
		return provDealNum;
	}

	public void setProvDealNum(Integer provDealNum) {
		this.provDealNum = provDealNum;
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

	@Column(name = "reset_phone", length = 256)
	@ColumnTransformer(
			forColumn="reset_phone",
			read="cast(aes_decrypt(unhex(reset_phone), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getResetPhone() {
		return resetPhone;
	}

	public void setResetPhone(String resetPhone) {
		this.resetPhone = resetPhone;
	}
	
	@Column(name="org_deal_flag")
	public Integer getOrgDealFlag() {
		return orgDealFlag;
	}
	
	public void setOrgDealFlag(Integer orgDealFlag) {
		this.orgDealFlag = orgDealFlag;
	}
	
	@Column(name="hq_deal_flag")
	public Integer getHqDealFlag() {
		return hqDealFlag;
	}
	
	public void setHqDealFlag(Integer hqDealFlag) {
		this.hqDealFlag = hqDealFlag;
	}
	
	@Column(name="prov_deal_flag")
	public Integer getProvDealFlag() {
		return provDealFlag;
	}
	
	public void setProvDealFlag(Integer provDealFlag) {
		this.provDealFlag = provDealFlag;
	}
	@Column(name="prov_deal_man")
	public String getProvDealMan() {
		return provDealMan;
	}
	public void setProvDealMan(String provDealMan) {
		this.provDealMan = provDealMan;
	}
	@Column(name="has_letter")
	public String getHasLetter() {
		return hasLetter;
	}
	
	public void setHasLetter(String hasLetter) {
		this.hasLetter = hasLetter;
	}
	
	@Column(name="letter_date")
	public Date getLetterDate() {
		return letterDate;
	}
	
	public void setLetterDate(Date letterDate) {
		this.letterDate = letterDate;
	}
	
	@Column(name="hq_deal_type")
	public String getHqDealType() {
		return hqDealType;
	}
	
	public void setHqDealType(String hqDealType) {
		this.hqDealType = hqDealType;
	}
	
	@Column(name="hq_deal_man")
	public String getHqDealMan() {
		return hqDealMan;
	}
	
	public void setHqDealMan(String hqDealMan) {
		this.hqDealMan = hqDealMan;
	}
	
	@Column(name="hq_deal_type2")
	public String getHqDealType2() {
		return hqDealType2;
	}
	
	public void setHqDealType2(String hqDealType2) {
		this.hqDealType2 = hqDealType2;
	}
	
	@Column(name="hq_deal_man2")
	public String getHqDealMan2() {
		return hqDealMan2;
	}
	
	public void setHqDealMan2(String hqDealMan2) {
		this.hqDealMan2 = hqDealMan2;
	}
	
	@Column(name="hq_deal_type3")
	public String getHqDealType3() {
		return hqDealType3;
	}
	
	public void setHqDealType3(String hqDealType3) {
		this.hqDealType3 = hqDealType3;
	}
	
	@Column(name="hq_deal_man3")
	public String getHqDealMan3() {
		return hqDealMan3;
	}
	
	public void setHqDealMan3(String hqDealMan3) {
		this.hqDealMan3 = hqDealMan3;
	}
	
	@Column(name="hq_deal_type4")
	public String getHqDealType4() {
		return hqDealType4;
	}
	
	public void setHqDealType4(String hqDealType4) {
		this.hqDealType4 = hqDealType4;
	}
	
	@Column(name="hq_deal_man4")
	public String getHqDealMan4() {
		return hqDealMan4;
	}
	
	public void setHqDealMan4(String hqDealMan4) {
		this.hqDealMan4 = hqDealMan4;
	}
	
	@Column(name="hq_deal_type5")
	public String getHqDealType5() {
		return hqDealType5;
	}
	
	public void setHqDealType5(String hqDealType5) {
		this.hqDealType5 = hqDealType5;
	}
	
	@Column(name="hq_deal_man5")
	public String getHqDealMan5() {
		return hqDealMan5;
	}
	public void setHqDealMan5(String hqDealMan5) {
		this.hqDealMan5 = hqDealMan5;
	}
	
	@Column(name="hq_deal_type6")
	public String getHqDealType6() {
		return hqDealType6;
	}
	
	public void setHqDealType6(String hqDealType6) {
		this.hqDealType6 = hqDealType6;
	}
	
	@Column(name="hq_deal_man6")
	public String getHqDealMan6() {
		return hqDealMan6;
	}
	
	public void setHqDealMan6(String hqDealMan6) {
		this.hqDealMan6 = hqDealMan6;
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
	@Column(name="can_call_again")
	public Boolean getCanCallAgain() {
		return canCallAgain;
	}
	public void setCanCallAgain(Boolean canCallAgain) {
		this.canCallAgain = canCallAgain;
	}
	@Column(name="can_call_again_remark")
	public String getCanCallAgainRemark() {
		return canCallAgainRemark;
	}
	public void setCanCallAgainRemark(String canCallAgainRemark) {
		this.canCallAgainRemark = canCallAgainRemark;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="reset_date")
	public Date getResetDate() {
		return resetDate;
	}
	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}
}