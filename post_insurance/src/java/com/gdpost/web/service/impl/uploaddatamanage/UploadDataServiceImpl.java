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
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;

import System.Data.DataRow;
import System.Data.DataTable;

import com.gdpost.utils.TemplateHelper.CheckColumn;
import com.gdpost.utils.TemplateHelper.ColumnItem;
import com.gdpost.utils.TemplateHelper.ColumnType;
import com.gdpost.utils.TemplateHelper.IssueColumn;
import com.gdpost.utils.TemplateHelper.PolicyColumn;
import com.gdpost.utils.TemplateHelper.PolicyDtlColumn;
import com.gdpost.utils.TemplateHelper.RemitMoneyColumn;
import com.gdpost.utils.TemplateHelper.RenewedColumn;
import com.gdpost.utils.TemplateHelper.Template.FileTemplate;
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.dao.uploaddatamanage.UploadDataDAO;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.service.uploaddatamanage.UploadDataService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

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
	public boolean importData(FileTemplate ft, HttpServletRequest request, DataTable dt, long member_id, int ny) {		
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		
		// AES Encrypt key
		String strKey = com.gdpost.web.MySQLAESKey.AESKey;

		List<ColumnItem> standardColumns = null;
		String strStatementText = null;
		String strEncrypt = "";
		switch(ft) {
		case Policy:
			standardColumns = PolicyColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE T_POLICY character set utf8 (";
	        break;
		case PolicyDtl:
			standardColumns = PolicyDtlColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE T_POLICY_DTL character set utf8 (";
			break;
		case Issue:
			standardColumns = IssueColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE T_ISSUE character set utf8 (";
			break;
		case CallFail:
			//standardColumns = CailFileColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE T_CALL_FAIL character set utf8 (";
			break;
		case Renewed:
			standardColumns = RenewedColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE T_RENEWED_LIST character set utf8 (";
			break;
		case RemitMoney:
			standardColumns = RemitMoneyColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE T_REMIT_MONEY_LIST character set utf8 (";
			break;
		case CheckWrite:
			standardColumns = CheckColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE T_CHECK_WRITE character set utf8 (";
			break;
		case CheckRecord:
			standardColumns = CheckColumn.getStandardColumns();
			strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' REPLACE INTO TABLE T_CHECK_RECORD character set utf8 (";
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
        //log.debug("--------------" + strStatementText);
        
        StringBuilder builder = new StringBuilder();
        Object cell = null;
        boolean isBreak = false;
        for (DataRow row : dt.Rows) {
        	isBreak = false;
        	// 从处理后的行中，取出标准列数据
        	//log.debug("--------------" + row.toString());
        	for(ColumnItem item : standardColumns) {
        		if(ft.name().equals(FileTemplate.CheckWrite.name()) || ft.name().equals(FileTemplate.CheckRecord.name())) {
        			if(row.getValue(new CheckColumn().getCheckColumn()).equals("不要整改")) {
        				isBreak = true;
        				break;
        			}
        		}
        		
        		cell = row.getValue(item.getDisplayName());
        		//if(item.getDisplayName().equals(""))
        		builder.append(cell);
	            builder.append('\t');
        	}
        	if(isBreak) {
        		continue;
        	}
        	if(ft.name().equals(FileTemplate.CheckWrite.name()) || ft.name().equals(FileTemplate.CheckRecord.name())) {
    			if(row.getValue(new CheckColumn().getCheckColumn()).equals("不要整改")) {
    				isBreak = true;
    				break;
    			}
    		}
            builder.append(member_id);
            builder.append('\n');
            //log.debug("-----" + builder.toString());
        }

        InputStream is = null;
        try {
			is = IOUtils.toInputStream(builder.toString(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
        statement.setLocalInfileInputStream(is);
        try {
			statement.execute(strStatementText);
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
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			
			builder.delete(0, builder.length());
			builder = null;
		}
        
		return(true);
	}
	
	
	/**
	 * 
	 */
	public boolean handleData(FileTemplate t, HttpServletRequest request, long member_id, List<String> listFiles, 
			int currentNY, int lastNY, long operator_id, String operator_name, int operator_type, StringBuilder builder, String memo) {
		log.debug("-----------handle DATA -use template:" + t);
		// 标准列
		List<ColumnItem> standardColumns = null;
		
		switch(t) {
		case Policy:
			log.debug("----------get the policy column");
			standardColumns = PolicyColumn.getStandardColumns();
			break;
		case PolicyDtl:
			log.debug("----------get the dtl column");
			standardColumns = PolicyDtlColumn.getStandardColumns();
			break;
		case Issue:
			log.debug("----------get the issue column");
			standardColumns = IssueColumn.getStandardColumns();
			break;
		case CallFail:
			//standardColumns = CallFailColumn.getStandardColumns();
			break;
		case Renewed:
			standardColumns = RenewedColumn.getStandardColumns();
			break;
		case RemitMoney:
			standardColumns = RemitMoneyColumn.getStandardColumns();
			break;
		case CheckWrite:
			standardColumns = CheckColumn.getStandardColumns();
			break;
		case CheckRecord:
			standardColumns = CheckColumn.getStandardColumns();
			break;
			default:
				standardColumns = PolicyColumn.getStandardColumns();
		}
		
	    boolean bFlag = true;
		Map<String, Integer> dtCurrentShop = new Hashtable<String, Integer>();
		
		String strFilePath = UploadDataUtils.getFileStoreTempPath(request);
		DataTable[] ds = null;
		Map<String, DataTable[]> listDataSet = new HashMap<String, DataTable[]>(); 
		
		// 文件组正确，执行导入
		for(String strOriginalFileName : listFiles) {
			// 读文件，取值到DataTable[]中
			ds = UploadDataUtils.getDataSet(strFilePath, strOriginalFileName, standardColumns);
			if(ds == null || ds.length == 0) {
				builder.append("处理文件[" + strOriginalFileName + "]中数据出错，没有找到数据。");
				return(false);
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
			return(false);
		}
		
		// 导入数据库
		for(DataTable[] dataSet : listDataSet.values()) {
			for(DataTable dt : dataSet) {
				bFlag = importData(t, request, dt, member_id, currentNY);
				dt = null;
				if(!bFlag) {
					builder.append("导入数据出错。");
					break;
				}
			}
		}
		
		ds = null;
		listDataSet.clear();
		listDataSet = null;
		
		return(true);
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
		
	@SuppressWarnings("resource")
	public boolean setImportDone(HttpServletRequest request, long member_id, int ny, long operator_id, String operator_name, int operate_type, String memo) {
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		boolean bResult = true;
		com.alibaba.druid.pool.DruidDataSource basic = null;
		return(bResult);
	}
	
	@SuppressWarnings("resource")
	public boolean clearImportDone(HttpServletRequest request, long member_id, int ny) {
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		boolean bResult = true;
		return(bResult);
	}
	
	@SuppressWarnings("resource")
	public boolean clearImport(HttpServletRequest request, long member_id, int ny) {
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		boolean bResult = true;
		
		return(bResult);
	}
	
	
	@SuppressWarnings("unused")
	private void fillTable(HttpServletRequest request, long member_id, Map<String, Map<String, Integer>> ds, String strTableName, String strValueColumnName, String strIDColumnName) {
		Map<String, Integer> dtRelate = new Hashtable<String, Integer>(); // data table
		
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		com.alibaba.druid.pool.DruidDataSource basic = null;
		try {
			DataSource dataSource = (DataSource)WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");;
			//org.apache.commons.dbcp2.BasicDataSource basic = (org.apache.commons.dbcp2.BasicDataSource)dataSource;
			basic = (com.alibaba.druid.pool.DruidDataSource)dataSource;
			connection = DriverManager.getConnection(basic.getUrl(), basic.getUsername(), basic.getPassword());
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
