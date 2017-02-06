package com.gdpost.web.controller.component;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.StringUtil;
import com.gdpost.web.controller.insurance.BaseDataController;
import com.gdpost.web.entity.basedata.Prd;
import com.gdpost.web.entity.component.StaffModel;
import com.gdpost.web.entity.component.TuiBaoModel;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.service.component.StasticsService;
import com.gdpost.web.service.insurance.BaseDataService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.Page;

@Controller
@RequestMapping("/component")
public class StasticsController {
	private static final Logger LOG = LoggerFactory.getLogger(BaseDataController.class);
	
	@Autowired
	private StasticsService stasticsService;
	@Autowired
	private BaseDataService prdService;
	
	private static final String TUIBAO_LIST = "insurance/stastics/tuibao/tuibao";
	private static final String TUIBAO_TOXLS = "insurance/stastics/tuibao/tuibaoXls";
	
	private static final String STAFF_LIST = "insurance/stastics/staff/staff";
	private static final String STAFF_TOXLS = "insurance/stastics/staff/staffXls";
	
	/*
	 * =======================================
	 * tuibao
	 * =======================================
	 * 
	 */
	@RequiresUser
	@RequestMapping(value="/stastics/tuibao", method={RequestMethod.GET, RequestMethod.POST})
	public String getTuiBaoStastics(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String organName = request.getParameter("name");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String csd1 = request.getParameter("csDate1");
		String csd2 = request.getParameter("csDate2");
		String netFlag = request.getParameter("netFlag");
		String levelFlag = request.getParameter("levelFlag");
		String prdCode = request.getParameter("prdCode");
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if(organCode == null || organCode.trim().length()<=0) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		} else if(!organCode.contains(userOrg.getOrgCode())){
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}
		
		String toPrdCode = prdCode;
		if(prdCode == null || prdCode.trim().length()<=0) {
			toPrdCode = "%%";
		} else {
			if(prdCode.indexOf("_") > 0) {
				toPrdCode = prdCode.substring(0, prdCode.indexOf("_"));
			}
		}
		
		boolean isCity = false;
		if(organCode.length()>4) {
			levelFlag = "city";
		}
		
		if(levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		}
		
		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		request.setAttribute("netFlag", netFlag);
		request.setAttribute("prdCode", prdCode);
		request.setAttribute("levelFlag", levelFlag);
		
		boolean hasNet = true;
		if(netFlag == null || netFlag.trim().length()<=0) {
			hasNet = false;
		}
		TuiBaoModel cm = new TuiBaoModel();
		cm.setNetFlag(netFlag);
		cm.setPrdCode(prdCode);
		cm.setLevelFlag(levelFlag);
		request.setAttribute("TuiBaoModel", cm);
	
		String fd = StringUtil.getFirstDayOfYear("yyyy-MM-dd");
		if(pd1 == null || pd1.trim().length()<=0) {
			pd1 = fd;
		}
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		if(pd2 == null || pd2.trim().length()<=0) {
			pd2 = "9999-12-31";
		}
		if(csd1 == null || csd1.trim().length()<=0) {
			csd1 = fd;
		}
		request.setAttribute("csDate1", csd1);
		request.setAttribute("csDate2", csd2);
		if(csd2 == null || csd2.trim().length()<=0) {
			csd2 = "9999-12-31";
		}
		
