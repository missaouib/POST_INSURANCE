package com.gdpost.web.entity.main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnTransformer;

import com.gdpost.web.entity.Idable;

/**
 * TCheckRecordDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_renewed_list")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.RenewedList")
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

	private String fixStatus;
	private String fixDesc;
	private String dealMan;
	private Date dealTime;
	
	// Constructors

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

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="policy_no", referencedColumnName="policy_no")
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

	@Override
	public String toString() {
		return "RenewedList [id=" + id + ", holder=" + holder + ", policyYear=" + policyYear + ", prdName=" + prdName + ", feeDate=" + feeDate + ", policy="
				+ policy + ", policyFee=" + policyFee + ", feeStatus=" + feeStatus + ", feeFailReason=" + feeFailReason + ", accountBank=" + accountBank
				+ ", account=" + account + ", addr=" + addr + ", postCode=" + postCode + ", phone=" + phone + ", mobile=" + mobile + ", orgName=" + orgName
				+ ", netName=" + netName + ", policyAdmin=" + policyAdmin + ", channel=" + channel + ", operateId=" + operateId + ", operateTime="
				+ operateTime + ", fixStatus=" + fixStatus + ", fixDesc=" + fixDesc + ", dealMan=" + dealMan + ", dealTime=" + dealTime + "]";
	}
	
}