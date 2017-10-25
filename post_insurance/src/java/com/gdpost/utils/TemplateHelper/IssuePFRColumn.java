package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class IssuePFRColumn {

	private static List<ColumnItem> standardColumns;
	public static String KEY_ROW = "事件编号";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		
		column = new ColumnItem();
		column.setDisplayName("事件编号");
		column.setColumnName("issue_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		/*
		column.setDisplayName("机构代码");
		column.setColumnName("organ_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		*/
		
		column = new ColumnItem();
		column.setDisplayName("任务类型");
		column.setColumnName("task_type");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("回访结果分类");
		column.setColumnName("issue_desc");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("回访结果");
		column.setColumnName("issue_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("工单内容");
		column.setColumnName("issue_content");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("事件备注");
		column.setColumnName("result");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保单号");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("所属机构");
		column.setColumnName("issue_org");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("客户编号");
		column.setColumnName("client_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("客户姓名");
		column.setColumnName("client_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("性别");
		column.setColumnName("client_sexy");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("证件号码");
		column.setColumnName("id_card");
		column.setColumnType(ColumnType.string);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("联系电话");
		column.setColumnName("holder_phone");
		column.setColumnType(ColumnType.string);
		column.setNeedEncrypt(true);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("移动电话");
		column.setColumnName("holder_mobile");
		column.setColumnType(ColumnType.string);
		column.setNeedEncrypt(true);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("省");
		column.setColumnName("issue_prov");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("市");
		column.setColumnName("issue_city");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("县");
		column.setColumnName("issue_area");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("详细地址");
		column.setColumnName("addr");
		column.setColumnType(ColumnType.string);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("处理部门");
		column.setColumnName("deal_dep");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("处理人");
		column.setColumnName("call_man");
		column.setColumnType(ColumnType.string);
		standardColumns.add(column);
		/*
		column = new ColumnItem();
		column.setDisplayName("自动催办状态");
		column.setColumnName("status");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		*/
		column = new ColumnItem();
		column.setDisplayName("手动催办状态");
		column.setColumnName("manual_status");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("创建时间");
		column.setColumnName("ready_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("回访方式");
		column.setColumnName("call_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("修改回访结果");
		column.setColumnName("fix_call_rst");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("签收日期");
		column.setColumnName("bill_back_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		/*
		column = new ColumnItem();
		column.setDisplayName("结案时间");
		column.setColumnName("finish_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("银行网点代码");
		column.setColumnName("bank_code");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);
		*/
		column = new ColumnItem();
		column.setDisplayName("销售网点");
		column.setColumnName("bank_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		/*
		column = new ColumnItem();
		column.setDisplayName("应访日期");
		column.setColumnName("should_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(14);
		column.setOutputName("YFRQ");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("回访日期");
		column.setColumnName("call_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		*/
		column = new ColumnItem();
		column.setDisplayName("主险保险年期");
		column.setColumnName("policy_term");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("主险缴费方式");
		column.setColumnName("policy_fee_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("主险缴费年期");
		column.setColumnName("policy_fee_year");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		/*
		column = new ColumnItem();
		column.setDisplayName("重置次数");
		column.setColumnName("reset_num");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("复访标记");
		column.setColumnName("recall_flag");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		*/
		column = new ColumnItem();
		column.setDisplayName("附加险名称");
		column.setColumnName("attr_prod");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("附加险保费");
		column.setColumnName("attr_fee");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setStaticValue("0");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("附加险保险年期");
		column.setColumnName("attr_year");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("附加险缴费方式");
		column.setColumnName("attr_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("附加险缴费年期");
		column.setColumnName("attr_fee_year");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
