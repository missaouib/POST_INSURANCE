package com.gdpost.web.entity.main;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
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

/**
 * Inquire entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_inquire")

public class Inquire implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3940144933724062764L;
	private Long id;
	private String inquireNo;
	private String inquireType;
	private String inquireSubtype;
	private String inquireStatus;
	private String inquireDesc;
	private String inquireRemark;
	private String inquireRst;
	private String dealDepart;
	private String dealMan;
	private String taskType;
	private String finishDate;
	private Policy policy;
	private String policyNos;
	private String organName;
	private String netName;
	private String holder;
	private String holderPhone;
	private String holderMobile;
	private String policyDate;
	private String gpolicyNo;
	private String gorganName;
	private String gnetName;
	private String ginsured;
	private String ginsuredPhone;
	private String ginsuredMobile;
	private String gpolicyDate;
	private String client;
	private String clientPhone1;
	private String clientPhone2;
	private String clientSexy;
	private String clientBrd;
	private Integer clientAge;
	private String clientCardType;
	private String clientCardNum;
	private String clientStateless;
	private String clientMz;
	private String clientFm;
	private String clientProv;
	private String clientCity;
	private String clientArea;
	private String clientDcity;
	private String clientPostcode;
	private Date dealTime;
	private String reopenReason;
	//private Long reopenId;
	private User reopenUser;
	private Date reopenDate;
	private String checker;
	private Date checkDate;
	private String checkRst;
	private Long operateId;
	private Date operateTime;

	// Constructors

	/** default constructor */
	public Inquire() {
	}

	/** minimal constructor */
	public Inquire(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	/** full constructor */
	public Inquire(String inquireNo, String inquireType, String inquireSubtype, String inquireStatus,
			String inquireDesc, String inquireRemark, String inquireRst, String dealDepart, String dealMan,
			String taskType, String finishDate, String policyNos, String organName, String netName,
			String holder, String holderPhone, String holderMobile, String policyDate, String gpolicyNo,
			String gorganName, String gnetName, String ginsured, String ginsuredPhone, String ginsuredMobile,
			String gpolicyDate, String client, String clientPhone1, String clientPhone2, String clientSexy,
			String clientBrd, Integer clientAge, String clientCardType, String clientCardNum, String clientStateless,
			String clientMz, String clientFm, String clientProv, String clientCity, String clientArea,
			String clientDcity, String clientPostcode, Date dealTime, String reopenReason,
			Date reopenDate, String checker, Timestamp checkDate, String checkRst, Long operateId,
			Timestamp operateTime) {
		this.inquireNo = inquireNo;
		this.inquireType = inquireType;
		this.inquireSubtype = inquireSubtype;
		this.inquireStatus = inquireStatus;
		this.inquireDesc = inquireDesc;
		this.inquireRemark = inquireRemark;
		this.inquireRst = inquireRst;
		this.dealDepart = dealDepart;
		this.dealMan = dealMan;
		this.taskType = taskType;
		this.finishDate = finishDate;
		this.policyNos = policyNos;
		this.organName = organName;
		this.netName = netName;
		this.holder = holder;
		this.holderPhone = holderPhone;
		this.holderMobile = holderMobile;
		this.policyDate = policyDate;
		this.gpolicyNo = gpolicyNo;
		this.gorganName = gorganName;
		this.gnetName = gnetName;
		this.ginsured = ginsured;
		this.ginsuredPhone = ginsuredPhone;
		this.ginsuredMobile = ginsuredMobile;
		this.gpolicyDate = gpolicyDate;
		this.client = client;
		this.clientPhone1 = clientPhone1;
		this.clientPhone2 = clientPhone2;
		this.clientSexy = clientSexy;
		this.clientBrd = clientBrd;
		this.clientAge = clientAge;
		this.clientCardType = clientCardType;
		this.clientCardNum = clientCardNum;
		this.clientStateless = clientStateless;
		this.clientMz = clientMz;
		this.clientFm = clientFm;
		this.clientProv = clientProv;
		this.clientCity = clientCity;
		this.clientArea = clientArea;
		this.clientDcity = clientDcity;
		this.clientPostcode = clientPostcode;
		this.dealTime = dealTime;
		this.reopenReason = reopenReason;
		this.reopenDate = reopenDate;
		this.checker = checker;
		this.checkDate = checkDate;
		this.checkRst = checkRst;
		this.operateId = operateId;
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

	@Column(name = "inquire_no", length = 21)

	public String getInquireNo() {
		return this.inquireNo;
	}

	public void setInquireNo(String inquireNo) {
		this.inquireNo = inquireNo;
	}

	@Column(name = "inquire_type", length = 12)

	public String getInquireType() {
		return this.inquireType;
	}

	public void setInquireType(String inquireType) {
		this.inquireType = inquireType;
	}

	@Column(name = "inquire_subtype", length = 18)

	public String getInquireSubtype() {
		return this.inquireSubtype;
	}

	public void setInquireSubtype(String inquireSubtype) {
		this.inquireSubtype = inquireSubtype;
	}

	@Column(name = "inquire_status", length = 12)

	public String getInquireStatus() {
		return this.inquireStatus;
	}

	public void setInquireStatus(String inquireStatus) {
		this.inquireStatus = inquireStatus;
	}

	@Column(name = "inquire_desc", length = 256)

	public String getInquireDesc() {
		return this.inquireDesc;
	}

	public void setInquireDesc(String inquireDesc) {
		this.inquireDesc = inquireDesc;
	}

	@Column(name = "inquire_remark", length = 256)

	public String getInquireRemark() {
		return this.inquireRemark;
	}

	public void setInquireRemark(String inquireRemark) {
		this.inquireRemark = inquireRemark;
	}

	@Column(name = "inquire_rst", length = 256)

	public String getInquireRst() {
		return this.inquireRst;
	}

	public void setInquireRst(String inquireRst) {
		this.inquireRst = inquireRst;
	}

	@Column(name = "deal_depart", length = 128)

	public String getDealDepart() {
		return this.dealDepart;
	}

	public void setDealDepart(String dealDepart) {
		this.dealDepart = dealDepart;
	}

	@Column(name = "deal_man", length = 12)

	public String getDealMan() {
		return this.dealMan;
	}

	public void setDealMan(String dealMan) {
		this.dealMan = dealMan;
	}

	@Column(name = "task_type", length = 12)

	public String getTaskType() {
		return this.taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	@Column(name = "finish_date", length = 10)
	public String getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	@ManyToOne(optional=true )
	@JoinColumnsOrFormulas(value={
	@JoinColumnOrFormula(column=@JoinColumn(name ="policy_no", referencedColumnName ="policy_no", insertable =false, updatable = false)),
	@JoinColumnOrFormula(formula=@JoinFormula(value="0", referencedColumnName = "attached_flag"))
	})
	public Policy getPolicy() {
		return this.policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Column(name = "policy_nos", length = 74)
	public String getPolicyNos() {
		return this.policyNos;
	}

	public void setPolicyNos(String policyNos) {
		this.policyNos = policyNos;
	}

	@Column(name = "organ_name")

	public String getOrganName() {
		return this.organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	@Column(name = "net_name", length = 128)

	public String getNetName() {
		return this.netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	@Column(name = "holder", length = 12)

	public String getHolder() {
		return this.holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	@Column(name = "holder_phone", length = 12)

	public String getHolderPhone() {
		return this.holderPhone;
	}

	public void setHolderPhone(String holderPhone) {
		this.holderPhone = holderPhone;
	}

	@Column(name = "holder_mobile", length = 64)

	public String getHolderMobile() {
		return this.holderMobile;
	}

	public void setHolderMobile(String holderMobile) {
		this.holderMobile = holderMobile;
	}

	@Column(name = "policy_date", length = 64)

	public String getPolicyDate() {
		return this.policyDate;
	}

	public void setPolicyDate(String policyDate) {
		this.policyDate = policyDate;
	}

	@Column(name = "gpolicy_no", length = 64)

	public String getGpolicyNo() {
		return this.gpolicyNo;
	}

	public void setGpolicyNo(String gpolicyNo) {
		this.gpolicyNo = gpolicyNo;
	}

	@Column(name = "gorgan_name", length = 64)

	public String getGorganName() {
		return this.gorganName;
	}

	public void setGorganName(String gorganName) {
		this.gorganName = gorganName;
	}

	@Column(name = "gnet_name", length = 64)

	public String getGnetName() {
		return this.gnetName;
	}

	public void setGnetName(String gnetName) {
		this.gnetName = gnetName;
	}

	@Column(name = "ginsured", length = 12)

	public String getGinsured() {
		return this.ginsured;
	}

	public void setGinsured(String ginsured) {
		this.ginsured = ginsured;
	}

	@Column(name = "ginsured_phone", length = 64)

	public String getGinsuredPhone() {
		return this.ginsuredPhone;
	}

	public void setGinsuredPhone(String ginsuredPhone) {
		this.ginsuredPhone = ginsuredPhone;
	}

	@Column(name = "ginsured_mobile", length = 64)

	public String getGinsuredMobile() {
		return this.ginsuredMobile;
	}

	public void setGinsuredMobile(String ginsuredMobile) {
		this.ginsuredMobile = ginsuredMobile;
	}

	@Column(name = "gpolicy_date", length = 64)

	public String getGpolicyDate() {
		return this.gpolicyDate;
	}

	public void setGpolicyDate(String gpolicyDate) {
		this.gpolicyDate = gpolicyDate;
	}

	@Column(name = "client", length = 12)

	public String getClient() {
		return this.client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	@Column(name = "client_phone1", length = 11)

	public String getClientPhone1() {
		return this.clientPhone1;
	}

	public void setClientPhone1(String clientPhone1) {
		this.clientPhone1 = clientPhone1;
	}

	@Column(name = "client_phone2", length = 11)

	public String getClientPhone2() {
		return this.clientPhone2;
	}

	public void setClientPhone2(String clientPhone2) {
		this.clientPhone2 = clientPhone2;
	}

	@Column(name = "client_sexy", length = 3)

	public String getClientSexy() {
		return this.clientSexy;
	}

	public void setClientSexy(String clientSexy) {
		this.clientSexy = clientSexy;
	}

	@Column(name = "client_brd", length = 10)
	public String getClientBrd() {
		return this.clientBrd;
	}

	public void setClientBrd(String clientBrd) {
		this.clientBrd = clientBrd;
	}

	@Column(name = "client_age")

	public Integer getClientAge() {
		return this.clientAge;
	}

	public void setClientAge(Integer clientAge) {
		this.clientAge = clientAge;
	}

	@Column(name = "client_card_type", length = 27)

	public String getClientCardType() {
		return this.clientCardType;
	}

	public void setClientCardType(String clientCardType) {
		this.clientCardType = clientCardType;
	}

	@Column(name = "client_card_num", length = 18)

	public String getClientCardNum() {
		return this.clientCardNum;
	}

	public void setClientCardNum(String clientCardNum) {
		this.clientCardNum = clientCardNum;
	}

	@Column(name = "client_stateless", length = 12)

	public String getClientStateless() {
		return this.clientStateless;
	}

	public void setClientStateless(String clientStateless) {
		this.clientStateless = clientStateless;
	}

	@Column(name = "client_mz", length = 12)

	public String getClientMz() {
		return this.clientMz;
	}

	public void setClientMz(String clientMz) {
		this.clientMz = clientMz;
	}

	@Column(name = "client_fm", length = 6)

	public String getClientFm() {
		return this.clientFm;
	}

	public void setClientFm(String clientFm) {
		this.clientFm = clientFm;
	}

	@Column(name = "client_prov", length = 15)

	public String getClientProv() {
		return this.clientProv;
	}

	public void setClientProv(String clientProv) {
		this.clientProv = clientProv;
	}

	@Column(name = "client_city", length = 32)

	public String getClientCity() {
		return this.clientCity;
	}

	public void setClientCity(String clientCity) {
		this.clientCity = clientCity;
	}

	@Column(name = "client_area", length = 32)

	public String getClientArea() {
		return this.clientArea;
	}

	public void setClientArea(String clientArea) {
		this.clientArea = clientArea;
	}

	@Column(name = "client_dcity", length = 128)

	public String getClientDcity() {
		return this.clientDcity;
	}

	public void setClientDcity(String clientDcity) {
		this.clientDcity = clientDcity;
	}

	@Column(name = "client_postcode", length = 6)

	public String getClientPostcode() {
		return this.clientPostcode;
	}

	public void setClientPostcode(String clientPostcode) {
		this.clientPostcode = clientPostcode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "deal_time", length = 10)

	public Date getDealTime() {
		return this.dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	@Column(name = "reopen_reason", length = 256)

	public String getReopenReason() {
		return this.reopenReason;
	}

	public void setReopenReason(String reopenReason) {
		this.reopenReason = reopenReason;
	}

	/*
	@Column(name = "reopen_id")
	public Long getReopenId() {
		return this.reopenId;
	}

	public void setReopenId(Long reopenId) {
		this.reopenId = reopenId;
	}
	*/
	
	@ManyToOne(optional=true)
	@JoinColumn(name = "reopen_id", referencedColumnName="id")
	public User getReopenUser() {
		return reopenUser;
	}

	public void setReopenUser(User reopenUser) {
		this.reopenUser = reopenUser;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "reopen_date", length = 10)

	public Date getReopenDate() {
		return this.reopenDate;
	}

	public void setReopenDate(Date reopenDate) {
		this.reopenDate = reopenDate;
	}

	@Column(name = "checker", length = 16)

	public String getChecker() {
		return this.checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "check_date", length = 26)
	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name = "check_rst", length = 24)

	public String getCheckRst() {
		return this.checkRst;
	}

	public void setCheckRst(String checkRst) {
		this.checkRst = checkRst;
	}

	@Column(name = "operate_id")

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "operate_time", nullable = false, length = 26)
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

}