/**
 * 
 */
package com.gdpost.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
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

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.OrganizationRole;
import com.gdpost.web.entity.main.Role;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.OrganizationRoleService;
import com.gdpost.web.service.OrganizationService;
import com.gdpost.web.service.RoleService;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/management/security/organization")
public class OrganizationController {
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private OrganizationRoleService organizationRoleService;
	
	@Autowired
	private RoleService roleService;
	
	private static final String CREATE = "management/security/organization/create";
	private static final String UPDATE = "management/security/organization/update";
	private static final String LIST = "management/security/organization/list";
	private static final String TREE = "management/security/organization/tree";
	private static final String TREE_LIST = "management/security/organization/tree_list";
	
	private static final String LOOK_UP_ROLE = "management/security/organization/assign_organization_role";
	private static final String LOOK_ORGANIZATION_ROLE = "management/security/organization/delete_organization_role";
	
	private static final String LOOKUP_PARENT = "management/security/organization/lookup_parent";
	
	@RequiresPermissions("Organization:save")
	@RequestMapping(value="/create/{parentOrganizationId}", method=RequestMethod.GET)
	public String preCreate(@PathVariable Long parentOrganizationId, Map<String, Object> map) {
		map.put("parentOrganizationId", parentOrganizationId);
		return CREATE;
	}
	
