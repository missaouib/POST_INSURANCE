package com.gdpost.web.util;

public class DoRst {

	public boolean rstFlag = true;
	public String msg = null;
	public int num = 0;
	public int updateRow = -1;
	public boolean isFlag() {
		return rstFlag;
	}
	public void setFlag(boolean flag) {
		this.rstFlag = flag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getUpdateRow() {
		return updateRow;
	}
	public void setUpdateRow(int updateRow) {
		this.updateRow = updateRow;
	}
	
	
}
