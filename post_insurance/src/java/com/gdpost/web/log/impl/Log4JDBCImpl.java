/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.log.Log4JDBC.java
 * Class:			Log4JDBC
 * Date:			2013-5-3
 * Author:			sendtend
 * Version          2.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.gdpost.web.log.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.entity.main.LogInfo;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.service.LogInfoService;
import com.gdpost.web.shiro.ShiroUser;

/** 
 * 全局日志等级<包日志等级<类和方法日志等级
 * @author 	sendtend
 * Version  2.1.0
 * @since   2013-5-3 下午4:41:55 
 */
public class Log4JDBCImpl extends LogAdapter {
	
	private LogLevel rootLogLevel = LogLevel.ERROR;
	
	private LogInfoService logInfoService;
	
	private Map<String, LogLevel> customLogLevel = new HashMap<String, LogLevel>();

	/**
	 * 
	 * @param message
	 * @param objects
	 * @param logLevel  
	 * @see com.gdpost.web.log.impl.LogAdapter#log(java.lang.String, java.lang.Object[], com.gdpost.web.log.LogLevel)
	 */
	@Override
	public void log(String message, Object[] objects, LogLevel logLevel) {	
		
		MessageFormat mFormat = new MessageFormat(message);
		String result = mFormat.format(objects);
		
		if (!StringUtils.isNotBlank(result)) {
			return;
		}
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		
		//result = shiroUser.toString() + ":" + result;
		
		LogInfo logInfo = new LogInfo();
		logInfo.setCreateTime(new Date());
		
		logInfo.setUsername(shiroUser.getLoginName());
		logInfo.setMessage(result);
		logInfo.setIpAddress(shiroUser.getIpAddress());
		logInfo.setLogLevel(logLevel);
		
		logInfoService.saveOrUpdate(logInfo);
	}

	public void setRootLogLevel(LogLevel rootLogLevel) {
		this.rootLogLevel = rootLogLevel;
	}

	/**   
	 * 
	 * @return  
	 * @see com.gdpost.web.log.LogTemplate#getRootLogLevel()  
	 */
	@Override
	public LogLevel getRootLogLevel() {
		return rootLogLevel;
	}
	
	public void setCustomLogLevel(Map<String, LogLevel> customLogLevel) {
		this.customLogLevel = customLogLevel;
	}
	
	@Override
	public Map<String, LogLevel> getCustomLogLevel() {
		return customLogLevel;
	}

	public void setLogInfoService(LogInfoService logInfoService) {
		this.logInfoService = logInfoService;
	}
	
}
