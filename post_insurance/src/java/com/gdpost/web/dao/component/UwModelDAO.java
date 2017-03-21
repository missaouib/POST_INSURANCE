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
			value="select hb.name as org_name,count(hb.L50) as 'l50',count(hb.L30) as 'l30',count(hb.L20) as 'l20', count(hb.L10) as 'l10',(count(hb.L50)+count(hb.L30)+count(hb.L20)+ count(hb.L10)) as 'sc' from "
			+ "(select tb.org_code, tb.name, "
			+ "max(case when longPerm='L50' then 'L50' end) as 'L50', "
			+ "max(case when longPerm='L30' then 'L30' end) as 'L30', "
			+ "max(case when longPerm='L20' then 'L20' end) as 'L20', "
			+ "max(case when longPerm='L10' then 'L10' end) as 'L10' "
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
			+ "and uw.sign_date between '2017-01-01' and '2017-12-31' "
			+ "and org.org_code like '8644%' "
			+ ") as tb "
			+ "GROUP BY tb.org_code, tb.name "
			+ ") as hb "
			+ "group by hb.hb.name "
			+ "order by hb.org_code;",
			nativeQuery=true)
	List<UwModel> getProvUwStastic(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2);
	
	@Query(name="getCityUwStastics",
			value="select tp.organ_name as organ_name,count(tp.organ_name) as sum_staff_count,sum(tp.total_fee) as sum_policy_fee, "
			+ "t2.staff_count as staff_count, "
		      + "t3.policy_fee as policy_fee "
		      + "from (t_policy tp "
		      + "left JOIN "
		      + "(select itp.organ_name as organ_name, count(itp.organ_name)  as staff_count "
		      + "from t_policy itp "
		      + "where itp.staff_flag=1 and itp.cs_flag<>1 "
		      + "and itp.organ_code<>\"86440001\" "
		      + "and itp.organ_code like :orgCode "
		      + "and itp.policy_date between :p1 and :p2 "
		      + "and itp.prod_name like :prdCode "
		      + "and itp.fee_frequency like :toPerm "
		      + "group by left(itp.organ_name,2) ) as t2 "
		      + "on tp.organ_name=t2.organ_name) "
		      + "left join "
		      + "(select itp.organ_name as organ_name, sum(itp.policy_fee) as policy_fee "
		      + "from t_policy itp "
		      + "where itp.staff_flag=1 and itp.cs_flag<>1 "
		      + "and itp.organ_code<>\"86440001\" "
		      + "and itp.organ_code like :orgCode "
		      + "and itp.policy_date between :p1 and :p2 "
		      + "and itp.prod_name like :prdCode "
		      + "and itp.fee_frequency like :toPerm "
		      + "group by itp.organ_name ) as t3 "
		      + "on tp.organ_name=t3.organ_name "
		      + "where tp.cs_flag<>1 "
		      + "and tp.organ_code like :orgCode "
		      + "and tp.organ_code<>\"86440001\" "
		      + "and tp.policy_date between :p1 and :p2 "
		      + "and tp.prod_name like :prdCode "
		      + "and tp.fee_frequency like :toPerm "
		      + "group by tp.organ_name "
		      + "order by tp.organ_code;",
			nativeQuery=true)
	List<UwModel> getCityUwStastics(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2);
}