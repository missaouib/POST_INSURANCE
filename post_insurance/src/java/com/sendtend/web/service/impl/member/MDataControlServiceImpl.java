/**
 * 
 */
package	com.sendtend.web.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendtend.web.dao.member.MDataControlDAO;
import com.sendtend.web.entity.member.TblMemberDataControl;
import com.sendtend.web.service.member.MDataControlService;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.dwz.PageUtils;

@Service
@Transactional
public class MDataControlServiceImpl implements MDataControlService {
	
	@Autowired
	private MDataControlDAO dataControlDAO;

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.DataControlService#get(java.lang.Long)  
	 */ 
	@Override
	public TblMemberDataControl get(Long id) {
		return dataControlDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.sendtend.web.service.DataControlService#saveOrUpdate(com.sendtend.web.entity.main.DataControl)  
	 */
	@Override
	public void saveOrUpdate(TblMemberDataControl dataControl) {
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
	public List<TblMemberDataControl> findAll(Page page) {
		org.springframework.data.domain.Page<TblMemberDataControl> springDataPage = dataControlDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.DataControlService#findByExample(org.springframework.data.jpa.domain.Specification, com.sendtend.web.util.dwz.Page)	
	 */
	@Override
	public List<TblMemberDataControl> findByExample(
			Specification<TblMemberDataControl> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberDataControl> springDataPage = dataControlDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
