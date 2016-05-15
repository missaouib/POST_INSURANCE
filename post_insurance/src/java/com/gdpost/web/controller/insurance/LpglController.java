/**
 * 
 * ==========================================================
 * 理赔管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.StringUtil;
import com.gdpost.web.entity.component.SettleTask;
import com.gdpost.web.entity.component.SettleTaskLog;
import com.gdpost.web.entity.component.Settlement;
import com.gdpost.web.entity.component.SettlementDtl;
import com.gdpost.web.entity.component.SettlementLog;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.insurance.LpglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/lpgl")
public class LpglController {
	//private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private LpglService lpglService;

	private static final String CREATE = "insurance/lpgl/follow/create";
	private static final String UPDATE = "insurance/lpgl/follow/update";
	private static final String LIST = "insurance/lpgl/follow/list";
	private static final String TO_XLS = "insurance/lpgl/follow/toXls";
	private static final String CREATE_DTL = "insurance/lpgl/follow/createDtl";
	private static final String LOG_DTL = "insurance/lpgl/follow/logs";
	
	private static final String CREATE_TASK = "insurance/lpgl/task/create";
	private static final String UPDATE_TASK = "insurance/lpgl/task/update";
	private static final String LIST_TASK = "insurance/lpgl/task/list";
	private static final String TASK_TO_XLS = "insurance/lpgl/task/toXls";
	private static final String TASK_LOG_DTL = "insurance/lpgl/task/logs";
	
	@RequiresPermissions("Settlement:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}
	
	@Log(message="添加了{0}的理赔案件。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("Settlement:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid Settlement settle, HttpServletRequest request) {
		User user = SecurityUtils.getShiroUser().getUser();
		try {
			settle.setOperateId(user.getId());
			settle.setCreateTime(new Date());
			lpglService.saveOrUpdateSettle(settle);
			SettlementLog settleLog = new SettlementLog();
			settleLog.setSettlement(settle);
			settleLog.setDealDate(new Date());
			settleLog.setUser(user);
			settleLog.setInfo("添加了理赔案件信息；");
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleLog(settleLog);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加理赔案件失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{settle.getInsured()}));
		return AjaxObject.newOk("添加理赔案件成功！").toString();
	}
	
	@RequiresPermissions("Settlement:save")
	@RequestMapping(value="/detail/{id}", method=RequestMethod.GET)
	public String preCreateDtl(@PathVariable Long id, HttpServletRequest request) {
		Settlement settle = lpglService.getSettle(id);
		request.setAttribute("settle", settle);
		SettlementDtl settleDtl = lpglService.getDtlBySettlementId(id);
		if(settleDtl != null && settleDtl.getId() != null) {
			request.setAttribute("flag", "update");
			request.setAttribute("settleDtl", settleDtl);
		} else {
			request.setAttribute("flag", "create");
		}
		return CREATE_DTL;
	}

	@Log(message="登记了{0}的理赔案件详情。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("Settlement:save")
	@RequestMapping(value="/detail", method=RequestMethod.POST)
	public @ResponseBody String createDtl(SettlementDtl settleDtl, HttpServletRequest request) {
		try {
			lpglService.saveOrUpdateSettleDtl(settleDtl);
			
			Settlement settle = lpglService.getSettle(settleDtl.getSettlement().getId());
			SettlementLog settleLog = new SettlementLog();
			User user = SecurityUtils.getShiroUser().getUser();
			settleLog.setSettlement(settle);
			settleLog.setDealDate(new Date());
			settleLog.setUser(user);
			settleLog.setInfo("添加或者更新了理赔案件相信信息；");
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleLog(settleLog);
			
		} catch (ExistedException e) {
			return AjaxObject.newError("理赔案件详情登记失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{settleDtl.getClaimsNo()}));
		return AjaxObject.newOk("理赔案件详情登记成功！").toString();
	}

	@RequiresPermissions("Settlement:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Settlement settle = lpglService.getSettle(id);
		
		map.put("settle", settle);
		return UPDATE;
	}
	
	@Log(message="修改了出险人{0}的案件信息。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("Settlement:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid Settlement settle, HttpServletRequest request) {
		Settlement src = lpglService.getSettle(settle.getId());
		StringBuffer loginfo = new StringBuffer("");
		if(settle.getCaseDate()!=null && !DateUtils.isSameDay(src.getCaseDate(), settle.getCaseDate())) {
			loginfo.append("改出险日期：" + StringUtil.date2Str(src.getCaseDate(), "yy-M-d") + "->" + StringUtil.date2Str(settle.getCaseDate(), "yy-M-d") + "；");
		}
		if(settle.getCaseStatus()!=null && !src.getCaseStatus().equals(settle.getCaseStatus())) {
			loginfo.append("改状态：" + src.getCaseStatus() + "->" + settle.getCaseStatus() + "；");
		}
		if(settle.getCaseType()!=null && !src.getCaseType().equals(settle.getCaseType())) {
			loginfo.append("改类型：" + src.getCaseType() + "->" + settle.getCaseType() + "；");
		}
		if(settle.getCloseDate()!=null && !DateUtils.isSameDay(src.getCloseDate(), settle.getCloseDate())) {
			loginfo.append("改关闭日期：" + StringUtil.date2Str(src.getCloseDate(), "yy-M-d") + "->" + StringUtil.date2Str(settle.getCloseDate(), "yy-M-d") + "；");
		}
		if(settle.getInsured()!=null && !src.getInsured().equals(settle.getInsured())) {
			loginfo.append("改出险人：" + src.getInsured() + "->" + settle.getInsured() + "；");
		}
		if(settle.getOrganization()!=null && !src.getOrganization().getName().equals(settle.getOrganization().getName())) {
			loginfo.append("改机构；");
		}
		if(settle.getPayFee()!=null && (src.getPayFee()==null?0:src.getPayFee().doubleValue()) != settle.getPayFee().doubleValue()) {
			loginfo.append("改赔付金额：" + src.getPayFee() + "->" + settle.getPayFee() + "；");
		}
		if(settle.getRecordDate()!=null && !DateUtils.isSameDay(src.getRecordDate(), settle.getRecordDate())) {
			loginfo.append("改立案日期：" + StringUtil.date2Str(src.getRecordDate(), "yy-M-d") + "->" + StringUtil.date2Str(settle.getRecordDate(), "yy-M-d") + "；");
		}
		if(settle.getReporteDate()!=null && !DateUtils.isSameDay(src.getReporteDate(), settle.getReporteDate())) {
			loginfo.append("改报案日期：" + StringUtil.date2Str(src.getReporteDate(), "yy-M-d") + "->" + StringUtil.date2Str(settle.getReporteDate(), "yy-M-d") + "；");
		}
		if(settle.getReporter()!=null && !(src.getReporter()==null?"":src.getReporter()).equals(settle.getReporter())) {
			loginfo.append("改报案人：" + src.getReporter() + "->" + settle.getReporter() + "；");
		}
		if(settle.getReporterPhone()!=null && !(src.getReporterPhone()==null?"":src.getReporterPhone()).equals(settle.getReporterPhone())) {
			loginfo.append("改报案人电话：" + src.getReporterPhone() + "->" + settle.getReporterPhone() + "；");
		}
		
		lpglService.saveOrUpdateSettle(settle);
		
		User user = SecurityUtils.getShiroUser().getUser();
		SettlementLog settleLog = new SettlementLog();
		settleLog.setSettlement(settle);
		settleLog.setUser(user);
		settleLog.setDealDate(new Date());
		settleLog.setInfo(loginfo.toString());
		settleLog.setIp(request.getRemoteAddr());
		settleLog.setIsKeyInfo(true);
		lpglService.saveOrUpdateSettleLog(settleLog);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{settle.getInsured()}));
		return	AjaxObject.newOk("修改案件成功！").toString(); 
	}
	
	@Log(message="删除了{0}的案件信息。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("Settlement:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id, HttpServletRequest request) {
		Settlement settle = null;
		try {
			settle = lpglService.getSettle(id);
			
			User user = SecurityUtils.getShiroUser().getUser();
			SettlementLog settleLog = new SettlementLog();
			settleLog.setSettlement(settle);
			settleLog.setUser(user);
			settleLog.setDealDate(new Date());
			settleLog.setInfo("删除报案信息：" + settle.getInsured());
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleLog(settleLog);
			
			
			lpglService.deleteSettle(settle.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除案件失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{settle.getInsured()}));
		return AjaxObject.newOk("删除案件成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}案件。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("Settlement:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids, HttpServletRequest request) {
		String[] policys = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				Settlement settle = lpglService.getSettle(ids[i]);
				
				User user = SecurityUtils.getShiroUser().getUser();
				SettlementLog settleLog = new SettlementLog();
				settleLog.setSettlement(settle);
				settleLog.setUser(user);
				settleLog.setDealDate(new Date());
				settleLog.setInfo("删除报案信息：" + settle.getInsured());
				settleLog.setIp(request.getRemoteAddr());
				settleLog.setIsKeyInfo(true);
				lpglService.saveOrUpdateSettleLog(settleLog);
				
				lpglService.deleteSettle(settle.getId());
				
				policys[i] = settle.getInsured();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除案件失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("删除案件成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("Settlement:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String caseStatus = request.getParameter("caseStatus");
		Settlement settle = new Settlement();
		settle.setCaseStatus(caseStatus);
		request.setAttribute("settle", settle);
		String orgCode = request.getParameter("organization.orgCode");
		if(orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = user.getOrganization().getOrgCode();
			if(orgCode.contains("11185")) {
				orgCode = "8644";
			}
		} else {
			if(!orgCode.contains(user.getOrganization().getOrgCode())){
				orgCode = user.getOrganization().getOrgCode();
			}
			String orgName = request.getParameter("organization.name");
			request.setAttribute("org_code", orgCode);
			request.setAttribute("org_name", orgName);
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE, orgCode));
		if(caseStatus != null && caseStatus.trim().length() >0) {
			csf.add(new SearchFilter("caseStatus", Operator.LIKE, caseStatus));
		}
		
		Specification<Settlement> specification = DynamicSpecifications.bySearchFilter(request, Settlement.class, csf);
		List<Settlement> users = lpglService.findBySettleExample(specification, page);

		map.put("page", page);
		map.put("users", users);
		return LIST;
	}
	
	@RequiresPermissions("Settlement:view")
	@RequestMapping(value="/toXls", method=RequestMethod.GET)
	public String toXls(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String caseStatus = request.getParameter("caseStatus");
		page.setNumPerPage(65564);
		page.setOrderField("organization.orgCode");
		page.setOrderDirection("ASC");
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE, user.getOrganization().getOrgCode()));
		if(caseStatus != null && caseStatus.trim().length() > 0) {
			csf.add(new SearchFilter("caseStatus", Operator.EQ, caseStatus));
		}
		
		Specification<Settlement> specification = DynamicSpecifications.bySearchFilter(request, Settlement.class, csf);
		List<Settlement> reqs = lpglService.findBySettleExample(specification, page);
	
		map.put("settles", reqs);
		return TO_XLS;
	}
	
	@RequiresPermissions("Settlement:view")
	@RequestMapping(value="/log/{id}", method=RequestMethod.GET)
	public String viewLog(@PathVariable Long id, HttpServletRequest request, Page page, Map<String, Object> map) {
		request.setAttribute("settle_id", id);
		List<SettlementLog> logs = lpglService.findLogBySettleId(id);
		
		map.put("settleLog", logs);
		map.put("page", page);
		return LOG_DTL;
	}
	
	/*
	 * settle task
	 */
	@RequiresPermissions("SettleTask:save")
	@RequestMapping(value="/task/create", method=RequestMethod.GET)
	public String preCreateTask() {
		return CREATE_TASK;
	}
	
	@Log(message="添加了{0}的理赔案件调查任务。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("SettleTask:save")
	@RequestMapping(value="/task/create", method=RequestMethod.POST)
	public @ResponseBody String createTask(@Valid SettleTask settleTask, HttpServletRequest request) {
		User user = SecurityUtils.getShiroUser().getUser();
		try {
			settleTask.setOperateId(user.getId());
			settleTask.setCreateTime(new Date());
			settleTask.setCheckStatus(SettleTask.STATUS_ING);
			lpglService.saveOrUpdateSettleTask(settleTask);
			
			SettleTaskLog settleLog = new SettleTaskLog();
			settleLog.setSettleTask(settleTask);
			settleLog.setDealDate(new Date());
			settleLog.setUser(user);
			settleLog.setInfo("添加了理赔案件调查任务信息；");
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleTaskLog(settleLog);
			
		} catch (ExistedException e) {
			return AjaxObject.newError("添加理赔案件失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{settleTask.getInsured()}));
		return AjaxObject.newOk("添加理赔案件成功！").toString();
	}
	
	@Log(message="关闭了{0}的理赔案件调查任务。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("SettleTask:save")
	@RequestMapping(value="/task/done", method=RequestMethod.POST)
	public @ResponseBody String doneTask(Long id, HttpServletRequest request) {
		User user = SecurityUtils.getShiroUser().getUser();
		SettleTask task = lpglService.getSettleTask(id);
		try {
			task.setCheckStatus(SettleTask.STATUS_DONE);
			lpglService.saveOrUpdateSettleTask(task);
			
			SettleTaskLog settleLog = new SettleTaskLog();
			settleLog.setSettleTask(task);
			settleLog.setDealDate(new Date());
			settleLog.setUser(user);
			settleLog.setInfo("将理赔案件调查任务置为完成状态；");
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleTaskLog(settleLog);
			
		} catch (ExistedException e) {
			return AjaxObject.newError("关闭理赔案件任务失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{task.getInsured()}));
		return AjaxObject.newOk("关闭理赔案件调查任务成功！").toString();
	}
	
	@RequiresPermissions("SettleTask:edit")
	@RequestMapping(value="/task/update/{id}", method=RequestMethod.GET)
	public String preUpdateTask(@PathVariable Long id, Map<String, Object> map) {
		SettleTask settle = lpglService.getSettleTask(id);
		
		map.put("settle", settle);
		return UPDATE_TASK;
	}
	
	@Log(message="修改了出险人{0}的案件调查任务信息。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("SettleTask:edit")
	@RequestMapping(value="/task/update", method=RequestMethod.POST)
	public @ResponseBody String updateTask(@Valid SettleTask settle, HttpServletRequest request) {
		SettleTask src = lpglService.getSettleTask(settle.getId());
		StringBuffer loginfo = new StringBuffer("");
		if(settle.getAttrLink()!=null && src.getAttrLink() == null) {
			loginfo.append("上传附件；");
		}
		if(settle.getInsured()!=null && !src.getInsured().equals(settle.getInsured())) {
			loginfo.append("改出险人：" + src.getInsured() + "->" + settle.getInsured() + "；");
		}
		if(settle.getOrganization()!=null && !src.getOrganization().getName().equals(settle.getOrganization().getName())) {
			loginfo.append("改机构；");
		}
		if(settle.getChecker()!=null && !src.getChecker().equals(settle.getChecker())) {
			loginfo.append("改调查人：" + src.getChecker() + "->" + settle.getChecker() + "；");
		}
		if(settle.getCheckEndDate()!=null && !DateUtils.isSameDay(src.getCheckEndDate(), settle.getCheckEndDate())) {
			loginfo.append("改调查开始日期：" + StringUtil.date2Str(src.getCheckEndDate(), "yy-M-d") + "->" + StringUtil.date2Str(settle.getCheckEndDate(), "yy-M-d") + "；");
		}
		if(settle.getCheckStartDate()!=null && !DateUtils.isSameDay(src.getCheckStartDate(), settle.getCheckStartDate())) {
			loginfo.append("改调查结束日期：" + StringUtil.date2Str(src.getCheckStartDate(), "yy-M-d") + "->" + StringUtil.date2Str(settle.getCheckStartDate(), "yy-M-d") + "；");
		}
		if(settle.getCheckerAddr()!=null && !(src.getCheckerAddr()==null?"":src.getCheckerAddr()).equals(settle.getCheckerAddr())) {
			loginfo.append("改调查地：" + src.getCheckerAddr() + "->" + settle.getCheckerAddr() + "；");
		}
		if(settle.getCheckReq()!=null && !(src.getCheckReq()==null?"":src.getCheckReq()).equals(settle.getCheckReq())) {
			loginfo.append("改调查要求：" + src.getCheckReq() + "->" + settle.getCheckReq() + "；");
		}
		if(settle.getCheckFee()!=null && (src.getCheckFee()==null?0:src.getCheckFee().doubleValue()) != settle.getCheckFee().doubleValue()) {
			loginfo.append("改查勘费：" + src.getCheckFee() + "->" + settle.getCheckFee() + "；");
		}
		
		lpglService.saveOrUpdateSettleTask(settle);
		
		User user = SecurityUtils.getShiroUser().getUser();
		SettleTaskLog settleLog = new SettleTaskLog();
		settleLog.setSettleTask(settle);
		settleLog.setUser(user);
		settleLog.setDealDate(new Date());
		settleLog.setInfo(loginfo.toString());
		settleLog.setIp(request.getRemoteAddr());
		settleLog.setIsKeyInfo(true);
		lpglService.saveOrUpdateSettleTaskLog(settleLog);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{settle.getInsured()}));
		return	AjaxObject.newOk("修改案件调查任务成功！").toString(); 
	}
	
	@Log(message="删除了{0}的案件调查任务信息。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("SettleTask:delete")
	@RequestMapping(value="/task/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteTask(@PathVariable Long id, HttpServletRequest request) {
		SettleTask settle = null;
		try {
			settle = lpglService.getSettleTask(id);
			
			User user = SecurityUtils.getShiroUser().getUser();
			SettleTaskLog settleLog = new SettleTaskLog();
			settleLog.setSettleTask(settle);
			settleLog.setUser(user);
			settleLog.setDealDate(new Date());
			settleLog.setInfo("删除报案任务信息：" + settle.getInsured());
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleTaskLog(settleLog);
			
			
			lpglService.deleteSettle(settle.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除案件任务失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{settle.getInsured()}));
		return AjaxObject.newOk("删除案件任务成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}案件调查任务。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("SettleTask:delete")
	@RequestMapping(value="/task/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyTask(Long[] ids, HttpServletRequest request) {
		String[] policys = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				SettleTask settle = lpglService.getSettleTask(ids[i]);
				
				User user = SecurityUtils.getShiroUser().getUser();
				SettleTaskLog settleLog = new SettleTaskLog();
				settleLog.setSettleTask(settle);
				settleLog.setUser(user);
				settleLog.setDealDate(new Date());
				settleLog.setInfo("删除报案调查任务信息：" + settle.getInsured());
				settleLog.setIp(request.getRemoteAddr());
				settleLog.setIsKeyInfo(true);
				lpglService.saveOrUpdateSettleTaskLog(settleLog);
				
				lpglService.deleteSettle(settle.getId());
				
				policys[i] = settle.getInsured();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除案件调查任务失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("删除案件调查任务成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("SettleTask:view")
	@RequestMapping(value="/task/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listTask(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String caseStatus = request.getParameter("caseStatus");
		Settlement settle = new Settlement();
		settle.setCaseStatus(caseStatus);
		request.setAttribute("settle", settle);
		String orgCode = request.getParameter("organization.orgCode");
		if(orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = user.getOrganization().getOrgCode();
			if(orgCode.contains("11185")) {
				orgCode = "8644";
			}
		} else {
			if(!orgCode.contains(user.getOrganization().getOrgCode())){
				orgCode = user.getOrganization().getOrgCode();
			}
			String orgName = request.getParameter("organization.name");
			request.setAttribute("org_code", orgCode);
			request.setAttribute("org_name", orgName);
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE, orgCode));
		if(caseStatus != null && caseStatus.trim().length() >0) {
			csf.add(new SearchFilter("caseStatus", Operator.LIKE, caseStatus));
		}
		
		Specification<SettleTask> specification = DynamicSpecifications.bySearchFilter(request, SettleTask.class, csf);
		List<SettleTask> users = lpglService.findBySettleTaskExample(specification, page);

		map.put("page", page);
		map.put("users", users);
		return LIST_TASK;
	}
	
	@RequiresPermissions("SettleTask:view")
	@RequestMapping(value="/task/toXls", method=RequestMethod.GET)
	public String taskToXls(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String caseStatus = request.getParameter("caseStatus");
		page.setNumPerPage(65564);
		page.setOrderField("organization.orgCode");
		page.setOrderDirection("ASC");
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE, user.getOrganization().getOrgCode()));
		if(caseStatus != null && caseStatus.trim().length() > 0) {
			csf.add(new SearchFilter("caseStatus", Operator.EQ, caseStatus));
		}
		
		Specification<SettleTask> specification = DynamicSpecifications.bySearchFilter(request, SettleTask.class, csf);
		List<SettleTask> reqs = lpglService.findBySettleTaskExample(specification, page);
	
		map.put("settles", reqs);
		return TASK_TO_XLS;
	}
	
	@RequiresPermissions("SettleTask:view")
	@RequestMapping(value="/task/log/{id}", method=RequestMethod.GET)
	public String viewTaskLog(@PathVariable Long id, HttpServletRequest request, Page page, Map<String, Object> map) {
		request.setAttribute("settle_id", id);
		List<SettleTaskLog> logs = lpglService.findLogBySettleTaskId(id);
		
		map.put("settleLog", logs);
		map.put("page", page);
		return TASK_LOG_DTL;
	}
	
	@RequestMapping(value="/lookupSettlesuggest", method={RequestMethod.POST})
	public @ResponseBody String lookupSettleSuggest(ServletRequest request, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();
		Organization userOrg = user.getOrganization();
		//boolean isOffsite = false;
		Specification<SettlementDtl> specification = DynamicSpecifications.bySearchFilter(request, SettlementDtl.class, 
				new SearchFilter("settlement.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		
		String policyNo = request.getParameter("policyNo");
		if(policyNo != null && policyNo.trim().length() <= 3) {
			return "[{}]";
		} if(policyNo != null && policyNo.trim().length() > 3) {
			if(policyNo.startsWith("86")) {
				if(policyNo.trim().length()>9) {
					specification = DynamicSpecifications.bySearchFilterWithoutRequest(SettlementDtl.class, 
							new SearchFilter("policyNo", Operator.LIKE, policyNo));
				} else {
					return "[{}]";
				}
			}
		}
		Page page = new Page();
		page.setNumPerPage(15);
		List<SettlementDtl> org = lpglService.findBySettleDtlExample(specification, page);
		SerializeConfig mapping = new SerializeConfig();
		HashMap<String, String> fm = new HashMap<String, String>();
		
		//if(!isOffsite) {
		fm.put("id", "id");
		//}
		
		fm.put("policyNo", "policyNo");
		fm.put("holder", "holder");
		fm.put("settlement.insured", "insured");
		fm.put("prodName", "prodName");
		fm.put("policyFee", "policyFee");
		fm.put("plicyValidDate", "policyDate");
		String dateFormat = "yyyy-MM-dd";  
	    mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
		mapping.put(SettlementDtl.class, new JavaBeanSerializer(SettlementDtl.class, fm));
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
		String str = JSON.toJSONString(org, mapping, SerializerFeature.WriteDateUseDateFormat);
		
		return str;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
