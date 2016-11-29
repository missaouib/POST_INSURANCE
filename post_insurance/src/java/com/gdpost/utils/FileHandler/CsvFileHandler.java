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

import com.gdpost.utils.MyException;
import com.gdpost.utils.StringUtil;
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;

public class CsvFileHandler extends AbstractFileHandler {
	public static Logger log = LoggerFactory.getLogger(CsvFileHandler.class);
	
	public DataTable[] readFile(String strFilePath, String strFileName, String keyRow) throws MyException{
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
		
		CsvListReader reader = new CsvListReader(freader, CsvPreference.EXCEL_PREFERENCE);
		DataTable dt = null;
		DataColumn column = null;
		DataRow dataRow = null;
		boolean hasKeyRow = false;
		int keyRowIdx = -1;
		String[] headers = null;
		//获取头部信息
		try {
			headers = reader.getHeader(true);
			String h = null;
			for(int i=0; i<headers.length; i++) {
				h = headers[i].trim();
				if (h!=null && h.equals(keyRow)) {
					hasKeyRow = true;
					keyRowIdx = i;
				}
			}
			if(!hasKeyRow) {
				reader.close();
				return null;
			}
			dt = new DataTable();
			dt.TableName = strFileName;
			for(int i = 0; i < headers.length; i++) {
				if(headers[i] == null) break;
				column = new DataColumn(headers[i].trim());
				dt.Columns.Add(column);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			try {
				reader.close();
			} catch (IOException e1) {
			}
			return(ds);
		}

		List<String> line = new ArrayList<String>();
		int markIdx = 0;
		//获取数据部分
		try {
			while ((line = reader.read()) != null) {
				markIdx ++;
				//log.debug("----------csv file read line" + line);
			    if(line.size() < dt.Columns.size()/2) {
			    	continue;
			    }
			    if(line.get(keyRowIdx)==null || line.get(keyRowIdx).trim().length()<=0) {
			    	continue;
			    }
			    dataRow = dt.NewRow();
			    for(int i = 0; i < dt.Columns.size(); i++) {
			    	dataRow.setValue(i, StringUtil.trimStr(line.get(i)));
			    	//log.debug(i + "----------csv read data: " + dataRow.getValue(i));
			    }
			    
			    dt.Rows.add(dataRow);
			}
			
			ds = new DataTable[1];
			ds[0] = dt;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new MyException("表头下的数据第" + markIdx + "行数据有问题：" + e.getMessage());
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}

		return(ds);
	}
	
}
