package com.gdpost.web.entity.component;

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
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.Policy;

/**
 * TSettleTask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_settle_task")
public class SettleTask implements Idable<Long> {

	@Transient
	public static final String STATUS_ING = "调查中";
	@Transient
	public static final String STATUS_DONE = "调查完成";
	// Fields

	private Long id;
	private Organization organization;
	private Policy policy;
	private String insured;
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
	private List<SettleTaskLog> settleTaskLogs = new ArrayList<SettleTaskLog>(0);

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

	@ManyToOne
	@JoinColumn(name = "org_id")
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToOne
	@JoinColumn(name="policy_no", referencedColumnName="policy_no")
	public Policy getPolicy() {
		return this.policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Column(name = "insured", length = 32)
	public String getInsured() {
		return this.insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "settleTask")
	public List<SettleTaskLog> getSettleTaskLogs() {
		return this.settleTaskLogs;
	}

	public void setSettleTaskLogs(List<SettleTaskLog> settleTaskLogs) {
		this.settleTaskLogs = settleTaskLogs;
	}

}