/**
 * 
 */
package com.gdpost.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.StringUtil;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.Role;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.entity.main.UserRole;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.OrganizationService;
import com.gdpost.web.service.RoleService;
import com.gdpost.web.service.UserRoleService;
import com.gdpost.web.service.UserService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;

@Controller
@RequestMapping("/management/security/user")
public class UserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private OrganizationService organizationService;
	
	private static final String CREATE = "management/security/user/create";
	private static final String UPDATE = "management/security/user/update";
	private static final String LIST = "management/security/user/list";
	private static final String LOOK_UP_ROLE = "management/security/user/assign_user_role";
	private static final String LOOK_USER_ROLE = "management/security/user/delete_user_role";
	private static final String LOOK_ORG = "management/security/user/lookup_org";
	private static final String LOOK_USER = "management/security/user/lookup_user";
	private static final String RESET_PASSWORD = "management/security/user/reset";
	
	@RequiresPermissions("User:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}
	
	@Log(message="添加了{0}用户。", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("User:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid User user) {	
		user.setCreateTime(new Date());
		try {
			String p = "^(?=.*\\d)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[\\da-zA-Z~_\\-!@#$%^&*]{8,16}$";
			String pwd = user.getPlainPassword();
			if(!pwd.matches(p)) {
				return AjaxObject.newError("密码强度不够，须包含大小写字母和数字").setCallbackType("").toString();
			}
			userService.saveOrUpdate(user);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加用户失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername()}));
		return AjaxObject.newOk("添加用户成功！").toString();
	}
	
	@ModelAttribute("preloadUser")
	public User preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			User user = userService.get(id);
			if(user != null) {
				user.setOrganization(null);
			}
			return user;
		}
		return null;
	}
	
	@RequiresPermissions("User:edit:User拥有的资源")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		User user = userService.get(id);
		
		map.put("user", user);
		return UPDATE;
	}
	
	@Log(message="修改了{0}用户的信息。", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("User:edit:User拥有的资源")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadUser")User user) {
		userService.saveOrUpdate(user);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername()}));
		return	AjaxObject.newOk("修改用户成功！").toString(); 
	}
	
	@Log(message="删除了{0}用户。", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("User:delete:User拥有的资源")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		User user = null;
		try {
			user = userService.get(id);
			userService.delete(user.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除用户失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername()}));
		return AjaxObject.newOk("删除用户成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}用户。", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("User:delete:User拥有的资源")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		String[] usernames = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				User user = userService.get(ids[i]);
				userService.delete(user.getId());
				
				usernames[i] = user.getUsername();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除用户失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(usernames)}));
		return AjaxObject.newOk("删除用户成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("User:view:User拥有的资源")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<User> specification = DynamicSpecifications.bySearchFilter(request, User.class);
		List<User> users = userService.findByExample(specification, page);

		map.put("page", page);
		map.put("users", users);
		return LIST;
	}
	
	@RequiresPermissions("User:reset:User拥有的资源")
	@RequestMapping(value="/resetPassword/{userId}", method={RequestMethod.POST, RequestMethod.GET})
	public String preReset(ServletRequest request, @PathVariable Long userId) {
		request.setAttribute("userId", userId);
		return RESET_PASSWORD;
	}
	
	@Log(message="{0}用户{1}。", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("User:reset:User拥有的资源")
	@RequestMapping(value="/reset/{type}/{userId}", method=RequestMethod.POST)
	public @ResponseBody String reset(ServletRequest request, @PathVariable String type, @PathVariable Long userId) {
		User user = userService.get(userId);
		AjaxObject ajaxObject = new AjaxObject();
		ajaxObject.setCallbackType("");
		
		if (type.equals("password")) {
			String plainPassword = request.getParameter("plainPassword");
			
			String p = "^(?=.*\\d)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[\\da-zA-Z~_\\-!@#$%^&*]{8,16}$";
			if(!plainPassword.matches(p)) {
				return AjaxObject.newError("密码强度不够，须包含大小写字母和数字").setCallbackType("").toString();
			}
			
			//LOG.info("-----------password:" + plainPassword);
			userService.resetPwd(user, plainPassword);
			//userService.resetPwd(user, "123456");
			ajaxObject.setMessage("重置了密码");
			return AjaxObject.newOk("重置密码成功！").toString();
		} else if (type.equals("status")) {
			if (user.getStatus().equals("enabled")) {
				user.setStatus("disabled");
			} else {
				user.setStatus("enabled");
			}
			
			ajaxObject.setMessage("更新状态成功，当前为" + (user.getStatus().equals(User.STATUS_ENABLED)?"可用":"不可用"));
			
			userService.saveOrUpdate(user);
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername(), ajaxObject.getMessage()}));
		return ajaxObject.toString();
	}
	
	@Log(message="向{0}用户分配了{1}的角色。", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("User:assign")
	@RequestMapping(value="/create/userRole", method={RequestMethod.POST})
	public @ResponseBody void assignRole(UserRole userRole) {
		userRoleService.saveOrUpdate(userRole);
		
		User user = userService.get(userRole.getUser().getId());
		Role role = roleService.get(userRole.getRole().getId());
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername(), role.getName()}));
	}
	
	@RequiresPermissions("User:assign")
	@RequestMapping(value="/lookup2create/userRole/{userId}", method={RequestMethod.GET, RequestMethod.POST})
	public String listUnassignRole(Map<String, Object> map, @PathVariable Long userId) {
		Page page = new Page();
		page.setNumPerPage(Integer.MAX_VALUE);
		
		List<UserRole> userRoles = userRoleService.findByUserId(userId);
		List<Role> roles = roleService.findAll(page);
		
		List<Role> rentList = new ArrayList<Role>();
		// 删除已分配roles
		for (Role role : roles) {
			boolean isHas = false;
			for (UserRole or : userRoles) {
				if (or.getRole().getId().equals(role.getId())) {
					isHas = true;
					break;
				} 
			}
			if (isHas == false) {
				rentList.add(role);
			}
		}
		
		map.put("userRoles", userRoles);
		map.put("roles", rentList);
		
		map.put("userId", userId);
		return LOOK_UP_ROLE;
	}
	
	@RequiresPermissions("User:assign")
	@RequestMapping(value="/lookup2delete/userRole/{userId}", method={RequestMethod.GET, RequestMethod.POST})
	public String listUserRole(Map<String, Object> map, @PathVariable Long userId) {
		List<UserRole> userRoles = userRoleService.findByUserId(userId);
		map.put("userRoles", userRoles);
		return LOOK_USER_ROLE;
	}
	
	@Log(message="撤销了{0}用户的{1}角色。", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("User:assign")
	@RequestMapping(value="/delete/userRole/{userRoleId}", method={RequestMethod.POST})
	public @ResponseBody void deleteUserRole(@PathVariable Long userRoleId) {
		UserRole userRole = userRoleService.get(userRoleId);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{userRole.getUser().getUsername(), userRole.getRole().getName()}));
		
		userRoleService.delete(userRoleId);
	}
	
	@RequiresUser
	@RequestMapping(value="/lookup2org", method={RequestMethod.GET})
	public String lookupOrg(ServletRequest request, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = userService.get(shiroUser.getId());
		String flag = request.getParameter("flag");
		
		if(flag != null && flag.equals("prov")) {
			user = userService.getByUsername("admin");
		}
		
		Organization org = organizationService.getTree(user);
		LOG.debug("------------ LOOKUP FOR ORG: " + org);
		map.put("org", org);
		return LOOK_ORG;
	}
	
	@RequiresPermissions("MemberMessage:edit")
	@RequestMapping(value="/lookup2user", method=RequestMethod.POST)
	public @ResponseBody String lookupuser(ServletRequest request, User user) {
		List<User> list = null;
		//LOG.info("-----------------lookupuser:" + request.getParameter("search_LIKE_username"));
		String keyword = request.getParameter("search_LIKE_username");
		if(keyword.length()>=3) {
			Specification<User> specification = DynamicSpecifications.bySearchFilter(request, User.class);
			Page page = new Page();
			page.setNumPerPage(15);
			page.setPageNum(1);
			list = userService.findByExample(specification, page);
			PropertyFilter filter = new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
			        if("id".equals(name)) {
			            return true;
			        }
			        if("username".equals(name)) {
			            return true;
			        }
			        return false;
			    }
			};
			SerializeWriter out = new SerializeWriter();
			JSONSerializer serializer = new JSONSerializer(out);
			serializer.getPropertyFilters().add(filter);
			serializer.write(list);
			return StringUtil.toChartset(out.toString(), "UTF-8", "ISO8859-1");
		}
		return "";
	}
	
	@RequiresPermissions("MemberMessage:edit")
	@RequestMapping(value="/preLookup", method=RequestMethod.GET)
	public String preLookupuser(ServletRequest request, Map<String, Object> map) {
		Page page = new Page();
		page.setNumPerPage(5);
		map.put("page", page);
		Organization org = organizationService.getTree();
		
		map.put("org", org);
		return LOOK_USER;
	}
	
	@RequiresPermissions("MemberMessage:edit")
	@RequestMapping(value="/lookupUser", method=RequestMethod.POST)
	public String lookupUser(ServletRequest request, Map<String, Object> map, Organization org, Page page) {
		//LOG.info(org.getId() + "-----------------ORG:" + org.toString());
		List<User> list = new ArrayList<User>();
		if(org.getId() != null) {
			list = userService.findByOrganizationId(org.getId());
		} else {
			Specification<User> specification = DynamicSpecifications.bySearchFilter(request, User.class);
			list = userService.findByExample(specification, page);
		}
		map.put("userList", list);
		return LOOK_USER;
	}
}
