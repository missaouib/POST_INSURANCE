/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.IssueDAO;
import com.gdpost.web.entity.main.Issue;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.service.insurance.KfglService;
import com.gdpost.web.util.StatusDefine.STATUS;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Service
@Transactional
public class KfglServiceImpl implements KfglService {
	//private static final Logger LOG = LoggerFactory.getLogger(KfglServiceImpl.class);
	
	@Autowired
	private IssueDAO issueDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public Issue get(Long id) {
		return issueDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.Issue)  
	 */
	@Override
	public void saveOrUpdate(Issue policy) {
		if (policy.getId() == null) {
			if (issueDAO.getByIssueNo(policy.getIssueNo()) != null) {
				throw new ExistedException("保单号：" + policy.getIssueNo() + "已存在。");
			}
		}
		
		issueDAO.save(policy);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		Issue user = issueDAO.findOne(id);
		issueDAO.delete(user.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<Issue> findAll(Page page) {
		org.springframework.data.domain.Page<Issue> springDataPage = issueDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<Issue> findByExample(
			Specification<Issue> specification, Page page) {
		org.springframework.data.domain.Page<Issue> springDataPage = issueDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getByIssueNo(java.lang.String)
	 */
	@Override
	public Issue getByIssueNo(String issueNo) {
		return issueDAO.getByIssueNo(issueNo);
	}

	@Override
	public List<Issue> getTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		Page page = new Page();
		page.setNumPerPage(100);
		//默认返回未处理工单
		Specification<Issue> specification = null;
		
		//如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if (user.getOrganization().getOrgCode().length() > 4) {
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(Issue.class,
					new SearchFilter("status", Operator.OR_LIKE, STATUS.NewStatus.getDesc()),
					new SearchFilter("status", Operator.OR_LIKE, STATUS.ReopenStatus.getDesc()),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		} else {
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(Issue.class,
					new SearchFilter("status", Operator.LIKE, STATUS.DealStatus.getDesc()),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		}
		
		List<Issue> issues = this.findByExample(specification, page);
		
		return issues;
	}
}
