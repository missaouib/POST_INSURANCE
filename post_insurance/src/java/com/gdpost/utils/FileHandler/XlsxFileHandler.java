package com.gdpost.utils.FileHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdpost.utils.MyException;
import com.gdpost.utils.StringUtil;

import System.Data.DataColumn;
import System.Data.DataRow;
import System.Data.DataTable;

public class XlsxFileHandler extends AbstractFileHandler {
	public static Logger log = LoggerFactory.getLogger(XlsxFileHandler.class);
	
	// 读取Excel 2007文件
	public DataTable[] readFile(String strFilePath, String strFileName, String keyRow) throws MyException {
		List<DataTable> list = new ArrayList<DataTable>();
		XSSFWorkbook workbook = null;
		int markIdx = 0;
        try
        {
        	// 设置访问密码
        	Biff8EncryptionKey.setCurrentUserPassword(this.m_strPassword);
        	
            workbook = new XSSFWorkbook(new FileInputStream(strFilePath + File.separator + strFileName));
            XSSFSheet sheet = null;
            XSSFRow headerRow = null;
            int iSheets = workbook.getNumberOfSheets();// 一共有几个sheet

            DataTable dt = null;
            DataColumn column = null;
            int cellCount = 0;
            int rowCount = 0;
            int markRow = -1;
			int skipRow = -1;
			int lastRow = -1;
			int count = -1;
			int keyRowIdx = -1;
			Cell checkCell = null;
			String check = null;
			boolean hasKeyRow = false;
            XSSFRow row = null;
            DataRow dataRow = null;
            XSSFCell cell = null;
            boolean bFlag = false;
            
            for(int iSheet = 0; iSheet < iSheets; iSheet++) {
            	markIdx = 0;
	            sheet = (XSSFSheet)workbook.getSheetAt(iSheet);
	            
	            int sheetmergerCount = sheet.getNumMergedRegions();
				log.debug("--------------有这么多个合并单元格：" + sheetmergerCount);
				if(sheetmergerCount > 0) {
					skipRow = sheet.getMergedRegion(sheetmergerCount-1).getLastRow();
				}
				
				lastRow = sheet.getLastRowNum();
				for (int i = skipRow+1; i < lastRow; i++) {
					count = -1;
					headerRow = (XSSFRow) sheet.getRow(i);
					if (headerRow == null) {
						continue;
					}
					//log.debug("--------------headerRow : " + headerRow + ", and the cell num: " + headerRow.getLastCellNum() + ", template column size: " + this.m_column.size());
					Iterator<Cell> iter = headerRow.cellIterator();
					while(iter.hasNext()) {
						count ++;
						checkCell = iter.next();
						switch(checkCell.getCellType()) {
						case BLANK:
							break;
						case FORMULA:
							check = checkCell.getStringCellValue();
							break;
						case NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(checkCell)) {
						        check = StringUtil.trimStr(DateFormatUtils.format(checkCell.getDateCellValue(), "yyyy-MM-dd"));
						    } else {
						    	DecimalFormat df = new DecimalFormat("0");
						    	check = df.format(checkCell.getNumericCellValue());
						    }
							//isNum = true;
							break;
						default:
							check = checkCell.getStringCellValue();
							break;
						}
						if(check != null && check.trim().equals(keyRow)) {
							keyRowIdx = count;
							hasKeyRow = true;
							markRow = i;
							i = lastRow;
							break;
						}
					}
				}
				if(!hasKeyRow) {
					return null;
				}
				log.debug("--------------get the header row num : " + markRow);
				if (markRow < 0) {
					return null;
				}
				headerRow = sheet.getRow(markRow);
				log.debug("------------ header is null?" + (headerRow == null?"null":headerRow.getSheet().getSheetName()));
				if(headerRow == null) {
					continue;
				}
				
	            cellCount = headerRow.getLastCellNum();
	            
	            dt = new DataTable();
	            dt.TableName = sheet.getSheetName();
	
	            for (int i = headerRow.getFirstCellNum(); i < cellCount; i++) {
	            	cell = headerRow.getCell(i);
	                if (cell != null && ("") != cell.toString()) {
						switch(cell.getCellType()) {
						case NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
						        Date date = cell.getDateCellValue();
						        column = new DataColumn(StringUtil.trimStr(DateFormatUtils.format(date, "yyyy-MM-dd")));
						    } else {
						        DecimalFormat df = new DecimalFormat("0");
						        column = new DataColumn(df.format(cell.getNumericCellValue()));
						    }
							break;
							default:
								cell.setCellType(CellType.STRING);
								column = new DataColumn(StringUtil.trimStr(cell.getStringCellValue()));
						}
					} else {
						column = new DataColumn("");
					}
	                dt.Columns.Add(column);
	            }
	
	            rowCount = sheet.getLastRowNum();
	
	            //for (int i = (sheet.getFirstRowNum() + 1); i <= rowCount; i++) {
	            for (int i = markRow + 1; i <= rowCount; i++) {
	            	markIdx ++;
					row = (XSSFRow) sheet.getRow(i);
					if (row == null) { // 空行，不允许，退出
						//continue;
						break;
					}
					//如果row的列长度不够，也退出
					if(row.getLastCellNum() < this.m_column.size()/2) {
						break;
					}
					if(row.getCell(keyRowIdx) == null) {
						continue;
					}
					if(row.getCell(keyRowIdx).getCellType() == CellType.NUMERIC?row.getCell(keyRowIdx).getNumericCellValue()<=0:row.getCell(keyRowIdx).getStringCellValue().trim().length()<=0) {
						continue;
					}
					dataRow = dt.NewRow();
					bFlag = false;
					for (int j = row.getFirstCellNum(); j < cellCount; j++) {
						cell = row.getCell(j);
						if (cell != null && ("") != cell.toString()) {
							bFlag = true;
							//log.debug("-----------" + cell.getCellType());
							switch(cell.getCellType()) {
							case NUMERIC:
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
							        Date date = cell.getDateCellValue();
							        dataRow.setValue(j, StringUtil.trimStr(DateFormatUtils.format(date, "yyyy-MM-dd")));
							    } else {
							        DecimalFormat df = new DecimalFormat("0");
							        dataRow.setValue(j, df.format(cell.getNumericCellValue()));
							    }
								break;
							case STRING:
								dataRow.setValue(j, StringUtil.trimStr(cell.getStringCellValue()));
								break;
							case BLANK:
								dataRow.setValue(j, "");
								break;
							case BOOLEAN:
								dataRow.setValue(j, StringUtil.trimStr(cell.getBooleanCellValue()));
								break;
							case ERROR:
								dataRow.setValue(j, "");
								break;
								default:
									//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
									dataRow.setValue(j, StringUtil.trimStr(cell.getStringCellValue()));
							}
						}
					}

					if (!bFlag) { // 每个cell　都为空，跳过
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
        	e.printStackTrace();
			log.error(e.getMessage());
			throw new MyException("除开表头后数据中第" + markIdx + "行数据有问题：" + e.getMessage());
        } finally {
        	if(workbook != null) {
        		try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        
        DataTable[] ds = new DataTable[list.size()];
		return(list.toArray(ds));
	}
}
