/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.log.LogAdapter.java
 * Class:			LogTemplateAdapter
 * Date:			2013-5-3
 * Author:			sendtend
 * Version          2.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.gdpost.web.log.impl;

import java.util.HashMap;
import java.util.Map;

import com.gdpost.web.log.LogAPI;
import com.gdpost.web.log.LogLevel;

/** 
 * 	
 * @author 	sendtend
 * Version  2.1.0
 * @since   2013-5-3 下午5:21:07 
 */

public class LogAdapter implements LogAPI {

	/**   
	 * @param message
	 * @param logLevel  
	 * @see com.gdpost.web.log.LogAPI#log(java.lang.String, com.gdpost.web.log.LogLevel)  
	 */
	@Override
	public void log(String message, LogLevel logLevel) {
		log(message, null, logLevel);
	}

	/**   
	 * @param message
	 * @param objects
	 * @param logLevel  
	 * @see com.gdpost.web.log.LogAPI#log(java.lang.String, java.lang.Object[], com.gdpost.web.log.LogLevel)  
	 */
	@Override
	public void log(String message, Object[] objects, LogLevel logLevel) {
		
	}
	
	/**   
	 * @return  
	 * @see com.gdpost.web.log.LogAPI#getRootLogLevel()  
	 */
	@Override
	public LogLevel getRootLogLevel() {
		return LogLevel.ERROR;
	}

	/**   
	 * @return  
	 * @see com.gdpost.web.log.LogAPI#getCustomLogLevel()  
	 */
	@Override
	public Map<String, LogLevel> getCustomLogLevel() {
		return new HashMap<String, LogLevel>();
	}
}
