/**
 * 
 */
package	com.sendtend.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendtend.web.dao.DictionaryDAO;
import com.sendtend.web.entity.main.Dictionary;
import com.sendtend.web.entity.main.Dictionary.DictionaryType;
import com.sendtend.web.service.DictionaryService;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.dwz.PageUtils;

@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService {
	
	@Autowired
	private DictionaryDAO dictionaryDAO;

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.DictionaryService#get(java.lang.Long)  
	 */ 
	@Override
	public Dictionary get(Long id) {
		return dictionaryDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.sendtend.web.service.DictionaryService#saveOrUpdate(com.sendtend.web.entity.Dictionary)  
	 */
	@Override
	public void saveOrUpdate(Dictionary dictionary) {
		dictionaryDAO.save(dictionary);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.DictionaryService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		dictionaryDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.DictionaryService#findAll(com.sendtend.web.util.dwz.Page)  
	 */
	@Override
	public List<Dictionary> findAll(Page page) {
		org.springframework.data.domain.Page<Dictionary> springDataPage = dictionaryDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.DictionaryService#findByExample(org.springframework.data.jpa.domain.Specification, com.sendtend.web.util.dwz.Page)	
	 */
	@Override
	public List<Dictionary> findByExample(
			Specification<Dictionary> specification, Page page) {
		org.springframework.data.domain.Page<Dictionary> springDataPage = dictionaryDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/* (non-Javadoc)
	 * @see com.sendtend.web.service.DictionaryService#findByThemeName(java.lang.String, com.sendtend.web.util.dwz.Page)
	 */
	@Override
	public List<Dictionary> findByThemeName(String themeName, Page page) {
		if (page == null) {
			return dictionaryDAO.findAllItems(themeName);
		}
		org.springframework.data.domain.Page<Dictionary> springDataPage = dictionaryDAO.findByParentNameAndType(themeName, DictionaryType.ITEM, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
