package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class RenewedFeeMatchColumn {

	private static List<ColumnItem> standardColumns;
	
	public static String KEY_ROW = "匹配结果";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("保险单号码");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("险种名称");
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
		column.setDisplayName("匹配结果");
		column.setColumnName("fee_match");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
