package com.gdpost.web.controller.document;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.entity.component.Resource;
import com.gdpost.web.entity.member.TblMember;
import com.gdpost.web.entity.member.TblMemberResource;
import com.gdpost.web.entity.member.TblMemberUser;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.component.ResourceService;
import com.gdpost.web.service.member.MemberService;
import com.gdpost.web.service.uploaddatamanage.MemberResourceService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;

@Controller
@RequestMapping("/management/document")
public class PushController {
	private static final Logger LOG = LoggerFactory.getLogger(PushController.class); 
	
	@Autowired
	private MemberResourceService memberResourceService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ResourceService resourceService;
	
	private static final String LIST = "management/document/list";
	private static final String CREATE = "management/document/create";
	private static final String TREE = "management/document/tree";
	private static final String RESOURCE = "management/document/resource";
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		// 设置需要包裹的元素个数，默认为256
	    binder.setAutoGrowCollectionLimit(5000);
	}
	
	@RequiresPermissions("Push:show")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(HttpServletRequest request, Page page, Map<String, Object> map) {
		Specification<TblMemberResource> specification = DynamicSpecifications.bySearchFilter(request, TblMemberResource.class);
		page.setOrderField("id");
		page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
		List<TblMemberResource> resources = memberResourceService.findAll(specification, page);
		
		map.put("page", page);
		map.put("resources", resources);

		return LIST;
	}

	@RequiresPermissions("Push:save")
	@RequestMapping(value="/create", method={RequestMethod.GET, RequestMethod.POST})
	public String create(Map<String, Object> map) {
		
		return CREATE;
	}
	
	@RequiresPermissions("TblMember:view")
	@RequestMapping(value="/tree", method={RequestMethod.GET, RequestMethod.POST})
	public String tree(Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		TblMemberUser user = shiroUser.getMemberUser();
		TblMember member = null;
		if(user != null) {
			member = memberService.getTree(user.getTblMember().getId());
		} else {
			member = memberService.getTree();
		}
		
		memberService.get(new Long(1));
		map.put("member", member);
		return TREE;
	}
	
	@RequiresPermissions("Resource:view")
	@RequestMapping(value="/resource", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<Resource> specification = DynamicSpecifications.bySearchFilter(request, Resource.class);
		page.setOrderField("id");
		page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
		List<Resource> resources = resourceService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("resources", resources);

		return RESOURCE;
	}
	
	@RequiresPermissions("Push:save")
	@RequestMapping(value="/setting/{treeIds}", method=RequestMethod.POST)
	public @ResponseBody String setting(Long[] ids, @PathVariable String treeIds, HttpServletRequest request) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		String[] aryTreeIds = treeIds.split(",");
		TblMemberResource resource = null;
		try {
			for(String treeId : aryTreeIds) {
				for (Long id : ids) {
					resource = new TblMemberResource();
					resource.setCreateDate(new Timestamp(System.currentTimeMillis()));
					resource.setCreatedBy(shiroUser.getId());
					resource.setResource(resourceService.get(id));
					resource.setStatus(0);
					resource.setTblMember(memberService.get(Long.parseLong(treeId)));
					memberResourceService.saveOrUpdate(resource);
				}
			}
		} catch(Exception err) {
			return AjaxObject.newOk("设置推送错误，请重试！").setNavTabId("document_navTab").setCallbackType("").toString();
		}
		
		return AjaxObject.newOk("设置推送成功！").setNavTabId("document_navTab").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0},{1}推送。")
	@RequiresPermissions("Push:delete")
	public void delete(Long id) {
		TblMemberResource resource = memberResourceService.get(id);
		memberResourceService.delete(resource.getId());

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{resource.getTblMember().getMemberName(), resource.getResource().getName()}));
	}
	
	@RequiresPermissions("Push:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids, HttpServletRequest request) {
		for (Long id : ids) {
			delete(id);
		}
		
		return AjaxObject.newOk("删除推送成功！").setCallbackType("").toString();
	}
}