	@Log(message="?????????{0}?????????", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("Organization:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid Organization organization) {
		try {
			organizationService.saveOrUpdate(organization);
		} catch (ServiceException e) {
			return AjaxObject.newError("?????????????????????" + e.getMessage()).toString();
		}
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{organization.getName()}));
		return AjaxObject.newOk("?????????????????????").toString();
	}
	
	@ModelAttribute("preloadOrg")
	public Organization preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Organization organization = organizationService.get(id);
			organization.setParent(null);
			return organization;
		}
		return null;
	}
	
	@RequiresPermissions("Organization:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Organization organization = organizationService.get(id);
		
		map.put("organization", organization);
		return UPDATE;
	}
	
	@Log(message="?????????{0}??????????????????", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("Organization:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadOrg")Organization organization) {
		organizationService.saveOrUpdate(organization);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{organization.getName()}));
		return AjaxObject.newOk("?????????????????????").toString();
	}
	
	@Log(message="?????????{0}?????????", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("Organization:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		Organization organization = organizationService.get(id);
		try {
			organizationService.delete(id);
		} catch (ServiceException e) {
			return AjaxObject.newError("?????????????????????" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{organization.getName()}));
		return AjaxObject.newOk("?????????????????????").setCallbackType("").toString();
	}
	
	@RequiresPermissions("Organization:view")
	@RequestMapping(value="/tree_list", method={RequestMethod.GET, RequestMethod.POST})
	public String tree_list(Map<String, Object> map) {
		return TREE_LIST;
	}
	
	@RequiresPermissions("Organization:view")
	@RequestMapping(value="/tree", method={RequestMethod.GET, RequestMethod.POST})
	public String tree(Map<String, Object> map) {
		Organization organization = organizationService.getTree();
		
		map.put("organization", organization);
		return TREE;
	}
	
	@RequiresPermissions("Organization:view")
	@RequestMapping(value="/list/{parentOrganizationId}", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, @PathVariable Long parentOrganizationId, 
			Map<String, Object> map) {
		Specification<Organization> specification = DynamicSpecifications.bySearchFilter(request, Organization.class,
				new SearchFilter("parent.id", Operator.EQ, parentOrganizationId));
		List<Organization> organizations = organizationService.findByExample(specification, page);
		
		if(organizations.isEmpty()) {
			specification = DynamicSpecifications.bySearchFilter(request, Organization.class,
					new SearchFilter("id", Operator.EQ, parentOrganizationId));
			organizations = organizationService.findByExample(specification, page);
		}
		
		map.put("page", page);
		map.put("organizations", organizations);
		map.put("parentOrganizationId", parentOrganizationId);

		return LIST;
	}
	
	/**
	 * ?????????????????????
	 * ??????
	 * @param userRole
	 */
	@Log(message="???{0}???????????????{1}????????????", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("Organization:assign")
	@RequestMapping(value="/create/organizationRole", method={RequestMethod.POST})
	public @ResponseBody void assignRole(OrganizationRole organizationRole) {
		organizationRoleService.saveOrUpdate(organizationRole);
		
		Organization organization = organizationService.get(organizationRole.getOrganization().getId());
		Role role = roleService.get(organizationRole.getRole().getId());
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{organization.getName(), role.getName()}));
	}
	
	/**
	 * ??????????????????????????????????????????
	 * ??????
	 * @param map
	 * @param organizationId
	 * @return
	 */
	@RequiresPermissions("Organization:assign")
	@RequestMapping(value="/lookup2create/organizationRole/{organizationId}", method={RequestMethod.GET, RequestMethod.POST})
	public String listUnassignRole(Map<String, Object> map, @PathVariable Long organizationId) {
		Page page = new Page();
		page.setNumPerPage(Integer.MAX_VALUE);
		
		List<OrganizationRole> organizationRoles = organizationRoleService.findByOrganizationId(organizationId);
		List<Role> roles = roleService.findAll(page);
		
		List<Role> rentList = new ArrayList<Role>();
		// ???????????????roles
		for (Role role : roles) {
			boolean isHas = false;
			for (OrganizationRole or : organizationRoles) {
				if (or.getRole().getId().equals(role.getId())) {
					isHas = true;
					break;
				} 
			}
			if (isHas == false) {
				rentList.add(role);
			}
		}
		
		map.put("organizationRoles", organizationRoles);
		map.put("roles", rentList);
		
		map.put("organizationId", organizationId);
		return LOOK_UP_ROLE;
	}
	
	/**
	 * ??????????????????????????????
	 * ??????
	 * @param map
	 * @param organizationId
	 * @return
	 */
	@RequiresPermissions("Organization:assign")
	@RequestMapping(value="/lookup2delete/organizationRole/{organizationId}", method={RequestMethod.GET, RequestMethod.POST})
	public String listOrganizationRole(Map<String, Object> map, @PathVariable Long organizationId) {
		List<OrganizationRole> organizationRoles = organizationRoleService.findByOrganizationId(organizationId);
		map.put("organizationRoles", organizationRoles);
		return LOOK_ORGANIZATION_ROLE;
	}
	
	/**
	 * ???????????????????????????
	 * ??????
	 * @param organizationId
	 */
	@Log(message="?????????{0}?????????{1}?????????", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("Organization:assign")
	@RequestMapping(value="/delete/organizationRole/{organizationRoleId}", method={RequestMethod.POST})
	public @ResponseBody void deleteOrganizationRole(@PathVariable Long organizationRoleId) {
		OrganizationRole organizationRole = organizationRoleService.get(organizationRoleId);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{organizationRole.getOrganization().getName(), organizationRole.getRole().getName()}));
		
		organizationRoleService.delete(organizationRoleId);
	}
	
	@RequiresPermissions(value={"Organization:edit", "Organization:save"}, logical=Logical.OR)
	@RequestMapping(value="/lookupParent/{id}", method={RequestMethod.GET})
	public String lookup(@PathVariable Long id, Map<String, Object> map) {
		Organization organization  = organizationService.getTree();
		
		map.put("organization", organization);
		return LOOKUP_PARENT;
	}
	
	@RequiresPermissions("MemberMessage:edit")
	@RequestMapping(value="/lookup2org", method=RequestMethod.POST)
	public @ResponseBody String lookupuser(ServletRequest request, User user) {
		List<Organization> list = null;
		String keyword = request.getParameter("search_LIKE_name");
		if(keyword.length()>=1) {
			Specification<Organization> specification = DynamicSpecifications.bySearchFilter(request, Organization.class);
			Page page = new Page();
			page.setNumPerPage(15);
			page.setPageNum(1);
			list = organizationService.findByExample(specification, page);
			PropertyFilter filter = new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
			        if("id".equals(name)) {
			            return true;
			        }
			        if("name".equals(name)) {
			            return true;
			        }
			        return false;
			    }
			};
			SerializeWriter out = new SerializeWriter();
			JSONSerializer serializer = new JSONSerializer(out);
			serializer.getPropertyFilters().add(filter);
			serializer.write(list);
			//return StringUtil.toChartset(out.toString(), "UTF-8", "ISO8859-1");
			return out.toString();
		}
		return "";
	}
}
