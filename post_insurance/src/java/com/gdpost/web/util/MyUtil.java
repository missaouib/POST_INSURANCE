package com.gdpost.web.util;

import java.util.Arrays;
import java.util.List;

public class MyUtil {

	static final String[] dxs = new String[]{"133","149","153","173","177","180","181","189"};
	static final List<String> dx = Arrays.asList(dxs);
	
	static final String[] yds = new String[]{"134","135","136","137","138","139","147","150","151","152","157","158","159","172","178","182","183","184","187","188"};
	static final List<String> yd = Arrays.asList(yds);

	static final String[] lts = new String[]{"130","131","132","145","155","156","171","175","176","185","186"};
	static final List<String> lt = Arrays.asList(lts);
	
	public static String getFeeValue(String feeMatch, String phone, Double policyFee) {
		if (policyFee == null) policyFee = new Double(0);
		if(feeMatch != null && feeMatch.trim().equals("可用余额不足")) {
			if(dx.contains(phone)) {
				if(policyFee>=10000) {
					return "30";
				} else {
					return "0";
				}
			} else if(yd.contains(phone) || lt.contains(phone)) {
				if(policyFee>=30000) {
					return "30";
				} else if(policyFee>=10000 && policyFee<30000) {
					return "20";
				} else {
					return "10";
				}
			}
		}
		return "";
 	}
}
