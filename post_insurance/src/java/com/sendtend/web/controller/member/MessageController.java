/**
 * 
 */
package com.sendtend.web.controller.member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sendtend.utils.SecurityUtils;
import com.sendtend.web.entity.main.Organization;
import com.sendtend.web.entity.main.User;
import com.sendtend.web.entity.member.TblMemberMessage;
import com.sendtend.web.entity.member.TblMemberMessageAssign;
import com.sendtend.web.entity.member.TblMemberMessageCategory;
import com.sendtend.web.exception.ServiceException;
import com.sendtend.web.log.Log;
import com.sendtend.web.log.LogMessageObject;
import com.sendtend.web.log.impl.LogUitls;
import com.sendtend.web.service.OrganizationService;
import com.sendtend.web.service.UserService;
import com.sendtend.web.service.member.MessageCategoryService;
import com.sendtend.web.service.member.MessageService;
import com.sendtend.web.shiro.ShiroUser;
import com.sendtend.web.util.dwz.AjaxObject;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.persistence.DynamicSpecifications;
import com.sendtend.web.util.persistence.SearchFilter;
import com.sendtend.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/members/message")
public class MessageController {
	private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MessageCategoryService messageCategoryService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrganizationService organizationService;
	
	private static final String CREATE = "members/message/create";
	private static final String WEBCREATE = "members/message/webCreate";
	private static final String UPDATE = "members/message/update";
	private static final String REPLY = "members/message/reply";
	private static final String VIEW = "members/message/view";
	private static final String LIST = "members/message/list";
	//private static final String REASSIGN = "members/message/reassign";
	private static final String REASSIGN = "members/message/tree_list";
	private static final String ASSIGNLIST = "members/message/assignlist";
	private static final String ASSIGNTREE = "members/message/tree";
	private static final String ASSIGNUSER = "members/message/userList";
	
	private static final int MSG_STATUS_NEW = 0;
	private static final int MSG_STATUS_SUBMIT = 1;
	//private static final int MSG_STATUS_READ = 2;
	private static final int MSG_STATUS_REPLY = 2;
	//private static final int MSG_STATUS_NO_FIX = 3;
	private static final int MSG_STATUS_FIXED = 3;
	
	
	@RequiresPermissions("MemberMessage:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		TblMemberMessage message = new TblMemberMessage();
		List<TblMemberMessageCategory> mc = messageCategoryService.findAllWithCache();
		map.put("categoryList", mc);
		map.put("message", message);
		return CREATE;
	}
	
	@RequiresPermissions("MemberMessage:save")
	@RequestMapping(value="/toMsgCreate", method=RequestMethod.GET)
	public String preMsgCreate(ServletRequest request, Map<String, Object> map) {
		String code = request.getParameter("code");
		if(code != null && code.equals("0")) {
			request.setAttribute("msg", "留言失败，请重试或者联系管理人员。");
		} else if(code != null && code.equals("1")) {
			request.setAttribute("msg", "留言成功。");
		}
		TblMemberMessage message = new TblMemberMessage();
		List<TblMemberMessageCategory> mc = messageCategoryService.findAllWithCache();
		map.put("categoryList", mc);
		map.put("message", message);
		return WEBCREATE;
	}
	
