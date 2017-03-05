package com.gdpost.web.entity.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.gdpost.web.entity.Idable;

/**
 * TCsAddr entity. @author MyEclipse Persistence Tools
 */
@Entity
public class TinyCsAddr implements Idable<String> {

	// Fields

	private String id;
	private String mailAddr;

	// Constructors

	/** default constructor */
	public TinyCsAddr() {
	}


	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "mail_addr", length = 8)
	public String getMailAddr() {
		return this.mailAddr;
	}

	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}

}