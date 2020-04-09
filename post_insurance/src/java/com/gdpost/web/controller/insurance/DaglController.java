/**
 * 
 * ==========================================================
 * 档案管理
 * ==========================================================
 * 
 */
package com.gdpost.web.controller.insurance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -5);
        String before_six = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1);//六个月前
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        try {
			min.setTime(sdf.parse(before_six));
			min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
			max.setTime(sdf.parse(sdf.format(new Date())));
			max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
		
		request.setAttribute("months", result);
		
		String theMon = request.getParameter("monthStr");
		if(theMon == null) {
			theMon = sdf.format(max.getTime());
		}
		request.setAttribute("monthStr", theMon);
		DocStatModel docsm = new DocStatModel();
		docsm.setMonthStr(theMon);
		request.setAttribute("dsm", docsm);
		
		String d1 = theMon + "-1";
		String d2 = theMon + "-31";
		
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
