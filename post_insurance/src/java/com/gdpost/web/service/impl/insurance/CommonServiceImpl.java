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
import com.gdpost.web.entity.insurance.Policy;
import com.gdpost.web.service.insurance.CommonService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class CommonServiceImpl implements CommonService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private PolicyDAO policyDAO;
	
	@Override
	public Policy getPolicyById(Long id) {
		return policyDAO.findById(id).get();
	}
	
	@Override
	public Policy getByPolicyNo(String policyNo) {
		return policyDAO.getByPolicyNoAndAttachedFlag(policyNo, 0);
	}

	@Override
	public List<Policy> findByPolicyExample(
			Specification<Policy> specification, Page page) {
		org.springframework.data.domain.Page<Policy> springDataPage = policyDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
}
