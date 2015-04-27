/**
 * 
 */
package	com.gdpost.web.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.member.MDataControlDAO;
import com.gdpost.web.entity.member.TblMemberDataControl;
import com.gdpost.web.service.member.MDataControlService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class MDataControlServiceImpl implements MDataControlService {
	
	@Autowired
	private MDataControlDAO dataControlDAO;

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.DataControlService#get(java.lang.Long)  
	 */ 
	@Override
	public TblMemberDataControl get(Long id) {
		return dataControlDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.DataControlService#saveOrUpdate(com.gdpost.web.entity.main.DataControl)  
	 */
	@Override
	public void saveOrUpdate(TblMemberDataControl dataControl) {
		dataControlDAO.save(dataControl);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.DataControlService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		dataControlDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.DataControlService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<TblMemberDataControl> findAll(Page page) {
		org.springframework.data.domain.Page<TblMemberDataControl> springDataPage = dataControlDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.DataControlService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<TblMemberDataControl> findByExample(
			Specification<TblMemberDataControl> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberDataControl> springDataPage = dataControlDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
