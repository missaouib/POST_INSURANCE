package com.gdpost.web.controller.component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.StringUtil;
import com.gdpost.web.controller.insurance.BaseDataController;
import com.gdpost.web.entity.basedata.Prd;
import com.gdpost.web.entity.component.CheckModel;
import com.gdpost.web.entity.component.PolicyStatModel;
import com.gdpost.web.entity.component.QyCheckModel;
import com.gdpost.web.entity.component.StaffDtlModel;
import com.gdpost.web.entity.component.StaffModel;
import com.gdpost.web.entity.component.TuiBaoDtlModel;
import com.gdpost.web.entity.component.TuiBaoModel;
import com.gdpost.web.entity.component.UwDtlModel;
import com.gdpost.web.entity.component.UwModel;
import com.gdpost.web.entity.insurance.StasticsArea;
import com.gdpost.web.entity.insurance.StasticsCity;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.service.OrganizationService;
import com.gdpost.web.service.component.StasticsService;
import com.gdpost.web.service.insurance.BaseDataService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.StatusDefine.QY_STATUS;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/component")
public class StasticsController {
	private static final Logger LOG = LoggerFactory.getLogger(BaseDataController.class);

	@Autowired
	private StasticsService stasticsService;
	@Autowired
	private BaseDataService prdService;
	@Autowired
	private OrganizationService orgService;

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
	
	private static final String CHECK_LIST = "insurance/stastics/check/stats";
	private static final String CHECK_TOXLS = "insurance/stastics/check/check_stat_xls";
	//private static final String CHECK_WRITE_TOXLS = "insurance/stastics/check/check_write_dtl_xls";
	//private static final String CHECK_RECORD_TOXLS = "insurance/stastics/check/check_record_dtl_xls";
	
	private static final String TRUTH_LIST = "insurance/stastics/check/truthStat";
	private static final String TRUTH_LIST_TOXLS = "insurance/stastics/check/truthStat_xls";
	//private static final String TRUTH_DTL_TOXLS = "insurance/stastics/check/truthStat_dtl_xls";
	
	private static final String PRINT_LIST = "insurance/stastics/check/printStat";
	private static final String PRINT_LIST_TOXLS = "insurance/stastics/check/printStat_xls";
	
	private static final String CHECK_DTL_XLS = "insurance/stastics/check/check_dtl_xls";

	private static final String CITY_STAT_LIST = "insurance/stastics/check/cityStat";
	private static final String CITY_STAT_TOXLS = "insurance/stastics/check/cityStat_xls";
	private static final String AREA_STAT_LIST = "insurance/stastics/check/areaStat";
	private static final String AREA_STAT_TOXLS = "insurance/stastics/check/areaStat_xls";
	
