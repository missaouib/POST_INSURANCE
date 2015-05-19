package com.gdpost.web.controller.uploaddatamanage;

import java.sql.Timestamp;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.web.entity.member.TblMember;
import com.gdpost.web.entity.member.TblMemberDataTemplate;
import com.gdpost.web.entity.member.TblMemberDataTemplateField;
import com.gdpost.web.entity.member.TblMemberUser;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.uploaddatamanage.TemplateFieldService;
import com.gdpost.web.service.uploaddatamanage.TemplateService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.persistence.DynamicSpecifications;

@Controller
@RequestMapping("/uploaddatamanage/templatefield")
public class TemplateFieldController {
	private static final Logger LOG = LoggerFactory.getLogger(TemplateController.class);
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private TemplateFieldService fieldService;
	
	private static final String UPDATE = "uploaddatamanage/templatefield/update";
	private static final String LIST = "uploaddatamanage/templatefield/list";
	
	@InitBinder
	public void initListBinder(WebDataBinder binder) {
		// 设置需要包裹的元素个数，默认为256
	    binder.setAutoGrowCollectionLimit(5000);
	}
	
	@RequiresPermissions("template:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		TblMemberDataTemplateField field = fieldService.get(id);
		map.put("field", field);
		return UPDATE;
	}
	
	@ModelAttribute("preloadTemplateField")
	public TblMemberDataTemplateField preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			TblMemberDataTemplateField field = fieldService.get(id);
			return field;
		}
		return null;
	}
	
	@Log(message="修改了{0}模板{1}字段的信息。")
	@RequiresPermissions("template:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadTemplateField")TblMemberDataTemplateField field) {
		// 检验是否是该用户的模板
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		TblMember tblMember = shiroUser.getMemberUser().getTblMember();
		if(field.getTblMemberDataTemplate().getTblMember().getId() == tblMember.getId()) {
			field.setModifyBy(shiroUser.getMemberUser().getId());
			field.setModifyDate(new Timestamp(System.currentTimeMillis()));
			
			fieldService.saveOrUpdate(field);
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{field.getTblMemberDataTemplate().getTemplateName(), field.getMapColumn()}));
		return AjaxObject.newOk("修改模板成功！").toString();
	}
	
	@RequiresPermissions("template:show")
	@RequestMapping(value="/list/{id}", method={RequestMethod.GET, RequestMethod.POST})
	public String list(@PathVariable Long id, ServletRequest request, Page page, Map<String, Object> map) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();
		TblMemberDataTemplate template = templateService.get(id);
		if(user.getTblMember().getId() == template.getTblMember().getId()) {
		Specification<TblMemberDataTemplateField> specification = DynamicSpecifications.bySearchFilter(request, TblMemberDataTemplateField.class,
				new SearchFilter("tblMemberDataTemplate.id", Operator.EQ, id));
		
			page.setOrderDirection("ASC");
			page.setOrderField("field");
			List<TblMemberDataTemplateField> fields = fieldService.findAll(specification, page);
			map.put("page", page);
			map.put("fields", fields);
			map.put("templateid", id);
		}
		return LIST;
	}
}
