/**
 * 
 */
package com.sendtend.web.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.main.Permission;
import com.sendtend.web.util.dwz.Page;

public interface PermissionService {
	Permission get(Long id);

	void saveOrUpdate(Permission permission);

	void delete(Long id);
	
	List<Permission> findAll(Page page);
	
	List<Permission> findByExample(Specification<Permission> specification, Page page);
}
