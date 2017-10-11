/**
 * 
 */
package	com.gdpost.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.LogInfoDAO;
import com.gdpost.web.entity.main.LogInfo;
import com.gdpost.web.service.LogInfoService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class LogInfoServiceImpl implements LogInfoService {
	
	@Autowired
	private LogInfoDAO logInfoDAO;

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.LogInfoService#get(java.lang.Long)  
	 */ 
	@Override
	public LogInfo get(Long id) {
		return logInfoDAO.getOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.LogInfoService#saveOrUpdate(com.gdpost.web.entity.main.LogInfo)  
	 */
	@Override
	public void saveOrUpdate(LogInfo logInfo) {
		logInfoDAO.save(logInfo);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.LogInfoService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		logInfoDAO.deleteById(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.LogInfoService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<LogInfo> findAll(Page page) {
		org.springframework.data.domain.Page<LogInfo> springDataPage = logInfoDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.LogInfoService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<LogInfo> findByExample(
			Specification<LogInfo> specification, Page page) {
		org.springframework.data.domain.Page<LogInfo> springDataPage = logInfoDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
