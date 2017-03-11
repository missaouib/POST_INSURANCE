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
		LOG.debug(" ------ policy org:" + orgName);
		if (!orgCode.contains(userOrg)) {
			return SURRENDER_REQ;
		}

		orgCode = orgCode.substring(0, 6);

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

		Double policyFee = 0.0;
		Double sumPolicyFee = 0.0;
		Double pf = 0.0;
		Double spf = 0.0;
		Double curRate = 0.0;
		Double newRate = 0.0;
		Double orgCurRate = 0.0;
		Double orgNewRate = 0.0;
		List<TuiBaoModel> list = statService.getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(orgCode + "%", p1, p2, p1, c2, "%%", "%%", "1");
		for (TuiBaoModel tbm : list) {
			if (tbm.getOrganName().equals(orgName)) {
				pf = tbm.getPolicyFee() == null ? 0 : tbm.getPolicyFee();
				spf = tbm.getSumPolicyFee() == null ? 0 : tbm.getSumPolicyFee();
			}
			policyFee += tbm.getPolicyFee() == null ? 0 : tbm.getPolicyFee();
			sumPolicyFee += tbm.getSumPolicyFee() == null ? 0 : tbm.getSumPolicyFee();
			LOG.debug(tbm.toString());
		}

		curRate = (policyFee / sumPolicyFee) * 100;
		newRate = ((policyFee + reqFee) / sumPolicyFee) * 100;

		orgCurRate = (pf / spf) * 100;
		orgNewRate = ((pf + reqFee) / spf) * 100;
		LOG.debug(" -------------- " + curRate + "," + newRate + "," + orgCurRate + "," + orgNewRate);

		request.setAttribute("holder", policy.getHolder());
		request.setAttribute("policyNo", policyNo);
		request.setAttribute("policyFee", reqFee.toString());
		request.setAttribute("tbReason", tbReason);
		request.setAttribute("totalRate", String.format("%.2f", orgCurRate) + "%");
		request.setAttribute("newRate", String.format("%.2f", orgNewRate) + "%");
		request.setAttribute("cityTotalRate", String.format("%.2f", curRate) + "%");
		request.setAttribute("cityNewRate", String.format("%.2f", newRate) + "%");
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
			range.replaceText("${holder}", policy.getHolder());
			range.replaceText("${orgName}", orgName);
			range.replaceText("${policyNo}", policyNo);
			range.replaceText("${policyFee}", reqFee.toString());
			range.replaceText("${tbReason}", tbReason);
			range.replaceText("${totalRate}", String.format("%.2f", orgCurRate) + "%");
			range.replaceText("${newRate}", String.format("%.2f", orgNewRate) + "%");
			range.replaceText("${cityTotalRate}", String.format("%.2f", curRate) + "%");
			range.replaceText("${cityNewRate}", String.format("%.2f", newRate) + "%");
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
