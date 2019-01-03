/**
 * 
 * ==========================================================
 * 保全管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.web.entity.basedata.BankCode;
import com.gdpost.web.entity.basedata.CallDealType;
import com.gdpost.web.entity.basedata.CheckFixType;
import com.gdpost.web.entity.basedata.ConservationError;
import com.gdpost.web.entity.basedata.IssueType;
import com.gdpost.web.entity.basedata.Prd;
import com.gdpost.web.entity.basedata.RenewalType;
import com.gdpost.web.entity.basedata.Sales;
import com.gdpost.web.entity.component.CsAddr;
import com.gdpost.web.entity.component.Staff;
import com.gdpost.web.entity.main.ConservationType;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.ProvOrgCode;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.OrganizationService;
import com.gdpost.web.service.insurance.BaseDataService;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;

@Controller
@RequestMapping("/basedata")
public class BaseDataController {
	private static final Logger LOG = LoggerFactory.getLogger(BaseDataController.class);
	
	@Autowired
	private BaseDataService baseService;
	
	@Autowired
	private OrganizationService orgService;

	private static final String CALL_DEAL_TYPE_CREATE = "insurance/basedata/CallDealType/create";
	private static final String CALL_DEAL_TYPE_UPDATE = "insurance/basedata/CallDealType/update";
	private static final String CALL_DEAL_TYPE_LIST = "insurance/basedata/CallDealType/list";
	
	private static final String CONSERVATION_ERROR_CREATE = "insurance/basedata/ConservationError/create";
	private static final String CONSERVATION_ERROR_UPDATE = "insurance/basedata/ConservationError/update";
	private static final String CONSERVATION_ERROR_LIST = "insurance/basedata/ConservationError/list";
	
	private static final String ISSUE_TYPE_CREATE = "insurance/basedata/IssueType/create";
	private static final String ISSUE_TYPE_UPDATE = "insurance/basedata/IssueType/update";
	private static final String ISSUE_TYPE_LIST = "insurance/basedata/IssueType/list";
	
	private static final String NET_CODE_CREATE = "insurance/basedata/netcode/create";
	private static final String NET_CODE_UPDATE = "insurance/basedata/netcode/update";
	private static final String NET_CODE_LIST = "insurance/basedata/netcode/list";
	
	private static final String RENEWAL_TYPE_CREATE = "insurance/basedata/renewalType/create";
	private static final String RENEWAL_TYPE_UPDATE = "insurance/basedata/renewalType/update";
	private static final String RENEWAL_TYPE_LIST = "insurance/basedata/renewalType/list";
	
	private static final String PRD_CREATE = "insurance/basedata/product/create";
	private static final String PRD_UPDATE = "insurance/basedata/product/update";
	private static final String PRD_LIST = "insurance/basedata/product/list";
	
	private static final String CHECK_FIX_TYPE_CREATE = "insurance/basedata/checkFixType/create";
	private static final String CHECK_FIX_TYPE_UPDATE = "insurance/basedata/checkFixType/update";
	private static final String CHECK_FIX_TYPE_LIST = "insurance/basedata/checkFixType/list";
	
	private static final String CONSERVATION_TYPE_CREATE = "insurance/basedata/ConservationType/create";
	private static final String CONSERVATION_TYPE_UPDATE = "insurance/basedata/ConservationType/update";
	private static final String CONSERVATION_TYPE_LIST = "insurance/basedata/ConservationType/list";
	
	private static final String PROV_ORG_CODE_CREATE = "insurance/basedata/provOrgCode/create";
	private static final String PROV_ORG_CODE_UPDATE = "insurance/basedata/provOrgCode/update";
	private static final String PROV_ORG_CODE_LIST = "insurance/basedata/provOrgCode/list";
	
	private static final String STAFF_CREATE = "insurance/basedata/staff/create";
	private static final String STAFF_UPDATE = "insurance/basedata/staff/update";
	private static final String STAFF_LIST = "insurance/basedata/staff/list";
	
	private static final String CS_ADDR_CREATE = "insurance/basedata/csaddr/create";
	private static final String CS_ADDR_UPDATE = "insurance/basedata/csaddr/update";
	private static final String CS_ADDR_LIST = "insurance/basedata/csaddr/list";
	
	private static final String SALES_CREATE = "insurance/basedata/sales/create";
	private static final String SALES_UPDATE = "insurance/basedata/sales/update";
	private static final String SALES_LIST = "insurance/basedata/sales/list";
	/*
	 * =======================================
	 * call deal type 回访类型
	 * =======================================
	 * 
	 */
	@RequiresPermissions("CallDealType:save")
	@RequestMapping(value="/callDealType/create", method=RequestMethod.GET)
	public String preCreateCallDealType() {
		return CALL_DEAL_TYPE_CREATE;
	}
	
	@Log(message="添加了{0}回访处理类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("CallDealType:save")
	@RequestMapping(value="/callDealType/create", method=RequestMethod.POST)
	public @ResponseBody String createCallDealType(@Valid CallDealType basedata) {	
		try {
			baseService.saveOrUpdateCallDealType(basedata);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加回访处理类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{basedata.getTypeName()}));
		return AjaxObject.newOk("添加回访处理类型成功！").toString();
	}
	
	@RequiresPermissions("CallDealType:edit")
	@RequestMapping(value="/callDealType/update/{id}", method=RequestMethod.GET)
	public String preUpdateCallDealType(@PathVariable Long id, Map<String, Object> map) {
		CallDealType basedata = baseService.getCallDealType(id);
		
		map.put("basedata", basedata);
		return CALL_DEAL_TYPE_UPDATE;
	}
	
	@Log(message="修改了{0}回访处理类型的信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("CallDealType:edit")
	@RequestMapping(value="/callDealType/update", method=RequestMethod.POST)
	public @ResponseBody String updateCallDealType(CallDealType basedata) {
		LOG.debug("-----------conservateoinError:" + basedata.toString());
		baseService.saveOrUpdateCallDealType(basedata);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{basedata.getTypeName()}));
		return	AjaxObject.newOk("修改回访处理类型成功！").toString(); 
	}
	
	@Log(message="删除了{0}回访处理类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("CallDealType:delete")
	@RequestMapping(value="/callDealType/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteCallDealType(@PathVariable Long id) {
		CallDealType basedata = null;
		try {
			basedata = baseService.getCallDealType(id);
			baseService.deleteCallDealType(basedata.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除回访处理类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{basedata.getTypeName()}));
		return AjaxObject.newOk("删除回访处理类型成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}回访处理类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("CallDealType:delete")
	@RequestMapping(value="/callDealType/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyCallDealType(Long[] ids) {
		String[] callDealTypes = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				CallDealType basedata = baseService.getCallDealType(ids[i]);
				baseService.deleteCallDealType(basedata.getId());
				
				callDealTypes[i] = basedata.getTypeName();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除回访处理类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(callDealTypes)}));
		return AjaxObject.newOk("删除回访处理类型成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("CallDealType:view")
	@RequestMapping(value="/callDealType/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listCallDealType(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<CallDealType> specification = DynamicSpecifications.bySearchFilter(request, CallDealType.class);
		List<CallDealType> basedata = baseService.findByCallDealTypeExample(specification, page);

		map.put("page", page);
		map.put("basedata", basedata);
		return CALL_DEAL_TYPE_LIST;
	}
	
	/*
	 * =======================================
	 * conservation error 保全错误类型
	 * =======================================
	 * 
	 */
	@RequiresPermissions("ConservationError:save")
	@RequestMapping(value="/conservationError/create", method=RequestMethod.GET)
	public String preCreateConservationError() {
		return CONSERVATION_ERROR_CREATE;
	}
	
	@Log(message="添加了{0}保全错误类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("ConservationError:save")
	@RequestMapping(value="/conservationError/create", method=RequestMethod.POST)
	public @ResponseBody String createConservationError(@Valid ConservationError conservationError) {	
		try {
			baseService.saveOrUpdateConservationError(conservationError);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加保全错误类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{conservationError.getErrorCode()}));
		return AjaxObject.newOk("添加保全错误类型成功！").toString();
	}
	
	@RequiresPermissions("ConservationError:edit")
	@RequestMapping(value="/conservationError/update/{id}", method=RequestMethod.GET)
	public String preUpdateConservationError(@PathVariable Long id, Map<String, Object> map) {
		ConservationError basedata = baseService.getConservationError(id);
		
		map.put("basedata", basedata);
		return CONSERVATION_ERROR_UPDATE;
	}
	
	@Log(message="修改了{0}保全错误类型的信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("ConservationError:edit")
	@RequestMapping(value="/conservationError/update", method=RequestMethod.POST)
	public @ResponseBody String updateConservationError(ConservationError conservationError) {
		LOG.debug("-----------conservateoinError:" + conservationError.toString());
		baseService.saveOrUpdateConservationError(conservationError);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{conservationError.getErrorCode()}));
		return	AjaxObject.newOk("修改保全错误类型成功！").toString(); 
	}
	
	@Log(message="删除了{0}保全错误类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("ConservationError:delete")
	@RequestMapping(value="/conservationError/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteConservationError(@PathVariable Long id) {
		ConservationError conservationError = null;
		try {
			conservationError = baseService.getConservationError(id);
			baseService.deleteConservationError(conservationError.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除保全错误类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{conservationError.getErrorCode()}));
		return AjaxObject.newOk("删除保全错误类型成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}保全错误类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("ConservationError:delete")
	@RequestMapping(value="/conservationError/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyConservationError(Long[] ids) {
		String[] conservationErrors = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				ConservationError conservationError = baseService.getConservationError(ids[i]);
				baseService.deleteConservationError(conservationError.getId());
				
				conservationErrors[i] = conservationError.getErrorCode();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除保全错误类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(conservationErrors)}));
		return AjaxObject.newOk("删除保全错误类型成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("ConservationError:view")
	@RequestMapping(value="/conservationError/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listConservationError(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<ConservationError> specification = DynamicSpecifications.bySearchFilter(request, ConservationError.class);
		List<ConservationError> basedata = baseService.findByConservationErrorExample(specification, page);

		map.put("page", page);
		map.put("basedata", basedata);
		return CONSERVATION_ERROR_LIST;
	}
	
	/*
	 * =======================================
	 * ISSUE TYPE 工单类型
	 * =======================================
	 * 
	 */
	@RequiresPermissions("IssueType:save")
	@RequestMapping(value="/issueType/create", method=RequestMethod.GET)
	public String preCreateIssueType() {
		return ISSUE_TYPE_CREATE;
	}
	
	@Log(message="添加了{0}工单错误类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("IssueType:save")
	@RequestMapping(value="/issueType/create", method=RequestMethod.POST)
	public @ResponseBody String createIssueType(@Valid IssueType issueType) {	
		try {
			baseService.saveOrUpdateIssueType(issueType);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加工单错误类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issueType.getTypeName()}));
		return AjaxObject.newOk("添加工单错误类型成功！").toString();
	}
	
	@RequiresPermissions("IssueType:edit")
	@RequestMapping(value="/issueType/update/{id}", method=RequestMethod.GET)
	public String preUpdateIssueType(@PathVariable Long id, Map<String, Object> map) {
		IssueType basedata = baseService.getIssueType(id);
		
		map.put("basedata", basedata);
		return ISSUE_TYPE_UPDATE;
	}
	
	@Log(message="修改了{0}工单错误类型的信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("IssueType:edit")
	@RequestMapping(value="/issueType/update", method=RequestMethod.POST)
	public @ResponseBody String updateIssueType(IssueType issueType) {
		baseService.saveOrUpdateIssueType(issueType);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issueType.getTypeName()}));
		return	AjaxObject.newOk("修改工单错误类型成功！").toString(); 
	}
	
	@Log(message="删除了{0}工单错误类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("IssueType:delete")
	@RequestMapping(value="/issueType/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteIssueType(@PathVariable Long id) {
		IssueType issueType = null;
		try {
			issueType = baseService.getIssueType(id);
			baseService.deleteIssueType(issueType.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除工单错误类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issueType.getTypeName()}));
		return AjaxObject.newOk("删除工单错误类型成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}工单错误类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("IssueType:delete")
	@RequestMapping(value="/issueType/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyIssueType(Long[] ids) {
		String[] issueTypes = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				IssueType issueType = baseService.getIssueType(ids[i]);
				baseService.deleteIssueType(issueType.getId());
				
				issueTypes[i] = issueType.getTypeName();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除工单错误类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(issueTypes)}));
		return AjaxObject.newOk("删除工单错误类型成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("IssueType:view")
	@RequestMapping(value="/issueType/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listIssueType(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<IssueType> specification = DynamicSpecifications.bySearchFilter(request, IssueType.class);
		List<IssueType> basedata = baseService.findByIssueTypeExample(specification, page);

		map.put("page", page);
		map.put("basedata", basedata);
		return ISSUE_TYPE_LIST;
	}
	
	/*
	 * =======================================
	 * 网点映射 netcode
	 * =======================================
	 * 
	 */
	@RequiresPermissions("BankCode:save")
	@RequestMapping(value="/bankCode/create", method=RequestMethod.GET)
	public String preCreateNetCode() {
		return NET_CODE_CREATE;
	}
	
	@Log(message="添加了{0}网点对照。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("BankCode:save")
	@RequestMapping(value="/bankCode/create", method=RequestMethod.POST)
	public @ResponseBody String createNetCode(@Valid BankCode bankCode) {	
		try {
			baseService.saveOrUpdateBankCode(bankCode);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加网点对照失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{bankCode.getCpiCode()}));
		return AjaxObject.newOk("添加网点对照成功！").toString();
	}
	
	@RequiresPermissions("BankCode:edit")
	@RequestMapping(value="/bankCode/update/{id}", method=RequestMethod.GET)
	public String preUpdateNetCode(@PathVariable Long id, Map<String, Object> map) {
		BankCode basedata = baseService.getBankCode(id);
		
		map.put("basedata", basedata);
		return NET_CODE_UPDATE;
	}
	
	@Log(message="修改了{0}网点对照的信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("BankCode:edit")
	@RequestMapping(value="/bankCode/update", method=RequestMethod.POST)
	public @ResponseBody String updateNetCode(BankCode bankCode) {
		BankCode bc = baseService.getBankCode(bankCode.getId());
		Organization org = orgService.getByOrgCode(bankCode.getOrganization().getOrgCode());
		BeanUtils.copyProperties(bankCode, bc);
		bc.setOrganization(org);
		baseService.saveOrUpdateBankCode(bc);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{bankCode.getCpiCode()}));
		return	AjaxObject.newOk("修改网点对照成功！").toString(); 
	}
	
	@Log(message="删除了{0}网点对照。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("BankCode:delete")
	@RequestMapping(value="/bankCode/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteNetCode(@PathVariable Long id) {
		BankCode bankCode = null;
		try {
			bankCode = baseService.getBankCode(id);
			baseService.deleteBankCode(bankCode.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除网点对照失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{bankCode.getCpiCode()}));
		return AjaxObject.newOk("删除网点对照成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}网点对照。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("BankCode:delete")
	@RequestMapping(value="/bankCode/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyNetCode(Long[] ids) {
		String[] bankCodes = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				BankCode bankCode = baseService.getBankCode(ids[i]);
				baseService.deleteBankCode(bankCode.getId());
				
				bankCodes[i] = bankCode.getCpiCode();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除网点对照失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(bankCodes)}));
		return AjaxObject.newOk("删除网点对照成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("BankCode:view")
	@RequestMapping(value="/bankCode/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listNetCode(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<BankCode> specification = DynamicSpecifications.bySearchFilter(request, BankCode.class);
		List<BankCode> basedata = baseService.findByBankCodeExample(specification, page);

		map.put("page", page);
		map.put("basedata", basedata);
		return NET_CODE_LIST;
	}
	
	/*
	 * =======================================
	 * renewal type 催缴类型
	 * =======================================
	 * 
	 */
	@RequiresPermissions("RenewalType:save")
	@RequestMapping(value="/renewalType/create", method=RequestMethod.GET)
	public String preCreateRenewalType() {
		return RENEWAL_TYPE_CREATE;
	}
	
	@Log(message="添加了{0}催缴类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("RenewalType:save")
	@RequestMapping(value="/renewalType/create", method=RequestMethod.POST)
	public @ResponseBody String createRenewalType(@Valid RenewalType renewalType) {	
		try {
			baseService.saveOrUpdateRenewalType(renewalType);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加催缴类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{renewalType.getTypeName()}));
		return AjaxObject.newOk("添加催缴类型成功！").toString();
	}
	
	@RequiresPermissions("RenewalType:edit")
	@RequestMapping(value="/renewalType/update/{id}", method=RequestMethod.GET)
	public String preUpdateRenewalType(@PathVariable Long id, Map<String, Object> map) {
		RenewalType basedata = baseService.getRenewalType(id);
		
		map.put("basedata", basedata);
		return RENEWAL_TYPE_UPDATE;
	}
	
	@Log(message="修改了{0}催缴类型的信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("RenewalType:edit")
	@RequestMapping(value="/renewalType/update", method=RequestMethod.POST)
	public @ResponseBody String updateRenewalType(RenewalType renewalType) {
		baseService.saveOrUpdateRenewalType(renewalType);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{renewalType.getTypeName()}));
		return	AjaxObject.newOk("修改催缴类型成功！").toString(); 
	}
	
	@Log(message="删除了{0}催缴类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("RenewalType:delete")
	@RequestMapping(value="/renewalType/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteRenewalType(@PathVariable Long id) {
		RenewalType renewalType = null;
		try {
			renewalType = baseService.getRenewalType(id);
			baseService.deleteRenewalType(renewalType.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除催缴类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{renewalType.getTypeName()}));
		return AjaxObject.newOk("删除催缴类型成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}催缴类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("RenewalType:delete")
	@RequestMapping(value="/renewalType/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyRenewalType(Long[] ids) {
		String[] renewalTypes = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				RenewalType renewalType = baseService.getRenewalType(ids[i]);
				baseService.deleteRenewalType(renewalType.getId());
				
				renewalTypes[i] = renewalType.getTypeName();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除催缴类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(renewalTypes)}));
		return AjaxObject.newOk("删除催缴类型成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("RenewalType:view")
	@RequestMapping(value="/renewalType/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listRenewalType(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<RenewalType> specification = DynamicSpecifications.bySearchFilter(request, RenewalType.class);
		List<RenewalType> basedata = baseService.findByRenewalTypeExample(specification, page);

		map.put("page", page);
		map.put("basedata", basedata);
		return RENEWAL_TYPE_LIST;
	}
	
	/*
	 * =======================================
	 * Product 产品
	 * =======================================
	 * 
	 */
	@RequiresPermissions("Prd:save")
	@RequestMapping(value="/prd/create", method=RequestMethod.GET)
	public String preCreatePrd() {
		return PRD_CREATE;
	}
	
	@Log(message="添加了{0}产品。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("Prd:save")
	@RequestMapping(value="/prd/create", method=RequestMethod.POST)
	public @ResponseBody String createPrd(@Valid Prd prd) {	
		try {
			baseService.saveOrUpdatePrd(prd);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加产品失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{prd.getPrdCode()}));
		return AjaxObject.newOk("添加产品成功！").toString();
	}
	
	@RequiresPermissions("Prd:edit")
	@RequestMapping(value="/prd/update/{id}", method=RequestMethod.GET)
	public String preUpdatePrd(@PathVariable Long id, Map<String, Object> map) {
		Prd basedata = baseService.getPrd(id);
		
		map.put("basedata", basedata);
		return PRD_UPDATE;
	}
	
	@Log(message="修改了{0}产品的信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("Prd:edit")
	@RequestMapping(value="/prd/update", method=RequestMethod.POST)
	public @ResponseBody String updatePrd(Prd prd) {
		baseService.saveOrUpdatePrd(prd);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{prd.getPrdCode()}));
		return	AjaxObject.newOk("修改产品成功！").toString(); 
	}
	
	@Log(message="删除了{0}产品。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("Prd:delete")
	@RequestMapping(value="/prd/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deletePrd(@PathVariable Long id) {
		Prd prd = null;
		try {
			prd = baseService.getPrd(id);
			baseService.deletePrd(prd.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除产品失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{prd.getPrdCode()}));
		return AjaxObject.newOk("删除产品成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}产品。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("Prd:delete")
	@RequestMapping(value="/prd/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyPrd(Long[] ids) {
		String[] prds = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				Prd prd = baseService.getPrd(ids[i]);
				baseService.deletePrd(prd.getId());
				
				prds[i] = prd.getPrdCode();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除产品失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(prds)}));
		return AjaxObject.newOk("删除产品成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("Prd:view")
	@RequestMapping(value="/prd/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listPrd(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<Prd> specification = DynamicSpecifications.bySearchFilter(request, Prd.class);
		List<Prd> prds = baseService.findByPrdExample(specification, page);

		map.put("page", page);
		map.put("prds", prds);
		return PRD_LIST;
	}
	
	/*
	 * =======================================
	 * check fix type 新契约不合格整改类型
	 * =======================================
	 * 
	 */
	@RequiresPermissions("CheckFixType:save")
	@RequestMapping(value="/checkFixType/create", method=RequestMethod.GET)
	public String preCreateCheckFixType() {
		return CHECK_FIX_TYPE_CREATE;
	}
	
	@Log(message="添加了{0}新契约整改类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("CheckFixType:save")
	@RequestMapping(value="/checkFixType/create", method=RequestMethod.POST)
	public @ResponseBody String createCheckFixType(@Valid CheckFixType checkFixType) {	
		try {
			baseService.saveOrUpdateCheckFixType(checkFixType);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加新契约整改类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{checkFixType.getTypeName()}));
		return AjaxObject.newOk("添加新契约整改类型成功！").toString();
	}
	
	@RequiresPermissions("CheckFixType:edit")
	@RequestMapping(value="/checkFixType/update/{id}", method=RequestMethod.GET)
	public String preUpdateCheckFixType(@PathVariable Long id, Map<String, Object> map) {
		CheckFixType basedata = baseService.getCheckFixType(id);
		
		map.put("basedata", basedata);
		return CHECK_FIX_TYPE_UPDATE;
	}
	
	@Log(message="修改了{0}新契约整改类型的信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("CheckFixType:edit")
	@RequestMapping(value="/checkFixType/update", method=RequestMethod.POST)
	public @ResponseBody String updateCheckFixType(CheckFixType checkFixType) {
		baseService.saveOrUpdateCheckFixType(checkFixType);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{checkFixType.getTypeName()}));
		return	AjaxObject.newOk("修改新契约整改类型成功！").toString(); 
	}
	
	@Log(message="删除了{0}新契约整改类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("CheckFixType:delete")
	@RequestMapping(value="/checkFixType/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteCheckFixType(@PathVariable Long id) {
		CheckFixType checkFixType = null;
		try {
			checkFixType = baseService.getCheckFixType(id);
			baseService.deleteCheckFixType(checkFixType.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除新契约整改类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{checkFixType.getTypeName()}));
		return AjaxObject.newOk("删除新契约整改类型成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}新契约整改类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("CheckFixType:delete")
	@RequestMapping(value="/checkFixType/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyCheckFixType(Long[] ids) {
		String[] checkFixTypes = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				CheckFixType checkFixType = baseService.getCheckFixType(ids[i]);
				baseService.deleteCheckFixType(checkFixType.getId());
				
				checkFixTypes[i] = checkFixType.getTypeName();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除新契约整改类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(checkFixTypes)}));
		return AjaxObject.newOk("删除新契约整改类型成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("CheckFixType:view")
	@RequestMapping(value="/checkFixType/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listCheckFixType(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<CheckFixType> specification = DynamicSpecifications.bySearchFilter(request, CheckFixType.class);
		List<CheckFixType> basedata = baseService.findByCheckFixTypeExample(specification, page);

		map.put("page", page);
		map.put("basedata", basedata);
		return CHECK_FIX_TYPE_LIST;
	}
	
	/*
	 * =======================================
	 * ConservationType 保全业务类型
	 * =======================================
	 * 
	 */
	@RequiresPermissions("ConservationType:save")
	@RequestMapping(value="/conservationType/create", method=RequestMethod.GET)
	public String preCreateConservationType() {
		return CONSERVATION_TYPE_CREATE;
	}
	
	@Log(message="添加了{0}保全业务类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("ConservationType:save")
	@RequestMapping(value="/conservationType/create", method=RequestMethod.POST)
	public @ResponseBody String createConservationType(@Valid ConservationType conservationType) {	
		try {
			baseService.saveOrUpdateConservationType(conservationType);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加保全业务类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{conservationType.getCsName()}));
		return AjaxObject.newOk("添加保全业务类型成功！").toString();
	}
	
	@RequiresPermissions("ConservationType:edit")
	@RequestMapping(value="/conservationType/update/{id}", method=RequestMethod.GET)
	public String preUpdateConservationType(@PathVariable Long id, Map<String, Object> map) {
		ConservationType basedata = baseService.getConservationType(id);
		
		map.put("basedata", basedata);
		return CONSERVATION_TYPE_UPDATE;
	}
	
	@Log(message="修改了{0}保全业务类型的信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("ConservationType:edit")
	@RequestMapping(value="/conservationType/update", method=RequestMethod.POST)
	public @ResponseBody String updateConservationType(ConservationType conservationType) {
		baseService.saveOrUpdateConservationType(conservationType);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{conservationType.getCsName()}));
		return	AjaxObject.newOk("修改保全业务类型成功！").toString(); 
	}
	
	@Log(message="删除了{0}保全业务类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("ConservationType:delete")
	@RequestMapping(value="/conservationType/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteConservationType(@PathVariable Long id) {
		ConservationType conservationType = null;
		try {
			conservationType = baseService.getConservationType(id);
			baseService.deleteConservationType(conservationType.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除保全业务类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{conservationType.getCsName()}));
		return AjaxObject.newOk("删除保全业务类型成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}保全业务类型。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("ConservationType:delete")
	@RequestMapping(value="/conservationType/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyConservationType(Long[] ids) {
		String[] conservationTypes = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				ConservationType conservationType = baseService.getConservationType(ids[i]);
				baseService.deleteConservationType(conservationType.getId());
				
				conservationTypes[i] = conservationType.getCsName();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除保全业务类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(conservationTypes)}));
		return AjaxObject.newOk("删除保全业务类型成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("ConservationType:view")
	@RequestMapping(value="/conservationType/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listConservationType(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<ConservationType> specification = DynamicSpecifications.bySearchFilter(request, ConservationType.class);
		List<ConservationType> basedata = baseService.findByConservationTypeExample(specification, page);

		map.put("page", page);
		map.put("basedata", basedata);
		return CONSERVATION_TYPE_LIST;
	}
	
	/*
	 * =======================================
	 * Prov Org Code 省分机构代码建议对照表
	 * =======================================
	 * 
	 */
	@RequiresPermissions("ProvOrgCode:save")
	@RequestMapping(value="/provOrgCode/create", method=RequestMethod.GET)
	public String preCreateProvOrgCode() {
		return PROV_ORG_CODE_CREATE;
	}
	
	@Log(message="添加了{0}省分机构代码对照。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("ProvOrgCode:save")
	@RequestMapping(value="/provOrgCode/create", method=RequestMethod.POST)
	public @ResponseBody String createProvOrgCode(@Valid ProvOrgCode provOrgCode) {	
		try {
			baseService.saveOrUpdateProvOrgCode(provOrgCode);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加省分机构代码对照失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{provOrgCode.getOrgName()}));
		return AjaxObject.newOk("添加省分机构代码对照成功！").toString();
	}
	
	@RequiresPermissions("ProvOrgCode:edit")
	@RequestMapping(value="/provOrgCode/update/{id}", method=RequestMethod.GET)
	public String preUpdateProvOrgCode(@PathVariable Long id, Map<String, Object> map) {
		ProvOrgCode basedata = baseService.getProvOrgCode(id);
		
		map.put("basedata", basedata);
		return PROV_ORG_CODE_UPDATE;
	}
	
	@Log(message="修改了{0}省分机构代码对照的信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("ProvOrgCode:edit")
	@RequestMapping(value="/provOrgCode/update", method=RequestMethod.POST)
	public @ResponseBody String updateProvOrgCode(ProvOrgCode provOrgCode) {
		baseService.saveOrUpdateProvOrgCode(provOrgCode);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{provOrgCode.getOrgName()}));
		return	AjaxObject.newOk("修改省分机构代码对照成功！").toString(); 
	}
	
	@Log(message="删除了{0}省分机构代码对照。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("ProvOrgCode:delete")
	@RequestMapping(value="/provOrgCode/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteProvOrgCode(@PathVariable Long id) {
		ProvOrgCode provOrgCode = null;
		try {
			provOrgCode = baseService.getProvOrgCode(id);
			baseService.deleteProvOrgCode(provOrgCode.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除省分机构代码对照失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{provOrgCode.getOrgName()}));
		return AjaxObject.newOk("删除省分机构代码对照成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}省分机构代码对照。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("ProvOrgCode:delete")
	@RequestMapping(value="/provOrgCode/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyProvOrgCode(Long[] ids) {
		String[] provOrgCodes = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				ProvOrgCode provOrgCode = baseService.getProvOrgCode(ids[i]);
				baseService.deleteProvOrgCode(provOrgCode.getId());
				
				provOrgCodes[i] = provOrgCode.getOrgName();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除省分机构代码对照失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(provOrgCodes)}));
		return AjaxObject.newOk("删除省分机构代码对照成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("ProvOrgCode:view")
	@RequestMapping(value="/provOrgCode/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listProvOrgCode(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<ProvOrgCode> specification = DynamicSpecifications.bySearchFilter(request, ProvOrgCode.class);
		page.setOrderField("orgCode");
		page.setOrderDirection("ASC");
		List<ProvOrgCode> basedata = baseService.findByProvOrgCodeExample(specification, page);

		map.put("page", page);
		map.put("basedata", basedata);
		return PROV_ORG_CODE_LIST;
	}

	/*
	 * =======================================
	 * Staff 员工信息
	 * =======================================
	 * 
	 */
	@RequiresPermissions("Staff:save")
	@RequestMapping(value="/staff/create", method=RequestMethod.GET)
	public String preCreateStaff() {
		return STAFF_CREATE;
	}

	@Log(message="添加了{0}员工信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("Staff:save")
	@RequestMapping(value="/staff/create", method=RequestMethod.POST)
	public @ResponseBody String createStaff(@Valid Staff staff) {	
		try {
			baseService.saveOrUpdateStaff(staff);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加员工信息失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{staff.getIdCard()}));
		return AjaxObject.newOk("添加员工信息成功！").toString();
	}

	@RequiresPermissions("Staff:edit")
	@RequestMapping(value="/staff/update/{id}", method=RequestMethod.GET)
	public String preUpdateStaff(@PathVariable Long id, Map<String, Object> map) {
		Staff basedata = baseService.getStaff(id);
		
		map.put("basedata", basedata);
		return STAFF_UPDATE;
	}

	@Log(message="修改了{0}员工信息的信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("Staff:edit")
	@RequestMapping(value="/staff/update", method=RequestMethod.POST)
	public @ResponseBody String updateStaff(Staff staff) {
		baseService.saveOrUpdateStaff(staff);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{staff.getIdCard()}));
		return	AjaxObject.newOk("修改员工信息成功！").toString(); 
	}

	@Log(message="删除了{0}员工信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("Staff:delete")
	@RequestMapping(value="/staff/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteStaff(@PathVariable Long id) {
		Staff staff = null;
		try {
			staff = baseService.getStaff(id);
			baseService.deleteStaff(staff.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除员工信息失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{staff.getIdCard()}));
		return AjaxObject.newOk("删除员工信息成功！").setCallbackType("").toString();
	}

	@Log(message="删除了{0}员工信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("Staff:delete")
	@RequestMapping(value="/staff/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyStaff(Long[] ids) {
		String[] staffs = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				Staff staff = baseService.getStaff(ids[i]);
				baseService.deleteStaff(staff.getId());
				
				staffs[i] = staff.getIdCard();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除员工信息失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(staffs)}));
		return AjaxObject.newOk("删除员工信息成功！").setCallbackType("").toString();
	}

	@RequiresPermissions("Staff:view")
	@RequestMapping(value="/staff/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listStaff(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<Staff> specification = DynamicSpecifications.bySearchFilter(request, Staff.class);
		List<Staff> basedata = baseService.findByStaffExample(specification, page);
	
		map.put("page", page);
		map.put("basedata", basedata);
		return STAFF_LIST;
	}
	
	/*
	 * =======================================
	 * CsAddr 异地保全地址
	 * =======================================
	 * 
	 */
	@RequiresPermissions("CsAddr:save")
	@RequestMapping(value="/csAddr/create", method=RequestMethod.GET)
	public String preCreateCsAddr() {
		return CS_ADDR_CREATE;
	}

	@Log(message="添加了{0}异地保全地址。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("CsAddr:save")
	@RequestMapping(value="/csAddr/create", method=RequestMethod.POST)
	public @ResponseBody String createCsAddr(@Valid CsAddr csAddr) {	
		try {
			baseService.saveOrUpdateCsAddr(csAddr);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加异地保全地址失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{csAddr.getCity()}));
		return AjaxObject.newOk("添加异地保全地址成功！").toString();
	}

	@RequiresPermissions("CsAddr:edit")
	@RequestMapping(value="/csAddr/update/{id}", method=RequestMethod.GET)
	public String preUpdateCsAddr(@PathVariable Long id, Map<String, Object> map) {
		CsAddr basedata = baseService.getCsAddr(id);
		
		map.put("basedata", basedata);
		return CS_ADDR_UPDATE;
	}

	@Log(message="修改了{0}异地保全地址的信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("CsAddr:edit")
	@RequestMapping(value="/csAddr/update", method=RequestMethod.POST)
	public @ResponseBody String updateCsAddr(CsAddr csAddr) {
		baseService.saveOrUpdateCsAddr(csAddr);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{csAddr.getCity()}));
		return	AjaxObject.newOk("修改异地保全地址成功！").toString(); 
	}

	@Log(message="删除了{0}异地保全地址。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("CsAddr:delete")
	@RequestMapping(value="/csAddr/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteCsAddr(@PathVariable Long id) {
		CsAddr csAddr = null;
		try {
			csAddr = baseService.getCsAddr(id);
			baseService.deleteCsAddr(csAddr.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除异地保全地址失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{csAddr.getCity()}));
		return AjaxObject.newOk("删除异地保全地址成功！").setCallbackType("").toString();
	}

	@Log(message="删除了{0}异地保全地址。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("CsAddr:delete")
	@RequestMapping(value="/csAddr/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyCsAddr(Long[] ids) {
		String[] csAddrs = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				CsAddr csAddr = baseService.getCsAddr(ids[i]);
				baseService.deleteCsAddr(csAddr.getId());
				
				csAddrs[i] = csAddr.getCity();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除异地保全地址失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(csAddrs)}));
		return AjaxObject.newOk("删除异地保全地址成功！").setCallbackType("").toString();
	}

	@RequiresPermissions("CsAddr:view")
	@RequestMapping(value="/csAddr/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listCsAddr(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<CsAddr> specification = DynamicSpecifications.bySearchFilter(request, CsAddr.class);
		List<CsAddr> basedata = baseService.findByCsAddrExample(specification, page);
	
		map.put("page", page);
		map.put("basedata", basedata);
		return CS_ADDR_LIST;
	}
	
	/*
	 * =======================================
	 * SALES 销售人员
	 * =======================================
	 * 
	 */
	@RequiresPermissions("Sales:save")
	@RequestMapping(value="/sales/create", method=RequestMethod.GET)
	public String preCreateSales() {
		return SALES_CREATE;
	}

	@Log(message="添加了{0}销售人员信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("Sales:save")
	@RequestMapping(value="/sales/create", method=RequestMethod.POST)
	public @ResponseBody String createSales(@Valid Sales sales) {	
		try {
			baseService.saveOrUpdateSales(sales);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加销售人员信息失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{sales.getSalesName()}));
		return AjaxObject.newOk("添加销售人员信息成功！").toString();
	}

	@RequiresPermissions("Sales:edit")
	@RequestMapping(value="/sales/update/{id}", method=RequestMethod.GET)
	public String preUpdateSales(@PathVariable Long id, Map<String, Object> map) {
		Sales basedata = baseService.getSales(id);
		
		map.put("basedata", basedata);
		return SALES_UPDATE;
	}

	@Log(message="修改了{0}销售人员信息的信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("Sales:edit")
	@RequestMapping(value="/sales/update", method=RequestMethod.POST)
	public @ResponseBody String updateSales(Sales sales) {
		baseService.saveOrUpdateSales(sales);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{sales.getSalesName()}));
		return	AjaxObject.newOk("修改销售人员信息成功！").toString(); 
	}

	@Log(message="删除了{0}销售人员信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("Sales:delete")
	@RequestMapping(value="/sales/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteSales(@PathVariable Long id) {
		Sales sales = null;
		try {
			sales = baseService.getSales(id);
			baseService.deleteSales(sales.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除销售人员信息失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{sales.getSalesName()}));
		return AjaxObject.newOk("删除销售人员信息成功！").setCallbackType("").toString();
	}

	@Log(message="删除了{0}销售人员信息。", level=LogLevel.WARN, module=LogModule.BaseDate)
	@RequiresPermissions("Sales:delete")
	@RequestMapping(value="/sales/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManySales(Long[] ids) {
		String[] saless = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				Sales sales = baseService.getSales(ids[i]);
				baseService.deleteSales(sales.getId());
				
				saless[i] = sales.getSalesName();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除销售人员信息失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(saless)}));
		return AjaxObject.newOk("删除销售人员信息成功！").setCallbackType("").toString();
	}

	@RequiresPermissions("Sales:view")
	@RequestMapping(value="/sales/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listSales(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<Sales> specification = DynamicSpecifications.bySearchFilter(request, Sales.class);
		List<Sales> basedata = baseService.findBySalesExample(specification, page);
	
		map.put("page", page);
		map.put("basedata", basedata);
		return SALES_LIST;
	}
}
