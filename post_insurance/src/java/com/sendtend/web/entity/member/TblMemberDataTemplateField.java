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
 * TblMemberDataTemplateField entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_data_template_field")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.sendtend.web.entity.member.TblMemberDataTemplateField")
public class TblMemberDataTemplateField implements Idable<Long> {

	// Fields

	private Long id;
	private TblMemberDataTemplate tblMemberDataTemplate;
	private String fieldName;
	private Integer field;
	private Integer isUsingColumn = new Integer(0);
	private Integer dataColumn;
	private String mapColumn;
	private String columnName;
	private Integer isUsingMapcolumn = new Integer(0);
	private Integer isUsingFilename = new Integer(0);
	private Integer isUsingSheetname = new Integer(0);
	private Integer isUsingMulticolumn = new Integer(0);
	private Integer isStaticValue = new Integer(0);
	private String staticValue;
	private String multicolumn;
	private Timestamp createDate;
	private Long createdBy;
	private Timestamp modifyDate;
	private Long modifyBy;
	private Set<TblMemberDataTemplateFieldRule> tblMemberDataTemplateFieldRules = new HashSet<TblMemberDataTemplateFieldRule>(0);

	// Constructors

	/** default constructor */
	public TblMemberDataTemplateField() {
	}

	/** minimal constructor */
	public TblMemberDataTemplateField(String fieldName, Integer field, Integer isUsingColumn, Integer isUsingFilename, Integer isUsingSheetname,
			Integer isUsingMulticolumn, Timestamp createDate, Long createdBy) {
		this.fieldName = fieldName;
		this.field = field;
		this.isUsingColumn = isUsingColumn;
		this.isUsingFilename = isUsingFilename;
		this.isUsingSheetname = isUsingSheetname;
		this.isUsingMulticolumn = isUsingMulticolumn;
		this.createDate = createDate;
		this.createdBy = createdBy;
	}

	/** full constructor */
	public TblMemberDataTemplateField(TblMemberDataTemplate tblMemberDataTemplate, String fieldName, Integer field, Integer isUsingColumn, Integer dataColumn,
			String mapColumn, Integer isUsingFilename, Integer isUsingSheetname, Integer isUsingMulticolumn, String multicolumn, Timestamp createDate,
			Long createdBy, Timestamp modifyDate, Long modifyBy, Set<TblMemberDataTemplateFieldRule> tblMemberDataTemplateFieldRules) {
		this.tblMemberDataTemplate = tblMemberDataTemplate;
		this.fieldName = fieldName;
		this.field = field;
		this.isUsingColumn = isUsingColumn;
		this.dataColumn = dataColumn;
		this.mapColumn = mapColumn;
		this.isUsingFilename = isUsingFilename;
		this.isUsingSheetname = isUsingSheetname;
		this.isUsingMulticolumn = isUsingMulticolumn;
		this.multicolumn = multicolumn;
		this.createDate = createDate;
		this.createdBy = createdBy;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
		this.tblMemberDataTemplateFieldRules = tblMemberDataTemplateFieldRules;
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
	@JoinColumn(name = "template_id")
	public TblMemberDataTemplate getTblMemberDataTemplate() {
		return this.tblMemberDataTemplate;
	}

	public void setTblMemberDataTemplate(TblMemberDataTemplate tblMemberDataTemplate) {
		this.tblMemberDataTemplate = tblMemberDataTemplate;
	}

	@Column(name = "field_name", nullable = false, length = 100)
	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Column(name = "field", nullable = false)
	public Integer getField() {
		return this.field;
	}

	public void setField(Integer field) {
		this.field = field;
	}

	@Column(name = "is_using_column", nullable = false)
	public Integer getIsUsingColumn() {
		return this.isUsingColumn;
	}

	public void setIsUsingColumn(Integer isUsingColumn) {
		this.isUsingColumn = isUsingColumn;
	}

	@Column(name = "data_column", length = 100)
	public Integer getDataColumn() {
		return this.dataColumn;
	}

	public void setDataColumn(Integer dataColumn) {
		this.dataColumn = dataColumn;
	}

	@Column(name = "map_column", length = 100)
	public String getMapColumn() {
		return this.mapColumn;
	}

	public void setMapColumn(String mapColumn) {
		this.mapColumn = mapColumn;
	}

	@Column(name = "is_using_filename", nullable = false)
	public Integer getIsUsingFilename() {
		return this.isUsingFilename;
	}

	public void setIsUsingFilename(Integer isUsingFilename) {
		this.isUsingFilename = isUsingFilename;
	}

	@Column(name = "is_using_sheetname", nullable = false)
	public Integer getIsUsingSheetname() {
		return this.isUsingSheetname;
	}

	public void setIsUsingSheetname(Integer isUsingSheetname) {
		this.isUsingSheetname = isUsingSheetname;
	}

	@Column(name = "is_using_multicolumn", nullable = false)
	public Integer getIsUsingMulticolumn() {
		return this.isUsingMulticolumn;
	}

	public void setIsUsingMulticolumn(Integer isUsingMulticolumn) {
		this.isUsingMulticolumn = isUsingMulticolumn;
	}

	@Column(name = "multicolumn", length = 200)
	public String getMulticolumn() {
		return this.multicolumn;
	}

	public void setMulticolumn(String multicolumn) {
		this.multicolumn = multicolumn;
	}

	@Column(name = "create_date", nullable = false, length = 19)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "created_by", nullable = false)
	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
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

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "tblMemberDataTemplateField", orphanRemoval=true)
	public Set<TblMemberDataTemplateFieldRule> getTblMemberDataTemplateFieldRules() {
		return this.tblMemberDataTemplateFieldRules;
	}

	public void setTblMemberDataTemplateFieldRules(Set<TblMemberDataTemplateFieldRule> tblMemberDataTemplateFieldRules) {
		this.tblMemberDataTemplateFieldRules = tblMemberDataTemplateFieldRules;
	}

	public Integer getIsUsingMapcolumn() {
		return isUsingMapcolumn;
	}

	public void setIsUsingMapcolumn(Integer isUsingMapcolumn) {
		this.isUsingMapcolumn = isUsingMapcolumn;
	}

	@Column(name = "is_static_value", nullable = false)
	public Integer getIsStaticValue() {
		return isStaticValue;
	}

	public void setIsStaticValue(Integer isStaticValue) {
		this.isStaticValue = isStaticValue;
	}

	@Column(name = "static_value", nullable = false, length = 100)
	public String getStaticValue() {
		return staticValue;
	}

	public void setStaticValue(String staticValue) {
		this.staticValue = staticValue;
	}

	@Column(name = "column_name", length = 100)
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

}