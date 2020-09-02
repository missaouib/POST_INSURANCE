package com.gdpost.web.entity.insurance;

import java.io.Serializable;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.Organization;

/**
 * settlement entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_settlement")
public class Settlement implements Idable<Long>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3048489897303282878L;
	// Fields

	private Long id;
	private Organization organization;
	private String organName;
	private String claimsNo;
	private Policy policy;
	private String caseType;
	private String caseMan;
	private String reporter;
	private String reporterPhone;
	private Date settleDate;
	private Date caseDate;
	private Date reporteDate;
	private Date followDate;
	private String caseStatus;
	private String remark;
	private Date operateTime;
	private Long operateId;
	
	private SettleTask settleTask;
	private ClaimsCloseReport claimsCloseReport;
	private List<SettlementLog> settlementLogs = new ArrayList<SettlementLog>(0);
	
	@Transient
	private String needFeedBack;
	
	// Constructors

	/** default constructor */
	public Settlement() {
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
	@JoinColumn(name = "organ_code", referencedColumnName="org_code")
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	@Column(name = "organ_name", length = 60)

	public String getOrganName() {
		return this.organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	@Column(name = "claims_no", length = 21)

	public String getClaimsNo() {
		return this.claimsNo;
	}

	public void setClaimsNo(String claimsNo) {
		this.claimsNo = claimsNo;
	}

	@ManyToOne(optional=true)
	@JoinColumnsOrFormulas(value={
	@JoinColumnOrFormula(column=@JoinColumn(name ="policy_no", referencedColumnName ="policy_no", insertable =false, updatable = false, nullable=true)),
	@JoinColumnOrFormula(formula=@JoinFormula(value="0", referencedColumnName = "attached_flag"))
	})
	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Column(name = "case_type", length = 15)

	public String getCaseType() {
		return this.caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	@Column(name = "case_man", length = 12)

	public String getCaseMan() {
		return this.caseMan;
	}

	public void setCaseMan(String caseMan) {
		this.caseMan = caseMan;
	}

	@Column(name = "reporter", length = 12)

	public String getReporter() {
		return this.reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	@Column(name = "reporter_phone", length = 11)

	public String getReporterPhone() {
		return this.reporterPhone;
	}

	public void setReporterPhone(String reporterPhone) {
		this.reporterPhone = reporterPhone;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "settle_date", length = 10)

	public Date getSettleDate() {
		return this.settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "case_date", length = 10)

	public Date getCaseDate() {
		return this.caseDate;
	}

	public void setCaseDate(Date caseDate) {
		this.caseDate = caseDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "reporte_date", length = 10)

	public Date getReporteDate() {
		return this.reporteDate;
	}

	public void setReporteDate(Date reporteDate) {
		this.reporteDate = reporteDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "follow_date", length = 10)

	public Date getFollowDate() {
		return this.followDate;
	}

	public void setFollowDate(Date followDate) {
		this.followDate = followDate;
	}

	@Column(name = "case_status", length = 9)

	public String getCaseStatus() {
		return this.caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	@Column(name = "remark", length = 119)

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "operate_time", length = 26)

	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "operate_id")

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	/**/
	@OneToOne(optional=true)
	@JoinColumn(name="claims_no", referencedColumnName="claims_no", insertable=false, updatable=false, nullable=true)
	public SettleTask getSettleTask() {
		return settleTask;
	}

	public void setSettleTask(SettleTask settleTask) {
		this.settleTask = settleTask;
	}
	
	@OneToOne(optional=true)
	@JoinColumn(name="claims_no", referencedColumnName="claims_no", insertable=false, updatable=false, nullable=true)
	public ClaimsCloseReport getClaimsCloseReport() {
		return claimsCloseReport;
	}

	public void setClaimsCloseReport(ClaimsCloseReport claimsCloseReport) {
		this.claimsCloseReport = claimsCloseReport;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "settlement")
	public List<SettlementLog> getSettlementLogs() {
		return settlementLogs;
	}

	public void setSettlementLogs(List<SettlementLog> settlementLogs) {
		this.settlementLogs = settlementLogs;
	}

	@Transient
	public String getNeedFeedBack() {
		if(this.settlementLogs != null && this.settlementLogs.size()>0) {
			SettlementLog slog = this.settlementLogs.get(this.settlementLogs.size()-1);
			if (slog != null && slog.getIsFollow() != null && slog.getIsFollow() && slog.getFollowDate() == null) {
				return "待反馈";
			} else if (slog != null && slog.getIsFollow() != null && slog.getIsFollow() && slog.getFollowDate() != null) {
				return "已反馈";
			}
		}
		return null;
	}

	@Transient
	public void setNeedFeedBack(String needFeedBack) {
		this.needFeedBack = needFeedBack;
	}
	
	
}