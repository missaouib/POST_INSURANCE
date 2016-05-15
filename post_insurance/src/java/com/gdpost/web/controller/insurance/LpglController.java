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
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.entity.component.Settlement;
import com.gdpost.web.entity.component.SettlementDtl;
import com.gdpost.web.entity.component.SettlementLog;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.insurance.LpglService;
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
	private static final String CREATE_DTL = "insurance/lpgl/follow/createDtl";
	//private static final String UPDATE_DTL = "insurance/lpgl/follow/updateDtl";
	
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
			settleLog.setUser(user);
			settleLog.setInfo("添加了理赔案件信息");
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
	public @ResponseBody String createDtl(SettlementDtl settleDtl) {
		try {
			lpglService.saveOrUpdateSettleDtl(settleDtl);
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
		if(src.getCaseDate()!=null && !src.getCaseDate().equals(settle.getCaseDate())) {
			loginfo.append("修改了出险日期：" + src.getCaseDate() + "->" + settle.getCaseDate() + ";");
		}
		if(src.getCaseStatus()!=null && !src.getCaseStatus().equals(settle.getCaseStatus())) {
			loginfo.append("修改了状态：" + src.getCaseStatus() + "->" + settle.getCaseStatus() + ";");
		}
		if(src.getCaseType()!=null && !src.getCaseType().equals(settle.getCaseType())) {
			loginfo.append("修改了类型：" + src.getCaseType() + "->" + settle.getCaseType() + ";");
		}
		if(src.getCloseDate()!=null && !src.getCloseDate().equals(settle.getCloseDate())) {
			loginfo.append("修改了关闭日期：" + src.getCloseDate() + "->" + settle.getCloseDate() + ";");
		}
		if(src.getInsured()!=null && !src.getInsured().equals(settle.getInsured())) {
			loginfo.append("修改了出险人：" + src.getInsured() + "->" + settle.getInsured() + ";");
		}
		if(src.getOrganization()!=null && !src.getOrganization().getName().equals(settle.getOrganization().getName())) {
			loginfo.append("修改了机构;");
		}
		if(src.getPayFee()!=null && src.getPayFee().doubleValue() != settle.getPayFee().doubleValue()) {
			loginfo.append("修改了赔付金额：" + src.getPayFee() + "->" + settle.getPayFee() + ";");
		}
		if(src.getRecordDate()!=null && !src.getRecordDate().equals(settle.getRecordDate())) {
			loginfo.append("修改了立案日期：" + src.getRecordDate() + "->" + settle.getRecordDate() + ";");
		}
		if(src.getReporteDate()!=null && !src.getReporteDate().equals(settle.getReporteDate())) {
			loginfo.append("修改了报案日期：" + src.getReporteDate() + "->" + settle.getReporteDate() + ";");
		}
		if(src.getReporter()!=null && !src.getReporter().equals(settle.getReporter())) {
			loginfo.append("修改了报案人：" + src.getReporter() + "->" + settle.getReporter() + ";");
		}
		if(src.getReporterPhone()!=null && !src.getReporterPhone().equals(settle.getReporterPhone())) {
			loginfo.append("修改了报案人电话：" + src.getReporterPhone() + "->" + settle.getReporterPhone() + ";");
		}
		
		lpglService.saveOrUpdateSettle(settle);
		
		User user = SecurityUtils.getShiroUser().getUser();
		SettlementLog settleLog = new SettlementLog();
		settleLog.setSettlement(settle);
		settleLog.setUser(user);
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
