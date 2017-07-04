package com.gdpost.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.Base64Utils;

import System.Data.DataRow;


public class StringUtil {

	/**
	 * 得到两个日期相差的天数
	 */
	public static int getBetweenDay(Date date1, Date date2) {
		Calendar d1 = new GregorianCalendar();
		d1.setTime(date1);
		Calendar d2 = new GregorianCalendar();
		d2.setTime(date2);
		int days = d2.get(Calendar.DAY_OF_YEAR)- d1.get(Calendar.DAY_OF_YEAR);
		//System.out.println("days="+days);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
//			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}
	
	public static String getFirstDayOfYear(String patten) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		return sdf.format(cal.getTime());
	}
	
	public static String getFirstDayOfMonth(String patten) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		return sdf.format(cal.getTime());
	}
	
	public static String getLastDayOfMonth(String patten) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1); 
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		return sdf.format(cal.getTime());
	}
	
	public static String getMonthFirstDayOfMonth(int month, String patten) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		return sdf.format(cal.getTime());
	}
	
	public static String getMonthLastDayOfMonth(int month, String patten) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1); 
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		return sdf.format(cal.getTime());
	}
	
	public static String decode(String str) {
		return Base64Utils.encodeToString(str.getBytes());
	}
	
	public static String trimStr(Object obj, Boolean... mysqlFlag) {
		if(obj == null) {
			return "";
		}
		String str = obj.toString().trim();
		Pattern p = Pattern.compile("\\t|\r|\n|\\\\");
        Matcher m = p.matcher(str);
        String dest = m.replaceAll("");
        
        if(mysqlFlag != null && mysqlFlag.length>0 && mysqlFlag[0]) {
        	if (dest != null && dest.indexOf("\"") >= 0)
        		dest = dest.replaceAll("\"", "\\\\\"");
        	if (dest != null && dest.indexOf("'") >= 0)
        		dest = dest.replaceAll("'", "\\\\'");
        }
        
		return dest;
	}
	
	public static Object getValue(Object obj, String columnName) {
		DataRow dr = (DataRow)obj;
		return(dr.getValue(columnName));
	}
	
	public static String urlEncode(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	public static String urlDecode(String url) {
		try {
			return URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
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
	
	public static Date str2Date(String str, String patten) {
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	public static String date2Str(Date date, String patten) {
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		return sdf.format(date);
	}
	
	public static Date dateAdd(Date date, int addDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, addDay);
		return cal.getTime();
	}
	
	public static void main(String[] args) {
		
		System.out.println(StringUtil.getMonthLastDayOfMonth(5, "yyyy-MM-dd"));
		Calendar cal = new GregorianCalendar();
		System.out.println(cal.get(Calendar.MONTH));
		Date d1 = new Date();
		System.out.println(DateUtils.isSameDay(d1, null));
	}
}
