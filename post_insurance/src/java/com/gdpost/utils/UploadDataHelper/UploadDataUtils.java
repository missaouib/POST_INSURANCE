package com.gdpost.utils.UploadDataHelper;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import System.Data.DataRow;
import System.Data.DataTable;

import com.gdpost.utils.FileUtils;
import com.gdpost.utils.MyException;
import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.CalculatorHelper.CalculatorLexer;
import com.gdpost.utils.CalculatorHelper.CalculatorParser;
import com.gdpost.utils.FileHandler.CsvFileHandler;
import com.gdpost.utils.FileHandler.DbfFileHandler;
import com.gdpost.utils.FileHandler.IFileHandler;
import com.gdpost.utils.FileHandler.MdbFileHandler;
import com.gdpost.utils.FileHandler.TextFileHandler;
import com.gdpost.utils.FileHandler.XlsFileHandler;
import com.gdpost.utils.FileHandler.XlsxFileHandler;
import com.gdpost.utils.TemplateHelper.ColumnItem;
import com.gdpost.web.shiro.ShiroUser;

public class UploadDataUtils {
	public static Logger log = LoggerFactory.getLogger(UploadDataUtils.class);
	
	private static final String STORE_DIR_TEMP = "uploaddatatemp";
	private static final String STORE_DIR = "uploaddata";

	public static String getFileStorePath(HttpServletRequest request, int iMonth) {
		java.util.Calendar.getInstance();
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		//long lID = shiroUser.getMemberUser() != null ? shiroUser.getMemberUser().getId() : shiroUser.getUser().getId();
		String strID = shiroUser.getUser().getId().toString();
		String strPath = request.getSession().getServletContext().getRealPath("/");
		if(strPath.endsWith(File.separator)) {
			strPath = strPath.substring(0, strPath.length() - 1);
		}
		// 跳出网站目录，跳两层
		strPath = strPath.substring(0, strPath.lastIndexOf(File.separator));
		strPath = strPath.substring(0, strPath.lastIndexOf(File.separator) + 1);

		strPath = strPath + STORE_DIR;
		if(!(new File(strPath )).isDirectory()) {
			new File(strPath).mkdir();
		}
		
		strPath = strPath + File.separator + iMonth;
		
		if(!(new File(strPath)).isDirectory()) {
			new File(strPath).mkdir();
		}
		
		//strPath = strPath + File.separator + lID;
		strPath = strPath + File.separator + strID;
		
		if(!(new File(strPath)).isDirectory()) {
			new File(strPath).mkdir();
		}
		
		return(strPath);
	}
	
	public static String getFileStorePath(HttpServletRequest request) {
		java.util.Calendar.getInstance();
		// 按月、用户ID存放
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int iMonth = calendar.get(Calendar.MONTH) + 1;
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		//long lID = shiroUser.getMemberUser() != null ? shiroUser.getMemberUser().getId() : shiroUser.getUser().getId();
		String strID = shiroUser.getUser().getId().toString();
		String strPath = request.getSession().getServletContext().getRealPath("/");
		if(strPath.endsWith(File.separator)) {
			strPath = strPath.substring(0, strPath.length() - 1);
		}
		// 跳出网站目录，跳两层
		strPath = strPath.substring(0, strPath.lastIndexOf(File.separator));
		strPath = strPath.substring(0, strPath.lastIndexOf(File.separator) + 1);

		strPath = strPath + STORE_DIR;
		if(!(new File(strPath )).isDirectory()) {
			new File(strPath).mkdir();
		}
		
		strPath = strPath + File.separator + iMonth;
		
		if(!(new File(strPath)).isDirectory()) {
			new File(strPath).mkdir();
		}
		
		//strPath = strPath + File.separator + lID;
		strPath = strPath + File.separator + strID;
		
		if(!(new File(strPath)).isDirectory()) {
			new File(strPath).mkdir();
		}
		
		return(strPath);
	}
	
