package com.gdpost.utils.TemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class Template {
	public static final int POLICY_TEMPLATE = 1;
	public static final int POLICY_DTL_TEMPLATE = 2;
	public static final int ISSUE_TEMPLATE = 3;
	public static final int CALL_FAIL_TEMPLATE = 4;
	public static final int RENEWED_TEMPLATE = 5;
	public static final int REMIT_MONEY_TEMPLATE = 6;
	
	public enum FileTemplate {Policy(1), PolicyDtl(2), Issue(3), CallFail(4), Renewed(5), RemitMoney(6);
		private int index;
		FileTemplate (int idx) {
			this.index = idx;
		}
		public int getIndex() { 
	        return index; 
	    }
	}
	
	public static final List<String> FileTemplate = new ArrayList<String>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 5659627182743937031L;

		{add("POLICY_TEMPLATE"); add("POLICY_DTL_TEMPLATE"); add("ISSUE_TEMPLATE"); add("CALL_FAIL_TEMPLATE"); add("RENEWED_TEMPLATE"); add("REMIT_MONEY_TEMPLATE");}};
}
