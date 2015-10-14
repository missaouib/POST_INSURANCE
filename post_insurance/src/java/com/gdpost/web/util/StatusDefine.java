package com.gdpost.web.util;

public class StatusDefine {
	public enum STATUS {
		NewStatus("待处理"), IngStatus("处理中"), DealStatus("已回复"), ReopenStatus("重打开"), CloseStatus("已结案");
		private String desc;

		STATUS(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}
	};
	
	public enum HF_STATUS {
		NewStatus("待处理"), DealStatus("已回复"), ResetStatus("已重置"), DoorSuccessStatus("上门成功"), DoorFailStatus("上门失败"), CallSuccessStatus("二访成功"), CallFailStatus("二访失败"), CloseStatus("已结案");
		private String desc;

		HF_STATUS(String desc) {
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
	
	public enum XQ_STATUS {
		NewStatus("未收"), FeeFailStatus("交费失败"), SuspendedStatus("挂起"), BqSuspendedStatus("保全挂起"), CloseStatus("交费成功");
		private String desc;

		XQ_STATUS(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}
	};
	
	public enum XQ_DEAL_STATUS {
		SuccessStatus("成功"), FailStatus("不成功件"), ProblemStatus("问题件"), TerminateStatus("已退保");
		private String desc;

		XQ_DEAL_STATUS(String desc) {
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

	public enum FEE_FAIL_STATUS {
		NewStatus("待关闭"), CloseStatus("已关闭");
		private String desc;

		FEE_FAIL_STATUS(String desc) {
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