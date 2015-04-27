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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;

import com.gdpost.web.entity.Idable;

/**
 * TblMemberUserRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_user_role")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.member.TblMemberUserRole")
public class TblMemberUserRole implements Idable<Long> {

	// Fields

	private Long id;
	private Integer priority = 999;
	private TblMemberUser tblMemberUser;
	private TblMemberRole tblMemberRole;

	// Constructors

	/** default constructor */
	public TblMemberUserRole() {
	}

	/** full constructor */
	public TblMemberUserRole(TblMemberUser tblMemberUser, TblMemberRole tblMemberRole) {
		this.tblMemberUser = tblMemberUser;
		this.tblMemberRole = tblMemberRole;
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

	@Range(min=1, max=999)
	@Column(name = "priority",length=3)
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public TblMemberUser getTblMemberUser() {
		return this.tblMemberUser;
	}

	public void setTblMemberUser(TblMemberUser tblMemberUser) {
		this.tblMemberUser = tblMemberUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	public TblMemberRole getTblMemberRole() {
		return this.tblMemberRole;
	}

	public void setTblMemberRole(TblMemberRole tblMemberRole) {
		this.tblMemberRole = tblMemberRole;
	}

}