package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class UnderWriteDtlColumn {

private static List<ColumnItem> standardColumns;
	
	public static String KEY_ROW = "核保完成日期";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		//所属地市机构 投保单号 保单号 保单承保日 被保人年龄 被保险人职业 被保险人性别 险种名称 
		//保险费 基本保额 交费方式 缴费期数 转核原因 问题件数 体检件数 承保时效 核保结论 非实时保单录入日期 核心业务录入日期 复核日期 保单进入人核日期 
		//体检通知书下发日期 体检通知书回销日期 契调通知书下发日期 契调通知书回销日期 核保通知书下发日期 核保通知书回销日期 核保完成结束日期 保单签单日期 保单打印日期 客户签收日期 回执回销日期 客户填单日期
		ColumnItem column = new ColumnItem();
		column.setDisplayName("保单号");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(16);
		column.setOutputName("QD");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("签收日期");
		column.setColumnName("client_receive_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("回单日期");
		column.setColumnName("sign_input_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
