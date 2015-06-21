package com.gdpost.web.entity.main;

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
 * TCategoryOrg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_category_org", uniqueConstraints = @UniqueConstraint(columnNames = { "org_id", "category_id" }))
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.CategoryOrg")
public class CategoryOrg implements Idable<Long> {

	// Fields

	private Long id;
	private Long orgId;
	private Long categoryId;

	// Constructors

	/** default constructor */
	public CategoryOrg() {
	}

	/** full constructor */
	public CategoryOrg(Long orgId, Long categoryId) {
		this.orgId = orgId;
		this.categoryId = categoryId;
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

	@Column(name = "org_id")
	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Column(name = "category_id")
	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

}