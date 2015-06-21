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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;

/**
 * TCheckWriteDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_check_write_dtl")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.CheckWriteDtl")
public class CheckWriteDtl implements Idable<Long> {

	// Fields

	private Long id;
	private Policy policy;
	private String checker;
	private Date checkDate;
	private Boolean needFix;
	private Boolean scanError;
	private Boolean holderName;
	private Boolean holderBirthday;
	private Boolean holderSexy;
	private Boolean holderCardId;
	private Boolean holderCardValidDate;
	private Boolean holderMarried;
	private Boolean holderHight;
	private Boolean holderWeight;
	private Boolean holderJob;
	private Boolean holderJobCode;
	private Boolean holderNation;
	private Boolean holderHousehold;
	private Boolean holderReceipt;
	private Boolean holderPhone;
	private Boolean holderMobile;
	private Boolean holderAddr;
	private Boolean holderPostcode;
	private Boolean relationship;
	private Boolean insuredName;
	private Boolean insuredBirthday;
	private Boolean insuredSexy;
	private Boolean insuredCardId;
	private Boolean insuredCardValidDate;
	private Boolean insuredHight;
	private Boolean insuredWeight;
	private Boolean insuredJob;
	private Boolean insuredJobCode;
	private Boolean insuredNation;
	private Boolean insuredHousehold;
	private Boolean insuredMarried;
	private Boolean insuredPhone;
	private Boolean insuredMobile;
	private Boolean insuredAddr;
	private Boolean insuredPostcode;
	private Boolean missFile;
	private Boolean prdName;
	private Boolean copies;
	private Boolean payPeriod;
	private Boolean duration;
	private Boolean premiums;
	private Boolean payFrequency;
	private Boolean payMethod;
	private Boolean payName;
	private Boolean payCardId;
	private Boolean bonusFlag;
	private Boolean beneficiary;
	private Boolean disputeType;
	private Boolean notify;
	private Boolean riskSentence;
	private Boolean holderSigned;
	private Boolean insuredSigned;
	private Boolean insureDate;
	private Boolean bankSigned;
	private Boolean returnReceipt;
	private Boolean bankElseInfo;
	private Boolean autoBankAccountTransfer;

	// Constructors

	/** default constructor */
	public CheckWriteDtl() {
	}

	/** full constructor */
	public CheckWriteDtl(Policy TPolicy, String checker, Date checkDate, Boolean needFix, Boolean scanError, Boolean holderName, Boolean holderBirthday,
			Boolean holderSexy, Boolean holderCardId, Boolean holderCardValidDate, Boolean holderMarried, Boolean holderHight, Boolean holderWeight,
			Boolean holderJob, Boolean holderJobCode, Boolean holderNation, Boolean holderHousehold, Boolean holderReceipt, Boolean holderPhone,
			Boolean holderMobile, Boolean holderAddr, Boolean holderPostcode, Boolean relationship, Boolean insuredName, Boolean insuredBirthday,
			Boolean insuredSexy, Boolean insuredCardId, Boolean insuredCardValidDate, Boolean insuredHight, Boolean insuredWeight, Boolean insuredJob,
			Boolean insuredJobCode, Boolean insuredNation, Boolean insuredHousehold, Boolean insuredMarried, Boolean insuredPhone, Boolean insuredMobile,
			Boolean insuredAddr, Boolean insuredPostcode, Boolean missFile, Boolean prdName, Boolean copies, Boolean payPeriod, Boolean duration,
			Boolean premiums, Boolean payFrequency, Boolean payMethod, Boolean payName, Boolean payCardId, Boolean bonusFlag, Boolean beneficiary,
			Boolean disputeType, Boolean notify, Boolean riskSentence, Boolean holderSigned, Boolean insuredSigned, Boolean insureDate, Boolean bankSigned,
			Boolean returnReceipt, Boolean bankElseInfo, Boolean autoBankAccountTransfer) {
		this.policy = TPolicy;
		this.checker = checker;
		this.checkDate = checkDate;
		this.needFix = needFix;
		this.scanError = scanError;
		this.holderName = holderName;
		this.holderBirthday = holderBirthday;
		this.holderSexy = holderSexy;
		this.holderCardId = holderCardId;
		this.holderCardValidDate = holderCardValidDate;
		this.holderMarried = holderMarried;
		this.holderHight = holderHight;
		this.holderWeight = holderWeight;
		this.holderJob = holderJob;
		this.holderJobCode = holderJobCode;
		this.holderNation = holderNation;
		this.holderHousehold = holderHousehold;
		this.holderReceipt = holderReceipt;
		this.holderPhone = holderPhone;
		this.holderMobile = holderMobile;
		this.holderAddr = holderAddr;
		this.holderPostcode = holderPostcode;
		this.relationship = relationship;
		this.insuredName = insuredName;
		this.insuredBirthday = insuredBirthday;
		this.insuredSexy = insuredSexy;
		this.insuredCardId = insuredCardId;
		this.insuredCardValidDate = insuredCardValidDate;
		this.insuredHight = insuredHight;
		this.insuredWeight = insuredWeight;
		this.insuredJob = insuredJob;
		this.insuredJobCode = insuredJobCode;
		this.insuredNation = insuredNation;
		this.insuredHousehold = insuredHousehold;
		this.insuredMarried = insuredMarried;
		this.insuredPhone = insuredPhone;
		this.insuredMobile = insuredMobile;
		this.insuredAddr = insuredAddr;
		this.insuredPostcode = insuredPostcode;
		this.missFile = missFile;
		this.prdName = prdName;
		this.copies = copies;
		this.payPeriod = payPeriod;
		this.duration = duration;
		this.premiums = premiums;
		this.payFrequency = payFrequency;
		this.payMethod = payMethod;
		this.payName = payName;
		this.payCardId = payCardId;
		this.bonusFlag = bonusFlag;
		this.beneficiary = beneficiary;
		this.disputeType = disputeType;
		this.notify = notify;
		this.riskSentence = riskSentence;
		this.holderSigned = holderSigned;
		this.insuredSigned = insuredSigned;
		this.insureDate = insureDate;
		this.bankSigned = bankSigned;
		this.returnReceipt = returnReceipt;
		this.bankElseInfo = bankElseInfo;
		this.autoBankAccountTransfer = autoBankAccountTransfer;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "policy_id")
	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Column(name = "checker", length = 32)
	public String getChecker() {
		return this.checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "check_date", length = 10)
	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name = "need_fix")
	public Boolean getNeedFix() {
		return this.needFix;
	}

	public void setNeedFix(Boolean needFix) {
		this.needFix = needFix;
	}

	@Column(name = "scan_error")
	public Boolean getScanError() {
		return this.scanError;
	}

	public void setScanError(Boolean scanError) {
		this.scanError = scanError;
	}

	@Column(name = "holder_name")
	public Boolean getHolderName() {
		return this.holderName;
	}

	public void setHolderName(Boolean holderName) {
		this.holderName = holderName;
	}

	@Column(name = "holder_birthday")
	public Boolean getHolderBirthday() {
		return this.holderBirthday;
	}

	public void setHolderBirthday(Boolean holderBirthday) {
		this.holderBirthday = holderBirthday;
	}

	@Column(name = "holder_sexy")
	public Boolean getHolderSexy() {
		return this.holderSexy;
	}

	public void setHolderSexy(Boolean holderSexy) {
		this.holderSexy = holderSexy;
	}

	@Column(name = "holder_card_id")
	public Boolean getHolderCardId() {
		return this.holderCardId;
	}

	public void setHolderCardId(Boolean holderCardId) {
		this.holderCardId = holderCardId;
	}

	@Column(name = "holder_card_valid_date")
	public Boolean getHolderCardValidDate() {
		return this.holderCardValidDate;
	}

	public void setHolderCardValidDate(Boolean holderCardValidDate) {
		this.holderCardValidDate = holderCardValidDate;
	}

	@Column(name = "holder_married")
	public Boolean getHolderMarried() {
		return this.holderMarried;
	}

	public void setHolderMarried(Boolean holderMarried) {
		this.holderMarried = holderMarried;
	}

	@Column(name = "holder_hight")
	public Boolean getHolderHight() {
		return this.holderHight;
	}

	public void setHolderHight(Boolean holderHight) {
		this.holderHight = holderHight;
	}

	@Column(name = "holder_weight")
	public Boolean getHolderWeight() {
		return this.holderWeight;
	}

	public void setHolderWeight(Boolean holderWeight) {
		this.holderWeight = holderWeight;
	}

	@Column(name = "holder_job")
	public Boolean getHolderJob() {
		return this.holderJob;
	}

	public void setHolderJob(Boolean holderJob) {
		this.holderJob = holderJob;
	}

	@Column(name = "holder_job_code")
	public Boolean getHolderJobCode() {
		return this.holderJobCode;
	}

	public void setHolderJobCode(Boolean holderJobCode) {
		this.holderJobCode = holderJobCode;
	}

	@Column(name = "holder_nation")
	public Boolean getHolderNation() {
		return this.holderNation;
	}

	public void setHolderNation(Boolean holderNation) {
		this.holderNation = holderNation;
	}

	@Column(name = "holder_household")
	public Boolean getHolderHousehold() {
		return this.holderHousehold;
	}

	public void setHolderHousehold(Boolean holderHousehold) {
		this.holderHousehold = holderHousehold;
	}

	@Column(name = "holder_receipt")
	public Boolean getHolderReceipt() {
		return this.holderReceipt;
	}

	public void setHolderReceipt(Boolean holderReceipt) {
		this.holderReceipt = holderReceipt;
	}

	@Column(name = "holder_phone")
	public Boolean getHolderPhone() {
		return this.holderPhone;
	}

	public void setHolderPhone(Boolean holderPhone) {
		this.holderPhone = holderPhone;
	}

	@Column(name = "holder_mobile")
	public Boolean getHolderMobile() {
		return this.holderMobile;
	}

	public void setHolderMobile(Boolean holderMobile) {
		this.holderMobile = holderMobile;
	}

	@Column(name = "holder_addr")
	public Boolean getHolderAddr() {
		return this.holderAddr;
	}

	public void setHolderAddr(Boolean holderAddr) {
		this.holderAddr = holderAddr;
	}

	@Column(name = "holder_postcode")
	public Boolean getHolderPostcode() {
		return this.holderPostcode;
	}

	public void setHolderPostcode(Boolean holderPostcode) {
		this.holderPostcode = holderPostcode;
	}

	@Column(name = "relationship")
	public Boolean getRelationship() {
		return this.relationship;
	}

	public void setRelationship(Boolean relationship) {
		this.relationship = relationship;
	}

	@Column(name = "insured_name")
	public Boolean getInsuredName() {
		return this.insuredName;
	}

	public void setInsuredName(Boolean insuredName) {
		this.insuredName = insuredName;
	}

	@Column(name = "insured_birthday")
	public Boolean getInsuredBirthday() {
		return this.insuredBirthday;
	}

	public void setInsuredBirthday(Boolean insuredBirthday) {
		this.insuredBirthday = insuredBirthday;
	}

	@Column(name = "insured_sexy")
	public Boolean getInsuredSexy() {
		return this.insuredSexy;
	}

	public void setInsuredSexy(Boolean insuredSexy) {
		this.insuredSexy = insuredSexy;
	}

	@Column(name = "insured_card_id")
	public Boolean getInsuredCardId() {
		return this.insuredCardId;
	}

	public void setInsuredCardId(Boolean insuredCardId) {
		this.insuredCardId = insuredCardId;
	}

	@Column(name = "insured_card_valid_date")
	public Boolean getInsuredCardValidDate() {
		return this.insuredCardValidDate;
	}

	public void setInsuredCardValidDate(Boolean insuredCardValidDate) {
		this.insuredCardValidDate = insuredCardValidDate;
	}

	@Column(name = "insured_hight")
	public Boolean getInsuredHight() {
		return this.insuredHight;
	}

	public void setInsuredHight(Boolean insuredHight) {
		this.insuredHight = insuredHight;
	}

	@Column(name = "insured_weight")
	public Boolean getInsuredWeight() {
		return this.insuredWeight;
	}

	public void setInsuredWeight(Boolean insuredWeight) {
		this.insuredWeight = insuredWeight;
	}

	@Column(name = "insured_job")
	public Boolean getInsuredJob() {
		return this.insuredJob;
	}

	public void setInsuredJob(Boolean insuredJob) {
		this.insuredJob = insuredJob;
	}

	@Column(name = "insured_job_code")
	public Boolean getInsuredJobCode() {
		return this.insuredJobCode;
	}

	public void setInsuredJobCode(Boolean insuredJobCode) {
		this.insuredJobCode = insuredJobCode;
	}

	@Column(name = "insured_nation")
	public Boolean getInsuredNation() {
		return this.insuredNation;
	}

	public void setInsuredNation(Boolean insuredNation) {
		this.insuredNation = insuredNation;
	}

	@Column(name = "insured_household")
	public Boolean getInsuredHousehold() {
		return this.insuredHousehold;
	}

	public void setInsuredHousehold(Boolean insuredHousehold) {
		this.insuredHousehold = insuredHousehold;
	}

	@Column(name = "insured_married")
	public Boolean getInsuredMarried() {
		return this.insuredMarried;
	}

	public void setInsuredMarried(Boolean insuredMarried) {
		this.insuredMarried = insuredMarried;
	}

	@Column(name = "insured_phone")
	public Boolean getInsuredPhone() {
		return this.insuredPhone;
	}

	public void setInsuredPhone(Boolean insuredPhone) {
		this.insuredPhone = insuredPhone;
	}

	@Column(name = "insured_mobile")
	public Boolean getInsuredMobile() {
		return this.insuredMobile;
	}

	public void setInsuredMobile(Boolean insuredMobile) {
		this.insuredMobile = insuredMobile;
	}

	@Column(name = "insured_addr")
	public Boolean getInsuredAddr() {
		return this.insuredAddr;
	}

	public void setInsuredAddr(Boolean insuredAddr) {
		this.insuredAddr = insuredAddr;
	}

	@Column(name = "insured_postcode")
	public Boolean getInsuredPostcode() {
		return this.insuredPostcode;
	}

	public void setInsuredPostcode(Boolean insuredPostcode) {
		this.insuredPostcode = insuredPostcode;
	}

	@Column(name = "miss_file")
	public Boolean getMissFile() {
		return this.missFile;
	}

	public void setMissFile(Boolean missFile) {
		this.missFile = missFile;
	}

	@Column(name = "prd_name")
	public Boolean getPrdName() {
		return this.prdName;
	}

	public void setPrdName(Boolean prdName) {
		this.prdName = prdName;
	}

	@Column(name = "copies")
	public Boolean getCopies() {
		return this.copies;
	}

	public void setCopies(Boolean copies) {
		this.copies = copies;
	}

	@Column(name = "pay_period")
	public Boolean getPayPeriod() {
		return this.payPeriod;
	}

	public void setPayPeriod(Boolean payPeriod) {
		this.payPeriod = payPeriod;
	}

	@Column(name = "duration")
	public Boolean getDuration() {
		return this.duration;
	}

	public void setDuration(Boolean duration) {
		this.duration = duration;
	}

	@Column(name = "premiums")
	public Boolean getPremiums() {
		return this.premiums;
	}

	public void setPremiums(Boolean premiums) {
		this.premiums = premiums;
	}

	@Column(name = "pay_frequency")
	public Boolean getPayFrequency() {
		return this.payFrequency;
	}

	public void setPayFrequency(Boolean payFrequency) {
		this.payFrequency = payFrequency;
	}

	@Column(name = "pay_method")
	public Boolean getPayMethod() {
		return this.payMethod;
	}

	public void setPayMethod(Boolean payMethod) {
		this.payMethod = payMethod;
	}

	@Column(name = "pay_name")
	public Boolean getPayName() {
		return this.payName;
	}

	public void setPayName(Boolean payName) {
		this.payName = payName;
	}

	@Column(name = "pay_card_id")
	public Boolean getPayCardId() {
		return this.payCardId;
	}

	public void setPayCardId(Boolean payCardId) {
		this.payCardId = payCardId;
	}

	@Column(name = "bonus_flag")
	public Boolean getBonusFlag() {
		return this.bonusFlag;
	}

	public void setBonusFlag(Boolean bonusFlag) {
		this.bonusFlag = bonusFlag;
	}

	@Column(name = "beneficiary")
	public Boolean getBeneficiary() {
		return this.beneficiary;
	}

	public void setBeneficiary(Boolean beneficiary) {
		this.beneficiary = beneficiary;
	}

	@Column(name = "dispute_type")
	public Boolean getDisputeType() {
		return this.disputeType;
	}

	public void setDisputeType(Boolean disputeType) {
		this.disputeType = disputeType;
	}

	@Column(name = "notify")
	public Boolean getNotify() {
		return this.notify;
	}

	public void setNotify(Boolean notify) {
		this.notify = notify;
	}

	@Column(name = "risk_sentence")
	public Boolean getRiskSentence() {
		return this.riskSentence;
	}

	public void setRiskSentence(Boolean riskSentence) {
		this.riskSentence = riskSentence;
	}

	@Column(name = "holder_signed")
	public Boolean getHolderSigned() {
		return this.holderSigned;
	}

	public void setHolderSigned(Boolean holderSigned) {
		this.holderSigned = holderSigned;
	}

	@Column(name = "insured_signed")
	public Boolean getInsuredSigned() {
		return this.insuredSigned;
	}

	public void setInsuredSigned(Boolean insuredSigned) {
		this.insuredSigned = insuredSigned;
	}

	@Column(name = "insure_date")
	public Boolean getInsureDate() {
		return this.insureDate;
	}

	public void setInsureDate(Boolean insureDate) {
		this.insureDate = insureDate;
	}

	@Column(name = "bank_signed")
	public Boolean getBankSigned() {
		return this.bankSigned;
	}

	public void setBankSigned(Boolean bankSigned) {
		this.bankSigned = bankSigned;
	}

	@Column(name = "return_receipt")
	public Boolean getReturnReceipt() {
		return this.returnReceipt;
	}

	public void setReturnReceipt(Boolean returnReceipt) {
		this.returnReceipt = returnReceipt;
	}

	@Column(name = "bank_else_info")
	public Boolean getBankElseInfo() {
		return this.bankElseInfo;
	}

	public void setBankElseInfo(Boolean bankElseInfo) {
		this.bankElseInfo = bankElseInfo;
	}

	@Column(name = "auto_bank_account_transfer")
	public Boolean getAutoBankAccountTransfer() {
		return this.autoBankAccountTransfer;
	}

	public void setAutoBankAccountTransfer(Boolean autoBankAccountTransfer) {
		this.autoBankAccountTransfer = autoBankAccountTransfer;
	}

}