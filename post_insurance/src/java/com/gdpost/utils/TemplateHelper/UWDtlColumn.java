package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class UWDtlColumn {

private static List<ColumnItem> standardColumns;
	
	public static String KEY_ROW = "投保单号";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		//手工单流转时效统计表
		// 投保单号，保险单号，险种名称，保险费，交费方式，交费期数，是否人工核保，转核原因，核保结论，核保意见，客户填单日期，非实时保单录入日期，核心业务系统录入日期，复核完成日期，保单进入人核日期，
		//体检下发日期，体检回销日期，契约调查下发日期，契调回销日期，核保修改事项索要资料通知书下发日期，核保修改事项索要资料通知书回销日期，核保完成日期，是否返回复核，保单签单日期，保单打印日期，客户签收日期，回执回销日期

		//总部的：投保单号, 保单号, 保单承保日, 被保人年龄, 被保险人职业, 被保险人性别, 险种名称, 保险费, 基本保额, 交费方式, 缴费期数, 转核原因, 问题件数, 体检件数, 承保时效, 核保结论, 
		//非实时保单录入日期, 核心业务录入日期, 复核日期, 保单进入人核, 体检通知书下发日期, 体检通知书回销日期, 契调通知书下发日期, 契调通知书回销日期, 核保通知书下, 核保通知书回, 核保完成日期, 保单签单日期, 保单打印日期, 客户签收日期, 回执回销日期, 客户填单日期


		ColumnItem column = new ColumnItem();
		
		column = new ColumnItem();
		column.setDisplayName("市县名称");
		column.setColumnName("organ_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("投保单号");
		column.setColumnName("form_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保险单号");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("险种名称");
		column.setColumnName("prd_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保险费");
		column.setColumnName("policy_fee");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("交费期数");
		column.setColumnName("perm");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("交费方式");
		column.setColumnName("fee_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("转核原因");
		column.setColumnName("underwrite_reason");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("非实时保单录入日期");
		column.setColumnName("ybt_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("核心业务系统录入日期");
		column.setColumnName("sys_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("复核完成日期");
		column.setColumnName("check_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("体检下发日期");
		column.setColumnName("body_check_date1");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("体检回销日期");
		column.setColumnName("body_check_date2");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("契调通知书下发日期");
		column.setColumnName("deal_check_date1");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("契调回销日期");
		column.setColumnName("deal_check_date2");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("核保完成日期");
		column.setColumnName("hb_end_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保单打印日期");
		column.setColumnName("prov_send_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保单签单日期");
		column.setColumnName("sign_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("客户签收日期");
		column.setColumnName("client_receive_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("回执回销日期");
		column.setColumnName("bill_back_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("客户填单日期");
		column.setColumnName("form_write_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
