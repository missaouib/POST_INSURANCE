/**
 * 
 * ==========================================================
 * 通用controller
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.StringUtil;
import com.gdpost.web.entity.component.TuiBaoModel;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.service.component.StasticsService;
import com.gdpost.web.service.insurance.PolicyService;
import com.gdpost.web.shiro.ShiroUser;

@Controller
@RequestMapping("/surrender")
public class SurrenderController {
	private static final Logger LOG = LoggerFactory.getLogger(SurrenderController.class);

	private static final String SURRENDER_REQ = "insurance/stastics/tuibao/tuibaoReq";
	private static final String SURRENDER_RST = "insurance/stastics/tuibao/tuibaoRst";

	@Autowired
	private PolicyService policyService;

	@Autowired
	StasticsService statService;

	@RequiresUser
	@RequestMapping(value = "/req", method = { RequestMethod.POST, RequestMethod.GET })
	public String surrenderReq(ServletRequest request, Map<String, Object> map) {
		String policyNo = request.getParameter("policyNo");
		String idCardNum = request.getParameter("idCardNum");

		if((policyNo == null || policyNo.trim().length() <= 0) && (idCardNum ==null || idCardNum.trim().length()<=0)) {
			return SURRENDER_REQ;
		}
		Policy policy = null;
		if(policyNo != null && policyNo.trim().length() == 14) {
			policy = policyService.getByPolicyNo(policyNo);
		} else {
			policy = policyService.getByHolderIdCardNum(idCardNum);
		}
		List<Policy> list = new ArrayList<Policy> ();
		
		if(policy == null) {
			return SURRENDER_REQ;
		}
		request.setAttribute("isStaff", policy.getIsStaff());
		request.setAttribute("policyNo", policyNo);
		request.setAttribute("idCardNum", idCardNum);
		boolean isBankPolicy = policyService.isBankPolicy(policy.getPolicyNo());
		request.setAttribute("isBankPolicy", isBankPolicy);
		
		list.add(policy);
		map.put("policies", list);
		
		return SURRENDER_REQ;
	}
	
	@RequiresUser
	@RequestMapping(value = "/rst", method = { RequestMethod.POST, RequestMethod.GET })
	public String surrenderRst(ServletRequest request, Map<String, Object> map) {
		// 如果是市局登录
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		String userOrg = shiroUser.getUser().getOrganization().getOrgCode();

		String tbReason = request.getParameter("tbReason");
		String policyNo = request.getParameter("policyNo");

		Policy policy = policyService.getByPolicyNo(policyNo);

		String orgCode = policy.getOrganization().getOrgCode();
		String orgName = policy.getOrganization().getName();
		Double reqFee = policy.getPolicyFee();
		LOG.debug(" ---111--- policy org:" + orgName + ", orgCode:" + orgCode);

		orgCode = orgCode.substring(0, 6);
		//LOG.debug(" ---222--- policy org:" + orgName + ", orgCode:" + orgCode);
		
		//userOrg = userOrg.length()>4?userOrg.substring(0, 6):userOrg;
		LOG.debug(" ---222--- userOrg:" + userOrg);
		String tmpOrgCode = orgCode.length()>4?orgCode.substring(0, 6):orgCode;
		String tmpUserOrg = userOrg.length()>4?userOrg.substring(0, 6):userOrg;
		if (!tmpOrgCode.contains(tmpUserOrg)) {
			return SURRENDER_REQ;
		}

		Calendar ca = Calendar.getInstance();
		ca.setTime(policy.getPolicyDate());
		int policyYear = ca.get(Calendar.YEAR);
		LOG.debug(" -------- policy Year: " + policyYear);
		ca.set(Calendar.DAY_OF_YEAR, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String p1 = sdf.format(ca.getTime());
		ca.set(Calendar.YEAR, policyYear);
		ca.roll(Calendar.DAY_OF_YEAR, -1);
		String p2 = sdf.format(ca.getTime());

		String c2 = "9999-12-31";

		Double permpolicyFee = 0.0;
		Double permsumPolicyFee = 0.0;
		Double permpf = 0.0;
		Double permspf = 0.0;
		
		Double permstaffpolicyFee = 0.0;
		Double permstaffsumPolicyFee = 0.0;
		Double permstaffpf = 0.0;
		Double permstaffspf = 0.0;
		
		/*
		Double policyFee = 0.0;
		Double sumPolicyFee = 0.0;
		Double pf = 0.0;
		Double spf = 0.0;
		
		Double staffpolicyFee = 0.0;
		Double staffsumPolicyFee = 0.0;
		Double staffpf = 0.0;
		Double staffspf = 0.0;
		*/
		//Double curPermRate = 0.0;
		Double newPermRate = 0.0;
		//Double orgCurPermRate = 0.0;
		Double orgNewPermRate = 0.0;
		
		Double orgCurStaffRate = 0.0;
		Double orgNewStaffRate = 0.0;
		Double curStaffRate = 0.0;
		Double newStaffRate = 0.0;
		
		/*
		Double curStaffRate = 0.0;
		Double newStaffRate = 0.0;
		Double orgCurStaffRate = 0.0;
		Double orgNewStaffRate = 0.0;
		*/
		List<TuiBaoModel> permlist = statService.getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(orgCode + "%", p1, p2, p1, c2, "%%", "%年交%", "%%");
		List<TuiBaoModel> permStafflist = statService.getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(orgCode + "%", p1, p2, p1, c2, "%%", "%年交%", "1");
		//List<TuiBaoModel> list = statService.getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(orgCode + "%", p1, p2, p1, c2, "%%", "%%", "%%");
		//List<TuiBaoModel> Stafflist = statService.getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(orgCode + "%", p1, p2, p1, c2, "%%", "%%", "1");
		for (TuiBaoModel tbm : permlist) {
			if (tbm.getOrganName().equals(orgName)) {
				permpf = tbm.getPolicyFee() == null ? 0 : tbm.getPolicyFee();//县区期交退保保费
				permspf = tbm.getSumPolicyFee() == null ? 0 : tbm.getSumPolicyFee();//县区期交总保费
			}
			permpolicyFee += tbm.getPolicyFee() == null ? 0 : tbm.getPolicyFee();//市局期交退保保费
			permsumPolicyFee += tbm.getSumPolicyFee() == null ? 0 : tbm.getSumPolicyFee();//市局期交总保费
			LOG.debug(tbm.toString());
		}
		for (TuiBaoModel tbm : permStafflist) {
			if (tbm.getOrganName().equals(orgName)) {
				permstaffpf = tbm.getPolicyFee() == null ? 0 : tbm.getPolicyFee();//县区员工单退保保费
				permstaffspf = tbm.getSumPolicyFee() == null ? 0 : tbm.getSumPolicyFee();//县区员工单保费
			}
			permstaffpolicyFee += tbm.getPolicyFee() == null ? 0 : tbm.getPolicyFee();//市局员工单退保保费
			permstaffsumPolicyFee += tbm.getSumPolicyFee() == null ? 0 : tbm.getSumPolicyFee();//市局员工单保费
			LOG.debug(tbm.toString());
		}
		
		//orgCurPermRate = (permpf / permspf) * 100;
		orgNewPermRate = ((permpf + reqFee) / permspf) * 100;
		//curPermRate = (permpolicyFee / permsumPolicyFee) * 100;
		newPermRate = ((permpolicyFee + reqFee) / permsumPolicyFee) * 100;
		
		orgCurStaffRate = (permstaffpf / permstaffspf) * 100;
		orgNewStaffRate = ((permstaffpf + reqFee) / permstaffspf) * 100;
		
		curStaffRate = (permstaffpolicyFee / permstaffsumPolicyFee) * 100;
		newStaffRate = ((permstaffpolicyFee+ reqFee) / permstaffsumPolicyFee) * 100;

		//已超xx级（xx%）退保预警/距离三级退保预警xx%
		String orgTips = null;
		if(policyYear==2015 || policyYear==2016) {
			if (orgNewPermRate/100>=0.12) {
				orgTips = "已超一级（12%）退保预警";
			} else if (orgNewPermRate/100>=0.09) {
				orgTips = "已超二级（9%）退保预警";
			} else if (orgNewPermRate/100>=0.07) {
				orgTips = "已超三级（7%）退保预警";
			} else {
				orgTips = "距离三级退保预警" + String.format("%.2f", (0.07-orgNewPermRate/100)*100) + "%";
			}
		} else if(policyYear==2017) {
			if (orgNewPermRate/100>=0.12) {
				orgTips = "已超一级（10%）退保预警";
			} else if (orgNewPermRate/100>=0.09) {
				orgTips = "已超二级（7%）退保预警";
			} else if (orgNewPermRate/100>=0.07) {
				orgTips = "已超三级（5%）退保预警";
			} else {
				orgTips = "距离三级退保预警" + String.format("%.2f", (0.05-orgNewPermRate/100)*100) + "%";
			}
		}
		String cityTips = null;
		if(policyYear==2015 || policyYear==2016) {
			if (newPermRate/100>=0.12) {
				cityTips = "已超一级（12%）退保预警";
			} else if (newPermRate/100>=0.09) {
				cityTips = "已超二级（9%）退保预警";
			} else if (newPermRate/100>=0.07) {
				cityTips = "已超三级（7%）退保预警";
			} else {
				cityTips = "距离三级退保预警" + String.format("%.2f", (0.07-newPermRate/100)*100) + "%";
			}
		} else if(policyYear==2017) {
			if (newPermRate/100>=0.12) {
				cityTips = "已超一级（10%）退保预警";
			} else if (newPermRate/100>=0.09) {
				cityTips = "已超二级（7%）退保预警";
			} else if (newPermRate/100>=0.07) {
				cityTips = "已超三级（5%）退保预警";
			} else {
				cityTips = "距离三级退保预警" + String.format("%.2f", (0.05-newPermRate/100)*100) + "%";
			}
		}
		
		request.setAttribute("year", policyYear);
		request.setAttribute("policyDate", policy.getPolicyDate());
		request.setAttribute("orgTips", orgTips);
		request.setAttribute("cityTips", cityTips);
		
		request.setAttribute("totalAreaFee", permspf/10000);
		request.setAttribute("staffAreaFee", permstaffspf/10000);
		request.setAttribute("orgCurStaffRate", String.format("%.2f", orgCurStaffRate) + "%");
		request.setAttribute("orgNewStaffRate", String.format("%.2f", orgNewStaffRate) + "%");
		request.setAttribute("orgNewPermRate", String.format("%.2f", orgNewPermRate) + "%");
		
		request.setAttribute("totalCityFee", permsumPolicyFee/10000);
		request.setAttribute("staffCityFee", permstaffsumPolicyFee/10000);
		request.setAttribute("curStaffRate", String.format("%.2f", curStaffRate) + "%");
		request.setAttribute("newStaffRate", String.format("%.2f", newStaffRate) + "%");
		request.setAttribute("newPermRate", String.format("%.2f", newPermRate) + "%");
		
		request.setAttribute("holder", policy.getHolder());
		request.setAttribute("policyNo", policyNo);
		request.setAttribute("policyFee", reqFee.toString());
		request.setAttribute("tbReason", tbReason);
		request.setAttribute("orgName", orgName);
		
		String templatePath = request.getServletContext().getRealPath("/") + File.separator + "doc/template.doc";
		String newPath = request.getServletContext().getRealPath("/") + File.separator + "doc" + File.separator + policyNo + ".doc";
		InputStream is = null;
		OutputStream os = null;
		HWPFDocument doc = null;
		try {
			is = new FileInputStream(templatePath);
			doc = new HWPFDocument(is);
			Range range = doc.getRange();
			range.replaceText("${year}", new Integer(policyYear).toString());
			range.replaceText("${policyDate}", StringUtil.date2Str(policy.getPolicyDate(), "yyyy/MM/dd"));
			range.replaceText("${orgTips}", orgTips);
			range.replaceText("${cityTips}", cityTips);
			
			range.replaceText("${totalAreaFee}", (permpolicyFee/10000) + "");
			range.replaceText("${staffAreaFee}", (permstaffpolicyFee/10000) + "");
			range.replaceText("${orgCurStaffRate}", String.format("%.2f", orgCurStaffRate) + "%");
			range.replaceText("${orgNewStaffRate}", String.format("%.2f", orgNewStaffRate) + "%");
			range.replaceText("${orgNewPermRate}", String.format("%.2f", orgNewPermRate) + "%");
			
			range.replaceText("${totalCityFee}", (permsumPolicyFee/10000) + "");
			range.replaceText("${staffCityFee}", (permstaffsumPolicyFee/10000) + "");
			range.replaceText("${curStaffRate}", String.format("%.2f", curStaffRate) + "%");
			range.replaceText("${newStaffRate}", String.format("%.2f", newStaffRate) + "%");
			range.replaceText("${newPermRate}", String.format("%.2f", newPermRate) + "%");
			
			range.replaceText("${holder}", policy.getHolder());
			range.replaceText("${policyNo}", policyNo);
			range.replaceText("${policyFee}", reqFee.toString());
			range.replaceText("${tbReason}", tbReason);
			range.replaceText("${orgName}", orgName);
			os = new FileOutputStream(newPath);
			// 把doc输出到输出流中
			doc.write(os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.closeSource(is, os, doc);
		}

		return SURRENDER_RST;
	}

	/**
	 * 关闭输入流
	 * 
	 * @param is
	 */
	private void closeSource(InputStream is, OutputStream os, HWPFDocument doc) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (doc != null) {
			try {
				doc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
