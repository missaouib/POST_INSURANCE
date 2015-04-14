/**
 * 
 */
package com.sendtend.web.controller.member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sendtend.utils.SecurityUtils;
import com.sendtend.web.entity.member.TblMemberDataControl;
import com.sendtend.web.entity.member.TblMemberModule;
import com.sendtend.web.entity.member.TblMemberRole;
import com.sendtend.web.entity.member.TblMemberRolePermission;
import com.sendtend.web.entity.member.TblMemberRolePermissionDataControl;
import com.sendtend.web.entity.member.TblMemberUser;
import com.sendtend.web.log.Log;
import com.sendtend.web.log.LogMessageObject;
import com.sendtend.web.log.impl.LogUitls;
import com.sendtend.web.service.member.MDataControlService;
import com.sendtend.web.service.member.MModuleService;
import com.sendtend.web.service.member.MRolePermissionDataControlService;
import com.sendtend.web.service.member.MRolePermissionService;
import com.sendtend.web.service.member.MRoleService;
import com.sendtend.web.util.dwz.AjaxObject;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.persistence.DynamicSpecifications;
import com.sendtend.web.util.persistence.SearchFilter;
import com.sendtend.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/members/role")
public class MRoleController {

	private static final Logger LOG = LoggerFactory.getLogger(MRoleController.class);
	
	@Autowired
	private MRoleService roleService;
	
	@Autowired
	private MRolePermissionService rolePermissionService;
	
	@Autowired
	private MRolePermissionDataControlService tblMemberRolePermissionDataControlService;
	
	@Autowired
	private MModuleService moduleService;
	
	@Autowired
	private MDataControlService dataControlService;
	
	private static final String CREATE = "members/role/create";
	private static final String UPDATE = "members/role/update";
	private static final String WEBCREATE = "members/role/webcreate";
	private static final String WEBUPDATE = "members/role/webupdate";
	private static final String LIST = "members/role/list";
	private static final String WEBLIST = "members/role/weblist";
	private static final String VIEW = "members/role/view";
	private static final String ASSIGN_DATA_CONTROL = "members/role/assign_data_control";
	private static final String LOOKUP_DATA_CONTROL = "members/role/lookup_data_control";
	
	@InitBinder
	public void initListBinder(WebDataBinder binder) {
		// 设置需要包裹的元素个数，默认为256
	    binder.setAutoGrowCollectionLimit(5000);
	}
	
	@RequiresPermissions("TblMemberRole:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		map.put("module", moduleService.getTree());
		return CREATE;
	}
	
	@RequiresPermissions("TblMemberRole:save")
	@RequestMapping(value="/webCreate", method=RequestMethod.GET)
	public String preWebCreate(Map<String, Object> map) {
		map.put("module", moduleService.getTree());
		return WEBCREATE;
	}
	
