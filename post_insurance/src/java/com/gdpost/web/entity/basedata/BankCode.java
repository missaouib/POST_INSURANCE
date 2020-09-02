package com.gdpost.web.entity.basedata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.Organization;

/**
 * TBankCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_bank_code")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.basedata.BankCode")
public class BankCode implements Idable<Long>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7774161525578999403L;
	// Fields

	private Long id;
	private String ybtCode;
	private String cpiCode;
	private String selBankCode;
	private String name;
	private Organization organization;
	private Integer netFlag;
	private Integer status;
	
	private String fullName;
	private String ranks;
	private String attr;
	private String city;
	private String area;
	private String addrAttr;
	private String addr;
	private String oldName;

	// Constructors

	/** default constructor */
	public BankCode() {
	}

	/** full constructor */
	public BankCode(String cpiCode, String ybtCode) {
		this.cpiCode = cpiCode;
		this.ybtCode = ybtCode;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ybt_code", length = 10)
	public String getYbtCode() {
		return ybtCode;
	}

	public void setYbtCode(String ybtCode) {
		this.ybtCode = ybtCode;
	}

	@Column(name = "cpi_code", length = 18)
	public String getCpiCode() {
		return this.cpiCode;
	}

	public void setCpiCode(String cpiCode) {
		this.cpiCode = cpiCode;
	}

	@Column(name = "sel_bank_code", length = 20)
	public String getSelBankCode() {
		return this.selBankCode;
	}

	public void setSelBankCode(String bankCode) {
		this.selBankCode = bankCode;
	}

	@Column(name = "name", length = 60)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name="organ_code", referencedColumnName="org_code")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Column(name = "net_flag")
	public Integer getNetFlag() {
		return netFlag;
	}

	public void setNetFlag(Integer netFlag) {
		this.netFlag = netFlag;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "full_name")
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "ranks")
	public String getRanks() {
		return ranks;
	}

	public void setRanks(String ranks) {
		this.ranks = ranks;
	}

	@Column(name = "attr")
	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	@Column(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "area")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "addr_attr")
	public String getAddrAttr() {
		return addrAttr;
	}

	public void setAddrAttr(String addrAttr) {
		this.addrAttr = addrAttr;
	}

	@Column(name = "addr")
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name = "old_name")
	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

}