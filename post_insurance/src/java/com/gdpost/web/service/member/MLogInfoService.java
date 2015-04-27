/**
 * 
 */
package com.gdpost.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.main.LogInfo;
import com.gdpost.web.util.dwz.Page;

public interface MLogInfoService {
	LogInfo get(Long id);

	void saveOrUpdate(LogInfo logInfo);

	void delete(Long id);
	
	List<LogInfo> findAll(Page page);
	
	List<LogInfo> findByExample(Specification<LogInfo> specification, Page page);
}
