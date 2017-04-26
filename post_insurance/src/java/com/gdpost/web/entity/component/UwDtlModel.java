package com.gdpost.web.entity.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * VCallFailList entity. @author MyEclipse Persistence Tools
 */
@Entity
public class UwDtlModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3288285191150301593L;
	private Long id;
	private String orgCode;
	private String orgName;
	private String policyNo;
	private String formNo;
	private String holder;
	private Double policyFee;
	private String prdName;
	private Date signDate;
	private String netName;
	private String ybtDate;
	private Date provSendDate;
	private String provEmsNo;
	private String longPerm;
	private String planDate;
	private String remark;
	
	// Constructors

	/** default constructor */
	public UwDtlModel() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="org_code")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name="org_name")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name="policy_no")
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name="form_no")
	public String getFormNo() {
		return formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	@Column(name="holder")
	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	@Column(name="policy_fee")
	public Double getPolicyFee() {
		return policyFee;
	}

	public void setPolicyFee(Double policyFee) {
		this.policyFee = policyFee;
	}

	@Column(name="prd_name")
	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	@Column(name="sign_date")
	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	@Column(name="net_name")
	public String getNetName() {
		return netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	@Column(name="ybt_date")
	public String getYbtDate() {
		return ybtDate;
	}

	public void setYbtDate(String ybtDate) {
		this.ybtDate = ybtDate;
	}

	@Column(name="prov_send_date")
	public Date getProvSendDate() {
		return provSendDate;
	}

	public void setProvSendDate(Date provSendDate) {
		this.provSendDate = provSendDate;
	}

	@Column(name="prov_ems_no")
	public String getProvEmsNo() {
		return provEmsNo;
	}

	public void setProvEmsNo(String provEmsNo) {
		this.provEmsNo = provEmsNo;
	}

	@Column(name="long_perm")
	public String getLongPerm() {
		return longPerm;
	}

	public void setLongPerm(String longPerm) {
		this.longPerm = longPerm;
	}

	@Column(name="plan_date")
	public String getPlanDate() {
		return planDate;
	}
	
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
