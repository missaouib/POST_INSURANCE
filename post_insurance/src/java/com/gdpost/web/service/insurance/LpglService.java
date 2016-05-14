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
	
	Settlement getSettleBySettlementNo(String name);
	
	SettlementDtl getSettleDtl(Long id);

	void saveOrUpdateSettleDtl(SettlementDtl dtl);

	void deleteSettleDtl(Long id);
	
	List<SettlementDtl> findAllSettleDtl(Page page);
	
	List<SettlementDtl> findBySettleDtlExample(Specification<SettlementDtl> specification, Page page);
	
	SettlementDtl getDtlBySettlementId(Long id);
	
	SettlementLog getSettleLog(Long id);

	void saveOrUpdateSettleLog(SettlementLog log);
	
	List<SettlementLog> findLogBySettleId(Long id);
	
}
