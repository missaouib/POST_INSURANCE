/**
 * 
 * ==========================================================
 * 契约管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.basedata.CheckFixType;
import com.gdpost.web.entity.component.YbtPolicyModel;
import com.gdpost.web.entity.insurance.CheckRecord;
import com.gdpost.web.entity.insurance.CheckWrite;
import com.gdpost.web.entity.insurance.UnderWrite;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.util.dwz.Page;

public interface QyglService {
	CheckWrite getCheckWrite(Long id);
	
	CheckRecord getCheckRecord(Long id);

	void saveOrUpdateCheckWrite(CheckWrite check);
	
	void saveOrUpdateCheckRecord(CheckRecord check);
	
	void deleteCheckWrite(CheckWrite check);
	
	void deleteCheckRecord(Long id);

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

	List<CheckFixType> getCheckFixTypeList();
	
	Integer getOverdueUWCount(HttpServletRequest request, User user);
	
	Integer getOverdueUWCall(HttpServletRequest request, User user);
	
	Integer getOverdueUWWeixin(HttpServletRequest request, User user);
	
	List<UnderWrite> getOverdueUWList2Pop(User user, Page page);
	
	List<UnderWrite> getOverdueUWList2Call(User user, Page page);
	
	List<UnderWrite> getOverdueUWList2Weixin(User user, Page page);
	
	/*
	 * ============== YBT POLICY LIST ================
	 */
	List<YbtPolicyModel> listYBTPolicys(String orgCode, String date1, String date2, Page pageable);

}
