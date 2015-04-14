package com.sendtend.utils.FileHandler;

import java.util.List;

import com.sendtend.utils.TemplateHelper.ColumnItem;

public abstract class AbstractFileHandler implements IFileHandler {

	protected String m_strUserName = "";
	protected String m_strPassword = "";
	protected List<ColumnItem> m_column;
	
	public void setUserName(String strUserName) {
		this.m_strUserName = strUserName;
	}
	
	public void setPassword(String strPassword) {
		this.m_strPassword = strPassword;
	}
	
	public void setStandardColumn(List<ColumnItem> column) {
		this.m_column = column;
	}
}
