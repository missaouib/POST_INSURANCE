/**
 * 
 */
package com.sendtend.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sendtend.web.entity.main.Role;

public interface RoleDAO extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

}