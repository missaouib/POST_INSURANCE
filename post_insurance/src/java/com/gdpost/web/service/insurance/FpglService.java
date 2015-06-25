/**
 * 
 * ==========================================================
 * 发票管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.main.InvoiceReq;
import com.gdpost.web.entity.main.Policy;
import com.gdpost.web.util.dwz.Page;

public interface FpglService {
	InvoiceReq get(Long id);

	void saveOrUpdate(InvoiceReq req);

	void delete(Long id);
	
	List<InvoiceReq> findAll(Page page);
	
	List<InvoiceReq> findByExample(Specification<InvoiceReq> specification, Page page);
	
	InvoiceReq getByPolicyAndFeeDate(Policy policy, Date feeDate);
}
