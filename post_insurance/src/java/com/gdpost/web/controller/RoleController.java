/**
 * 
 */
package com.gdpost.web.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.web.entity.main.DataControl;
import com.gdpost.web.entity.main.Module;
import com.gdpost.web.entity.main.Role;
import com.gdpost.web.entity.main.RolePermission;
import com.gdpost.web.entity.main.RolePermissionDataControl;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.DataControlService;
import com.gdpost.web.service.ModuleService;
import com.gdpost.web.service.RolePermissionDataControlService;
import com.gdpost.web.service.RolePermissionService;
import com.gdpost.web.service.RoleService;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;

@Controller
@RequestMapping("/management/security/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Autowired
	private RolePermissionDataControlService rolePermissionDataControlService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private DataControlService dataControlService;
	
	private static final String CREATE = "management/security/role/create";
	private static final String UPDATE = "management/security/role/update";
	private static final String LIST = "management/security/role/list";
	private static final String VIEW = "management/security/role/view";
	private static final String ASSIGN_DATA_CONTROL = "management/security/role/assign_data_control";
	private static final String LOOKUP_DATA_CONTROL = "management/security/role/lookup_data_control";
	
	@InitBinder
	public void initListBinder(WebDataBinder binder) {
		// ?????????????????????????????????????????????256
	    binder.setAutoGrowCollectionLimit(5000);
	}
	
	@RequiresPermissions("Role:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		map.put("module", moduleService.getTree());
		return CREATE;
	}
	
	@Log(message="?????????{0}?????????", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("Role:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid Role role) {
		List<RolePermission> rolePermissions = new ArrayList<RolePermission>();
		for (RolePermission rolePermission : role.getRolePermissions()) {
			if (rolePermission.getPermission() != null && rolePermission.getPermission().getId() != null) {
				rolePermissions.add(rolePermission);
			}
		}
		
		for (RolePermission rolePermission : rolePermissions) {
			rolePermission.setRole(role);
		}
		
		roleService.saveOrUpdate(role);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{role.getName()}));
		return AjaxObject.newOk("?????????????????????").toString();
	}
	
	@RequiresPermissions("Role:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Role role = roleService.get(id);
		
		map.put("module", moduleService.getTree());
		map.put("role", role);
		return UPDATE;
	}
	
	@Log(message="?????????{0}??????????????????", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("Role:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid Role role) {
		Role oldRole = roleService.get(role.getId());
		oldRole.setName(role.getName());
		oldRole.setDescription(role.getDescription());
		
		List<RolePermission> newRList = new ArrayList<RolePermission>();
		List<RolePermission> delRList = new ArrayList<RolePermission>();
		
		List<RolePermission> hasRolePermissions = rolePermissionService.findByRoleId(role.getId());
		for (RolePermission rolePermission : role.getRolePermissions()) {
			if (rolePermission.getId() == null && rolePermission.getPermission() != null) {
				rolePermission.setRole(oldRole);
				newRList.add(rolePermission);
			} else if (rolePermission.getId() != null && rolePermission.getPermission() == null) {
				for (RolePermission rp : hasRolePermissions) {
					if (rp.getId().equals(rolePermission.getId())) {
						delRList.add(rp);
						break;
					}
				}
			}
		}
		
		rolePermissionService.save(newRList);
		rolePermissionService.delete(delRList);
		roleService.saveOrUpdate(oldRole);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{oldRole.getName()}));
		return AjaxObject.newOk("?????????????????????").toString();
	}
	
	@Log(message="?????????{0}?????????", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("Role:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		Role role = roleService.get(id);
		roleService.delete(role.getId());

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{role.getName()}));
		return AjaxObject.newOk("?????????????????????").setCallbackType("").toString();
	}
	
	@RequiresPermissions("Role:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<Role> specification = DynamicSpecifications.bySearchFilter(request, Role.class);
		List<Role> roles = roleService.findByExample(specification, page);

		map.put("page", page);
		map.put("roles", roles);
		return LIST;
	}
	
	@RequiresPermissions("Role:view")
	@RequestMapping(value="/view/{id}", method={RequestMethod.GET})
	public String view(@PathVariable Long id, Map<String, Object> map) {
		Role role = roleService.get(id);
		
		map.put("module", moduleService.getTree());
		map.put("role", role);
		return VIEW;
	}
	
	@RequiresPermissions("Role:assign")
	@RequestMapping(value="/assign/{id}", method=RequestMethod.GET)
	public String preAssign(@PathVariable Long id, Map<String, Object> map) {
		Role role = roleService.get(id);
		
		Map<Module, List<RolePermission>> mpMap = new LinkedHashMap<Module, List<RolePermission>>(); 
		for (RolePermission rp : role.getRolePermissions()) {
			Module module = rp.getPermission().getModule();
			
			List<RolePermission> rps = mpMap.get(module);
			if (rps == null) {
				rps = new ArrayList<RolePermission>();
				
				mpMap.put(module, rps);
			}
			
			rps.add(rp);
		}
		
		map.put("role", role);
		map.put("mpMap", mpMap);
		return ASSIGN_DATA_CONTROL;
	}
	
	@Log(message="?????????{0}????????????????????????", level=LogLevel.WARN, module=LogModule.PZGL)
	@RequiresPermissions("Role:assign")
	@RequestMapping(value="/assign", method=RequestMethod.POST)
	public @ResponseBody String assign(Role role) {
		List<RolePermissionDataControl> newRList = new ArrayList<RolePermissionDataControl>();
		List<RolePermissionDataControl> delRList = new ArrayList<RolePermissionDataControl>();
		
		List<RolePermissionDataControl> hasRpdcs = rolePermissionDataControlService.findByRolePermissionRoleId(role.getId());
		
		if (role.getRolePermissions().isEmpty()) {
			//delRList.addAll(hasRpdcs);
		}
		
		for (RolePermission rolePermission : role.getRolePermissions()) {
			// ??????
			for (RolePermissionDataControl rpdc : rolePermission.getRolePermissionDataControls()) {
				boolean isHas = false;
				for (RolePermissionDataControl hasRpdc : hasRpdcs) {
					if (rpdc.getDataControl().getId().equals(hasRpdc.getDataControl().getId())
							&& rpdc.getRolePermission().getId().equals(hasRpdc.getRolePermission().getId())) {
						isHas = true;
						hasRpdcs.remove(hasRpdc);
						break;
					}
				}
				
				if (!isHas) {
					newRList.add(rpdc);
				}
			}
		}
		
		// ??????
		for (RolePermissionDataControl hasRpdc : hasRpdcs) {
			boolean isDelete = true;
			for (RolePermission rolePermission : role.getRolePermissions()) {
				for (RolePermissionDataControl rpdc : rolePermission.getRolePermissionDataControls()) {
					if (rpdc.getDataControl().getId().equals(hasRpdc.getDataControl().getId())
							&& rpdc.getRolePermission().getId().equals(hasRpdc.getRolePermission().getId())) {
						isDelete = false;
						break;
					}
				}
				
				if (!isDelete) {
					break;
				}
			}
			
			if (isDelete) {
				delRList.add(hasRpdc);
			}
		}
		
		rolePermissionDataControlService.save(newRList);
		rolePermissionDataControlService.delete(delRList);
		
		Role oldRole = roleService.get(role.getId());
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{oldRole.getName()}));
		return AjaxObject.newOk("???????????????????????????").toString();
	}
	
	@RequiresPermissions(value={"Role:assign"})
	@RequestMapping(value="/lookup", method={RequestMethod.GET, RequestMethod.POST})
	public String lookup(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<DataControl> specification = DynamicSpecifications.bySearchFilter(request, DataControl.class);
		List<DataControl> dataControls = dataControlService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("dataControls", dataControls);

		return LOOKUP_DATA_CONTROL;
	}
}
