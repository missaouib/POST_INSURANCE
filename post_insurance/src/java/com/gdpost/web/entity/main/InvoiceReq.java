package com.gdpost.web.entity.main;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gdpost.web.entity.Idable;

/**
 * TInvoiceReq entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_invoice_req")
public class InvoiceReq implements Idable<Long> {

	// Fields

	private Long id;
	private Policy policy;
	private String flag;
	private Date feeDate;
	private Double fee;
	private Long dealMan;
	private Date reqDate;
	private String reqAddr;
	private String reqMan;
	private String phone;
	private String status;
	private Date sendDate;
	private Long sendMan;
	private Long receiveMan;
	private Date receiveDate;
	private String billNo;
	private Boolean isElectiveBill;
	private String billAddr;

	// Constructors

	/** default constructor */
	public InvoiceReq() {
	}

	/** minimal constructor */
	public InvoiceReq(Policy policy, Date reqDate) {
		this.policy = policy;
		this.reqDate = reqDate;
	}

	/** full constructor */
	public InvoiceReq(Policy policy, String flag, Date feeDate, Double fee, Long dealMan, Date reqDate, String reqAddr, String reqMan, String phone,
			String status, Date sendDate, Long sendMan, Long receiveMan, Date receiveDate) {
		this.policy = policy;
		this.flag = flag;
		this.feeDate = feeDate;
		this.fee = fee;
		this.dealMan = dealMan;
		this.reqDate = reqDate;
		this.reqAddr = reqAddr;
		this.reqMan = reqMan;
		this.phone = phone;
		this.status = status;
		this.sendDate = sendDate;
		this.sendMan = sendMan;
		this.receiveMan = receiveMan;
		this.receiveDate = receiveDate;
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
	@JoinColumn(name = "policy_no", referencedColumnName="policy_no")
	public Policy getPolicy() {
		return this.policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Column(name = "flag", length = 2)
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "fee_date", length = 10)
	public Date getFeeDate() {
		return this.feeDate;
	}

	public void setFeeDate(Date feeDate) {
		this.feeDate = feeDate;
	}

	@Column(name = "fee", precision = 22, scale = 0)
	public Double getFee() {
		return this.fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	@Column(name = "deal_man")
	public Long getDealMan() {
		return this.dealMan;
	}

	public void setDealMan(Long dealMan) {
		this.dealMan = dealMan;
	}

	@Column(name = "req_date", nullable = false, length = 19)
	public Date getReqDate() {
		return this.reqDate;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	@Column(name = "req_addr", length = 128)
	public String getReqAddr() {
		return this.reqAddr;
	}

	public void setReqAddr(String reqAddr) {
		this.reqAddr = reqAddr;
	}

	@Column(name = "req_man", length = 5)
	public String getReqMan() {
		return this.reqMan;
	}

	public void setReqMan(String reqMan) {
		this.reqMan = reqMan;
	}

	@Column(name = "phone", length = 23)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "status", length = 12)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "send_date", length = 10)
	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Column(name = "send_man")
	public Long getSendMan() {
		return this.sendMan;
	}

	public void setSendMan(Long sendMan) {
		this.sendMan = sendMan;
	}

	@Column(name = "receive_man")
	public Long getReceiveMan() {
		return this.receiveMan;
	}

	public void setReceiveMan(Long receiveMan) {
		this.receiveMan = receiveMan;
	}

	@Column(name = "receive_date", length = 10)
	public Date getReceiveDate() {
		return this.receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	@Column(name="bill_no")
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	@Column(name="is_elective_bill")
	public Boolean getIsElectiveBill() {
		return isElectiveBill;
	}

	public void setIsElectiveBill(Boolean isElectiveBill) {
		this.isElectiveBill = isElectiveBill;
	}

	@Column(name="bill_addr")
	public String getBillAddr() {
		return billAddr;
	}

	public void setBillAddr(String billAddr) {
		this.billAddr = billAddr;
	}

}