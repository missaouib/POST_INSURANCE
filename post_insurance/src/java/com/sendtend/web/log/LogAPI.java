/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.sendtend.web.log.LogAPI.java
 * Class:			LogInterface
 * Date:			2013-5-5
 * Author:			sendtend
 * Version          2.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.sendtend.web.log;

import java.util.Map;

/** 
 * 	
 * @author 	sendtend
 * Version  2.1.0
 * @since   2013-5-5 下午12:01:45 
 */

public interface LogAPI {
	void log(String message, LogLevel logLevel);
	
	void log(String message, Object[] objects, LogLevel logLevel);
	
	/**
	 * 
	 * 得到全局日志等级
	 * @return
	 */
	LogLevel getRootLogLevel();
	
	/**
	 * 
	 * 得到自定义包的日志等级
	 * @return
	 */
	Map<String, LogLevel> getCustomLogLevel();
}
