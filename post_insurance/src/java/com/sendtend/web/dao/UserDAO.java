/**
 * 
 */
package com.sendtend.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sendtend.web.entity.main.User;

public interface UserDAO extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	User getByUsername(String username);

	List<User> findByOrganizationId(Long id);
}