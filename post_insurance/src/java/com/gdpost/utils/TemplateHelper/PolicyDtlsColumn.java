package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class PolicyDtlsColumn {

	private static List<ColumnItem> standardColumns;
	
	public static String KEY_ROW = "保险单号码";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("机构");
		column.setColumnName("organ_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保险单号码");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保人");
		column.setColumnName("holder");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("性别");
		column.setColumnName("holder_sexy");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("被保险人");
		column.setColumnName("insured");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("险种名称");
		column.setColumnName("prod_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保险金额");
		column.setColumnName("insured_amount");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保险费");
		column.setColumnName("policy_fee");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("营业员");
		column.setColumnName("sales_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("承保日期");
		column.setColumnName("policy_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("联系电话");
		column.setColumnName("holder_mobile");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("手机号码");
		column.setColumnName("holder_phone");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("联系地址");
		column.setColumnName("holder_addr");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("邮编");
		column.setColumnName("holder_postcode");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("证件类型");
		column.setColumnName("holder_card_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("证件号码");
		column.setColumnName("holder_card_num");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
