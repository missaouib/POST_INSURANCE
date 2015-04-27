/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.service.component.ResourceService.java
 * Class:			ResourceService
 * Date:			2013-6-28
 * Author:			sendtend
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.gdpost.web.service.component;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.component.Resource;
import com.gdpost.web.util.dwz.Page;

/** 
 * 	
 * @author 	sendtend
 * Version  3.1.0
 * @since   2013-6-28 上午10:19:01 
 */

public interface ResourceService {
	Resource get(Long id);

	void saveOrUpdate(Resource resource);

	void delete(Long id);
	
	void delete(Resource resource);
	
	List<Resource> findAll(Page page);
	
	List<Resource> findByExample(Specification<Resource> specification, Page page);
	
	Resource getByUuid(String uuid);
}
