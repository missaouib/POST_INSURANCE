package com.gdpost.web.entity.component;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gdpost.web.entity.Idable;

/**
 * TNotice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_notice")
public class Notice implements Idable<Long> {

	// Fields

	private Long id;
	private Long sender;
	private Date sendDate;
	private Long receiver;
	private Long receiveOrg;
	private Long receiveRole;
	private Date invalidDate;
	private String noticeTitle;
	private String noticeContent;
	private List<NoticeAtt> noticeAtts = new ArrayList<NoticeAtt>(0);

	// Constructors

	/** default constructor */
	public Notice() {
	}

	/** full constructor */
	public Notice(Long sender, Timestamp sendDate, Long receiver, Long receiveOrg, Long receiveRole, Date invalidDate, String noticeTitle,
			String noticeContent, List<NoticeAtt> TNoticeAtts) {
		this.sender = sender;
		this.sendDate = sendDate;
		this.receiver = receiver;
		this.receiveOrg = receiveOrg;
		this.receiveRole = receiveRole;
		this.invalidDate = invalidDate;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeAtts = TNoticeAtts;
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

	@Column(name = "sender")
	public Long getSender() {
		return this.sender;
	}

	public void setSender(Long sender) {
		this.sender = sender;
	}

	@Column(name = "send_date", length = 19)
	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Column(name = "receiver")
	public Long getReceiver() {
		return this.receiver;
	}

	public void setReceiver(Long receiver) {
		this.receiver = receiver;
	}

	@Column(name = "receive_org")
	public Long getReceiveOrg() {
		return this.receiveOrg;
	}

	public void setReceiveOrg(Long receiveOrg) {
		this.receiveOrg = receiveOrg;
	}

	@Column(name = "receive_role")
	public Long getReceiveRole() {
		return this.receiveRole;
	}

	public void setReceiveRole(Long receiveRole) {
		this.receiveRole = receiveRole;
	}

	@Column(name = "invalid_date", length = 10)
	public Date getInvalidDate() {
		return this.invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	@Column(name = "notice_title", length = 64)
	public String getNoticeTitle() {
		return this.noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	@Column(name = "notice_content", length = 65535)
	public String getNoticeContent() {
		return this.noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TNotice")
	public List<NoticeAtt> getNoticeAtts() {
		return this.noticeAtts;
	}

	public void setNoticeAtts(List<NoticeAtt> noticeAtts) {
		this.noticeAtts = noticeAtts;
	}

}