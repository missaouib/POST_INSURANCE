/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.BankCodeDAO;
import com.gdpost.web.dao.CallDealTypeDAO;
import com.gdpost.web.dao.CheckFixTypeDAO;
import com.gdpost.web.dao.ConservationErrorDAO;
import com.gdpost.web.dao.ConservationTypeDAO;
import com.gdpost.web.dao.IssueTypeDAO;
import com.gdpost.web.dao.PrdDAO;
import com.gdpost.web.dao.ProvOrgCodeDAO;
import com.gdpost.web.dao.RenewalTypeDAO;
import com.gdpost.web.entity.basedata.BankCode;
import com.gdpost.web.entity.basedata.CallDealType;
import com.gdpost.web.entity.basedata.CheckFixType;
import com.gdpost.web.entity.basedata.ConservationError;
import com.gdpost.web.entity.basedata.IssueType;
import com.gdpost.web.entity.basedata.Prd;
import com.gdpost.web.entity.basedata.RenewalType;
import com.gdpost.web.entity.main.ConservationType;
import com.gdpost.web.entity.main.ProvOrgCode;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.service.insurance.BaseDataService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class BaseDataServiceImpl implements BaseDataService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private BankCodeDAO bankCodeDAO;
	
	@Autowired
	private CallDealTypeDAO callDealTypeDAO;
	
	@Autowired
	private ConservationErrorDAO conservationErrorDAO;
	
	@Autowired
	private IssueTypeDAO issueTypeDAO;
	
	@Autowired
	private RenewalTypeDAO renewalTypeDAO;
	
	@Autowired
	private CheckFixTypeDAO checkfixTypeDAO;
	
	@Autowired
	private PrdDAO prdDAO;
	
	@Autowired
	private ConservationTypeDAO csTypeDAO;
	
	@Autowired
	private ProvOrgCodeDAO provOrgCodeDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public BankCode getBankCode(Long id) {
		return bankCodeDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.Policy)  
	 */
	@Override
	public void saveOrUpdateBankCode(BankCode bankCode) {
		if (bankCode.getId() == null) {
			if (bankCodeDAO.getByCpiCode(bankCode.getCpiCode()) != null) {
				throw new ExistedException("网点代码：" + bankCode.getCpiCode() + "已存在。");
			}
		}
		
		bankCodeDAO.save(bankCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void deleteBankCode(Long id) {
		BankCode type = bankCodeDAO.findOne(id);
		bankCodeDAO.delete(type.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<BankCode> findAllBankCode(Page page) {
		org.springframework.data.domain.Page<BankCode> springDataPage = bankCodeDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<BankCode> findByBankCodeExample(
			Specification<BankCode> specification, Page page) {
		org.springframework.data.domain.Page<BankCode> springDataPage = bankCodeDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getByPolicyNo(java.lang.String)
	 */
	@Override
	public BankCode getByCpiCode(String cpiCode) {
		return bankCodeDAO.getByCpiCode(cpiCode);
	}

	@Override
	public CallDealType getCallDealType(Long id) {
		return callDealTypeDAO.findOne(id);
	}

	@Override
	public void saveOrUpdateCallDealType(CallDealType type) {
		if (type.getId() == null) {
			if (callDealTypeDAO.getByTypeNameAndFlag(type.getTypeName(), type.getFlag()) != null) {
				throw new ExistedException("回访类型 " + type.getTypeName() + "已存在。");
			}
		}
		
		callDealTypeDAO.save(type);
	}

	@Override
	public void deleteCallDealType(Long id) {
		CallDealType type = this.getCallDealType(id);
		callDealTypeDAO.delete(type);
		
	}

	@Override
	public List<CallDealType> findAllCallDealType(Page page) {
		org.springframework.data.domain.Page<CallDealType> springDataPage = callDealTypeDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<CallDealType> findByCallDealTypeExample(Specification<CallDealType> specification, Page page) {
		org.springframework.data.domain.Page<CallDealType> springDataPage = callDealTypeDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public CallDealType getByCallDealTypeNameAndFlag(String typeName, Integer flag) {
		return callDealTypeDAO.getByTypeNameAndFlag(typeName, flag);
	}

	@Override
	public ConservationError getConservationError(Long id) {
		return conservationErrorDAO.findOne(id);
	}

	@Override
	public void saveOrUpdateConservationError(ConservationError type) {
		if (type.getId() == null) {
			if (conservationErrorDAO.getByErrorCode(type.getErrorCode()) != null) {
				throw new ExistedException("保全错误 " + type.getErrorCode() + "已存在。");
			}
		}
		
		conservationErrorDAO.save(type);
	}

	@Override
	public void deleteConservationError(Long id) {
		ConservationError error = this.getConservationError(id);
		conservationErrorDAO.delete(error);
	}

	@Override
	public List<ConservationError> findAllConservationError(Page page) {
		org.springframework.data.domain.Page<ConservationError> springDataPage = conservationErrorDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<ConservationError> findByConservationErrorExample(Specification<ConservationError> specification, Page page) {
		org.springframework.data.domain.Page<ConservationError> springDataPage = conservationErrorDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public ConservationError getByErrorCode(String errorCode) {
		return conservationErrorDAO.getByErrorCode(errorCode);
	}

	@Override
	public IssueType getIssueType(Long id) {
		return issueTypeDAO.findOne(id);
	}

	@Override
	public void saveOrUpdateIssueType(IssueType type) {
		if (type.getId() == null) {
			if (issueTypeDAO.getByTypeName(type.getTypeName()) != null) {
				throw new ExistedException("工单类型 " + type.getTypeName() + "已存在。");
			}
		}
		
		issueTypeDAO.save(type);
		
	}

	@Override
	public void deleteIssueType(Long id) {
		IssueType type = issueTypeDAO.findOne(id);
		issueTypeDAO.delete(type);
		
	}

	@Override
	public List<IssueType> findAllIssueType(Page page) {
		org.springframework.data.domain.Page<IssueType> springDataPage = issueTypeDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<IssueType> findByIssueTypeExample(Specification<IssueType> specification, Page page) {
		org.springframework.data.domain.Page<IssueType> springDataPage = issueTypeDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public IssueType getByIssueTypeName(String typeName) {
		return issueTypeDAO.getByTypeName(typeName);
	}

	@Override
	public RenewalType getRenewalType(Long id) {
		return renewalTypeDAO.findOne(id);
	}

	@Override
	public void saveOrUpdateRenewalType(RenewalType type) {
		if (type.getId() == null) {
			if (renewalTypeDAO.getByTypeName(type.getTypeName()) != null) {
				throw new ExistedException("催缴类型 " + type.getTypeName() + "已存在。");
			}
		}
		
		renewalTypeDAO.save(type);
		
	}

	@Override
	public void deleteRenewalType(Long id) {
		RenewalType type = renewalTypeDAO.findOne(id);
		renewalTypeDAO.delete(type);
		
	}

	@Override
	public List<RenewalType> findAllRenewalType(Page page) {
		org.springframework.data.domain.Page<RenewalType> springDataPage = renewalTypeDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<RenewalType> findByRenewalTypeExample(Specification<RenewalType> specification, Page page) {
		org.springframework.data.domain.Page<RenewalType> springDataPage = renewalTypeDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public RenewalType getByRenewalTypeName(String typeName) {
		return renewalTypeDAO.getByTypeName(typeName);
	}

	@Override
	public Prd getPrd(Long id) {
		return prdDAO.findOne(id);
	}

	@Override
	public void saveOrUpdatePrd(Prd type) {
		if (type.getId() == null) {
			if (prdDAO.getByPrdCode(type.getPrdCode()) != null) {
				throw new ExistedException("产品代码" + type.getPrdCode() + "已存在。");
			}
		}
		
		prdDAO.save(type);
		
	}

	@Override
	public void deletePrd(Long id) {
		Prd prd = prdDAO.findOne(id);
		prdDAO.delete(prd);
		
	}

	@Override
	public List<Prd> findAllPrd(Page page) {
		org.springframework.data.domain.Page<Prd> springDataPage = prdDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<Prd> findByPrdExample(Specification<Prd> specification, Page page) {
		org.springframework.data.domain.Page<Prd> springDataPage = prdDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public Prd getByPrdCode(String prdCode) {
		return prdDAO.getByPrdCode(prdCode);
	}
	
	/*
	 * ================
	 * CheckFixType
	 * ================
	 * 
	 */
	@Override
	public CheckFixType getCheckFixType(Long id) {
		return checkfixTypeDAO.findOne(id);
	}

	@Override
	public void saveOrUpdateCheckFixType(CheckFixType type) {
		if (type.getId() == null) {
			if (checkfixTypeDAO.getByTypeName(type.getTypeName()) != null) {
				throw new ExistedException("类型 " + type.getTypeName() + "已存在。");
			}
		}
		
		checkfixTypeDAO.save(type);
		
	}

	@Override
	public void deleteCheckFixType(Long id) {
		CheckFixType type = checkfixTypeDAO.findOne(id);
		checkfixTypeDAO.delete(type);
		
	}

	@Override
	public List<CheckFixType> findAllCheckFixType(Page page) {
		org.springframework.data.domain.Page<CheckFixType> springDataPage = checkfixTypeDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<CheckFixType> findByCheckFixTypeExample(Specification<CheckFixType> specification, Page page) {
		org.springframework.data.domain.Page<CheckFixType> springDataPage = checkfixTypeDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public CheckFixType getByCheckFixTypeName(String typeName) {
		return checkfixTypeDAO.getByTypeName(typeName);
	}
	
	/*
	 * ================
	 * ConservationType
	 * ================
	 * 
	 */
	@Override
	public ConservationType getConservationType(Long id) {
		return csTypeDAO.findOne(id);
	}

	@Override
	public void saveOrUpdateConservationType(ConservationType type) {
		if (type.getId() == null) {
			if (csTypeDAO.getByCsName(type.getCsName()) != null) {
				throw new ExistedException("类型 " + type.getCsName() + "已存在。");
			}
		}
		
		csTypeDAO.save(type);
		
	}

	@Override
	public void deleteConservationType(Long id) {
		ConservationType type = csTypeDAO.findOne(id);
		csTypeDAO.delete(type);
		
	}

	@Override
	public List<ConservationType> findAllConservationType(Page page) {
		org.springframework.data.domain.Page<ConservationType> springDataPage = csTypeDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<ConservationType> findByConservationTypeExample(Specification<ConservationType> specification, Page page) {
		org.springframework.data.domain.Page<ConservationType> springDataPage = csTypeDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public ConservationType getByConservationTypeName(String typeName) {
		return csTypeDAO.getByCsName(typeName);
	}
	
	/*
	 * ================
	 * ProvOrgCode
	 * ================
	 * 
	 */
	@Override
	public ProvOrgCode getProvOrgCode(Long id) {
		return provOrgCodeDAO.findOne(id);
	}

	@Override
	public void saveOrUpdateProvOrgCode(ProvOrgCode type) {
		if (type.getId() == null) {
			if (provOrgCodeDAO.getByOrgName(type.getOrgName()) != null) {
				throw new ExistedException("类型 " + type.getOrgName() + "已存在。");
			}
		}
		
		provOrgCodeDAO.save(type);
		
	}

	@Override
	public void deleteProvOrgCode(Long id) {
		ProvOrgCode type = provOrgCodeDAO.findOne(id);
		provOrgCodeDAO.delete(type);
		
	}

	@Override
	public List<ProvOrgCode> findAllProvOrgCode(Page page) {
		org.springframework.data.domain.Page<ProvOrgCode> springDataPage = provOrgCodeDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<ProvOrgCode> findByProvOrgCodeExample(Specification<ProvOrgCode> specification, Page page) {
		org.springframework.data.domain.Page<ProvOrgCode> springDataPage = provOrgCodeDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public ProvOrgCode getByProvOrgCodeOrgCode(String orgCode) {
		return provOrgCodeDAO.getByOrgCode(orgCode);
	}
}
