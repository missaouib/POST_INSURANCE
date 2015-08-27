package com.gdpost.web.entity.main;

import java.sql.Timestamp;
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
import javax.persistence.UniqueConstraint;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.basedata.Prd;

/**
 * TUnderWrite entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_under_write", uniqueConstraints = @UniqueConstraint(columnNames = "policy_no"))
public class UnderWrite implements Idable<Long> {

	// Fields

	private Long id;
	private Organization organization;
	private Prd prd;
	private String policyNo;
	private String formNo;
	private String holder;
	private String insured;
	private String underwriteReason;
	private Date ybtDate;
	private Timestamp sysDate;
	private Timestamp checkDate;
	private Short issueFlag;
	private String errorDesc;
	private String dealMan;
	private Timestamp underwriteDate;
	private Boolean isLetter;
	private Timestamp signDate;
	private Date provReceiveDate;
	private Date provSendDate;
	private String provEmsNo;
	private Date cityReceiveDate;
	private Date citySendDate;
	private String cityEmsNo;
	private Date areaReceiveDate;
	private Date areaSendDate;
	private String areaEmsNo;
	private Date netReceiveDate;
	private Date clientReceiveDate;
	private Date signInputDate;
	private String remark;
	
	private String relation;
	private Double policyFee;

	// Constructors

	@Column(name="relation")
	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	@Column(name="policy_fee")
	public Double getPolicyFee() {
		return policyFee;
	}

	public void setPolicyFee(Double policyFee) {
		this.policyFee = policyFee;
	}

	/** default constructor */
	public UnderWrite() {
	}

	/** full constructor */
	public UnderWrite(Organization organization, Prd prd, String policyNo, String formNo, String holder, String insured, String underwriteReason,
			Date ybtDate, Timestamp sysDate, Timestamp checkDate, Short issueFlag, String errorDesc, String dealMan, Timestamp underwriteDate,
			Boolean isLetter, Timestamp signDate, Date provReceiveDate, Date provSendDate, String provEmsNo, Date cityReceiveDate, Date citySendDate,
			String cityEmsNo, Date areaReceiveDate, Date areaSendDate, String areaEmsNo, Date netReceiveDate, Date clientReceiveDate, Date signInputDate,
			String remark) {
		this.organization = organization;
		this.prd = prd;
		this.policyNo = policyNo;
		this.formNo = formNo;
		this.holder = holder;
		this.insured = insured;
		this.underwriteReason = underwriteReason;
		this.ybtDate = ybtDate;
		this.sysDate = sysDate;
		this.checkDate = checkDate;
		this.issueFlag = issueFlag;
		this.errorDesc = errorDesc;
		this.dealMan = dealMan;
		this.underwriteDate = underwriteDate;
		this.isLetter = isLetter;
		this.signDate = signDate;
		this.provReceiveDate = provReceiveDate;
		this.provSendDate = provSendDate;
		this.provEmsNo = provEmsNo;
		this.cityReceiveDate = cityReceiveDate;
		this.citySendDate = citySendDate;
		this.cityEmsNo = cityEmsNo;
		this.areaReceiveDate = areaReceiveDate;
		this.areaSendDate = areaSendDate;
		this.areaEmsNo = areaEmsNo;
		this.netReceiveDate = netReceiveDate;
		this.clientReceiveDate = clientReceiveDate;
		this.signInputDate = signInputDate;
		this.remark = remark;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organ_id")
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	public Prd getPrd() {
		return this.prd;
	}

	public void setPrd(Prd prd) {
		this.prd = prd;
	}

	@Column(name = "policy_no", unique = true, length = 18)
	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name = "form_no", length = 25)
	public String getFormNo() {
		return this.formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	@Column(name = "holder", length = 32)
	public String getHolder() {
		return this.holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	@Column(name = "insured", length = 32)
	public String getInsured() {
		return this.insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	@Column(name = "underwrite_reason", length = 64)
	public String getUnderwriteReason() {
		return this.underwriteReason;
	}

	public void setUnderwriteReason(String underwriteReason) {
		this.underwriteReason = underwriteReason;
	}

	@Column(name = "ybt_date", length = 10)
	public Date getYbtDate() {
		return this.ybtDate;
	}

	public void setYbtDate(Date ybtDate) {
		this.ybtDate = ybtDate;
	}

	@Column(name = "sys_date", length = 19)
	public Timestamp getSysDate() {
		return this.sysDate;
	}

	public void setSysDate(Timestamp sysDate) {
		this.sysDate = sysDate;
	}

	@Column(name = "check_date", length = 19)
	public Timestamp getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Timestamp checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name = "issue_flag")
	public Short getIssueFlag() {
		return this.issueFlag;
	}

	public void setIssueFlag(Short issueFlag) {
		this.issueFlag = issueFlag;
	}

	@Column(name = "error_desc", length = 256)
	public String getErrorDesc() {
		return this.errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	@Column(name = "deal_man", length = 12)
	public String getDealMan() {
		return this.dealMan;
	}

	public void setDealMan(String dealMan) {
		this.dealMan = dealMan;
	}

	@Column(name = "underwrite_date", length = 19)
	public Timestamp getUnderwriteDate() {
		return this.underwriteDate;
	}

	public void setUnderwriteDate(Timestamp underwriteDate) {
		this.underwriteDate = underwriteDate;
	}

	@Column(name = "is_letter")
	public Boolean getIsLetter() {
		return this.isLetter;
	}

	public void setIsLetter(Boolean isLetter) {
		this.isLetter = isLetter;
	}

	@Column(name = "sign_date", length = 19)
	public Timestamp getSignDate() {
		return this.signDate;
	}

	public void setSignDate(Timestamp signDate) {
		this.signDate = signDate;
	}

	@Column(name = "prov_receive_date", length = 10)
	public Date getProvReceiveDate() {
		return this.provReceiveDate;
	}

	public void setProvReceiveDate(Date provReceiveDate) {
		this.provReceiveDate = provReceiveDate;
	}

	@Column(name = "prov_send_date", length = 10)
	public Date getProvSendDate() {
		return this.provSendDate;
	}

	public void setProvSendDate(Date provSendDate) {
		this.provSendDate = provSendDate;
	}

	@Column(name = "prov_ems_no", length = 32)
	public String getProvEmsNo() {
		return this.provEmsNo;
	}

	public void setProvEmsNo(String provEmsNo) {
		this.provEmsNo = provEmsNo;
	}

	@Column(name = "city_receive_date", length = 10)
	public Date getCityReceiveDate() {
		return this.cityReceiveDate;
	}

	public void setCityReceiveDate(Date cityReceiveDate) {
		this.cityReceiveDate = cityReceiveDate;
	}

	@Column(name = "city_send_date", length = 10)
	public Date getCitySendDate() {
		return this.citySendDate;
	}

	public void setCitySendDate(Date citySendDate) {
		this.citySendDate = citySendDate;
	}

	@Column(name = "city_ems_no", length = 32)
	public String getCityEmsNo() {
		return this.cityEmsNo;
	}

	public void setCityEmsNo(String cityEmsNo) {
		this.cityEmsNo = cityEmsNo;
	}

	@Column(name = "area_receive_date", length = 10)
	public Date getAreaReceiveDate() {
		return this.areaReceiveDate;
	}

	public void setAreaReceiveDate(Date areaReceiveDate) {
		this.areaReceiveDate = areaReceiveDate;
	}

	@Column(name = "area_send_date", length = 10)
	public Date getAreaSendDate() {
		return this.areaSendDate;
	}

	public void setAreaSendDate(Date areaSendDate) {
		this.areaSendDate = areaSendDate;
	}

	@Column(name = "area_ems_no", length = 32)
	public String getAreaEmsNo() {
		return this.areaEmsNo;
	}

	public void setAreaEmsNo(String areaEmsNo) {
		this.areaEmsNo = areaEmsNo;
	}

	@Column(name = "net_receive_date", length = 10)
	public Date getNetReceiveDate() {
		return this.netReceiveDate;
	}

	public void setNetReceiveDate(Date netReceiveDate) {
		this.netReceiveDate = netReceiveDate;
	}

	@Column(name = "client_receive_date", length = 10)
	public Date getClientReceiveDate() {
		return this.clientReceiveDate;
	}

	public void setClientReceiveDate(Date clientReceiveDate) {
		this.clientReceiveDate = clientReceiveDate;
	}

	@Column(name = "sign_input_date", length = 10)
	public Date getSignInputDate() {
		return this.signInputDate;
	}

	public void setSignInputDate(Date signInputDate) {
		this.signInputDate = signInputDate;
	}

	@Column(name = "remark", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}