	public static String getNoticeFileStorePath(HttpServletRequest request, int iMonth) {
		java.util.Calendar.getInstance();
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		//long lID = shiroUser.getMemberUser() != null ? shiroUser.getMemberUser().getId() : shiroUser.getUser().getId();
		String strID = shiroUser.getUser().getId().toString();
		String strPath = request.getSession().getServletContext().getRealPath("/");
		if(strPath.endsWith(File.separator)) {
			strPath = strPath.substring(0, strPath.length() - 1);
		}

		strPath = strPath + File.separator + STORE_DIR;
		if(!(new File(strPath )).isDirectory()) {
			new File(strPath).mkdir();
		}
		
		strPath = strPath + File.separator + iMonth;
		
		if(!(new File(strPath)).isDirectory()) {
			new File(strPath).mkdir();
		}
		
		//strPath = strPath + File.separator + lID;
		strPath = strPath + File.separator + strID;
		
		if(!(new File(strPath)).isDirectory()) {
			new File(strPath).mkdir();
		}
		
		return(strPath);
	}
	
	public static String getNoticeRelateFileStorePath(HttpServletRequest request, int iMonth) {
		java.util.Calendar.getInstance();
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		//long lID = shiroUser.getMemberUser() != null ? shiroUser.getMemberUser().getId() : shiroUser.getUser().getId();
		String strID = shiroUser.getUser().getId().toString();
		String strPath = "/";
		strPath = strPath + STORE_DIR;
		strPath = strPath + "/" + iMonth;
		strPath = strPath + "/" + strID;
		return(strPath);
	}
	
	public static String getNoticeFileStorePath(HttpServletRequest request) {
		java.util.Calendar.getInstance();
		// 按月、用户ID存放
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int iMonth = calendar.get(Calendar.MONTH) + 1;
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		//long lID = shiroUser.getMemberUser() != null ? shiroUser.getMemberUser().getId() : shiroUser.getUser().getId();
		String strID = shiroUser.getUser().getId().toString();
		String strPath = request.getSession().getServletContext().getRealPath("/");
		if(strPath.endsWith(File.separator)) {
			strPath = strPath.substring(0, strPath.length() - 1);
		}

		strPath = strPath + STORE_DIR;
		if(!(new File(strPath )).isDirectory()) {
			new File(strPath).mkdir();
		}
		
		strPath = strPath + File.separator + iMonth;
		
		if(!(new File(strPath)).isDirectory()) {
			new File(strPath).mkdir();
		}
		
		//strPath = strPath + File.separator + lID;
		strPath = strPath + File.separator + strID;
		
		if(!(new File(strPath)).isDirectory()) {
			new File(strPath).mkdir();
		}
		
		return(strPath);
	}
	
	public static String getFileStoreTempPath(HttpServletRequest request) {
		String strPath = request.getSession().getServletContext().getRealPath("/");
		if(strPath.endsWith(File.separator)) {
			strPath = strPath.substring(0, strPath.length() - 1);
		}
		// 跳出网站目录，跳两层
		strPath = strPath.substring(0, strPath.lastIndexOf(File.separator));
		strPath = strPath.substring(0, strPath.lastIndexOf(File.separator) + 1);
				
		strPath = strPath + STORE_DIR_TEMP;
		
		if(!(new File(strPath)).isDirectory()) {
			new File(strPath).mkdir();
		}
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		long lID = shiroUser.getUser().getId();

		strPath = strPath + File.separator + lID;
		
		if(!(new File(strPath)).isDirectory()) {
			new File(strPath).mkdir();
		}
		
		return(strPath);
	}
	
