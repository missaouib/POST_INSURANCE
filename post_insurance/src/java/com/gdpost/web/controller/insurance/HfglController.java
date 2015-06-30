/**
 * 
 * ==========================================================
 * 回访管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.entity.main.CallFail;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.insurance.HfglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.StatusDefine.XQ_STATUS;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/hfgl")
public class HfglController {
	//private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private HfglService hfglService;

	private static final String VIEW = "insurance/hfgl/wtj/view";
	private static final String UPDATE = "insurance/hfgl/wtj/update";
	private static final String LIST = "insurance/hfgl/wtj/list";
	
	@RequiresPermissions("Callfail:view")
	@RequestMapping(value="/issue/view/{id}", method=RequestMethod.GET)
	public String view(@PathVariable Long id, Map<String, Object> map) {
		CallFail issue = hfglService.get(id);
		
		map.put("issue", issue);
		//map.put("status", XQ_STATUS.ReopenStatus);
		return VIEW;
	}
	
	@RequiresPermissions("Callfail:edit")
	@RequestMapping(value="/issue/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		CallFail issue = hfglService.get(id);
		
		map.put("issue", issue);
		return UPDATE;
	}
	
	@Log(message="回复了{0}新契约不合格件的信息。")
	@RequiresPermissions("Callfail:edit")
	@RequestMapping(value="/issue/update", method=RequestMethod.POST)
	public @ResponseBody String update(CallFail issue) {
		CallFail src = hfglService.get(issue.getId());
		src.setDealMan(issue.getDealMan());
		src.setDealTime(issue.getDealTime());
		src.setFixDesc(issue.getFixDesc());
		src.setFixStatus(XQ_STATUS.DealStatus.getDesc());
		hfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复新契约不合格件成功！").toString(); 
	}
	
	@Log(message="结案了{0}新契约不合格件的信息。")
	@RequiresPermissions("Callfail:edit")
	@RequestMapping(value="/issue/close", method=RequestMethod.POST)
	public @ResponseBody String close(@Valid @ModelAttribute("preload")CallFail issue) {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		CallFail src = hfglService.get(issue.getId());
		src.setFixStatus(XQ_STATUS.CloseStatus.getDesc());
		hfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("结案新契约不合格件成功！").toString(); 
	}
	
	@RequiresPermissions("Callfail:view")
	@RequestMapping(value="/issue/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		String status = request.getParameter("feeStatus");
		LOG.debug("-------------- status: " + status);
		CallFail issue = new CallFail();
		if(status == null) {
			status = XQ_STATUS.NewStatus.getDesc();
		} else if(status.trim().length()>0) {
			issue.setFeeStatus(XQ_STATUS.valueOf(status).getDesc());
		}
		issue.setFeeStatus(status);
		
		Specification<CallFail> specification = DynamicSpecifications.bySearchFilter(request, CallFail.class,
				new SearchFilter("feeStatus", Operator.LIKE, status),
				new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		
		List<CallFail> issues = hfglService.findByExample(specification, page);
		
		map.put("issue", issue);
		map.put("statusList", XQ_STATUS.values());
		map.put("page", page);
		map.put("issues", issues);
		return LIST;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
