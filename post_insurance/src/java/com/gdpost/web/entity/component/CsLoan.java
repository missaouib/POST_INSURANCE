package com.gdpost.web.entity.component;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.Policy;

/**
 * TCsLoan entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cs_loan")

public class CsLoan implements Idable<Long> {

	// Fields

	private Long id;
	private String organName;
	private Policy policy;
	private String holder;
	private String holderSexy;
	private String prodName;
	private String bankName;
	private Date loanDate;
	private Double loanFee;
	private Date shouldDate;
	private Date realDate;
	private Double backFee;
	private Double freeFee;
	private String freeReason;
	private String status;
	private String phone;
	private Long operatorId;
	private Date operateTime;

	// Constructors

	/** default constructor */
	public CsLoan() {
	}

	/** full constructor */
	public CsLoan(String organName, Policy policy, String holder, String holderSexy, String prodName,
			String bankName, Date loanDate, Double loanFee, Date shouldDate, Date realDate, Double backFee,
			Double freeFee, String freeReason, String status, String phone, Long operatorId, Date operateTime) {
		this.organName = organName;
		this.policy = policy;
		this.holder = holder;
		this.holderSexy = holderSexy;
		this.prodName = prodName;
		this.bankName = bankName;
		this.loanDate = loanDate;
		this.loanFee = loanFee;
		this.shouldDate = shouldDate;
		this.realDate = realDate;
		this.backFee = backFee;
		this.freeFee = freeFee;
		this.freeReason = freeReason;
		this.status = status;
		this.phone = phone;
		this.operatorId = operatorId;
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

	@Column(name = "organ_name", length = 64)

	public String getOrganName() {
		return this.organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

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

	@Column(name = "holder", length = 18)

	public String getHolder() {
		return this.holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	@Column(name = "holder_sexy", length = 4)

	public String getHolderSexy() {
		return this.holderSexy;
	}

	public void setHolderSexy(String holderSexy) {
		this.holderSexy = holderSexy;
	}

	@Column(name = "prod_name", length = 64)

	public String getProdName() {
		return this.prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	@Column(name = "bank_name", length = 128)

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "loan_date", length = 10)

	public Date getLoanDate() {
		return this.loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	@Column(name = "loan_fee", precision = 22, scale = 0)

	public Double getLoanFee() {
		return this.loanFee;
	}

	public void setLoanFee(Double loanFee) {
		this.loanFee = loanFee;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "should_date", length = 10)

	public Date getShouldDate() {
		return this.shouldDate;
	}

	public void setShouldDate(Date shouldDate) {
		this.shouldDate = shouldDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "real_date", length = 10)

	public Date getRealDate() {
		return this.realDate;
	}

	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}

	@Column(name = "back_fee", precision = 22, scale = 0)

	public Double getBackFee() {
		return this.backFee;
	}

	public void setBackFee(Double backFee) {
		this.backFee = backFee;
	}

	@Column(name = "free_fee", precision = 22, scale = 0)

	public Double getFreeFee() {
		return this.freeFee;
	}

	public void setFreeFee(Double freeFee) {
		this.freeFee = freeFee;
	}

	@Column(name = "free_reason", length = 128)

	public String getFreeReason() {
		return this.freeReason;
	}

	public void setFreeReason(String freeReason) {
		this.freeReason = freeReason;
	}

	@Column(name = "status", length = 12)

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "phone", length = 12)

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

}