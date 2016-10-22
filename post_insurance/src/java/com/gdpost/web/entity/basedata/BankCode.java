package com.gdpost.web.entity.basedata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.Organization;

/**
 * TBankCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_bank_code")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.basedata.BankCode")
public class BankCode implements Idable<Long>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7774161525578999403L;
	// Fields

	private Long id;
	private String ybtCode;
	private String cpiCode;
	private String bankCode;
	private String name;
	private Organization organization;
	private Integer flag;
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

	@Column(name = "ybt_code", length = 10)
	public String getYbtCode() {
		return ybtCode;
	}

	public void setYbtCode(String ybtCode) {
		this.ybtCode = ybtCode;
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

	@ManyToOne
	@JoinColumn(name="organ_code", referencedColumnName="org_code")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}