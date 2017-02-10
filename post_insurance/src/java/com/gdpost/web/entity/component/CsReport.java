package com.gdpost.web.entity.component;

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

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.Policy;

/**
 * TCsReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cs_report")
public class CsReport implements Idable<Long> {

	// Fields

	private Long id;
	private String csNo;
	private Policy policy;
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
	
	private String staffFlag;
	
	@Transient
	private String search_LIKE_staffFlag;

	@Transient
	public String getSearch_LIKE_staffFlag() {
		return search_LIKE_staffFlag;
	}
	@Transient
	public void setSearch_LIKE_staffFlag(String search_LIKE_staffFlag) {
		this.search_LIKE_staffFlag = search_LIKE_staffFlag;
	}

	/** default constructor */
	public CsReport() {
	}

	/** full constructor */
	public CsReport(String csNo, Policy policy, String organName, String netName, String csChannel,
			String operateOrg, String holder, String insured, Date csDate, String csCode, Double money, String csDeal,
			Long operateId, Date operateTime) {
		this.csNo = csNo;
		this.policy = policy;
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

	/*
	@Column(name = "policy_no", length = 15)

	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	 */
	@ManyToOne
	@JoinColumnsOrFormulas(value={
	@JoinColumnOrFormula(column=@JoinColumn(name ="policy_no", referencedColumnName ="policy_no")),
	@JoinColumnOrFormula(formula=@JoinFormula(value="0", referencedColumnName = "attached_flag"))
	})
	public Policy getPolicy() {
		return this.policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
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

	@Temporal(TemporalType.TIMESTAMP)
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
	
	@Column(name="staff_flag")
	public String getStaffFlag() {
		return staffFlag;
	}

	public void setStaffFlag(String staffFlag) {
		this.staffFlag = staffFlag;
	}

}