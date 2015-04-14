package com.sendtend.utils.FileHandler;

import java.util.List;

import com.sendtend.utils.TemplateHelper.ColumnItem;

import System.Data.DataTable;

public interface IFileHandler {
	/*
	 * strFilePath 文件路径，文件所在文件夹全路径
	 * strFileName 文件名，不包含路径
	 */
	DataTable[] readFile(String strFilePath, String strFileName);
	void setUserName(String strUserName);
	void setPassword(String strPassword);
	void setStandardColumn(List<ColumnItem> column);
}
