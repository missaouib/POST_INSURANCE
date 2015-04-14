/**
 * 
 */
package	com.sendtend.web.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendtend.web.dao.member.MemberMessageCategoryDAO;
import com.sendtend.web.entity.member.TblMemberMessageCategory;
import com.sendtend.web.service.member.MessageCategoryService;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.dwz.PageUtils;

@Service
@Transactional
public class MessageCategoryServiceImpl implements MessageCategoryService {
	
	//private static final Logger LOG = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@Autowired
	private MemberMessageCategoryDAO messageCategoryDAO;
	
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.MemberService#get(java.lang.Long)  
	 */ 
	@Override
	public TblMemberMessageCategory get(Long id) {
		return messageCategoryDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.sendtend.web.service.MemberService#saveOrUpdate(com.sendtend.web.entity.main.TblMemberMessageCategory)  
	 */
	@Override
	public void saveOrUpdate(TblMemberMessageCategory messageCategory) {
		
		messageCategoryDAO.save(messageCategory);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.MemberService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		TblMemberMessageCategory messageCategory = this.get(id);
		messageCategoryDAO.delete(messageCategory);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.MemberService#findAll(com.sendtend.web.util.dwz.Page)  
	 */
	@Override
	public List<TblMemberMessageCategory> findAll(Page page) {
		org.springframework.data.domain.Page<TblMemberMessageCategory> springDataPage = messageCategoryDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<TblMemberMessageCategory> findAllWithCache() {
		List<TblMemberMessageCategory> list = messageCategoryDAO.findAllWithCache();
		return list;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.MemberService#findByExample(org.springframework.data.jpa.domain.Specification, com.sendtend.web.util.dwz.Page)	
	 */
	@Override
	public List<TblMemberMessageCategory> findByExample(
			Specification<TblMemberMessageCategory> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberMessageCategory> springDataPage = messageCategoryDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
