/**
 * 
 * ==========================================================
 * 保全管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.component.Notice;
import com.gdpost.web.util.dwz.Page;

public interface NoticeService {
	
	Notice getNotice(Long id);

	void saveOrUpdateNotice(Notice type);

	void deleteNotice(Long id);
	
	List<Notice> findAllNotice(Page page);
	
	List<Notice> findByNoticeExample(Specification<Notice> specification, Page page);
	
}
