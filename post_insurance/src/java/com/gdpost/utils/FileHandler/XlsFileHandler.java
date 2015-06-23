package com.gdpost.utils.FileHandler;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import System.Data.DataColumn;
import System.Data.DataRow;
import System.Data.DataTable;

import com.gdpost.utils.StringUtil;

public class XlsFileHandler extends AbstractFileHandler {
	public static Logger log = LoggerFactory.getLogger(XlsFileHandler.class);

	// 读取Excel 2003文件
	public DataTable[] readFile(String strFilePath, String strFileName) {
		List<DataTable> list = new ArrayList<DataTable>();

        try
        {
        	// 设置访问密码
        	//Biff8EncryptionKey.setCurrentUserPassword(this.m_strPassword);
        	
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(strFilePath + "\\" + strFileName));
            HSSFSheet sheet = null;
            HSSFRow headerRow = null;
            int iSheets = workbook.getNumberOfSheets();// 一共有几个sheet

            DataTable dt = null;
            DataColumn column = null;
            DataRow dataRow = null;
            int cellCount = 0;
            int rowCount = 0;
            HSSFRow row = null;
            HSSFCell cell = null;
            boolean bFlag = false;
            int markRow = 0;
            
            for(int iSheet = 0; iSheet < iSheets; iSheet++) {
	            sheet = (HSSFSheet)workbook.getSheetAt(iSheet);
	            for(int i=0; i < sheet.getLastRowNum(); i++) {
		            headerRow = (HSSFRow)sheet.getRow(i);
		            if(headerRow == null) {
		            	continue;
		            }
		            if(headerRow.getLastCellNum() > this.m_column.size()/2) {
		            	markRow = i;
		            	break;
		            }
	            }
	            if(markRow<=0) {
	            	return null;
	            }
	            headerRow = sheet.getRow(markRow);
	            cellCount = headerRow.getLastCellNum();
	            log.debug("-----------xls file handle: " + cellCount);
	            
	            dt = new DataTable();
	            dt.TableName = sheet.getSheetName();
	
	            for (int i = headerRow.getFirstCellNum(); i < cellCount; i++) {
	            	if(cellCount < this.m_column.size()) {
		            	continue;
		            }                                                                                                                                                                                                                                                                                                                                                      
	                column = new DataColumn(headerRow.getCell(i).getStringCellValue());
	                dt.Columns.Add(column);
	            }
	
	            rowCount = sheet.getLastRowNum();
	
	            for (int i = markRow+1; i <= rowCount; i++) {
//	            	if(cellCount < this.m_column.size()) {
//		            	continue;
//		            }
	            	row = (HSSFRow)sheet.getRow(i);
	            	if(row == null) {	// 空行，跳过
	            		continue;
	            	}
	            	
	                dataRow = dt.NewRow();
	                bFlag = false;
	                for (int j = row.getFirstCellNum(); j < cellCount; j++) {
	                	cell = row.getCell(j);
	                    if (cell != null && ("") != cell.toString()) {
	                    	bFlag = true;
	    	            	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	                        dataRow.setValue(j, StringUtil.trimStr(cell.getStringCellValue()));
	                    }
	                }
	
	                if(!bFlag) {	// 每个cell　都为空，跳过
	                	continue;
	                }
	                
	                dt.Rows.add(dataRow);
	            }
	            
	            sheet = null;
	            list.add(dt);
            }
            
            workbook.close();
            workbook = null;
        }
        catch (Exception e)
        {
        	log.error(e.getMessage());
        }
        
        DataTable[] ds = new DataTable[list.size()];
		return(list.toArray(ds));
	}
}

