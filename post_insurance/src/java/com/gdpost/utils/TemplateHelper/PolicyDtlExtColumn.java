package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class PolicyDtlExtColumn {

	private static List<ColumnItem> standardColumns;
	
	public static String KEY_ROW = "保险单号";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		
		column = new ColumnItem();
		column.setDisplayName("保险单号");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("险种名称");
		column.setColumnName("prod_name");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("证件类型");
		column.setColumnName("insured_card_type");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("证件号码");
		column.setColumnName("insured_card_num");
		column.setColumnType(ColumnType.string);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("证件有效期");
		column.setColumnName("insured_card_valid");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("缴费账号");
		column.setColumnName("bank_account");
		column.setColumnType(ColumnType.string);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
