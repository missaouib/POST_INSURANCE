/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.InvoiceReqDAO;
import com.gdpost.web.entity.main.InvoiceReq;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.service.insurance.FpglService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class FpglServiceImpl implements FpglService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private InvoiceReqDAO invoiceReqDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public InvoiceReq get(Long id) {
		return invoiceReqDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.InvoiceReq)  
	 */
	@Override
	public void saveOrUpdate(InvoiceReq policy) {
		
		invoiceReqDAO.save(policy);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		InvoiceReq user = invoiceReqDAO.findOne(id);
		invoiceReqDAO.delete(user.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<InvoiceReq> findAll(Page page) {
		org.springframework.data.domain.Page<InvoiceReq> springDataPage = invoiceReqDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<InvoiceReq> findByExample(
			Specification<InvoiceReq> specification, Page page) {
		org.springframework.data.domain.Page<InvoiceReq> springDataPage = invoiceReqDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public InvoiceReq getByPolicyAndFeeDate(Policy policy, Date feeDate) {
		return invoiceReqDAO.getByPolicyPolicyNoAndFeeDate(policy.getPolicyNo(), feeDate);
	}
	
}
