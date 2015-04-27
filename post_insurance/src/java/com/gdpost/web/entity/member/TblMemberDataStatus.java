package com.gdpost.web.entity.member;

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

import com.gdpost.web.entity.Idable;

/**
 * TblMemberDataStatus entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_data_status")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.member.TblMemberDataStatus")
public class TblMemberDataStatus implements Idable<Long> {

	// Fields

	private Long id;
	private TblMember tblMember;
	private Integer ny;
	private Integer status;
	private Timestamp createDate;
	private Long operator;
	private String operatorName;
	private Integer operateType;
	private String memo;

	// Constructors

	/** default constructor */
	public TblMemberDataStatus() {
	}

	/** minimal constructor */
	public TblMemberDataStatus(Integer ny, Integer status, Timestamp createDate, Long operator, String operatorName, Integer operateType) {
		this.ny = ny;
		this.status = status;
		this.createDate = createDate;
		this.operator = operator;
		this.operatorName = operatorName;
		this.operateType = operateType;
	}

	/** full constructor */
	public TblMemberDataStatus(TblMember tblMember, Integer ny, Integer status, Timestamp createDate, Long operator, String operatorName, Integer operateType) {
		this.tblMember = tblMember;
		this.ny = ny;
		this.status = status;
		this.createDate = createDate;
		this.operator = operator;
		this.operatorName = operatorName;
		this.operateType = operateType;
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

	@Column(name = "ny", nullable = false)
	public Integer getNy() {
		return this.ny;
	}

	public void setNy(Integer ny) {
		this.ny = ny;
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

	@Column(name = "operator", nullable = false)
	public Long getOperator() {
		return this.operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	@Column(name = "operator_name", nullable = false, length = 20)
	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	@Column(name = "operate_type", nullable = false)
	public Integer getOperateType() {
		return this.operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	@Column(name = "memo", nullable = true, length = 300)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}