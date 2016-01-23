package com.gdpost.utils.FileHandler;

import java.util.List;

import System.Data.DataTable;

import com.gdpost.utils.TemplateHelper.ColumnItem;

public interface IFileHandler {
	/*
	 * strFilePath 文件路径，文件所在文件夹全路径
	 * strFileName 文件名，不包含路径
	 */
	DataTable[] readFile(String strFilePath, String strFileName, String keyRow);
	void setUserName(String strUserName);
	void setPassword(String strPassword);
	void setStandardColumn(List<ColumnItem> column);
	void setKeyRow(String keyRow);
}
