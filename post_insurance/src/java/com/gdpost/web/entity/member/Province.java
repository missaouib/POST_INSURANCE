package com.gdpost.web.entity.member;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;

/**
 * Province entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_province")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.member.Province")
public class Province implements Comparable<Province>, Idable<Long> {

	// Fields

	private Long id;
	private String provinceCode;
	private String provinceName;
	//private List<City> children;
	// Constructors

	/** default constructor */
	public Province() {
	}

	/** full constructor */
	public Province(String provinceCode, String provinceName) {
		this.provinceCode = provinceCode;
		this.provinceName = provinceName;
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

	@Column(name = "Province_code", nullable = false, length = 6)
	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	@Column(name = "Province_name", nullable = false, length = 50)
	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
/*
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	public List<City> getChildren() {
		return children;
	}

	public void setChildren(List<City> children) {
		this.children = children;
	}
*/
	@Override
	public int compareTo(Province o) {
		// TODO Auto-generated method stub
		return 0;
	}

}