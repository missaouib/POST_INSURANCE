package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class RenewedHQListColumn {

	private static List<ColumnItem> standardColumns;
	
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
		column.setDisplayName("险种名称");
		column.setColumnName("prd_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(4);
		column.setOutputName("XZMC");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("工单子类");
		column.setColumnName("hq_issue_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(16);
		column.setOutputName("QD");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("工单内容");
		column.setColumnName("hq_deal_rst");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(16);
		column.setOutputName("QD");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("回访日期");
		column.setColumnName("hq_deal_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(16);
		column.setOutputName("QD");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("备注");
		column.setColumnName("hq_deal_remark");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(16);
		column.setOutputName("QD");
		standardColumns.add(column);
		
		return(standardColumns);
	}
}