package com.gdpost.web.controller.component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.gdpost.web.entity.component.PolicyStatModel;
import com.gdpost.web.entity.component.StaffDtlModel;
import com.gdpost.web.entity.component.StaffModel;
import com.gdpost.web.entity.component.TuiBaoDtlModel;
import com.gdpost.web.entity.component.TuiBaoModel;
import com.gdpost.web.entity.component.UwDtlModel;
import com.gdpost.web.entity.component.UwModel;
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
	private static final String TUIBAO_DTL_TOXLS = "insurance/stastics/tuibao/tuibaoDtlXls";
	
	private static final String STAFF_LIST = "insurance/stastics/staff/staff";
	private static final String STAFF_TOXLS = "insurance/stastics/staff/staffXls";
	private static final String STAFF_DTL_TOXLS = "insurance/stastics/staff/staffDtlXls";
	
	private static final String UW_LIST = "insurance/stastics/underwrite/stats";
	private static final String UW_TOXLS = "insurance/stastics/underwrite/uw_xls";
	private static final String UW_DTL_TOXLS = "insurance/stastics/underwrite/uw_dtl_xls";
	
	private static final String POLICY_LIST = "insurance/stastics/policy/policy";
	private static final String POLICY_TOXLS = "insurance/stastics/policy/policyXls";
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
		//String prdName = request.getParameter("prdName");
		String perm = request.getParameter("perm");
		String staffFlag = request.getParameter("staffFlag");
		String bankName = request.getParameter("bankName");
		
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
		
		if(prdCode == null || prdCode.trim().length()<=0) {
			prdCode = "%%";
		}
		
		/*
		String toPrdName = prdCode;
		if(prdCode == null || prdCode.trim().length()<=0) {
			toPrdName = "%%";
		} else {
			Prd prd = prdService.getByPrdCode(prdCode);
			toPrdName = prd.getPrdName();
			if(toPrdName.indexOf("_") > 0) {
				toPrdName = "%" + toPrdName.substring(0, toPrdName.indexOf("_")) + "%";
			} else {
				toPrdName = "%" + toPrdName + "%";
			}
		}
		*/
		
		String toPerm = perm;
		if(perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if(perm.trim().length()<=0) {
			toPerm = "%%";
		} else if(perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		String isStaff = staffFlag;
		if(staffFlag == null || staffFlag.trim().length()<=0) {
			isStaff = "%%";
		}
		
		boolean isCity = false;
		boolean isNet = false;
		if(levelFlag == null) {
			if(organCode.length()>=8) {
				levelFlag = "net";
			} else if(organCode.length()>4) {
				levelFlag = "city";
			}
		}
		
		if(levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		} else if(levelFlag != null && levelFlag.trim().equals("net")) {
			isNet = true;
		}
		
		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		request.setAttribute("netFlag", netFlag);
		request.setAttribute("prdCode", prdCode);
		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("perm", perm);
		request.setAttribute("staffFlag", staffFlag);
		
		boolean hasNet = true;
		if(netFlag == null || netFlag.trim().length()<=0) {
			hasNet = false;
		}
		TuiBaoModel cm = new TuiBaoModel();
		cm.setNetFlag(netFlag);
		cm.setPrdCode(prdCode);
		cm.setLevelFlag(levelFlag);
		cm.setPerm(perm);
		cm.setStaffFlag(staffFlag);
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
			csd1 = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		}
		request.setAttribute("csDate1", csd1);
		request.setAttribute("csDate2", csd2);
		if(csd2 == null || csd2.trim().length()<=0) {
			csd2 = "9999-12-31";
		}
		if(bankName == null || bankName.trim().length()<=0) {
			bankName= "%%";
		} else {
			bankName = "&" + bankName + "%";
		}
		
		List<TuiBaoModel> temp = null;
		//List<TuiBaoModel> temp1 = null;
		if(isNet) {
			if(hasNet) {
				temp = stasticsService.getNetTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2, netFlag, prdCode, toPerm, isStaff, bankName);
			} else {
				temp = stasticsService.getNetTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2, prdCode, toPerm, isStaff, bankName);
			}
