package com.gdpost.utils.TemplateHelper;


public class Template {
	
	public enum FileTemplate {Policy("承保清单"), PolicyDtl("承保业务信息清单"), Issue("问题件工单"), CallFail("回访不成功清单"), CallFailStatus("11185回访数据"), 
		MiniCallFailStatus("简单二访数据"), CallFailCityStatus("市县上门回访数据"), Renewed("续期应交清单"), RenewedStatus("续期继续率清单"), RenewedHQList("总部催收清单"), 
		/*RemitMoney("划扣费清单"), */CheckWrite("抽检填写明细"), CheckRecord("抽检录入明细"), PayToFailList("付费失败清单"), PayFromFailList("收费失败清单"), PaySuccessList("付费成功清单");
		private String desc;
		FileTemplate (String desc) {
			this.desc = desc;
		}
		public String getDesc() { 
	        return desc; 
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
