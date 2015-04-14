/**
 * <pre>
 * Date:			2013年10月17日
 * Author:			sendtend
 * Description:
 * </pre>
 **/
 
package com.sendtend.web.entity;

import java.io.Serializable;

/** 
 * 	
 * @author 	sendtend
 * @since   2013年10月17日 下午4:13:13 
 */

public interface Idable<T extends Serializable> {
	public T getId();

	public void setId(T id);
}
