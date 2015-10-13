/**
 * 
 * ==========================================================
 * 发票管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.main.PayFailList;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.util.dwz.Page;

public interface PayListService {
	PayFailList get(Long id);

	void saveOrUpdate(PayFailList req);

	void delete(Long id);
	
	List<PayFailList> findAll(Page page);
	
	List<PayFailList> findByExample(Specification<PayFailList> specification, Page page);

	List<PayFailList> getBQToFailListTODOIssueList(User user);

	List<PayFailList> getBQFromFailListTODOIssueList(User user);

	List<PayFailList> getQYFromFailListTODOIssueList(User user);

	List<PayFailList> getLPToFailListTODOIssueList(User user);

	List<PayFailList> getXQFromFailListTODOIssueList(User user);
	
}
