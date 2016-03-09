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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import System.Data.DataColumn;
import System.Data.DataRow;
import System.Data.DataTable;

import com.gdpost.utils.MyException;
import com.gdpost.utils.StringUtil;

public class XlsFileHandler extends AbstractFileHandler {
	public static Logger log = LoggerFactory.getLogger(XlsFileHandler.class);

	// 读取Excel 2003文件
	public DataTable[] readFile(String strFilePath, String strFileName, String mkeyRow) throws MyException{
		List<DataTable> list = new ArrayList<DataTable>();
		log.debug("--------------ready to read xls file in handler" + this.keyRow);
		HSSFWorkbook workbook = null;
		int markIdx = 0;
		try {
			// 设置访问密码
			// Biff8EncryptionKey.setCurrentUserPassword(this.m_strPassword);

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
			//boolean isNum = false;
			//List<CellRangeAddress> calist = new ArrayList<CellRangeAddress>();
			// 如果是合并单元表格，略过
			
			for (int iSheet = 0; iSheet < iSheets; iSheet++) {
				markIdx = 0;
				log.debug("---------sheet:" + iSheet);
				sheet = (HSSFSheet) workbook.getSheetAt(iSheet);
				int sheetmergerCount = sheet.getNumMergedRegions();
				log.debug("--------------有这么多个合并单元格：" + sheetmergerCount);
				//log.debug("--------------合并单元格：" + sheet.getMergedRegion(sheetmergerCount-1).getFirstRow());
				//log.debug("--------------合并单元格：" + sheet.getMergedRegion(sheetmergerCount-1).getNumberOfCells());
				if(sheetmergerCount > 0) {
					skipRow = sheet.getMergedRegion(sheetmergerCount-1).getLastRow();
				}
				//log.debug("--------------to skip i: " + skipRow + ", sheet last row: " + sheet.getLastRowNum());
				lastRow = sheet.getLastRowNum();
				for (int i = skipRow+1; i < lastRow; i++) {
					count = -1;
					headerRow = (HSSFRow) sheet.getRow(i);
					if (headerRow == null) {
						continue;
					}
					//log.debug("--------------headerRow : " + headerRow + ", and the cell num: " + headerRow.getLastCellNum() + ", template column size: " + this.m_column.size());
					Iterator<Cell> iter = headerRow.cellIterator();
					while(iter.hasNext()) {
						count ++;
						checkCell = iter.next();
						switch(checkCell.getCellType()) {
						case HSSFCell.CELL_TYPE_BLANK:
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							check = checkCell.getCellFormula();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
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
				log.debug("-----------xls file handle: " + cellCount);

				dt = new DataTable();
				dt.TableName = sheet.getSheetName();
				rowCount = sheet.getLastRowNum();
				
				for (int i = headerRow.getFirstCellNum(); i < cellCount; i++) {
					cell = headerRow.getCell(i);
					if (cell != null && ("") != cell.toString()) {
						switch(cell.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
						        Date date = cell.getDateCellValue();
						        column = new DataColumn(StringUtil.trimStr(DateFormatUtils.format(date, "yyyy-MM-dd")));
						    } else {
						        DecimalFormat df = new DecimalFormat("0");
						        column = new DataColumn(df.format(cell.getNumericCellValue()));
						    }
							break;
							default:
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								column = new DataColumn(StringUtil.trimStr(cell.getStringCellValue()));
						}
					} else {
						column = new DataColumn("");
					}
					dt.Columns.Add(column);
				}

				for (int i = markRow + 1; i <= rowCount; i++) {
					markIdx ++;
					row = (HSSFRow) sheet.getRow(i);
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
					if(row.getCell(keyRowIdx).getCellType() == HSSFCell.CELL_TYPE_NUMERIC?row.getCell(keyRowIdx).getNumericCellValue()<=0:row.getCell(keyRowIdx).getStringCellValue().trim().length()<=0) {
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
							case HSSFCell.CELL_TYPE_NUMERIC:
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
							        Date date = cell.getDateCellValue();
							        dataRow.setValue(j, StringUtil.trimStr(DateFormatUtils.format(date, "yyyy-MM-dd")));
							    } else {
							        DecimalFormat df = new DecimalFormat("0");
							        dataRow.setValue(j, df.format(cell.getNumericCellValue()));
							    }
								break;
							case HSSFCell.CELL_TYPE_STRING:
								dataRow.setValue(j, StringUtil.trimStr(cell.getStringCellValue()));
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								dataRow.setValue(j, "");
								break;
							case HSSFCell.CELL_TYPE_FORMULA:
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

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new MyException("除开表头后数据中第" + markIdx + "行数据有问题：" + e.getMessage());
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
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
