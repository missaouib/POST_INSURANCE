/**
 * 
 */
package com.gdpost.web.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.main.Role;
import com.gdpost.web.util.dwz.Page;

public interface RoleService {
	Role get(Long id);

	void saveOrUpdate(Role role);

	void delete(Long id);
	
	List<Role> findByIds(List<Long> ids);
	
	List<Role> findByNameLike(String name);
	
	List<Role> findAll(Page page);
	
	List<Role> findByExample(Specification<Role> specification, Page page);
}
