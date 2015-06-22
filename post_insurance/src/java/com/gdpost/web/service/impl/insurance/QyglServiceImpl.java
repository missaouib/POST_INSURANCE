/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.CheckRecordDAO;
import com.gdpost.web.dao.CheckWriteDAO;
import com.gdpost.web.entity.main.CheckRecord;
import com.gdpost.web.entity.main.CheckWrite;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.service.insurance.QyglService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class QyglServiceImpl implements QyglService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private CheckWriteDAO checkWriteDAO;
	
	@Autowired
	private CheckRecordDAO checkRecordDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public CheckWrite getCheckWrite(Long id) {
		return checkWriteDAO.findOne(id);
	}
	
	@Override
	public CheckRecord getCheckRecord(Long id) {
		return checkRecordDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.Policy)  
	 */
	@Override
	public void saveOrUpdateCheckWrite(CheckWrite check) {
		if (check.getId() == null) {
			if (checkWriteDAO.getByPolicyPolicyNo(check.getPolicy().getPolicyNo()) != null) {
				throw new ExistedException("保单号：" + check.getPolicy().getPolicyNo() + "已存在。");
			}
		}
		
		checkWriteDAO.save(check);
	}
	
	@Override
	public void saveOrUpdateCheckRecord(CheckRecord check) {
		if (check.getId() == null) {
			if (checkRecordDAO.getByPolicyPolicyNo(check.getPolicy().getPolicyNo()) != null) {
				throw new ExistedException("保单号：" + check.getPolicy().getPolicyNo() + "已存在。");
			}
		}
		
		checkRecordDAO.save(check);
	}

	@Override
	public List<CheckWrite> findAllCheckWrite(Page page) {
		org.springframework.data.domain.Page<CheckWrite> springDataPage = checkWriteDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<CheckRecord> findAllCheckRecord(Page page) {
		org.springframework.data.domain.Page<CheckRecord> springDataPage = checkRecordDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<CheckWrite> findByCheckWriteExample(
			Specification<CheckWrite> specification, Page page) {
		org.springframework.data.domain.Page<CheckWrite> springDataPage = checkWriteDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<CheckRecord> findByCheckRecordExample(
			Specification<CheckRecord> specification, Page page) {
		org.springframework.data.domain.Page<CheckRecord> springDataPage = checkRecordDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getByPolicyNo(java.lang.String)
	 */
	@Override
	public CheckWrite getByCheckWritePolicyNo(String policyNo) {
		return checkWriteDAO.getByPolicyPolicyNo(policyNo);
	}
	
	@Override
	public CheckRecord getByCheckRecordPolicyNo(String policyNo) {
		return checkRecordDAO.getByPolicyPolicyNo(policyNo);
	}
}
