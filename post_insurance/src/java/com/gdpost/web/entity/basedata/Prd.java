package com.gdpost.web.entity.basedata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;

/**
 * TPrd entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_prd")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.basedata.Prd")
public class Prd implements Idable<Long>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6181491653423772664L;
	// Fields

	private Long id;
	private String prdCode;
	private String prdName;
	private String prdFullName;
	private Integer prdStatus;
	private Integer prdPerMoney;
	private Integer prdPerm;
	private Integer duration;
	private Integer multiple;
	//private List<Policy> policies = new ArrayList<Policy>(0);

	// Constructors

	/** default constructor */
	public Prd() {
	}

	/** full constructor */
	public Prd(String prdCode, String prdName, Integer prdStatus, Integer prdPerMoney, Integer prdPerm, Integer duration) {
		this.prdCode = prdCode;
		this.prdName = prdName;
		this.prdStatus = prdStatus;
		this.prdPerMoney = prdPerMoney;
		this.prdPerm = prdPerm;
		this.duration = duration;
		//this.policies = TPolicies;
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

	@Column(name = "prd_code", length = 8)
	public String getPrdCode() {
		return this.prdCode;
	}

	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}

	@Column(name = "prd_name", length = 128)
	public String getPrdName() {
		return this.prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}
	
	@Column(name = "prd_full_name", length = 128)
	public String getPrdFullName() {
		return this.prdFullName;
	}

	public void setPrdFullName(String prdFullName) {
		this.prdFullName = prdFullName;
	}

	@Column(name = "prd_status")
	public Integer getPrdStatus() {
		return this.prdStatus;
	}

	public void setPrdStatus(Integer prdStatus) {
		this.prdStatus = prdStatus;
	}

	@Column(name = "prd_per_money")
	public Integer getPrdPerMoney() {
		return this.prdPerMoney;
	}

	public void setPrdPerMoney(Integer prdPerMoney) {
		this.prdPerMoney = prdPerMoney;
	}

	@Column(name = "prd_perm")
	public Integer getPrdPerm() {
		return this.prdPerm;
	}

	public void setPrdPerm(Integer prdPerm) {
		this.prdPerm = prdPerm;
	}

	@Column(name = "duration")
	public Integer getDuration() {
		return this.duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Column(name = "multiple")
	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

//	@OneToMany(mappedBy="prd", cascade={CascadeType.REMOVE}, orphanRemoval=true)
//	public List<Policy> getPolicies() {
//		return policies;
//	}
//
//	public void setPolicies(List<Policy> policies) {
//		this.policies = policies;
//	}

}