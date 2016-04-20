package com.gdpost.utils.FileHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import System.Data.DataColumn;
import System.Data.DataRow;
import System.Data.DataTable;

import com.gdpost.utils.UploadDataHelper.UploadDataUtils;

public class TxtFileHandler extends AbstractFileHandler {
	public static Logger log = LoggerFactory.getLogger(TxtFileHandler.class);
	
	public DataTable[] readFile(String strFilePath, String strFileName, String keyRow) {
		DataTable[] ds = null;
		
		InputStreamReader freader = null;
		try {
			File file = new File(strFilePath + File.separator + strFileName);
			String strCharset = UploadDataUtils.getCharset(file);
			freader = new InputStreamReader(new FileInputStream(file), strCharset);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
			return(ds);
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
			return(ds);
		}

		CsvListReader reader = new CsvListReader(freader, CsvPreference.TAB_PREFERENCE);
		DataTable dt = null;
		DataColumn column = null;
		DataRow dataRow = null;
		
		//获取头部信息
		String[] headers = new String[]{"序号","印刷号","状态","机构号","保单号","投保单号","快递单号","打印时间", "标记"};//reader.getHeader(true);
		dt = new DataTable();
		dt.TableName = strFileName;
		for(int i = 0; i < headers.length; i++) {
			column = new DataColumn(headers[i]);
			dt.Columns.Add(column);
		}

		List<String> line = new ArrayList<String>();
		boolean hasData = false;
		//String tv = null;
		int j = 0;
		//获取数据部分
		try {
			while ((line = reader.read()) != null) {
			    dataRow = dt.NewRow();
			    j = 0;
			    for(int i = 0; i < line.size(); i++) {
			    	if(line.get(0).contains("8644")) {
			    		log.debug("-------------------------------" + line.get(0));
			    		hasData = true;
			    		for(Object tv:line.get(0).split(",")) {
			    			dataRow.setValue(j++, tv);
			    		}
			    	} else {
			    		break;
			    	}
			    }
			    
			    if(hasData) {
			    	dt.Rows.add(dataRow);
			    }
			}
			
			ds = new DataTable[1];
			ds[0] = dt;
		} catch (IOException e) {
			log.error(e.getMessage());
			return(ds);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}

		return(ds);
	}
}
