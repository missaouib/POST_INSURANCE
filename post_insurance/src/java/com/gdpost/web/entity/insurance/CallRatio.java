package com.gdpost.web.entity.insurance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gdpost.web.entity.Idable;

/**
 * TCallRatio entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_call_ratio")

public class CallRatio implements java.io.Serializable, Idable<Long> {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4328835523112877513L;
	private Long id;
	private String cityCode;
	private String cityName;
	private String areaCode;
	private String areaName;
	private String netCode;
	private String netName;
	private Integer realCount;
	private Integer standCount;
	private Integer callIntime;
	private String callRatio;
	private Integer failDoor;
	private Integer failLetter;
	private Integer failConfirm;
	private Integer failElse;
	private Integer failReport;
	private Integer failComplaint;
	private Integer failSale;
	private Integer failCt;
	private Integer failFit;
	private Integer failNotdeal;
	private Integer failCut;
	private Integer failNoanswer;
	private Integer failLenguage;
	private Integer failLink;
	private Integer failErr;
	private Integer failNobody;
	private Integer failCount;
	private Integer issueElse;
	private Integer issueTell;
	private Integer issueWrite;
	private Integer issueCt;
	private Integer issueSign1;
	private Integer issueSign2;
	private Integer issueSaving;
	private Integer issueProfit;
	private Integer issuePeriod;
	private Integer issueDuration;
	private Integer issueDuty;
	private Integer issueIntend;
	private Integer issueRecord;
	private Integer issueDelivery;
	private Integer issueCount;
	private Integer rejectCount;
	private Integer ctCount;
	private Integer orderCount;
	private Integer waitCount;
	private String successRatio;
	private String attachedRatio;
	private String recordRatio;
	private String issuePercent;
	private Date SDate;
	private Date EDate;

	// Constructors

	/** default constructor */
	public CallRatio() {
	}

	/** full constructor */
	public CallRatio(String cityCode, String cityName, String areaCode, String areaName, String netCode,
			String netName, Integer realCount, Integer standCount, Integer callIntime, String callRatio,
			Integer failDoor, Integer failLetter, Integer failConfirm, Integer failElse, Integer failReport,
			Integer failComplaint, Integer failSale, Integer failCt, Integer failFit, Integer failNotdeal,
			Integer failCut, Integer failNoanswer, Integer failLenguage, Integer failLink, Integer failErr,
			Integer failNobody, Integer failCount, Integer issueElse, Integer issueTell, Integer issueWrite,
			Integer issueCt, Integer issueSign1, Integer issueSign2, Integer issueSaving, Integer issueProfit,
			Integer issuePeriod, Integer issueDuration, Integer issueDuty, Integer issueIntend, Integer issueRecord,
			Integer issueDelivery, Integer issueCount, Integer rejectCount, Integer ctCount, Integer orderCount,
			Integer waitCount, String successRatio, String attachedRatio, String recordRatio, String issuePercent,
			Date SDate, Date EDate) {
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.areaCode = areaCode;
		this.areaName = areaName;
		this.netCode = netCode;
		this.netName = netName;
		this.realCount = realCount;
		this.standCount = standCount;
		this.callIntime = callIntime;
		this.callRatio = callRatio;
		this.failDoor = failDoor;
		this.failLetter = failLetter;
		this.failConfirm = failConfirm;
		this.failElse = failElse;
		this.failReport = failReport;
		this.failComplaint = failComplaint;
		this.failSale = failSale;
		this.failCt = failCt;
		this.failFit = failFit;
		this.failNotdeal = failNotdeal;
		this.failCut = failCut;
		this.failNoanswer = failNoanswer;
		this.failLenguage = failLenguage;
		this.failLink = failLink;
		this.failErr = failErr;
		this.failNobody = failNobody;
		this.failCount = failCount;
		this.issueElse = issueElse;
		this.issueTell = issueTell;
		this.issueWrite = issueWrite;
		this.issueCt = issueCt;
		this.issueSign1 = issueSign1;
		this.issueSign2 = issueSign2;
		this.issueSaving = issueSaving;
		this.issueProfit = issueProfit;
		this.issuePeriod = issuePeriod;
		this.issueDuration = issueDuration;
		this.issueDuty = issueDuty;
		this.issueIntend = issueIntend;
		this.issueRecord = issueRecord;
		this.issueDelivery = issueDelivery;
		this.issueCount = issueCount;
		this.rejectCount = rejectCount;
		this.ctCount = ctCount;
		this.orderCount = orderCount;
		this.waitCount = waitCount;
		this.successRatio = successRatio;
		this.attachedRatio = attachedRatio;
		this.recordRatio = recordRatio;
		this.issuePercent = issuePercent;
		this.SDate = SDate;
		this.EDate = EDate;
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

	@Column(name = "city_code", length = 6)

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(name = "city_name", length = 64)

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "area_code", length = 8)

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "area_name", length = 64)

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "net_code", length = 11)

	public String getNetCode() {
		return this.netCode;
	}

	public void setNetCode(String netCode) {
		this.netCode = netCode;
	}

	@Column(name = "net_name", length = 96)

	public String getNetName() {
		return this.netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	@Column(name = "real_count")

	public Integer getRealCount() {
		return this.realCount;
	}

	public void setRealCount(Integer realCount) {
		this.realCount = realCount;
	}

	@Column(name = "stand_count")

	public Integer getStandCount() {
		return this.standCount;
	}

	public void setStandCount(Integer standCount) {
		this.standCount = standCount;
	}

	@Column(name = "call_intime")

	public Integer getCallIntime() {
		return this.callIntime;
	}

	public void setCallIntime(Integer callIntime) {
		this.callIntime = callIntime;
	}

	@Column(name = "call_ratio", length = 7)

	public String getCallRatio() {
		return this.callRatio;
	}

	public void setCallRatio(String callRatio) {
		this.callRatio = callRatio;
	}

	@Column(name = "fail_door")

	public Integer getFailDoor() {
		return this.failDoor;
	}

	public void setFailDoor(Integer failDoor) {
		this.failDoor = failDoor;
	}

	@Column(name = "fail_letter")

	public Integer getFailLetter() {
		return this.failLetter;
	}

	public void setFailLetter(Integer failLetter) {
		this.failLetter = failLetter;
	}

	@Column(name = "fail_confirm")

	public Integer getFailConfirm() {
		return this.failConfirm;
	}

	public void setFailConfirm(Integer failConfirm) {
		this.failConfirm = failConfirm;
	}

	@Column(name = "fail_else")

	public Integer getFailElse() {
		return this.failElse;
	}

	public void setFailElse(Integer failElse) {
		this.failElse = failElse;
	}

	@Column(name = "fail_report")

	public Integer getFailReport() {
		return this.failReport;
	}

	public void setFailReport(Integer failReport) {
		this.failReport = failReport;
	}

	@Column(name = "fail_complaint")

	public Integer getFailComplaint() {
		return this.failComplaint;
	}

	public void setFailComplaint(Integer failComplaint) {
		this.failComplaint = failComplaint;
	}

	@Column(name = "fail_sale")

	public Integer getFailSale() {
		return this.failSale;
	}

	public void setFailSale(Integer failSale) {
		this.failSale = failSale;
	}

	@Column(name = "fail_ct")

	public Integer getFailCt() {
		return this.failCt;
	}

	public void setFailCt(Integer failCt) {
		this.failCt = failCt;
	}

	@Column(name = "fail_fit")

	public Integer getFailFit() {
		return this.failFit;
	}

	public void setFailFit(Integer failFit) {
		this.failFit = failFit;
	}

	@Column(name = "fail_notdeal")

	public Integer getFailNotdeal() {
		return this.failNotdeal;
	}

	public void setFailNotdeal(Integer failNotdeal) {
		this.failNotdeal = failNotdeal;
	}

	@Column(name = "fail_cut")

	public Integer getFailCut() {
		return this.failCut;
	}

	public void setFailCut(Integer failCut) {
		this.failCut = failCut;
	}

	@Column(name = "fail_noanswer")

	public Integer getFailNoanswer() {
		return this.failNoanswer;
	}

	public void setFailNoanswer(Integer failNoanswer) {
		this.failNoanswer = failNoanswer;
	}

	@Column(name = "fail_lenguage")

	public Integer getFailLenguage() {
		return this.failLenguage;
	}

	public void setFailLenguage(Integer failLenguage) {
		this.failLenguage = failLenguage;
	}

	@Column(name = "fail_link")

	public Integer getFailLink() {
		return this.failLink;
	}

	public void setFailLink(Integer failLink) {
		this.failLink = failLink;
	}

	@Column(name = "fail_err")

	public Integer getFailErr() {
		return this.failErr;
	}

	public void setFailErr(Integer failErr) {
		this.failErr = failErr;
	}

	@Column(name = "fail_nobody")

	public Integer getFailNobody() {
		return this.failNobody;
	}

	public void setFailNobody(Integer failNobody) {
		this.failNobody = failNobody;
	}

	@Column(name = "fail_count")

	public Integer getFailCount() {
		return this.failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	@Column(name = "issue_else")

	public Integer getIssueElse() {
		return this.issueElse;
	}

	public void setIssueElse(Integer issueElse) {
		this.issueElse = issueElse;
	}

	@Column(name = "issue_tell")

	public Integer getIssueTell() {
		return this.issueTell;
	}

	public void setIssueTell(Integer issueTell) {
		this.issueTell = issueTell;
	}

	@Column(name = "issue_write")

	public Integer getIssueWrite() {
		return this.issueWrite;
	}

	public void setIssueWrite(Integer issueWrite) {
		this.issueWrite = issueWrite;
	}

	@Column(name = "issue_ct")

	public Integer getIssueCt() {
		return this.issueCt;
	}

	public void setIssueCt(Integer issueCt) {
		this.issueCt = issueCt;
	}

	@Column(name = "issue_sign1")

	public Integer getIssueSign1() {
		return this.issueSign1;
	}

	public void setIssueSign1(Integer issueSign1) {
		this.issueSign1 = issueSign1;
	}

	@Column(name = "issue_sign2")

	public Integer getIssueSign2() {
		return this.issueSign2;
	}

	public void setIssueSign2(Integer issueSign2) {
		this.issueSign2 = issueSign2;
	}

	@Column(name = "issue_saving")

	public Integer getIssueSaving() {
		return this.issueSaving;
	}

	public void setIssueSaving(Integer issueSaving) {
		this.issueSaving = issueSaving;
	}

	@Column(name = "issue_profit")

	public Integer getIssueProfit() {
		return this.issueProfit;
	}

	public void setIssueProfit(Integer issueProfit) {
		this.issueProfit = issueProfit;
	}

	@Column(name = "issue_period")

	public Integer getIssuePeriod() {
		return this.issuePeriod;
	}

	public void setIssuePeriod(Integer issuePeriod) {
		this.issuePeriod = issuePeriod;
	}

	@Column(name = "issue_duration")

	public Integer getIssueDuration() {
		return this.issueDuration;
	}

	public void setIssueDuration(Integer issueDuration) {
		this.issueDuration = issueDuration;
	}

	@Column(name = "issue_duty")

	public Integer getIssueDuty() {
		return this.issueDuty;
	}

	public void setIssueDuty(Integer issueDuty) {
		this.issueDuty = issueDuty;
	}

	@Column(name = "issue_intend")

	public Integer getIssueIntend() {
		return this.issueIntend;
	}

	public void setIssueIntend(Integer issueIntend) {
		this.issueIntend = issueIntend;
	}

	@Column(name = "issue_record")

	public Integer getIssueRecord() {
		return this.issueRecord;
	}

	public void setIssueRecord(Integer issueRecord) {
		this.issueRecord = issueRecord;
	}

	@Column(name = "issue_delivery")

	public Integer getIssueDelivery() {
		return this.issueDelivery;
	}

	public void setIssueDelivery(Integer issueDelivery) {
		this.issueDelivery = issueDelivery;
	}

	@Column(name = "issue_count")

	public Integer getIssueCount() {
		return this.issueCount;
	}

	public void setIssueCount(Integer issueCount) {
		this.issueCount = issueCount;
	}

	@Column(name = "reject_count")

	public Integer getRejectCount() {
		return this.rejectCount;
	}

	public void setRejectCount(Integer rejectCount) {
		this.rejectCount = rejectCount;
	}

	@Column(name = "ct_count")

	public Integer getCtCount() {
		return this.ctCount;
	}

	public void setCtCount(Integer ctCount) {
		this.ctCount = ctCount;
	}

	@Column(name = "order_count")

	public Integer getOrderCount() {
		return this.orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	@Column(name = "wait_count")

	public Integer getWaitCount() {
		return this.waitCount;
	}

	public void setWaitCount(Integer waitCount) {
		this.waitCount = waitCount;
	}

	@Column(name = "success_ratio", length = 7)

	public String getSuccessRatio() {
		return this.successRatio;
	}

	public void setSuccessRatio(String successRatio) {
		this.successRatio = successRatio;
	}

	@Column(name = "attached_ratio", length = 7)

	public String getAttachedRatio() {
		return this.attachedRatio;
	}

	public void setAttachedRatio(String attachedRatio) {
		this.attachedRatio = attachedRatio;
	}

	@Column(name = "record_ratio", length = 7)

	public String getRecordRatio() {
		return this.recordRatio;
	}

	public void setRecordRatio(String recordRatio) {
		this.recordRatio = recordRatio;
	}

	@Column(name = "issue_percent", length = 7)

	public String getIssuePercent() {
		return this.issuePercent;
	}

	public void setIssuePercent(String issuePercent) {
		this.issuePercent = issuePercent;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "s_date", length = 10)

	public Date getSDate() {
		return this.SDate;
	}

	public void setSDate(Date SDate) {
		this.SDate = SDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "e_date", length = 10)

	public Date getEDate() {
		return this.EDate;
	}

	public void setEDate(Date EDate) {
		this.EDate = EDate;
	}

}