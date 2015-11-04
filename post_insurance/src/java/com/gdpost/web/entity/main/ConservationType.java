package com.gdpost.web.entity.main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

import com.gdpost.web.entity.Idable;

/**
 * TConservationType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_conservation_type")
public class ConservationType implements Idable<Long> {

	// Fields

	private Long id;
	private String csType;
	private String csName;

	// Constructors

	/** default constructor */
	public ConservationType() {
	}

	/** full constructor */
	public ConservationType(String csType, String csName) {
		this.csType = csType;
		this.csName = csName;
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

	@Column(name = "cs_type", length = 4)
	public String getCsType() {
		return this.csType;
	}

	public void setCsType(String csType) {
		this.csType = csType;
	}

	@Column(name = "cs_name", length = 16)
	public String getCsName() {
		return this.csName;
	}

	public void setCsName(String csName) {
		this.csName = csName;
	}

}