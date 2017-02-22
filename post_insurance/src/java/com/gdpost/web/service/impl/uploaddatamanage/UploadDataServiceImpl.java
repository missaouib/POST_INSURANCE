package com.gdpost.web.service.impl.uploaddatamanage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.gdpost.utils.MyException;
import com.gdpost.utils.StringUtil;
import com.gdpost.utils.TemplateHelper.CallFailCityMiniListColumn;
import com.gdpost.utils.TemplateHelper.CallFailCloseListColumn;
import com.gdpost.utils.TemplateHelper.CallFailDoorBackListColumn;
import com.gdpost.utils.TemplateHelper.CallFailHQListColumn;
import com.gdpost.utils.TemplateHelper.CallFailHQMiniListColumn;
import com.gdpost.utils.TemplateHelper.CallFailMailBackListColumn;
import com.gdpost.utils.TemplateHelper.CallFailMailListColumn;
import com.gdpost.utils.TemplateHelper.CallFailMailSuccessListColumn;
import com.gdpost.utils.TemplateHelper.CallFailNeedDoorListColumn;
import com.gdpost.utils.TemplateHelper.CheckColumn;
import com.gdpost.utils.TemplateHelper.ColumnItem;
import com.gdpost.utils.TemplateHelper.ColumnType;
import com.gdpost.utils.TemplateHelper.ConversationReqColumn;
import com.gdpost.utils.TemplateHelper.CsReportColumn;
import com.gdpost.utils.TemplateHelper.IssueColumn;
import com.gdpost.utils.TemplateHelper.IssuePFRColumn;
import com.gdpost.utils.TemplateHelper.PayFailListColumn;
import com.gdpost.utils.TemplateHelper.PolicyBackDateColumn;
import com.gdpost.utils.TemplateHelper.PolicyColumn;
import com.gdpost.utils.TemplateHelper.PolicyDtlColumn;
import com.gdpost.utils.TemplateHelper.PolicySentDataColumn;
import com.gdpost.utils.TemplateHelper.PolicyUnderWriteColumn;
import com.gdpost.utils.TemplateHelper.RenewedCityListColumn;
import com.gdpost.utils.TemplateHelper.RenewedColumn;
import com.gdpost.utils.TemplateHelper.RenewedHQListColumn;
import com.gdpost.utils.TemplateHelper.RenewedProvListColumn;
import com.gdpost.utils.TemplateHelper.RenewedStatusColumn;
import com.gdpost.utils.TemplateHelper.Template.FileTemplate;
import com.gdpost.utils.TemplateHelper.UnderWriteSentDataColumn;
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.dao.uploaddatamanage.UploadDataDAO;
import com.gdpost.web.entity.main.PayFailList;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.service.uploaddatamanage.UploadDataService;
import com.gdpost.web.util.DoRst;
import com.gdpost.web.util.StatusDefine.FEE_FAIL_STATUS;
import com.gdpost.web.util.StatusDefine.STATUS;
import com.gdpost.web.util.StatusDefine.XQ_STATUS;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

import System.Data.DataRow;
import System.Data.DataTable;

@Service
@Transactional
public class UploadDataServiceImpl implements UploadDataService{
	private static final Logger log = LoggerFactory.getLogger(UploadDataServiceImpl.class);
	
	@Autowired
	private UploadDataDAO uploadDataDAO;
	
