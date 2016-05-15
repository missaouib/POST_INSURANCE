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

import com.gdpost.web.entity.component.SettleTask;
import com.gdpost.web.entity.component.SettleTaskLog;
import com.gdpost.web.entity.component.Settlement;
import com.gdpost.web.entity.component.SettlementDtl;
import com.gdpost.web.entity.component.SettlementLog;
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
	
	SettlementLog getSettleLog(Long id);

	void saveOrUpdateSettleLog(SettlementLog log);
	
	List<SettlementLog> findLogBySettleId(Long id);
	
	/**
	 * SettleTask
	 */
	SettleTask getSettleTask(Long id);

	void saveOrUpdateSettleTask(SettleTask settle);

	void deleteSettleTask(Long id);
	
	List<SettleTask> findAllSettleTask(Page page);
	
	List<SettleTask> findBySettleTaskExample(Specification<SettleTask> specification, Page page);
	
	List<SettleTaskLog> findLogBySettleTaskId(Long id);

	void saveOrUpdateSettleTaskLog(SettleTaskLog log);
}
