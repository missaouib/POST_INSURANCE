/**
 * 
 * ==========================================================
 * 续期管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.insurance.XqglService;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;

@Controller
@RequestMapping("/Xqgl")
public class XqglController {
	//private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private XqglService xqglService;

	private static final String CREATE = "insurance/xqgl/wtj/create";
	private static final String UPDATE = "insurance/xqgl/wtj/update";
	private static final String LIST = "insurance/xqgl/wtj/list";
	
	@RequiresPermissions("Policy:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}
	
	@Log(message="添加了{0}保单。")
	@RequiresPermissions("Policy:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid Policy user) {	
		try {
			xqglService.saveOrUpdate(user);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加保单失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getPolicyNo()}));
		return AjaxObject.newOk("添加保单成功！").toString();
	}
	
	@ModelAttribute("preloadUser")
	public Policy preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Policy user = xqglService.get(id);
			if(user != null) {
				user.setOrganization(null);
			}
			return user;
		}
		return null;
	}
	
	@RequiresPermissions("Policy:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Policy user = xqglService.get(id);
		
		map.put("user", user);
		return UPDATE;
	}
	
	@Log(message="修改了{0}保单的信息。")
	@RequiresPermissions("Policy:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadUser")Policy user) {
		xqglService.saveOrUpdate(user);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getPolicyNo()}));
		return	AjaxObject.newOk("修改保单成功！").toString(); 
	}
	
	@Log(message="删除了{0}保单。")
	@RequiresPermissions("Policy:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		Policy user = null;
		try {
			user = xqglService.get(id);
			xqglService.delete(user.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除保单失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getPolicyNo()}));
		return AjaxObject.newOk("删除保单成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}保单。")
	@RequiresPermissions("Policy:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		String[] policys = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				Policy user = xqglService.get(ids[i]);
				xqglService.delete(user.getId());
				
				policys[i] = user.getPolicyNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除保单失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("删除保单成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("Policy:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<Policy> specification = DynamicSpecifications.bySearchFilter(request, Policy.class);
		List<Policy> users = xqglService.findByExample(specification, page);

		map.put("page", page);
		map.put("users", users);
		return LIST;
	}
}
