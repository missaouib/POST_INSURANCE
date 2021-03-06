package com.gdpost.utils.TemplateHelper;



public class Template {
	
	/**
	 * 
	 * @author Aming
	 *
	 */
	public enum FileTemplate {Policy("承保清单", "QY"), /*PolicyDtl("承保业务信息清单", "QY"),*/ NewPolicyDtl("承保业务清单（新）", "QY"), UWDtlData("手工单时效数据", "QY"), 
		UnderWriteSentData("打印数据", "QY"), PolicyBackDate("保单回单数据", "QY"), UnderWriteDtlData("总部人核件详细数据", "QY"), UnderWriteData("人核件登记数据", "QY"), UnderWriteRemark("市县人核件跟进数据", "QY"), UnderWriteInsured("被保险人身份数据", "QY"), DocNotScanDtl("未扫描档案明细", "QY"),
		/*Issue("问题件工单", "KF"), */IssuePFR("问题件工单（新）", "KF"),Inquire("咨询件工单", "KF"),IssuePFRDeal("问题件工单处理数据", "KF"), /*CallFail("回访不成功清单", "HF"),*/ 
		CallFailPFR("回访不成功清单（新）", "HF"), /*CallFailStatus("11185回访数据", "HF"),*/ MiniCallFailStatus("简单二访数据", "HF"), CallRatio("回访工单数据报表", "HF"), 
		CallFailMailStatus("已发信函数据", "HF"), CallFailMailBackStatus("退信信函数据", "HF"), CallFailMailSuccessStatus("信函成功回邮数据", "HF"), 
		CallFailNeedDoorStatus("需上门回访数据", "HF"), CallFailCityStatus("市县上门回访数据", "HF"), CallFailMiniCityStatus("市县回访详细数据", "HF"), 
		CallFailCloseStatus("回访结案数据", "HF"), CallFailPhoneStatus("二访通话清单", "HF"), RenewedFollow("市县大额保单反馈", "XQ"), Renewed("续期应交清单", "XQ"), RenewedProvList("省分续期催收底稿", "XQ"), RenewedCityList("机构续期催收底稿", "XQ"), RenewedFeeRst("市县催收反馈", "XQ"),
		/*RenewedStatus("续期继续率清单", "XQ"), RenewedFeeMatchList("余额匹配结果及省分催收数据", "XQ"),*/
		CheckWrite("抽检填写明细", "QY"), CheckRecord("抽检录入明细", "QY"),CheckCityBack("市县新契约填写整改明细", "QY"), ConversationReq("保全免填单申请", "BQ"), 
		ConversationReport("保全月报", "BQ"),CsLoan("保全质押借款未还报表", "BQ"), CsExpire("保全满期数据", "BQ"), Settlement("理赔未结案清单","LP"), ClaimsCloseReport("理赔结案清单","LP"), PayToFailList("付费失败清单", "ALL"), 
		PayFromFailList("收费失败清单", "ALL"), PaySuccessList("付费成功清单", "ALL"), PayFromSuccessList("收费成功清单", "ALL"), PolicyUnderWrite("人核件回单数据", "QT"), StatCity("考核结果（市）", "ALL"), StatArea("考核结果（县区）", "ALL");
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
