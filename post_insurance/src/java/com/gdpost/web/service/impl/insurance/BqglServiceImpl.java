/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.ConservationDtlDAO;
import com.gdpost.web.entity.main.ConservationDtl;
import com.gdpost.web.service.insurance.BqglService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class BqglServiceImpl implements BqglService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private ConservationDtlDAO conservationDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public ConservationDtl get(Long id) {
		return conservationDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.ConservationDtl)  
	 */
	@Override
	public void saveOrUpdate(ConservationDtl policy) {
		conservationDAO.save(policy);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		ConservationDtl user = conservationDAO.findOne(id);
		conservationDAO.delete(user.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<ConservationDtl> findAll(Page page) {
		org.springframework.data.domain.Page<ConservationDtl> springDataPage = conservationDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<ConservationDtl> findByExample(
			Specification<ConservationDtl> specification, Page page) {
		org.springframework.data.domain.Page<ConservationDtl> springDataPage = conservationDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getByConservationDtlNo(java.lang.String)
	 */
	@Override
	public ConservationDtl getByPolicyNo(String policyNo) {
		return conservationDAO.getByPolicyPolicyNo(policyNo);
	}
}
