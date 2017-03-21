package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class RenewedHQListColumn {

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
		
		column = new ColumnItem();
		column.setDisplayName("主险名称");
		column.setColumnName("prd_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
//		column = new ColumnItem();
//		column.setDisplayName("保单年度");
//		column.setColumnName("policy_year");
//		column.setColumnType(ColumnType.numeric);
//		column.setNullable(false);
//		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("回访结果分类");
		column.setColumnName("hq_issue_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("回访结果");
		column.setColumnName("hq_deal_rst");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("创建时间");
		column.setColumnName("hq_deal_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("工单内容");
		column.setColumnName("hq_deal_remark");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
