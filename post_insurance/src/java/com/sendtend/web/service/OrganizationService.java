/**
 * 
 */
package com.sendtend.web.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.main.Organization;
import com.sendtend.web.util.dwz.Page;

public interface OrganizationService {
	Organization get(Long id);

	void saveOrUpdate(Organization organization);

	void delete(Long id);
	
	List<Organization> findAll(Page page);
	
	List<Organization> findByExample(Specification<Organization> specification, Page page);

	Organization getByName(String name);
	
	Organization getTree();
}
