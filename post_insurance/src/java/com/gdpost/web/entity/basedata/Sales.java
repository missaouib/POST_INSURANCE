package com.gdpost.web.entity.basedata;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;

/**
 * Sales entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_sales")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.basedata.Sales")
public class Sales implements Idable<Long>, Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6743922033100492258L;
	private Long id;
	private String organCode;
	private String organName;
	private String bankName;
	private String salesCode;
	private String salesName;
	private String phone;
	private Integer status;
	private Long operateId;
	private Timestamp operateTime;

	// Constructors

	/** default constructor */
	public Sales() {
	}

	/** full constructor */
	public Sales(String organCode, String organName, String bankName, String salesCode, String salesName, String phone,
			Integer status, Long operateId, Timestamp operateTime) {
		this.organCode = organCode;
		this.organName = organName;
		this.bankName = bankName;
		this.salesCode = salesCode;
		this.salesName = salesName;
		this.phone = phone;
		this.status = status;
		this.operateId = operateId;
		this.operateTime = operateTime;
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

	@Column(name = "organ_code", length = 8)
	public String getOrganCode() {
		return this.organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	@Column(name = "organ_name", length = 64)
	public String getOrganName() {
		return this.organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	@Column(name = "bank_name", length = 64)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "sales_code", length = 11)
	public String getSalesCode() {
		return this.salesCode;
	}

	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}

	@Column(name = "sales_name", length = 18)
	public String getSalesName() {
		return this.salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	@Column(name = "phone", length = 18)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "operate_id")
	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Column(name = "operate_time", length = 19)
	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

}