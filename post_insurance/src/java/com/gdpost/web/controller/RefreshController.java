package com.gdpost.web.controller;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/refresh")
public class RefreshController {
	//private static final Logger LOG = LoggerFactory.getLogger(RefreshController.class); 
	
	@RequiresUser
	@RequestMapping(method = RequestMethod.POST)
	public String refresh() {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		
		//LOG.info("refreshed by " + shiroUser.getLoginName());
		
		return "refreshing";
	}
}
