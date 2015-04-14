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
 * TblMemberCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_category")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.sendtend.web.entity.member.TblMemberCategory")
public class TblMemberCategory implements Idable<Long> {

	// Fields

	private Long id;
	private TblMember tblMember;
	private String pl;
	private Timestamp createDate;
	private Integer createdBy;
	//private Set<TblMemberData> tblMemberDatas = new HashSet<TblMemberData>(0);

	// Constructors

	/** default constructor */
	public TblMemberCategory() {
	}

	/** minimal constructor */
	public TblMemberCategory(String pl, Timestamp createDate, Integer createdBy) {
		this.pl = pl;
		this.createDate = createDate;
		this.createdBy = createdBy;
	}

	/** full constructor */
	public TblMemberCategory(TblMember tblMember, String pl, Timestamp createDate, Integer createdBy/*, Set<TblMemberData> tblMemberDatas*/) {
		this.tblMember = tblMember;
		this.pl = pl;
		this.createDate = createDate;
		this.createdBy = createdBy;
		//this.tblMemberDatas = tblMemberDatas;
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

	@Column(name = "pl", nullable = false, length = 100)
	public String getPl() {
		return this.pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
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

	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tblMemberCategory")
	//public Set<TblMemberData> getTblMemberDatas() {
	//	return this.tblMemberDatas;
	//}

	//public void setTblMemberDatas(Set<TblMemberData> tblMemberDatas) {
	//	this.tblMemberDatas = tblMemberDatas;
	//}

}