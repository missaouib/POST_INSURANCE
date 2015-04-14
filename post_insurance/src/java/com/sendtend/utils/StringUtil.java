package com.sendtend.utils;

import java.util.regex.Pattern;

import System.Data.DataRow;


public class StringUtil {

	public static Object getValue(Object obj, String columnName) {
		DataRow dr = (DataRow)obj;
		return(dr.getValue(columnName));
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String iso2UTF(String str) {
		if (str == null)
			return null;
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes("ISO8859-1");
			String temp = new String(temp_t, "UTF-8");
			return temp;
		} catch (Exception e) {
		}
		return null;
	}
	
	public static String ascii2UTF(String str) {
		if (str == null)
			return null;
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes("US-ASCII");
			String temp = new String(temp_t, "UTF-8");
			return temp;
		} catch (Exception e) {
		}
		return null;
	}
	
	public static String utfLE2UTF(String str) {
		if (str == null)
			return null;
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes("UTF-16LE");
			String temp = new String(temp_t, "UTF-8");
			return temp;
		} catch (Exception e) {
		}
		return null;
	}
	
	public static String utfBE2UTF(String str) {
		if (str == null)
			return null;
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes("UTF-16BE");
			String temp = new String(temp_t, "UTF-8");
			return temp;
		} catch (Exception e) {
		}
		return null;
	}
	
	public static String utf162UTF(String str) {
		if (str == null)
			return null;
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes("UTF-16");
			String temp = new String(temp_t, "UTF-8");
			return temp;
		} catch (Exception e) {
		}
		return null;
	}
	
	public static String gbk2UTF(String str) {
		if (str == null)
			return null;
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes("GBK");
			String temp = new String(temp_t, "UTF-8");
			return temp;
		} catch (Exception e) {
		}
		return null;
	}
	
	public static String getUtfStr(String str) {
		if (str == null)
			return null;
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes();
			String temp = new String(temp_t, "UTF-8");
			return temp;
		} catch (Exception e) {
		}
		return null;
	}

	public static String getUtfString(String str) {
		if (str == null)
			return null;
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes();
			String temp = new String(temp_t, "UTF-8");
			return temp;
		} catch (Exception e) {
		}
		return null;
	}
	
	public static String getUtfStr(byte[] b) {
		if (b == null)
			return null;
		try {
			String temp = new String(b, "UTF-8");
			return temp;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String utf2ISO(String str) {
		if (str == null)
			return null;
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes("UTF-8");
			String temp = new String(temp_t, "ISO8859-1");
			return temp;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String gbk2ISO(String str) {
		if (str == null)
			return null;
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes("GBK");
			String temp = new String(temp_t, "ISO8859-1");
			return temp;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 
	 * @param str
	 * @param fromChartset
	 * @param toChartset
	 * @return
	 */
	public static String toChartset(String str, String fromChartset,
			String toChartset) {
		if (str == null)
			return null;
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes(fromChartset);
			String temp = new String(temp_t, toChartset);
			return temp;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String convert(String str) {
		if(str == null || str.trim().length() <=0) {
			return "";
		}
		String tmp;
		StringBuffer sb = new StringBuffer(10000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (c > 255) {
				sb.append("\\u");
				j = (c >>> 8);
				tmp = Integer.toHexString(j);
				if (tmp.length() == 1)
					sb.append("0");
				sb.append(tmp);
				j = (c & 0xFF);
				tmp = Integer.toHexString(j);
				if (tmp.length() == 1)
					sb.append("0");
				sb.append(tmp);
			} else {
				sb.append(c);
			}
		}
		return (new String(sb));
	}
	
	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[-+]?[0-9]+(\\.[0-9]+)?"); //判断是否为double类型
	    return pattern.matcher(str).matches();    
	 }
	
	public static String handleQuote(String str) {
		String strValue = str;
		strValue = strValue.replaceAll("\"", "\\\\\"");
		
		return(strValue);
	}
}
