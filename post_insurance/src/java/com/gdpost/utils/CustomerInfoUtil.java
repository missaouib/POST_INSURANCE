package com.gdpost.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerInfoUtil {

	private static final Logger LOG = LoggerFactory.getLogger(CustomerInfoUtil.class);

	/**
	 * 姓名不能为数字
	 * 电话不能同时为空
	 * 其他传过来的数据都不能为空
	 * 
	 * @param holder
	 * @param holderAge
	 * @param holderCardType
	 * @param holderCardNum
	 * @param holderCardValid
	 * @param insured
	 * @param insuredCardType
	 * @param insuredCardNum
	 * @param insuredAge
	 * @param phone
	 * @param mobile
	 * @param email
	 * @param addr
	 * @return
	 */
	public static String checkData(Statement stat, String policyNo, String holder, Integer holderAge, String holderCardType, String holderCardNum, String holderCardValid, 
			String insured, String insuredCardType, String insuredCardNum, Integer insuredAge, String relation, String phone, String mobile, String email, String addr) {
		StringBuffer str = new StringBuffer("");
		if(holder == null || insured == null || NumberUtils.isDigits(holder) || NumberUtils.isDigits(insured)) {
			str.append("投保人姓名或者被保险人姓名有误;");
		}
		if((phone == null || phone.trim().length()<=0) && (mobile == null || mobile.trim().length()<=0)) {
			str.append("联系方式全为空;");
		}
		if(!checkEmail(email)) {
			str.append("Email地址有误;");
		}
		str.append(checkLogic(holderAge, insuredAge, insuredCardType, insuredCardNum, relation));
		
		boolean isSales = isSalesPhone(stat, holder, mobile);
		if(isSales) {
			str.append("使用了销售人员电话出单;");
		}
		
		str.append(checkAddr(stat, policyNo, addr, isSales));
		
		//str.append(checkDateValid(holderCardValid));
		if( !checkMobile(mobile)) {
			str.append("手机号码有误;");
		}
		if( !checkPhone(phone)) {
			str.append("固定号码有误;");
		}
		str.append(checkCardInfo(holderCardType, holderCardNum));
		str.append(checkCardInfo(insuredCardType, insuredCardNum));
		return str.toString();
	}
	
	/**
	 * 如果空，返回true
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		// and (holder_email not regexp
		// '^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$'
		// or holder_email regexp '//..*')
		LOG.debug("---- CHECK EMAIL ----" + email);
		if (email == null || email.trim().length() <= 0) {
			return true;
		}
		String check = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		boolean isMatched = matcher.matches();
		if(!isMatched) {
			return false;
		}
		
		String c2 = "//..*";
		Pattern r2 = Pattern.compile(c2);
		Matcher m2 = r2.matcher(email);
		boolean isM2 = m2.matches();
		if(isM2) {
			return false;
		}
		//TODO:增加Email校验规则，如QQ邮箱的规则等
		String ss = email.substring(email.indexOf("@") + 1);
		String name = email.substring(0,email.indexOf("@"));
		
		boolean patch = true;
		switch(ss) {
		case "163.com":
		case "126.com":
		case "yeah.com":
			if (name.length()<6) return false;
			break;
		case "qq.com":
			if (NumberUtils.isDigits(name) && name.length()<5) return false;
			break;
		case "139.com":
		case "wo.com":
		case "189.com":
			if (NumberUtils.isDigits(name) && name.length()<11) return false;
			break;
		case "sina.com":
			if (name.length()<4) return false;
			break;
		}
		
		if (isMatched && !isM2 && patch) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param holdeAge
	 * @param holderSexy
	 * @param insuredAge 
	 * @param relation 被保险人是投保人的
	 * @return
	 */
	public static String checkLogic(Integer holderAge, Integer insuredAge, String insuredCardType, String insuredCardNum, String relation) {
		
		StringBuffer str = new StringBuffer("");
		if(holderAge == null || insuredCardType==null || insuredAge == null || relation == null || insuredCardNum == null) {
			return "客户证件信息不完整;";
		}
		if (relation.equals("本人")) {
			return "";
		}
		//List<String> older = Arrays.asList("父亲","母亲","祖父","祖母","外祖父","外祖母","哥哥","姐姐","公公","婆婆","岳父","岳母");
		//List<String> younger = Arrays.asList("儿子","女儿","孙子","孙女","外孙子","外孙女","弟弟","妹妹","儿媳","女婿");
		List<String> olderMale = Arrays.asList("父亲","祖父","外祖父","哥哥","公公","岳父");
		List<String> olderFemale = Arrays.asList("母亲","祖母","外祖母","姐姐","婆婆","岳母");
		List<String> youngerMale = Arrays.asList("儿子","孙子","外孙子","弟弟","女婿");
		List<String> youngerFemale = Arrays.asList("女儿","孙女","外孙女","妹妹","儿媳");
		if(insuredCardType.equals("身份证") || insuredCardType.equals("户口本") || insuredCardType.equals("0") || insuredCardType.equals("4")) {
			Integer flag = new Integer(insuredCardNum.substring(16, 17));
			if (flag % 2 ==0) { //女
				if (holderAge > insuredAge) {
					if (youngerMale.contains(relation)) {
						str.append("关系不符逻辑要求;");
					}
				} else {
					if (olderMale.contains(relation)) {
						str.append("关系不符逻辑要求;");
					}
				}
			} else { //男
				if (holderAge > insuredAge) {
					if (youngerFemale.contains(relation)) {
						str.append("关系不符逻辑要求;");
					}
				} else {
					if (olderFemale.contains(relation)) {
						str.append("关系不符逻辑要求;");
					}
				}
			}
		}
		
		return str.toString();
	}

	/**
	 * 规则描述：
	 * 1、长度＜7位
	 * 2、地址库结尾校验：以县区（镇/区）结尾，镇数据没有维护
	 * 3、以“附近"、对面、旁边、“路”“街”，“道”，“栋、幢"结尾、市场、小区、开发区、工业区
	 * 4、含有“邮政……等字样（待完善）
	 * 5、不含数字以及中文数字
	 * 6、以村结尾：要含有乡、镇、村、屯/组
	 * 
	 * @param stat
	 * @param addr
	 * @return
	 */
	public static String checkAddr(Statement stat,String policyNo, String addr, boolean isSales) {
		StringBuffer str = new StringBuffer("");
		LOG.debug(" --------- addr: " + addr);
		//1、长度校验
		if(addr == null || addr.trim().length()<=7) {
			return "地址长度太短;";
		}
		boolean hasNum = false;
		String pattern = ".*[一二三四五六七八九零十百千万亿〇壹贰叁肆伍陆柒捌玖０１２３４５６７８９0-9a-zA-Z]+.*";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(addr);
		if(matcher.matches()) {
			hasNum = true;
		}
		boolean endNum = false;
		String p2 = ".*[一二三四五六七八九零十百千万亿〇壹贰叁肆伍陆柒捌玖０１２３４５６７８９0-9a-zA-Z]+$";
		Pattern regex2 = Pattern.compile(p2);
		Matcher matcher2 = regex2.matcher(addr);
		if(matcher2.matches()) {
			endNum = true;
		}
		
		boolean endCharNum = false;
		String p3 = ".*[a-zA-Z]+.[一二三四五六七八九零十百千万亿〇壹贰叁肆伍陆柒捌玖０１２３４５６７８９0-9]+.*";
		Pattern regex3 = Pattern.compile(p3);
		Matcher matcher3 = regex3.matcher(addr);
		if(matcher3.matches()) {
			endCharNum = true;
		}
		
		boolean endNumChar = false;
		String p5 = ".*[一二三四五六七八九零十百千万亿〇壹贰叁肆伍陆柒捌玖０１２３４５６７８９0-9]{1,}[a-zA-Z]+.*";//".*[0-9][a-zA-Z]+$";
		endNumChar = addr.matches(p5);
		
		boolean hasDownTown = false;
		String p4 = ".*[庄屯村湾堡铺店寨关围垸屋埠嘴岗冈厦驿坝庭轩梯]+.*";
		Pattern regex4 = Pattern.compile(p4);
		Matcher matcher4 = regex4.matcher(addr);
		if(matcher4.matches()) {
			hasDownTown = true;
		}
		
		boolean isTown = false;
		if(addr.contains("村")) {
			isTown = true;
		}
		
		//2、地址库结尾校验。
		//拿到地址库的地址
		String sql = "select area,city from t_addr_lib";
		
		ResultSet rst = null;
		try {
			rst = stat.executeQuery(sql);
			//boolean backAddr = false;
			//String town = null;
			String area = null;
			String city = null;
			String ap = addr.indexOf("省") ==-1?"":addr.substring(0, addr.indexOf("省"));
			String ac = addr.indexOf("市") == -1?"":addr.substring(0, addr.indexOf("市"));
			boolean bah = false;
			boolean blen = true;
			boolean hasCity = false;
			if(ap.length()<=0 && ac.length()<=0) { //如果没有省也没有市的
				bah = false;
			}
			while(rst != null && rst.next()) {
				area = rst.getString("area");
				city = rst.getString("city");
				if(addr.contains(city)) {
					hasCity = true;
				}
				if(!bah) {
					if((addr.contains("广东") && addr.contains(city)) 
							|| (addr.contains("广东") && addr.contains(area))) {
						bah = true; //带有广东或者地市开头
					}
					if((addr.contains(city) && addr.contains(area))) {
						bah = true; //带有广东或者地市开头
					} else {
						if((addr.contains("省") && addr.contains("市"))
								|| (addr.contains("省") && addr.contains("县"))) {
							bah = true;
							if((addr.contains("市") && (addr.length() - addr.indexOf("市") <= 7)) 
									|| (addr.contains("县") && (addr.length() - addr.indexOf("县") < 7))) {
								blen = false;
								//return "地址不够详细1-length；";
							}
						}
						if((addr.contains("市") && addr.contains("县"))
								|| (addr.contains("市") && addr.contains("区"))
								|| (addr.contains("市") && addr.contains("镇"))) {
							bah = true;
							if((addr.contains("市") && (addr.length() - addr.indexOf("市") <= 7)) 
									|| (addr.contains("区") && !addr.contains("工业") && !addr.contains("开发") && (addr.length() - addr.indexOf("区") < 7))
									|| (addr.contains("县") && (addr.length() - addr.indexOf("县") < 7))) {
								blen = false;
								//return "地址不够详细1-length；";
							}
						}
						if((addr.contains("省") && addr.contains("市") && addr.contains("镇"))
								|| (addr.contains("省") && addr.contains("县") && addr.contains("镇"))) {
							bah = true;
							if(addr.length() - addr.indexOf("镇") <= 5) {
								blen = false;
								//return "地址不够详细1-length；";
							}
						}
					}
				}
				if((addr.contains(city) && (addr.length() - addr.indexOf(city) <= 7)) 
						|| (addr.contains(area) && (addr.length() - addr.indexOf(area) < 7))) {
					blen = false;
					//return "地址不够详细1-length；";
				}
			}
			if(hasCity) {
				if((addr.length() - addr.indexOf(city) <= 7)) {
					blen = false;
					//return "地址不够详细1-length；";
				}
			}
			
			if(!isSales && (addr.contains("邮政") || addr.contains("营业所") || addr.contains("邮局") || addr.endsWith("邮储") || addr.endsWith("支局") || addr.endsWith("支行"))) {
				return "地址含有邮政关键信息;";
			}
			
			if( !hasNum ) {
				if(!blen && !isTown) {
					return "地址疑似不够详细2；省市后信息不详;";
				}
			}
			/*
			if((!blen && !hasNum) && !isTown) {
				return "地址疑似不够详细2；省市后信息不详;";
			}
			*/
			
			if(addr.endsWith("附近") || addr.endsWith("对面") || addr.endsWith("旁边") 
					|| addr.endsWith("路") || addr.endsWith("街") || addr.endsWith("道") || (!isTown && !hasNum && addr.endsWith("里")) || (!hasNum && addr.endsWith("巷")) 
					|| (!isTown && !hasNum && addr.endsWith("园")) || (!hasNum && addr.endsWith("区")) || addr.endsWith("镇") || addr.endsWith("乡") || addr.endsWith("县")) {
				return "地址不够详细3-end;";
			}
			if(!hasNum && (addr.endsWith("栋") || addr.endsWith("幢") || addr.endsWith("楼"))) {
				return "地址缺门牌号码;";
			}
			
			if (endNum && !hasDownTown && !isTown && !addr.contains("栋") && !addr.contains("幢") && !addr.contains("楼") && !addr.contains("座") && !addr.contains("层") 
					&& !addr.contains("阁") && !addr.contains("榭") && !addr.contains("里") && !addr.contains("巷") && !addr.contains("厝") && !addr.contains("-") && !addr.contains("号")) {
				if(endCharNum || endNumChar || addr.contains("梯") || (addr.contains("镇") && (addr.contains("街道") || addr.contains("居委"))) || (addr.contains("路") || addr.contains("道") || addr.contains("街")) 
						&& (addr.length()-addr.indexOf("路")>5 || addr.length()-addr.indexOf("道")>5 || addr.length()-addr.indexOf("街")>5)) {
					//nothing
				} else {
					return "地址不够详细4-end with num;";
				}
			}
			/*
			 * 如果以上条件都满足的话，估计这个柜面出单的省市县信息是对的了。
			 * 只能这样子了，无可奈何 20190123 closed
			if(!bah && policyNo.startsWith("8644")) {
				return "地址疑似不够详细1；缺省市信息;";
			}
			*/
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rst != null) rst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return str.toString();
	}
	
	public static String testAddr (String policyNo, String addr) {
		String area = "凤岗";
		String city = "东莞";
		StringBuffer str = new StringBuffer("");
		LOG.debug(" --------- addr: " + addr);
		//1、长度校验
		if(addr == null || addr.trim().length()<=7) {
			return "地址长度太短;";
		}
		boolean hasNum = false;
		String pattern = ".*[一二三四五六七八九零十百千万亿〇壹贰叁肆伍陆柒捌玖０１２３４５６７８９0-9a-zA-Z]+.*";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(addr);
		if(matcher.matches()) {
			hasNum = true;
		}
		LOG.debug(" --------- hasNum: " + hasNum);
		boolean endNum = false;
		String p2 = ".*[一二三四五六七八九零十百千万亿〇壹贰叁肆伍陆柒捌玖０１２３４５６７８９0-9a-zA-Z]+$";
		Pattern regex2 = Pattern.compile(p2);
		Matcher matcher2 = regex2.matcher(addr);
		if(matcher2.matches()) {
			endNum = true;
		}
		
		boolean endCharNum = false;
		String p3 = ".*[a-zA-Z]+.[一二三四五六七八九零十百千万亿〇壹贰叁肆伍陆柒捌玖０１２３４５６７８９0-9]+.*";
		Pattern regex3 = Pattern.compile(p3);
		Matcher matcher3 = regex3.matcher(addr);
		if(matcher3.matches()) {
			endCharNum = true;
		}
		
		boolean hasDownTown = false;
		String p4 = ".*[庄屯村湾堡铺店寨关围垸屋埠嘴岗冈厦驿坝庭轩梯]+.*";
		Pattern regex4 = Pattern.compile(p4);
		Matcher matcher4 = regex4.matcher(addr);
		if(matcher4.matches()) {
			hasDownTown = true;
		}
		
		boolean endNumChar = false;
		String p5 = ".*[一二三四五六七八九零十百千万亿〇壹贰叁肆伍陆柒捌玖０１２３４５６７８９0-9]{1,}[a-zA-Z]+.*";//".*[0-9][a-zA-Z]+$";
		endNumChar = addr.matches(p5);
		
		//2、地址库结尾校验。
		//拿到地址库的地址
		
		//boolean backAddr = false;
		//String town = null;
		String ap = addr.indexOf("省") ==-1?"":addr.substring(0, addr.indexOf("省"));
		String ac = addr.indexOf("市") == -1?"":addr.substring(0, addr.indexOf("市"));
		boolean bah = false;
		boolean blen = true;
		boolean hasCity = false;
		boolean isTown = false;
		if(ap.length()<=0 && ac.length()<=0) { //如果没有省也没有市的
			bah = false;
		}
		if(addr.contains(city)) {
			hasCity = true;
		}
		if(addr.contains("村")) {
			isTown = true;
		}
		LOG.debug(" --------- bah: " + bah);
		if(!bah) {
			if((addr.contains("广东") && addr.contains(city)) 
					|| (addr.contains("广东") && addr.contains(area))) {
				bah = true; //带有广东或者地市开头
				LOG.debug(" --------- bah2: " + bah);
			}
			if((addr.contains(city) && addr.contains(area))) {
				bah = true; //带有广东或者地市开头
				LOG.debug(" --------- bah3: " + bah);
			} else {
				if((addr.contains("省") && addr.contains("市"))
						|| (addr.contains("省") && addr.contains("县"))) {
					bah = true;
					LOG.debug(" --------- bah4: " + bah);
					LOG.debug(" --------- in blen 00: " + (addr.length() - addr.indexOf("市")));
					LOG.debug(" --------- in blen 01: " + (addr.length() - addr.indexOf("县")));
					if((addr.contains("市") && (addr.length() - addr.indexOf("市") <= 5)) 
							|| (addr.contains("县") && (addr.length() - addr.indexOf("县") <= 7))) {
						blen = false;
						//return "地址不够详细1-length；";
					}
				}
				if((addr.contains("市") && addr.contains("县"))
						|| (addr.contains("市") && addr.contains("区"))
						|| (addr.contains("市") && addr.contains("镇"))) {
					bah = true;
					LOG.debug(" --------- bah5: " + bah);
					LOG.debug(" --------- in blen 10: " + (addr.length() - addr.indexOf("区")));
					LOG.debug(" --------- in blen 11: " + (addr.length() - addr.indexOf("县")));
					if((addr.contains("市") && (addr.length() - addr.indexOf("市") <= 5)) 
							|| (addr.contains("区") && !addr.contains("工业") && !addr.contains("开发") && (addr.length() - addr.indexOf("区") <= 7))
							|| (addr.contains("县") && (addr.length() - addr.indexOf("县") <= 7))) {
						blen = false;
						//return "地址不够详细1-length；";
					}
				}
				if((addr.contains("省") && addr.contains("市") && addr.contains("镇"))
						|| (addr.contains("省") && addr.contains("县") && addr.contains("镇"))) {
					bah = true;
					LOG.debug(" --------- bah6: " + bah);
					LOG.debug(" --------- in blen 20: " + (addr.length() - addr.indexOf("镇")));
					if(addr.length() - addr.indexOf("镇") <= 5) {
						blen = false;
						//return "地址不够详细1-length；";
					}
				}
			}
		}
		LOG.debug(" --------- blen1: " + blen);
		LOG.debug(" --------- in blen 30: " + (addr.length() - addr.indexOf(city)));
		LOG.debug(" --------- in blen 31: " + (addr.length() - addr.indexOf(area)));
		if((addr.contains(city) && (addr.length() - addr.indexOf(city) <= 7)) 
				|| (addr.contains(area) && (addr.length() - addr.indexOf(area) <= 7))) {
			blen = false;
			//return "地址不够详细1-length；";
		}
		LOG.debug(" --------- blen2: " + blen);
		if(hasCity) {
			LOG.debug(" --------- in blen 40: " + (addr.length() - addr.indexOf(city)));
			if((addr.length() - addr.indexOf(city) <= 7)) {
				blen = false;
				
				//return "地址不够详细1-length；";
			}
		}
		LOG.debug(" --------- blen3: " + blen);
		if( !hasNum ) {
			if(!blen && !isTown) {
				return "地址疑似不够详细2；省市后信息不详;";
			}
		}
		/*
		if((!blen && !hasNum) && !isTown) {
			return "地址疑似不够详细2；省市后信息不详;";
		}
		*/
		if(addr.endsWith("附近") || addr.endsWith("对面") || addr.endsWith("旁边") 
				|| addr.endsWith("路") || addr.endsWith("街") || addr.endsWith("道") || ((!isTown && !hasNum) && addr.endsWith("里")) || ((!isTown && !hasNum) && addr.endsWith("巷")) 
				|| (!isTown && !hasNum && addr.endsWith("园")) || addr.endsWith("区") || addr.endsWith("镇") || addr.endsWith("乡") || addr.endsWith("县")) {
			return "地址不够详细3-end;";
		}
		if(!hasNum && (addr.endsWith("栋") || addr.endsWith("幢") || addr.endsWith("楼"))) {
			return "地址缺门牌号码;";
		}
		if (endNum && !hasDownTown && !isTown && !addr.contains("栋") && !addr.contains("幢") && !addr.contains("楼") && !addr.contains("座") && !addr.contains("层") 
				&& !addr.contains("阁") && !addr.contains("榭") && !addr.contains("里") && !addr.contains("巷") && !addr.contains("厝") && !addr.contains("-") && !addr.contains("号")) {
			if(endCharNum || endNumChar || (addr.contains("路") || addr.contains("道") || addr.contains("街")) 
					&& (addr.length()-addr.indexOf("路")>5 || addr.length()-addr.indexOf("道")>5 || addr.length()-addr.indexOf("街")>5)) {
				//nothing
			} else {
				return "地址不够详细4-end with num;";
			}
		}
		return str.append("addr is good!").toString();
	}
	
	/**
	 * 
	 * @param stat
	 * @param holder
	 * @param phone
	 * @return
	 */
	public static boolean isSalesPhone(Statement stat, String holder, String phone) {
		if(phone == null || phone.trim().length()<=10 || !NumberUtils.isDigits(phone)) {
			return false;
		}
		String sql = "select sales_name,phone from t_sales where phone=\"" + phone + "\"";
		ResultSet rst = null;
		try {
			rst = stat.executeQuery(sql);
			while(rst != null && rst.next()) {
				if(rst.getString("sales_name") != null && !rst.getString("sales_name").equals(holder)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rst != null) rst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 这个校验主要在存储过程中校验，但是为了尽可能在同一个客户信息数据中体现问题，在校验的时候同样被调用和校验。
	 * 
	 * pattern: yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String checkDateValid(String date) {
		if (date == null || date.trim().length() <= 0 || date.trim().equals("长期")) {
			return "";
		}
		Date d1 = StringUtil.str2Date(date, "yyyy-MM-dd");
		int dc = StringUtil.getBetweenDay(new Date(), d1);
		if (dc <= 5) {
			return "证件有效期5日内失效;";
		}
		return "";
	}

	/**
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		if (mobile == null || mobile.trim().length() <= 0) {
			return true;
		}
		String check = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[0-9]))\\d{8}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(mobile);
		return matcher.matches();
	}

	/**
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean checkPhone(String phone) {
		// ^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$
		if (phone == null || phone.trim().length() <= 0) {
			return true;
		}
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}(-??)[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		if (phone.length() > 9) {
			m = p1.matcher(phone);
			b = m.matches();
		} else {
			m = p2.matcher(phone);
			b = m.matches();
		}
		return b;
	}

	/**
	 * 
	 * @param type
	 * @param cardNum
	 * @return
	 */
	public static String checkCardInfo(String type, String cardNum) {
		StringBuffer str = new StringBuffer("");
		switch (type) {
		case "身份证":
		case "户口本":
			if (cardNum == null || cardNum.trim().length() < 18) {
				str.append("证件号码长度不符；");
				break;
			}
			if(!is18ByteIdCardComplex(cardNum)) {
				str.append("证件号码不符合身份证号码标准；");
			}
			break;
		case "出生证":
			if (cardNum == null || cardNum.trim().length() != 8) {
				str.append("出生证号码应为8位出生年月日；");
				break;
			}
			String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";  
			Pattern pat = Pattern.compile(rexp);    
			Matcher mat = pat.matcher(cardNum);    
			boolean dateType = mat.matches();
			if(!dateType) {
				str.append("出生证号码不符合日期格式；");
			}
			break;
		case "港澳居民来往内地通行证":
			String check = "^([H|M|C])([A-Z^I^O]??)\\d{8,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(cardNum);
			if (!matcher.matches()) {
				str.append("港澳通行证证件号码不符合格式要求；");
			}
			break;
		default:
			break;
		}
		return str.toString();
	}

	/*
	 * =======================================
	 * 身份证校验
	 * =======================================
	 */
    private final static Map<String, String> cityMap;
    static {
        cityMap = new HashMap<>();
        cityMap.put("11", "北京");
        cityMap.put("12", "天津");
        cityMap.put("13", "河北");
        cityMap.put("14", "山西");
        cityMap.put("15", "内蒙古");
 
        cityMap.put("21", "辽宁");
        cityMap.put("22", "吉林");
        cityMap.put("23", "黑龙江");
 
        cityMap.put("31", "上海");
        cityMap.put("32", "江苏");
        cityMap.put("33", "浙江");
        cityMap.put("34", "安徽");
        cityMap.put("35", "福建");
        cityMap.put("36", "江西");
        cityMap.put("37", "山东");
 
        cityMap.put("41", "河南");
        cityMap.put("42", "湖北");
        cityMap.put("43", "湖南");
        cityMap.put("44", "广东");
        cityMap.put("45", "广西");
        cityMap.put("46", "海南");
 
        cityMap.put("50", "重庆");
        cityMap.put("51", "四川");
        cityMap.put("52", "贵州");
        cityMap.put("53", "云南");
        cityMap.put("54", "西藏");
 
        cityMap.put("61", "陕西");
        cityMap.put("62", "甘肃");
        cityMap.put("63", "青海");
        cityMap.put("64", "宁夏");
        cityMap.put("65", "新疆");
 
        cityMap.put("71", "台湾");
        cityMap.put("81", "香港");
        cityMap.put("82", "澳门");
        cityMap.put("91", "国外");
    }
 
    //粗略的校验
    private static final Pattern pattern = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$");
 
    /**
     * 18位身份证校验,粗略的校验
     * @author lyl
     * @param idCard
     * @return
     */
    public static boolean is18ByteIdCard(CharSequence idCard){
        return pattern.matcher(idCard).matches();
    }
    /**
     * 18位身份证校验,比较严格校验
     * @author lyl
     * @param idCard
     * @return
     */
    public static boolean is18ByteIdCardComplex(CharSequence idCard){
    	LOG.debug(" ----------- check idCard: " + idCard);
        int[] prefix = new int[]{7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
        char[] suffix = new char[]{ '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
        if(is18ByteIdCard(idCard)){
            if(cityMap.get(idCard.subSequence(0,2).toString()) == null ){
                return false;
            }
            int idCardWiSum=0;  //用来保存前17位各自乖以加权因子后的总和
            for(int i=0;i<17;i++){
                idCardWiSum+=(idCard.charAt(i) - '0') *prefix[i];
            }
 
            int idCardMod=idCardWiSum%11;//计算出校验码所在数组的位置
            char idCardLast=idCard.charAt(17);//得到最后一位身份证号码
 
            return idCardLast == suffix[idCardMod];
        }
        return false;
    }

	public static void main(String[] args) {
		
		//String str = "441827197812105641";
		//boolean i1 = CustomerInfoUtil.is18ByteIdCardComplex(str);
		//boolean i2 = CustomerInfoUtil.is18ByteIdCard(str);
		//System.out.println(i1);
		//System.out.println(i2);
		//System.out.println(str.substring(16, 17));
		
		//String email = "12345@qq..com";
		//System.out.println(CustomerInfoUtil.checkEmail(email));
		
		//String city = "肇庆";
		//String area = "四会";
		String addr = "惠州市惠阳区淡水东方新城147B";
		//System.out.println(addr.length() - addr.indexOf(city));
		//System.out.println(addr.length() - addr.indexOf(area));
		System.out.println(CustomerInfoUtil.testAddr("814400000124544", addr));
		//一二三四五六七八九零十百千万亿〇壹贰叁肆伍陆柒捌玖０１２３４５６７８９
		//".*\\d+.*"
		/**/
		String pattern =  ".*[一二三四五六七八九零十百千万亿〇壹贰叁肆伍陆柒捌玖０１２３４５６７８９0-9]{1,}[a-zA-Z]+.*";//".*[0-9][a-zA-Z]+$";
		System.out.println(addr.matches(pattern));
		
		
		//String type = "港澳居民来往内地通行证";
		//String num = "44162319760819001X";
		//System.out.println(CustomerInfoUtil.is18ByteIdCardComplex(num));
		/*
		String pattern = ".*[一二三四五六七八九零十百千万亿〇壹贰叁肆伍陆柒捌玖０１２３４５６７８９0-9]+$";
		Pattern p=Pattern.compile(pattern);
		String str = "东莞市清溪镇12e323元厦坭园宝厂０";
        System.out.println(p.matcher(str).matches());;
        */
	}
}
