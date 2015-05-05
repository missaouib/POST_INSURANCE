package com.gdpost.web.service.impl.uploaddatamanage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;

import System.Data.DataRow;
import System.Data.DataTable;

import com.gdpost.utils.TemplateHelper.ColumnItem;
import com.gdpost.utils.TemplateHelper.ColumnType;
import com.gdpost.utils.TemplateHelper.ConcatRule;
import com.gdpost.utils.TemplateHelper.StandardColumn;
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.dao.uploaddatamanage.UploadDataDAO;
import com.gdpost.web.entity.member.TblMember;
import com.gdpost.web.entity.member.TblMemberData;
import com.gdpost.web.entity.member.TblMemberDataTemplate;
import com.gdpost.web.entity.member.TblMemberDataTemplateField;
import com.gdpost.web.entity.member.TblMemberDataTemplateFieldRule;
import com.gdpost.web.service.member.MemberService;
import com.gdpost.web.service.uploaddatamanage.TemplateService;
import com.gdpost.web.service.uploaddatamanage.UploadDataService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Service
@Transactional
public class UploadDataServiceImpl implements UploadDataService{
	//private static final Logger log = LoggerFactory.getLogger(UploadDataController.class);
	
	@Autowired
	private UploadDataDAO uploadDataDAO;
	
	@Autowired
	private TemplateService templateService;

	@Autowired
	private MemberService memberService;
	
