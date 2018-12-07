package com.gdpost.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.utils.StringUtil;
import com.gdpost.web.util.CsvUtils;

@Controller
@RequestMapping("/csv/export")
public class CsvExportController {
	
	//private static final Log LOG = LogFactory.getLog(CsvExportController.class);

	@RequestMapping(value = "/xqList")
	public @ResponseBody void exportExcel(HttpServletRequest request, HttpServletResponse response, Class<T> vo)
			throws IOException {
		File csvFile = createCSVFile(request, vo);

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		response.setHeader("Content-disposition",
				"attachment; filename=" + URLEncoder.encode(csvFile.getName(), "UTF-8"));

		response.setHeader("Content-Length", String.valueOf(csvFile.length()));

		bis = new BufferedInputStream(new FileInputStream(csvFile));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		while (true) {
			int bytesRead;
			if (-1 == (bytesRead = bis.read(buff, 0, buff.length)))
				break;
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();
	}

	public File createCSVFile(HttpServletRequest request, Class<T> vo) {

		// 设置表格头
		Object[] head = { "客户姓名", "证件类型", "证件号码", "银行账号", "理财账号", "客户类型", "风险等级", "归属状况", "归属机构", "客户经理", "营销比例(%)" };
		List<Object> headList = Arrays.asList(head);

		List<T> list = new ArrayList<T>();

		// 设置数据
		List<List<Object>> dataList = new ArrayList<List<Object>>();
		List<Object> rowList = null;
		for (int i = 0; i < list.size(); i++) {
			rowList = new ArrayList<Object>();
			//T kc_vo = list.get(i);
			// rowList.add(i + 1);
			rowList.add("1");
			rowList.add("2");
			// rowList.add(cjsj);
			dataList.add(rowList);
		}

		// 导出文件路径
		String downloadFilePath = "C:" + File.separator + "cap4j" + File.separator + "download" + File.separator;

		// String downloadFilePath =
		// request.getSession().getServletContext().getRealPath("/exportload");

		// 导出文件名称
		String datetimeStr = StringUtil.date2Str(new Date(), "yyyyMMddHHmmss");
		String fileName = "客户列表_" + datetimeStr;

		// String fileName = "";
		// try {
		// fileName = URLDecoder.decode("khxxCx_list","utf-8");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// 导出CSV文件
		File csvFile = CsvUtils.createCSVFile(headList, dataList, downloadFilePath, fileName);

		return csvFile;
	}
}
