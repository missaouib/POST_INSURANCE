/**
 * 
 */
package com.sendtend.web.controller.member;

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

import com.sendtend.web.entity.member.TblMemberModule;
import com.sendtend.web.entity.member.TblMemberPermission;
import com.sendtend.web.exception.ExistedException;
import com.sendtend.web.exception.ServiceException;
import com.sendtend.web.log.Log;
import com.sendtend.web.log.LogMessageObject;
import com.sendtend.web.log.impl.LogUitls;
import com.sendtend.web.service.member.MModuleService;
import com.sendtend.web.service.member.MPermissionService;
import com.sendtend.web.util.dwz.AjaxObject;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.persistence.DynamicSpecifications;
import com.sendtend.web.util.persistence.SearchFilter;
import com.sendtend.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/members/module")
public class MModuleController{
	//private static final Logger LOG = LoggerFactory.getLogger(MModuleController.class);

	@Autowired
	private MModuleService moduleService;

	@Autowired
	private MPermissionService permissionService;

	private static final String CREATE = "members/module/create";
	private static final String UPDATE = "members/module/update";
	private static final String LIST = "members/module/list";
	private static final String TREE = "members/module/tree";
	private static final String VIEW = "members/module/view";
	private static final String TREE_LIST = "members/module/tree_list";
	private static final String LOOKUP_PARENT = "members/module/lookup_parent";

	@RequiresPermissions("TblMemberModule:save")
	@RequestMapping(value = "/create/{parentTblMemberModuleId}", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map, @PathVariable Long parentTblMemberModuleId) {
		map.put("parentTblMemberModuleId", parentTblMemberModuleId);
		return CREATE;
	}

	@Log(message = "添加了{0}模块。")
	@RequiresPermissions("TblMemberModule:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(@Valid TblMemberModule module) {
		TblMemberModule parentTblMemberModule = moduleService.get(module.getParent().getId());
		if (parentTblMemberModule == null) {
			return AjaxObject.newError("添加模块失败：id=" + module.getParent().getId() + "的父模块不存在！").toString();
		}

		List<TblMemberPermission> permissions = new ArrayList<TblMemberPermission>();
		for (TblMemberPermission permission : module.getTblMemberPermissions()) {
			if (StringUtils.isNotBlank(permission.getSn())) {
				permissions.add(permission);
			}
		}

		for (TblMemberPermission permission : permissions) {
			permission.setTblMemberModule(module);
		}
		module.setTblMemberPermissions(permissions);

		try {
			moduleService.saveOrUpdate(module);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加模块失败：" + e.getMessage()).toString();
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { module.getName() }));
		return AjaxObject.newOk("添加模块成功！").toString();
	}

	@RequiresPermissions("TblMemberModule:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		TblMemberModule module = moduleService.get(id);

		map.put("module", module);
		return UPDATE;
	}

	@Log(message = "修改了{0}模块的信息。")
	@RequiresPermissions("TblMemberModule:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(@Valid TblMemberModule module) {
		TblMemberModule oldTblMemberModule = moduleService.get(module.getId());
		oldTblMemberModule.setName(module.getName());
		oldTblMemberModule.setPriority(module.getPriority());
		oldTblMemberModule.setDescription(module.getDescription());
		oldTblMemberModule.setSn(module.getSn());
		oldTblMemberModule.setUrl(module.getUrl());
		oldTblMemberModule.setParent(module.getParent());
		oldTblMemberModule.setClassName(module.getClassName());

		// 模块自定义权限，删除过后新增报错，会有validate的验证错误。hibernate不能copy到permission的值，导致。
		for (TblMemberPermission permission : module.getTblMemberPermissions()) {
			if (StringUtils.isNotBlank(permission.getSn())) {// 已选中的
				if (permission.getId() == null) {// 新增
					permission.setTblMemberModule(oldTblMemberModule);
					oldTblMemberModule.getTblMemberPermissions().add(permission);
					permissionService.saveOrUpdate(permission);
				}
			} else {// 未选中的
				if (permission.getId() != null) {// 删除
					for (TblMemberPermission oldPermission : oldTblMemberModule.getTblMemberPermissions()) {
						if (oldPermission.getId().equals(permission.getId())) {
							oldPermission.setTblMemberModule(null);
							permission = oldPermission;
							break;
						}
					}
					permissionService.delete(permission.getId());
					oldTblMemberModule.getTblMemberPermissions().remove(permission);
				}
			}
		}

		moduleService.saveOrUpdate(oldTblMemberModule);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { oldTblMemberModule.getName() }));
		return AjaxObject.newOk("修改模块成功！").toString();
	}

	@Log(message = "删除了{0}模块。")
	@RequiresPermissions("TblMemberModule:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		TblMemberModule module = moduleService.get(id);
		try {
			moduleService.delete(id);
		} catch (ServiceException e) {
			return AjaxObject.newError("删除模块失败：" + e.getMessage()).setCallbackType("").toString();
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { module.getName() }));
		return AjaxObject.newOk("删除模块成功！").setCallbackType("").toString();
	}

	@RequiresPermissions("TblMemberModule:view")
	@RequestMapping(value = "/tree_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String tree_list(Map<String, Object> map) {
		return TREE_LIST;
	}

	@RequiresPermissions("TblMemberModule:view")
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String tree(Map<String, Object> map) {
		TblMemberModule module = moduleService.getTree();

		map.put("module", module);
		return TREE;
	}

	@RequiresPermissions("TblMemberModule:view")
	@RequestMapping(value = "/list/{parentTblMemberModuleId}", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, @PathVariable Long parentTblMemberModuleId, Map<String, Object> map) {
		Specification<TblMemberModule> specification = DynamicSpecifications.bySearchFilter(request, TblMemberModule.class, new SearchFilter("parent.id", Operator.EQ,
				parentTblMemberModuleId));
		List<TblMemberModule> modules = moduleService.findByExample(specification, page);
		//如果没有下级回显点击的节点信息
		if(modules.isEmpty()) {
			specification = DynamicSpecifications.bySearchFilter(request, TblMemberModule.class, new SearchFilter("id", Operator.EQ,
					parentTblMemberModuleId));
			modules = moduleService.findByExample(specification, page);
		}
		map.put("page", page);
		map.put("modules", modules);
		map.put("parentTblMemberModuleId", parentTblMemberModuleId);

		return LIST;
	}

	@RequiresPermissions("TblMemberModule:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map) {
		TblMemberModule module = moduleService.get(id);

		map.put("module", module);
		return VIEW;
	}

	@RequiresPermissions(value = { "TblMemberModule:edit", "TblMemberModule:save" }, logical = Logical.OR)
	@RequestMapping(value = "/lookupParent/{id}", method = { RequestMethod.GET })
	public String lookup(@PathVariable Long id, Map<String, Object> map) {
		TblMemberModule module = moduleService.getTree();

		map.put("module", module);
		return LOOKUP_PARENT;
	}
}
