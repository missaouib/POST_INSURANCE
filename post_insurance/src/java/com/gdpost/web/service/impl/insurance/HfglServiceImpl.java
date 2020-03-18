/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.CallDealTypeDAO;
import com.gdpost.web.dao.CallFailListDAO;
import com.gdpost.web.entity.basedata.CallDealType;
import com.gdpost.web.entity.insurance.CallFailList;
import com.gdpost.web.entity.insurance.Policy;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.service.insurance.HfglService;
import com.gdpost.web.util.StatusDefine.HF_STATUS;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Service
@Transactional
public class HfglServiceImpl implements HfglService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private CallFailListDAO callFailListDAO;
	
	@Autowired
	private CallDealTypeDAO cdtDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public CallFailList get(Long id) {
		return callFailListDAO.findById(id).get();
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.CallFailList)  
	 */
	@Override
	public void saveOrUpdate(CallFailList policy) {
		callFailListDAO.save(policy);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		callFailListDAO.deleteById(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<CallFailList> findAll(Page page) {
		org.springframework.data.domain.Page<CallFailList> springDataPage = callFailListDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	
	@Override
	public List<CallFailList> findByExample(
			Specification<CallFailList> specification, Page page) {
		org.springframework.data.domain.Page<CallFailList> springDataPage = callFailListDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	
	@Override
	public List<CallFailList> batchMail(List<CallFailList> list) {
		return callFailListDAO.saveAll(list);
	}
	
	@Override
	public List<CallFailList> getTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<CallFailList> specification = null;
		
		//如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if(user.getOrganization().getOrgCode().contains("11185")) {
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(CallFailList.class,
					new SearchFilter("status", Operator.OR_EQ, HF_STATUS.NewStatus.getDesc()),
					new SearchFilter("status", Operator.OR_EQ, HF_STATUS.ResetStatus.getDesc()),
					new SearchFilter("status", Operator.OR_EQ, HF_STATUS.CallFailStatus.getDesc()),
					new SearchFilter("policy.attachedFlag", Operator.EQ, "0"),
					new SearchFilter("lastDateNum", Operator.GTE, 3));
		} else if (userOrg.getOrgCode().length() >= 4) {
			
			Collection<SearchFilter> csf = new HashSet<SearchFilter>();
			//csf.add(new SearchFilter("policy.attachedFlag", Operator.EQ, "0"));
			csf.add(new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
			csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.NewStatus.getDesc()));
			csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.ResetStatus.getDesc()));
			csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.CallFailStatus.getDesc()));
			csf.add(new SearchFilter("status", Operator.OR_EQ, HF_STATUS.NeedDoorStatus.getDesc()));
			specification = DynamicSpecifications.bySearchFilter(CallFailList.class, csf);
		}
		Page page = new Page();
		page.setNumPerPage(5);
		page.setOrderField("policy.policyDate");
		page.setOrderDirection("DESC");

		//LOG.debug("------------ ready to search:");
		List<CallFailList> issues = new ArrayList<CallFailList>();
		try {
			issues = this.findByExample(specification, page);
			if (issues == null || issues.isEmpty()) {
				issues = new ArrayList<CallFailList>();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return issues;
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getByPolicyNo(java.lang.String)
	 */
	@Override
	public CallFailList getByPolicy(Policy policy) {
		return callFailListDAO.getByPolicy(policy);
	}

	@Override
	public List<CallDealType> getCallDealTypeList(Integer typeFlag) {
		return cdtDAO.getByTypeFlag(typeFlag);
	}
}
