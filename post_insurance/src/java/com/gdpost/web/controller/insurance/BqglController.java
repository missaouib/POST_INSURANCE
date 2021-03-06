/**
 * 
 * ==========================================================
 * 保全管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gdpost.utils.BeanValidators;
import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.StringUtil;
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.entity.insurance.ConservationDtl;
import com.gdpost.web.entity.insurance.CsExpire;
import com.gdpost.web.entity.insurance.CsExpireDtl;
import com.gdpost.web.entity.insurance.CsLoan;
import com.gdpost.web.entity.insurance.CsReissue;
import com.gdpost.web.entity.insurance.CsReport;
import com.gdpost.web.entity.insurance.MtdReq;
import com.gdpost.web.entity.insurance.OffsiteConservation;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.insurance.BqglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.StatusDefine.BQ_STATUS;
import com.gdpost.web.util.StatusDefine.CSEXPIRE_STATUS;
import com.gdpost.web.util.StatusDefine.FP_STATUS;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/bqgl")
public class BqglController {
	private static final Logger LOG = LoggerFactory.getLogger(BqglController.class);
	
	@Autowired
	private BqglService bqglService;

	private static final String CREATE = "insurance/bqgl/wtj/create";
	private static final String UPDATE = "insurance/bqgl/wtj/update";
	private static final String LIST = "insurance/bqgl/wtj/list";
	private static final String ISSUE_LIST = "insurance/bqgl/wtj/issuelist";
	private static final String TO_HELP = "insurance/help/bqgl";

	private static final String TO_XLS = "insurance/bqgl/wtj/toXls";
	
	private static final String CREATE_OC = "insurance/bqgl/offsite/create";
	private static final String UPDATE_OC = "insurance/bqgl/offsite/update";
	private static final String PROV_UPDATE_OC = "insurance/bqgl/offsite/provupdate";
	private static final String LIST_OC = "insurance/bqgl/offsite/list";

	private static final String OC_TO_XLS = "insurance/bqgl/offsite/toXls";
	
	private static final String UPDATE_RI = "insurance/bqgl/reissue/update";
	private static final String PROV_UPDATE_RI = "insurance/bqgl/reissue/provupdate";
	private static final String LIST_RI = "insurance/bqgl/reissue/list";
	private static final String RI_TO_XLS = "insurance/bqgl/reissue/toXls";
	private static final String RI_MAIL_DATE = "insurance/bqgl/reissue/mailDate";
	private static final String RI_REC_DATE = "insurance/bqgl/reissue/recDate";
	
	private static final String C_REQ_LIST = "insurance/bqgl/req/list";
	private static final String C_TO_XLS = "insurance/bqgl/req/toXls";
	
	private static final String CS_LIST = "insurance/bqgl/report/list";
	private static final String CS_LIST_TO_XLS = "insurance/bqgl/report/toXls";
	
	private static final String C_LOAN_LIST = "insurance/bqgl/loan/list";
	private static final String C_LOAN_TO_XLS = "insurance/bqgl/loan/toXls";
	private static final String C_LOAN_REMARK = "insurance/bqgl/loan/remark";
	
	private static final String C_EXPIRE_LIST = "insurance/bqgl/expire/list";
	private static final String C_EXPIRE_TO_XLS = "insurance/bqgl/expire/toXls";
	private static final String C_EXPIRE_UPDATE = "insurance/bqgl/expire/update";
	
	String strError = "{\"jsonrpc\":\"2.0\",\"result\":\"error\",\"id\":\"id\",\"message\":\"操作失败。\"}";
	
	@RequiresPermissions("Cservice:save")
	@RequestMapping(value="/issue/create", method=RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}
	
	@RequestMapping(value="/help", method=RequestMethod.GET)
	public String toHelp() {
		return TO_HELP;
	}
	
	@Log(message="添加了{0}保全复核问题。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions("Cservice:save")
	@RequestMapping(value="/issue/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid ConservationDtl issue) {	
		try {
			issue.setCsDate(new Date());
			issue.setCsUserId(SecurityUtils.getShiroUser().getId());
			issue.setStatus(BQ_STATUS.NewStatus.name());
			if(issue.getInfo().equals("审核通过")) {
				issue.setStatus(BQ_STATUS.CloseStatus.name());
			}
			issue = bqglService.saveOrUpdate(issue);
//			if(issue.getConservationCode().equalsIgnoreCase("htbf")) {
//				CsReissue cr = new CsReissue();
//				cr.getCsReport(issue);
//				cr.setStatus(FP_STATUS.NewStatus.name());
//				LOG.debug("---------cs reissue: " + cr.toString());
//				bqglService.updateCsReissue(cr);
//			}
		} catch (ExistedException e) {
			return AjaxObject.newError("添加保全复核问题失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return AjaxObject.newOk("添加保全复核问题成功！").toString();
	}
	
	@RequiresPermissions("Cservice:edit")
	@RequestMapping(value="/issue/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		ConservationDtl issue = bqglService.get(id);
		
		map.put("issue", issue);
		return UPDATE;
	}
	
	@Log(message="修改了{0}保全复核问题的信息。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions("Cservice:edit")
	@RequestMapping(value="/issue/update", method=RequestMethod.POST)
	public @ResponseBody String update(ConservationDtl issue) {
		bqglService.saveOrUpdate(issue);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("修改保全复核问题成功！").toString(); 
	}
	
	@Log(message="修改了{0}保全复核问题的状态。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions(value={"Cservice:reset","Cservice:deal"}, logical=Logical.OR)
	@RequestMapping(value="/issue/{status}/{id}", method=RequestMethod.POST)
	public @ResponseBody String updateStatus(@PathVariable("status") String status, @PathVariable("id") Long id) {
		ConservationDtl issue = bqglService.get(id);
		BQ_STATUS bs = BQ_STATUS.DealStatus;
		try {
			bs = BQ_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		switch (bs) {
		case DealStatus:
			issue.setDealUserId(SecurityUtils.getShiroUser().getId());
			break;
		case CancelStatus:
			issue.setCancelDate(new Date());
			issue.setCancelFlag(true);
			issue.setCancelMan(SecurityUtils.getShiroUser().getId());
			break;
		case CloseStatus:
			break;
			default:
				break;
		}
		issue.setStatus(status);
		bqglService.saveOrUpdate(issue);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("修改保全复核问题成功！").setCallbackType("").toString();
	}
	
	@Log(message="批量修改了{0}保全复核问题的{1}状态。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions(value={"Cservice:reset","Cservice:deal"}, logical=Logical.OR)
	@RequestMapping(value="/issue/{status}", method=RequestMethod.POST)
	public @ResponseBody String batchUpdateStatus(@PathVariable("status") String status, Long[] ids) {
		ConservationDtl issue = null;
		BQ_STATUS bs = BQ_STATUS.DealStatus;
		try {
			bs = BQ_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		String[] policys = new String[ids.length];
		for(int i = 0; i<ids.length; i++) {
			issue = bqglService.get(ids[i]);
			switch (bs) {
			case DealStatus:
				issue.setDealUserId(SecurityUtils.getShiroUser().getId());
				break;
			case CancelStatus:
				issue.setCancelDate(new Date());
				issue.setCancelFlag(true);
				issue.setCancelMan(SecurityUtils.getShiroUser().getId());
				break;
			case CloseStatus:
				break;
				default:
					break;
			}
			issue.setStatus(status);
			bqglService.saveOrUpdate(issue);
			policys[i] = issue.getPolicy().getPolicyNo();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys), status}));
		return	AjaxObject.newOk("批量" + status + "保全复核问题成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}保全复核问题。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions("Cservice:delete")
	@RequestMapping(value="/issue/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		ConservationDtl issue = null;
		try {
			issue = bqglService.get(id);
			bqglService.delete(issue.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除保全复核问题失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return AjaxObject.newOk("删除保全复核问题成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}保全复核问题。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions("Cservice:delete")
	@RequestMapping(value="/issue/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		String[] policys = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				ConservationDtl issue = bqglService.get(ids[i]);
				bqglService.delete(issue.getId());
				
				policys[i] = issue.getPolicy().getPolicyNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除保全复核问题失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("删除保全复核问题成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("Cservice:view")
	@RequestMapping(value="/issue/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		//默认返回未处理工单
		String status = request.getParameter("status");
		LOG.debug("-------------- status: " + status);
		ConservationDtl issue = new ConservationDtl();
		if(status == null) {
			status = "";
		} else if(status.trim().length()>0) {
			issue.setStatus(BQ_STATUS.valueOf(status).name());
		}
		
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("csDate");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
		if (status.length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		} else {
			csf.add(new SearchFilter("status", Operator.NEQ, BQ_STATUS.CloseStatus.name()));
		}
		
		Specification<ConservationDtl> specification = DynamicSpecifications.bySearchFilter(request, ConservationDtl.class, csf);
		
		List<ConservationDtl> issues = bqglService.findByExample(specification, page);
		
		map.put("issue", issue);
		map.put("status", status);
		map.put("baStatusList", BQ_STATUS.values());
		map.put("page", page);
		map.put("issues", issues);
		return LIST;
	}
	
	@Log(message="下载了保全差错件数据！", level=LogLevel.INFO, module=LogModule.BQGL)
	@RequiresPermissions("Cservice:view")
	@RequestMapping(value="/toXls", method=RequestMethod.GET)
	public String toXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		page.setNumPerPage(65564);
		//默认返回未处理工单
		String status = request.getParameter("status");
		LOG.debug("-------------- status: " + status);
		ConservationDtl issue = new ConservationDtl();
		if(status == null) {
			status = "";
		} else if(status.trim().length()>0) {
			issue.setStatus(BQ_STATUS.valueOf(status).name());
		}
		
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("csDate");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
		if (status.length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		
		Specification<ConservationDtl> specification = DynamicSpecifications.bySearchFilter(request, ConservationDtl.class, csf);
		
		List<ConservationDtl> reqs = bqglService.findByExample(specification, page);
		
		map.put("reqs", reqs);
		return TO_XLS;
	}
	
	@RequiresPermissions("Cservice:view")
	@RequestMapping(value="/issuelist", method={RequestMethod.GET, RequestMethod.POST})
	public String issueList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put("issueList", bqglService.getTODOIssueList(shiroUser.getUser()));
		return ISSUE_LIST;
	}
	
	/*
	 * ===========================================
	 * 异地保全管理
	 * ===========================================
	 */
	
	@RequiresPermissions("OffsiteConservation:save")
	@RequestMapping(value="/offsite/create", method=RequestMethod.GET)
	public String preCreateOffsiteConservation() {
		return CREATE_OC;
	}
	
	@Log(message="添加了{0}异地保全。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions("OffsiteConservation:save")
	@RequestMapping(value="/offsite/create", method=RequestMethod.POST)
	public @ResponseBody String createOffsiteConservation(@Valid OffsiteConservation offsite) {	
		try {
			offsite.setOperateTime(new Date());
			offsite.setOperatorId(SecurityUtils.getShiroUser().getId());
			offsite.setStatus(BQ_STATUS.NewStatus.name());
			bqglService.saveOrUpdateOffsiteConservation(offsite);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加异地保全失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{offsite.getPolicyNo()}));
		return AjaxObject.newOk("添加异地保全成功！").toString();
	}
	
	@RequiresPermissions("OffsiteConservation:edit")
	@RequestMapping(value="/offsite/update/{id}", method=RequestMethod.GET)
	public String preUpdateOffsiteConservation(@PathVariable Long id, Map<String, Object> map) {
		OffsiteConservation offsite = bqglService.getOffsiteConservation(id);
		
		map.put("offsite", offsite);
		return UPDATE_OC;
	}
	
	@RequiresPermissions("OffsiteConservation:provEdit")
	@RequestMapping(value="/offsite/provupdate/{id}", method=RequestMethod.GET)
	public String preProvUpdateOffsiteConservation(@PathVariable Long id, Map<String, Object> map) {
		OffsiteConservation offsite = bqglService.getOffsiteConservation(id);
		
		map.put("offsite", offsite);
		return PROV_UPDATE_OC;
	}
	
	@Log(message="修改了{0}异地保全的信息。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions("OffsiteConservation:edit")
	@RequestMapping(value="/offsite/update", method=RequestMethod.POST)
	public @ResponseBody String updateOffsiteConservation(OffsiteConservation src) {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		//LOG.debug("--------------0:" + src.toString());
		OffsiteConservation offsite = bqglService.getOffsiteConservation(src.getId());
		//LOG.debug("--------------1:" + offsite.toString());
		BeanUtils.copyProperties(src, offsite, BeanValidators.getNullPropertyNames(src));
		bqglService.saveOrUpdateOffsiteConservation(offsite);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{offsite.getPolicyNo()}));
		return	AjaxObject.newOk("修改异地保全成功！").toString(); 
	}
	
	@Log(message="修改了{0}异地保全的状态。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions(value={"OffsiteConservation:reset","OffsiteConservation:deal"}, logical=Logical.OR)
	@RequestMapping(value="/offsite/{status}/{id}", method=RequestMethod.POST)
	public @ResponseBody String updateOffsiteConservationStatus(@PathVariable("status") String status, @PathVariable("id") Long id) {
		OffsiteConservation offsite = bqglService.getOffsiteConservation(id);
		BQ_STATUS bs = BQ_STATUS.DealStatus;
		try {
			bs = BQ_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		switch (bs) {
		case CloseStatus:
			break;
			default:
				break;
		}
		offsite.setStatus(status);
		bqglService.saveOrUpdateOffsiteConservation(offsite);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{offsite.getPolicyNo()}));
		return	AjaxObject.newOk("修改异地保全成功！").setCallbackType("").toString();
	}
	
	@Log(message="批量修改了{0}异地保全的{1}状态。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions(value={"OffsiteConservation:reset","OffsiteConservation:deal"}, logical=Logical.OR)
	@RequestMapping(value="/offsite/{status}", method=RequestMethod.POST)
	public @ResponseBody String batchUpdateOffsiteConservationStatus(@PathVariable("status") String status, Long[] ids) {
		OffsiteConservation offsite = null;
		BQ_STATUS bs = BQ_STATUS.DealStatus;
		try {
			bs = BQ_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		String[] policys = new String[ids.length];
		for(int i = 0; i<ids.length; i++) {
			offsite = bqglService.getOffsiteConservation(ids[i]);
			switch (bs) {
			case CloseStatus:
				break;
				default:
					break;
			}
			offsite.setStatus(status);
			bqglService.saveOrUpdateOffsiteConservation(offsite);
			policys[i] = offsite.getPolicyNo();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys), status}));
		return	AjaxObject.newOk("批量" + status + "异地保全成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}异地保全。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions("OffsiteConservation:delete")
	@RequestMapping(value="/offsite/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteOffsiteConservation(@PathVariable Long id) {
		OffsiteConservation offsite = null;
		try {
			offsite = bqglService.getOffsiteConservation(id);
			bqglService.deleteOffsiteConservation(offsite.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除异地保全失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{offsite.getPolicyNo()}));
		return AjaxObject.newOk("删除异地保全成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}异地保全。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions("OffsiteConservation:delete")
	@RequestMapping(value="/offsite/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyOffsiteConservation(Long[] ids) {
		String[] policys = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				OffsiteConservation offsite = bqglService.getOffsiteConservation(ids[i]);
				bqglService.deleteOffsiteConservation(offsite.getId());
				
				policys[i] = offsite.getPolicyNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除异地保全失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("删除异地保全成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("OffsiteConservation:view")
	@RequestMapping(value="/offsite/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listOffsiteConservation(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(user.getOrganization().getOrgCode())){
			orgCode = user.getOrganization().getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		//默认返回未处理工单
		String status = request.getParameter("status");
		LOG.debug("-------------- status: " + status + "  orgCode:" + orgCode);
		OffsiteConservation offsite = new OffsiteConservation();
		if(status == null) {
			status = "";
		} else if(status.trim().length()>0) {
			offsite.setStatus(BQ_STATUS.valueOf(status).name());
		}
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("dealDate");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE_R, orgCode));
		if (status.length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		} else {
			csf.add(new SearchFilter("status", Operator.NEQ, BQ_STATUS.CloseStatus.name()));
		}
		Specification<OffsiteConservation> specification = DynamicSpecifications.bySearchFilter(request, OffsiteConservation.class, csf);
		
		List<OffsiteConservation> offsites = bqglService.findByOffsiteConservationExample(specification, page);
		
		map.put("offsite", offsite);
		map.put("status", status);
		map.put("baStatusList", BQ_STATUS.values());
		map.put("page", page);
		map.put("offsites", offsites);
		return LIST_OC;
	}
	
	@Log(message="下载了异地保全数据！", level=LogLevel.INFO, module=LogModule.BQGL)
	@RequiresPermissions("OffsiteConservation:view")
	@RequestMapping(value="/offsite/toXls", method=RequestMethod.GET)
	public String ocToXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(user.getOrganization().getOrgCode())){
			orgCode = user.getOrganization().getOrgCode();
		}
		page.setNumPerPage(65564);
		//默认返回未处理工单
		String status = request.getParameter("status");
		LOG.debug("-------------- status: " + status);
		OffsiteConservation offsite = new OffsiteConservation();
		if(status == null) {
			status = "";
		} else if(status.trim().length()>0) {
			offsite.setStatus(BQ_STATUS.valueOf(status).name());
		}
		
		String orderField = request.getParameter("orderField");
		page.setNumPerPage(Integer.MAX_VALUE);
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("operateTime");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE_R, orgCode));
		if (status.length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		Specification<OffsiteConservation> specification = DynamicSpecifications.bySearchFilter(request, OffsiteConservation.class, csf);
		
		List<OffsiteConservation> reqs = bqglService.findByOffsiteConservationExample(specification, page);
		
		map.put("reqs", reqs);
		return OC_TO_XLS;
	}
	
	/*
	 * =============================================================
	 *  re issue
	 * =============================================================
	 */

	@RequiresPermissions("CsReissue:edit")
	@RequestMapping(value="/reissue/update/{id}", method=RequestMethod.GET)
	public String preUpdateCsReissue(@PathVariable Long id, Map<String, Object> map) {
		CsReissue reissue = bqglService.getCsReissue(id);
		
		map.put("reissue", reissue);
		return UPDATE_RI;
	}

	@RequiresPermissions("CsReissue:provEdit")
	@RequestMapping(value="/reissue/provupdate/{id}", method=RequestMethod.GET)
	public String preProvUpdateCsReissue(@PathVariable Long id, Map<String, Object> map) {
		CsReissue reissue = bqglService.getCsReissue(id);
		
		map.put("reissue", reissue);
		return PROV_UPDATE_RI;
	}

	@Log(message="修改了{0}合同补发的信息。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions("CsReissue:edit")
	@RequestMapping(value="/reissue/update", method=RequestMethod.POST)
	public @ResponseBody String updateCsReissue(CsReissue src) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		CsReissue reissue = bqglService.getCsReissue(src.getId());
		BeanUtils.copyProperties(src, reissue, BeanValidators.getNullPropertyNames(src));
		User user = shiroUser.getUser();
		Organization userOrg = user.getOrganization();
		if(userOrg.getOrgCode().length()>4) {
			reissue.setStatus(FP_STATUS.DealStatus.name());
		} else {
			reissue.setStatus(FP_STATUS.ReceiveStatus.name());
		}
		bqglService.updateCsReissue(reissue);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{reissue.getCsReport().getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("修改合同补发成功！").toString(); 
	}

	@Log(message="修改了{0}合同补发的状态。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions(value={"CsReissue:reset","CsReissue:deal"}, logical=Logical.OR)
	@RequestMapping(value="/reissue/{status}/{id}", method=RequestMethod.POST)
	public @ResponseBody String updateCsReissueStatus(@PathVariable("status") String status, @PathVariable("id") Long id) {
		CsReissue reissue = bqglService.getCsReissue(id);
		FP_STATUS bs = FP_STATUS.DealStatus;
		try {
			bs = FP_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		switch (bs) {
		case CloseStatus:
			break;
			default:
				break;
		}
		reissue.setStatus(status);
		bqglService.updateCsReissue(reissue);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{reissue.getCsReport().getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("修改合同补发成功！").setCallbackType("").toString();
	}

	@Log(message="批量修改了{0}合同补发的{1}状态。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions(value={"CsReissue:reset","CsReissue:deal"}, logical=Logical.OR)
	@RequestMapping(value="/reissue/{status}", method=RequestMethod.POST)
	public @ResponseBody String batchUpdateCsReissueStatus(@PathVariable("status") String status, Long[] ids) {
		CsReissue reissue = null;
		FP_STATUS bs = FP_STATUS.DealStatus;
		try {
			bs = FP_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		String[] policys = new String[ids.length];
		for(int i = 0; i<ids.length; i++) {
			reissue = bqglService.getCsReissue(ids[i]);
			switch (bs) {
			case CloseStatus:
				break;
				default:
					break;
			}
			reissue.setStatus(status);
			bqglService.updateCsReissue(reissue);
			policys[i] = reissue.getCsReport().getPolicy().getPolicyNo();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys), status}));
		return	AjaxObject.newOk("批量" + status + "合同补发成功！").setCallbackType("").toString();
	}

	@RequiresPermissions("CsReissue:view")
	@RequestMapping(value="/reissue/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listCsReissue(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(user.getOrganization().getOrgCode())){
			orgCode = user.getOrganization().getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		//默认返回未处理工单
		String status = request.getParameter("status");
		LOG.debug("-------------- status: " + status + "  orgCode:" + orgCode);
		CsReissue reissue = new CsReissue();
		if(status == null) {
			if(orgCode.length()>4) {
				status = FP_STATUS.DealStatus.name();
				reissue.setStatus(FP_STATUS.valueOf(status).name());
			}
		} else if(status.trim().length()>0) {
			reissue.setStatus(FP_STATUS.valueOf(status).name());
		}
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("csReport.csDate");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("csReport.policy.organization.orgCode", Operator.LIKE_R, orgCode));
		if (status != null && status.length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		} else {
			csf.add(new SearchFilter("status", Operator.NEQ, BQ_STATUS.CloseStatus.name()));
		}
		Specification<CsReissue> specification = DynamicSpecifications.bySearchFilter(request, CsReissue.class, csf);
		
		List<CsReissue> reissues = bqglService.getByCsReissueExample(specification, page);
		
		map.put("reissue", reissue);
		map.put("status", status);
		map.put("crStatusList", FP_STATUS.values());
		map.put("page", page);
		map.put("reissues", reissues);
		return LIST_RI;
	}
	
	@RequiresPermissions(value={"CsReissue:edit","CsReissue:provEdit","CsReissue:cityEdit","CsReissue:areaEdit"}, logical=Logical.OR)
	@RequestMapping(value="/reissue/{mailFlag}", method=RequestMethod.GET)
	public String preMailRecDateUpdate(@PathVariable String mailFlag, String ids, Map<String, Object> map) {
		
		map.put("reissueIds", ids);
		map.put("mailFlag", mailFlag);
		if(mailFlag.contains("Sent")) {
			return RI_MAIL_DATE;
		} else {
			return RI_REC_DATE;
		}
	}
		
		
	@Log(message="更新了{0}保全补发的信息。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions(value={"CsReissue:edit","CsReissue:provEdit","CsReissue:cityEdit","CsReissue:areaEdit"}, logical=Logical.OR)
	@RequestMapping(value="/reissue/sendRecUpdate", method=RequestMethod.POST)
	public @ResponseBody String mailDateUpdate(ServletRequest request, String ids) {
		LOG.debug("ready to update re issue : " + ids);
		String mailFlag = request.getParameter("mailFlag");
		String[] sids = ids.split(",");
		String[] policys = new String[sids.length];
		String sentDate = request.getParameter("provSentDate");
		String expNo = request.getParameter("provExpressNo");
		String proRecDate = request.getParameter("provReceiveDate");
		String receDate = request.getParameter("cityReceiveDate");
		String cityReceiver = request.getParameter("cityReceiver");
		
		for (int i = 0; i<sids.length; i++) {
			CsReissue cssrc = bqglService.getCsReissue(new Long(sids[i]));
			
			switch(mailFlag) {
			case "batchSent":
				cssrc.setProvSentDate(StringUtil.str2Date(sentDate, "yyyy-MM-dd"));
				cssrc.setProvExpressNo(expNo);
				cssrc.setProvReceiveDate(StringUtil.str2Date(proRecDate, "yyyy-MM-dd"));
				cssrc.setStatus(FP_STATUS.DealStatus.name());
				break;
			case "batchReceive":
				cssrc.setCityReceiveDate(StringUtil.str2Date(receDate, "yyyy-MM-dd"));
				cssrc.setCityReceiver(cityReceiver);
				cssrc.setStatus(FP_STATUS.CloseStatus.name());
				break;
				default:
					LOG.warn("-------------保全补发的寄发标记缺失!");
					break;
			}
			
			bqglService.updateCsReissue(cssrc);
			policys[i] = cssrc.getCsReport().getPolicy().getPolicyNo();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return	AjaxObject.newOk("更新合同补发成功！").toString(); 
	}

	@Log(message="下载了保全补发数据！", level=LogLevel.INFO, module=LogModule.BQGL)
	@RequiresPermissions("CsReissue:view")
	@RequestMapping(value="/reissue/toXls", method=RequestMethod.GET)
	public String reissueToXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(user.getOrganization().getOrgCode())){
			orgCode = user.getOrganization().getOrgCode();
		}
		page.setNumPerPage(65564);
		//默认返回未处理工单
		String status = request.getParameter("status");
		LOG.debug("-------------- status: " + status);
		CsReissue reissue = new CsReissue();
		if(status == null) {
			status = "";
		} else if(status.trim().length()>0) {
			reissue.setStatus(BQ_STATUS.valueOf(status).name());
		}
		
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("csReport.csDate");
			page.setOrderDirection("ASC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("csReport.policy.organization.orgCode", Operator.LIKE_R, orgCode));
		if (status.length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		Specification<CsReissue> specification = DynamicSpecifications.bySearchFilter(request, CsReissue.class, csf);
		
		List<CsReissue> reqs = bqglService.getByCsReissueExample(specification, page);
		
		map.put("reqs", reqs);
		return RI_TO_XLS;
	}
	
	/*
	 * ===================================
	 * 保全免填单数据
	 * ===================================
	 */
	
	@RequiresPermissions("ConservationReq:view")
	@RequestMapping(value="/req/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listReq(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		//默认返回未处理工单
		String status = request.getParameter("status");
		LOG.debug("-------------- status: " + status);
		MtdReq req = new MtdReq();
		if(status == null) {
			status = "NewStatus";
		}
		req.setStatus(BQ_STATUS.valueOf(status).name());
		
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("reqDate");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("bankCode.organization.orgCode", Operator.LIKE_R, orgCode));
		csf.add(new SearchFilter("reqType", Operator.EQ, 1));
		if (status.length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		} else {
			csf.add(new SearchFilter("status", Operator.NEQ, BQ_STATUS.CloseStatus.name()));
		}
		
		Specification<MtdReq> specification = DynamicSpecifications.bySearchFilter(request, MtdReq.class, csf);
		
		List<MtdReq> reqs = bqglService.findConservationReqByExample(specification, page);
		
		map.put("req", req);
		map.put("status", status);
		map.put("baStatusList", BQ_STATUS.values());
		map.put("page", page);
		map.put("reqs", reqs);
		return C_REQ_LIST;
	}
	
	@Log(message="修改了{0}免填单保全申请的状态。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions(value={"ConservationReq:edit"}, logical=Logical.OR)
	@RequestMapping(value="/req/{status}", method=RequestMethod.POST)
	public @ResponseBody String batchUpdateConservationReqStatus(@PathVariable("status") String status, Long[] ids) {
		MtdReq req = null;
		BQ_STATUS bs = BQ_STATUS.DealStatus;
		try {
			bs = BQ_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		String[] policys = new String[ids.length];
		for(int i = 0; i<ids.length; i++) {
			req = bqglService.getConservationReq(ids[i]);
			switch (bs) {
			case CloseStatus:
				break;
				default:
					break;
			}
			req.setStatus(status);
			bqglService.updateConservationReq(req);
			policys[i] = req.getPolicy()==null?req.getFormNo():req.getPolicy().getPolicyNo();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys), status}));
		return	AjaxObject.newOk("批量" + status + "免填单数据成功！").setCallbackType("").toString();
	}
	
	@Log(message="下载了保全申请免填单数据！", level=LogLevel.INFO, module=LogModule.BQGL)
	@RequiresPermissions("ConservationReq:view")
	@RequestMapping(value="/req/toXls", method=RequestMethod.GET)
	public String cReqtoXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		//默认返回未处理工单
		String status = request.getParameter("status");
		if(status == null) {
			status = "";
		}
		String orderField = request.getParameter("orderField");
		page.setNumPerPage(Integer.MAX_VALUE);
		
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("reqDate");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("bankCode.organization.orgCode", Operator.LIKE_R, orgCode));
		if (status.length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		} else {
			csf.add(new SearchFilter("status", Operator.NEQ, BQ_STATUS.CloseStatus.name()));
		}
		
		Specification<MtdReq> specification = DynamicSpecifications.bySearchFilter(request, MtdReq.class, csf);
		
		List<MtdReq> reqs = bqglService.findConservationReqByExample(specification, page);
		
		map.put("reqs", reqs);
		return C_TO_XLS;
	}

	@RequiresPermissions("CsReport:view")
	@RequestMapping(value="/report/list", method={RequestMethod.GET, RequestMethod.POST})
	public String reportList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		//默认返回未处理工单
		String sf = request.getParameter("search_LIKE_staffFlag");
		CsReport cr = new CsReport();
		cr.setSearch_LIKE_staffFlag(sf);
		request.setAttribute("csReport", cr);
		
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("csDate");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
		
		String prdName = request.getParameter("prd.prdFullName");
		if(prdName != null && prdName.trim().length()>0) {
			csf.add(new SearchFilter("policy.prd.prdFullName", Operator.EQ, prdName));
			request.setAttribute("prd_name", prdName);
			try {
				request.setAttribute("prodName", URLEncoder.encode(prdName, "UTF8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		Specification<CsReport> specification = DynamicSpecifications.bySearchFilter(request, CsReport.class, csf);
		
		List<CsReport> issues = bqglService.findCsReportByExample(specification, page);
		
		map.put("page", page);
		map.put("issues", issues);
		return CS_LIST;
	}
	
	@Log(message="下载了保全月报数据！", level=LogLevel.INFO, module=LogModule.BQGL)
	@RequiresPermissions("CsReport:view")
	@RequestMapping(value="/report/list/toXls", method={RequestMethod.GET, RequestMethod.POST})
	public String reportListToXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		//默认返回未处理工单
		
		String orderField = request.getParameter("orderField");
		page.setNumPerPage(Integer.MAX_VALUE);
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("csDate");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
		
		String prdName = request.getParameter("prd.prdFullName");
		if(prdName != null && prdName.trim().length()>0) {
			csf.add(new SearchFilter("policy.prd.prdFullName", Operator.EQ, prdName));
		}
		
		Specification<CsReport> specification = DynamicSpecifications.bySearchFilter(request, CsReport.class, csf);
		
		List<CsReport> issues = bqglService.findCsReportByExample(specification, page);
		
		map.put("issues", issues);
		return CS_LIST_TO_XLS;
	}
	
	/*
	 * ======================================
	 * cs loan
	 * ======================================
	 */
	@RequiresPermissions("CsLoan:view")
	@RequestMapping(value="/loan/list", method={RequestMethod.GET, RequestMethod.POST})
	public String loanList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		String orgName = request.getParameter("name");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		
		String status = request.getParameter("status");
		String flag = request.getParameter("flag");
		if(status == null) {
			status = "借款";
		}
		CsLoan loan = new CsLoan();
		loan.setStatus(status);
		loan.setFlag(flag==null||flag.trim().length()<=0?null:new Integer(flag));
		request.setAttribute("status", status);
		request.setAttribute("flag", flag);
		request.setAttribute("loan", loan);
		
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("loanDate");
			page.setOrderDirection("ASC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
		
		if(status != null && status.trim().length()>0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		
		if(flag != null && flag.trim().length()>0) {
			csf.add(new SearchFilter("flag", Operator.EQ, flag));
		}
		Specification<CsLoan> specification = DynamicSpecifications.bySearchFilter(request, CsLoan.class, csf);
		
		List<CsLoan> issues = bqglService.findCsLoanByExample(specification, page);
		map.put("page", page);
		map.put("issues", issues);
		return C_LOAN_LIST;
	}
	
	@Log(message="对{0}质押借款进行了关闭。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions("CsLoan:delete")
	@RequestMapping(value="/loan/CloseStatus", method=RequestMethod.POST)
	public @ResponseBody String closeManyCsLoan(Long[] ids) {
		String[] policys = new String[ids.length];
		try {
			CsLoan loan = null;
			for (int i = 0; i < ids.length; i++) {
				loan = bqglService.getCsLoan(ids[i]);
				loan.setRealDate(new Date());
				loan.setStatus("关闭");
				bqglService.updateCsLoan(loan);
				
				policys[i] = loan.getPolicy().getPolicyNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("关闭失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("成功关闭！").setCallbackType("").toString();
	}
	
	@Log(message="下载了保全质押借款数据！", level=LogLevel.INFO, module=LogModule.BQGL)
	@RequiresPermissions("CsLoan:view")
	@RequestMapping(value="/loan/list/toXls", method={RequestMethod.GET, RequestMethod.POST})
	public String loanListToXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		
		String status = request.getParameter("status");
		if(status == null) {
			status = "借款";
		}
		CsLoan loan = new CsLoan();
		loan.setStatus(status);
		request.setAttribute("status", status);
		request.setAttribute("loan", loan);
		
		String orderField = request.getParameter("orderField");
		page.setNumPerPage(Integer.MAX_VALUE);
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("loanDate");
			page.setOrderDirection("ASC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
		if(status != null && status.trim().length()>0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		Specification<CsLoan> specification = DynamicSpecifications.bySearchFilter(request, CsLoan.class, csf);
		
		List<CsLoan> issues = bqglService.findCsLoanByExample(specification, page);
		
		map.put("issues", issues);
		
		return C_LOAN_TO_XLS;
	}

	@RequiresPermissions(value={"CsLoan:edit"}, logical=Logical.OR)
	@RequestMapping(value="/loan/remark/{id}", method=RequestMethod.GET)
	public String preCsLoanRemark(@PathVariable Long id, Map<String, Object> map) {
		CsLoan req = bqglService.getCsLoan(id);
		map.put("loan", req);
		return C_LOAN_REMARK;
	}
	
	@Log(message="对{0}设置了备注。", level=LogLevel.WARN, module=LogModule.HFGL)
	@RequiresPermissions(value={"CsLoan:edit"}, logical=Logical.OR)
	@RequestMapping(value="/loan/remark", method=RequestMethod.POST)
	public @ResponseBody String setCsLoanRemark(HttpServletRequest request, @RequestParam(value = "file", required = true) MultipartFile file) {
		String id = request.getParameter("id");
		if(id == null) {
			return null;
		}
		LOG.debug("-------------------------------------upload:" + file.getOriginalFilename());
        int iNY = UploadDataUtils.getNianYue();
        String strPath = UploadDataUtils.getNoticeFileStorePath(request, iNY, "BQGL");
        String updatePath = UploadDataUtils.getNoticeRelateFileStorePath(request, iNY, "BQGL");
		String strNewFileName = null;
		boolean hasFile = false;
        if(file != null && file.getOriginalFilename() != null && file.getOriginalFilename().trim().length()>0) {
	        try
	        {
	        	hasFile = true;
	        	String name = file.getOriginalFilename();
	            Long lFileSize = file.getSize();
	
	            com.gdpost.utils.UploadDataHelper.SessionChunk sessionChunk = new com.gdpost.utils.UploadDataHelper.SessionChunk();
	            com.gdpost.utils.UploadDataHelper.FileChunk fileChunk = sessionChunk.getSessionChunk(request);
	            if(fileChunk == null) {
	                fileChunk = new com.gdpost.utils.UploadDataHelper.FileChunk();
	            }
	            String chunkSize = request.getParameter("chunkSize");
	            int iChunkSize = Integer.parseInt(chunkSize==null?"0":chunkSize); //分块大小
	            String strOriginalFileName = name;
	            String strSessionID = request.getSession().getId();
	            
	            int iCurrentChunk = 1;
	            int iChunks = 1;
	            try {
	            	iCurrentChunk = Integer.parseInt(request.getParameter("chunk")); //当前分块
	            	iChunks = Integer.parseInt(request.getParameter("chunks"));//总的分块数量
	            } catch(Exception e) {
	            }
	            
	            strNewFileName = strOriginalFileName;
	            if (iChunks > 1) {
	            	strNewFileName = iCurrentChunk + "_" + strSessionID + "_" + strOriginalFileName;   //按文件块重命名块文件
	            }
	            
	            String strFileGroup = request.getParameter("fileGroup"); // 当前文件组
	            fileChunk.setChunks(iChunks);
	            fileChunk.setChunkSize(iChunkSize);
	            fileChunk.setCurrentChunk(iCurrentChunk);
	            fileChunk.setFileName(strOriginalFileName);
	            fileChunk.setFileGroup(strFileGroup);
	            fileChunk.setFileSize(lFileSize);
	            fileChunk.setListFileName(strOriginalFileName);
	            
	            sessionChunk.setSessionChunk(request, fileChunk);
	            
	            File uploadedFile = null;
	            if (iChunks > 1) {
	            	uploadedFile = new File(strPath, strNewFileName);
	            	if(uploadedFile.exists()) {
	            		uploadedFile.delete();
	            	}
	            	
	            	uploadedFile = new File(strPath, strNewFileName);
	            	
	            	try {
	    				org.apache.commons.io.FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
	    			} catch (IOException e) {
	    				LOG.error(e.getMessage());
	    				return (strError);
	    			} catch (Exception e) {
	    				LOG.error(e.getMessage());
	    				return (strError);				
	    			}
	            	
	                if(iCurrentChunk + 1 == iChunks) {
	                    // 上传完成转移到完成文件目录
	                    int BUFSIZE = 1024 * 1024 * 8;
	                    FileChannel outChannel = null;
	                    
	                	try {
	                		FileOutputStream fos = new FileOutputStream(strPath + File.separator + strOriginalFileName);
	                		outChannel = fos.getChannel();
	                		String strChunkFile = "";
	                		ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
	                	    for (int i = 0; i < iChunks; i++) {
	                	    	strChunkFile = strPath + File.separator + i + "_" + strSessionID + "_" + strOriginalFileName;
	                	    	FileInputStream fis = new FileInputStream(strChunkFile);
	                	    	FileChannel fc = fis.getChannel();
	                	    	while(fc.read(bb) != -1){
	                	    		bb.flip();
	                	    	    outChannel.write(bb);
	                	    		bb.clear();
	                	    	}
	                	    	
	                	    	fc.close();
	                	    	fis.close();
	                	    
	                	    	java.nio.file.Path path = java.nio.file.Paths.get(strChunkFile);
	                	    	Files.delete(path);
	            	    	}
	
	                	    fos.close();
	            	    	
	                		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{strNewFileName}));
	                	    ShiroUser shiroUser = SecurityUtils.getShiroUser();
	                	    LOG.info(shiroUser.getLoginName() + "上传了" + strOriginalFileName);
	    				
	            		} catch (FileNotFoundException e) {
	            			LOG.error(e.getMessage());
	            			return AjaxObject.newError("新增失败！").toString();
	    				} catch (IOException e) {
	    					LOG.error(e.getMessage());
	    					return AjaxObject.newError("新增失败！").toString();
	    				} catch (Exception e) {
	    					LOG.error(e.getMessage());
	    					return AjaxObject.newError("新增失败！").toString();
	    				}
	                	finally {
	            			try {
	            				if(outChannel != null) {
	            					outChannel.close();
	            				}
	    					} catch (IOException e) {
	    					}
	                	}
	                }
	            } else { 
	                // 单个文件直接保存
	            	LOG.debug("-------------single file name:" + strOriginalFileName);
	            	uploadedFile = new File(strPath, strNewFileName);
	            	try {
	    				org.apache.commons.io.FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
	            	    ShiroUser shiroUser = SecurityUtils.getShiroUser();
	            	    LOG.info(shiroUser.getLoginName() + "上传了" + strNewFileName);
	    				LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{strOriginalFileName}));
	    			} catch (IOException e) {
	    				LOG.error(e.getMessage());
	    				return AjaxObject.newError("发布失败！").toString();
	    			}
	            }
	        }
	        catch (Exception e)
	        {
	            // 上传失败，IO异常！
	            e.printStackTrace();
	            LOG.error("--- UPLOAD FAIL ---");
	            LOG.error(e.getMessage());
	            return AjaxObject.newError("增加失败！").toString();
	        }
        }

        String attrLink = updatePath + "/" + strNewFileName;
        
		CsLoan loan = bqglService.getCsLoan(new Long(id));
		if(hasFile) {
			loan.setAttachment(attrLink);
		}
		String remark = request.getParameter("remark");
		loan.setRemark(remark);
		bqglService.updateCsLoan(loan);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{loan.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("设置成功！").toString();
	}
	
	/*
	 * ======================================
	 * cs expire
	 * ======================================
	 */
	@RequiresPermissions("CsExpire:view")
	@RequestMapping(value="/expire/list", method={RequestMethod.GET, RequestMethod.POST})
	public String expireList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		String orgName = request.getParameter("name");
		String search_LIKE_finalLevel = request.getParameter("search_LIKE_finalLevel");
		request.setAttribute("orgCode", orgCode);
		request.setAttribute("name", orgName);
		
		String status = request.getParameter("status");
		if(status == null) {
			status = "NewStatus";
		}
		CsExpire expire = new CsExpire();
		expire.setStatus(status);
		expire.setSearch_LIKE_finalLevel(search_LIKE_finalLevel);
		request.setAttribute("status", status);
		request.setAttribute("expire", expire);
		request.setAttribute("search_LIKE_finalLevel", search_LIKE_finalLevel);
		
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("policyEndDate");
			page.setOrderDirection("ASC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
		
		String prdName = request.getParameter("prd.prdFullName");
		if(prdName != null && prdName.trim().length()>0) {
			csf.add(new SearchFilter("policy.prd.prdFullName", Operator.EQ, prdName));
			request.setAttribute("prd_name", prdName);
			try {
				request.setAttribute("prodName", URLEncoder.encode(prdName, "UTF8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		if(status != null && status.trim().length()>0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		
		Specification<CsExpire> specification = DynamicSpecifications.bySearchFilter(request, CsExpire.class, csf);
		
		List<CsExpire> expires = bqglService.findCsExpireByExample(specification, page);
		map.put("page", page);
		map.put("expires", expires);
		map.put("csStatusList", CSEXPIRE_STATUS.values());
		return C_EXPIRE_LIST;
	}
	
	@RequiresPermissions("CsExpire:edit")
	@RequestMapping(value="/expire/update/{id}", method=RequestMethod.GET)
	public String preUpdateCsExpire(@PathVariable Long id, Map<String, Object> map) {
		CsExpire expire = bqglService.getCsExpire(id);
		map.put("expire", expire);
		map.put("expireDtls", expire.getCsExpireDtls());
		map.put("today", StringUtil.date2Str(new Date(), "yyyy-MM-dd"));
		return C_EXPIRE_UPDATE;
	}
	
	@Log(message="对{0}满期给付保单进行了更新。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions("CsExpire:update")
	@RequestMapping(value="/expire/update", method=RequestMethod.POST)
	public @ResponseBody String updateCsExpire(CsExpireDtl csExpireDtl, ServletRequest request) {
		String csId = request.getParameter("csExpireId");
		CsExpire cs = bqglService.getCsExpire(new Long(csId));
//		cs.setStatus(BQ_STATUS.DealStatus.getDesc());
//		bqglService.updateCsExpire(cs);
		csExpireDtl.setCsExpire(cs);
		bqglService.updateCsExpireDtl(csExpireDtl);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{cs.getPolicy().getPolicyNo()}));
		return AjaxObject.newOk("更新成功！").setCallbackType("").toString();
	}
	
	@Log(message="批量关闭满期保单：{0}。", level=LogLevel.WARN, module=LogModule.BQGL)
	@RequiresPermissions(value={"CsExpire:update"}, logical=Logical.OR)
	@RequestMapping(value="/expire/{status}", method=RequestMethod.POST)
	public @ResponseBody String batchUpdateCsExpireStatus(@PathVariable("status") String status, Long[] ids) {
		CsExpire csExpire = null;
		CSEXPIRE_STATUS bs = CSEXPIRE_STATUS.WARNStatus;
		try {
			bs = CSEXPIRE_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		String[] policys = new String[ids.length];
		for(int i = 0; i<ids.length; i++) {
			csExpire = bqglService.getCsExpire(ids[i]);
			switch (bs) {
			case WARNStatus:
				break;
				default:
					break;
			}
			csExpire.setStatus(status);
			bqglService.updateCsExpire(csExpire);
			policys[i] = csExpire.getPolicy().getPolicyNo();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys), status}));
		return	AjaxObject.newOk("批量" + bs.getDesc() + "满期给付保单！").setCallbackType("").toString();
	}
	
	@Log(message="下载了保全满期数据！", level=LogLevel.INFO, module=LogModule.BQGL)
	@RequiresPermissions("CsExpire:view")
	@RequestMapping(value="/expire/toXls", method={RequestMethod.GET, RequestMethod.POST})
	public String csExpireListToXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		String orgCode = request.getParameter("orgCode");
		if(orgCode == null || orgCode.trim().length()<=0) {
			orgCode = userOrg.getOrgCode();
		} else if(!orgCode.contains(userOrg.getOrgCode())){
			orgCode = userOrg.getOrgCode();
		}
		
		String status = request.getParameter("status");
		if(status == null) {
			status = "NewStatus";
		}
		CsExpire expire = new CsExpire();
		expire.setStatus(status);
		request.setAttribute("status", status);
		request.setAttribute("expire", expire);
		
		String orderField = request.getParameter("orderField");
		page.setNumPerPage(Integer.MAX_VALUE);
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("policyEndDate");
			page.setOrderDirection("ASC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
		if(status != null && status.trim().length()>0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		
		String prdName = request.getParameter("prd.prdFullName");
		if(prdName != null && prdName.trim().length()>0) {
			csf.add(new SearchFilter("policy.prd.prdFullName", Operator.EQ, prdName));
		}
		
		Specification<CsExpire> specification = DynamicSpecifications.bySearchFilter(request, CsExpire.class, csf);
		
		List<CsExpire> expires = bqglService.findCsExpireByExample(specification, page);
		
		map.put("expires", expires);
		
		return C_EXPIRE_TO_XLS;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
