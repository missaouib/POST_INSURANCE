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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.web.entity.basedata.BankCode;
import com.gdpost.web.entity.basedata.CallDealType;
import com.gdpost.web.entity.basedata.ConservationError;
import com.gdpost.web.entity.basedata.IssueType;
import com.gdpost.web.entity.basedata.Prd;
import com.gdpost.web.entity.basedata.RenewalType;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.insurance.BaseDataService;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;

@Controller
@RequestMapping("/basedata")
public class BaseDataController {
	//private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private BaseDataService baseService;

	private static final String CALL_DEAL_TYPE_CREATE = "insurance/basedata/CallDealType/create";
	private static final String CALL_DEAL_TYPE_UPDATE = "insurance/basedata/CallDealType/update";
	private static final String CALL_DEAL_TYPE_LIST = "insurance/basedata/CallDealType/list";
	
	private static final String CONSERVATION_ERROR_CREATE = "insurance/basedata/conservationError/create";
	private static final String CONSERVATION_ERROR_UPDATE = "insurance/basedata/conservationError/update";
	private static final String CONSERVATION_ERROR_LIST = "insurance/basedata/conservationError/list";
	
	private static final String ISSUE_TYPE_CREATE = "insurance/basedata/issueType/create";
	private static final String ISSUE_TYPE_UPDATE = "insurance/basedata/issueType/update";
	private static final String ISSUE_TYPE_LIST = "insurance/basedata/issueType/list";
	
	private static final String NET_CODE_CREATE = "insurance/basedata/netcode/create";
	private static final String NET_CODE_UPDATE = "insurance/basedata/netcode/update";
	private static final String NET_CODE_LIST = "insurance/basedata/netcode/list";
	
	private static final String RENEWAL_TYPE_CREATE = "insurance/basedata/renewalType/create";
	private static final String RENEWAL_TYPE_UPDATE = "insurance/basedata/renewalType/update";
	private static final String RENEWAL_TYPE_LIST = "insurance/basedata/renewalType/list";
	
	private static final String PRD_CREATE = "insurance/basedata/product/create";
	private static final String PRD_UPDATE = "insurance/basedata/product/update";
	private static final String PRD_LIST = "insurance/basedata/product/list";
	
	/*
	 * =======================================
	 * call deal type 回访类型
	 * =======================================
	 * 
	 */
	@RequiresPermissions("BaseData:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreateCallDealType() {
		return CALL_DEAL_TYPE_CREATE;
	}
	
