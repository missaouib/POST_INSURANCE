package com.gdpost.web.entity.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.User;

/**
 * TSettleTaskLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_settle_task_log")
public class SettleTaskLog implements Idable<Long> {

	// Fields

	private Long id;
	private SettleTask settleTask;
	private User user;
	private Date dealDate;
	private String info;
	private Boolean isKeyInfo;
	private String ip;

	private Integer toDealDay;
	private Date followDate;
	private Boolean isFollow;
	
	// Constructors

	/** default constructor */
	public SettleTaskLog() {
	}

	/** full constructor */
	public SettleTaskLog(SettleTask settleTask, Date dealDate, String info, Boolean isKeyInfo, String ip) {
		this.settleTask = settleTask;
		this.dealDate = dealDate;
		this.info = info;
		this.isKeyInfo = isKeyInfo;
		this.ip = ip;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "settle_task_id")
	public SettleTask getSettleTask() {
		return this.settleTask;
	}

	public void setSettleTask(SettleTask settleTask) {
		this.settleTask = settleTask;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "deal_date", length = 19)
	public Date getDealDate() {
		return this.dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	@Column(name = "info", length = 1024)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "is_key_info")
	public Boolean getIsKeyInfo() {
		return this.isKeyInfo;
	}

	public void setIsKeyInfo(Boolean isKeyInfo) {
		this.isKeyInfo = isKeyInfo;
	}

	@Column(name = "ip", length = 24)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(name = "to_deal_day")
	public Integer getToDealDay() {
		return toDealDay;
	}

	public void setToDealDay(Integer toDealDay) {
		this.toDealDay = toDealDay;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "follow_date")
	public Date getFollowDate() {
		return followDate;
	}

	public void setFollowDate(Date followDate) {
		this.followDate = followDate;
	}

	@Column(name = "is_follow")
	public Boolean getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(Boolean isFollow) {
		this.isFollow = isFollow;
	}

}