	@Log(message="添加了{0}留言。")
	@RequiresPermissions("MemberMessage:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(HttpServletRequest request, HttpServletResponse response, @Valid TblMemberMessage memberMessage) {
		String flag = request.getParameter("flag");
		try {
			ShiroUser shiroUser = SecurityUtils.getShiroUser();
			if(shiroUser.getUserType().equals("admin")) {
				memberMessage.setUser(shiroUser.getUser());
			} else {
				memberMessage.setTblMemberUser(shiroUser.getMemberUser());
			}
			memberMessage.setCreateDate(new Date());
			//memberMessage.setParentId(new Long(-1));
			//memberMessage.setStatus(0);
			TblMemberMessageCategory mmc = messageCategoryService.get(memberMessage.getCategoryId());
			memberMessage.setTblMemberMessageCategory(mmc);
			messageService.saveOrUpdate(memberMessage);
			
			if(flag != null && flag.equals("web")) {
				try {
					response.sendRedirect("/members/message/toMsgCreate?code=1");
					return null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (ServiceException e) {
			if(flag != null && flag.equals("web")) {
				try {
					response.sendRedirect("/members/message/toMsgCreate?code=0");
					return null;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			return AjaxObject.newError("添加留言失败：" + e.getMessage()).toString();
		}
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{memberMessage.getTitle()}));
		return AjaxObject.newOk("添加留言成功！").toString();
	}
	
	@ModelAttribute("preloadMessage")
	public TblMemberMessage preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			TblMemberMessage memberMessage = messageService.get(id);
			return memberMessage;
		}
		return null;
	}
	
	@RequiresPermissions("MemberMessage:view")
	@RequestMapping(value="/view/{id}", method={RequestMethod.GET, RequestMethod.POST})
	public String view(ServletRequest request, @PathVariable Long id, Map<String, Object> map) {
		List<TblMemberMessage> list = new ArrayList<TblMemberMessage>();
		TblMemberMessage message = messageService.get(id);
		
		//LOG.info("----MC-------" + request.getParameter("pTabid"));
		request.setAttribute("pTabid", request.getParameter("pTabid"));

		list.add(message);
		Specification<TblMemberMessage> s = DynamicSpecifications.bySearchFilter(request, TblMemberMessage.class, new SearchFilter("parentId", Operator.EQ,
				id));
		Page page = new Page();
		page.setNumPerPage(100);
		page.setPageNum(1);
		list.addAll(messageService.findByExample(s, page));
		map.put("messages", list);
		map.put("memberMessage", message);
		List<TblMemberMessageCategory> mc = messageCategoryService.findAllWithCache();
		map.put("categoryList", mc);
		return VIEW;
	}
	
	@RequiresPermissions("MemberMessage:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		TblMemberMessage memberMessage = messageService.get(id);
		List<TblMemberMessageCategory> mc = messageCategoryService.findAllWithCache();
		map.put("categoryList", mc);
		map.put("memberMessage", memberMessage);
		
		return UPDATE;
	}
	
	@Log(message="修改了{0}留言的信息。")
	@RequiresPermissions("MemberMessage:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadMessage")TblMemberMessage memberMessage) {
		messageService.saveOrUpdate(memberMessage);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{memberMessage.getTitle()}));
		return AjaxObject.newOk("修改留言成功！").toString();
	}
	
	@Log(message="修改了{0}留言的状态。")
	@RequiresPermissions("MemberMessage:edit")
	@RequestMapping(value="/updateStatus/{id}/{status}", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String updateStatus(ServletRequest request, @PathVariable Long id, @PathVariable Integer status) {
		TblMemberMessage message = messageService.get(id);
		message.setStatus(status);
		messageService.saveOrUpdate(message);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{message.getTitle()}));
		if(status == 3) {
			String pTabid = request.getParameter("pTabid");
			return AjaxObject.newRefreshNavtab(pTabid,"提交成功！").toString();
		} else {
			return AjaxObject.newOk("提交成功！").setCallbackType("").toString();
		}
	}
	
	@RequiresPermissions("MemberMessage:edit")
	@RequestMapping(value="/reply/{id}", method=RequestMethod.GET)
	public String preReply(@PathVariable Long id, Map<String, Object> map) {
		TblMemberMessage message = messageService.get(id);
		//message.setStatus(MSG_STATUS_READ);
		//messageService.saveOrUpdate(message);
		List<TblMemberMessageCategory> mc = messageCategoryService.findAllWithCache();
		map.put("categoryList", mc);
		map.put("memberMessage", message);
		map.put("parentId", id);
		
		return REPLY;
	}
	
	@Log(message="回复了{0}留言的信息。")
	@RequiresPermissions("MemberMessage:edit")
	@RequestMapping(value="/reply", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String reply(ServletRequest request, @Valid TblMemberMessage memberMessage) {
		TblMemberMessage parent = messageService.get(memberMessage.getParentId());
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		if(shiroUser.getUserType().equals("admin")) {
			memberMessage.setUser(shiroUser.getUser());
			parent.setStatus(MSG_STATUS_REPLY);
		} else {
			memberMessage.setTblMemberUser(shiroUser.getMemberUser());
			parent.setStatus(MSG_STATUS_SUBMIT);
		}
		messageService.saveOrUpdate(parent);
		memberMessage.setCreateDate(new Date());
		memberMessage.setStatus(MSG_STATUS_NEW);
		//TblMemberMessageCategory mmc = messageCategoryService.get(memberMessage.getCategoryId());
		//memberMessage.setTblMemberMessageCategory(mmc);
		messageService.saveOrUpdate(memberMessage);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{memberMessage.getTitle()}));
		if(request.getParameter("relNavTabId") != null) {
			return AjaxObject.newRefreshNavtab(request.getParameter("relNavTabId"), "回复留言成功！").toString();
		} else {
			return AjaxObject.newOk("回复留言成功！").setCallbackType("").toString();
		}
	}
	
	@Log(message="删除了{0}留言。")
	@RequiresPermissions("MemberMessage:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		TblMemberMessage memberMessage = messageService.get(id);
		try {
			messageService.delete(id);
		} catch (ServiceException e) {
			return AjaxObject.newError("删除留言失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{memberMessage.getTitle()}));
		return AjaxObject.newOk("删除留言成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("MemberMessage:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, ModelMap map, TblMemberMessage message) {
		//for page
		List<TblMemberMessageCategory> mc = messageCategoryService.findAllWithCache();
		map.put("categoryList", mc);
		map.put("message", message);
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		
		Specification<TblMemberMessage> specification = null;
		if(shiroUser.getUserType().equalsIgnoreCase("admin")) {
			request.setAttribute("userType", "admin");
			//不是超级管理员角色才需要过滤
			if(!shiroUser.getUser().getUsername().equals("admin")) { 
				specification = DynamicSpecifications.bySearchFilter(request, TblMemberMessage.class, 
						new SearchFilter("tblMemberMessageCategory.tblCategoryOrg.organization.id", Operator.EQ, shiroUser.getUser().getOrganization().getId()),
						new SearchFilter("parentId", Operator.ISNULL, null),
						new SearchFilter("status", Operator.NEQ, 0));
			} else {
				specification = DynamicSpecifications.bySearchFilter(request, TblMemberMessage.class, 
						new SearchFilter("parentId", Operator.ISNULL, null),
						new SearchFilter("status", Operator.NEQ, 0));
			}
		} else {
			request.setAttribute("userType", "member");
			specification = DynamicSpecifications.bySearchFilter(request, TblMemberMessage.class, 
					new SearchFilter("tblMemberUser.id", Operator.EQ, shiroUser.getMemberUser().getId()), new SearchFilter("parentId", Operator.ISNULL, null));
		}
		page.setOrderField("id");
		page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
		List<TblMemberMessage> messages = messageService.findByExample(specification, page);
		//messages = DataControlFilter.filterBeanList(messages, "Message");
		
		map.put("page", page);
		map.put("messages", messages);

		return LIST;
	}
	
	@RequiresPermissions("MessageAssign:view")
	@RequestMapping(value="/assignList", method={RequestMethod.GET, RequestMethod.POST})
	public String assignList(ServletRequest request, Page page, ModelMap map, TblMemberMessage message) {
		//for page
		List<TblMemberMessageCategory> mc = messageCategoryService.findAllWithCache();
		map.put("categoryList", mc);
		map.put("message", message);
		
		List<TblMemberMessageAssign> messages = null;
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		
		Specification<TblMemberMessageAssign> specification = null;
		if(shiroUser.getUserType().equalsIgnoreCase("admin")) {
			User user = userService.get(shiroUser.getId());
			//不是超级管理员角色才需要过滤
			if(!user.getUsername().equals("admin")) { 
				messages = messageService.findAssignListByUserIdOrOrgId(user.getId(), user.getOrganization().getId(), page);
			} else {
				specification = DynamicSpecifications.bySearchFilter(request, TblMemberMessageAssign.class);
				messages = messageService.findAssignListByExample(specification, page);
			}
		}
		
		map.put("page", page);
		map.put("messages", messages);

		return ASSIGNLIST;
	}
	
	@RequiresPermissions("MessageAssign:assign")
	@RequestMapping(value="/orgTree", method={RequestMethod.GET, RequestMethod.POST})
	public String tree(Map<String, Object> map) {
		Organization organization = organizationService.getTree();
		
		map.put("organization", organization);
		return ASSIGNTREE;
	}
	
	@RequiresPermissions("MessageAssign:assign")
	@RequestMapping(value="/userList/{orgId}", method={RequestMethod.GET, RequestMethod.POST})
	public String userList(ServletRequest request, Page page, @PathVariable Long orgId, Map<String, Object> map) {
		Specification<User> specification = DynamicSpecifications.bySearchFilter(request, User.class,
				new SearchFilter("organization.id", Operator.EQ, orgId));
		List<User> users = userService.findByExample(specification, page);
		//LOG.info("--------------------" + users.size());
		request.setAttribute("orgId", orgId);
		String msgId = request.getParameter("msgId");
		request.setAttribute("msgId", msgId);
		map.put("page", page);
		map.put("users", users);
		return ASSIGNUSER;
	}
	
	@RequiresPermissions("MessageAssign:assign")
	@RequestMapping(value="/reAssign/{id}", method=RequestMethod.GET)
	public String preReAssign(@PathVariable Long id, Map<String, Object> map) {
		TblMemberMessage message = messageService.get(id);
		map.put("message", message);
		map.put("parentId", id); 
		User user = new User();
		map.put("user", user);
		return REASSIGN;
	}
	
	@Log(message="添加了{0}留言分流。")
	@RequiresPermissions("MessageAssign:assign")
	@RequestMapping(value="/reAssign", method={RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String reAssign(@Valid TblMemberMessageAssign assign) {
		TblMemberMessage tmm = messageService.get(assign.getMessage().getId());
		try {
			ShiroUser shiroUser = SecurityUtils.getShiroUser();
			assign.setCreateDate(new Date());
			User user = userService.get(shiroUser.getId());
			assign.setCreatedBy(user.getId());
			assign.setTblMemberMessageCategory(tmm.getTblMemberMessageCategory());
			
			//TblMemberMessageCategory mmc = messageCategoryService.get(memberMessage.getCategoryId());
			//memberMessage.setTblMemberMessageCategory(mmc);
			messageService.saveOrUpdateAssign(assign);
		} catch (ServiceException e) {
			return AjaxObject.newError("分流留言失败：" + e.getMessage()).toString();
		}
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{tmm.getTitle()}));
		return AjaxObject.newOk("分流留言成功！").toString();
	}
	
	//@RequiresPermissions("MemberMessage:edit")
	@RequestMapping(value="/check", method=RequestMethod.GET)
	public @ResponseBody String check() {
		String rst = "";
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		//登录失效 
		if(shiroUser == null || shiroUser.getUserType() == null) {
			return "";
		}
		String userType = shiroUser.getUserType();
		if(userType.equals("admin")) {
			User user = userService.get(shiroUser.getId());
			if(messageService.checkStatus(userType, null, user, MSG_STATUS_NEW)>0) {
				rst = "newMsg";
			} else if(messageService.checkStatus(userType, null, user, MSG_STATUS_SUBMIT)>0) {
				rst = "newQuestion";
			}
		} else {
			if(messageService.checkStatus(userType, shiroUser.getMemberUser(), null, MSG_STATUS_REPLY)>0) {
				rst = "newReply";
			} else if(messageService.checkStatus(userType, shiroUser.getMemberUser(), null, MSG_STATUS_FIXED)>0) {
				rst = "fixed";
			}
		}
		return rst;
	}
}
