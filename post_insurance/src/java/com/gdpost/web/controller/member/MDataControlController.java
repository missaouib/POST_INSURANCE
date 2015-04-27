/**
 * 
 */
package com.gdpost.web.controller.member;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

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

import com.gdpost.web.entity.member.TblMemberDataControl;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.member.MDataControlService;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;

@Controller
@RequestMapping("/members/dataControl")
public class MDataControlController {

	@Autowired
	private MDataControlService dataControlService;
	
	private static final String CREATE = "members/dataControl/create";
	private static final String UPDATE = "members/dataControl/update";
	private static final String LIST = "members/dataControl/list";
	private static final String VIEW = "members/dataControl/view";
	
	
	@RequiresPermissions("TblMemberDataControl:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		return CREATE;
	}
	
	@Log(message="添加了id={0}数据权限。")
	@RequiresPermissions("TblMemberDataControl:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid TblMemberDataControl dataControl) {
		dataControlService.saveOrUpdate(dataControl);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{dataControl.getId()}));
		return AjaxObject.newOk("数据权限添加成功！").toString();
	}
	
	@ModelAttribute("preloadTblMemberDataControl")
	public TblMemberDataControl preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			TblMemberDataControl dataControl = dataControlService.get(id);
			return dataControl;
		}
		return null;
	}
	
	@RequiresPermissions("TblMemberDataControl:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		TblMemberDataControl dataControl = dataControlService.get(id);
		map.put("dataControl", dataControl);
		return UPDATE;
	}
	
	@Log(message="修改了id={0}数据权限的信息。")
	@RequiresPermissions("TblMemberDataControl:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadTblMemberDataControl")TblMemberDataControl dataControl) {
		dataControlService.saveOrUpdate(dataControl);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{dataControl.getId()}));
		return AjaxObject.newOk("数据权限修改成功！").toString();
	}

	@Log(message="删除了id={0}数据权限。")
	@RequiresPermissions("TblMemberDataControl:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		dataControlService.delete(id);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{id}));
		return AjaxObject.newOk("数据权限删除成功！").setCallbackType("").toString();
	}
	
	@Log(message="批量删除了id={0}数据权限。")
	@RequiresPermissions("TblMemberDataControl:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			TblMemberDataControl dataControl = dataControlService.get(ids[i]);
			dataControlService.delete(dataControl.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(ids)}));
		return AjaxObject.newOk("数据权限删除成功！").setCallbackType("").toString();
	}

	@RequiresPermissions("TblMemberDataControl:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<TblMemberDataControl> specification = DynamicSpecifications.bySearchFilter(request, TblMemberDataControl.class);
		List<TblMemberDataControl> dataControls = dataControlService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("dataControls", dataControls);

		return LIST;
	}
	
	@RequiresPermissions("TblMemberDataControl:view")
	@RequestMapping(value="/view/{id}", method={RequestMethod.GET})
	public String view(@PathVariable Long id, Map<String, Object> map) {
		TblMemberDataControl dataControl = dataControlService.get(id);
		map.put("dataControl", dataControl);
		return VIEW;
	}
}
