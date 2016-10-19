package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class UnderWriteReqColumn {

private static List<ColumnItem> standardColumns;
	
	public static String KEY_ROW = "underwrite";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		//地市机构代码	地市机构名称	县区局机构代码	县区局机构名称	网点代码	网点名称	保险单号	申请人	保单生效日期	保费	保全项目名称	保全申请日期	客户联系电话
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("地市机构代码");
		column.setColumnName("policy_no");
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
		column.setDisplayName("快递单号");
		column.setColumnName("prov_ems_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("寄出日期");
		column.setColumnName("prov_send_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
