/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.PolicyDAO;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.service.insurance.KfglService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class KfglServiceImpl implements KfglService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private PolicyDAO policyDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public Policy get(Long id) {
		return policyDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.Policy)  
	 */
	@Override
	public void saveOrUpdate(Policy policy) {
		if (policy.getId() == null) {
			if (policyDAO.getByPolicyNo(policy.getPolicyNo()) != null) {
				throw new ExistedException("保单号：" + policy.getPolicyNo() + "已存在。");
			}
		}
		
		policyDAO.save(policy);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		Policy user = policyDAO.findOne(id);
		policyDAO.delete(user.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<Policy> findAll(Page page) {
		org.springframework.data.domain.Page<Policy> springDataPage = policyDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<Policy> findByExample(
			Specification<Policy> specification, Page page) {
		org.springframework.data.domain.Page<Policy> springDataPage = policyDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getByPolicyNo(java.lang.String)
	 */
	@Override
	public Policy getByPolicyNo(String policyNo) {
		return policyDAO.getByPolicyNo(policyNo);
	}
}
