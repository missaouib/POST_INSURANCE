/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.ConservationErrorDAO;
import com.gdpost.web.dao.PolicyDAO;
import com.gdpost.web.dao.PrdDAO;
import com.gdpost.web.entity.basedata.ConservationError;
import com.gdpost.web.entity.basedata.Prd;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.service.insurance.CommonService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class CommonServiceImpl implements CommonService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private PolicyDAO policyDAO;
	
	@Autowired
	private ConservationErrorDAO conservationErrorDAO;
	
	@Autowired
	private PrdDAO prdDAO;
	
	@Override
	public Policy getPolicyById(Long id) {
		return policyDAO.findOne(id);
	}
	
	@Override
	public Policy getByPolicyNo(String policyNo) {
		return policyDAO.getByPolicyNo(policyNo);
	}

	@Override
	public List<Policy> findByPolicyExample(
			Specification<Policy> specification, Page page) {
		org.springframework.data.domain.Page<Policy> springDataPage = policyDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<ConservationError> findByConservationErrorExample(
			Specification<ConservationError> specification, Page page) {
		org.springframework.data.domain.Page<ConservationError> springDataPage = conservationErrorDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<Prd> findByPrdExample(Specification<Prd> specification, Page page) {
		org.springframework.data.domain.Page<Prd> springDataPage = prdDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
}
