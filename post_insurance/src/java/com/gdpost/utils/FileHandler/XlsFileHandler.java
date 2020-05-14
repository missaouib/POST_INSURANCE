package com.gdpost.utils.FileHandler;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdpost.utils.MyException;
import com.gdpost.utils.StringUtil;

import System.Data.DataColumn;
import System.Data.DataRow;
import System.Data.DataTable;

public class XlsFileHandler extends AbstractFileHandler {
	public static Logger log = LoggerFactory.getLogger(XlsFileHandler.class);

	public static boolean isMergedRegion(HSSFSheet sheet, int row, int column) {
	    int sheetMergeCount = sheet.getNumMergedRegions();
	    for (int i = 0; i < sheetMergeCount; i++) {
	        CellRangeAddress ca = sheet.getMergedRegion(i);
	        int firstColumn = ca.getFirstColumn();
	        int lastColumn = ca.getLastColumn();
	        int firstRow = ca.getFirstRow();
	        int lastRow = ca.getLastRow();
	        if (row >= firstRow && row <= lastRow) {
	            if (column >= firstColumn && column <= lastColumn) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	// 读取Excel 2003文件
	public DataTable[] readFile(String strFilePath, String strFileName, String mkeyRow) throws MyException{
		List<DataTable> list = new ArrayList<DataTable>();
		log.debug("--------------ready to read xls file in handler" + this.keyRow);
		
		String realKeyRow = mkeyRow;
		if(mkeyRow.equals("underwrite")) {
			realKeyRow = "打印时间";
		}
		HSSFWorkbook workbook = null;
		int markIdx = 0;
		try {
			// 设置访问密码
			// Biff8EncryptionKey.setCurrentUserPassword(this.m_strPassword);
			log.debug("-------- xls file -----------" + strFilePath + File.separator + strFileName);
			workbook = new HSSFWorkbook(new FileInputStream(strFilePath + File.separator + strFileName));
			HSSFSheet sheet = null;
			HSSFRow headerRow = null;
			int iSheets = workbook.getNumberOfSheets();// 一共有几个sheet
			log.debug("--------------sheets's num: " + iSheets);
			DataTable dt = null;
			DataColumn column = null;
			DataRow dataRow = null;
			int cellCount = 0;
			int rowCount = 0;
			HSSFRow row = null;
			HSSFCell cell = null;
			boolean bFlag = false;
			int markRow = -1;
			int skipRow = -1;
			int lastRow = -1;
			boolean hasKeyRow = false;
			String check = null;
			int keyRowIdx = -1;
			int count = -1;
			Cell checkCell = null;
			int org_idx = -1;
			String sDate = null;
			String eDate = null;
			//boolean isNum = false;
			//List<CellRangeAddress> calist = new ArrayList<CellRangeAddress>();
			// 如果是合并单元表格，略过
			Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$"); 
	        
			for (int iSheet = 0; iSheet < iSheets; iSheet++) {
				markIdx = 0;
				log.debug("---------sheet:" + iSheet);
				sheet = (HSSFSheet) workbook.getSheetAt(iSheet);
				int sheetmergerCount = sheet.getNumMergedRegions();
				log.debug("--------------有这么多个合并单元格：" + sheetmergerCount);
				
				//如果是回访合格率数据，获取时间点
				if(keyRow.equals("成功率")) {
					headerRow = (HSSFRow) sheet.getRow(2);
					sDate = headerRow.getCell(1).getStringCellValue();
					eDate = headerRow.getCell(3).getStringCellValue();
				}
				
				if(sheetmergerCount > 0) {
					skipRow = sheet.getMergedRegion(sheetmergerCount-1).getLastRow();
				}
				if(skipRow > 10) {
					for (int i=0; i<10; i++) {
						if(!isMergedRegion(sheet, i, 0)) {
							skipRow = i-1;
							break;
						}
					}
				}
				lastRow = sheet.getLastRowNum();
				if(realKeyRow.equals("保全受理号") || realKeyRow.equals("保单号码") || realKeyRow.equals("险种编码") || realKeyRow.equals("投保人证件号码") || realKeyRow.equals("da保单号") || realKeyRow.equals("约定还款日期")) {
					if(realKeyRow.equals("da保单号")) {
						realKeyRow = "保单号";
					}
					for(int i=0; i<=7; i++) {
						headerRow = (HSSFRow) sheet.getRow(i);
						if (headerRow == null) {
							continue;
						}
						Iterator<Cell> iter = headerRow.cellIterator();
						count = 0;
						while(iter.hasNext()) {
							checkCell = iter.next();
							if(checkCell.getStringCellValue() != null && checkCell.getStringCellValue().trim().equals("序号")) {
								skipRow = i-1;
								break;
							}
							if(count++>1) {
								break;
							}
						}
					}
				}
				for (int i = skipRow+1; i < lastRow; i++) {
					count = -1;
					headerRow = (HSSFRow) sheet.getRow(i);
					if (headerRow == null) {
						continue;
					}
					Iterator<Cell> iter = headerRow.cellIterator();
					while(iter.hasNext()) {
						count ++;
						checkCell = iter.next();
						switch(checkCell.getCellType()) {
						case BLANK:
							break;
						case _NONE:
							break;
						case FORMULA:
							check = checkCell.getCellFormula();
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
						case STRING:
						    check = StringUtil.trimStr(checkCell.getStringCellValue());
							break;
						default:
							check = checkCell.getStringCellValue();
							break;
						}
						if(check != null && check.trim().equals(realKeyRow)) {
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
				log.debug("-----------xls file handle: " + cellCount);

				dt = new DataTable();
				dt.TableName = sheet.getSheetName();
				rowCount = sheet.getLastRowNum();
				
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
					if (mkeyRow.equals("underwrite") && column.ColumnName.equals("机构代码")) {
						org_idx = i;
					}
					dt.Columns.Add(column);
				}
				
				if(keyRow.equals("成功率")) {
					dt.Columns.Add("开始时间");
					dt.Columns.Add("结束时间");
				}

				String val = null;
				
				boolean isPolicyBackDate = false;
				if(mkeyRow.equals("签收日期")) {
					isPolicyBackDate = true;
					if(markRow!=1) {
						markRow = 3;
					}
				}
				for (int i = markRow + 1; i <= rowCount; i++) {
					markIdx ++;
					row = (HSSFRow) sheet.getRow(i);
					if (row == null) { // 空行，不允许，退出
						continue;
						//break;
					}
					//如果row的列长度不够，也退出
					if(row.getLastCellNum() < this.m_column.size()/2) {
						continue;
						//break;
					}
					if(row.getCell(keyRowIdx) == null) {
						continue;
					}
					if(mkeyRow.equals("关联业务号码")) {
						if(row.getCell(keyRowIdx-1) == null) {
							continue;
						}
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
							if (mkeyRow.equals("underwrite") && j==org_idx) { //如果是人核件打印数据，且内容不是8644的，跳过。
								switch(cell.getCellType()) {
								case NUMERIC:
									Double cv = cell.getNumericCellValue();
									if (!Integer.toString(cv.intValue()).contains("8644") && !Integer.toString(cv.intValue()).contains("7644") && !Integer.toString(cv.intValue()).contains("9644") && !Integer.toString(cv.intValue()).contains("8144")) {
										bFlag = false;
										break;
									}
									break;
								default:
									if (!cell.getStringCellValue().contains("8644") && !cell.getStringCellValue().contains("7644") && !cell.getStringCellValue().contains("9644") && !cell.getStringCellValue().contains("8144")) {
										bFlag = false;
										break;
									}
								}
							}
							if(isPolicyBackDate && j==row.getFirstCellNum()) {
								if(cell.getStringCellValue() == null || cell.getStringCellValue().length()==0 || !pattern.matcher(cell.getStringCellValue()).matches()) {
									bFlag = false;
									break;
								}
							}
							//log.debug("-----------" + cell.getCellType());
							switch(cell.getCellType()) {
							case NUMERIC:
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
							        Date date = cell.getDateCellValue();
							        dataRow.setValue(j, StringUtil.trimStr(DateFormatUtils.format(date, "yyyy-MM-dd")));
							    } else {
							    	/*
							    	Double d = cell.getNumericCellValue();
							    	if((d.toString().length() - d.toString().indexOf("."))>=5) {
							    		DecimalFormat df = new DecimalFormat();
							    		df.setMaximumFractionDigits(5);
							    		dataRow.setValue(j, new DataColumn(df.format(cell.getNumericCellValue())));
							    	} else {
							    	*/
							    		cell.setCellType(CellType.STRING);
							    		dataRow.setValue(j, cell.getStringCellValue());
							    	//}
							    }
								break;
							case STRING:
								val = StringUtil.trimStr(cell.getStringCellValue());
								if(keyRowIdx == j && val.contains("日期")) {
									j = 10000;
								}
								dataRow.setValue(j, val);
								break;
							case BLANK:
								dataRow.setValue(j, "");
								break;
							case FORMULA:
								cell.setCellType(CellType.STRING);
								dataRow.setValue(j, StringUtil.trimStr(cell.getStringCellValue()));
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
					
					if(keyRow.equals("成功率")) {
						dataRow.setValue(cellCount,sDate);
						dataRow.setValue(cellCount+1,eDate);
					}
					if (!bFlag) { // 每个cell　都为空，跳过
						continue;
					}

					dt.Rows.add(dataRow);
				}

				sheet = null;
				list.add(dt);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new MyException("除开表头后数据中第" + markIdx + "行数据有问题：" + e.getMessage());
		} finally {
			try {
				workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		DataTable[] ds = new DataTable[list.size()];
		return (list.toArray(ds));
	}

	/**
	 * 判断单元格是否为合并单元格
	 * 
	 * @param listCombineCell
	 *            存放合并单元格的list
	 * @param cell
	 *            需要判断的单元格
	 * @param sheet
	 *            sheet
	 * @return
	 */
	public static Boolean isCombineCell(List<CellRangeAddress> listCombineCell, HSSFCell cell, HSSFSheet sheet) {
		int firstC = 0;
		int lastC = 0;
		int firstR = 0;
		int lastR = 0;
		for (CellRangeAddress ca : listCombineCell) {
			// 获得合并单元格的起始行, 结束行, 起始列, 结束列
			firstC = ca.getFirstColumn();
			lastC = ca.getLastColumn();
			firstR = ca.getFirstRow();
			lastR = ca.getLastRow();
			if (cell.getColumnIndex() <= lastC && cell.getColumnIndex() >= firstC) {
				if (cell.getRowIndex() <= lastR && cell.getRowIndex() >= firstR) {
					return true;
				}
			}
		}
		return false;
	}
}
