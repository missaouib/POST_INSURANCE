package com.gdpost.web.entity.main;

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
 * TRenewedFeeRst entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_renewed_fee_rst")
public class RenewedFeeRst implements Idable<Long> {

	// Fields

	private Long id;
	private String policyNo;
	private String prodCode;
	private String holder;
	private Date reqDate;
	private Double reqFee;
	private Double rstFee;
	private Date rstDate;
	private String feeChannel;
	private String feeType;
	private String policyFlag;

	// Constructors

	/** default constructor */
	public RenewedFeeRst() {
	}

	/** full constructor */
	public RenewedFeeRst(String policyNo, String holder, Date reqDate, Double reqFee, Double rstFee, Date rstDate,
			String feeChannel, String feeType, String policyFlag) {
		this.policyNo = policyNo;
		this.holder = holder;
		this.reqDate = reqDate;
		this.reqFee = reqFee;
		this.rstFee = rstFee;
		this.rstDate = rstDate;
		this.feeChannel = feeChannel;
		this.feeType = feeType;
		this.policyFlag = policyFlag;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "policy_no", length = 15)
	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name = "prod_code")
	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	@Column(name = "holder", length = 12)
	public String getHolder() {
		return this.holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "req_date", length = 10)
	public Date getReqDate() {
		return this.reqDate;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	@Column(name = "req_fee", precision = 22, scale = 0)
	public Double getReqFee() {
		return this.reqFee;
	}

	public void setReqFee(Double reqFee) {
		this.reqFee = reqFee;
	}

	@Column(name = "rst_fee", precision = 22, scale = 0)
	public Double getRstFee() {
		return this.rstFee;
	}

	public void setRstFee(Double rstFee) {
		this.rstFee = rstFee;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "rst_date", length = 10)
	public Date getRstDate() {
		return this.rstDate;
	}

	public void setRstDate(Date rstDate) {
		this.rstDate = rstDate;
	}

	@Column(name = "fee_channel", length = 12)
	public String getFeeChannel() {
		return this.feeChannel;
	}

	public void setFeeChannel(String feeChannel) {
		this.feeChannel = feeChannel;
	}

	@Column(name = "fee_type", length = 6)
	public String getFeeType() {
		return this.feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	@Column(name = "policy_flag", length = 12)
	public String getPolicyFlag() {
		return this.policyFlag;
	}

	public void setPolicyFlag(String policyFlag) {
		this.policyFlag = policyFlag;
	}

}