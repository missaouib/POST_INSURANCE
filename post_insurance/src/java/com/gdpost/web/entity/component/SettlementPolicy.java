package com.gdpost.web.entity.component;

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

import com.gdpost.web.entity.Idable;

/**
 * SettlementPolicy entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_settlement_policy")
public class SettlementPolicy implements Idable<Long> {

	// Fields

	private Long id;
	private Settlement settlement;
	private String policyNo;
	private String prod;
	private Double policyFee;
	private Date validDate;

	// Constructors

	/** default constructor */
	public SettlementPolicy() {
	}

	/** minimal constructor */
	public SettlementPolicy(Long id) {
		this.id = id;
	}

	/** full constructor */
	public SettlementPolicy(Long id, Settlement settlement, String policyNo,
			String prod, Double policyFee, Date validDate) {
		this.id = id;
		this.settlement = settlement;
		this.policyNo = policyNo;
		this.prod = prod;
		this.policyFee = policyFee;
		this.validDate = validDate;
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
	@JoinColumn(name = "settlement_id")
	public Settlement getSettlement() {
		return this.settlement;
	}

	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}

	@Column(name = "policy_no", length = 18)
	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name = "prod", length = 64)
	public String getProd() {
		return this.prod;
	}

	public void setProd(String prod) {
		this.prod = prod;
	}

	@Column(name = "policy_fee", precision = 22, scale = 0)
	public Double getPolicyFee() {
		return this.policyFee;
	}

	public void setPolicyFee(Double policyFee) {
		this.policyFee = policyFee;
	}

	@Column(name = "valid_date", length = 10)
	public Date getValidDate() {
		return this.validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

}