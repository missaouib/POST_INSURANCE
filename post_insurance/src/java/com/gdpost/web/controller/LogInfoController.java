/**
 * 
 */
package com.gdpost.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.web.entity.main.LogInfo;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.LogInfoService;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/management/security/logInfo")
public class LogInfoController {

	@Autowired
	private LogInfoService logInfoService;
	
	private static final String LIST = "management/security/logInfo/list";
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, 
				new CustomDateEditor(df, true));
	}
	
	@Log(message="删除了{0}条日志。", level=LogLevel.WARN, module=LogModule.QTCZ)
	@RequiresPermissions("LogInfo:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		AjaxObject ajaxObject = new AjaxObject("删除日志成功！");
		for (Long id : ids) {
			logInfoService.delete(id);
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{ids.length}));
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}

	@RequiresPermissions("LogInfo:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map, LogInfo loginfo) {
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		String module = request.getParameter("module");
		if(module != null && module.trim().length()>0) {
			csf.add(new SearchFilter("module", Operator.EQ, module));
			request.setAttribute("module", module);
		}
		
		Specification<LogInfo> specification = DynamicSpecifications.bySearchFilter(request, LogInfo.class, csf);
		page.setOrderField("id");
		page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
		List<LogInfo> logInfos = logInfoService.findByExample(specification, page);
		
		map.put("logInfo", loginfo);
		
		map.put("page", page);
		map.put("logInfos", logInfos);
		map.put("logLevels", LogLevel.values());
		map.put("modules", LogModule.values());

		return LIST;
	}
}
