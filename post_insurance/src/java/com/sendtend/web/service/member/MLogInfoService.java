/**
 * 
 */
package com.sendtend.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.main.LogInfo;
import com.sendtend.web.util.dwz.Page;

public interface MLogInfoService {
	LogInfo get(Long id);

	void saveOrUpdate(LogInfo logInfo);

	void delete(Long id);
	
	List<LogInfo> findAll(Page page);
	
	List<LogInfo> findByExample(Specification<LogInfo> specification, Page page);
}
