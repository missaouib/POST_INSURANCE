package com.sendtend.web.controller.uploaddatamanage;

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

import com.sendtend.utils.SecurityUtils;
import com.sendtend.web.entity.member.TblMember;
import com.sendtend.web.entity.member.TblMemberDataTemplate;
import com.sendtend.web.entity.member.TblMemberDataTemplateField;
import com.sendtend.web.entity.member.TblMemberDataTemplateFieldRule;
import com.sendtend.web.entity.member.TblMemberUser;
import com.sendtend.web.log.Log;
import com.sendtend.web.log.LogMessageObject;
import com.sendtend.web.log.impl.LogUitls;
import com.sendtend.web.service.uploaddatamanage.TemplateFieldRuleService;
import com.sendtend.web.service.uploaddatamanage.TemplateFieldService;
import com.sendtend.web.service.uploaddatamanage.TemplateService;
import com.sendtend.web.shiro.ShiroUser;
import com.sendtend.web.util.dwz.AjaxObject;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.persistence.DynamicSpecifications;
import com.sendtend.web.util.persistence.SearchFilter;
import com.sendtend.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/uploaddatamanage/template")
public class TemplateController {
	private static final Logger LOG = LoggerFactory.getLogger(TemplateController.class);
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private TemplateFieldService fieldService;
	
	@Autowired
	private TemplateFieldRuleService ruleService;
	
	private static final String CREATE = "uploaddatamanage/template/create";
	private static final String COPY = "uploaddatamanage/template/copy";
	private static final String UPDATE = "uploaddatamanage/template/update";
	private static final String LIST = "uploaddatamanage/template/list";
	private static final String VIEW = "uploaddatamanage/template/view";
	
	@InitBinder
	public void initListBinder(WebDataBinder binder) {
		// 设置需要包裹的元素个数，默认为256
	    binder.setAutoGrowCollectionLimit(5000);
	}
	
	@RequiresPermissions("template:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		return CREATE;
	}
	
