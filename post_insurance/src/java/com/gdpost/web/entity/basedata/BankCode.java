package com.gdpost.web.entity.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gdpost.web.entity.Idable;

/**
 * TBankCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_bank_code", catalog = "postinsurance")
public class BankCode implements Idable<Long> {

	// Fields

	private Long id;
	private String cpiCode;
	private String bankCode;
	private String name;
	private String bankName;
	private Integer status;

	// Constructors

	/** default constructor */
	public BankCode() {
	}

	/** full constructor */
	public BankCode(String cpiCode, String bankCode, String name, Integer status) {
		this.cpiCode = cpiCode;
		this.bankCode = bankCode;
		this.name = name;
		this.status = status;
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

	@Column(name = "cpi_code", length = 18)
	public String getCpiCode() {
		return this.cpiCode;
	}

	public void setCpiCode(String cpiCode) {
		this.cpiCode = cpiCode;
	}

	@Column(name = "bank_code", length = 20)
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "name", length = 60)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "bank_name")
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}