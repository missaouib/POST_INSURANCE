package com.gdpost.web.entity.main;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gdpost.web.entity.Idable;

/**
 * TPaySuccessList entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_pay_success_list")
public class PaySuccessList implements Idable<Long> {

	public static final Integer PAY_TO = 1;
	public static final Integer PAY_FROM = 2;
	// Fields

	private Long id;
	private String accountName;
	private String account;
	private String money;
	private String failDesc;
	private Date backDate;
	private String feeType;
	private String relNo;
	private String orgName;
	private Integer payType;
	private Integer status;
	private Long operateId;
	private Date operateTime;

	// Constructors

	/** default constructor */
	public PaySuccessList() {
	}

	/** full constructor */
	public PaySuccessList(String accountName, String account, String money,
			String failDesc, Date backDate, String feeType, String relNo,
			String orgName, Integer payType, Integer status, Long operateId,
			Date operateTime) {
		this.accountName = accountName;
		this.account = account;
		this.money = money;
		this.failDesc = failDesc;
		this.backDate = backDate;
		this.feeType = feeType;
		this.relNo = relNo;
		this.orgName = orgName;
		this.payType = payType;
		this.status = status;
		this.operateId = operateId;
		this.operateTime = operateTime;
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

	@Column(name = "account_name", length = 32)
	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name = "account", length = 256)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "money", length = 12)
	public String getMoney() {
		return this.money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	@Column(name = "fail_desc", length = 32)
	public String getFailDesc() {
		return this.failDesc;
	}

	public void setFailDesc(String failDesc) {
		this.failDesc = failDesc;
	}

	@Column(name = "back_date", length = 10)
	public Date getBackDate() {
		return this.backDate;
	}

	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}

	@Column(name = "fee_type", length = 16)
	public String getFeeType() {
		return this.feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	@Column(name = "rel_no", length = 32)
	public String getRelNo() {
		return this.relNo;
	}

	public void setRelNo(String relNo) {
		this.relNo = relNo;
	}

	@Column(name = "org_name", length = 64)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "pay_type")
	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "operate_id")
	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Column(name = "operate_time", length = 19)
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

}