package com.gdpost.web.entity.member;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;

/**
 * TblMemberMessageAssign entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_message_assign")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.member.TblMemberMessageAssign")
public class TblMemberMessageAssign implements Idable<Long> {

	// Fields

	private Long id;
	private TblMemberMessageCategory tblMemberMessageCategory;
	private Long userId;
	private Long orgId;
	private TblMemberMessage message;
	private Date createDate;
	private Long createdBy;
	private Date modifyDate;
	private Long modifyBy;

	// Constructors

	/** default constructor */
	public TblMemberMessageAssign() {
	}

	/** minimal constructor */
	public TblMemberMessageAssign(Date createDate, Long createdBy) {
		this.createDate = createDate;
		this.createdBy = createdBy;
	}

	/** full constructor */
	public TblMemberMessageAssign(TblMemberMessageCategory tblMemberMessageCategory, Long userId, TblMemberMessage message, Date createDate, Long createdBy,
			Date modifyDate, Long modifyBy) {
		this.tblMemberMessageCategory = tblMemberMessageCategory;
		this.userId = userId;
		this.message = message;
		this.createDate = createDate;
		this.createdBy = createdBy;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
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
	@JoinColumn(name = "category_id")
	public TblMemberMessageCategory getTblMemberMessageCategory() {
		return this.tblMemberMessageCategory;
	}

	public void setTblMemberMessageCategory(TblMemberMessageCategory tblMemberMessageCategory) {
		this.tblMemberMessageCategory = tblMemberMessageCategory;
	}

	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Column(name = "org_id")
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_id")
	public TblMemberMessage getMessage() {
		return message;
	}

	public void setMessage(TblMemberMessage message) {
		this.message = message;
	}

	@Column(name = "create_date", nullable = false, length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "created_by", nullable = false)
	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "modify_date", length = 19)
	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Column(name = "modify_by")
	public Long getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(Long modifyBy) {
		this.modifyBy = modifyBy;
	}

}