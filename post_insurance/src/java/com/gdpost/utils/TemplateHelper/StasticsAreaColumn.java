package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class StasticsAreaColumn {

	private static List<ColumnItem> standardColumns;
	public static String KEY_ROW = "总分排名";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("机构号");
		column.setColumnName("organ_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("机构名称");
		column.setColumnName("organ_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("所属月度");
		column.setColumnName("mth");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("新契约综合合格率");
		column.setColumnName("qyhgl_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("新契约综合合格率得分");
		column.setColumnName("qyhgl_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("犹豫期内电话回访成功率");
		column.setColumnName("kfhfcgl_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("犹豫期内电话回访成功率得分");
		column.setColumnName("kfhfcgl_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("销售类问题件占比");
		column.setColumnName("wtj_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("销售类问题件占比得分");
		column.setColumnName("wtj_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		/*
		column = new ColumnItem();
		column.setDisplayName("13个月保费继续率");
		column.setColumnName("xq13j_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("13个月保费继续率得分");
		column.setColumnName("xq13j_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("25个月保费继续率");
		column.setColumnName("xq25j_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("25个月保费继续率得分");
		column.setColumnName("xq25j_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("期交新单当年退保率");
		column.setColumnName("qjxdtbl_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("期交新单当年退保率得分");
		column.setColumnName("qjxdtbl_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);
		
		*/
		
		column = new ColumnItem();
		column.setDisplayName("3年期交保费综合丢失率");
		column.setColumnName("xq3ylost_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("3年期交保费综合丢失率得分");
		column.setColumnName("xq3ylost_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("保单品质维度得分");
		column.setColumnName("billvalue_total_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("人核件全流程时效");
		column.setColumnName("uw_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("人核件全流程时效得分");
		column.setColumnName("uw_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("出险支付时效");
		column.setColumnName("lppay_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("出险支付时效得分");
		column.setColumnName("lppay_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("续期业务系统服务工单处理率");
		column.setColumnName("xqissue_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("续期业务系统服务工单处理率得分");
		column.setColumnName("xqissue_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("问题件咨询件处理时效");
		column.setColumnName("issuetime_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("问题件咨询件处理时效得分");
		column.setColumnName("issuetime_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("失效保单件数清理率");
		column.setColumnName("invalid_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("失效保单件数清理率得分");
		column.setColumnName("invalid_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("客户服务维度得分");
		column.setColumnName("kf_total_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("保全录入修改率");
		column.setColumnName("input_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("保全录入修改率得分");
		column.setColumnName("input_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("团险契约保全业务扫描件退回率");
		column.setColumnName("gwtj_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("团险契约保全业务扫描件退回率得分");
		column.setColumnName("gwtj_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		/*
		column = new ColumnItem();
		column.setDisplayName("简易险理赔资料准确率");
		column.setColumnName("lpjyx_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("简易险理赔资料准确率得分");
		column.setColumnName("lpjyx_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);
		*/
		
		column = new ColumnItem();
		column.setDisplayName("作业质量维度得分");
		column.setColumnName("job_total_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("质押借款逾期率");
		column.setColumnName("bqjyjk_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("质押借款逾期率得分");
		column.setColumnName("bqjyjk_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("客户信息真实性综合合格率");
		column.setColumnName("truth_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("客户信息真实性综合合格率得分");
		column.setColumnName("truth_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("风险事件");
		column.setColumnName("risk_value");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("风险事件得分");
		column.setColumnName("risk_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);
		
		column = new ColumnItem();
		column.setDisplayName("风险管控维度得分");
		column.setColumnName("risk_total_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("总分");
		column.setColumnName("total_score");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("总分排名");
		column.setColumnName("city_sort");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(false);
		standardColumns.add(column);

		return(standardColumns);
	}
}
