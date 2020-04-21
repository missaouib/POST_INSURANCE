package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class CallRatioColumn {

	private static List<ColumnItem> standardColumns;
	public static String KEY_ROW = "成功率";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		
		column = new ColumnItem();
		column.setDisplayName("市分代码");
		column.setColumnName("city_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("市分名称");
		column.setColumnName("city_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("县分代码");
		column.setColumnName("area_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("县分名称");
		column.setColumnName("area_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("网点代码");
		column.setColumnName("net_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("网点名称");
		column.setColumnName("net_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("实际回访量");
		column.setColumnName("real_count");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("标准件");
		column.setColumnName("stand_count");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("犹豫期内电话回访成功量");
		column.setColumnName("call_intime");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("犹豫期内电话回访成功率");
		column.setColumnName("call_ratio");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-已上门回访-未成功面见投保人");
		column.setColumnName("fail_door");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-信函已发-未回函");
		column.setColumnName("fail_letter");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-身份核实问题");
		column.setColumnName("fail_confirm");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-其他");
		column.setColumnName("fail_else");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-因涉及投诉无法完成回访");
		column.setColumnName("fail_report");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-身体原因无法进行回访");
		column.setColumnName("fail_complaint");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-销售误导未完成回访");
		column.setColumnName("fail_sale");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-退保未完成回访");
		column.setColumnName("fail_ct");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-客户不配合，无法完成身份核实");
		column.setColumnName("fail_fit");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-客户称未办理");
		column.setColumnName("fail_notdeal");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-客户挂断");
		column.setColumnName("fail_cut");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-无法接通");
		column.setColumnName("fail_noanswer");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-方言");
		column.setColumnName("fail_lenguage");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-无法联系客户");
		column.setColumnName("fail_link");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-电话错误");
		column.setColumnName("fail_err");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件-无人接听");
		column.setColumnName("fail_nobody");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("不成功件合计");
		column.setColumnName("fail_count");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件-其他");
		column.setColumnName("issue_else");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件-健康财务状况不如实告知");
		column.setColumnName("issue_tell");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件-未抄写风险提示语");
		column.setColumnName("issue_write");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件-意向退保");
		column.setColumnName("issue_ct");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件-投保人代签名");
		column.setColumnName("issue_sign1");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件-被保人代签名");
		column.setColumnName("issue_sign2");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件-条款解释不清-存款");
		column.setColumnName("issue_saving");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件-条款解释不清-产品收益");
		column.setColumnName("issue_profit");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件-条款解释不清-犹豫期");
		column.setColumnName("issue_period");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件-条款解释不清-保险期间");
		column.setColumnName("issue_duration");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件-条款解释不清-保险责任");
		column.setColumnName("issue_duty");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件-双录非本人意愿");
		column.setColumnName("issue_intend");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件-未双录");
		column.setColumnName("issue_record");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件-保单未送达");
		column.setColumnName("issue_delivery");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件合计");
		column.setColumnName("issue_count");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("拒访件合计");
		column.setColumnName("reject_count");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("退保件合计");
		column.setColumnName("ct_count");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("预约件合计");
		column.setColumnName("order_count");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("待回访合计");
		column.setColumnName("wait_count");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("成功率");
		column.setColumnName("success_ratio");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("附件上传率");
		column.setColumnName("attached_ratio");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("录音上传率");
		column.setColumnName("record_ratio");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件占比");
		column.setColumnName("issue_percent");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		/**/
		column = new ColumnItem();
		column.setDisplayName("开始时间");
		column.setColumnName("s_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("结束时间");
		column.setColumnName("e_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		
		return(standardColumns);
	}
}
