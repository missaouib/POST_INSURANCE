/**
 * 
 */
package com.gdpost.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.main.Role;

public interface RoleDAO extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
	List<Role> findByIdIn(List<Long> ids);
	
	List<Role> findByNameLike(String name);
}