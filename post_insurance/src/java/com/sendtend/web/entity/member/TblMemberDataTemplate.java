package com.sendtend.web.entity.member;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sendtend.web.entity.Idable;

/**
 * TblMemberDataTemplate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_data_template")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.sendtend.web.entity.member.TblMemberDataTemplate")
public class TblMemberDataTemplate implements Idable<Long> {

	// Fields

	private Long id;
	private TblMember tblMember;
	private String templateName;
	private Integer templateType;
	private Integer status;
	private Timestamp createDate;
	private Integer createdBy;
	private Timestamp modifyDate;
	private Long modifyBy;
	private Set<TblMemberDataTemplateField> tblMemberDataTemplateFields = new HashSet<TblMemberDataTemplateField>(0);
	private String userName = "";
	private String password = "";

	// Constructors

	/** default constructor */
	public TblMemberDataTemplate() {
	}

	/** minimal constructor */
	public TblMemberDataTemplate(String templateName, Integer templateType, Integer status, Timestamp createDate, Integer createdBy, String userName, String password) {
		this.templateName = templateName;
		this.templateType = templateType;
		this.status = status;
		this.createDate = createDate;
		this.createdBy = createdBy;
		this.userName = userName;
		this.password = password;
	}

	/** full constructor */
	public TblMemberDataTemplate(TblMember tblMember, String templateName, Integer templateType, Integer status, Timestamp createDate, Integer createdBy,
			Timestamp modifyDate, Long modifyBy, Set<TblMemberDataTemplateField> tblMemberDataTemplateFields, String userName, String password) {
		this.tblMember = tblMember;
		this.templateName = templateName;
		this.templateType = templateType;
		this.status = status;
		this.createDate = createDate;
		this.createdBy = createdBy;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
		this.tblMemberDataTemplateFields = tblMemberDataTemplateFields;
		this.userName = userName;
		this.password = password;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	public TblMember getTblMember() {
		return this.tblMember;
	}

	public void setTblMember(TblMember tblMember) {
		this.tblMember = tblMember;
	}

	@Column(name = "template_name", nullable = false, length = 100)
	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Column(name = "template_type", nullable = false)
	public Integer getTemplateType() {
		return this.templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "create_date", nullable = false, length = 19)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "created_by", nullable = false)
	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "modify_date", length = 19)
	public Timestamp getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Column(name = "modify_by")
	public Long getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(Long modifyBy) {
		this.modifyBy = modifyBy;
	}

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "tblMemberDataTemplate", orphanRemoval=true)
	public Set<TblMemberDataTemplateField> getTblMemberDataTemplateFields() {
		return this.tblMemberDataTemplateFields;
	}

	public void setTblMemberDataTemplateFields(Set<TblMemberDataTemplateField> tblMemberDataTemplateFields) {
		this.tblMemberDataTemplateFields = tblMemberDataTemplateFields;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}