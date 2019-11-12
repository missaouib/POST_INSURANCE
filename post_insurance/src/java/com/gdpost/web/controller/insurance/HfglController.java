/**
 * 
 * ==========================================================
 * 回访管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authz.annotation.Logical;
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
import com.gdpost.web.entity.insurance.CallFailList;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.OrganizationService;
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
	@Autowired
	private OrganizationService orgService;

	private static final String VIEW = "insurance/hfgl/wtj/view";
	private static final String UPDATE = "insurance/hfgl/wtj/update";
	private static final String PROV_UPDATE = "insurance/hfgl/wtj/provupdate";
	private static final String HQ_UPDATE = "insurance/hfgl/wtj/hqupdate";
	private static final String LIST = "insurance/hfgl/wtj/list";
	private static final String MAX_LIST = "insurance/hfgl/wtj/maxlist";
	private static final String ISSUE_LIST = "insurance/hfgl/wtj/issuelist";
	private static final String RESET = "insurance/hfgl/wtj/setPhone";
	private static final String CALL_AGAIN = "insurance/hfgl/wtj/callAgain";
	private static final String SET_MAIL_DATE = "insurance/hfgl/wtj/mailDate";
	private static final String TO_HELP = "insurance/help/hfgl";
	
	private static final String TO_XLS = "insurance/hfgl/wtj/toXls";
	
	@RequestMapping(value="/help", method=RequestMethod.GET)
	public String toHelp() {
		return TO_HELP;
	}
	
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
//		if(issue.getStatus() == null || issue.getStatus().equals(HF_STATUS.NewStatus.getDesc())) {
//			issue.setStatus(HF_STATUS.DealStatus.getDesc());
//		}
		map.put("issue", issue);
		List<CallDealType> cdtList = hfglService.getCallDealTypeList(CallDealType.ORG_TYPE);
		map.put("orgTypeList", cdtList);
		return UPDATE;
	}
	
	@RequiresPermissions("Callfail:provEdit")
	@RequestMapping(value="/issue/toSetMailDate", method={RequestMethod.POST, RequestMethod.GET})
	public String preMail(String ids, Map<String, Object> map) {
		LOG.info("-------------" + ids);
		map.put("hfIds", ids);
		return SET_MAIL_DATE;
	}
	
	@RequiresPermissions("Callfail:provEdit")
	@RequestMapping(value="/issue/provUpdate/{id}", method=RequestMethod.GET)
	public String preProvUpdate(@PathVariable Long id, Map<String, Object> map) {
		CallFailList issue = hfglService.get(id);
//		if(issue.getStatus() == null || issue.getStatus().equals(HF_STATUS.NewStatus.getDesc())) {
//			issue.setStatus(HF_STATUS.DealStatus.getDesc());
//		}
		map.put("issue", issue);
		
		List<CallDealType> cdtList = hfglService.getCallDealTypeList(CallDealType.HQ_TYPE);
		map.put("hqTypeList", cdtList);
		return PROV_UPDATE;
	}
	
	@RequiresPermissions("Callfail:11185Edit")
	@RequestMapping(value="/issue/hqUpdate/{id}", method=RequestMethod.GET)
	public String preHqUpdate(@PathVariable Long id, Map<String, Object> map) {
		CallFailList issue = hfglService.get(id);
//		if(issue.getStatus() == null || issue.getStatus().equals(HF_STATUS.NewStatus.getDesc())) {
//			issue.setStatus(HF_STATUS.CallFailStatus.getDesc());
//		}
		map.put("issue", issue);
		List<CallDealType> cdtList = hfglService.getCallDealTypeList(CallDealType.HQ_TYPE);
		map.put("hqTypeList", cdtList);
		return HQ_UPDATE;
	}
	
	@Log(message="回复了{0}回访不成功件的信息。", level=LogLevel.WARN, module=LogModule.HFGL)
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
		boolean hqDone = src.getStatus().equals(HF_STATUS.CallSuccessStatus.getDesc())?true:false;
		if(issue.getDealType().contains("可再访")) {
			src.setCanCallAgain(true);
			src.setCanCallAgainRemark(issue.getDealDesc());
			src.setResetDate(new Date());
		} else if(issue.getDealType().equals("成功件") || issue.getDealType().equals("标准件") || issue.getDealType().equals("问题件")) {
			if(!hqDone) {
				src.setStatus(HF_STATUS.DoorSuccessStatus.getDesc());
			}
			src.setDealStatus(HF_STATUS.DoorSuccessStatus.getDesc());
			src.setOrgDealFlag(1);
		} else if(issue.getDealType().equals("已退保")) {
			if(!hqDone) {
				src.setStatus(HF_STATUS.TerminalStatus.getDesc());
			}
			src.setDealStatus(HF_STATUS.TerminalStatus.getDesc());
			src.setOrgDealFlag(0);
		} else {
			if(!hqDone) {
				src.setStatus(HF_STATUS.DoorFailStatus.getDesc());
			}
			src.setDealStatus(HF_STATUS.DoorFailStatus.getDesc());
			src.setOrgDealFlag(0);
		}
		hfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复回访不成功件成功！").toString(); 
	}
	
	@Log(message="回复了{0}回访不成功件的信息。", level=LogLevel.WARN, module=LogModule.HFGL)
	@RequiresPermissions("Callfail:provEdit")
	@RequestMapping(value="/issue/provUpdate", method=RequestMethod.POST)
	public @ResponseBody String provUpdate(CallFailList issue) {
		CallFailList src = hfglService.get(issue.getId());
		src.setProvIssueType(issue.getProvIssueType());
		src.setProvDealRst(issue.getProvDealRst());
		src.setProvDealDate(new Date());
		src.setProvDealMan(issue.getProvDealMan());
		src.setProvDealRemark(issue.getProvDealRemark());
		//src.setStatus(XQ_STATUS.DealStatus.getDesc());
		src.setProvDealNum((src.getProvDealNum()==null?0:src.getProvDealNum())+1);
		//src.setStatus(issue.getStatus());
		if(issue.getProvIssueType().equals("成功件")) {
			src.setStatus(HF_STATUS.CallSuccessStatus.getDesc());
			src.setDealStatus(HF_STATUS.CallSuccessStatus.getDesc());
			src.setProvDealFlag(1);
		} else if(issue.getProvIssueType().equals("已退保")) {
			src.setStatus(HF_STATUS.TerminalStatus.getDesc());
			src.setDealStatus(HF_STATUS.TerminalStatus.getDesc());
			src.setProvDealFlag(0);
		} else if(issue.getProvIssueType().equals("拒访")) {
			src.setStatus(HF_STATUS.RejectStatus.getDesc());
			src.setDealStatus(HF_STATUS.RejectStatus.getDesc());
			src.setProvDealFlag(0);
		} else if(issue.getProvIssueType().equals("需上门回访")) {
			src.setStatus(HF_STATUS.NeedDoorStatus.getDesc());
			src.setDealStatus(HF_STATUS.NeedDoorStatus.getDesc());
			src.setProvDealFlag(0);
		} else {
			src.setStatus(HF_STATUS.CallFailStatus.getDesc());
			src.setDealStatus(HF_STATUS.CallFailStatus.getDesc());
			src.setProvDealFlag(0);
		}
		/*
		if(issue.getStatus().equals(HF_STATUS.CallSuccessStatus.getDesc())) {
			src.setProvDealFlag(1);
		}
		*/
		hfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复回访不成功件成功！").toString(); 
	}
	
	@Log(message="回复了{0}回访不成功件的信息。", level=LogLevel.WARN, module=LogModule.HFGL)
	@RequiresPermissions("Callfail:11185Edit")
	@RequestMapping(value="/issue/hqUpdate", method=RequestMethod.POST)
	public @ResponseBody String hqUpdate(CallFailList issue) {
		CallFailList src = hfglService.get(issue.getId());
		if(issue.getHqDealType() != null && issue.getHqDealType().trim().length()>0) {
			src.setHqDealRst(issue.getHqDealRst());
			src.setHqDealDate(issue.getHqDealDate());
			src.setHqDealMan(issue.getHqDealMan());
			src.setHqDealType(issue.getHqDealType());
			if(issue.getHqDealType().equals("已退保")) {
				src.setStatus(HF_STATUS.TerminalStatus.getDesc());
				src.setDealStatus(HF_STATUS.TerminalStatus.getDesc());
				src.setHqDealFlag(0);
			} else if(issue.getHqDealType().equals("成功件")) {
				src.setStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setDealStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setHqDealFlag(1);
			} else {
				src.setStatus(HF_STATUS.CallFailStatus.getDesc());
				src.setDealStatus(HF_STATUS.CallFailStatus.getDesc());
				src.setHqDealFlag(0);
			} 
		}
		if(issue.getHqDealType2() != null && issue.getHqDealType2().trim().length()>0) {
			src.setHqDealRst2(issue.getHqDealRst2());
			src.setHqDealDate2(issue.getHqDealDate2());
			src.setHqDealMan2(issue.getHqDealMan2());
			src.setHqDealType2(issue.getHqDealType2());
			if(issue.getHqDealType2().equals("已退保")) {
				src.setStatus(HF_STATUS.TerminalStatus.getDesc());
				src.setDealStatus(HF_STATUS.TerminalStatus.getDesc());
				src.setHqDealFlag(0);
			} else if(issue.getHqDealType2().equals("成功件")) {
				src.setStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setDealStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setHqDealFlag(1);
			} else {
				src.setStatus(HF_STATUS.CallFailStatus.getDesc());
				src.setDealStatus(HF_STATUS.CallFailStatus.getDesc());
				src.setHqDealFlag(0);
			}
		}
		if(issue.getHqDealType3() != null && issue.getHqDealType3().trim().length()>0) {
			src.setHqDealRst3(issue.getHqDealRst3());
			src.setHqDealDate3(issue.getHqDealDate3());
			src.setHqDealMan3(issue.getHqDealMan3());
			src.setHqDealType3(issue.getHqDealType3());
			if(issue.getHqDealType3().equals("已退保")) {
				src.setStatus(HF_STATUS.TerminalStatus.getDesc());
				src.setDealStatus(HF_STATUS.TerminalStatus.getDesc());
				src.setHqDealFlag(0);
			} else if(issue.getHqDealType3().equals("成功件")) {
				src.setStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setDealStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setHqDealFlag(1);
			} else {
				src.setStatus(HF_STATUS.CallFailStatus.getDesc());
				src.setDealStatus(HF_STATUS.CallFailStatus.getDesc());
				src.setHqDealFlag(0);
			} 
		}
		if(issue.getHqDealType4() != null && issue.getHqDealType4().trim().length()>0) {
			src.setHqDealRst4(issue.getHqDealRst4());
			src.setHqDealDate4(issue.getHqDealDate4());
			src.setHqDealMan4(issue.getHqDealMan4());
			src.setHqDealType4(issue.getHqDealType4());
			if(issue.getHqDealType4().equals("已退保")) {
				src.setStatus(HF_STATUS.TerminalStatus.getDesc());
				src.setDealStatus(HF_STATUS.TerminalStatus.getDesc());
				src.setHqDealFlag(0);
			} else if(issue.getHqDealType4().equals("成功件")) {
				src.setStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setDealStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setHqDealFlag(1);
			} else {
				src.setStatus(HF_STATUS.CallFailStatus.getDesc());
				src.setDealStatus(HF_STATUS.CallFailStatus.getDesc());
				src.setHqDealFlag(0);
			} 
		}
		if(issue.getHqDealType5() != null && issue.getHqDealType5().trim().length()>0) {
			src.setHqDealRst5(issue.getHqDealRst5());
			src.setHqDealDate5(issue.getHqDealDate5());
			src.setHqDealMan5(issue.getHqDealMan5());
			src.setHqDealType5(issue.getHqDealType5());
			if(issue.getHqDealType5().equals("已退保")) {
				src.setStatus(HF_STATUS.TerminalStatus.getDesc());
				src.setDealStatus(HF_STATUS.TerminalStatus.getDesc());
				src.setHqDealFlag(0);
			} else if(issue.getHqDealType5().equals("成功件")) {
				src.setStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setDealStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setHqDealFlag(1);
			} else {
				src.setStatus(HF_STATUS.CallFailStatus.getDesc());
				src.setDealStatus(HF_STATUS.CallFailStatus.getDesc());
				src.setHqDealFlag(0);
			} 
		}
		if(issue.getHqDealType6() != null && issue.getHqDealType6().trim().length()>0) {
			src.setHqDealRst6(issue.getHqDealRst6());
			src.setHqDealDate6(issue.getHqDealDate6());
			src.setHqDealMan6(issue.getHqDealMan6());
			src.setHqDealType6(issue.getHqDealType6());
			if(issue.getHqDealType6().equals("已退保")) {
				src.setStatus(HF_STATUS.TerminalStatus.getDesc());
				src.setDealStatus(HF_STATUS.TerminalStatus.getDesc());
				src.setHqDealFlag(0);
			} else if(issue.getHqDealType6().equals("成功件")) {
				src.setStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setDealStatus(HF_STATUS.CallSuccessStatus.getDesc());
				src.setHqDealFlag(1);
			} else {
				src.setStatus(HF_STATUS.CallFailStatus.getDesc());
				src.setDealStatus(HF_STATUS.CallFailStatus.getDesc());
				src.setHqDealFlag(0);
			} 
		}
		src.setHqDealNum((src.getHqDealNum()==null?0:src.getHqDealNum())+1);
//		if(issue.getStatus().equals(HF_STATUS.CallSuccessStatus.getDesc())) {
//			src.setHqDealFlag(1);
//		}
		
		hfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("回复回访不成功件成功！").toString(); 
	}
	
	@RequiresPermissions("Callfail:edit")
	@RequestMapping(value="/updateResetStatus/{id}", method=RequestMethod.GET)
	public String preUpdateResetStatus(@PathVariable Long id, Map<String, Object> map) {
		CallFailList req = hfglService.get(id);
		map.put("cfl", req);
		return RESET;
	}
	
	@RequiresPermissions(value={"Callfail:edit","Callfail:provEdit"}, logical=Logical.OR)
	@RequestMapping(value="/callReset/{id}", method=RequestMethod.GET)
	public String preCallAgain(@PathVariable Long id, Map<String, Object> map) {
		CallFailList req = hfglService.get(id);
		map.put("cfl", req);
		return CALL_AGAIN;
	}
	
	@Log(message="进行了{0}设置了可再访。", level=LogLevel.WARN, module=LogModule.HFGL)
	@RequiresPermissions(value={"Callfail:edit","Callfail:provEdit"}, logical=Logical.OR)
	@RequestMapping(value="/callReset/{id}", method=RequestMethod.POST)
	public @ResponseBody String setCallAgain(ServletRequest request, @PathVariable("id") Long id) {
		CallFailList call = hfglService.get(id);
		String callAgainRemark = request.getParameter("canCallAgainRemark");
		String resetPhone = request.getParameter("resetPhone");
		call.setResetPhone(resetPhone);
		call.setCanCallAgain(true);
		call.setCanCallAgainRemark(callAgainRemark);
		call.setResetDate(new Date());
		hfglService.saveOrUpdate(call);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{call.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("设置成功！").toString();
	}
	
	@Log(message="进行了{0}设置了可再访。", level=LogLevel.WARN, module=LogModule.HFGL)
	@RequiresPermissions(value={"Callfail:edit","Callfail:provEdit"}, logical=Logical.OR)
	@RequestMapping(value="/batchCallReset", method=RequestMethod.POST)
	public @ResponseBody String batchCallAgain(ServletRequest request, Long[] ids) {
		String[] policys = new String[ids.length];
		CallFailList call = null;
		for(int i=0;i<ids.length; i++) {
			call = hfglService.get(ids[i]);
			call.setCanCallAgain(true);
			call.setResetDate(new Date());
			hfglService.saveOrUpdate(call);
		}
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return	AjaxObject.newOk("设置成功！").setCallbackType("").toString();
	}
	
	@Log(message="进行了{0}更新操作{1}。", level=LogLevel.WARN, module=LogModule.HFGL)
	@RequiresPermissions(value={"Callfail:edit","Callfail:provEdit"}, logical=Logical.OR)
	@RequestMapping(value="/issue/{status}/{id}", method=RequestMethod.POST)
	public @ResponseBody String updateStatus(ServletRequest request, @PathVariable("status") String status, @PathVariable("id") Long id) {
		CallFailList call = hfglService.get(id);
		String resetPhone = request.getParameter("resetPhone");
		HF_STATUS bs = HF_STATUS.ResetStatus;
		String desc = null;
		try {
			bs = HF_STATUS.valueOf(status);
		}catch (Exception ex) {
			return	AjaxObject.newError("状态不对！").setCallbackType("").toString();
		}
		switch (bs) {
		case ResetStatus:
			desc = "成功重置电话";
			call.setResetPhone(resetPhone);
			call.setResetDate(new Date());
			break;
		case CloseStatus:
			desc = "成功进行结案";
			call.setStatus(bs.getDesc());
			break;
			default:
				break;
		}
		call.setStatus(bs.getDesc());
		hfglService.saveOrUpdate(call);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{call.getPolicy().getPolicyNo(), desc}));
		return	AjaxObject.newOk(desc).setCallbackType("").toString();
	}
	
	@Log(message="对{0}回访不成结进行了结案关闭。", level=LogLevel.WARN, module=LogModule.HFGL)
	@RequiresPermissions("Callfail:provEdit")
	@RequestMapping(value="/issue/CloseStatus", method=RequestMethod.POST)
	public @ResponseBody String closeMany(Long[] ids) {
		String[] policys = new String[ids.length];
		try {
			CallFailList call = null;
			for (int i = 0; i < ids.length; i++) {
				call = hfglService.get(ids[i]);
				call.setStatus(HF_STATUS.CloseStatus.getDesc());
				hfglService.saveOrUpdate(call);
				
				policys[i] = call.getIssueNo();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("结案关闭回访不成件失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("成功结案关闭！").setCallbackType("").toString();
	}
	
	@Log(message="结案了{0}回访不成功件的信息。", level=LogLevel.WARN, module=LogModule.HFGL)
	@RequiresPermissions("Callfail:edit")
	@RequestMapping(value="/issue/close", method=RequestMethod.POST)
	public @ResponseBody String close(CallFailList issue) {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		CallFailList src = hfglService.get(issue.getId());
		src.setStatus(HF_STATUS.CloseStatus.getDesc());
		hfglService.saveOrUpdate(src);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("结案回访不成功件完成！").toString(); 
	}
	
	@Log(message="删除了{0}回访不成功件的信息。", level=LogLevel.WARN, module=LogModule.HFGL)
	@RequiresPermissions("Callfail:provEdit")
	@RequestMapping(value="/issue/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(ServletRequest request, @PathVariable Long id) {
		//ShiroUser shiroUser = SecurityUtils.getShiroUser();
		CallFailList src = hfglService.get(id);
		hfglService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{src.getPolicy().getPolicyNo()}));
		return	AjaxObject.newOk("删除回访不成功件完成！").toString(); 
	}
	
	@Log(message="进行了信函的已发登记。", level=LogLevel.WARN, module=LogModule.HFGL)
	@RequiresPermissions("Callfail:provEdit")
	@RequestMapping(value="/issue/setMailDate", method=RequestMethod.POST)
	public @ResponseBody String mail(String ids, Date mailDate) {
		List<CallFailList> list = new ArrayList<CallFailList>();
		String[] strIds = ids.split(",");
		for(String id:strIds) {
			CallFailList src = hfglService.get(new Long(id));
			src.setLetterDate(mailDate);
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
		CallFailList issue = new CallFailList();
		
		String dealType = request.getParameter("dealType");
		String hqDealType = request.getParameter("hqDealType");
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);
		
		issue.setDealType(dealType);
		issue.setHqDealType(hqDealType);
		issue.setDuration(duration);
		request.setAttribute("dealType", dealType);
		request.setAttribute("hqDealType", hqDealType);
		request.setAttribute("duration", duration);
		
		//默认返回未处理工单
		String status = request.getParameter("status");
		String statusNull = request.getParameter("statusNull");
		Enumeration<String> params = request.getParameterNames();
		String param = null;
		boolean check = false;
		while(params.hasMoreElements()) {
			param = params.nextElement();
			if(!param.equals("statusNull") && !param.equals("_") && !param.contains("page") && !param.contains("Page") && !param.contains("order") && !param.equals("totalCount") && request.getParameter(param).trim().length()>0) {
				check = true;
			}
		}
		if(check) {
			statusNull = "";
		} else {
			status = null;
			statusNull = "NULL";
		}
//		if(statusNull != null && statusNull.trim().length()>0 ) {
//			if(check || (status != null && status.trim().length()>0)) {
//				statusNull = "";
//			} else {
//				status = null;
//			}
//		} else {
//			statusNull = "NULL";
//		}
		String encodeStatus = "";
		org.apache.catalina.util.URLEncoder urlEncoder = new org.apache.catalina.util.URLEncoder();
		if(status != null) {
			encodeStatus = urlEncoder.encode(status, Charset.defaultCharset());
		} else {
			request.setAttribute("statusNull", statusNull);
		}
		//request.setAttribute("encodeStatus", Base64Utils.encodeToString(status.getBytes()));
		request.setAttribute("status", encodeStatus);
		LOG.debug("-------------- status: " + status + ", user org code:" + userOrg.getOrgCode());
		String hasLetter = request.getParameter("search_LIKE_hasLetter");
		if(hasLetter == null) {
			hasLetter = "";
		}
		//request.setAttribute("encodeHasLetter", Base64Utils.encodeToString(URLEncoder.encode(hasLetter, "UTF-8").getBytes()));
		request.setAttribute("hasLetter", urlEncoder.encode(hasLetter, Charset.defaultCharset()));
		
		issue.setSearch_LIKE_hasLetter(hasLetter);
		issue.setStatus(status);
		
		String canCallAgainStr = request.getParameter("canCallAgain");
		String orgDeal = request.getParameter("orgDealFlag");
		String hqDealFlag = request.getParameter("hqDealFlag");
		
		boolean canCallAgain = false;
		if(canCallAgainStr != null && canCallAgainStr.equals("true")) {
			canCallAgain = true;
			issue.setCanCallAgain(true);
			request.setAttribute("canCallAgain", "true");
		}
		
		if(page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("readyDate");
			page.setOrderDirection("DESC");
		}
		
		String orgCode = request.getParameter("policy.orgCode");
		if(orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = user.getOrganization().getOrgCode();
			if(orgCode.contains("11185")) {
				orgCode = "8644";
			}
		} else {
			if(!orgCode.contains(user.getOrganization().getOrgCode())){
				orgCode = user.getOrganization().getOrgCode();
			}
			String orgName = request.getParameter("policy.name");
			request.setAttribute("policy_orgCode", orgCode);
			request.setAttribute("policy_name", orgName);
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		
		if(dealType != null && dealType.trim().length() >0) {
			csf.add(new SearchFilter("dealType", Operator.EQ, dealType));
		}
		if(hqDealType != null && hqDealType.trim().length() >0) {
			csf.add(new SearchFilter("hqDealType", Operator.EQ, hqDealType));
		}
		
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
		if(canCallAgain) {
			csf.add(new SearchFilter("canCallAgain", Operator.EQ, true));
		}
		
		if(orgDeal != null && orgDeal.trim().length()>0) {
			issue.setOrgDealFlag(new Integer(orgDeal));
			request.setAttribute("orgDealFlag", orgDeal);
			csf.add(new SearchFilter("orgDealFlag", Operator.EQ, orgDeal));
		}
		
		if(hqDealFlag != null && hqDealFlag.trim().length()>0) {
			issue.setHqDealFlag(new Integer(hqDealFlag));
			request.setAttribute("hqDealFlag", hqDealFlag);
			csf.add(new SearchFilter("hqDealFlag", Operator.EQ, hqDealFlag));
		}
		
		if(duration >= 10) {
			csf.add(new SearchFilter("policy.duration", Operator.GTE, duration));
		}
		
		if(user.getOrganization().getOrgCode().contains("11185")) {
			if(status == null) {
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.NewStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.ResetStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.CallFailStatus.getDesc()));
				csf.add(new SearchFilter("lastDateNum", Operator.GTE, 3));
			} else if(status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		} else if (user.getOrganization().getOrgCode().length() > 4) {
			if(status == null) {
				if(user.getOrganization().getOrgCode().length() >= 6) {
					csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.ResetStatus.getDesc()));
				} else {
					LOG.debug("-------------- 111: " );
					csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.NewStatus.getDesc()));
					csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.ResetStatus.getDesc()));
					csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.CallFailStatus.getDesc()));
				}
				//issue.setStatus(HF_STATUS.ResetStatus.getDesc());
			} else if(status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		} else {
			if(status == null) {
				LOG.debug("-------------- 333: " );
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.NewStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.ResetStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.DoorSuccessStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.DoorFailStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.CallSuccessStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.CallFailStatus.getDesc()));
			} else if(status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		}
		Specification<CallFailList> specification = DynamicSpecifications.bySearchFilter(request, CallFailList.class, csf);
		List<CallFailList> issues = hfglService.findByExample(specification, page);
		
		map.put("issue", issue);
		map.put("hfStatusList", HF_STATUS.values());
		LOG.debug("---111--" + issues);
		
		List<CallDealType> cdtList = hfglService.getCallDealTypeList(CallDealType.ORG_TYPE);
		map.put("orgTypeList", cdtList);
		List<CallDealType> hqList = hfglService.getCallDealTypeList(CallDealType.HQ_TYPE);
		map.put("hqTypeList", hqList);
		
		map.put("page", page);
		map.put("issues", issues);
		return LIST;
	}
	
	@RequiresPermissions("Callfail:view")
	@RequestMapping(value="/issue/maxlist", method={RequestMethod.GET, RequestMethod.POST})
	public String maxList(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		CallFailList issue = new CallFailList();
		
		String dealType = request.getParameter("dealType");
		String hqDealType = request.getParameter("hqDealType");
		issue.setDealType(dealType);
		issue.setHqDealType(hqDealType);
		request.setAttribute("dealType", dealType);
		request.setAttribute("hqDealType", hqDealType);
		
		//默认返回未处理工单
		String status = request.getParameter("status");
		String statusNull = request.getParameter("statusNull");
		Map<String, String[]> params = request.getParameterMap();
		LOG.debug("---- params names:" + params);
		if(statusNull != null && statusNull.trim().length()>0 ) {
			if(status != null && status.trim().length()>0) {
				statusNull = "";
			} else {
				status = null;
			}
		} else {
			statusNull = "NULL";
		}
		
		LOG.debug("-------------- status: " + status + ", user org code:" + userOrg.getOrgCode());
		String hasLetter = request.getParameter("search_LIKE_hasLetter");
		LOG.debug("-------------- hasLetter: " + hasLetter);
		issue.setSearch_LIKE_hasLetter(hasLetter);
		request.setAttribute("search_LIKE_hasLetter", hasLetter);
		//request.setAttribute("hasLetter", hasLetter);
		//request.setAttribute("status", status);
		issue.setHasLetter(hasLetter);
		issue.setStatus(status);
		
		String encodeStatus = "";
		org.apache.catalina.util.URLEncoder urlEncoder = new org.apache.catalina.util.URLEncoder();
		if(status != null) {
			encodeStatus = urlEncoder.encode(status, Charset.defaultCharset());
		} else {
			request.setAttribute("statusNull", statusNull);
		}
		
		//request.setAttribute("encodeStatus", Base64Utils.encodeToString(status.getBytes()));
		request.setAttribute("status", encodeStatus);
		LOG.debug("-------------- status: " + status + ", user org code:" + userOrg.getOrgCode());
		if(hasLetter == null) {
			hasLetter = "";
		}
		//request.setAttribute("encodeHasLetter", Base64Utils.encodeToString(URLEncoder.encode(hasLetter, "UTF-8").getBytes()));
		request.setAttribute("hasLetter", urlEncoder.encode(hasLetter, Charset.defaultCharset()));
		
		if(page.getOrderField() == null || page.getOrderField().trim().length()<=0) {
			page.setOrderField("readyDate");
			page.setOrderDirection("DESC");
		}
		
		String orgCode = request.getParameter("policy.orgCode");
		if(orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = user.getOrganization().getOrgCode();
			if(orgCode.contains("11185")) {
				orgCode = "8644";
			}
		} else {
			if(!orgCode.contains(user.getOrganization().getOrgCode())){
				orgCode = user.getOrganization().getOrgCode();
			}
			//String orgName = request.getParameter("policy.name");
			Organization org = orgService.getByOrgCode(orgCode);
			request.setAttribute("policy_orgCode", orgCode);
			request.setAttribute("policy_name", org.getName());
			//String encodeOrgName = Base64Utils.encodeToString(org.getName().getBytes());
			//request.setAttribute("encodeOrgName", encodeOrgName);
		}
		
		String canCallAgainStr = request.getParameter("canCallAgain");
		String orgDeal = request.getParameter("orgDealFlag");
		String hqDealFlag = request.getParameter("hqDealFlag");
		
		boolean canCallAgain = false;
		if(canCallAgainStr != null && canCallAgainStr.equals("true")) {
			canCallAgain = true;
			issue.setCanCallAgain(true);
			request.setAttribute("canCallAgain", "true");
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		
		if(dealType != null && dealType.trim().length() >0) {
			csf.add(new SearchFilter("dealType", Operator.EQ, dealType));
		}
		if(hqDealType != null && hqDealType.trim().length() >0) {
			csf.add(new SearchFilter("hqDealType", Operator.EQ, hqDealType));
		}
		
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
		
		if(canCallAgain) {
			csf.add(new SearchFilter("canCallAgain", Operator.EQ, true));
		}
		
		if(orgDeal != null && orgDeal.trim().length()>0) {
			issue.setOrgDealFlag(new Integer(orgDeal));
			request.setAttribute("orgDealFlag", orgDeal);
			csf.add(new SearchFilter("orgDealFlag", Operator.EQ, orgDeal));
		}
		
		if(hqDealFlag != null && hqDealFlag.trim().length()>0) {
			issue.setHqDealFlag(new Integer(hqDealFlag));
			request.setAttribute("hqDealFlag", hqDealFlag);
			csf.add(new SearchFilter("hqDealFlag", Operator.EQ, hqDealFlag));
		}
		
		if(user.getOrganization().getOrgCode().contains("11185")) {
			if(status == null) {
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.NewStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.ResetStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.CallFailStatus.getDesc()));
				csf.add(new SearchFilter("lastDateNum", Operator.GTE, 3));
			} else if(status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		} else if (user.getOrganization().getOrgCode().length() > 4) {
			if(status == null) {
				LOG.debug("-------------- 111: " );
				//csf.add(new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.NewStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.ResetStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.CallFailStatus.getDesc()));
				//csf.add(new SearchFilter("lastDateNum", Operator.LTE, 3));
			} else if(status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		} else {
			LOG.debug("-------------- 3330000: " );
			if(status == null) {
				LOG.debug("-------------- 333: " );
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.NewStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.ResetStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.DoorSuccessStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.DoorFailStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.CallSuccessStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.CallFailStatus.getDesc()));
			} else if(status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		}
		if(hasLetter != null && hasLetter.trim().length() > 0) {
			csf.add(new SearchFilter("hasLetter", Operator.EQ, hasLetter));
		}
		Specification<CallFailList> specification = DynamicSpecifications.bySearchFilter(request, CallFailList.class, csf);
		List<CallFailList> issues = hfglService.findByExample(specification, page);
		
		map.put("issue", issue);
		map.put("hfStatusList", HF_STATUS.values());
		LOG.debug("---111--" + issues);
		
		List<CallDealType> cdtList = hfglService.getCallDealTypeList(CallDealType.ORG_TYPE);
		map.put("orgTypeList", cdtList);
		List<CallDealType> hqList = hfglService.getCallDealTypeList(CallDealType.HQ_TYPE);
		map.put("hqTypeList", hqList);
		
		map.put("page", page);
		map.put("issues", issues);
		return MAX_LIST;
	}
	
	@Log(message="下载了回访不成功件列表。", level=LogLevel.INFO, module=LogModule.HFGL)
	@RequiresPermissions("Callfail:view")
	@RequestMapping(value="/toXls", method=RequestMethod.GET)
	public String toXls(ServletRequest request, Page page, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();//userService.get(shiroUser.getId());
		Organization userOrg = user.getOrganization();
		
		String dealType = request.getParameter("dealType");
		String hqDealType = request.getParameter("hqDealType");
		
		String durationStr = request.getParameter("duration");
		Integer duration = (durationStr == null || !NumberUtils.isDigits(durationStr)) ? 0
				: Integer.valueOf(durationStr);
		
		//默认返回未处理工单
		String status = request.getParameter("status");
		
		LOG.debug("-------------- status: " + status + ", user org code:" + userOrg.getOrgCode());
		String hasLetter = request.getParameter("search_LIKE_hasLetter");
		String canCallAgainStr = request.getParameter("canCallAgain");
		String orgDeal = request.getParameter("orgDealFlag");
		String hqDealFlag = request.getParameter("hqDealFlag");
		
		boolean canCallAgain = false;
		if(canCallAgainStr != null && canCallAgainStr.equals("true")) {
			canCallAgain = true;
		}
		
		if(page.getOrderField() == null || page.getOrderField().trim().length()<=0) {
			page.setOrderField("issueNo");
			page.setOrderDirection("DESC");
		}
		
		page.setNumPerPage(Integer.MAX_VALUE);
		
		String orgCode = request.getParameter("policy.orgCode");
		if(orgCode == null || orgCode.trim().length() <= 0) {
			orgCode = user.getOrganization().getOrgCode();
			if(orgCode.contains("11185")) {
				orgCode = "8644";
			}
		} else {
			if(!orgCode.contains(user.getOrganization().getOrgCode())){
				orgCode = user.getOrganization().getOrgCode();
			}
		}
		
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		
		if(dealType != null && dealType.trim().length() >0) {
			csf.add(new SearchFilter("dealType", Operator.EQ, dealType));
		}
		if(hqDealType != null && hqDealType.trim().length() >0) {
			csf.add(new SearchFilter("hqDealType", Operator.EQ, hqDealType));
		}
		
		csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, orgCode));
		
		if(canCallAgain) {
			csf.add(new SearchFilter("canCallAgain", Operator.EQ, true));
		}
		
		if(orgDeal != null && orgDeal.trim().length()>0) {
			csf.add(new SearchFilter("orgDealFlag", Operator.EQ, orgDeal));
		}
		
		if(hqDealFlag != null && hqDealFlag.trim().length()>0) {
			csf.add(new SearchFilter("hqDealFlag", Operator.EQ, hqDealFlag));
		}
		
		if(duration >= 10) {
			csf.add(new SearchFilter("policy.duration", Operator.GTE, duration));
		}
		
		if(user.getOrganization().getOrgCode().contains("11185")) {
			if(status == null) {
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.NewStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.ResetStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.CallFailStatus.getDesc()));
				csf.add(new SearchFilter("lastDateNum", Operator.GTE, 3));
			} else if(status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		} else if (user.getOrganization().getOrgCode().length() > 4) {
			if(status == null) {
				LOG.debug("-------------- 111: " );
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.NewStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.ResetStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.CallFailStatus.getDesc()));
				csf.add(new SearchFilter("lastDateNum", Operator.LTE, 3));
			} else if(status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		} else {
			LOG.debug("-------------- 3330000: " );
			if(status == null) {
				LOG.debug("-------------- 333: " );
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.NewStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.ResetStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.DoorSuccessStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.DoorFailStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.CallSuccessStatus.getDesc()));
				csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.CallFailStatus.getDesc()));
			} else if(status.trim().length() > 0) {
				csf.add(new SearchFilter("status", Operator.EQ, status));
			}
		}
		if(hasLetter != null && hasLetter.trim().length() > 0) {
			csf.add(new SearchFilter("hasLetter", Operator.EQ, hasLetter));
		}
		Specification<CallFailList> specification = DynamicSpecifications.bySearchFilter(request, CallFailList.class, csf);
		List<CallFailList> reqs = hfglService.findByExample(specification, page);
		
		map.put("reqs", reqs);
		return TO_XLS;
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
