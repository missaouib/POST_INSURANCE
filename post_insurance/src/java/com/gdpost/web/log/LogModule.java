/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.log.LogLevel.java
 * Class:			LogLevel
 * Date:			2013-5-3
 * Author:			Aming
 * Version          2.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.gdpost.web.log;

/** 
 * 值越大，等级越高。 	
 * @author 	Aming
 * Version  2.1.0
 * @since   2013-5-3 下午4:29:47 
 */

public enum LogModule {
	BaseDate("基础数据管理"), QYGL("契约管理"), BQGL("保全管理"), KFGL("客服管理"), GGGL("公告管理"), FYGL("扣付费管理"), WJSC("文件上传"),
	HFGL("回访管理"), XQGL("续期管理"), LPGL("理赔管理"), PZGL("配置管理"), QTCZ("其他操作"), FPGL("发票管理");
	private String desc;

	LogModule(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
