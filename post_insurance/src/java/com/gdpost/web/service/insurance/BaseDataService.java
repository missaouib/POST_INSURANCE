/**
 * 
 * ==========================================================
 * 保全管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.basedata.BankCode;
import com.gdpost.web.entity.basedata.CallDealType;
import com.gdpost.web.entity.basedata.CheckFixType;
import com.gdpost.web.entity.basedata.ConservationError;
import com.gdpost.web.entity.basedata.IssueType;
import com.gdpost.web.entity.basedata.Prd;
import com.gdpost.web.entity.basedata.RenewalType;
import com.gdpost.web.entity.main.ConservationType;
import com.gdpost.web.entity.main.ProvOrgCode;
import com.gdpost.web.util.dwz.Page;

public interface BaseDataService {
	/*
	 * ==============
	 * bank code
	 * ==============
	 */
	BankCode getBankCode(Long id);

	void saveOrUpdateBankCode(BankCode type);

	void deleteBankCode(Long id);
	
	List<BankCode> findAllBankCode(Page page);
	
	List<BankCode> findByBankCodeExample(Specification<BankCode> specification, Page page);
	
	BankCode getByCpiCode(String cpiCode);
	
	/*
	 * ==============
	 * call deal type
	 * ==============
	 */
	CallDealType getCallDealType(Long id);

	void saveOrUpdateCallDealType(CallDealType type);

	void deleteCallDealType(Long id);
	
	List<CallDealType> findAllCallDealType(Page page);
	
	List<CallDealType> findByCallDealTypeExample(Specification<CallDealType> specification, Page page);
	
	CallDealType getByCallDealTypeNameAndFlag(String typeName, Integer falg);
	
	/*
	 * ==============
	 * ConservationError
	 * ==============
	 */
	ConservationError getConservationError(Long id);

	void saveOrUpdateConservationError(ConservationError type);

	void deleteConservationError(Long id);
	
	List<ConservationError> findAllConservationError(Page page);
	
	List<ConservationError> findByConservationErrorExample(Specification<ConservationError> specification, Page page);
	
	ConservationError getByErrorCode(String errorCode);
	
	/*
	 * ==============
	 * issueType
	 * ==============
	 */
	IssueType getIssueType(Long id);

	void saveOrUpdateIssueType(IssueType type);

	void deleteIssueType(Long id);
	
	List<IssueType> findAllIssueType(Page page);
	
	List<IssueType> findByIssueTypeExample(Specification<IssueType> specification, Page page);
	
	IssueType getByIssueTypeName(String typeName);
	
	/*
	 * ==============
	 * RenewalType
	 * ==============
	 */
	RenewalType getRenewalType(Long id);

	void saveOrUpdateRenewalType(RenewalType type);

	void deleteRenewalType(Long id);
	
	List<RenewalType> findAllRenewalType(Page page);
	
	List<RenewalType> findByRenewalTypeExample(Specification<RenewalType> specification, Page page);
	
	RenewalType getByRenewalTypeName(String typeName);
	
	/*
	 * ==============
	 * product
	 * ==============
	 */
	Prd getPrd(Long id);

	void saveOrUpdatePrd(Prd type);

	void deletePrd(Long id);
	
	List<Prd> findAllPrd(Page page);
	
	List<Prd> findByPrdExample(Specification<Prd> specification, Page page);
	
	Prd getByPrdCode(String typeName);
	
	/*
	 * ==============
	 * CheckFixType
	 * ==============
	 */
	CheckFixType getCheckFixType(Long id);

	void saveOrUpdateCheckFixType(CheckFixType type);

	void deleteCheckFixType(Long id);
	
	List<CheckFixType> findAllCheckFixType(Page page);
	
	List<CheckFixType> findByCheckFixTypeExample(Specification<CheckFixType> specification, Page page);
	
	CheckFixType getByCheckFixTypeName(String typeName);
	
	/*
	 * ==============
	 * ConservationType
	 * ==============
	 */
	ConservationType getConservationType(Long id);

	void saveOrUpdateConservationType(ConservationType type);

	void deleteConservationType(Long id);
	
	List<ConservationType> findAllConservationType(Page page);
	
	List<ConservationType> findByConservationTypeExample(Specification<ConservationType> specification, Page page);
	
	ConservationType getByConservationTypeName(String typeName);
	
	/*
	 * ==============
	 * ProvOrgCode
	 * ==============
	 */
	ProvOrgCode getProvOrgCode(Long id);

	void saveOrUpdateProvOrgCode(ProvOrgCode type);

	void deleteProvOrgCode(Long id);
	
	List<ProvOrgCode> findAllProvOrgCode(Page page);
	
	List<ProvOrgCode> findByProvOrgCodeExample(Specification<ProvOrgCode> specification, Page page);
	
	ProvOrgCode getByProvOrgCodeOrgCode(String orgCode);
}
