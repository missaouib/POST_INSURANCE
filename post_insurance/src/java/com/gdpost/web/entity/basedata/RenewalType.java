package com.gdpost.web.entity.basedata;

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
 * TRenewalType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_renewal_type")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.RenewalType")
public class RenewalType implements Idable<Long> {

	public static final int HQ_TYPE = 1;
	public static final int ORG_TYPE = 2;
	// Fields

	private Long id;
	private String typeName;
	private String typeDesc;
	private Integer typeFlag;

	// Constructors

	/** default constructor */
	public RenewalType() {
	}

	/** full constructor */
	public RenewalType(String typeName, String typeDesc) {
		this.typeName = typeName;
		this.typeDesc = typeDesc;
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

	@Column(name = "type_name", length = 32)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "type_desc")
	public String getTypeDesc() {
		return this.typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	@Column(name = "type_flag")
	public Integer getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(Integer typeFlag) {
		this.typeFlag = typeFlag;
	}
}