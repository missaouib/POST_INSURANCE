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
		List<CommonModel> temp = stasticsService.getTuiBaoWarnning();
		for(CommonModel cm:temp) {
			LOG.debug(" ------- " + cm.getOrganName() + ":" + cm.getPolicyFee());
		}
		LOG.debug(temp.toString());
		return TEST_LIST;
	}
}
