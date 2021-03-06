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
import com.gdpost.web.dao.SalesDAO;
import com.gdpost.web.dao.component.CsAddrDAO;
import com.gdpost.web.dao.component.StaffDAO;
import com.gdpost.web.entity.basedata.BankCode;
import com.gdpost.web.entity.basedata.CallDealType;
import com.gdpost.web.entity.basedata.CheckFixType;
import com.gdpost.web.entity.basedata.ConservationError;
import com.gdpost.web.entity.basedata.IssueType;
import com.gdpost.web.entity.basedata.Prd;
import com.gdpost.web.entity.basedata.RenewalType;
import com.gdpost.web.entity.basedata.Sales;
import com.gdpost.web.entity.insurance.ConservationType;
import com.gdpost.web.entity.insurance.CsAddr;
import com.gdpost.web.entity.insurance.ProvOrgCode;
import com.gdpost.web.entity.insurance.Staff;
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
	
	@Autowired
	private StaffDAO staffDAO;
	
	@Autowired
	private CsAddrDAO csAddrDAO;
	
	@Autowired
	private SalesDAO salesDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public BankCode getBankCode(Long id) {
		return bankCodeDAO.findById(id).get();
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.Policy)  
	 */
	@Override
	public void saveOrUpdateBankCode(BankCode bankCode) {
		if (bankCode.getId() == null) {
			if (bankCodeDAO.getByCpiCode(bankCode.getCpiCode()) != null) {
				throw new ExistedException("???????????????" + bankCode.getCpiCode() + "????????????");
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
		BankCode type = bankCodeDAO.findById(id).get();
		bankCodeDAO.deleteById(type.getId());
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
		return callDealTypeDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateCallDealType(CallDealType type) {
//		if (type.getId() == null) {
//			if (callDealTypeDAO.getByTypeNameAndTypeFlag(type.getTypeName(), type.getTypeFlag()) != null) {
//				throw new ExistedException("???????????? " + type.getTypeName() + "????????????");
//			}
//		}
		
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
	public CallDealType getByCallDealTypeNameAndFlag(String typeName, Integer typeFlag) {
		return callDealTypeDAO.getByTypeNameAndTypeFlag(typeName, typeFlag);
	}

	@Override
	public ConservationError getConservationError(Long id) {
		return conservationErrorDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateConservationError(ConservationError type) {
		if (type.getId() == null) {
			if (conservationErrorDAO.getByErrorCode(type.getErrorCode()) != null) {
				throw new ExistedException("???????????? " + type.getErrorCode() + "????????????");
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
		return issueTypeDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateIssueType(IssueType type) {
		if (type.getId() == null) {
			if (issueTypeDAO.getByTypeName(type.getTypeName()) != null) {
				throw new ExistedException("???????????? " + type.getTypeName() + "????????????");
			}
		}
		
		issueTypeDAO.save(type);
		
	}

	@Override
	public void deleteIssueType(Long id) {
		IssueType type = issueTypeDAO.findById(id).get();
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
		return renewalTypeDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateRenewalType(RenewalType type) {
		if (type.getId() == null) {
			if (renewalTypeDAO.getByTypeName(type.getTypeName()) != null) {
				throw new ExistedException("???????????? " + type.getTypeName() + "????????????");
			}
		}
		
		renewalTypeDAO.save(type);
		
	}

	@Override
	public void deleteRenewalType(Long id) {
		RenewalType type = renewalTypeDAO.findById(id).get();
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

	/**
	 * =======================================
	 * Prd
	 * =======================================
	 */
	@Override
	public Prd getPrd(Long id) {
		return prdDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdatePrd(Prd type) {
		if (type.getId() == null) {
			if (prdDAO.getByPrdCode(type.getPrdCode()) != null) {
				throw new ExistedException("????????????" + type.getPrdCode() + "????????????");
			}
		}
		
		prdDAO.save(type);
		
	}

	@Override
	public void deletePrd(Long id) {
		Prd prd = prdDAO.findById(id).get();
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
		return checkfixTypeDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateCheckFixType(CheckFixType type) {
		if (type.getId() == null) {
			if (checkfixTypeDAO.getByTypeName(type.getTypeName()) != null) {
				throw new ExistedException("?????? " + type.getTypeName() + "????????????");
			}
		}
		
		checkfixTypeDAO.save(type);
		
	}

	@Override
	public void deleteCheckFixType(Long id) {
		CheckFixType type = checkfixTypeDAO.findById(id).get();
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
		return csTypeDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateConservationType(ConservationType type) {
		if (type.getId() == null) {
			if (csTypeDAO.getByCsName(type.getCsName()) != null) {
				throw new ExistedException("?????? " + type.getCsName() + "????????????");
			}
		}
		
		csTypeDAO.save(type);
		
	}

	@Override
	public void deleteConservationType(Long id) {
		ConservationType type = csTypeDAO.findById(id).get();
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
		return provOrgCodeDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateProvOrgCode(ProvOrgCode type) {
		if (type.getId() == null) {
			if (provOrgCodeDAO.getByOrgName(type.getOrgName()) != null) {
				throw new ExistedException("?????? " + type.getOrgName() + "????????????");
			}
		}
		
		provOrgCodeDAO.save(type);
		
	}

	@Override
	public void deleteProvOrgCode(Long id) {
		ProvOrgCode type = provOrgCodeDAO.findById(id).get();
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
	
	/*
	 * ========================
	 *  Staff
	 *  =======================
	 */
	@Override
	public Staff getStaff(Long id) {
		return staffDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateStaff(Staff staff) {
		if (staff.getId() == null) {
			if (staffDAO.getByIdCard(staff.getIdCard()) != null) {
				throw new ExistedException("????????????" + staff.getIdCard() + "????????????");
			}
		}
		
		staffDAO.save(staff);
		
	}

	@Override
	public void deleteStaff(Long id) {
		Staff staff = staffDAO.findById(id).get();
		staffDAO.delete(staff);
		
	}

	@Override
	public List<Staff> findAllStaff(Page page) {
		org.springframework.data.domain.Page<Staff> springDataPage = staffDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<Staff> findByStaffExample(Specification<Staff> specification, Page page) {
		org.springframework.data.domain.Page<Staff> springDataPage = staffDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public Staff getByStaffIdCard(String idCard) {
		return staffDAO.getByIdCard(idCard);
	}
	
	/*
	 * ========================
	 *  CsAddr
	 *  =======================
	 */
	@Override
	public CsAddr getCsAddr(Long id) {
		return csAddrDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateCsAddr(CsAddr csAddr) {
		if (csAddr.getId() == null) {
			if (csAddrDAO.getByCity(csAddr.getCity()) != null) {
				throw new ExistedException("????????????" + csAddr.getCity() + "????????????");
			}
		}
		
		csAddrDAO.save(csAddr);
		
	}

	@Override
	public void deleteCsAddr(Long id) {
		CsAddr csAddr = csAddrDAO.findById(id).get();
		csAddrDAO.delete(csAddr);
		
	}

	@Override
	public List<CsAddr> findAllCsAddr(Page page) {
		org.springframework.data.domain.Page<CsAddr> springDataPage = csAddrDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<CsAddr> findByCsAddrExample(Specification<CsAddr> specification, Page page) {
		org.springframework.data.domain.Page<CsAddr> springDataPage = csAddrDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public CsAddr getByCsAddrCity(String city) {
		return csAddrDAO.getByCity(city);
	}
	
	/**
	 * =======================================
	 * Sales
	 * =======================================
	 */
	@Override
	public Sales getSales(Long id) {
		return salesDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateSales(Sales type) {
		if (type.getId() == null) {
			if (salesDAO.getBySalesName(type.getSalesName()) != null) {
				throw new ExistedException("????????????" + type.getSalesName() + "????????????");
			}
		}
		
		salesDAO.save(type);
		
	}

	@Override
	public void deleteSales(Long id) {
		Sales sales = salesDAO.findById(id).get();
		salesDAO.delete(sales);
		
	}

	@Override
	public List<Sales> findAllSales(Page page) {
		org.springframework.data.domain.Page<Sales> springDataPage = salesDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<Sales> findBySalesExample(Specification<Sales> specification, Page page) {
		org.springframework.data.domain.Page<Sales> springDataPage = salesDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<Sales> getByName(String name) {
		return salesDAO.getBySalesName(name);
	}

	@Override
	public List<Sales> getByPhone(String phone) {
		return salesDAO.getByPhone(phone);
	}

	
}
