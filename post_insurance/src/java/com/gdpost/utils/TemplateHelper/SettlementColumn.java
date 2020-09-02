package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class SettlementColumn {

private static List<ColumnItem> standardColumns;
	
	public static String KEY_ROW = "赔案号";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		//机构代码	机构名称	赔案号	出险人	报案人	报案人联系方式	事故日期	出险日期	案件状态	立案时间	立案人	审核时间	审核人	复核时间	复核人	金额 
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("机构代码");
		column.setColumnName("organ_code");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
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
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("出险人");
		column.setColumnName("case_man");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("报案人");
		column.setColumnName("reporter");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("报案人联系方式");
		column.setColumnName("reporter_phone");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("事故日期");
		column.setColumnName("settle_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("出险日期");
		column.setColumnName("case_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("案件状态");
		column.setColumnName("case_status");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		return(standardColumns);
	}
}
