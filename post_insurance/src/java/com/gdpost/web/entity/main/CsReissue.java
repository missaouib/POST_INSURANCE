package com.gdpost.web.entity.main;

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
 * TCsReissue entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cs_reissue")
public class CsReissue implements Idable<Long> {

	// Fields

	private Long id;
	private ConservationDtl conservationDtl;
	private String provExpressNo;
	private Date provReceiveDate;
	private Date provSentDate;
	private Integer status;
	private Date cityReceiveDate;
	private String cityReceiver;
	private String remark;

	// Constructors

	/** default constructor */
	public CsReissue() {
	}

	/** minimal constructor */
	public CsReissue(Long id) {
		this.id = id;
	}

	/** full constructor */
	public CsReissue(Long id, ConservationDtl ConservationDtl,
			String provExpressNo, Date provReceiveDate, Date provSentDate,
			Integer status, Date cityReceiveDate, String cityReceiver,
			String remark) {
		this.id = id;
		this.conservationDtl = ConservationDtl;
		this.provExpressNo = provExpressNo;
		this.provReceiveDate = provReceiveDate;
		this.provSentDate = provSentDate;
		this.status = status;
		this.cityReceiveDate = cityReceiveDate;
		this.cityReceiver = cityReceiver;
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
	@JoinColumn(name = "cs_id")
	public ConservationDtl getConservationDtl() {
		return this.conservationDtl;
	}

	public void setConservationDtl(ConservationDtl conservationDtl) {
		this.conservationDtl = conservationDtl;
	}

	@Column(name = "prov_express_no", length = 32)
	public String getProvExpressNo() {
		return this.provExpressNo;
	}

	public void setProvExpressNo(String provExpressNo) {
		this.provExpressNo = provExpressNo;
	}

	@Column(name = "prov_receive_date", length = 10)
	public Date getProvReceiveDate() {
		return this.provReceiveDate;
	}

	public void setProvReceiveDate(Date provReceiveDate) {
		this.provReceiveDate = provReceiveDate;
	}

	@Column(name = "prov_sent_date", length = 10)
	public Date getProvSentDate() {
		return this.provSentDate;
	}

	public void setProvSentDate(Date provSentDate) {
		this.provSentDate = provSentDate;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "city_receive_date", length = 10)
	public Date getCityReceiveDate() {
		return this.cityReceiveDate;
	}

	public void setCityReceiveDate(Date cityReceiveDate) {
		this.cityReceiveDate = cityReceiveDate;
	}

	@Column(name = "city_receiver", length = 16)
	public String getCityReceiver() {
		return this.cityReceiver;
	}

	public void setCityReceiver(String cityReceiver) {
		this.cityReceiver = cityReceiver;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}