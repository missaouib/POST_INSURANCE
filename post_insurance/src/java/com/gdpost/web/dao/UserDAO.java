/**
 * 
 */
package com.gdpost.web.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.main.User;

public interface UserDAO extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	User getByUsername(String username);

	List<User> findByOrganizationId(Long id);
	
	List<User> findByUserRolesRoleNameAndStatus(String roleName, String status, Pageable page);
	
	List<User> findByRealnameLikeAndUserRolesRoleNameAndStatus(String realname,String roleName, String status, Pageable page);
	
	List<User> findByRealnameLikeAndUserRolesRoleIdAndStatus(String realname,Long roleId, String status, Pageable page);
}