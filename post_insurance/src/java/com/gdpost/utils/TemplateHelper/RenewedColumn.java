package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class RenewedColumn {

	private static List<ColumnItem> standardColumns;
	
	public static String KEY_ROW = "保险单号码";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("保险单号码");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(1);
		column.setOutputName("BDHM");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保人");
		column.setColumnName("holder");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保单年度");
		column.setColumnName("policy_year");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(3);
		column.setOutputName("BDND");
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
		column.setDisplayName("交费对应日");
		column.setColumnName("fee_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(5);
		column.setOutputName("JFDYR");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("应收保费");
		column.setColumnName("policy_fee");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(6);
		column.setOutputName("YSBF");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("收费状态");
		column.setColumnName("fee_status");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(6);
		column.setOutputName("SFZT");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("开户行");
		column.setColumnName("account_bank");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(7);
		column.setOutputName("JBBE");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("账号");
		column.setColumnName("account");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(8);
		column.setOutputName("ZH");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("通讯地址");
		column.setColumnName("addr");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setiOutputOrder(9);
		column.setOutputName("TXDZ");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("邮编");
		column.setColumnName("post_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(10);
		column.setOutputName("YB");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("联系电话");
		column.setColumnName("phone");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(11);
		column.setOutputName("LXDH");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("手机号码");
		column.setColumnName("mobile");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setiOutputOrder(12);
		column.setOutputName("SJHM");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("管理机构");
		column.setColumnName("org_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(13);
		column.setOutputName("GLJG");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("网点名称");
		column.setColumnName("net_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(14);
		column.setOutputName("WDMC");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("银保专管员");
		column.setColumnName("policy_admin");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(15);
		column.setOutputName("YBZGY");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("渠道");
		column.setColumnName("channel");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(16);
		column.setOutputName("QD");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("标记");
		column.setColumnName("prov_activity");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
