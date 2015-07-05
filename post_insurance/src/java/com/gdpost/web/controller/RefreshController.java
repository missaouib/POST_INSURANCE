package com.gdpost.web.controller;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.service.insurance.BqglService;
import com.gdpost.web.service.insurance.HfglService;
import com.gdpost.web.service.insurance.KfglService;
import com.gdpost.web.service.insurance.QyglService;
import com.gdpost.web.service.insurance.XqglService;
import com.gdpost.web.shiro.ShiroUser;

@Controller
@RequestMapping("/refresh")
public class RefreshController {
	//private static final Logger LOG = LoggerFactory.getLogger(RefreshController.class); 
	
	@Autowired
	private KfglService kfglService;
	
	@Autowired
	private BqglService bqglService;
	
	@Autowired
	private QyglService qyglService;
	
	@Autowired
	private XqglService xqglService;
	
	@Autowired
	private HfglService hfglService;
	
	@RequiresUser
	@RequestMapping(method = RequestMethod.POST)
	public String refresh() {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		
		//LOG.info("refreshed by " + shiroUser.getLoginName());
		
		return "refreshing";
	}
	
	@RequiresUser
	@RequestMapping(value="/checkTodoList", method=RequestMethod.GET)
	public @ResponseBody String check() {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		boolean hasIssue = false;
		boolean hasBqIssue = false;
		boolean hasWriteCheck = false;
		boolean hasRecordCheck = false;
		boolean hasXqIssue = false;
		boolean hasHfissue = false;
		if(kfglService.getTODOIssueList(shiroUser.getUser()).size() > 0) {
			hasIssue = true;
		} else if (bqglService.getTODOIssueList(shiroUser.getUser()).size() > 0) {
			hasBqIssue = true;
		} else if (qyglService.getTODOWriteIssueList(shiroUser.getUser()).size() > 0) {
			hasWriteCheck = true;
		} else if (qyglService.getTODORecordIssueList(shiroUser.getUser()).size() > 0) {
			hasRecordCheck = true;
		} else if (xqglService.getTODOIssueList(shiroUser.getUser()).size() > 0) {
			hasXqIssue = true;
		} else if (hfglService.getTODOIssueList(shiroUser.getUser()).size() > 0) {
			hasHfissue = true;
		}
		if(hasIssue) {
			return "issue";
		}
		if(hasBqIssue) {
			return "bqissue";
		}
		if(hasWriteCheck) {
			return "writecheck";
		}
		if(hasRecordCheck) {
			return "recordcheck";
		}
		if(hasXqIssue) {
			return "xqissue";
		}
		if(hasHfissue) {
			return "hfissue";
		}
		return "";
	}
}
