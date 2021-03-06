package com.gdpost.web.entity.insurance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.Organization;

/**
 * settlement entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_gsettle")
public class Gsettle implements Idable<Long> {

	// Fields

	private Long id;
	private Organization organization;
	private String gpolicyNo;
	private String insured;
	private String reporter;
	private String reporterPhone;
	private Date caseDate;
	private String caseType;
	private Date reporteDate;
	private Date recordDate;
	private Date closeDate;
	private Double payFee;
	private String caseStatus;
	private String remark;
	private Long operateId;
	private Date createTime;
	private List<GsettleLog> gsettleLogs = new ArrayList<GsettleLog>(0);
	private GsettleDtl gsettleDtls;
	
	@Transient
	private String needFeedBack;
	
	// Constructors

	/** default constructor */
	public Gsettle() {
	}

	/** full constructor */
	public Gsettle(Organization organization, String insured, String reporter,
			String reporterPhone, Date caseDate, String caseType,
			Date reporteDate, Date recordDate, Date closeDate, Double payFee,
			String caseStatus, String remark) {
		this.organization = organization;
		this.insured = insured;
		this.reporter = reporter;
		this.reporterPhone = reporterPhone;
		this.caseDate = caseDate;
		this.caseType = caseType;
		this.reporteDate = reporteDate;
		this.recordDate = recordDate;
		this.closeDate = closeDate;
		this.payFee = payFee;
		this.caseStatus = caseStatus;
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

	@ManyToOne
	@JoinColumn(name = "org_id", referencedColumnName="id")
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	@Column(name = "gpolicy_no")
	public String getGpolicyNo() {
		return gpolicyNo;
	}

	public void setGpolicyNo(String gpolicyNo) {
		this.gpolicyNo = gpolicyNo;
	}

	@Column(name = "insured", length = 32)
	public String getInsured() {
		return this.insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	@Column(name = "reporter", length = 32)
	public String getReporter() {
		return this.reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	@Column(name = "reporter_phone", length = 32)
	public String getReporterPhone() {
		return this.reporterPhone;
	}

	public void setReporterPhone(String reporterPhone) {
		this.reporterPhone = reporterPhone;
	}

	@Column(name = "case_date", length = 10)
	public Date getCaseDate() {
		return this.caseDate;
	}

	public void setCaseDate(Date caseDate) {
		this.caseDate = caseDate;
	}

	@Column(name = "case_type", length = 16)
	public String getCaseType() {
		return this.caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	@Column(name = "reporte_date", length = 10)
	public Date getReporteDate() {
		return this.reporteDate;
	}

	public void setReporteDate(Date reporteDate) {
		this.reporteDate = reporteDate;
	}

	@Column(name = "record_date", length = 10)
	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	@Column(name = "close_date", length = 10)
	public Date getCloseDate() {
		return this.closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	@Column(name = "pay_fee", precision = 22, scale = 0)
	public Double getPayFee() {
		return this.payFee;
	}

	public void setPayFee(Double payFee) {
		this.payFee = payFee;
	}

	@Column(name = "case_status", length = 16)
	public String getCaseStatus() {
		return this.caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	@Column(name = "remark", length = 1024)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="operate_id")
	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "gsettle")
	public List<GsettleLog> getGsettleLogs() {
		return gsettleLogs;
	}

	public void setGsettleLogs(List<GsettleLog> gsettleLogs) {
		this.gsettleLogs = gsettleLogs;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "gsettle", orphanRemoval = true)
	public GsettleDtl getGsettleDtls() {
		return gsettleDtls;
	}

	public void setGsettleDtls(GsettleDtl gsettleDtls) {
		this.gsettleDtls = gsettleDtls;
	}

	@Transient
	public String getNeedFeedBack() {
		if(this.gsettleLogs != null && this.gsettleLogs.size()>0) {
			GsettleLog slog = this.gsettleLogs.get(this.gsettleLogs.size()-1);
			if (slog != null && slog.getIsFollow() != null && slog.getIsFollow() && slog.getFollowDate() == null) {
				return "?????????";
			} else if (slog != null && slog.getIsFollow() != null && slog.getIsFollow() && slog.getFollowDate() != null) {
				return "?????????";
			}
		}
		return null;
	}

	@Transient
	public void setNeedFeedBack(String needFeedBack) {
		this.needFeedBack = needFeedBack;
	}
	
	
}