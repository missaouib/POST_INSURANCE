/**
 * 
 */
package com.gdpost.web.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.main.Module;
import com.gdpost.web.util.dwz.Page;

public interface ModuleService {
	Module get(Long id);

	void saveOrUpdate(Module module);

	void delete(Long id);
	
	List<Module> findAll(Page page);
	
	List<Module> findByExample(Specification<Module> specification, Page page);
	
	Module getTree();
	
	List<Module> findAll();
}
