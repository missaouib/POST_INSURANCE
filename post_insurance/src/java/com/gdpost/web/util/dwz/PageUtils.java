/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.util.dwz.SpringDataJpaPageConvert.java
 * Class:			SpringDataJpaPageConvert
 * Date:			2012-8-6
 * Author:			Aming
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.gdpost.web.util.dwz;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * 解决dwz page 的遗留问题，使程序更易移植和替换
 * 
 * @author Aming Version 1.1.0
 * @since 2012-8-6 下午10:03:18
 */

public class PageUtils {

	/**
	 * 生成spring data JPA 对象 描述
	 * 
	 * @param page
	 * @return
	 */
	public static Pageable createPageable(Page page) {
		
		if(page.getOrders() != null && !page.getOrders().isEmpty()) {
			return PageRequest.of(page.getPlainPageNum() - 1, page.getNumPerPage(), Sort.by(page.getOrders()));
		}
		
		if (StringUtils.isNotBlank(page.getOrderField())) {
			// 忽略大小写
			if (page.getOrderDirection().equalsIgnoreCase(Page.ORDER_DIRECTION_ASC)) {
				return PageRequest.of(page.getPlainPageNum() - 1, page.getNumPerPage(), 
						Sort.Direction.ASC, page.getOrderField());
			} else {
				return PageRequest.of(page.getPlainPageNum() - 1, page.getNumPerPage(), 
						Sort.Direction.DESC, page.getOrderField());
			}
		}

		return PageRequest.of(page.getPlainPageNum() - 1, page.getNumPerPage());
	}

	/**
	 * 将springDataPage的属性注入page描述
	 * 
	 * @param page
	 * @param springDataPage
	 */
	@Deprecated
	public static void injectPageProperties(Page page,
			org.springframework.data.domain.Page<?> springDataPage) {
		// 暂时只注入总记录数量
		page.setTotalCount(springDataPage.getTotalElements());
	}
}
