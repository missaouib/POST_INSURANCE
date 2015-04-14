/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.sendtend.web.service.impl.ServiceUtil.java
 * Class:			ServiceUtil
 * Date:			2012-9-14
 * Author:			sendtend
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.sendtend.web.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.sendtend.web.service.CacheService;

/** 
 * 	
 * @author 	sendtend
 * Version  1.1.0
 * @since   2012-9-14 上午9:59:55 
 */
@Service
public class CacheServiceImpl implements CacheService {
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * @see com.sendtend.web.service.CacheService#clearAllCache()
	 */
	public void clearAllCache() {
		em.getEntityManagerFactory().getCache().evictAll();
	}

}
