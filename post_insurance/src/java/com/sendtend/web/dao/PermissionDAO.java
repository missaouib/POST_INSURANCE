/**
 * 
 */
package com.sendtend.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sendtend.web.entity.main.Permission;

public interface PermissionDAO extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

}