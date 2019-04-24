package com.gdpost.web.dao.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.gdpost.web.entity.component.YbtPolicyModel;

/**
 * Interface for TPolicyDtlDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface YbtPolicyModelDAO extends JpaRepository<YbtPolicyModel, String>, JpaSpecificationExecutor<YbtPolicyModel> {
	@Query(nativeQuery=true,
			name="getYbtPolicyDateList",
			value="select tp.organ_name, tp.bank_name, tp.policy_no, tp.form_no, tp.holder, tp.prod_name, tp.policy_fee, tp.policy_date, "
					+ "case when cw.policy_no is null then false else true end as has_err, cw.fix_status, "
					+ "case when dnsd.policy_no is null then true else false end as has_scan "
					+ "from t_policy tp left join t_check_write cw on tp.policy_no=cw.policy_no left join t_doc_not_scan_dtl dnsd on tp.policy_no=dnsd.policy_no "
					+ "where tp.attached_flag=0 and tp.policy_no like '8644%' and tp.organ_code like ?1 and tp.policy_date between ?2 and ?3  ORDER BY ?#{#pageable}", 
			countQuery = "SELECT COUNT(tp.policy_no) FROM t_policy tp where tp.attached_flag=0 and tp.policy_no like ?1 and tp.policy_date between ?2 and ?3"		
		  )
	Page<YbtPolicyModel> getYbtPolicyDateList(String orgCode, String policyDate1, String policyDate2, Pageable pageable);
	
}