package com.gdpost.utils.FileHandler;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
		log.debug("--------------ready to read xls file in handler");
		try {
			// 设置访问密码
			// Biff8EncryptionKey.setCurrentUserPassword(this.m_strPassword);

			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(strFilePath + File.separator + strFileName));
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
			//List<CellRangeAddress> calist = new ArrayList<CellRangeAddress>();
			// 如果是合并单元表格，略过
			
			for (int iSheet = 0; iSheet < iSheets; iSheet++) {
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
				for (int i = skipRow+1; i < sheet.getLastRowNum(); i++) {
					log.debug("------------ row: " + i);
					headerRow = (HSSFRow) sheet.getRow(i);
					log.debug("------------ row: " + headerRow);
					if (headerRow == null) {
						log.debug("--------------------headerRow is null");
						continue;
					}
					log.debug("--------------headerRow : " + headerRow + ", and the cell num: " + headerRow.getLastCellNum() + ", template column size: " + this.m_column.size());
					if (headerRow.getLastCellNum() > this.m_column.size() / 2) {
						markRow = i;
						//log.debug("--------------get the header row num : " + markRow);
						break;
					}
					//break;
				}
				log.debug("--------------get the header row num : " + markRow);
				if (markRow <= 0) {
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

				for (int i = headerRow.getFirstCellNum(); i < cellCount; i++) {
					if (cellCount < this.m_column.size()) {
						continue;
					}
					column = new DataColumn(headerRow.getCell(i).getStringCellValue());
					dt.Columns.Add(column);
				}

				rowCount = sheet.getLastRowNum();

				for (int i = markRow + 1; i <= rowCount; i++) {
					// if(cellCount < this.m_column.size()) {
					// continue;
					// }
					row = (HSSFRow) sheet.getRow(i);
					if (row == null) { // 空行，跳过
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
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
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
