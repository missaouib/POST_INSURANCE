package com.gdpost.web.controller.uploaddatamanage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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

import System.Data.DataRow;
import System.Data.DataTable;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.StringUtil;
import com.gdpost.utils.UploadDataHelper.StatisticsType;
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.entity.member.TblMemberData;
import com.gdpost.web.entity.member.TblMemberUser;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.uploaddatamanage.StatisticsService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/uploaddatamanage/statistics")
public class StatisticsController {
	private static final Logger LOG = LoggerFactory.getLogger(TemplateController.class);
	
	@Autowired
	private StatisticsService statisticsService;
	
	private static final String LIST = "uploaddatamanage/statistics/list";	// 查询数据
	private static final String ZZL = "uploaddatamanage/statistics/zzl";	// 连续三个月销售额增长率
	private static final String XSE = "uploaddatamanage/statistics/xse";		// 销售额排行
	private static final String PL = "uploaddatamanage/statistics/pl";		// 品类排行
	private static final String PP = "uploaddatamanage/statistics/pp";		// 品牌排行
	private static final String PPDESC = "uploaddatamanage/statistics/ppdesc";		// 品牌倒排行
	
	private static final String WEBZZL = "uploaddatamanage/statistics/webzzl";	// 连续三个月销售额增长率
	private static final String WEBPL = "uploaddatamanage/statistics/webpl";		// 品类排行
	
	@InitBinder
	public void initListBinder(WebDataBinder binder) {
		// 设置需要包裹的元素个数，默认为256
	    binder.setAutoGrowCollectionLimit(5000);
	}
	
	@RequiresPermissions("queryuploaddata:show")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String preList(HttpServletRequest request, Page page, Map<String, Object> map) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
		long member_id = user.getTblMember().getId();		
		// 返回年月
		DataTable dtNY = statisticsService.getNY(request, member_id);
		
		List<Integer> listNY = new ArrayList<Integer>();
		for(DataRow dr : dtNY.Rows) {
			listNY.add(Integer.parseInt(dr.getValue("ny").toString()));
		}		
		
		Specification<TblMemberData> specification = DynamicSpecifications.bySearchFilter(request, TblMemberData.class,
				new SearchFilter("tblMember.id", Operator.EQ, user.getTblMember().getId()),
				new SearchFilter("ny", Operator.EQ, listNY.size()>0 ? listNY.get(0) : -1)
				);
		
		List<TblMemberData> items = statisticsService.findAll(specification, page);

		map.put("page", page);
		map.put("items", items);
		map.put("ny", listNY);
		
