/**
 * 
 */
package com.sendtend.web.controller.member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
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

import com.sendtend.utils.DataControlFilter;
import com.sendtend.utils.SecurityUtils;
import com.sendtend.web.entity.main.User;
import com.sendtend.web.entity.member.TblMember;
import com.sendtend.web.entity.member.TblMemberRole;
import com.sendtend.web.entity.member.TblMemberUser;
import com.sendtend.web.entity.member.TblMemberUserRole;
import com.sendtend.web.exception.ExistedException;
import com.sendtend.web.exception.ServiceException;
import com.sendtend.web.log.Log;
import com.sendtend.web.log.LogMessageObject;
import com.sendtend.web.log.impl.LogUitls;
import com.sendtend.web.service.member.MRoleService;
import com.sendtend.web.service.member.MUserRoleService;
import com.sendtend.web.service.member.MUserService;
import com.sendtend.web.service.member.MemberService;
import com.sendtend.web.shiro.ShiroUser;
import com.sendtend.web.util.dwz.AjaxObject;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.persistence.DynamicSpecifications;
import com.sendtend.web.util.persistence.SearchFilter;
import com.sendtend.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/members/user")
public class MUserController {
	private static final Logger LOG = LoggerFactory.getLogger(MUserController.class);

	@Autowired
	private MUserService userService;
	
	@Autowired
	MUserRoleService userRoleService;
	
	@Autowired
	private MRoleService roleService;
	
	@Autowired
	private MemberService memberService;
	
	private static final String CREATE = "members/user/create";
	private static final String WEBCREATE = "members/user/webcreate";
	private static final String UPDATE = "members/user/update";
	private static final String WEBUPDATE = "members/user/webupdate";
	private static final String PRIVATEUPDATE = "members/user/privateupdate";
	private static final String LIST = "members/user/list";
	private static final String INDEX = "members/user/index";
	private static final String WEBLIST = "members/user/weblist";
	private static final String LOOK_UP_ROLE = "members/user/assign_user_role";
	private static final String LOOK_USER_ROLE = "members/user/delete_user_role";
	private static final String LOOK_ORG = "members/user/lookup_org";
	private static final String RESET_PASSWORD = "members/user/reset";
	
	private static final String TREE_LIST = "members/user/tree_list";
	private static final String ORGTREE = "members/user/tree";
	private static final String ORGUSER = "members/user/userList";
	
	@RequiresPermissions("TblMemberUser:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(ServletRequest request) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		TblMemberUser user = shiroUser.getMemberUser();
		if(user == null) {
			request.setAttribute("isAdmin", "1");
		} else {
			request.setAttribute("isAdmin", "0");
			request.setAttribute("member", user.getTblMember());
		}
		String orgId = request.getParameter("orgId");
		if(orgId != null && !orgId.equals("1")) {
			TblMember member = memberService.get(new Long(orgId));
			request.setAttribute("member", member);
		}
		return CREATE;
	}
	
