/**
 * <pre>
 * Date:			2013年10月17日
 * Author:			Aming
 * Description:
 * </pre>
 **/
 
package com.gdpost.web.entity;

import java.io.Serializable;

/** 
 * 	
 * @author 	Aming
 * @since   2013年10月17日 下午4:13:13 
 */

public interface Idable<T extends Serializable> {
	public T getId();

	public void setId(T id);
}
