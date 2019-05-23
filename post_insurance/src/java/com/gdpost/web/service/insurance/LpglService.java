/**
 * 
 * ==========================================================
 * 理赔管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.insurance.Gsettle;
import com.gdpost.web.entity.insurance.GsettleDtl;
import com.gdpost.web.entity.insurance.GsettleLog;
import com.gdpost.web.entity.insurance.SettleTask;
import com.gdpost.web.entity.insurance.SettleTaskLog;
import com.gdpost.web.entity.insurance.Settlement;
import com.gdpost.web.entity.insurance.SettlementDtl;
import com.gdpost.web.entity.insurance.SettlementLog;
import com.gdpost.web.util.dwz.Page;

public interface LpglService {
	Settlement getSettle(Long id);

	void saveOrUpdateSettle(Settlement settle);

	void deleteSettle(Long id);
	
	List<Settlement> findAllSettle(Page page);
	
	List<Settlement> findBySettleExample(Specification<Settlement> specification, Page page);
	
	Settlement getSettleByPolicyNo(String name);
	
	SettlementDtl getSettleDtl(Long id);

	void saveOrUpdateSettleDtl(SettlementDtl dtl);

	void deleteSettleDtl(Long id);
	
	List<SettlementDtl> findAllSettleDtl(Page page);
	
	List<SettlementDtl> findBySettleDtlExample(Specification<SettlementDtl> specification, Page page);
	
	SettlementDtl getDtlBySettlementId(Long id);
	
	SettlementDtl getDtlByPolicyPolicyNo(String policyNo);
	
	SettlementLog getSettleLog(Long id);

	void saveOrUpdateSettleLog(SettlementLog log);
	
	List<SettlementLog> findLogBySettleId(Long id);
	
	List<SettlementLog> findDealLogBySettleId(Long id);
	
	/**
	 * SettleTask
	 */
	SettleTask getSettleTask(Long id);

	void saveOrUpdateSettleTask(SettleTask settle);

	void deleteSettleTask(Long id);
	
	List<SettleTask> findAllSettleTask(Page page);
	
	List<SettleTask> findBySettleTaskExample(Specification<SettleTask> specification, Page page);
	
	List<SettleTaskLog> findLogBySettleTaskId(Long id);
	
	List<SettleTaskLog> findDealLogByTaskId(Long id);

	void saveOrUpdateSettleTaskLog(SettleTaskLog log);
	
	/*
	 * ==========
	 * group settle
	 * ==========
	 * 
	 */
	Gsettle getGsettle(Long id);

	void saveOrUpdateGsettle(Gsettle settle);

	void deleteGsettle(Long id);
	
	List<Gsettle> findAllGsettle(Page page);
	
	List<Gsettle> findByGsettleExample(Specification<Gsettle> specification, Page page);
	
	Gsettle getGsettleByPolicyNo(String name);
	
	GsettleDtl getGsettleDtl(Long id);

	void saveOrUpdateGsettleDtl(GsettleDtl dtl);

	void deleteGsettleDtl(Long id);
	
	List<GsettleDtl> findAllGsettleDtl(Page page);
	
	List<GsettleDtl> findByGsettleDtlExample(Specification<GsettleDtl> specification, Page page);
	
	GsettleDtl getDtlByGsettleId(Long id);
	
	GsettleDtl getGsettleDtlByGpolicyNo(String policyNo);
	
	GsettleLog getGsettleLog(Long id);

	void saveOrUpdateGsettleLog(GsettleLog log);
	
	List<GsettleLog> findLogByGsettleId(Long id);
	
	List<GsettleLog> findDealLogByGsettleId(Long id);
}