	@RequiresPermissions("TblMemberUser:save")
	@RequestMapping(value="/webCreate", method=RequestMethod.GET)
	public String preWebCreate(ServletRequest request) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		TblMemberUser user = shiroUser.getMemberUser();
		if(user == null) {
			request.setAttribute("isAdmin", "1");
		} else {
			request.setAttribute("isAdmin", "0");
			request.setAttribute("member", user.getTblMember());
		}
		String orgId = request.getParameter("orgId");
		if(orgId != null && !orgId.equals("1")) {
			TblMember member = memberService.get(new Long(orgId));
			request.setAttribute("member", member);
		}
		return WEBCREATE;
	}
	
	@Log(message="添加了{0}用户。")
	@RequiresPermissions("TblMemberUser:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(ServletRequest request, @Valid TblMemberUser user) {
		String flag = request.getParameter("flag");
		user.setCreateDate(new Date());
		TblMemberUser tmu = SecurityUtils.getLoginTblMemberUser();
		Long userId = new Long(-1);
		if(tmu != null) {
			userId = tmu.getId();
			user.setIsAdmin(0);
		} else {
			User auser = (User) SecurityUtils.getLoginUser();
			userId = auser.getId();
			user.setIsAdmin(1);
		}
		user.setCreatedBy(userId);
		try {
			/*
			 * native sql aes_encrypt method
			 */
			//userService.add(user, MySQLAESKey.AESKey);
			userService.saveOrUpdate(user);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加用户失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUserName()}));
		if(flag != null && flag.equals("web")) {
			return AjaxObject.newOk("添加用户成功！").setCallbackType("").toString();
		}
		return AjaxObject.newOk("添加用户成功！").toString();
	}
	
	@ModelAttribute("preloadUser")
	public TblMemberUser preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			TblMemberUser user = userService.get(id);
			user.setTblMember(null);
			return user;
		}
		return null;
	}
	
	@RequiresPermissions("TblMemberUser:edit:User拥有的资源")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		TblMemberUser user = userService.get(id);
		
		map.put("user", user);
		return UPDATE;
	}
	
	@RequiresPermissions("TblMemberUser:edit:User拥有的资源")
	@RequestMapping(value="/webUpdate/{id}", method=RequestMethod.GET)
	public String preWebUpdate(@PathVariable Long id, Map<String, Object> map) {
		TblMemberUser user = userService.get(id);
		
		map.put("user", user);
		return WEBUPDATE;
	}
	
	@RequiresUser
	@RequestMapping(value="/privateUpdate/{id}", method=RequestMethod.GET)
	public String prePrivateUpdate(@PathVariable Long id, Map<String, Object> map) {
		TblMemberUser user = userService.get(id);
		
		map.put("user", user);
		return PRIVATEUPDATE;
	}
	
	@Log(message="修改了{0}用户的信息。")
	@RequiresPermissions("TblMemberUser:edit:User拥有的资源")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadUser")TblMemberUser user) {
		userService.saveOrUpdate(user);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUserName()}));
		return	AjaxObject.newOk("修改用户成功！").toString(); 
	}
	
	@Log(message="修改了{0}用户的信息。")
	@RequiresUser
	@RequestMapping(value="/privateUpdate", method=RequestMethod.POST)
	public @ResponseBody String privateUpdate(@Valid @ModelAttribute("preloadUser")TblMemberUser user, String plainPassword, 
			String newPassword, String rPassword) {
		if (newPassword != null && newPassword.trim().length()>0 &&  newPassword.equals(rPassword)) {
			LOG.debug(plainPassword + "-----web private info 修改密码----" + newPassword);
			user.setPlainPassword(plainPassword);
			try {
				userService.updatePwd(user, newPassword);
			} catch (ServiceException e) {
				e.printStackTrace();
				LogUitls.putArgs(LogMessageObject.newIgnore());//忽略日志
				return AjaxObject.newError(e.getMessage()).setCallbackType("").toString();
			}
			LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUserName()}));
			return AjaxObject.newOk("修改用户成功！").toString();
		}
		userService.saveOrUpdate(user);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUserName()}));
		return	AjaxObject.newOk("修改用户成功！").toString(); 
	}
	
	@Log(message="删除了{0}用户。")
	@RequiresPermissions("TblMemberUser:delete:User拥有的资源")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		TblMemberUser user = null;
		try {
			user = userService.get(id);
			userService.delete(user.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除用户失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUserName()}));
		return AjaxObject.newOk("删除用户成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}用户。")
	@RequiresPermissions("TblMemberUser:delete:User拥有的资源")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		String[] usernames = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				TblMemberUser user = userService.get(ids[i]);
				userService.delete(user.getId());
				
				usernames[i] = user.getUserName();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除用户失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(usernames)}));
		return AjaxObject.newOk("删除用户成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}用户。")
	@RequiresPermissions("TblMemberUser:delete:User拥有的资源")
	@RequestMapping(value="/webDelete/{id}", method=RequestMethod.POST)
	public String webDeleteMany(HttpServletResponse response, @PathVariable Long id) {
		String usernames = null;
		try {
			TblMemberUser user = userService.get(id);
			userService.delete(user.getId());
			
			usernames = user.getUserName();
		} catch (ServiceException e) {
			return AjaxObject.newError("删除用户失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{usernames}));
		//return AjaxObject.newOk("删除用户成功！").setCallbackType("").setRel("container").toString();
		try {
			response.sendRedirect("/members/user/weblist");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequiresPermissions("TblMemberUser:view:User拥有的资源")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		List<TblMemberUser> users = null;
		String userType = SecurityUtils.getShiroUser().getUserType();
		if(userType.equals("admin")) {
			Specification<TblMemberUser> specification = DynamicSpecifications.bySearchFilter(request, TblMemberUser.class);
			users = userService.findByExample(specification, page);
		} else {
			TblMemberUser user = userService.get(SecurityUtils.getShiroUser().getId());
			String userName = request.getParameter("search_LIKE_userName");
			if(userName == null) {
				userName = "";
			}
			String realName = request.getParameter("search_LIKE_realName");
			if(realName == null) {
				realName = "";
			}
			users = userService.findByTblMemberUserAndTblMemberOrTblMemberParent(userName, realName, user.getTblMember(), page);
		}
		users = DataControlFilter.filterBeanList(users, "MemberUser");
		map.put("page", page);
		map.put("users", users);
		return LIST;
	}
	
	@RequestMapping(value="/index", method={RequestMethod.GET, RequestMethod.POST})
	public String index(ServletRequest request, Page page, Map<String, Object> map) {
		if(SecurityUtils.getShiroUser().getUserType().equals("admin")) {
			return INDEX;
		}
		TblMemberUser user = userService.get(SecurityUtils.getShiroUser().getId());
		//LOG.debug("-----------login user id:" + SecurityUtils.getShiroUser().getId());
		//LOG.debug(user.toString());
		map.put("memberid", user.getTblMember().getId());
		map.put("userId", user.getId());
		return INDEX;
	}
	
	@RequiresPermissions("TblMemberUser:view:User拥有的资源")
	@RequestMapping(value="/weblist", method={RequestMethod.GET, RequestMethod.POST})
	public String weblist(ServletRequest request, Page page, Map<String, Object> map) {
		List<TblMemberUser> users = null;
		String userType = SecurityUtils.getShiroUser().getUserType();
		if(userType.equals("admin")) {
			Specification<TblMemberUser> specification = DynamicSpecifications.bySearchFilter(request, TblMemberUser.class);
			users = userService.findByExample(specification, page);
		} else {
			TblMemberUser user = userService.get(SecurityUtils.getShiroUser().getId());
			String userName = request.getParameter("search_LIKE_userName");
			if(userName == null) {
				userName = "";
			}
			String realName = request.getParameter("search_LIKE_realName");
			if(realName == null) {
				realName = "";
			}
			users = userService.findByTblMemberUserAndTblMemberOrTblMemberParent(userName, realName, user.getTblMember(), page);
		}
		users = DataControlFilter.filterBeanList(users, "MemberUser");
		map.put("page", page);
		map.put("users", users);
		return WEBLIST;
	}
	
	@RequiresPermissions("TblMemberUser:reset")
	@RequestMapping(value="/resetPassword/{userId}", method={RequestMethod.POST, RequestMethod.GET})
	public String preReset(ServletRequest request, @PathVariable Long userId) {
		request.setAttribute("userId", userId);
		return RESET_PASSWORD;
	}
	
	@Log(message="{0}用户{1}")
	@RequiresPermissions("TblMemberUser:reset")
	@RequestMapping(value="/reset/{type}/{userId}", method=RequestMethod.POST)
	public @ResponseBody String reset(ServletRequest request, @PathVariable String type, @PathVariable Long userId) {
		TblMemberUser user = userService.get(userId);
		AjaxObject ajaxObject = new AjaxObject();
		ajaxObject.setCallbackType("");
		
		if (type.equals("password")) {
			String plainPassword = request.getParameter("plainPassword");
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
			
			ajaxObject.setMessage("更新状态成功，当前为" + (user.getStatus().equals(TblMemberUser.STATUS_ENABLED)?"可用":"不可用"));
			
			userService.saveOrUpdate(user);
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUserName(), ajaxObject.getMessage()}));
		return AjaxObject.newOk(ajaxObject.getMessage()).setCallbackType("").toString();
	}
	
	@Log(message="向{0}用户分配了{1}的角色。")
	@RequiresPermissions("TblMemberUser:assign")
	@RequestMapping(value="/create/userRole", method={RequestMethod.POST})
	public @ResponseBody void assignRole(TblMemberUserRole userRole) {
		userRoleService.saveOrUpdate(userRole);
		
		TblMemberUser user = userService.get(userRole.getTblMemberUser().getId());
		TblMemberRole role = roleService.get(userRole.getTblMemberRole().getId());
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUserName(), role.getName()}));
	}
	
	@RequiresPermissions("TblMemberUser:assign")
	@RequestMapping(value="/lookup2create/userRole/{userId}", method={RequestMethod.GET, RequestMethod.POST})
	public String listUnassignRole(ServletRequest request, Map<String, Object> map, @PathVariable Long userId) {
		Page page = new Page();
		page.setNumPerPage(Integer.MAX_VALUE);
		
		List<TblMemberUserRole> userRoles = userRoleService.findByUserId(userId);
		//List<TblMemberRole> roles = roleService.findAll(page);
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();
		Specification<TblMemberRole> specification = DynamicSpecifications.bySearchFilter(request, TblMemberRole.class,
				new SearchFilter("memberId", Operator.EQ, -1));
		if(user != null) {
			specification = DynamicSpecifications.bySearchFilter(request, TblMemberRole.class,
					new SearchFilter("memberId", Operator.EQ, user.getTblMember().getId()));
		}
		
		List<TblMemberRole> roles = roleService.findByExample(specification, page);
		//LOG.debug("---------1111----------" + roles);
		List<TblMemberRole> rentList = new ArrayList<TblMemberRole>();
		// 删除已分配roles
		for (TblMemberRole role : roles) {
			boolean isHas = false;
			for (TblMemberUserRole or : userRoles) {
				if (or.getTblMemberRole().getId().equals(role.getId())) {
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
	
	@RequiresPermissions("TblMemberUser:assign")
	@RequestMapping(value="/lookup2delete/userRole/{userId}", method={RequestMethod.GET, RequestMethod.POST})
	public String listUserRole(Map<String, Object> map, @PathVariable Long userId) {
		List<TblMemberUserRole> userRoles = userRoleService.findByUserId(userId);
		map.put("userRoles", userRoles);
		return LOOK_USER_ROLE;
	}
	
	@Log(message="撤销了{0}用户的{1}角色。")
	@RequiresPermissions("TblMemberUser:assign")
	@RequestMapping(value="/delete/userRole/{userRoleId}", method={RequestMethod.POST})
	public @ResponseBody String deleteUserRole(@PathVariable Long userRoleId) {
		TblMemberUserRole userRole = userRoleService.get(userRoleId);
		TblMemberUser user = userService.get(SecurityUtils.getShiroUser().getId());
		if(user != null && user.getTblMemberUserRoles().contains(userRole)) {
			return "不能删除自身角色";
		}
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{userRole.getTblMemberUser().getUserName(), userRole.getTblMemberRole().getName()}));
		
		userRoleService.delete(userRoleId);
		
		return "";
	}
	
	@RequiresPermissions(value={"TblMemberUser:edit", "TblMemberUser:save"}, logical=Logical.OR)
	@RequestMapping(value="/lookup2org", method={RequestMethod.GET})
	public String lookup(Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		TblMemberUser user = shiroUser.getMemberUser();
		TblMember member = null;
		if(user != null) {
			member = memberService.getTree(user.getTblMember().getId());
		} else {
			member = memberService.getTree();
		}
		
		map.put("member", member);
		return LOOK_ORG;
	}

	@RequiresPermissions(value={"TblMemberUser:edit", "TblMemberUser:save"}, logical=Logical.OR)
	@RequestMapping(value="/tree_list", method={RequestMethod.GET, RequestMethod.POST})
	public String tree_list(Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		if(shiroUser.getUserType().equals("admin")) {
			map.put("memberId", 1);
		} else {
			map.put("memberId", shiroUser.getMemberUser().getTblMember().getId());
		}
		
		return TREE_LIST;
	}
	
	@RequiresPermissions(value={"TblMemberUser:edit", "TblMemberUser:save"}, logical=Logical.OR)
	@RequestMapping(value="/orgTree", method={RequestMethod.GET, RequestMethod.POST})
	public String tree(Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		TblMemberUser user = shiroUser.getMemberUser();
		TblMember member = null;
		if(user != null) {
			member = memberService.getTree(user.getTblMember().getId());
		} else {
			member = memberService.getTree();
		}
		map.put("member", member);
		return ORGTREE;
	}

	@RequiresPermissions(value={"TblMemberUser:edit", "TblMemberUser:save"}, logical=Logical.OR)
	@RequestMapping(value="/userList/{orgId}", method={RequestMethod.GET, RequestMethod.POST})
	public String userList(ServletRequest request, Page page, @PathVariable Long orgId, Map<String, Object> map) {
		Specification<TblMemberUser> specification = DynamicSpecifications.bySearchFilter(request, TblMemberUser.class,
				new SearchFilter("tblMember.id", Operator.EQ, orgId));
		
		List<TblMemberUser> users = userService.findByExample(specification, page);
		request.setAttribute("orgId", orgId);
		map.put("page", page);
		map.put("users", users);
		return ORGUSER;
	}
}
