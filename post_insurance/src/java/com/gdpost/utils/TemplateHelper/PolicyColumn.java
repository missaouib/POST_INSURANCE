package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class PolicyColumn {

	private static List<ColumnItem> standardColumns;
	public static String KEY_ROW = "保单号码";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("机构代码");
		column.setColumnName("organ_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedEncrypt(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("机构名称");
		column.setColumnName("organ_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedEncrypt(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保单号码");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedEncrypt(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保单号码");
		column.setColumnName("form_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedEncrypt(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("投保人姓名");
		column.setColumnName("holder");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedEncrypt(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("被保险人姓名");
		column.setColumnName("insured");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("计划编码");
		column.setColumnName("plan_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("计划名称");
		column.setColumnName("plan_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("险种编码");
		column.setColumnName("prod_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("险种名称");
		column.setColumnName("prod_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保费");
		column.setColumnName("policy_fee");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedEncrypt(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("基本保额");
		column.setColumnName("insured_amount");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("缴费频率");
		column.setColumnName("fee_frequency");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("缴费期数");
		column.setColumnName("perm");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedEncrypt(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(12);
		column.setOutputName("JFPL");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("签单日期");
		column.setColumnName("policy_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("生效日期");
		column.setColumnName("plicy_valid_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保单状态");
		column.setColumnName("status");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("网点编码");
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
		column.setDisplayName("代理机构销售人员编码");
		column.setColumnName("sales_id");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("代理机构销售人员姓名");
		column.setColumnName("sales_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("客户经理编码");
		column.setColumnName("customer_manager_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("姓名");
		column.setColumnName("customer_manager");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
