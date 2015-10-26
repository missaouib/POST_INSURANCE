package com.gdpost.web.entity.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;

/**
 * TComponentResource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_component_resource", uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.ComponentResource")
public class ComponentResource implements Idable<Long> {

	// Fields

	private Long id;
	private String file;
	private String name;
	private String size;
	private String storeType;
	private Date uploadTime;
	private String uuid;

	// Constructors

	/** default constructor */
	public ComponentResource() {
	}

	/** full constructor */
	public ComponentResource(String file, String name, String size, String storeType, Date uploadTime, String uuid) {
		this.file = file;
		this.name = name;
		this.size = size;
		this.storeType = storeType;
		this.uploadTime = uploadTime;
		this.uuid = uuid;
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

	@Column(name = "file")
	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Column(name = "name", length = 128)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "size", length = 32)
	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Column(name = "store_type", length = 16)
	public String getStoreType() {
		return this.storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	@Column(name = "upload_time", length = 19)
	public Date getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	@Column(name = "uuid", unique = true, length = 32)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}