	@Override
	public List<Policy> findAll(Page page) {
		org.springframework.data.domain.Page<Policy> springDataPage = uploadDataDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<Policy> findByExample(
			Specification<Policy> specification, Page page) {
		org.springframework.data.domain.Page<Policy> springDataPage = uploadDataDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@SuppressWarnings("resource")
	@Override
	public DoRst importData(FileTemplate ft, HttpServletRequest request, DataTable dt, long member_id, int ny) {		
		log.debug("---------   into import data");
		DoRst dr = new DoRst();
		dr.setNum(dt.Rows.size());
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		try {
			Object objDataSource = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");
			DruidDataSource dataSource = (DruidDataSource)objDataSource;
			connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
			statement = (com.mysql.jdbc.Statement)connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			dr.setFlag(false);
			dr.setMsg(e.getMessage());
			return dr;
		}
		
		// AES Encrypt key
		String strKey = com.gdpost.web.MySQLAESKey.AESKey;

		List<ColumnItem> standardColumns = null;
		String strStatementText = null;
		String strEncrypt = "";
		String sql1 = null;
		String sql2 = null;
		String sql3 = null;
		String sql4 = null;
		String sql5 = null;
		switch(ft) {
		case Policy:
			standardColumns = PolicyColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_policy character set utf8 (";
			sql3 = "update t_under_write as uw inner join t_policy tp on uw.form_no=tp.form_no set uw.policy_no=tp.policy_no,uw.sign_date=tp.policy_date where uw.policy_no is null;";
			sql2 = "update t_policy set attached_flag = 1 where prod_name like \"%附加%\";";
			sql1 = "delete from t_policy where form_no is null;";
	        break;
		case PolicyIngor:
			standardColumns = PolicyColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_policy character set utf8 (";
			sql3 = "update t_under_write as uw inner join t_policy tp on uw.form_no=tp.form_no set uw.policy_no=tp.policy_no,uw.sign_date=tp.policy_date where uw.policy_no is null;";
			sql2 = "update t_policy set attached_flag = 1 where prod_name like \"%附加%\";";
			sql1 = "delete from t_policy where form_no is null;";
			break;
		case PolicyDtl:
			standardColumns = PolicyDtlColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_policy_dtl character set utf8 (";
			sql1 = "update t_policy tp, t_policy_dtl tpd, t_staff ts set tp.staff_flag=true where tp.staff_flag=0 and tp.policy_no=tpd.policy_no and tpd.holder_card_num=ts.id_card";
			break;
		case Issue:
			standardColumns = IssueColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_issue character set utf8 (";
			//sql1 = "update t_call_fail_list set finish_date=\"2015-01-01 00:00:00\" where finish_date<\"2000-11-01 09:00:00\";";
			break;
		case IssuePFR:
			standardColumns = IssuePFRColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_issue character set utf8 (";
			sql1 = "update t_issue set should_date=ready_date where should_date is null or should_date<\"2000-11-01 09:00:00\";";
			break;
		case CallFail:
			standardColumns = IssueColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_call_fail_list character set utf8 (";
			sql1 = "update t_call_fail_list set finish_date=\"2015-01-01 00:00:00\" where finish_date<\"2000-11-01 09:00:00\";";
			break;
		case CallFailPFR:
			standardColumns = IssuePFRColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_call_fail_list character set utf8 (";
			//sql1 = "update t_call_fail_list set finish_date=\"2015-01-01 00:00:00\" where finish_date<\"2000-11-01 09:00:00\";";
			break;
		case CallFailStatus:
			standardColumns = CallFailHQListColumn.getStandardColumns();
			return dr;
		case MiniCallFailStatus:
			return dr;
		case CallFailMailStatus:
			return dr;
		case CallFailCityStatus:
			return dr;
		case Renewed:
			standardColumns = RenewedColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_renewed_list character set utf8 (";
			break;
		case RenewedStatus:
			standardColumns = RenewedStatusColumn.getStandardColumns();
			return dr;
		case RenewedHQList:
			standardColumns = RenewedHQListColumn.getStandardColumns();
			return dr;
		/*
		case RemitMoney:
			standardColumns = RemitMoneyColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_remit_money_list character set utf8 (";
			break;
			*/
		case CheckWrite:
			standardColumns = CheckColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_check_write character set utf8 (fix_status, ";
			break;
		case CheckRecord:
			standardColumns = CheckColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_check_record character set utf8 (fix_status, ";
			break;
		case PayToFailList:
			sql1 = "delete from t_pay_fail_list where pay_type=" + PayFailList.PAY_TO + " and operate_time<CURRENT_DATE and fee_type<>'保全受理号'; ";
			standardColumns = PayFailListColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_pay_fail_list character set utf8 (pay_type, status, ";
			break;
		case PayFromFailList:
			sql1 = "delete from t_pay_fail_list where pay_type=" + PayFailList.PAY_FROM + " and operate_time<CURRENT_DATE and fee_type<>'保全受理号';";
			standardColumns = PayFailListColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_pay_fail_list character set utf8 (pay_type, status, ";
			break;
		case PaySuccessList:
			standardColumns = PayFailListColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_pay_success_list character set utf8 (pay_type, status, ";
			sql1 = "update t_pay_fail_list set status='CloseStatus' where rel_no in (select rel_no from t_pay_success_list);";
			sql2 = "update t_renewed_list set fee_status='交费成功' where fee_status<>\"交费成功\" and policy_no in (select rel_no from t_pay_success_list where pay_type=2 and policy_fee=money);";
			sql3 = "update t_renewed_list t0 set t0.fee_status='交费成功' where t0.fee_status<>\"交费成功\" "
					+ "and t0.policy_no in (select t2.policy_no from t_pay_success_list t1, t_cs_report t2 "
					+ "where t1.pay_type=2 and t0.policy_fee=t1.money and t1.rel_no=t2.cs_no and t2.cs_code='RE');";
			break;
		case UnderWriteSentData:
			standardColumns = PolicySentDataColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_policy_reprint_dtl character set utf8 (";
			break;
		case ConversationReq:
			standardColumns = ConversationReqColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_conservation_req character set utf8 (";
			break;
		case ConversationReport:
			standardColumns = CsReportColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_cs_report character set utf8 (";
			sql1 = "update t_renewed_list t0 set t0.fee_status='交费成功' where t0.fee_status<>\"交费成功\" "
					+ "and t0.policy_no in (select t2.policy_no from t_pay_success_list t1, t_cs_report t2 "
					+ "where t1.pay_type=2 and t0.policy_fee=t1.money and t1.rel_no=t2.cs_no and t2.cs_code='RE');";
			sql2 = "update t_call_fail_list t1, t_cs_report t2 set t1.status=\"已退保\" where t1.policy_no=t2.policy_no and t2.cs_code=\"CT\";";
			sql3 = "update t_cs_report tsr, t_policy_dtl tpd, t_staff ts set tsr.staff_flag=true where tsr.staff_flag=0 and tsr.policy_no=tpd.policy_no and tpd.holder_card_num=ts.id_card;";
			sql4 = "update t_policy tp, t_cs_report tcr set tp.cs_flag=1 where tp.policy_no=tcr.policy_no and tcr.cs_code=\"CT\" and datediff(tcr.cs_date,tp.policy_date)<=15;";
			sql5 = "update t_policy tp, t_cs_report tcr set tp.cs_flag=2 where tp.policy_no=tcr.policy_no and tcr.cs_code=\"CT\" and datediff(tcr.cs_date,tp.policy_date)>15;";
			break;
			default:
				log.warn("------------reach the default FileTemplate?? oh no!!");
		}
		
		for(ColumnItem item : standardColumns) {
    		if(item.isNeedEncrypt()) {
    			if(strEncrypt.equals("")) {
    				strEncrypt = "SET " + item.getColumnName() + "=HEX(AES_Encrypt(" + item.getColumnName() + ",'" + strKey + "'))";
    			} else {
    				strEncrypt += "," + item.getColumnName() + "=HEX(AES_Encrypt(" + item.getColumnName() + ",'" + strKey + "'))";
    			}
    		}
        	
        	strStatementText += item.getColumnName() + ",";
        }

        // member_id, ny,最后补上两列数据
        strStatementText += "operate_id) ";
        strStatementText += strEncrypt + ";";
        log.debug("--------------" + strStatementText);
        
        StringBuilder builder = new StringBuilder();
        String cell = null;
        dr.setNum(dt.Rows.size());
        for (DataRow row : dt.Rows) {
        	// 从处理后的行中，取出标准列数据
        	//log.debug("--------------" + row.toString());
        	if(ft.name().equals(FileTemplate.CheckWrite.name()) || ft.name().equals(FileTemplate.CheckRecord.name())) {
    			if(row.getValue(new CheckColumn().getCheckColumn()).equals("要整改")) {
    				builder.append(STATUS.NewStatus.name());
    	            builder.append('\t');
    			} else {
    	            builder.append('\t');
    			}
    		} else if(ft.name().equals(FileTemplate.PayToFailList.name())) {
    			builder.append(PayFailList.PAY_TO);
	            builder.append('\t');
	            builder.append(FEE_FAIL_STATUS.NewStatus.name());
	            builder.append('\t');
    		} else if(ft.name().equals(FileTemplate.PaySuccessList.name())) {
    			builder.append(PayFailList.PAY_TO);
	            builder.append('\t');
	            builder.append(FEE_FAIL_STATUS.NewStatus.name());
	            builder.append('\t');
    		} else if(ft.name().equals(FileTemplate.PayFromFailList.name())) {
    			builder.append(PayFailList.PAY_FROM);
	            builder.append('\t');
	            builder.append(FEE_FAIL_STATUS.NewStatus.name());
	            builder.append('\t');
    		}
        	for(ColumnItem item : standardColumns) {
        		cell = StringUtil.trimStr(row.getValue(item.getDisplayName()), true);
        		
        		if(ft.name().equals(FileTemplate.CallFail.name()) || ft.name().equals(FileTemplate.Issue.name())) {
        			if(item.getDisplayName().equals("结案时间") && cell != null && StringUtil.trimStr(cell).length()<=0) {
        				log.debug("----------- 结案时间: " + cell);
        	            builder.append("2015-01-01 00:00:00\t");
        	            continue;
        			}
        		}
        		if(ft.name().equals(FileTemplate.CallFailPFR.name()) || ft.name().equals(FileTemplate.IssuePFR.name())) {
        			if(item.getDisplayName().equals("附加险保费") && (cell == null || StringUtil.trimStr(cell).length()<=0)) {
        				log.debug("----------- 附加险保费: " + cell);
        	            builder.append("0\t");
        	            continue;
        			}
        		}
//        		if(log.isDebugEnabled()) {
//        			if(item.getDisplayName().equals("保险单号码")) {
//        				log.debug("----------- 保险单号码: " + row.getValue(item.getDisplayName()));
//        			}
//        		}
        		builder.append(cell);
	            builder.append('\t');
        	}
            builder.append(member_id);
            builder.append('\n');
            //log.debug("---000--" + builder.toString());
        }
        
        log.debug("------111-----" + builder.toString());
        
        InputStream is = null;
        try {
			is = IOUtils.toInputStream(builder.toString(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			dr.setFlag(false);
			dr.setMsg(e.getMessage());
		}

        //再执行
        statement.setLocalInfileInputStream(is);
        try {
			//statement.execute(strStatementText);
        	int updateRow = statement.executeUpdate(strStatementText);
        	dr.setUpdateRow(updateRow);
			
			if(sql1 != null) {
        		statement.execute(sql1);
        	}
			if(sql2 != null) {
        		statement.execute(sql2);
        	}
			if(sql3 != null) {
        		statement.execute(sql3);
        	}
			if(sql4 != null) {
        		statement.execute(sql4);
        	}
			if(sql5 != null) {
        		statement.execute(sql5);
        	}
			dr.setFlag(true);
		} catch (SQLException e) {
			e.printStackTrace();
			dr.setFlag(false);
			dr.setMsg(e.getMessage());
		} finally {
			if(statement != null) {
				try {
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			
			builder.delete(0, builder.length());
			builder = null;
		}
        
		return(dr);
	}
	
	@SuppressWarnings("resource")
	@Override
	public DoRst updateStatusData(FileTemplate ft, HttpServletRequest request, DataTable dt) {	
		log.info("---------  into update status data:" + ft.getDesc());
		DoRst dr = new DoRst();
		dr.setNum(dt.Rows.size());
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		try {
			Object objDataSource = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");
			DruidDataSource dataSource = (DruidDataSource)objDataSource;
			connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
			statement = (com.mysql.jdbc.Statement)connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			dr.setFlag(false);
			dr.setMsg(e.getMessage());
		}
		
		List<ColumnItem> standardColumns = null;
		StringBuffer sql = null;
		StringBuffer line = null;
		String sql2 = null;
		String sql3 = null;
		String sql4 = null;
		String sql5 = null;
		//String sql6 = null;
		boolean isFail = false;
		//boolean updateRst = true;
		Object val = null;
		switch(ft) {
		case Policy:
			return dr;
		case PolicyIngor:
			return dr;
		case PolicyDtl:
			return dr;
		case Issue:
			return dr;
		case CallFail:
			return dr;
		case CallFailStatus:
			standardColumns = CallFailHQListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_call_fail_list(policy_no, issue_desc, status, issue_type, issue_content, "
					+ "hq_deal_date, hq_deal_man, hq_deal_type, hq_deal_rst, "
					+ "hq_deal_date2, hq_deal_man2, hq_deal_type2, hq_deal_rst2, hq_deal_date3, hq_deal_man3, hq_deal_type3, hq_deal_rst3, "
					+ "hq_deal_date4, hq_deal_man4, hq_deal_type4, hq_deal_rst4, hq_deal_date5, hq_deal_man5, hq_deal_type5, hq_deal_rst5, "
					+ "hq_deal_date6, hq_deal_man6, hq_deal_type6, hq_deal_rst6) VALUES ");
			line = null;
			for (DataRow row : dt.Rows) {
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = StringUtil.trimStr(row.getValue(item.getDisplayName()));
        			
	        		if(item.getDisplayName().contains("回访日期")) {
	        			if(val == null || val.toString().trim().length() <= 0) {
	        				line.append("null,");
	        			} else {
	        				line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        			}
	        		} else {
	        			line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        		}
	        	}
	        	line.deleteCharAt(line.length() - 1);
	        	line.append("),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE issue_desc=VALUES(issue_desc), status=VALUES(status), ");
			sql.append("issue_type=VALUES(issue_type), issue_content=VALUES(issue_content), ");
			sql.append("hq_deal_date=VALUES(hq_deal_date), hq_deal_man=VALUES(hq_deal_man), ");
			sql.append("hq_deal_type=VALUES(hq_deal_type), hq_deal_rst=VALUES(hq_deal_rst), ");
			sql.append("hq_deal_date2=VALUES(hq_deal_date2), hq_deal_man2=VALUES(hq_deal_man2), ");
			sql.append("hq_deal_type2=VALUES(hq_deal_type2), hq_deal_rst2=VALUES(hq_deal_rst2), ");
			sql.append("hq_deal_date3=VALUES(hq_deal_date3), hq_deal_man3=VALUES(hq_deal_man3), ");
			sql.append("hq_deal_type3=VALUES(hq_deal_type3), hq_deal_rst3=VALUES(hq_deal_rst3), ");
			sql.append("hq_deal_date4=VALUES(hq_deal_date4), hq_deal_man4=VALUES(hq_deal_man4), ");
			sql.append("hq_deal_type4=VALUES(hq_deal_type4), hq_deal_rst4=VALUES(hq_deal_rst4), ");
			sql.append("hq_deal_date5=VALUES(hq_deal_date5), hq_deal_man5=VALUES(hq_deal_man5), ");
			sql.append("hq_deal_type5=VALUES(hq_deal_type5), hq_deal_rst5=VALUES(hq_deal_rst5), ");
			sql.append("hq_deal_date6=VALUES(hq_deal_date6), hq_deal_man6=VALUES(hq_deal_man6), ");
			sql.append("hq_deal_type6=VALUES(hq_deal_type6), hq_deal_rst6=VALUES(hq_deal_rst6);");
			log.debug("----------------batch update : " + sql);
			sql2 = "delete from t_call_fail_list where issue_no is null";
			break;
		case MiniCallFailStatus:
			standardColumns = CallFailHQMiniListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_call_fail_list(policy_no, status, "
					+ "hq_deal_date, hq_deal_man, hq_deal_type, hq_deal_rst, hq_deal_type_else, client_remark, phone_num) VALUES  ");
			line = null;
			for (DataRow row : dt.Rows) {
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
	        		
	        		if(item.getDisplayName().contains("二访日期")) {
	        			if(val == null || val.toString().trim().length() <= 0) {
	        				line.append("null,");
	        			} else {
	        				line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        			}
	        		} else {
	        			line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        		}
	        	}
	        	line.deleteCharAt(line.length() - 1);
	        	line.append("),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE status=VALUES(status), ");
			sql.append("hq_deal_date=VALUES(hq_deal_date), hq_deal_man=VALUES(hq_deal_man), ");
			sql.append("hq_deal_type=VALUES(hq_deal_type), hq_deal_rst=VALUES(hq_deal_rst), ");
			sql.append("hq_deal_type_else=VALUES(hq_deal_type_else), client_remark=VALUES(client_remark), ");
			sql.append("phone_num=VALUES(phone_num); ");
			log.debug("----------------batch update : " + sql);
			sql2 = "delete from t_call_fail_list where issue_no is null";
			sql3 = "update t_call_fail_list set hq_deal_flag = 1 where status='二访成功';";
			sql4 = "update t_call_fail_list set status = \"重点跟进\" where status='二访失败' and 15-datediff(now(),bill_back_date)<=3;";
			break;
		case CallFailMiniCityStatus:
			standardColumns = CallFailCityMiniListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_call_fail_list(policy_no, status, "
					+ "deal_time, deal_man, deal_type, deal_desc) VALUES ");
			line = null;
			for (DataRow row : dt.Rows) {
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
	        		
	        		if(item.getDisplayName().contains("上门回访日期")) {
	        			if(val == null || val.toString().trim().length() <= 0) {
	        				line.append("null,");
	        			} else {
	        				line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        			}
	        		} else {
	        			line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        		}
	        	}
	        	line.deleteCharAt(line.length() - 1);
	        	line.append("),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE status=VALUES(status), ");
			sql.append("deal_time=VALUES(deal_time), deal_man=VALUES(deal_man), ");
			sql.append("deal_type=VALUES(deal_type), deal_desc=VALUES(deal_desc);");
			log.debug("----------------batch update : " + sql);
			sql2 = "delete from t_call_fail_list where issue_no is null";
			sql3 = "update t_call_fail_list set org_deal_flag = 1 where status='上门成功';";
			sql4 = "update t_call_fail_list t1 set status=\"需上门回访\" where TO_DAYS(NOW())-TO_DAYS(should_date)>15 and status=\"二访失败\";";
			break;
		case CallFailCityStatus:
			standardColumns = CallFailDoorBackListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_call_fail_list(policy_no, door_back_date, door_stats_date, "
					+ "deal_type, deal_time, deal_man) VALUES ");
			line = null;
			for (DataRow row : dt.Rows) {
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		if(item.getDisplayName().contains("回访日期")) {
	        			if(val == null || val.toString().trim().length() <= 0) {
	        				line.append("null,");
	        			} else {
	        				line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        			}
	        		} else {
	        			line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        		}
	        	}
	        	line.deleteCharAt(line.length() - 1);
	        	line.append("),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE door_back_date=VALUES(door_back_date), door_stats_date=VALUES(door_stats_date), ");
			sql.append("deal_type=VALUES(deal_type), deal_time=VALUES(deal_time), deal_man=VALUES(deal_man);");
			//sql.append("deal_desc=VALUES(deal_desc);");
			
			log.debug("----------------batch update : " + sql);
			sql2 = "delete from t_call_fail_list where issue_no is null";
			sql3 = "update t_call_fail_list set status='上门成功' where (deal_type='上门成功' or deal_type='上门回访成功') and (status not like \"%成功%\" and status<>\"%已退保%\" and status<>\"%已结案%\")";
			sql4 = "update t_call_fail_list set status='上门失败' where (deal_type='上门失败' or deal_type='上门回访失败') and (status not like \"%成功%\" and status<>\"%已退保%\" and status<>\"%已结案%\")";
			sql5 = "update t_call_fail_list set org_deal_flag = 1 where (deal_type='上门成功' or deal_type='上门回访成功');";
			break;
		case CallFailMailStatus:
			standardColumns = CallFailMailListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_call_fail_list(policy_no, letter_date, has_letter) VALUES ");
			line = null;
			for (DataRow row : dt.Rows) {
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		if(item.getDisplayName().contains("信函日期")) {
	        			if(val == null || val.toString().trim().length() <= 0) {
	        				line.append("NULL,");
	        			} else {
	        				line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        			}
	        		} else {
	        			line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        		}
	        	}
	        	//line.deleteCharAt(line.length() - 1);
	        	line.append("\"信函已发\" ),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE ");
			sql.append("letter_date=VALUES(letter_date), has_letter=VALUES(has_letter);");
			log.debug("----------------batch update : " + sql);
			sql2 = "delete from t_call_fail_list where issue_no is null";
			break;
		case CallFailMailBackStatus:
			standardColumns = CallFailMailBackListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_call_fail_list(policy_no, mail_fail_reason, mail_fail_date, has_letter) VALUES ");
			line = null;
			for (DataRow row : dt.Rows) {
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		if(item.getDisplayName().contains("退信时间") || item.getDisplayName().contains("回邮时间")) {
	        			if(val == null || val.toString().trim().length() <= 0) {
	        				line.append("NULL,");
	        			} else {
	        				line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        			}
	        		} else {
	        			line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        		}
	        	}
	        	//line.deleteCharAt(line.length() - 1);
	        	line.append("\"信函失败\"),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE mail_fail_reason=VALUES(mail_fail_reason), ");
			sql.append("mail_fail_date=VALUES(mail_fail_date),has_letter=VALUES(has_letter);");
			log.debug("----------------batch update : " + sql);
			sql2 = "delete from t_call_fail_list where issue_no is null";
			break;
		case CallFailMailSuccessStatus:
			standardColumns = CallFailMailSuccessListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_call_fail_list(policy_no, has_letter, mail_back_date, client_sign_date) VALUES ");
			line = null;
			for (DataRow row : dt.Rows) {
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		if(item.getDisplayName().contains("客户签名时间") || item.getDisplayName().contains("回邮时间")) {
	        			if(val == null || val.toString().trim().length() <= 0) {
	        				line.append("null,");
	        			} else {
	        				line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        			}
	        		} else {
	        			line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        		}
	        	}
	        	line.deleteCharAt(line.length() - 1);
	        	line.append("),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE has_letter=VALUES(has_letter), ");
			sql.append("mail_back_date=VALUES(mail_back_date), client_sign_date=VALUES(client_sign_date);");
			log.debug("----------------batch update : " + sql);
			sql2 = "delete from t_call_fail_list where issue_no is null";
			break;
		case CallFailNeedDoorStatus:
			standardColumns = CallFailNeedDoorListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_call_fail_list(policy_no, status) VALUES ");
			line = null;
			for (DataRow row : dt.Rows) {
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        	}
	        	line.deleteCharAt(line.length() - 1);
	        	line.append("),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE ");
			sql.append("status=VALUES(status);");
			log.debug("----------------batch update : " + sql);
			sql2 = "delete from t_call_fail_list where issue_no is null";
			//sql3 = "update t_call_fail_list set status=deal_status where deal_status is not null and status<>\"二访成功\"";
			sql4 = "update t_call_fail_list t1 set status=\"需上门回访\" where TO_DAYS(NOW())-TO_DAYS(should_date)>15 and status=\"二访失败\";";
			break;
		case CallFailPhoneStatus:
			standardColumns = CallFailNeedDoorListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_call_fail_list(policy_no, phone_num, phone_start, phone_end, phone_time) VALUES ");
			line = null;
			for (DataRow row : dt.Rows) {
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        	}
	        	line.deleteCharAt(line.length() - 1);
	        	line.append("),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE ");
			sql.append("phone_num=VALUES(phone_num), phone_start=VALUES(phone_start), phone_end=VALUES(phone_end), phone_time=VALUES(phone_time);");
			log.debug("----------------batch update : " + sql);
			sql2 = "delete from t_call_fail_list where issue_no is null";
			break;
		case CallFailCloseStatus:
			standardColumns = CallFailCloseListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_call_fail_list(policy_no, finish_date, status) VALUES ");
			line = null;
			for (DataRow row : dt.Rows) {
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        	}
	        	line.deleteCharAt(line.length() - 1);
	        	line.append(", \"已结案\"),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE ");
			sql.append("finish_date=VALUES(finish_date), status=VALUES(status);");
			log.debug("----------------batch update : " + sql);
			sql2 = "delete from t_call_fail_list where issue_no is null";
			break;
		case Renewed:
			return dr;
		case RenewedStatus://继续率清单
			standardColumns = RenewedStatusColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_renewed_list(policy_no, prd_name, fee_status, fee_fail_reason) VALUES ");
			line = null;
			isFail = false;
			val = null;
			for (DataRow row : dt.Rows) {
				isFail = false;
				val = null;
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		if(item.getDisplayName().equals("保单当前状态")) {
	        			if(val != null && val.equals(XQ_STATUS.FeeFailStatus.getDesc())) {
	        				isFail = true;
	        			}
	        		}
	        		if(item.getDisplayName().equals("交费失败原因") && isFail) {
	        			if(val == null || val.toString().length() <= 0) {
	        				line.append("\"" + XQ_STATUS.DeadStatus.getDesc() + "\",");
	        			} else {
	        				line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        			}
	        		} else {
	        			line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        		}
	        	}
	        	line.deleteCharAt(line.length() - 1);
	        	line.append("),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE ");
			sql.append("fee_status=VALUES(fee_status), fee_fail_reason=VALUES(fee_fail_reason);");
			log.debug("----------------batch update : " + sql);
			sql2 = "delete from t_renewed_list where holder is null";
			sql3 = "update t_renewed_list set fee_status=\"" + XQ_STATUS.DeadStatus.getDesc() + "\" where fee_fail_reason=\"" + XQ_STATUS.DeadStatus.getDesc() + "\"";
			sql4 = "update t_renewed_list set fee_status=\"" + XQ_STATUS.FeeFailStatus.getDesc() + "\" where fee_status=\"挂起\"";
			break;
		case RenewedHQList://总部的催收
			standardColumns = RenewedHQListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_renewed_list(policy_no, prd_name, hq_issue_type, hq_deal_rst, hq_deal_date, hq_deal_remark) VALUES ");
			line = null;
			isFail = false;
			val = null;
			for (DataRow row : dt.Rows) {
				isFail = false;
				val = null;
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		if(item.getDisplayName().equals("工单子类")) {
	        			if(val == null || val.toString().length() <= 0) {
	        				line.append("\"已告知\",");
	        			} else {
	        				line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        			}
	        		} else if(item.getDisplayName().equals("回访日期")) {
	        			if(val == null || val.toString().length() <= 0) {
	        				line.append("null,");
	        			} else {
	        				line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        			}
	        		} else {
	        			line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        		}
	        	}
	        	line.deleteCharAt(line.length() - 1);
	        	line.append("),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE ");
			sql.append("hq_issue_type=VALUES(hq_issue_type), hq_deal_rst=VALUES(hq_deal_rst), ");
			sql.append("hq_deal_date=VALUES(hq_deal_date), hq_deal_remark=VALUES(hq_deal_remark);");
			log.debug("----------------hq update status batch sql : " + sql);
			sql2 = "delete from t_renewed_list where holder is null";
			break;
		case RenewedProvList://总部的催收
			standardColumns = RenewedProvListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_renewed_list(policy_no, prd_name, prov_deal_date, prov_deal_rst, prov_activity) VALUES ");
			line = null;
			isFail = false;
			val = null;
			for (DataRow row : dt.Rows) {
				isFail = false;
				val = null;
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        	}
	        	line.deleteCharAt(line.length() - 1);
	        	line.append("),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE ");
			sql.append("prov_deal_date=VALUES(prov_deal_date), prov_deal_rst=VALUES(prov_deal_rst), prov_activity=VALUES(prov_activity);");
			log.debug("----------------prov update status batch sql : " + sql);
			sql2 = "delete from t_renewed_list where holder is null";
			sql3 = "update t_renewed_list t1, t_renewed_list t2 set t1.prov_deal_date=t2.prov_deal_date,t1.prov_deal_rst=t2.prov_deal_rst where t1.prd_name=\"中邮附加重大疾病保险\" and t1.policy_no=t2.policy_no and t2.prov_deal_rst is not null;";
			break;
		case RenewedCityList://总部的催收
			standardColumns = RenewedCityListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_renewed_list(policy_no, prd_name, deal_time, fix_status) VALUES ");
			line = null;
			isFail = false;
			val = null;
			for (DataRow row : dt.Rows) {
				isFail = false;
				val = null;
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        	}
	        	line.deleteCharAt(line.length() - 1);
	        	line.append("),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE ");
			sql.append("deal_time=VALUES(deal_time), fix_status=VALUES(fix_status);");
			log.debug("----------------city update status batch sql : " + sql);
			sql2 = "delete from t_renewed_list where holder is null";
			sql3 = "update t_renewed_list t1, t_renewed_list t2 set t1.deal_time=t2.deal_time,t1.fix_status=t2.fix_status where t1.prd_name=\"中邮附加重大疾病保险\" and t1.policy_no=t2.policy_no and t2.fix_status is not null;";
			break;
		case CheckWrite:
			return dr;
		case CheckRecord:
			return dr;
		case PayToFailList:
			return dr;
		case PayFromFailList:
			return dr;
		case PaySuccessList:
			return dr;
		case PolicyUnderWrite:
			standardColumns = PolicyUnderWriteColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_under_write(policy_no, client_receive_date, sign_input_date, status) VALUES ");
			line = null;
			isFail = false;
			val = null;
			for (DataRow row : dt.Rows) {
				isFail = false;
				val = null;
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        	}
	        	//line.deleteCharAt(line.length() - 1);
	        	line.append("\"CloseStatus\"),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE ");
			sql.append("client_receive_date=VALUES(client_receive_date), sign_input_date=VALUES(sign_input_date), status=VALUES(status);");
			log.debug("----------------city update status batch sql : " + sql);
			sql2 = "delete from t_under_write where form_no is null";
			sql3 = "update t_policy t1, t_under_write t2 set t1.bill_back_date=t2.client_receive_date, t2.status=\"CloseStatus\" "
					+ "where t1.policy_no=t2.policy_no and t2.client_receive_date is not null;";
			sql4 = "update t_policy set bill_back_date=policy_date where bill_back_date is null;";
			break;
		case PolicyBackDate:
			standardColumns = PolicyBackDateColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_policy(policy_no, prod_code, bill_back_date) VALUES ");
			line = null;
			isFail = false;
			val = null;
			for (DataRow row : dt.Rows) {
				isFail = false;
				val = null;
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        	}
	        	line.deleteCharAt(line.length() - 1);
	        	line.append("),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE ");
			sql.append("bill_back_date=VALUES(bill_back_date);");
			log.debug("----------------city update status batch sql : " + sql);
			sql2 = "delete from t_policy where form_no is null";
			//sql3 = "update t_policy set bill_back_date=policy_date where bill_back_date is null;";
			break;
		case UnderWriteSentData:
			standardColumns = UnderWriteSentDataColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_under_write(policy_no, form_no, prov_ems_no, prov_send_date, status) VALUES ");
			line = null;
			isFail = false;
			val = null;
			for (DataRow row : dt.Rows) {
				isFail = false;
				val = null;
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        	}
	        	//line.deleteCharAt(line.length() - 1);
	        	line.append("\"SendStatus\"),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE ");
			sql.append("policy_no=VALUES(policy_no), form_no=VALUES(form_no), prov_ems_no=VALUES(prov_ems_no), prov_send_date=VALUES(prov_send_date), status=VALUES(status);");
			log.debug("----------------city update status batch sql : " + sql);
			sql2 = "delete from t_under_write where holder is null";
			break;
		default:
			break;
		}

        try {
        	int updateRow = statement.executeUpdate(sql.toString());
        	dr.setUpdateRow(updateRow);
			if(sql2 != null) {
				statement.execute(sql2);
			}
			if(sql3 != null) {
				statement.execute(sql3);
			}
			if(sql4 != null) {
				statement.execute(sql4);
			}
			if(sql5 != null) {
				statement.execute(sql5);
			}
			//log.info("------------renewed status update result:" + updateRst);
			dr.setFlag(true);
		} catch (SQLException e) {
			e.printStackTrace();
			dr.setFlag(false);
			dr.setMsg(e.getMessage());
		} finally {
			if(statement != null) {
				try {
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
        
		return dr;
	}
	
	/**
	 * 
	 */
	public DoRst handleData(FileTemplate t, HttpServletRequest request, long member_id, List<String> listFiles, 
			int currentNY, int lastNY, long operator_id, String operator_name, int operator_type, StringBuilder builder, String memo) {
		log.debug("-----------handle DATA -use template:" + t);
		// 标准列
		List<ColumnItem> standardColumns = null;
		DoRst dr = new DoRst();
		String keyRow = null;
		switch(t) {
		case Policy:
			log.debug("----------get the policy column");
			standardColumns = PolicyColumn.getStandardColumns();
			keyRow = PolicyColumn.KEY_ROW;
			break;
		case PolicyIngor:
			log.debug("----------get the policyIngor column");
			standardColumns = PolicyColumn.getStandardColumns();
			keyRow = PolicyColumn.KEY_ROW;
			break;
		case PolicyDtl:
			log.debug("----------get the dtl column");
			standardColumns = PolicyDtlColumn.getStandardColumns();
			keyRow = PolicyDtlColumn.KEY_ROW;
			break;
		case PolicyUnderWrite:
			log.debug("----------get the dtl column");
			standardColumns = PolicyUnderWriteColumn.getStandardColumns();
			keyRow = PolicyUnderWriteColumn.KEY_ROW;
			break;
		case Issue:
			log.debug("----------get the issue column");
			standardColumns = IssueColumn.getStandardColumns();
			keyRow = IssueColumn.KEY_ROW;
			break;
		case CallFail:
			standardColumns = IssueColumn.getStandardColumns();
			keyRow = IssueColumn.KEY_ROW;
			break;
		case CallFailStatus:
			standardColumns = CallFailHQListColumn.getStandardColumns();
			keyRow = CallFailHQListColumn.KEY_ROW;
			break;
		case MiniCallFailStatus:
			standardColumns = CallFailHQMiniListColumn.getStandardColumns();
			keyRow = CallFailHQMiniListColumn.KEY_ROW;
			break;
		case CallFailMailStatus:
			standardColumns = CallFailMailListColumn.getStandardColumns();
			keyRow = CallFailMailListColumn.KEY_ROW;
			break;
		case CallFailNeedDoorStatus:
			standardColumns = CallFailNeedDoorListColumn.getStandardColumns();
			keyRow = CallFailNeedDoorListColumn.KEY_ROW;
			break;
		case CallFailCloseStatus:
			standardColumns = CallFailCloseListColumn.getStandardColumns();
			keyRow = CallFailCloseListColumn.KEY_ROW;
			break;
		case Renewed:
			standardColumns = RenewedColumn.getStandardColumns();
			keyRow = RenewedColumn.KEY_ROW;
			break;
		case RenewedStatus:
			standardColumns = RenewedStatusColumn.getStandardColumns();
			keyRow = RenewedStatusColumn.KEY_ROW;
			break;
		case RenewedHQList:
			standardColumns = RenewedHQListColumn.getStandardColumns();
			keyRow = RenewedHQListColumn.KEY_ROW;
			break;
		case RenewedProvList:
			standardColumns = RenewedProvListColumn.getStandardColumns();
			keyRow = RenewedProvListColumn.KEY_ROW;
			break;
		case RenewedCityList:
			standardColumns = RenewedCityListColumn.getStandardColumns();
			keyRow = RenewedCityListColumn.KEY_ROW;
			break;
		case CheckWrite:
			standardColumns = CheckColumn.getStandardColumns();
			keyRow = CheckColumn.KEY_ROW;
			break;
		case CheckRecord:
			standardColumns = CheckColumn.getStandardColumns();
			keyRow = CheckColumn.KEY_ROW;
			break;
		case PayToFailList:
			standardColumns = PayFailListColumn.getStandardColumns();
			keyRow = PayFailListColumn.KEY_ROW;
			break;
		case PayFromFailList:
			standardColumns = PayFailListColumn.getStandardColumns();
			keyRow = PayFailListColumn.KEY_ROW;
			break;
		case PaySuccessList:
			standardColumns = PayFailListColumn.getStandardColumns();
			keyRow = PayFailListColumn.KEY_ROW;
			break;
		case CallFailCityStatus:
			standardColumns = CallFailDoorBackListColumn.getStandardColumns();
			keyRow = CallFailDoorBackListColumn.KEY_ROW;
			break;
		case CallFailMailBackStatus:
			standardColumns = CallFailMailBackListColumn.getStandardColumns();
			keyRow = CallFailMailBackListColumn.KEY_ROW;
			break;
		case CallFailMiniCityStatus:
			standardColumns = CallFailCityMiniListColumn.getStandardColumns();
			keyRow = CallFailCityMiniListColumn.KEY_ROW;
			break;
		case CallFailMailSuccessStatus:
			standardColumns = CallFailMailSuccessListColumn.getStandardColumns();
			keyRow = CallFailMailSuccessListColumn.KEY_ROW;
			break;
		case CallFailPhoneStatus:
			standardColumns = CallFailMailSuccessListColumn.getStandardColumns();
			keyRow = CallFailMailSuccessListColumn.KEY_ROW;
			break;
		case UnderWriteSentData:
			standardColumns = UnderWriteSentDataColumn.getStandardColumns();
			keyRow = UnderWriteSentDataColumn.KEY_ROW;
			break;
		case PolicyBackDate:
			standardColumns = PolicyBackDateColumn.getStandardColumns();
			keyRow = PolicyBackDateColumn.KEY_ROW;
			break;
		case ConversationReq:
			standardColumns = ConversationReqColumn.getStandardColumns();
			keyRow = ConversationReqColumn.KEY_ROW;
			break;
		case ConversationReport:
			standardColumns = CsReportColumn.getStandardColumns();
			keyRow = CsReportColumn.KEY_ROW;
			break;
		case IssuePFR:
			standardColumns = IssuePFRColumn.getStandardColumns();
			keyRow = IssuePFRColumn.KEY_ROW;
			break;
		case CallFailPFR:
			standardColumns = IssuePFRColumn.getStandardColumns();
			keyRow = IssuePFRColumn.KEY_ROW;
			break;
		}
		
	    boolean bFlag = true;
		Map<String, Integer> dtCurrentShop = new Hashtable<String, Integer>();
		
		String strFilePath = UploadDataUtils.getFileStoreTempPath(request);
		DataTable[] ds = null;
		Map<String, DataTable[]> listDataSet = new HashMap<String, DataTable[]>(); 
		
		// 文件组正确，执行导入
		for(String strOriginalFileName : listFiles) {
			// 读文件，取值到DataTable[]中
			try {
				ds = UploadDataUtils.getDataSet(strFilePath, strOriginalFileName, standardColumns, keyRow);
			} catch(MyException ex) {
				dr.setMsg(ex.getMessage());
				dr.setFlag(false);
				return dr;
			}
			if(ds == null || ds.length == 0) {
				builder.append("处理文件[" + strOriginalFileName + "]中数据出错，没有找到数据。");
				dr.setMsg("处理文件[" + strOriginalFileName + "]中数据出错，没有找到数据。");
				dr.setFlag(false);
				return dr;
				//return(false);
			}
			
			listDataSet.put(strOriginalFileName, ds);
		}
		
		for(String strKey : listDataSet.keySet()) {
			DataTable[] dataSet = listDataSet.get(strKey);
			
			// 处理行列数据
			bFlag = handleColumn(dataSet, standardColumns, strKey, dtCurrentShop, builder);
			if(!bFlag) {
				break;
			}
		}
		
		if(!bFlag) {
			ds = null;
			listDataSet.clear();
			listDataSet = null;
			dr.setMsg("处理列数据失败！");
			dr.setFlag(false);
			return dr;
			//return(false);
		}
		
		// 导入数据库
		for(DataTable[] dataSet : listDataSet.values()) {
			for(DataTable dt : dataSet) {
				if(dt.Rows == null || dt.Rows.size()<=0) {
					dr.setMsg(dr.getMsg() + "。存在的工作表中可能因为关键列" + keyRow + "缺失数据，请检查数据！");
					dr.setFlag(false);
					return dr;
				}
				if(t.name().equals(FileTemplate.RenewedStatus.name())
						|| t.name().equals((FileTemplate.RenewedHQList.name())) 
						|| t.name().equals((FileTemplate.CallFailStatus.name()))
						|| t.name().equals(FileTemplate.MiniCallFailStatus.name())
						|| t.name().equals(FileTemplate.CallFailCityStatus.name())
						|| t.name().equals(FileTemplate.CallFailMailStatus.name())
						|| t.name().equals(FileTemplate.CallFailNeedDoorStatus.name())
						|| t.name().equals(FileTemplate.CallFailMailBackStatus.name())
						|| t.name().equals(FileTemplate.CallFailMailSuccessStatus.name())
						|| t.name().equals(FileTemplate.CallFailCloseStatus.name())
						|| t.name().equals(FileTemplate.CallFailPhoneStatus.name())
						|| t.name().equals(FileTemplate.RenewedProvList.name())
						|| t.name().equals(FileTemplate.RenewedCityList.name())
						|| t.name().equals(FileTemplate.PolicyUnderWrite.name())
						|| t.name().equals(FileTemplate.UnderWriteSentData.name())
						|| t.name().equals(FileTemplate.CallFailMiniCityStatus.name())
						|| t.name().equals(FileTemplate.PolicyBackDate.name())) {
					
					dr = updateStatusData(t, request, dt);
					
					//如果是保单打印数据，需要单独导入到打印明细中
					if(t.name().equals(FileTemplate.UnderWriteSentData.name())) {
						importData(t, request, dt, member_id, currentNY);
					}
					
				} else {
					dr = importData(t, request, dt, member_id, currentNY);
				}
				dt = null;
				if(!dr.isFlag()) {
					builder.append("导入数据出错。" + dr.getMsg());
					dr.setMsg("导入数据出错。" + dr.getMsg());
					break;
				}
			}
		}
		
		ds = null;
		listDataSet.clear();
		listDataSet = null;
		
		return dr;
	}
	
	private boolean handleColumn(DataTable[] ds, List<ColumnItem> standardColumns, String strOriginalFileName, Map<String, Integer> dtCurrentShop, StringBuilder builder) {
		String strHandleMessage = "";
		String columnName = "";
		boolean bFlag = true;
		int iRowCount = 0;
		String strValue = "";
		Double dValue;
		
		for(DataTable dt : ds) {
			// 处理数据对应到标准列头
			for(ColumnItem item : standardColumns) {
        		if(!item.isHasValue()) {
        			continue;
        		}
        		
				columnName = item.getColumnName();
				if(item.isMapColumn()) {
					// 列对应，先检查列序号是否
					// 如果是数值型，则检查数值数据是否正确
					iRowCount = 0;
					if(item.getColumnType() == ColumnType.numeric) {
						for(DataRow dataRow : dt.Rows) {
							iRowCount++;
							try {
								strValue = dataRow.getValue(item.getMapColumnIndex() - 1).toString();
								strValue = strValue.replace(",", "");
								dValue = Double.parseDouble(strValue);
								dataRow.setValue(columnName, dValue);
							} catch(Exception e) {
								if(!item.isNullable() && iRowCount != dt.Rows.size()) {	// 最后一行出错也忽略
									builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "]第[" + iRowCount + "]行[" + item.getMapColumnIndex() + "]列数值出错。");
									bFlag = false;
									break;
								} else {
									dValue = 0d;
								}
							}
						}
					} else {
						// 检查字符长度
						for(DataRow dataRow : dt.Rows) {
							iRowCount++;
							try {
								strValue = "";
								if(dataRow.getValue(item.getMapColumnIndex() - 1) == null) {
									if(!item.isNullable() && iRowCount != dt.Rows.size()) {
										builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "]第[" + iRowCount + "]行[" + item.getMapColumnIndex() + "]列长度为0出错。");
										bFlag = false;
										break;
									}
								} else {								
									strValue = dataRow.getValue(item.getMapColumnIndex() - 1).toString();
									strValue = strValue.replace("\r", "");
									strValue = strValue.replace("\n", "");
									strValue = strValue.replace("\t", "");
								}
								
								// 判断字符串长度
								if(strValue.length() == 0 && !item.isNullable() && iRowCount != dt.Rows.size()) {
									builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "]第[" + iRowCount + "]行[" + item.getMapColumnIndex() + "]列长度为0出错。");
									bFlag = false;
									break;
								}
								
								if(strValue.length() > 100) {
									builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "]第[" + iRowCount + "]行[" + item.getMapColumnIndex() + "]列长度超长出错。");
									bFlag = false;
									break;
								}
								
								dataRow.setValue(columnName, strValue);
							} catch(Exception e) {
								builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "]第[" + iRowCount + "]行[" + item.getMapColumnIndex() + "]列出错。");
								bFlag = false;
								break;
							}
						}
					}
					// 修改对应列名
					if(bFlag) {
						dt.Columns.get(item.getMapColumnIndex() - 1).ColumnName = columnName;
					}
				} else if(item.isFromFileName()) {
					// 如果DataTable 没有该标准列，则添加，然后设置行值
					if(dt.Columns.get(item.getColumnName()) == null) {
						dt.Columns.Add(columnName);
					}
					
					// 从文件名中截取
					if(item.getConcatRule() != null) {
						item.getConcatRule().setConcatValue(strOriginalFileName);
						bFlag = setCellValue(strOriginalFileName, columnName, dt, item, builder);
						if(!bFlag) {
							break;
						}
					} else {
						iRowCount = 0;
						for(DataRow dataRow : dt.Rows) {
							iRowCount++;
							try {
								dataRow.setValue(columnName, strOriginalFileName);
							} catch(Exception e) {
								builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "]第[" + iRowCount + "]行[" + item.getMapColumnIndex() + "]列出错。");
								bFlag = false;
								break;
							}
						}
					}
				} else if(item.isFromSheetName()) {
					// 如果DataTable 没有该标准列，则添加，然后设置行值
					if(dt.Columns.get(item.getColumnName()) == null) {
						dt.Columns.Add(columnName);
					}
					
					// 从Sheet名中截取，TableName就是SheetName
					if(item.getConcatRule() != null) {
						item.getConcatRule().setConcatValue(dt.TableName);
						bFlag = setCellValue(strOriginalFileName, columnName, dt, item, builder);
						if(!bFlag) {
							break;
						}
					} else {
						iRowCount = 0;
						for(DataRow dataRow : dt.Rows) {
							iRowCount++;
							try {
								dataRow.setValue(columnName, dt.TableName);
							} catch(Exception e) {
								builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "]第[" + iRowCount + "]行[" + item.getMapColumnIndex() + "]列出错。");
								bFlag = false;
								break;
							}
						}
					}
				} else if(item.isFromColumn()) {
					// 从列值中截取
					// 如果DataTable 没有该标准列，则添加，然后设置行值
					if(dt.Columns.get(item.getColumnName()) == null) {
						dt.Columns.Add(columnName);
					}
					
					if(item.getConcatRule() != null) {
						bFlag = setCellValue2(strOriginalFileName, columnName, dt, item, builder);
						if(!bFlag) {
							break;
						}
					} else {
						iRowCount = 0;
						for(DataRow dataRow : dt.Rows) {
							iRowCount++;
							try {
								strValue = "";
								if(dataRow.getValue(item.getMapColumnIndex() - 1) != null) {
									strValue = dataRow.getValue(item.getMapColumnIndex() - 1).toString();
									strValue = strValue.replace("\r", "");
									strValue = strValue.replace("\n", "");
									strValue = strValue.replace("\t", "");
								}
								dataRow.setValue(columnName, strValue);
							} catch(Exception e) {
								builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "]第[" + iRowCount + "]行[" + item.getMapColumnIndex() + "]列出错。");
								bFlag = false;
								break;
							}
						}
					}
				} else if(item.isNeedCalculate()) {
					// 多列计算，如果列不存在则先增加列，否则直接计算保存于该列名
					if(dt.Columns.get(item.getColumnName()) == null) {
						dt.Columns.Add(columnName);
					}
					
					strHandleMessage = UploadDataUtils.getRowCalculator(item.getFormula(), dt, columnName);
					if(strHandleMessage != "") {
						builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "]" + strHandleMessage);
						bFlag = false;
						break;
					}
				} else if(item.isStaticValue()) {
					// 如果DataTable 没有该标准列，则添加，然后设置行值
					if(dt.Columns.get(item.getColumnName()) == null) {
						dt.Columns.Add(columnName);
					}
					
					// 固定值
					strValue = item.getStaticValue();
					if(strValue.length() > 100) {
						builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "]固定值[" + strValue + "]列长度超长出错。");
						bFlag = false;
						break;
					}
					
					for(DataRow dataRow : dt.Rows) {
						iRowCount++;
						try {
							dataRow.setValue(columnName, strValue);
						} catch(Exception e) {
							builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "]第[" + iRowCount + "]行[" + item.getMapColumnIndex() + "]列出错。");
							bFlag = false;
							break;
						}
					}
				}
				
				if(!bFlag) {
					break;
				}
			}
			
			if(!bFlag) {
				break;
			}
			
			// 上传数据中的店名
			for(ColumnItem item : standardColumns) {
				if(item.getColumnName().equalsIgnoreCase("dm") && item.isHasValue()) {
					for(DataRow dataRow : dt.Rows) {
						if(!dtCurrentShop.containsKey(dataRow.getValue("dm"))) {
							dtCurrentShop.put(dataRow.getValue("dm").toString(), 0);
						}
					}
				}
			}
		}
		
		return(bFlag);
	}
	
	private boolean setCellValue(String strOriginalFileName, String columnName, DataTable dt, ColumnItem item, StringBuilder builder) {
		String strValue = "";
		
		try {
			strValue = item.getConcatRule().getConcatResult();
			// 判断字符串长度
			if(strValue.length() == 0 && !item.isNullable()) {
				builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "][" + item.getColumnName() + "]长度为0出错。");
				return(false);
			}
			
			if(strValue.length() > 100) {
				builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "][" + item.getColumnName() + "]长度超长出错。");
				return(false);
			}
		} catch(Exception e) {
			builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "][" + item.getColumnName() + "]出错。");
			return(false);
		}

		for(DataRow dataRow : dt.Rows) {
			if(strValue != null) {
				strValue = strValue.replace("\r", "");
				strValue = strValue.replace("\n", "");
				strValue = strValue.replace("\t", "");
			}
			dataRow.setValue(columnName, strValue);
		}
		
		return(true);
	}
	
	private boolean setCellValue2(String strOriginalFileName, String columnName, DataTable dt, ColumnItem item, StringBuilder builder) {
		String strValue = "";
		int iRowCount = 0;
		boolean bFlag = true;
		
		for(DataRow dataRow : dt.Rows) {
			iRowCount++;
			try {
				item.getConcatRule().setConcatValue(dataRow.getValue(item.getFromColumnIndex() - 1).toString());
				strValue = item.getConcatRule().getConcatResult();
				// 判断字符串长度
				if(strValue.length() == 0 && !item.isNullable() && iRowCount != dt.Rows.size()) {
					builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "]第[" + iRowCount + "]行[" + item.getFromColumnIndex() + "]列长度为0出错。");
					bFlag = false;
					break;
				}
				
				if(strValue.length() > 100) {
					builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "]第[" + iRowCount + "]行[" + item.getFromColumnIndex() + "]列长度超长出错。");
					bFlag = false;
					break;
				}
			} catch(Exception e) {
				builder.append("处理[" + strOriginalFileName + "][" + dt.TableName + "]第[" + iRowCount + "]行[" + item.getFromColumnIndex() + "]列出错。");
				bFlag = false;
				break;
			}
			
			if(bFlag) {
				if(strValue != null) {
					strValue = strValue.replace("\r", "");
					strValue = strValue.replace("\n", "");
					strValue = strValue.replace("\t", "");
				}
				dataRow.setValue(columnName, strValue);
			}
		}
		
		return(bFlag);
	}
		
	public boolean setImportDone(HttpServletRequest request, long member_id, int ny, long operator_id, String operator_name, int operate_type, String memo) {
//		java.sql.Connection connection = null;
//		com.mysql.jdbc.Statement statement = null;
		boolean bResult = true;
		//com.alibaba.druid.pool.DruidDataSource basic = null;
		return(bResult);
	}
	
	public boolean clearImportDone(HttpServletRequest request, long member_id, int ny) {
//		java.sql.Connection connection = null;
//		com.mysql.jdbc.Statement statement = null;
		boolean bResult = true;
		return(bResult);
	}
	
	public boolean clearImport(HttpServletRequest request, long member_id, int ny) {
//		java.sql.Connection connection = null;
//		com.mysql.jdbc.Statement statement = null;
		boolean bResult = true;
		
		return(bResult);
	}
	
	
	@SuppressWarnings("unused")
	private void fillTable(HttpServletRequest request, long member_id, Map<String, Map<String, Integer>> ds, String strTableName, String strValueColumnName, String strIDColumnName) {
		Map<String, Integer> dtRelate = new Hashtable<String, Integer>(); // data table
		
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		try {
			Object objDataSource = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");
			@SuppressWarnings("resource")
			DruidDataSource dataSource = (DruidDataSource)objDataSource;
			connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
			statement = (com.mysql.jdbc.Statement)connection.createStatement();
			String statementText = "SELECT " + strValueColumnName + "," + strIDColumnName + " FROM " + strTableName + " WHERE member_id=" + member_id;
			ResultSet rs = statement.executeQuery(statementText);
			while(rs.next()) {
				// add data row				
				dtRelate.put(rs.getString(strValueColumnName), rs.getInt(strIDColumnName));
			}
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		ds.put(strTableName, dtRelate);// add data table to data set
	}
}
