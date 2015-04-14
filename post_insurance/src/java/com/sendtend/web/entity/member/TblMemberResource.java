package com.sendtend.web.entity.member;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

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
import com.sendtend.web.entity.component.Resource;

/**
 * TblMemberResource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_resource")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.sendtend.web.entity.member.TblMemberResource")
public class TblMemberResource implements Idable<Long> {

	// Fields

	private Long id;
	private TblMember tblMember;
	private Resource resource;
	private Integer status;
	private Timestamp readDate;
	private Timestamp createDate;
	private Long createdBy;

	// Constructors

	/** default constructor */
	public TblMemberResource() {
	}

	/** minimal constructor */
	public TblMemberResource(TblMember tblMember) {
		this.tblMember = tblMember;
	}

	/** full constructor */
	public TblMemberResource(TblMember tblMember, Resource resource, Integer status, Timestamp readDate, Timestamp createDate, Long createdBy) {
		this.tblMember = tblMember;
		this.resource = resource;
		this.status = status;
		this.readDate = readDate;
		this.createDate = createDate;
		this.createdBy = createdBy;
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
	@JoinColumn(name = "member_id", nullable = true)
	public TblMember getTblMember() {
		return this.tblMember;
	}

	public void setTblMember(TblMember tblMember) {
		this.tblMember = tblMember;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "read_date", length = 19)
	public Timestamp getReadDate() {
		return this.readDate;
	}

	public void setReadDate(Timestamp readDate) {
		this.readDate = readDate;
	}

	@Column(name = "create_date", length = 19)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "created_by")
	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_id", nullable = true)
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

}