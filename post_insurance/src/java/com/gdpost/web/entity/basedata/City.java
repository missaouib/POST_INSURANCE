package com.gdpost.web.entity.basedata;

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
 * City entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_city")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.member.City")
public class City implements Comparable<City>, Idable<Long> {

	// Fields

	private Long id;
	private String cityCode;
	private String cityName;
	private String provinceCode;

	// Constructors

	/** default constructor */
	public City() {
	}

	/** full constructor */
	public City(String cityCode, String cityName, String provinceCode) {
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.provinceCode = provinceCode;
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

	@Column(name = "city_code", nullable = false, length = 6)
	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(name = "city_name", nullable = false, length = 50)
	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "province_code", nullable = false, length = 6)
	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	@Override
	public int compareTo(City o) {
		// TODO Auto-generated method stub
		return 0;
	}

}