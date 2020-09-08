/**
 * 
 * ==========================================================
 * 理赔管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.StringUtil;
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.entity.insurance.Gsettle;
import com.gdpost.web.entity.insurance.GsettleDtl;
import com.gdpost.web.entity.insurance.GsettleLog;
import com.gdpost.web.entity.insurance.Policy;
import com.gdpost.web.entity.insurance.SettleTask;
import com.gdpost.web.entity.insurance.SettleTaskLog;
import com.gdpost.web.entity.insurance.Settlement;
import com.gdpost.web.entity.insurance.SettlementLog;
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
import com.gdpost.web.service.insurance.PolicyService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/lpgl")
public class LpglController {
	private static final Logger LOG = LoggerFactory.getLogger(LpglController.class);
	
	@Autowired
	private LpglService lpglService;
	
	@Autowired
	private PolicyService policyService;

	private static final String CREATE = "insurance/lpgl/follow/create";
	private static final String UPDATE = "insurance/lpgl/follow/update";
	private static final String UPDATE_DTL = "insurance/lpgl/follow/updateDtl";
	private static final String LIST = "insurance/lpgl/follow/list";
	private static final String TO_XLS = "insurance/lpgl/follow/toXls";
	private static final String LOG_DTL = "insurance/lpgl/follow/logs";
	
	private static final String CREATE_TASK = "insurance/lpgl/task/create";
	private static final String UPDATE_TASK = "insurance/lpgl/task/update";
	private static final String LIST_TASK = "insurance/lpgl/task/list";
	private static final String TASK_TO_XLS = "insurance/lpgl/task/toXls";
	private static final String TASK_LOG_DTL = "insurance/lpgl/task/logs";
	
	/*
	 * ===
	 * 团险
	 * ===
	 */
	private static final String G_CREATE = "insurance/lpgl/gfollow/create";
	private static final String G_UPDATE = "insurance/lpgl/gfollow/update";
	private static final String G_LIST = "insurance/lpgl/gfollow/list";
	private static final String G_TO_XLS = "insurance/lpgl/gfollow/toXls";
	private static final String G_CREATE_DTL = "insurance/lpgl/gfollow/createDtl";
	private static final String G_LOG_DTL = "insurance/lpgl/gfollow/logs";
	/*
	 * ===
	 * end of tuanxian 
	 * ===
	 */
	
	String strError = "{\"jsonrpc\":\"2.0\",\"result\":\"error\",\"id\":\"id\",\"message\":\"操作失败。\"}";
	
	@RequiresPermissions("Settlement:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}
	
	@Log(message="添加了{0}的理赔案件。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("Settlement:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@Deprecated
	public @ResponseBody String create(@Valid Settlement settle, HttpServletRequest request) {
		User user = SecurityUtils.getShiroUser().getUser();
		try {
			settle.setOperateId(user.getId());
			//settle.setCreateTime(new Date());
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
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{settle.getCaseMan()}));
		return AjaxObject.newOk("添加理赔案件成功！").toString();
	}
	
	@RequiresPermissions("Settlement:edit")
	@RequestMapping(value="/detail/{id}", method=RequestMethod.GET)
	public String preDetail(@PathVariable Long id, HttpServletRequest request, Map<String, Object> map) {
		Settlement settle = lpglService.getSettle(id);
		request.setAttribute("settle", settle);
		
		List<SettlementLog> dealLogs = lpglService.findDealLogBySettleId(id);
		request.setAttribute("dealLogs", dealLogs);
		
		if(settle != null && settle.getId() != null) {
			request.setAttribute("flag", "update");
			request.setAttribute("settle", settle);
		} else {
			request.setAttribute("flag", "create");
		}
		return UPDATE_DTL;
	}
	
	@Log(message="登记了{0}的理赔案件详情。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("Settlement:save")
	@RequestMapping(value="/detail", method=RequestMethod.POST)
	public @ResponseBody String createDtl(Settlement settle, HttpServletRequest request) {
		
		User user = SecurityUtils.getShiroUser().getUser();
		Settlement src = lpglService.getSettle(settle.getId());
		StringBuffer loginfo = new StringBuffer("");
		String policyId = request.getParameter("policy.id");
		String policyNo = request.getParameter("policy.policyNo");
		boolean needTask = false;
		Policy policy = null;
		
		if(policyId != null && policyId.trim().length()>0) {
			policy = policyService.get(Long.parseLong(policyId));
		}
		
		if(policyId!=null && (src.getPolicy() == null  || !policyId.equals(src.getPolicy().getId().toString()))) {
			loginfo.append("改保单号：" + (src.getPolicy()==null?"":src.getPolicy().getPolicyNo()) + "->" + policyNo + "；");
			src.setPolicy(policy);
		}
		if(settle.getCaseStatus()!=null && !src.getCaseStatus().equals(settle.getCaseStatus())) {
			loginfo.append("改状态：" + src.getCaseStatus() + "->" + settle.getCaseStatus() + "；");
			src.setCaseStatus(settle.getCaseStatus());
			if(settle.getCaseStatus().equals("待调查")) {
				needTask = true;
			}
		}
		if(settle.getCaseType()!=null && (src.getCaseType() == null || !src.getCaseType().equals(settle.getCaseType()))) {
			loginfo.append("改理赔类型：" + src.getCaseType() + "->" + settle.getCaseType() + "；");
			src.setCaseType(settle.getCaseType());
		}
		if(settle.getReporteDate()!=null && src.getReporteDate() != null && !DateUtils.isSameDay(src.getReporteDate(), settle.getReporteDate())) {
			loginfo.append("改报案日期：" + StringUtil.date2Str(src.getReporteDate(), "yy-M-d") + "->" + StringUtil.date2Str(settle.getReporteDate(), "yy-M-d") + "；");
			src.setReporteDate(settle.getReporteDate());
		} else if(settle.getReporteDate()!=null && src.getReporteDate() == null) {
			loginfo.append("改报案日期：" + "null ->" + StringUtil.date2Str(settle.getReporteDate(), "yy-M-d") + "；");
			src.setReporteDate(settle.getReporteDate());
		}
		if(settle.getRemark()!=null && !(src.getRemark()==null?"":src.getRemark()).equals(settle.getRemark())) {
			loginfo.append("改出险描述：" + src.getRemark() + "->" + settle.getRemark() + "；");
			src.setRemark(settle.getRemark());
		}
		
		if(loginfo.length() > 0) {
			SettlementLog settleLog = new SettlementLog();
			settleLog.setSettlement(src);
			settleLog.setUser(user);
			settleLog.setDealDate(new Date());
			settleLog.setInfo(loginfo.toString());
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleLog(settleLog);
		}
		
		try {			
			SettlementLog settleLog = new SettlementLog();
			settleLog.setSettlement(src);
			settleLog.setDealDate(new Date());
			settleLog.setUser(user);
			settleLog.setInfo("添加或者更新了案件进度相关信息；");
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleLog(settleLog);
			
			String toDealDay = request.getParameter("toDealDay");//需要几天反馈
			//String followDate = request.getParameter("followDate"); //自动计算
			Date followDate = StringUtil.dateAdd(new Date(), Integer.parseInt(toDealDay));
			
			String info = request.getParameter("info");
			SettlementLog settleInfo = new SettlementLog();
			settleInfo.setSettlement(settle);
			settleInfo.setDealDate(new Date());
			
			settleInfo.setFollowDate(followDate);
			settleInfo.setIsFollow(true);
			settleInfo.setToDealDay(Integer.valueOf(toDealDay));
			settleInfo.setInfo("设置跟进要求->第" + toDealDay + "日内反馈：" + info);
			
			settleInfo.setUser(user);
			settleInfo.setIp(request.getRemoteAddr());
			settleInfo.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleLog(settleInfo);
			
			src.setFollowDate(followDate);
			lpglService.saveOrUpdateSettle(src);
			
			//如果为待调查，自动生成一条调查任务
			if(needTask && lpglService.getSettleTaskByClaimsNo(src.getClaimsNo()) == null) {
				LOG.debug("--------------- 系统自动生成理赔调查任务：" + src.getClaimsNo());
				SettleTask task = new SettleTask();
				task.setSettlement(src);
				task.setCaseType(src.getCaseType());
				task.setPolicy(policy);
				task.setInsured(src.getCaseMan());
				task.setOrganization(policy.getOrganization());
				task.setOperateId(user.getId());
				task.setCreateTime(new Date());
				task.setCheckStartDate(new Date());
				task.setCheckStatus(SettleTask.STATUS_ING);
				lpglService.saveOrUpdateSettleTask(task);
				
				SettleTaskLog taskLog = new SettleTaskLog();
				taskLog.setSettleTask(task);
				taskLog.setDealDate(new Date());
				taskLog.setUser(user);
				taskLog.setInfo("系统自动触发添加理赔案件调查任务信息；");
				taskLog.setIp(request.getRemoteAddr());
				taskLog.setIsKeyInfo(true);
				lpglService.saveOrUpdateSettleTaskLog(taskLog);
			}
		} catch (ExistedException e) {
			return AjaxObject.newError("理赔案件详情登记失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getClaimsNo()}));
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
		boolean needTask = false;
		User user = SecurityUtils.getShiroUser().getUser();
		
		if(settle.getCaseStatus()!=null && !src.getCaseStatus().equals(settle.getCaseStatus())) {
			loginfo.append("改状态：" + src.getCaseStatus() + "->" + settle.getCaseStatus() + "；");
			src.setCaseStatus(settle.getCaseStatus());
			if(settle.getCaseStatus().equals("待调查")) {
				needTask = true;
			}
		}
		
		if(settle.getCaseType()!=null && (src.getCaseType() == null || !src.getCaseType().equals(settle.getCaseType()))) {
			loginfo.append("改理赔类型：" + src.getCaseType() + "->" + settle.getCaseType() + "；");
			src.setCaseType(settle.getCaseType());
		}
		if(settle.getReporteDate()!=null && src.getReporteDate() != null && !DateUtils.isSameDay(src.getReporteDate(), settle.getReporteDate())) {
			loginfo.append("改报案日期：" + StringUtil.date2Str(src.getReporteDate(), "yy-M-d") + "->" + StringUtil.date2Str(settle.getReporteDate(), "yy-M-d") + "；");
			src.setReporteDate(settle.getReporteDate());
		} else if(settle.getReporteDate()!=null && src.getReporteDate() == null) {
			loginfo.append("改报案日期：" + "null ->" + StringUtil.date2Str(settle.getReporteDate(), "yy-M-d") + "；");
			src.setReporteDate(settle.getReporteDate());
		}
		
		if(settle.getRemark()!=null && !(src.getRemark()==null?"":src.getRemark()).equals(settle.getRemark())) {
			loginfo.append("改出险描述：" + src.getRemark() + "->" + settle.getRemark() + "；");
			src.setRemark(settle.getRemark());
		}
		
		lpglService.saveOrUpdateSettle(src);
		
		if(loginfo.length() > 0) {
			SettlementLog settleLog = new SettlementLog();
			settleLog.setSettlement(src);
			settleLog.setUser(user);
			settleLog.setDealDate(new Date());
			settleLog.setInfo(loginfo.toString());
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleLog(settleLog);
		}
		
		//如果为待调查，自动生成一条调查任务
		if(needTask && lpglService.getSettleTaskByClaimsNo(src.getClaimsNo()) == null) {
			LOG.debug("--------------- 系统自动生成理赔调查任务：" + src.getClaimsNo());
			SettleTask task = new SettleTask();
			task.setSettlement(src);
			task.setCaseType(src.getCaseType());
			//task.setPolicy(policy);
			task.setInsured(src.getCaseMan());
			//task.setOrganization(policy.getOrganization());
			task.setOperateId(user.getId());
			task.setCreateTime(new Date());
			task.setCheckStartDate(new Date());
			task.setCheckStatus(SettleTask.STATUS_ING);
			lpglService.saveOrUpdateSettleTask(task);
			
			SettleTaskLog taskLog = new SettleTaskLog();
			taskLog.setSettleTask(task);
			taskLog.setDealDate(new Date());
			taskLog.setUser(user);
			taskLog.setInfo("系统自动触发添加理赔案件调查任务信息；");
			taskLog.setIp(request.getRemoteAddr());
			taskLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleTaskLog(taskLog);
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{settle.getCaseMan()}));
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
			settleLog.setInfo("删除报案信息：" + settle.getCaseMan());
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleLog(settleLog);
			
			
			lpglService.deleteSettle(settle.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除案件失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{settle.getCaseMan()}));
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
				settleLog.setInfo("删除报案信息：" + settle.getCaseMan());
				settleLog.setIp(request.getRemoteAddr());
				settleLog.setIsKeyInfo(true);
				lpglService.saveOrUpdateSettleLog(settleLog);
				
				lpglService.deleteSettle(settle.getId());
				
				policys[i] = settle.getCaseMan();
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
		request.setAttribute("caseStatus", caseStatus);
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
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE_R, orgCode));
		if(caseStatus != null && caseStatus.trim().length() >0) {
			csf.add(new SearchFilter("caseStatus", Operator.EQ, caseStatus));
		} else {
			csf.add(new SearchFilter("caseStatus", Operator.NEQ, "已结案"));
		}
		
		Specification<Settlement> specification = DynamicSpecifications.bySearchFilter(request, Settlement.class, csf);
		List<Settlement> users = lpglService.findBySettleExample(specification, page);

		map.put("page", page);
		map.put("users", users);
		return LIST;
	}
	
	@Log(message="下载了理赔案件数据", level=LogLevel.INFO, module=LogModule.LPGL)
	@RequiresPermissions("Settlement:view")
	@RequestMapping(value="/toXls", method=RequestMethod.GET)
	public String toXls(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String caseStatus = request.getParameter("caseStatus");
		page.setNumPerPage(65564);
		page.setOrderField("organization.orgCode");
		page.setOrderDirection("ASC");
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE_R, user.getOrganization().getOrgCode()));
		if(caseStatus != null && caseStatus.trim().length() >0) {
			csf.add(new SearchFilter("caseStatus", Operator.EQ, caseStatus));
		} else {
			csf.add(new SearchFilter("caseStatus", Operator.NEQ, "已结案"));
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
	public @ResponseBody String createTask(HttpServletRequest request, @RequestParam(value = "file", required = true) MultipartFile file, SettleTask task) {
		LOG.debug("-------------------------------------upload:" + file.getOriginalFilename());
        LOG.debug("------------" + task.toString());
        int iNY = UploadDataUtils.getNianYue();
        String strPath = UploadDataUtils.getNoticeFileStorePath(request, iNY, "LPGL");
        String updatePath = UploadDataUtils.getNoticeRelateFileStorePath(request, iNY, "LPGL");
		String strNewFileName = null;
		boolean hasFile = false;
		String realname = request.getParameter("realname");
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
        ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();

        String attrLink = updatePath + "/" + strNewFileName;
        
		try {
			if(hasFile) {
				task.setAttrLink(attrLink);
			}
			task.setChecker(realname);
			task.setCheckStartDate(new Date());
			String policyNo = request.getParameter("policyNo");
			Policy policy = policyService.getByPolicyNo(policyNo);
			task.setPolicy(policy);
			if(policy != null) {
				Settlement settle = lpglService.getSettleByPolicyNo(policyNo);
				task.setSettlement(settle);
				task.setOrganization(policy.getOrganization());
			} else {
				task.setOrganization(user.getOrganization());
			}
			task.setOperateId(user.getId());
			task.setCreateTime(new Date());
			task.setCheckStatus(SettleTask.STATUS_ING);
			lpglService.saveOrUpdateSettleTask(task);
			
			SettleTaskLog settleLog = new SettleTaskLog();
			settleLog.setSettleTask(task);
			settleLog.setDealDate(new Date());
			settleLog.setUser(user);
			settleLog.setInfo("添加了理赔案件调查任务信息；");
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleTaskLog(settleLog);
			
			String toDealDay = request.getParameter("toDealDay");
			String followDate = request.getParameter("followDate");
			String info = request.getParameter("info");
			SettleTaskLog taskLog = new SettleTaskLog();
			taskLog.setSettleTask(task);
			taskLog.setDealDate(new Date());
			if(followDate != null && followDate.trim().length()>0 && info != null && info.trim().length()>0) {
				taskLog.setFollowDate(StringUtil.str2Date(followDate, "yyyy-MM-dd"));
				taskLog.setInfo(info);
				taskLog.setIsFollow(true);
				taskLog.setToDealDay(null);
			} else {
				taskLog.setIsFollow(true);
				taskLog.setToDealDay(Integer.valueOf(toDealDay));
				taskLog.setInfo("设置了案件调查任务跟进要求->第" + toDealDay + "日内反馈。");
			}
			taskLog.setUser(user);
			taskLog.setIp(request.getRemoteAddr());
			taskLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleTaskLog(taskLog);
			
		} catch (ExistedException e) {
			return AjaxObject.newError("添加理赔案件失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{task.getInsured()}));
		return AjaxObject.newOk("添加理赔案件成功！").toString();
	}
	
	@Log(message="关闭了{0}的理赔案件调查任务。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("SettleTask:save")
	@RequestMapping(value="/task/done/{id}", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String doneTask(@PathVariable Long id, HttpServletRequest request) {
		User user = SecurityUtils.getShiroUser().getUser();
		SettleTask task = lpglService.getSettleTask(id);
		try {
			task.setCheckEndDate(new Date());
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
		return AjaxObject.newOk("关闭理赔案件调查任务成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("SettleTask:edit")
	@RequestMapping(value="/task/update/{id}", method=RequestMethod.GET)
	public String preUpdateTask(@PathVariable Long id, Map<String, Object> map) {
		SettleTask task = lpglService.getSettleTask(id);
		
		Settlement settle = new Settlement();
		Policy policy = new Policy();
		if(task.getPolicy() != null) {
			settle = lpglService.getSettleByPolicyNo(task.getPolicy().getPolicyNo());
			policy = policyService.getByPolicyNo(task.getPolicy().getPolicyNo());
		} else if(task.getSettlement() != null) {
			settle = lpglService.getSettleByClaimsNo(task.getSettlement().getClaimsNo());
		}
		
		List<SettleTaskLog> dealLogs = lpglService.findDealLogByTaskId(id);
		map.put("dealLogs", dealLogs);
		
		map.put("task", task);
		map.put("policy", policy);
		map.put("settle", settle);
		return UPDATE_TASK;
	}
	
	@Log(message="修改了出险人{0}的案件调查任务信息。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("SettleTask:edit")
	@RequestMapping(value="/task/update", method=RequestMethod.POST)
	public @ResponseBody String updateTask(HttpServletRequest request, @RequestParam(value = "file", required = true) MultipartFile file, SettleTask task) {
		String taskId = request.getParameter("taskId");
		String policyNo = request.getParameter("policyNo");
		String realname = request.getParameter("user.realname");
		String insured = request.getParameter("insured");
		
		int iNY = UploadDataUtils.getNianYue();
        String strPath = UploadDataUtils.getNoticeFileStorePath(request, iNY, "LPGL");
        String updatePath = UploadDataUtils.getNoticeRelateFileStorePath(request, iNY, "LPGL");
		String strNewFileName = null;
		boolean hasFile = false;
		
		Policy policy = policyService.getByPolicyNo(policyNo);
		SettleTask src = lpglService.getSettleTask(new Long(taskId));
		
        if(file != null && file.getOriginalFilename() != null && file.getOriginalFilename().trim().length()>0) {
	        try
	        {
	        	hasFile = true;
	        	
	        	if(src.getAttrLink() != null && src.getAttrLink().trim().length()>0) {
					UploadDataUtils.delateFile(request, src.getAttrLink());
				}
	        	
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
        ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();

        String attrLink = updatePath + "/" + strNewFileName;
		
		if(hasFile) {
			src.setAttrLink(attrLink);
		}
		
		StringBuffer loginfo = new StringBuffer("");
		if(task.getAttrLink()!=null) {
			loginfo.append("修改附件；");
			src.setAttrLink(task.getAttrLink());
		}
		if(insured!=null && !src.getInsured().equals(insured)) {
			loginfo.append("改出险人：" + src.getInsured() + "->" + task.getInsured() + "；");
			src.setInsured(insured);
		}
		if(realname!=null && !src.getChecker().equals(realname)) {
			loginfo.append("改调查人：" + src.getChecker() + "->" + task.getChecker() + "；");
			src.setChecker(realname);
		}
		if(task.getCheckerAddr()!=null && !(src.getCheckerAddr()==null?"":src.getCheckerAddr()).equals(task.getCheckerAddr())) {
			loginfo.append("改调查地：" + src.getCheckerAddr() + "->" + task.getCheckerAddr() + "；");
			src.setCheckerAddr(task.getCheckerAddr());
		}
		if(task.getCheckReq()!=null && !(src.getCheckReq()==null?"":src.getCheckReq()).equals(task.getCheckReq())) {
			loginfo.append("改调查要求：" + src.getCheckReq() + "->" + task.getCheckReq() + "；");
			src.setCheckReq(task.getCheckReq());
		}
		if(task.getCheckFee()!=null && (src.getCheckFee()==null?0:src.getCheckFee().doubleValue()) != task.getCheckFee().doubleValue()) {
			loginfo.append("改查勘费：" + src.getCheckFee() + "->" + task.getCheckFee() + "；");
			src.setCheckFee(task.getCheckFee());
		}
		
		src.setPolicy(policy);
		if(policy != null) {
			Settlement settleDtl = lpglService.getSettleByPolicyNo(policyNo);
			src.setSettlement(settleDtl);
			src.setOrganization(policy.getOrganization());
		} else {
			src.setOrganization(user.getOrganization());
		}
		src.setCheckStatus(SettleTask.STATUS_ING);
		lpglService.saveOrUpdateSettleTask(src);
		
		if(loginfo.length() > 0) {
			SettleTaskLog settleLog = new SettleTaskLog();
			settleLog.setSettleTask(src);
			settleLog.setUser(user);
			settleLog.setDealDate(new Date());
			settleLog.setInfo(loginfo.toString());
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleTaskLog(settleLog);
		}
		
		String toDealDay = request.getParameter("toDealDay");
		String followDate = request.getParameter("followDate");
		String info = request.getParameter("info");
		SettleTaskLog taskLog = new SettleTaskLog();
		taskLog.setSettleTask(src);
		taskLog.setDealDate(new Date());
		if(followDate != null && followDate.trim().length()>0 && info != null && info.trim().length()>0) {
			taskLog.setFollowDate(StringUtil.str2Date(followDate, "yyyy-MM-dd"));
			taskLog.setInfo(info);
			taskLog.setIsFollow(true);
			taskLog.setToDealDay(null);
		} else {
			taskLog.setIsFollow(true);
			taskLog.setToDealDay(Integer.valueOf(toDealDay));
			taskLog.setInfo("设置了案件调查任务跟进要求->第" + toDealDay + "日内反馈。");
		}
		taskLog.setUser(user);
		taskLog.setIp(request.getRemoteAddr());
		taskLog.setIsKeyInfo(true);
		lpglService.saveOrUpdateSettleTaskLog(taskLog);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{task.getInsured()}));
		return	AjaxObject.newOk("修改案件调查任务成功！").setCallbackType("").toString(); 
	}
	
	@Log(message="删除了{0}的案件调查任务信息。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("SettleTask:delete")
	@RequestMapping(value="/task/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteTask(@PathVariable Long id, HttpServletRequest request) {
		SettleTask settle = null;
		try {
			settle = lpglService.getSettleTask(id);
			
			if(settle.getAttrLink() != null && settle.getAttrLink().trim().length() >0 ) {
				UploadDataUtils.delateFile(request, settle.getAttrLink());
			}
			
			User user = SecurityUtils.getShiroUser().getUser();
			SettleTaskLog settleLog = new SettleTaskLog();
			settleLog.setSettleTask(settle);
			settleLog.setUser(user);
			settleLog.setDealDate(new Date());
			settleLog.setInfo("删除报案任务信息：" + settle.getInsured());
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateSettleTaskLog(settleLog);
			
			
			lpglService.deleteSettleTask(settle.getId());
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
				
				if(settle.getAttrLink() != null && settle.getAttrLink().trim().length() >0 ) {
					UploadDataUtils.delateFile(request, settle.getAttrLink());
				}
				
				User user = SecurityUtils.getShiroUser().getUser();
				SettleTaskLog settleLog = new SettleTaskLog();
				settleLog.setSettleTask(settle);
				settleLog.setUser(user);
				settleLog.setDealDate(new Date());
				settleLog.setInfo("删除报案调查任务信息：" + settle.getInsured());
				settleLog.setIp(request.getRemoteAddr());
				settleLog.setIsKeyInfo(true);
				lpglService.saveOrUpdateSettleTaskLog(settleLog);
				
				lpglService.deleteSettleTask(settle.getId());
				
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
		String checkStatus = request.getParameter("checkStatus");
		if(checkStatus == null) {
			checkStatus = SettleTask.STATUS_ING;
		}
		SettleTask task = new SettleTask();
		task.setCheckStatus(checkStatus);
		request.setAttribute("task", task);
		request.setAttribute("checkStatus", checkStatus);
		String checkDateFlag = request.getParameter("checkDateFlag");
		if(checkDateFlag == null||checkDateFlag.trim().length()<=0) {
			checkDateFlag = "1";
		}
		request.setAttribute("checkDateFlag", checkDateFlag);
		String taskLong = request.getParameter("taskLong");
		String checkDate1 = request.getParameter("checkDate1");
		String checkDate2 = request.getParameter("checkDate2");
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
		csf.add(new SearchFilter("organization.orgCode", Operator.OR_LIKE_R, orgCode));
		csf.add(new SearchFilter("organization.orgCode", Operator.OR_ISNULL, null));
		csf.add(new SearchFilter("checker", Operator.OR_EQ, user.getRealname()));
		if(checkDate1 != null && checkDate2 != null) {
			request.setAttribute("checkDate1", checkDate1);
			request.setAttribute("checkDate2", checkDate2);
			if(checkDateFlag.equals("0")) {
				csf.add(new SearchFilter("checkStartDate", Operator.GTE, checkDate1));
				csf.add(new SearchFilter("checkStartDate", Operator.LTE, checkDate2));
			} else {
				csf.add(new SearchFilter("checkEndDate", Operator.GTE, checkDate1));
				csf.add(new SearchFilter("checkEndDate", Operator.LTE, checkDate2));
			}
		}
		if(checkStatus != null && checkStatus.trim().length() >0) {
			csf.add(new SearchFilter("checkStatus", Operator.EQ, checkStatus));
		}
		
		if(taskLong != null && taskLong.trim().length()>0) {
			request.setAttribute("taskLong", taskLong);
			if(Integer.parseInt(taskLong)<=7) {
				csf.add(new SearchFilter("now()-checkStartDate", Operator.LT, taskLong));
			} else {
				csf.add(new SearchFilter("now()-checkStartDate", Operator.GTE, taskLong));
			}
		}
		Specification<SettleTask> specification = DynamicSpecifications.bySearchFilter(request, SettleTask.class, csf);
		List<SettleTask> users = lpglService.findBySettleTaskExample(specification, page);

		map.put("page", page);
		map.put("users", users);
		return LIST_TASK;
	}
	
	@Log(message="下载了理赔调查任务数据", level=LogLevel.INFO, module=LogModule.LPGL)
	@RequiresPermissions("SettleTask:view")
	@RequestMapping(value="/task/toXls", method=RequestMethod.GET)
	public String taskToXls(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String checkStatus = request.getParameter("checkStatus");
		String orgCode = request.getParameter("organization.orgCode");
		String checkDateFlag = request.getParameter("checkDateFlag");
		if(checkDateFlag == null||checkDateFlag.trim().length()<=0) {
			checkDateFlag = "1";
		}
		String checkDate1 = request.getParameter("checkDate1");
		String checkDate2 = request.getParameter("checkDate2");
		
		if(orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = user.getOrganization().getOrgCode();
			if(orgCode.contains("11185")) {
				orgCode = "8644";
			}
		} else {
			if(!orgCode.contains(user.getOrganization().getOrgCode())){
				orgCode = user.getOrganization().getOrgCode();
			}
		}
		
		page.setNumPerPage(65564);
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE_R, orgCode));
		if(checkStatus != null && checkStatus.trim().length() >0) {
			csf.add(new SearchFilter("checkStatus", Operator.EQ, checkStatus));
		}
		if(checkDateFlag.equals("0")) {
			csf.add(new SearchFilter("checkStartDate", Operator.GTE, checkDate1));
			csf.add(new SearchFilter("checkStartDate", Operator.LTE, checkDate2));
		} else {
			csf.add(new SearchFilter("checkEndDate", Operator.GTE, checkDate1));
			csf.add(new SearchFilter("checkEndDate", Operator.LTE, checkDate2));
		}
		Specification<SettleTask> specification = DynamicSpecifications.bySearchFilter(request, SettleTask.class, csf);
		List<SettleTask> tasks = lpglService.findBySettleTaskExample(specification, page);

		map.put("page", page);
		map.put("tasks", tasks);
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
		Specification<Settlement> specification = DynamicSpecifications.bySearchFilter(request, Settlement.class, 
				new SearchFilter("settlement.organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		
		String policyNo = request.getParameter("policyNo");
		if(policyNo != null && policyNo.trim().length() <= 3) {
			return "[{}]";
		} if(policyNo != null && policyNo.trim().length() > 3) {
			if(policyNo.startsWith("86") || policyNo.startsWith("76") || policyNo.startsWith("96") || policyNo.startsWith("81")) {
				if(policyNo.trim().length()>9) {
					specification = DynamicSpecifications.bySearchFilterWithoutRequest(Settlement.class, 
							new SearchFilter("policyNo", Operator.LIKE, policyNo));
				} else {
					return "[{}]";
				}
			}
		}
		Page page = new Page();
		page.setNumPerPage(15);
		List<Settlement> org = lpglService.findBySettleExample(specification, page);
		if(org == null || org.isEmpty()) {
			return "[{}]";
		}
		SerializeConfig mapping = new SerializeConfig();
		HashMap<String, String> fm = new HashMap<String, String>();
		//LOG.debug(org.get(0).toString());
		//if(!isOffsite) {
		fm.put("id", "id");
		//}
		
		fm.put("policyNo", "policyNo");
		fm.put("holder", "holder");
		fm.put("insured", "insured");
		fm.put("prodName", "prodName");
		fm.put("policyFee", "policyFee");
		fm.put("policyDate", "policyDate");
		fm.put("caseDate", "caseDate");
		fm.put("caseType", "caseType");
		String dateFormat = "yyyy-MM-dd";  
	    mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
		mapping.put(Settlement.class, new JavaBeanSerializer(Settlement.class, fm));
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
		String str = JSON.toJSONString(org, mapping, SerializerFeature.WriteDateUseDateFormat);
		
		return str;
	}
	
	/*
	 * ============
	 * 团险
	 * ===========
	 */
	@RequiresPermissions("Gsettle:save")
	@RequestMapping(value="/gfollow/create", method=RequestMethod.GET)
	public String preCreateGFollow() {
		return G_CREATE;
	}
	
	@Log(message="添加了{0}的团险理赔案件。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("Gsettle:save")
	@RequestMapping(value="/gfollow/create", method=RequestMethod.POST)
	public @ResponseBody String createGFollow(@Valid Gsettle settle, HttpServletRequest request) {
		User user = SecurityUtils.getShiroUser().getUser();
		try {
			settle.setOperateId(user.getId());
			settle.setCreateTime(new Date());
			settle.setCaseStatus("客户首次提交资料");
			lpglService.saveOrUpdateGsettle(settle);
			GsettleLog settleLog = new GsettleLog();
			settleLog.setGsettle(settle);
			settleLog.setDealDate(new Date());
			settleLog.setUser(user);
			settleLog.setInfo("添加了团险理赔案件信息；");
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateGsettleLog(settleLog);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加团险理赔案件失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{settle.getInsured()}));
		return AjaxObject.newOk("添加团险理赔案件成功！").toString();
	}
	
	@RequiresPermissions("Gsettle:save")
	@RequestMapping(value="/gfollow/detail/{id}", method=RequestMethod.GET)
	public String preCreateGFollowDtl(@PathVariable Long id, HttpServletRequest request) {
		Gsettle settle = lpglService.getGsettle(id);
		request.setAttribute("settle", settle);
		GsettleDtl settleDtl = lpglService.getDtlByGsettleId(id);
		
		List<GsettleLog> dealLogs = lpglService.findDealLogByGsettleId(id);
		request.setAttribute("dealLogs", dealLogs);
		
		if(settleDtl != null && settleDtl.getId() != null) {
			request.setAttribute("flag", "update");
			request.setAttribute("settleDtl", settleDtl);
		} else {
			request.setAttribute("flag", "create");
		}
		return G_CREATE_DTL;
	}

	@Log(message="登记了{0}的团险理赔案件详情。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("Gsettle:save")
	@RequestMapping(value="/gfollow/detail", method=RequestMethod.POST)
	public @ResponseBody String createGFollowDtl(GsettleDtl settleDtl, HttpServletRequest request) {
		try {
			LOG.debug("----------- settle dtl id:" + settleDtl.getId());
			String id = request.getParameter("gsettleDtlId");
			Long gsettleId = settleDtl.getGsettle().getId();
			Gsettle gsettle = lpglService.getGsettle(gsettleId);
			GsettleDtl dtl = null;
			if(id != null && id.trim().length() >0) {
				dtl = lpglService.getGsettleDtl(new Long(id));
				BeanUtils.copyProperties(settleDtl, dtl, "id");
			} else {
				dtl = settleDtl;
			}
			dtl.setGsettle(gsettle);
			lpglService.saveOrUpdateGsettleDtl(dtl);
			
			Gsettle settle = lpglService.getGsettle(settleDtl.getGsettle().getId());
			
			User user = SecurityUtils.getShiroUser().getUser();
			
			String toDealDay = request.getParameter("toDealDay");
			String followDate = request.getParameter("followDate");
			String info = request.getParameter("info");
			GsettleLog settleInfo = new GsettleLog();
			settleInfo.setGsettle(settle);
			settleInfo.setDealDate(new Date());
			if(followDate != null && followDate.trim().length()>0 && info != null && info.trim().length()>0) {
				settleInfo.setFollowDate(StringUtil.str2Date(followDate, "yyyy-MM-dd"));
				settleInfo.setInfo(info);
				settleInfo.setIsFollow(true);
				settleInfo.setToDealDay(null);
			} else {
				settleInfo.setIsFollow(true);
				settleInfo.setToDealDay(Integer.valueOf(toDealDay));
				settleInfo.setInfo("设置了团险案件进度跟进要求->第" + toDealDay + "日内反馈。");
			}
			settleInfo.setUser(user);
			settleInfo.setIp(request.getRemoteAddr());
			settleInfo.setIsKeyInfo(true);
			lpglService.saveOrUpdateGsettleLog(settleInfo);
			
		} catch (ExistedException e) {
			return AjaxObject.newError("团险理赔案件详情登记失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{settleDtl.getClaimsNo()}));
		return AjaxObject.newOk("团险理赔案件详情登记成功！").toString();
	}

	@RequiresPermissions("Gsettle:edit")
	@RequestMapping(value="/gfollow/update/{id}", method=RequestMethod.GET)
	public String preUpdateGFollow(@PathVariable Long id, Map<String, Object> map) {
		Gsettle settle = lpglService.getGsettle(id);
		
		map.put("settle", settle);
		return G_UPDATE;
	}
	
	@Log(message="修改了出险人{0}的案件信息（团险）。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("Gsettle:edit")
	@RequestMapping(value="/gfollow/update", method=RequestMethod.POST)
	public @ResponseBody String updateGFollow(@Valid Gsettle settle, HttpServletRequest request) {
		Gsettle src = lpglService.getGsettle(settle.getId());
		StringBuffer loginfo = new StringBuffer("");
		if(settle.getCaseDate()!=null && src.getCaseDate() != null && !DateUtils.isSameDay(src.getCaseDate(), settle.getCaseDate())) {
			loginfo.append("改出险日期：" + StringUtil.date2Str(src.getCaseDate(), "yy-M-d") + "->" + StringUtil.date2Str(settle.getCaseDate(), "yy-M-d") + "；");
			src.setCaseDate(settle.getCaseDate());
		} else if(settle.getCaseDate()!=null && src.getCaseDate() == null) {
			loginfo.append("改出险日期：" + "null ->" + StringUtil.date2Str(settle.getCaseDate(), "yy-M-d") + "；");
			src.setCaseDate(settle.getCaseDate());
		}
		
		if(settle.getCaseStatus()!=null && !src.getCaseStatus().equals(settle.getCaseStatus())) {
			loginfo.append("改状态：" + src.getCaseStatus() + "->" + settle.getCaseStatus() + "；");
			src.setCaseStatus(settle.getCaseStatus());
		}
		
		if(settle.getCaseType()!=null && !src.getCaseType().equals(settle.getCaseType())) {
			loginfo.append("改类型：" + src.getCaseType() + "->" + settle.getCaseType() + "；");
			src.setCaseType(settle.getCaseType());
		}
		
		if(settle.getCloseDate()!=null && src.getCloseDate() != null && !DateUtils.isSameDay(src.getCloseDate(), settle.getCloseDate())) {
			loginfo.append("改关闭日期：" + StringUtil.date2Str(src.getCloseDate(), "yy-M-d") + "->" + StringUtil.date2Str(settle.getCloseDate(), "yy-M-d") + "；");
			src.setCloseDate(settle.getCloseDate());
		} else if(settle.getCloseDate()!=null && src.getCloseDate() == null) {
			loginfo.append("改关闭日期：" + "null ->" + StringUtil.date2Str(settle.getCloseDate(), "yy-M-d") + "；");
			src.setCloseDate(settle.getCloseDate());
		}
		
		if(settle.getInsured()!=null && !src.getInsured().equals(settle.getInsured())) {
			loginfo.append("改出险人：" + src.getInsured() + "->" + settle.getInsured() + "；");
			src.setInsured(settle.getInsured());
		}
		if(settle.getOrganization()!=null && !src.getOrganization().getName().equals(settle.getOrganization().getName())) {
			loginfo.append("改机构；");
			src.setOrganization(settle.getOrganization());
		}
		if(settle.getPayFee()!=null && (src.getPayFee()==null?0:src.getPayFee().doubleValue()) != settle.getPayFee().doubleValue()) {
			loginfo.append("改赔付金额：" + src.getPayFee() + "->" + settle.getPayFee() + "；");
			src.setPayFee(settle.getPayFee());
		}
		
		if(settle.getRecordDate()!=null && src.getRecordDate() != null && !DateUtils.isSameDay(src.getRecordDate(), settle.getRecordDate())) {
			loginfo.append("改立案日期：" + StringUtil.date2Str(src.getRecordDate(), "yy-M-d") + "->" + StringUtil.date2Str(settle.getRecordDate(), "yy-M-d") + "；");
			src.setRecordDate(settle.getRecordDate());
		} else if(settle.getRecordDate()!=null && src.getRecordDate() == null ) {
			loginfo.append("改立案日期：" + "null ->" + StringUtil.date2Str(settle.getRecordDate(), "yy-M-d") + "；");
			src.setRecordDate(settle.getRecordDate());
		}
			
		if(settle.getReporteDate()!=null && src.getReporteDate() != null && !DateUtils.isSameDay(src.getReporteDate(), settle.getReporteDate())) {
			loginfo.append("改报案日期：" + StringUtil.date2Str(src.getReporteDate(), "yy-M-d") + "->" + StringUtil.date2Str(settle.getReporteDate(), "yy-M-d") + "；");
			src.setReporteDate(settle.getReporteDate());
		} else if(settle.getReporteDate()!=null && src.getReporteDate() == null) {
			loginfo.append("改报案日期：" + "null ->" + StringUtil.date2Str(settle.getReporteDate(), "yy-M-d") + "；");
			src.setReporteDate(settle.getReporteDate());
		}
		
		if(settle.getReporter()!=null && !(src.getReporter()==null?"":src.getReporter()).equals(settle.getReporter())) {
			loginfo.append("改报案人：" + src.getReporter() + "->" + settle.getReporter() + "；");
			src.setReporter(settle.getReporter());
		}
		if(settle.getReporterPhone()!=null && !(src.getReporterPhone()==null?"":src.getReporterPhone()).equals(settle.getReporterPhone())) {
			loginfo.append("改报案人电话：" + src.getReporterPhone() + "->" + settle.getReporterPhone() + "；");
			src.setReporterPhone(settle.getReporterPhone());
		}
		
		lpglService.saveOrUpdateGsettle(src);
		
		if(loginfo.length() > 0) {
			User user = SecurityUtils.getShiroUser().getUser();
			GsettleLog settleLog = new GsettleLog();
			settleLog.setGsettle(src);
			settleLog.setUser(user);
			settleLog.setDealDate(new Date());
			settleLog.setInfo(loginfo.toString());
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateGsettleLog(settleLog);
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{settle.getInsured()}));
		return	AjaxObject.newOk("修改团险案件成功！").toString(); 
	}
	
	@Log(message="删除了{0}的团险案件信息。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("Gsettle:prov")
	@RequestMapping(value="/gfollow/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteGFollow(@PathVariable Long id, HttpServletRequest request) {
		Gsettle settle = null;
		try {
			settle = lpglService.getGsettle(id);
			
			User user = SecurityUtils.getShiroUser().getUser();
			GsettleLog settleLog = new GsettleLog();
			settleLog.setGsettle(settle);
			settleLog.setUser(user);
			settleLog.setDealDate(new Date());
			settleLog.setInfo("删除团险报案信息：" + settle.getInsured());
			settleLog.setIp(request.getRemoteAddr());
			settleLog.setIsKeyInfo(true);
			lpglService.saveOrUpdateGsettleLog(settleLog);
			
			
			lpglService.deleteGsettle(settle.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除团险案件失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{settle.getInsured()}));
		return AjaxObject.newOk("删除团险案件成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}团险案件。", level=LogLevel.WARN, module=LogModule.LPGL)
	@RequiresPermissions("Gsettle:delete")
	@RequestMapping(value="/gfollow/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyGF(Long[] ids, HttpServletRequest request) {
		String[] policys = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				Gsettle settle = lpglService.getGsettle(ids[i]);
				
				User user = SecurityUtils.getShiroUser().getUser();
				GsettleLog settleLog = new GsettleLog();
				settleLog.setGsettle(settle);
				settleLog.setUser(user);
				settleLog.setDealDate(new Date());
				settleLog.setInfo("删除团险报案信息：" + settle.getInsured());
				settleLog.setIp(request.getRemoteAddr());
				settleLog.setIsKeyInfo(true);
				lpglService.saveOrUpdateGsettleLog(settleLog);
				
				lpglService.deleteGsettle(settle.getId());
				
				policys[i] = settle.getInsured();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除团险案件失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("删除团险案件成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("Gsettle:view")
	@RequestMapping(value="/gfollow/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listGFollow(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String caseStatus = request.getParameter("caseStatus");
		request.setAttribute("caseStatus", caseStatus);
		Gsettle settle = new Gsettle();
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
		
		Specification<Gsettle> specification = DynamicSpecifications.bySearchFilter(request, Gsettle.class, csf);
		List<Gsettle> users = lpglService.findByGsettleExample(specification, page);

		map.put("page", page);
		map.put("users", users);
		return G_LIST;
	}
	
	@Log(message="下载了团险理赔案件数据", level=LogLevel.INFO, module=LogModule.LPGL)
	@RequiresPermissions("Gsettle:view")
	@RequestMapping(value="/gfollow/toXls", method=RequestMethod.GET)
	public String gfollowToXls(ServletRequest request, Page page, Map<String, Object> map) {
		User user = SecurityUtils.getShiroUser().getUser();
		String caseStatus = request.getParameter("caseStatus");
		page.setNumPerPage(65564);
		page.setOrderField("organization.orgCode");
		page.setOrderDirection("ASC");
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("organization.orgCode", Operator.LIKE_R, user.getOrganization().getOrgCode()));
		if(caseStatus != null && caseStatus.trim().length() > 0) {
			csf.add(new SearchFilter("caseStatus", Operator.EQ, caseStatus));
		}
		
		Specification<Gsettle> specification = DynamicSpecifications.bySearchFilter(request, Gsettle.class, csf);
		List<Gsettle> reqs = lpglService.findByGsettleExample(specification, page);
	
		map.put("settles", reqs);
		return G_TO_XLS;
	}
	
	@RequiresPermissions("Gsettle:view")
	@RequestMapping(value="/gfollow/log/{id}", method=RequestMethod.GET)
	public String viewGFollowLog(@PathVariable Long id, HttpServletRequest request, Page page, Map<String, Object> map) {
		request.setAttribute("settle_id", id);
		List<GsettleLog> logs = lpglService.findLogByGsettleId(id);
		
		map.put("settleLog", logs);
		map.put("page", page);
		return G_LOG_DTL;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
