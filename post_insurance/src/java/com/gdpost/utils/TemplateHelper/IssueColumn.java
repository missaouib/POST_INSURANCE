package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class IssueColumn {

	private static List<ColumnItem> standardColumns;
	
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
		column.setNeedOutput(true);
		column.setiOutputOrder(1);
		column.setOutputName("JGDM");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("机构名称");
		column.setColumnName("organ_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("回访人");
		column.setColumnName("call_man");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(2);
		column.setOutputName("HFR");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("工单流水号");
		column.setColumnName("issue_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(3);
		column.setOutputName("GDLSH");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("保单号");
		column.setColumnName("policy_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		column.setNeedOutput(true);
		column.setiOutputOrder(4);
		column.setOutputName("BDH");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("工单类别");
		column.setColumnName("issue_desc");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(5);
		column.setOutputName("GDLB");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("工单子类");
		column.setColumnName("issue_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(6);
		column.setOutputName("GDZL");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("工单内容");
		column.setColumnName("issue_content");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(7);
		column.setOutputName("JBBE");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("处理结果");
		column.setColumnName("result");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(8);
		column.setOutputName("CLJG");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("工单状态");
		column.setColumnName("status");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(9);
		column.setOutputName("GDZT");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("证件号码");
		column.setColumnName("id_card");
		column.setColumnType(ColumnType.string);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("通信地址");
		column.setColumnName("addr");
		column.setColumnType(ColumnType.string);
		column.setNeedEncrypt(true);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("待处理时间");
		column.setColumnName("should_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(10);
		column.setOutputName("CBRQ");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("结案时间");
		column.setColumnName("finish_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setNeedOutput(true);
		column.setiOutputOrder(11);
		column.setOutputName("JASJ");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("银行网点代码");
		column.setColumnName("bank_code");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setiOutputOrder(12);
		column.setOutputName("YHWDDM");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("银行网点名称");
		column.setColumnName("bank_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(13);
		column.setOutputName("YHWDMC");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("联系电话");
		column.setColumnName("holder_phone");
		column.setColumnType(ColumnType.string);
		column.setNeedEncrypt(true);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("手机号码");
		column.setColumnName("holder_mobile");
		column.setColumnType(ColumnType.string);
		column.setNeedEncrypt(true);
		column.setNullable(false);
		standardColumns.add(column);
		
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
		column.setiOutputOrder(15);
		column.setOutputName("HFRQ");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("不成功件生成时间");
		column.setColumnName("issue_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(16);
		column.setOutputName("BCGJSCSJ");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("重置次数");
		column.setColumnName("reset_num");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		column.setiOutputOrder(16);
		column.setOutputName("CZCS");
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("复访标记");
		column.setColumnName("recall_flag");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		column.setiOutputOrder(16);
		column.setOutputName("FGBZ");
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
