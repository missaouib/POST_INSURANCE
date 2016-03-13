package com.gdpost.utils.TemplateHelper;



public class Template {
	
	public enum FileTemplate {Policy("承保清单", "QY"), PolicyDtl("承保业务信息清单", "QY"), PolicyUnderWrite("人核件回单数据", "QY"), Issue("问题件工单", "KF"), CallFail("回访不成功清单", "HF"), 
		CallFailStatus("11185回访数据", "HF"), MiniCallFailStatus("简单二访数据", "HF"), CallFailMailStatus("已发信函数据", "HF"), CallFailMailBackStatus("回邮信函数据", "HF"), CallFailNeedDoorStatus("需上门回访数据", "HF"),
		CallFailCityStatus("市县上门回访数据", "HF"), CallFailCloseStatus("回访结案数据", "HF"), Renewed("续期应交清单", "XQ"), RenewedStatus("续期继续率清单", "XQ"), RenewedHQList("总部催收清单", "XQ"), 
		RenewedProvList("省分催收详情", "XQ"), RenewedCityList("市县催收详情", "XQ"), 
		/*RemitMoney("划扣费清单"), */CheckWrite("抽检填写明细", "QY"), CheckRecord("抽检录入明细", "QY"), PayToFailList("付费失败清单", "ALL"), 
		PayFromFailList("收费失败清单", "ALL"), PaySuccessList("付费成功清单", "ALL");
		private String desc;
		private String type;
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
	
	public static void main(String[] args) {
		FileTemplate[] s = FileTemplate.values();
		String[] tt = new String[]{"ALL"};
		for(FileTemplate ft:s) {
			for(String t:tt) {
				if(ft.getType().equals(t)) {
					System.out.println(ft.name());
				}
			}
		}
	}
}
