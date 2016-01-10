/**
 * 
 * ==========================================================
 * 发票管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.PayFailList;
import com.gdpost.web.entity.main.PaySuccessList;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.service.insurance.PayListService;
import com.gdpost.web.util.StatusDefine.FEE_FAIL_STATUS;
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

	private static final String BQ_PAY_TO_LIST = "insurance/bqgl/to/list";
	private static final String BQ_PAY_FROM_LIST = "insurance/bqgl/from/list";
	private static final String XQ_PAY_FROM_LIST = "insurance/xqgl/from/list";
	private static final String QY_PAY_FROM_LIST = "insurance/qygl/from/list";
	private static final String LP_PAY_TO_LIST = "insurance/lpgl/to/list";
	private static final String BQ_PAY_TO_SUCCESS_LIST = "insurance/bqgl/success/list";
	private static final String TO_HELP = "insurance/help/feefailgl";
	
	private static final String TO_TOXLS = "insurance/bqgl/to/toXls";
	
	@RequestMapping(value="/help", method=RequestMethod.GET)
	public String toHelp() {
		return TO_HELP;
	}
	
	@RequiresPermissions(value={"ToBQFailList:edit","ToQYFailList:edit","ToLPFailList:edit","ToXQFailList:edit","FromBQFailList:edit","FromQYFailList:edit","FromLPFailList:edit","FromXQFailList:edit"}, logical=Logical.OR)
	@RequestMapping(value="/close/{id}", method=RequestMethod.POST)
	public @ResponseBody String updateStatus(ServletRequest request, @PathVariable("id") Long id) {
		PayFailList req = payListService.get(id);
		req.setStatus(FEE_FAIL_STATUS.CloseStatus.name());
		payListService.saveOrUpdate(req);
		
		return	AjaxObject.newOk("记录关闭成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions(value={"ToBQFailList:view","ToQYFailList:view","ToLPFailList:view","ToXQFailList:view"}, logical=Logical.OR)
	@RequestMapping(value="/to/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		String status = request.getParameter("status");
		String flag = request.getParameter("flag");
		LOG.debug("-----------------status:" + status);
		PayFailList req = new PayFailList();
		if(status == null) {
			req.setStatus(FEE_FAIL_STATUS.NewStatus.name());
			status = FEE_FAIL_STATUS.NewStatus.name();
		} else if(status.trim().length()>0) {
			req.setStatus(status);
		}
		String feeType = "";
		switch(flag) {
		case "bq":
			feeType = "保全受理号";
			break;
		case "lp":
			feeType = "案件号";
			break;
			default:
				
		}
		Specification<PayFailList> specification = DynamicSpecifications.bySearchFilter(request, PayFailList.class,
				new SearchFilter("payType", Operator.EQ, PayFailList.PAY_TO),
				new SearchFilter("feeType", Operator.EQ, feeType),
				new SearchFilter("status", Operator.EQ, status),
				new SearchFilter("relNo", Operator.OR_LIKE, orgCode),
				new SearchFilter("organization.orgCode", Operator.OR_LIKE, orgCode));
		
		List<PayFailList> reqs = payListService.findByExample(specification, page);

		request.setAttribute("status", status);
		map.put("pay", req);
		map.put("ffStatusList", FEE_FAIL_STATUS.values());
		
		map.put("page", page);
		map.put("paylists", reqs);
		
		switch(flag) {
		case "bq":
			return BQ_PAY_TO_LIST;
		case "lp":
			return LP_PAY_TO_LIST;
			default:
				return null;
		}
	}
	
	@RequiresPermissions(value={"ToBQSuccessList:view","ToQYSuccessList:view","ToLPSuccessList:view","ToXQSuccessList:view"}, logical=Logical.OR)
	@RequestMapping(value="/success/list", method={RequestMethod.GET, RequestMethod.POST})
	public String toSuccesslist(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		String status = request.getParameter("status");
		String flag = request.getParameter("flag");
		LOG.debug("-----------------status:" + status);
		PayFailList req = new PayFailList();
		if(status == null) {
			req.setStatus(FEE_FAIL_STATUS.NewStatus.name());
			status = FEE_FAIL_STATUS.NewStatus.name();
		} else if(status.trim().length()>0) {
			req.setStatus(status);
		}
		String feeType = "";
		switch(flag) {
		case "bq":
			feeType = "保全受理号";
			break;
		case "lp":
			feeType = "案件号";
			break;
			default:
				
		}
		Specification<PaySuccessList> specification = DynamicSpecifications.bySearchFilter(request, PaySuccessList.class,
				new SearchFilter("payType", Operator.EQ, PayFailList.PAY_TO),
				new SearchFilter("feeType", Operator.EQ, feeType),
				new SearchFilter("status", Operator.EQ, status),
				new SearchFilter("relNo", Operator.OR_LIKE, orgCode),
				new SearchFilter("organization.orgCode", Operator.OR_LIKE, orgCode));
		
		List<PaySuccessList> reqs = payListService.findBySuccessDtlExample(specification, page);

		request.setAttribute("status", status);
		map.put("pay", req);
		map.put("ffStatusList", FEE_FAIL_STATUS.values());
		
		map.put("page", page);
		map.put("paylists", reqs);
		
		switch(flag) {
		case "bq":
			return BQ_PAY_TO_SUCCESS_LIST;
		case "lp":
			return LP_PAY_TO_LIST;
			default:
				return BQ_PAY_TO_SUCCESS_LIST;
		}
	}
	
	@RequiresPermissions(value={"ToBQFailList:view","ToQYFailList:view","ToLPFailList:view","ToXQFailList:view"}, logical=Logical.OR)
	@RequestMapping(value="/to/toXls", method={RequestMethod.GET, RequestMethod.POST})
	public String failToXls(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		String status = request.getParameter("status");
		String flag = request.getParameter("flag");
		LOG.debug("-----------------status:" + status);
		PayFailList req = new PayFailList();
		if(status == null) {
			req.setStatus(FEE_FAIL_STATUS.NewStatus.name());
			status = FEE_FAIL_STATUS.NewStatus.name();
		} else if(status.trim().length()>0) {
			req.setStatus(status);
		}
		String feeType = "";
		switch(flag) {
		case "bq":
			feeType = "保全受理号";
			break;
		case "lp":
			feeType = "案件号";
			break;
			default:
				
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE, orgCode));
		csf.add(new SearchFilter("payType", Operator.EQ, PayFailList.PAY_TO));
		csf.add(new SearchFilter("feeType", Operator.EQ, feeType));
		if (status.length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		
		Specification<PayFailList> specification = DynamicSpecifications.bySearchFilter(request, PayFailList.class, csf);
		
		List<PayFailList> reqs = payListService.findByExample(specification, page);

		request.setAttribute("date",new Date());
		map.put("paylists", reqs);
		
		return TO_TOXLS;
	}
	
	@RequiresPermissions(value={"FromBQFailList:view","FromQYFailList:view","FromLPFailList:view","FromXQFailList:view"}, logical=Logical.OR)
	@RequestMapping(value="/from/list", method={RequestMethod.GET, RequestMethod.POST})
	public String fromlist(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		String status = request.getParameter("status");
		String flag = request.getParameter("flag");
		LOG.debug("-----------------status:" + status);
		PayFailList req = new PayFailList();
		if(status == null) {
			req.setStatus(FEE_FAIL_STATUS.NewStatus.name());
			status = FEE_FAIL_STATUS.NewStatus.name();
		} else if(status.trim().length()>0) {
			req.setStatus(status);
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
				new SearchFilter("payType", Operator.EQ, PayFailList.PAY_FROM),
				new SearchFilter("feeType", Operator.EQ, feeType),
				new SearchFilter("status", Operator.EQ, status),
				new SearchFilter("relNo", Operator.OR_LIKE, orgCode),
				new SearchFilter("organization.orgCode", Operator.OR_LIKE, orgCode));
		
		List<PayFailList> reqs = payListService.findByExample(specification, page);

		request.setAttribute("status", status);
		map.put("pay", req);
		map.put("ffStatusList", FEE_FAIL_STATUS.values());
		
		map.put("page", page);
		map.put("paylists", reqs);
		
		switch(flag) {
		case "bq":
			return BQ_PAY_FROM_LIST;
		case "qy":
			return QY_PAY_FROM_LIST;
		case "xq":
			return XQ_PAY_FROM_LIST;
			default:
				return null;
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
