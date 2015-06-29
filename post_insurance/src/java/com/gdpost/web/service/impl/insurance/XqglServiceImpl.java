/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.RenewedListDAO;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.entity.main.RenewedList;
import com.gdpost.web.service.insurance.XqglService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class XqglServiceImpl implements XqglService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private RenewedListDAO renewedListDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public RenewedList get(Long id) {
		return renewedListDAO.findOne(id);
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
		RenewedList user = renewedListDAO.findOne(id);
		renewedListDAO.delete(user.getId());
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
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getByPolicyNo(java.lang.String)
	 */
	@Override
	public RenewedList getByPolicyNoAndPrdName(Policy policy, String prdName) {
		return renewedListDAO.getByPolicyAndPrdName(policy, prdName);
	}
}
