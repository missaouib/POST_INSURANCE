package com.sendtend.web.entity.member;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sendtend.web.entity.Idable;

/**
 * TblMemberMessageCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_message_category")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.sendtend.web.entity.member.TblMemberMessageCategory")
public class TblMemberMessageCategory implements Idable<Long> {

	// Fields

	private Long id;
	private String categoryName;
	private String description;
	private Integer status;
	private Date createDate;
	private Long createdBy;
	private Date modifyDate;
	private Long modifyBy;
	private List<TblMemberMessageAssign> tblMemberMessageAssigns = new ArrayList<TblMemberMessageAssign>(0);
	private List<TblMemberMessage> tblMemberMessages = new ArrayList<TblMemberMessage>(0);
	private TblCategoryOrg tblCategoryOrg;

	// Constructors

	/** default constructor */
	public TblMemberMessageCategory() {
	}

	/** minimal constructor */
	public TblMemberMessageCategory(String categoryName, Integer status, Date createDate, Long createdBy) {
		this.categoryName = categoryName;
		this.status = status;
		this.createDate = createDate;
		this.createdBy = createdBy;
	}

	/** full constructor */
	public TblMemberMessageCategory(String categoryName, String description, Integer status, Date createDate, Long createdBy, Date modifyDate,
			Long modifyBy, List<TblMemberMessageAssign> tblMemberMessageAssigns, List<TblMemberMessage> tblMemberMessages) {
		this.categoryName = categoryName;
		this.description = description;
		this.status = status;
		this.createDate = createDate;
		this.createdBy = createdBy;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
		this.tblMemberMessageAssigns = tblMemberMessageAssigns;
		this.tblMemberMessages = tblMemberMessages;
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

	@Column(name = "category_name", nullable = false, length = 100)
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tblMemberMessageCategory")
	public List<TblMemberMessageAssign> getTblMemberMessageAssigns() {
		return this.tblMemberMessageAssigns;
	}

	public void setTblMemberMessageAssigns(List<TblMemberMessageAssign> tblMemberMessageAssigns) {
		this.tblMemberMessageAssigns = tblMemberMessageAssigns;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tblMemberMessageCategory")
	public List<TblMemberMessage> getTblMemberMessages() {
		return this.tblMemberMessages;
	}

	public void setTblMemberMessages(List<TblMemberMessage> tblMemberMessages) {
		this.tblMemberMessages = tblMemberMessages;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id")
	public TblCategoryOrg getTblCategoryOrg() {
		return tblCategoryOrg;
	}

	public void setTblCategoryOrg(TblCategoryOrg tblCategoryOrg) {
		this.tblCategoryOrg = tblCategoryOrg;
	}

}