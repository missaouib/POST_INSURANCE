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

import com.gdpost.web.entity.insurance.PayList;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.util.dwz.Page;

public interface PayListService {
	PayList get(Long id);

	void saveOrUpdate(PayList req);

	void delete(Long id);
	
	List<PayList> findFailAll(Page page);
	
	List<PayList> findFailByExample(Specification<PayList> specification, Page page);

	List<PayList> getBQToFailListTODOIssueList(User user);

	List<PayList> getBQFromFailListTODOIssueList(User user);

	List<PayList> getQYFromFailListTODOIssueList(User user);

	List<PayList> getLPToFailListTODOIssueList(User user);

	List<PayList> getXQFromFailListTODOIssueList(User user);

	PayList getSuccessDtl(Long id);

	void saveOrUpdateSuccessDtl(PayList policy);

	void deleteSuccessDtl(Long id);

	List<PayList> findAllSuccessList(Page page);

	List<PayList> findBySuccessDtlExample(Specification<PayList> specification, Page page);

	List<PayList> getBQToSuccessListTODOIssueList(User user);

	List<PayList> getBQFromSuccessListTODOIssueList(User user);

	List<PayList> getXQFromSuccessListTODOIssueList(User user);

	List<PayList> getQYFromSuccessListTODOIssueList(User user);

	List<PayList> getLPToSuccessListTODOIssueList(User user);

}
