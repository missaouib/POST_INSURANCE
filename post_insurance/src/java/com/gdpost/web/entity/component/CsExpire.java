package com.gdpost.web.entity.component;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.Policy;

/**
 * TCsExpire entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cs_expire")

public class CsExpire implements Idable<Long> {

	// Fields

	private Long id;
	private Policy policy;
	private String saleChannel;
	private Integer duration;
	private Date policyEndDate;
	private Date insuredBirthday;
	private Date holderBirthday;
	private String holderCardNum;
	private String holderCardType;
	private Integer holderYear;
	private Integer holderExpireYear;
	private String insuredCardNum;
	private String insuredCardType;
	private Integer insuredYear;
	private Integer insuredExpireYear;
	private Double policyMoney;
	private String holderMobile;
	private String holderPhone;
	private Double expireMoney;
	private Double expireProfit;
	private Float expireRate;
	private Float yearRate;
	private String relation;
	private String issueFlag;
	private String subFlag;
	private String balMatch;
	private String payLevel;
	private String holderAgeLevel;
	private String adultLevel;
	private String ageDiffLevel;
	private String issueLevel;
	private String staffLevel;
	private String accountLevel;
	private String finalLevel;
	private String status;
	private Date csDate;
	private Long operateId;
	private Date operateTime;

	private List<CsExpireDtl> csExpireDtls = new ArrayList<CsExpireDtl>(0);
	
	// Constructors

	/** default constructor */
	public CsExpire() {
	}

	/** full constructor */
	public CsExpire(Policy policy, String saleChannel, Integer duration, Date policyEndDate, Date insuredBirthday,
			Date holderBirthday, String holderCardNum, String holderCardType, String insuredCardNum,
			String insuredCardType, Double policyMoney, String holderMobile, String holderPhone, Double expireMoney,
			Double expireProfit, Float expireRate, Float yearRate, String relation, String issueFlag, String subFlag,
			String balMatch, String payLevel, String holderAgeLevel, String adultLevel, String ageDiffLevel,
			String issueLevel, String staffLevel, String accountLevel, String finalLevel, Long operateId,
			Date operateTime) {
		this.policy = policy;
		this.saleChannel = saleChannel;
		this.duration = duration;
		this.policyEndDate = policyEndDate;
		this.insuredBirthday = insuredBirthday;
		this.holderBirthday = holderBirthday;
		this.holderCardNum = holderCardNum;
		this.holderCardType = holderCardType;
		this.insuredCardNum = insuredCardNum;
		this.insuredCardType = insuredCardType;
		this.policyMoney = policyMoney;
		this.holderMobile = holderMobile;
		this.holderPhone = holderPhone;
		this.expireMoney = expireMoney;
		this.expireProfit = expireProfit;
		this.expireRate = expireRate;
		this.yearRate = yearRate;
		this.relation = relation;
		this.issueFlag = issueFlag;
		this.subFlag = subFlag;
		this.balMatch = balMatch;
		this.payLevel = payLevel;
		this.holderAgeLevel = holderAgeLevel;
		this.adultLevel = adultLevel;
		this.ageDiffLevel = ageDiffLevel;
		this.issueLevel = issueLevel;
		this.staffLevel = staffLevel;
		this.accountLevel = accountLevel;
		this.finalLevel = finalLevel;
		this.operateId = operateId;
		this.operateTime = operateTime;
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
	@JoinColumnsOrFormulas(value = {
			@JoinColumnOrFormula(column = @JoinColumn(name = "policy_no", referencedColumnName = "policy_no")),
			@JoinColumnOrFormula(formula = @JoinFormula(value = "0", referencedColumnName = "attached_flag")) })
	public Policy getPolicy() {
		return this.policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Column(name = "sale_channel", length = 6)

	public String getSaleChannel() {
		return this.saleChannel;
	}

	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}

	@Column(name = "duration")

	public Integer getDuration() {
		return this.duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "policy_end_date", length = 10)

	public Date getPolicyEndDate() {
		return this.policyEndDate;
	}

	public void setPolicyEndDate(Date policyEndDate) {
		this.policyEndDate = policyEndDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "insured_birthday", length = 10)

	public Date getInsuredBirthday() {
		return this.insuredBirthday;
	}

	public void setInsuredBirthday(Date insuredBirthday) {
		this.insuredBirthday = insuredBirthday;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "holder_birthday", length = 10)

	public Date getHolderBirthday() {
		return this.holderBirthday;
	}

	public void setHolderBirthday(Date holderBirthday) {
		this.holderBirthday = holderBirthday;
	}

	@Column(name = "holder_card_num", length = 18)

	public String getHolderCardNum() {
		return this.holderCardNum;
	}

	public void setHolderCardNum(String holderCardNum) {
		this.holderCardNum = holderCardNum;
	}

	@Column(name = "holder_card_type", length = 15)
	public String getHolderCardType() {
		return this.holderCardType;
	}

	public void setHolderCardType(String holderCardType) {
		this.holderCardType = holderCardType;
	}
	
	@Column(name = "holder_year", length = 3)
	public Integer getHolderYear() {
		return holderYear;
	}

	public void setHolderYear(Integer holderYear) {
		this.holderYear = holderYear;
	}

	@Column(name = "holder_expire_year", length = 3)
	public Integer getHolderExpireYear() {
		return holderExpireYear;
	}

	public void setHolderExpireYear(Integer holderExpireYear) {
		this.holderExpireYear = holderExpireYear;
	}

	@Column(name = "insured_card_num", length = 18)
	public String getInsuredCardNum() {
		return this.insuredCardNum;
	}

	public void setInsuredCardNum(String insuredCardNum) {
		this.insuredCardNum = insuredCardNum;
	}

	@Column(name = "insured_card_type", length = 15)
	public String getInsuredCardType() {
		return this.insuredCardType;
	}

	public void setInsuredCardType(String insuredCardType) {
		this.insuredCardType = insuredCardType;
	}
	
	@Column(name = "insured_year", length = 3)
	public Integer getInsuredYear() {
		return insuredYear;
	}

	public void setInsuredYear(Integer insuredYear) {
		this.insuredYear = insuredYear;
	}

	@Column(name = "insured_expire_year", length = 3)
	public Integer getInsuredExpireYear() {
		return insuredExpireYear;
	}

	public void setInsuredExpireYear(Integer insuredExpireYear) {
		this.insuredExpireYear = insuredExpireYear;
	}

	@Column(name = "policy_money", precision = 22, scale = 0)
	public Double getPolicyMoney() {
		return this.policyMoney;
	}

	public void setPolicyMoney(Double policyMoney) {
		this.policyMoney = policyMoney;
	}

	@Column(name = "holder_mobile", length = 11)

	public String getHolderMobile() {
		return this.holderMobile;
	}

	public void setHolderMobile(String holderMobile) {
		this.holderMobile = holderMobile;
	}

	@Column(name = "holder_phone", length = 12)

	public String getHolderPhone() {
		return this.holderPhone;
	}

	public void setHolderPhone(String holderPhone) {
		this.holderPhone = holderPhone;
	}

	@Column(name = "expire_money", precision = 22, scale = 0)

	public Double getExpireMoney() {
		return this.expireMoney;
	}

	public void setExpireMoney(Double expireMoney) {
		this.expireMoney = expireMoney;
	}

	@Column(name = "expire_profit", precision = 22, scale = 0)

	public Double getExpireProfit() {
		return this.expireProfit;
	}

	public void setExpireProfit(Double expireProfit) {
		this.expireProfit = expireProfit;
	}

	@Column(name = "expire_rate", precision = 12, scale = 0)

	public Float getExpireRate() {
		return this.expireRate;
	}

	public void setExpireRate(Float expireRate) {
		this.expireRate = expireRate;
	}

	@Column(name = "year_rate", precision = 12, scale = 0)

	public Float getYearRate() {
		return this.yearRate;
	}

	public void setYearRate(Float yearRate) {
		this.yearRate = yearRate;
	}

	@Column(name = "relation", length = 12)

	public String getRelation() {
		return this.relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	@Column(name = "issue_flag", length = 64)

	public String getIssueFlag() {
		return this.issueFlag;
	}

	public void setIssueFlag(String issueFlag) {
		this.issueFlag = issueFlag;
	}

	@Column(name = "sub_flag", length = 12)

	public String getSubFlag() {
		return this.subFlag;
	}

	public void setSubFlag(String subFlag) {
		this.subFlag = subFlag;
	}

	@Column(name = "bal_match", length = 6)

	public String getBalMatch() {
		return this.balMatch;
	}

	public void setBalMatch(String balMatch) {
		this.balMatch = balMatch;
	}

	@Column(name = "pay_level", length = 6)

	public String getPayLevel() {
		return this.payLevel;
	}

	public void setPayLevel(String payLevel) {
		this.payLevel = payLevel;
	}

	@Column(name = "holder_age_level", length = 6)

	public String getHolderAgeLevel() {
		return this.holderAgeLevel;
	}

	public void setHolderAgeLevel(String holderAgeLevel) {
		this.holderAgeLevel = holderAgeLevel;
	}

	@Column(name = "adult_level", length = 6)

	public String getAdultLevel() {
		return this.adultLevel;
	}

	public void setAdultLevel(String adultLevel) {
		this.adultLevel = adultLevel;
	}

	@Column(name = "age_diff_level", length = 6)

	public String getAgeDiffLevel() {
		return this.ageDiffLevel;
	}

	public void setAgeDiffLevel(String ageDiffLevel) {
		this.ageDiffLevel = ageDiffLevel;
	}

	@Column(name = "issue_level", length = 6)

	public String getIssueLevel() {
		return this.issueLevel;
	}

	public void setIssueLevel(String issueLevel) {
		this.issueLevel = issueLevel;
	}

	@Column(name = "staff_level", length = 6)

	public String getStaffLevel() {
		return this.staffLevel;
	}

	public void setStaffLevel(String staffLevel) {
		this.staffLevel = staffLevel;
	}

	@Column(name = "account_level", length = 6)

	public String getAccountLevel() {
		return this.accountLevel;
	}

	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}

	@Column(name = "final_level", length = 6)

	public String getFinalLevel() {
		return this.finalLevel;
	}

	public void setFinalLevel(String finalLevel) {
		this.finalLevel = finalLevel;
	}
	
	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="cs_date")
	public Date getCsDate() {
		return csDate;
	}

	public void setCsDate(Date csDate) {
		this.csDate = csDate;
	}

	@Column(name = "operate_id")
	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "operate_time", length = 19)
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "csExpire", orphanRemoval=true)
	public List<CsExpireDtl> getCsExpireDtls() {
		return this.csExpireDtls;
	}

	public void setCsExpireDtls(List<CsExpireDtl> csExpireDtls) {
		this.csExpireDtls = csExpireDtls;
	}
}