package com.gdpost.web.controller.uploadadmin;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.entity.member.TblMember;
import com.gdpost.web.entity.member.TblMemberData;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.member.MemberService;
import com.gdpost.web.service.uploaddatamanage.StatisticsService;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;

@Controller
@RequestMapping("/management/queryuploaddata")
public class UploadAdminController {
	private static final Logger LOG = LoggerFactory.getLogger(UploadAdminController.class);
	
	private static final String LIST = "management/uploaddata/uploaddata";	// 查询数据
	private static final String LOOKUP_MEMBER = "management/uploaddata/lookup_member";
	
	@Autowired
	private StatisticsService statisticsService;
	
	@Autowired
	private MemberService memberService;
	
	@InitBinder
	public void initListBinder(WebDataBinder binder) {
		// 设置需要包裹的元素个数，默认为256
	    binder.setAutoGrowCollectionLimit(5000);
	}
	
	@RequiresPermissions("queryuploaddata:show")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String preList(HttpServletRequest request, Page page, Map<String, Object> map) {		
		// 返回年月
		int iLastNY = UploadDataUtils.getLastNianYue();
		int iNY = UploadDataUtils.getNianYue();
		int iNextNY = UploadDataUtils.getNextNianYue();
		int iLastNY2 = UploadDataUtils.getLastNianYue(iLastNY);
		
		List<Integer> listNY = new ArrayList<Integer>();
		//listNY.add(iLastNY);
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
	@RequiresPermissions("queryuploaddata:show")
	@RequestMapping(value="/list", method={RequestMethod.POST})
	public String list(HttpServletRequest request, Page page, Map<String, Object> map) {
		Specification<TblMemberData> specification = DynamicSpecifications.bySearchFilter(request, TblMemberData.class);
		List<TblMemberData> items = statisticsService.findAll(specification, page);
		
		// 返回年月
		int iLastNY = UploadDataUtils.getLastNianYue();
		int iNY = UploadDataUtils.getNianYue();
		int iNextNY = UploadDataUtils.getNextNianYue();
		int iLastNY2 = UploadDataUtils.getLastNianYue(iLastNY);
		
		List<Integer> listNY = new ArrayList<Integer>();
		listNY.add(iLastNY);
		listNY.add(iNY);
		//listNY.add(iNextNY);
		listNY.add(iLastNY);
		listNY.add(iLastNY2);
		
		map.put("page", page);
		map.put("items", items);
		map.put("ny", listNY);
		
		String strLog = "";
		if(request.getParameter("search_EQ_tblMember.id") != null && !request.getParameter("search_EQ_tblMember.id").equals("")) {
			strLog += ",连锁:" + request.getParameter("search_EQ_tblMember.id");
		}
		
		if(request.getParameter("search_EQ_ny") != null && !request.getParameter("search_EQ_ny").equals("")) {
			strLog += ",年月:" + request.getParameter("search_EQ_ny");
		}
		
		if(request.getParameter("search_LIKE_dm") != null && !request.getParameter("search_LIKE_dm").equals("")) {
			strLog += ",店名:" + request.getParameter("search_LIKE_dm");
		}
		
		if(request.getParameter("search_LIKE_pl") != null && !request.getParameter("search_LIKE_pl").equals("")) {
			strLog += ",品类:" + request.getParameter("search_LIKE_pl");
		}
		
		if(request.getParameter("search_LIKE_pm") != null && !request.getParameter("search_LIKE_pm").equals("")) {
			strLog += ",品名:" + request.getParameter("search_LIKE_pm");
		}
		
		if(request.getParameter("search_LIKE_cj") != null && !request.getParameter("search_LIKE_cj").equals("")) {
			strLog += ",厂家:" + request.getParameter("search_LIKE_cj");
		}
		
		if(request.getParameter("search_LIKE_gg") != null && !request.getParameter("search_LIKE_gg").equals("")) {
			strLog += ",规格:" + request.getParameter("search_LIKE_gg");
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{strLog}));
		
		return LIST;
	}
	
	@RequiresPermissions("queryuploaddata:show")
	@RequestMapping(value="/lookup2member", method={RequestMethod.GET})
	public String lookup(Map<String, Object> map) {
		TblMember member = memberService.getTree();
		map.put("member", member);
		return LOOKUP_MEMBER;
	}

	@Log(message="删除了{0}会员{1}月份上传数据。")
	@RequiresPermissions("queryuploaddata:delete")
	@RequestMapping(value="/delete/{memberId}/{month}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long memberId, @PathVariable Integer month) {
		TblMember member = memberService.get(memberId);
		int rst = statisticsService.deleteUploadData(memberId, month);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{member.getMemberName(), month}));
		if(rst <= 0) {
			return AjaxObject.newOk("删除数据失败，请联系管理员！").setCallbackType("").toString();
		}
		return AjaxObject.newOk("删除数据成功！").setCallbackType("").toString();
	}
}
