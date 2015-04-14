/**
 * 
 */
package com.sendtend.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sendtend.web.entity.main.RolePermissionDataControl;

public interface RolePermissionDataControlDAO extends JpaRepository<RolePermissionDataControl, Long>, JpaSpecificationExecutor<RolePermissionDataControl> {
	List<RolePermissionDataControl> findByRolePermissionRoleId(Long id);
}