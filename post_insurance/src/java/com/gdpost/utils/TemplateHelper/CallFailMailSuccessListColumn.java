package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class CallFailMailSuccessListColumn {

	private static List<ColumnItem> standardColumns;
	public static String KEY_ROW = "保单号";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("保单号");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		/*
		column = new ColumnItem();
		column.setDisplayName("工单流水号");
		column.setColumnName("issue_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		*/
		
		column = new ColumnItem();
		column.setDisplayName("信函成功");
		column.setColumnName("has_letter");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("回邮时间");
		column.setColumnName("mail_back_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("客户签名时间");
		column.setColumnName("client_sign_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		
		return(standardColumns);
	}
}
