/**
 * 
 * ==========================================================
 * 保全管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.BeanValidators;
import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.entity.main.ConservationDtl;
import com.gdpost.web.entity.main.CsReissue;
import com.gdpost.web.entity.main.OffsiteConservation;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.insurance.BqglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.StatusDefine.BQ_STATUS;
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
	
	@RequiresPermissions("Cservice:save")
	@RequestMapping(value="/issue/create", method=RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}
	
	@RequestMapping(value="/help", method=RequestMethod.GET)
	public String toHelp() {
		return TO_HELP;
	}
	
	@Log(message="添加了{0}保全复核问题。")
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
			if(issue.getConservationCode().equalsIgnoreCase("htbf")) {
				CsReissue cr = new CsReissue();
				cr.setConservationDtl(issue);
				cr.setStatus(FP_STATUS.NewStatus.name());
				LOG.debug("---------cs reissue: " + cr.toString());
				bqglService.updateCsReissue(cr);
			}
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
	
	@Log(message="修改了{0}保全复核问题的信息。")
	@RequiresPermissions("Cservice:edit")
	@RequestMapping(value="/issue/update", method=RequestMethod.POST)
	public @ResponseBody String update(ConservationDtl issue) {
		bqglService.saveOrUpdate(issue);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("修改保全复核问题成功！").toString(); 
	}
	
	@Log(message="修改了{0}保全复核问题的状态。")
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
	
	@Log(message="批量修改了{0}保全复核问题的{1}状态。")
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
	
	@Log(message="删除了{0}保全复核问题。")
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
	
	@Log(message="删除了{0}保全复核问题。")
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
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
		if (status.length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
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
			/*
			issue.setStatus(BQ_STATUS.NewStatus.name());
			s = BQ_STATUS.NewStatus.name();
			if (userOrg.getOrgCode().length()<=4) {
				issue.setStatus(BQ_STATUS.DealStatus.name());
				s = BQ_STATUS.DealStatus.name();
			}
			*/
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
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE, orgCode));
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
	
	@Log(message="添加了{0}异地保全。")
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
	
	@Log(message="修改了{0}异地保全的信息。")
	@RequiresPermissions("OffsiteConservation:edit")
	@RequestMapping(value="/offsite/update", method=RequestMethod.POST)
	public @ResponseBody String updateOffsiteConservation(OffsiteConservation src) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		OffsiteConservation offsite = bqglService.getOffsiteConservation(src.getId());
		/*
		offsite.setOrganization(src.getOrganization());
		offsite.setTransactor(src.getTransactor());
		offsite.setDealDate(src.getDealDate());
		offsite.setExpressBillNo(src.getExpressBillNo());
		offsite.setPolicyNo(src.getPolicyNo());
		offsite.setOrginProv(src.getOrginProv());
		offsite.setClient(src.getClient());
		offsite.setConservationType(src.getConservationType());
		*/
		BeanUtils.copyProperties(src, offsite, BeanValidators.getNullPropertyNames(src));
		User user = shiroUser.getUser();
		Organization userOrg = user.getOrganization();
		if(userOrg.getOrgCode().length()>4) {
			offsite.setStatus(BQ_STATUS.NewStatus.name());
		} else {
			offsite.setStatus(BQ_STATUS.DealStatus.name());
		}
		bqglService.saveOrUpdateOffsiteConservation(offsite);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{offsite.getPolicyNo()}));
		return	AjaxObject.newOk("修改异地保全成功！").toString(); 
	}
	
	@Log(message="修改了{0}异地保全的状态。")
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
	
	@Log(message="批量修改了{0}异地保全的{1}状态。")
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
	
	@Log(message="删除了{0}异地保全。")
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
	
	@Log(message="删除了{0}异地保全。")
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
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE, orgCode));
		if (status.length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
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
			/*
			offsite.setStatus(BQ_STATUS.NewStatus.name());
			s = BQ_STATUS.NewStatus.name();
			if (userOrg.getOrgCode().length()<=4) {
				offsite.setStatus(BQ_STATUS.DealStatus.name());
				s = BQ_STATUS.DealStatus.name();
			}
			*/
		} else if(status.trim().length()>0) {
			offsite.setStatus(BQ_STATUS.valueOf(status).name());
		}
		
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("operateTime");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE, orgCode));
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

	@Log(message="修改了{0}合同补发的信息。")
	@RequiresPermissions("CsReissue:edit")
	@RequestMapping(value="/reissue/update", method=RequestMethod.POST)
	public @ResponseBody String updateCsReissue(CsReissue src) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		CsReissue reissue = bqglService.getCsReissue(src.getId());
		/*
		reissue.setOrganization(src.getOrganization());
		reissue.setTransactor(src.getTransactor());
		reissue.setDealDate(src.getDealDate());
		reissue.setExpressBillNo(src.getExpressBillNo());
		reissue.setPolicyNo(src.getPolicyNo());
		reissue.setOrginProv(src.getOrginProv());
		reissue.setClient(src.getClient());
		reissue.setConservationType(src.getConservationType());
		*/
		BeanUtils.copyProperties(src, reissue, BeanValidators.getNullPropertyNames(src));
		User user = shiroUser.getUser();
		Organization userOrg = user.getOrganization();
		if(userOrg.getOrgCode().length()>4) {
			reissue.setStatus(FP_STATUS.DealStatus.name());
		} else {
			reissue.setStatus(FP_STATUS.ReceiveStatus.name());
		}
		bqglService.updateCsReissue(reissue);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{reissue.getConservationDtl().getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("修改合同补发成功！").toString(); 
	}

	@Log(message="修改了{0}合同补发的状态。")
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
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{reissue.getConservationDtl().getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("修改合同补发成功！").setCallbackType("").toString();
	}

	@Log(message="批量修改了{0}合同补发的{1}状态。")
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
			policys[i] = reissue.getConservationDtl().getPolicy().getPolicyNo();
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
			status = "";
		} else if(status.trim().length()>0) {
			reissue.setStatus(BQ_STATUS.valueOf(status).name());
		}
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("conservationDtl.csDate");
			page.setOrderDirection("DESC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("conservationDtl.policy.organization.orgCode", Operator.LIKE, orgCode));
		if (status.length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
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
	@RequestMapping(value="/reissue/{flag}/{id}", method=RequestMethod.GET)
	public String preMailRecDateUpdate(@PathVariable String flag, @PathVariable Long id, Map<String, Object> map) {
		CsReissue reissue = bqglService.getCsReissue(id);
		
		map.put("reissue", reissue);
		map.put("flag", flag);
		if(flag.contains("Send")) {
			return RI_MAIL_DATE;
		} else {
			return RI_REC_DATE;
		}
	}
		
		
	@Log(message="更新了{0}保全补发的信息。")
	@RequiresPermissions(value={"CsReissue:edit","CsReissue:provEdit","CsReissue:cityEdit","CsReissue:areaEdit"}, logical=Logical.OR)
	@RequestMapping(value="/reissue/sendRecUpdate", method=RequestMethod.POST)
	public @ResponseBody String mailDateUpdate(ServletRequest request, CsReissue reissue) {
		LOG.debug("ready to update re issue : " + reissue.toString());
		CsReissue cssrc = bqglService.getCsReissue(reissue.getId());
		LOG.debug("get the re issue src id : " + cssrc.getId());
		String flag = request.getParameter("flag");
		switch(flag) {
		case "provSend":
			LOG.debug("----------" + cssrc.getProvSentDate());
			cssrc.setProvSentDate(reissue.getProvSentDate());
			cssrc.setProvExpressNo(reissue.getProvExpressNo());
			cssrc.setProvReceiveDate(reissue.getProvReceiveDate());
			cssrc.setStatus(FP_STATUS.DealStatus.name());
			break;
		case "cityRec":
			cssrc.setCityReceiveDate(reissue.getCityReceiveDate());
			cssrc.setCityReceiver(reissue.getCityReceiver());
			cssrc.setStatus(FP_STATUS.CloseStatus.name());
			break;
			default:
				LOG.warn("-------------保全补发的寄发标记缺失!");
				break;
		}
		
		bqglService.updateCsReissue(cssrc);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{cssrc.getConservationDtl().getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("更新合同补发成功！").toString(); 
	}

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
			/*
			reissue.setStatus(BQ_STATUS.NewStatus.name());
			s = BQ_STATUS.NewStatus.name();
			if (userOrg.getOrgCode().length()<=4) {
				reissue.setStatus(BQ_STATUS.DealStatus.name());
				s = BQ_STATUS.DealStatus.name();
			}
			*/
		} else if(status.trim().length()>0) {
			reissue.setStatus(BQ_STATUS.valueOf(status).name());
		}
		
		String orderField = request.getParameter("orderField");
		if(orderField == null || orderField.trim().length()<=0) {
			page.setOrderField("conservationDtl.csDate");
			page.setOrderDirection("ASC");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("conservationDtl.policy.organization.orgCode", Operator.LIKE, orgCode));
		if (status.length() > 0) {
			csf.add(new SearchFilter("status", Operator.EQ, status));
		}
		Specification<CsReissue> specification = DynamicSpecifications.bySearchFilter(request, CsReissue.class, csf);
		
		List<CsReissue> reqs = bqglService.getByCsReissueExample(specification, page);
		
		map.put("reqs", reqs);
		return RI_TO_XLS;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
