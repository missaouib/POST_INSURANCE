package com.gdpost.web.entity.basedata;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.insurance.Issue;

/**
 * TIssueType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_issue_type")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.basedata.IssueType")
public class IssueType implements Idable<Long> {

	// Fields

	private Long id;
	private String typeCode;
	private String typeName;
	private String typeDesc;
	private List<Issue> issues = new ArrayList<Issue>(0);

	// Constructors

	/** default constructor */
	public IssueType() {
	}

	/** full constructor */
	public IssueType(String typeName, String typeDesc, List<Issue> TIssues) {
		this.typeName = typeName;
		this.typeDesc = typeDesc;
		this.issues = TIssues;
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

	@Column(name="type_code")
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	@Column(name = "type_name", length = 32)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "type_desc")
	public String getTypeDesc() {
		return this.typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	@OneToMany(mappedBy="issueType", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

}