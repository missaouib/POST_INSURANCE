/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.RenewalTypeDAO;
import com.gdpost.web.dao.RenewedListDAO;
import com.gdpost.web.entity.basedata.RenewalType;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.entity.main.RenewedList;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.service.insurance.XqglService;
import com.gdpost.web.util.StatusDefine.XQ_STATUS;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Service
@Transactional
public class XqglServiceImpl implements XqglService {
	//private static final Logger LOG = LoggerFactory.getLogger(XqglServiceImpl.class);
	
	@Autowired
	private RenewedListDAO renewedListDAO;
	
	@Autowired
	private RenewalTypeDAO rnwDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public RenewedList get(Long id) {
		return renewedListDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.RenewedList)  
	 */
	@Override
	public void saveOrUpdate(RenewedList renewed) {
		renewedListDAO.save(renewed);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		RenewedList user = renewedListDAO.findOne(id);
		renewedListDAO.delete(user.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<RenewedList> findAll(Page page) {
		org.springframework.data.domain.Page<RenewedList> springDataPage = renewedListDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<RenewedList> findByExample(
			Specification<RenewedList> specification, Page page) {
		org.springframework.data.domain.Page<RenewedList> springDataPage = renewedListDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<RenewedList> getTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<RenewedList> specification = null;
		
		if (user.getOrganization().getOrgCode().length() > 4) {
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(RenewedList.class,
					new SearchFilter("feeStatus", Operator.OR_LIKE, XQ_STATUS.NewStatus.getDesc()),
					new SearchFilter("feeStatus", Operator.OR_LIKE, XQ_STATUS.FeeFailStatus.getDesc()),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		} else {
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(RenewedList.class,
					new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		}
		Page page = new Page();
		page.setNumPerPage(100);
		page.setOrderField("policy.policyDate");
		page.setOrderDirection("ASC");
		
		List<RenewedList> issues = this.findByExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<RenewedList>();
		}
		
		return issues;
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getByPolicyNo(java.lang.String)
	 */
	@Override
	public RenewedList getByPolicyNoAndPrdName(Policy policy, String prdName) {
		return renewedListDAO.getByPolicyAndPrdName(policy, prdName);
	}

	@Override
	public List<RenewalType> getRenewedDealTypeList(int flag) {
		return rnwDAO.getByFlag(flag);
	}

	@Override
	public List<RenewalType> getAllRenewedDealTypeList() {
		return rnwDAO.findAll();
	}
}
