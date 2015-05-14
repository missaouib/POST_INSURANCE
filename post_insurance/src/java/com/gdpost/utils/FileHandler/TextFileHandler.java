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

public class TextFileHandler extends AbstractFileHandler {
	public static Logger log = LoggerFactory.getLogger(TextFileHandler.class);
	
	public DataTable[] readFile(String strFilePath, String strFileName) {
		DataTable[] ds = null;
		
		InputStreamReader freader = null;
		try {
			File file = new File(strFilePath + "\\" + strFileName);
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
		try {
			String[] headers = reader.getHeader(true);
			dt = new DataTable();
			dt.TableName = strFileName;
			for(int i = 0; i < headers.length; i++) {
				column = new DataColumn(headers[i]);
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
		
		//获取数据部分
		try {
			while ((line = reader.read()) != null) {
			    dataRow = dt.NewRow();
			    
			    for(int i = 0; i < line.size(); i++) {
			    	dataRow.setValue(i, line.get(i));
			    }
			    
			    dt.Rows.add(dataRow);
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