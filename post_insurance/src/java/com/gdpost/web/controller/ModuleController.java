/**
 * 
 */
package com.gdpost.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.web.entity.main.Module;
import com.gdpost.web.entity.main.Permission;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.ModuleService;
import com.gdpost.web.service.PermissionService;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/management/security/module")
public class ModuleController {

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private PermissionService permissionService;

	private static final String CREATE = "management/security/module/create";
	private static final String UPDATE = "management/security/module/update";
	private static final String LIST = "management/security/module/list";
	private static final String TREE = "management/security/module/tree";
	private static final String VIEW = "management/security/module/view";
	private static final String TREE_LIST = "management/security/module/tree_list";
	private static final String LOOKUP_PARENT = "management/security/module/lookup_parent";

	@RequiresPermissions("Module:save")
	@RequestMapping(value = "/create/{parentModuleId}", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map, @PathVariable Long parentModuleId) {
		map.put("parentModuleId", parentModuleId);
		return CREATE;
	}

	@Log(message = "?????????{0}?????????", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("Module:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(@Valid Module module) {
		Module parentModule = moduleService.get(module.getParent().getId());
		if (parentModule == null) {
			return AjaxObject.newError("?????????????????????id=" + module.getParent().getId() + "????????????????????????").toString();
		}

		List<Permission> permissions = new ArrayList<Permission>();
		for (Permission permission : module.getPermissions()) {
			if (StringUtils.isNotBlank(permission.getSn())) {
				permissions.add(permission);
			}
		}

		for (Permission permission : permissions) {
			permission.setModule(module);
		}
		module.setPermissions(permissions);

		try {
			moduleService.saveOrUpdate(module);
		} catch (ExistedException e) {
			return AjaxObject.newError("?????????????????????" + e.getMessage()).toString();
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { module.getName() }));
		return AjaxObject.newOk("?????????????????????").toString();
	}

	@RequiresPermissions("Module:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Module module = moduleService.get(id);

		map.put("module", module);
		return UPDATE;
	}

	@Log(message = "?????????{0}??????????????????", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("Module:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(@Valid Module module) {
		Module oldModule = moduleService.get(module.getId());
		oldModule.setName(module.getName());
		oldModule.setPriority(module.getPriority());
		oldModule.setDescription(module.getDescription());
		oldModule.setSn(module.getSn());
		oldModule.setUrl(module.getUrl());
		oldModule.setParent(module.getParent());
		oldModule.setClassName(module.getClassName());

		// ?????????????????????????????????????????????????????????validate??????????????????hibernate??????copy???permission??????????????????
		for (Permission permission : module.getPermissions()) {
			if (StringUtils.isNotBlank(permission.getSn())) {// ????????????
				if (permission.getId() == null) {// ??????
					permission.setModule(oldModule);
					oldModule.getPermissions().add(permission);
					permissionService.saveOrUpdate(permission);
				}
			} else {// ????????????
				if (permission.getId() != null) {// ??????
					for (Permission oldPermission : oldModule.getPermissions()) {
						if (oldPermission.getId().equals(permission.getId())) {
							oldPermission.setModule(null);
							permission = oldPermission;
							break;
						}
					}
					permissionService.delete(permission.getId());
					oldModule.getPermissions().remove(permission);
				}
			}
		}

		moduleService.saveOrUpdate(oldModule);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { oldModule.getName() }));
		return AjaxObject.newOk("?????????????????????").toString();
	}

	@Log(message = "?????????{0}?????????", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("Module:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		Module module = moduleService.get(id);
		try {
			moduleService.delete(id);
		} catch (ServiceException e) {
			return AjaxObject.newError("?????????????????????" + e.getMessage()).setCallbackType("").toString();
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { module.getName() }));
		return AjaxObject.newOk("?????????????????????").setCallbackType("").toString();
	}

	@RequiresPermissions("Module:view")
	@RequestMapping(value = "/tree_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String tree_list(Map<String, Object> map) {
		return TREE_LIST;
	}

	@RequiresPermissions("Module:view")
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String tree(Map<String, Object> map) {
		Module module = moduleService.getTree();

		map.put("module", module);
		return TREE;
	}

	@RequiresPermissions("Module:view")
	@RequestMapping(value = "/list/{parentModuleId}", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, @PathVariable Long parentModuleId, Map<String, Object> map) {
		Specification<Module> specification = DynamicSpecifications.bySearchFilter(request, Module.class, new SearchFilter("parent.id", Operator.EQ,
				parentModuleId));
		List<Module> modules = moduleService.findByExample(specification, page);
		if(modules.isEmpty()) {
			specification = DynamicSpecifications.bySearchFilter(request, Module.class, new SearchFilter("id", Operator.EQ,
					parentModuleId));
			modules = moduleService.findByExample(specification, page);
		}
		map.put("page", page);
		map.put("modules", modules);
		map.put("parentModuleId", parentModuleId);

		return LIST;
	}

	@RequiresPermissions("Module:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map) {
		Module module = moduleService.get(id);

		map.put("module", module);
		return VIEW;
	}

	@RequiresPermissions(value = { "Module:edit", "Module:save" }, logical = Logical.OR)
	@RequestMapping(value = "/lookupParent/{id}", method = { RequestMethod.GET })
	public String lookup(@PathVariable Long id, Map<String, Object> map) {
		Module module = moduleService.getTree();

		map.put("module", module);
		return LOOKUP_PARENT;
	}
}
