/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.GsettleDAO;
import com.gdpost.web.dao.GsettleDtlDAO;
import com.gdpost.web.dao.GsettleLogDAO;
import com.gdpost.web.dao.SettleTaskDAO;
import com.gdpost.web.dao.SettleTaskLogDAO;
import com.gdpost.web.dao.SettlementDAO;
import com.gdpost.web.dao.SettlementDtlDAO;
import com.gdpost.web.dao.SettlementLogDAO;
import com.gdpost.web.entity.component.Gsettle;
import com.gdpost.web.entity.component.GsettleDtl;
import com.gdpost.web.entity.component.GsettleLog;
import com.gdpost.web.entity.component.SettleTask;
import com.gdpost.web.entity.component.SettleTaskLog;
import com.gdpost.web.entity.component.Settlement;
import com.gdpost.web.entity.component.SettlementDtl;
import com.gdpost.web.entity.component.SettlementLog;
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
	
	@Autowired
	private SettlementLogDAO settlementLogDAO;
	
	@Autowired
	private SettleTaskDAO settleTaskDAO;
	
	@Autowired
	private SettleTaskLogDAO settleTaskLogDAO;
	
	@Autowired
	private GsettleDAO gsettleDAO;
	
	@Autowired
	private GsettleDtlDAO gsettleDtlDAO;
	
	@Autowired
	private GsettleLogDAO gsettleLogDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public Settlement getSettle(Long id) {
		return settlementDAO.findById(id).get();
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.Settlement)  
	 */
	@Override
	public void saveOrUpdateSettle(Settlement settle) {
		if (settle.getId() == null) {
			if(settle.getCaseType() != null && !settle.getCaseType().equals("医疗")) {
				if (settlementDAO.getByInsuredAndCaseDate(settle.getInsured(), settle.getCaseDate()) != null) {
					throw new ExistedException("出险人：" + settle.getInsured() + "已记录。");
				}
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
		settlementDAO.deleteById(id);
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
	public Settlement getSettleByPolicyNo(String policyNo) {
		return settlementDAO.getByPolicyPolicyNo(policyNo);
	}
	
	@Override
	public SettlementDtl getSettleDtl(Long id) {
		return settlementDtlDAO.findById(id).get();
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.SettlementDtl)  
	 */
	@Override
	public void saveOrUpdateSettleDtl(SettlementDtl SettlemenDtl) {
		settlementDtlDAO.saveAndFlush(SettlemenDtl);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void deleteSettleDtl(Long id) {
		settlementDtlDAO.deleteById(id);
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
	
	@Override
	public SettlementDtl getDtlByPolicyPolicyNo(String policyNo) {
		return settlementDtlDAO.getByPolicyNo(policyNo);
	}

	@Override
	public SettlementLog getSettleLog(Long id) {
		return settlementLogDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateSettleLog(SettlementLog log) {
		settlementLogDAO.save(log);
		
	}

	@Override
	public List<SettlementLog> findLogBySettleId(Long id) {
		return settlementLogDAO.findBySettlementId(id);
	}
	
	@Override
	public List<SettlementLog> findDealLogBySettleId(Long id) {
		return settlementLogDAO.findBySettlementIdAndIsFollowOrderByIdDesc(id,true);
	}
	
	/*
	 * settle task
	 */
	
	@Override
	public SettleTask getSettleTask(Long id) {
		return settleTaskDAO.findById(id).get();
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.SettleTask)  
	 */
	@Override
	public void saveOrUpdateSettleTask(SettleTask settle) {
		
		settleTaskDAO.save(settle);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void deleteSettleTask(Long id) {
		settleTaskDAO.deleteById(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<SettleTask> findAllSettleTask(Page page) {
		org.springframework.data.domain.Page<SettleTask> springDataPage = settleTaskDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<SettleTask> findBySettleTaskExample(
			Specification<SettleTask> specification, Page page) {
		org.springframework.data.domain.Page<SettleTask> springDataPage = settleTaskDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public void saveOrUpdateSettleTaskLog(SettleTaskLog log) {
		settleTaskLogDAO.save(log);
		
	}
	
	@Override
	public List<SettleTaskLog> findLogBySettleTaskId(Long id) {
		return settleTaskLogDAO.findBySettleTaskId(id);
	}
	
	@Override
	public List<SettleTaskLog> findDealLogByTaskId(Long id) {
		return settleTaskLogDAO.findBySettleTaskIdAndIsFollowOrderByIdDesc(id,true);
	}
	
	/*
	 * ===============
	 * group settle
	 * ===============
	 */
	@Override
	public Gsettle getGsettle(Long id) {
		return gsettleDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateGsettle(Gsettle settle) {
		if (settle.getId() == null) {
			if(settle.getCaseType() != null && !settle.getCaseType().equals("医疗")) {
				if (gsettleDAO.getByInsuredAndCaseDate(settle.getInsured(), settle.getCaseDate()) != null) {
					throw new ExistedException("出险人：" + settle.getInsured() + "已记录。");
				}
			}
		}
		
		gsettleDAO.save(settle);
	}

	@Override
	public void deleteGsettle(Long id) {
		gsettleDAO.deleteById(id);
	}
	
	@Override
	public List<Gsettle> findAllGsettle(Page page) {
		org.springframework.data.domain.Page<Gsettle> springDataPage = gsettleDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<Gsettle> findByGsettleExample(
			Specification<Gsettle> specification, Page page) {
		org.springframework.data.domain.Page<Gsettle> springDataPage = gsettleDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public Gsettle getGsettleByPolicyNo(String policyNo) {
		return gsettleDAO.getByPolicyPolicyNo(policyNo);
	}
	
	@Override
	public GsettleDtl getGsettleDtl(Long id) {
		return gsettleDtlDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateGsettleDtl(GsettleDtl gsettleDtl) {
		gsettleDtlDAO.saveAndFlush(gsettleDtl);
	}

	@Override
	public void deleteGsettleDtl(Long id) {
		gsettleDtlDAO.deleteById(id);
	}
	
	@Override
	public List<GsettleDtl> findAllGsettleDtl(Page page) {
		org.springframework.data.domain.Page<GsettleDtl> springDataPage = gsettleDtlDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<GsettleDtl> findByGsettleDtlExample(
			Specification<GsettleDtl> specification, Page page) {
		org.springframework.data.domain.Page<GsettleDtl> springDataPage = gsettleDtlDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public GsettleDtl getDtlByGsettleId(Long id) {
		return gsettleDtlDAO.getByGsettleId(id);
	}
	
	@Override
	public GsettleDtl getGsettleDtlByPolicyPolicyNo(String policyNo) {
		return gsettleDtlDAO.getByPolicyNo(policyNo);
	}

	@Override
	public GsettleLog getGsettleLog(Long id) {
		return gsettleLogDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateGsettleLog(GsettleLog log) {
		gsettleLogDAO.save(log);
		
	}

	@Override
	public List<GsettleLog> findLogByGsettleId(Long id) {
		return gsettleLogDAO.findByGsettleId(id);
	}
	
	@Override
	public List<GsettleLog> findDealLogByGsettleId(Long id) {
		return gsettleLogDAO.findByGsettleIdAndIsFollowOrderByIdDesc(id,true);
	}
}
