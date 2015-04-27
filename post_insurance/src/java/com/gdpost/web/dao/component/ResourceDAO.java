/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.dao.component.ResourceDAO.java
 * Class:			ResourceDAO
 * Date:			2013-6-28
 * Author:			sendtend
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.gdpost.web.dao.component;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.component.Resource;

/** 
 * 	
 * @author 	sendtend
 * Version  3.1.0
 * @since   2013-6-28 上午10:18:10 
 */

public interface ResourceDAO extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource>{
	Resource getByUuid(String uuid);
}
