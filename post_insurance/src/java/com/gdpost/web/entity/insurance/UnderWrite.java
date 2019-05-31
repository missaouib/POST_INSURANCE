package com.gdpost.web.entity.insurance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.gdpost.utils.StringUtil;
import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.basedata.Prd;
import com.gdpost.web.entity.main.Organization;

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
	private Date sysDate;
	private Date checkDate;
	private Short issueFlag;
	private String errorDesc;
	private String dealMan;
	private Date underwriteDate;
	private Boolean isLetter;
	private Date signDate;
	private Date provReceiveDate;
	private Date provSendDate;
	private Boolean toNet;
	private String provEmsNo;
	private Date cityReceiveDate;
	private Date citySendDate;
	private String cityEmsNo;
	private Date areaReceiveDate;
	private Date areaSendDate;
	private String areaEmsNo;
	private Date netReceiveDate;
	private Date clientReceiveDate;
	private Date billBackDate;
	private String remark;
	
	private String relation;
	private Double policyFee;
	private Integer hasRecord;
	private String recordDesc;
	private String status;
	private Date planDate;
	private Boolean planFlag;
	
	private String prdName;
	private Integer perm;
	private Integer insuredAage;
	private Integer issue;
	private Date bodyCheckDate1;
	private Date bodyCheckDate2;
	private Date hbEndDate;
	private Date formWriteDate;
	
	private String netName;
	private PolicyDtl policyDtl;
	
	private String feeType;
	
	private PayFailList payFailList;
	@Transient
	private boolean payFail;
	@Transient
	public boolean getPayFail() {
		if(this.getPayFailList() != null) {
			return true;
		}
		return false;
	}
	@Transient
	public void setPayFail(boolean payFail) {
		this.payFail = payFail;
	}

	@Transient
	private Date receiveDate;
	@Transient
	private Date sendDate;
	@Transient
	private String emsNo;
	@Transient
	private int longDate;
	@Transient
	private int hadSendDate;
	
	private String holderAge;
	
	// Constructors
	@Transient
	public Date getReceiveDate() {
		return receiveDate;
	}
	@Transient
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	@Transient
	public Date getSendDate() {
		return sendDate;
	}
	@Transient
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	@Transient
	public String getEmsNo() {
		return emsNo;
	}
	@Transient
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	
	@Transient
	public int getLongDate() {
		if(this.ybtDate != null) {
			return StringUtil.getBetweenDay(this.ybtDate, new Date());
		}
		return -1;
	}
	
	@Transient
	public void setLongDate(int longDate) {
		this.longDate = longDate;
	}
	
	
	@Transient
	public int getHadSendDate() {
		if(this.provSendDate != null) {
			return StringUtil.getBetweenDay(this.provSendDate, this.billBackDate==null?new Date():this.billBackDate);
		}
		return -1;
	}
	
	@Transient
	public void setHadSendDate(int hadSendDate) {
		this.hadSendDate = hadSendDate;
	}
	
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
			Date ybtDate, Date sysDate, Date checkDate, Short issueFlag, String errorDesc, String dealMan, Date underwriteDate,
			Boolean isLetter, Date signDate, Date provReceiveDate, Date provSendDate, String provEmsNo, Date cityReceiveDate, Date citySendDate,
			String cityEmsNo, Date areaReceiveDate, Date areaSendDate, String areaEmsNo, Date netReceiveDate, Date clientReceiveDate, Date billBackDate,
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
		this.billBackDate = billBackDate;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "organ_id")
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToOne(fetch = FetchType.EAGER)
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
	public Date getSysDate() {
		return this.sysDate;
	}

	public void setSysDate(Date sysDate) {
		this.sysDate = sysDate;
	}

	@Column(name = "check_date", length = 19)
	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
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
	public Date getUnderwriteDate() {
		return this.underwriteDate;
	}

	public void setUnderwriteDate(Date underwriteDate) {
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
	public Date getSignDate() {
		return this.signDate;
	}

	public void setSignDate(Date signDate) {
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
	
	@Column(name = "to_net")
	public Boolean getToNet() {
		return toNet;
	}
	public void setToNet(Boolean toNet) {
		this.toNet = toNet;
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

	@Column(name = "bill_back_date", length = 10)
	public Date getBillBackDate() {
		return this.billBackDate;
	}

	public void setBillBackDate(Date billBackDate) {
		this.billBackDate = billBackDate;
	}

	@Column(name = "remark", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="has_record")
	public Integer getHasRecord() {
		return hasRecord;
	}
	
	public void setHasRecord(Integer hasRecord) {
		this.hasRecord = hasRecord;
	}
	
	@Column(name="record_desc")
	public String getRecordDesc() {
		return recordDesc;
	}
	public void setRecordDesc(String recordDesc) {
		this.recordDesc = recordDesc;
	}
	
	@Column(name="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="plan_date")
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	
	@Column(name="plan_flag")
	public Boolean getPlanFlag() {
		return planFlag;
	}
	public void setPlanFlag(Boolean planFlag) {
		this.planFlag = planFlag;
	}
	@Column(name="holder_age")
	public String getHolderAge() {
		return holderAge;
	}
	public void setHolderAge(String holderAge) {
		this.holderAge = holderAge;
	}
	@Column(name="prd_name")
	public String getPrdName() {
		return prdName;
	}
	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}
	@Column(name="perm")
	public Integer getPerm() {
		return perm;
	}
	public void setPerm(Integer perm) {
		this.perm = perm;
	}
	@Column(name="insured_age")
	public Integer getInsuredAage() {
		return insuredAage;
	}
	public void setInsuredAage(Integer insuredAage) {
		this.insuredAage = insuredAage;
	}
	@Column(name="issue")
	public Integer getIssue() {
		return issue;
	}
	public void setIssue(Integer issue) {
		this.issue = issue;
	}
	@Column(name="body_check_date1")
	public Date getBodyCheckDate1() {
		return bodyCheckDate1;
	}
	public void setBodyCheckDate1(Date bodyCheckDate1) {
		this.bodyCheckDate1 = bodyCheckDate1;
	}
	@Column(name="body_check_date2")
	public Date getBodyCheckDate2() {
		return bodyCheckDate2;
	}
	public void setBodyCheckDate2(Date bodyCheckDate2) {
		this.bodyCheckDate2 = bodyCheckDate2;
	}
	@Column(name="hb_end_date")
	public Date getHbEndDate() {
		return hbEndDate;
	}
	public void setHbEndDate(Date hbEndDate) {
		this.hbEndDate = hbEndDate;
	}
	@Column(name="form_write_date")
	public Date getFormWriteDate() {
		return formWriteDate;
	}
	public void setFormWriteDate(Date formWriteDate) {
		this.formWriteDate = formWriteDate;
	}
	
	@Column(name="net_name")
	public String getNetName() {
		return netName;
	}
	public void setNetName(String netName) {
		this.netName = netName;
	}
	
	@OneToOne(optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name="policy_no", referencedColumnName="policy_no", insertable=false, updatable=false, nullable=true)
	public PolicyDtl getPolicyDtl() {
		return policyDtl;
	}
	public void setPolicyDtl(PolicyDtl policyDtl) {
		this.policyDtl = policyDtl;
	}
	
	@OneToOne(optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name="form_no", referencedColumnName="rel_no", insertable=false, updatable=false, nullable=true)
	public PayFailList getPayFailList() {
		return payFailList;
	}
	
	public void setPayFailList(PayFailList payFailList) {
		this.payFailList = payFailList;
	}
	
	@Column(name="fee_type")
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	
}