	public static DataTable[] getDataSet(String strFilePath, String strFileName, List<ColumnItem> column, String keyRow) throws MyException {
		String strExtension = FileUtils.getFileExtension(strFileName);
		DataTable[] ds = null;
		IFileHandler handler = null;
		
		if(strExtension.equals(".xls")) {
			handler = new XlsFileHandler();
			//handler = new XlsFileHandler_NoHeader();
			//handler = new XlsFileHandler_Converter();
		} else if(strExtension.equals(".xlsx")) {
			handler = new XlsxFileHandler();
			//handler = new XlsxFileHandler_NoHeader();
			//handler = new XlsxFileHandler_NoHeader_Stream();
		}  else if(strExtension.equals(".dbf")) {
			handler = new DbfFileHandler();
		} else if(strExtension.equals(".mdb")) {
			handler = new MdbFileHandler();
		} else if(strExtension.equals(".txt")) {
			handler = new TextFileHandler();
		} else if(strExtension.equals(".csv")) {
			handler = new CsvFileHandler();
		}
		
		//handler.setUserName(strUserName);
		//handler.setPassword(strPassword);
		handler.setStandardColumn(column);
		handler.setKeyRow(keyRow);
		ds = handler.readFile(strFilePath, strFileName, keyRow);
		
		return(ds);
	}
	
	public static int getNianYue() {
		int iNY = 0;
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int iYear = calendar.get(Calendar.YEAR);
		int iMonth = calendar.get(Calendar.MONTH) + 1;
		String strNY = iYear + "" + (iMonth < 10 ? ("0" + iMonth) : iMonth);
		iNY = Integer.parseInt(strNY);
		
		return(iNY);
	}
	
	public static int getNextNianYue() {
		int iNY = 0;
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		int iYear = calendar.get(Calendar.YEAR);
		int iMonth = calendar.get(Calendar.MONTH) + 1;
		String strNY = iYear + "" + (iMonth < 10 ? ("0" + iMonth) : iMonth);
		iNY = Integer.parseInt(strNY);
		
		return(iNY);
	}
	
	public static int getLastNianYue() {
		int iNY = 0;
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		int iYear = calendar.get(Calendar.YEAR);
		int iMonth = calendar.get(Calendar.MONTH) + 1;
		String strNY = iYear + "" + (iMonth < 10 ? ("0" + iMonth) : iMonth);
		iNY = Integer.parseInt(strNY);
		
		return(iNY);
	}
	
	public static int getLastNianYue(int ny) {
		int iNY = 0;
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, Integer.parseInt(String.valueOf(ny).substring(0, 4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(String.valueOf(ny).substring(4)));
		calendar.add(Calendar.MONTH, -2);
		int iYear = calendar.get(Calendar.YEAR);
		int iMonth = calendar.get(Calendar.MONTH) + 1;
		String strNY = iYear + "" + (iMonth < 10 ? ("0" + iMonth) : iMonth);
		iNY = Integer.parseInt(strNY);
		
		return(iNY);
	}
	
	public static String getCharset(File file) {  
        String charset = "GBK";  
        byte[] first3Bytes = new byte[3];
        BufferedInputStream bis = null;
        
        try {  
            boolean checked = false;  
            bis = new BufferedInputStream(new FileInputStream(file));  
            bis.mark(0);  
            int read = bis.read(first3Bytes, 0, 3);  
            if (read == -1) {
            	bis.close();
            	return charset;  
            }
            
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {  
                charset = "UTF-16LE";  
                checked = true;  
            } else if (first3Bytes[0] == (byte) 0xFE&& first3Bytes[1] == (byte) 0xFF) {  
                charset = "UTF-16BE";  
                checked = true;  
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {  
                charset = "UTF-8";  
                checked = true;  
            }
            
            bis.reset();  
            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0)  
                        break;  
                    if (0x80 <= read && read <= 0xBF)  
                        break;  
                    if (0xC0 <= read && read <= 0xDF) {  
                        read = bis.read();  
                        if (0x80 <= read && read <= 0xBF)  
                            continue;  
                        else  
                            break;  
                    } else if (0xE0 <= read && read <= 0xEF) {  
                        read = bis.read();  
                        if (0x80 <= read && read <= 0xBF) {  
                            read = bis.read();  
                            if (0x80 <= read && read <= 0xBF) {  
                                charset = "UTF-8";  
                                break;  
                            } else  
                                break;  
                        } else  
                            break;  
                    }  
                }
            }  
  
            bis.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {
        	if(bis != null) {
        		try {
					bis.close();
				} catch (IOException e) {
				}
        	}
        }
        
        return charset;  
    }
	
