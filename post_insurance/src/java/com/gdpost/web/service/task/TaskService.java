package com.gdpost.web.service.task;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.pool.DruidDataSource;

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
		log.info("---------   task to update hfgl's data");
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		try {
			DruidDataSource dataSource = (DruidDataSource)this.getDateSource();
			connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
			statement = (com.mysql.jdbc.Statement)connection.createStatement();
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
		
        try {
        	sql = "delete from t_policy where form_no is null;";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
        	sql = "update t_policy set attached_flag = 1 where prod_name like \"%附加%\";";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_policy set attached_flag = 2 where prod_name like \"%禄禄通%\";";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_policy set attached_flag = 3 where policy_no like \"5244%\";";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
        	sql = "insert into t_log_info (username, message,ip_address,log_level,module) values "
					+ "('admin','spring task update batch job start.','127.0.0.1','WARN','其他操作');";
        	log.info("------------ task service 1 :" + sql);
        	statement.executeUpdate(sql);
        	log.info("------------ finish exec sql");
        	
        	sql1 = "update t_call_fail_list set status = \"重点跟进\" where (status='二访失败' or status='待处理') and 15-datediff(now(),bill_back_date)<=3;";
        	log.info("------------ task service :" + sql1);
        	iRst1 = statement.executeUpdate(sql1);
        	log.info("------------ task service 1 rst :" + iRst1);
			
			sql2 = "update t_call_fail_list set status = \"重点跟进\" where (status='二访失败' or status='待处理') and (hq_deal_type_else like '%电话错误%' or hq_deal_type_else like '%挂断%' or hq_deal_type_else like '%拒接%' or hq_deal_type_else like '%不配合%' or hq_deal_type_else like '%过期%' or hq_deal_type_else like '%空号%' or hq_deal_type_else like '%无法联系本人%' or hq_deal_type_else like '%不信任%');";
			log.info("------------ task service 2 :" + sql2);
			iRst2 = statement.executeUpdate(sql2);
			log.info("------------ task service 2 rst :" + iRst2);
			
			sql3 = "update t_call_fail_list set status=\"需上门回访\" where datediff(NOW(),bill_back_date)>=15 and (status=\"二访失败\" or status=\"重点跟进\" or status='待处理');";
			log.info("------------ task service 3 :" + sql3);
			iRst3 = statement.executeUpdate(sql3);
			log.info("------------ task service 3 rst :" + iRst3);
			
			sql4 = "update t_call_fail_list set status=\"上门成功\" where org_deal_flag=1 and (status=\"重点跟进\" or status=\"需上门回访\");";
			log.info("------------ task service 4 :" + sql4);
			iRst4 = statement.executeUpdate(sql4);
			log.info("------------ task service 4 rst :" + iRst4);
			
			sql5 = "update t_call_fail_list set status=\"上门成功\" where org_deal_flag=1 and (status=\"二访成功\");";
			log.info("------------ task service 5 :" + sql5);
			iRst5 = statement.executeUpdate(sql5);
			log.info("------------ task service 5 rst :" + iRst5);
			
			sql6 = "update t_call_fail_list set status=\"信函成功\" where has_letter=\"信函成功\" and (status=\"上门成功\" or status=\"需上门回访\" or status=\"上门失败\" or status=\"拒访\" or status=\"重点跟进\" or status=\"二访失败\" or status=\"已结案\");";
			log.info("------------ task service 6 :" + sql6);
			iRst6 = statement.executeUpdate(sql6);
			log.info("------------ task service 6 rst :" + iRst6);
			
			sql7 = "update t_call_fail_list set status=\"二访成功\" where (prov_deal_flag=1 or hq_deal_flag=1) and status<>\"二访成功\";";
			log.info("------------ task service 7:" + sql7);
			statement.executeUpdate(sql7);
			log.info("------------ finish exec sql");
			
			sql = "update t_under_write t1,t_policy_reprint_dtl t2 set t1.prov_ems_no=t2.ems_no, t1.status='SendStatus', "
					+ "t1.prov_send_date=t2.print_date where t1.policy_no is not null and (t1.policy_no=t2.policy_no or t1.form_no=t2.form_no) "
					+ "and t1.status='NewStatus';";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_under_write as uw inner join t_policy tp on uw.form_no=tp.form_no set uw.policy_no=tp.policy_no,uw.sign_date=tp.policy_date where uw.policy_no is null;";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_policy tp inner join (select sum(policy_fee) as total_fee, policy_no from t_policy where total_fee=0 group by policy_no) as tp2 set tp.total_fee=tp2.total_fee where tp.total_fee=0 and tp.attached_flag=0 and tp.policy_no=tp2.policy_no;";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_policy tp, t_policy_dtl tpd, t_staff ts set tp.staff_flag=true where tp.staff_flag=0 and tp.policy_no=tpd.policy_no and tpd.holder_card_num=ts.id_card";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_pay_fail_list set status='CloseStatus' where rel_no in (select rel_no from t_pay_success_list);";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_renewed_list set fee_status='交费成功' where fee_status<>\"交费成功\" and policy_no in (select rel_no from t_pay_success_list where pay_type=2 and datediff(back_date,fee_date)>0);";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_renewed_list t0 set t0.fee_status='交费成功' where t0.fee_status<>\"交费成功\" "
					+ "and t0.policy_no in (select t2.policy_no from t_pay_success_list t1, t_cs_report t2 "
					+ "where t1.back_date>t2.cs_date and t2.cs_date>t0.fee_date and t1.pay_type=2 and t0.policy_fee=t1.money and t1.rel_no=t2.cs_no and t2.cs_code='RE');";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_policy t0 set t0.status='有效' where t0.policy_no in (select t2.policy_no from t_pay_success_list t1, t_cs_report t2 "
					+ "where t1.pay_type=2 and t1.rel_no=t2.cs_no and t2.cs_code='RE');";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_renewed_list t0 set t0.fee_status='交费成功' where t0.fee_status<>\"交费成功\" "
					+ "and t0.policy_no in (select t2.policy_no from t_pay_success_list t1, t_cs_report t2 "
					+ "where t1.back_date>t2.cs_date and t2.cs_date>t0.fee_date and t1.pay_type=2 and t0.policy_fee=t1.money and t1.rel_no=t2.cs_no and t2.cs_code='RE');";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_call_fail_list t1, t_cs_report t2 set t1.status=\"已退保\" where t1.policy_no=t2.policy_no and t2.cs_code=\"CT\";";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_cs_report tsr, t_policy_dtl tpd, t_staff ts set tsr.staff_flag=true where tsr.staff_flag=0 and tsr.policy_no=tpd.policy_no and tpd.holder_card_num=ts.id_card;";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_policy tp, t_cs_report tcr set tp.cs_flag=1,tp.status=\"终止\" where tp.policy_no=tcr.policy_no and tp.cs_flag=0 and tp.attached_flag=0 and tcr.cs_code=\"CT\" and abs(tcr.money)=tp.total_fee;";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_policy tp, t_cs_report tcr set tp.cs_flag=2,tp.status=\"终止\" where tp.policy_no=tcr.policy_no and tp.cs_flag=0 and tp.attached_flag=0 and tcr.cs_code=\"CT\" and abs(tcr.money)>(tp.total_fee*0.1);";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_renewed_list t1, t_cs_report t2, t_policy t3 set t1.fee_status=\"已终止\" where t1.policy_no=t2.policy_no and t2.policy_no=t3.policy_no and t3.attached_flag=0 and t2.cs_code=\"CT\" and abs(t2.money)>(t3.total_fee*0.1);";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_policy t1, t_bank_code t2 set t1.bank_name=t2.name where (t1.bank_name like '%邮政局%' or t1.bank_name='') and t1.prod_name not like '%禄禄通%' and t1.bank_code=t2.cpi_code;";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_under_write uw, t_policy tp set uw.policy_no=tp.policy_no, uw.holder=tp.holder, uw.sign_date=tp.policy_date, uw.policy_fee=tp.policy_fee, uw.perm=tp.perm, uw.sync=true where uw.form_no=tp.form_no and sync=false;";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_under_write uw, t_policy tp, t_organization org set uw.organ_id=org.id where uw.policy_no=tp.policy_no and tp.organ_code=org.org_code;";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_under_write uw, t_policy tp, t_prd prd set uw.product_id=prd.id where uw.policy_no=tp.policy_no and tp.prod_code=left(prd.prd_code,6);";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "insert into t_log_info (username, message,ip_address,log_level,module) values "
					+ "('admin','spring task end。犹豫期设重点跟进" + iRst1 + ",其他重点跟进" + iRst2 + ",犹豫期外需上门" + iRst3 + ",信函成功" + iRst6 + "','127.0.0.1','WARN','其他操作');";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_cs_loan set flag=case when DATEDIFF(NOW(),should_date)>1 then '2' when  DATEDIFF(NOW(),should_date)>-30 then '1' else '0' end;";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "update t_under_write set status=\"DelStatus\" where status=\"NewStatus\" and DATEDIFF(NOW(),ybt_date)>35;";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			log.info("------------ finish exec sql");
			
			sql = "delete from t_under_write where form_no is null or ybt_date is null;";
			log.info("------------ sql :" + sql);
			statement.executeUpdate(sql);
			
			log.info("------------ task service update finish");
			
		} catch (SQLException e) {
			e.printStackTrace();
			sql = "insert into t_log_info (username, message,ip_address,log_level,module) values "
					+ "('admin','spring task error:" + e.getMessage() + "','127.0.0.1','WARN','其他操作');";
			try {
				statement.executeUpdate(sql);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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
	}
}
