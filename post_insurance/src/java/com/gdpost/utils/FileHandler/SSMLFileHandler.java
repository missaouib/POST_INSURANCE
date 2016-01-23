package com.gdpost.utils.FileHandler;

import java.util.ArrayList;
import java.util.List;

import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.excel.Worksheet;
import nl.fountain.xelem.lex.ExcelReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import System.Data.DataColumn;
import System.Data.DataRow;
import System.Data.DataTable;

import com.gdpost.utils.TemplateHelper.ColumnItem;

public class SSMLFileHandler extends AbstractFileHandler {
	public static Logger log = LoggerFactory.getLogger(XlsFileHandler_NoHeader.class);
	
	@Override
	public DataTable[] readFile(String strFilePath, String strFileName, String keyRow) {
		List<DataTable> list = new ArrayList<DataTable>();
		
		try {
            DataTable dt = null;
            DataColumn column = null;
            DataRow dataRow = null;
            int cellCount = 0;
            int rowCount = 0;
            boolean bFlag = false;
            int iHeaderRow = 0;
            Row row = null;
            Cell cell = null;
            Row headerRow = null;
            
			ExcelReader reader = new ExcelReader();
			Workbook wb = reader.getWorkbook("file:///" + strFilePath + "\\" + strFileName);
			for(Worksheet sheet : wb.getWorksheets()) {
	            // 获取列头行标号
	            iHeaderRow = getHeaderRow(sheet);
	            if(iHeaderRow == -1) {
	            	// 没有找到列头，不是数据sheet，跳过
	            	continue;
	            }
	            
	            headerRow = (Row)sheet.getRowAt(iHeaderRow);
	            cellCount = headerRow.size();
	            
	            dt = new DataTable();
	            dt.TableName = sheet.getName();
	
	            for (int i = 1; i <= cellCount; i++) {
	                column = new DataColumn(headerRow.getCellAt(i).getData$());
	                dt.Columns.Add(column);
	            }
	
	            rowCount = sheet.getRows().size();
	            
	            // 从headerRow的下一行开始读数据
	            for (int i = (iHeaderRow + 1); i <= rowCount; i++) {
	            	row = (Row)sheet.getRowAt(i);
	            	if(row == null) {	// 空行，跳过
	            		continue;
	            	}
	            	
	            	// 最后一行，如果有计算公式则认为是汇总行，跳过
	            	if(i == rowCount && checkFormulaRow(row)) {
	            		continue;
	            	}
	            	
	                dataRow = dt.NewRow();
	                bFlag = false;
	                for (int j = 0; j < cellCount; j++) {
	                	cell = row.getCellAt(j+1);
	                    if (cell != null && cell.hasData() && ("") != cell.getData$()) {
	                    	bFlag = true;
	                        dataRow.setValue(j, cell.getData$());
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
		} catch(Exception e) {
			log.error(e.getMessage());
		}
		
        DataTable[] ds = new DataTable[list.size()];
		return(list.toArray(ds));
	}

	
	private int getHeaderRow(Worksheet sheet) {
		int iHeaderRow = -1;
		Row row = null;
		
		// 按行从上到下，按列从左到右，查找列头行
		for (int i = sheet.firstRow; i <= sheet.getRows().size(); i++) {			
			row = sheet.getRowAt(i);
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
		Cell cell;
		
		for(ColumnItem item : this.m_column) {
    		if(!item.isHasValue()) {
    			continue;
    		}
    		
			columnName = item.getMapColumnName();
			if(item.isMapColumn() || item.isFromColumn()) {
				bFlag = false;
				for(int i=1; i<=row.size(); i++) {
					// 在行中查找每个标准列，如果有一个找不到，则认为非表头
					cell = row.getCellAt(i);
					if(cell != null && cell.hasData() && cell.getData$().trim().equalsIgnoreCase(columnName.trim())) {
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
	
	private boolean checkFormulaRow(Row row) {
		boolean bFlag = false;
		int iDataRowCount = 0;
		// 如果有数据的非公式列多于3列，则认为正常数据

		for(int i=1; i<=row.size(); i++) {
			// 行中有一个公式，则认为为汇总行
			if(row.getCellAt(i) != null) {
				if(row.getCellAt(i).getFormula() != null && row.getCellAt(i).getFormula() != "") {
					bFlag = true;
				}
			} else {
				iDataRowCount++;
			}
		}

		return((iDataRowCount<4)&&bFlag);
	}

}
