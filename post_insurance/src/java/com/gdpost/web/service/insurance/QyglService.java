/**
 * 
 * ==========================================================
 * 契约管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.main.CheckRecord;
import com.gdpost.web.entity.main.CheckWrite;
import com.gdpost.web.entity.main.UnderWrite;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.util.dwz.Page;

public interface QyglService {
	CheckWrite getCheckWrite(Long id);
	
	CheckRecord getCheckRecord(Long id);

	void saveOrUpdateCheckWrite(CheckWrite check);
	
	void saveOrUpdateCheckRecord(CheckRecord check);

	//void delete(Long id);
	
	List<CheckWrite> findAllCheckWrite(Page page);
	
	List<CheckRecord> findAllCheckRecord(Page page);
	
	List<CheckWrite> findByCheckWriteExample(Specification<CheckWrite> specification, Page page);
	
	List<CheckRecord> findByCheckRecordExample(Specification<CheckRecord> specification, Page page);
	
	CheckWrite getByCheckWritePolicyNo(String policyNo);
	
	CheckRecord getByCheckRecordPolicyNo(String policyNo);
	
	List<CheckWrite> getTODOWriteIssueList(User user);
	
	List<CheckRecord> getTODORecordIssueList(User user);
	
	/*
	 * ================
	 * 人核件
	 * ================
	 */
	UnderWrite getUnderWrite(Long id);

	void saveOrUpdateUnderWrite(UnderWrite uw);

	void deleteUnderWrite(Long id);
	
	List<UnderWrite> findAllUnderWrite(Page page);
	
	List<UnderWrite> findByUnderWriteExample(Specification<UnderWrite> specification, Page page);
	
	UnderWrite getUnderWriteByPolicyNo(String policyNo);
	
	UnderWrite getUnderWriteByFormNo(String formNo);
	
	List<UnderWrite> getTODOUnderWriteList(User user);
}
