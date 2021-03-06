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
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;

import com.gdpost.utils.StringUtil;
import com.gdpost.web.entity.Idable;

/**
 * TCheckRecordDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_renewed_list")
public class RenewedList implements Idable<Long> {

	// Fields

	private Long id;
	private String holder;
	private Integer policyYear;
	private String prdName;
	private Date feeDate;
	private Policy policy;
	private String policyFee;
	private String feeStatus;
	private String feeFailReason;
	private String accountBank;
	private String account;
	private String addr;
	private String postCode;
	private String phone;
	private String mobile;
	private String orgName;
	private String netName;
	private String policyAdmin;
	private String channel;
	private Long operateId;
	private Date operateTime;

	private String dealType;
	private String fixStatus;
	private String fixDesc;
	private String dealMan;
	private Date dealTime;
	private String hqIssueType;
	private String hqDealRst;
	private String hqDealRemark;
	private String hqDealDate;
	private String provIssueType;
	private String provDealRst;
	private String provDealRemark;
	private String provDealDate;
	
	private String hqDealMan;
	private String provDealMan;
	private String provActivity;
	
	private String feeMatch;
	
	private String giveFee;
	private String giveFlag;
	private String giveRst;
	private String serviceLevel;
	
	
	@Transient
	private Integer lastDateNum;
	
	
	@Transient
	private String search_EQ_feeStatus;
	@Transient
	private String staffFlag;
	/*
	@Transient
	private String search_EQ_hqDealRemark;
	@Transient
	private String search_EQ_dealType;
	@Transient
	private String search_EQ_feeFailReason;
	@Transient
	private String search_EQ_hqIssueType;
	*/
	
	@Transient
	public Integer getLastDateNum() {
		if(this.feeDate != null) {
//			Calendar c1 = Calendar.getInstance();
//			c1.setTime(this.feeDate);
//			Calendar now = Calendar.getInstance();
			int check = StringUtil.getBetweenDay(this.feeDate, new Date());
			int c = 60-check;
			if(c<0) {
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
	// Constructors

	@Transient
	public String getSearch_EQ_feeStatus() {
		return search_EQ_feeStatus;
	}
	@Transient
	public void setSearch_EQ_feeStatus(String search_EQ_feeStatus) {
		this.search_EQ_feeStatus = search_EQ_feeStatus;
	}
	
	@Transient
	public String getStaffFlag() {
		return staffFlag;
	}

	@Transient
	public void setStaffFlag(String staffFlag) {
		this.staffFlag = staffFlag;
	}

	/*
	@Transient
	public String getSearch_EQ_hqDealRemark() {
		return search_EQ_hqDealRemark;
	}
	@Transient
	public void setSearch_EQ_hqDealRemark(String search_EQ_hqDealRemark) {
		this.search_EQ_hqDealRemark = search_EQ_hqDealRemark;
	}
	@Transient
	public String getSearch_EQ_dealType() {
		return search_EQ_dealType;
	}
	@Transient
	public void setSearch_EQ_dealType(String search_EQ_dealType) {
		this.search_EQ_dealType = search_EQ_dealType;
	}

	@Transient
	public String getSearch_EQ_feeFailReason() {
		return search_EQ_feeFailReason;
	}
	
	@Transient
	public void setSearch_EQ_feeFailReason(String search_EQ_feeFailReason) {
		this.search_EQ_feeFailReason = search_EQ_feeFailReason;
	}

	@Transient
	public String getSearch_EQ_hqIssueType() {
		return search_EQ_hqIssueType;
	}

	@Transient
	public void setSearch_EQ_hqIssueType(String search_EQ_hqIssueType) {
		this.search_EQ_hqIssueType = search_EQ_hqIssueType;
	}
	*/
	/** default constructor */
	public RenewedList() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "holder", length = 256)
	@ColumnTransformer(
			forColumn="holder",
			read="cast(aes_decrypt(unhex(holder), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	@Column(name="policy_year")
	public Integer getPolicyYear() {
		return policyYear;
	}

	public void setPolicyYear(Integer policyYear) {
		this.policyYear = policyYear;
	}

	@Column(name="prd_name")
	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	@Column(name="fee_date")
	public Date getFeeDate() {
		return feeDate;
	}

	public void setFeeDate(Date feeDate) {
		this.feeDate = feeDate;
	}

	@ManyToOne(cascade = CascadeType.DETACH, targetEntity = Policy.class)
	@JoinColumnsOrFormulas(value={
	@JoinColumnOrFormula(column=@JoinColumn(name ="policy_no", referencedColumnName ="policy_no", insertable =false, updatable = false)),
	@JoinColumnOrFormula(column=@JoinColumn(name ="prd_name", referencedColumnName ="prod_name", insertable =false, updatable = false))
	})
	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Column(name = "policy_fee", length = 256)
	@ColumnTransformer(
			forColumn="policy_fee",
			read="cast(aes_decrypt(unhex(policy_fee), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getPolicyFee() {
		return policyFee;
	}

	public void setPolicyFee(String policyFee) {
		this.policyFee = policyFee;
	}

	@Column(name="fee_status")
	public String getFeeStatus() {
		return feeStatus;
	}

	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}

	@Column(name="fee_fail_reason")
	public String getFeeFailReason() {
		return feeFailReason;
	}

	public void setFeeFailReason(String feeFailReason) {
		this.feeFailReason = feeFailReason;
	}

	@Column(name="account_bank")
	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	@Column(name = "account", length = 256)
	@ColumnTransformer(
			forColumn="account",
			read="cast(aes_decrypt(unhex(account), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "addr", length = 256)
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

	@Column(name="post_code")
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Column(name = "phone", length = 256)
	@ColumnTransformer(
			forColumn="phone",
			read="cast(aes_decrypt(unhex(phone), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "mobile", length = 256)
	@ColumnTransformer(
			forColumn="mobile",
			read="cast(aes_decrypt(unhex(mobile), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name="org_name")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name="net_name")
	public String getNetName() {
		return netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	@Column(name="policy_admin")
	public String getPolicyAdmin() {
		return policyAdmin;
	}

	public void setPolicyAdmin(String policyAdmin) {
		this.policyAdmin = policyAdmin;
	}

	@Column(name="channel")
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	@Column(name="hq_issue_type")
	public String getHqIssueType() {
		return hqIssueType;
	}

	public void setHqIssueType(String hqIssueType) {
		this.hqIssueType = hqIssueType;
	}

	@Column(name="hq_deal_rst")
	public String getHqDealRst() {
		return hqDealRst;
	}

	public void setHqDealRst(String hqDealRst) {
		this.hqDealRst = hqDealRst;
	}

	@Column(name="hq_deal_remark")
	public String getHqDealRemark() {
		return hqDealRemark;
	}

	public void setHqDealRemark(String hqDealRemark) {
		this.hqDealRemark = hqDealRemark;
	}

	@Column(name="hq_deal_date")
	public String getHqDealDate() {
		return hqDealDate;
	}

	public void setHqDealDate(String hqDealDate) {
		this.hqDealDate = hqDealDate;
	}

	@Column(name="prov_issue_type")
	public String getProvIssueType() {
		return provIssueType;
	}

	public void setProvIssueType(String provIssueType) {
		this.provIssueType = provIssueType;
	}

	@Column(name="prov_deal_rst")
	public String getProvDealRst() {
		return provDealRst;
	}

	public void setProvDealRst(String provDealRst) {
		this.provDealRst = provDealRst;
	}

	@Column(name="prov_deal_remark")
	public String getProvDealRemark() {
		return provDealRemark;
	}

	public void setProvDealRemark(String provDealRemark) {
		this.provDealRemark = provDealRemark;
	}

	@Column(name="prov_deal_date")
	public String getProvDealDate() {
		return provDealDate;
	}

	public void setProvDealDate(String provDealDate) {
		this.provDealDate = provDealDate;
	}

	@Column(name="hq_deal_man")
	public String getHqDealMan() {
		return hqDealMan;
	}

	public void setHqDealMan(String hqDealMan) {
		this.hqDealMan = hqDealMan;
	}

	@Column(name="prov_deal_man")
	public String getProvDealMan() {
		return provDealMan;
	}

	public void setProvDealMan(String provDealMan) {
		this.provDealMan = provDealMan;
	}

	@Column(name="deal_type")
	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	@Column(name="prov_activity")
	public String getProvActivity() {
		return provActivity;
	}

	public void setProvActivity(String provActivity) {
		this.provActivity = provActivity;
	}

	@Column(name="fee_match")
	public String getFeeMatch() {
		return feeMatch;
	}

	public void setFeeMatch(String feeMatch) {
		this.feeMatch = feeMatch;
	}

	@Column(name="give_fee")
	public String getGiveFee() {
		return giveFee;
	}

	public void setGiveFee(String giveFee) {
		this.giveFee = giveFee;
	}

	@Column(name="give_flag")
	public String getGiveFlag() {
		return giveFlag;
	}

	public void setGiveFlag(String giveFlag) {
		this.giveFlag = giveFlag;
	}

	@Column(name="give_rst")
	public String getGiveRst() {
		return giveRst;
	}

	public void setGiveRst(String giveRst) {
		this.giveRst = giveRst;
	}

	@Column(name="service_level")
	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	@Override
	public String toString() {
		return "RenewedList [id=" + id + ", holder=" + holder + ", policyYear=" + policyYear + ", prdName=" + prdName + ", feeDate=" + feeDate + ", policy="
				+ policy + ", policyFee=" + policyFee + ", feeStatus=" + feeStatus + ", feeFailReason=" + feeFailReason + ", accountBank=" + accountBank
				+ ", account=" + account + ", addr=" + addr + ", postCode=" + postCode + ", phone=" + phone + ", mobile=" + mobile + ", orgName=" + orgName
				+ ", netName=" + netName + ", policyAdmin=" + policyAdmin + ", channel=" + channel + ", operateId=" + operateId + ", operateTime="
				+ operateTime + ", fixStatus=" + fixStatus + ", fixDesc=" + fixDesc + ", dealMan=" + dealMan + ", dealTime=" + dealTime + "]";
	}
	
}