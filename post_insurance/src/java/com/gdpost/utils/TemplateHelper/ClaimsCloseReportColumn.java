package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class ClaimsCloseReportColumn {

private static List<ColumnItem> standardColumns;
	
	public static String KEY_ROW = "理赔结论";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		//机构代码	机构名称	赔案号	保单号	保单所在地	险种代码	险种名称	客户号	投保人	被保险人	性别	年龄	职业	出险类型	理赔类型	申请人	申请人电话	报案日期	出险日期	立案日期	理赔结论	总费用	理算金额	给付金额	拒赔金额	结案日期 
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("机构代码");
		column.setColumnName("organ_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("机构名称");
		column.setColumnName("organ_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("赔案号");
		column.setColumnName("claims_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("保单号");
		column.setColumnName("policy_nos");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("保单所在地");
		column.setColumnName("area_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("险种代码");
		column.setColumnName("prod_code");
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
		column.setDisplayName("客户号");
		column.setColumnName("client_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("投保人");
		column.setColumnName("holder");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("被保险人");
		column.setColumnName("insured");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("性别");
		column.setColumnName("fm");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("年龄");
		column.setColumnName("age");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("职业");
		column.setColumnName("job");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("出险类型");
		column.setColumnName("settle_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("理赔类型");
		column.setColumnName("claims_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("申请人");
		column.setColumnName("req_man");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("申请人电话");
		column.setColumnName("req_phone");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("报案日期");
		column.setColumnName("report_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("出险日期");
		column.setColumnName("settle_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("立案日期");
		column.setColumnName("case_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("理赔结论");
		column.setColumnName("claims_rst");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("总费用");
		column.setColumnName("total_fee");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("理算金额");
		column.setColumnName("claims_fee");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("给付金额");
		column.setColumnName("give_fee");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("拒赔金额");
		column.setColumnName("reject_fee");
		column.setColumnType(ColumnType.numeric);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("结案日期");
		column.setColumnName("close_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		return(standardColumns);
	}
}
