/**
 * 
 */
package com.gdpost.web.entity.main;

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
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import com.gdpost.web.entity.Idable;

/**
 * @author 
 *
 */
@Entity
@Table(name="t_data_control")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.main.DataControl")
public class DataControl implements Idable<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Length(max=64)
	@Column(length=64, nullable=false, unique=true, updatable=false)
	private String name;
	
	@Length(max=256)
	@Column(length=256)
	private String description;
	
	@Length(max=10240)
	@Column(length=10240)
	private String control;
	
	@OneToMany(mappedBy="dataControl", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	private List<RolePermissionDataControl> rolePermissionDataControls = new ArrayList<RolePermissionDataControl>();
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.entity.Idable#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see com.gdpost.web.entity.Idable#setId(java.io.Serializable)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the control
	 */
	public String getControl() {
		return control;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param control the control to set
	 */
	public void setControl(String control) {
		this.control = control;
	}
	
	/**
	 * @return the rolePermissionDataControls
	 */
	public List<RolePermissionDataControl> getRolePermissionDataControls() {
		return rolePermissionDataControls;
	}

	/**
	 * @param rolePermissionDataControls the rolePermissionDataControls to set
	 */
	public void setRolePermissionDataControls(
			List<RolePermissionDataControl> rolePermissionDataControls) {
		this.rolePermissionDataControls = rolePermissionDataControls;
	}

	@Override
	public String toString() {
		return "DataControl [id=" + id + ", name=" + name + ", description=" + description + ", control=" + control + "]";
	}

}
