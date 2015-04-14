package com.sendtend.web.controller.uploadadmin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sendtend.utils.UploadDataHelper.UploadDataUtils;
import com.sendtend.web.entity.member.TblMember;
import com.sendtend.web.entity.member.TblMemberDataStatus;
import com.sendtend.web.log.Log;
import com.sendtend.web.log.LogMessageObject;
import com.sendtend.web.log.impl.LogUitls;
import com.sendtend.web.service.member.MemberDataStatusService;
import com.sendtend.web.service.member.MemberService;
import com.sendtend.web.service.uploaddatamanage.UploadDataService;
import com.sendtend.web.util.dwz.AjaxObject;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.persistence.DynamicSpecifications;

@Controller
@RequestMapping("/management/uploadstatus")
public class UploadStatusController {
	private static final Logger LOG = LoggerFactory.getLogger(UploadStatusController.class);
	
	private static final String LIST = "management/uploaddata/uploadstatus";	// 查询数据
	private static final String LOOKUP_MEMBER = "management/uploaddata/lookup_member";
	
	@Autowired
	private MemberDataStatusService memberDataStatusService;
	
	@Autowired
	private MemberService memberService;

	@Autowired
	private UploadDataService uploadDataService;
	
	@InitBinder
	public void initListBinder(WebDataBinder binder) {
		// 设置需要包裹的元素个数，默认为256
	    binder.setAutoGrowCollectionLimit(5000);
	}
	
	@RequiresPermissions("uploadstatus:show")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String preList(HttpServletRequest request, Page page, Map<String, Object> map) {		
		// 返回年月
		int iLastNY = UploadDataUtils.getLastNianYue();
		int iNY = UploadDataUtils.getNianYue();
		int iNextNY = UploadDataUtils.getNextNianYue();
		int iLastNY2 = UploadDataUtils.getLastNianYue(iLastNY);
		
		List<Integer> listNY = new ArrayList<Integer>();
		listNY.add(iNY);
		//listNY.add(iNextNY);
		listNY.add(iLastNY);
		listNY.add(iLastNY2);
		
		map.put("page", page);
		map.put("items", null);
		map.put("ny", listNY);
		
		return LIST;
	}
	
	@Log(message="查询上传数据{0}。")
	@RequiresPermissions("uploadstatus:show")
	@RequestMapping(value="/list", method={RequestMethod.POST})
	public String list(HttpServletRequest request, Page page, Map<String, Object> map) {
		Specification<TblMemberDataStatus> specification = DynamicSpecifications.bySearchFilter(request, TblMemberDataStatus.class);
		page.setOrderField("id");
		page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
		List<TblMemberDataStatus> items = memberDataStatusService.findAll(specification, page);
		
		// 返回年月
		int iLastNY = UploadDataUtils.getLastNianYue();
		int iNY = UploadDataUtils.getNianYue();
		int iNextNY = UploadDataUtils.getNextNianYue();
		int iLastNY2 = UploadDataUtils.getLastNianYue(iLastNY);
		
		List<Integer> listNY = new ArrayList<Integer>();
		listNY.add(iNY);
		//listNY.add(iNextNY);
		listNY.add(iLastNY);
		listNY.add(iLastNY2);
		
		map.put("page", page);
		map.put("items", items);
		map.put("ny", listNY);
		
		return LIST;
	}
	
	@RequiresPermissions("uploadstatus:show")
	@RequestMapping(value="/lookup2member", method={RequestMethod.GET})
	public String lookup(Map<String, Object> map) {
		TblMember member = memberService.getTree();
		map.put("member", member);
		return LOOKUP_MEMBER;
	}
	
	@Log(message="删除了{0}数据。")
	@RequiresPermissions("uploadstatus:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String delete(HttpServletRequest request, Long[] ids) {
		String strMessage = "";
		for(Long id : ids) {
			TblMemberDataStatus item = memberDataStatusService.get(id);
			strMessage += item.getNy() + "" + item.getTblMember().getMemberName() + ",";
		 	uploadDataService.delete(request, item.getNy(), item.getTblMember().getId());
		}

		if(strMessage.length() > 0) {
			strMessage = strMessage.substring(0, strMessage.length() - 1);
		}
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{strMessage}));
		String strResult = AjaxObject.newOk("删除数据成功！").setCallbackType("").toString();
		return strResult;
	}
}