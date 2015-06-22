package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class CheckColumn {

	private static List<ColumnItem> standardColumns;
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("抽检批次号");
		column.setColumnName("check_batch");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(1);
		column.setOutputName("CJPCH");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保单号");
		column.setColumnName("form_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(2);
		column.setOutputName("TBDH");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保单号码");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(3);
		column.setOutputName("BDH");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("网点类型");
		column.setColumnName("net_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(4);
		column.setOutputName("WDLX");
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("险种名称");
		column.setColumnName("prd_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(5);
		column.setOutputName("XZMC");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("缴费方式");
		column.setColumnName("pay_method");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(6);
		column.setOutputName("JFFS");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("是否整改");
		column.setColumnName("need_fix");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(7);
		column.setOutputName("SFZG");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("关键信息");
		column.setColumnName("key_info");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(8);
		column.setOutputName("GJXX");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("重要信息");
		column.setColumnName("importance_info");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(9);
		column.setOutputName("ZYXX");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("其他信息");
		column.setColumnName("else_info");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(10);
		column.setOutputName("QTXX");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("单证是否使用错误");
		column.setColumnName("doc_error");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(11);
		column.setOutputName("DZSFSYCW");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保资料是否缺失");
		column.setColumnName("doc_miss");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(12);
		column.setOutputName("TBZLSFQS");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("抽检人");
		column.setColumnName("checker");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(13);
		column.setOutputName("CJR");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("网点名称");
		column.setColumnName("net_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(14);
		column.setOutputName("WDMC");
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
