/**
 * 
 * ==========================================================
 * 契约管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.basedata.CheckFixType;
import com.gdpost.web.entity.component.YbtPolicyModel;
import com.gdpost.web.entity.insurance.CheckRecord;
import com.gdpost.web.entity.insurance.CheckWrite;
import com.gdpost.web.entity.insurance.ReuseRisk;
import com.gdpost.web.entity.insurance.UnderWrite;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.util.dwz.Page;

public interface QyglService {
	CheckWrite getCheckWrite(Long id);

	void saveOrUpdateCheckWrite(CheckWrite check);
	
	void deleteCheckWrite(CheckWrite check);

	List<CheckWrite> findAllCheckWrite(Page page);
	
	List<CheckWrite> findByCheckWriteExample(Specification<CheckWrite> specification, Page page);
	
	CheckWrite getByCheckWritePolicyNo(String policyNo);
	
	List<CheckWrite> getTODOWriteIssueList(User user);
	
	CheckRecord getCheckRecord(Long id);
	
	void deleteCheckRecord(Long id);
	
	void saveOrUpdateCheckRecord(CheckRecord check);
	
	List<CheckRecord> findAllCheckRecord(Page page);
	
	List<CheckRecord> findByCheckRecordExample(Specification<CheckRecord> specification, Page page);
	
	CheckRecord getByCheckRecordPolicyNo(String policyNo);
	
	List<CheckRecord> getTODORecordIssueList(User user);
	
	/*
	 * ============================
	 * reuse risk
	 * ============================
	 */
	ReuseRisk getReuseRisk(Long id);

	void saveOrUpdateReuseRisk(ReuseRisk check);
	
	void deleteReuseRisk(ReuseRisk check);

	List<ReuseRisk> findAllReuseRisk(Page page);
	
	List<ReuseRisk> findByReuseRiskExample(Specification<ReuseRisk> specification, Page page);
	
	ReuseRisk getByReuseRiskPolicyNo(String policyNo);
	/*
	 * ================
	 * 人核件
	 * ================
	 */
	UnderWrite getUnderWrite(Long id);

	void saveOrUpdateUnderWrite(UnderWrite uw);
	
	void saveOrUpdateUnderWrite(Date strDate, String emsNo);

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
