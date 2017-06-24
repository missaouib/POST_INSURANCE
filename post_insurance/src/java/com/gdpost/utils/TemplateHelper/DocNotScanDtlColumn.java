package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class DocNotScanDtlColumn {

	private static List<ColumnItem> standardColumns;
	
	public static String KEY_ROW = "da保单号";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("管理机构");
		column.setColumnName("organ_name");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保单号");
		column.setColumnName("form_no");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保单号");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("收费日期");
		column.setColumnName("fee_date");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("签单日期");
		column.setColumnName("sign_date");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("生效日期");
		column.setColumnName("valid_date");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保人");
		column.setColumnName("holder");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("被保险人");
		column.setColumnName("insured");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保费");
		column.setColumnName("policy_fee");
		column.setColumnType(ColumnType.numeric);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("交费方式");
		column.setColumnName("fee_type");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("营业员姓名");
		column.setColumnName("sales_name");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("银行代理机构");
		column.setColumnName("bank_name");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
