package com.gdpost.web.util;

public class StatusDefine {
	public enum STATUS {
		NewStatus("待处理"), DealStatus("已回复"), ReopenStatus("重打开"), CloseStatus("已结案");
		private String desc;

		STATUS(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}
	};

	public enum BQ_STATUS {
		NewStatus("待处理"), DealStatus("已处理"), CancelStatus("已撤销"), CloseStatus("已关闭");
		private String desc;

		BQ_STATUS(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}
	};
	
	public enum FP_STATUS {
		NewStatus("待处理"), DealStatus("已寄出"), ReceiveStatus("已接收"), CloseStatus("已关闭");
		private String desc;

		FP_STATUS(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}
	};

	public static void main(String[] args) {
		STATUS[] s = StatusDefine.STATUS.values();
		System.out.println(s[0].desc);

		System.out.println(STATUS.valueOf("DealStatus").desc);
	}
}