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

import com.gdpost.web.controller.insurance.BaseDataController;
import com.gdpost.web.entity.component.CommonModel;
import com.gdpost.web.service.component.StasticsService;
import com.gdpost.web.util.dwz.Page;

@Controller
@RequestMapping("/component")
public class StasticsController {
	private static final Logger LOG = LoggerFactory.getLogger(BaseDataController.class);
	
	@Autowired
	private StasticsService stasticsService;
	
	private static final String TEST_LIST = "insurance/stastics/list";
	
	/*
	 * =======================================
	 * call deal type 回访类型
	 * =======================================
	 * 
	 */
	//@RequiresPermissions("CallDealType:view")
	@RequestMapping(value="/stastics/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listCallDealType(ServletRequest request, Page page, Map<String, Object> map) {
		LOG.debug("-------------------here----------");
		String organCode = request.getParameter("organCode");
		String organName = request.getParameter("organName");
		String pd1 = request.getParameter("policyDate1");
		String pd2 = request.getParameter("policyDate2");
		String csd1 = request.getParameter("csDate1");
		String csd2 = request.getParameter("csDate2");
		if(organCode == null || organCode.trim().length()<=0) {
			organCode = "8644";
		}
		request.setAttribute("organCode", organCode);
		request.setAttribute("organName", organName);
		request.setAttribute("policyDate1", pd1);
		request.setAttribute("policyDate2", pd2);
		request.setAttribute("csDate1", csd1);
		request.setAttribute("csDate2", csd2);
		boolean isPd = false;
		if((pd1!= null && pd1.trim().length()>0) || (pd2!= null && pd2.trim().length()>0)) {
			isPd = true;
		}
		if(pd1 == null || pd1.trim().length()<=0) {
			pd1 = "1900-01-01";
		}
		if(pd2 == null || pd2.trim().length()<=0) {
			pd2 = "9999-12-31";
		}
		boolean isCsd = false;
		if((csd1!= null && csd1.trim().length()>0) || (csd2!= null && csd2.trim().length()>0)) {
			isCsd = true;
		}
		if(csd1 == null || csd1.trim().length()<=0) {
			csd1 = "1900-01-01";
		}
		if(csd2 == null || csd2.trim().length()<=0) {
			csd2 = "9999-12-31";
		}
		isPd = true;
		if(!isPd && !isCsd) {
			return TEST_LIST;
		}
		List<CommonModel> temp = null;
		if(isPd && isCsd) {
			temp = stasticsService.getTuiBaoWarnningWithPolicyDateAndCsDate(organCode + "%", pd1, pd2, csd1, csd2);
		} else if(isPd) {
			temp = stasticsService.getTuiBaoWarnningWithPolicyDate(organCode + "%", pd1, pd2);
		} else {
			temp = stasticsService.getTuiBaoWarnningWithCsDate(organCode + "%", csd1, csd2);
		}
		
		LOG.debug(" ------------ result size:" + temp.size());
		return TEST_LIST;
	}
}
