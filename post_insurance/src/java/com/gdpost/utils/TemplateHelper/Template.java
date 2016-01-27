package com.gdpost.utils.TemplateHelper;


public class Template {
	
	public enum FileTemplate {Policy("承保清单", "QY"), PolicyDtl("承保业务信息清单", "QY"), Issue("问题件工单", "KF"), CallFail("回访不成功清单", "HF"), 
		CallFailStatus("11185回访数据", "HF"), MiniCallFailStatus("简单二访数据", "HF"), CallFailMailStatus("已发信函数据", "HF"), 
		CallFailCityStatus("市县上门回访数据", "HF"), Renewed("续期应交清单", "XQ"), RenewedStatus("续期继续率清单", "XQ"), RenewedHQList("总部催收清单", "XQ"), 
		/*RemitMoney("划扣费清单"), */CheckWrite("抽检填写明细", "QY"), CheckRecord("抽检录入明细", "QY"), PayToFailList("付费失败清单", "ALL"), 
		PayFromFailList("收费失败清单", "ALL"), PaySuccessList("付费成功清单", "ALL");
		private String desc;
		private String type;
		public final int QY_NUM = 7;
		public final int KF_NUM = 4;
		public final int HF_NUM = 8;
		public final int XQ_NUM = 6;
		FileTemplate (String desc, String type) {
			this.desc = desc;
			this.type = type;
		}
		public String getDesc() { 
	        return desc; 
	    }
		public String getType() {
			return type;
		}
		
	}
	
	private String templateValue;

	public String getTemplateValue() {
		return templateValue;
	}

	public void setTemplateValue(String templateValue) {
		this.templateValue = templateValue;
	}
	
}
