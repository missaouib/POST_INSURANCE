/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.controller.CacheManageController.java
 * Class:			CacheManageController
 * Date:			2012-9-14
 * Author:			Aming
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.gdpost.web.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.service.CacheService;
import com.gdpost.web.util.dwz.AjaxObject;

/** 
 * 	
 * @author 	Aming
 * Version  1.1.0
 * @since   2012-9-14 上午11:08:15 
 */
@Controller
@RequestMapping("/management/security/cache")
public class CacheController {
	@Autowired
	private CacheService cacheService;
	
	private static final String INDEX = "management/security/cache/index";
	
	@RequiresPermissions("Cache:view")
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index() {
		return INDEX;
	}
	
	@Log(message="进行了缓存清理。", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequiresPermissions(value={"Cache:edit", "Cache:delete"}, logical=Logical.OR)
	@RequestMapping(value="/clear", method=RequestMethod.POST)
	public @ResponseBody String clear() {
		cacheService.clearAllCache();
		
		return AjaxObject.newOk("清除缓存成功！").setCallbackType("").toString();
	}
}
