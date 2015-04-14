/**
 * 
 */
package	com.sendtend.web.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendtend.web.dao.LogInfoDAO;
import com.sendtend.web.entity.main.LogInfo;
import com.sendtend.web.service.member.MLogInfoService;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.dwz.PageUtils;

@Service
@Transactional
public class MLogInfoServiceImpl implements MLogInfoService {
	
	@Autowired
	private LogInfoDAO logInfoDAO;

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.LogInfoService#get(java.lang.Long)  
	 */ 
	@Override
	public LogInfo get(Long id) {
		return logInfoDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.sendtend.web.service.LogInfoService#saveOrUpdate(com.sendtend.web.entity.main.LogInfo)  
	 */
	@Override
	public void saveOrUpdate(LogInfo logInfo) {
		logInfoDAO.save(logInfo);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.LogInfoService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		logInfoDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.LogInfoService#findAll(com.sendtend.web.util.dwz.Page)  
	 */
	@Override
	public List<LogInfo> findAll(Page page) {
		org.springframework.data.domain.Page<LogInfo> springDataPage = logInfoDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.LogInfoService#findByExample(org.springframework.data.jpa.domain.Specification, com.sendtend.web.util.dwz.Page)	
	 */
	@Override
	public List<LogInfo> findByExample(
			Specification<LogInfo> specification, Page page) {
		org.springframework.data.domain.Page<LogInfo> springDataPage = logInfoDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
