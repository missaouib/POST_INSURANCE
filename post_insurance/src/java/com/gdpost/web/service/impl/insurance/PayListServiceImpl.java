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

import com.gdpost.web.dao.PayListDAO;
import com.gdpost.web.entity.insurance.PayList;
import com.gdpost.web.entity.main.Organization;
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
	private PayListDAO payListDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public PayList get(Long id) {
		return payListDAO.findById(id).get();
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.PayFailList)  
	 */
	@Override
	public void saveOrUpdate(PayList policy) {
		
		payListDAO.save(policy);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		payListDAO.deleteById(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<PayList> findFailAll(Page page) {
//		org.springframework.data.domain.Page<PayList> springDataPage = payListDAO.findAll(PageUtils.createPageable(page));
//		page.setTotalCount(springDataPage.getTotalElements());
//		return springDataPage.getContent();
		Specification<PayList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayList.class,
				new SearchFilter("failDesc", Operator.NOT_LIKE, "成功"));
		
		List<PayList> issues = this.findFailByExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayList>();
		}
		
		return issues;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<PayList> findFailByExample(Specification<PayList> specification, Page page) {
		org.springframework.data.domain.Page<PayList> springDataPage = payListDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
//		Specification<PayList> s2 = DynamicSpecifications.bySearchFilterWithoutRequest(PayList.class,
//				new SearchFilter("failDesc", Operator.NOT_LIKE, "成功"));
//		if(specification != null) {
//			s2.and(specification);
//		}
//		
//		List<PayList> issues = this.findFailByExample(s2, page);
//		if (issues == null || issues.isEmpty()) {
//			issues = new ArrayList<PayList>();
//		}
//		
//		return issues;
	}
	
	@Override
	public List<PayList> getBQToFailListTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<PayList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayList.class,
				new SearchFilter("payType", Operator.EQ, PayList.PAY_TO),
				new SearchFilter("feeType", Operator.EQ, "保全受理号"),
				new SearchFilter("failDesc", Operator.NOT_LIKE, "成功"),
				new SearchFilter("status", Operator.EQ, BQ_STATUS.NewStatus.name()),
				new SearchFilter("organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		
		Page page = new Page();
		page.setNumPerPage(5);
		List<PayList> issues = this.findFailByExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayList>();
		}
		
		return issues;
	}
	
	@Override
	public List<PayList> getBQFromFailListTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<PayList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayList.class,
				new SearchFilter("payType", Operator.EQ, PayList.PAY_FROM),
				new SearchFilter("feeType", Operator.EQ, "保全受理号"),
				new SearchFilter("failDesc", Operator.NOT_LIKE, "成功"),
				new SearchFilter("status", Operator.EQ, BQ_STATUS.NewStatus.name()),
				new SearchFilter("organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		
		Page page = new Page();
		page.setNumPerPage(5);
		List<PayList> issues = this.findFailByExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayList>();
		}
		
		return issues;
	}
	
	@Override
	public List<PayList> getXQFromFailListTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<PayList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayList.class,
				new SearchFilter("payType", Operator.EQ, PayList.PAY_FROM),
				new SearchFilter("feeType", Operator.EQ, "保单合同号"),
				new SearchFilter("failDesc", Operator.NOT_LIKE, "成功"),
				new SearchFilter("status", Operator.EQ, BQ_STATUS.NewStatus.name()),
				new SearchFilter("organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		
		Page page = new Page();
		page.setNumPerPage(5);
		List<PayList> issues = this.findFailByExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayList>();
		}
		
		return issues;
	}
	
	@Override
	public List<PayList> getQYFromFailListTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<PayList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayList.class,
				new SearchFilter("payType", Operator.EQ, PayList.PAY_FROM),
				new SearchFilter("feeType", Operator.EQ, "投保单印刷号"),
				new SearchFilter("failDesc", Operator.NOT_LIKE, "成功"),
				new SearchFilter("status", Operator.EQ, BQ_STATUS.NewStatus.name()),
				new SearchFilter("organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		
		Page page = new Page();
		page.setNumPerPage(5);
		List<PayList> issues = this.findFailByExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayList>();
		}
		
		return issues;
	}
	
	@Override
	public List<PayList> getLPToFailListTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<PayList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayList.class,
				new SearchFilter("payType", Operator.EQ, PayList.PAY_TO),
				new SearchFilter("feeType", Operator.EQ, "案件号"),
				new SearchFilter("failDesc", Operator.NOT_LIKE, "成功"),
				new SearchFilter("status", Operator.EQ, BQ_STATUS.NewStatus.name()),
				new SearchFilter("relNo", Operator.OR_LIKE_R, userOrg.getOrgCode()),
				new SearchFilter("organization.orgCode", Operator.OR_LIKE_R, userOrg.getOrgCode()));
		
		Page page = new Page();
		page.setNumPerPage(5);
		List<PayList> issues = this.findFailByExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayList>();
		}
		
		return issues;
	}
	
	/*
	 * ==============================================================
	 * Pay Success list
	 * ==============================================================
	 *
	 */
	@Override
	public PayList getSuccessDtl(Long id) {
		return payListDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateSuccessDtl(PayList policy) {
		
		payListDAO.save(policy);
	}

	@Override
	public void deleteSuccessDtl(Long id) {
		payListDAO.deleteById(id);
	}
	
	@Override
	public List<PayList> findAllSuccessList(Page page) {
		org.springframework.data.domain.Page<PayList> springDataPage = payListDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<PayList> findBySuccessDtlExample(
			Specification<PayList> specification, Page page) {
		org.springframework.data.domain.Page<PayList> springDataPage = payListDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<PayList> getBQToSuccessListTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<PayList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayList.class,
				new SearchFilter("payType", Operator.EQ, PayList.PAY_TO),
				new SearchFilter("feeType", Operator.EQ, "保全受理号"),
				new SearchFilter("failDesc", Operator.EQ, "成功"),
				new SearchFilter("status", Operator.EQ, BQ_STATUS.NewStatus.name()),
				new SearchFilter("organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		
		Page page = new Page();
		page.setNumPerPage(5);
		List<PayList> issues = this.findBySuccessDtlExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayList>();
		}
		
		return issues;
	}
	
	@Override
	public List<PayList> getBQFromSuccessListTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<PayList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayList.class,
				new SearchFilter("payType", Operator.EQ, PayList.PAY_FROM),
				new SearchFilter("feeType", Operator.EQ, "保全受理号"),
				new SearchFilter("failDesc", Operator.EQ, "成功"),
				new SearchFilter("status", Operator.EQ, BQ_STATUS.NewStatus.name()),
				new SearchFilter("organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		
		Page page = new Page();
		page.setNumPerPage(5);
		List<PayList> issues = this.findBySuccessDtlExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayList>();
		}
		
		return issues;
	}
	
	@Override
	public List<PayList> getXQFromSuccessListTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<PayList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayList.class,
				new SearchFilter("payType", Operator.EQ, PayList.PAY_FROM),
				new SearchFilter("feeType", Operator.EQ, "保单合同号"),
				new SearchFilter("failDesc", Operator.EQ, "成功"),
				new SearchFilter("status", Operator.EQ, BQ_STATUS.NewStatus.name()),
				new SearchFilter("organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		
		Page page = new Page();
		page.setNumPerPage(5);
		List<PayList> issues = this.findBySuccessDtlExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayList>();
		}
		
		return issues;
	}
	
	@Override
	public List<PayList> getQYFromSuccessListTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<PayList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayList.class,
				new SearchFilter("payType", Operator.EQ, PayList.PAY_FROM),
				new SearchFilter("feeType", Operator.EQ, "投保单印刷号"),
				new SearchFilter("failDesc", Operator.EQ, "成功"),
				new SearchFilter("status", Operator.EQ, BQ_STATUS.NewStatus.name()),
				new SearchFilter("organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		
		Page page = new Page();
		page.setNumPerPage(5);
		List<PayList> issues = this.findBySuccessDtlExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayList>();
		}
		
		return issues;
	}
	
	@Override
	public List<PayList> getLPToSuccessListTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<PayList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(PayList.class,
				new SearchFilter("payType", Operator.EQ, PayList.PAY_TO),
				new SearchFilter("feeType", Operator.EQ, "案件号"),
				new SearchFilter("failDesc", Operator.EQ, "成功"),
				new SearchFilter("status", Operator.EQ, BQ_STATUS.NewStatus.name()),
				new SearchFilter("relNo", Operator.OR_LIKE_R, userOrg.getOrgCode()),
				new SearchFilter("organization.orgCode", Operator.OR_LIKE_R, userOrg.getOrgCode()));
		
		Page page = new Page();
		page.setNumPerPage(5);
		List<PayList> issues = this.findBySuccessDtlExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<PayList>();
		}
		
		return issues;
	}
}
