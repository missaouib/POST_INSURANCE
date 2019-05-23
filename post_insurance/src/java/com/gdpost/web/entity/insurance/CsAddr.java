package com.gdpost.web.entity.insurance;

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
 * TCsAddr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cs_addr", catalog = "postinsurance")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.component.CsAddr")
public class CsAddr implements Idable<Long> {

	// Fields

	private Long id;
	private String organCode;
	private String prov;
	private String city;
	private String linker;
	private String phone;
	private String addr;
	private String postCode;

	// Constructors

	/** default constructor */
	public CsAddr() {
	}

	/** full constructor */
	public CsAddr(String organCode, String city, String linker, String phone, String addr, String postCode) {
		this.organCode = organCode;
		this.city = city;
		this.linker = linker;
		this.phone = phone;
		this.addr = addr;
		this.postCode = postCode;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
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

	@Column(name = "prov", length = 12)
	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	@Column(name = "city", length = 17)

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "linker", length = 12)

	public String getLinker() {
		return this.linker;
	}

	public void setLinker(String linker) {
		this.linker = linker;
	}

	@Column(name = "phone", length = 24)

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "addr", length = 128)

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name = "post_code", length = 6)

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

}