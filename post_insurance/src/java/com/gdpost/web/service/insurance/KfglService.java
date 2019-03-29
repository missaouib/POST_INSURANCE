/**
 * 
 * ==========================================================
 * 客服管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.main.Inquire;
import com.gdpost.web.entity.main.Issue;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.util.dwz.Page;

public interface KfglService {
	Issue get(Long id);

	void saveOrUpdate(Issue user);

	void delete(Long id);
	
	List<Issue> findAll(Page page);
	
	List<Issue> findByExample(Specification<Issue> specification, Page page);
	
	Issue getByIssueNo(String username);
	
	List<Issue> getTODOIssueList(User user);
	
	List<String> getIssueTypeList();
	
	Inquire getInquire(Long id);

	void saveOrUpdateInquire(Inquire user);

	void deleteInquire(Long id);
	
	List<Inquire> findAllInquire(Page page);
	
	List<Inquire> findByInquireExample(Specification<Inquire> specification, Page page);
	
	Inquire getByInquireNo(String username);
	
	List<String> getInquireSubtypeList();
	
}
