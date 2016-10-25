package com.gdpost.web.entity.main;

import java.util.Date;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.basedata.BankCode;

/**
 * TConservationReq entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_conservation_req")
public class ConservationReq implements Idable<Long> {

	// Fields

	private Long id;
	private String formNo;
	private Policy policy;
	private BankCode bankCode;
	private String bankName;
	private String reqMan;
	private String reqTypeName;
	private Date reqDate;
	private String reqPhone;
	private String status;
	private Long operateId;

	// Constructors

	/** default constructor */
	public ConservationReq() {
	}

	/** full constructor */
	public ConservationReq(String formNo, String policyNo, String bankCode, String reqMan, String reqTypeName,
			Date reqDate, String reqPhone) {
		this.formNo = formNo;
		this.reqMan = reqMan;
		this.reqTypeName = reqTypeName;
		this.reqDate = reqDate;
		this.reqPhone = reqPhone;
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

	@Column(name = "form_no", length = 25)

	public String getFormNo() {
		return this.formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	@ManyToOne(optional=true)
	@JoinColumnsOrFormulas(value={
	@JoinColumnOrFormula(column=@JoinColumn(name ="policy_no", referencedColumnName ="policy_no", insertable =false, updatable = false, nullable=true)),
	@JoinColumnOrFormula(formula=@JoinFormula(value="0", referencedColumnName = "flag"))
	})
	public Policy getPolicy() {
		return this.policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@JoinColumn(name="bank_code", referencedColumnName="ybt_code", nullable=true)
	@ManyToOne(targetEntity=BankCode.class, optional=true)
	public BankCode getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(BankCode bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name="bank_name")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "req_man", length = 12)
	public String getReqMan() {
		return this.reqMan;
	}

	public void setReqMan(String reqMan) {
		this.reqMan = reqMan;
	}

	@Column(name = "req_type_name", length = 32)

	public String getReqTypeName() {
		return this.reqTypeName;
	}

	public void setReqTypeName(String reqTypeName) {
		this.reqTypeName = reqTypeName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "req_date", length = 10)

	public Date getReqDate() {
		return this.reqDate;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	@Column(name = "req_phone", length = 18)
	public String getReqPhone() {
		return this.reqPhone;
	}

	public void setReqPhone(String reqPhone) {
		this.reqPhone = reqPhone;
	}
	
	@Column(name="status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="operate_id")
	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

}