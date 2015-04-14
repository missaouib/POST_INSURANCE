/**
 * 
 */
package com.sendtend.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sendtend.web.entity.main.UserRole;

public interface UserRoleDAO extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole> {
	List<UserRole> findByUserId(Long userId);
}