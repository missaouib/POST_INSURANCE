package com.gdpost.web.entity.main;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.component.CsReport;

/**
 * TCsReissue entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cs_reissue")
public class CsReissue implements Idable<Long> {

	// Fields

	private Long id;
	private CsReport csReport;
	private String provExpressNo;
	private Date provReceiveDate;
	private Date provSentDate;
	private String status;
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
	public CsReissue(Long id, CsReport csReport,
			String provExpressNo, Date provReceiveDate, Date provSentDate,
			String status, Date cityReceiveDate, String cityReceiver,
			String remark) {
		this.id = id;
		this.csReport = csReport;
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
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name = "cs_id", referencedColumnName="id")
	public CsReport getCsReport() {
		return this.csReport;
	}

	public void setCsReport(CsReport csReport) {
		this.csReport = csReport;
	}

	@Column(name = "prov_express_no", length = 32)
	public String getProvExpressNo() {
		return this.provExpressNo;
	}

	public void setProvExpressNo(String provExpressNo) {
		this.provExpressNo = provExpressNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "prov_receive_date", length = 10)
	public Date getProvReceiveDate() {
		return this.provReceiveDate;
	}

	public void setProvReceiveDate(Date provReceiveDate) {
		this.provReceiveDate = provReceiveDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "prov_sent_date", length = 10)
	public Date getProvSentDate() {
		return this.provSentDate;
	}

	public void setProvSentDate(Date provSentDate) {
		this.provSentDate = provSentDate;
	}

	@Column(name = "status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
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

	@Override
	public String toString() {
		return "CsReissue [id=" + id + ", provExpressNo=" + provExpressNo + ", provReceiveDate="
				+ provReceiveDate + ", provSentDate=" + provSentDate
				+ ", status=" + status + ", cityReceiveDate=" + cityReceiveDate
				+ ", cityReceiver=" + cityReceiver + ", remark=" + remark + "]";
	}

}