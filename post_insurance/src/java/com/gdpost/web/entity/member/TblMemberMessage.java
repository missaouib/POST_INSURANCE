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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.User;

/**
 * TblMemberMessage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_message")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.member.TblMemberMessage")
public class TblMemberMessage implements Idable<Long> {

	// Fields

	private Long id;
	private TblMemberUser tblMemberUser;
	private TblMemberMessageCategory tblMemberMessageCategory;
	private User user;
	private String title;
	private String content;
	private Date createDate;
	private Integer status;
	private Long parentId;

	// Constructors

	/** default constructor */
	public TblMemberMessage() {
	}

	/** minimal constructor */
	public TblMemberMessage(String title, String content, Date createDate) {
		this.title = title;
		this.content = content;
		this.createDate = createDate;
	}

	/** full constructor */
	public TblMemberMessage(TblMemberUser tblMemberUser, TblMemberMessageCategory tblMemberMessageCategory, User user, String title, String content,
			Date createDate, Long parentId) {
		this.tblMemberUser = tblMemberUser;
		this.tblMemberMessageCategory = tblMemberMessageCategory;
		this.user = user;
		this.title = title;
		this.content = content;
		this.createDate = createDate;
		this.parentId = parentId;
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
	@JoinColumn(name = "member_user_id")
	public TblMemberUser getTblMemberUser() {
		return this.tblMemberUser;
	}

	public void setTblMemberUser(TblMemberUser tblMemberUser) {
		this.tblMemberUser = tblMemberUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	public TblMemberMessageCategory getTblMemberMessageCategory() {
		return this.tblMemberMessageCategory;
	}

	public void setTblMemberMessageCategory(TblMemberMessageCategory tblMemberMessageCategory) {
		this.tblMemberMessageCategory = tblMemberMessageCategory;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "title", nullable = false, length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", nullable = false, length = 1000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "create_date", nullable = false, length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "parent_id")
	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Transient
	private Long categoryId;

	@Transient
	public Long getCategoryId() {
		return categoryId;
	}

	@Transient
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "TblMemberMessage [id=" + id + ", title=" + title + ", content=" + content + ", createDate=" + createDate + ", status=" + status + ", parentId=" + parentId
				+ ", categoryId=" + categoryId + "]";
	}
}