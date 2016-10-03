package com.gdpost.utils.FileHandler;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import System.Data.DataColumn;
import System.Data.DataRow;
import System.Data.DataTable;

import com.gdpost.utils.TemplateHelper.ColumnItem;

public class XlsxFileHandler_NoHeader extends AbstractFileHandler {
	public static Logger log = LoggerFactory.getLogger(XlsxFileHandler_NoHeader.class);
	
	// 读取Excel 2007文件
	@SuppressWarnings("deprecation")
	public DataTable[] readFile(String strFilePath, String strFileName, String keyRow) {
		List<DataTable> list = new ArrayList<DataTable>();	
		
        try
        {
        	// 设置访问密码
        	Biff8EncryptionKey.setCurrentUserPassword(this.m_strPassword);
        	
            XSSFWorkbook workbook = null;
            try {
            	workbook = new XSSFWorkbook(new FileInputStream(strFilePath + File.separator + strFileName));
            } catch(Exception e) {
            	// 可能内存溢出
            	IFileHandler handler = new XlsxFileHandler_NoHeader_Stream();
        		handler.setUserName(this.m_strUserName);
        		handler.setPassword(this.m_strPassword);
        		handler.setStandardColumn(this.m_column);
        		return(handler.readFile(strFilePath, strFileName, keyRow));
            }
            
            XSSFSheet sheet = null;
            XSSFRow headerRow = null;
            int iSheets = workbook.getNumberOfSheets();// 一共有几个sheet

            DataTable dt = null;
            DataColumn column = null;
            int cellCount = 0;
            int rowCount = 0;
            XSSFRow row = null;
            DataRow dataRow = null;
            XSSFCell cell = null;
            boolean bFlag = false;
            int iHeaderRow = 0;
            
            for(int iSheet = 0; iSheet < iSheets; iSheet++) {
	            sheet = (XSSFSheet)workbook.getSheetAt(iSheet);
	            // 获取列头行标号
	            iHeaderRow = getHeaderRow(sheet);
	            if(iHeaderRow == -1) {
	            	// 没有找到列头，不是数据sheet，跳过
	            	continue;
	            }
	            
	            headerRow = (XSSFRow)sheet.getRow(iHeaderRow);
	            cellCount = headerRow.getLastCellNum();
	            
	            dt = new DataTable();
	            dt.TableName = sheet.getSheetName();
	
	            for (int i = headerRow.getFirstCellNum(); i < cellCount; i++) {
	            	if(headerRow.getCell(i) == null) {
	            		break;
	            	}
	                column = new DataColumn(headerRow.getCell(i).getStringCellValue());
	                dt.Columns.Add(column);
	            }
	
	            rowCount = sheet.getLastRowNum();
	
	            for (int i = (iHeaderRow + 1); i <= rowCount; i++) {
	            	row = (XSSFRow)sheet.getRow(i);
	            	if(row == null) {	// 空行，跳过
	            		continue;
	            	}
	            	
	            	// 最后一行，如果有计算公式则认为是汇总行，跳过
	            	if(i == rowCount && checkFormulaRow(row)) {
	            		continue;
	            	}
	            	
	                dataRow = dt.NewRow();
	                bFlag = false;
	                for (int j = row.getFirstCellNum(); j < cellCount; j++) {
	                	cell = row.getCell(j);
	                    if (cell != null && ("") != cell.toString()) {
	                    	bFlag = true;
	                    	cell.setCellType(XSSFCell.CELL_TYPE_STRING);
	                        dataRow.setValue(j, cell.getStringCellValue());
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

	
	private int getHeaderRow(XSSFSheet sheet) {
		int iHeaderRow = -1;
		XSSFRow row = null;
		
		// 按行从上到下，按列从左到右，查找列头行
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			if(i < 0) {
				continue;
			}
			
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
	
	private boolean checkRow(XSSFRow row) {
		boolean bFlag = false;
		String columnName = "";
		
		for(ColumnItem item : this.m_column) {
    		if(!item.isHasValue()) {
    			continue;
    		}
    		
			columnName = item.getMapColumnName();
			if(item.isMapColumn() || item.isFromColumn()) {
				bFlag = false;
				for(int i=row.getFirstCellNum(); i<=row.getLastCellNum(); i++) {
					if(i < 0) {
						break;
					}
					
					// 在行中查找每个标准列，如果有一个找不到，则认为非表头
					if(row.getCell(i) != null && row.getCell(i).toString().trim().equalsIgnoreCase(columnName.trim())) {
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
	
	@SuppressWarnings("deprecation")
	private boolean checkFormulaRow(XSSFRow row) {
		//boolean bFlag = false;
		int iDataRowCount = 0;
		// 如果有数据的非公式列多于3列，则认为正常数据
		// 如果只有一列数据，则认为是 汇总数据

		for(int i=row.getFirstCellNum(); i<=row.getLastCellNum(); i++) {
			// 行中有一个公式，则认为为汇总行
			if(row.getCell(i) != null) {
				if(row.getCell(i).getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA) {
					//bFlag = true;
				} else {
					if(!("").equals(row.getCell(i).toString().trim())) {
						iDataRowCount++;
					}
				}
			}
		}

		return(iDataRowCount<4);
	}
}
