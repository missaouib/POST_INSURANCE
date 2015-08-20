/**
 * 
 * ==========================================================
 * 回访管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.entity.basedata.CallDealType;
import com.gdpost.web.entity.component.VCallFailList;
import com.gdpost.web.entity.main.CallFailList;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.insurance.HfglService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.StatusDefine.HF_STATUS;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/hfgl")
public class HfglController {
	private static final Logger LOG = LoggerFactory.getLogger(HfglController.class);
	
	@Autowired
	private HfglService hfglService;

	private static final String VIEW = "insurance/hfgl/wtj/view";
	private static final String UPDATE = "insurance/hfgl/wtj/update";
	private static final String PROV_UPDATE = "insurance/hfgl/wtj/provupdate";
	private static final String HQ_UPDATE = "insurance/hfgl/wtj/hqupdate";
	private static final String LIST = "insurance/hfgl/wtj/list";
	private static final String ISSUE_LIST = "insurance/hfgl/wtj/issuelist";
	private static final String RESET = "insurance/hfgl/wtj/setPhone";
	
	@RequiresPermissions("Callfail:view")
	@RequestMapping(value="/issue/view/{id}", method=RequestMethod.GET)
	public String view(@PathVariable Long id, Map<String, Object> map) {
		CallFailList issue = hfglService.get(id);
		
		map.put("issue", issue);
		//map.put("status", HF_STATUS.ReopenStatus);
		return VIEW;
	}
	
	@RequiresPermissions("Callfail:edit")
	@RequestMapping(value="/issue/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		CallFailList issue = hfglService.get(id);
		if(issue.getStatus() == null || issue.getStatus().equals(HF_STATUS.NewStatus.getDesc())) {
			issue.setStatus(HF_STATUS.DealStatus.getDesc());
		}
		map.put("issue", issue);
		List<CallDealType> cdtList = hfglService.getCallDealTypeList(CallDealType.ORG_TYPE);
		map.put("orgTypeList", cdtList);
		return UPDATE;
	}
	
	@RequiresPermissions("Callfail:provEdit")
	@RequestMapping(value="/issue/provUpdate/{id}", method=RequestMethod.GET)
	public String preProvUpdate(@PathVariable Long id, Map<String, Object> map) {
		CallFailList issue = hfglService.get(id);
		if(issue.getStatus() == null || issue.getStatus().equals(HF_STATUS.NewStatus.getDesc())) {
			issue.setStatus(HF_STATUS.DealStatus.getDesc());
		}
		map.put("issue", issue);
		return PROV_UPDATE;
	}
	
	@RequiresPermissions("Callfail:11185Edit")
	@RequestMapping(value="/issue/hqUpdate/{id}", method=RequestMethod.GET)
	public String preHqUpdate(@PathVariable Long id, Map<String, Object> map) {
		CallFailList issue = hfglService.get(id);
		if(issue.getStatus() == null || issue.getStatus().equals(HF_STATUS.NewStatus.getDesc())) {
			issue.setStatus(HF_STATUS.CallFailStatus.getDesc());
		}
		map.put("issue", issue);
		List<CallDealType> cdtList = hfglService.getCallDealTypeList(CallDealType.HQ_TYPE);
		map.put("hqTypeList", cdtList);
		return HQ_UPDATE;
	}
	
	@Log(message="回复了{0}回访不成功件的信息。")
	@RequiresPermissions("Callfail:edit")
	@RequestMapping(value="/issue/update", method=RequestMethod.POST)
	public @ResponseBody String update(CallFailList issue) {
		CallFailList src = hfglService.get(issue.getId());
		src.setDealMan(issue.getDealMan());
		src.setDealTime(issue.getDealTime());
		src.setDealDesc(issue.getDealDesc());
		src.setDealNum((issue.getDealNum()==null?0:issue.getDealNum()) + 1);
		//src.setStatus(issue.getStatus());
		src.setDealType(issue.getDealType());
		if(issue.getDealType().equals("不成功件")) {
//			if(issue.getStatus().equals(HF_STATUS.DoorSuccessStatus.getDesc())) {
//				src.setOrgDealFlag(1);
//			}
			src.setStatus(HF_STATUS.DoorFailStatus.getDesc());
			//src.setOrgDealFlag(1);
		} else if(issue.getDealType().equals("成功件")) {
			src.setStatus(HF_STATUS.DoorSuccessStatus.getDesc());
			src.setOrgDealFlag(1);
		}
		hfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复回访不成功件成功！").toString(); 
	}
	
	@Log(message="回复了{0}回访不成功件的信息。")
	@RequiresPermissions("Callfail:provEdit")
	@RequestMapping(value="/issue/provUpdate", method=RequestMethod.POST)
	public @ResponseBody String provUpdate(CallFailList issue) {
		CallFailList src = hfglService.get(issue.getId());
		src.setProvDealRst(issue.getProvDealRst());
		src.setProvDealDate(new Date());
		src.setProvDealRemark(issue.getProvDealRemark());
		//src.setStatus(XQ_STATUS.DealStatus.getDesc());
		src.setProvDealNum((src.getProvDealNum()==null?0:src.getProvDealNum())+1);
		src.setStatus(issue.getStatus());
		src.setProvIssueType(issue.getStatus());
		if(issue.getStatus().equals(HF_STATUS.CallSuccessStatus.getDesc())) {
			src.setProvDealFlag(1);
		}
		hfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复回访不成功件成功！").toString(); 
	}
	
	@Log(message="回复了{0}回访不成功件的信息。")
	@RequiresPermissions("Callfail:11185Edit")
	@RequestMapping(value="/issue/hqUpdate", method=RequestMethod.POST)
	public @ResponseBody String hqUpdate(CallFailList issue) {
		CallFailList src = hfglService.get(issue.getId());
		if(issue.getHqDealType() != null && issue.getHqDealType().trim().length()>0) {
			src.setHqDealRst(issue.getHqDealRst());
			src.setHqDealDate(issue.getHqDealDate());
			src.setHqDealMan(issue.getHqDealMan());
			src.setHqDealType(issue.getHqDealType());
			if(issue.getHqDealType().equals("不成功件")) {
				src.setStatus(HF_STATUS.CallFailStatus.getDesc());
			} else if(issue.getHqDealType().equals("成功件")) {
				src.setStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setOrgDealFlag(1);
			}
		}
		if(issue.getHqDealType2() != null && issue.getHqDealType2().trim().length()>0) {
			src.setHqDealRst2(issue.getHqDealRst2());
			src.setHqDealDate2(issue.getHqDealDate2());
			src.setHqDealMan2(issue.getHqDealMan2());
			src.setHqDealType2(issue.getHqDealType2());
			if(issue.getHqDealType2().equals("不成功件")) {
				src.setStatus(HF_STATUS.CallFailStatus.getDesc());
			} else if(issue.getHqDealType2().equals("成功件")) {
				src.setStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setOrgDealFlag(1);
			}
		}
		if(issue.getHqDealType3() != null && issue.getHqDealType3().trim().length()>0) {
			src.setHqDealRst3(issue.getHqDealRst3());
			src.setHqDealDate3(issue.getHqDealDate3());
			src.setHqDealMan3(issue.getHqDealMan3());
			src.setHqDealType3(issue.getHqDealType3());
			if(issue.getHqDealType3().equals("不成功件")) {
				src.setStatus(HF_STATUS.CallFailStatus.getDesc());
			} else if(issue.getHqDealType3().equals("成功件")) {
				src.setStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setOrgDealFlag(1);
			}
		}
		if(issue.getHqDealType4() != null && issue.getHqDealType4().trim().length()>0) {
			src.setHqDealRst4(issue.getHqDealRst4());
			src.setHqDealDate4(issue.getHqDealDate4());
			src.setHqDealMan4(issue.getHqDealMan4());
			src.setHqDealType4(issue.getHqDealType4());
			if(issue.getHqDealType4().equals("不成功件")) {
				src.setStatus(HF_STATUS.CallFailStatus.getDesc());
			} else if(issue.getHqDealType4().equals("成功件")) {
				src.setStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setOrgDealFlag(1);
			}
		}
		if(issue.getHqDealType5() != null && issue.getHqDealType5().trim().length()>0) {
			src.setHqDealRst5(issue.getHqDealRst5());
			src.setHqDealDate5(issue.getHqDealDate5());
			src.setHqDealMan5(issue.getHqDealMan5());
			src.setHqDealType5(issue.getHqDealType5());
			if(issue.getHqDealType5().equals("不成功件")) {
				src.setStatus(HF_STATUS.CallFailStatus.getDesc());
			} else if(issue.getHqDealType5().equals("成功件")) {
				src.setStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setOrgDealFlag(1);
			}
		}
		if(issue.getHqDealType6() != null && issue.getHqDealType6().trim().length()>0) {
			src.setHqDealRst6(issue.getHqDealRst6());
			src.setHqDealDate6(issue.getHqDealDate6());
			src.setHqDealMan6(issue.getHqDealMan6());
			src.setHqDealType6(issue.getHqDealType6());
			if(issue.getHqDealType6().equals("不成功件")) {
				src.setStatus(HF_STATUS.CallFailStatus.getDesc());
			} else if(issue.getHqDealType6().equals("成功件")) {
				src.setStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setOrgDealFlag(1);
			}
		}
		src.setHqDealNum((src.getHqDealNum()==null?0:src.getHqDealNum())+1);
//		if(issue.getStatus().equals(HF_STATUS.CallSuccessStatus.getDesc())) {
//			src.setHqDealFlag(1);
//		}
		
		hfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复回访不成功件成功！").toString(); 
	}
	
	@RequiresPermissions("Callfail:edit")
	@RequestMapping(value="/updateResetStatus/{id}", method=RequestMethod.GET)
	public String preUpdateResetStatus(@PathVariable Long id, Map<String, Object> map) {
		CallFailList req = hfglService.get(id);
		if(req.getStatus() == null || req.getStatus().equals(HF_STATUS.NewStatus.getDesc())) {
			req.setStatus(HF_STATUS.ResetStatus.getDesc());
		}
		map.put("cfl", req);
		return RESET;
	}
	
	@Log(message="进行了{0}电话重置(holderMobile)。")
	@RequiresPermissions("Callfail:edit")
	@RequestMapping(value="/issue/{status}/{id}", method=RequestMethod.POST)
	public @ResponseBody String updateStatus(ServletRequest request, @PathVariable("status") String status, @PathVariable("id") Long id) {
		CallFailList call = hfglService.get(id);
		String resetPhone = request.getParameter("resetPhone");
		HF_STATUS bs = HF_STATUS.ResetStatus;
		try {
			bs = HF_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		switch (bs) {
		case ResetStatus:
			call.setResetPhone(resetPhone);
			break;
		case CloseStatus:
			call.setStatus(bs.getDesc());
			break;
			default:
				break;
		}
		call.setStatus(bs.getDesc());
		hfglService.saveOrUpdate(call);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{call.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回访电话重置成功！").setCallbackType("").toString();
	}
	
	@Log(message="结案了{0}回访不成功件的信息。")
	@RequiresPermissions("Callfail:edit")
	@RequestMapping(value="/issue/close", method=RequestMethod.POST)
	public @ResponseBody String close(CallFailList issue) {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		CallFailList src = hfglService.get(issue.getId());
		src.setStatus(HF_STATUS.CloseStatus.getDesc());
		hfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{issue.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("结案回访不成功件完成！").toString(); 
	}
	
	@Log(message="进行了信函的已发登记。")
	@RequiresPermissions("Callfail:edit")
	@RequestMapping(value="/issue/WithMailStatus", method=RequestMethod.POST)
	public @ResponseBody String mail(Long[] ids) {
		List<CallFailList> list = new ArrayList<CallFailList>();
		for(Long id:ids) {
			CallFailList src = hfglService.get(id);
			src.setLetterDate(new Date());
			src.setHasLetter("信函已发");
			list.add(src);
		}
		hfglService.batchMail(list);
		LogUitls.putArgs(LogMessageObject.newWrite());
		return	AjaxObject.newOk("登记信函已发成功！").toString(); 
	}
	
	@RequiresPermissions("Callfail:view")
	@RequestMapping(value="/issue/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		String status = request.getParameter("status");
		LOG.debug("-------------- status: " + status + ", user org code:" + userOrg.getOrgCode());
		CallFailList issue = new CallFailList();
//		if(status == null) {
//			status = HF_STATUS.NewStatus.getDesc();
//		} else if(status.trim().length()>0) {
//			issue.setStatus(status);
//		}
		issue.setStatus(status);
		
		if(page.getOrderField() == null) {
			page.setOrderField("policy.policyDate");
			page.setOrderDirection("ASC");
		}
		
		Specification<VCallFailList> specification = null;
		
		if(user.getOrganization().getOrgCode().contains("11185")) {
			if(status == null) {
				specification = DynamicSpecifications.bySearchFilter(request, VCallFailList.class,
						new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.NewStatus.getDesc()),
						new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.ResetStatus.getDesc()),
						new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.CallFailStatus.getDesc()),
						new SearchFilter("lastDateNum", Operator.GTE, 3));
			} else {
				specification = DynamicSpecifications.bySearchFilter(request, VCallFailList.class,
						new SearchFilter("status", Operator.LIKE, status));
			}
		} else if (user.getOrganization().getOrgCode().length() > 4) {
			if(status == null) {
				LOG.debug("-------------- 111: " );
				specification = DynamicSpecifications.bySearchFilter(request, VCallFailList.class,
						new SearchFilter("status", Operator.LIKE, HF_STATUS.CallFailStatus.getDesc()),
						new SearchFilter("lastDateNum", Operator.LTE, 3),
						new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
			} else {
				LOG.debug("-------------- 222: " );
				specification = DynamicSpecifications.bySearchFilter(request, VCallFailList.class,
					new SearchFilter("status", Operator.LIKE, status),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
			}
		} else {
			if(status == null) {
				LOG.debug("-------------- 333: " );
				issue.setStatus(HF_STATUS.DealStatus.getDesc());
				specification = DynamicSpecifications.bySearchFilter(request, VCallFailList.class,
						new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.NewStatus.getDesc()),
						new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.DealStatus.getDesc()),
						new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.ResetStatus.getDesc()),
						new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.DoorSuccessStatus.getDesc()),
						new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.DoorFailStatus.getDesc()),
						new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.CallSuccessStatus.getDesc()),
						new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.CallFailStatus.getDesc()));
			} else {
				LOG.debug("-------------- 444: " );
				specification = DynamicSpecifications.bySearchFilter(request, VCallFailList.class,
					new SearchFilter("status", Operator.LIKE, status));
			}
		}
		List<VCallFailList> issues = hfglService.findByExample(specification, page);
		
		map.put("issue", issue);
		map.put("hfStatusList", HF_STATUS.values());
		LOG.debug("---111--" + issues);
		map.put("page", page);
		map.put("issues", issues);
		return LIST;
	}
	
	@RequiresPermissions("Callfail:view")
	@RequestMapping(value="/issuelist", method={RequestMethod.GET, RequestMethod.POST})
	public String issueList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		map.put("issueList", hfglService.getTODOIssueList(shiroUser.getUser()));
		return ISSUE_LIST;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
