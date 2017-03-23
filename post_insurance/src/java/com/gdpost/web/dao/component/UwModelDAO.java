package com.gdpost.web.dao.component;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.component.UwModel;

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
public interface UwModelDAO extends JpaRepository<UwModel, String>, JpaSpecificationExecutor<UwModel> {
	
	@Query(name="getProvUwStastic",
			value="select left(hb.name,2) as org_name,SUM(hb.L50) as 'l50',SUM(hb.L30) as 'l30',SUM(hb.L20) as 'l20', SUM(hb.L10) as 'l10',(SUM(hb.L50)+SUM(hb.L30)+SUM(hb.L20)+ SUM(hb.L10)) as 'sc' from "
			+ "(select tb.org_code, tb.name, "
			+ "count(case when longPerm='L50' then 'L50' else null end) as 'L50', "
			+ "count(case when longPerm='L30' then 'L30' else NULL end) as 'L30', "
			+ "count(case when longPerm='L20' then 'L20' else NULL end) as 'L20', "
			+ "count(case when longPerm='L10' then 'L10' else NULL end) as 'L10' "
			+ "from ( "
			+ "select org.org_code, org.name, "
			+ "case when DATEDIFF(now(),prov_send_date)>=50 then 'L50' "
			+ "when DATEDIFF(now(),prov_send_date)>=30 then 'L30' "
			+ "when DATEDIFF(now(),prov_send_date)>=20 then 'L20' "
			+ "else 'L10' "
			+ "end as longPerm "
			+ "from t_under_write uw, t_organization org "
			+ "where uw.organ_id=org.id and prov_send_date is not null "
			+ "and client_receive_date is null "
			+ "and uw.sign_date between :p1 and :p2 "
			+ "and org.org_code like :orgCode "
			+ ") as tb "
			+ "GROUP BY tb.org_code, tb.name "
			+ ") as hb "
			+ "group by left(hb.name,2) "
			+ "order by hb.org_code;",
			nativeQuery=true)
	List<UwModel> getProvUwStastic(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2);
	
	@Query(name="getCityUwStastic",
			value="select hb.name as org_name,SUM(hb.L50) as 'l50',SUM(hb.L30) as 'l30',SUM(hb.L20) as 'l20', SUM(hb.L10) as 'l10',(SUM(hb.L50)+SUM(hb.L30)+SUM(hb.L20)+ SUM(hb.L10)) as 'sc' from "
			+ "(select tb.org_code, tb.name, "
			+ "count(case when longPerm='L50' then 'L50' else null end) as 'L50', "
			+ "count(case when longPerm='L30' then 'L30' else NULL end) as 'L30', "
			+ "count(case when longPerm='L20' then 'L20' else NULL end) as 'L20', "
			+ "count(case when longPerm='L10' then 'L10' else NULL end) as 'L10' "
			+ "from ( "
			+ "select org.org_code, org.name, "
			+ "case when DATEDIFF(now(),prov_send_date)>=50 then 'L50' "
			+ "when DATEDIFF(now(),prov_send_date)>=30 then 'L30' "
			+ "when DATEDIFF(now(),prov_send_date)>=20 then 'L20' "
			+ "else 'L10' "
			+ "end as longPerm "
			+ "from t_under_write uw, t_organization org "
			+ "where uw.organ_id=org.id and prov_send_date is not null "
			+ "and client_receive_date is null "
			+ "and uw.sign_date between :p1 and :p2 "
			+ "and org.org_code like :orgCode "
			+ ") as tb "
			+ "GROUP BY tb.org_code, tb.name "
			+ ") as hb "
			+ "group by hb.hb.name "
			+ "order by hb.org_code;",
			nativeQuery=true)
	List<UwModel> getCityUwStastic(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2);
}