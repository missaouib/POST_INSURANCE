/**
 * 
 * ==========================================================
 * 保全管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.main.Notice;
import com.gdpost.web.entity.main.NoticeAtt;
import com.gdpost.web.entity.main.Role;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.util.dwz.Page;

public interface NoticeService {
	
	Notice getNotice(Long id);

	Notice saveOrUpdateNotice(Notice type);

	void deleteNotice(Long id);
	
	List<Notice> findAllNotice(Page page);
	
	List<Notice> findByNoticeExample(Specification<Notice> specification, Page page);
	
	NoticeAtt getNoticeAtt(Long id);

	void saveOrUpdateNoticeAtt(NoticeAtt type);

	void deleteNoticeAtt(Long id);
	
	List<NoticeAtt> findAllNoticeAtt(Page page);
	
	List<NoticeAtt> findByNoticeAttExample(Specification<NoticeAtt> specification, Page page);

	List<Notice> findByOwnNoticeList(Page page, String orgCode, List<Role> roles,
			User user, User sender, Date... d1);
	
}
