package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class RenewedFeeRstColumn {

	private static List<ColumnItem> standardColumns;
	
	public static String KEY_ROW = "服务内容";
	
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
		
		column = new ColumnItem();
		column.setDisplayName("险种");
		column.setColumnName("prd_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保单年度");
		column.setColumnName("policy_year");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("处理人");
		column.setColumnName("deal_man");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("处理日期 ");
		column.setColumnName("deal_time");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("处理类型");
		column.setColumnName("deal_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("服务内容");
		column.setColumnName("fix_desc");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("备注");
		column.setColumnName("fix_status");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
