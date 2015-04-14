package com.sendtend.web.entity.member;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sendtend.web.entity.Idable;

/**
 * TblMemberDataTemplateFieldRule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_data_template_field_rule")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.sendtend.web.entity.member.TblMemberDataTemplateFieldRule")
public class TblMemberDataTemplateFieldRule implements Idable<Long> {

	// Fields

	private Long id;
	private TblMemberDataTemplateField tblMemberDataTemplateField;
	private String ruleName;
	private String splitChar;
	private Integer valueIndex;
	private Timestamp createDate;
	private Integer createdBy;
	private Timestamp modifyDate;
	private Long modifyBy;

	// Constructors

	/** default constructor */
	public TblMemberDataTemplateFieldRule() {
	}

	/** minimal constructor */
	public TblMemberDataTemplateFieldRule(String ruleName, String splitChar, Integer valueIndex, Timestamp createDate, Integer createdBy) {
		this.ruleName = ruleName;
		this.splitChar = splitChar;
		this.valueIndex = valueIndex;
		this.createDate = createDate;
		this.createdBy = createdBy;
	}

	/** full constructor */
	public TblMemberDataTemplateFieldRule(TblMemberDataTemplateField tblMemberDataTemplateField, String ruleName, String splitChar, Integer valueIndex,
			Timestamp createDate, Integer createdBy, Timestamp modifyDate, Long modifyBy) {
		this.tblMemberDataTemplateField = tblMemberDataTemplateField;
		this.ruleName = ruleName;
		this.splitChar = splitChar;
		this.valueIndex = valueIndex;
		this.createDate = createDate;
		this.createdBy = createdBy;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "field_id")
	public TblMemberDataTemplateField getTblMemberDataTemplateField() {
		return this.tblMemberDataTemplateField;
	}

	public void setTblMemberDataTemplateField(TblMemberDataTemplateField tblMemberDataTemplateField) {
		this.tblMemberDataTemplateField = tblMemberDataTemplateField;
	}

	@Column(name = "rule_name", nullable = false, length = 100)
	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	@Column(name = "split_char", nullable = false, length = 20)
	public String getSplitChar() {
		return this.splitChar;
	}

	public void setSplitChar(String splitChar) {
		this.splitChar = splitChar;
	}

	@Column(name = "value_index", nullable = false)
	public Integer getValueIndex() {
		return this.valueIndex;
	}

	public void setValueIndex(Integer valueIndex) {
		this.valueIndex = valueIndex;
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

}