package com.gdpost.utils.TemplateHelper;

public class ConcatRule {
	// 从中获取的原始字符串值
	private String concatValue = "";
	// 从中获取的原始字符串值
	private String rule = "";
	// 分割后返回的分割序号
	private int dataIndex = 0;
	// 分割规则
	ConcatRule concatRule = null;
	
	public String getConcatValue() {
		return concatValue;
	}
	public void setConcatValue(String concatValue) {
		this.concatValue = concatValue;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public int getDataIndex() {
		return dataIndex;
	}
	public void setDataIndex(int dataIndex) {
		this.dataIndex = dataIndex;
	}
	public ConcatRule getConcatRule() {
		return concatRule;
	}
	public void setConcatRule(ConcatRule concatRule) {
		this.concatRule = concatRule;
	}
	
	public String getConcatResult() {
		String strResult = this.concatValue;
		
		String strRex = this.rule;
		//strRex = strRex.replace("\\", "\\\\");
		//strRex = strRex.replace(".", "\\.");
		
		String[] aryResult = strResult.split(strRex);
		if(aryResult.length >= this.dataIndex) {
			strResult = aryResult[this.dataIndex - 1];
		} else {
			strResult = aryResult[0];
		}
		
		if(this.concatRule != null) {
			this.concatRule.setConcatValue(strResult);
			strResult = this.concatRule.getConcatResult();
		}
		
		return(strResult);
	}
}
