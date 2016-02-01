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
 * SettlementReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_settlement_report")
public class SettlementReport implements Idable<Long> {

	// Fields

	private Long id;
	private Settlement settlement;
	private Date firstCaseTime;
	private String caseMan;
	private Date firstFileDate;
	private String firstSignMan;
	private Date allFileDate;
	private String allSignMan;

	// Constructors

	/** default constructor */
	public SettlementReport() {
	}

	/** minimal constructor */
	public SettlementReport(Long id) {
		this.id = id;
	}

	/** full constructor */
	public SettlementReport(Long id, Settlement settlement,
			Date firstCaseTime, String caseMan, Date firstFileDate,
			String firstSignMan, Date allFileDate, String allSignMan) {
		this.id = id;
		this.settlement = settlement;
		this.firstCaseTime = firstCaseTime;
		this.caseMan = caseMan;
		this.firstFileDate = firstFileDate;
		this.firstSignMan = firstSignMan;
		this.allFileDate = allFileDate;
		this.allSignMan = allSignMan;
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

	@Column(name = "first_case_time", length = 10)
	public Date getFirstCaseTime() {
		return this.firstCaseTime;
	}

	public void setFirstCaseTime(Date firstCaseTime) {
		this.firstCaseTime = firstCaseTime;
	}

	@Column(name = "case_man", length = 10)
	public String getCaseMan() {
		return this.caseMan;
	}

	public void setCaseMan(String caseMan) {
		this.caseMan = caseMan;
	}

	@Column(name = "first_file_date", length = 10)
	public Date getFirstFileDate() {
		return this.firstFileDate;
	}

	public void setFirstFileDate(Date firstFileDate) {
		this.firstFileDate = firstFileDate;
	}

	@Column(name = "first_sign_man", length = 32)
	public String getFirstSignMan() {
		return this.firstSignMan;
	}

	public void setFirstSignMan(String firstSignMan) {
		this.firstSignMan = firstSignMan;
	}

	@Column(name = "all_file_date", length = 10)
	public Date getAllFileDate() {
		return this.allFileDate;
	}

	public void setAllFileDate(Date allFileDate) {
		this.allFileDate = allFileDate;
	}

	@Column(name = "all_sign_man", length = 32)
	public String getAllSignMan() {
		return this.allSignMan;
	}

	public void setAllSignMan(String allSignMan) {
		this.allSignMan = allSignMan;
	}

}