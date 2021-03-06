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
import com.gdpost.web.dao.RenewedFollowDAO;
import com.gdpost.web.dao.RenewedListDAO;
import com.gdpost.web.dao.RenewedStayDAO;
import com.gdpost.web.entity.basedata.RenewalType;
import com.gdpost.web.entity.insurance.Policy;
import com.gdpost.web.entity.insurance.RenewedFollow;
import com.gdpost.web.entity.insurance.RenewedList;
import com.gdpost.web.entity.insurance.RenewedStay;
import com.gdpost.web.entity.main.Organization;
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
	private RenewedFollowDAO renewedFollowDAO;
	
	@Autowired
	private RenewalTypeDAO rnwDAO;
	
	@Autowired
	private RenewedStayDAO stayDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public RenewedList get(Long id) {
		return renewedListDAO.findById(id).get();
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
		renewedListDAO.deleteById(id);
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
		//???????????????????????????
		Specification<RenewedList> specification = DynamicSpecifications.bySearchFilterWithoutRequest(RenewedList.class,
				new SearchFilter("feeStatus", Operator.OR_EQ, XQ_STATUS.NewStatus.getDesc()),
				new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		Page page = new Page();
		page.setNumPerPage(5);
		page.setOrderField("policy.policyDate");
		page.setOrderDirection("Desc");
		
		List<RenewedList> issues = null;
		try {
			issues = this.findByExample(specification, page);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
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
	public List<RenewalType> getRenewedDealTypeList(int typeFlag) {
		return rnwDAO.getByTypeFlag(typeFlag);
	}

	@Override
	public List<RenewalType> getAllRenewedDealTypeList() {
		return rnwDAO.findAll();
	}
	
	@Override
	public List<String> getProvAcitivity() {
		List<String> rst = renewedListDAO.getProvActivitys();
		if(rst == null || rst.isEmpty() || rst.get(0) == null) {
			return new ArrayList<String>();
		}
		return rst;
	}
	
	@Override
	public List<String> getFeeMatch() {
		List<String> rst = renewedListDAO.getFeeMatchs();
		if(rst == null || rst.isEmpty() || rst.get(0) == null) {
			return new ArrayList<String>();
		}
		return rst;
	}
	
	/*
	 * ======================================================
	 * Renewed Stay
	 * ======================================================
	 */
	
	@Override
	public RenewedStay getRenewedStay(Long id) {
		return stayDAO.findById(id).get();
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.RenewedStay)  
	 */
	@Override
	public void saveOrUpdateRenewedStay(RenewedStay oc) {
		
		stayDAO.save(oc);
	}

	@Override
	public int getRenewedStayNum(String policyNo) {
		List<RenewedStay> list = stayDAO.getByPolicyPolicyNoOrderByIdDesc(policyNo);
		if(list == null || list.isEmpty()) {
			return 1;
		}
		return list.size() + 1;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void deleteRenewedStay(Long id) {
		stayDAO.deleteById(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<RenewedStay> findAllRenewedStay(Page page) {
		org.springframework.data.domain.Page<RenewedStay> springDataPage = stayDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<RenewedStay> findByRenewedStayExample(
			Specification<RenewedStay> specification, Page page) {
		org.springframework.data.domain.Page<RenewedStay> springDataPage = stayDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getByRenewedStayNo(java.lang.String)
	 */
	@Override
	public RenewedStay getRenewedStayByPolicyNo(String policyNo) {
		List<RenewedStay> list = stayDAO.getByPolicyPolicyNoOrderByIdDesc(policyNo);
		if(list == null || list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	/**
	 * follow
	 */
	@Override
	public RenewedFollow getRenewedFollow(Long id) {
		return renewedFollowDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateRenewedFollow(RenewedFollow rf) {
		renewedFollowDAO.save(rf);
	}

	@Override
	public List<RenewedFollow> findAllRenewedFollow(Page page) {
		org.springframework.data.domain.Page<RenewedFollow> springDataPage = renewedFollowDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<RenewedFollow> findRenewedFollowByExample(Specification<RenewedFollow> specification, Page page) {
		org.springframework.data.domain.Page<RenewedFollow> springDataPage = renewedFollowDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
}
