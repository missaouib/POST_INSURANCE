/**
 * 
 * ==========================================================
 * 发票管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.entity.main.PayFailList;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.service.insurance.PayListService;
import com.gdpost.web.util.StatusDefine.FEE_FAIL_STATUS;
import com.gdpost.web.util.StatusDefine.FP_STATUS;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/pay")
public class PayListController {
	private static final Logger LOG = LoggerFactory.getLogger(PayListController.class);
	
	@Autowired
	private PayListService payListService;

	private static final String PAY_TO_LIST = "insurance/paylist/to/list";
	private static final String PAY_FROM_LIST = "insurance/paylist/from/list";
	private static final String TO_HELP = "insurance/help/fpgl";
	
	@RequestMapping(value="/help", method=RequestMethod.GET)
	public String toHelp() {
		return TO_HELP;
	}
	
	@RequiresPermissions(value={"ToFailList:edit", "FromFailList:edit"}, logical=Logical.OR)
	@RequestMapping(value="/close/{id}", method=RequestMethod.POST)
	public @ResponseBody String updateStatus(ServletRequest request, @PathVariable("id") Long id) {
		PayFailList req = payListService.get(id);
		req.setStatus(0);
		payListService.saveOrUpdate(req);
		
		return	AjaxObject.newOk("记录关闭成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("ToFailList:view")
	@RequestMapping(value="/to/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String s = request.getParameter("status");
		String flag = request.getParameter("flag");
		LOG.debug("-----------------status:" + s);
		PayFailList req = new PayFailList();
		Integer status = null;
		if(s == null) {
			req.setStatus(FEE_FAIL_STATUS.NewStatus.getDesc());
			status = 1;
		} else if(s.trim().length()>0) {
			req.setStatus(FEE_FAIL_STATUS.valueOf(s).getDesc());
			status = req.getStatus();
		}
		String feeType = "";
		switch(flag) {
		case "bq":
			feeType = "保全受理号";
			break;
		case "qy":
			feeType = "投保单印刷号";
			break;
		case "xq":
			feeType = "保单合同号";
			break;
			default:
				
		}
		Specification<PayFailList> specification = DynamicSpecifications.bySearchFilter(request, PayFailList.class, 
				new SearchFilter("payType", Operator.EQ, PayFailList.PAY_TO),
				new SearchFilter("feeType", Operator.EQ, feeType));
		if(status != null) {
			specification = DynamicSpecifications.bySearchFilter(request, PayFailList.class, 
					new SearchFilter("payType", Operator.EQ, PayFailList.PAY_TO),
					new SearchFilter("feeType", Operator.EQ, feeType),
					new SearchFilter("status", Operator.EQ, status));
		}
		if(user.getOrganization().getOrgCode().length()>4) {
			specification = DynamicSpecifications.bySearchFilter(request, PayFailList.class,
					new SearchFilter("payType", Operator.EQ, PayFailList.PAY_TO),
					new SearchFilter("feeType", Operator.EQ, feeType),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE, user.getOrganization().getOrgCode()));
			if(status != null) {
				specification = DynamicSpecifications.bySearchFilter(request, PayFailList.class, 
						new SearchFilter("payType", Operator.EQ, PayFailList.PAY_TO),
						new SearchFilter("feeType", Operator.EQ, feeType),
						new SearchFilter("status", Operator.EQ, status),
						new SearchFilter("policy.organization.orgCode", Operator.LIKE, user.getOrganization().getOrgCode()));
			}
		}
		List<PayFailList> reqs = payListService.findByExample(specification, page);

		map.put("pay", req);
		map.put("ffStatusList", FEE_FAIL_STATUS.values());
		
		map.put("page", page);
		map.put("paylists", reqs);
		return PAY_TO_LIST;
	}
	
	@RequiresPermissions("FromFailList:view")
	@RequestMapping(value="/from/list", method={RequestMethod.GET, RequestMethod.POST})
	public String fromlist(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String s = request.getParameter("status");
		String flag = request.getParameter("flag");
		LOG.debug("-----------------status:" + s);
		PayFailList req = new PayFailList();
		if(s == null) {
			req.setStatus(FEE_FAIL_STATUS.NewStatus.getDesc());
			s = FP_STATUS.NewStatus.name();
		} else if(s.trim().length()>0) {
			req.setStatus(FEE_FAIL_STATUS.valueOf(s).getDesc());
		}
		String feeType = "";
		switch(flag) {
		case "bq":
			feeType = "保全受理号";
			break;
		case "qy":
			feeType = "投保单印刷号";
			break;
		case "xq":
			feeType = "保单合同号";
			break;
		case "lq":
			feeType = "案件号";
			break;
			default:
				
		}
		Specification<PayFailList> specification = DynamicSpecifications.bySearchFilter(request, PayFailList.class, 
				new SearchFilter("payType", Operator.EQ, PayFailList.PAY_FROM),
				new SearchFilter("feeType", Operator.EQ, feeType),
				new SearchFilter("status", Operator.LIKE, s));
		if(user.getOrganization().getOrgCode().length()>4) {
			specification = DynamicSpecifications.bySearchFilter(request, PayFailList.class,
					new SearchFilter("payType", Operator.EQ, PayFailList.PAY_FROM),
					new SearchFilter("feeType", Operator.EQ, feeType),
					new SearchFilter("status", Operator.LIKE, s),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE, user.getOrganization().getOrgCode()));
		}
		List<PayFailList> reqs = payListService.findByExample(specification, page);

		map.put("pay", req);
		map.put("ffStatusList", FEE_FAIL_STATUS.values());
		
		map.put("page", page);
		map.put("paylists", reqs);
		return PAY_FROM_LIST;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
