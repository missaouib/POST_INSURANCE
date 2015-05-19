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
import com.gdpost.web.entity.member.TblMemberDataTemplateField;
import com.gdpost.web.entity.member.TblMemberDataTemplateFieldRule;
import com.gdpost.web.entity.member.TblMemberUser;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.uploaddatamanage.TemplateFieldRuleService;
import com.gdpost.web.service.uploaddatamanage.TemplateFieldService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.persistence.DynamicSpecifications;

@Controller
@RequestMapping("/uploaddatamanage/templatefieldrule")
public class TemplateFieldRuleController {
	private static final Logger LOG = LoggerFactory.getLogger(TemplateController.class);
	
	@Autowired
	private TemplateFieldRuleService ruleService;
	
	@Autowired
	private TemplateFieldService fieldService;
	
	private static final String CREATE = "uploaddatamanage/templatefieldrule/create";
	private static final String UPDATE = "uploaddatamanage/templatefieldrule/update";
	private static final String LIST = "uploaddatamanage/templatefieldrule/list";
	private static final String VIEW = "uploaddatamanage/templatefieldrule/view";
	
	@InitBinder
	public void initListBinder(WebDataBinder binder) {
		// 设置需要包裹的元素个数，默认为256
	    binder.setAutoGrowCollectionLimit(5000);
	}
	
	@RequiresPermissions("template:edit")
	@RequestMapping(value="/create/{id}", method=RequestMethod.GET)
	public String preCreate(@PathVariable Long id, Map<String, Object> map) {
		TblMemberDataTemplateField field = fieldService.get(id);
		TblMemberDataTemplateFieldRule rule = new TblMemberDataTemplateFieldRule();
		rule.setTblMemberDataTemplateField(field);
		map.put("rule", rule);
		return CREATE;
	}
	
	@Log(message="添加{0}模板{1}字段列规则{2}。")
	@RequiresPermissions("template:edit")
	@RequestMapping(value="/create/{id}", method=RequestMethod.POST)
	public @ResponseBody String create(@PathVariable Long id, @Valid TblMemberDataTemplateFieldRule rule) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		TblMember tblMember = shiroUser.getMemberUser().getTblMember();
		TblMemberDataTemplateField field = fieldService.get(id);
		if(field.getTblMemberDataTemplate().getTblMember().getId() == tblMember.getId()) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			rule.setCreateDate(timestamp);
			Long createdBy = shiroUser.getMemberUser().getId();
			Integer iCreatedBy = new Integer(createdBy.intValue());
			rule.setCreatedBy(iCreatedBy);
			rule.setTblMemberDataTemplateField(field);
			
			ruleService.saveOrUpdate(rule);
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{field.getTblMemberDataTemplate().getTemplateName(), field.getMapColumn(), rule.getRuleName()}));
		return AjaxObject.newOk("添加规则成功！").toString();
	}
	
	@RequiresPermissions("template:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		TblMemberDataTemplateFieldRule rule = ruleService.get(id);
		map.put("rule", rule);
		return UPDATE;
	}
	
	@ModelAttribute("preloadTemplateFieldRule")
	public TblMemberDataTemplateFieldRule preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			TblMemberDataTemplateFieldRule rule = ruleService.get(id);
			return rule;
		}
		return null;
	}
	
	@Log(message="修改了{0}模板{1}字段的列规则{2}。")
	@RequiresPermissions("template:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadTemplateFieldRule")TblMemberDataTemplateFieldRule rule) {
		// 检验是否是该用户的模板
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		TblMember tblMember = shiroUser.getMemberUser().getTblMember();
		if(rule.getTblMemberDataTemplateField().getTblMemberDataTemplate().getTblMember().getId() == tblMember.getId()) {
			rule.setModifyBy(shiroUser.getMemberUser().getId());
			rule.setModifyDate(new Timestamp(System.currentTimeMillis()));

			ruleService.saveOrUpdate(rule);
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{rule.getTblMemberDataTemplateField().getTblMemberDataTemplate().getTemplateName(), rule.getTblMemberDataTemplateField().getMapColumn(), rule.getRuleName()}));
		return AjaxObject.newOk("修改规则成功！").toString();
	}
	
	@Log(message="修改了{0}模板{1}字段的列规则{2}。")
	@RequiresPermissions("template:edit")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		TblMemberDataTemplateFieldRule rule = ruleService.get(id);
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();
		if(user.getTblMember().getId() == rule.getTblMemberDataTemplateField().getTblMemberDataTemplate().getTblMember().getId()) {
			ruleService.delete(rule.getId());
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{rule.getTblMemberDataTemplateField().getTblMemberDataTemplate().getTemplateName(), rule.getTblMemberDataTemplateField().getMapColumn(), rule.getRuleName()}));
		return AjaxObject.newOk("删除规则成功！").setCallbackType("").setNavTabId("templatefieldrule_navTab").toString();
	}
	
	@RequiresPermissions("template:show")
	@RequestMapping(value="/list/{id}", method={RequestMethod.GET, RequestMethod.POST})
	public String list(@PathVariable Long id, ServletRequest request, Page page, Map<String, Object> map) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();
		if(user.getTblMember().getId() == fieldService.get(id).getTblMemberDataTemplate().getTblMember().getId()) {
			Specification<TblMemberDataTemplateFieldRule> specification = DynamicSpecifications.bySearchFilter(request, TblMemberDataTemplateFieldRule.class,
					new SearchFilter("tblMemberDataTemplateField.id", Operator.EQ, id));
			
			page.setOrderDirection("ASC");
			page.setOrderField("id");
			List<TblMemberDataTemplateFieldRule> rules = ruleService.findAll(specification, page);
	
			map.put("page", page);
			map.put("rules", rules);
			map.put("fieldID", id);
		}

		return LIST;
	}
	
	@RequiresPermissions("template:edit")
	@RequestMapping(value="/view/{id}", method={RequestMethod.GET})
	public String view(@PathVariable Long id, Map<String, Object> map) {
		TblMemberDataTemplateFieldRule rule = ruleService.get(id);
		
		map.put("rule", rule);
		return VIEW;
	}
}
