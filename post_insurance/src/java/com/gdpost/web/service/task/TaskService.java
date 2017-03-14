package com.gdpost.web.service.task;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.pool.DruidDataSource;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogModule;

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

	@Log(message="spring task update batch job。", level=LogLevel.WARN, module=LogModule.HFGL)
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

		String sql1 = null;
		String sql2 = null;
		String sql3 = null;
		String sql4 = null;
		String sql5 = null;
		String sql6 = null;
		int iRst1 = -1;
		int iRst2 = -1;
		int iRst3 = -1;
		int iRst4 = -1;
		int iRst5 = -1;
		int iRst6 = -1;
		sql1 = "update t_call_fail_list set status = \"重点跟进\" where status='二访失败' and 15-datediff(now(),bill_back_date)<=3;";
		sql2 = "update t_call_fail_list set status = \"重点跟进\" where status='二访失败' and (hq_deal_type_else like '%电话错误%' or hq_deal_type_else like '%挂断%' or hq_deal_type_else like '%拒接%' or hq_deal_type_else like '%不配合%' or hq_deal_type_else like '%过期%' or hq_deal_type_else like '%空号%' or hq_deal_type_else like '%无法联系本人%' or hq_deal_type_else like '%不信任%');";
		sql3 = "update t_call_fail_list set status=\"需上门回访\" where datediff(NOW(),bill_back_date)>15 and (status=\"二访失败\" or status=\"重点跟进\");";
		sql4 = "update t_call_fail_list set status=\"上门成功\" where org_deal_flag=1 and (status=\"重点跟进\" or status=\"需上门回访\");";
		sql5 = "update t_call_fail_list set status=\"上门成功\" where org_deal_flag=1 and (status=\"二访成功\");";
		sql6 = "update t_call_fail_list set status=\"信函成功\" where has_letter=\"信函成功\" and (status=\"上门成功\" or status=\"需上门回访\" or status=\"上门失败\" or status=\"拒访\" or status=\"重点跟进\" or status=\"二访失败\" or status=\"已结案\");";

        try {
        	log.info("------------ task service 1 :" + sql1);
        	String sql = "insert into t_log_info (username, message,ip_address,log_level,module) values "
					+ "('admin','spring task update batch job start.','127.0.0.1','WARN','回访管理');";
        	statement.executeUpdate(sql);
        	
        	iRst1 = statement.executeUpdate(sql1);
        	log.info("------------ task service 1 rst :" + iRst1);
			if(sql2 != null) {
				log.info("------------ task service 2 :" + sql2);
				iRst2 = statement.executeUpdate(sql2);
				log.info("------------ task service 2 rst :" + iRst2);
			}
			if(sql3 != null) {
				log.info("------------ task service 3 :" + sql3);
				iRst3 = statement.executeUpdate(sql3);
				log.info("------------ task service 3 rst :" + iRst3);
			}
			if(sql4 != null) {
				log.info("------------ task service 4 :" + sql4);
				iRst4 = statement.executeUpdate(sql4);
				log.info("------------ task service 4 rst :" + iRst4);
			}
			if(sql5 != null) {
				log.info("------------ task service 5 :" + sql5);
				iRst5 = statement.executeUpdate(sql5);
				log.info("------------ task service 5 rst :" + iRst5);
			}
			if(sql6 != null) {
				log.info("------------ task service 6 :" + sql6);
				iRst6 = statement.executeUpdate(sql6);
				log.info("------------ task service 6 rst :" + iRst6);
			}
			
			sql = "update t_under_write t1,t_policy_reprint_dtl t2 set t1.prov_ems_no=t2.ems_no, t1.status='SendStatus', "
					+ "t1.prov_send_date=t2.print_date where t1.policy_no is not null and (t1.policy_no=t2.policy_no or t1.form_no=t2.form_no) "
					+ "and t1.status='NewStatus';";
			statement.executeUpdate(sql);
			
			connection.commit();
			
			sql = "insert into t_log_info (username, message,ip_address,log_level,module) values "
					+ "('admin','spring task end。犹豫期设重点跟进" + iRst1 + ",其他重点跟进" + iRst2 + ",犹豫期外需上门" + iRst3 + ",信函成功" + iRst6 + "','127.0.0.1','WARN','回访管理');";
			statement.executeUpdate(sql);
			log.info("------------ task service update finish");
		} catch (SQLException e) {
			e.printStackTrace();
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
