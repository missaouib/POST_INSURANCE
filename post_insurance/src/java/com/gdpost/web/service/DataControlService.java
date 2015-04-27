/**
 * 
 */
package com.gdpost.web.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.main.DataControl;
import com.gdpost.web.util.dwz.Page;

public interface DataControlService {
	DataControl get(Long id);

	void saveOrUpdate(DataControl dataControl);

	void delete(Long id);
	
	List<DataControl> findAll(Page page);
	
	List<DataControl> findByExample(Specification<DataControl> specification, Page page);
}
