package com.gdpost.web.entity.member;

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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;

/**
 * TblMemberModule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_module", uniqueConstraints = @UniqueConstraint(columnNames = "sn"))
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.member.TblMemberModule")
public class TblMemberModule implements Comparable<TblMemberModule>, Idable<Long> {

	// Fields

	private Long id;
	private TblMemberModule parent;
	private String className;
	private String description;
	private String name;
	private Integer priority;
	private String sn;
	private String url;
	private List<TblMemberPermission> tblMemberPermissions = new ArrayList<TblMemberPermission>();
	private List<TblMemberModule> children = new ArrayList<TblMemberModule>();

	// Constructors

	/** default constructor */
	public TblMemberModule() {
	}

	/** minimal constructor */
	public TblMemberModule(String name, Integer priority, String sn, String url) {
		this.name = name;
		this.priority = priority;
		this.sn = sn;
		this.url = url;
	}

	/** full constructor */
	public TblMemberModule(TblMemberModule tblMemberModule, String className, String description, String name, Integer priority, String sn, String url,
			List<TblMemberPermission> tblMemberPermissions, List<TblMemberModule> tblMemberModules) {
		this.parent = tblMemberModule;
		this.className = className;
		this.description = description;
		this.name = name;
		this.priority = priority;
		this.sn = sn;
		this.url = url;
		this.tblMemberPermissions = tblMemberPermissions;
		this.children = tblMemberModules;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	public TblMemberModule getParent() {
		return this.parent;
	}

	public void setParent(TblMemberModule tblMemberModule) {
		this.parent = tblMemberModule;
	}

	@Column(name = "class_name", length = 256)
	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "description", length = 256)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "name", nullable = false, length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "priority", nullable = false)
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "sn", unique = true, nullable = false, length = 32)
	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Column(name = "url", nullable = false, length = 256)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tblMemberModule")
	public List<TblMemberPermission> getTblMemberPermissions() {
		return this.tblMemberPermissions;
	}

	public void setTblMemberPermissions(List<TblMemberPermission> tblMemberPermissions) {
		this.tblMemberPermissions = tblMemberPermissions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	public List<TblMemberModule> getChildren() {
		return this.children;
	}

	public void setChildren(List<TblMemberModule> tblMemberModules) {
		this.children = tblMemberModules;
	}
	
	/*
	 * 
	 */
	@Override
	public int compareTo(TblMemberModule m) {
		if (m == null) {
			return -1;
		} else if (m == this) {
			return 0;
		} else if (this.priority < m.getPriority()) {
			return -1;
		} else if (this.priority > m.getPriority()) {
			return 1;
		}

		return 0;	
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}