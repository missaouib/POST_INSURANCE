package com.gdpost.web.entity.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gdpost.web.entity.Idable;

/**
 * TCsReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cs_report")
public class CsReport implements Idable<Long> {

	// Fields

	private Long id;
	private String csNo;
	private String policyNo;
	private String organName;
	private String netName;
	private String csChannel;
	private String operateOrg;
	private String holder;
	private String insured;
	private Date csDate;
	private String csCode;
	private Double money;
	private String csDeal;
	private Long operateId;
	private Date operateTime;

	// Constructors

	/** default constructor */
	public CsReport() {
	}

	/** full constructor */
	public CsReport(String csNo, String policyNo, String organName, String netName, String csChannel,
			String operateOrg, String holder, String insured, Date csDate, String csCode, Double money, String csDeal,
			Long operateId, Date operateTime) {
		this.csNo = csNo;
		this.policyNo = policyNo;
		this.organName = organName;
		this.netName = netName;
		this.csChannel = csChannel;
		this.operateOrg = operateOrg;
		this.holder = holder;
		this.insured = insured;
		this.csDate = csDate;
		this.csCode = csCode;
		this.money = money;
		this.csDeal = csDeal;
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

	@Column(name = "cs_no", length = 17)

	public String getCsNo() {
		return this.csNo;
	}

	public void setCsNo(String csNo) {
		this.csNo = csNo;
	}

	@Column(name = "policy_no", length = 15)

	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name = "organ_name", length = 64)

	public String getOrganName() {
		return this.organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	@Column(name = "net_name", length = 128)

	public String getNetName() {
		return this.netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	@Column(name = "cs_channel", length = 12)

	public String getCsChannel() {
		return this.csChannel;
	}

	public void setCsChannel(String csChannel) {
		this.csChannel = csChannel;
	}

	@Column(name = "operate_org", length = 6)

	public String getOperateOrg() {
		return this.operateOrg;
	}

	public void setOperateOrg(String operateOrg) {
		this.operateOrg = operateOrg;
	}

	@Column(name = "holder", length = 16)

	public String getHolder() {
		return this.holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	@Column(name = "insured", length = 16)

	public String getInsured() {
		return this.insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "cs_date", length = 10)

	public Date getCsDate() {
		return this.csDate;
	}

	public void setCsDate(Date csDate) {
		this.csDate = csDate;
	}

	@Column(name = "cs_code", length = 4)

	public String getCsCode() {
		return this.csCode;
	}

	public void setCsCode(String csCode) {
		this.csCode = csCode;
	}

	@Column(name = "money", precision = 22, scale = 0)

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "cs_deal", length = 12)

	public String getCsDeal() {
		return this.csDeal;
	}

	public void setCsDeal(String csDeal) {
		this.csDeal = csDeal;
	}

	@Column(name = "operate_id")

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "operate_time", length = 10)

	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

}