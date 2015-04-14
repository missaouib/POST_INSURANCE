/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.sendtend.web.controller.CacheManageController.java
 * Class:			CacheManageController
 * Date:			2012-9-14
 * Author:			sendtend
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.sendtend.web.controller.member;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sendtend.web.log.Log;
import com.sendtend.web.service.CacheService;
import com.sendtend.web.util.dwz.AjaxObject;

/**
 * 
 * @author sendtend Version 1.1.0
 * @since 2012-9-14 上午11:08:15
 */
@Controller
@RequestMapping("/members/cache")
public class MCacheController {
	@Autowired
	private CacheService cacheService;

	private static final String INDEX = "management/security/cache/index";

	@RequiresPermissions("Cache:view")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return INDEX;
	}

	@Log(message = "进行了缓存清理。")
	@RequiresPermissions(value = { "Cache:edit", "Cache:delete" }, logical = Logical.OR)
	@RequestMapping(value = "/clear", method = RequestMethod.POST)
	public @ResponseBody
	String clear() {
		cacheService.clearAllCache();

		return AjaxObject.newOk("清除缓存成功！").setCallbackType("").toString();
	}
}