	@Log(message="添加了{0}模板。")
	@RequiresPermissions("template:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid TblMemberDataTemplate template) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		TblMember tblMember = shiroUser.getMemberUser().getTblMember();
		template.setTblMember(tblMember);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		template.setCreateDate(timestamp);
		Long createdBy = shiroUser.getMemberUser().getId();
		Integer iCreatedBy = new Integer(createdBy.intValue());
		template.setCreatedBy(iCreatedBy);
		
		// 如果是默认模板，则更新其他为非默认状态
		if(template.getStatus().intValue() == 1) {
			List<TblMemberDataTemplate> list = templateService.findAll(tblMember.getId());
			for(TblMemberDataTemplate item : list) {
				item.setStatus(new Integer(0));
				templateService.saveOrUpdate(item);
			}
		}
		
		templateService.saveOrUpdate(template);
		
		// 添加默认字段设置
		TblMemberDataTemplateField field = new TblMemberDataTemplateField();
		
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("店名");
		field.setField(new Integer(1));
		field.setFieldName("dm");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("药店编码");
		field.setField(new Integer(2));
		field.setFieldName("ydbm");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("品类大类");
		field.setField(new Integer(3));
		field.setFieldName("pldl");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("品类");
		field.setField(new Integer(4));
		field.setFieldName("pl");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("品类子类");
		field.setField(new Integer(5));
		field.setFieldName("plzl");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("品名");
		field.setField(new Integer(6));
		field.setFieldName("pm");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("药品编码");
		field.setField(new Integer(7));
		field.setFieldName("ypbm");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("医保");
		field.setField(new Integer(8));
		field.setFieldName("yb");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("处方药/非处方药");
		field.setField(new Integer(9));
		field.setFieldName("otc");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("厂家");
		field.setField(new Integer(10));
		field.setFieldName("cj");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("规格");
		field.setField(new Integer(11));
		field.setFieldName("gg");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("单价");
		field.setField(new Integer(12));
		field.setFieldName("dj");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("成本价");
		field.setField(new Integer(13));
		field.setFieldName("cbj");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("毛利率");
		field.setField(new Integer(14));
		field.setFieldName("mll");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("销售量");
		field.setField(new Integer(15));
		field.setFieldName("xsl");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("销售额");
		field.setField(new Integer(16));
		field.setFieldName("xse");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("月初库存");
		field.setField(new Integer(17));
		field.setFieldName("yckc");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("进货量");
		field.setField(new Integer(18));
		field.setFieldName("jhl");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("月末库存");
		field.setField(new Integer(19));
		field.setFieldName("ymkc");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("连锁");
		field.setField(new Integer(20));
		field.setFieldName("ls");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("城市");
		field.setField(new Integer(21));
		field.setFieldName("cs");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("条码");
		field.setField(new Integer(22));
		field.setFieldName("tm");
		fieldService.saveOrUpdate(field);
		
		field = new TblMemberDataTemplateField();
		field.setTblMemberDataTemplate(template);
		field.setCreateDate(timestamp);
		field.setCreatedBy(createdBy);
		field.setMapColumn("单位");
		field.setField(new Integer(23));
		field.setFieldName("dw");
		fieldService.saveOrUpdate(field);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{template.getTemplateName()}));
		return AjaxObject.newOk("添加模板成功！").toString();
	}
	
	@RequiresPermissions("template:save")
	@RequestMapping(value="/copy/{templateId}", method=RequestMethod.GET)
	public String preCopy(@PathVariable Long templateId, ServletRequest request, Map<String, Object> map) {
		request.setAttribute("templateId", templateId);
		return COPY;
	}
	
	@Log(message="添加了{0}模板。")
	@RequiresPermissions("template:save")
	@RequestMapping(value="/copy", method=RequestMethod.POST)
	public @ResponseBody String copy(@Valid TblMemberDataTemplate template, ServletRequest request) {
		TblMemberDataTemplate oldTemplate = templateService.get(new Long(request.getParameter("templateId")));
		template.setPassword(oldTemplate.getPassword());
		template.setTemplateType(oldTemplate.getTemplateType());
		template.setStatus(0);
		template.setUserName(oldTemplate.getUserName());
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		TblMember tblMember = shiroUser.getMemberUser().getTblMember();
		template.setTblMember(tblMember);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		template.setCreateDate(timestamp);
		Long createdBy = shiroUser.getMemberUser().getId();
		Integer iCreatedBy = new Integer(createdBy.intValue());
		template.setCreatedBy(iCreatedBy);
		
		// 如果是默认模板，则更新其他为非默认状态
		if(template.getStatus().intValue() == 1) {
			List<TblMemberDataTemplate> list = templateService.findAll(tblMember.getId());
			for(TblMemberDataTemplate item : list) {
				item.setStatus(new Integer(0));
				templateService.saveOrUpdate(item);
			}
		}
		//LOG.debug("------000------" + template.getId());
		template = templateService.saveOrUpdate(template);
		//LOG.debug("------111------" + template.getId());
		// 添加默认字段设置
		List<TblMemberDataTemplateField> ofields = fieldService.getByTemplateId(oldTemplate.getId());
		//LOG.debug("-------222-----" + ofields.size());
		TblMemberDataTemplateField cfield = null;
		TblMemberDataTemplateFieldRule orule = null;
		TblMemberDataTemplateFieldRule crule = null;
		for(TblMemberDataTemplateField field:ofields) {
			cfield = new TblMemberDataTemplateField();
			cfield.setTblMemberDataTemplate(template);
			cfield.setCreateDate(new Timestamp(System.currentTimeMillis()));
			cfield.setCreatedBy(new Long(iCreatedBy));
			cfield.setTblMemberDataTemplate(template);
			cfield.setMapColumn(field.getMapColumn());
			cfield.setField(field.getField());
			cfield.setFieldName(field.getFieldName());
			cfield.setColumnName(field.getColumnName());
			cfield.setDataColumn(field.getDataColumn());
			cfield.setIsStaticValue(field.getIsStaticValue());
			cfield.setIsUsingColumn(field.getIsUsingColumn());
			cfield.setIsUsingFilename(field.getIsUsingFilename());
			cfield.setIsUsingMapcolumn(field.getIsUsingMapcolumn());
			cfield.setIsUsingMulticolumn(field.getIsUsingMulticolumn());
			cfield.setIsUsingSheetname(field.getIsUsingSheetname());
			cfield.setStaticValue(field.getStaticValue());
			//LOG.debug("------333------" + cfield.getId());
			cfield = fieldService.saveOrUpdate(cfield);
			//LOG.debug("------444------" + cfield.getId());
			
			//列规则
			orule = ruleService.findByTblMemberDataTemplateFieldId(field.getId());
			//LOG.debug("------555------" + orule==null?"rule null":"rule not null");
			if(orule != null) {
				crule = new TblMemberDataTemplateFieldRule();
				crule.setCreateDate(timestamp);
				crule.setCreatedBy(iCreatedBy);
				crule.setTblMemberDataTemplateField(cfield);
				crule.setRuleName(orule.getRuleName());
				crule.setSplitChar(orule.getSplitChar());
				crule.setValueIndex(orule.getValueIndex());
				
				ruleService.saveOrUpdate(crule);
			}
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{template.getTemplateName()}));
		return AjaxObject.newOk("添加模板成功！").toString();
	}
	
	@RequiresPermissions("template:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		TblMemberDataTemplate template = templateService.get(id);
		map.put("template", template);
		return UPDATE;
	}
	
	@ModelAttribute("preloadTemplate")
	public TblMemberDataTemplate preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			TblMemberDataTemplate template = templateService.get(id);
			return template;
		}
		return null;
	}
	
	@Log(message="修改了{0}模板的信息。")
	@RequiresPermissions("template:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadTemplate")TblMemberDataTemplate template) {
		// 检验是否是该用户的模板
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		TblMember tblMember = shiroUser.getMemberUser().getTblMember();
		if(template.getTblMember().getId() == tblMember.getId()) {
			template.setModifyBy(shiroUser.getMemberUser().getId());
			template.setModifyDate(new Timestamp(System.currentTimeMillis()));
			
			// 如果是默认模板，则更新其他为非默认状态
			if(template.getStatus().intValue() == 1) {
				List<TblMemberDataTemplate> list = templateService.findAll(tblMember.getId());
				for(TblMemberDataTemplate item : list) {
					item.setStatus(new Integer(0));
					templateService.saveOrUpdate(item);
				}
			}
			
			template.setStatus(new Integer(1));
			templateService.saveOrUpdate(template);
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{template.getTemplateName()}));
		return AjaxObject.newOk("修改模板成功！").toString();
	}
	
	@Log(message="删除了{0}模板。")
	@RequiresPermissions("template:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		TblMemberDataTemplate template = templateService.get(id);
		templateService.delete(template.getId());

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{template.getTemplateName()}));
		return AjaxObject.newOk("删除模板成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("template:show")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();
		Specification<TblMemberDataTemplate> specification = DynamicSpecifications.bySearchFilter(request, TblMemberDataTemplate.class,
				new SearchFilter("tblMember.id", Operator.EQ, user.getTblMember().getId()));
		List<TblMemberDataTemplate> templates = templateService.findAll(specification, page);

		map.put("page", page);
		map.put("templates", templates);
		return LIST;
	}
	
	@RequiresPermissions("template:view")
	@RequestMapping(value="/view/{id}", method={RequestMethod.GET})
	public String view(@PathVariable Long id, Map<String, Object> map) {
		TblMemberDataTemplate template = templateService.get(id);
		
		map.put("template", template);
		return VIEW;
	}
}
