package com.gdpost.web.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.insurance.UnderWrite;

/**
 * Interface for TUnderWriteDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface UnderWriteDAO extends JpaRepository<UnderWrite, Long>, JpaSpecificationExecutor<UnderWrite> {
	UnderWrite getByPolicyNo(String policyNo);
	UnderWrite getByFormNo(String formNo);
	
	@Query(name="UnderWriteDAO.findDistinctUnderWrite2Pop",
			value="select t1 from UnderWrite t1,Organization t2 where t1.organization.id=t2.id and "
					+ "((t1.provSendDate is not null and t1.citySendDate is null and t1.areaSendDate is null and NOW()-t1.provSendDate>3) or "
					+ "(t1.citySendDate is not null and t1.toNet=1 and t1.clientReceiveDate is null and NOW()-t1.citySendDate>5) or "
					+ "(t1.citySendDate is not null and t1.areaSendDate is null and NOW()-t1.citySendDate>3) or "
					+ "(t1.areaSendDate is not null and NOW()-t1.areaSendDate>5) or "
					+ "(t1.sysDate is not null and now()-t1.sysDate>15)) "
					+ "and t2.orgCode like ?1 and "
					+ "t1.clientReceiveDate is null and t1.status=?2"
	)
	Page<UnderWrite> findDistinctUnderWrite2Pop(String orgCode, String status, Pageable pageable);
	
	@Query(name="UnderWriteDAO.findDistinctUnderWrite2Call",
			value="select t1 from UnderWrite t1,Organization t2 where t1.organization.id=t2.id and "
					+ "((t1.citySendDate is not null and t1.toNet=1 and NOW()-t1.citySendDate>10) or "
					+ "(t1.areaSendDate is not null and NOW()-t1.areaSendDate>10) or "
					+ "(t1.sysDate is not null and now()-t1.sysDate>15)) "
					+ "and t2.orgCode like :orgCode and "
					+ "t1.clientReceiveDate is null and t1.status=:status")
	Page<UnderWrite> findDistinctUnderWrite2Call(@Param("orgCode") String orgCode, @Param("status") String status, Pageable pageable);
	
	@Query(name="UnderWriteDAO.findDistinctUnderWrite2Weixin",
			value="select u from UnderWrite u, Organization o where u.organization.id=o.id and"
			+ "(u.sysDate is not null and NOW()-u.sysDate>20) "
			+ "and o.orgCode like :orgCode and "
			+ "u.clientReceiveDate is null and u.status=:status")
	Page<UnderWrite> findDistinctUnderWrite2Weixin(@Param("orgCode") String orgCode, @Param("status") String status, Pageable pageable);
}