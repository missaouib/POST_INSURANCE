package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class CallFailNeedDoorListColumn {

	private static List<ColumnItem> standardColumns;
	public static String KEY_ROW = "工单状态";
	
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
		column.setiOutputOrder(16);
		column.setOutputName("QD");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("工单流水号");
		column.setColumnName("issue_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(3);
		column.setOutputName("GDLSH");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("工单状态");
		column.setColumnName("status");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(16);
		column.setOutputName("QD");
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
