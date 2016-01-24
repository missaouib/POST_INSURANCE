/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.NoticeAttDAO;
import com.gdpost.web.dao.NoticeDAO;
import com.gdpost.web.entity.component.Notice;
import com.gdpost.web.entity.component.NoticeAtt;
import com.gdpost.web.entity.main.Role;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.service.insurance.NoticeService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	@Autowired
	private NoticeAttDAO noticeAttDAO;
		
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public Notice getNotice(Long id) {
		return noticeDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.Policy)  
	 */
	@Override
	public Notice saveOrUpdateNotice(Notice bankCode) {
		
		return noticeDAO.save(bankCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void deleteNotice(Long id) {
		Notice type = noticeDAO.findOne(id);
		noticeDAO.delete(type.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<Notice> findAllNotice(Page page) {
		org.springframework.data.domain.Page<Notice> springDataPage = noticeDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<Notice> findByNoticeExample(
			Specification<Notice> specification, Page page) {
		org.springframework.data.domain.Page<Notice> springDataPage = noticeDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<Notice> findByOwnNoticeList(Page page, String orgCode, List<Role> roles, User user, User senderId, Date... d) {
		if(d != null && d.length >= 2) {
			org.springframework.data.domain.Page<Notice> springDataPage = noticeDAO.findBetweenList(orgCode, roles, user, user, d[0], d[1], PageUtils.createPageable(page));
			page.setTotalCount(springDataPage.getTotalElements());
			return springDataPage.getContent();
		}
		org.springframework.data.domain.Page<Notice> springDataPage = noticeDAO.findValidList(orgCode, roles, user, user, new Date(), PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	@Override
	public List<Notice> findByDateNoticeList(String orgCode, List<Role> roles, User user, User senderId, Date d1, Date d2, Page page) {
		org.springframework.data.domain.Page<Notice> springDataPage = noticeDAO.findBetweenList(orgCode, roles, user, user, d1, d2, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	*/
	@Override
	public NoticeAtt getNoticeAtt(Long id) {
		return noticeAttDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.Policy)  
	 */
	@Override
	public void saveOrUpdateNoticeAtt(NoticeAtt noticeAtt) {
		
		noticeAttDAO.save(noticeAtt);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void deleteNoticeAtt(Long id) {
		NoticeAtt type = noticeAttDAO.findOne(id);
		noticeAttDAO.delete(type.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<NoticeAtt> findAllNoticeAtt(Page page) {
		org.springframework.data.domain.Page<NoticeAtt> springDataPage = noticeAttDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<NoticeAtt> findByNoticeAttExample(
			Specification<NoticeAtt> specification, Page page) {
		org.springframework.data.domain.Page<NoticeAtt> springDataPage = noticeAttDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
}
