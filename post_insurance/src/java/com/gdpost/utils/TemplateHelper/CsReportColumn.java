package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class CsReportColumn {

private static List<ColumnItem> standardColumns;
	
	public static String KEY_ROW = "comma";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		//保全受理号 保单号 	保单所属机构 网点名称 	渠道 	操作机构代码 投保人姓名 	被保险人姓名 	保全复核通过日期 项目编码 金额 申请方式 
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("网点代码");
		column.setColumnName("bank_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("网点名称");
		column.setColumnName("bank_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保单号");
		column.setColumnName("form_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保险单号");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("申请人");
		column.setColumnName("req_man");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保全项目名称");
		column.setColumnName("req_type_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保全申请日期");
		column.setColumnName("req_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("客户联系电话");
		column.setColumnName("req_phone");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
