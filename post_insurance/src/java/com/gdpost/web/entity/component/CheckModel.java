package com.gdpost.web.entity.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * QyCheckModel entity
 */
@Entity
public class CheckModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6446754569354605959L;
	private String policyNo;
	private String organCode;
	private String organName;
	private String checkBatch;
	private String needFix;
	private String keyInfo;
	private String fixStatus;
	private String fixDesc;
	private String bankName;
	private String holder;
	private String holderMobile;
	private String holderPhone;
	private String checker;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="policy_no")
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	
	@Column(name="organ_code")
	public String getOrganCode() {
		return organCode;
	}
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}
	@Column(name="organ_name")
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	@Column(name="check_batch")
	public String getCheckBatch() {
		return checkBatch;
	}
	public void setCheckBatch(String checkBatch) {
		this.checkBatch = checkBatch;
	}
	@Column(name="need_fix")
	public String getNeedFix() {
		return needFix;
	}
	public void setNeedFix(String needFix) {
		this.needFix = needFix;
	}
	@Column(name="key_info")
	public String getKeyInfo() {
		return keyInfo;
	}
	public void setKeyInfo(String keyInfo) {
		this.keyInfo = keyInfo;
	}
	@Column(name="fix_status")
	public String getFixStatus() {
		return fixStatus;
	}
	public void setFixStatus(String fixStatus) {
		this.fixStatus = fixStatus;
	}
	@Column(name="fix_desc")
	public String getFixDesc() {
		return fixDesc;
	}
	public void setFixDesc(String fixDesc) {
		this.fixDesc = fixDesc;
	}
	@Column(name="bank_name")
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	@Column(name="holder")
	public String getHolder() {
		return holder;
	}
	public void setHolder(String holder) {
		this.holder = holder;
	}
	@Column(name="holder_mobile")
	public String getHolderMobile() {
		return holderMobile;
	}
	public void setHolderMobile(String holderMobile) {
		this.holderMobile = holderMobile;
	}
	@Column(name="holder_phone")
	public String getHolderPhone() {
		return holderPhone;
	}
	public void setHolderPhone(String holderPhone) {
		this.holderPhone = holderPhone;
	}
	@Column(name="checker")
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
}
