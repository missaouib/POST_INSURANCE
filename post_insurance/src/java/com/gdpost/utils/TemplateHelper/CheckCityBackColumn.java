package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class CheckCityBackColumn {

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
		column.setNullable(false);
		standardColumns.add(column);
		
//		column = new ColumnItem();
//		column.setDisplayName("批次号");
//		column.setColumnName("check_batch");
//		column.setColumnType(ColumnType.string);
//		column.setNullable(false);
//		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("处理结果");
		column.setColumnName("fix_desc");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("经办人");
		column.setColumnName("deal_man");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("经办日期");
		column.setColumnName("deal_time");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
