package com.gdpost.web.dao.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.component.DocStatModel;

/**
 * Interface for TPolicyDtlDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface DocStatModelDAO extends JpaRepository<DocStatModel, String>, JpaSpecificationExecutor<DocStatModel> {
	@Query(nativeQuery=true,
			name="getDocNotScanStat",
			value="select left(dns.organ_name,2) as org_name,count(dns.policy_no) as sum_doc "
			+ "from t_doc_not_scan_dtl dns, t_organization tob "
			+ "where dns.organ_name=tob.name group by left(dns.organ_name,2) order by tob.org_code; ")
	List<DocStatModel> getDocNotScanStat();
	
	@Query(nativeQuery=true,
			name="getSubDocNotScanStat",
			value="select tob.short_name as org_name,count(dns.policy_no) as sum_doc "
			+ "from t_doc_not_scan_dtl dns, t_organization tob "
			+ "where dns.organ_name=tob.name and LEFT(organ_name,2)=:organName "
			+ "group by tob.short_name order by tob.org_code;")
	List<DocStatModel> getSubDocNotScanStat(@Param("organName")String organName);
	
	@Query(nativeQuery=true,
			name="getDocSumStat",
			value="select left(tp.organ_name,2) as org_name,count(tp.policy_no) as sum_doc "
			+ "from t_policy tp, t_organization tob "
			+ "where tp.organ_name=tob.name and LEFT(tp.organ_name,2)=:organName "
			+ "and tp.policy_date between :d1 and :d2 and tp.attached_flag=0 "
			+ "group by left(tp.organ_name,2) order by tob.org_code;")
	List<DocStatModel> getDocSumStat(@Param("organName")String organName, @Param("d1")String d1, @Param("d2")String d2);
}