	private static final String STATUS_STAT_LIST = "insurance/stastics/check/statusStat";
	private static final String STATUS_STAT_TOXLS = "insurance/stastics/check/statusStat_xls";
	/*
	 * =======================================
	 *  tuibao
	 * =======================================
	 * 
	 */
	@RequiresUser
	@Log(message="进行退保分析！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/tuibao", method = { RequestMethod.GET, RequestMethod.POST })
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
		// String prdName = request.getParameter("prdName");
		String perm = request.getParameter("perm");
		String staffFlag = request.getParameter("staffFlag");
		String bankName = request.getParameter("bankName");
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}

		if (prdCode == null || prdCode.trim().length() <= 0) {
			prdCode = "%%";
		}

		String toPerm = perm;
		if (perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if (perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		String isStaff = staffFlag;
		if (staffFlag == null || staffFlag.trim().length() <= 0) {
			isStaff = "%%";
		}

		boolean isCity = false;
		boolean isNet = false;
		if (levelFlag == null) {
			if (organCode.length() >= 8) {
				levelFlag = "net";
			} else if (organCode.length() > 4) {
				levelFlag = "city";
			}
		}

		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		} else if (levelFlag != null && levelFlag.trim().equals("net")) {
			isNet = true;
		}

		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		request.setAttribute("netFlag", netFlag);
		request.setAttribute("prdCode", prdCode);
		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("perm", perm);
		request.setAttribute("staffFlag", staffFlag);
		request.setAttribute("duration", duration);

		boolean hasNet = true;
		if (netFlag == null || netFlag.trim().length() <= 0) {
			hasNet = false;
		}
		TuiBaoModel cm = new TuiBaoModel();
		cm.setNetFlag(netFlag);
		cm.setPrdCode(prdCode);
		cm.setLevelFlag(levelFlag);
		cm.setPerm(perm);
		cm.setStaffFlag(staffFlag);
		cm.setDuration(duration);
		request.setAttribute("TuiBaoModel", cm);

		String fd = StringUtil.getFirstDayOfYear("yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}
		if (csd1 == null || csd1.trim().length() <= 0) {
			csd1 = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		}
		request.setAttribute("csDate1", csd1);
		request.setAttribute("csDate2", csd2);
		if (csd2 == null || csd2.trim().length() <= 0) {
			csd2 = "9999-12-31";
		}
		if (bankName == null || bankName.trim().length() <= 0) {
			bankName = "%%";
		} else {
			bankName = "&" + bankName + "%";
		}

		List<TuiBaoModel> temp = null;
		// List<TuiBaoModel> temp1 = null;
		if(levelFlag != null && levelFlag.equals("prov")) {
			organCode="8644";
		}
		if (isNet) {
			if (hasNet) {
				temp = stasticsService.getNetTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1,
						csd2, netFlag, prdCode, toPerm, isStaff, bankName, duration);
			} else {
				temp = stasticsService.getNetTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1,
						csd2, prdCode, toPerm, isStaff, bankName, duration);
			}
		} else if (isCity) {
			if (hasNet) {
				temp = stasticsService.getTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2,
						netFlag, prdCode, toPerm, isStaff, duration);
			} else {
				temp = stasticsService.getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(organCode + "%", pd1, pd2,
						csd1, csd2, prdCode, toPerm, isStaff, duration);
			}
		} else {
			if (hasNet) {
				temp = stasticsService.getProvTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1,
						csd2, netFlag, prdCode, toPerm, isStaff, duration);
			} else {
				temp = stasticsService.getProvTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(organCode + "%", pd1, pd2,
						csd1, csd2, prdCode, toPerm, isStaff, duration);
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
		for (TuiBaoModel tcm : temp) {// (isNet?temp1:temp)
			col += "'" + tcm.getOrganName() + "',";
			sumTb += tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee();
			totalTb += tcm.getSumPolicyFee();
			totalCS += tcm.getSumCsFee() == null ? 0 : tcm.getSumCsFee();
			zhanbi += df.format(tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee() / tcm.getSumPolicyFee() * 100)
					+ ",";
			if (((tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee()) / tcm.getSumPolicyFee() * 100 + 1) > maxZB) {
				maxZB = Math.ceil(tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee() / tcm.getSumPolicyFee() * 100)
						+ 1;
			}
			tuibao += (tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee()) / 10000 + ",";
			if ((tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee()) / 10000 > maxTB) {
				maxTB = (int) Math.ceil(tcm.getPolicyFee() / 10000);
			}
		}
		int m = 1;
		for (int i = 0; i < (int) Math.log10(maxTB); i++) {
			m *= 10;
		}
		// 第一位
		maxTB = (maxTB / m + 1) * m;
		request.setAttribute("col", col);
		request.setAttribute("zhanbi", zhanbi);
		request.setAttribute("tuibao", tuibao);
		request.setAttribute("maxZB", maxZB);
		request.setAttribute("maxTB", maxTB);
		request.setAttribute("sumTb", sumTb / 10000);
		request.setAttribute("totalCS", totalCS / 10000);
		request.setAttribute("totalTb", totalTb / 10000);

		Page page = new Page();
		page.setNumPerPage(50);
		List<Prd> prds = prdService.findAllPrd(page);
		request.setAttribute("prds", prds);
		LOG.debug(" ------------ result size:" + temp.size());
		return TUIBAO_LIST;
	}

	@RequiresUser
	@Log(message="下载了退保分析结果！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/tuibao/toXls", method = { RequestMethod.GET, RequestMethod.POST })
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
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
		}

		if (prdCode == null || prdCode.trim().length() <= 0) {
			prdCode = "%%";
		}
		String toPerm = perm;
		if (perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if (perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		String isStaff = staffFlag;
		if (staffFlag == null || staffFlag.trim().length() <= 0) {
			isStaff = "%%";
		}
		boolean isCity = false;
		boolean isNet = false;
		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		} else if (levelFlag != null && levelFlag.trim().equals("net")) {
			isNet = true;
		}

		boolean hasNet = true;
		if (netFlag == null || netFlag.trim().length() <= 0) {
			hasNet = false;
		}

		String fd = StringUtil.getFirstDayOfYear("yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}
		if (csd1 == null || csd1.trim().length() <= 0) {
			csd1 = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		}
		if (csd2 == null || csd2.trim().length() <= 0) {
			csd2 = "9999-12-31";
		}
		if (bankName == null || bankName.trim().length() <= 0) {
			bankName = "%%";
		} else {
			bankName = "&" + bankName + "%";
		}

		List<TuiBaoModel> temp = null;
		
		if(levelFlag != null && levelFlag.equals("prov")) {
			organCode="8644";
		}
		
		if (isNet) {
			if (hasNet) {
				temp = stasticsService.getNetTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1,
						csd2, netFlag, prdCode, toPerm, isStaff, bankName, duration);
			} else {
				temp = stasticsService.getNetTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1,
						csd2, prdCode, toPerm, isStaff, bankName, duration);
			}
		} else if (isCity) {
			if (hasNet) {
				temp = stasticsService.getTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2,
						netFlag, prdCode, toPerm, isStaff, duration);
			} else {
				temp = stasticsService.getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(organCode + "%", pd1, pd2,
						csd1, csd2, prdCode, toPerm, isStaff, duration);
			}
		} else {
			if (hasNet) {
				temp = stasticsService.getProvTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1,
						csd2, netFlag, prdCode, toPerm, isStaff, duration);
			} else {
				temp = stasticsService.getProvTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(organCode + "%", pd1, pd2,
						csd1, csd2, prdCode, toPerm, isStaff, duration);
			}
		}

		request.setAttribute("cmRst", temp);

		double sumTb = 0;
		double totalTb = 0;
		double totalCS = 0;
		for (TuiBaoModel tcm : temp) {// (isNet?temp1:temp)
			sumTb += tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee();
			totalTb += tcm.getSumPolicyFee();
			totalCS += tcm.getSumCsFee() == null ? 0 : tcm.getSumCsFee();
		}
		// 第一位
		request.setAttribute("sumTb", sumTb / 10000);
		request.setAttribute("totalCS", totalCS / 10000);
		request.setAttribute("totalTb", totalTb / 10000);

		LOG.debug(" ------------ result size:" + temp.size());
		return TUIBAO_TOXLS;
	}

	@Log(message="下载了退保分析明细数据！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequiresUser
	@RequestMapping(value = "/stastics/tuibao/dtlXls", method = { RequestMethod.GET, RequestMethod.POST })
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
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
		}
		if (prdCode == null || prdCode.trim().length() <= 0) {
			prdCode = "%%";
		}
		String toPerm = perm;
		if (perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if (perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		String isStaff = staffFlag;
		if (staffFlag == null || staffFlag.trim().length() <= 0) {
			isStaff = "%%";
		}

		if (netFlag == null || netFlag.trim().length() <= 0) {
			netFlag = "%%";
		}

		String fd = StringUtil.getFirstDayOfYear("yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}
		if (csd1 == null || csd1.trim().length() <= 0) {
			csd1 = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		}
		if (csd2 == null || csd2.trim().length() <= 0) {
			csd2 = "9999-12-31";
		}
		if (bankName == null || bankName.trim().length() <= 0) {
			bankName = "%%";
		} else {
			bankName = "&" + bankName + "%";
		}

		List<TuiBaoDtlModel> temp = stasticsService.getTuiBaoWarnningDetailWithBankCode(organCode + "%", pd1, pd2, csd1,
				csd2, netFlag, prdCode, toPerm, isStaff, bankName, duration);

		request.setAttribute("cmRst", temp);
		LOG.debug(" ------------ result size:" + temp.size());
		return TUIBAO_DTL_TOXLS;
	}

	@RequiresUser
	@Log(message="下载了退保分析数据犹撤数据！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/tuibao/csdtlXls", method = { RequestMethod.GET, RequestMethod.POST })
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
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
		}
		if (prdCode == null || prdCode.trim().length() <= 0) {
			prdCode = "%%";
		}
		String toPerm = perm;
		if (perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if (perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		String isStaff = staffFlag;
		if (staffFlag == null || staffFlag.trim().length() <= 0) {
			isStaff = "%%";
		}

		if (netFlag == null || netFlag.trim().length() <= 0) {
			netFlag = "%%";
		}

		String fd = StringUtil.getFirstDayOfYear("yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}
		if (csd1 == null || csd1.trim().length() <= 0) {
			csd1 = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		}
		if (csd2 == null || csd2.trim().length() <= 0) {
			csd2 = "9999-12-31";
		}
		if (bankName == null || bankName.trim().length() <= 0) {
			bankName = "%%";
		} else {
			bankName = "&" + bankName + "%";
		}

		List<TuiBaoDtlModel> temp = stasticsService.getTuiBaoCsDetailWithBankCode(organCode + "%", pd1, pd2, csd1, csd2,
				netFlag, prdCode, toPerm, isStaff, bankName, duration);

		request.setAttribute("cmRst", temp);
		LOG.debug(" ------------ result size:" + temp.size());
		return TUIBAO_DTL_TOXLS;
	}

	/*
	 * =======================================
	 *  staff
	 * =======================================
	 * 
	 */
	@RequiresUser
	@Log(message="进行员工单分析！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/staff", method = { RequestMethod.GET, RequestMethod.POST })
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
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}
		if (prdCode == null || prdCode.trim().length() <= 0) {
			prdCode = "%%";
		}

		String toPerm = perm;
		if (perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if (perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}

		boolean isCity = false;
		if (organCode.length() > 4) {
			levelFlag = "city";
		}

		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		}

		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		request.setAttribute("netFlag", netFlag);
		request.setAttribute("prdCode", prdCode);
		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("perm", perm);
		request.setAttribute("duration", duration);

		boolean hasNet = true;
		if (netFlag == null || netFlag.trim().length() <= 0) {
			hasNet = false;
		}
		StaffModel cm = new StaffModel();
		cm.setNetFlag(netFlag);
		cm.setPrdCode(prdCode);
		cm.setLevelFlag(levelFlag);
		cm.setPerm(perm);
		cm.setDuration(duration);
		request.setAttribute("StaffModel", cm);

		String fd = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}

		List<StaffModel> temp = null;
		if(levelFlag != null && levelFlag.equals("prov")) {
			organCode="8644";
		}
		if (isCity) {
			if (hasNet) {
				temp = stasticsService.getStaffCountWithPolicyDate(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm,
						duration);
			} else {
				temp = stasticsService.getStaffCountWithPolicyDateNoBankCode(organCode + "%", pd1, pd2, prdCode, toPerm,
						duration);
			}
		} else {
			if (hasNet) {
				temp = stasticsService.getProvStaffCountWithPolicyDate(organCode + "%", pd1, pd2, netFlag, prdCode,
						toPerm, duration);
			} else {
				temp = stasticsService.getProvStaffCountWithPolicyDateNoBankCode(organCode + "%", pd1, pd2, prdCode,
						toPerm, duration);
			}
		}

		request.setAttribute("cmRst", temp);
		String col = "";
		String zhanbi = "";
		String tuibao = "";
		double maxZB = 0;
		int maxTB = 0;
		int csumTb = 0;// 员工件数
		int ctotalTb = 0;// 总件数
		double ssumTb = 0;// 员工保费
		double stotalTb = 0;// 总保费
		DecimalFormat df = new DecimalFormat("#.#");
		for (StaffModel tcm : temp) {
			col += "'" + tcm.getOrganName() + "',";
			csumTb += tcm.getStaffCount() == null ? 0 : tcm.getStaffCount();// 员工件数
			ctotalTb += tcm.getSumStaffCount();// 总件数
			ssumTb += tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee();// 员工保费
			stotalTb += tcm.getSumPolicyFee();// 总保费
			zhanbi += df.format((tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee()) / tcm.getSumPolicyFee() * 100)
					+ ",";
			if ((tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee()) / tcm.getSumPolicyFee() * 100 + 1 > maxZB) {
				maxZB = Math.ceil(((tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee()) / tcm.getSumPolicyFee()) * 100)
						+ 1;
			}
			tuibao += (tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee()) / 10000 + ",";
			if ((tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee()) / 10000 > maxTB) {
				maxTB = (int) Math.ceil((tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee()) / 10000);
			}
		}
		int m = 1;
		for (int i = 0; i < (int) Math.log10(maxTB); i++) {
			m *= 10;
		}
		// 第一位
		maxTB = (maxTB / m + 1) * m;
		request.setAttribute("col", col);
		request.setAttribute("zhanbi", zhanbi);
		request.setAttribute("staff", tuibao);
		request.setAttribute("maxZB", maxZB);
		request.setAttribute("maxTB", maxTB);
		request.setAttribute("csumTb", csumTb);
		request.setAttribute("ctotalTb", ctotalTb);
		request.setAttribute("ssumTb", ssumTb / 10000);
		request.setAttribute("stotalTb", stotalTb / 10000);

		Page page = new Page();
		page.setNumPerPage(50);
		List<Prd> prds = prdService.findAllPrd(page);
		request.setAttribute("prds", prds);
		LOG.debug(" ------------ result size:" + temp.size());
		return STAFF_LIST;
	}

	@RequiresUser
	@Log(message="员工单分析结果导出！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/staff/toXls", method = { RequestMethod.GET, RequestMethod.POST })
	public String staffStasticsToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String netFlag = request.getParameter("netFlag");
		String levelFlag = request.getParameter("levelFlag");
		String prdCode = request.getParameter("prdCode");
		String perm = request.getParameter("perm");
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
		}
		if (prdCode == null || prdCode.trim().length() <= 0) {
			prdCode = "%%";
		}
		String toPerm = perm;
		if (perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if (perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		boolean isCity = false;
		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		}

		boolean hasNet = true;
		if (netFlag == null || netFlag.trim().length() <= 0) {
			hasNet = false;
		}

		String fd = StringUtil.getFirstDayOfYear("yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}

		List<StaffModel> temp = null;
		if(levelFlag != null && levelFlag.equals("prov")) {
			organCode="8644";
		}
		if (isCity) {
			if (hasNet) {
				temp = stasticsService.getStaffCountWithPolicyDate(organCode + "%", pd1, pd2, netFlag, prdCode, toPerm,
						duration);
			} else {
				temp = stasticsService.getStaffCountWithPolicyDateNoBankCode(organCode + "%", pd1, pd2, prdCode, toPerm,
						duration);
			}
		} else {
			if (hasNet) {
				temp = stasticsService.getProvStaffCountWithPolicyDate(organCode + "%", pd1, pd2, netFlag, prdCode,
						toPerm, duration);
			} else {
				temp = stasticsService.getProvStaffCountWithPolicyDateNoBankCode(organCode + "%", pd1, pd2, prdCode,
						toPerm, duration);
			}
		}

		request.setAttribute("cmRst", temp);

		int csumTb = 0;
		int ctotalTb = 0;
		double ssumTb = 0;
		double stotalTb = 0;
		for (StaffModel tcm : temp) {
			csumTb += tcm.getStaffCount() == null ? 0 : tcm.getStaffCount();
			ctotalTb += tcm.getSumStaffCount();
			ssumTb += tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee();
			stotalTb += tcm.getSumPolicyFee();
		}
		request.setAttribute("csumTb", csumTb);
		request.setAttribute("ctotalTb", ctotalTb);
		request.setAttribute("ssumTb", ssumTb / 10000);
		request.setAttribute("stotalTb", stotalTb / 10000);

		LOG.debug(" ------------ result size:" + temp.size());
		return STAFF_TOXLS;
	}

	@RequiresUser
	@Log(message="员工单分析明细数据导出！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/staff/dtlXls", method = { RequestMethod.GET, RequestMethod.POST })
	public String staffDtlToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String netFlag = request.getParameter("netFlag");
		String prdCode = request.getParameter("prdCode");
		String perm = request.getParameter("perm");
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
		}
		if (prdCode == null || prdCode.trim().length() <= 0) {
			prdCode = "%%";
		}
		if (netFlag == null || netFlag.trim().length() <= 0) {
			netFlag = "%%";
		}
		String toPerm = perm;
		if (perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if (perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		String fd = StringUtil.getFirstDayOfYear("yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}

		List<StaffDtlModel> temp = stasticsService.getStaffDetailWithPolicyDate(organCode + "%", pd1, pd2, netFlag,
				prdCode, toPerm, duration);

		request.setAttribute("cmRst", temp);
		LOG.debug(" ------------ result size:" + temp.size());
		return STAFF_DTL_TOXLS;
	}

	/*
	 * =======================================
	 *  underwrite
	 * =======================================
	 * 
	 */
	@RequiresUser
	@Log(message="进行人核件分析！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/underwrite", method = { RequestMethod.GET, RequestMethod.POST })
	public String getUWStastics(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String organName = request.getParameter("name");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String levelFlag = request.getParameter("levelFlag");
		String statFlag = request.getParameter("statFlag");

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}

		boolean isCity = false;
		boolean isNet = false;
		if (levelFlag == null) {
			if (organCode.length() >= 8) {
				levelFlag = "net";
			} else if (organCode.length() > 4) {
				levelFlag = "city";
			}
		}

		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		} else if (levelFlag != null && levelFlag.trim().equals("net")) {
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
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}

		List<UwModel> temp = null;
		if (statFlag == null || statFlag.trim().equals("sellBack")) {
			if (isNet) {
				temp = stasticsService.getNetUwStastics(organCode + "%", pd1, pd2);
			} else if (isCity) {
				temp = stasticsService.getCityUwStastics(organCode + "%", pd1, pd2);
			} else {
				temp = stasticsService.getProvUwStastics(organCode + "%", pd1, pd2);
			}
		} else {
			if (isNet) {
				temp = stasticsService.getNetLongUwStastics(organCode + "%", pd1, pd2);
			} else if (isCity) {
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

		for (UwModel uw : temp) {
			tl50 += (uw.getL50() == null ? 0 : uw.getL50());
			tl30 += (uw.getL30() == null ? 0 : uw.getL30());
			tl20 += (uw.getL20() == null ? 0 : uw.getL20());
			tl10 += (uw.getL10() == null ? 0 : uw.getL10());
			tsc += (uw.getSc() == null ? 0 : uw.getSc());
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
	@Log(message="人核件分析结果导出！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/underwrite/toXls", method = { RequestMethod.GET, RequestMethod.POST })
	public String uwStasticsToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String levelFlag = request.getParameter("levelFlag");
		String statFlag = request.getParameter("statFlag");

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
		}

		boolean isCity = false;
		boolean isNet = false;

		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		} else if (levelFlag != null && levelFlag.trim().equals("net")) {
			isNet = true;
		}

		String fd = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}

		List<UwModel> temp = null;
		if (statFlag == null || statFlag.trim().equals("sellBack")) {
			if (isNet) {
				temp = stasticsService.getNetUwStastics(organCode + "%", pd1, pd2);
			} else if (isCity) {
				temp = stasticsService.getCityUwStastics(organCode + "%", pd1, pd2);
			} else {
				temp = stasticsService.getProvUwStastics(organCode + "%", pd1, pd2);
			}
		} else {
			if (isNet) {
				temp = stasticsService.getNetLongUwStastics(organCode + "%", pd1, pd2);
			} else if (isCity) {
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

		for (UwModel uw : temp) {
			tl50 += (uw.getL50() == null ? 0 : uw.getL50());
			tl30 += (uw.getL30() == null ? 0 : uw.getL30());
			tl20 += (uw.getL20() == null ? 0 : uw.getL20());
			tl10 += (uw.getL10() == null ? 0 : uw.getL10());
			tsc += (uw.getSc() == null ? 0 : uw.getSc());
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
	@Log(message="人核件明细数据导出！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/underwrite/dtlXls", method = { RequestMethod.GET, RequestMethod.POST })
	public String uwDtlToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String statFlag = request.getParameter("statFlag");

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
		}

		String fd = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}

		List<UwDtlModel> temp = null;
		if (statFlag == null || statFlag.trim().equals("sellBack")) {
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
	 *  tuibao
	 * =======================================
	 * 
	 */
	@RequiresUser
	@Log(message="进行保单分析！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/policy", method = { RequestMethod.GET, RequestMethod.POST })
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
		String status = request.getParameter("status");
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);

		if (statType == null || statType.trim().length() <= 0) {
			statType = "Organ";
		}
		if (csFlag == null || csFlag.trim().length() <= 0) {
			csFlag = "1";
		}
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}
		if (prdCode == null || prdCode.trim().length() <= 0) {
			prdCode = "%%";
		}

		String toPerm = perm;
		if (perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if (perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		String isStaff = staffFlag;
		if (staffFlag == null || staffFlag.trim().length() <= 0) {
			isStaff = "%%";
		}

		boolean isCity = false;
		boolean isNet = false;
		if (levelFlag == null) {
			if (organCode.length() >= 8) {
				levelFlag = "net";
			} else if (organCode.length() > 4) {
				levelFlag = "city";
			}
		}

		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		} else if (levelFlag != null && levelFlag.trim().equals("net")) {
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
		request.setAttribute("saleType2", saleType == null || saleType.length() < 4 ? "" : saleType.substring(0, 4));
		request.setAttribute("status", status);
		request.setAttribute("duration", duration);

		boolean hasNet = true;
		if (netFlag == null || netFlag.trim().length() <= 0) {
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
		cm.setStatus(status);
		cm.setDuration(duration);
		request.setAttribute("PolicyStatModel", cm);

		String fd = StringUtil.getFirstDayOfMonth("yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}
		if (bankName == null || bankName.trim().length() <= 0) {
			bankName = "%%";
		} else {
			bankName = "%" + bankName + "%";
		}

		List<PolicyStatModel> temp = null;
		// List<TuiBaoModel> temp1 = null;
		if (statType.equals("Organ")) {
			if (isNet) {
				if (hasNet) {
					temp = stasticsService.getPolicyOrganNetStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag,
							prdCode, toPerm, isStaff, bankName, csFlag, saleType, status, duration);
				} else {
					temp = stasticsService.getPolicyOrganNetStastics(organCode + "%", pd1, pd2, prdCode, toPerm,
							isStaff, bankName, csFlag, saleType, status, duration);
				}
			} else if (isCity) {
				if (hasNet) {
					temp = stasticsService.getPolicyOrganStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag,
							prdCode, toPerm, isStaff, bankName, csFlag, saleType, status, duration);
				} else {
					temp = stasticsService.getPolicyOrganStastics(organCode + "%", pd1, pd2, prdCode, toPerm, isStaff,
							csFlag, saleType, status, duration);
				}
			} else {
				if (hasNet) {
					temp = stasticsService.getProvPolicyOrganStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag,
							prdCode, toPerm, isStaff, bankName, csFlag, saleType, status, duration);
				} else {
					temp = stasticsService.getProvPolicyOrganStastics(organCode + "%", pd1, pd2, prdCode, toPerm,
							isStaff, csFlag, saleType, status, duration);
				}
			}
		} else if (statType.equals("Prod")) {
			if (hasNet) {
				temp = stasticsService.getPolicyProdStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag, prdCode,
						toPerm, isStaff, bankName, csFlag, saleType, status, duration);
			} else {
				temp = stasticsService.getPolicyProdStastics(organCode + "%", pd1, pd2, prdCode, toPerm, isStaff,
						csFlag, saleType, status, duration);
			}
		} else {
			if (hasNet) {
				temp = stasticsService.getPolicyFeeFrequencyStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag,
						prdCode, toPerm, isStaff, bankName, csFlag, saleType, status, duration);
			} else {
				temp = stasticsService.getPolicyFeeFrequencyStastics(organCode + "%", pd1, pd2, prdCode, toPerm,
						isStaff, csFlag, saleType, status, duration);
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
		for (PolicyStatModel tcm : temp) {// (isNet?temp1:temp)
			col += "'" + tcm.getStatName() + "',";
			countList.add(tcm.getPolicyCount());
			count += tcm.getPolicyCount();
			countStr += tcm.getPolicyCount() + ",";
			sumList.add(tcm.getPolicyFee());
			sumStr += tcm.getPolicyFee() + ",";
			sum += tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee();
		}
		for (Double d : countList) {
			countPtStr += df.format((d == null ? 0 : d) / count * 100) + ",";
			if ((d == null ? 0 : d) / count * 100 > maxZB) {
				maxZB = (d == null ? 0 : d) / count * 100;
			}
			if (d != null && d > maxTB) {
				maxTB = d.intValue();
			}
		}
		for (Double d : sumList) {
			sumPtStr += df.format((d == null ? 0 : d) / sum * 100) + ",";
			if ((d == null ? 0 : d) / count * 100 > maxZB) {
				maxZB = (d == null ? 0 : d) / count * 100;
			}
			if (d != null && d > maxTB) {
				maxTB = d.intValue();
			}
		}
		int m = 1;
		for (int i = 0; i < (int) Math.log10(maxTB); i++) {
			m *= 10;
		}
		// 第一位
		maxTB = (maxTB / m + 1) * m;
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
	@Log(message="保单分析结果导出！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/policy/toXls", method = { RequestMethod.GET, RequestMethod.POST })
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
		String status = request.getParameter("status");
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);

		if (statType == null || statType.trim().length() <= 0) {
			statType = "Organ";
		}
		if (csFlag == null || csFlag.trim().length() <= 0) {
			csFlag = "1";
		}
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
		}
		if (prdCode == null || prdCode.trim().length() <= 0) {
			prdCode = "%%";
		}

		String toPerm = perm;
		if (perm == null) {
			toPerm = "年交";
			perm = "1";
		} else if (perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		String isStaff = staffFlag;
		if (staffFlag == null || staffFlag.trim().length() <= 0) {
			isStaff = "%%";
		}

		if (saleType == null || saleType.trim().length() <= 0) {
			saleType = "%%";
		} else {
			saleType = saleType + "%";
		}

		if (status == null || status.trim().length() <= 0) {
			status = "%%";
		}

		boolean isCity = false;
		boolean isNet = false;
		if (levelFlag == null) {
			if (organCode.length() >= 8) {
				levelFlag = "net";
			} else if (organCode.length() > 4) {
				levelFlag = "city";
			}
		}

		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		} else if (levelFlag != null && levelFlag.trim().equals("net")) {
			isNet = true;
		}

		boolean hasNet = true;
		if (netFlag == null || netFlag.trim().length() <= 0) {
			hasNet = false;
		}

		String fd = StringUtil.getFirstDayOfMonth("yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}
		if (bankName == null || bankName.trim().length() <= 0) {
			bankName = "%%";
		} else {
			bankName = "%" + bankName + "%";
		}

		List<PolicyStatModel> temp = null;
		// List<TuiBaoModel> temp1 = null;
		if (statType.equals("Organ")) {
			if (isNet) {
				if (hasNet) {
					temp = stasticsService.getPolicyOrganNetStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag,
							prdCode, toPerm, isStaff, bankName, csFlag, saleType, status, duration);
				} else {
					temp = stasticsService.getPolicyOrganNetStastics(organCode + "%", pd1, pd2, prdCode, toPerm,
							isStaff, bankName, csFlag, saleType, status, duration);
				}
			} else if (isCity) {
				if (hasNet) {
					temp = stasticsService.getPolicyOrganStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag,
							prdCode, toPerm, isStaff, bankName, csFlag, saleType, status, duration);
				} else {
					temp = stasticsService.getPolicyOrganStastics(organCode + "%", pd1, pd2, prdCode, toPerm, isStaff,
							csFlag, saleType, status, duration);
				}
			} else {
				if (hasNet) {
					temp = stasticsService.getProvPolicyOrganStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag,
							prdCode, toPerm, isStaff, bankName, csFlag, saleType, status, duration);
				} else {
					temp = stasticsService.getProvPolicyOrganStastics(organCode + "%", pd1, pd2, prdCode, toPerm,
							isStaff, csFlag, saleType, status, duration);
				}
			}
		} else if (statType.equals("Prod")) {
			if (hasNet) {
				temp = stasticsService.getPolicyProdStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag, prdCode,
						toPerm, isStaff, bankName, csFlag, saleType, status, duration);
			} else {
				temp = stasticsService.getPolicyProdStastics(organCode + "%", pd1, pd2, prdCode, toPerm, isStaff,
						csFlag, saleType, status, duration);
			}
		} else {
			if (hasNet) {
				temp = stasticsService.getPolicyFeeFrequencyStasticsWithBankCode(organCode + "%", pd1, pd2, netFlag,
						prdCode, toPerm, isStaff, bankName, csFlag, saleType, status, duration);
			} else {
				temp = stasticsService.getPolicyFeeFrequencyStastics(organCode + "%", pd1, pd2, prdCode, toPerm,
						isStaff, csFlag, saleType, status, duration);
			}
		}

		request.setAttribute("cmRst", temp);
		LOG.debug(" ------------ result size:" + temp.size());
		double sum = 0;
		double count = 0;
		for (PolicyStatModel tcm : temp) {// (isNet?temp1:temp)
			count += tcm.getPolicyCount();
			sum += tcm.getPolicyFee() == null ? 0 : tcm.getPolicyFee();
		}
		// 第一位
		request.setAttribute("countPt", count);
		request.setAttribute("sumPt", sum);

		return POLICY_TOXLS;
	}
	
	/*
	 * =======================================
	 *  check stastics
	 * =======================================
	 * 
	 */
	@RequiresUser
	@Log(message="进行抽检分析！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/check", method = { RequestMethod.GET, RequestMethod.POST })
	public String getCheckCityStastics(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String organName = request.getParameter("name");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String levelFlag = request.getParameter("levelFlag");
		String perm = request.getParameter("perm");
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}

		String toPerm = perm;
		if (perm == null || perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		
		boolean isCity = false;
		if (levelFlag == null) {
			if (organCode.length() >= 8) {
				levelFlag = "city";
			} else if (organCode.length() > 4) {
				levelFlag = "prov";
			}
		}

		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		}

		QyCheckModel cm = new QyCheckModel();
		cm.setLevelFlag(levelFlag);
		cm.setDuration(duration);
		cm.setPerm(perm);
		
		//cm.setStatFlag(flag);
		request.setAttribute("CheckModel", cm);

		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		request.setAttribute("perm", perm);
		request.setAttribute("duration", duration);

		String fd = StringUtil.getMonthFirstDayOfMonth(Calendar.getInstance().get(Calendar.MONTH), "yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}

		if(levelFlag != null && levelFlag.equals("prov")) {
			organCode = "8644";
		}
		
		List<TreeMap<String, String>> orgList = orgService.getOrgCodeAndNameMap(organCode);
		
		List<QyCheckModel> writes = null;
		List<QyCheckModel> records = null;
		//將record寫到write model中
		List<QyCheckModel> rst = new ArrayList<QyCheckModel>();
		QyCheckModel qcm = null;
		String orgCode = null;
		
		int totalPolicyCount = 0;
		int totalCheck = 0;
		int totalErr = 0;
		int totalRecordCheck = 0;
		int totalRecordErr = 0;
		
		//if (flag == null || flag.trim().equals("write")) {
		if (!isCity) {
			TreeMap<String, String> cityMap = orgList.get(0);
			writes = stasticsService.getCheckWriteCityStastics(pd1, pd2, duration, toPerm);
			records = stasticsService.getCheckRecordCityStastics(pd1, pd2, duration, toPerm);
			
			Iterator<String> keys = cityMap.keySet().iterator();
			
			while(keys.hasNext()) {
				qcm = new QyCheckModel();
				orgCode = keys.next();
				qcm.setOrganCode(orgCode);
				qcm.setOrgName(cityMap.get(orgCode));
				
				for (QyCheckModel write : writes) {
					if(write.getOrganCode().equals(orgCode)) {
						qcm.setPolicyCounts(write.getPolicyCounts());
						qcm.setCheckCounts(write.getCheckCounts());
						qcm.setErrCounts(write.getErrCounts());
						
						totalPolicyCount += write.getPolicyCounts();
						totalCheck += write.getCheckCounts();
						totalErr += write.getErrCounts();
					}
				}
				rst.add(qcm);
			}
			
			for (QyCheckModel write : rst) {
				for (QyCheckModel record : records) {
					if (write.getOrganCode().equals(record.getOrganCode())) {
						write.setCheckRecordCounts(record.getCheckCounts());
						write.setCheckRecordErrCounts(record.getErrCounts());
						
						totalRecordCheck += record.getCheckCounts();
						totalRecordErr += record.getErrCounts();
						
						break;
					}
				}
			}
			
		} else {
			TreeMap<String, String> areaMap = orgList.get(1);
			writes = stasticsService.getCheckWriteAreaStastics(organCode + "%", pd1, pd2, duration, toPerm);
			records = stasticsService.getCheckRecordAreaStastics(organCode + "%", pd1, pd2, duration, toPerm);
			
			Iterator<String> keys = areaMap.keySet().iterator();
			
			while(keys.hasNext()) {
				qcm = new QyCheckModel();
				orgCode = keys.next();
				qcm.setOrganCode(orgCode);
				qcm.setOrgName(areaMap.get(orgCode));
				
				for (QyCheckModel write : writes) {
					if(write.getOrganCode().equals(orgCode)) {
						qcm.setPolicyCounts(write.getPolicyCounts());
						qcm.setCheckCounts(write.getCheckCounts());
						qcm.setErrCounts(write.getErrCounts());

						totalCheck += write.getCheckCounts();
						totalErr += write.getErrCounts();
					}
				}
				rst.add(qcm);
			}
			
			for (QyCheckModel write : rst) {
				for (QyCheckModel record : records) {
					if (write.getOrganCode().equals(record.getOrganCode())) {
						write.setCheckRecordCounts(record.getCheckCounts());
						write.setCheckRecordErrCounts(record.getErrCounts());
						
						totalRecordCheck += record.getCheckCounts();
						totalRecordErr += record.getErrCounts();
						
						break;
					}
				}
			}
			
		}
		
		request.setAttribute("cmRst", rst);
		request.setAttribute("totalPolicyCount", totalPolicyCount);
		request.setAttribute("totalCheck", totalCheck);
		request.setAttribute("totalErr", totalErr);
		request.setAttribute("totalRecordCheck", totalRecordCheck);
		request.setAttribute("totalRecordErr", totalRecordErr);

		return CHECK_LIST;
	}
	
	@RequiresUser
	@Log(message="抽检结果导出！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/checkToXls", method = { RequestMethod.GET, RequestMethod.POST })
	public String checkCityStasticsToXls(ServletRequest request, Map<String, Object> map) {
		String organCode = request.getParameter("orgCode");
		String organName = request.getParameter("name");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String levelFlag = request.getParameter("levelFlag");
		String perm = request.getParameter("perm");

		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}
		/* close all user can view the hold province's stastics
		 * 
		else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}*/

		String toPerm = perm;
		if (perm == null || perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		
		boolean isCity = false;
		if (levelFlag == null) {
			if (organCode.length() >= 8) {
				levelFlag = "city";
			} else if (organCode.length() > 4) {
				levelFlag = "prov";
			}
		}

		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		}

		QyCheckModel cm = new QyCheckModel();
		cm.setLevelFlag(levelFlag);
		cm.setPerm(perm);
		//cm.setStatFlag(flag);
		request.setAttribute("CheckModel", cm);

		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		request.setAttribute("duration", duration);
		request.setAttribute("perm", perm);

		String fd = StringUtil.getMonthFirstDayOfMonth(Calendar.getInstance().get(Calendar.MONTH), "yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}

		if(levelFlag != null && levelFlag.equals("prov")) {
			organCode = "8644";
		}
		
		List<TreeMap<String, String>> orgList = orgService.getOrgCodeAndNameMap(organCode);
		
		List<QyCheckModel> writes = null;
		List<QyCheckModel> records = null;
		//將record寫到write model中
		List<QyCheckModel> rst = new ArrayList<QyCheckModel>();
		QyCheckModel qcm = null;
		String orgCode = null;
		
		int totalPolicyCount = 0;
		int totalCheck = 0;
		int totalErr = 0;
		int totalRecordCheck = 0;
		int totalRecordErr = 0;
		
		//if (flag == null || flag.trim().equals("write")) {
		if (!isCity) {
			TreeMap<String, String> cityMap = orgList.get(0);
			writes = stasticsService.getCheckWriteCityStastics(pd1, pd2, duration, toPerm);
			records = stasticsService.getCheckRecordCityStastics(pd1, pd2, duration, toPerm);
			
			Iterator<String> keys = cityMap.keySet().iterator();
			
			while(keys.hasNext()) {
				qcm = new QyCheckModel();
				orgCode = keys.next();
				qcm.setOrganCode(orgCode);
				qcm.setOrgName(cityMap.get(orgCode));
				
				for (QyCheckModel write : writes) {
					if(write.getOrganCode().equals(orgCode)) {
						qcm.setPolicyCounts(write.getPolicyCounts());
						qcm.setCheckCounts(write.getCheckCounts());
						qcm.setErrCounts(write.getErrCounts());
						
						totalPolicyCount += write.getPolicyCounts();
						totalCheck += write.getCheckCounts();
						totalErr += write.getErrCounts();
					}
				}
				rst.add(qcm);
			}
			
			for (QyCheckModel write : rst) {
				for (QyCheckModel record : records) {
					if (write.getOrganCode().equals(record.getOrganCode())) {
						write.setCheckRecordCounts(record.getCheckCounts());
						write.setCheckRecordErrCounts(record.getErrCounts());
						
						totalRecordCheck += record.getCheckCounts();
						totalRecordErr += record.getErrCounts();
						
						break;
					}
				}
			}
			
		} else {
			TreeMap<String, String> areaMap = orgList.get(1);
			writes = stasticsService.getCheckWriteAreaStastics(organCode + "%", pd1, pd2, duration, toPerm);
			records = stasticsService.getCheckRecordAreaStastics(organCode + "%", pd1, pd2, duration, toPerm);
			
			Iterator<String> keys = areaMap.keySet().iterator();
			
			while(keys.hasNext()) {
				qcm = new QyCheckModel();
				orgCode = keys.next();
				qcm.setOrganCode(orgCode);
				qcm.setOrgName(areaMap.get(orgCode));
				
				for (QyCheckModel write : writes) {
					if(write.getOrganCode().equals(orgCode)) {
						qcm.setPolicyCounts(write.getPolicyCounts());
						qcm.setCheckCounts(write.getCheckCounts());
						qcm.setErrCounts(write.getErrCounts());

						totalCheck += write.getCheckCounts();
						totalErr += write.getErrCounts();
					}
				}
				rst.add(qcm);
			}
			
			for (QyCheckModel write : rst) {
				for (QyCheckModel record : records) {
					if (write.getOrganCode().equals(record.getOrganCode())) {
						write.setCheckRecordCounts(record.getCheckCounts());
						write.setCheckRecordErrCounts(record.getErrCounts());
						
						totalRecordCheck += record.getCheckCounts();
						totalRecordErr += record.getErrCounts();
						
						break;
					}
				}
			}
			
		}
		
		request.setAttribute("cmRst", rst);
		request.setAttribute("totalPolicyCount", totalPolicyCount);
		request.setAttribute("totalCheck", totalCheck);
		request.setAttribute("totalErr", totalErr);
		request.setAttribute("totalRecordCheck", totalRecordCheck);
		request.setAttribute("totalRecordErr", totalRecordErr);

		return CHECK_TOXLS;
	}
	
	@RequiresUser
	@Log(message="填写抽检明细导出！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/checkWrite/dtlToXls", method = { RequestMethod.GET, RequestMethod.POST })
	public String checkWriteStasticsDtlToXls(ServletRequest request, Map<String, Object> map) {
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String perm = request.getParameter("perm");
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
		}

		String toPerm = perm;
		if (perm == null || perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		
		String fd = StringUtil.getMonthFirstDayOfMonth(Calendar.getInstance().get(Calendar.MONTH), "yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}

		List<CheckModel> rst = stasticsService.getCheckWritetasticsDtl(organCode + "%", pd1, pd2, duration, toPerm);
		
		request.setAttribute("cmRst", rst);

		return CHECK_DTL_XLS;
	}
	
	@RequiresUser
	@Log(message="抽检录入明细导出！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/checkRecord/dtlToXls", method = { RequestMethod.GET, RequestMethod.POST })
	public String checkRecordStasticsDtlToXls(ServletRequest request, Map<String, Object> map) {
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String perm = request.getParameter("perm");
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
		}

		String toPerm = perm;
		if (perm == null || perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		
		String fd = StringUtil.getMonthFirstDayOfMonth(Calendar.getInstance().get(Calendar.MONTH), "yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}

		List<CheckModel> rst = stasticsService.getCheckRecordtasticsDtl(organCode + "%", pd1, pd2, duration, toPerm);
		
		request.setAttribute("cmRst", rst);

		return CHECK_DTL_XLS;
	}
	
	/*
	 * =======================================
	 *  truth check stastics
	 * =======================================
	 * 
	 */
	@RequiresUser
	@Log(message="进行客户信息真实性分析！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/truth", method = { RequestMethod.GET, RequestMethod.POST })
	public String truthStastics(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String organName = request.getParameter("name");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String levelFlag = request.getParameter("levelFlag");
		String perm = request.getParameter("perm");
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}
		/* close all user can view the hold province's stastics
		 * 
		else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}*/

		String toPerm = perm;
		if (perm == null || perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		
		boolean isCity = false;
		if (levelFlag == null) {
			if (organCode.length() >= 8) {
				levelFlag = "city";
			} else if (organCode.length() > 4) {
				levelFlag = "prov";
			}
		}

		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		}

		QyCheckModel cm = new QyCheckModel();
		cm.setLevelFlag(levelFlag);
		cm.setDuration(duration);
		cm.setPerm(perm);
		//cm.setStatFlag(flag);
		request.setAttribute("CheckModel", cm);

		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		request.setAttribute("perm", perm);
		request.setAttribute("duration", duration);

		String fd = StringUtil.getMonthFirstDayOfMonth(Calendar.getInstance().get(Calendar.MONTH), "yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}
		
		if(levelFlag != null && levelFlag.equals("prov")) {
			organCode = "8644";
		}
		
		List<TreeMap<String, String>> orgList = orgService.getOrgCodeAndNameMap(organCode);
		
		List<QyCheckModel> writes = null;
		//將record寫到write model中
		List<QyCheckModel> rst = new ArrayList<QyCheckModel>();
		QyCheckModel qcm = null;
		String orgCode = null;
		
		//if (flag == null || flag.trim().equals("write")) {
		if (!isCity) {
			TreeMap<String, String> cityMap = orgList.get(0);
			writes = stasticsService.getCheckTruthCityStastics(pd1, pd2, duration, toPerm);
			Iterator<String> keys = cityMap.keySet().iterator();
			
			while(keys.hasNext()) {
				qcm = new QyCheckModel();
				orgCode = keys.next();
				qcm.setOrganCode(orgCode);
				qcm.setOrgName(cityMap.get(orgCode));
				
				for (QyCheckModel write : writes) {
					if(write.getOrganCode().equals(orgCode)) {
						qcm.setPolicyCounts(write.getPolicyCounts());
						qcm.setCheckCounts(write.getCheckCounts());
						qcm.setErrCounts(write.getErrCounts());
					}
				}
				rst.add(qcm);
			}
		} else {
			TreeMap<String, String> areaMap = orgList.get(1);
			writes = stasticsService.getCheckTruthAreaStastics(organCode + "%", pd1, pd2, duration, toPerm);
			
			Iterator<String> keys = areaMap.keySet().iterator();
			
			while(keys.hasNext()) {
				qcm = new QyCheckModel();
				orgCode = keys.next();
				qcm.setOrganCode(orgCode);
				qcm.setOrgName(areaMap.get(orgCode));
				
				for (QyCheckModel write : writes) {
					if(write.getOrganCode().equals(orgCode)) {
						qcm.setPolicyCounts(write.getPolicyCounts());
						qcm.setCheckCounts(write.getCheckCounts());
						qcm.setErrCounts(write.getErrCounts());
					}
				}
				rst.add(qcm);
			}
		}
		
		request.setAttribute("cmRst", rst);
		
		String col = "";
		String countStr = "";
		String errsumStr = "";
		String countPtStr = "";
		double maxZB = 0;
		int maxTB = 0;
		double errsum = 0;
		double count = 0;
		List<Integer> countList = new ArrayList<Integer>();
		List<Integer> errsumList = new ArrayList<Integer>();
		DecimalFormat df = new DecimalFormat("#.#");
		for (QyCheckModel tcm : rst) {
			col += "'" + tcm.getOrgName() + "',";
			countList.add(tcm.getPolicyCounts());
			count += tcm.getPolicyCounts();
			countStr += tcm.getPolicyCounts() + ",";
			errsumList.add(tcm.getErrCounts());
			errsumStr += tcm.getErrCounts() + ",";
			errsum += tcm.getErrCounts() == null ? 0 : tcm.getErrCounts();
			
			Double err = (double) (tcm.getPolicyCounts()==0?1:tcm.getErrCounts());
			Double p = (double) (tcm.getPolicyCounts()==0?1:tcm.getPolicyCounts());
			countPtStr += (df.format((1-err/p)*100) + ",");
			
			if(tcm.getPolicyCounts() > maxTB) {
				maxTB = tcm.getPolicyCounts();
			}
			if(maxZB<(1-tcm.getErrCounts()/(tcm.getPolicyCounts()==0?1:tcm.getPolicyCounts()))) {
				maxZB = 1-tcm.getErrCounts()/(tcm.getPolicyCounts()==0?1:tcm.getPolicyCounts());
			}
		}
		
		int m = 1;
		for (int i = 0; i < (int) Math.log10(maxTB); i++) {
			m *= 10;
		}
		// 第一位
		maxTB = (maxTB / m + 1) * m;
		maxZB = maxZB*100;
		request.setAttribute("col", col);
		request.setAttribute("countStr", countStr);
		request.setAttribute("sumStr", errsumStr);
		request.setAttribute("countPtStr", countPtStr);
		request.setAttribute("maxTB", maxTB);
		request.setAttribute("maxZB", maxZB);
		request.setAttribute("countPt", count);
		request.setAttribute("sumPt", errsum);

		LOG.debug(" ------------ result size:" + rst.size());
		return TRUTH_LIST;
	}
	
	@RequiresUser
	@Log(message="客户信息真实性分析结果导出！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/truth/toXls", method = { RequestMethod.GET, RequestMethod.POST })
	public String truthStasticsToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String levelFlag = request.getParameter("levelFlag");
		String perm = request.getParameter("perm");
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
		}

		boolean isCity = false;
		if (levelFlag == null) {
			if (organCode.length() >= 8) {
				levelFlag = "city";
			} else if (organCode.length() > 4) {
				levelFlag = "prov";
			}
		}
		
		String toPerm = perm;
		if (perm == null || perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}

		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		}

		String fd = StringUtil.getMonthFirstDayOfMonth(Calendar.getInstance().get(Calendar.MONTH), "yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}

		if(levelFlag != null && levelFlag.equals("prov")) {
			organCode = "8644";
		}
		
		List<TreeMap<String, String>> orgList = orgService.getOrgCodeAndNameMap(organCode);
		
		List<QyCheckModel> writes = null;
		//將record寫到write model中
		List<QyCheckModel> rst = new ArrayList<QyCheckModel>();
		QyCheckModel qcm = null;
		String orgCode = null;
		
		//if (flag == null || flag.trim().equals("write")) {
		if (!isCity) {
			TreeMap<String, String> cityMap = orgList.get(0);
			writes = stasticsService.getCheckTruthCityStastics(pd1, pd2, duration, toPerm);
			Iterator<String> keys = cityMap.keySet().iterator();
			
			while(keys.hasNext()) {
				qcm = new QyCheckModel();
				orgCode = keys.next();
				qcm.setOrganCode(orgCode);
				qcm.setOrgName(cityMap.get(orgCode));
				
				for (QyCheckModel write : writes) {
					if(write.getOrganCode().equals(orgCode)) {
						qcm.setPolicyCounts(write.getPolicyCounts());
						qcm.setCheckCounts(write.getCheckCounts());
						qcm.setErrCounts(write.getErrCounts());
					}
				}
				rst.add(qcm);
			}
		} else {
			TreeMap<String, String> areaMap = orgList.get(1);
			writes = stasticsService.getCheckTruthAreaStastics(organCode + "%", pd1, pd2, duration, toPerm);
			
			Iterator<String> keys = areaMap.keySet().iterator();
			
			while(keys.hasNext()) {
				qcm = new QyCheckModel();
				orgCode = keys.next();
				qcm.setOrganCode(orgCode);
				qcm.setOrgName(areaMap.get(orgCode));
				
				for (QyCheckModel write : writes) {
					if(write.getOrganCode().equals(orgCode)) {
						qcm.setPolicyCounts(write.getPolicyCounts());
						qcm.setCheckCounts(write.getCheckCounts());
						qcm.setErrCounts(write.getErrCounts());
					}
				}
				rst.add(qcm);
			}
		}
		
		request.setAttribute("cmRst", rst);
		
		double errsum = 0;
		double count = 0;
		for (QyCheckModel tcm : rst) {
			count += tcm.getPolicyCounts();
			errsum += tcm.getErrCounts() == null ? 0 : tcm.getErrCounts();
		}
		request.setAttribute("countPt", count);
		request.setAttribute("sumPt", errsum);
		
		return TRUTH_LIST_TOXLS;
	}
	
	@RequiresUser
	@Log(message="抽检客户信息真实性排查差错明细导出！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/truth/dtlToXls", method = { RequestMethod.GET, RequestMethod.POST })
	public String truthStasticsDtlToXls(ServletRequest request, Map<String, Object> map) {
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String perm = request.getParameter("perm");
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
		} else if (!organCode.contains(userOrg.getOrgCode())) {
			organCode = userOrg.getOrgCode();
		}

		String toPerm = perm;
		if (perm == null || perm.trim().length() <= 0) {
			toPerm = "%%";
		} else if (perm.equals("1")) {
			toPerm = "年交";
		} else {
			toPerm = "趸交";
		}
		
		String fd = StringUtil.getMonthFirstDayOfMonth(Calendar.getInstance().get(Calendar.MONTH), "yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}

		List<CheckModel> rst = stasticsService.getCheckTruthStasticsDtl(organCode + "%", pd1, pd2, duration, toPerm);
		
		request.setAttribute("cmRst", rst);

		return CHECK_DTL_XLS;
	}

	/*
	 * =======================================
	 *  policy print stastics
	 * =======================================
	 * 
	 */
	@RequiresUser
	@Log(message="进行纸质保单使用率分析！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/policyPrint", method = { RequestMethod.GET, RequestMethod.POST })
	public String printStastics(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String organName = request.getParameter("name");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String levelFlag = request.getParameter("levelFlag");

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}

		boolean isCity = false;
		if (levelFlag == null) {
			if (organCode.length() >= 8) {
				levelFlag = "city";
			} else if (organCode.length() > 4) {
				levelFlag = "prov";
			}
		}

		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		}

		QyCheckModel cm = new QyCheckModel();
		cm.setLevelFlag(levelFlag);
		request.setAttribute("CheckModel", cm);

		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		//request.setAttribute("flag", flag);

		String fd = StringUtil.getMonthFirstDayOfMonth(Calendar.getInstance().get(Calendar.MONTH), "yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}

		if(levelFlag != null && levelFlag.equals("prov")) {
			organCode = "8644";
		}
		
		List<TreeMap<String, String>> orgList = orgService.getOrgCodeAndNameMap(organCode);
		
		List<QyCheckModel> writes = null;
		//將record寫到write model中
		List<QyCheckModel> rst = new ArrayList<QyCheckModel>();
		QyCheckModel qcm = null;
		String orgCode = null;
		
		//if (flag == null || flag.trim().equals("write")) {
		if (!isCity) {
			TreeMap<String, String> cityMap = orgList.get(0);
			writes = stasticsService.getPrintCityStastics(pd1, pd2);
			Iterator<String> keys = cityMap.keySet().iterator();
			
			while(keys.hasNext()) {
				qcm = new QyCheckModel();
				orgCode = keys.next();
				qcm.setOrganCode(orgCode);
				qcm.setOrgName(cityMap.get(orgCode));
				
				for (QyCheckModel write : writes) {
					if(write.getOrganCode().equals(orgCode)) {
						qcm.setPolicyCounts(write.getPolicyCounts());
						qcm.setCheckCounts(write.getCheckCounts());
						qcm.setErrCounts(write.getErrCounts());
					}
				}
				rst.add(qcm);
			}
		} else {
			TreeMap<String, String> areaMap = orgList.get(1);
			writes = stasticsService.getPrintAreaStastics(organCode + "%", pd1, pd2);
			
			Iterator<String> keys = areaMap.keySet().iterator();
			
			while(keys.hasNext()) {
				qcm = new QyCheckModel();
				orgCode = keys.next();
				qcm.setOrganCode(orgCode);
				qcm.setOrgName(areaMap.get(orgCode));
				
				for (QyCheckModel write : writes) {
					if(write.getOrganCode().equals(orgCode)) {
						qcm.setPolicyCounts(write.getPolicyCounts());
						qcm.setCheckCounts(write.getCheckCounts());
						qcm.setErrCounts(write.getErrCounts());
					}
				}
				rst.add(qcm);
			}
		}
		
		request.setAttribute("cmRst", rst);
		
		String col = "";
		String countStr = "";
		String errsumStr = "";
		String countPtStr = "";
		double maxZB = 0;
		int maxTB = 0;
		double errsum = 0;
		double count = 0;
		List<Integer> countList = new ArrayList<Integer>();
		List<Integer> errsumList = new ArrayList<Integer>();
		DecimalFormat df = new DecimalFormat("#.#");
		for (QyCheckModel tcm : rst) {
			col += "'" + tcm.getOrgName() + "',";
			countList.add(tcm.getPolicyCounts());
			count += tcm.getPolicyCounts();
			countStr += tcm.getPolicyCounts() + ",";
			errsumList.add(tcm.getErrCounts());
			errsumStr += tcm.getErrCounts() + ",";
			errsum += tcm.getErrCounts() == null ? 0 : tcm.getErrCounts();
			
			Double err = (double) (tcm.getPolicyCounts()==0?0:tcm.getErrCounts());
			Double p = (double) (tcm.getPolicyCounts()==0?1:tcm.getPolicyCounts());
			countPtStr += (df.format((err/p)*100) + ",");
			
			if(tcm.getPolicyCounts() > maxTB) {
				maxTB = tcm.getPolicyCounts();
			}
			if(maxZB<(err/p)) {
				maxZB = err/p;
			}
		}
		
		int m = 1;
		for (int i = 0; i < (int) Math.log10(maxTB); i++) {
			m *= 10;
		}
		// 第一位
		maxTB = (maxTB / m + 1) * m;
		maxZB = maxZB*100;
		request.setAttribute("col", col);
		request.setAttribute("countStr", countStr);
		request.setAttribute("sumStr", errsumStr);
		request.setAttribute("countPtStr", countPtStr);
		request.setAttribute("maxTB", maxTB);
		request.setAttribute("maxZB", maxZB);
		request.setAttribute("countPt", count);
		request.setAttribute("sumPt", errsum);

		LOG.debug(" ------------ result size:" + rst.size());
		return PRINT_LIST;
	}
	
	@RequiresUser
	@Log(message="纸质保单使用率分析结果导出！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/policyPrint/toXls", method = { RequestMethod.GET, RequestMethod.POST })
	public String printStasticsToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String levelFlag = request.getParameter("levelFlag");

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
		}

		boolean isCity = false;
		if (levelFlag == null) {
			if (organCode.length() >= 8) {
				levelFlag = "city";
			} else if (organCode.length() > 4) {
				levelFlag = "prov";
			}
		}

		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		}


		String fd = StringUtil.getMonthFirstDayOfMonth(Calendar.getInstance().get(Calendar.MONTH), "yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}

		if(levelFlag != null && levelFlag.equals("prov")) {
			organCode = "8644";
		}
		
		List<TreeMap<String, String>> orgList = orgService.getOrgCodeAndNameMap(organCode);
		
		List<QyCheckModel> writes = null;
		//將record寫到write model中
		List<QyCheckModel> rst = new ArrayList<QyCheckModel>();
		QyCheckModel qcm = null;
		String orgCode = null;
		
		//if (flag == null || flag.trim().equals("write")) {
		if (!isCity) {
			TreeMap<String, String> cityMap = orgList.get(0);
			writes = stasticsService.getPrintCityStastics(pd1, pd2);
			Iterator<String> keys = cityMap.keySet().iterator();
			
			while(keys.hasNext()) {
				qcm = new QyCheckModel();
				orgCode = keys.next();
				qcm.setOrganCode(orgCode);
				qcm.setOrgName(cityMap.get(orgCode));
				
				for (QyCheckModel write : writes) {
					if(write.getOrganCode().equals(orgCode)) {
						qcm.setPolicyCounts(write.getPolicyCounts());
						qcm.setCheckCounts(write.getCheckCounts());
						qcm.setErrCounts(write.getErrCounts());
					}
				}
				rst.add(qcm);
			}
		} else {
			TreeMap<String, String> areaMap = orgList.get(1);
			writes = stasticsService.getPrintAreaStastics(organCode + "%", pd1, pd2);
			
			Iterator<String> keys = areaMap.keySet().iterator();
			
			while(keys.hasNext()) {
				qcm = new QyCheckModel();
				orgCode = keys.next();
				qcm.setOrganCode(orgCode);
				qcm.setOrgName(areaMap.get(orgCode));
				
				for (QyCheckModel write : writes) {
					if(write.getOrganCode().equals(orgCode)) {
						qcm.setPolicyCounts(write.getPolicyCounts());
						qcm.setCheckCounts(write.getCheckCounts());
						qcm.setErrCounts(write.getErrCounts());
					}
				}
				rst.add(qcm);
			}
		}
		
		request.setAttribute("cmRst", rst);
		
		double errsum = 0;
		double count = 0;
		for (QyCheckModel tcm : rst) {
			count += tcm.getPolicyCounts();
			errsum += tcm.getErrCounts() == null ? 0 : tcm.getErrCounts();
		}
		request.setAttribute("countPt", count);
		request.setAttribute("sumPt", errsum);
		
		return PRINT_LIST_TOXLS;
	}

	/*
	 * =======================================
	 *  total考核结果
	 * =======================================
	 * 
	 */
	@RequiresUser
	@Log(message="查看了全省考核结果！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/cityStat", method = { RequestMethod.GET, RequestMethod.POST })
	public String cityStastics(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String organName = request.getParameter("name");
		String mthYear = request.getParameter("mthYear");
		String mthMonth = request.getParameter("mthMonth");
		String mth = (mthYear==null?(StringUtil.date2Str(StringUtil.dateAdd(new Date(), -30),"yyyyMM")):(mthYear + mthMonth));
		String flag = request.getParameter("flag");
		String mthStr = mth;
		if(flag == null || flag.equals("0")) {
			flag = "0";
			mth = mth + "01";
		}
		
		StasticsCity sc = new StasticsCity();
		sc.setMthYear(mthYear);
		sc.setMthMonth(mthMonth);
		request.setAttribute("StasticsCity", sc);
		request.setAttribute("mthYear", mthYear);
		request.setAttribute("mthMonth", mthMonth);
		
		List<Integer> yl = new ArrayList<Integer> ();
		yl.add(Calendar.getInstance().get(Calendar.YEAR));
		yl.add(Calendar.getInstance().get(Calendar.YEAR)-1);
		
		List<String> ml = new ArrayList<String> ();
		for(int i=1; i<=12; i++) {
			if(i<10) {
				ml.add("0" + i);
			} else {
				ml.add("" + i);
			}
		}
		request.setAttribute("yl", yl);
		request.setAttribute("ml", ml);
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}
	
		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		request.setAttribute("flag", flag);
		request.setAttribute("mthStr", mthStr);
		request.setAttribute("mth", mth);
	
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		if(mth!=null) {
			csf.add(new SearchFilter("mth", Operator.EQ, mth));
		}
		if(organCode!=null) {
			csf.add(new SearchFilter("organCode", Operator.LIKE_R, organCode));
		}
		Page page = new Page();
		page.setNumPerPage(Integer.MAX_VALUE);
		Specification<StasticsCity> specification = DynamicSpecifications.bySearchFilter(request, StasticsCity.class, csf);
		List<StasticsCity> rst = stasticsService.findByCityStatByExample(specification, page);
		
		request.setAttribute("cmRst", rst);
	
		return CITY_STAT_LIST;
	}

	@RequiresUser
	@Log(message="全省考核结果导出！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/cityStat/toXls", method = { RequestMethod.GET, RequestMethod.POST })
	public String cityStasticsToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String organName = request.getParameter("name");
		String mth = request.getParameter("mth");
		String flag = request.getParameter("flag");
		String mthStr = mth;
		if(flag == null) {
			flag = "0";
		}
		if (mth == null) {
			mthStr = StringUtil.date2Str(StringUtil.dateAdd(new Date(), -30), "yyyyMM");
			if(flag.equals("0")) {
				mth = StringUtil.date2Str(StringUtil.dateAdd(new Date(), -30), "yyyyMM01");
			} else {
				mth = mthStr;
			}
		} else {
			if(flag.equals("0")) {
				mth = mth + "01";
			}
		}
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}
	
		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		request.setAttribute("flag", flag);
		request.setAttribute("mthStr", mthStr);
		request.setAttribute("mth", mth);
	
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		if(mth!=null) {
			csf.add(new SearchFilter("mth", Operator.EQ, mth));
		}
		if(organCode!=null) {
			csf.add(new SearchFilter("organCode", Operator.LIKE_R, organCode));
		}
		
		Page page = new Page();
		page.setNumPerPage(Integer.MAX_VALUE);
		Specification<StasticsCity> specification = DynamicSpecifications.bySearchFilter(request, StasticsCity.class, csf);
		List<StasticsCity> rst = stasticsService.findByCityStatByExample(specification, page);
		
		request.setAttribute("cmRst", rst);
	
		return CITY_STAT_TOXLS;
	}

	/*
	 * =======================================
	 *  total考核结果
	 * =======================================
	 * 
	 */
	@RequiresUser
	@Log(message="查看了全省县区考核结果！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/areaStat", method = { RequestMethod.GET, RequestMethod.POST })
	public String areaStastics(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String organName = request.getParameter("name");
		String mthYear = request.getParameter("mthYear");
		String mthMonth = request.getParameter("mthMonth");
		String mth = (mthYear==null?(StringUtil.date2Str(StringUtil.dateAdd(new Date(), -30),"yyyyMM")):(mthYear + mthMonth));
		String flag = request.getParameter("flag");
		String mthStr = mth;
		if(flag == null || flag.equals("0")) {
			flag = "0";
			mth = mth + "01";
		}
		
		StasticsCity sc = new StasticsCity();
		sc.setMthYear(mthYear);
		sc.setMthMonth(mthMonth);
		request.setAttribute("StasticsCity", sc);
		request.setAttribute("mthYear", mthYear);
		request.setAttribute("mthMonth", mthMonth);
		
		List<Integer> yl = new ArrayList<Integer> ();
		yl.add(Calendar.getInstance().get(Calendar.YEAR));
		yl.add(Calendar.getInstance().get(Calendar.YEAR)-1);
		
		List<String> ml = new ArrayList<String> ();
		for(int i=1; i<=12; i++) {
			if(i<10) {
				ml.add("0" + i);
			} else {
				ml.add("" + i);
			}
		}
		request.setAttribute("yl", yl);
		request.setAttribute("ml", ml);
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}
	
		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		request.setAttribute("flag", flag);
		request.setAttribute("mthStr", mthStr);
		request.setAttribute("mth", mth);
	
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		if(mth!=null) {
			csf.add(new SearchFilter("mth", Operator.EQ, mth));
		}
		
		if(organCode!=null) {
			csf.add(new SearchFilter("organCode", Operator.LIKE_R, organCode));
		}
		
		Page page = new Page();
		page.setNumPerPage(Integer.MAX_VALUE);
		Specification<StasticsArea> specification = DynamicSpecifications.bySearchFilter(request, StasticsArea.class, csf);
		List<StasticsArea> rst = stasticsService.findByAreaStatByExample(specification, page);
		
		request.setAttribute("cmRst", rst);
	
		return AREA_STAT_LIST;
	}

	@RequiresUser
	@Log(message="全省县区考核结果导出！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/areaStat/toXls", method = { RequestMethod.GET, RequestMethod.POST })
	public String areaStasticsToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String organName = request.getParameter("name");
		String mth = request.getParameter("mth");
		String flag = request.getParameter("flag");
		String mthStr = mth;
		if(flag == null) {
			flag = "0";
		}
		if (mth == null) {
			mthStr = StringUtil.date2Str(StringUtil.dateAdd(new Date(), -30), "yyyyMM");
			if(flag.equals("0")) {
				mth = StringUtil.date2Str(StringUtil.dateAdd(new Date(), -30), "yyyyMM01");
			} else {
				mth = mthStr;
			}
		} else {
			if(flag.equals("0")) {
				mth = mth + "01";
			}
		}
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}
	
		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		request.setAttribute("flag", flag);
		request.setAttribute("mthStr", mthStr);
		request.setAttribute("mth", mth);
	
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		if(mth!=null) {
			csf.add(new SearchFilter("mth", Operator.EQ, mth));
		}
		if(organCode!=null) {
			csf.add(new SearchFilter("organCode", Operator.LIKE_R, organCode));
		}
		Page page = new Page();
		page.setNumPerPage(Integer.MAX_VALUE);
		Specification<StasticsArea> specification = DynamicSpecifications.bySearchFilter(request, StasticsArea.class, csf);
		List<StasticsArea> rst = stasticsService.findByAreaStatByExample(specification, page);
		
		request.setAttribute("cmRst", rst);
	
		return AREA_STAT_TOXLS;
	}

	/*
	 * =======================================
	 *  total考核结果
	 * =======================================
	 * 
	 */
	@RequiresUser
	@Log(message="查看了整改进度！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/statusStat", method = { RequestMethod.GET, RequestMethod.POST })
	public String statusStastics(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------statusStastics----------");
		String organCode = request.getParameter("orgCode");
		String organName = request.getParameter("name");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String levelFlag = request.getParameter("levelFlag");
		String fixStatus = request.getParameter("fixStatus");
		if(fixStatus == null || fixStatus.trim().length()<=0) {
			fixStatus = QY_STATUS.NewStatus.name();
		}

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
			organName = userOrg.getName();
		}

		boolean isCity = false;
		if (levelFlag == null) {
			if (organCode.length() >= 8) {
				levelFlag = "city";
			} else if (organCode.length() > 4) {
				levelFlag = "prov";
			}
		}

		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		}

		QyCheckModel cm = new QyCheckModel();
		cm.setLevelFlag(levelFlag);
		cm.setFixStatus(fixStatus);
		//cm.setStatFlag(flag);
		request.setAttribute("CheckModel", cm);

		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		request.setAttribute("fixStatus", fixStatus);

		String fd = StringUtil.getMonthFirstDayOfMonth(Calendar.getInstance().get(Calendar.MONTH), "yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}
		
		if(levelFlag != null && levelFlag.equals("prov")) {
			organCode = "8644";
		}
		
		List<QyCheckModel> rst = new ArrayList<QyCheckModel>();
		if (!isCity) {
			rst = stasticsService.getStatusCheckWriteCityStastics(pd1, pd2, fixStatus);
		} else {
			rst = stasticsService.getStatusCheckWriteAreaStastics(organCode + "%", pd1, pd2, fixStatus);
		}
		
		request.setAttribute("cmRst", rst);
		
		String col = "";
		String countStr = "";
		String errsumStr = "";
		String countPtStr = "";
		double maxZB = 0;
		int maxTB = 0;
		double errsum = 0;
		double count = 0;
		List<Integer> countList = new ArrayList<Integer>();
		List<Integer> errsumList = new ArrayList<Integer>();
		DecimalFormat df = new DecimalFormat("#.#");
		for (QyCheckModel tcm : rst) {
			col += "'" + tcm.getOrganCode() + "',";
			countList.add(tcm.getCheckCounts());
			count += tcm.getCheckCounts();
			countStr += tcm.getCheckCounts() + ",";
			errsumList.add(tcm.getErrCounts());
			errsumStr += tcm.getErrCounts() + ",";
			errsum += tcm.getErrCounts() == null ? 0 : tcm.getErrCounts();
			
			Double err = (double) (tcm.getCheckCounts()==0?1:tcm.getErrCounts());
			Double p = (double) (tcm.getCheckCounts()==0?1:tcm.getCheckCounts());
			countPtStr += (df.format((1-err/p)*100) + ",");
			
			if(tcm.getCheckCounts() > maxTB) {
				maxTB = tcm.getCheckCounts();
			}
			if(maxZB<(1-tcm.getErrCounts()/(tcm.getCheckCounts()==0?1:tcm.getCheckCounts()))) {
				maxZB = 1-tcm.getErrCounts()/(tcm.getCheckCounts()==0?1:tcm.getCheckCounts());
			}
		}
		
		int m = 1;
		for (int i = 0; i < (int) Math.log10(maxTB); i++) {
			m *= 10;
		}
		// 第一位
		maxTB = (maxTB / m + 1) * m;
		maxZB = maxZB*100;
		request.setAttribute("col", col);
		request.setAttribute("countStr", countStr);
		request.setAttribute("sumStr", errsumStr);
		request.setAttribute("countPtStr", countPtStr);
		request.setAttribute("maxTB", maxTB);
		request.setAttribute("maxZB", maxZB);
		request.setAttribute("countPt", count);
		request.setAttribute("sumPt", errsum);

		LOG.debug(" ------------ result size:" + rst.size());
		map.put("qyWriteStatusList", QY_STATUS.values());
	
		return STATUS_STAT_LIST;
	}

	@RequiresUser
	@Log(message="全省整改进度导出！", level=LogLevel.INFO, module=LogModule.QTCZ)
	@RequestMapping(value = "/stastics/statusStat/toXls", method = { RequestMethod.GET, RequestMethod.POST })
	public String statusStasticsToXls(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------statusStastics----------");
		String organCode = request.getParameter("orgCode");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String levelFlag = request.getParameter("levelFlag");
		String fixStatus = request.getParameter("fixStatus");
		if(fixStatus == null || fixStatus.trim().length()<=0) {
			fixStatus = QY_STATUS.NewStatus.name();
		}

		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();// userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		if (organCode == null || organCode.trim().length() <= 0) {
			organCode = userOrg.getOrgCode();
		}

		boolean isCity = false;
		if (levelFlag == null) {
			if (organCode.length() >= 8) {
				levelFlag = "city";
			} else if (organCode.length() > 4) {
				levelFlag = "prov";
			}
		}

		if (levelFlag != null && levelFlag.trim().equals("city")) {
			isCity = true;
		}

		String fd = StringUtil.getMonthFirstDayOfMonth(Calendar.getInstance().get(Calendar.MONTH), "yyyy-MM-dd");
		if (pd1 == null || pd1.trim().length() <= 0) {
			pd1 = fd;
		}
		if (pd2 == null || pd2.trim().length() <= 0) {
			pd2 = "9999-12-31";
		}
		
		if(levelFlag != null && levelFlag.equals("prov")) {
			organCode = "8644";
		}
		
		List<QyCheckModel> rst = new ArrayList<QyCheckModel>();
		if (!isCity) {
			rst = stasticsService.getStatusCheckWriteCityStastics(pd1, pd2, fixStatus);
		} else {
			rst = stasticsService.getStatusCheckWriteAreaStastics(organCode + "%", pd1, pd2, fixStatus);
		}
		
		request.setAttribute("cmRst", rst);
	
		double errsum = 0;
		double count = 0;
		for (QyCheckModel tcm : rst) {
			count += tcm.getCheckCounts();
			errsum += tcm.getErrCounts() == null ? 0 : tcm.getErrCounts();
		}
		
		request.setAttribute("countPt", count);
		request.setAttribute("sumPt", errsum);
		
		return STATUS_STAT_TOXLS;
	}
}