//			if(hasNet) {
//				temp1 = stasticsService.getTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2, netFlag, toPrdName, toPerm, isStaff);
//			} else {
//				temp1 = stasticsService.getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(organCode + "%", pd1, pd2, csd1, csd2, toPrdName, toPerm, isStaff);
//			}
		} else if(isCity) {
			if(hasNet) {
				temp = stasticsService.getTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2, netFlag, prdCode, toPerm, isStaff);
			} else {
				temp = stasticsService.getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(organCode + "%", pd1, pd2, csd1, csd2, prdCode, toPerm, isStaff);
			}
		} else {
			if(hasNet) {
				temp = stasticsService.getProvTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2, netFlag, prdCode, toPerm, isStaff);
			} else {
				temp = stasticsService.getProvTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(organCode + "%", pd1, pd2, csd1, csd2, prdCode, toPerm, isStaff);
			}
		}
		
		request.setAttribute("cmRst", temp);
		String col = "";
		String zhanbi = "";
		String tuibao = "";
		double maxZB = 0;
		int maxTB = 0;
		double sumTb = 0;
		double totalTb = 0;
		double totalCS = 0;
		DecimalFormat df = new DecimalFormat("#.#"); 
		for(TuiBaoModel tcm:temp) {//(isNet?temp1:temp)
			col += "'" + tcm.getOrganName() + "',";
			sumTb += tcm.getPolicyFee()==null?0:tcm.getPolicyFee();
			totalTb += tcm.getSumPolicyFee();
			totalCS += tcm.getSumCsFee()==null?0:tcm.getSumCsFee();
			zhanbi += df.format(tcm.getPolicyFee()==null?0:tcm.getPolicyFee()/tcm.getSumPolicyFee()*100) + ",";
			if(((tcm.getPolicyFee()==null?0:tcm.getPolicyFee())/tcm.getSumPolicyFee()*100+1) > maxZB) {
				maxZB = Math.ceil(tcm.getPolicyFee()==null?0:tcm.getPolicyFee()/tcm.getSumPolicyFee()*100) +1;
			}
			tuibao += (tcm.getPolicyFee()==null?0:tcm.getPolicyFee())/10000 + ",";
			if((tcm.getPolicyFee()==null?0:tcm.getPolicyFee())/10000 > maxTB) {
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
		request.setAttribute("sumTb", sumTb/10000);
		request.setAttribute("totalCS", totalCS/10000);
		request.setAttribute("totalTb", totalTb/10000);
		
		Page page = new Page();
		page.setNumPerPage(50);
		List<Prd> prds = prdService.findAllPrd(page);
		request.setAttribute("prds", prds);
		LOG.debug(" ------------ result size:" + temp.size());
		return TUIBAO_LIST;
	}
	
	@RequiresUser
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
		String perm = request.getParameter("perm");
		String staffFlag = request.getParameter("staffFlag");
		String bankName = request.getParameter("bankName");
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if(organCode == null || organCode.trim().length()<=0) {
			organCode = userOrg.getOrgCode();
		} else if(!organCode.contains(userOrg.getOrgCode())){
			organCode = userOrg.getOrgCode();
		}
		
		if(prdCode == null || prdCode.trim().length()<=0) {
			prdCode = "%%";
		}
		/*
		String toPrdName = prdCode;
		if(prdCode == null || prdCode.trim().length()<=0) {
			toPrdName = "%%";
		} else {
			Prd prd = prdService.getByPrdCode(prdCode);
			toPrdName = prd.getPrdName();
			if(toPrdName.indexOf("_") > 0) {
				toPrdName = "%" + toPrdName.substring(0, toPrdName.indexOf("_")) + "%";
			} else {
				toPrdName = "%" + toPrdName + "%";
			}
		}
		*/
		String toPerm = perm;
		if(perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if(perm.trim().length()<=0) {
			toPerm = "%%";
		} else if(perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		String isStaff = staffFlag;
		if(staffFlag == null || staffFlag.trim().length()<=0) {
			isStaff = "%%";
		}
		boolean isCity = false;
		boolean isNet = false;
		if(levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		} else if(levelFlag != null && levelFlag.trim().equals("net")) {
			isNet = true;
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
			csd1 = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		}
		if(csd2 == null || csd2.trim().length()<=0) {
			csd2 = "9999-12-31";
		}
		if(bankName == null || bankName.trim().length()<=0) {
			bankName= "%%";
		} else {
			bankName = "&" + bankName + "%";
		}
		
		List<TuiBaoModel> temp = null;
		if(isNet) {
			if(hasNet) {
				temp = stasticsService.getNetTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2, netFlag, prdCode, toPerm, isStaff, bankName);
			} else {
				temp = stasticsService.getNetTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2, prdCode, toPerm, isStaff, bankName);
			}
		} else if(isCity) {
			if(hasNet) {
				temp = stasticsService.getTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2, netFlag, prdCode, toPerm, isStaff);
			} else {
				temp = stasticsService.getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(organCode + "%", pd1, pd2, csd1, csd2, prdCode, toPerm, isStaff);
			}
		} else {
			if(hasNet) {
				temp = stasticsService.getProvTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2, netFlag, prdCode, toPerm, isStaff);
			} else {
				temp = stasticsService.getProvTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(organCode + "%", pd1, pd2, csd1, csd2, prdCode, toPerm, isStaff);
			}
		}
		
		request.setAttribute("cmRst", temp);
		
		double sumTb = 0;
		double totalTb = 0;
		double totalCS = 0;
		for(TuiBaoModel tcm:temp) {//(isNet?temp1:temp)
			sumTb += tcm.getPolicyFee()==null?0:tcm.getPolicyFee();
			totalTb += tcm.getSumPolicyFee();
			totalCS += tcm.getSumCsFee()==null?0:tcm.getSumCsFee();
		}
		// 第一位  
		request.setAttribute("sumTb", sumTb/10000);
		request.setAttribute("totalCS", totalCS/10000);
		request.setAttribute("totalTb", totalTb/10000);
		
		LOG.debug(" ------------ result size:" + temp.size());
		return TUIBAO_TOXLS;
	}
	
	@RequiresUser
	@RequestMapping(value="/stastics/tuibao/dtlXls", method={RequestMethod.GET, RequestMethod.POST})
	public String tuibaoDtlToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String csd1 = request.getParameter("csDate1");
		String csd2 = request.getParameter("csDate2");
		String netFlag = request.getParameter("netFlag");
		String prdCode = request.getParameter("prdCode");
		String perm = request.getParameter("perm");
		String staffFlag = request.getParameter("staffFlag");
		String bankName = request.getParameter("bankName");
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if(organCode == null || organCode.trim().length()<=0) {
			organCode = userOrg.getOrgCode();
		} else if(!organCode.contains(userOrg.getOrgCode())){
			organCode = userOrg.getOrgCode();
		}
		if(prdCode == null || prdCode.trim().length()<=0) {
			prdCode = "%%";
		}
		/*
		String toPrdName = prdCode;
		if(prdCode == null || prdCode.trim().length()<=0) {
			toPrdName = "%%";
		} else {
			Prd prd = prdService.getByPrdCode(prdCode);
			toPrdName = prd.getPrdName();
			if(toPrdName.indexOf("_") > 0) {
				toPrdName = "%" + toPrdName.substring(0, toPrdName.indexOf("_")) + "%";
			} else {
				toPrdName = "%" + toPrdName + "%";
			}
		}
		*/
		String toPerm = perm;
		if(perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if(perm.trim().length()<=0) {
			toPerm = "%%";
		} else if(perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		String isStaff = staffFlag;
		if(staffFlag == null || staffFlag.trim().length()<=0) {
			isStaff = "%%";
		}
		
		if(netFlag == null || netFlag.trim().length()<=0) {
			netFlag = "%%";
		}
	
		String fd = StringUtil.getFirstDayOfYear("yyyy-MM-dd");
		if(pd1 == null || pd1.trim().length()<=0) {
			pd1 = fd;
		}
		if(pd2 == null || pd2.trim().length()<=0) {
			pd2 = "9999-12-31";
		}
		if(csd1 == null || csd1.trim().length()<=0) {
			csd1 = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		}
		if(csd2 == null || csd2.trim().length()<=0) {
			csd2 = "9999-12-31";
		}
		if(bankName == null || bankName.trim().length()<=0) {
			bankName= "%%";
		} else {
			bankName = "&" + bankName + "%";
		}
		
		List<TuiBaoDtlModel> temp = stasticsService.getTuiBaoWarnningDetailWithBankCode(organCode + "%", pd1, pd2, csd1, csd2, netFlag, prdCode, toPerm, isStaff, bankName);
		
		request.setAttribute("cmRst", temp);
		LOG.debug(" ------------ result size:" + temp.size());
		return TUIBAO_DTL_TOXLS;
	}
	
	@RequiresUser
	@RequestMapping(value="/stastics/tuibao/csdtlXls", method={RequestMethod.GET, RequestMethod.POST})
	public String tuibaoCsDtlToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String csd1 = request.getParameter("csDate1");
		String csd2 = request.getParameter("csDate2");
		String netFlag = request.getParameter("netFlag");
		String prdCode = request.getParameter("prdCode");
		String perm = request.getParameter("perm");
		String staffFlag = request.getParameter("staffFlag");
		String bankName = request.getParameter("bankName");
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if(organCode == null || organCode.trim().length()<=0) {
			organCode = userOrg.getOrgCode();
		} else if(!organCode.contains(userOrg.getOrgCode())){
			organCode = userOrg.getOrgCode();
		}
		if(prdCode == null || prdCode.trim().length()<=0) {
			prdCode = "%%";
		}
		/*
		String toPrdName = prdCode;
		if(prdCode == null || prdCode.trim().length()<=0) {
			toPrdName = "%%";
		} else {
			Prd prd = prdService.getByPrdCode(prdCode);
			toPrdName = prd.getPrdName();
			if(toPrdName.indexOf("_") > 0) {
				toPrdName = "%" + toPrdName.substring(0, toPrdName.indexOf("_")) + "%";
			} else {
				toPrdName = "%" + toPrdName + "%";
			}
		}
		*/
		String toPerm = perm;
		if(perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if(perm.trim().length()<=0) {
			toPerm = "%%";
		} else if(perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		String isStaff = staffFlag;
		if(staffFlag == null || staffFlag.trim().length()<=0) {
			isStaff = "%%";
		}
		
		if(netFlag == null || netFlag.trim().length()<=0) {
			netFlag = "%%";
		}
	
		String fd = StringUtil.getFirstDayOfYear("yyyy-MM-dd");
		if(pd1 == null || pd1.trim().length()<=0) {
			pd1 = fd;
		}
		if(pd2 == null || pd2.trim().length()<=0) {
			pd2 = "9999-12-31";
		}
		if(csd1 == null || csd1.trim().length()<=0) {
			csd1 = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		}
		if(csd2 == null || csd2.trim().length()<=0) {
			csd2 = "9999-12-31";
		}
		if(bankName == null || bankName.trim().length()<=0) {
			bankName= "%%";
		} else {
			bankName = "&" + bankName + "%";
		}
		
		List<TuiBaoDtlModel> temp = stasticsService.getTuiBaoCsDetailWithBankCode(organCode + "%", pd1, pd2, csd1, csd2, netFlag, prdCode, toPerm, isStaff, bankName);
		
		request.setAttribute("cmRst", temp);
		LOG.debug(" ------------ result size:" + temp.size());
		return TUIBAO_DTL_TOXLS;
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
		String perm = request.getParameter("perm");
		
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
		if(prdCode == null || prdCode.trim().length()<=0) {
			prdCode = "%%";
		}
		
		/*
		String toPrdName = prdCode;
		if(prdCode == null || prdCode.trim().length()<=0) {
			toPrdName = "%%";
		} else {
			Prd prd = prdService.getByPrdCode(prdCode);
			toPrdName = prd.getPrdName();
			if(toPrdName.indexOf("_") > 0) {
				toPrdName = "%" + toPrdName.substring(0, toPrdName.indexOf("_")) + "%";
			} else {
				toPrdName = "%" + toPrdName + "%";
			}
		}
		*/
		String toPerm = perm;
		if(perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if(perm.trim().length()<=0) {
			toPerm = "%%";
		} else if(perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
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
		request.setAttribute("perm", perm);
		
		boolean hasNet = true;
		if(netFlag == null || netFlag.trim().length()<=0) {
			hasNet = false;
		}
		StaffModel cm = new StaffModel();
		cm.setNetFlag(netFlag);
		cm.setPrdCode(prdCode);
		cm.setLevelFlag(levelFlag);
		cm.setPerm(perm);
		request.setAttribute("StaffModel", cm);
	
		String fd = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
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
				temp = stasticsService.getStaffCountWithPolicyDate(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm);
			} else {
				temp = stasticsService.getStaffCountWithPolicyDateNoBankCode(organCode + "%", pd1, pd2, prdCode, toPerm);
			}
		} else {
			if(hasNet) {
				temp = stasticsService.getProvStaffCountWithPolicyDate(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm);
			} else {
				temp = stasticsService.getProvStaffCountWithPolicyDateNoBankCode(organCode + "%", pd1, pd2, prdCode, toPerm);
			}
		}
		
		request.setAttribute("cmRst", temp);
		String col = "";
		String zhanbi = "";
		String tuibao = "";
		double maxZB = 0;
		int maxTB = 0;
		int csumTb = 0;//员工件数
		int ctotalTb = 0;//总件数
		double ssumTb = 0;//员工保费
		double stotalTb = 0;//总保费
		DecimalFormat df = new DecimalFormat("#.#"); 
		for(StaffModel tcm:temp) {
			col += "'" + tcm.getOrganName() + "',";
			csumTb += tcm.getStaffCount()==null?0:tcm.getStaffCount();//员工件数
			ctotalTb += tcm.getSumStaffCount();//总件数
			ssumTb += tcm.getPolicyFee()==null?0:tcm.getPolicyFee();//员工保费
			stotalTb += tcm.getSumPolicyFee();//总保费
			zhanbi += df.format((tcm.getPolicyFee()==null?0:tcm.getPolicyFee())/tcm.getSumPolicyFee()*100) + ",";
			if((tcm.getPolicyFee()==null?0:tcm.getPolicyFee())/tcm.getSumPolicyFee()*100+1 > maxZB) {
				maxZB = Math.ceil(((tcm.getPolicyFee()==null?0:tcm.getPolicyFee())/tcm.getSumPolicyFee())*100) + 1;
			}
			tuibao += (tcm.getPolicyFee()==null?0:tcm.getPolicyFee())/10000 + ",";
			if((tcm.getPolicyFee()==null?0:tcm.getPolicyFee())/10000 > maxTB) {
				maxTB = (int)Math.ceil((tcm.getPolicyFee()==null?0:tcm.getPolicyFee())/10000);
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
		request.setAttribute("csumTb", csumTb);
		request.setAttribute("ctotalTb", ctotalTb);
		request.setAttribute("ssumTb", ssumTb/10000);
		request.setAttribute("stotalTb", stotalTb/10000);
		
		Page page = new Page();
		page.setNumPerPage(50);
		List<Prd> prds = prdService.findAllPrd(page);
		request.setAttribute("prds", prds);
		LOG.debug(" ------------ result size:" + temp.size());
		return STAFF_LIST;
	}
	
	@RequiresUser
	@RequestMapping(value="/stastics/staff/toXls", method={RequestMethod.GET, RequestMethod.POST})
	public String staffStasticsToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String netFlag = request.getParameter("netFlag");
		String levelFlag = request.getParameter("levelFlag");
		String prdCode = request.getParameter("prdCode");
		String perm = request.getParameter("perm");
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if(organCode == null || organCode.trim().length()<=0) {
			organCode = userOrg.getOrgCode();
		} else if(!organCode.contains(userOrg.getOrgCode())){
			organCode = userOrg.getOrgCode();
		}
		if(prdCode == null || prdCode.trim().length()<=0) {
			prdCode = "%%";
		}
		/*
		String toPrdName = prdCode;
		if(prdCode == null || prdCode.trim().length()<=0) {
			toPrdName = "%%";
		} else {
			Prd prd = prdService.getByPrdCode(prdCode);
			toPrdName = prd.getPrdName();
			if(toPrdName.indexOf("_") > 0) {
				toPrdName = "%" + toPrdName.substring(0, toPrdName.indexOf("_")) + "%";
			} else {
				toPrdName = "%" + toPrdName + "%";
			}
		}
		*/
		String toPerm = perm;
		if(perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if(perm.trim().length()<=0) {
			toPerm = "%%";
		} else if(perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
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
				temp = stasticsService.getStaffCountWithPolicyDate(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm);
			} else {
				temp = stasticsService.getStaffCountWithPolicyDateNoBankCode(organCode + "%", pd1, pd2, prdCode, toPerm);
			}
		} else {
			if(hasNet) {
				temp = stasticsService.getProvStaffCountWithPolicyDate(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm);
			} else {
				temp = stasticsService.getProvStaffCountWithPolicyDateNoBankCode(organCode + "%", pd1, pd2, prdCode, toPerm);
			}
		}
		
		request.setAttribute("cmRst", temp);
		
		int csumTb = 0;
		int ctotalTb = 0;
		double ssumTb = 0;
		double stotalTb = 0;
		for(StaffModel tcm:temp) {
			csumTb += tcm.getStaffCount()==null?0:tcm.getStaffCount();
			ctotalTb += tcm.getSumStaffCount();
			ssumTb += tcm.getPolicyFee()==null?0:tcm.getPolicyFee();
			stotalTb += tcm.getSumPolicyFee();
		}
		request.setAttribute("csumTb", csumTb);
		request.setAttribute("ctotalTb", ctotalTb);
		request.setAttribute("ssumTb", ssumTb/10000);
		request.setAttribute("stotalTb", stotalTb/10000);
		
		LOG.debug(" ------------ result size:" + temp.size());
		return STAFF_TOXLS;
	}

	@RequiresUser
	@RequestMapping(value="/stastics/staff/dtlXls", method={RequestMethod.GET, RequestMethod.POST})
	public String staffDtlToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String netFlag = request.getParameter("netFlag");
		String prdCode = request.getParameter("prdCode");
		String perm = request.getParameter("perm");
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if(organCode == null || organCode.trim().length()<=0) {
			organCode = userOrg.getOrgCode();
		} else if(!organCode.contains(userOrg.getOrgCode())){
			organCode = userOrg.getOrgCode();
		}
		if(prdCode == null || prdCode.trim().length()<=0) {
			prdCode = "%%";
		}
		/*
		String toPrdName = prdCode;
		if(prdCode == null || prdCode.trim().length()<=0) {
			toPrdName = "%%";
		} else {
			Prd prd = prdService.getByPrdCode(prdCode);
			toPrdName = prd.getPrdName();
			if(toPrdName.indexOf("_") > 0) {
				toPrdName = "%" + toPrdName.substring(0, toPrdName.indexOf("_")) + "%";
			} else {
				toPrdName = "%" + toPrdName + "%";
			}
		}
		*/
		if(netFlag == null || netFlag.trim().length()<=0) {
			netFlag = "%%";
		}
		String toPerm = perm;
		if(perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if(perm.trim().length()<=0) {
			toPerm = "%%";
		} else if(perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		String fd = StringUtil.getFirstDayOfYear("yyyy-MM-dd");
		if(pd1 == null || pd1.trim().length()<=0) {
			pd1 = fd;
		}
		if(pd2 == null || pd2.trim().length()<=0) {
			pd2 = "9999-12-31";
		}
		
		List<StaffDtlModel> temp = stasticsService.getStaffDetailWithPolicyDate(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm);
		
		request.setAttribute("cmRst", temp);
		LOG.debug(" ------------ result size:" + temp.size());
		return STAFF_DTL_TOXLS;
	}

	/*
	 * =======================================
	 * staff
	 * =======================================
	 * 
	 */
	@RequiresUser
	@RequestMapping(value="/stastics/underwrite", method={RequestMethod.GET, RequestMethod.POST})
	public String getUWStastics(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String organName = request.getParameter("name");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String levelFlag = request.getParameter("levelFlag");
		String statFlag = request.getParameter("statFlag");
		
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
		
		boolean isCity = false;
		boolean isNet = false;
		if(levelFlag == null) {
			if(organCode.length()>=8) {
				levelFlag = "net";
			} else if(organCode.length()>4) {
				levelFlag = "city";
			}
		}
		
		if(levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		} else if(levelFlag != null && levelFlag.trim().equals("net")) {
			isNet = true;
		}
		
		UwModel cm = new UwModel();
		cm.setLevelFlag(levelFlag);
		cm.setStatFlag(statFlag);
		request.setAttribute("UwModel", cm);
		
		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		request.setAttribute("statFlag", statFlag);
	
		String fd = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		if(pd1 == null || pd1.trim().length()<=0) {
			pd1 = fd;
		}
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		if(pd2 == null || pd2.trim().length()<=0) {
			pd2 = "9999-12-31";
		}
		
		List<UwModel> temp = null;
		if(statFlag==null || statFlag.trim().equals("sellBack")) {
			if(isNet) {
				temp = stasticsService.getNetUwStastics(organCode + "%", pd1, pd2);
			} else if(isCity) {
				temp = stasticsService.getCityUwStastics(organCode + "%", pd1, pd2);
			} else {
				temp = stasticsService.getProvUwStastics(organCode + "%", pd1, pd2);
			}
		} else {
			if(isNet) {
				temp = stasticsService.getNetLongUwStastics(organCode + "%", pd1, pd2);
			} else if(isCity) {
				temp = stasticsService.getCityLongUwStastics(organCode + "%", pd1, pd2);
			} else {
				temp = stasticsService.getProvLongUwStastics(organCode + "%", pd1, pd2);
			}
		}
		
		request.setAttribute("cmRst", temp);
		
		Integer tl50 = 0;
		Integer tl30 = 0;
		Integer tl20 = 0;
		Integer tl10 = 0;
		Integer tsc = 0;
		
		for(UwModel uw:temp) {
			tl50 += (uw.getL50()==null?0:uw.getL50());
			tl30 += (uw.getL30()==null?0:uw.getL30());
			tl20 += (uw.getL20()==null?0:uw.getL20());
			tl10 += (uw.getL10()==null?0:uw.getL10());
			tsc += (uw.getSc()==null?0:uw.getSc());
		}
		request.setAttribute("tl50", tl50);
		request.setAttribute("tl30", tl30);
		request.setAttribute("tl20", tl20);
		request.setAttribute("tl10", tl10);
		request.setAttribute("tsc", tsc);
		
		Page page = new Page();
		page.setNumPerPage(50);
		List<Prd> prds = prdService.findAllPrd(page);
		request.setAttribute("prds", prds);
		LOG.debug(" ------------ result size:" + temp.size());
		return UW_LIST;
	}

	@RequiresUser
	@RequestMapping(value="/stastics/underwrite/toXls", method={RequestMethod.GET, RequestMethod.POST})
	public String uwStasticsToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String levelFlag = request.getParameter("levelFlag");
		String statFlag = request.getParameter("statFlag");
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if(organCode == null || organCode.trim().length()<=0) {
			organCode = userOrg.getOrgCode();
		} else if(!organCode.contains(userOrg.getOrgCode())){
			organCode = userOrg.getOrgCode();
		}
		
		boolean isCity = false;
		boolean isNet = false;
		
		if(levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		} else if(levelFlag != null && levelFlag.trim().equals("net")) {
			isNet = true;
		}
		
		String fd = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		if(pd1 == null || pd1.trim().length()<=0) {
			pd1 = fd;
		}
		if(pd2 == null || pd2.trim().length()<=0) {
			pd2 = "9999-12-31";
		}
		
		List<UwModel> temp = null;
		if(statFlag==null || statFlag.trim().equals("sellBack")) {
			if(isNet) {
				temp = stasticsService.getNetUwStastics(organCode + "%", pd1, pd2);
			} else if(isCity) {
				temp = stasticsService.getCityUwStastics(organCode + "%", pd1, pd2);
			} else {
				temp = stasticsService.getProvUwStastics(organCode + "%", pd1, pd2);
			}
		} else {
			if(isNet) {
				temp = stasticsService.getNetLongUwStastics(organCode + "%", pd1, pd2);
			} else if(isCity) {
				temp = stasticsService.getCityLongUwStastics(organCode + "%", pd1, pd2);
			} else {
				temp = stasticsService.getProvLongUwStastics(organCode + "%", pd1, pd2);
			}
		}
		
		request.setAttribute("cmRst", temp);
		
		Integer tl50 = 0;
		Integer tl30 = 0;
		Integer tl20 = 0;
		Integer tl10 = 0;
		Integer tsc = 0;
		
		for(UwModel uw:temp) {
			tl50 += (uw.getL50()==null?0:uw.getL50());
			tl30 += (uw.getL30()==null?0:uw.getL30());
			tl20 += (uw.getL20()==null?0:uw.getL20());
			tl10 += (uw.getL10()==null?0:uw.getL10());
			tsc += (uw.getSc()==null?0:uw.getSc());
		}
		request.setAttribute("tl50", tl50);
		request.setAttribute("tl30", tl30);
		request.setAttribute("tl20", tl20);
		request.setAttribute("tl10", tl10);
		request.setAttribute("tsc", tsc);
		
		LOG.debug(" ------------ result size:" + temp.size());
		return UW_TOXLS;
	}

	@RequiresUser
	@RequestMapping(value="/stastics/underwrite/dtlXls", method={RequestMethod.GET, RequestMethod.POST})
	public String uwDtlToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String statFlag = request.getParameter("statFlag");
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if(organCode == null || organCode.trim().length()<=0) {
			organCode = userOrg.getOrgCode();
		} else if(!organCode.contains(userOrg.getOrgCode())){
			organCode = userOrg.getOrgCode();
		}
		
		String fd = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		if(pd1 == null || pd1.trim().length()<=0) {
			pd1 = fd;
		}
		if(pd2 == null || pd2.trim().length()<=0) {
			pd2 = "9999-12-31";
		}
		
		List<UwDtlModel> temp = null;
		if(statFlag==null || statFlag.trim().equals("sellBack")) {
			temp = stasticsService.getUwDtlStastics(organCode + "%", pd1, pd2);
		} else {
			temp = stasticsService.getLongUwDtlStastics(organCode + "%", pd1, pd2);
		}
		
		request.setAttribute("cmRst", temp);
		LOG.debug(" ------------ result size:" + temp.size());
		return UW_DTL_TOXLS;
	}

	/*
		 * =======================================
		 * tuibao
		 * =======================================
		 * 
		 */
		@RequiresUser
		@RequestMapping(value="/stastics/policy", method={RequestMethod.GET, RequestMethod.POST})
		public String policyStastics(ServletRequest request, Map<String, Object> map) {
			LOG.debug("-------------------here----------");
			String organCode = request.getParameter("orgCode");
			String organName = request.getParameter("name");
			String pd1 = request.getParameter("policyDate1");
			String pd2 = request.getParameter("policyDate2");
			String netFlag = request.getParameter("netFlag");
			String levelFlag = request.getParameter("levelFlag");
			String prdCode = request.getParameter("prdCode");
			String statType = request.getParameter("statType");
			String perm = request.getParameter("perm");
			String staffFlag = request.getParameter("staffFlag");
			String bankName = request.getParameter("bankName");
			String csFlag = request.getParameter("csFlag");
			String saleType = request.getParameter("saleType");
			
			if(statType == null || statType.trim().length()<=0) {
				statType = "Organ";
			}
			if(csFlag== null || csFlag.trim().length()<=0) {
				csFlag = "1";
			}
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
			if(prdCode == null || prdCode.trim().length()<=0) {
				prdCode = "%%";
			}
			
			/*
			String toPrdName = prdCode;
			if(prdCode == null || prdCode.trim().length()<=0) {
				toPrdName = "%%";
			} else {
				Prd prd = prdService.getByPrdCode(prdCode);
				toPrdName = prd.getPrdName();
				if(toPrdName.indexOf("_") > 0) {
					toPrdName = "%" + toPrdName.substring(0, toPrdName.indexOf("_")) + "%";
				} else {
					toPrdName = "%" + toPrdName + "%";
				}
			}
			*/
			String toPerm = perm;
			if(perm == null) {
				toPerm = "年交";
				perm = "1";
			} else if(perm.trim().length()<=0) {
				toPerm = "%%";
			} else if(perm.equals("1")) {
				toPerm = "年交";
			} else {
				toPerm = "趸交";
			}
			String isStaff = staffFlag;
			if(staffFlag == null || staffFlag.trim().length()<=0) {
				isStaff = "%%";
			}
			
			boolean isCity = false;
			boolean isNet = false;
			if(levelFlag == null) {
				if(organCode.length()>=8) {
					levelFlag = "net";
				} else if(organCode.length()>4) {
					levelFlag = "city";
				}
			}
			
			if(levelFlag != null && levelFlag.trim().equals("city")) {
				isCity = true;
			} else if(levelFlag != null && levelFlag.trim().equals("net")) {
				isNet = true;
			}
			
			request.setAttribute("orgCode", organCode);
			request.setAttribute("name", organName);
			request.setAttribute("netFlag", netFlag);
			request.setAttribute("prdCode", prdCode);
			request.setAttribute("levelFlag", levelFlag);
			request.setAttribute("perm", perm);
			request.setAttribute("staffFlag", staffFlag);
			request.setAttribute("statType", statType);
			request.setAttribute("csFlag", csFlag);
			request.setAttribute("saleType", saleType);
			
			boolean hasNet = true;
			if(netFlag == null || netFlag.trim().length()<=0) {
				hasNet = false;
			}
			PolicyStatModel cm = new PolicyStatModel();
			cm.setNetFlag(netFlag);
			cm.setPrdCode(prdCode);
			cm.setLevelFlag(levelFlag);
			cm.setPerm(perm);
			cm.setStaffFlag(staffFlag);
			cm.setStatType(statType);
			cm.setCsFlag(csFlag);
			cm.setSaleType(saleType);
			request.setAttribute("PolicyStatModel", cm);
		
			String fd = StringUtil.getFirstDayOfMonth("yyyy-MM-dd");
			if(pd1 == null || pd1.trim().length()<=0) {
				pd1 = fd;
			}
			request.setAttribute("policyDate1", pd1);
			request.setAttribute("policyDate2", pd2);
			if(pd2 == null || pd2.trim().length()<=0) {
				pd2 = "9999-12-31";
			}
			if(bankName == null || bankName.trim().length()<=0) {
				bankName= "%%";
			} else {
				bankName = "&" + bankName + "%";
			}
			
			List<PolicyStatModel> temp = null;
			//List<TuiBaoModel> temp1 = null;
			if(statType.equals("Organ")) {
				if(isNet) {
					if(hasNet) {
						temp = stasticsService.getPolicyOrganNetStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm, isStaff, bankName, csFlag, saleType);
					} else {
						temp = stasticsService.getPolicyOrganNetStastics(organCode + "%", pd1, pd2, prdCode, toPerm, isStaff, bankName, csFlag, saleType);
					}
				} else if(isCity) {
					if(hasNet) {
						temp = stasticsService.getPolicyOrganStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm, isStaff, bankName, csFlag, saleType);
					} else {
						temp = stasticsService.getPolicyOrganStastics(organCode + "%", pd1, pd2, prdCode, toPerm, isStaff, csFlag, saleType);
					}
				} else {
					if(hasNet) {
						temp = stasticsService.getProvPolicyOrganStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm, isStaff, bankName, csFlag, saleType);
					} else {
						temp = stasticsService.getProvPolicyOrganStastics(organCode + "%", pd1, pd2, prdCode, toPerm, isStaff, csFlag, saleType);
					}
				}
			} else if(statType.equals("Prod")) {
				if(hasNet) {
					temp = stasticsService.getPolicyProdStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm, isStaff, bankName, csFlag, saleType);
				} else {
					temp = stasticsService.getPolicyProdStastics(organCode + "%", pd1, pd2, prdCode, toPerm, isStaff, csFlag, saleType);
				}
			} else {
				if(hasNet) {
					temp = stasticsService.getPolicyFeeFrequencyStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm, isStaff, bankName, csFlag, saleType);
				} else {
					temp = stasticsService.getPolicyFeeFrequencyStastics(organCode + "%", pd1, pd2, prdCode, toPerm, isStaff, csFlag, saleType);
				}
			}
			
			
			request.setAttribute("cmRst", temp);
			String col = "";
			String countStr = "";
			String sumStr = "";
			String countPtStr = "";
			String sumPtStr = "";
			double maxZB = 0;
			int maxTB = 0;
			double sum = 0;
			double count = 0;
			List<Double> countList = new ArrayList<Double>();
			List<Double> sumList = new ArrayList<Double>();
			DecimalFormat df = new DecimalFormat("#.#"); 
			for(PolicyStatModel tcm:temp) {//(isNet?temp1:temp)
				col += "'" + tcm.getStatName() + "',";
				countList.add(tcm.getPolicyCount());
				count += tcm.getPolicyCount();
				countStr += tcm.getPolicyCount() + ",";
				sumList.add(tcm.getPolicyFee());
				sumStr += tcm.getPolicyFee() + ",";
				sum += tcm.getPolicyFee()==null?0:tcm.getPolicyFee();
			}
			for(Double d:countList) {
				countPtStr += df.format((d==null?0:d)/count*100) + ",";
				if((d==null?0:d)/count*100 > maxZB) {
					maxZB = (d==null?0:d)/count*100;
				}
				if(d!=null && d>maxTB) {
					maxTB = d.intValue();
				}
			}
			for(Double d:sumList) {
				sumPtStr += df.format((d==null?0:d)/sum*100) + ",";
				if((d==null?0:d)/count*100 > maxZB) {
					maxZB = (d==null?0:d)/count*100;
				}
				if(d!=null && d>maxTB) {
					maxTB = d.intValue();
				}
			}
			int m = 1;
			for (int i = 0; i < (int) Math.log10(maxTB); i++) {  
			    m *= 10;  
			}  
			// 第一位  
			maxTB = (maxTB / m +1) *m; 
			request.setAttribute("col", col);
			request.setAttribute("countStr", countStr);
			request.setAttribute("sumStr", sumStr);
			request.setAttribute("countPtStr", countPtStr);
			request.setAttribute("sumPtStr", sumPtStr);
			request.setAttribute("maxTB", maxTB);
			request.setAttribute("maxZB", maxZB);
			request.setAttribute("countPt", count);
			request.setAttribute("sumPt", sum);
			
			Page page = new Page();
			page.setNumPerPage(50);
			List<Prd> prds = prdService.findAllPrd(page);
			request.setAttribute("prds", prds);
			LOG.debug(" ------------ result size:" + temp.size());
			return POLICY_LIST;
		}

	@RequiresUser
	@RequestMapping(value="/stastics/policy/toXls", method={RequestMethod.GET, RequestMethod.POST})
	public String policyStasticsToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String netFlag = request.getParameter("netFlag");
		String levelFlag = request.getParameter("levelFlag");
		String prdCode = request.getParameter("prdCode");
		String statType = request.getParameter("statType");
		String perm = request.getParameter("perm");
		String staffFlag = request.getParameter("staffFlag");
		String bankName = request.getParameter("bankName");
		String csFlag = request.getParameter("csFlag");
		String saleType = request.getParameter("saleType");
		
		if(statType == null || statType.trim().length()<=0) {
			statType = "Organ";
		}
		if(csFlag== null || csFlag.trim().length()<=0) {
			csFlag = "1";
		}
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if(organCode == null || organCode.trim().length()<=0) {
			organCode = userOrg.getOrgCode();
		} else if(!organCode.contains(userOrg.getOrgCode())){
			organCode = userOrg.getOrgCode();
		}
		if(prdCode == null || prdCode.trim().length()<=0) {
			prdCode = "%%";
		}
		
		/*
		String toPrdName = prdCode;
		if(prdCode == null || prdCode.trim().length()<=0) {
			toPrdName = "%%";
		} else {
			Prd prd = prdService.getByPrdCode(prdCode);
			toPrdName = prd.getPrdName();
			if(toPrdName.indexOf("_") > 0) {
				toPrdName = "%" + toPrdName.substring(0, toPrdName.indexOf("_")) + "%";
			} else {
				toPrdName = "%" + toPrdName + "%";
			}
		}
		*/
		String toPerm = perm;
		if(perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if(perm.trim().length()<=0) {
			toPerm = "%%";
		} else if(perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		String isStaff = staffFlag;
		if(staffFlag == null || staffFlag.trim().length()<=0) {
			isStaff = "%%";
		}
		
		boolean isCity = false;
		boolean isNet = false;
		if(levelFlag == null) {
			if(organCode.length()>=8) {
				levelFlag = "net";
			} else if(organCode.length()>4) {
				levelFlag = "city";
			}
		}
		
		if(levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		} else if(levelFlag != null && levelFlag.trim().equals("net")) {
			isNet = true;
		}
		
		boolean hasNet = true;
		if(netFlag == null || netFlag.trim().length()<=0) {
			hasNet = false;
		}
	
		String fd = StringUtil.getFirstDayOfYear("yyyy-MM-dd");
		if(pd1 == null || pd1.trim().length()<=0) {
			pd1 = fd;
		}
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		if(pd2 == null || pd2.trim().length()<=0) {
			pd2 = "9999-12-31";
		}
		if(bankName == null || bankName.trim().length()<=0) {
			bankName= "%%";
		} else {
			bankName = "&" + bankName + "%";
		}
		
		List<PolicyStatModel> temp = null;
		//List<TuiBaoModel> temp1 = null;
		if(statType.equals("Organ")) {
			if(isNet) {
				if(hasNet) {
					temp = stasticsService.getPolicyOrganNetStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm, isStaff, bankName, csFlag, saleType);
				} else {
					temp = stasticsService.getPolicyOrganNetStastics(organCode + "%", pd1, pd2, prdCode, toPerm, isStaff, bankName, csFlag, saleType);
				}
			} else if(isCity) {
				if(hasNet) {
					temp = stasticsService.getPolicyOrganStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm, isStaff, bankName, csFlag, saleType);
				} else {
					temp = stasticsService.getPolicyOrganStastics(organCode + "%", pd1, pd2, prdCode, toPerm, isStaff, csFlag, saleType);
				}
			} else {
				if(hasNet) {
					temp = stasticsService.getProvPolicyOrganStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm, isStaff, bankName, csFlag, saleType);
				} else {
					temp = stasticsService.getProvPolicyOrganStastics(organCode + "%", pd1, pd2, prdCode, toPerm, isStaff, csFlag, saleType);
				}
			}
		} else if(statType.equals("Prod")) {
			if(hasNet) {
				temp = stasticsService.getPolicyProdStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm, isStaff, bankName, csFlag, saleType);
			} else {
				temp = stasticsService.getPolicyProdStastics(organCode + "%", pd1, pd2, prdCode, toPerm, isStaff, csFlag, saleType);
			}
		} else {
			if(hasNet) {
				temp = stasticsService.getPolicyFeeFrequencyStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm, isStaff, bankName, csFlag, saleType);
			} else {
				temp = stasticsService.getPolicyFeeFrequencyStastics(organCode + "%", pd1, pd2, prdCode, toPerm, isStaff, csFlag, saleType);
			}
		}
		
		request.setAttribute("cmRst", temp);
		double sum = 0;
		double count = 0;
		for(PolicyStatModel tcm:temp) {//(isNet?temp1:temp)
			count += tcm.getPolicyCount();
			sum += tcm.getPolicyFee()==null?0:tcm.getPolicyFee();
		}
		// 第一位  
		request.setAttribute("countPt", count);
		request.setAttribute("sumPt", sum);
		
		request.setAttribute("cmRst", temp);
		LOG.debug(" ------------ result size:" + temp.size());
		return POLICY_TOXLS;
	}
}
