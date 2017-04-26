package com.gdpost.web.dao.component;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.component.UwDtlModel;

/**
 * A data access object (DAO) providing persistence and search support for
 * TConservationDtl entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see com.gdpost.web.entity.main.ConservationDtl
 * @author MyEclipse Persistence Tools
 */
public interface UwDtlModelDAO extends JpaRepository<UwDtlModel, Long>, JpaSpecificationExecutor<UwDtlModel> {
	
	@Query(name="getUwDtlStastic",
			value="select uw.id, uw.policy_no, uw.form_no, uw.holder, uw.policy_fee, uw.prd_name, uw.net_name, "
					+ "uw.ybt_date, uw.sign_date, uw.prov_send_date, uw.prov_ems_no, org.org_code, org.name as org_name, "
			+ "case when DATEDIFF(now(),sign_date)>=50 then 'L50' "
			+ "when DATEDIFF(now(),sign_date)>=30 then 'L30' "
			+ "when DATEDIFF(now(),sign_date)>=20 then 'L20' "
			+ "else 'L10' "
			+ "end as long_perm, uw.plan_date, uw.remark "
			+ "from t_under_write uw, t_organization org "
			+ "where uw.organ_id=org.id and policy_no is not null "
			+ "and client_receive_date is null "
			+ "and uw.sign_date between :p1 and :p2 "
			+ "and org.org_code like :orgCode "
			+ "order by org.org_code;",
			nativeQuery=true)
	List<UwDtlModel> getUwDtlStastic(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2);
	
}