package com.sendtend.web.entity.member;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sendtend.web.entity.Idable;

/**
 * TblMemberRolePermissionDataControl entity. @author MyEclipse Persistence
 * Tools
 */
@Entity
@Table(name = "tbl_member_role_permission_data_control")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.sendtend.web.entity.member.TblMemberRolePermissionDataControl")
public class TblMemberRolePermissionDataControl implements Idable<Long> {

	// Fields

	private Long id;
	private TblMemberDataControl tblMemberDataControl;
	private TblMemberRolePermission tblMemberRolePermission;

	// Constructors

	/** default constructor */
	public TblMemberRolePermissionDataControl() {
	}

	/** full constructor */
	public TblMemberRolePermissionDataControl(TblMemberDataControl tblMemberDataControl, TblMemberRolePermission tblMemberRolePermission) {
		this.tblMemberDataControl = tblMemberDataControl;
		this.tblMemberRolePermission = tblMemberRolePermission;
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
	@JoinColumn(name = "data_control_id")
	public TblMemberDataControl getTblMemberDataControl() {
		return this.tblMemberDataControl;
	}

	public void setTblMemberDataControl(TblMemberDataControl tblMemberDataControl) {
		this.tblMemberDataControl = tblMemberDataControl;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_permission_id")
	public TblMemberRolePermission getTblMemberRolePermission() {
		return this.tblMemberRolePermission;
	}

	public void setTblMemberRolePermission(TblMemberRolePermission tblMemberRolePermission) {
		this.tblMemberRolePermission = tblMemberRolePermission;
	}

}