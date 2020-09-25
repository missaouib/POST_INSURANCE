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

import com.gdpost.utils.StringUtil;
import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.Organization;

/**
 * TSettleTask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_settle_task")
public class SettleTask implements Idable<Long>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3678356878663291218L;
	@Transient
	public static final String STATUS_ING = "调查中";
	@Transient
	public static final String STATUS_DONE = "调查完成";
	// Fields

	private Long id;
	private Organization organization;
	private Policy policy;
	private Settlement settlement;
	private String insured;
	private String prodName;
	private Double policyFee;
	private Date policyDate;
	private Date caseDate;
	private String caseType;
	private Date checkStartDate;
	private Date checkEndDate;
	private Integer limitation;
	private String checkReq;
	private String checker;
	private String checkerAddr;
	private Double checkFee;
	private String attrLink;
	private String checkStatus;
	private Long operateId;
	private Date createTime;
	private String remark;
	private Integer policyType;//0个险1团险
	private Date hxDate;
	private Date reporteDate;
	private Date followDate;
	private List<SettleTaskLog> settleTaskLogs = new ArrayList<SettleTaskLog>(0);
	
	@Transient
	private String needFeedBack;
	
	@Transient
	private Integer lessFeedBack;

	@Transient
	public String getNeedFeedBack() {
		if(this.settleTaskLogs != null && this.settleTaskLogs.size()>0) {
			SettleTaskLog slog = this.settleTaskLogs.get(this.settleTaskLogs.size()-1);
			Integer lfb = null;
			if(this.followDate != null) {
				lfb = StringUtil.getBetweenDay(new Date(), this.followDate);
			}
			if (slog != null && slog.getIsFollow() != null && slog.getIsFollow() && slog.getFollowDate() == null || (lfb != null && lfb<=0)) {
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

	@Transient
	public Integer getLessFeedBack() {
		if(this.followDate != null) {
			return StringUtil.getBetweenDay(new Date(), this.followDate);
		}
		return lessFeedBack;
	}

	@Transient
	public void setLessFeedBack(Integer lessFeedBack) {
		this.lessFeedBack = lessFeedBack;
	}
	
	// Constructors

	/** default constructor */
	public SettleTask() {
	}

	/** full constructor */
	public SettleTask(Policy policy, String insured, Date checkStartDate, Date checkEndDate, Integer limitation, String checkReq,
			String checker, String checkerAddr, Double checkFee, String attrLink, Long operateId, Date createTime) {
		this.policy = policy;
		this.insured = insured;
		this.checkStartDate = checkStartDate;
		this.checkEndDate = checkEndDate;
		this.limitation = limitation;
		this.checkReq = checkReq;
		this.checker = checker;
		this.checkerAddr = checkerAddr;
		this.checkFee = checkFee;
		this.attrLink = attrLink;
		this.operateId = operateId;
		this.createTime = createTime;
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

	@ManyToOne(optional=true)
	@JoinColumn(name = "org_id", nullable = true)
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToOne(optional=true)
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
	
	@OneToOne(optional=true)
	@JoinColumn(name="claims_no", referencedColumnName="claims_no", nullable=true)
	public Settlement getSettlement() {
		return this.settlement;
	}

	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}

	@Column(name = "insured", length = 32)
	public String getInsured() {
		return this.insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	@Column(name = "prod_name")
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	@Column(name = "policy_fee")
	public Double getPolicyFee() {
		return policyFee;
	}

	public void setPolicyFee(Double policyFee) {
		this.policyFee = policyFee;
	}

	@Column(name = "policy_date")
	public Date getPolicyDate() {
		return policyDate;
	}

	public void setPolicyDate(Date policyDate) {
		this.policyDate = policyDate;
	}

	@Column(name = "case_date")
	public Date getCaseDate() {
		return caseDate;
	}

	public void setCaseDate(Date caseDate) {
		this.caseDate = caseDate;
	}

	@Column(name = "case_type")
	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	@Column(name = "check_start_date", length = 10)
	public Date getCheckStartDate() {
		return this.checkStartDate;
	}

	public void setCheckStartDate(Date checkStartDate) {
		this.checkStartDate = checkStartDate;
	}

	@Column(name = "check_end_date", length = 10)
	public Date getCheckEndDate() {
		return this.checkEndDate;
	}

	public void setCheckEndDate(Date checkEndDate) {
		this.checkEndDate = checkEndDate;
	}

	@Column(name = "limitation")
	public Integer getLimitation() {
		//this.limitation = StringUtil.getBetweenDay(checkStartDate, this.checkEndDate==null?new Date():this.checkEndDate);
		return this.limitation;
	}

	public void setLimitation(Integer limitation) {
		this.limitation = limitation;
	}

	@Column(name = "check_req", length = 254)
	public String getCheckReq() {
		return this.checkReq;
	}

	public void setCheckReq(String checkReq) {
		this.checkReq = checkReq;
	}

	@Column(name = "checker", length = 16)
	public String getChecker() {
		return this.checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	@Column(name = "checker_addr", length = 16)
	public String getCheckerAddr() {
		return this.checkerAddr;
	}

	public void setCheckerAddr(String checkerAddr) {
		this.checkerAddr = checkerAddr;
	}

	@Column(name = "check_fee", precision = 22, scale = 0)
	public Double getCheckFee() {
		return this.checkFee;
	}

	public void setCheckFee(Double checkFee) {
		this.checkFee = checkFee;
	}

	@Column(name = "attr_link", length = 128)
	public String getAttrLink() {
		return this.attrLink;
	}

	public void setAttrLink(String attrLink) {
		this.attrLink = attrLink;
	}

	@Column(name="check_status")
	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	@Column(name = "operate_id")
	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Column(name = "create_time", length = 10)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "settleTask")
	public List<SettleTaskLog> getSettleTaskLogs() {
		return this.settleTaskLogs;
	}

	public void setSettleTaskLogs(List<SettleTaskLog> settleTaskLogs) {
		this.settleTaskLogs = settleTaskLogs;
	}
	
	@Column(name="policy_type")
	public Integer getPolicyType() {
		return policyType;
	}
	public void setPolicyType(Integer policyType) {
		this.policyType = policyType;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="hx_date")
	public Date getHxDate() {
		return hxDate;
	}
	public void setHxDate(Date hxDate) {
		this.hxDate = hxDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="reporte_date")
	public Date getReporteDate() {
		return reporteDate;
	}
	public void setReporteDate(Date reporteDate) {
		this.reporteDate = reporteDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="follow_date")
	public Date getFollowDate() {
		return followDate;
	}
	public void setFollowDate(Date followDate) {
		this.followDate = followDate;
	}

}