package com.gdpost.web.entity.component;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gdpost.web.entity.Idable;

/**
 * TNoticeAtt entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_notice_att")
public class NoticeAtt implements Idable<Long> {

	// Fields

	private Long id;
	private Notice notice;
	private String attrLink;

	// Constructors

	/** default constructor */
	public NoticeAtt() {
	}

	/** minimal constructor */
	public NoticeAtt(Long id) {
		this.id = id;
	}

	/** full constructor */
	public NoticeAtt(Long id, Notice notice, String attrLink) {
		this.id = id;
		this.notice = notice;
		this.attrLink = attrLink;
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
	@JoinColumn(name = "notice_id")
	public Notice getNotice() {
		return this.notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	@Column(name = "attr_link", length = 128)
	public String getAttrLink() {
		return this.attrLink;
	}

	public void setAttrLink(String attrLink) {
		this.attrLink = attrLink;
	}

}