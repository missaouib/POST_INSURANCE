package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class PolicyBackDateColumn {

	private static List<ColumnItem> standardColumns;
	public static String KEY_ROW = "签收日期";
	
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
		column.setNeedEncrypt(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("险种代码");
		column.setColumnName("prod_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedEncrypt(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("回单日期");
		column.setColumnName("bill_back_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("签收日期");
		column.setColumnName("client_receive_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
