/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.ConservationDtlDAO;
import com.gdpost.web.entity.main.ConservationDtl;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.service.insurance.BqglService;
import com.gdpost.web.util.StatusDefine.BQ_STATUS;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Service
@Transactional
public class BqglServiceImpl implements BqglService {
	private static final Logger LOG = LoggerFactory.getLogger(BqglServiceImpl.class);
	
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
	
	@Override
	public List<ConservationDtl> getTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<ConservationDtl> specification = DynamicSpecifications.bySearchFilterWithoutRequest(ConservationDtl.class,
				new SearchFilter("status", Operator.LIKE, BQ_STATUS.NewStatus.name()),
				new SearchFilter("organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		
		//如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if (userOrg.getOrgCode().length() > 6) {
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(ConservationDtl.class,
					new SearchFilter("status", Operator.LIKE, BQ_STATUS.NewStatus.name()),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		} else if (userOrg.getOrgCode().length() <= 4) { //如果是省分的，看已回复的。
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(ConservationDtl.class,
					new SearchFilter("status", Operator.LIKE, BQ_STATUS.DealStatus.name()),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		}
		Page page = new Page();
		page.setNumPerPage(100);
		LOG.debug("------------ ready to search:");
		List<ConservationDtl> issues = this.findByExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<ConservationDtl>();
		}
		
		return issues;
	}
}
