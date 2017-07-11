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

import com.gdpost.web.entity.component.CsLoan;
import com.gdpost.web.entity.component.CsReport;
import com.gdpost.web.entity.component.TinyCsAddr;
import com.gdpost.web.entity.main.ConservationDtl;
import com.gdpost.web.entity.main.ConservationReq;
import com.gdpost.web.entity.main.CsReissue;
import com.gdpost.web.entity.main.OffsiteConservation;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.util.dwz.Page;

public interface BqglService {
	ConservationDtl get(Long id);

	ConservationDtl saveOrUpdate(ConservationDtl user);

	void delete(Long id);

	List<ConservationDtl> findAll(Page page);

	List<ConservationDtl> findByExample(Specification<ConservationDtl> specification, Page page);

	ConservationDtl getByPolicyNo(String policyNo);

	List<ConservationDtl> getTODOIssueList(User user);

	OffsiteConservation getOffsiteConservation(Long id);

	void saveOrUpdateOffsiteConservation(OffsiteConservation oc);

	void deleteOffsiteConservation(Long id);

	List<OffsiteConservation> findAllOffsiteConservation(Page page);

	List<OffsiteConservation> findByOffsiteConservationExample(Specification<OffsiteConservation> specification,
			Page page);

	OffsiteConservation getOffsiteConservationByPolicyNo(String policyNo);

	List<CsReissue> getAllCsReissue(Page page);

	List<CsReissue> getByCsReissueExample(Specification<CsReissue> specification, Page page);

	CsReissue getCsReissue(Long id);

	CsReissue getCsReissueByPolicyNo(String policyNo);

	void updateCsReissue(CsReissue reissue);

	// 免填单保全申请
	ConservationReq getConservationReq(Long id);

	List<ConservationReq> findConservationReqByExample(Specification<ConservationReq> specification, Page page);

	void updateConservationReq(ConservationReq conservationReq);

	List<CsReport> findCsReportByExample(Specification<CsReport> specification, Page page);

	List<TinyCsAddr> findCsAddrByExample(String addr);

	// 保全质押借款
	CsLoan getCsLoan(Long id);

	List<CsLoan> findCsLoanByExample(Specification<CsLoan> specification, Page page);

	void updateCsLoan(CsLoan conservationReq);


}
