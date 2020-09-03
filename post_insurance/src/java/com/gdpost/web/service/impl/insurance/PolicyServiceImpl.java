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
import com.gdpost.web.dao.model.PolicyDataDAO;
import com.gdpost.web.entity.component.PolicyDataModel;
import com.gdpost.web.entity.insurance.Policy;
import com.gdpost.web.entity.insurance.PolicyReprintDtl;
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
	
	@Autowired
	private PolicyDataDAO pdDAO;
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public Policy get(Long id) {
		return policyDAO.findById(id).get();
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
		return policyDAO.getByPolicyNoAndAttachedFlag(policyNo, 0);
	}
	
	@Override
	public Policy getByHolderIdCardNum(String idCardNum) {
		return policyDAO.getFirstByPolicyDtlHolderCardNum(idCardNum);
	}
	
	@Override
	public List<Policy> getByInsuredIdCardNum(String idCardNum) {
		return policyDAO.findByPolicyDtlInsuredCardNum(idCardNum);
	}

	/*
	 * ===================================================
	 * policy reprint dtl
	 * ===================================================
	 */	
	@Override
	public PolicyReprintDtl getPolicyReprintDtl(Long id) {
		return policyReprintDtlDAO.findById(id).get();
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

	@Override
	public List<PolicyDataModel> getPolicyDate(String organCode, String pd1, String pd2, String prodName) {
		return pdDAO.getPolicyDate(organCode, pd1, pd2, prodName);
	}

	@Override
	public boolean isBankPolicy(String policyNo) {
		Policy policy = policyDAO.isBankPolicy(policyNo);
		if(policy != null && policy.getId() != null) {
			return true;
		}
		return false;
	}
	
}
