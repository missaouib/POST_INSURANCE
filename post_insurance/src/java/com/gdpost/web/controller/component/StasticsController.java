package com.gdpost.web.controller.component;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdpost.web.util.dwz.Page;

@Controller
@RequestMapping("/component")
public class StasticsController {
	//private static final Logger LOG = LoggerFactory.getLogger(BaseDataController.class);
	
	//@Autowired
	//private BaseDataService baseService;
	
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
		return TEST_LIST;
	}
}
