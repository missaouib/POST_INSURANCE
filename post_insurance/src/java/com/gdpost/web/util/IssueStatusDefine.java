package com.gdpost.web.util;

public class IssueStatusDefine {
	public enum STATUS {  NewStatus("待处理"), DealStatus("已回复"), ReopenStatus("重打开"), CloseStatus("已结案");
		private String desc;
		STATUS (String desc) {
			this.desc = desc;
		}
		public String getDesc() { 
	        return desc; 
	    }
	};
	
	public static void main(String[] args) {
		STATUS[] s = IssueStatusDefine.STATUS.values();
		System.out.println(s[0].desc);
		
		System.out.println(STATUS.valueOf("DealStatus").desc);
	}
}