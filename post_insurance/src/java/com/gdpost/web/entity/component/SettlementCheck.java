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

import com.gdpost.web.entity.Idable;

/**
 * SettlementCheck entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_settlement_check")
public class SettlementCheck implements Idable<Long> {

	// Fields

	private Long id;
	private Settlement settlement;
	private Date checkDate;
	private String checker;
	private Date checkDoneDate;
	private String checkDoneMan;

	// Constructors

	/** default constructor */
	public SettlementCheck() {
	}

	/** minimal constructor */
	public SettlementCheck(Long id) {
		this.id = id;
	}

	/** full constructor */
	public SettlementCheck(Long id, Settlement settlement, Date checkDate,
			String checker, Date checkDoneDate, String checkDoneMan) {
		this.id = id;
		this.settlement = settlement;
		this.checkDate = checkDate;
		this.checker = checker;
		this.checkDoneDate = checkDoneDate;
		this.checkDoneMan = checkDoneMan;
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
	@JoinColumn(name = "settlement_id")
	public Settlement getSettlement() {
		return this.settlement;
	}

	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}

	@Column(name = "check_date", length = 10)
	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name = "checker", length = 32)
	public String getChecker() {
		return this.checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	@Column(name = "check_done_date", length = 10)
	public Date getCheckDoneDate() {
		return this.checkDoneDate;
	}

	public void setCheckDoneDate(Date checkDoneDate) {
		this.checkDoneDate = checkDoneDate;
	}

	@Column(name = "check_done_man", length = 32)
	public String getCheckDoneMan() {
		return this.checkDoneMan;
	}

	public void setCheckDoneMan(String checkDoneMan) {
		this.checkDoneMan = checkDoneMan;
	}

}