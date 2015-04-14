package com.sendtend.web.entity.member;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sendtend.web.entity.Idable;

/**
 * TblMemberDataControl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_data_control", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.sendtend.web.entity.member.TblMemberDataControl")
public class TblMemberDataControl implements Idable<Long> {

	// Fields

	private Long id;
	private String control;
	private String description;
	private String name;
	private Set<TblMemberRolePermissionDataControl> tblMemberRolePermissionDataControls = new HashSet<TblMemberRolePermissionDataControl>(0);

	// Constructors

	/** default constructor */
	public TblMemberDataControl() {
	}

	/** minimal constructor */
	public TblMemberDataControl(String name) {
		this.name = name;
	}

	/** full constructor */
	public TblMemberDataControl(String control, String description, String name, Set<TblMemberRolePermissionDataControl> tblMemberRolePermissionDataControls) {
		this.control = control;
		this.description = description;
		this.name = name;
		this.tblMemberRolePermissionDataControls = tblMemberRolePermissionDataControls;
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

	@Column(name = "control", length = 10240)
	public String getControl() {
		return this.control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	@Column(name = "description", length = 256)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "name", unique = true, nullable = false, length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tblMemberDataControl")
	public Set<TblMemberRolePermissionDataControl> getTblMemberRolePermissionDataControls() {
		return this.tblMemberRolePermissionDataControls;
	}

	public void setTblMemberRolePermissionDataControls(Set<TblMemberRolePermissionDataControl> tblMemberRolePermissionDataControls) {
		this.tblMemberRolePermissionDataControls = tblMemberRolePermissionDataControls;
	}

}