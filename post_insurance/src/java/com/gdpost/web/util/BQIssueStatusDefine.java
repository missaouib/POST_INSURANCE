package com.gdpost.web.util;

public class BQIssueStatusDefine {
	public enum BQ_STATUS {  NewStatus("待处理"), DealStatus("已处理"), CancelStatus("已撤销"), CloseStatus("已关闭");
		private String desc;
		BQ_STATUS (String desc) {
			this.desc = desc;
		}
		public String getDesc() { 
	        return desc; 
	    }
	};
	
	public static void main(String[] args) {
		
		System.out.println(BQ_STATUS.NewStatus.name());
	}
}