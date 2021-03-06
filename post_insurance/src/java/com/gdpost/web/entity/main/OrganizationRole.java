/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, 
 * Filename:		com.gdpost.web.entity.main.OrganizationRole.java
 * Class:			OrganizationRole
 * Date:			2013-4-15
 * Author:			Aming
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/
 
package com.gdpost.web.entity.main;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;

import com.gdpost.web.entity.Idable;

/** 
 * 	
 * @author 	Aming
 * Version  2.0.0
 * @since   2013-4-15 下午4:01:34 
 */
@Entity
@Table(name="t_organization_role")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.OrganizationRole")
public class OrganizationRole implements Idable<Long>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1071159246014925345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	/**
	 * 值越小，优先级越高
	 */
	@NotNull
	@Range(min=1, max=999)
	@Column(name="priority", length=3, nullable=false)
	private Integer priority = 999;
	
	@ManyToOne
	@JoinColumn(name="role_id", referencedColumnName="id")
	private Role role;
	
	@ManyToOne
	@JoinColumn(name="organization_id", referencedColumnName="id")
	private Organization organization;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**  
	 * 返回 role 的值   
	 * @return role  
	 */
	public Role getRole() {
		return role;
	}

	/**  
	 * 设置 role 的值  
	 * @param role
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**  
	 * 返回 priority 的值   
	 * @return priority  
	 */
	public Integer getPriority() {
		return priority;
	}

	/**  
	 * 设置 priority 的值  
	 * @param priority
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
}
