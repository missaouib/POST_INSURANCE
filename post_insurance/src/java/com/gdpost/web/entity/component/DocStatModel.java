package com.gdpost.web.entity.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * VCallFailList entity. @author MyEclipse Persistence Tools
 */
@Entity
public class DocStatModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6953817886114363056L;
	private String orgName;
	private Integer sumDoc;
	
	@Transient
	private Integer allDoc;
	@Transient
	private String percent;
	@Transient
	private String remark;
	@Transient
	private Integer month;

	/** default constructor */
	public DocStatModel() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="org_name")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String organName) {
		this.orgName = organName;
	}

	@Column(name="sum_doc")
	public Integer getSumDoc() {
		return sumDoc;
	}

	public void setSumDoc(Integer sumDoc) {
		this.sumDoc = sumDoc;
	}

	@Transient
	public String getRemark() {
		return remark;
	}

	@Transient
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public String getPercent() {
		return percent;
	}

	@Transient
	public void setPercent(String percent) {
		this.percent = percent;
	}

	@Transient
	public Integer getAllDoc() {
		return allDoc;
	}

	@Transient
	public void setAllDoc(Integer allDoc) {
		this.allDoc = allDoc;
	}

	@Transient
	public Integer getMonth() {
		return month;
	}

	@Transient
	public void setMonth(Integer month) {
		this.month = month;
	}

}
