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

import com.gdpost.web.entity.insurance.PayFailList;
import com.gdpost.web.entity.insurance.PaySuccessList;
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

	PaySuccessList getSuccessDtl(Long id);

	void saveOrUpdateSuccessDtl(PaySuccessList policy);

	void deleteSuccessDtl(Long id);

	List<PaySuccessList> findAllSuccessList(Page page);

	List<PaySuccessList> findBySuccessDtlExample(
			Specification<PaySuccessList> specification, Page page);

	List<PaySuccessList> getBQToSuccessListTODOIssueList(User user);

	List<PaySuccessList> getBQFromSuccessListTODOIssueList(User user);

	List<PaySuccessList> getXQFromSuccessListTODOIssueList(User user);

	List<PaySuccessList> getQYFromSuccessListTODOIssueList(User user);

	List<PaySuccessList> getLPToSuccessListTODOIssueList(User user);

}