	@Log(message="添加了{0}回访处理类型。")
	@RequiresPermissions("BaseData:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String createCallDealType(@Valid CallDealType user) {	
		try {
			baseService.saveOrUpdateCallDealType(user);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加回访处理类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getTypeName()}));
		return AjaxObject.newOk("添加回访处理类型成功！").toString();
	}
	
	@ModelAttribute("preloadCallDealType")
	public CallDealType preloadCallDealType(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			CallDealType user = baseService.getCallDealType(id);
			return user;
		}
		return null;
	}
	
	@RequiresPermissions("BaseData:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdateCallDealType(@PathVariable Long id, Map<String, Object> map) {
		CallDealType user = baseService.getCallDealType(id);
		
		map.put("user", user);
		return CALL_DEAL_TYPE_UPDATE;
	}
	
	@Log(message="修改了{0}回访处理类型的信息。")
	@RequiresPermissions("BaseData:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String updateCallDealType(@Valid @ModelAttribute("preloadCallDealType")CallDealType user) {
		baseService.saveOrUpdateCallDealType(user);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getTypeName()}));
		return	AjaxObject.newOk("修改回访处理类型成功！").toString(); 
	}
	
	@Log(message="删除了{0}回访处理类型。")
	@RequiresPermissions("BaseData:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String deleteCallDealType(@PathVariable Long id) {
		CallDealType user = null;
		try {
			user = baseService.getCallDealType(id);
			baseService.deleteCallDealType(user.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError("删除回访处理类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getTypeName()}));
		return AjaxObject.newOk("删除回访处理类型成功！").setCallbackType("").toString();
	}
	
	@Log(message="删除了{0}回访处理类型。")
	@RequiresPermissions("BaseData:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteManyCallDealType(Long[] ids) {
		String[] callDealTypes = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				CallDealType user = baseService.getCallDealType(ids[i]);
				baseService.deleteCallDealType(user.getId());
				
				callDealTypes[i] = user.getTypeName();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除回访处理类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(callDealTypes)}));
		return AjaxObject.newOk("删除回访处理类型成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("BaseData:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listCallDealType(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<CallDealType> specification = DynamicSpecifications.bySearchFilter(request, CallDealType.class);
		List<CallDealType> users = baseService.findByCallDealTypeExample(specification, page);

		map.put("page", page);
		map.put("users", users);
		return CALL_DEAL_TYPE_LIST;
	}
	
	/*
	 * =======================================
	 * conservation error 保全错误类型
	 * =======================================
	 * 
	 */
	@RequiresPermissions("BaseData:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreateConservationError() {
		return CONSERVATION_ERROR_CREATE;
	}
	
	@Log(message="添加了{0}保全错误类型。")
	@RequiresPermissions("BaseData:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String createConservationError(@Valid ConservationError conservationError) {	
		try {
			baseService.saveOrUpdateConservationError(conservationError);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加保全错误类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{conservationError.getErrorCode()}));
		return AjaxObject.newOk("添加保全错误类型成功！").toString();
	}
	
	@ModelAttribute("preloadConservationError")
	public ConservationError preloadConservationError(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			ConservationError conservationError = baseService.getConservationError(id);
			return conservationError;
		}
		return null;
	}
	
	@RequiresPermissions("BaseData:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdateConservationError(@PathVariable Long id, Map<String, Object> map) {
		ConservationError conservationError = baseService.getConservationError(id);
		
		map.put("conservationError", conservationError);
		return CONSERVATION_ERROR_UPDATE;
	}
	
	@Log(message="修改了{0}保全错误类型的信息。")
	@RequiresPermissions("BaseData:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String updateConservationError(@Valid @ModelAttribute("preloadConservationError")ConservationError conservationError) {
		baseService.saveOrUpdateConservationError(conservationError);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{conservationError.getErrorCode()}));
		return	AjaxObject.newOk("修改保全错误类型成功！").toString(); 
	}
	
	@Log(message="删除了{0}保全错误类型。")
	@RequiresPermissions("BaseData:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
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
	
	@Log(message="删除了{0}保全错误类型。")
	@RequiresPermissions("BaseData:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
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
	
	@RequiresPermissions("BaseData:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listConservationError(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<ConservationError> specification = DynamicSpecifications.bySearchFilter(request, ConservationError.class);
		List<ConservationError> conservationErrors = baseService.findByConservationErrorExample(specification, page);

		map.put("page", page);
		map.put("conservationErrors", conservationErrors);
		return CONSERVATION_ERROR_LIST;
	}
	
	/*
	 * =======================================
	 * ISSUE TYPE 工单类型
	 * =======================================
	 * 
	 */
	@RequiresPermissions("BaseData:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreateIssueType() {
		return ISSUE_TYPE_CREATE;
	}
	
	@Log(message="添加了{0}工单错误类型。")
	@RequiresPermissions("BaseData:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String createIssueType(@Valid IssueType issueType) {	
		try {
			baseService.saveOrUpdateIssueType(issueType);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加工单错误类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issueType.getTypeName()}));
		return AjaxObject.newOk("添加工单错误类型成功！").toString();
	}
	
	@ModelAttribute("preloadIssueType")
	public IssueType preloadIssueType(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			IssueType issueType = baseService.getIssueType(id);
			return issueType;
		}
		return null;
	}
	
	@RequiresPermissions("BaseData:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdateIssueType(@PathVariable Long id, Map<String, Object> map) {
		IssueType issueType = baseService.getIssueType(id);
		
		map.put("issueType", issueType);
		return ISSUE_TYPE_UPDATE;
	}
	
	@Log(message="修改了{0}工单错误类型的信息。")
	@RequiresPermissions("BaseData:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String updateIssueType(@Valid @ModelAttribute("preloadIssueType")IssueType issueType) {
		baseService.saveOrUpdateIssueType(issueType);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issueType.getTypeName()}));
		return	AjaxObject.newOk("修改工单错误类型成功！").toString(); 
	}
	
	@Log(message="删除了{0}工单错误类型。")
	@RequiresPermissions("BaseData:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
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
	
	@Log(message="删除了{0}工单错误类型。")
	@RequiresPermissions("BaseData:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
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
	
	@RequiresPermissions("BaseData:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listIssueType(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<IssueType> specification = DynamicSpecifications.bySearchFilter(request, IssueType.class);
		List<IssueType> issueTypes = baseService.findByIssueTypeExample(specification, page);

		map.put("page", page);
		map.put("issueTypes", issueTypes);
		return ISSUE_TYPE_LIST;
	}
	
	/*
	 * =======================================
	 * 网点映射 netcode
	 * =======================================
	 * 
	 */
	@RequiresPermissions("BaseData:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreateNetCode() {
		return NET_CODE_CREATE;
	}
	
	@Log(message="添加了{0}网点对照。")
	@RequiresPermissions("BaseData:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String createNetCode(@Valid BankCode bankCode) {	
		try {
			baseService.saveOrUpdateBankCode(bankCode);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加网点对照失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{bankCode.getCpiCode()}));
		return AjaxObject.newOk("添加网点对照成功！").toString();
	}
	
	@ModelAttribute("preloadNetCode")
	public BankCode preloadNetCode(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			BankCode bankCode = baseService.getBankCode(id);
			return bankCode;
		}
		return null;
	}
	
	@RequiresPermissions("BaseData:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdateNetCode(@PathVariable Long id, Map<String, Object> map) {
		BankCode bankCode = baseService.getBankCode(id);
		
		map.put("bankCode", bankCode);
		return NET_CODE_UPDATE;
	}
	
	@Log(message="修改了{0}网点对照的信息。")
	@RequiresPermissions("BaseData:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String updateNetCode(@Valid @ModelAttribute("preloadNetCode")BankCode bankCode) {
		baseService.saveOrUpdateBankCode(bankCode);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{bankCode.getCpiCode()}));
		return	AjaxObject.newOk("修改网点对照成功！").toString(); 
	}
	
	@Log(message="删除了{0}网点对照。")
	@RequiresPermissions("BaseData:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
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
	
	@Log(message="删除了{0}网点对照。")
	@RequiresPermissions("BaseData:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
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
	
	@RequiresPermissions("BaseData:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listNetCode(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<BankCode> specification = DynamicSpecifications.bySearchFilter(request, BankCode.class);
		List<BankCode> bankCodes = baseService.findByBankCodeExample(specification, page);

		map.put("page", page);
		map.put("bankCodes", bankCodes);
		return NET_CODE_LIST;
	}
	
	/*
	 * =======================================
	 * renewal type 催缴类型
	 * =======================================
	 * 
	 */
	@RequiresPermissions("BaseData:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreateRenewalType() {
		return RENEWAL_TYPE_CREATE;
	}
	
	@Log(message="添加了{0}催缴类型。")
	@RequiresPermissions("BaseData:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String createRenewalType(@Valid RenewalType renewalType) {	
		try {
			baseService.saveOrUpdateRenewalType(renewalType);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加催缴类型失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{renewalType.getTypeName()}));
		return AjaxObject.newOk("添加催缴类型成功！").toString();
	}
	
	@ModelAttribute("preloadRenewalType")
	public RenewalType preloadRenewalType(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			RenewalType renewalType = baseService.getRenewalType(id);
			return renewalType;
		}
		return null;
	}
	
	@RequiresPermissions("BaseData:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdateRenewalType(@PathVariable Long id, Map<String, Object> map) {
		RenewalType renewalType = baseService.getRenewalType(id);
		
		map.put("renewalType", renewalType);
		return RENEWAL_TYPE_UPDATE;
	}
	
	@Log(message="修改了{0}催缴类型的信息。")
	@RequiresPermissions("BaseData:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String updateRenewalType(@Valid @ModelAttribute("preloadRenewalType")RenewalType renewalType) {
		baseService.saveOrUpdateRenewalType(renewalType);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{renewalType.getTypeName()}));
		return	AjaxObject.newOk("修改催缴类型成功！").toString(); 
	}
	
	@Log(message="删除了{0}催缴类型。")
	@RequiresPermissions("BaseData:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
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
	
	@Log(message="删除了{0}催缴类型。")
	@RequiresPermissions("BaseData:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
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
	
	@RequiresPermissions("BaseData:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listRenewalType(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<RenewalType> specification = DynamicSpecifications.bySearchFilter(request, RenewalType.class);
		List<RenewalType> renewalTypes = baseService.findByRenewalTypeExample(specification, page);

		map.put("page", page);
		map.put("renewalTypes", renewalTypes);
		return RENEWAL_TYPE_LIST;
	}
	
	/*
	 * =======================================
	 * Product 产品
	 * =======================================
	 * 
	 */
	@RequiresPermissions("BaseData:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreatePrd() {
		return PRD_CREATE;
	}
	
	@Log(message="添加了{0}产品。")
	@RequiresPermissions("BaseData:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String createPrd(@Valid Prd prd) {	
		try {
			baseService.saveOrUpdatePrd(prd);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加产品失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{prd.getPrdCode()}));
		return AjaxObject.newOk("添加产品成功！").toString();
	}
	
	@ModelAttribute("preloadPrd")
	public Prd preloadPrd(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Prd prd = baseService.getPrd(id);
			return prd;
		}
		return null;
	}
	
	@RequiresPermissions("BaseData:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdatePrd(@PathVariable Long id, Map<String, Object> map) {
		Prd prd = baseService.getPrd(id);
		
		map.put("prd", prd);
		return PRD_UPDATE;
	}
	
	@Log(message="修改了{0}产品的信息。")
	@RequiresPermissions("BaseData:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String updatePrd(@Valid @ModelAttribute("preloadPrd")Prd prd) {
		baseService.saveOrUpdatePrd(prd);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{prd.getPrdCode()}));
		return	AjaxObject.newOk("修改产品成功！").toString(); 
	}
	
	@Log(message="删除了{0}产品。")
	@RequiresPermissions("BaseData:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
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
	
	@Log(message="删除了{0}产品。")
	@RequiresPermissions("BaseData:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
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
	
	@RequiresPermissions("BaseData:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String listPrd(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<Prd> specification = DynamicSpecifications.bySearchFilter(request, Prd.class);
		List<Prd> prds = baseService.findByPrdExample(specification, page);

		map.put("page", page);
		map.put("prds", prds);
		return PRD_LIST;
	}
}
