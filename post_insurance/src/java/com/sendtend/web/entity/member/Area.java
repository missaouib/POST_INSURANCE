package com.sendtend.web.entity.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sendtend.web.entity.Idable;

/**
 * Area entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_area")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.sendtend.web.entity.member.Area")
public class Area implements Comparable<Area>, Idable<Long> {

	// Fields

	private Long id;
	private String areaCode;
	private String areaName;
	private String cityCode;

	// Constructors

	/** default constructor */
	public Area() {
	}

	/** full constructor */
	public Area(String areaCode, String areaName, String cityCode) {
		this.areaCode = areaCode;
		this.areaName = areaName;
		this.cityCode = cityCode;
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

	@Column(name = "area_code", nullable = false, length = 6)
	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "area_name", nullable = false, length = 50)
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "city_code", nullable = false, length = 6)
	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Override
	public int compareTo(Area o) {
		// TODO Auto-generated method stub
		return 0;
	}

}