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

import com.gdpost.web.dao.CallDealTypeDAO;
import com.gdpost.web.dao.CallFailListDAO;
import com.gdpost.web.dao.component.IVCallFailListDAO;
import com.gdpost.web.entity.basedata.CallDealType;
import com.gdpost.web.entity.component.VCallFailList;
import com.gdpost.web.entity.main.CallFailList;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.Policy;
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
	private IVCallFailListDAO vCFLDAO;
	
	@Autowired
	private CallDealTypeDAO cdtDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public CallFailList get(Long id) {
		return callFailListDAO.findOne(id);
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
		CallFailList user = callFailListDAO.findOne(id);
		callFailListDAO.delete(user.getId());
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
	public List<VCallFailList> find11185List(Specification<VCallFailList> specification, Page page) {
		org.springframework.data.domain.Page<VCallFailList> springDataPage = vCFLDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<VCallFailList> findByExample(
			Specification<VCallFailList> specification, Page page) {
		org.springframework.data.domain.Page<VCallFailList> springDataPage = vCFLDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Deprecated
	@Override
	public List<VCallFailList> get11185TODOList() {
		Page page = new Page();
		page.setNumPerPage(100);
		page.setOrderField("policy.policyDate");
		page.setOrderDirection("DESC");
		Specification<VCallFailList> spec = DynamicSpecifications.bySearchFilterWithoutRequest(VCallFailList.class,
					new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.NewStatus.getDesc()),
					new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.CallFailStatus.getDesc()),
					new SearchFilter("lastDateNum", Operator.GTE, 3));
		List<VCallFailList> issues = this.find11185List(spec, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<VCallFailList>();
		}
		
		return issues;
	}
	
	@Override
	public List<CallFailList> batchMail(List<CallFailList> list) {
		return callFailListDAO.save(list);
	}
	
	@Override
	public List<VCallFailList> getTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<VCallFailList> specification = null;
		
		//如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if(user.getOrganization().getOrgCode().contains("11185")) {
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(VCallFailList.class,
					new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.NewStatus.getDesc()),
					new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.ResetStatus.getDesc()),
					new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.CallFailStatus.getDesc()),
					new SearchFilter("policy.flag", Operator.EQ, "0"),
					new SearchFilter("lastDateNum", Operator.GTE, 3));
		} else if (userOrg.getOrgCode().length() > 4) {
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(VCallFailList.class,
					new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.NewStatus.getDesc()),
					new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.ResetStatus.getDesc()),
					new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.CallFailStatus.getDesc()),
					new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.NeedDoorStatus.getDesc()),
					new SearchFilter("policy.flag", Operator.EQ, "0"),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		} else if (userOrg.getOrgCode().length() <= 4) { //如果是省分的，看已回复的。
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(VCallFailList.class,
					new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.NewStatus.getDesc()),
					new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.ResetStatus.getDesc()),
					new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.NeedDoorStatus.getDesc()),
					//new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.DoorSuccessStatus.getDesc()),
					//new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.DoorFailStatus.getDesc()),
					//new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.CallSuccessStatus.getDesc()),
					new SearchFilter("status", Operator.OR_LIKE, HF_STATUS.CallFailStatus.getDesc()),
					new SearchFilter("policy.flag", Operator.EQ, "0"),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
		}
		Page page = new Page();
		page.setNumPerPage(100);
		page.setOrderField("policy.policyDate");
		page.setOrderDirection("DESC");

		//LOG.debug("------------ ready to search:");
		List<VCallFailList> issues = this.findByExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<VCallFailList>();
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
	public List<CallDealType> getCallDealTypeList(Integer flag) {
		return cdtDAO.getByFlag(flag);
	}
}
