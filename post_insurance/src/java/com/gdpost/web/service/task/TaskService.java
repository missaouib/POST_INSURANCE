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

		String sql1 = null;
		String sql2 = null;
		String sql3 = null;
		String sql4 = null;
		String sql5 = null;
		String sql6 = null;
		sql1 = "update t_call_fail_list set status = \"重点跟进\" where status='二访失败' and 15-datediff(now(),bill_back_date)<=3;";
		sql2 = "update t_call_fail_list set status = \"重点跟进\" where status='二访失败' and (hq_deal_type_else like '%电话错误%' or hq_deal_type_else like '%挂断%' or hq_deal_type_else like '%拒接%' or hq_deal_type_else like '%不配合%' or hq_deal_type_else like '%过期%' or hq_deal_type_else like '%空号%' or hq_deal_type_else like '%无法联系本人%' or hq_deal_type_else like '%不信任%');";
		sql3 = "update t_call_fail_list t1 set status=\"需上门回访\" where datediff(NOW(),bill_back_date)>15 and (status=\"二访失败\" or status=\"重点跟进\");";
		sql4 = "update t_call_fail_list set status=\"上门成功\" where org_deal_flag=1 and (status=\"重点跟进\" or status=\"需上门回访\");";
		sql5 = "update t_call_fail_list set status=\"上门成功\" where org_deal_flag=1 and (status=\"二访成功\");";
		sql6 = "update t_call_fail_list set status=\"信函成功\" where has_letter=\"信函成功\" and (status=\"上门成功\" or status=\"需上门回访\" or status=\"上门失败\" or status=\"拒访\" or status=\"重点跟进\" or status=\"二访失败\" or status=\"已结案\");";

        try {
        	statement.executeUpdate(sql1);
			if(sql2 != null) {
				statement.executeUpdate(sql2);
			}
			if(sql3 != null) {
				statement.executeUpdate(sql3);
			}
			if(sql4 != null) {
				statement.executeUpdate(sql4);
			}
			if(sql5 != null) {
				statement.executeUpdate(sql5);
			}
			if(sql6 != null) {
				statement.executeUpdate(sql6);
			}
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