		List<TuiBaoModel> temp = null;
		if(isCity) {
			if(hasNet) {
				temp = stasticsService.getTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2, netFlag, toPrdCode);
			} else {
				temp = stasticsService.getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(organCode + "%", pd1, pd2, csd1, csd2, toPrdCode);
			}
		} else {
			if(hasNet) {
				temp = stasticsService.getProvTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2, netFlag, toPrdCode);
			} else {
				temp = stasticsService.getProvTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(organCode + "%", pd1, pd2, csd1, csd2, toPrdCode);
			}
		}
		
		request.setAttribute("cmRst", temp);
		String col = "";
		String zhanbi = "";
		String tuibao = "";
		double maxZB = 0;
		int maxTB = 0;
		DecimalFormat df = new DecimalFormat("#.00"); 
		for(TuiBaoModel tcm:temp) {
			col += "'" + tcm.getOrganName() + "',";
			zhanbi += df.format(tcm.getPolicyFee()/tcm.getSumPolicyFee()) + ",";
			if(tcm.getPolicyFee()/tcm.getSumPolicyFee() > maxZB) {
				maxZB = Math.ceil(tcm.getPolicyFee()/tcm.getSumPolicyFee());
			}
			tuibao += tcm.getPolicyFee()/10000 + ",";
			if(tcm.getPolicyFee()/10000 > maxTB) {
				maxTB = (int)Math.ceil(tcm.getPolicyFee()/10000);
			}
		}
		int m = 1;
		for (int i = 0; i < (int) Math.log10(maxTB); i++) {  
		    m *= 10;  
		}  
		// 第一位  
		maxTB = (maxTB / m +1) *m; 
		request.setAttribute("col", col);
		request.setAttribute("zhanbi", zhanbi);
		request.setAttribute("tuibao", tuibao);
		request.setAttribute("maxZB", maxZB);
		request.setAttribute("maxTB", maxTB);
		
		Page page = new Page();
		page.setNumPerPage(50);
		List<Prd> prds = prdService.findAllPrd(page);
		request.setAttribute("prds", prds);
		LOG.debug(" ------------ result size:" + temp.size());
		return TUIBAO_LIST;
	}
	
	@RequestMapping(value="/stastics/tuibao/toXls", method={RequestMethod.GET, RequestMethod.POST})
	public String tuibaoStasticsToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String csd1 = request.getParameter("csDate1");
		String csd2 = request.getParameter("csDate2");
		String netFlag = request.getParameter("netFlag");
		String levelFlag = request.getParameter("levelFlag");
		String prdCode = request.getParameter("prdCode");
		if(organCode == null || organCode.trim().length()<=0) {
			organCode = "8644";
		}
		String toPrdCode = prdCode;
		if(prdCode == null || prdCode.trim().length()<=0) {
			toPrdCode = "%%";
		} else {
			if(prdCode.indexOf("_") > 0) {
				toPrdCode = prdCode.substring(0, prdCode.indexOf("_"));
			}
		}
		boolean isCity = false;
		if(levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		}
		
		boolean hasNet = true;
		if(netFlag == null || netFlag.trim().length()<=0) {
			hasNet = false;
		}
	
		String fd = StringUtil.getFirstDayOfYear("yyyy-MM-dd");
		if(pd1 == null || pd1.trim().length()<=0) {
			pd1 = fd;
		}
		if(pd2 == null || pd2.trim().length()<=0) {
			pd2 = "9999-12-31";
		}
		if(csd1 == null || csd1.trim().length()<=0) {
			csd1 = fd;
		}
		if(csd2 == null || csd2.trim().length()<=0) {
			csd2 = "9999-12-31";
		}
		
		List<TuiBaoModel> temp = null;
		if(isCity) {
			if(hasNet) {
				temp = stasticsService.getTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2, netFlag, toPrdCode);
			} else {
				temp = stasticsService.getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(organCode + "%", pd1, pd2, csd1, csd2, toPrdCode);
			}
		} else {
			if(hasNet) {
				temp = stasticsService.getProvTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2, netFlag, toPrdCode);
			} else {
				temp = stasticsService.getProvTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(organCode + "%", pd1, pd2, csd1, csd2, toPrdCode);
			}
		}
		
		request.setAttribute("cmRst", temp);
		LOG.debug(" ------------ result size:" + temp.size());
		return TUIBAO_TOXLS;
	}
	
	/*
	 * =======================================
	 * staff
	 * =======================================
	 * 
	 */
	@RequiresUser
	@RequestMapping(value="/stastics/staff", method={RequestMethod.GET, RequestMethod.POST})
	public String getStaffStastics(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String organName = request.getParameter("name");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String netFlag = request.getParameter("netFlag");
		String levelFlag = request.getParameter("levelFlag");
		String prdCode = request.getParameter("prdCode");
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if(organCode == null || organCode.trim().length()<=0) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		} else if(!organCode.contains(userOrg.getOrgCode())){
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}
		
		String toPrdCode = prdCode;
		if(prdCode == null || prdCode.trim().length()<=0) {
			toPrdCode = "%%";
		} else {
			if(prdCode.indexOf("_") > 0) {
				toPrdCode = prdCode.substring(0, prdCode.indexOf("_"));
			}
		}
		
		boolean isCity = false;
		if(organCode.length()>4) {
			levelFlag = "city";
		}
		
		if(levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		}
		
		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		request.setAttribute("netFlag", netFlag);
		request.setAttribute("prdCode", prdCode);
		request.setAttribute("levelFlag", levelFlag);
		
		boolean hasNet = true;
		if(netFlag == null || netFlag.trim().length()<=0) {
			hasNet = false;
		}
		StaffModel cm = new StaffModel();
		cm.setNetFlag(netFlag);
		cm.setPrdCode(prdCode);
		cm.setLevelFlag(levelFlag);
		request.setAttribute("StaffModel", cm);
	
		String fd = StringUtil.getFirstDayOfYear("yyyy-MM-dd");
		if(pd1 == null || pd1.trim().length()<=0) {
			pd1 = fd;
		}
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		if(pd2 == null || pd2.trim().length()<=0) {
			pd2 = "9999-12-31";
		}
		
		List<StaffModel> temp = null;
		if(isCity) {
			if(hasNet) {
				temp = stasticsService.getStaffCountWithPolicyDate(organCode + "%", pd1, pd2, netFlag, toPrdCode);
			} else {
				temp = stasticsService.getStaffCountWithPolicyDateNoBankCode(organCode + "%", pd1, pd2, toPrdCode);
			}
		} else {
			if(hasNet) {
				temp = stasticsService.getProvStaffCountWithPolicyDate(organCode + "%", pd1, pd2, netFlag, toPrdCode);
			} else {
				temp = stasticsService.getProvStaffCountWithPolicyDateNoBankCode(organCode + "%", pd1, pd2, toPrdCode);
			}
		}
		
		request.setAttribute("cmRst", temp);
		String col = "";
		String zhanbi = "";
		String tuibao = "";
		double maxZB = 0;
		int maxTB = 0;
		DecimalFormat df = new DecimalFormat("#.00"); 
		for(StaffModel tcm:temp) {
			col += "'" + tcm.getOrganName() + "',";
			zhanbi += df.format(tcm.getPolicyFee()/tcm.getSumPolicyFee()) + ",";
			if(tcm.getPolicyFee()/tcm.getSumPolicyFee() > maxZB) {
				maxZB = Math.ceil(tcm.getPolicyFee()/tcm.getSumPolicyFee());
			}
			tuibao += tcm.getPolicyFee()/10000 + ",";
			if(tcm.getPolicyFee()/10000 > maxTB) {
				maxTB = (int)Math.ceil(tcm.getPolicyFee()/10000);
			}
		}
		int m = 1;
		for (int i = 0; i < (int) Math.log10(maxTB); i++) {  
		    m *= 10;  
		}  
		// 第一位  
		maxTB = (maxTB / m +1) *m; 
		request.setAttribute("col", col);
		request.setAttribute("zhanbi", zhanbi);
		request.setAttribute("staff", tuibao);
		request.setAttribute("maxZB", maxZB);
		request.setAttribute("maxTB", maxTB);
		
		Page page = new Page();
		page.setNumPerPage(50);
		List<Prd> prds = prdService.findAllPrd(page);
		request.setAttribute("prds", prds);
		LOG.debug(" ------------ result size:" + temp.size());
		return STAFF_LIST;
	}
	
	@RequestMapping(value="/stastics/staff/toXls", method={RequestMethod.GET, RequestMethod.POST})
	public String staffStasticsToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String netFlag = request.getParameter("netFlag");
		String levelFlag = request.getParameter("levelFlag");
		String prdCode = request.getParameter("prdCode");
		if(organCode == null || organCode.trim().length()<=0) {
			organCode = "8644";
		}
		String toPrdCode = prdCode;
		if(prdCode == null || prdCode.trim().length()<=0) {
			toPrdCode = "%%";
		} else {
			if(prdCode.indexOf("_") > 0) {
				toPrdCode = prdCode.substring(0, prdCode.indexOf("_"));
			}
		}
		boolean isCity = false;
		if(levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		}
		
		boolean hasNet = true;
		if(netFlag == null || netFlag.trim().length()<=0) {
			hasNet = false;
		}
	
		String fd = StringUtil.getFirstDayOfYear("yyyy-MM-dd");
		if(pd1 == null || pd1.trim().length()<=0) {
			pd1 = fd;
		}
		if(pd2 == null || pd2.trim().length()<=0) {
			pd2 = "9999-12-31";
		}
		
		List<StaffModel> temp = null;
		if(isCity) {
			if(hasNet) {
				temp = stasticsService.getStaffCountWithPolicyDate(organCode + "%", pd1, pd2, netFlag, toPrdCode);
			} else {
				temp = stasticsService.getStaffCountWithPolicyDateNoBankCode(organCode + "%", pd1, pd2, toPrdCode);
			}
		} else {
			if(hasNet) {
				temp = stasticsService.getProvStaffCountWithPolicyDate(organCode + "%", pd1, pd2, netFlag, toPrdCode);
			} else {
				temp = stasticsService.getProvStaffCountWithPolicyDateNoBankCode(organCode + "%", pd1, pd2, toPrdCode);
			}
		}
		
		request.setAttribute("cmRst", temp);
		LOG.debug(" ------------ result size:" + temp.size());
		return STAFF_TOXLS;
	}
}
