package com.gdpost.web.controller.component;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdpost.utils.StringUtil;
import com.gdpost.web.controller.insurance.BaseDataController;
import com.gdpost.web.entity.basedata.Prd;
import com.gdpost.web.entity.component.CommonModel;
import com.gdpost.web.service.component.StasticsService;
import com.gdpost.web.service.insurance.BaseDataService;
import com.gdpost.web.util.dwz.Page;

@Controller
@RequestMapping("/component")
public class StasticsController {
	private static final Logger LOG = LoggerFactory.getLogger(BaseDataController.class);
	
	@Autowired
	private StasticsService stasticsService;
	@Autowired
	private BaseDataService prdService;
	
	private static final String TUIBAO_LIST = "insurance/stastics/tuibao";
	
	/*
	 * =======================================
	 * tuibao
	 * =======================================
	 * 
	 */
	//@RequiresPermissions("CallDealType:view")
	@RequestMapping(value="/stastics/tuibao", method={RequestMethod.GET, RequestMethod.POST})
	public String listCallDealType(ServletRequest request, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("orgCode");
		String organName = request.getParameter("name");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String csd1 = request.getParameter("csDate1");
		String csd2 = request.getParameter("csDate2");
		String flag = request.getParameter("flag");
		String netFlag = request.getParameter("netFlag");
		String prdCode = request.getParameter("prd.prdCode");
		String prdName = request.getParameter("prd.prdName");
		if(organCode == null || organCode.trim().length()<=0) {
			organCode = "8644";
		}
		String toPrdCode = prdCode;
		if(prdCode == null || prdCode.trim().length()<=0) {
			toPrdCode = "%%";
		}
		
		request.setAttribute("orgCode", organCode);
		request.setAttribute("name", organName);
		request.setAttribute("flag", flag);
		request.setAttribute("netFlag", netFlag);
		request.setAttribute("prdCode", prdCode);
		request.setAttribute("prdName", prdName);
		if(flag == null || !flag.trim().equals("city")) {
			flag = "prov";
		}
		boolean hasNet = true;
		if(netFlag == null || netFlag.trim().length()<=0) {
			hasNet = false;
		}
		CommonModel cm = new CommonModel();
		cm.setFlag(flag);
		cm.setNetFlag(netFlag);
		cm.setPrdCode(prdCode);
		request.setAttribute("CommonModel", cm);
	
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
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		request.setAttribute("csDate1", csd1);
		request.setAttribute("csDate2", csd2);
		
		List<CommonModel> temp = null;
		if(hasNet) {
			temp = stasticsService.getProvTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2, netFlag, toPrdCode);
		} else {
			temp = stasticsService.getProvTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(organCode + "%", pd1, pd2, csd1, csd2, toPrdCode);
		}
		
		request.setAttribute("cmRst", temp);
		String col = "";
		String colData = "";
		for(CommonModel tcm:temp) {
			col += "\"" + tcm.getOrganName() + "\",";
			colData += "\"" + tcm.getPolicyFee()/tcm.getSumPolicyFee() + "\",";
		}
		request.setAttribute("col", col);
		request.setAttribute("colData", colData);
		
		Page page = new Page();
		page.setNumPerPage(50);
		List<Prd> prds = prdService.findAllPrd(page);
		request.setAttribute("prds", prds);
		LOG.debug(" ------------ result size:" + temp.size());
		return TUIBAO_LIST;
	}
}
