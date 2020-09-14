package com.gdpost.web.service.impl.uploaddatamanage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
import com.gdpost.utils.TemplateHelper.CallFailHQMiniListColumn;
import com.gdpost.utils.TemplateHelper.CallFailMailBackListColumn;
import com.gdpost.utils.TemplateHelper.CallFailMailListColumn;
import com.gdpost.utils.TemplateHelper.CallFailMailSuccessListColumn;
import com.gdpost.utils.TemplateHelper.CallFailNeedDoorListColumn;
import com.gdpost.utils.TemplateHelper.CallRatioColumn;
import com.gdpost.utils.TemplateHelper.CheckCityBackColumn;
import com.gdpost.utils.TemplateHelper.CheckColumn;
import com.gdpost.utils.TemplateHelper.ClaimsCloseReportColumn;
import com.gdpost.utils.TemplateHelper.ColumnItem;
import com.gdpost.utils.TemplateHelper.ColumnType;
import com.gdpost.utils.TemplateHelper.ConversationReqColumn;
import com.gdpost.utils.TemplateHelper.CsExpireColumn;
import com.gdpost.utils.TemplateHelper.CsLoanColumn;
import com.gdpost.utils.TemplateHelper.CsReportColumn;
import com.gdpost.utils.TemplateHelper.DocNotScanDtlColumn;
import com.gdpost.utils.TemplateHelper.InquireColumn;
import com.gdpost.utils.TemplateHelper.IssueColumn;
import com.gdpost.utils.TemplateHelper.IssuePFRColumn;
import com.gdpost.utils.TemplateHelper.IssuePFRDealColumn;
import com.gdpost.utils.TemplateHelper.PayFailListColumn;
import com.gdpost.utils.TemplateHelper.PolicyBackDateColumn;
import com.gdpost.utils.TemplateHelper.PolicyColumn;
import com.gdpost.utils.TemplateHelper.PolicyDtlExtColumn;
import com.gdpost.utils.TemplateHelper.PolicyDtlsColumn;
import com.gdpost.utils.TemplateHelper.PolicySentDataColumn;
import com.gdpost.utils.TemplateHelper.PolicyUnderWriteColumn;
import com.gdpost.utils.TemplateHelper.RenewedCityListColumn;
import com.gdpost.utils.TemplateHelper.RenewedColumn;
import com.gdpost.utils.TemplateHelper.RenewedFeeMatchColumn;
import com.gdpost.utils.TemplateHelper.RenewedFeeRstColumn;
import com.gdpost.utils.TemplateHelper.RenewedHQListColumn;
import com.gdpost.utils.TemplateHelper.RenewedProvListColumn;
import com.gdpost.utils.TemplateHelper.RenewedStatusColumn;
import com.gdpost.utils.TemplateHelper.SettlementColumn;
import com.gdpost.utils.TemplateHelper.StasticsAreaColumn;
import com.gdpost.utils.TemplateHelper.StasticsCityColumn;
import com.gdpost.utils.TemplateHelper.Template.FileTemplate;
import com.gdpost.utils.TemplateHelper.UWDtlColumn;
import com.gdpost.utils.TemplateHelper.UnderWriteColumn;
import com.gdpost.utils.TemplateHelper.UnderWriteDtlColumn;
import com.gdpost.utils.TemplateHelper.UnderWriteRemarkColumn;
import com.gdpost.utils.TemplateHelper.UnderWriteSentDataColumn;
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.dao.uploaddatamanage.UploadDataDAO;
import com.gdpost.web.entity.insurance.PayList;
import com.gdpost.web.entity.insurance.Policy;
import com.gdpost.web.service.uploaddatamanage.UploadDataService;
import com.gdpost.web.util.DoRst;
import com.gdpost.web.util.MyUtil;
import com.gdpost.web.util.StatusDefine.FEE_FAIL_STATUS;
import com.gdpost.web.util.StatusDefine.STATUS;
import com.gdpost.web.util.StatusDefine.XQ_STATUS;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;
import com.mysql.cj.jdbc.JdbcStatement;

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

	@Override
	public DoRst importData(FileTemplate ft, HttpServletRequest request, DataTable dt, long member_id, int ny) {		
		log.info("---------   into import data");
		DoRst dr = new DoRst();
		dr.setNum(dt.Rows.size());
		java.sql.Connection connection = null;
		JdbcStatement statement = null;
		
		//com.mysql.cj.jdbc.ServerPreparedStatement statement = null;
		try {
			Object objDataSource = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");
			@SuppressWarnings("resource")
			DruidDataSource dataSource = (DruidDataSource)objDataSource;
			connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
			statement = (JdbcStatement) connection.createStatement();
			//statement = (com.mysql.cj.jdbc.ServerPreparedStatement)connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			dr.setFlag(false);
			dr.setMsg(e.getMessage());
			return dr;
		}
		
		// AES Encrypt key
		String strKey = com.gdpost.web.MySQLAESKey.AESKey;

		List<ColumnItem> standardColumns = null;
		String firstsql = null;
		//String tableName = null;
		String strStatementText = null;
		String strEncrypt = "";
		String sql1 = null;
		String sql2 = null;
		String sql3 = null;
		String sql4 = null;
		String sql5 = null;
		String sql6 = null;
		String sql7 = null;
		String sql8 = null;
		String sql9 = null;
		String sql10 = null;
		String sql11 = null;
		String sql12 = null;
		String sql13 = null;
		switch(ft) {
		case Policy:
			//tableName = "t_policy";
			standardColumns = PolicyColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_policy character set utf8 (";
			sql1 = "delete from t_policy where policy_date>=\"2013-01-01\" and form_no is null or policy_fee<=0;";
			sql2 = "update t_policy set attached_flag = 1 where attached_flag=0 and prod_name like \"中邮附加%\";";
			sql3 = "update t_policy set attached_flag = 2 where attached_flag=0 and prod_name like \"中邮禄禄通%\";";
			sql4 = "update t_policy set attached_flag = 3 where attached_flag=0 and prod_code=\"112004\";";
			sql12 = "update t_policy set attached_flag = 7 where attached_flag=0 and policy_fee=1 and prod_code in (\"125022\", \"112007\");";
			sql8 = "update t_policy set attached_flag = 8 where attached_flag=0 and prod_code in(\"125019\",\"121001\",\"121002\");";
			sql5 = "update t_policy tp inner join (select sum(policy_fee) as total_fee, policy_no from t_policy where total_fee=0 and TO_DAYS(NOW())=TO_DAYS(operate_time) group by policy_no) as tp2 set tp.total_fee=tp2.total_fee where tp.policy_no=tp2.policy_no and tp.total_fee=0 and tp.attached_flag=0 and TO_DAYS(NOW())=TO_DAYS(tp.operate_time);";
			sql6 = "update t_under_write uw,t_policy tp,t_bank_code bc set uw.net_name=bc.name where uw.policy_no=tp.policy_no and tp.bank_code=bc.cpi_code and uw.policy_no is not null and uw.net_name is null and TO_DAYS(NOW())=TO_DAYS(tp.operate_time);";
			sql7 = "update t_under_write as uw inner join t_policy tp on uw.form_no=tp.form_no set uw.policy_no=tp.policy_no,uw.sign_date=tp.policy_date where uw.policy_no is null and TO_DAYS(NOW())=TO_DAYS(tp.operate_time);";
			sql10 = "update t_policy t1, t_bank_code t2 set t1.bank_name=t2.name where t1.bank_code=t2.cpi_code and TO_DAYS(NOW())=TO_DAYS(t1.operate_time) and (t1.bank_name like '%邮政局%' or t1.bank_name='') and t1.prod_name not like '%禄禄通%';";
			sql11 = "update t_policy tp, t_policy_dtl tpd set tp.duration=tpd.duration where tp.policy_no=tpd.policy_no and tp.attached_flag=0 and tp.duration=1 and tp.duration<>tpd.duration and tpd.duration<>1 and TO_DAYS(NOW())=TO_DAYS(tp.operate_time);";
			sql13="update t_policy tp, t_organization org set tp.organ_name=org.short_name where tp.organ_name=org.name and TO_DAYS(NOW())=TO_DAYS(tp.operate_time);";
			
	        break;
	        /*
		case PolicyDtl:
			standardColumns = PolicyDtlColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_policy_dtl character set utf8 (";
			sql2 = "update t_policy tp, t_policy_dtl tpd, t_staff ts set tp.staff_flag=true,tp.duration=tpd.duration where tp.staff_flag=0 and tp.policy_no=tpd.policy_no and tpd.holder_card_num=ts.id_card;";
			sql3 = "update t_policy tp, t_policy_dtl tpd set tp.duration=tpd.duration where tp.policy_no=tpd.policy_no and tp.duration<>tpd.duration and tp.attached_flag=0 and tpd.duration<>1 and tp.duration=1;";
			sql1 = "delete from t_policy_dtl where prod_name like \"%附加%\";";
			break;
			*/
		case NewPolicyDtl:
			//tableName = "t_policy_dtl";
			standardColumns = PolicyDtlsColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_policy_dtl character set utf8 (";
			sql2 = "update t_policy tp, t_policy_dtl tpd, t_staff ts set tp.staff_flag=true,tp.duration=tpd.duration where tp.policy_no=tpd.policy_no and tp.staff_flag=0 and tpd.holder_card_num=ts.id_card and year(tp.policy_date)=ts.year and year(tp.policy_date)=year(now());";
			sql3 = "update t_policy tp, t_policy_dtl tpd set tp.duration=tpd.duration where tp.policy_no=tpd.policy_no and tp.duration=1 and tp.attached_flag=0 and tp.duration<>tpd.duration and tpd.duration<>1;";
			sql5 = "delete from t_policy_dtl where prod_name like \"中邮附加%\";";
			sql4 = "update t_policy_dtl set attached_flag=3 where attached_flag=0 and prod_code=\"112004\"";
			break;
			/*
		case Issue:
			standardColumns = IssueColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_issue character set utf8 (";
			sql1 = "update t_issue set issue_type=\"条款解释不清\" where issue_type like \"%条款解释不清%\";";
			break;
			*/
		case Inquire:
			//tableName = "t_inquire";
			standardColumns = InquireColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_inquire character set utf8 (";
			sql1 = "update t_inquire set policy_no=left(policy_nos,14) where policy_no is null;";
			sql2 = "update t_inquire set organ_name=gorgan_name where organ_name is null or organ_name=\"\";";
			sql3 = "update t_inquire set organ_name=deal_depart where (policy_no is null or policy_no=\"\") and (organ_name is null or organ_name=\"\");";
			sql4 = "update t_inquire set organ_name=left(organ_name,locate(\",\",organ_name)-1) where locate(\",\",organ_name)>0;";
			sql5 = "update t_inquire tiq,t_organization org set tiq.organ_name=org.name where tiq.organ_name=org.old_name;";
			break;
		case IssuePFR:
			//tableName = "t_issue";
			standardColumns = IssuePFRColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_issue character set utf8 (";
			sql2 = "update t_issue set should_date=ready_date where should_date is null or should_date<\"2000-11-01 09:00:00\";";
			sql3 = "update t_issue set issue_type=\"条款解释不清\" where issue_type like \"%条款解释不清%\";";
			sql4 = "update t_issue set issue_type=\"代签名\" where issue_type like \"%代签名%\";";
			sql1 = "delete from t_issue where bill_back_date<\"1900-01-01 00:00:01\";";
			break;
		case IssuePFRDeal:
			return dr;
		case CallFail:
			//tableName = "t_call_fail_list";
			standardColumns = IssueColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_call_fail_list character set utf8 (";
			sql1 = "update t_call_fail_list set finish_date=\"2015-01-01 00:00:00\" where finish_date<\"2000-11-01 09:00:00\";";
			break;
		case CallFailPFR:
			//tableName = "t_call_fail_list";
			standardColumns = IssuePFRColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_call_fail_list character set utf8 (";
			//sql1 = "update t_call_fail_list set finish_date=\"2015-01-01 00:00:00\" where finish_date<\"2000-11-01 09:00:00\";";
			break;
			/*
		case CallFailStatus:
			standardColumns = CallFailHQListColumn.getStandardColumns();
			return dr;
			*/
		case MiniCallFailStatus:
			return dr;
		case CallFailMailStatus:
			return dr;
		case CallFailCityStatus:
			return dr;
		case CallRatio:
			//tableName = "t_call_ratio";
			//firstsql = "delete from t_call_ratio where operate_time<CURRENT_DATE;";
			standardColumns = CallRatioColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_call_ratio character set utf8 (";
			break;
		case Renewed:
			//tableName = "t_renewed_list";
			standardColumns = RenewedColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_renewed_list character set utf8 (";
			sql1 = "update t_renewed_list t1, t_cs_report t2, t_policy t3 set t1.fee_status=\"已终止\" where t1.policy_no=t2.policy_no and t2.policy_no=t3.policy_no and t3.attached_flag=0 and t2.cs_code=\"CT\" and abs(t2.money)>(t3.total_fee*0.1);";
			sql2 = "update t_renewed_list set fee_status='交费成功',fee_fail_reason='' where fee_status<>\"交费成功\" and fee_status<>\"失效\" and policy_no in (select rel_no from t_pay_list where pay_type=2 and fail_desc like '%成功' and datediff(back_date,fee_date)>=0);";
			sql3 = "update t_policy t1, t_cs_report t2, t_pay_list t3 set t1.status=\"有效\" where t1.policy_no=t2.policy_no and t2.cs_code=\"RE\" and datediff(now(),t2.operate_time)=0 and t2.cs_no=t3.rel_no and t3.fail_desc=\"成功\";";
			sql4 = "update t_renewed_list t1, t_pay_list t2, t_policy t3 set t1.fee_status=\"交费失败\",t1.fee_fail_reason=t2.fail_desc where t1.policy_no=t2.rel_no and t2.rel_no=t3.policy_no and t2.fail_desc<>'成功' and datediff(t2.back_date,t1.fee_date)>=0;";
			break;
		case RenewedFeeRst:
			//tableName = "t_renewed_fee_rst";
			standardColumns = RenewedFeeRstColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_renewed_fee_rst character set utf8 (";
			sql1 = "update t_renewed_list trl, t_renewed_fee_rst trfr set trl.give_flag='需充值' where trl.policy_no=trfr.policy_no and datediff(trfr.rst_date, trfr.req_date)<=7;";
			sql2 = "update t_renewed_list t1, t_cs_report t2, t_policy t3 set t1.fee_status=\"已终止\" where t1.policy_no=t2.policy_no and t2.policy_no=t3.policy_no and t3.attached_flag=0 and t2.cs_code=\"CT\" and abs(t2.money)>(t3.total_fee*0.1);";
			sql3 = "update t_renewed_list set fee_status='交费成功',fee_fail_reason='' where fee_status<>\"交费成功\" and fee_status<>\"失效\" and policy_no in (select rel_no from t_pay_list where pay_type=2 and fail_desc like '%成功' and datediff(back_date,fee_date)>=0);";
			sql4 = "update t_policy t1, t_cs_report t2, t_pay_list t3 set t1.status=\"有效\" where t1.policy_no=t2.policy_no and t2.cs_code=\"RE\" and datediff(now(),t2.operate_time)=0 and t2.cs_no=t3.rel_no and t3.fail_desc=\"成功\";";
			sql5 = "update t_renewed_list t1, t_pay_list t2, t_policy t3 set t1.fee_status=\"交费失败\",t1.fee_fail_reason=t2.fail_desc where t1.policy_no=t2.rel_no and t2.rel_no=t3.policy_no and t2.fail_desc<>'成功' and datediff(t2.back_date,t1.fee_date)>=0;";
			sql6 = "update t_renewed_list t1, t_cs_report t2, t_pay_list t3 set t1.fee_status=\"交费成功\", t1.fee_fail_reason='' where t1.policy_no=t2.policy_no and t2.cs_code=\"RE\" and datediff(t3.back_date,t2.cs_date)>=0 and t2.cs_date>t1.fee_date and t2.cs_no=t3.rel_no and t3.fail_desc=\"成功\";";
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
			//tableName = "t_check_write";
			standardColumns = CheckColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_check_write character set utf8 (fix_status, ";
			sql2 = "update t_check_write set need_fix=\"要整改\" where key_info is not null and length(key_info)>0 and fix_status<>\"ElseStatus\" and need_fix=\"不要整改\";";
			sql3 = "update t_check_write cw, t_policy tp set cw.form_no=tp.form_no where cw.policy_no=tp.policy_no and tp.attached_flag=0 and cw.form_no is null and need_fix=\"要整改\";";
			sql1 = "delete from t_check_write where policy_no not in (select policy_no from t_policy);";
			break;
		case CheckRecord:
			//tableName = "t_check_record";
			standardColumns = CheckColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_check_record character set utf8 (fix_status, ";
			sql1 = "update t_check_record set need_fix=\"要整改\" where key_info is not null and length(key_info)>0 and fix_status<>\"ElseStatus\";";
			break;
		case CheckCityBack:
			standardColumns = CheckCityBackColumn.getStandardColumns();
			return dr;
		case PayToFailList:
			//tableName = "t_pay_list";
			firstsql = "update t_pay_list set status=\"CloseStatus\" where pay_type=" + PayList.PAY_TO + " and operate_time<CURRENT_DATE and fee_type not in ('保全受理号','保单号');";
			standardColumns = PayFailListColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_pay_list character set utf8 (pay_type, status, ";
			//sql2 = "update t_renewed_list t1, t_pay_list t2, t_policy t3 set t1.fee_status=\"交费失败\",t1.fee_fail_reason=t2.fail_desc where t1.policy_no=t2.rel_no and t2.rel_no=t3.policy_no and t2.fail_desc<>'成功' and datediff(t2.back_date,t1.fee_date)>=0;";
			sql3 = "update t_pay_list set success_flag=true where fail_desc=\"成功\";";
			break;
		case PayFromFailList:
			//tableName = "t_pay_list";
			firstsql = "update t_pay_list set status=\"CloseStatus\" where pay_type=" + PayList.PAY_FROM + " and operate_time<CURRENT_DATE and fee_type not in ('保全受理号','保单号');";
			standardColumns = PayFailListColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_pay_list character set utf8 (pay_type, status, ";
			//sql2 = "update t_renewed_list t1, t_pay_list t2, t_policy t3 set t1.fee_status=\"交费失败\",t1.fee_fail_reason=t2.fail_desc where t1.policy_no=t2.rel_no and t2.rel_no=t3.policy_no and t2.fail_desc<>'成功' and datediff(t2.back_date,t1.fee_date)>=0;";
			//sql3 = "update t_renewed_list t1, t_cs_report t2, t_policy t3 set t1.fee_status=\"已终止\" where t1.policy_no=t2.policy_no and t2.policy_no=t3.policy_no and t3.attached_flag=0 and t2.cs_code=\"CT\" and abs(t2.money)>(t3.total_fee*0.1);";
			//sql4 = "update t_renewed_list t1, t_cs_report t2, t_pay_list t3 set t1.fee_status=\"交费成功\", t1.fee_fail_reason='' where t1.policy_no=t2.policy_no and t2.cs_code=\"RE\" and datediff(t3.back_date,t2.cs_date)>=0 and t2.cs_date>t1.fee_date and t2.cs_no=t3.rel_no and t3.fail_desc=\"成功\";";
			sql5 = "update t_pay_list set success_flag=true where fail_desc=\"成功\";";
			break;
		case PaySuccessList:
			//tableName = "t_pay_list";
			//firstsql = "update t_pay_list set status=\"CloseStatus\" where fail_desc<>\"成功\" and operate_time<CURRENT_DATE and fee_type<>'保全受理号'; ";
			standardColumns = PayFailListColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_pay_list character set utf8 (pay_type, status, ";
			sql1 = "update t_pay_list t1,(select rel_no,back_date from t_pay_list where fail_desc=\"成功\") t2 set t1.status='CloseStatus' where t1.rel_no=t2.rel_no and datediff(t2.back_date,t1.back_date)>0 and t1.status<>'CloseStatus' and t1.status<>\"成功\";";
			//sql2 = "update t_renewed_list set fee_status='交费成功', fee_fail_reason='' where fee_status<>\"交费成功\" and fee_status<>\"失效\" and policy_no in (select rel_no from t_pay_list where pay_type=2 and fail_desc like '%成功' and datediff(back_date,fee_date)>=0);";
			//sql3 = "update t_renewed_list t1, t_cs_report t2, t_policy t3 set t1.fee_status=\"已终止\" where t1.policy_no=t2.policy_no and t2.policy_no=t3.policy_no and t3.attached_flag=0 and t2.cs_code=\"CT\" and abs(t2.money)>(t3.total_fee*0.1);";
			//sql4 = "update t_renewed_list t1, t_cs_report t2, t_pay_list t3 set t1.fee_status=\"交费成功\", t1.fee_fail_reason='' where t1.policy_no=t2.policy_no and t2.cs_code=\"RE\" and datediff(t3.back_date,t2.cs_date)>=0 and t2.cs_date>t1.fee_date and t2.cs_no=t3.rel_no and t3.fail_desc=\"成功\";";
			sql5 = "update t_pay_list set success_flag=true where fail_desc=\"成功\";";
			break;
		case PayFromSuccessList:
			//tableName = "t_pay_list";
			//firstsql = "update t_pay_list set status=\"CloseStatus\" where fail_desc<>\"成功\" and operate_time<CURRENT_DATE and fee_type<>'保全受理号'; ";
			standardColumns = PayFailListColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_pay_list character set utf8 (pay_type, status, ";
			sql1 = "update t_pay_list t1,(select rel_no,back_date from t_pay_list where fail_desc=\"成功\") t2 set t1.status='CloseStatus' where t1.rel_no=t2.rel_no and datediff(t2.back_date,t1.back_date)>0 and t1.status<>'CloseStatus' and t1.status<>\"成功\";";
			//sql2 = "update t_renewed_list set fee_status='交费成功', fee_fail_reason='' where fee_status<>\"交费成功\" and policy_no in (select rel_no from t_pay_list where pay_type=2 and fail_desc like '%成功' and datediff(back_date,fee_date)>=0);";
			//sql3 = "update t_renewed_list t1, t_cs_report t2, t_policy t3 set t1.fee_status=\"已终止\" where t1.policy_no=t2.policy_no and t2.policy_no=t3.policy_no and t3.attached_flag=0 and t2.cs_code=\"CT\" and abs(t2.money)>(t3.total_fee*0.1);";
			//sql4 = "update t_renewed_list t1, t_cs_report t2, t_pay_list t3 set t1.fee_status=\"交费成功\", t1.fee_fail_reason='' where t1.policy_no=t2.policy_no and t2.cs_code=\"RE\" and datediff(t3.back_date,t2.cs_date)>=0 and t2.cs_date>t1.fee_date and t2.cs_no=t3.rel_no and t3.fail_desc=\"成功\";";
			sql5 = "update t_pay_list set success_flag=true where fail_desc=\"成功\";";
			break;
		case UnderWriteSentData:
			//tableName = "t_policy_reprint_dtl";
			standardColumns = PolicySentDataColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_policy_reprint_dtl character set utf8 (";
			sql1 = "update t_cs_reissue t1,t_policy_reprint_dtl t2,t_cs_report t3 set t1.prov_express_no=t2.ems_no,t1.prov_sent_date=t2.print_date where t1.cs_id=t3.id and t2.policy_no=t3.policy_no and t2.print_date>=t3.cs_date;";
			sql2 = "update t_under_write tuw, t_policy_reprint_dtl tprd set tuw.prov_ems_no=tprd.ems_no,tuw.status=\"SendStatus\" where tuw.policy_no=tprd.policy_no and tuw.prov_ems_no is null and tuw.status<>\"CloseStatus\";";
			sql3 = "update t_under_write set status=\"CloseStatus\" where bill_back_date is not null;";
			break;
		case UnderWriteData:
			//tableName = "t_under_write";
			standardColumns = UnderWriteColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_under_write character set utf8 (";
			sql1 = "delete from t_under_write where form_no is null or ybt_date is null;";
			break;
		case UWDtlData:
			//tableName = "t_under_write";
			standardColumns = UWDtlColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_under_write character set utf8 (";
			sql1 = "delete from t_under_write where form_no is null or ybt_date is null;";
			sql2 = "update t_under_write set sys_date=check_date where sys_date<\"1900-01-01\";";
			sql3 = "update t_under_write set client_receive_date=null,bill_back_date=null where bill_back_date<\"1900-01-01\";";
			sql4 = "update t_under_write set body_check_date1=null,body_check_date2=null,deal_check_date1=null,deal_check_date2=null where holder is null;";
			sql5 = "update t_under_write uw, t_policy_dtl pd set uw.holder=cast(aes_decrypt(unhex(pd.holder), 'GDPost') as char(100)),uw.insured=cast(aes_decrypt(unhex(pd.insured), 'GDPost') as char(100)),uw.relation=pd.relation where uw.policy_no=pd.policy_no and uw.holder is null;";
			sql6 = "update t_under_write uw, t_prd prd set uw.product_id=prd.id where uw.product_id is null and uw.prd_name=prd.prd_full_name;";
			sql7 = "update t_under_write uw, t_organization org set uw.organ_id=org.id where uw.organ_id is null and uw.organ_name=org.name;";
			sql8 = "update t_under_write set sys_date=check_date where sys_date is null and check_date is not null;";
			sql9 = "update t_under_write set bill_back_date=client_receive_date where bill_back_date is null and client_receive_date is not null;";
			break;
		case UnderWriteDtlData:
			standardColumns = UnderWriteDtlColumn.getStandardColumns();
			return dr;
		case UnderWriteRemark:
			standardColumns = UnderWriteRemarkColumn.getStandardColumns();
			return dr;
		case ConversationReq:
			//tableName = "t_mtd_req";
			standardColumns = ConversationReqColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_mtd_req character set utf8 (req_type, ";
			sql1 = "delete from t_mtd_req where policy_no is null or policy_no=\"\";";
			break;
		case ConversationReport:
			//tableName = "t_cs_report";
			standardColumns = CsReportColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_cs_report character set utf8 (";
			/*sql1 = "update t_renewed_list t0 set t0.fee_status='交费成功' where t0.fee_status<>\"交费成功\" "
					+ "and t0.policy_no in (select t2.policy_no from t_pay_list t1, t_cs_report t2 "
					+ "where t1.pay_type=2 and t0.policy_fee=t1.money and t1.rel_no=t2.cs_no and t2.cs_code='RE' and datediff(now(),t2.operate_time)=0);";*/
			sql1 = "update t_cs_report set cs_code=left(full_cs_code,2) where (cs_code is null or cs_code=\"\") and full_cs_code<>\"\";";
			sql2 = "update t_call_fail_list t1, t_cs_report t2, t_policy t3 set t1.status=\"已退保\" where t1.policy_no=t2.policy_no and t2.policy_no=t3.policy_no and t3.attached_flag=0 and t2.cs_code=\"CT\" and abs(t2.money)>(t3.total_fee*0.1);";
			sql3 = "update t_cs_report tsr, t_policy_dtl tpd, t_staff ts set tsr.staff_flag=true where tsr.policy_no=tpd.policy_no and tpd.holder_card_num=ts.id_card and tsr.staff_flag=0 and year(tpd.policy_date)=ts.year and year(tpd.policy_date)=year(now());";
			sql4 = "update t_policy tp, t_cs_report tcr set tp.cs_flag=2,tp.status=\"终止\" where tp.policy_no=tcr.policy_no and tcr.full_cs_code=\"CT退保\" and tp.cs_flag=0 and tp.attached_flag=0 and tp.status<>\"终止\" and abs(tcr.money)>500;";
			sql5 = "update t_issue t1, t_cs_report t2, t_policy t3 set t1.status=\"已退保\" where t1.policy_no=t2.policy_no and t2.policy_no=t3.policy_no and t2.cs_code=\"CT\" and t3.attached_flag=0 and t1.status<>\"已结案\" and abs(t2.money)>(t3.total_fee*0.1);";
			//sql6 = "update t_renewed_list t1, t_cs_report t2, t_policy t3 set t1.fee_status=\"已终止\" where t1.policy_no=t2.policy_no and t2.policy_no=t3.policy_no and t3.attached_flag=0 and t2.cs_code=\"CT\" and abs(t2.money)>(t3.total_fee*0.1);";
			//sql7 = "update t_renewed_list t1, t_cs_report t2, t_pay_list t3 set t1.fee_status=\"交费成功\", t1.fee_fail_reason='' where t1.policy_no=t2.policy_no and t2.cs_code=\"RE\" and datediff(t3.back_date,t2.cs_date)>=0 and t2.cs_date>t1.fee_date and t2.cs_no=t3.rel_no and t3.fail_desc=\"成功\";";
			sql8 = "update t_policy t1, t_cs_report t2, t_pay_list t3 set t1.status=\"有效\" where t1.policy_no=t2.policy_no and t2.cs_code=\"RE\" and datediff(now(),t2.operate_time)=0 and t2.cs_no=t3.rel_no and t3.fail_desc=\"成功\";";
			sql9 = "update t_policy tp, t_cs_report tcr set tp.cs_flag=1,tp.status=\"终止\",tp.cs_date=tcr.cs_date where tp.policy_no=tcr.policy_no and tcr.full_cs_code=\"CT犹撤\" and tp.attached_flag=0 and tp.cs_flag<>1 and abs(tcr.money)>500;";
			sql10 = "update t_cs_expire ce, t_cs_report tcr set ce.status=\"AGStatus\",ce.cs_date=tcr.cs_date where ce.policy_no=tcr.policy_no and tcr.full_cs_code=\"AG满期给付\";";
			sql11 = "update t_cs_expire ce, t_cs_report tcr set ce.status=\"CTStatus\",ce.cs_date=tcr.cs_date where ce.policy_no=tcr.policy_no and tcr.cs_code=\"CT\";";
			sql12 = "INSERT INTO t_cs_reissue (cs_id) select tcr.id from t_cs_report tcr where tcr.cs_code=\"LR\" and tcr.id not in (select cs_id from t_cs_reissue);";
			sql13 = "update t_policy tp, t_cs_report tcr set tp.status=\"满期终止\",tp.cs_date=tcr.cs_date where tp.policy_no=tcr.policy_no and tcr.full_cs_code=\"AG满期给付\";";
			break;
		case CsLoan:
			//tableName = "t_cs_loan";
			standardColumns = CsLoanColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_cs_loan character set utf8 (";
			sql1 = "update t_cs_loan set flag=case when DATEDIFF(NOW(),should_date)>1 then '2' when  DATEDIFF(NOW(),should_date)>-30 then '1' else '0' end;";
			break;
		case CsExpire:
			//tableName = "t_cs_expire";
			standardColumns = CsExpireColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_cs_expire character set utf8 (";
			sql1 = "update t_cs_expire set holder_card_num=(REPLACE(holder_card_num, '\"', '')) where locate('\"',holder_card_num)>0;";
			sql2 = "update t_cs_expire set insured_card_num=(REPLACE(insured_card_num, '\"', '')) where locate('\"',insured_card_num)>0;";
			sql3 = "update t_cs_expire set holder_mobile=(REPLACE(holder_mobile, '\"', '')) where locate('\"',holder_mobile)>0;";
			sql4 = "update t_cs_expire set holder_phone=(REPLACE(holder_phone, '\"', '')) where locate('\"',holder_phone)>0;";
			sql5 = "update t_cs_expire ce, t_cs_report tcr set ce.status=\"AGStatus\",ce.cs_date=tcr.cs_date where ce.policy_no=tcr.policy_no and tcr.full_cs_code=\"AG满期给付\";";
			sql6 = "update t_cs_expire ce, t_cs_report tcr set ce.status=\"CTStatus\",ce.cs_date=tcr.cs_date where ce.policy_no=tcr.policy_no and tcr.cs_code=\"CT\";";
			break;
		case DocNotScanDtl:
			//tableName = "t_doc_not_scan_dtl";
			firstsql = "delete from t_doc_not_scan_dtl where operate_time<CURRENT_DATE;";
			standardColumns = DocNotScanDtlColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_doc_not_scan_dtl character set utf8 (";
			sql1 = "update t_doc_not_scan_dtl t1, t_organization org set t1.organ_name=org.short_name where t1.organ_name=org.name;";
			break;
		case CallFailCloseStatus:
			break;
		case CallFailMailBackStatus:
			break;
		case CallFailMailSuccessStatus:
			break;
		case CallFailMiniCityStatus:
			break;
		case CallFailNeedDoorStatus:
			break;
		case CallFailPhoneStatus:
			break;
		case PolicyBackDate:
			//tableName = "t_uw_back";
			standardColumns = PolicyBackDateColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_uw_back character set utf8 (";
			//sql1 = "update t_cs_loan set flag=case when DATEDIFF(NOW(),should_date)>1 then '2' when  DATEDIFF(NOW(),should_date)>-30 then '1' else '0' end;";
			break;
		case PolicyUnderWrite:
			break;
		case RenewedCityList:
			break;
		case RenewedFeeMatchList:
			break;
		case RenewedProvList:
			break;
		case UnderWriteInsured:
			break;
		case StatCity:
			standardColumns = StasticsCityColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_stastics_city character set utf8 (";
			//sql1 = "update t_cs_loan set flag=case when DATEDIFF(NOW(),should_date)>1 then '2' when  DATEDIFF(NOW(),should_date)>-30 then '1' else '0' end;";
			break;
		case StatArea:
			standardColumns = StasticsAreaColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE t_stastics_area character set utf8 (";
			//sql1 = "update t_cs_loan set flag=case when DATEDIFF(NOW(),should_date)>1 then '2' when  DATEDIFF(NOW(),should_date)>-30 then '1' else '0' end;";
			break;
		case ClaimsCloseReport:
			standardColumns = ClaimsCloseReportColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_claims_close_report character set utf8 (";
			sql1 = "update t_claims_close_report set policy_no=left(policy_nos,14) where policy_no is null;";
			sql2 = "update t_settlement ts,t_claims_close_report tccr set ts.case_status=\"已结案\",ts.case_end_date=tccr.close_date where ts.claims_no=tccr.claims_no and ts.case_end_date is null;";
			break;
		case Settlement:
			standardColumns = SettlementColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' IGNORE INTO TABLE t_settlement character set utf8 (";
			sql1 = "update t_settlement set reporte_date=date_add(operate_time,INTERVAL -1 DAY) where reporte_date is null;";
			sql2 = "update t_settlement set claims_type=\"1\" where claims_type is null and organ_code in(\"8644\",\"864400\");";
			sql3 = "update t_settlement set claims_type=\"0\" where claims_type is null and organ_code not in(\"8644\",\"864400\");";
			break;
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
        String sDate = null;
        String eDate = null;
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
    			builder.append(PayList.PAY_TO);
	            builder.append('\t');
	            builder.append(FEE_FAIL_STATUS.NewStatus.name());
	            builder.append('\t');
    		} else if(ft.name().equals(FileTemplate.PaySuccessList.name())) {
    			builder.append(PayList.PAY_TO);
	            builder.append('\t');
	            builder.append(FEE_FAIL_STATUS.NewStatus.name());
	            builder.append('\t');
    		} else if(ft.name().equals(FileTemplate.PayFromSuccessList.name())) {
    			builder.append(PayList.PAY_FROM);
	            builder.append('\t');
	            builder.append(FEE_FAIL_STATUS.NewStatus.name());
	            builder.append('\t');
    		} else if(ft.name().equals(FileTemplate.PayFromFailList.name())) {
    			builder.append(PayList.PAY_FROM);
	            builder.append('\t');
	            builder.append(FEE_FAIL_STATUS.NewStatus.name());
	            builder.append('\t');
    		} else if(ft.name().equals(FileTemplate.ConversationReq.name())) {
    			builder.append(1);
	            builder.append('\t');
    		}
        	
        	for(ColumnItem item : standardColumns) {
        		cell = StringUtil.trimStr(row.getValue(item.getDisplayName()), true);
        		
        		if(item.getDisplayName().contains("日期") && cell != null && StringUtil.trimStr(cell).length()<=0) {
    				log.debug(item.getDisplayName() + "----导入日期有问题， 重置为上传当日日期: " + cell);
    	            builder.append("NULL\t");
    	            continue;
        		}
        		
        		if(ft.name().equals(FileTemplate.ConversationReport.name())) {
        			if(item.getDisplayName().equals("金额") && cell != null && StringUtil.trimStr(cell).length()<=0) {
	    	            builder.append("0\t");
	    	            continue;
        			}
        		}
        		if(ft.name().equals(FileTemplate.CallFail.name())/* || ft.name().equals(FileTemplate.Issue.name())*/) {
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
        		
        		if(ft.name().equals(FileTemplate.Inquire.name())) {
        			if(item.getDisplayName().equals("完成时间") && cell != null && StringUtil.trimStr(cell).length()<=0) {
        				log.debug("----------- 处理z咨询工单的完成时间: " + cell);
        				Date d = StringUtil.dateAdd(new Date(), 5);
        				builder.append(StringUtil.date2Str(d, "yyyy-MM-dd HH:mm:ss") + "\t");
        	            continue;
        			}
        		}
        		
        		if(ft.name().equals(FileTemplate.CallRatio.name()) && (sDate==null || eDate == null)) {
        			if(item.getDisplayName().equals("开始时间")) {
        				sDate = cell;
        			}
        			if(item.getDisplayName().equals("结束时间")) {
        				eDate = cell;
        				firstsql = "delete from t_call_ratio where s_date>=\"" + sDate + "\" and e_date<=\"" + eDate + "\";";
        			}
        		}
        		builder.append(cell);
	            builder.append('\t');
        	}
            builder.append(member_id);
            builder.append('\n');
            //log.debug("---000--" + builder.toString());
        }
        
        log.debug("------111-----" + builder.toString());
        
        log.debug("------222-----" + strStatementText);
        
        InputStream is = null;
        try {
			is = IOUtils.toInputStream(builder.toString(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			dr.setFlag(false);
			dr.setMsg(e.getMessage());
		}
        
        //再执行
        try {
        	log.info("----------first import ready to execute sql1：" + firstsql);
        	if(firstsql != null) {
        		statement.execute(firstsql);
        	}
        	//statement.execute("ALTER TABLE " + tableName + " DISABLE KEYS;");
        	log.info("--------- ready to exec import sql");
        	statement.setLocalInfileInputStream(is);
			//statement.execute(strStatementText);
        	int updateRow = statement.executeUpdate(strStatementText);
        	dr.setUpdateRow(updateRow);
        	log.info("---- exec import end ---");
        	
			if(sql1 != null) {
				log.info("----------import ready to execute sql1：" + sql1);
        		statement.executeUpdate(sql1);
        	}
			
			if(sql2 != null) {
				log.info("---------- import  ready to execute sql2：" + sql2);
        		statement.executeUpdate(sql2);
        	}
			
			if(sql3 != null) {
				log.info("----------import ready to execute sql3：" + sql3);
        		statement.executeUpdate(sql3);
        	}
			
			if(sql4 != null) {
				log.info("----------import ready to execute sql4：" + sql4);
        		statement.executeUpdate(sql4);
        	}
			
			if(sql5 != null) {
				log.info("----------import ready to execute sql5：" + sql5);
        		statement.executeUpdate(sql5);
        	}
			
			if(sql6 != null) {
				log.info("----------import ready to execute sql6：" + sql6);
        		statement.executeUpdate(sql6);
        	}
			
			if(sql7 != null) {
				log.info("----------import ready to execute sql7：" + sql7);
        		statement.executeUpdate(sql7);
        	}
			
			if(sql8 != null) {
				log.info("----------import ready to execute sql8：" + sql8);
        		statement.executeUpdate(sql8);
        	}
			
			if(sql9 != null) {
				log.info("----------import ready to execute sql9：" + sql9);
        		statement.executeUpdate(sql9);
        	}
			
			if(sql10 != null) {
				log.info("----------import ready to execute sql10：" + sql10);
        		statement.executeUpdate(sql10);
        	}
			
			if(sql11 != null) {
				log.info("----------import ready to execute sql11：" + sql11);
        		statement.executeUpdate(sql11);
        	}
			
			if(sql12 != null) {
				log.info("----------import ready to execute sql12：" + sql12);
        		statement.executeUpdate(sql12);
        	}
			
			if(sql13 != null) {
				log.info("----------import ready to execute sql13：" + sql13);
        		statement.executeUpdate(sql13);
        	}
			//statement.execute("ALTER TABLE " + tableName + " ENABLE KEYS;");
			log.info("----------import finish execute sql");
			dr.setFlag(true);
		} catch (Exception e) {
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
		String strKey = com.gdpost.web.MySQLAESKey.AESKey;
		java.sql.Connection connection = null;
		JdbcStatement statement = null;
		//com.mysql.cj.jdbc.ServerPreparedStatement statement = null;
		try {
			Object objDataSource = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");
			DruidDataSource dataSource = (DruidDataSource)objDataSource;
			connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
			statement = (JdbcStatement) connection.createStatement();
			//statement = (com.mysql.cj.jdbc.ServerPreparedStatement)connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			dr.setFlag(false);
			dr.setMsg(e.getMessage());
		}
		
		List<ColumnItem> standardColumns = null;
		StringBuffer sql = null;
		StringBuffer line = null;
		//String tableName = null;
		String sql2 = null;
		String sql3 = null;
		String sql4 = null;
		String sql5 = null;
		String sql6 = null;
		String sql7 = null;
		String sql8 = null;
		boolean isFail = false;
		//boolean updateRst = true;
		Object val = null;
		switch(ft) {
		case Policy:
			return dr;
		//case PolicyDtl:
			//return dr;
		case NewPolicyDtl:
			return dr;
		//case Issue:
			//return dr;
		case Inquire:
			return dr;
		case IssuePFRDeal:
			//tableName = "t_issue";
			standardColumns = IssuePFRDealColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_issue(issue_no, policy_no, status, result, deal_man, deal_time) VALUES ");
			line = null;
			for (DataRow row : dt.Rows) {
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		if(item.getDisplayName().contains("日期")) {
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
			sql.append(" ON DUPLICATE KEY UPDATE status=VALUES(status), result=VALUES(result), ");
			sql.append(" deal_man=VALUES(deal_man), deal_time=VALUES(deal_time);");
			//sql.append("deal_desc=VALUES(deal_desc);");
			
			log.debug("----------------batch update : " + sql);
			sql2 = "delete from t_issue where issue_no is null or policy_no is null or issue_desc is null;";
			break;
		case CallFail:
			return dr;
			/*
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
	        		} else if(item.getDisplayName().contains("日期")) {
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
			sql2 = "delete from t_call_fail_list where issue_no is null;";
			break;
			*/
		case MiniCallFailStatus:
			//tableName = "t_call_fail_list";
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
	        		} else if(item.getDisplayName().contains("日期")) {
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
			sql3 = "update t_call_fail_list set status = \"重点跟进\" where status='二访失败' and 15-datediff(now(),bill_back_date)<=3;";
			sql4 = "update t_call_fail_list set status = \"重点跟进\" where status='二访失败' and (hq_deal_type_else like '%电话错误%' or hq_deal_type_else like '%挂断%' or hq_deal_type_else like '%拒接%' or hq_deal_type_else like '%不配合%' or hq_deal_type_else like '%过期%' or hq_deal_type_else like '%空号%' or hq_deal_type_else like '%无法联系本人%' or hq_deal_type_else like '%不信任%');";
			sql5 = "update t_call_fail_list set status=\"上门成功\" where org_deal_flag=1 and (status=\"重点跟进\" or status=\"需上门回访\");";
			sql6 = "update t_call_fail_list set hq_deal_flag = 1 where status='二访成功';";
			break;
		case CallFailMiniCityStatus:
			//tableName = "t_call_fail_list";
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
	        		} else if(item.getDisplayName().contains("日期")) {
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
			sql4 = "update t_call_fail_list t1 set status=\"需上门回访\" where TO_DAYS(NOW())-TO_DAYS(bill_back_date)>15 and (status=\"二访失败\" or status=\"重点跟进\");";
			sql5 = "update t_call_fail_list set status = \"重点跟进\" where status='二访失败' and (hq_deal_type_else like '%电话错误%' or hq_deal_type_else like '%挂断%' or hq_deal_type_else like '%拒接%' or hq_deal_type_else like '%不配合%' or hq_deal_type_else like '%过期%' or hq_deal_type_else like '%空号%' or hq_deal_type_else like '%无法联系本人%' or hq_deal_type_else like '%不信任%');";
			sql6 = "update t_call_fail_list set status=\"上门成功\" where org_deal_flag=1 and (status=\"重点跟进\" or status=\"需上门回访\");";
			sql7 = "update t_call_fail_list set hq_deal_flag = 1 where status='二访成功';";
			break;
		case CallFailCityStatus:
			//tableName = "t_call_fail_list";
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
	        		} else if(item.getDisplayName().contains("日期")) {
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
			sql6 = "update t_call_fail_list set hq_deal_flag = 1 where status='二访成功';";
			break;
		case CallFailMailStatus:
			//tableName = "t_call_fail_list";
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
	        		} else if(item.getDisplayName().contains("日期")) {
	        			if(val == null || val.toString().trim().length() <= 0) {
	        				line.append("null,");
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
			//tableName = "t_call_fail_list";
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
	        		} else if(item.getDisplayName().contains("日期")) {
	        			if(val == null || val.toString().trim().length() <= 0) {
	        				line.append("null,");
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
			//tableName = "t_call_fail_list";
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
	        		} else if(item.getDisplayName().contains("日期")) {
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
			sql3 = "update t_call_fail_list set status=\"信函成功\" where has_letter=\"信函成功\" and (status=\"上门成功\" or status=\"需上门回访\" or status=\"上门失败\" or status=\"拒访\" or status=\"重点跟进\" or status=\"二访失败\" or status=\"已结案\");";
			break;
		case CallFailNeedDoorStatus:
			//tableName = "t_call_fail_list";
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
			sql3 = "update t_call_fail_list t1 set status=\"需上门回访\" where TO_DAYS(NOW())-TO_DAYS(bill_back_date)>15 and (status=\"二访失败\" or status=\"重点跟进\");";
			sql4 = "update t_call_fail_list set status=\"上门成功\" where org_deal_flag=1 and (status=\"重点跟进\" or status=\"需上门回访\");";
			sql5 = "update t_call_fail_list set hq_deal_flag = 1 where status='二访成功';";
			break;
		case CallFailPhoneStatus:
			//tableName = "t_call_fail_list";
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
			//tableName = "t_call_fail_list";
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
		case CallRatio:
			return dr;
		case Renewed:
			return dr;
		case RenewedFeeRst:
			return dr;
		case RenewedStatus://继续率清单
			//tableName = "t_renewed_list";
			standardColumns = RenewedStatusColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_renewed_list(policy_no, prd_name, policy_year, fee_date, fee_status, fee_fail_reason) VALUES ");
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
	        				//line.append("\"" + XQ_STATUS.DeadStatus.getDesc() + "\",");
	        				line.append("\"" + StringUtil.trimStr(val, true) + "\",");//关闭空白失败原因被置为退保的状态条件，//by 廖宇 2017-11-22
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
			sql5 = "update t_renewed_list t1, t_cs_report t2, t_policy t3 set t1.fee_status=\"已终止\" where t1.policy_no=t2.policy_no and t2.policy_no=t3.policy_no and t3.attached_flag=0 and t2.cs_code=\"CT\" and abs(t2.money)>(t3.total_fee*0.1);";
			sql6 = "update t_renewed_list set fee_status='交费成功', fee_fail_reason='' where fee_status<>\"交费成功\" and fee_status<>\"失效\" and policy_no in (select rel_no from t_pay_list where pay_type=2 and fail_desc like '%成功' and datediff(back_date,fee_date)>=0);";
			sql7 = "update t_policy t1, t_cs_report t2, t_pay_list t3 set t1.status=\"有效\" where t1.policy_no=t2.policy_no and t2.cs_code=\"RE\" and datediff(now(),t2.operate_time)=0 and t2.cs_no=t3.rel_no and t3.fail_desc=\"成功\";";
			sql8 = "update t_renewed_list t1, t_pay_list t2, t_policy t3 set t1.fee_status=\"交费失败\",t1.fee_fail_reason=t2.fail_desc where t1.policy_no=t2.rel_no and t2.rel_no=t3.policy_no and t2.fail_desc<>'成功'  and datediff(t2.back_date,t1.fee_date)>=0;";
			break;
		case RenewedHQList://总部的催收
			//tableName = "t_renewed_list";
			standardColumns = RenewedHQListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_renewed_list(policy_no, prd_name, hq_issue_type, hq_deal_rst, hq_deal_date, hq_deal_remark) VALUES ");
			line = null;
			isFail = false;
			val = null;
			String tmpName = null;
			for (DataRow row : dt.Rows) {
				isFail = false;
				val = null;
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		if(item.getDisplayName().equals("回访结果")) {
	        			if(val == null || val.toString().length() <= 0) {
	        				line.append("\"已告知\",");
	        			} else {
	        				line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        			}
	        		} else if(item.getDisplayName().equals("主险名称")) {
	        			if(val == null || val.toString().length() <= 0) {
	        				line.append("null,");
	        			} else {
	        				tmpName = StringUtil.trimStr(val, true);
	        				tmpName = tmpName.replace("主险-", "");
	        				line.append("\"" + tmpName + "\",");
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
		case RenewedProvList:
			//tableName = "t_renewed_list";
			standardColumns = RenewedProvListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_renewed_list(policy_no, prd_name, policy_year, prov_deal_date, prov_deal_rst, prov_issue_type, prov_deal_remark, prov_activity) VALUES ");
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
			sql.append("prov_deal_date=VALUES(prov_deal_date), prov_deal_rst=VALUES(prov_deal_rst), prov_deal_remark=VALUES(prov_deal_remark), prov_issue_type=VALUES(prov_issue_type), prov_activity=VALUES(prov_activity);");
			log.debug("----------------prov update status batch sql : " + sql);
			sql2 = "delete from t_renewed_list where holder is null";
			sql3 = "update t_renewed_list t1, t_renewed_list t2 set t1.prov_deal_date=t2.prov_deal_date,t1.prov_deal_rst=t2.prov_deal_rst where t1.prd_name=\"中邮附加重大疾病保险\" and t1.policy_no=t2.policy_no and t2.prov_deal_rst is not null;";
			break;
		case RenewedFeeMatchList:
			//tableName = "t_renewed_list";
			standardColumns = RenewedFeeMatchColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_renewed_list(policy_no, prd_name, policy_year, fee_date, mobile, policy_fee, fee_match, prov_deal_date, prov_issue_type, prov_deal_rst, prov_deal_remark, give_fee) VALUES ");
			line = null;
			isFail = false;
			val = null;
			String phone;
			Double policyFee;
			String feeMatch;
			for (DataRow row : dt.Rows) {
				isFail = false;
				val = null;
				phone = null;
				policyFee = null;
				feeMatch = null;
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		if(item.getDisplayName().contains("一访时间")) {
	        			if(val == null || val.toString().trim().length() <= 0) {
	        				line.append("NULL,");
	        			} else {
	        				line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        			}
	        		} else {
	        			line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        		}
	        		//TODO renewed give fee
	        		if(item.getDisplayName().equals("手机号码")) {
	        			phone = ((val==null || val.toString().trim().length()<=10)?"":val.toString().substring(0, 3));
	        		}
	        		if(item.getDisplayName().equals("应收保费")) {
	        			policyFee = Double.valueOf(val == null?"0":val.toString());
	        		}
	        		if(item.getDisplayName().equals("匹配结果")) {
	        			feeMatch = (String) val;
	        		}
	        	}
	        	//line.deleteCharAt(line.length() - 1);
	        	line.append("\"" + MyUtil.getFeeValue(feeMatch, phone, policyFee) + "\"");
	        	line.append("),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE ");
			sql.append("policy_year=VALUES(policy_year), fee_date=VALUES(fee_date), fee_match=VALUES(fee_match), prov_deal_date=VALUES(prov_deal_date), prov_deal_remark=VALUES(prov_deal_remark), prov_issue_type=VALUES(prov_issue_type), prov_deal_rst=VALUES(prov_deal_rst), give_fee=VALUES(give_fee);");
			log.debug("----------------fee match batch sql : " + sql);
			sql2 = "delete from t_renewed_list where holder is null";
			sql3 = "update t_renewed_list t1, t_cs_report t2, t_policy t3 set t1.fee_status=\"已终止\" where t1.policy_no=t2.policy_no and t2.policy_no=t3.policy_no and t3.attached_flag=0 and t2.cs_code=\"CT\" and abs(t2.money)>(t3.total_fee*0.1);";
			sql4 = "update t_renewed_list set fee_status='交费成功', fee_fail_reason='' where fee_status<>\"交费成功\" and fee_status<>\"失效\" and policy_no in (select rel_no from t_pay_list where pay_type=2 and fail_desc like '%成功' and datediff(back_date,fee_date)>=0);";
			sql5 = "update t_policy t1, t_cs_report t2, t_pay_list t3 set t1.status=\"有效\" where t1.policy_no=t2.policy_no and t2.cs_code=\"RE\" and datediff(now(),t2.operate_time)=0 and t2.cs_no=t3.rel_no and t3.fail_desc=\"成功\";";
			sql6 = "update t_renewed_list t1, t_pay_list t2, t_policy t3 set t1.fee_status=\"交费失败\",t1.fee_fail_reason=t2.fail_desc where t1.policy_no=t2.rel_no and t2.rel_no=t3.policy_no and t2.fail_desc<>'成功'  and datediff(t2.back_date,t1.fee_date)>=0;";
			break;
		case RenewedCityList://总部的催收
			//tableName = "t_renewed_list";
			standardColumns = RenewedCityListColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_renewed_list(policy_no, prd_name, policy_year, deal_time, fix_status) VALUES ");
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
		case CheckCityBack:
			//tableName = "t_check_write";
			standardColumns = CheckCityBackColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_check_write (policy_no, fix_desc, deal_man, deal_time, fix_status) VALUES ");
			for (DataRow row : dt.Rows) {
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	
	        		line.append("\"" + StringUtil.trimStr(val, true) + "\",");
	        	}
	        	//line.deleteCharAt(line.length() - 1);
	        	line.append("\"IngStatus\"),");
	        	sql.append(line);
	        }
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ON DUPLICATE KEY UPDATE ");
			sql.append("fix_desc=VALUES(fix_desc), deal_man=VALUES(deal_man), deal_time=VALUES(deal_time), fix_status=VALUES(fix_status);");
			log.debug("---------------check city back data sql : " + sql);
			sql2 = "delete from t_check_write where need_fix is null;";
			sql3 = "update t_check_write set fix_type=\"已整改完毕\" where fix_type is null and need_fix=\"要整改\" and fix_desc like \"%整改完毕%\";";
			sql4 = "update t_check_write set fix_type=\"多次联系不上客户\" where fix_type is null and need_fix=\"要整改\" and fix_desc like \"%联系不上%\";";
			sql5 = "update t_check_write set fix_type=\"客户不愿意配合整改\" where fix_type is null and need_fix=\"要整改\" and fix_desc like \"%不愿意配合%\";";
			sql6 = "update t_check_write set fix_type=\"核实无误\" where fix_type is null and need_fix=\"要整改\" and fix_desc like \"%核实无误%\";";
			sql7 = "update t_check_write set fix_type=\"继续跟进\" where fix_type is null and need_fix=\"要整改\" and fix_desc like \"%继续跟进%\";";
			sql8 = "update t_check_write set fix_type=\"其他\" where fix_type is null and fix_desc is not null and need_fix=\"要整改\";";
			break;
		case PayToFailList:
			return dr;
		case PayFromFailList:
			return dr;
		case PaySuccessList:
			return dr;
		case PayFromSuccessList:
			return dr;
		case PolicyUnderWrite:
			//tableName = "t_check_write";
			standardColumns = PolicyUnderWriteColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_under_write(policy_no, client_receive_date, bill_back_date, status) VALUES ");
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
			sql.append("client_receive_date=VALUES(client_receive_date), bill_back_date=VALUES(bill_back_date), status=VALUES(status);");
			log.debug("----------------under write back bill date update status batch sql : " + sql);
			sql2 = "delete from t_under_write where form_no is null or ybt_date is null;";
			sql3 = "update t_policy set bill_back_date=policy_date,client_receive_date=policy_date where bill_back_date is null;";
			sql4 = "update t_policy t1, t_under_write t2 set t1.client_receive_date=t2.client_receive_date,t1.bill_back_date=t2.bill_back_date "
					+ "where t1.policy_no=t2.policy_no and t2.client_receive_date is not null;";
			sql5 = "update t_under_write t2 set t2.status=\"CloseStatus\" "
					+ "where t2.status<>\"CloseStatus\" and t2.client_receive_date is not null;";
			break;
		case PolicyBackDate:
			//tableName = "t_policy";
			standardColumns = PolicyBackDateColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_policy(policy_no, prod_code, client_receive_date, bill_back_date) VALUES ");
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
			sql.append("client_receive_date=VALUES(client_receive_date), bill_back_date=VALUES(bill_back_date);");
			log.debug("----------------policy back date update status batch sql : " + sql);
			sql2 = "delete from t_policy where form_no is null;";
			sql3 = "update t_policy set bill_back_date=policy_date,client_receive_date=policy_date where bill_back_date is null;";
			sql4 = "update t_policy t1, t_under_write t2 set t1.client_receive_date=t2.client_receive_date,t1.bill_back_date=t2.bill_back_date "
					+ "where t1.policy_no=t2.policy_no and t2.client_receive_date is not null;";
			sql5 = "update t_under_write t2 set t2.status=\"CloseStatus\" "
					+ "where t2.status<>\"CloseStatus\" and t2.client_receive_date is not null;";
			//sql3 = "update t_policy set bill_back_date=policy_date where bill_back_date is null;";
			break;
		case UnderWriteSentData:
			//tableName = "t_under_write";
			standardColumns = UnderWriteSentDataColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_under_write(policy_no, prov_ems_no, prov_send_date, status) VALUES ");
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
			sql.append("prov_ems_no=VALUES(prov_ems_no), prov_send_date=VALUES(prov_send_date), status=VALUES(status);");
			log.debug("----------------city update status batch sql : " + sql);
			sql2 = "delete from t_under_write where holder is null or ybt_date is null;";
			sql3 = "update t_under_write tuw, t_policy_reprint_dtl tprd set tuw.prov_ems_no=tprd.ems_no,tuw.status=\"SendStatus\" where tuw.policy_no=tprd.policy_no and tuw.prov_ems_no is null and tuw.status<>\"CloseStatus\";";
			sql4 = "update t_under_write set status=\"CloseStatus\" where bill_back_date is not null;";
			break;
		case UnderWriteDtlData:
			//tableName = "t_under_write";
			standardColumns = UnderWriteDtlColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_under_write(form_no, policy_no,prd_name,policy_fee,perm,fee_type,underwrite_reason,issue,insured_age,insured_sex,insured_job,sys_date,check_date,"
					+ "body_check_date1,body_check_date2,deal_check_date1,deal_check_date2,hb_end_date,sign_date,client_receive_date,bill_back_date,form_write_date) VALUES ");
			line = null;
			for (DataRow row : dt.Rows) {
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = StringUtil.trimStr(row.getValue(item.getDisplayName()));
        			
	        		if(item.getDisplayName().contains("日期")) {
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
			sql.append(" ON DUPLICATE KEY UPDATE form_no=VALUES(form_no), policy_no=VALUES(policy_no),prd_name=VALUES(prd_name), ");
			sql.append("policy_fee=VALUES(policy_fee), perm=VALUES(perm), fee_type=VALUES(fee_type), ");
			sql.append("underwrite_reason=VALUES(underwrite_reason), issue=VALUES(issue), insured_age=VALUES(insured_age), insured_sex=VALUES(insured_sex), insured_job=VALUES(insured_job), ");
			sql.append("sys_date=VALUES(sys_date), ");
			sql.append("check_date=VALUES(check_date), body_check_date1=VALUES(body_check_date1), ");
			sql.append("body_check_date2=VALUES(body_check_date2), deal_check_date1=VALUES(deal_check_date1), deal_check_date2=VALUES(deal_check_date2), hb_end_date=VALUES(hb_end_date), ");
			sql.append("sign_date=VALUES(sign_date), ");
			sql.append("client_receive_date=VALUES(client_receive_date), bill_back_date=VALUES(bill_back_date), ");
			sql.append("form_write_date=VALUES(form_write_date);");
			log.debug("----------------batch update : " + sql);
			sql2 = "delete from t_under_write where form_no is null;";
			log.debug("----------------batch update2 : " + sql2);
			sql3 = "update t_under_write uw, t_policy tp, t_prd prd set uw.product_id=prd.id where uw.policy_no=tp.policy_no and tp.attached_flag=0 and tp.prod_code=left(prd.prd_code,6);";
			sql4= "update t_under_write uw, t_policy tp, t_organization org set uw.organ_id=org.id where uw.policy_no=tp.policy_no and tp.organ_code=org.org_code and tp.attached_flag=0;";
			sql5= "update t_under_write uw, t_policy tp set uw.holder=tp.holder,uw.insured=cast(aes_decrypt(unhex(tp.insured), 'GDPost') as char(100)),uw.relation=\"本人\" where uw.holder is null and uw.policy_no=tp.policy_no and tp.attached_flag=0;";
			break;
		case UWDtlData:
			/* 
			 * 手工单时效报表
			 * 
			 * 市县名称organ_name、投保单号form_no、保险单号policy_no、险种名称prd_name、保险费policy_fee、交费期数perm、交费方式fee_type、转核原因underwrite_reason、
			 * 非实时保单录入日期ybt_date、核心业务系统录入日期sys_date、复核完成日期check_date、体检下发日期body_check_date1、体检回销日期body_check_date2、
			 * 契约调查下发日期deal_check_date1、契调回销日期deal_check_date2、核保完成日期hb_end_date、保单打印日期prov_send_date、保单签单日期sign_date、客户签收日期client_receive_date、回执回销日期bill_back_date、客户填单日期form_write_date、
			 */
			//tableName = "t_under_write";
			standardColumns = UWDtlColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_under_write(organ_name,form_no, policy_no,prd_name,policy_fee,perm,fee_type,underwrite_reason,ybt_date,sys_date,check_date,"
					+ "body_check_date1,body_check_date2,deal_check_date1,deal_check_date2,hb_end_date,sign_date,client_receive_date,bill_back_date,form_write_date) VALUES ");
			line = null;
			for (DataRow row : dt.Rows) {
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = StringUtil.trimStr(row.getValue(item.getDisplayName()));
        			
	        		if(item.getDisplayName().contains("非实时保单录入日期")) {
	        			if(val == null || val.toString().trim().length() <= 0) {
	        				line.append("null,");
	        			} else {
	        				val = StringUtil.trimStr(val, true);
	        				val = StringUtil.date2Str(StringUtil.str2Date(String.valueOf(val), "yyyyMMdd"), "yyyy-MM-dd");
	        				line.append("\"" + val + "\",");
	        			}
	        		} else if(item.getDisplayName().contains("日期")) {
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
			sql.append(" ON DUPLICATE KEY UPDATE policy_no=VALUES(policy_no),holder=VALUES(holder),insured=VALUES(insured), relation=VALUES(relation), ");
			sql.append("prd_name=VALUES(prd_name),organ_name=VALUES(organ_name), perm=VALUES(perm), fee_type=VALUES(fee_type), ");
			sql.append("underwrite_reason=VALUES(underwrite_reason), ");
			sql.append("sys_date=VALUES(sys_date), ybt_date=VALUES(ybt_date), ");
			sql.append("check_date=VALUES(check_date), body_check_date1=VALUES(body_check_date1), ");
			sql.append("body_check_date2=VALUES(body_check_date2), deal_check_date1=VALUES(deal_check_date1), deal_check_date2=VALUES(deal_check_date2), hb_end_date=VALUES(hb_end_date), ");
			sql.append("sign_date=VALUES(sign_date), ");
			sql.append("client_receive_date=VALUES(client_receive_date), bill_back_date=VALUES(bill_back_date), ");
			sql.append("form_write_date=VALUES(form_write_date);");
			log.debug("----------------batch update : " + sql);
			sql2 = "delete from t_under_write where form_no is null;";
			log.debug("----------------batch update2 : " + sql2);
			sql3 = "update t_under_write uw,t_prd prd set uw.product_id=prd.id where uw.product_id is null and uw.prd_name=prd.prd_name;";
			sql4= "update t_under_write uw, t_organization org set uw.organ_id=org.id where uw.organ_id is null and uw.organ_name=org.name;";
			sql5= "update t_under_write uw, t_policy tp set uw.holder=tp.holder,uw.insured=cast(aes_decrypt(unhex(tp.insured), 'GDPost') as char(100)),uw.relation=\"本人\" where uw.holder is null and uw.policy_no=tp.policy_no and tp.attached_flag=0;";
			sql6 = "update t_under_write uw, t_policy tp, t_prd prd set uw.product_id=prd.id where uw.policy_no=tp.policy_no and tp.attached_flag=0 and tp.prod_code=left(prd.prd_code,6);";
			sql7= "update t_under_write uw, t_policy tp, t_organization org set uw.organ_id=org.id where uw.policy_no=tp.policy_no and tp.organ_code=org.org_code and tp.attached_flag=0;";
			break;
		case UnderWriteRemark:
			//tableName = "t_under_write";
			standardColumns = UnderWriteRemarkColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_under_write(form_no, plan_date, remark) VALUES ");
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
			sql.append("plan_date=VALUES(plan_date), remark=VALUES(remark);");
			log.debug("----------------city update status batch sql : " + sql);
			sql2 = "delete from t_under_write where holder is null or ybt_date is null;";
			break;
		case UnderWriteInsured:
			//tableName = "t_policy_dtl";
			standardColumns = PolicyDtlExtColumn.getStandardColumns();
			sql = new StringBuffer("INSERT INTO t_policy_dtl(policy_no, prod_name, insured_card_type, insured_card_num, insured_card_valid, bank_account) VALUES ");
			line = null;
			val = null;
			for (DataRow row : dt.Rows) {
				val = null;
				line = new StringBuffer("(");
	        	for(ColumnItem item : standardColumns) {
	        		val = row.getValue(item.getDisplayName());
                	if(item.isNeedEncrypt()) {
                		line.append("HEX(AES_Encrypt(\"" + StringUtil.trimStr(val, true) + "\",'" + strKey + "')),");
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
			sql.append("insured_card_type=VALUES(insured_card_type), insured_card_num=VALUES(insured_card_num),");
			sql.append("insured_card_valid=VALUES(insured_card_valid), bank_account=VALUES(bank_account);");
			log.debug("----------------insured data batch sql : " + sql);
			sql2 = "delete from t_policy_dtl where holder is null;";
			break;
		case DocNotScanDtl:
			return dr;
		case CsLoan:
			return dr;
		case CsExpire:
			return dr;
		case CallFailPFR:
			return dr;
		case ConversationReport:
			return dr;
		case ConversationReq:
			return dr;
		case IssuePFR:
			return dr;
		case UnderWriteData:
			return dr;
		case StatCity:
			return dr;
		case StatArea:
			return dr;
		case ClaimsCloseReport:
			return dr;
		case Settlement:
			return dr;
		}

        try {
        	//statement.execute("ALTER TABLE " + tableName + " DISABLE KEYS;");
        	log.info("----------exec update sql now");
        	
        	int updateRow = statement.executeUpdate(sql.toString());
        	dr.setUpdateRow(updateRow);
        	log.info("----------end of update");
        	
			if(sql2 != null) {
				log.info("----------update ready to execute sql：" + sql2);
				statement.execute(sql2);
			}
			
			if(sql3 != null) {
				log.info("----------update ready to execute sql：" + sql3);
				statement.execute(sql3);
			}
			
			if(sql4 != null) {
				log.info("----------update ready to execute sql：" + sql4);
				statement.execute(sql4);
			}
			
			if(sql5 != null) {
				log.info("----------update ready to execute sql：" + sql5);
				statement.execute(sql5);
			}
			
			if(sql6 != null) {
				log.info("----------update ready to execute sql：" + sql6);
				statement.execute(sql6);
			}
			
			if(sql7 != null) {
				log.info("----------update ready to execute sql：" + sql7);
				statement.execute(sql7);
			}
			
			if(sql8 != null) {
				log.info("----------update ready to execute sql：" + sql8);
				statement.execute(sql8);
			}
			//statement.execute("ALTER TABLE " + tableName + " ENABLE KEYS;");
			log.info("----------update finish execute sql：");
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
		log.info("-----------handle DATA -use template:" + t);
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
			/*
		case PolicyDtl:
			log.debug("----------get the dtl column");
			standardColumns = PolicyDtlColumn.getStandardColumns();
			keyRow = PolicyDtlColumn.KEY_ROW;
			break;
			*/
		case NewPolicyDtl:
			log.debug("----------get the dtl column");
			standardColumns = PolicyDtlsColumn.getStandardColumns();
			keyRow = PolicyDtlsColumn.KEY_ROW;
			break;
		case PolicyUnderWrite:
			log.debug("----------get the dtl column");
			standardColumns = PolicyUnderWriteColumn.getStandardColumns();
			keyRow = PolicyUnderWriteColumn.KEY_ROW;
			break;
			/*
		case Issue:
			log.debug("----------get the issue column");
			standardColumns = IssueColumn.getStandardColumns();
			keyRow = IssueColumn.KEY_ROW;
			break;
			*/
		case Inquire:
			log.debug("----------get the inquire column");
			standardColumns = InquireColumn.getStandardColumns();
			keyRow = InquireColumn.KEY_ROW;
			break;
		case CallFail:
			standardColumns = IssueColumn.getStandardColumns();
			keyRow = IssueColumn.KEY_ROW;
			break;
			/*
		case CallFailStatus:
			standardColumns = CallFailHQListColumn.getStandardColumns();
			keyRow = CallFailHQListColumn.KEY_ROW;
			break;
			*/
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
		case RenewedFeeRst:
			standardColumns = RenewedFeeRstColumn.getStandardColumns();
			keyRow = RenewedFeeRstColumn.KEY_ROW;
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
		case CheckCityBack:
			standardColumns = CheckCityBackColumn.getStandardColumns();
			keyRow = CheckCityBackColumn.KEY_ROW;
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
		case PayFromSuccessList:
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
		case CallRatio:
			standardColumns = CallRatioColumn.getStandardColumns();
			keyRow = CallRatioColumn.KEY_ROW;
			break;
		case UnderWriteSentData:
			standardColumns = UnderWriteSentDataColumn.getStandardColumns();
			keyRow = UnderWriteSentDataColumn.KEY_ROW;
			break;
		case UWDtlData:
			standardColumns = UWDtlColumn.getStandardColumns();
			keyRow = UWDtlColumn.KEY_ROW;
			break;
		case UnderWriteRemark:
			standardColumns = UnderWriteRemarkColumn.getStandardColumns();
			keyRow = UnderWriteRemarkColumn.KEY_ROW;
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
		case CsLoan:
			standardColumns = CsLoanColumn.getStandardColumns();
			keyRow = CsLoanColumn.KEY_ROW;
			break;
		case IssuePFR:
			standardColumns = IssuePFRColumn.getStandardColumns();
			keyRow = IssuePFRColumn.KEY_ROW;
			break;
		case IssuePFRDeal:
			standardColumns = IssuePFRDealColumn.getStandardColumns();
			keyRow = IssuePFRDealColumn.KEY_ROW;
			break;
		case CallFailPFR:
			standardColumns = IssuePFRColumn.getStandardColumns();
			keyRow = IssuePFRColumn.KEY_ROW;
			break;
		case RenewedFeeMatchList:
			standardColumns = RenewedFeeMatchColumn.getStandardColumns();
			keyRow = RenewedFeeMatchColumn.KEY_ROW;
			break;
		case UnderWriteData:
			standardColumns = UnderWriteColumn.getStandardColumns();
			keyRow = UnderWriteColumn.KEY_ROW;
			break;
		case UnderWriteDtlData:
			standardColumns = UnderWriteDtlColumn.getStandardColumns();
			keyRow = UnderWriteDtlColumn.KEY_ROW;
			break;
		case UnderWriteInsured:
			standardColumns = PolicyDtlExtColumn.getStandardColumns();
			keyRow = PolicyDtlExtColumn.KEY_ROW;
			break;
		case DocNotScanDtl:
			standardColumns = DocNotScanDtlColumn.getStandardColumns();
			keyRow = DocNotScanDtlColumn.KEY_ROW;
			break;
		case CsExpire:
			standardColumns = CsExpireColumn.getStandardColumns();
			keyRow = CsExpireColumn.KEY_ROW;
			break;
		case StatCity:
			standardColumns = StasticsCityColumn.getStandardColumns();
			keyRow = StasticsCityColumn.KEY_ROW;
			break;
		case StatArea:
			standardColumns = StasticsAreaColumn.getStandardColumns();
			keyRow = StasticsAreaColumn.KEY_ROW;
			break;
		case ClaimsCloseReport:
			standardColumns = ClaimsCloseReportColumn.getStandardColumns();
			keyRow = ClaimsCloseReportColumn.KEY_ROW;
			break;
		case Settlement:
			standardColumns = SettlementColumn.getStandardColumns();
			keyRow = SettlementColumn.KEY_ROW;
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
						//|| t.name().equals((FileTemplate.CallFailStatus.name()))
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
						|| t.name().equals(FileTemplate.PolicyBackDate.name())
						|| t.name().equals(FileTemplate.RenewedFeeMatchList.name())
						|| t.name().equals(FileTemplate.UnderWriteDtlData.name())
						|| t.name().equals(FileTemplate.UnderWriteRemark.name())
						|| t.name().equals(FileTemplate.UnderWriteInsured.name())
						|| t.name().equals(FileTemplate.IssuePFRDeal.name())
						|| t.name().equals(FileTemplate.CheckCityBack.name())
						|| t.name().equals(FileTemplate.UWDtlData.name())) {
					
					dr = updateStatusData(t, request, dt);
					log.info("-------- finish update;");
					//如果是保单打印数据，需要单独导入到打印明细中
					if(t.name().equals(FileTemplate.UnderWriteSentData.name()) || t.name().equals(FileTemplate.UWDtlData.name())) {
						log.info("----------------ready to import underwrite sentdata or dtl data");
						importData(t, request, dt, member_id, currentNY);
					}
					
					if(t.name().equals(FileTemplate.PolicyBackDate.name())) {
						log.info("----------------ready to update underwrite PolicyUnderWrite data;");
						dr = updateStatusData(FileTemplate.PolicyUnderWrite, request, dt);
						log.info("----------------ready to import underwrite back date");
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
		JdbcStatement statement = null;
		//com.mysql.cj.jdbc.ServerPreparedStatement statement = null;
		try {
			Object objDataSource = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");
			@SuppressWarnings("resource")
			DruidDataSource dataSource = (DruidDataSource)objDataSource;
			connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
			statement = (JdbcStatement)connection.createStatement();
			//statement = (com.mysql.cj.jdbc.ServerPreparedStatement)connection.createStatement();
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
