/**
 * 
 */
package com.gdpost.web.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.main.OrganizationRole;
import com.gdpost.web.util.dwz.Page;

public interface OrganizationRoleService {
	OrganizationRole get(Long id);

	void saveOrUpdate(OrganizationRole organizationRole);

	void delete(Long id);
	
	List<OrganizationRole> findAll(Page page);
	
	List<OrganizationRole> findByExample(Specification<OrganizationRole> specification, Page page);
	
	/**
	 * 根据organizationId，找到已分配的角色。
	 * 描述
	 * @param organizationId
	 * @return
	 */
	List<OrganizationRole> findByOrganizationId(Long organizationId);
}
