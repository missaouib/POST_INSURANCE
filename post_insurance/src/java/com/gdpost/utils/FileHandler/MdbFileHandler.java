package com.gdpost.utils.FileHandler;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdpost.utils.StringUtil;

import System.Data.DataColumn;
import System.Data.DataRow;
import System.Data.DataTable;

public class MdbFileHandler extends AbstractFileHandler {
	public static Logger log = LoggerFactory.getLogger(MdbFileHandler.class);
	
	public DataTable[] readFile(String strFilePath, String strFileName, String keyRow) {
		DataTable[] ds = null;
		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		String tableName = "";
        DataTable dt = null;
        DataColumn column = null;
        DataRow dataRow = null;
        
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			conn = DriverManager.getConnection("jdbc:ucanaccess://" + strFilePath + File.separator + strFileName + ";Showschema=true;", this.m_strUserName, this.m_strPassword); 

			ResultSet tables = conn.getMetaData().getTables(
					null, null, null,
					new String[] { "TABLE" });
			// 获取表名
			if (tables.next()) {
				tableName = tables.getString(3);// getXXX can only be used once
			} else {
				return(ds);
			}
			
			dt = new DataTable();
			dt.TableName = tableName;
			statement = (Statement)conn.createStatement();
			// 读取表的内容
			rs = statement.executeQuery("select * from " + tableName);
			ResultSetMetaData data = rs.getMetaData();
			
			for (int i = 1; i <= data.getColumnCount(); i++) {
				column = new DataColumn(StringUtil.trimStr(data.getColumnName(i)));
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
