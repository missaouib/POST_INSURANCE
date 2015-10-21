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

import com.gdpost.web.dao.PayFailListDAO;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.PayFailList;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.service.insurance.PayListService;
import com.gdpost.web.util.StatusDefine.BQ_STATUS;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Service
@Transactional
public class PayListServiceImpl implements PayListService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private PayFailListDAO payFailListqDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public PayFailList get(Long id) {
		return payFailListqDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.PayFailList)  
	 */
	@Override
	public void saveOrUpdate(PayFailList policy) {
		
		payFailListqDAO.save(policy);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		PayFailList user = payFailListqDAO.findOne(id);
		payFailListqDAO.delete(user.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<PayFailList> findAll(Page page) {
		org.springframework.data.domain.Page<PayFailList> springDataPage = payFailListqDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<PayFailList> findByExample(
			Specification<PayFailList> specification, Page page) {
		org.springframework.data.domain.Page<PayFailList> springDataPage = payFailListqDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<PayFailList> getBQToFailListTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<PayFailList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayFailList.class,
				new SearchFilter("payType", Operator.EQ, PayFailList.PAY_TO),
				new SearchFilter("feeType", Operator.EQ, "保全受理号"),
				new SearchFilter("status", Operator.LIKE, BQ_STATUS.NewStatus.name()),
				new SearchFilter("organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		
		Page page = new Page();
		page.setNumPerPage(100);
		List<PayFailList> issues = this.findByExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayFailList>();
		}
		
		return issues;
	}
	
	@Override
	public List<PayFailList> getBQFromFailListTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<PayFailList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayFailList.class,
				new SearchFilter("payType", Operator.EQ, PayFailList.PAY_FROM),
				new SearchFilter("feeType", Operator.EQ, "保全受理号"),
				new SearchFilter("status", Operator.LIKE, BQ_STATUS.NewStatus.name()),
				new SearchFilter("organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		
		Page page = new Page();
		page.setNumPerPage(100);
		List<PayFailList> issues = this.findByExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayFailList>();
		}
		
		return issues;
	}
	
	@Override
	public List<PayFailList> getXQFromFailListTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<PayFailList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayFailList.class,
				new SearchFilter("payType", Operator.EQ, PayFailList.PAY_FROM),
				new SearchFilter("feeType", Operator.EQ, "保单合同号"),
				new SearchFilter("status", Operator.LIKE, BQ_STATUS.NewStatus.name()),
				new SearchFilter("organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		
		Page page = new Page();
		page.setNumPerPage(100);
		List<PayFailList> issues = this.findByExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayFailList>();
		}
		
		return issues;
	}
	
	@Override
	public List<PayFailList> getQYFromFailListTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<PayFailList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayFailList.class,
				new SearchFilter("payType", Operator.EQ, PayFailList.PAY_FROM),
				new SearchFilter("feeType", Operator.EQ, "投保单印刷号"),
				new SearchFilter("status", Operator.LIKE, BQ_STATUS.NewStatus.name()),
				new SearchFilter("organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		
		Page page = new Page();
		page.setNumPerPage(100);
		List<PayFailList> issues = this.findByExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayFailList>();
		}
		
		return issues;
	}
	
	@Override
	public List<PayFailList> getLPToFailListTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<PayFailList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayFailList.class,
				new SearchFilter("payType", Operator.EQ, PayFailList.PAY_TO),
				new SearchFilter("feeType", Operator.EQ, "案件号"),
				new SearchFilter("status", Operator.LIKE, BQ_STATUS.NewStatus.name()),
				new SearchFilter("relNo", Operator.OR_LIKE, userOrg.getOrgCode()),
				new SearchFilter("organization.orgCode", Operator.OR_LIKE, userOrg.getOrgCode()));
		
		Page page = new Page();
		page.setNumPerPage(100);
		List<PayFailList> issues = this.findByExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayFailList>();
		}
		
		return issues;
	}
}
