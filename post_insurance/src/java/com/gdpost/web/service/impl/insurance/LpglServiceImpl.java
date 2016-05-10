/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.SettlementDAO;
import com.gdpost.web.dao.SettlementDtlDAO;
import com.gdpost.web.entity.component.Settlement;
import com.gdpost.web.entity.component.SettlementDtl;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.service.insurance.LpglService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class LpglServiceImpl implements LpglService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private SettlementDAO settlementDAO;
	
	@Autowired
	private SettlementDtlDAO settlementDtlDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public Settlement getSettle(Long id) {
		return settlementDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.Settlement)  
	 */
	@Override
	public void saveOrUpdateSettle(Settlement settle) {
		if (settle.getId() == null) {
			if (settlementDAO.getByInsuredAndCaseDate(settle.getInsured(), settle.getCaseDate()) != null) {
				throw new ExistedException("出险人：" + settle.getInsured() + "已记录。");
			}
		}
		
		settlementDAO.save(settle);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void deleteSettle(Long id) {
		Settlement user = settlementDAO.findOne(id);
		settlementDAO.delete(user.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<Settlement> findAllSettle(Page page) {
		org.springframework.data.domain.Page<Settlement> springDataPage = settlementDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<Settlement> findBySettleExample(
			Specification<Settlement> specification, Page page) {
		org.springframework.data.domain.Page<Settlement> springDataPage = settlementDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getBySettlementNo(java.lang.String)
	 */
	@Override
	public Settlement getSettleBySettlementNo(String policyNo) {
		return settlementDAO.getByPolicyPolicyNo(policyNo);
	}
	
	@Override
	public SettlementDtl getSettleDtl(Long id) {
		return settlementDtlDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.SettlementDtl)  
	 */
	@Override
	public void saveOrUpdateSettleDtl(SettlementDtl SettlemenDtl) {
		settlementDtlDAO.save(SettlemenDtl);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void deleteSettleDtl(Long id) {
		SettlementDtl dtl = settlementDtlDAO.findOne(id);
		settlementDtlDAO.delete(dtl.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<SettlementDtl> findAllSettleDtl(Page page) {
		org.springframework.data.domain.Page<SettlementDtl> springDataPage = settlementDtlDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<SettlementDtl> findBySettleDtlExample(
			Specification<SettlementDtl> specification, Page page) {
		org.springframework.data.domain.Page<SettlementDtl> springDataPage = settlementDtlDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getBySettlementDtlNo(java.lang.String)
	 */
	@Override
	public SettlementDtl getDtlBySettlementId(Long id) {
		return settlementDtlDAO.getBySettlementId(id);
	}
}
