package com.gdpost.web.controller;

import java.util.ArrayList;
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
import com.gdpost.web.entity.insurance.Inquire;
import com.gdpost.web.entity.insurance.Issue;
import com.gdpost.web.entity.insurance.UnderWrite;
import com.gdpost.web.service.insurance.HfglService;
import com.gdpost.web.service.insurance.KfglService;
import com.gdpost.web.service.insurance.QyglService;
import com.gdpost.web.shiro.ShiroUser;

@Controller
@RequestMapping("/refresh")
public class RefreshController {
	//private static final Logger LOG = LoggerFactory.getLogger(RefreshController.class); 
	
	@Autowired
	private KfglService kfglService;
	
//	@Autowired
//	private BqglService bqglService;
	
	@Autowired
	private QyglService qyglService;
	
//	@Autowired
//	private XqglService xqglService;
	
	@Autowired
	private HfglService hfglService;
	
	@RequiresUser
	@RequestMapping(value="/alert", method = RequestMethod.POST)
	public @ResponseBody String refresh() {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		Subject sub = SecurityUtils.getSubject();
		boolean check = false;
		boolean isIssue = false;
		boolean isInquire = false;
		if(sub.isPermitted("UnderWrite:view")) {
			List<UnderWrite> list = new ArrayList<UnderWrite>();
			try{
				list = qyglService.getTODOUnderWriteList(shiroUser.getUser());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			for(UnderWrite uw:list) {
				if(uw == null || uw.getSysDate() ==null) {
					break;
				}
				if(StringUtil.getBetweenDay(uw.getSysDate(), new Date()) > 15) {
					check = true;
					break;
				}
			}
		}
		if(sub.isPermitted("Wtgd:view")) {
			List<Issue> list = kfglService.getTODOIssueList(shiroUser.getUser());
			for(Issue issue:list) {
				if(StringUtil.getBetweenDay(issue.getOperateTime(), new Date()) > 4) {
					isIssue = true;
					break;
				}
			}
		}
		if(sub.isPermitted("Inquire:view")) {
			List<Inquire> list = kfglService.getTODOInquireList(shiroUser.getUser());
			for(Inquire issue:list) {
				if(StringUtil.getBetweenDay(issue.getOperateTime(), new Date()) > 4) {
					isInquire = true;
					break;
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
		if(isInquire) {
			rst += "|IQGD|";
		}
		return rst;
	}
	
	@RequiresUser
	@RequestMapping(value="/checkTodoList", method=RequestMethod.GET)
	public @ResponseBody String check() {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		boolean hasIssue = false;
		boolean hasInquire = false;
		boolean hasBqIssue = false;
		boolean hasWriteCheck = false;
		boolean hasRecordCheck = false;
		boolean hasXqIssue = false;
		boolean hasHfissue = false;
		int zxnum = 0;
		int gdnum = 0;
		int wnum = 0;
		int rnum = 0;
		int hfnum = 0;
		Subject sub = SecurityUtils.getSubject();
		if(sub.isPermitted("Wtgd:view")) {
			gdnum = kfglService.getTODOIssueList(shiroUser.getUser()).size();
			if(gdnum > 0) {
				hasIssue = true;
			}
		}
		if(sub.isPermitted("Wtgd:view")) {
			zxnum = kfglService.getTODOInquireList(shiroUser.getUser()).size();
			if(zxnum > 0) {
				hasInquire = true;
			}
		}
//		if(sub.isPermitted("Cservice:view")) {
//			if (bqglService.getTODOIssueList(shiroUser.getUser()).size() > 0) {
//				hasBqIssue = true;
//			}
//		}
		if(sub.isPermitted("CheckWrite:view")) {
			wnum = qyglService.getTODOWriteIssueList(shiroUser.getUser()).size();
			if (wnum > 0) {
				hasWriteCheck = true;
			}
		}
		if(sub.isPermitted("CheckRecord:view")) {
			rnum = qyglService.getTODORecordIssueList(shiroUser.getUser()).size();
			if (rnum > 0) {
				hasRecordCheck = true;
			}
		}
//		if(sub.isPermitted("Renewed:view")) {
//			if (xqglService.getTODOIssueList(shiroUser.getUser()).size() > 0) {
//				hasXqIssue = true;
//			}
//		}
		if(sub.isPermitted("Callfail:view")) {
			hfnum = hfglService.getTODOIssueList(shiroUser.getUser()).size();
			if (hfnum > 0) {
				hasHfissue = true;
			}
		}
		String rst = "";
		if(hasIssue) {
			//rst += "|KFGD" + gdnum + "|";
			rst = "????????????" + gdnum + "???<br>";
		}
		if(hasInquire) {
			//rst += "|ZXGD" + zxnum + "|";
			rst = "????????????" + zxnum + "???<br>";
		}
		if(hasBqIssue) {
			//rst += "|BQGD|";
		}
		if(hasWriteCheck) {
			//rst += "|TXGD" + wnum + "|";
			rst = "????????????" + wnum + "???<br>";
		}
		if(hasRecordCheck) {
			//rst += "|LRGD" + rnum + "|";
			rst = "??????????????????" + rnum + "???";
		}
		if(hasXqIssue) {
			//rst += "|XQGD|";
		}
		if(hasHfissue) {
			//rst += "|HFGD" + hfnum + "|";
			//rst = "????????????" + gdnum + "???<br>";
		}
		return rst;
	}
	
	@RequiresUser
	@RequestMapping(value="/checkUrge", method=RequestMethod.GET)
	public @ResponseBody String checkUrge() {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		Subject sub = SecurityUtils.getSubject();
		if(sub.isPermitted("Wtgd:view")) {
			List<Inquire> rst = kfglService.getTODOInquireList(shiroUser.getUser(), true);
			if(rst != null && rst.size() >0 ) {
				return "??????" + rst.size() + "??????????????????&nbsp;&nbsp;<font color=red size=\"14\">??????</font>&nbsp;&nbsp;?????????<br>???????????????????????????";
			}
		}
		return "";
	}
}
