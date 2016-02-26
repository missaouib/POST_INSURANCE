package com.gdpost.web.entity.main;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gdpost.web.entity.Idable;

/**
 * TOffsiteConservation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_offsite_conservation")
public class OffsiteConservation implements Idable<Long> {

	// Fields

	private Long id;
	private Organization organization;
	private String transactor;
	private Date dealDate;
	private String policyNo;
	private String orginProv;
	private String client;
	private String conservationType;
	private String linker;
	private Date provDealDate;
	private String expressBillNo;
	private String mailAddr;
	private String remark;
	private Long operatorId;
	private Date operateTime;
	
	private String status;
	private String dealProv;

	// Constructors

	/** default constructor */
	public OffsiteConservation() {
	}

	/** full constructor */
	public OffsiteConservation(Organization organization, String transactor,
			Date dealDate, String policyNo, String orginProv, String client,
			String conservationType, String linker, Date provDealDate,
			String expressBillNo, String mailAddr, String remark,
			Long operatorId, Timestamp operateTime) {
		this.organization = organization;
		this.transactor = transactor;
		this.dealDate = dealDate;
		this.policyNo = policyNo;
		this.orginProv = orginProv;
		this.client = client;
		this.conservationType = conservationType;
		this.linker = linker;
		this.provDealDate = provDealDate;
		this.expressBillNo = expressBillNo;
		this.mailAddr = mailAddr;
		this.remark = remark;
		this.operatorId = operatorId;
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

	@ManyToOne
	@JoinColumn(name = "org_code", referencedColumnName="org_code")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Column(name = "transactor", length = 16)
	public String getTransactor() {
		return this.transactor;
	}

	public void setTransactor(String transactor) {
		this.transactor = transactor;
	}

	@Column(name = "deal_date", length = 10)
	public Date getDealDate() {
		return this.dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	@Column(name = "policy_no", length = 18)
	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name = "orgin_prov", length = 12)
	public String getOrginProv() {
		return this.orginProv;
	}

	public void setOrginProv(String orginProv) {
		this.orginProv = orginProv;
	}

	@Column(name = "client", length = 12)
	public String getClient() {
		return this.client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	@Column(name = "conservation_type", length = 12)
	public String getConservationType() {
		return this.conservationType;
	}

	public void setConservationType(String conservationType) {
		this.conservationType = conservationType;
	}

	@Column(name = "linker", length = 12)
	public String getLinker() {
		return this.linker;
	}

	public void setLinker(String linker) {
		this.linker = linker;
	}

	@Column(name = "prov_deal_date", length = 10)
	public Date getProvDealDate() {
		return this.provDealDate;
	}

	public void setProvDealDate(Date provDealDate) {
		this.provDealDate = provDealDate;
	}

	@Column(name = "express_bill_no", length = 16)
	public String getExpressBillNo() {
		return this.expressBillNo;
	}

	public void setExpressBillNo(String expressBillNo) {
		this.expressBillNo = expressBillNo;
	}

	@Column(name = "mail_addr", length = 128)
	public String getMailAddr() {
		return this.mailAddr;
	}

	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}

	@Column(name = "remark", length = 256)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="deal_prov")
	public String getDealProv() {
		return dealProv;
	}

	public void setDealProv(String dealProv) {
		this.dealProv = dealProv;
	}

	@Column(name = "operator_id")
	public Long getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	@Column(name = "operate_time", length = 19)
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Override
	public String toString() {
		return "OffsiteConservation [id=" + id + ", organization="
				+ organization + ", transactor=" + transactor + ", dealDate="
				+ dealDate + ", policyNo=" + policyNo + ", orginProv="
				+ orginProv + ", client=" + client + ", conservationType="
				+ conservationType + ", linker=" + linker + ", provDealDate="
				+ provDealDate + ", expressBillNo=" + expressBillNo
				+ ", mailAddr=" + mailAddr + ", remark=" + remark
				+ ", operatorId=" + operatorId + ", operateTime=" + operateTime
				+ ", status=" + status + "]";
	}

}