	@Log(message="添加了{0}角色。")
	@RequiresPermissions("TblMemberRole:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid TblMemberRole role) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();
		if(user != null) {
			role.setMemberId(user.getTblMember().getId());
		} else {
			role.setMemberId(new Long(-1));
		}
		List<TblMemberRolePermission> rolePermissions = new ArrayList<TblMemberRolePermission>();
		for (TblMemberRolePermission rolePermission : role.getTblMemberRolePermissions()) {
			if (rolePermission.getTblMemberPermission() != null && rolePermission.getTblMemberPermission().getId() != null) {
				rolePermissions.add(rolePermission);
			}
		}
		
		for (TblMemberRolePermission rolePermission : rolePermissions) {
			rolePermission.setTblMemberRole(role);
		}
		
		roleService.saveOrUpdate(role);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{role.getName()}));
		return AjaxObject.newOk("添加角色成功！").toString();
	}
	
	@RequiresPermissions("TblMemberRole:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		TblMemberRole role = roleService.get(id);
		TblMemberModule module = moduleService.getTree();
		map.put("module", module);
		
		map.put("role", role);
		return UPDATE;
	}
	
	@RequiresPermissions("TblMemberRole:edit")
	@RequestMapping(value="/webUpdate/{id}", method=RequestMethod.GET)
	public String preWebUpdate(@PathVariable Long id, Map<String, Object> map) {
		TblMemberRole role = roleService.get(id);
		TblMemberModule module = moduleService.getTree();
		map.put("module", module);
		
		map.put("role", role);
		return WEBUPDATE;
	}
	
	@Log(message="修改了{0}角色的信息。")
	@RequiresPermissions("TblMemberRole:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid TblMemberRole role) {
		TblMemberRole oldRole = roleService.get(role.getId());
		oldRole.setName(role.getName());
		oldRole.setDescription(role.getDescription());
		
		List<TblMemberRolePermission> newRList = new ArrayList<TblMemberRolePermission>();
		List<TblMemberRolePermission> delRList = new ArrayList<TblMemberRolePermission>();
		
		List<TblMemberRolePermission> hasRolePermissions = rolePermissionService.findByRoleId(role.getId());
		for (TblMemberRolePermission rolePermission : role.getTblMemberRolePermissions()) {
			if (rolePermission.getId() == null && rolePermission.getTblMemberPermission() != null) {
				rolePermission.setTblMemberRole(oldRole);
				newRList.add(rolePermission);
			} else if (rolePermission.getId() != null && rolePermission.getTblMemberPermission() == null) {
				for (TblMemberRolePermission rp : hasRolePermissions) {
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
		return AjaxObject.newOk("修改角色成功！").toString();
	}
	
	@Log(message="删除了{0}角色。")
	@RequiresPermissions("TblMemberRole:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		TblMemberRole role = roleService.get(id);
		roleService.delete(role.getId());

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{role.getName()}));
		return AjaxObject.newOk("删除角色成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("TblMemberRole:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<TblMemberRole> specification = DynamicSpecifications.bySearchFilter(request, TblMemberRole.class,
				new SearchFilter("memberId", Operator.EQ, new Long(-1)));
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();
		if(user != null) {
			specification = DynamicSpecifications.bySearchFilter(request, TblMemberRole.class,
					new SearchFilter("memberId", Operator.EQ, user.getTblMember().getId()));
		}
		List<TblMemberRole> roles = roleService.findByExample(specification, page);

		map.put("page", page);
		map.put("roles", roles);
		return LIST;
	}
	
	@RequiresPermissions("TblMemberRole:view")
	@RequestMapping(value="/weblist", method={RequestMethod.GET, RequestMethod.POST})
	public String weblist(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<TblMemberRole> specification = DynamicSpecifications.bySearchFilter(request, TblMemberRole.class,
				new SearchFilter("memberId", Operator.EQ, new Long(-1)));
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();
		if(user != null) {
			specification = DynamicSpecifications.bySearchFilter(request, TblMemberRole.class,
					new SearchFilter("memberId", Operator.EQ, user.getTblMember().getId()));
		}
		List<TblMemberRole> roles = roleService.findByExample(specification, page);

		map.put("page", page);
		map.put("roles", roles);
		return WEBLIST;
	}
	
	@RequiresPermissions("TblMemberRole:view")
	@RequestMapping(value="/view/{id}", method={RequestMethod.GET})
	public String view(@PathVariable Long id, Map<String, Object> map) {
		TblMemberRole role = roleService.get(id);
		
		map.put("module", moduleService.getTree());
		map.put("role", role);
		return VIEW;
	}
	
	@RequiresPermissions("TblMemberRole:assign")
	@RequestMapping(value="/assign/{id}", method=RequestMethod.GET)
	public String preAssign(@PathVariable Long id, Map<String, Object> map) {
		TblMemberRole role = roleService.get(id);
		Map<TblMemberModule, List<TblMemberRolePermission>> mpMap = new LinkedHashMap<TblMemberModule, List<TblMemberRolePermission>>(); 
		for (TblMemberRolePermission rp : role.getTblMemberRolePermissions()) {
			TblMemberModule module = rp.getTblMemberPermission().getTblMemberModule();
			
			List<TblMemberRolePermission> rps = mpMap.get(module);
			if (rps == null) {
				rps = new ArrayList<TblMemberRolePermission>();
				
				mpMap.put(module, rps);
			}
			
			rps.add(rp);
		}
		
		map.put("role", role);
		map.put("mpMap", mpMap);
		return ASSIGN_DATA_CONTROL;
	}
	
	@Log(message="修改了{0}角色的数据权限。")
	@RequiresPermissions("TblMemberRole:assign")
	@RequestMapping(value="/assign", method=RequestMethod.POST)
	public @ResponseBody String assign(TblMemberRole role) {
		List<TblMemberRolePermissionDataControl> newRList = new ArrayList<TblMemberRolePermissionDataControl>();
		List<TblMemberRolePermissionDataControl> delRList = new ArrayList<TblMemberRolePermissionDataControl>();
		
		List<TblMemberRolePermissionDataControl> hasRpdcs = tblMemberRolePermissionDataControlService.findByTblMemberRolePermissionTblMemberRoleId(role.getId());
		
		if (role.getTblMemberRolePermissions().isEmpty()) {
			LOG.debug("------------WARN----------- MEMBER ROLE PERMISSIONS IS EMPTY");
			//delRList.addAll(hasRpdcs);
		}
		
		for (TblMemberRolePermission rolePermission : role.getTblMemberRolePermissions()) {
			// 新增TblMemberRolePermissionDataControl
			for (TblMemberRolePermissionDataControl rpdc : rolePermission.getTblMemberRolePermissionDataControls()) {
				boolean isHas = false;
				for (TblMemberRolePermissionDataControl hasRpdc : hasRpdcs) {
					if (rpdc.getTblMemberDataControl().getId().equals(hasRpdc.getTblMemberDataControl().getId())
							&& rpdc.getTblMemberRolePermission().getId().equals(hasRpdc.getTblMemberRolePermission().getId())) {
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
		// 删除
		for (TblMemberRolePermissionDataControl hasRpdc : hasRpdcs) {
			boolean isDelete = true;
			for (TblMemberRolePermission rolePermission : role.getTblMemberRolePermissions()) {
				for (TblMemberRolePermissionDataControl rpdc : rolePermission.getTblMemberRolePermissionDataControls()) {
					if (rpdc.getTblMemberDataControl().getId().equals(hasRpdc.getTblMemberDataControl().getId())
							&& rpdc.getTblMemberRolePermission().getId().equals(hasRpdc.getTblMemberRolePermission().getId())) {
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
		
		tblMemberRolePermissionDataControlService.save(newRList);
		tblMemberRolePermissionDataControlService.delete(delRList);
		
		TblMemberRole oldRole = roleService.get(role.getId());
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{oldRole.getName()}));
		return AjaxObject.newOk("分配数据权限成功！").toString();
	}
	
	@RequiresPermissions(value={"TblMemberRole:assign"})
	@RequestMapping(value="/lookup", method={RequestMethod.GET, RequestMethod.POST})
	public String lookup(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<TblMemberDataControl> specification = DynamicSpecifications.bySearchFilter(request, TblMemberDataControl.class);
		List<TblMemberDataControl> dataControls = dataControlService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("dataControls", dataControls);

		return LOOKUP_DATA_CONTROL;
	}
}
