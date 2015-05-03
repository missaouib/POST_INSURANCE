/**
 * 
 */
package	com.gdpost.web.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.member.CategoryOrgDAO;
import com.gdpost.web.entity.member.TblCategoryOrg;
import com.gdpost.web.service.member.CategoryOrgService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class CategoryOrgServiceImpl implements CategoryOrgService {
	
	//private static final Logger LOG = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@Autowired(required=false)
	private CategoryOrgDAO categoryOrgDAO;
	
	@Override
	public TblCategoryOrg get(Long id) {
		return categoryOrgDAO.findOne(id);
	}

	@Override
	public void saveOrUpdate(TblCategoryOrg messageCategory) {
		
		categoryOrgDAO.save(messageCategory);
	}

	@Override
	public void delete(Long id) {
		TblCategoryOrg messageCategory = this.get(id);
		categoryOrgDAO.delete(messageCategory);
	}
	
	@Override
	public TblCategoryOrg findByCategoryId(Long categoryId) {
		return categoryOrgDAO.findByTblMemberMessageCategoryId(categoryId);
	}
	
	@Override
	public List<TblCategoryOrg> findAll(Page page) {
		org.springframework.data.domain.Page<TblCategoryOrg> springDataPage = categoryOrgDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<TblCategoryOrg> findAllWithCache() {
		List<TblCategoryOrg> list = categoryOrgDAO.findAllWithCache();
		return list;
	}
	
	@Override
	public List<TblCategoryOrg> findByExample(
			Specification<TblCategoryOrg> specification, Page page) {
		org.springframework.data.domain.Page<TblCategoryOrg> springDataPage = categoryOrgDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