	public static String getRowCalculator_old(String strFormula, DataTable dataTable, String strResultColumnName) {
		String strMessage = "";
		
    	InputStream in = new ByteArrayInputStream(strFormula.getBytes());   

    	ANTLRInputStream input = null;
		try {
			input = new ANTLRInputStream(in);
		} catch (IOException e) {
			strMessage = e.getMessage();
			return(strMessage);
		}
    	
    	CalculatorLexer lexer = new CalculatorLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CalculatorParser parser = new CalculatorParser(tokens);
       
        if(dataTable.Columns.get(strResultColumnName) == null) {
        	dataTable.Columns.Add(strResultColumnName);
        }
        
        int iRowCount = 0;
        double dValue = 0;
        for(DataRow dataRow : dataTable.Rows) {
        	iRowCount++;
	        parser.setDataRow(dataRow);
	        try {
	        	parser.prog();
	        } catch (Exception e) {
	        	strMessage = "按列计算公式[" + strFormula + "]计算第[" + iRowCount + "]行出错，" + e.getMessage();
	        	break;
	        }
	        
	        dValue = parser.getResult();
	        dataRow.setValue(strResultColumnName, dValue);
        }
        
		return(strMessage);
	}
	
	public static String getRowCalculator(String strFormula, DataTable dataTable, String strResultColumnName) {
		String strMessage = "";
       
        if(dataTable.Columns.get(strResultColumnName) == null) {
        	dataTable.Columns.Add(strResultColumnName);
        }
        
        int iRowCount = 0;
        int iTotal = dataTable.Rows.size();
        for(DataRow dataRow : dataTable.Rows) {
        	iRowCount++;
        	strMessage = getRowCalculator(strFormula, dataRow, strResultColumnName, iRowCount, iTotal);
        	if(strMessage.length() > 0) {
        		break;
        	}
        }
        
		return(strMessage);
	}

	public static String getRowCalculator(String strFormula, DataRow dataRow, String strResultColumnName, int iRowCount, int iTotal) {
		String strMessage = "";
		
		//strFormula += "\n";	// 增加结束符
    	InputStream in = new ByteArrayInputStream((strFormula + "\n").getBytes());   

    	ANTLRInputStream input = null;
		try {
			input = new ANTLRInputStream(in);
		} catch (IOException e) {
			strMessage = e.getMessage();
			return(strMessage);
		}
    	
    	CalculatorLexer lexer = new CalculatorLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CalculatorParser parser = new CalculatorParser(tokens);
        double dValue = 0;
        parser.setDataRow(dataRow);
        try {
        	parser.prog();
            dValue = parser.getResult();
            dataRow.setValue(strResultColumnName, dValue);
            if(parser.isHasError() && iRowCount != iTotal) {
            	strMessage = "按列计算公式[" + strFormula + "]计算第[" + iRowCount + "]行出错.";
            }
        } catch (Exception e) {
        	if(iRowCount != iTotal) {
        		strMessage = "按列计算公式[" + strFormula + "]计算第[" + iRowCount + "]行出错，" + e.getMessage();
        	}
        }

		return(strMessage);
	}
	
	public static double getRowCalculator(String strFormula, DataRow dataRow) {
		double dResult = 0;
		
    	InputStream in = new ByteArrayInputStream(strFormula.getBytes());   

    	ANTLRInputStream input = null;
		try {
			input = new ANTLRInputStream(in);
		} catch (IOException e) {
			// no handler
		}
    	
    	CalculatorLexer lexer = new CalculatorLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CalculatorParser parser = new CalculatorParser(tokens);
       
        parser.setDataRow(dataRow);
        try {
        	parser.prog();
        } catch (Exception e) {
        	// no handler
        }
        
        dResult = parser.getResult();
        
		return(dResult);
	}
	
}
