/**
 * 
 */
package	com.sendtend.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendtend.web.dao.DataControlDAO;
import com.sendtend.web.entity.main.DataControl;
import com.sendtend.web.service.DataControlService;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.dwz.PageUtils;

@Service
@Transactional
public class DataControlServiceImpl implements DataControlService {
	
	@Autowired
	private DataControlDAO dataControlDAO;

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.DataControlService#get(java.lang.Long)  
	 */ 
	@Override
	public DataControl get(Long id) {
		return dataControlDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.sendtend.web.service.DataControlService#saveOrUpdate(com.sendtend.web.entity.main.DataControl)  
	 */
	@Override
	public void saveOrUpdate(DataControl dataControl) {
		dataControlDAO.save(dataControl);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.DataControlService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		dataControlDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.DataControlService#findAll(com.sendtend.web.util.dwz.Page)  
	 */
	@Override
	public List<DataControl> findAll(Page page) {
		org.springframework.data.domain.Page<DataControl> springDataPage = dataControlDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.DataControlService#findByExample(org.springframework.data.jpa.domain.Specification, com.sendtend.web.util.dwz.Page)	
	 */
	@Override
	public List<DataControl> findByExample(
			Specification<DataControl> specification, Page page) {
		org.springframework.data.domain.Page<DataControl> springDataPage = dataControlDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
