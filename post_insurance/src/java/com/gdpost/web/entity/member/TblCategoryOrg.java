package com.gdpost.web.entity.member;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.Organization;

/**
 * TblCategoryOrg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_category_org", uniqueConstraints = @UniqueConstraint(columnNames = { "org_id", "category_id" }))
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.member.TblCategoryOrg")
public class TblCategoryOrg implements Idable<Long> {

	// Fields

	private Long id;
	private Organization organization;
	private TblMemberMessageCategory tblMemberMessageCategory;

	// Constructors

	/** default constructor */
	public TblCategoryOrg() {
	}

	/** full constructor */
	public TblCategoryOrg(Organization org, TblMemberMessageCategory category) {
		this.organization = org;
		this.tblMemberMessageCategory = category;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "org_id")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	public TblMemberMessageCategory getTblMemberMessageCategory() {
		return tblMemberMessageCategory;
	}

	public void setTblMemberMessageCategory(TblMemberMessageCategory tblMemberMessageCategory) {
		this.tblMemberMessageCategory = tblMemberMessageCategory;
	}
	
	@Transient
	private Long orgId;
	@Transient
	private Long categoryId;

	@Transient
	public Long getOrgId() {
		return orgId;
	}

	@Transient
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Transient
	public Long getCategoryId() {
		return categoryId;
	}

	@Transient
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
}