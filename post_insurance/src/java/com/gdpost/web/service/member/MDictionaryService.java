/**
 * 
 */
package com.gdpost.web.service.member;

import java.util.Dictionary;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.util.dwz.Page;

public interface MDictionaryService {
	Dictionary get(Long id);

	void saveOrUpdate(Dictionary dictionary);

	void delete(Long id);
	
	List<Dictionary> findAll(Page page);
	
	List<Dictionary> findByExample(Specification<Dictionary> specification, Page page);
	
	List<Dictionary> findByThemeName(String themeName, Page page);
}
