package com.sendtend.web.entity.member;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sendtend.web.entity.Idable;

/**
 * TblMemberRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_role", uniqueConstraints = @UniqueConstraint(columnNames = {"name","member_id"}))
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.sendtend.web.entity.member.TblMemberRole")
public class TblMemberRole implements Idable<Long> {

	// Fields

	private Long id;
	private String description;
	private String name;
	private Long memberId;
	private List<TblMemberRolePermission> tblMemberRolePermissions = new ArrayList<TblMemberRolePermission>();
	private List<TblMemberUserRole> tblMemberUserRoles = new ArrayList<TblMemberUserRole>();

	// Constructors

	/** default constructor */
	public TblMemberRole() {
	}

	/** minimal constructor */
	public TblMemberRole(String name) {
		this.name = name;
	}

	/** full constructor */
	public TblMemberRole(String description, String name, List<TblMemberRolePermission> tblMemberRolePermissions, List<TblMemberUserRole> tblMemberUserRoles) {
		this.description = description;
		this.name = name;
		this.tblMemberRolePermissions = tblMemberRolePermissions;
		this.tblMemberUserRoles = tblMemberUserRoles;
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

	@Column(name = "member_id")
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tblMemberRole", orphanRemoval=true)
	public List<TblMemberRolePermission> getTblMemberRolePermissions() {
		return this.tblMemberRolePermissions;
	}

	public void setTblMemberRolePermissions(List<TblMemberRolePermission> tblMemberRolePermissions) {
		this.tblMemberRolePermissions = tblMemberRolePermissions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tblMemberRole", orphanRemoval=true)
	@OrderBy("priority ASC")
	public List<TblMemberUserRole> getTblMemberUserRoles() {
		return this.tblMemberUserRoles;
	}

	public void setTblMemberUserRoles(List<TblMemberUserRole> tblMemberUserRoles) {
		this.tblMemberUserRoles = tblMemberUserRoles;
	}

}