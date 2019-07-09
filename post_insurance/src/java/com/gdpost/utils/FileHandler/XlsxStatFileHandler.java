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

public class XlsxStatFileHandler extends AbstractFileHandler {
	public static Logger log = LoggerFactory.getLogger(XlsxStatFileHandler.class);
	
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
            int markRow = 5;
			int count = -1;
			int keyRowIdx = -1;
			Cell checkCell = null;
			String check = null;
			boolean hasKeyRow = false;
            XSSFRow row = null;
            DataRow dataRow = null;
            XSSFCell cell = null;
            String mth = null;
            
            for(int iSheet = 0; iSheet < iSheets; iSheet++) {
            	markIdx = 0;
	            sheet = (XSSFSheet)workbook.getSheetAt(iSheet);
	            
	            sheet.getRow(0).getCell(1).setCellType(CellType.STRING);
	            mth = sheet.getRow(0).getCell(1).getStringCellValue();
	            
	            headerRow = sheet.getRow(1);
				Iterator<Cell> iter = headerRow.cellIterator();
				while(iter.hasNext()) {
					count ++;
					checkCell = iter.next();
					switch(checkCell.getCellType()) {
					case BLANK:
						break;
					case FORMULA:
						checkCell.setCellType(CellType.STRING);
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
						break;
					}
				}
				if(!hasKeyRow) {
					return null;
				}
				
	            cellCount = headerRow.getLastCellNum();
	            
	            dt = new DataTable();
	            dt.TableName = sheet.getSheetName();
	            
	            dt.Columns.Add("机构号");
	            dt.Columns.Add("机构名称");
	            
	            String tmpClumn = null;
	            for (int i = 2; i < cellCount; i++) {
	            	cell = headerRow.getCell(i);
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
							tmpClumn = StringUtil.trimStr(cell.getStringCellValue());
							column = new DataColumn(tmpClumn);
					}
	                dt.Columns.Add(column);
	                if(!tmpClumn.contains("维度") && !tmpClumn.contains("总分")) {
	                	dt.Columns.Add(tmpClumn + "得分");
	                	i++;
	                }
	            }
	            dt.Columns.Add("所属月度");
	            
	            rowCount = sheet.getLastRowNum();
	            
	            String val = null;
	
	            for (int i = markRow; i <= rowCount; i++) {
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
					dataRow = dt.NewRow();
					for (int j = 0; j < cellCount; j++) {
						cell = row.getCell(j);
						switch(cell.getCellType()) {
						case NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
						        Date date = cell.getDateCellValue();
						        dataRow.setValue(j, StringUtil.trimStr(DateFormatUtils.format(date, "yyyy-MM-dd")));
						    } else {
						    	Double d = cell.getNumericCellValue();
						    	if((d.toString().length() - d.toString().indexOf("."))>=5) {
						    		DecimalFormat df = new DecimalFormat();
						    		df.setMaximumFractionDigits(5);
						    		dataRow.setValue(j, new DataColumn(df.format(cell.getNumericCellValue())));
						    	} else {
						    		dataRow.setValue(j, cell.getNumericCellValue());
						    	}
						    }
							break;
						case STRING:
							val = StringUtil.trimStr(cell.getStringCellValue());
							dataRow.setValue(j, val);
							break;
						case BLANK:
							dataRow.setValue(j, "");
							break;
						case BOOLEAN:
							dataRow.setValue(j, StringUtil.trimStr(cell.getBooleanCellValue()));
							break;
						case FORMULA:
							cell.setCellType(CellType.STRING);
							dataRow.setValue(j, StringUtil.trimStr(cell.getStringCellValue()));
							break;
						case ERROR:
							dataRow.setValue(j, "");
							break;
							default:
								//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								cell.setCellType(CellType.STRING);
								dataRow.setValue(j, StringUtil.trimStr(cell.getStringCellValue()));
						}
					}
					
					dataRow.setValue(cellCount, mth);
					
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
