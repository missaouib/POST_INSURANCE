/**
 * 
 * ==========================================================
 * 发票管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.StringUtil;
import com.gdpost.web.entity.insurance.InvoiceReq;
import com.gdpost.web.entity.insurance.Policy;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.insurance.FpglService;
import com.gdpost.web.service.insurance.PolicyService;
import com.gdpost.web.util.StatusDefine.FP_STATUS;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/fpgl")
public class FpglController {
	private static final Logger LOG = LoggerFactory.getLogger(FpglController.class);
	
	@Autowired
	private FpglService fpglService;
	
	@Autowired
	private PolicyService policyService;

	private static final String CREATE = "insurance/fpgl/create";
	private static final String UPDATE = "insurance/fpgl/update";
	private static final String LIST = "insurance/fpgl/list";
	private static final String UPDATE_DEALSTATUS = "insurance/fpgl/setBillNo";
	private static final String TO_HELP = "insurance/help/fpgl";
	private static final String TO_XLS = "insurance/fpgl/toXls";
	
	@RequestMapping(value="/help", method=RequestMethod.GET)
	public String toHelp() {
		return TO_HELP;
	}
	
	@RequiresPermissions("InvoiceReq:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}
	
	@Log(message="添加了{0}发票申请。", level=LogLevel.WARN, module=LogModule.FPGL)
	@RequiresPermissions("InvoiceReq:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid InvoiceReq req) {
		Date feeDate = req.getFeeDate();
		Date feeDate1 = StringUtil.dateAdd(feeDate, -30);
		Date feeDate2 = StringUtil.dateAdd(feeDate, 30);
		if(fpglService.getByPolicyAndFeeDate(req.getPolicy(), feeDate1, feeDate2) != null) {
			return AjaxObject.newError("此发票申请已申请当期发票：").toString();
		}
		try {
			req.setDealMan(SecurityUtils.getShiroUser().getId());
			req.setReqDate(new Date());
			req.setStatus(FP_STATUS.NewStatus.name());
			LOG.debug("---------" + req.getPolicy().getPolicyNo());
			Policy p = policyService.get(req.getPolicy().getId());
			req.setPolicy(p);
			fpglService.saveOrUpdate(req);
		} catch (Exception e) {
			return AjaxObject.newError("添加发票申请失败：" + req.getPolicy().getId() + ":" + e.getMessage()).toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{req.getPolicy().getPolicyNo()}));
		return AjaxObject.newOk("添加发票申请成功！").toString();
	}
	
	@RequiresPermissions("InvoiceReq:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		InvoiceReq req = fpglService.get(id);
		
		map.put("req", req);
		return UPDATE;
	}
	
	@RequiresPermissions("InvoiceReq:edit")
	@RequestMapping(value="/updateDealStatus/{id}", method=RequestMethod.GET)
	public String preUpdateDealStatus(@PathVariable Long id, Map<String, Object> map) {
		InvoiceReq req = fpglService.get(id);
		
		map.put("req", req);
		return UPDATE_DEALSTATUS;
	}
	
	@Log(message="修改了{0}发票申请的信息。", level=LogLevel.WARN, module=LogModule.FPGL)
	@RequiresPermissions("InvoiceReq:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(InvoiceReq req) {
		Policy p = policyService.get(req.getPolicy().getId());
		req.setPolicy(p);
		fpglService.saveOrUpdate(req);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{req.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("修改发票申请成功！").toString(); 
	}
	
	@Log(message="修改了{0}发票申请的状态。", level=LogLevel.WARN, module=LogModule.FPGL)
	@RequiresPermissions(value={"InvoiceReq:reset","InvoiceReq:deal"}, logical=Logical.OR)
	@RequestMapping(value="/{status}/{id}", method=RequestMethod.POST)
	public @ResponseBody String updateStatus(ServletRequest request, @PathVariable("status") String status, @PathVariable("id") Long id) {
		InvoiceReq req = fpglService.get(id);
		String billNo = request.getParameter("billNo");
		FP_STATUS bs = FP_STATUS.DealStatus;
		try {
			bs = FP_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		switch (bs) {
		case DealStatus:
			req.setSendMan(SecurityUtils.getShiroUser().getId());
			req.setSendDate(new Date());
			req.setBillNo(billNo);
			break;
		case ReceiveStatus:
			req.setReceiveDate(new Date());
			req.setReceiveMan(SecurityUtils.getShiroUser().getId());
			break;
		case CloseStatus:
			break;
			default:
				break;
		}
		req.setStatus(status);
		fpglService.saveOrUpdate(req);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{req.getPolicy().getPolicyNo()}));
		if(billNo != null && billNo.trim().length() > 0) {
			return	AjaxObject.newOk("发票处理操作成功！").toString();
		}
		return	AjaxObject.newOk("发票状态更新成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}发票申请。", level=LogLevel.WARN, module=LogModule.FPGL)
	@RequiresPermissions("InvoiceReq:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		InvoiceReq req = null;
		try {
			req = fpglService.get(id);
			fpglService.delete(req.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除发票申请失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{req.getPolicy().getPolicyNo()}));
		return AjaxObject.newOk("删除发票申请成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}发票申请。", level=LogLevel.WARN, module=LogModule.FPGL)
	@RequiresPermissions("InvoiceReq:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		String[] policys = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				InvoiceReq req = fpglService.get(ids[i]);
				fpglService.delete(req.getId());
				
				policys[i] = req.getPolicy().getPolicyNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除发票申请失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("删除发票申请成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("InvoiceReq:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String search_LIKE_reqFlag = request.getParameter("search_LIKE_reqFlag");
		request.setAttribute("search_LIKE_reqFlag", search_LIKE_reqFlag);
		String status = request.getParameter("status");
		LOG.debug("-----------------status:" + status);
		InvoiceReq req = new InvoiceReq();
		req.setSearch_LIKE_reqFlag(search_LIKE_reqFlag);
		if(status == null) {
			req.setStatus(FP_STATUS.NewStatus.name());
			status = FP_STATUS.NewStatus.name();
			if(user.getOrganization().getOrgCode().length()>4) {
				req.setStatus(FP_STATUS.DealStatus.name());
				status = FP_STATUS.DealStatus.name();
			}
		} else if(status.trim().length()>0) {
			req.setStatus(FP_STATUS.valueOf(status).name());
		}
		LOG.debug("-----------------status2:" + status);
		request.setAttribute("status", status);
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("reqDate");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, user.getOrganization().getOrgCode()));
		if(status.trim().length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		
		Specification<InvoiceReq> specification = DynamicSpecifications.bySearchFilter(request, InvoiceReq.class, csf);
		List<InvoiceReq> reqs = fpglService.findByExample(specification, page);
		
		map.put("req", req);
		map.put("fpStatusList", FP_STATUS.values());
		
		map.put("page", page);
		map.put("reqs", reqs);
		return LIST;
	}
	
	@RequiresPermissions("InvoiceReq:view")
	@RequestMapping(value="/toXls", method=RequestMethod.GET)
	public String toXls(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String search_LIKE_reqFlag = request.getParameter("search_LIKE_reqFlag");
		String status = request.getParameter("status");
		if(status == null) {
			status = FP_STATUS.NewStatus.name();
			if(user.getOrganization().getOrgCode().length()>4) {
				status = FP_STATUS.DealStatus.name();
			}
		}
		page.setNumPerPage(65564);
		
		List<Order> orders=new ArrayList<Order>();
		orders.add(new Order(Direction.ASC, "policy.organization.orgCode"));
		orders.add(new Order(Direction.ASC, "policy.holder"));
		page.setOrders(orders);
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, user.getOrganization().getOrgCode()));
		if(status != null && status.trim().length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		
		Specification<InvoiceReq> specification = DynamicSpecifications.bySearchFilter(request, InvoiceReq.class, csf);
		List<InvoiceReq> reqs = fpglService.findByExample(specification, page);
	
		AtomicInteger xqai = new AtomicInteger(0);
		String type = "3%03d";
		if(search_LIKE_reqFlag != null && search_LIKE_reqFlag.equals("续期")) {
			type = "2%03d";
		}
		//List<InvoiceReq> finalReqs = new ArrayList<InvoiceReq>();
		for(InvoiceReq tmp:reqs) {
			tmp.setBillId(String.format(type,xqai.incrementAndGet()));
			//finalReqs.add(tmp);
		}
		
		map.put("reqs", reqs);
		return TO_XLS;
	}
	
	@RequestMapping(value="/lookupReqSuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupBQIssusSuggest(ServletRequest request, Map<String, Object> map) {
		String reqMan = request.getParameter("reqMan");
		LOG.debug("-------- reqMan:" + reqMan);
		User user = SecurityUtils.getShiroUser().getUser();
		if(reqMan != null && reqMan.trim().length() < 1) {
			return "[{}]";
		}
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("reqMan", Operator.LIKE, reqMan));
		if(user.getOrganization().getOrgCode().length()>4) {
			csf.add(new SearchFilter("dealMan", Operator.EQ, user.getId()));
		}
		csf.add(new SearchFilter("reqAddr", Operator.NOTNULL, null));
		//LOG.debug("-------- csf:" + csf);
		Specification<InvoiceReq> specification = DynamicSpecifications.bySearchFilterWithoutRequest(InvoiceReq.class, csf);
		Page page = new Page();
		page.setNumPerPage(3);
		List<InvoiceReq> org = fpglService.findByExample(specification, page);
		SerializeConfig mapping = new SerializeConfig();
		HashMap<String, String> fm = new HashMap<String, String>();
		fm.put("reqMan", "reqMan");
		fm.put("reqAddr", "reqAddr");
		fm.put("phone", "phone");
		mapping.put(InvoiceReq.class, new JavaBeanSerializer(InvoiceReq.class, fm));
		String str = JSON.toJSONString(org, mapping);
		LOG.debug("---------------- fapiao suggest: " + str);
		return str;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
