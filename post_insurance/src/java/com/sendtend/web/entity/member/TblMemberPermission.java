package com.sendtend.web.entity.member;

import static javax.persistence.GenerationType.IDENTITY;

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
 * TblMemberPermission entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_permission")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.sendtend.web.entity.member.TblMemberPermission")
public class TblMemberPermission implements Idable<Long> {

	// 用于菜单显示
	public final static String PERMISSION_SHOW = "show";

	public final static String PERMISSION_CREATE = "save";

	public final static String PERMISSION_READ = "view";

	public final static String PERMISSION_UPDATE = "edit";

	public final static String PERMISSION_DELETE = "delete";
	// Fields

	private Long id;
	private TblMemberModule tblMemberModule;
	private String description;
	private String name;
	private String sn;
	private Set<TblMemberRolePermission> tblMemberRolePermissions = new HashSet<TblMemberRolePermission>(0);

	// Constructors

	/** default constructor */
	public TblMemberPermission() {
	}

	/** minimal constructor */
	public TblMemberPermission(String name, String sn) {
		this.name = name;
		this.sn = sn;
	}

	/** full constructor */
	public TblMemberPermission(TblMemberModule tblMemberModule, String description, String name, String sn,
			Set<TblMemberRolePermission> tblMemberRolePermissions) {
		this.tblMemberModule = tblMemberModule;
		this.description = description;
		this.name = name;
		this.sn = sn;
		this.tblMemberRolePermissions = tblMemberRolePermissions;
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
	@JoinColumn(name = "module_id")
	public TblMemberModule getTblMemberModule() {
		return this.tblMemberModule;
	}

	public void setTblMemberModule(TblMemberModule tblMemberModule) {
		this.tblMemberModule = tblMemberModule;
	}

	@Column(name = "description", length = 256)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "name", nullable = false, length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "sn", nullable = false, length = 16)
	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tblMemberPermission")
	public Set<TblMemberRolePermission> getTblMemberRolePermissions() {
		return this.tblMemberRolePermissions;
	}

	public void setTblMemberRolePermissions(Set<TblMemberRolePermission> tblMemberRolePermissions) {
		this.tblMemberRolePermissions = tblMemberRolePermissions;
	}

}