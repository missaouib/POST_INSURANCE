/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, 
 * Filename:		com.gdpost.web.entity.main.CustomPermission.java
 * Class:			CustomPermission
 * Date:			2013-4-16
 * Author:			Aming
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/
 
package com.gdpost.web.entity.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import com.gdpost.web.entity.Idable;

/** 
 * 除了CRUD以外的自定义权限 	
 * @author 	Aming
 * Version  2.0.0
 * @since   2013-4-16 下午1:34:54 
 */
@Entity
@Table(name="t_permission")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.Permission")
public class Permission implements Idable<Long>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8090611909950519227L;

	/**
	 * 操作
	 */
	// 用于菜单显示
	public final static String PERMISSION_SHOW = "show";
	
	public final static String PERMISSION_CREATE = "save";
	
	public final static String PERMISSION_READ = "view";
	
	public final static String PERMISSION_UPDATE = "edit";
	
	public final static String PERMISSION_DELETE = "delete";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@NotBlank
	@Length(max=64)
	@Column(name="name", length=64, nullable=false)
	private String name;

	@NotBlank
	@Length(max=16)
	@Column(name="sn", length=16, nullable=false)
	private String sn;
	
	@Length(max=256)
	@Column(name="description", length=256)
	private String description;
	
	@ManyToOne
	@JoinColumn(name="module_id", referencedColumnName="id")
	private Module module;
	
	@OneToMany(mappedBy="permission", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	private List<RolePermission> rolePermissiones = new ArrayList<RolePermission>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**  
	 * 返回 name 的值   
	 * @return name  
	 */
	public String getName() {
		return name;
	}

	/**  
	 * 设置 name 的值  
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**  
	 * 返回 description 的值   
	 * @return description  
	 */
	public String getDescription() {
		return description;
	}

	/**  
	 * 设置 description 的值  
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**  
	 * 返回 module 的值   
	 * @return module  
	 */
	public Module getModule() {
		return module;
	}

	/**  
	 * 设置 module 的值  
	 * @param module
	 */
	public void setModule(Module module) {
		this.module = module;
	}

	/**  
	 * 返回 rolePermissiones 的值   
	 * @return rolePermissiones  
	 */
	public List<RolePermission> getRolePermissiones() {
		return rolePermissiones;
	}

	/**  
	 * 设置 rolePermissiones 的值  
	 * @param rolePermissiones
	 */
	public void setRolePermissiones(List<RolePermission> rolePermissiones) {
		this.rolePermissiones = rolePermissiones;
	}

	/**
	 * @return the sn
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * @param sn the sn to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**   
	 * @param arg0
	 * @return  
	 * @see java.lang.Object#equals(java.lang.Object)  
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	/**   
	 * @return  
	 * @see java.lang.Object#hashCode()  
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
}
