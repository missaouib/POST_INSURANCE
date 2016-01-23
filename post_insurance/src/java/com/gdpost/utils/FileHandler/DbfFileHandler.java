package com.gdpost.utils.FileHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import System.Data.DataColumn;
import System.Data.DataRow;
import System.Data.DataTable;

public class DbfFileHandler extends AbstractFileHandler {
	public static Logger log = LoggerFactory.getLogger(DbfFileHandler.class);

	public DataTable[] readFile(String strFilePath, String strFileName, String keyRow) {
		DataTable[] ds = null;
		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		String tableName = strFileName;
        DataTable dt = null;
        DataColumn column = null;
        DataRow dataRow = null;
        
		try {
			String url = "jdbc:dbf:/" + strFilePath;
            Class.forName("com.caigen.sql.dbf.DBFDriver");

            Properties props = new Properties();
            props.setProperty("user", this.m_strUserName);
            props.setProperty("password", this.m_strPassword);
            props.setProperty("delayedClose", "0");

            conn = DriverManager.getConnection(url, props);
            
			dt = new DataTable();
			dt.TableName = tableName;
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
			// 读取表的内容
			rs = statement.executeQuery("select * from " + tableName);
			ResultSetMetaData data = rs.getMetaData();
			
			for (int i = 1; i <= data.getColumnCount(); i++) {
				column = new DataColumn(data.getColumnName(i));
				dt.Columns.Add(column);
			}
			
			while (rs.next()) {
				dataRow = dt.NewRow();
				for (int i = 1; i <= data.getColumnCount(); i++) {
					dataRow.setValue(i - 1, rs.getString(i));
				}
				
				dt.Rows.add(dataRow);
			}
			
			ds = new DataTable[1];
			ds[0] = dt;
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	
		return(ds);
	}
}
