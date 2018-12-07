package com.gdpost.utils.FileHandler;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdpost.utils.StringUtil;
import com.gdpost.utils.TemplateHelper.ColumnItem;

import System.Data.DataColumn;
import System.Data.DataRow;
import System.Data.DataTable;

public class XlsFileHandler_Converter extends AbstractFileHandler {
	public static Logger log = LoggerFactory.getLogger(XlsFileHandler_Converter.class);

	// 读取Excel 2003文件
	public DataTable[] readFile(String strFilePath, String strFileName, String keyRow) {
		List<DataTable> list = new ArrayList<DataTable>();

        try
        {
        	// 设置访问密码
        	//Biff8EncryptionKey.setCurrentUserPassword(this.m_strPassword);
        	
        	String backKey = null;
    		if(keyRow != null && keyRow.equals("underwrite")) {
    			keyRow = "保单号";
    			backKey = "underwrite";
    		}
        	
        	HSSFWorkbook workbookold = new HSSFWorkbook(new FileInputStream(strFilePath + File.separator + strFileName));

            // convert
            XSSFWorkbook workbook = new XSSFWorkbook();
            Xls2Xlsx converter = new Xls2Xlsx();
            converter.transformHSSF(workbookold, workbook);
            
            Sheet sheet = null;
            Row headerRow = null;
            int iSheets = workbook.getNumberOfSheets();// 一共有几个sheet

            DataTable dt = null;
            DataColumn column = null;
            DataRow dataRow = null;
            int cellCount = 0;
            int rowCount = 0;
            Row row = null;
            Cell cell = null;
            boolean bFlag = false;
            int iHeaderRow = 0;
            boolean hasKeyRow = false;
    		int keyRowIdx = -1;
            
            for(int iSheet = 0; iSheet < iSheets; iSheet++) {
	            sheet = workbook.getSheetAt(iSheet);

	            // 获取列头行标号
	            iHeaderRow = getHeaderRow(sheet);
	            if(iHeaderRow == -1) {
	            	// 没有找到列头，不是数据sheet，跳过
	            	continue;
	            }
	            
	            //if(sheet.getc)
	            
	            headerRow = sheet.getRow(iHeaderRow);
	            cellCount = headerRow.getLastCellNum();
	            
	            for(int i=0; i<headerRow.getRowNum(); i++) {
					if (StringUtil.trimStr(headerRow.getCell(i))!=null && StringUtil.trimStr(headerRow.getCell(i)).equals(keyRow)) {
						hasKeyRow = true;
						keyRowIdx = i;
					}
				}
				if(!hasKeyRow) {
					workbookold.close();
					workbook.close();
					return null;
				}
				
	            dt = new DataTable();
	            dt.TableName = sheet.getSheetName();
	            
	            for (int i = headerRow.getFirstCellNum(); i < cellCount; i++) {
	            	
	                column = new DataColumn(StringUtil.trimStr(headerRow.getCell(i).getStringCellValue()));
	                dt.Columns.Add(column);
	            }
	
	            rowCount = sheet.getLastRowNum();
	            if(sheet.getRowSumsBelow()) {
	            	rowCount -= 1;
	            }
	            
	            // 从headerRow的下一行开始读数据
	            for (int i = (iHeaderRow + 1); i <= rowCount; i++) {
	            	row = sheet.getRow(i);
	            	if(row == null) {	// 空行，跳过
	            		continue;
	            	}
	            	
	            	if(backKey != null) {
						if(!row.getCell(keyRowIdx).getStringCellValue().contains("8644") && !row.getCell(keyRowIdx).getStringCellValue().contains("7644") 
								&& !row.getCell(keyRowIdx).getStringCellValue().contains("9644") && !row.getCell(keyRowIdx).getStringCellValue().contains("8144")) {
							continue;
						}
					}
	            	
	                dataRow = dt.NewRow();
	                bFlag = false;
	                for (int j = row.getFirstCellNum(); j < cellCount; j++) {
	                	cell = row.getCell(j);
	                    if (cell != null && ("") != cell.toString()) {
	                    	bFlag = true;
	    	            	cell.setCellType(CellType.STRING);
	                        dataRow.setValue(j, cell.getStringCellValue());
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
	
	private int getHeaderRow(Sheet sheet) {
		int iHeaderRow = -1;
		Row row = null;
		
		// 按行从上到下，按列从左到右，查找列头行
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
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
	
	private boolean checkRow(Row row) {
		boolean bFlag = false;
		String columnName = "";
		
		for(ColumnItem item : this.m_column) {
    		if(!item.isHasValue()) {
    			continue;
    		}
    		
			columnName = item.getMapColumnName();
			if(item.isMapColumn()) {
				bFlag = false;
				for(int i=row.getFirstCellNum(); i<=row.getLastCellNum(); i++) {
					// 在行中查找每个标准列，如果有一个找不到，则认为非表头
					if(row.getCell(i) != null && row.getCell(i).toString().trim().equalsIgnoreCase(columnName)) {
						bFlag = true;
					}
				}
				
				if(!bFlag) {
					break;
				}
			}
		}
		
		return(bFlag);
	}
}