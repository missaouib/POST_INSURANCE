package com.gdpost.web.entity.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.User;

/**
 * SettlementLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_settlement_log")
public class SettlementLog implements Idable<Long> {

	// Fields

	private Long id;
	private Settlement settlement;
	private User user;
	private Date dealDate;
	private String info;
	private Boolean isKeyInfo;
	private String ip;

	// Constructors

	/** default constructor */
	public SettlementLog() {
	}

	/** minimal constructor */
	public SettlementLog(Long id) {
		this.id = id;
	}

	/** full constructor */
	public SettlementLog(Long id, Settlement settlement, User user,
			Date dealDate, String info, Boolean isKeyInfo, String ip) {
		this.id = id;
		this.settlement = settlement;
		this.user = user;
		this.dealDate = dealDate;
		this.info = info;
		this.isKeyInfo = isKeyInfo;
		this.ip = ip;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "settlement_id")
	public Settlement getSettlement() {
		return this.settlement;
	}

	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
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

}