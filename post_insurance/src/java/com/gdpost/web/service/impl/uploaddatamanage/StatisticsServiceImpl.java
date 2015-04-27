package com.gdpost.web.service.impl.uploaddatamanage;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;

import System.Data.DataColumn;
import System.Data.DataRow;
import System.Data.DataTable;

import com.gdpost.utils.UploadDataHelper.StatisticsType;
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.dao.member.ITblMemberDataDAO;
import com.gdpost.web.dao.member.ITblMemberDataStatusDAO;
import com.gdpost.web.entity.member.TblMemberData;
import com.gdpost.web.service.uploaddatamanage.StatisticsService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class StatisticsServiceImpl  implements StatisticsService{

	@Autowired
	private ITblMemberDataDAO memberDataDAO;

	@Autowired
	private ITblMemberDataStatusDAO memberDataStatusDAO;
	
	@PersistenceContext 
	EntityManager em; 
	
	@Override
	public List<TblMemberData> findAll(
			Specification<TblMemberData> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberData> springDataPage = memberDataDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public DataTable getStatistics(HttpServletRequest request, long member_id, int ny, String dm, String pl, StatisticsType type) {
		return(fillDataTable(request, buildSQL(member_id, ny, dm, pl, type)));
	}
	
	@Override
	public DataTable getNY(HttpServletRequest request, long member_id) {
		String strSQL = "" +
"SELECT DISTINCT ny FROM tbl_member_data WHERE member_id=" + member_id + " ORDER BY ny DESC"
;
		
		return(fillDataTable(request, strSQL));
	}
	
	@Override
	public DataTable getDM(HttpServletRequest request, long member_id) {
		String strSQL = "" +
"SELECT CAST(IFNULL(AES_Decrypt(UNHEX(dm), '" + com.gdpost.web.MySQLAESKey.AESKey + "'), '无') AS CHAR(100)) AS dm " +
"FROM " +
"( " +
"	SELECT DISTINCT dm " + 
"	FROM tbl_member_data " + 
"	WHERE member_id=" + member_id +
") A " +
"ORDER BY dm"
;
		
		return(fillDataTable(request, strSQL));
	}
	
	@Override
	public DataTable getPL(HttpServletRequest request, long member_id) {
		String strSQL = "" +
"SELECT CAST(IFNULL(AES_Decrypt(UNHEX(pl), '" + com.gdpost.web.MySQLAESKey.AESKey + "'), '无') AS CHAR(100)) AS pl " +
"FROM " +
"( " +
"	SELECT DISTINCT pl " + 
"	FROM tbl_member_data " + 
"	WHERE member_id=" + member_id +
") A " +
"ORDER BY pl"
;
		
		return(fillDataTable(request, strSQL));
	}
	
	private DataTable fillDataTable(HttpServletRequest request, String strSQL) {
		DataTable dt = null;
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		com.alibaba.druid.pool.DruidDataSource basic = null;
		try {
			Object objDataSource = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");
			DataSource dataSource = (DataSource)objDataSource;
			//org.apache.commons.dbcp2.BasicDataSource basic = (org.apache.commons.dbcp2.BasicDataSource)dataSource;
			basic = (com.alibaba.druid.pool.DruidDataSource)dataSource;
			connection = DriverManager.getConnection(basic.getUrl(), basic.getUsername(), basic.getPassword());
			statement = (com.mysql.jdbc.Statement)connection.createStatement();
			
			ResultSet rs = null;
			
			rs = statement.executeQuery(strSQL);
			DataColumn column = null;
			DataRow row = null;
			dt = new DataTable();
			while(rs.next()) {
				if(rs.isFirst()) {
					// build column
					ResultSetMetaData rsmd = rs.getMetaData();
					for(int i=1; i<=rsmd.getColumnCount(); i++) {
						column = new DataColumn(rsmd.getColumnLabel(i));
						dt.Columns.Add(column);
					}
				}
				
				// add row
				row = dt.NewRow();
				for(DataColumn c : dt.Columns) {
					row.setValue(c.ColumnName, rs.getObject(c.ColumnName));
				}
				
				dt.Rows.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
			}
			try {
				statement.close();
			} catch (SQLException e) {
			}
		}
		
		return(dt);
	}
	
	private String buildSQL(long member_id, int ny, String dm, String pl, StatisticsType type) {
		String strSQL = "";
		boolean bFlag = false;
		
		switch(type) {
		case ZZL:
			strSQL = "" +
"SELECT member_id, ny, FLOOR(SUM(XSE)) AS XSE, FLOOR(SUM(XSL)) AS XSL " +
"FROM Tbl_Member_Data " +
"WHERE member_id=" + member_id + (" AND NY<=" + ny) + " " +
"GROUP BY member_id, ny " +
"ORDER BY ny "
;
			break;
		case XSE:
			strSQL = "" +
"SELECT member_id, CAST(IFNULL(AES_Decrypt(UNHEX(dm), '" + com.gdpost.web.MySQLAESKey.AESKey + "'),'无') AS CHAR(100)) AS dm, ny, xse, xsl, mll " +
"FROM " +
"( " +
"	SELECT member_id, dm, ny, FLOOR(SUM(xse)) AS xse, FLOOR(SUM(xsl)) AS xsl, AVG(mll) AS mll " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + (" AND NY<=" + ny) + " " +
"	GROUP BY member_id, dm, ny " +
") A " +
"ORDER BY dm, ny"		
;
			break;
		case PL:
			strSQL = "" +
"SELECT member_id, ny, " + (bFlag ? "CAST(IFNULL(AES_Decrypt(UNHEX(dm), '" + com.gdpost.web.MySQLAESKey.AESKey + "'), '无') AS CHAR(100)) AS dm, " : "") + "IFNULL(CAST(AES_Decrypt(UNHEX(pl), '" + com.gdpost.web.MySQLAESKey.AESKey + "') AS CHAR(100)), '') AS pl, FLOOR(SUM(xse)) AS xse " +
"FROM tbl_member_data " +
"WHERE member_id=" + member_id + " " + (!dm.equals("") ? "AND dm=HEX(AES_Encrypt('" + dm + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "'))" : "") + (" AND NY<=" + ny) + " " +
"GROUP BY member_id, ny, " + (!dm.equals("") ? "dm," : "") + " pl " +
"ORDER BY pl, ny"
;
			break;
		case PM:
			strSQL = "" +
"SELECT member_id, ny, " + (bFlag ? "CAST(IFNULL(AES_Decrypt(UNHEX(dm), '" + com.gdpost.web.MySQLAESKey.AESKey + "'), '无') AS CHAR(100)) AS dm, " : "") + "IFNULL(CAST(AES_Decrypt(UNHEX(pm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') AS CHAR(100)), '') AS pm, FLOOR(SUM(xse)) AS xse, FLOOR(SUM(xsl)) AS xsl " +
"FROM tbl_member_data " +
"WHERE member_id=" + member_id + " " + (!dm.equals("") ? "AND dm=HEX(AES_Encrypt('" + dm + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "'))" : "") + (" AND NY<=" + ny) + " " +(!pl.equals("") ? "AND pl=HEX(AES_Encrypt('" + pl + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "'))" : "") + 
"GROUP BY member_id, ny, " + (!dm.equals("") ? "dm," : "") + " pm " +
"ORDER BY pm, ny"
;
			break;
		case PM_XSE:
			strSQL = "" +
"SELECT member_id, CAST(AES_Decrypt(UNHEX(pm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') AS CHAR(100)) AS pm, FLOOR(SUM(xse)) AS data " +
"FROM tbl_member_data " +
"WHERE member_id=" + member_id + " AND NY=" + ny + " " + (!dm.equals("") ? " AND dm=HEX(AES_Encrypt('" + dm + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") + (!pl.equals("") ? " AND pl=HEX(AES_Encrypt('" + pl + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") +
"GROUP BY member_id, pm " +
"ORDER BY data DESC " +
"LIMIT 10 "
;
			break;
		case PM_XSL:
			strSQL = "" +
"SELECT member_id, CAST(AES_Decrypt(UNHEX(pm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') AS CHAR(100)) AS pm, FLOOR(SUM(xsl)) AS data " +
"FROM tbl_member_data " +
"WHERE member_id=" + member_id + " AND NY=" + ny + " " + (!dm.equals("") ? " AND dm=HEX(AES_Encrypt('" + dm + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") + (!pl.equals("") ? " AND pl=HEX(AES_Encrypt('" + pl + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") +
"GROUP BY member_id, pm " +
"ORDER BY data DESC " +
"LIMIT 10 "
;
			break;
		case PM_XSE_Pre:
			strSQL = "" +
"SELECT A.member_id, CAST(AES_Decrypt(UNHEX(A.pm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') AS CHAR(100)) AS pm, IFNULL(B.xse, 0) AS data " +
"FROM " +
"( " +
"	SELECT member_id, pm, SUM(xse) AS xse " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY=" + ny + " " + (!dm.equals("") ? " AND dm=HEX(AES_Encrypt('" + dm + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") + (!pl.equals("") ? " AND pl=HEX(AES_Encrypt('" + pl + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") +
"	GROUP BY member_id, pm " +
"	ORDER BY xse DESC " +
"	LIMIT 10 " +
") A " +
"LEFT JOIN " +
"( " +
"	SELECT member_id, pm, ny, FLOOR(SUM(xse)) AS xse " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY=" + UploadDataUtils.getLastNianYue(ny) + " " +
"	GROUP BY member_id, pm, ny " +
") B ON A.member_id=B.member_id AND A.pm=B.pm "
;
			break;
		case PM_XSL_Pre:
			strSQL = "" +
"SELECT A.member_id, CAST(AES_Decrypt(UNHEX(A.pm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') AS CHAR(100)) AS pm, IFNULL(B.xsl, 0) AS data " +
"FROM " +
"( " +
"	SELECT member_id, pm, SUM(xsl) AS xsl " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY=" + ny + " " + (!dm.equals("") ? " AND dm=HEX(AES_Encrypt('" + dm + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") + (!pl.equals("") ? " AND pl=HEX(AES_Encrypt('" + pl + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") +
"	GROUP BY member_id, pm " +
"	ORDER BY xsl DESC " +
"	LIMIT 10 " +
") A " +
"LEFT JOIN " +
"( " +
"	SELECT member_id, pm, ny, FLOOR(SUM(xsl)) AS xsl " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY=" + UploadDataUtils.getLastNianYue(ny) + " " +
"	GROUP BY member_id, pm, ny " +
") B ON A.member_id=B.member_id AND A.pm=B.pm "
;
			break;
		case PP_XSE:
			strSQL = "" +
"SELECT A.member_id, CAST(AES_Decrypt(UNHEX(A.pm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') AS CHAR(100)) AS pm, A.xse AS Total, B.ny, B.xse AS data, B.xsl, B.mll " +
"FROM " +
"( " +
"	SELECT member_id, pm, SUM(xse) AS xse, SUM(xsl) AS xsl, AVG(mll) AS mll " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY=" + ny + " " + (!dm.equals("") ? " AND dm=HEX(AES_Encrypt('" + dm + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") + (!pl.equals("") ? " AND pl=HEX(AES_Encrypt('" + pl + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") +
"	GROUP BY member_id, pm " +
"	ORDER BY xse DESC " +
"	LIMIT 10 " +
") A " +
"INNER JOIN " +
"( " +
"	SELECT member_id, pm, ny, FLOOR(SUM(xse)) AS xse, FLOOR(SUM(xsl)) AS xsl, AVG(mll) AS mll " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY<=" + ny + " " +
"	GROUP BY member_id, pm, ny " +
") B ON A.member_id=B.member_id AND A.pm=B.pm "
;
			break;
		case PP_XSL:
			strSQL = "" +
"SELECT A.member_id, CAST(AES_Decrypt(UNHEX(A.pm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') AS CHAR(100)) AS pm, A.xsl AS Total, B.ny, B.xse, B.xsl AS data, B.mll " +
"FROM " +
"( " +
"	SELECT member_id, pm, SUM(xse) AS xse, SUM(xsl) AS xsl, AVG(mll) AS mll " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY=" + ny + " " + (!dm.equals("") ? " AND dm=HEX(AES_Encrypt('" + dm + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") + (!pl.equals("") ? " AND pl=HEX(AES_Encrypt('" + pl + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") +
"	GROUP BY member_id, pm " +
"	ORDER BY xsl DESC " +
"	LIMIT 10 " +
") A " +
"INNER JOIN " +
"( " +
"	SELECT member_id, pm, ny, FLOOR(SUM(xse)) AS xse, FLOOR(SUM(xsl)) AS xsl, AVG(mll) AS mll " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY<=" + ny + " " +
"	GROUP BY member_id, pm, ny " +
") B ON A.member_id=B.member_id AND A.pm=B.pm "
;
			break;
		case PP_MLL:
			strSQL = "" +
"SELECT A.member_id, CAST(AES_Decrypt(UNHEX(A.pm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') AS CHAR(100)) AS pm, A.mll AS Total, B.ny, B.xse, B.xsl, B.mll AS data " +
"FROM " +
"( " +
"	SELECT member_id, pm, SUM(xse) AS xse, SUM(xsl) AS xsl, AVG(mll) AS mll " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY=" + ny + " " + (!dm.equals("") ? " AND dm=HEX(AES_Encrypt('" + dm + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") + (!pl.equals("") ? " AND pl=HEX(AES_Encrypt('" + pl + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") +
"	GROUP BY member_id, pm " +
"	ORDER BY mll DESC " +
"	LIMIT 10 " +
") A " +
"INNER JOIN " +
"( " +
"	SELECT member_id, pm, ny, FLOOR(SUM(xse)) AS xse, FLOOR(SUM(xsl)) AS xsl, AVG(mll) AS mll " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY<=" + ny + " " +
"	GROUP BY member_id, pm, ny " +
") B ON A.member_id=B.member_id AND A.pm=B.pm "
;
			break;
		case PP_ZZL:
			strSQL = "" +
"SELECT member_id, ny, CAST(AES_Decrypt(UNHEX(pm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') AS CHAR(100)) AS pm, FLOOR(SUM(xse)) AS xse " +
"FROM tbl_member_data " +
"WHERE member_id=" + member_id + (" AND NY<=" + ny) + " AND NY>=" + UploadDataUtils.getLastNianYue(ny) + " " + (!dm.equals("") ? " AND dm=HEX(AES_Encrypt('" + dm + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") + (!pl.equals("") ? " AND pl=HEX(AES_Encrypt('" + pl + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") +
"GROUP BY member_id, ny, pm " +
"ORDER BY pm, ny"
;
			break;
		case PPDESC_XSE:
			strSQL = "" +
"SELECT A.member_id, CAST(AES_Decrypt(UNHEX(A.pm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') AS CHAR(100)) AS pm, A.xse AS Total, B.ny, B.xse AS data, B.xsl, B.mll " +
"FROM " +
"( " +
"	SELECT member_id, pm, SUM(xse) AS xse, SUM(xsl) AS xsl, AVG(mll) AS mll " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY=" + ny + " " + (!dm.equals("") ? " AND dm=HEX(AES_Encrypt('" + dm + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") + (!pl.equals("") ? " AND pl=HEX(AES_Encrypt('" + pl + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") +
"	GROUP BY member_id, pm " +
"	ORDER BY xse " +
"	LIMIT 10 " +
") A " +
"INNER JOIN " +
"( " +
"	SELECT member_id, pm, ny, FLOOR(SUM(xse)) AS xse, FLOOR(SUM(xsl)) AS xsl, AVG(mll) AS mll " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY<=" + ny + " " +
"	GROUP BY member_id, pm, ny " +
") B ON A.member_id=B.member_id AND A.pm=B.pm "
;
			break;
		case PPDESC_XSL:
			strSQL = "" +
"SELECT A.member_id, CAST(AES_Decrypt(UNHEX(A.pm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') AS CHAR(100)) AS pm, A.xsl AS Total, B.ny, B.xse, B.xsl AS data, B.mll " +
"FROM " +
"( " +
"	SELECT member_id, pm, SUM(xse) AS xse, SUM(xsl) AS xsl, AVG(mll) AS mll " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY=" + ny + " " + (!dm.equals("") ? " AND dm=HEX(AES_Encrypt('" + dm + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") + (!pl.equals("") ? " AND pl=HEX(AES_Encrypt('" + pl + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") +
"	GROUP BY member_id, pm " +
"	ORDER BY xsl " +
"	LIMIT 10 " +
") A " +
"INNER JOIN " +
"( " +
"	SELECT member_id, pm, ny, FLOOR(SUM(xse)) AS xse, FLOOR(SUM(xsl)) AS xsl, AVG(mll) AS mll " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY<=" + ny + " " +
"	GROUP BY member_id, pm, ny " +
") B ON A.member_id=B.member_id AND A.pm=B.pm "
;
			break;
		case PPDESC_MLL:
			strSQL = "" +
"SELECT A.member_id, CAST(AES_Decrypt(UNHEX(A.pm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') AS CHAR(100)) AS pm, A.mll AS Total, B.ny, B.xse, B.xsl, B.mll AS data " +
"FROM " +
"( " +
"	SELECT member_id, pm, SUM(xse) AS xse, SUM(xsl) AS xsl, AVG(mll) AS mll " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY=" + ny + " " + (!dm.equals("") ? " AND dm=HEX(AES_Encrypt('" + dm + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") + (!pl.equals("") ? " AND pl=HEX(AES_Encrypt('" + pl + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") +
"	GROUP BY member_id, pm " +
"	ORDER BY mll " +
"	LIMIT 10 " +
") A " +
"INNER JOIN " +
"( " +
"	SELECT member_id, pm, ny, FLOOR(SUM(xse)) AS xse, FLOOR(SUM(xsl)) AS xsl, AVG(mll) AS mll " +
"	FROM tbl_member_data " +
"	WHERE member_id=" + member_id + " AND NY<=" + ny + " " +
"	GROUP BY member_id, pm, ny " +
") B ON A.member_id=B.member_id AND A.pm=B.pm "
;
			break;
		case PPDESC_ZZL:
			strSQL = "" +
"SELECT member_id, ny, CAST(AES_Decrypt(UNHEX(pm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') AS CHAR(100)) AS pm, FLOOR(SUM(xse)) AS xse " +
"FROM tbl_member_data " +
"WHERE member_id=" + member_id + (" AND NY<=" + ny) + " AND NY>=" + UploadDataUtils.getLastNianYue(ny) + " " + (!dm.equals("") ? " AND dm=HEX(AES_Encrypt('" + dm + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") + (!pl.equals("") ? " AND pl=HEX(AES_Encrypt('" + pl + "', '" + com.gdpost.web.MySQLAESKey.AESKey + "')) " : "") +
"GROUP BY member_id, ny, pm " +
"ORDER BY pm, ny"
;
			break;
		default:
			strSQL = "";
			break;
		}
		
		return(strSQL);
	}

	private DataTable queryNativeSQLAsDataTable(final String sql, final Object bean, final int thePage, final int pageSize) {
		Query query = em.createNativeQuery(sql);
		org.hibernate.jpa.internal.QueryImpl<?> qi = (org.hibernate.jpa.internal.QueryImpl<?>)query;
		org.hibernate.Query hquery = qi.getHibernateQuery();
		hquery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		if (bean != null) {
			hquery.setProperties(bean);
		}
		hquery.setMaxResults(pageSize);
		hquery.setFirstResult((thePage - 1) * pageSize);
		List<Map<?, ?>> rows = hquery.list();
		DataTable dt = new DataTable();
		DataColumn column = null;
		if(rows != null && rows.size() > 0) {
			Map<?, ?> map = rows.get(0);
			for(Object key : map.keySet()) {
				column = new DataColumn(key.toString());
				dt.Columns.Add(column);
			}
		}
		
		DataRow row = null;
		for(Map<?,?> map : rows) {
			row = dt.NewRow();
			for(DataColumn item : dt.Columns) {
				row.setValue(item.ColumnName, map.get(item.ColumnName));
			}
			
			dt.Rows.add(row);
		}
		
		return dt;
	}

	private List<Map<?, ?>> queryNativeSQL(final String sql, final Object bean, final int thePage, final int pageSize) {
		Query query = em.createNativeQuery(sql);
		org.hibernate.jpa.internal.QueryImpl<?> qi = (org.hibernate.jpa.internal.QueryImpl<?>)query;
		org.hibernate.Query hquery = qi.getHibernateQuery();
		hquery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		if (bean != null) {
			hquery.setProperties(bean);
		}
		hquery.setMaxResults(pageSize);
		hquery.setFirstResult((thePage - 1) * pageSize);
		List<Map<?, ?>> rows = hquery.list();
		return rows;
	}
	
	public int deleteUploadData(final Long memberId, final Integer ny) {
		int rst = memberDataDAO.deleteByTblMemberIdAndNy(memberId, ny);
		if(rst >0) {
			rst = memberDataStatusDAO.deleteByTblMemberIdAndNy(memberId, ny);
		}
		return rst;
	}
}
