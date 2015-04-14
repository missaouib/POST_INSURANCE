/**
 * 
 */
package com.sendtend.web.controller.member;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.sendtend.web.entity.member.TblCategoryOrg;
import com.sendtend.web.entity.member.TblMemberDataControl;
import com.sendtend.web.entity.member.TblMemberMessageCategory;
import com.sendtend.web.exception.ServiceException;
import com.sendtend.web.log.Log;
import com.sendtend.web.log.LogMessageObject;
import com.sendtend.web.log.impl.LogUitls;
import com.sendtend.web.service.member.CategoryOrgService;
import com.sendtend.web.service.member.MessageCategoryService;
import com.sendtend.web.shiro.ShiroUser;
import com.sendtend.web.util.dwz.AjaxObject;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.persistence.DynamicSpecifications;
import com.sendtend.web.util.persistence.SearchFilter;

@Controller
@RequestMapping("/members/messageCategory")
public class MessageCategoryController {
	private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MessageCategoryService messageCategoryService;
	
	@Autowired
	private CategoryOrgService categoryOrgService;
	
	private static final String CREATE = "members/messageCategory/create";
	private static final String UPDATE = "members/messageCategory/update";
	private static final String LIST = "members/messageCategory/list";
	private static final String ASSIGN = "members/messageCategory/assign";
	
	@RequiresPermissions("MessageCategory:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		return CREATE;
	}
	
	@Log(message="添加了{0}留言分类。")
	@RequiresPermissions("MessageCategory:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid TblMemberMessageCategory messageCategory) {
		try {
			ShiroUser shiroUser = SecurityUtils.getShiroUser();
			Long userId = null;
			if(shiroUser.getUserType().equals("admin")) {
				userId = shiroUser.getUser().getId();
			} else {
				userId = shiroUser.getMemberUser().getId();
			}
			messageCategory.setCreateDate(new Date());
			messageCategory.setCreatedBy(userId);
			messageCategory.setStatus(1);
			messageCategoryService.saveOrUpdate(messageCategory);
		} catch (ServiceException e) {
			return AjaxObject.newError("添加留言分类失败：" + e.getMessage()).toString();
		}
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{messageCategory.getCategoryName()}));
		return AjaxObject.newOk("添加留言分类成功！").toString();
	}
	
	@ModelAttribute("preloadCategory")
	public TblMemberMessageCategory preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			TblMemberMessageCategory messageCategory = messageCategoryService.get(id);
			return messageCategory;
		}
		return null;
	}
	
	@RequiresPermissions("MessageCategory:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		TblMemberMessageCategory messageCategory = messageCategoryService.get(id);
		
		map.put("messageCategory", messageCategory);
		return UPDATE;
	}
	
	@Log(message="修改了{0}留言分类的信息。")
	@RequiresPermissions("MessageCategory:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadCategory")TblMemberMessageCategory messageCategory) {
		LOG.debug("----------" + messageCategory.getStatus());
		messageCategoryService.saveOrUpdate(messageCategory);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{messageCategory.getCategoryName()}));
		return AjaxObject.newOk("修改留言分类成功！").toString();
	}
	
	@Log(message="删除了{0}留言分类。")
	@RequiresPermissions("MessageCategory:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		TblMemberMessageCategory messageCategory = messageCategoryService.get(id);
		try {
			messageCategoryService.delete(id);
		} catch (ServiceException e) {
			return AjaxObject.newError("删除留言分类失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{messageCategory.getCategoryName()}));
		return AjaxObject.newOk("删除留言分类成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("MessageCategory:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		Collection<SearchFilter> idfilters = new HashSet<SearchFilter>();
		String[] dc = null;
		Map<String, TblMemberDataControl> mdcs = SecurityUtils.getShiroUser().getHasTblMemberDataControls();
		if(SecurityUtils.getLoginTblMemberUser() != null) {
			if(mdcs != null && !mdcs.isEmpty()) {
				Collection<TblMemberDataControl> set = mdcs.values();
				String fv = null;
				SearchFilter sf = null;
				for(TblMemberDataControl tmdc:set) {
					fv = tmdc.getControl();
					//MessageCategory:id:EQ:1,2,3
					dc = fv.split(":");
					if(dc[0] != null && dc[0].equalsIgnoreCase("MessageCategory")) {
						/*
						 * 如果含有都好，一边拿情况下应该是in的操作
						 */
						if(dc[1].equalsIgnoreCase("id")) {
							if(dc[3].indexOf(",") != -1) { //一般情况下有“,”，应为in的操作
								sf = SearchFilter.getSearchFilter(dc[1], dc[2], dc[3].split(","));
							} else {
								sf = SearchFilter.getSearchFilter(dc[1], dc[2], dc[3]);
							}
							if(sf != null) {
								idfilters.add(sf);
							}
						}
					}
				}
			}
		}
		/*
		 * 改为本地sql方式查询，因为涉及到aes加解密。中文特别烦的
		 * */
		Specification<TblMemberMessageCategory> specification = DynamicSpecifications.bySearchFilter(request, TblMemberMessageCategory.class);
		
		if(idfilters != null && !idfilters.isEmpty()) {
			specification = DynamicSpecifications.bySearchFilter(request, TblMemberMessageCategory.class, idfilters);
		}
		
		List<TblMemberMessageCategory> members = messageCategoryService.findByExample(specification, page);
		//List<TblMemberMessageCategory> members = messageCategoryService.findByNativeSQL(idfilters, map, parentMemberId, page);
		if(mdcs != null && !mdcs.isEmpty()) {
			Collection<TblMemberDataControl> set = mdcs.values();
			String fv = null;
			for(TblMemberDataControl tmdc:set) {
				fv = tmdc.getControl();
				//MessageCategory:id:EQ:1,2,3
				dc = fv.split(":");
				if(dc[0] != null && dc[0].equalsIgnoreCase("MessageCategory")) {
					/*
					 * 如果含有都好，一边拿情况下应该是in的操作
					 */
					if(dc[1].equalsIgnoreCase("column")) {
						members = DataControlFilter.toColumnsFilterBeanList(members, dc[2], dc[3].split(","));
					}
				}
			}
		}
		
		map.put("page", page);
		map.put("mCategory", members);

		return LIST;
	}

	@RequiresPermissions("MessageCategory:edit")
	@RequestMapping(value="/assign/{id}", method=RequestMethod.GET)
	public String preAssign(ServletRequest request, @PathVariable Long id, Map<String, Object> map) {
		TblMemberMessageCategory messageCategory = messageCategoryService.get(id);
		map.put("messageCategory", messageCategory);
		TblCategoryOrg co = categoryOrgService.findByCategoryId(messageCategory.getId());
		map.put("categoryOrg", co);
		return ASSIGN;
	}

	@Log(message="指派了{0}留言分类的处理组织。")
	@RequiresPermissions("MessageCategory:edit")
	@RequestMapping(value="/assign", method=RequestMethod.POST)
	public @ResponseBody String assign(@Valid TblCategoryOrg categoryOrg) {
		categoryOrgService.saveOrUpdate(categoryOrg);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{categoryOrg.getTblMemberMessageCategory().getCategoryName()}));
		return AjaxObject.newOk("修改留言分类成功！").toString();
	}
}
