package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class InquireColumn {

	private static List<ColumnItem> standardColumns;
	public static String KEY_ROW = "事件编号";
	
	public static List<ColumnItem> getStandardColumns() {
		if(standardColumns != null) {
			return(standardColumns);
		}
		
		standardColumns = new ArrayList<ColumnItem>();
		
		ColumnItem column = new ColumnItem();
		column.setDisplayName("事件编号");
		column.setColumnName("inquire_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("事件类型");
		column.setColumnName("inquire_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("事件子类型");
		column.setColumnName("inquire_subtype");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

//		column = new ColumnItem();
//		column.setDisplayName("事件状态");
//		column.setColumnName("inquire_status");
//		column.setColumnType(ColumnType.string);
//		column.setNullable(false);
//		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("事件描述");
		column.setColumnName(" inquire_desc");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("事件备注");
		column.setColumnName(" inquire_remark");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("事件结果");
		column.setColumnName("inquire_rst");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("下级处理部门");
		column.setColumnName("deal_depart");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("下级处理人");
		column.setColumnName("deal_man");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("任务类型");
		column.setColumnName("task_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("完成时间");
		column.setColumnName("finish_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(true);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("个险保单号");
		column.setColumnName("policy_nos");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("个险管理机构");
		column.setColumnName("organ_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("个险销售网点");
		column.setColumnName("net_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("个险投保人姓名");
		column.setColumnName("holder");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("个险投保人联系电话");
		column.setColumnName("holder_phone");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("个险投保人手机");
		column.setColumnName("holder_mobile");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("个险生效日期");
		column.setColumnName("policy_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("团个保单号");
		column.setColumnName("gpolicy_no");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("团个管理机构");
		column.setColumnName("gorgan_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("团个销售网点");
		column.setColumnName("gnet_name");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("团个被保人姓名");
		column.setColumnName("ginsured");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("团个被保人联系电话");
		column.setColumnName("ginsured_phone");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("团个被保人手机");
		column.setColumnName("ginsured_mobile");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("团个生效日期");
		column.setColumnName("gpolicy_date");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("客户姓名");
		column.setColumnName("client");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("客户电话1");
		column.setColumnName("client_phone1");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("客户电话2");
		column.setColumnName("client_phone2");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("性别");
		column.setColumnName("client_sexy");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("生日");
		column.setColumnName("client_brd");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("年龄");
		column.setColumnName("client_age");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("证件类型");
		column.setColumnName("client_card_type");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("证件号码");
		column.setColumnName("client_card_num");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("国籍");
		column.setColumnName("client_stateless");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("民族");
		column.setColumnName("client_mz");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("婚否");
		column.setColumnName("client_fm");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("所属省份");
		column.setColumnName("client_prov");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("所属城市");
		column.setColumnName("client_city");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("所属城区");
		column.setColumnName("client_area");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("所属直辖市");
		column.setColumnName("client_dcity");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);

		column = new ColumnItem();
		column.setDisplayName("邮政编码");
		column.setColumnName("client_postcode");
		column.setColumnType(ColumnType.string);
		column.setNullable(false);
		standardColumns.add(column);
		
		return(standardColumns);
	}
}
