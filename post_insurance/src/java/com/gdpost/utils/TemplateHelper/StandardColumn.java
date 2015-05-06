package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class StandardColumn {

	private static List<ColumnItem> standardColumns;
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("保单号");
		column.setColumnName("policyNo");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedEncrypt(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(1);
		column.setOutputName("YDM");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("月份");
		column.setColumnName("ny");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedEncrypt(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(2);
		column.setOutputName("YF");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("品名");
		column.setColumnName("pm");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedEncrypt(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(3);
		column.setOutputName("YSPM");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("厂家");
		column.setColumnName("cj");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(4);
		column.setOutputName("YSCJ");
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("规格");
		column.setColumnName("gg");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(5);
		column.setOutputName("YSGG");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("单价");
		column.setColumnName("dj");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(6);
		column.setOutputName("DJ");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("销售量");
		column.setColumnName("xsl");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(7);
		column.setOutputName("XSL");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("销售额");
		column.setColumnName("xse");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(8);
		column.setOutputName("XSE");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("月初库存");
		column.setColumnName("yckc");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(9);
		column.setOutputName("QC");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("进货数量");
		column.setColumnName("jhl");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(10);
		column.setOutputName("JH");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("月末库存");
		column.setColumnName("ymkc");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(11);
		column.setOutputName("CM");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("药店编码");
		column.setColumnName("ydbm");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(12);
		column.setOutputName("YDBM");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("药品编码");
		column.setColumnName("ypbm");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(13);
		column.setOutputName("SPBH");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("单位");
		column.setColumnName("dw");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(14);
		column.setOutputName("DW");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("品类大类");
		column.setColumnName("pldl");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("品类");
		column.setColumnName("pl");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("品类子类");
		column.setColumnName("plzl");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("成本价");
		column.setColumnName("cbj");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("毛利率");
		column.setColumnName("mll");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("连锁");
		column.setColumnName("ls");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("城市");
		column.setColumnName("cs");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("条码");
		column.setColumnName("tm");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("医保/非医保");
		column.setColumnName("yb");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("处方药");
		column.setColumnName("otc");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
