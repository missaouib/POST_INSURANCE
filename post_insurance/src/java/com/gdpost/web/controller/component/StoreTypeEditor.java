/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.controller.component.StoreTypeEditor.java
 * Class:			EnumEditor
 * Date:			2013-7-4
 * Author:			Aming
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.gdpost.web.controller.component;

import java.beans.PropertyEditorSupport;

import com.gdpost.web.entity.component.StoreType;

/** 
 * 	
 * @author 	Aming
 * Version  3.1.0
 * @since   2013-7-4 上午9:09:44 
 */

public class StoreTypeEditor extends PropertyEditorSupport {

	/**   
	 * @param text
	 * @throws IllegalArgumentException  
	 * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)  
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		for (StoreType storeType : StoreType.values()) {
			// 忽略大小写
			if (storeType.value().equalsIgnoreCase(text)) {
				this.setValue(storeType);
				return;
			}
		}
		
		throw new IllegalArgumentException("没有找到" + text + "对应的枚举值。");
	}

}
