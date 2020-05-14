package com.gdpost.web.service.task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

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
public class TaskInNightService {
	private static final Logger log = LoggerFactory.getLogger(TaskInNightService.class);
	
	private Object dateSource;
	
	public Object getDateSource() {
		return dateSource;
	}

	@Value("#{dataSource}")
	public void setDateSource(Object dateSource) {
		this.dateSource = dateSource;
	}

	public void updateDBInNight() {		
		log.info("************* reuse in night task to update data");
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
		int rstInt = 0;
		
        try {
        	sql = "insert into t_log_info (username, message,ip_address,log_level,module) values "
					+ "('admin','spring task in night reuse update batch job start.','127.0.0.1','WARN','其他操作');";
        	log.info("------------ task service 1 :" + sql);
        	rstInt = statement.executeUpdate(sql);
        	log.info("------------ finish exec sql" + rstInt);
        	
        	sql = "update t_policy_dtl tpd, t_policy tp set tpd.policy_status=tp.status where tp.policy_no=tpd.policy_no and tpd.policy_status=\"有效\" and tp.status<>\"有效\";";
        	log.info("------------ task service 2 :" + sql);
        	rstInt = statement.executeUpdate(sql);
        	log.info("------------ finish exec sql：" + rstInt);
        	
			// 获取保单号、投保人（姓名、证件号码）、被保险人（证件号码），双方年龄、关系、地址、电话、email；进行判断
			//剔除简易险
			sql = "select form_no, policy_no, prod_name, cast(aes_decrypt(unhex(holder), 'GDPost') as char(100)) as holder,"
					+ "cast(aes_decrypt(unhex(insured), 'GDPost') as char(100)) as insured,"
					+ "cast(aes_decrypt(unhex(holder_addr), 'GDPost') as char(100)) as holder_addr,"
					+ "cast(aes_decrypt(unhex(holder_phone), 'GDPost') as char(100)) as holder_phone,"
					+ "cast(aes_decrypt(unhex(holder_mobile), 'GDPost') as char(100)) as holder_mobile, prod_name, holder_email  "
					+ "from t_policy_dtl where reuser_check=0 and policy_status=\"有效\" and attached_flag=0 and prod_code<>\"120022\" order by policy_date;";
			
			log.debug(" ----- sql:" + sql);
			
			rst = statement.executeQuery(sql);
			String checkRst = null;
			String formNo = null;
			String policyNo = null;
			String holder = null;
			String insured = null;
			String holderAddr = null;
			String holderPhone = null;
			String holderMobile = null;
			String holderEmail = null;
			String prodName = null;
			
			String checkBatch = StringUtil.date2Str(new java.util.Date(), "yyyyMMddHH");
			String keySql = "insert IGNORE into t_reuser_risk (check_batch,form_no,policy_no,prd_name,need_fix,key_info, checker) values (?,?,?,?,?,?,?);";
			
			String startTime = StringUtil.date2Str(new Date(), "yyyy-MM-dd");
			
			preStat = connection.prepareStatement(keySql);
			int idx = 0;
			while(rst.next()) {
				formNo = rst.getString("form_no");
				policyNo = rst.getString("policy_no");
				holder = rst.getString("holder");
				insured = rst.getString("insured");
				holderAddr = rst.getString("holder_addr");
				holderPhone = rst.getString("holder_phone");
				holderMobile = rst.getString("holder_mobile");
				holderEmail = rst.getString("holder_email");
				prodName = rst.getString("prod_name");
				//log.debug(" --------- holderaddr:" + holderAddr);
				
				checkRst = CustomerInfoUtil.reuseCheck(stat2,holder,holderMobile,holderAddr,holderEmail);
				
				log.debug(" ----- data: " + checkBatch + "," + holder + "," + insured + "," + holderPhone + "," + holderMobile + "," + holderAddr);
				if(checkRst != null && checkRst.trim().length() > 4) {
					preStat.setString(1, checkBatch);
					preStat.setString(2, formNo);
					preStat.setString(3, policyNo);
					preStat.setString(4, prodName);
					preStat.setString(5, "要整改");
					preStat.setString(6, checkRst);
					preStat.setString(7, "System");
					idx++;
					log.warn(policyNo + " --------- err:" + checkRst);
					preStat.execute();
				}
			}
			
			log.info(" -----------customer info reuser check, error: " + idx);
			//"insert into t_check_write (check_batch,form_no,policy_no,prod_name,need_fix,key_info, checker) values";
			
			String updateSQL = "update t_policy_dtl set reuser_check=true where reuser_check=false and policy_date<\"" + startTime + "\" and attached_flag=0;";
			rstInt = statement.executeUpdate(updateSQL);
			log.info("------------ finish exec sql" + rstInt);
			
			sql = "insert into t_log_info (username, message,ip_address,log_level,module) values "
					+ "('admin','customer info reuse check in night task, error:" + idx + "','127.0.0.1','WARN','其他操作');";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql" + rstInt);
			
			log.info("------------ task reuse service update finish");
		} catch (SQLException e) {
			e.printStackTrace();
			sql = "insert into t_log_info (username, message,ip_address,log_level,module) values "
					+ "('admin','spring task in night reuse error:" + e.getMessage() + "','127.0.0.1','WARN','其他操作');";
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
	
	public void updateCTInfo() {		
		log.info("************* ct info in night task to update data");
		java.sql.Connection connection = null;
		JdbcStatement statement = null;
		try {
			DruidDataSource dataSource = (DruidDataSource)this.getDateSource();
			connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
			statement = (JdbcStatement)connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql = null;
		int rstInt = 0;
		
        try {
        	sql = "insert into t_log_info (username, message,ip_address,log_level,module) values "
					+ "('admin','spring task in night reuse update batch job start.','127.0.0.1','WARN','其他操作');";
        	log.info("------------ task service 1 :" + sql);
        	rstInt = statement.executeUpdate(sql);
        	log.info("------------ finish exec sql" + rstInt);
        	String dateStr = (Calendar.getInstance().get(Calendar.YEAR)-1) + "-01-01";
        	
        	String updateSQL = "update t_policy_dtl tpd, " + 
        			"(select count(distinct cr.policy_no) as cs_count, abs(sum(cr.money)) as csmoney, tpd.holder_card_num as holder, " + 
        			"group_concat(cr.policy_no SEPARATOR \",\") policy_nos, group_concat(cr.money SEPARATOR \",\") cs_moneys " + 
        			"from t_policy_dtl tpd, t_cs_report cr " + 
        			"where tpd.policy_no=cr.policy_no and tpd.fee_num>1 and tpd.attached_flag=0 and cr.full_cs_code=\"CT退保\" and abs(cr.money)>500 " + 
        			"and tpd.prod_code<>\"120022\" " + 
        			"and tpd.policy_date >= \"" + dateStr + "\" " + 
        			"group by tpd.holder_card_num) as t1 " + 
        			"set tpd.ct_num=t1.cs_count, tpd.ct_money=t1.csmoney,tpd.ct_desc=CONCAT(t1.policy_nos,\",\",cs_moneys)  " + 
        			"where tpd.holder_card_num=t1.holder and t1.cs_count>0;";
        	log.info("------------ task service 1 :" + updateSQL);
			rstInt = statement.executeUpdate(updateSQL);
			log.info("------------ finish exec sql" + rstInt);
			
			sql = "insert into t_log_info (username, message,ip_address,log_level,module) values "
					+ "('admin','ct info stat in night task, total policy:" + rstInt + "','127.0.0.1','WARN','其他操作');";
			log.info("------------ sql :" + sql);
			rstInt = statement.executeUpdate(sql);
			log.info("------------ finish exec sql" + rstInt);
			
			log.info("------------ task reuse service update finish");
		} catch (SQLException e) {
			e.printStackTrace();
			sql = "insert into t_log_info (username, message,ip_address,log_level,module) values "
					+ "('admin','spring task in night reuse error:" + e.getMessage() + "','127.0.0.1','WARN','其他操作');";
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
