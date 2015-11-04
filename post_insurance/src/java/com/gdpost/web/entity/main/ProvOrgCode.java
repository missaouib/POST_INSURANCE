package com.gdpost.web.entity.main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

import com.gdpost.web.entity.Idable;

/**
 * TProvOrgCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_prov_org_code")
public class ProvOrgCode implements Idable<Long> {

	// Fields

	private Long id;
	private String orgCode;
	private String orgName;

	// Constructors

	/** default constructor */
	public ProvOrgCode() {
	}

	/** full constructor */
	public ProvOrgCode(String orgCode, String orgName) {
		this.orgCode = orgCode;
		this.orgName = orgName;
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

	@Column(name = "org_code", length = 8)
	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "org_name", length = 12)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}