/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.entity.main.Organization.java
 * Class:			Organization
 * Date:			2012-8-27
 * Author:			Aming
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.gdpost.web.entity.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.insurance.Policy;

/** 
 * 	
 * @author 	Aming
 * Version  1.1.0
 * @since   2012-8-27 下午3:25:15 
 */
@Entity
@Table(name = "t_organization")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.Organization")
public class Organization implements Comparable<Organization>, Idable<Long>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1629519720659716288L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@NotBlank
	@Length(max=64)
	@Column(name="name", length=64, nullable=false, unique=true)
	private String name;
	
	@Length(max=64)
	@Column(name="short_name", length=64, unique=true)
	private String shortName;
	
	/**
	 * 越小优先级越高
	 */
	@NotNull
	@Range(min=1, max=999)
	@Column(name="priority", length=3, nullable=false)
	private Integer priority = 999;
	
	@Length(max=256)
	@Column(name="description", length=256)
	private String description;

	@ManyToOne
	@JoinColumn(name="parent_id", referencedColumnName="id")
	private Organization parent;
	
	@OneToMany(mappedBy="parent", fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@OrderBy("priority ASC")
	private List<Organization> children = new ArrayList<Organization>();
	
	@OneToMany(mappedBy="organization", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	private List<User> users = new ArrayList<User>();
	
	@OneToMany(mappedBy="organization", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	@OrderBy("priority ASC")
	private List<OrganizationRole> organizationRoles = new ArrayList<OrganizationRole>();
	
	@Column(name = "org_code", nullable = false, length = 12)
	private String orgCode;
	
	@Column(name = "level", nullable = false)
	private Integer level;
	
	@Column(name = "old_name")
	private String oldName;
	
	@OneToMany(mappedBy="organization", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	@OrderBy("policyNo ASC")
	private List<Policy> policies = new ArrayList<Policy>(0);
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**  
	 * 返回 name 的值   
	 * @return name  
	 */
	public String getName() {
		return name;
	}

	/**  
	 * 设置 name 的值  
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**  
	 * 返回 description 的值   
	 * @return description  
	 */
	public String getDescription() {
		return description;
	}

	/**  
	 * 设置 description 的值  
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	/**  
	 * 返回 parent 的值   
	 * @return parent  
	 */
	public Organization getParent() {
		return parent;
	}

	/**  
	 * 设置 parent 的值  
	 * @param parent
	 */
	public void setParent(Organization parent) {
		this.parent = parent;
	}

	/**  
	 * 返回 children 的值   
	 * @return children  
	 */
	public List<Organization> getChildren() {
		return children;
	}

	/**  
	 * 设置 children 的值  
	 * @param children
	 */
	public void setChildren(List<Organization> children) {
		this.children = children;
	}

	/**  
	 * 返回 users 的值   
	 * @return users  
	 */
	public List<User> getUsers() {
		return users;
	}

	/**  
	 * 设置 users 的值  
	 * @param users
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public List<OrganizationRole> getOrganizationRoles() {
		return organizationRoles;
	}

	public void setOrganizationRoles(List<OrganizationRole> organizationRoles) {
		this.organizationRoles = organizationRoles;
	}
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<Policy> getPolicies() {
		return policies;
	}

	public void setPolicies(List<Policy> policies) {
		this.policies = policies;
	}

	/*
	 * 
	 */
	@Override
	public int compareTo(Organization org) {
		if (org == null) {
			return -1;
		} else if (org == this) {
			return 0;
		} else if (this.priority < org.getPriority()) {
			return -1;
		} else if (this.priority > org.getPriority()) {
			return 1;
		}

		return 0;	
	}

	@Override
	public String toString() {
		return "Organization [id=" + id + ", name=" + name + ", priority="
				+ priority + ", description=" + description + ", parent="
				+ parent + ", orgCode="
				+ orgCode + ", level=" + level + "]";
	}
}
