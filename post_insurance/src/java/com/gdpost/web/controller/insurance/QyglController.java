/**
 * 
 * ==========================================================
 * 契约管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.entity.main.CheckRecord;
import com.gdpost.web.entity.main.CheckWrite;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.insurance.QyglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.StatusDefine.STATUS;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/Qygl")
public class QyglController {
	private static final Logger log = LoggerFactory.getLogger(QyglController.class);
	
	@Autowired
	private QyglService qyglService;

	private static final String VIEW_WRITE = "insurance/qygl/wtj/write/create";
	private static final String UPDATE_WRITE = "insurance/qygl/wtj/write/update";
	private static final String LIST_WRITE = "insurance/qygl/wtj/write/list";
	
	private static final String VIEW_RECORD = "insurance/qygl/wtj/record/create";
	private static final String UPDATE_RECORD = "insurance/qygl/wtj/record/update";
	private static final String LIST_RECORD = "insurance/qygl/wtj/record/list";
	
	/*
	 * =====================================
	 * WRITE
	 * =====================================
	 */
	@RequiresPermissions("CheckWrite:view")
	@RequestMapping(value="/issue/viewCheckWrite/{id}", method=RequestMethod.GET)
	public String viewCheckWrite(@PathVariable Long id, Map<String, Object> map) {
		CheckWrite issue = qyglService.getCheckWrite(id);
		
		map.put("issue", issue);
		map.put("status", STATUS.ReopenStatus);
		return VIEW_WRITE;
	}
	
	@ModelAttribute("preloadCheckWrite")
	public CheckWrite preloadCheckWrite(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			CheckWrite issue = qyglService.getCheckWrite(id);
			return issue;
		}
		return null;
	}
	
	@RequiresPermissions("CheckWrite:edit")
	@RequestMapping(value="/issue/updateCheckWrite/{id}", method=RequestMethod.GET)
	public String preUpdateCheckWrite(@PathVariable Long id, Map<String, Object> map) {
		CheckWrite issue = qyglService.getCheckWrite(id);
		
		map.put("issue", issue);
		return UPDATE_WRITE;
	}
	
	@Log(message="回复了{0}新契约不合格件的信息。")
	@RequiresPermissions("CheckWrite:edit")
	@RequestMapping(value="/issue/updateCheckWrite", method=RequestMethod.POST)
	public @ResponseBody String updateCheckWrite(@Valid @ModelAttribute("preloadCheckWrite")CheckWrite issue) {
		CheckWrite src = qyglService.getCheckWrite(issue.getId());
		src.setDealMan(issue.getDealMan());
		src.setDealTime(issue.getDealTime());
		src.setFixDesc(issue.getFixDesc());
		src.setFixStatus(STATUS.DealStatus.getDesc());
		qyglService.saveOrUpdateCheckWrite(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复新契约不合格件成功！").toString(); 
	}
	
	@Log(message="重新打开了{0}新契约不合格件的信息。")
	@RequiresPermissions("CheckWrite:edit")
	@RequestMapping(value="/issue/reopenCheckWrite", method=RequestMethod.POST)
	public @ResponseBody String reopenCheckWrite(@Valid @ModelAttribute("preloadCheckWrite")CheckWrite issue) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		CheckWrite src = qyglService.getCheckWrite(issue.getId());
		src.setFixStatus(STATUS.ReopenStatus.getDesc());
		src.setReopenUser(shiroUser.getUser());
		src.setReopenReason(issue.getReopenReason());
		src.setReopenDate(new Date());
		qyglService.saveOrUpdateCheckWrite(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("重新打开新契约不合格件成功！").toString(); 
	}
	
	@Log(message="结案了{0}新契约不合格件的信息。")
	@RequiresPermissions("CheckWrite:edit")
	@RequestMapping(value="/issue/closeCheckWrite", method=RequestMethod.POST)
	public @ResponseBody String closeCheckWrite(@Valid @ModelAttribute("preloadCheckWrite")CheckWrite issue) {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		CheckWrite src = qyglService.getCheckWrite(issue.getId());
		src.setFixStatus(STATUS.CloseStatus.getDesc());
		qyglService.saveOrUpdateCheckWrite(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("结案新契约不合格件成功！").toString(); 
	}
	
	@RequiresPermissions("CheckWrite:view")
	@RequestMapping(value="/issue/listCheckWrite", method={RequestMethod.GET, RequestMethod.POST})
	public String listCheckWrite(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		String status = request.getParameter("status");
		log.debug("-------------- status: " + status);
		CheckWrite issue = new CheckWrite();
		if(status == null) {
			status = "待处理";
		}
		issue.setFixStatus(status);
		
		Specification<CheckWrite> specification = DynamicSpecifications.bySearchFilter(request, CheckWrite.class,
				new SearchFilter("fixStatus", Operator.LIKE, status),
				new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		
		List<CheckWrite> issues = qyglService.findByCheckWriteExample(specification, page);
		
		map.put("issue", issue);
		map.put("statusList", STATUS.values());
		map.put("page", page);
		map.put("issues", issues);
		return LIST_WRITE;
	}

	/*
	 * =====================================
	 * RECORD
	 * =====================================
	 */
	@RequiresPermissions("CheckRecord:view")
	@RequestMapping(value="/issue/viewCheckRecord/{id}", method=RequestMethod.GET)
	public String viewCheckRecord(@PathVariable Long id, Map<String, Object> map) {
		CheckRecord issue = qyglService.getCheckRecord(id);
		
		map.put("issue", issue);
		map.put("status", STATUS.ReopenStatus);
		return VIEW_RECORD;
	}
	
	@ModelAttribute("preloadCheckRecord")
	public CheckRecord preloadCheckRecord(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			CheckRecord issue = qyglService.getCheckRecord(id);
			return issue;
		}
		return null;
	}
	
	@RequiresPermissions("CheckRecord:edit")
	@RequestMapping(value="/issue/updateCheckRecord/{id}", method=RequestMethod.GET)
	public String preUpdateCheckRecord(@PathVariable Long id, Map<String, Object> map) {
		CheckRecord issue = qyglService.getCheckRecord(id);
		
		map.put("issue", issue);
		return UPDATE_RECORD;
	}
	
	@Log(message="回复了{0}新契约不合格件的信息。")
	@RequiresPermissions("CheckRecord:edit")
	@RequestMapping(value="/issue/updateCheckRecord", method=RequestMethod.POST)
	public @ResponseBody String updateCheckRecord(@Valid @ModelAttribute("preloadCheckRecord")CheckRecord issue) {
		CheckRecord src = qyglService.getCheckRecord(issue.getId());
		src.setDealMan(issue.getDealMan());
		src.setDealTime(issue.getDealTime());
		src.setFixDesc(issue.getFixDesc());
		src.setFixStatus(STATUS.DealStatus.getDesc());
		qyglService.saveOrUpdateCheckRecord(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复新契约不合格件成功！").toString(); 
	}
	
	@Log(message="重新打开了{0}新契约不合格件的信息。")
	@RequiresPermissions("CheckRecord:edit")
	@RequestMapping(value="/issue/reopenCheckRecord", method=RequestMethod.POST)
	public @ResponseBody String reopenCheckRecord(@Valid @ModelAttribute("preloadCheckRecord")CheckRecord issue) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		CheckRecord src = qyglService.getCheckRecord(issue.getId());
		src.setFixStatus(STATUS.ReopenStatus.getDesc());
		src.setReopenUser(shiroUser.getUser());
		src.setReopenReason(issue.getReopenReason());
		src.setReopenDate(new Date());
		qyglService.saveOrUpdateCheckRecord(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("重新打开新契约不合格件成功！").toString(); 
	}
	
	@Log(message="结案了{0}新契约不合格件的信息。")
	@RequiresPermissions("CheckRecord:edit")
	@RequestMapping(value="/issue/closeCheckRecord", method=RequestMethod.POST)
	public @ResponseBody String closeCheckRecord(@Valid @ModelAttribute("preloadCheckRecord")CheckRecord issue) {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		CheckRecord src = qyglService.getCheckRecord(issue.getId());
		src.setFixStatus(STATUS.CloseStatus.getDesc());
		qyglService.saveOrUpdateCheckRecord(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("结案新契约不合格件成功！").toString(); 
	}
	
	@RequiresPermissions("CheckRecord:view")
	@RequestMapping(value="/issue/listCheckRecord", method={RequestMethod.GET, RequestMethod.POST})
	public String listCheckRecord(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		String status = request.getParameter("status");
		log.debug("-------------- status: " + status);
		CheckRecord issue = new CheckRecord();
		if(status == null) {
			status = "待处理";
		}
		issue.setFixStatus(status);
		
		Specification<CheckRecord> specification = DynamicSpecifications.bySearchFilter(request, CheckRecord.class,
				new SearchFilter("fixStatus", Operator.LIKE, status),
				new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		
		List<CheckRecord> issues = qyglService.findByCheckRecordExample(specification, page);
		
		map.put("issue", issue);
		map.put("statusList", STATUS.values());
		map.put("page", page);
		map.put("issues", issues);
		return LIST_RECORD;
	}
	
	// 使用初始化绑定器, 将参数自动转化为日期类型,即所有日期类型的数据都能自动转化为yyyy-MM-dd格式的Date类型
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
