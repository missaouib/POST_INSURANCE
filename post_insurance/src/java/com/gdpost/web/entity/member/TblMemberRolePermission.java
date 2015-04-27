package com.gdpost.web.entity.member;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;

/**
 * TblMemberRolePermission entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_role_permission")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.member.TblMemberRolePermission")
public class TblMemberRolePermission implements Idable<Long> {

	// Fields

	private Long id;
	private TblMemberPermission tblMemberPermission;
	private TblMemberRole tblMemberRole;
	private List<TblMemberRolePermissionDataControl> tblMemberRolePermissionDataControls = new ArrayList<TblMemberRolePermissionDataControl>();

	// Constructors

	/** default constructor */
	public TblMemberRolePermission() {
	}

	/** full constructor */
	public TblMemberRolePermission(TblMemberPermission tblMemberPermission, TblMemberRole tblMemberRole,
			List<TblMemberRolePermissionDataControl> tblMemberRolePermissionDataControls) {
		this.tblMemberPermission = tblMemberPermission;
		this.tblMemberRole = tblMemberRole;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "permission_id")
	public TblMemberPermission getTblMemberPermission() {
		return this.tblMemberPermission;
	}

	public void setTblMemberPermission(TblMemberPermission tblMemberPermission) {
		this.tblMemberPermission = tblMemberPermission;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	public TblMemberRole getTblMemberRole() {
		return this.tblMemberRole;
	}

	public void setTblMemberRole(TblMemberRole tblMemberRole) {
		this.tblMemberRole = tblMemberRole;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tblMemberRolePermission", orphanRemoval=true)
	public List<TblMemberRolePermissionDataControl> getTblMemberRolePermissionDataControls() {
		return this.tblMemberRolePermissionDataControls;
	}

	public void setTblMemberRolePermissionDataControls(List<TblMemberRolePermissionDataControl> tblMemberRolePermissionDataControls) {
		this.tblMemberRolePermissionDataControls = tblMemberRolePermissionDataControls;
	}

}