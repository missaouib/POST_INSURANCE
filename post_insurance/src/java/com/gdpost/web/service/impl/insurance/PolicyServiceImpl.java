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
import com.gdpost.web.dao.PolicyReprintDtlDAO;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.entity.main.PolicyReprintDtl;
import com.gdpost.web.service.insurance.PolicyService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class PolicyServiceImpl implements PolicyService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private PolicyDAO policyDAO;
	
	@Autowired
	private PolicyReprintDtlDAO policyReprintDtlDAO;
	
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
		
		policyDAO.save(policy);
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

	@Override
	public Policy getByPolicyNo(String policyNo) {
		return policyDAO.getByPolicyNo(policyNo);
	}
	
	@Override
	public Policy getByHolderIdCardNum(String idCardNum) {
		return policyDAO.getByPolicyDtlHolderCardNum(idCardNum);
	}

	/*
	 * ===================================================
	 * policy reprint dtl
	 * ===================================================
	 */	
	@Override
	public PolicyReprintDtl getPolicyReprintDtl(Long id) {
		return policyReprintDtlDAO.findOne(id);
	}

	@Override
	public void saveOrUpdatePolicyReprintDtl(PolicyReprintDtl policyReprintDtl) {
		
		policyReprintDtlDAO.save(policyReprintDtl);
	}

	
	@Override
	public List<PolicyReprintDtl> findAllPolicyReprintDtl(Page page) {
		org.springframework.data.domain.Page<PolicyReprintDtl> springDataPage = policyReprintDtlDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<PolicyReprintDtl> findByPolicyReprintDtlExample(
			Specification<PolicyReprintDtl> specification, Page page) {
		org.springframework.data.domain.Page<PolicyReprintDtl> springDataPage = policyReprintDtlDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
}
