package com.sendtend.utils.FileHandler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sendtend.utils.FileHandler.XlsxFileStreamHandler.SheetDatasHandler;
import com.sendtend.utils.FileHandler.XlsxFileStreamHandler.XlsxStreamHandler;
import com.sendtend.utils.TemplateHelper.ColumnItem;

import System.Data.DataColumn;
import System.Data.DataRow;
import System.Data.DataTable;

public class XlsxFileHandler_NoHeader_Stream extends AbstractFileHandler {
	public static Logger log = LoggerFactory.getLogger(XlsxFileHandler_NoHeader_Stream.class);
	
	// 读取Excel 2007文件
	public DataTable[] readFile(String strFilePath, String strFileName) {
		List<DataTable> list = new ArrayList<DataTable>();	
		
        try
        {
        	// 设置访问密码
        	Biff8EncryptionKey.setCurrentUserPassword(this.m_strPassword);
        	
        	Path file = Paths.get(strFilePath, strFileName);  
        	SheetDatasHandler handler = XlsxStreamHandler.read(file);

        	List<List<Map<Integer, Object>>> sheets = handler.getSheetDatas();  
        	
        	List<Map<Integer, Object>> sheet = null;
        	Map<Integer, Object> headerRow = null;
            int iSheets = sheets.size();// 一共有几个sheet

            DataTable dt = null;
            DataColumn column = null;
            int cellCount = 0;
            int rowCount = 0;
            Map<Integer, Object> row = null;
            DataRow dataRow = null;
            Object cell = null;
            boolean bFlag = false;
            int iHeaderRow = 0;
            
            for(int iSheet = 0; iSheet < iSheets; iSheet++) {
	            sheet = sheets.get(iSheet);
	            // 获取列头行标号
	            iHeaderRow = getHeaderRow(sheet);
	            if(iHeaderRow == -1) {
	            	// 没有找到列头，不是数据sheet，跳过
	            	continue;
	            }
	            
	            headerRow = sheet.get(iHeaderRow);
	            cellCount = headerRow.size();
	            
	            dt = new DataTable();
	            dt.TableName = handler.getSheetName().get(iSheet);
	
	            for (int i : headerRow.keySet()) {
	                column = new DataColumn(headerRow.get(i).toString());
	                dt.Columns.Add(column);
	            }
	
	            rowCount = sheet.size();
	
	            for (int i = (iHeaderRow + 1); i < rowCount; i++) {
	            	row = sheet.get(i);
	            	if(row == null || row.size() == 0) {	// 空行，跳过
	            		continue;
	            	}
	            	
	            	// 最后一行，如果有计算公式则认为是汇总行，跳过
	            	if(i == (rowCount - 1) && checkFormulaRow(row)) {
	            		continue;
	            	}
	            	
	                dataRow = dt.NewRow();
	                bFlag = false;
	                for (int j : row.keySet()) {
	                	cell = row.get(j);
	                    if (cell != null && ("") != cell.toString()) {
	                    	bFlag = true;
	                        dataRow.setValue(j, cell.toString());
	                    }
	                }
	                
	                if(!bFlag) {	// 每个cell都为空，跳过
	                	continue;
	                }
	
	                dt.Rows.add(dataRow);
	            }
	            
	            sheet = null;
	            list.add(dt);
            }
        }
        catch (Exception e)
        {
        	log.error(e.getMessage());
        }
        
        DataTable[] ds = new DataTable[list.size()];
		return(list.toArray(ds));
	}

	private int getHeaderRow(List<Map<Integer, Object>> sheet) {
		int iHeaderRow = -1;
		Map<Integer, Object> row = null;
		
		// 按行从上到下，按列从左到右，查找列头行
		for (int i = 0; i < sheet.size(); i++) {
			row = sheet.get(i);
            if (row == null) {
                continue;
            }
            
            if(checkRow(row)) {
            	iHeaderRow = i;
            	break;
            }
		}
		
		return(iHeaderRow);
	}
	
	private boolean checkRow(Map<Integer, Object> row) {
		boolean bFlag = false;
		String columnName = "";
		
		for(ColumnItem item : this.m_column) {
    		if(!item.isHasValue()) {
    			continue;
    		}
    		
			columnName = item.getMapColumnName();
			if(item.isMapColumn() || item.isFromColumn()) {
				bFlag = false;
				for(int i : row.keySet()) {
					// 在行中查找每个标准列，如果有一个找不到，则认为非表头
					if(row.get(i) != null && row.get(i).toString().trim().equalsIgnoreCase(columnName.trim())) {
						bFlag = true;
						break;
					}
				}
				
				if(!bFlag) {
					break;
				}
			}
		}
		
		return(bFlag);
	}
	
	private boolean checkFormulaRow(Map<Integer, Object> row) {
		int iDataRowCount = 0;
		// 如果有数据的非公式列多于3列，则认为正常数据

		for(int i : row.keySet()) {
			// 行中有一个公式，则认为为汇总行
			if(row.get(i) != null) {
				if(!("").equals(row.get(i).toString().trim())) {
					iDataRowCount++;
				}
			}
		}

		return(iDataRowCount<4);
	}
}