		return LIST;
	}
	
	@Log(message="查询上传数据{0}。")
	@RequiresPermissions("queryuploaddata:show")
	@RequestMapping(value="/list", method={RequestMethod.POST})
	public String list(HttpServletRequest request, Page page, Map<String, Object> map) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();
		long member_id = user.getTblMember().getId();	
		Specification<TblMemberData> specification = DynamicSpecifications.bySearchFilter(request, TblMemberData.class,
				new SearchFilter("tblMember.id", Operator.EQ, user.getTblMember().getId()));
		
		List<TblMemberData> items = statisticsService.findAll(specification, page);
		
		// 返回年月
		DataTable dtNY = statisticsService.getNY(request, member_id);
		
		List<Integer> listNY = new ArrayList<Integer>();
		for(DataRow dr : dtNY.Rows) {
			listNY.add(Integer.parseInt(dr.getValue("ny").toString()));
		}
		
		map.put("page", page);
		map.put("items", items);
		map.put("ny", listNY);
		
		String strLog = "";
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

	@RequiresPermissions("statisticszzl:show")
	@RequestMapping(value = "/zzl", method = RequestMethod.GET)
	public String preZzl(HttpServletRequest request, Map<String, Object> map) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
		long member_id = user.getTblMember().getId();
		
		// 返回年月
		DataTable dtNY = statisticsService.getNY(request, member_id);
		
		List<String> listNY = new ArrayList<String>();
		for(DataRow dr : dtNY.Rows) {
			listNY.add(dr.getValue("ny").toString());
		}
		
		map.put("ny", listNY);
		
		return ZZL;
	}
	
	@Log(message="查询{0}月增长率统计。")
	@RequiresPermissions("statisticszzl:show")
	@RequestMapping(value="/zzl", method={RequestMethod.POST})
	public @ResponseBody String zzl(HttpServletRequest request, String ny) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
		long member_id = user.getTblMember().getId();
		int iNY = UploadDataUtils.getNianYue();
		try {
			iNY = Integer.parseInt(ny);
		} catch(Exception e) {
		}
		DataTable dt = statisticsService.getStatistics(request, member_id, iNY, "", "", StatisticsType.ZZL);
		String strNY = "";
		String strDataXSE = "";
		String strDataXSL = "";
		
		if(dt != null && dt.Rows.size() > 0) {
			for(DataRow dr : dt.Rows) {
				strNY += dr.getValue("NY").toString() + ",";
				strDataXSE += dr.getValue("XSE").toString() + ",";
				strDataXSL += dr.getValue("XSL").toString() + ",";
			}
			
			strNY = strNY.substring(0, strNY.length() - 1);
			strDataXSE = strDataXSE.substring(0, strDataXSE.length() - 1);
			strDataXSL = strDataXSL.substring(0, strDataXSL.length() - 1);
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"" + iNY}));
		return "{\"result\":\"success\",\"xse\":{\"ny\":[" + strNY + "],\"data\":[" + strDataXSE + "]},\"xsl\":{\"ny\":[" + strNY + "],\"data\":[" + strDataXSL + "]}}";
	}

	@RequiresPermissions("statisticszzl:show")
	@RequestMapping(value = "/webzzl", method = RequestMethod.GET)
	public String preWebZzl(HttpServletRequest request, Map<String, Object> map) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
		long member_id = user.getTblMember().getId();
		
		// 返回年月
		DataTable dtNY = statisticsService.getNY(request, member_id);
		
		List<String> listNY = new ArrayList<String>();
		for(DataRow dr : dtNY.Rows) {
			listNY.add(dr.getValue("ny").toString());
		}
		
		map.put("ny", listNY);
		
		return WEBZZL;
	}
	
	@RequiresPermissions("statisticsxse:show")
	@RequestMapping(value = "/xse", method = RequestMethod.GET)
	public String preXse(HttpServletRequest request, Map<String, Object> map) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
		long member_id = user.getTblMember().getId();
		
		// 返回年月
		DataTable dtNY = statisticsService.getNY(request, member_id);
		
		List<String> listNY = new ArrayList<String>();
		for(DataRow dr : dtNY.Rows) {
			listNY.add(dr.getValue("ny").toString());
		}
		
		map.put("ny", listNY);
		
		return XSE;
	}
	
	@Log(message="查询{0}月分店销售排行统计。")
	@RequiresPermissions("statisticsxse:show")
	@RequestMapping(value="/xse", method={RequestMethod.POST})
	public @ResponseBody String xse(HttpServletRequest request, String ny) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
		long member_id = user.getTblMember().getId();
		int iNY = UploadDataUtils.getNianYue();
		try {
			iNY = Integer.parseInt(ny);
		} catch(Exception e) {
		}
		
		DataTable dt = statisticsService.getStatistics(request, member_id, iNY, "", "", StatisticsType.XSE);
		String strDM = "";
		String strDataXSE = "";
		String strDataXSL = "";
		String strDataMLL = "";
		String strLastDM = "";
		List<String> listNY = new ArrayList<String>();
		List<String> listDM = new ArrayList<String>();
		String strTreemapXSE = "";
		String strTreemapXSL = "";
		String strTreemapMLL = "";
		String strTreemapZZL_XSE = "";
		String strTreemapZZL_XSL = "";
		String strTreemapZZL_MLL = "";
		
		if(dt != null && dt.Rows.size() > 0) {
			dt.Columns.Add("xse_zzl");
			dt.Columns.Add("xsl_zzl");
			dt.Columns.Add("mll_zzl");
			
			for(DataRow row : dt.Rows) {
				row.setValue("xse_zzl", 0);
				row.setValue("xsl_zzl", 0);
				row.setValue("mll_zzl", 0);
			}
			
			DataRow dr = null;
			DataRow drPre = null;
			double dValue = 0;
			double dPreValue = 0;
			double dProfit = 0;
			for(int iRow=0; iRow<dt.Rows.size(); iRow++) {
				dr = dt.Rows.get(iRow);
				if(!dr.getValue("dm").equals(strLastDM)) {	// 每店第一条数据，没有增长率
					strDM += "\"" + dr.getValue("dm").toString() + "\",";
					listDM.add(dr.getValue("dm").toString());
					strLastDM = dr.getValue("dm").toString();
				} else {	// 每店第二、三条数据，计算增长率
					if(iRow>0) {
						drPre = dt.Rows.get(iRow - 1);
						if(drPre.getValue("dm").equals(strLastDM)) {
							dValue = Double.parseDouble(dr.getValue("xse").toString());
							dPreValue = Double.parseDouble(drPre.getValue("xse").toString());
							if(dPreValue != 0) {
								dProfit = Math.round((dValue - dPreValue)*10000/dPreValue)/100;
								dr.setValue("xse_zzl", dProfit);
							}
							
							dValue = Double.parseDouble(dr.getValue("xsl").toString());
							dPreValue = Double.parseDouble(drPre.getValue("xsl").toString());
							if(dPreValue != 0) {
								dProfit = Math.round((dValue - dPreValue)*10000/dPreValue)/100;
								dr.setValue("xsl_zzl", dProfit);
							}
							
							dValue = Double.parseDouble(dr.getValue("mll").toString());
							dPreValue = Double.parseDouble(drPre.getValue("mll").toString());
							if(dPreValue != 0) {
								dProfit = Math.round((dValue - dPreValue)*10000/dPreValue)/100;
								dr.setValue("mll_zzl", dProfit);
							}
						}
					}
				}
				
				if(!listNY.contains(dr.getValue("ny").toString())) {
					listNY.add(dr.getValue("ny").toString());
				}
			}
			
			// sort ny aes
			for(int i=0; i<listNY.size() - 1; i++) {
				for(int j=i+1; j<listNY.size(); j++) {
					if(Integer.parseInt(listNY.get(i)) > Integer.parseInt(listNY.get(j))) {
						String strTemp = listNY.get(i);
						listNY.set(i, listNY.get(j));
						listNY.set(j, strTemp);
					}
				}
			}
			
			// zzl
			// [{name:ny1,data:[dm1 zzl, dm2 zzl, dm3 zzl]},{name:ny2,data:[dm1 zzl, dm2 zzl, dm3 zzl]}]
			boolean bFlag = false;
			if(listNY.size() > 1) {
				for(int i=1; i<listNY.size(); i++) {
					strDataXSE += "{\"name\":\"" + listNY.get(i) + "\",\"data\":[";
					strDataXSL += "{\"name\":\"" + listNY.get(i) + "\",\"data\":[";
					strDataMLL += "{\"name\":\"" + listNY.get(i) + "\",\"data\":[";
					// Treemap 显示增长率，取当前月份
					if(listNY.get(i).toString().equals("" + iNY)) {
						strTreemapZZL_XSE += "{\"ny\":\"" + listNY.get(i) + "\",\"data\":[";
						strTreemapZZL_XSL += "{\"ny\":\"" + listNY.get(i) + "\",\"data\":[";
						strTreemapZZL_MLL += "{\"ny\":\"" + listNY.get(i) + "\",\"data\":[";
					}
					
					for(String dm : listDM) {
						bFlag = false;
						for(DataRow row : dt.Rows) {
							if(row.getValue("dm").equals(dm) && row.getValue("ny").toString().equals(listNY.get(i))) {
								strDataXSE += row.getValue("xse_zzl").toString() + ",";
								strDataXSL += row.getValue("xsl_zzl").toString() + ",";
								strDataMLL += row.getValue("mll_zzl").toString() + ",";
								// Treemap 显示增长率，取当前月份
								if(listNY.get(i).toString().equals("" + iNY)) {
									strTreemapZZL_XSE += "{\"dm\":\"" + StringUtil.handleQuote(dm) + "\",\"zzl\":" + row.getValue("xse_zzl") + "},";
									strTreemapZZL_XSL += "{\"dm\":\"" + StringUtil.handleQuote(dm) + "\",\"zzl\":" + row.getValue("xsl_zzl") + "},";
									strTreemapZZL_MLL += "{\"dm\":\"" + StringUtil.handleQuote(dm) + "\",\"zzl\":" + row.getValue("mll_zzl") + "},";
								}
								bFlag = true;
								break;	// 每月、每店只有一条记录
							}
						}
						
						if(!bFlag) {
							strDataXSE += "0,";
							strDataXSL += "0,";
							strDataMLL += "0,";							
							// Treemap 显示增长率，取当前月份
							if(listNY.get(i).toString().equals("" + iNY)) {
								strTreemapZZL_XSE += "{\"dm\":\"" + StringUtil.handleQuote(dm) + "\",\"zzl\":0},";
								strTreemapZZL_XSL += "{\"dm\":\"" + StringUtil.handleQuote(dm) + "\",\"zzl\":0},";
								strTreemapZZL_MLL += "{\"dm\":\"" + StringUtil.handleQuote(dm) + "\",\"zzl\":0},";
							}}
					}
					
					strDataXSE = strDataXSE.substring(0, strDataXSE.length() - 1);
					strDataXSE += "]},";
					strDataXSL = strDataXSL.substring(0, strDataXSL.length() - 1);
					strDataXSL += "]},";
					strDataMLL = strDataMLL.substring(0, strDataMLL.length() - 1);
					strDataMLL += "]},";
					
					if(listNY.get(i).toString().equals("" + iNY)) {
						strTreemapZZL_XSE = strTreemapZZL_XSE.substring(0, strTreemapZZL_XSE.length() - 1);
						strTreemapZZL_XSE += "]},";
						strTreemapZZL_XSL = strTreemapZZL_XSL.substring(0, strTreemapZZL_XSL.length() - 1);
						strTreemapZZL_XSL += "]},";
						strTreemapZZL_MLL = strTreemapZZL_MLL.substring(0, strTreemapZZL_MLL.length() - 1);
						strTreemapZZL_MLL += "]},";
					}
				}
				
				strDataXSE = strDataXSE.substring(0, strDataXSE.length() - 1);
				strDataXSL = strDataXSL.substring(0, strDataXSL.length() - 1);
				strDataMLL = strDataMLL.substring(0, strDataMLL.length() - 1);
				strTreemapZZL_XSE = strTreemapZZL_XSE.substring(0, strTreemapZZL_XSE.length() - 1);
				strTreemapZZL_XSL = strTreemapZZL_XSL.substring(0, strTreemapZZL_XSL.length() - 1);
				strTreemapZZL_MLL = strTreemapZZL_MLL.substring(0, strTreemapZZL_MLL.length() - 1);
			}
			
			// treemap
			// {name:"总名称",children:"[{name:名称1,size:值1},{name:名称2,children:"[{name:名称3,size:值3}]"}]"}
			strTreemapXSE += "{\"name\":\"销售额\",\"children\":[";
			strTreemapXSL += "{\"name\":\"销售量\",\"children\":[";
			strTreemapMLL += "{\"name\":\"毛利率\",\"children\":[";
			if(listNY.size() > 0) {
				String strNY = listNY.get(listNY.size() - 1);
				for(DataRow row : dt.Rows) {
					if(row.getValue("ny").toString().equals(strNY)) {
						strTreemapXSE += "{\"name\":\"" + StringUtil.handleQuote(row.getValue("dm").toString()) + "\",\"size\":\"" + row.getValue("xse").toString() + "\"},";
						strTreemapXSL += "{\"name\":\"" + StringUtil.handleQuote(row.getValue("dm").toString()) + "\",\"size\":\"" + row.getValue("xsl").toString() + "\"},";
						strTreemapMLL += "{\"name\":\"" + StringUtil.handleQuote(row.getValue("dm").toString()) + "\",\"size\":\"" + row.getValue("mll").toString() + "\"},";
					}
				}
				
				strTreemapXSE = strTreemapXSE.substring(0, strTreemapXSE.length() - 1);
				strTreemapXSL = strTreemapXSL.substring(0, strTreemapXSL.length() - 1);
				strTreemapMLL = strTreemapMLL.substring(0, strTreemapMLL.length() - 1);
			}
			
			strTreemapXSE += "]}";
			strTreemapXSL += "]}";
			strTreemapMLL += "]}";
			
			if(strDM.length() > 0) {
				strDM = strDM.substring(0, strDM.length() - 1);
			}
		}

		if(strTreemapZZL_XSE.length() == 0) {
			strTreemapZZL_XSE = "null";
		}
		if(strTreemapZZL_XSL.length() == 0) {
			strTreemapZZL_XSL = "null";
		}
		if(strTreemapZZL_MLL.length() == 0) {
			strTreemapZZL_MLL = "null";
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"" + iNY}));
		return "{\"result\":\"success\",\"xse\":{\"dm\":[" + strDM + "],\"data\":[" + strDataXSE + "],\"treemap\":" + strTreemapXSE + "},\"xsl\":{\"dm\":[" + strDM + "],\"data\":[" + strDataXSL + "],\"treemap\":" + strTreemapXSL + "},\"mll\":{\"dm\":[" + strDM + "],\"data\":[" + strDataMLL + "],\"treemap\":" + strTreemapMLL + "}" + 
		",\"zzl_xse\":" + strTreemapZZL_XSE + "," + "\"zzl_xsl\":" + strTreemapZZL_XSL + "," + "\"zzl_mll\":" + strTreemapZZL_MLL + "}";
	}
	
	@RequiresPermissions("statisticspl:show")
	@RequestMapping(value = "/pl", method = RequestMethod.GET)
	public String prePL(HttpServletRequest request, Map<String, Object> map) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
		long member_id = user.getTblMember().getId();
		
		// 返回年月、店名列表
		DataTable dtNY = statisticsService.getNY(request, member_id);
		DataTable dtDM = statisticsService.getDM(request, member_id);
		
		List<String> listNY = new ArrayList<String>();
		for(DataRow dr : dtNY.Rows) {
			listNY.add(dr.getValue("ny").toString());
		}
		
		List<String> listDM = new ArrayList<String>();
		listDM.add("全部");
		for(DataRow dr : dtDM.Rows) {
			listDM.add(dr.getValue("dm").toString());
		}
		
		map.put("ny", listNY);
		map.put("dm", listDM);
		
		return PL;
	}
	
	@RequiresPermissions("statisticspl:show")
	@RequestMapping(value = "/webpl", method = RequestMethod.GET)
	public String preWebPL(HttpServletRequest request, Map<String, Object> map) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
		long member_id = user.getTblMember().getId();
		
		// 返回年月、店名列表
		DataTable dtNY = statisticsService.getNY(request, member_id);
		DataTable dtDM = statisticsService.getDM(request, member_id);
		
		List<String> listNY = new ArrayList<String>();
		for(DataRow dr : dtNY.Rows) {
			listNY.add(dr.getValue("ny").toString());
		}
		
		List<String> listDM = new ArrayList<String>();
		listDM.add("全部");
		for(DataRow dr : dtDM.Rows) {
			listDM.add(dr.getValue("dm").toString());
		}
		
		map.put("ny", listNY);
		map.put("dm", listDM);
		
		return WEBPL;
	}
	
	@Log(message="查询{0}月品类统计。")
	@RequiresPermissions("statisticspl:show")
	@RequestMapping(value="/pl", method={RequestMethod.POST})
	public @ResponseBody String pl(HttpServletRequest request, String ny, String dm) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
		long member_id = user.getTblMember().getId();
		int iNY = UploadDataUtils.getNianYue();
		try {
			iNY = Integer.parseInt(ny);
		} catch(Exception e) {
		}
		
		if(dm.equals(null) || dm.equals("") || dm.equals("全部")) {
			dm = "";
		}
		
		DataTable dt = statisticsService.getStatistics(request, member_id, iNY, dm, "", StatisticsType.PL);
		String strDataXSE = "";
		String strDataZZL = "";
		String strLastPL = "";
		String strPL = "";
		List<String> listNY = new ArrayList<String>();
		List<String> listPL = new ArrayList<String>();
		String strXSE_PH = "";	// 销售额排行，前三
		String strZZL_PH = "";	// 增长率排行，前三
		int iPHCount = 10;
		
		// 先判断，是否有品类数据，没有则直接返回错误
		boolean bFlag = false;
		for(int iRow=0; iRow<dt.Rows.size(); iRow++) {
			if(dt.Rows.get(iRow).getValue("pl") != null) {
				bFlag = true;
				break;
			}
		}
		
		if(!bFlag) {
			return "{\"result\":\"error\"}";
		}
		
		if(dt != null && dt.Rows.size() > 0) {			
			dt.Columns.Add("xse_zzl");
			for(DataRow row : dt.Rows) {
				row.setValue("xse_zzl", 0);
			}

			DataRow dr = null;
			DataRow drPre = null;
			double dValue = 0;
			double dPreValue = 0;
			double dProfit = 0;
			for(int iRow=0; iRow<dt.Rows.size(); iRow++) {
				dr = dt.Rows.get(iRow);
				if(!dr.getValue("pl").equals(strLastPL)) {	// 每品类第一条数据，没有增长率
					strPL += "\"" + dr.getValue("pl").toString() + "\",";
					listPL.add(dr.getValue("pl").toString());
					strLastPL = dr.getValue("pl").toString();
				} else {	// 每品类第二、三条数据，计算增长率
					if(iRow>0) {
						drPre = dt.Rows.get(iRow - 1);
						if(drPre.getValue("pl").equals(strLastPL)) {
							dValue = Double.parseDouble(dr.getValue("xse").toString());
							dPreValue = Double.parseDouble(drPre.getValue("xse").toString());
							if(dPreValue != 0) {
								dProfit = Math.round((dValue - dPreValue)*10000/dPreValue)/100;
								dr.setValue("xse_zzl", dProfit);
							}
						}
					}
				}
				
				if(!listNY.contains(dr.getValue("ny").toString())) {
					listNY.add(dr.getValue("ny").toString());
				}
			}
			
			// sort ny
			for(int i=0; i<listNY.size() - 1; i++) {
				for(int j=i+1; j<listNY.size(); j++) {
					if(Integer.parseInt(listNY.get(i)) > Integer.parseInt(listNY.get(j))) {
						String strTemp = listNY.get(i);
						listNY.set(i, listNY.get(j));
						listNY.set(j, strTemp);
					}
				}
			}
			
			// zzl
			// [{name:ny1,data:[pl1 zzl, pl2 zzl, pl3 zzl]},{name:ny2,data:[pl1 zzl, pl2 zzl, pl3 zzl]}]
			if(listNY.size() > 1) {
				for(int i=1; i<listNY.size(); i++) {
					strDataZZL += "{\"name\":\"" + listNY.get(i) + "\",\"data\":[";
					for(String pl : listPL) {
						for(DataRow row : dt.Rows) {
							if(row.getValue("pl").equals(pl) && row.getValue("ny").toString().equals(listNY.get(i))) {
								strDataZZL += row.getValue("xse_zzl").toString() + ",";
								break;	// 每月、每品类只有一条记录
							}
						}
					}
					
					strDataZZL = strDataZZL.substring(0, strDataZZL.length() - 1);
					strDataZZL += "]},";
				}
				
				strDataZZL = strDataZZL.substring(0, strDataZZL.length() - 1);
				
				// 增长率排行
				String strZZL_ny = listNY.get(listNY.size() - 1);
				Map<String, Double> listZZL_PH = new HashMap<String, Double>();
				
				for(DataRow row : dt.Rows) {
					if(row.getValue("ny").toString().equals(strZZL_ny)) {
						listZZL_PH.put(row.getValue("pl").toString(), Double.parseDouble(row.getValue("xse_zzl").toString()));
					}
				}
				
				// 对增长率进行排序
				List<Map.Entry<String,Double>> mappingList = new ArrayList<Map.Entry<String,Double>>(listZZL_PH.entrySet()); 
				Collections.sort(mappingList, new Comparator<Map.Entry<String,Double>>() { 
					   public int compare(Map.Entry<String,Double> mapping1,Map.Entry<String,Double> mapping2) { 
						   return mapping2.getValue().compareTo(mapping1.getValue()); 
					   } 
				});
				
				// 增长率，取前三，如果值相等的，串起来
				iPHCount = 10;
				dValue = 0;
				for(Map.Entry<String,Double> mapping : mappingList){
					if(iPHCount > 0 && mapping.getValue() > 0) {
						if(strZZL_PH.length() > 0) {
							if(dValue == mapping.getValue()) {
								strZZL_PH += " /" + StringUtil.handleQuote(mapping.getKey()) + "";
							} else {
								strZZL_PH += "\",\"" + StringUtil.handleQuote(mapping.getKey()) + "";
								iPHCount--;
							}
						} else {
							strZZL_PH += "\"" + StringUtil.handleQuote(mapping.getKey()) + "";
							dValue = mapping.getValue();	// 第一个
							iPHCount--;
						}
					} else {
						break;
					}
				}
			}
			
			// 销售额排行
			String strXSE_ny = listNY.get(listNY.size() - 1);
			Map<String, Double> listXSE_PH = new HashMap<String, Double>();
			
			for(DataRow row : dt.Rows) {
				if(row.getValue("ny").toString().equals(strXSE_ny)) {
					listXSE_PH.put(row.getValue("pl").toString(), Double.parseDouble(row.getValue("xse").toString()));
				}
			}
			
			// 对销售额进行排序
			List<Map.Entry<String,Double>> mappingList = new ArrayList<Map.Entry<String,Double>>(listXSE_PH.entrySet()); 
			Collections.sort(mappingList, new Comparator<Map.Entry<String,Double>>() { 
				   public int compare(Map.Entry<String,Double> mapping1,Map.Entry<String,Double> mapping2) { 
					   return mapping2.getValue().compareTo(mapping1.getValue()); 
				   } 
			});
			
			iPHCount = 10;
			dValue = 0;
			strXSE_PH = "";
			for(Map.Entry<String,Double> mapping : mappingList){
				if(iPHCount > 0 && mapping.getValue() > 0) {
					if(strXSE_PH.length() > 0) {
						if(dValue == mapping.getValue()) {
							strXSE_PH += " /" + StringUtil.handleQuote(mapping.getKey()) + "";
						} else {
							strXSE_PH += "\",\"" + StringUtil.handleQuote(mapping.getKey()) + "";
							iPHCount--;
						}
					} else {
						strXSE_PH += "\"" + StringUtil.handleQuote(mapping.getKey()) + "";
						dValue = mapping.getValue();	// 第一个
						iPHCount--;
					}
				} else {
					break;
				}
			}
			
			for(DataRow row : dt.Rows) {
				if(row.getValue("ny").equals(iNY)) {
					strDataXSE += "[\"" + StringUtil.handleQuote(row.getValue("pl").toString()) + "\"," + row.getValue("xse").toString() + "],";
					/*
					// 数据库查询返回已排序，直接取前三
					if(iPHCount > 0) {
						if(strXSE_PH.length() > 0) {
							if(dValue == Double.parseDouble(row.get("xse").toString())) {
								strXSE_PH += "/" + StringUtil.handleQuote(row.getValue("pl").toString()) + "";
							} else {
								strXSE_PH += "\",\"" + StringUtil.handleQuote(row.getValue("pl").toString()) + "";
								iPHCount--;
							}
						} else {
							strXSE_PH += "\"" + StringUtil.handleQuote(row.getValue("pl").toString()) + "";
							dValue = Double.parseDouble(row.get("xse").toString());	// 第一个
							iPHCount--;
						}
					} else {
						break;
					}
					*/
				}
			}
			
			strDataXSE = strDataXSE.substring(0, strDataXSE.length() - 1);
			if(strPL.length() > 0) {
				strPL = strPL.substring(0, strPL.length() - 1);
			}
			if(strXSE_PH.length() > 0) {
				strXSE_PH += "\"";
			}
			if(strZZL_PH.length() > 0) {
				strZZL_PH += "\"";
			}
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"" + iNY}));
		return "{\"result\":\"success\",\"xse\":[{\"name\":[\"" + dm + "\"],\"data\":[" + strDataXSE + "]}]" + ",\"zzl\":{\"pl\":[" + strPL + "],\"data\":[" + strDataZZL + "]}" + ",\"xse_ph\":[" + strXSE_PH + "]" + ",\"zzl_ph\":[" + strZZL_PH + "]" + "}";
	}

	private String getXSPP(HttpServletRequest request, int iNY, String dm, String pl, long member_id, StatisticsType st) {
		DataTable dt = statisticsService.getStatistics(request, member_id, iNY, dm, pl, st);
		String strData = "";
		String strCategory = "";
		
		if(dt != null && dt.Rows.size() > 0) {
			for(DataRow dr : dt.Rows) {
				if(dr.getValue("pm") == null) {
					continue;
				}
				
				strCategory += "\"" + StringUtil.handleQuote(dr.getValue("pm").toString()) + "\",";
				strData += "" + dr.getValue("data").toString() + ",";
			}
			
			if(strData.length() > 0) {
				strData = strData.substring(0, strData.length() - 1);
			}
			
			if(strCategory.length() > 0) {
				strCategory = strCategory.substring(0, strCategory.length() - 1);
			}
		}

		if(st == StatisticsType.PM_XSE) {
			return ",\"xse_categories\":[" + strCategory + "],\"xse_data\":[" + strData + "]";
		} else if(st == StatisticsType.PM_XSL) {
			return ",\"xsl_categories\":[" + strCategory + "],\"xsl_data\":[" + strData + "]";
		} else if(st == StatisticsType.PM_XSE_Pre) {
			return ",\"xse_categories_pre\":[" + UploadDataUtils.getLastNianYue(iNY) + "," + iNY + "],\"xse_data_pre\":[" + strData + "]";
		} else if(st == StatisticsType.PM_XSL_Pre) {
			return ",\"xsl_categories_pre\":[" + UploadDataUtils.getLastNianYue(iNY) + "," + iNY + "],\"xsl_data_pre\":[" + strData + "]";
		}
		
		return("");
	}

	@Log(message="查询{0}月品类统计。")
	@RequiresPermissions("statisticspl:show")
	@RequestMapping(value="/pm", method={RequestMethod.POST})
	public @ResponseBody String pm(HttpServletRequest request, String ny, String dm, String pl) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
		long member_id = user.getTblMember().getId();
		int iNY = UploadDataUtils.getNianYue();
		try {
			iNY = Integer.parseInt(ny);
		} catch(Exception e) {
		}
		
		if(dm.equals(null) || dm.equals("") || dm.equals("全部")) {
			dm = "";
		}
		
		DataTable dt = statisticsService.getStatistics(request, member_id, iNY, dm, pl, StatisticsType.PM);
		String strDataXSE = "";
		String strDataZZL = "";
		String strLastPM = "";
		String strPM = "";
		List<String> listNY = new ArrayList<String>();
		List<String> listPM = new ArrayList<String>();
		String strXSE_PH = "";	// 销售额排行
		String strZZL_PH = "";	// 增长率排行
		String strZZL_PH_Data = "";
		int iPHCount = 10;
		
		// 先判断，是否有品类数据，没有则直接返回错误
		boolean bFlag = false;
		for(int iRow=0; iRow<dt.Rows.size(); iRow++) {
			if(dt.Rows.get(iRow).getValue("pm") != null) {
				bFlag = true;
				break;
			}
		}
		
		if(!bFlag) {
			return "{\"result\":\"error\"}";
		}
		
		if(dt != null && dt.Rows.size() > 0) {			
			dt.Columns.Add("xse_zzl");
			for(DataRow row : dt.Rows) {
				row.setValue("xse_zzl", 0);
			}

			DataRow dr = null;
			DataRow drPre = null;
			double dValue = 0;
			double dPreValue = 0;
			double dProfit = 0;
			for(int iRow=0; iRow<dt.Rows.size(); iRow++) {
				dr = dt.Rows.get(iRow);
				if(!dr.getValue("pm").equals(strLastPM)) {	// 每品类第一条数据，没有增长率
					strPM += "\"" + dr.getValue("pm").toString() + "\",";
					listPM.add(dr.getValue("pm").toString());
					strLastPM = dr.getValue("pm").toString();
				} else {	// 每品类第二、三条数据，计算增长率
					if(iRow>0) {
						drPre = dt.Rows.get(iRow - 1);
						if(drPre.getValue("pm").equals(strLastPM)) {
							dValue = Double.parseDouble(dr.getValue("xse").toString());
							dPreValue = Double.parseDouble(drPre.getValue("xse").toString());
							if(dPreValue != 0) {
								dProfit = Math.round((dValue - dPreValue)*10000/dPreValue)/100;
								dr.setValue("xse_zzl", dProfit);
							}
						}
					}
				}
				
				if(!listNY.contains(dr.getValue("ny").toString())) {
					listNY.add(dr.getValue("ny").toString());
				}
			}
			
			// sort ny
			for(int i=0; i<listNY.size() - 1; i++) {
				for(int j=i+1; j<listNY.size(); j++) {
					if(Integer.parseInt(listNY.get(i)) > Integer.parseInt(listNY.get(j))) {
						String strTemp = listNY.get(i);
						listNY.set(i, listNY.get(j));
						listNY.set(j, strTemp);
					}
				}
			}
			
			// zzl
			// [{name:ny1,data:[pl1 zzl, pl2 zzl, pl3 zzl]},{name:ny2,data:[pl1 zzl, pl2 zzl, pl3 zzl]}]
			if(listNY.size() > 1) {
				for(int i=1; i<listNY.size(); i++) {
					strDataZZL += "{\"name\":\"" + listNY.get(i) + "\",\"data\":[";
					for(String pm : listPM) {
						for(DataRow row : dt.Rows) {
							if(row.getValue("pm").equals(pm) && row.getValue("ny").toString().equals(listNY.get(i))) {
								strDataZZL += row.getValue("xse_zzl").toString() + ",";
								break;	// 每月、每品类只有一条记录
							}
						}
					}
					
					strDataZZL = strDataZZL.substring(0, strDataZZL.length() - 1);
					strDataZZL += "]},";
				}
				
				strDataZZL = strDataZZL.substring(0, strDataZZL.length() - 1);
				
				// 增长率排行
				String strZZL_ny = listNY.get(listNY.size() - 1);
				Map<String, Double> listZZL_PH = new HashMap<String, Double>();
				
				for(DataRow row : dt.Rows) {
					if(row.getValue("ny").toString().equals(strZZL_ny)) {
						listZZL_PH.put(row.getValue("pm").toString(), Double.parseDouble(row.getValue("xse_zzl").toString()));
					}
				}
				
				// 对增长率进行排序
				List<Map.Entry<String,Double>> mappingList = new ArrayList<Map.Entry<String,Double>>(listZZL_PH.entrySet()); 
				Collections.sort(mappingList, new Comparator<Map.Entry<String,Double>>() { 
					   public int compare(Map.Entry<String,Double> mapping1,Map.Entry<String,Double> mapping2) { 
						   return mapping2.getValue().compareTo(mapping1.getValue()); 
					   } 
				});
				
				// 增长率
				iPHCount = 10;
				dValue = 0;
				for(Map.Entry<String,Double> mapping : mappingList){
					if(iPHCount > 0 && mapping.getValue() > 0) {
						if(strZZL_PH.length() > 0) {
							strZZL_PH += "\",\"" + StringUtil.handleQuote(mapping.getKey()) + "";
							strZZL_PH_Data += "," + mapping.getValue();
							iPHCount--;
						} else {
							strZZL_PH += "\"" + StringUtil.handleQuote(mapping.getKey()) + "";
							strZZL_PH_Data += mapping.getValue() + "";
							iPHCount--;
						}
					} else {
						break;
					}
				}
			}
			
			// 销售额排行
			String strXSE_ny = listNY.get(listNY.size() - 1);
			Map<String, Double> listXSE_PH = new HashMap<String, Double>();
			
			for(DataRow row : dt.Rows) {
				if(row.getValue("ny").toString().equals(strXSE_ny)) {
					listXSE_PH.put(row.getValue("pm").toString() + (row.getValue("gg") != null ? ("," + row.getValue("gg").toString()) : ""), Double.parseDouble(row.getValue("xse").toString()));
				}
			}
			
			// 对增长率进行排序，倒叙，取后三位
			List<Map.Entry<String,Double>> mappingList = new ArrayList<Map.Entry<String,Double>>(listXSE_PH.entrySet()); 
			Collections.sort(mappingList, new Comparator<Map.Entry<String,Double>>() { 
				   public int compare(Map.Entry<String,Double> mapping1,Map.Entry<String,Double> mapping2) { 
					   return mapping1.getValue().compareTo(mapping2.getValue()); 
				   } 
			});
			
			iPHCount = 3;
			dValue = 0;
			for(Map.Entry<String,Double> mapping : mappingList){
				if(iPHCount > 0 && mapping.getValue() > 0) {
					if(strXSE_PH.length() > 0) {
						if(dValue == mapping.getValue()) {
							strXSE_PH += "/" + StringUtil.handleQuote(mapping.getKey()) + "";
						} else {
							strXSE_PH += "\",\"" + StringUtil.handleQuote(mapping.getKey()) + "";
							iPHCount--;
						}
					} else {
						strXSE_PH += "\"" + StringUtil.handleQuote(mapping.getKey()) + "";
						dValue = mapping.getValue();	// 第一个
						iPHCount--;
					}
				} else {
					break;
				}
			}
			
			if(strDataXSE.length() > 0) {
				strDataXSE = strDataXSE.substring(0, strDataXSE.length() - 1);
			}
			if(strPM.length() > 0) {
				strPM = strPM.substring(0, strPM.length() - 1);
			}
			if(strXSE_PH.length() > 0) {
				strXSE_PH += "\"";
			}
			if(strZZL_PH.length() > 0) {
				strZZL_PH += "\"";
			}
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"" + iNY}));
		String strXSE = getXSPP(request, iNY, dm, pl, member_id, StatisticsType.PM_XSE);
		String strXSL = getXSPP(request, iNY, dm, pl, member_id, StatisticsType.PM_XSL);
		String strXSE_Pre = getXSPP(request, iNY, dm, pl, member_id, StatisticsType.PM_XSE_Pre);
		String strXSL_Pre = getXSPP(request, iNY, dm, pl, member_id, StatisticsType.PM_XSL_Pre);
		return "{\"result\":\"success\",\"xse\":[{\"name\":[\"" + dm + "\"],\"data\":[" + strDataXSE + "]}]" + ",\"zzl\":{\"pm\":[" + strPM + "],\"data\":[" + strDataZZL + "]}" + ",\"xse_ph\":[" + strXSE_PH + "]" + ",\"zzl_ph\":[" + strZZL_PH + "]" + ",\"zzl_ph_data\":[" + strZZL_PH_Data + "]" + strXSE + strXSL + strXSE_Pre + strXSL_Pre + "}";
	}
	
	@RequiresPermissions("statisticspp:show")
	@RequestMapping(value = "/pp", method = RequestMethod.GET)
	public String prePP(HttpServletRequest request, Map<String, Object> map) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
		long member_id = user.getTblMember().getId();
		
		// 返回年月、店名、品类列表
		DataTable dtNY = statisticsService.getNY(request, member_id);
		DataTable dtDM = statisticsService.getDM(request, member_id);
		DataTable dtPL = statisticsService.getPL(request, member_id);
		
		List<String> listNY = new ArrayList<String>();
		for(DataRow dr : dtNY.Rows) {
			listNY.add(dr.getValue("ny").toString());
		}
		
		List<String> listDM = new ArrayList<String>();
		listDM.add("全部");
		for(DataRow dr : dtDM.Rows) {
			listDM.add(dr.getValue("dm").toString());
		}
		
		List<String> listPL = new ArrayList<String>();
		listPL.add("全部");
		for(DataRow dr : dtPL.Rows) {
			listPL.add(dr.getValue("pl").toString());
		}
		
		map.put("ny", listNY);
		map.put("dm", listDM);
		map.put("pl", listPL);
		
		return PP;
	}

	@Log(message="查询{0}月{1}统计。")
	@RequiresPermissions("statisticspp:show")
	@RequestMapping(value="/pp", method={RequestMethod.POST})
	public @ResponseBody String pp(HttpServletRequest request, String ny, String dm, String pl, int statisticstype, int statisticsorder) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
		long member_id = user.getTblMember().getId();
		int iNY = UploadDataUtils.getNianYue();
		try {
			iNY = Integer.parseInt(ny);
		} catch(Exception e) {
		}
		
		if(dm.equals(null) || dm.equals("") || dm.equals("全部")) {
			dm = "";
		}
		
		if(pl.equals(null) || pl.equals("") || pl.equals("全部")) {
			pl = "";
		}
		
		if(statisticsorder == 0) {
			switch(statisticstype) {
				case 0:
					LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"" + iNY, "品牌销售额排行"}));
					return(getPP(request, iNY, dm, pl, member_id, StatisticsType.PP_XSE));
				case 1:
					LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"" + iNY, "品牌销售量排行"}));
					return(getPP(request, iNY, dm, pl, member_id, StatisticsType.PP_XSL));
				case 2:
					LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"" + iNY, "品牌毛利率排行"}));
					return(getPP(request, iNY, dm, pl, member_id, StatisticsType.PP_MLL));
				case 3:
					LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"" + iNY, "品牌增长率排行"}));
					return(getPP_ZZL(request, iNY, dm, pl, member_id));
			}
		} else if(statisticsorder == 1) {
			switch(statisticstype) {
				case 0:
					LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"" + iNY, "品牌销售额倒排行"}));
					return(getPP(request, iNY, dm, pl, member_id, StatisticsType.PPDESC_XSE));
				case 1:
					LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"" + iNY, "品牌销售量倒排行"}));
					return(getPP(request, iNY, dm, pl, member_id, StatisticsType.PPDESC_XSL));
				case 2:
					LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"" + iNY, "品牌毛利率倒排行"}));
					return(getPP(request, iNY, dm, pl, member_id, StatisticsType.PPDESC_MLL));
				case 3:
					LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"" + iNY, "品牌增长率倒排行"}));
					return(getPP_ZZL_Desc(request, iNY, dm, pl, member_id));
			}
		}
		
		return("");
	}

	private String getPP(HttpServletRequest request, int iNY, String dm, String pl, long member_id, StatisticsType st) {
		DataTable dt = statisticsService.getStatistics(request, member_id, iNY, dm, pl, st);
		String strData = "";
		String strNY = "";
		List<String> listNY = new ArrayList<String>();
		List<String> listPM = new ArrayList<String>();
		
		if(dt != null && dt.Rows.size() > 0) {
			for(DataRow dr : dt.Rows) {
				if(!listNY.contains(dr.getValue("ny").toString())) {
					listNY.add(dr.getValue("ny").toString());
				}
				
				if(!listPM.contains(dr.getValue("pm").toString())) {
					listPM.add(dr.getValue("pm").toString());
				}
			}
			
			// sort ny
			for(int i=0; i<listNY.size() - 1; i++) {
				for(int j=i+1; j<listNY.size(); j++) {
					if(Integer.parseInt(listNY.get(i)) > Integer.parseInt(listNY.get(j))) {
						String strTemp = listNY.get(i);
						listNY.set(i, listNY.get(j));
						listNY.set(j, strTemp);
					}
				}
			}
			
			for(String item : listNY) {
				strNY += "\"" + item + "\",";
			}
			
			// xxe
			// [{name:pl1,data:[xse1, xse2, xse3]},{name:pl2,data:[xse1, xse2, xse3]}]
			boolean bFlag = false;
			for(String pmItem : listPM) {
				strData += "{\"name\":\"" + StringUtil.handleQuote(pmItem) + "\",\"data\":[";
				for(String nyItem : listNY) {
					bFlag = false;
					for(DataRow dr : dt.Rows) {
						if(dr.getValue("pm").equals(pmItem) && dr.getValue("ny").toString().equals(nyItem)) {
							strData += dr.getValue("data") + ",";
							bFlag = true;
							break;
						}
					}
					
					if(!bFlag) {
						// 该月无数据，默认为0
						strData += "0,";
					}
				}
				
				strData = strData.substring(0, strData.length() - 1);
				strData += "]},";
			}
			
			if(strData.length() > 0) {
				strData = strData.substring(0, strData.length() - 1);
			}
			
			if(strNY.length() > 0) {
				strNY = strNY.substring(0, strNY.length() - 1);
			}
		}

		return "{\"result\":\"success\",\"data\":{\"categories\":[" + strNY + "],\"data\":[" + strData + "]}"+ "}";
	}

	private String getPP_ZZL(HttpServletRequest request, int iNY, String dm, String pl, long member_id) {
		DataTable dt = statisticsService.getStatistics(request, member_id, iNY, dm, pl, StatisticsType.PP_ZZL);
		String strDataZZL = "";
		String strLastPM = "";
		String strPM = "";
		List<String> listNY = new ArrayList<String>();
		List<String> listPM = new ArrayList<String>();
		
		if(dt != null && dt.Rows.size() > 0) {
			dt.Columns.Add("xse_zzl");
			for(DataRow row : dt.Rows) {
				row.setValue("xse_zzl", 0);
			}

			DataRow dr = null;
			DataRow drPre = null;
			double dValue = 0;
			double dPreValue = 0;
			double dProfit = 0;
			for(int iRow=0; iRow<dt.Rows.size(); iRow++) {
				dr = dt.Rows.get(iRow);
				if(dr.getValue("pm")!=null && !dr.getValue("pm").equals(strLastPM)) {	// 每品类第一条数据，没有增长率
					listPM.add(dr.getValue("pm").toString());
					strLastPM = dr.getValue("pm").toString();
				} else {	// 每品类第二条数据，计算增长率
					if(iRow>0) {
						drPre = dt.Rows.get(iRow - 1);
						if(drPre.getValue("pm")!=null && drPre.getValue("pm").equals(strLastPM)) {
							dValue = Double.parseDouble(dr.getValue("xse").toString());
							dPreValue = Double.parseDouble(drPre.getValue("xse").toString());
							if(dPreValue != 0) {
								dProfit = Math.round((dValue - dPreValue)*10000/dPreValue)/100;
								dr.setValue("xse_zzl", dProfit);
							}
						}
					}
				}
				
				if(!listNY.contains(dr.getValue("ny").toString())) {
					listNY.add(dr.getValue("ny").toString());
				}
			}
			
			// sort ny
			for(int i=0; i<listNY.size() - 1; i++) {
				for(int j=i+1; j<listNY.size(); j++) {
					if(Integer.parseInt(listNY.get(i)) > Integer.parseInt(listNY.get(j))) {
						String strTemp = listNY.get(i);
						listNY.set(i, listNY.get(j));
						listNY.set(j, strTemp);
					}
				}
			}
			
			// zzl
			// [{name:ny1,data:[pl1 zzl, pl2 zzl, pl3 zzl]},{name:ny2,data:[pl1 zzl, pl2 zzl, pl3 zzl]}]
			Map<String, Double> listSortPM = new HashMap<String, Double>();
			if(listNY.size() > 1) {
				for(int i=1; i<listNY.size(); i++) {
					for(String item : listPM) {
						for(DataRow row : dt.Rows) {
							if(row.getValue("pm")!=null && row.getValue("pm").equals(item) && row.getValue("ny").toString().equals(listNY.get(i))) {
								listSortPM.put(row.getValue("pm").toString(), Double.parseDouble(row.getValue("xse_zzl").toString()));
								break;	// 每月、每品类只有一条记录
							}
						}
					}
				}
			}
			
			// sort 
			List<Map.Entry<String,Double>> mappingList = new ArrayList<Map.Entry<String,Double>>(listSortPM.entrySet()); 
			Collections.sort(mappingList, new Comparator<Map.Entry<String,Double>>() { 
				   public int compare(Map.Entry<String,Double> mapping1,Map.Entry<String,Double> mapping2) { 
					   return mapping1.getValue().compareTo(mapping2.getValue()); 
				   } 
			});
			
			int iCount = 0;
			int iN = 9;	// 前N
			strPM = "";
			strDataZZL = "";
			strDataZZL += "{\"name\":\"" + iNY + "\",\"data\":[";
			for(Map.Entry<String,Double> mapping : mappingList){
				iCount++;
				strPM += "\"" + StringUtil.handleQuote(mapping.getKey()) + "\",";
				strDataZZL += mapping.getValue() + ",";
				
				if(iCount > iN) {
					break;
				}
			}
			
			if(iCount > 0) {
				strDataZZL = strDataZZL.substring(0, strDataZZL.length() - 1);
			}
			
			strDataZZL += "]}";
			
			if(strPM.length() > 0) {
				strPM = strPM.substring(0, strPM.length() - 1);
			}
		}

		return "{\"result\":\"success\",\"zzl\":{\"pm\":[" + strPM + "],\"data\":[" + strDataZZL + "]}" + "}";
	}

	@RequiresPermissions("statisticsppdesc:show")
	@RequestMapping(value = "/ppdesc", method = RequestMethod.GET)
	public String prePPDesc(HttpServletRequest request, Map<String, Object> map) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
		long member_id = user.getTblMember().getId();
		
		// 返回年月、店名、品类列表
		DataTable dtNY = statisticsService.getNY(request, member_id);
		DataTable dtDM = statisticsService.getDM(request, member_id);
		DataTable dtPL = statisticsService.getPL(request, member_id);
		
		List<String> listNY = new ArrayList<String>();
		for(DataRow dr : dtNY.Rows) {
			listNY.add(dr.getValue("ny").toString());
		}
		
		List<String> listDM = new ArrayList<String>();
		listDM.add("全部");
		for(DataRow dr : dtDM.Rows) {
			listDM.add(dr.getValue("dm").toString());
		}
		
		List<String> listPL = new ArrayList<String>();
		listPL.add("全部");
		for(DataRow dr : dtPL.Rows) {
			listPL.add(dr.getValue("pl").toString());
		}
		
		map.put("ny", listNY);
		map.put("dm", listDM);
		map.put("pl", listPL);
		
		return PPDESC;
	}

	@RequiresPermissions("statisticsppdesc:show")
	@RequestMapping(value="/ppdesc", method={RequestMethod.POST})
	public @ResponseBody String ppdesc(HttpServletRequest request, String ny, String dm, String pl, int statisticstype) {
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
		long member_id = user.getTblMember().getId();
		int iNY = UploadDataUtils.getNianYue();
		try {
			iNY = Integer.parseInt(ny);
		} catch(Exception e) {
		}
		
		if(dm.equals(null) || dm.equals("") || dm.equals("全部")) {
			dm = "";
		}
		
		if(pl.equals(null) || pl.equals("") || pl.equals("全部")) {
			pl = "";
		}
		
		switch(statisticstype) {
			case 0:
				return(getPP(request, iNY, dm, pl, member_id, StatisticsType.PPDESC_XSE));
			case 1:
				return(getPP(request, iNY, dm, pl, member_id, StatisticsType.PPDESC_XSL));
			case 2:
				return(getPP(request, iNY, dm, pl, member_id, StatisticsType.PPDESC_MLL));
			case 3:
				return(getPP_ZZL_Desc(request, iNY, dm, pl, member_id));
		}

		return("");
	}

	private String getPP_ZZL_Desc(HttpServletRequest request, int iNY, String dm, String pl, long member_id) {
		DataTable dt = statisticsService.getStatistics(request, member_id, iNY, dm, pl, StatisticsType.PPDESC_ZZL);
		String strDataZZL = "";
		String strLastPM = "";
		String strPM = "";
		List<String> listNY = new ArrayList<String>();
		List<String> listPM = new ArrayList<String>();
		
		if(dt != null && dt.Rows.size() > 0) {
			dt.Columns.Add("xse_zzl");
			for(DataRow row : dt.Rows) {
				row.setValue("xse_zzl", 0);
			}

			DataRow dr = null;
			DataRow drPre = null;
			double dValue = 0;
			double dPreValue = 0;
			double dProfit = 0;
			for(int iRow=0; iRow<dt.Rows.size(); iRow++) {
				dr = dt.Rows.get(iRow);
				if(dr.getValue("pm") != null && !dr.getValue("pm").equals(strLastPM)) {	// 每品类第一条数据，没有增长率
					listPM.add(dr.getValue("pm").toString());
					strLastPM = dr.getValue("pm").toString();
				} else {	// 每品类第二条数据，计算增长率
					if(iRow>0) {
						drPre = dt.Rows.get(iRow - 1);
						if(drPre.getValue("pm") != null && drPre.getValue("pm").equals(strLastPM)) {
							dValue = Double.parseDouble(dr.getValue("xse").toString());
							dPreValue = Double.parseDouble(drPre.getValue("xse").toString());
							if(dPreValue != 0) {
								dProfit = Math.round((dValue - dPreValue)*10000/dPreValue)/100;
								dr.setValue("xse_zzl", dProfit);
							}
						}
					}
				}
				
				if(!listNY.contains(dr.getValue("ny").toString())) {
					listNY.add(dr.getValue("ny").toString());
				}
			}
			
			// sort ny
			for(int i=0; i<listNY.size() - 1; i++) {
				for(int j=i+1; j<listNY.size(); j++) {
					if(Integer.parseInt(listNY.get(i)) > Integer.parseInt(listNY.get(j))) {
						String strTemp = listNY.get(i);
						listNY.set(i, listNY.get(j));
						listNY.set(j, strTemp);
					}
				}
			}
			
			// zzl
			// [{name:ny1,data:[pl1 zzl, pl2 zzl, pl3 zzl]},{name:ny2,data:[pl1 zzl, pl2 zzl, pl3 zzl]}]
			Map<String, Double> listSortPM = new HashMap<String, Double>();
			if(listNY.size() > 1) {
				for(int i=1; i<listNY.size(); i++) {
					for(String item : listPM) {
						for(DataRow row : dt.Rows) {
							if(row.getValue("pm") != null && row.getValue("pm").equals(item) && row.getValue("ny").toString().equals(listNY.get(i))) {
								listSortPM.put(row.getValue("pm").toString(), Double.parseDouble(row.getValue("xse_zzl").toString()));
								break;	// 每月、每品类只有一条记录
							}
						}
					}
				}
			}
			
			// sort 
			List<Map.Entry<String,Double>> mappingList = new ArrayList<Map.Entry<String,Double>>(listSortPM.entrySet()); 
			Collections.sort(mappingList, new Comparator<Map.Entry<String,Double>>() { 
				   public int compare(Map.Entry<String,Double> mapping1,Map.Entry<String,Double> mapping2) { 
					   return mapping2.getValue().compareTo(mapping1.getValue()); 
				   } 
			});
			
			int iCount = 0;
			int iN = 9;	// 后N
			strPM = "";
			strDataZZL = "";
			strDataZZL += "{\"name\":\"" + iNY + "\",\"data\":[";
			for(Map.Entry<String,Double> mapping:mappingList){
				iCount++;
				strPM += "\"" + StringUtil.handleQuote(mapping.getKey()) + "\",";
				strDataZZL += mapping.getValue() + ",";
				
				if(iCount > iN) {
					break;
				}
			}
			
			if(iCount > 0) {
				strDataZZL = strDataZZL.substring(0, strDataZZL.length() - 1);
			}
			
			strDataZZL += "]}";
			
			if(strPM.length() > 0) {
				strPM = strPM.substring(0, strPM.length() - 1);
			}
		}

		return "{\"result\":\"success\",\"zzl\":{\"pm\":[" + strPM + "],\"data\":[" + strDataZZL + "]}" + "}";
	}

}

