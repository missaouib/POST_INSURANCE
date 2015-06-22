package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class RemitMoneyColumn {

	private static List<ColumnItem> standardColumns;
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("机构代码");
		column.setColumnName("org_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(1);
		column.setOutputName("BDHM");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("单位名称");
		column.setColumnName("org_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(2);
		column.setOutputName("TBR");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保单号");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(3);
		column.setOutputName("BDND");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("应交日");
		column.setColumnName("should_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(4);
		column.setOutputName("XZMC");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("交至日");
		column.setColumnName("to_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(5);
		column.setOutputName("JFDYR");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保单当前状态");
		column.setColumnName("policy_status");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(6);
		column.setOutputName("YSBF");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("交费失败原因");
		column.setColumnName("fail_desc");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(7);
		column.setOutputName("JBBE");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("险种代码");
		column.setColumnName("prd_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(8);
		column.setOutputName("ZH");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("险种名称");
		column.setColumnName("prd_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(9);
		column.setOutputName("TXDZ");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("主附险标志");
		column.setColumnName("insurance_flag");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(10);
		column.setOutputName("YB");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("交次");
		column.setColumnName("frequence");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(11);
		column.setOutputName("LXDH");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("交费方式");
		column.setColumnName("fee_frequency");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(12);
		column.setOutputName("SJHM");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("应收保费");
		column.setColumnName("policy_fee");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setiOutputOrder(13);
		column.setOutputName("GLJG");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保人");
		column.setColumnName("holder");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setiOutputOrder(14);
		column.setOutputName("WDMC");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("被保人");
		column.setColumnName("insured");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setiOutputOrder(15);
		column.setOutputName("YBZGY");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保人联系地址");
		column.setColumnName("holder_addr");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setiOutputOrder(16);
		column.setOutputName("QD");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保人联系电话");
		column.setColumnName("holder_phone");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setiOutputOrder(17);
		column.setOutputName("QD");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保人手机");
		column.setColumnName("holder_mobile");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setiOutputOrder(18);
		column.setOutputName("QD");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("服务员业务号");
		column.setColumnName("server_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(19);
		column.setOutputName("QD");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("服务员姓名");
		column.setColumnName("server_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(20);
		column.setOutputName("QD");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("服务人员联系电话");
		column.setColumnName("server_phone");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(21);
		column.setOutputName("QD");
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
