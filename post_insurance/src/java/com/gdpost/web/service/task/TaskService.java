package com.gdpost.web.service.task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.pool.DruidDataSource;
import com.gdpost.utils.CustomerInfoUtil;
import com.gdpost.utils.StringUtil;
import com.mysql.cj.jdbc.JdbcStatement;

@Service
@Transactional
public class TaskService {
	private static final Logger log = LoggerFactory.getLogger(TaskService.class);
	
	private Object dateSource;
	
	public Object getDateSource() {
		return dateSource;
	}

	@Value("#{dataSource}")
	public void setDateSource(Object dateSource) {
		this.dateSource = dateSource;
	}

	public void updateDB() {		
		log.info("---------   task to update data");
		java.sql.Connection connection = null;
		Connection conn2 = null;
		JdbcStatement statement = null;
		JdbcStatement stat2 = null;
		ResultSet rst = null;
		PreparedStatement preStat = null;
		try {
			DruidDataSource dataSource = (DruidDataSource)this.getDateSource();
			connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
			conn2 = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
			statement = (JdbcStatement)connection.createStatement();
			stat2 = (JdbcStatement) conn2.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql = null;
		String sql1 = null;
		String sql2 = null;
		String sql3 = null;
		String sql4 = null;
		String sql5 = null;
		String sql6 = null;
		String sql7 = null;
		int iRst1 = -1;
		int iRst2 = -1;
		int iRst3 = -1;
		int iRst4 = -1;
		int iRst5 = -1;
		int iRst6 = -1;
		int rstInt = 0;
		
        try {
        	sql = "insert into t_log_info (username, message,ip_address,log_level,module) values "
					+ "('admin','spring task update batch job start.','127.0.0.1','WARN','????????????');";
        	log.info("------------ task service 1 :" + sql);
        	rstInt = statement.executeUpdate(sql);
        	log.info("------------ finish exec sql???" + rstInt);
        	
        	sql = "delete from t_policy where policy_fee<=0 and attached_flag=0;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
        	sql = "delete from t_policy where form_no is null;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
        	sql = "update t_policy set attached_flag = 1 where attached_flag=0 and prod_name like \"%??????%\";";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy set attached_flag = 2 where attached_flag=0 and prod_name like \"%?????????%\";";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy set attached_flag = 3 where attached_flag=0 and prod_code in (\"112004\",\"112006\");";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy set attached_flag = 8 where attached_flag=0 and prod_code in(\"125019\",\"121001\",\"121002\");";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy set attached_flag = 8 where attached_flag=0 and policy_fee=1 and prod_code in(\"125025\");";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy set plan_name=\"????????????????????????????????????\" where plan_code=\"125012_B\" and plan_name is null;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy set plan_name=\"????????????????????????????????????\" where plan_code=\"125012_A\" and plan_name is null;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy set plan_name=\"????????????C??????\" where plan_code=\"151104\" and plan_name is null;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "{CALL split_claims_policynos()}";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy set status=\"????????????\" where prod_code<>\"112004\" and prod_code<>\"112006\" and status=\"??????\" and policy_no in (select policy_no from t_temp_policyno);";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy_dtl tpd, t_policy tp set tpd.policy_status=tp.status where tpd.policy_no=tp.policy_no and tpd.policy_status<>tp.status and tpd.policy_status=\"??????\";";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_cs_expire set status=\"LPStatus\" where policy_no in (select policy_no from t_temp_policyno);";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp set tp.status=\"????????????\" where attached_flag=3 and STATUS=\"??????\" and TIMESTAMPDIFF(DAY,tp.policy_date,now())>366;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
        	sql1 = "update t_call_fail_list set status = \"????????????\" where (status='????????????' or status='?????????') and 15-datediff(now(),bill_back_date)<=3;";
        	log.info("------------ task service :" + sql1);
        	iRst1 = statement.executeUpdate(sql1);
        	log.info("------------ task service 1 rst :" + iRst1);
			
			sql2 = "update t_call_fail_list set status = \"????????????\" where (status='????????????' or status='?????????') and (hq_deal_type_else like '%????????????%' or hq_deal_type_else like '%??????%' or hq_deal_type_else like '%??????%' or hq_deal_type_else like '%?????????%' or hq_deal_type_else like '%??????%' or hq_deal_type_else like '%??????%' or hq_deal_type_else like '%??????????????????%' or hq_deal_type_else like '%?????????%');";
			log.info("------------ task service 2 :" + sql2);
			iRst2 = statement.executeUpdate(sql2);
			log.info("------------ task service 2 rst :" + iRst2);
			
			sql3 = "update t_call_fail_list set status=\"???????????????\" where datediff(NOW(),bill_back_date)>15 and (status=\"????????????\" or status=\"????????????\" or status='?????????');";
			log.info("------------ task service 3 :" + sql3);
			iRst3 = statement.executeUpdate(sql3);
			log.info("------------ task service 3 rst :" + iRst3);
			
			sql = "update t_call_fail_list t1, t_cs_report t2 set t1.status=\"?????????\" where t1.status<>\"?????????\"  and t1.status<>\"????????????\" and t1.status<>\"????????????\" and t1.policy_no=t2.policy_no and t2.cs_code=\"CT\";";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql4 = "update t_call_fail_list set status=\"????????????\" where org_deal_flag=1 and (status=\"????????????\" or status=\"???????????????\");";
			log.info("------------ task service 4 :" + sql4);
			iRst4 = statement.executeUpdate(sql4);
			log.info("------------ task service 4 rst :" + iRst4);
			
			sql5 = "update t_call_fail_list set status=\"????????????\" where org_deal_flag=1 and (status=\"????????????\");";
			log.info("------------ task service 5 :" + sql5);
			iRst5 = statement.executeUpdate(sql5);
			log.info("------------ task service 5 rst :" + iRst5);
			
			sql6 = "update t_call_fail_list set status=\"????????????\" where has_letter=\"????????????\" and (status=\"????????????\" or status=\"???????????????\" or status=\"????????????\" or status=\"??????\" or status=\"????????????\" or status=\"????????????\" or status=\"?????????\");";
			log.info("------------ task service 6 :" + sql6);
			iRst6 = statement.executeUpdate(sql6);
			log.info("------------ task service 6 rst :" + iRst6);
			
			sql = "update t_under_write t1,t_policy_reprint_dtl t2 set t1.prov_ems_no=t2.ems_no, t1.status='SendStatus', "
					+ "t1.prov_send_date=t2.print_date where t1.status='NewStatus' and t1.policy_no is not null and (t1.policy_no=t2.policy_no or t1.form_no=t2.form_no);";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql7 = "update t_call_fail_list set status=\"????????????\" where (prov_deal_flag=1 or hq_deal_flag=1) and status<>\"????????????\";";
			log.info("------------ task service 7:" + sql7);
			rstInt = statement.executeUpdate(sql7);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_under_write as uw inner join t_policy tp on uw.form_no=tp.form_no set uw.policy_no=tp.policy_no,uw.sign_date=tp.policy_date where uw.policy_no is null;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp inner join (select sum(policy_fee) as total_fee, policy_no from t_policy where total_fee=0 and status=\"??????\" group by policy_no) as tp2 set tp.total_fee=tp2.total_fee where tp.policy_no=tp2.policy_no and tp.total_fee=0 and tp.status=\"??????\" and tp.attached_flag=0;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_policy_dtl tpd, t_staff ts set tp.staff_flag=true,tp.duration=tpd.duration where tp.policy_no=tpd.policy_no and tpd.holder_card_num=ts.id_card and tp.staff_flag=0 and year(tp.policy_date)=ts.year and year(tp.policy_date)=year(now());";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_pay_list t1,(select rel_no,back_date from t_pay_list where fail_desc=\"??????\") t2 set t1.status='CloseStatus' where t1.rel_no=t2.rel_no and pay_type=2 and datediff(t2.back_date,t1.back_date)>0 and t1.status<>'CloseStatus' and t1.status<>\"??????\";";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			/*
			sql = "update t_renewed_list set fee_status='????????????', fee_fail_reason='' where fee_status<>\"????????????\" and policy_no in (select rel_no from t_pay_list where pay_type=2 and fail_desc like '%??????' and datediff(back_date,fee_date)>=0);";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_renewed_list t0 set t0.fee_status='????????????', fee_fail_reason='' where t0.fee_status<>\"????????????\" "
					+ "and t0.policy_no in (select t2.policy_no from t_pay_list t1, t_cs_report t2 "
					+ "where t1.back_date>t2.cs_date and t2.cs_date>t0.fee_date and t1.pay_type=2 and t1.fail_desc like '%??????' and t0.policy_fee=t1.money and t1.rel_no=t2.cs_no and t2.cs_code='RE');";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_renewed_list rdl set tp.status=\"??????\" where tp.policy_no=rdl.policy_no and tp.status=\"??????\" and rdl.fee_status<>\"????????????\" and TIMESTAMPDIFF(DAY,rdl.fee_date,now())>61 and rdl.policy_no not in (select psl.rel_no from t_pay_list psl where TIMESTAMPDIFF(DAY,psl.back_date,now())>60);";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_renewed_list t0 set t0.fee_status='????????????', fee_fail_reason='' where t0.fee_status<>\"????????????\" "
					+ "and t0.policy_no in (select t2.policy_no from t_pay_list t1, t_cs_report t2 "
					+ "where t1.back_date>t2.cs_date and t2.cs_date>t0.fee_date and t1.pay_type=2 and t1.fail_desc like '%??????' and t0.policy_fee=t1.money and t1.rel_no=t2.cs_no and t2.cs_code='RE');";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_renewed_list t1, t_cs_report t2, t_policy t3 set t1.fee_status=\"?????????\" where t1.fee_status<>\"?????????\" and t1.policy_no=t2.policy_no and t2.policy_no=t3.policy_no and t3.attached_flag=0 and t2.cs_code=\"CT\" and abs(t2.money)>(t3.total_fee*0.1);";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			*/
			
			sql = "update t_policy t0 set t0.status='??????' where t0.status<>'??????' and t0.policy_no in (select t2.policy_no from t_pay_list t1, t_cs_report t2 where t1.rel_no=t2.cs_no and t2.cs_code='RE' and t1.pay_type=2 and t1.fail_desc='??????');";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_cs_report tsr, t_policy_dtl tpd, t_staff ts set tsr.staff_flag=true where tsr.policy_no=tpd.policy_no and tpd.holder_card_num=ts.id_card and tsr.staff_flag=0 and year(tpd.policy_date)=ts.year and year(tpd.policy_date)=year(now());";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy t1, t_bank_code t2 set t1.bank_name=t2.name where t1.bank_code=t2.cpi_code and (t1.bank_name like '%?????????%' or t1.bank_name='' or t1.bank_name is null) and t1.prod_name not like '%?????????%';";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_under_write uw, t_policy tp set uw.policy_no=tp.policy_no, uw.holder=tp.holder, uw.sign_date=tp.policy_date, uw.policy_fee=tp.policy_fee, uw.perm=tp.perm, uw.sync=true where uw.form_no=tp.form_no and sync=false and uw.policy_no is null;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_under_write uw, t_policy tp, t_organization org set uw.organ_id=org.id where uw.policy_no=tp.policy_no and tp.organ_code=org.org_code and tp.attached_flag=0;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_under_write uw, t_policy tp set uw.holder=tp.holder,uw.insured=cast(aes_decrypt(unhex(tp.insured), 'GDPost') as char(100)),uw.relation=\"??????\" where uw.holder is null and uw.policy_no=tp.policy_no and tp.attached_flag=0;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_under_write uw, t_policy tp, t_prd prd set uw.product_id=prd.id where uw.policy_no=tp.policy_no and tp.attached_flag=0 and tp.prod_code=left(prd.prd_code,6);";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "insert into t_log_info (username, message,ip_address,log_level,module) values "
					+ "('admin','spring task end???????????????????????????" + iRst1 + ",??????????????????" + iRst2 + ",?????????????????????" + iRst3 + ",????????????" + iRst6 + "','127.0.0.1','WARN','????????????');";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "insert IGNORE into t_cs_loan (organ_name,policy_no,holder,holder_sexy,prod_name,bank_name,loan_date,loan_fee,should_date,status,phone,operate_id) (select csr.organ_name,csr.policy_no,csr.holder,tpd.holder_sexy,tpd.prod_name,csr.net_name,csr.cs_date,csr.money,date_add(cs_date,INTERVAL '180' day),'??????',cast(aes_decrypt(unhex(holder_MOBILE), 'GDPost') as char(100)),tpd.operate_id from t_cs_report csr, t_policy_dtl tpd where csr.policy_no=tpd.policy_no and csr.cs_code='LN');";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			
			sql = "update t_cs_loan set flag=case when DATEDIFF(NOW(),should_date)>1 then '2' when  DATEDIFF(NOW(),should_date)>-30 then '1' else '0' end;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_cs_loan csl, t_cs_report csr set csl.status=\"??????\", csl.real_date=csr.cs_date where csl.policy_no=csr.policy_no and csr.cs_code in ('RF','CT','AG') and csr.cs_date>csl.loan_date and csl.status<>'??????';";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_under_write set status=\"DelStatus\" where status=\"NewStatus\" and DATEDIFF(NOW(),ybt_date)>35;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "delete from t_under_write where form_no is null or ybt_date is null;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "insert IGNORE into t_cs_reissue (cs_id,status) (select tsr.id,\"NewStatus\" from t_cs_report tsr where tsr.cs_code=\"LR\" and tsr.id not in (select cs_id from t_cs_reissue));";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_cs_report tcr set tp.status=\"????????????\",tp.cs_date=tcr.cs_date where tp.status<>\"????????????\" and tp.policy_no=tcr.policy_no and tcr.full_cs_code=\"AG????????????\";";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			//sql = "call procDealCardValid();";
			sql = "update t_check_write set fix_status=\"CTStatus\",fix_desc=\"?????????\",deal_man=\"System\", deal_time=now() where fix_status=\"NewStatus\" and policy_no in (select policy_no from t_policy where cs_flag<>0);";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_check_write set fix_status=\"CTStatus\",fix_desc=concat(fix_desc,\"????????????\")  where fix_status in (\"NewStatus\", \"IngStatus\", \"FailStatus\") and policy_no in (select policy_no from t_policy where cs_flag<>0);";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_check_write cw, t_policy_dtl tpd set cw.fix_status=\"ElseStatus\", cw.need_fix=\"?????????\",cw.fix_desc=concat(cw.fix_desc,\"?????????????????????\"),cw.deal_man=\"System\",cw.deal_time=current_timestamp where cw.policy_no=tpd.policy_no and cw.key_info=\"??????????????????????????????;\" and cw.fix_status<>\"ElseStatus\" and tpd.holder_card_num in (select id_card from t_staff where year=year(now()));";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_check_write set is_truth=true where is_truth=false and checker='System'";
			log.info("-----------?????????????????????- sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_check_write set is_truth=true where is_truth=false and checker<>'System' and (key_info like '%??????%' or key_info like '%?????????%' or key_info like '%??????%')";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_cs_report set cs_code=left(full_cs_code,2) where (cs_code is null or cs_code=\"\") and full_cs_code<>\"\";";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_organization org set tp.organ_name=org.short_name where length(tp.organ_name)>=30 and tp.organ_name=org.old_name and TO_DAYS(NOW())-TO_DAYS(operate_time)<=2;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_organization org set tp.organ_name=org.short_name where length(tp.organ_name)>=30 and tp.organ_name=org.name and TO_DAYS(NOW())-TO_DAYS(operate_time)<=2;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_cs_expire ce, t_policy_dtl tpd set tp.status=\"????????????\", tpd.policy_status=\"????????????\", tp.policy_end_date=ce.policy_end_date where tp.policy_no=ce.policy_no and tp.policy_no=tpd.policy_no;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp set policy_end_date=date_add(DATE_ADD(plicy_valid_date,INTERVAL 5 YEAR),INTERVAL -1 DAY) where attached_flag=0 and policy_end_date is null and duration=5;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp set policy_end_date=date_add(DATE_ADD(plicy_valid_date,INTERVAL 6 YEAR),INTERVAL -1 DAY) where attached_flag=0 and policy_end_date is null and duration=6;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp set policy_end_date=date_add(DATE_ADD(plicy_valid_date,INTERVAL 15 YEAR),INTERVAL -1 DAY) where attached_flag=0 and policy_end_date is null and duration=15;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp set policy_end_date=date_add(DATE_ADD(plicy_valid_date,INTERVAL 1 YEAR),INTERVAL -1 DAY) where attached_flag=0 and policy_end_date is null and duration=1;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp set policy_end_date=date_add(DATE_ADD(plicy_valid_date,INTERVAL 10 YEAR),INTERVAL -1 DAY) where attached_flag=0 and policy_end_date is null and duration=10;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp set policy_end_date=date_add(DATE_ADD(plicy_valid_date,INTERVAL 30 YEAR),INTERVAL -1 DAY) where attached_flag=0 and policy_end_date is null and duration=30;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp set policy_end_date=date_add(DATE_ADD(plicy_valid_date,INTERVAL 105 YEAR),INTERVAL -1 DAY) where attached_flag=0 and policy_end_date is null and duration=105;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp set policy_end_date=date_add(DATE_ADD(plicy_valid_date,INTERVAL 25 YEAR),INTERVAL -1 DAY) where attached_flag=0 and policy_end_date is null and duration=25;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp set policy_end_date=date_add(DATE_ADD(plicy_valid_date,INTERVAL 20 YEAR),INTERVAL -1 DAY) where attached_flag=0 and policy_end_date is null and duration=20;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp set policy_end_date=date_add(DATE_ADD(plicy_valid_date,INTERVAL 12 MONTH),INTERVAL -1 DAY) where attached_flag=0 and policy_end_date is null and duration=12;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy set status=\"????????????\" where TIMESTAMPDIFF(YEAR,policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d'))>5 and prod_code in (\"125001\",\"122003\") and status=\"??????\";";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp,t_policy_dtl tpd set tp.status=\"????????????\" where tp.policy_no=tpd.policy_no and TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d'))>6 and tp.prod_code in (\"122001\",\"120009\",\"120015\",\"120017\",\"120019\") and tp.status=\"??????\";";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp,t_policy_dtl tpd set tp.status=\"????????????\" where tp.policy_no=tpd.policy_no and tp.status=\"??????\" and tpd.duration>=5 and tpd.duration is not null and TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d'))>tpd.duration;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy set status=\"????????????\" where TIMESTAMPDIFF(YEAR,policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d'))>1 and prod_code in (\"112004\", \"112006\") and status=\"??????\";";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp set tp.status=\"????????????\" where tp.duration>1 and TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d'))>=tp.duration and tp.status=\"??????\"; ";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_policy_dtl tpd set tpd.policy_status=\"????????????\" where tp.policy_no=tpd.policy_no and tpd.policy_status=\"??????\" and tp.status=\"????????????\";";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_cs_report tcr set tp.cs_flag=2,tp.status=\"??????\",tp.cs_date=tcr.cs_date where tp.policy_no=tcr.policy_no and tp.cs_flag=0 and tp.attached_flag=0 and tcr.full_cs_code=\"CT??????\"  and abs(tcr.money)>500;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_cs_report tcr set tp.cs_flag=2,tp.status=\"??????\",tp.cs_date=tcr.cs_date where tp.policy_no=tcr.policy_no and tp.cs_flag=0 and tp.attached_flag=1 and tcr.full_cs_code=\"CT??????\"  and abs(tcr.money)<=500;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_cs_report tcr set tp.cs_flag=2,tp.status=\"??????\",tp.cs_date=tcr.cs_date where tp.policy_no=tcr.policy_no and tp.cs_flag=0 and tp.attached_flag=0 and tcr.full_cs_code=\"CT\"  and abs(tcr.money)>500;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_cs_report tcr set tp.cs_flag=2,tp.status=\"??????\",tp.cs_date=tcr.cs_date where tp.policy_no=tcr.policy_no and tp.cs_flag=0 and tp.attached_flag=1 and tcr.full_cs_code=\"CT\"  and abs(tcr.money)<=500;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_cs_report tcr set tp.cs_flag=2,tp.status=\"??????\",tp.cs_date=tcr.cs_date where tp.policy_no=tcr.policy_no and tp.cs_flag<>2 and tp.attached_flag=0 and tcr.full_cs_code=\"CT??????\"  and tp.prod_name like \"%????????????%\";";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_cs_report tcr set tp.cs_flag=1,tp.status=\"??????\",tp.cs_date=tcr.cs_date where tp.policy_no=tcr.policy_no and tcr.full_cs_code=\"CT??????\" and tp.attached_flag=0 and abs(tcr.money)>500;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_cs_report tcr set tp.cs_flag=1,tp.status=\"??????\",tp.cs_date=tcr.cs_date where tp.policy_no=tcr.policy_no and tcr.full_cs_code=\"CT??????\" and tp.attached_flag=1 and abs(tcr.money)<=500;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_cs_report tcr set tp.cs_flag=1,tp.status=\"??????\",tp.cs_date=tcr.cs_date where tp.policy_no=tcr.policy_no and tcr.full_cs_code=\"CT??????\" and tp.prod_code=\"120022\" and tp.attached_flag=0 and tp.cs_flag<>1;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_cs_report tcr set tp.cs_flag=2,tp.status=\"??????\",tp.cs_date=tcr.cs_date where tp.policy_no=tcr.policy_no and tcr.full_cs_code=\"CT??????\" and tp.prod_code=\"120022\" and tp.attached_flag=0 and tp.cs_flag<>2;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_cs_report tcr set tp.cs_flag=2,tp.status=\"??????\",tp.cs_date=tcr.cs_date where tp.policy_no=tcr.policy_no and tcr.cs_code=\"CT\" and tp.prod_code in (\"112004\", \"112006\") and tp.cs_flag<>2;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			//?????????????????????
			sql = "update t_policy tp, t_cs_report tcr set tp.cs_flag=2,tp.status=\"??????\",tp.cs_date=tcr.cs_date where tp.policy_no=tcr.policy_no and tp.cs_flag=0 and prod_code in (\"112004\",\"112006\") and tcr.full_cs_code=\"CT??????\";";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_policy tp, t_cs_report tcr set tp.cs_flag=1,tp.status=\"??????\",tp.cs_date=tcr.cs_date where tp.policy_no=tcr.policy_no and tp.cs_flag=0 and prod_code in (\"112004\",\"112006\") and tcr.full_cs_code=\"CT??????\";";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			//end ?????????
			
			sql = "update t_policy_dtl tpd, t_policy_reprint_dtl tprd set tpd.policy_send_type=\"????????????\" where tpd.policy_no=tprd.policy_no and tpd.policy_send_type is null;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_under_write uw, t_policy t set uw.sign_date=t.policy_date where uw.policy_no=t.policy_no and uw.sign_date is null;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_under_write uw, t_policy_dtl tpd set uw.holder_age=((substring(policy_date,1,4)-substring(cast(aes_decrypt(unhex(tpd.holder_card_num), 'GDPost') as char(100)),7,4))-(substring(cast(aes_decrypt(unhex(tpd.holder_card_num), 'GDPost') as char(100)),11,4)-date_format(policy_date,'%m%d')>0)) where uw.policy_no=tpd.policy_no and uw.holder_age is null and uw.policy_no is not null and tpd.holder_card_type=\"?????????\" and tpd.holder_card_num is not null;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_settlement ts,t_claims_close_report tccr set ts.case_status=\"?????????\",ts.case_end_date=tccr.close_date where ts.claims_no=tccr.claims_no and ts.case_end_date is null;";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "insert ignore into t_renewed_follow (policy_no) (select tp.policy_no from t_policy tp where tp.policy_date>=\"2021-01-01\" and tp.attached_flag=0 and tp.status=\"??????\" and tp.perm>1 and ((tp.perm between 3 and 5 and tp.policy_fee>=100000) or (tp.perm>5 and tp.policy_fee>=50000) or ((prod_name like \"%?????????%\" or prod_name like \"%?????????%\") and policy_fee>=200000)));";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_renewed_follow trf, t_policy tp set trf.status=\"CTStatus\" where trf.policy_no=tp.policy_no and tp.attached_flag=0 and tp.status<>\"??????\";";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			// ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????email???????????????
			//???????????????
			sql = "select form_no, policy_no, prod_name, cast(aes_decrypt(unhex(holder), 'GDPost') as char(100)) as holder,holder_age,"
					+ "cast(aes_decrypt(unhex(insured), 'GDPost') as char(100)) as insured,insured_age,"
					+ "holder_card_type,cast(aes_decrypt(unhex(holder_card_num), 'GDPost') as char(100)) as holder_card_num,holder_card_valid,"
					+ "cast(aes_decrypt(unhex(holder_addr), 'GDPost') as char(100)) as holder_addr,"
					+ "cast(aes_decrypt(unhex(holder_phone), 'GDPost') as char(100)) as holder_phone,"
					+ "cast(aes_decrypt(unhex(holder_mobile), 'GDPost') as char(100)) as holder_mobile,"
					+ "insured_card_type,cast(aes_decrypt(unhex(insured_card_num), 'GDPost') as char(100)) as insured_card_num,insured_card_valid, prod_name, holder_email, relation "
					+ "from t_policy_dtl where check_flag=0 and relation is not null and policy_no not like \"5244%\" and policy_no not in (select policy_no from t_under_write where policy_no is not null);";
			
			log.debug(" ----- sql:" + sql);
			
			rst = statement.executeQuery(sql);
			String checkRst = null;
			String formNo = null;
			String policyNo = null;
			String holder = null;
			Integer holderAge = null;
			String insured = null;
			Integer insuredAge = null;
			String holderCardType = null;
			String holderCardNum = null;
			String holderCardValid = null;
			String holderAddr = null;
			String holderPhone = null;
			String holderMobile = null;
			String insuredCardType = null;
			String insuredCardNum = null;
			//String insuredCardValid = null;
			String relation = null;
			String holderEmail = null;
			String prodName = null;
			
			String checkBatch = StringUtil.date2Str(new java.util.Date(), "yyyyMMddHH");
			String keySql = "insert IGNORE into t_check_write (check_batch,form_no,policy_no,prd_name,need_fix,key_info, checker) values (?,?,?,?,?,?,?);";
			
			preStat = connection.prepareStatement(keySql);
			int idx = 0;
			while(rst.next()) {
				formNo = rst.getString("form_no");
				policyNo = rst.getString("policy_no");
				holder = rst.getString("holder");
				holderAge = rst.getInt("holder_age");
				insured = rst.getString("insured");
				insuredAge = rst.getInt("insured_age");
				holderCardType = rst.getString("holder_card_type");
				holderCardNum = rst.getString("holder_card_num");
				holderCardValid = rst.getString("holder_card_valid");
				holderAddr = rst.getString("holder_addr");
				holderPhone = rst.getString("holder_phone");
				holderMobile = rst.getString("holder_mobile");
				insuredCardType = rst.getString("insured_card_type");
				insuredCardNum = rst.getString("insured_card_num");
				//insuredCardValid = rst.getString("insured_card_valid");
				relation = rst.getString("relation");
				holderEmail = rst.getString("holder_email");
				prodName = rst.getString("prod_name");
				//log.debug(" --------- holderaddr:" + holderAddr);
				
				checkRst = CustomerInfoUtil.checkData(stat2, policyNo, holder, holderAge, holderCardType, holderCardNum, holderCardValid, 
						insured, insuredCardType, insuredCardNum, insuredAge, relation, holderPhone, holderMobile, holderEmail, holderAddr);
				
				log.debug(" ----- data: " + checkBatch + "," + holder + "," + holderAge + "," + holderCardType + "," + holderCardNum + "," + holderCardValid + "," + insured + "," + insuredCardType + "," + insuredCardNum + "," + insuredAge + "," + relation + "," + holderPhone + "," + holderMobile + "," + holderEmail + "," + holderAddr);
				if(checkRst != null && checkRst.trim().length() > 0) {
					preStat.setString(1, checkBatch);
					preStat.setString(2, formNo);
					preStat.setString(3, policyNo);
					preStat.setString(4, prodName);
					preStat.setString(5, "?????????");
					preStat.setString(6, checkRst);
					preStat.setString(7, "System");
					idx++;
					log.warn(policyNo + " --------- err:" + checkRst);
					preStat.execute();
				}
			}
			
			log.info(" -----------customer info check, error: " + idx);
			//"insert into t_check_write (check_batch,form_no,policy_no,prod_name,need_fix,key_info, checker) values";
			
			String updateSQL = "update t_policy_dtl set check_flag=true where check_flag=false and relation is not null and policy_no not like \"5244%\";";
			log.info("------------ finish exec sql???" + updateSQL);
			rstInt = statement.executeUpdate(updateSQL);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "delete from t_check_write where key_info not like \"Email?????????%\" and policy_no in(select policy_no from t_policy where prod_code=\"120022\");";
			log.info("------------ finish exec sql???" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_check_write set is_truth=true where checker=\"System\" and is_truth=false;";
			log.info("------------ finish exec sql???" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "delete from t_check_record where policy_no in(select policy_no from t_policy where prod_code=\"120022\");";
			log.info("------------ finish exec sql???" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_under_write uw, t_policy tp, t_prd prd set uw.product_id=prd.id where uw.policy_no=tp.policy_no and tp.attached_flag=0 and tp.prod_code=left(prd.prd_code,6);";
			log.info("------------ finish exec sql???" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_under_write uw, t_policy tp, t_organization org set uw.organ_id=org.id where uw.policy_no=tp.policy_no and tp.organ_code=org.org_code and tp.attached_flag=0;";
			log.info("------------ finish exec sql???" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_under_write uw, t_policy tp set uw.holder=tp.holder,uw.insured=cast(aes_decrypt(unhex(tp.insured), 'GDPost') as char(100)),uw.relation=\"??????\" where uw.holder is null and uw.policy_no=tp.policy_no and tp.attached_flag=0;";
			log.info("------------ finish exec sql???" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_settle_task set limitation=datediff(check_end_date, check_start_date) where check_end_date is not null;";
			log.info("------------ finish exec sql???" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "update t_settle_task set limitation=datediff(now(), check_start_date) where check_end_date is null;";
			log.info("------------ finish exec sql???" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			sql = "insert into t_log_info (username, message,ip_address,log_level,module) values "
					+ "('admin','customer info check, error:" + idx + "','127.0.0.1','WARN','????????????');";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql???" + rstInt);
			
			log.info("------------ task service update finish");
		} catch (SQLException e) {
			e.printStackTrace();
			sql = "insert into t_log_info (username, message,ip_address,log_level,module) values "
					+ "('admin','spring task error:" + e.getMessage() + "','127.0.0.1','WARN','????????????');";
			try {
				statement.executeUpdate(sql);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if(statement != null) {
				try {
					rst.close();
					preStat.close();
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
