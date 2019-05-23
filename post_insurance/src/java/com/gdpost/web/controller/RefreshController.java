package com.gdpost.web.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.StringUtil;
import com.gdpost.web.entity.insurance.Issue;
import com.gdpost.web.entity.insurance.UnderWrite;
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
	@RequestMapping(value="/alert", method = RequestMethod.POST)
	public @ResponseBody String refresh() {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		Subject sub = SecurityUtils.getSubject();
		boolean check = false;
		boolean isIssue = false;
		if(sub.isPermitted("UnderWrite:view")) {
			List<UnderWrite> list = qyglService.getTODOUnderWriteList(shiroUser.getUser());
			for(UnderWrite uw:list) {
				if(StringUtil.getBetweenDay(uw.getSysDate(), new Date()) > 15) {
					check = true;
				}
			}
		}
		if(sub.isPermitted("Wtgd:view")) {
			List<Issue> list = kfglService.getTODOIssueList(shiroUser.getUser());
			for(Issue issue:list) {
				if(StringUtil.getBetweenDay(issue.getOperateTime(), new Date()) > 4) {
					isIssue = true;
				}
			}
		}
		String rst = "";
		if(isIssue) {
			rst += "|KFGD|";
		}
		if(check) {
			rst += "|QYGD|";
		}
		return rst;
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
		Subject sub = SecurityUtils.getSubject();
		if(sub.isPermitted("Wtgd:view")) {
			if(kfglService.getTODOIssueList(shiroUser.getUser()).size() > 0) {
				hasIssue = true;
			}
		}
		if(sub.isPermitted("Cservice:view")) {
			if (bqglService.getTODOIssueList(shiroUser.getUser()).size() > 0) {
				hasBqIssue = true;
			}
		}
		if(sub.isPermitted("CheckWrite:view")) {
			if (qyglService.getTODOWriteIssueList(shiroUser.getUser()).size() > 0) {
				hasWriteCheck = true;
			}
		}
		if(sub.isPermitted("CheckRecord:view")) {
			if (qyglService.getTODORecordIssueList(shiroUser.getUser()).size() > 0) {
				hasRecordCheck = true;
			}
		}
		if(sub.isPermitted("Renewed:view")) {
			if (xqglService.getTODOIssueList(shiroUser.getUser()).size() > 0) {
				hasXqIssue = true;
			}
		}
		if(sub.isPermitted("Callfail:view")) {
			if (hfglService.getTODOIssueList(shiroUser.getUser()).size() > 0) {
				hasHfissue = true;
			}
		}
		String rst = "";
		if(hasIssue) {
			rst += "|KFGD|";
		}
		if(hasBqIssue) {
			rst += "|BQGD|";
		}
		if(hasWriteCheck) {
			rst += "|TXGD|";
		}
		if(hasRecordCheck) {
			rst += "|LRGD|";
		}
		if(hasXqIssue) {
			rst += "|XQGD|";
		}
		if(hasHfissue) {
			rst += "|HFGD|";
		}
		return rst;
	}
}
