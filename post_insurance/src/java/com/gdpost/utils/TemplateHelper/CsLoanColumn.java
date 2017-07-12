package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class CsLoanColumn {

private static List<ColumnItem> standardColumns;
	
	public static String KEY_ROW = "约定还款日期";
	//管理机构	保单号码	投保人姓名	投保人性别	险种名称		出单网点	借款日期	借款金额	约定还款日期	实际还款日期	还款金额	免息金额	免息原因	保单状态	联系方式
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		//保全受理号 保单号 	保单所属机构 网点名称 	渠道 	操作机构代码 投保人姓名 	被保险人姓名 	保全复核通过日期 项目编码 金额 申请方式 
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("管理机构");
		column.setColumnName("organ_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保单号码");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保人姓名");
		column.setColumnName("holder");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保人性别");
		column.setColumnName("holder_sexy");
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
		column.setDisplayName("出单网点");
		column.setColumnName("bank_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("借款日期");
		column.setColumnName("loan_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("借款金额");
		column.setColumnName("loan_fee");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("约定还款日期");
		column.setColumnName("should_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		/*
		column = new ColumnItem();
		column.setDisplayName("实际还款日期");
		column.setColumnName("real_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		*/
		
		column = new ColumnItem();
		column.setDisplayName("还款金额");
		column.setColumnName("back_fee");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("免息金额");
		column.setColumnName("free_fee");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("免息原因");
		column.setColumnName("free_reason");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保单状态");
		column.setColumnName("status");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("联系方式");
		column.setColumnName("phone");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