	@Override
	public void saveOrUpdate(TblMemberData tblMemberData) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("resource")
	@Override
	public boolean delete(HttpServletRequest request) {
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		boolean bResult = false;
		com.alibaba.druid.pool.DruidDataSource basic = null;
		try {
			DataSource dataSource = (DataSource)WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");;
			//org.apache.commons.dbcp2.BasicDataSource basic = (org.apache.commons.dbcp2.BasicDataSource)dataSource;
			basic = (com.alibaba.druid.pool.DruidDataSource)dataSource;
			connection = DriverManager.getConnection(basic.getUrl(), basic.getUsername(), basic.getPassword());
			statement = (com.mysql.jdbc.Statement)connection.createStatement();
			String statementText = "";
			statementText = "DELETE FROM tbl_member_data_status WHERE ny=";
			bResult = statement.execute(statementText);
			
			statementText = "DELETE FROM tbl_member_data WHERE ny=";
			bResult = statement.execute(statementText);
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
		
		return(bResult);
	}

	@Override
	public List<TblMemberData> findAll(Page page) {
		org.springframework.data.domain.Page<TblMemberData> springDataPage = uploadDataDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<TblMemberData> findByExample(
			Specification<TblMemberData> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberData> springDataPage = uploadDataDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@SuppressWarnings("resource")
	@Override
	public boolean importData(HttpServletRequest request, DataTable dt) {		
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

		List<ColumnItem> standardColumns = StandardColumn.getStandardColumns();
		String strStatementText = "LOAD DATA LOCAL INFILE 'file.txt' INTO TABLE Tbl_member_data character set utf8 (";
		String strEncrypt = "";
        for(ColumnItem item : standardColumns) {
        	if(item.isHasValue()) {
        		if(item.isNeedEncrypt()) {
        			if(strEncrypt.equals("")) {
        				strEncrypt = "SET " + item.getColumnName() + "=HEX(AES_Encrypt(" + item.getColumnName() + ",'" + strKey + "'))";
        			} else {
        				strEncrypt += "," + item.getColumnName() + "=HEX(AES_Encrypt(" + item.getColumnName() + ",'" + strKey + "'))";
        			}
        		}
        		
        		strStatementText += item.getColumnName() + ",";
        	}
        }

        // member_id, ny,最后补上两列数据
        strStatementText += "member_id,ny) ";
        strStatementText += strEncrypt + ";";
  
        StringBuilder builder = new StringBuilder();
        Object cell = null;
        for (DataRow row : dt.Rows) {
        	// 从处理后的行中，取出标准列数据
        	for(ColumnItem item : standardColumns) {
        		if(!item.isHasValue()) {
        			continue;
        		}
        		
        		cell = row.getValue(item.getColumnName());
        		builder.append(cell);
	            builder.append('\t');
        	}
        	
            //builder.append(member_id);
            builder.append('\t');
            //builder.append(ny);
            builder.append('\n');
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
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(connection != null) {
				try {
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
	
	@Override
	public boolean patchImportData(HttpServletRequest request, InputStream is, long operator_id, String operator_name) {
		//TODO
		
		return true;
	}
	
	public boolean handleData(HttpServletRequest request, List<String> listFiles, 
			long operator_id, String operator_name, int operator_type, StringBuilder builder, String memo) {
				
		// 标准列
		List<ColumnItem> standardColumns = StandardColumn.getStandardColumns();
		
	    boolean bFlag = true;
		Map<String, Integer> dtCurrentShop = new Hashtable<String, Integer>();
		
		// 改为读取临时文件夹，读取完删除文件
		//String strFilePath = UploadDataUtils.getFileStorePath(request);
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
		
		// 对比前一月数据
		//checkData(request, listDataSet, dtCurrentShop, builder);
		
		// 导入数据库
		for(DataTable[] dataSet : listDataSet.values()) {
			for(DataTable dt : dataSet) {
				bFlag = importData(request, dt);
				dt = null;
				if(!bFlag) {
					builder.append("导入数据出错。");
					break;
				}
			}
		}
		
		// 导出CSV标准模板文件
		//createCSV(request, operator_id, listDataSet);
		
		// 按DataTable最多列数的对比，超过必须上传列数的，每列增加5个积分
		int iMustColumns = 5;
		int iMaxColumns = 0;
		for(DataTable[] dataSet : listDataSet.values()) {
			for(DataTable dt : dataSet) {
				if(dt.Columns.size() > iMaxColumns) {
					iMaxColumns = dt.Columns.size();
				}
			}
		}
		
		ds = null;
		listDataSet.clear();
		listDataSet = null;
		if(!bFlag) {
			// 清除本次已导入数据
			clearImport(request); 
			return(false);
		}
		
		// 设置本月已上传
		setImportDone(request, operator_id, operator_name, operator_type, memo);	
	
		//log.info(shiroUser.getLoginName() + "导入了" + strOriginalFileName);
	
		return(true);
	}
	
	private void initStandardColumns(List<ColumnItem> standardColumns, TblMemberDataTemplate template) {
		for(ColumnItem item : standardColumns) {
			for(TblMemberDataTemplateField field : template.getTblMemberDataTemplateFields()) {
				if(item.getColumnName().equalsIgnoreCase(field.getFieldName())) {
					item.setIsStaticValue(field.getIsStaticValue().intValue() == 1); // 是否固定值
					if(item.isStaticValue()) {
						item.setStaticValue(field.getStaticValue());	// 固定值
					}
					item.setFromColumn(field.getIsUsingColumn().intValue() == 1); // 内容从列数据中截取
					if(item.isFromColumn()) {
						item.setFromColumnIndex(field.getDataColumn()); // 对应列序号
						item.setMapColumnIndex(field.getDataColumn()); // 对应列序号
						item.setMapColumnName(field.getColumnName());	// 对应列名
					}
					item.setFromFileName(field.getIsUsingFilename().intValue() == 1); // 内容从文件名中截取
					item.setFromSheetName(field.getIsUsingSheetname().intValue() == 1); // 内容从Sheet名中截取
					item.setMapColumn(field.getIsUsingMapcolumn().intValue() == 1); // 是否对应列
					item.setNeedCalculate(field.getIsUsingMulticolumn().intValue() == 1); // 是否多列计算
					if(item.isMapColumn()) {
						item.setMapColumnIndex(field.getDataColumn()); // 对应列序号
						item.setMapColumnName(field.getColumnName());	// 对应列名
					}
					item.setFormula(field.getMulticolumn()); // 多列计算公式
					Set<TblMemberDataTemplateFieldRule> rules = field.getTblMemberDataTemplateFieldRules();
					item.setNeedConcat(!rules.isEmpty()); // 是否需要根据规则截取
					for(TblMemberDataTemplateFieldRule rule : rules) {
						ConcatRule concatRule = new ConcatRule();
						concatRule.setRule(rule.getSplitChar());
						concatRule.setDataIndex(rule.getValueIndex());
						if(item.getConcatRule() == null) {
							item.setConcatRule(concatRule);
						} else {
							ConcatRule parent = item.getConcatRule();
							while(parent.getConcatRule() != null) {
								parent = parent.getConcatRule();
							}

							parent.setConcatRule(concatRule);
						}
					}
					
					item.setHasValue(item.isStaticValue() || item.isFromColumn() || item.isFromFileName() || item.isFromSheetName() || item.isMapColumn() || item.isNeedCalculate());
				}
			}
		}
	}
	
	@Deprecated
	private boolean checkData(HttpServletRequest request, Map<String, DataTable[]> listDataSet, Map<String, Integer> dtCurrentShop, StringBuilder builder) {
		// 检查数据正确性，做出提示，用户可以选择继续
		// 检查总记录数与上月记录数，相差50%则为错误，做出提示，用户可以选择继续
		// 检查店名与上月对比，有差别（增加或减少），做出提示，用户可以选择继续
		// 读取上月数据，总记录数、店名称
		boolean bFlag = true;
		Map<String, Integer> dtLastShop = new Hashtable<String, Integer>();
		getLastShop(request, dtLastShop);
		int iLastRecordCount = getLastRecordCount(request);
		int iRecordCount = 0;
		
		for(DataTable[] dataSet : listDataSet.values()) {
			iRecordCount += getRecordCount(dataSet);
		}
		
		if(iLastRecordCount > 0 && Math.abs(iRecordCount - iLastRecordCount)/iLastRecordCount >= 0.5) {
			bFlag = false;
			builder.append("与上月相比，记录数相差达50%，");
		}
		
		if(dtLastShop.size() > 0) {
			int iNew = 0;
			int iLost = 0;
			for(String currentShop : dtCurrentShop.keySet()) {
				if(!dtLastShop.containsKey(currentShop)) {
					dtCurrentShop.put(currentShop, 2); // 2新增
					iNew++;
				} else {
					dtLastShop.put(currentShop, 1);	// 1现有，默认为0，0为缺少
				}
			}
						
			for(Integer label :  dtLastShop.values()) {
				if(label.intValue() == 0) {
					iLost++;
				}
			}
			
			if(iNew > 0) {
				bFlag = false;
				builder.append("新增店" + iNew + "间，");
			}
			
			if(iLost > 0) {
				bFlag = false;
				builder.append("减少店" + iLost + "间，");
			}
		}
		
		return(bFlag);
	}
	
	private int getRecordCount(DataTable[] ds) {
		int iRecordCount = 0;
		for(DataTable dt : ds) {
			iRecordCount += dt.Rows.size();
		}
		
		return(iRecordCount);
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
	public boolean setImportDone(HttpServletRequest request, long operator_id, String operator_name, int operate_type, String memo) {
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		boolean bResult = false;
		com.alibaba.druid.pool.DruidDataSource basic = null;
		try {
			DataSource dataSource = (DataSource)WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");;
			//org.apache.commons.dbcp2.BasicDataSource basic = (org.apache.commons.dbcp2.BasicDataSource)dataSource;
			basic = (com.alibaba.druid.pool.DruidDataSource)dataSource;
			connection = DriverManager.getConnection(basic.getUrl(), basic.getUsername(), basic.getPassword());
			statement = (com.mysql.jdbc.Statement)connection.createStatement();
			String statementText = "INSERT INTO tbl_member_data_status(member_id, ny, status, create_date, operator, operator_name, operate_type, memo)" +
			"VALUES(0, now(), " + operator_id + ", '" + operator_name + "', " + operate_type + ",'" + memo + "')";
			bResult = statement.execute(statementText);
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
		
		return(bResult);
	}
	
	@SuppressWarnings("resource")
	public boolean clearImportDone(HttpServletRequest request) {
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		boolean bResult = false;
		com.alibaba.druid.pool.DruidDataSource basic = null;
		try {
			DataSource dataSource = (DataSource)WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");;
			//org.apache.commons.dbcp2.BasicDataSource basic = (org.apache.commons.dbcp2.BasicDataSource)dataSource;
			basic = (com.alibaba.druid.pool.DruidDataSource)dataSource;
			connection = DriverManager.getConnection(basic.getUrl(), basic.getUsername(), basic.getPassword());
			statement = (com.mysql.jdbc.Statement)connection.createStatement();
			String statementText = "DELETE FROM tbl_member_data_status WHERE member_id=";
			bResult = statement.execute(statementText);
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
		
		return(bResult);
	}
	
	@SuppressWarnings("resource")
	public boolean clearImport(HttpServletRequest request) {
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		boolean bResult = false;
		com.alibaba.druid.pool.DruidDataSource basic = null;
		try {
			DataSource dataSource = (DataSource)WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");;
			//org.apache.commons.dbcp2.BasicDataSource basic = (org.apache.commons.dbcp2.BasicDataSource)dataSource;
			basic = (com.alibaba.druid.pool.DruidDataSource)dataSource;
			connection = DriverManager.getConnection(basic.getUrl(), basic.getUsername(), basic.getPassword());
			statement = (com.mysql.jdbc.Statement)connection.createStatement();
			String statementText = "DELETE FROM tbl_member_data WHERE member_id=";
			bResult = statement.execute(statementText);
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
		
		return(bResult);
	}
	
	@SuppressWarnings("resource")
	private int getLastRecordCount(HttpServletRequest request) {
		int iValue = 0;
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		com.alibaba.druid.pool.DruidDataSource basic = null;
		try {
			DataSource dataSource = (DataSource)WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");;
			//org.apache.commons.dbcp2.BasicDataSource basic = (org.apache.commons.dbcp2.BasicDataSource)dataSource;
			basic = (com.alibaba.druid.pool.DruidDataSource)dataSource;
			connection = DriverManager.getConnection(basic.getUrl(), basic.getUsername(), basic.getPassword());
			statement = (com.mysql.jdbc.Statement)connection.createStatement();
			String statementText = "SELECT COUNT(1) AS LastRecordCount FROM tbl_member_data WHERE member_id=";
			ResultSet rs = statement.executeQuery(statementText);
			while(rs.next()) {			
				iValue = rs.getInt("LastRecordCount");
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
		
		return(iValue);
	}
	
	private void getLastShop(HttpServletRequest request, Map<String, Integer> dtLastShop) {
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		com.alibaba.druid.pool.DruidDataSource basic = null;
		try {
			DataSource dataSource = (DataSource)WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");;
			//org.apache.commons.dbcp2.BasicDataSource basic = (org.apache.commons.dbcp2.BasicDataSource)dataSource;
			basic = (com.alibaba.druid.pool.DruidDataSource)dataSource;
			connection = DriverManager.getConnection(basic.getUrl(), basic.getUsername(), basic.getPassword());
			statement = (com.mysql.jdbc.Statement)connection.createStatement();
			String statementText = "SELECT aes_decrypt(unhex(dm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') AS dm FROM (SELECT DISTINCT dm FROM tbl_member_data WHERE member_id=" + ") A";
			ResultSet rs = statement.executeQuery(statementText);
			while(rs.next()) {		
				dtLastShop.put(rs.getString("dm"), 0);
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
	}
	
	@SuppressWarnings("unused")
	private void fillTable(HttpServletRequest request, Map<String, Map<String, Integer>> ds, String strTableName, String strValueColumnName, String strIDColumnName) {
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
			String statementText = "SELECT " + strValueColumnName + "," + strIDColumnName + " FROM " + strTableName + " WHERE member_id=";
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

	@SuppressWarnings("resource")
	@Override
	public boolean checkImportNY(HttpServletRequest request) {
		java.sql.Connection connection = null;
		com.mysql.jdbc.Statement statement = null;
		com.alibaba.druid.pool.DruidDataSource basic = null;
		boolean bFlag = true;
		try {
			DataSource dataSource = (DataSource)WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");;
			//org.apache.commons.dbcp2.BasicDataSource basic = (org.apache.commons.dbcp2.BasicDataSource)dataSource;
			basic = (com.alibaba.druid.pool.DruidDataSource)dataSource;
			connection = DriverManager.getConnection(basic.getUrl(), basic.getUsername(), basic.getPassword());
			statement = (com.mysql.jdbc.Statement)connection.createStatement();
			String statementText = "SELECT ny FROM tbl_member_data_status WHERE member_id=" + " LIMIT 1";
			ResultSet rs = statement.executeQuery(statementText);
			while(rs.next()) {		
				bFlag = false;
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
		
		return bFlag;
	}
	
}
