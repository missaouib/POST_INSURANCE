/**
 * 
 * ==========================================================
 * 档案管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdpost.utils.StringUtil;
import com.gdpost.web.entity.component.DocStatModel;
import com.gdpost.web.service.insurance.DaglService;

@Controller
@RequestMapping("/dagl")
public class DaglController {
	//private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private DaglService daglService;

	private static final String DOC_NOT_SCAN = "insurance/dagl/scan/notscanstat";
	
	@RequiresUser
	@RequestMapping(value="/scan/stat", method={RequestMethod.GET, RequestMethod.POST})
	public String docNotScan(ServletRequest request, Map<String, Object> map) {
		Calendar cal = GregorianCalendar.getInstance(Locale.CHINA);
		int month = cal.get(Calendar.MONTH) + 1;
		
		HashMap<Integer,String> monmap = new HashMap<Integer,String>();
		monmap.put(month-1, (month-1)+"月");
		monmap.put(month, month+"月");
		
		request.setAttribute("months", monmap);
		
		String theMon = request.getParameter("month");
		request.setAttribute("month", theMon);
		DocStatModel docsm = new DocStatModel();
		docsm.setMonth(new Integer(theMon==null?month+"":theMon));
		request.setAttribute("dsm", docsm);
		
		String d1 = StringUtil.getMonthFirstDayOfMonth(new Integer(theMon==null?month+"":theMon), "yyyy-MM-dd");
		String d2 = StringUtil.getMonthLastDayOfMonth(new Integer(theMon==null?month+"":theMon), "yyyy-MM-dd");
		
		List<DocStatModel> list = daglService.getDocNotScanStat(d1, d2);
		if(list == null || list.isEmpty()) {
			return DOC_NOT_SCAN;
		}
		String organName = null;
		Integer noscan = null;
		StringBuffer remark = null;
		Integer sumDocs = 0;
		Double p = null;
		List<DocStatModel> subList = null;
		List<DocStatModel> sumList = null;
		
		for(DocStatModel dsm:list) {
			remark = new StringBuffer("");
			organName = dsm.getOrgName();
			noscan = dsm.getSumDoc();
			
			sumList = daglService.getSumDocStat(organName, d1, d2);
			if(sumList == null || sumList.isEmpty()) {
				return DOC_NOT_SCAN;
			}
			sumDocs = sumList.get(0).getSumDoc();
			dsm.setAllDoc(sumDocs);
			p = (new Double(sumDocs)-new Double(noscan))/new Double(sumDocs)*100;
			dsm.setPercent(p.toString());
			
			subList = daglService.getSubDocNotScanStat(organName, d1, d2);
			for(DocStatModel subDsm:subList) {
				remark.append(subDsm.getOrgName().substring(2, 4) + ":" + subDsm.getSumDoc() + "，");
			}
			dsm.setRemark(remark.toString());
		}
		map.put("statData", list);
		return DOC_NOT_SCAN;
	}
}
