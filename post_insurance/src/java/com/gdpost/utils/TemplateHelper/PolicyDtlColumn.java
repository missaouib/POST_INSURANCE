package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class PolicyDtlColumn {

	private static List<ColumnItem> standardColumns;
	
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
		column.setNeedEncrypt(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(1);
		column.setOutputName("JGDM");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保险单号码");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedEncrypt(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(3);
		column.setOutputName("BDH");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保人");
		column.setColumnName("holder");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedEncrypt(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(5);
		column.setOutputName("TBR");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("性别");
		column.setColumnName("holder_sexy");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedEncrypt(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(5);
		column.setOutputName("XB");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("被保险人");
		column.setColumnName("insured");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(6);
		column.setOutputName("BBXRXM");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("险种名称");
		column.setColumnName("prod_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(8);
		column.setOutputName("XZMC");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保险金额");
		column.setColumnName("insured_amount");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(10);
		column.setOutputName("JBBE");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保险费");
		column.setColumnName("policy_fee");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(9);
		column.setOutputName("BF");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("营业员");
		column.setColumnName("sales_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(19);
		column.setOutputName("DLJGXSRYBM");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("承保日期");
		column.setColumnName("policy_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(13);
		column.setOutputName("CBRQ");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("联系电话");
		column.setColumnName("holder_mobile");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(14);
		column.setOutputName("SXRQ");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("手机号码");
		column.setColumnName("holder_phone");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setiOutputOrder(15);
		column.setOutputName("BDZT");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("联系地址");
		column.setColumnName("holder_addr");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(16);
		column.setOutputName("WDBM");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("邮编");
		column.setColumnName("holder_postcode");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(17);
		column.setOutputName("WDMC");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("证件类型");
		column.setColumnName("holder_card_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(18);
		column.setOutputName("DLJGXSRYBM");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("证件号码");
		column.setColumnName("holder_card_num");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(18);
		column.setOutputName("DLJGXSRYBM");
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
