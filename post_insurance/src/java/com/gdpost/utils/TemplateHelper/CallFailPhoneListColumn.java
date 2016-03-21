package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class CallFailPhoneListColumn {

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
		column.setDisplayName("通话号码");
		column.setColumnName("phone_num");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("通话开始时间");
		column.setColumnName("phone_start");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("通话结束时间");
		column.setColumnName("phone_end");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("通话时长");
		column.setColumnName("phone_time");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
