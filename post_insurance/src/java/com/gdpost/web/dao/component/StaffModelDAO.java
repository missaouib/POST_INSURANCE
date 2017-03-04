package com.gdpost.web.dao.component;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.component.StaffModel;

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
public interface StaffModelDAO extends JpaRepository<StaffModel, String>, JpaSpecificationExecutor<StaffModel> {
	
	@Query(name="getProvStaffCountWithPolicyDateNoBankCode",
			value="select left(tp.organ_name,2) as organ_name,count(tp.organ_name) as sum_staff_count,sum(tp.total_fee) as sum_policy_fee, "
			+ "t2.staff_count as staff_count, "
		      + "t3.policy_fee as policy_fee "
		      + "from (t_policy tp "
		      + "left JOIN "
		      + "(select left(itp.organ_name,2) as organ_name, count(itp.organ_name)  as staff_count "
		      + "from t_policy itp "
		      + "where itp.staff_flag=1 "
		      + "and itp.organ_code<>\"86440001\" "
		      + "and itp.organ_code like :orgCode "
		      + "and itp.policy_date between :p1 and :p2 "
		      + "and itp.prod_name like :prdCode "
		      + "and itp.fee_frequency like :toPerm "
		      + "group by left(itp.organ_name,2) ) as t2 "
		      + "on left(tp.organ_name,2)=t2.organ_name) "
		      + "left join "
		      + "(select left(itp.organ_name,2) as organ_name, sum(itp.policy_fee) as policy_fee "
		      + "from t_policy itp "
		      + "where itp.staff_flag=1 "
		      + "and itp.organ_code<>\"86440001\" "
		      + "and itp.organ_code like :orgCode "
		      + "and itp.policy_date between :p1 and :p2 "
		      + "and itp.prod_name like :prdCode "
		      + "and itp.fee_frequency like :toPerm "
		      + "group by left(itp.organ_name,2) ) as t3 "
		      + "on left(tp.organ_name,2)=t3.organ_name "
		      + "where  "
		      + "tp.organ_code like :orgCode "
		      + "and tp.organ_code<>\"86440001\" "
		      + "and tp.policy_date between :p1 and :p2 "
		      + "and tp.prod_name like :prdCode "
		      + "and tp.fee_frequency like :toPerm "
		      + "group by left(tp.organ_name,2) "
		      + "order by tp.organ_code;",
			nativeQuery=true)
	List<StaffModel> getProvStaffCountWithPolicyDateNoBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm);
	
	@Query(name="getProvStaffCountWithPolicyDate",
			value="select left(tp.organ_name,2) as organ_name,count(tp.organ_name) as sum_staff_count,sum(tp.total_fee) as sum_policy_fee, "
	        + "t2.staff_count as staff_count, "
	        + "t3.policy_fee as policy_fee "
	        + "from t_bank_code tbc, t_policy tp "
	        + "left JOIN "
	        + "(select left(itp.organ_name,2) as organ_name, count(itp.organ_name)  as staff_count "
	        + "from t_policy itp, t_bank_code itbc "
	        + "where itp.staff_flag=1 and itp.bank_code=itbc.cpi_code and itbc.net_flag=:netFlag "
	        + "and itp.organ_code<>\"86440001\" "
	        + "and itp.organ_code like :orgCode "
	        + "and itp.policy_date between :p1 and :p2 "
	        + "and itp.prod_name like :prdCode "
	        + "and itp.fee_frequency like :toPerm "
	        + "group by left(itp.organ_name,2) ) as t2 "
	        + "on left(tp.organ_name,2)=t2.organ_name "
	        + "left join "
	        + "(select left(itp.organ_name,2) as organ_name, sum(itp.policy_fee) as policy_fee "
	        + "from t_policy itp, t_bank_code itbc "
	        + "where itp.staff_flag=1 and itp.bank_code=itbc.cpi_code and itbc.net_flag=:netFlag "
	        + "and itp.organ_code<>\"86440001\" "
	        + "and itp.organ_code like :orgCode "
	        + "and itp.policy_date between :p1 and :p2 "
	        + "and itp.prod_name like :prdCode "
	        + "and itp.fee_frequency like :toPerm "
	        + "group by left(itp.organ_name,2) ) as t3 "
	        + "on left(tp.organ_name,2)=t3.organ_name "
	        + "where tp.bank_code=tbc.cpi_code and tbc.net_flag=:netFlag "
	        + "and tp.organ_code like :orgCode "
	        + "and tp.organ_code<>\"86440001\" "
	        + "and tp.policy_date between :p1 and :p2 "
	        + "and tp.prod_name like :prdCode "
	        + "and tp.fee_frequency like :toPerm "
	        + "group by left(tp.organ_name,2) "
	        + "order by tp.organ_code;",
			nativeQuery=true)
	List<StaffModel> getProvStaffCountWithPolicyDate(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm);
	
	@Query(name="getStaffCountWithPolicyDateNoBankCode",
			value="select tp.organ_name as organ_name,count(tp.organ_name) as sum_staff_count,sum(tp.total_fee) as sum_policy_fee, "
			+ "t2.staff_count as staff_count, "
		      + "t3.policy_fee as policy_fee "
		      + "from (t_policy tp "
		      + "left JOIN "
		      + "(select itp.organ_name as organ_name, count(itp.organ_name)  as staff_count "
		      + "from t_policy itp "
		      + "where itp.staff_flag=1 "
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
		      + "where itp.staff_flag=1 "
		      + "and itp.organ_code<>\"86440001\" "
		      + "and itp.organ_code like :orgCode "
		      + "and itp.policy_date between :p1 and :p2 "
		      + "and itp.prod_name like :prdCode "
		      + "and itp.fee_frequency like :toPerm "
		      + "group by itp.organ_name ) as t3 "
		      + "on tp.organ_name=t3.organ_name "
		      + "where  "
		      + "tp.organ_code like :orgCode "
		      + "and tp.organ_code<>\"86440001\" "
		      + "and tp.policy_date between :p1 and :p2 "
		      + "and tp.prod_name like :prdCode "
		      + "and tp.fee_frequency like :toPerm "
		      + "group by tp.organ_name "
		      + "order by tp.organ_code;",
			nativeQuery=true)
	List<StaffModel> getStaffCountWithPolicyDateNoBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm);
	
	@Query(name="getStaffCountWithPolicyDate",
			value="select tp.organ_name as organ_name,count(tp.organ_name) as sum_staff_count,sum(tp.total_fee) as sum_policy_fee, "
	        + "t2.staff_count as staff_count, "
	        + "t3.policy_fee as policy_fee "
	        + "from t_bank_code tbc, t_policy tp "
	        + "left JOIN "
	        + "(select itp.organ_name as organ_name, count(itp.organ_name)  as staff_count "
	        + "from t_policy itp, t_bank_code itbc "
	        + "where itp.staff_flag=1 and itp.bank_code=itbc.cpi_code and itbc.net_flag=:netFlag "
	        + "and itp.organ_code<>\"86440001\" "
	        + "and itp.organ_code like :orgCode "
	        + "and itp.policy_date between :p1 and :p2 "
	        + "and itp.prod_name like :prdCode "
	        + "and itp.fee_frequency like :toPerm "
	        + "group by left(itp.organ_name,2) ) as t2 "
	        + "on tp.organ_name=t2.organ_name "
	        + "left join "
	        + "(select itp.organ_name as organ_name, sum(itp.policy_fee) as policy_fee "
	        + "from t_policy itp, t_bank_code itbc "
	        + "where itp.staff_flag=1 and itp.bank_code=itbc.cpi_code and itbc.net_flag=:netFlag "
	        + "and itp.organ_code<>\"86440001\" "
	        + "and itp.organ_code like :orgCode "
	        + "and itp.policy_date between :p1 and :p2 "
	        + "and itp.prod_name like :prdCode "
	        + "and itp.fee_frequency like :toPerm "
	        + "group by itp.organ_name ) as t3 "
	        + "on tp.organ_name=t3.organ_name "
	        + "where tp.bank_code=tbc.cpi_code and tbc.net_flag=:netFlag "
	        + "and tp.organ_code like :orgCode "
	        + "and tp.organ_code<>\"86440001\" "
	        + "and tp.policy_date between :p1 and :p2 "
	        + "and tp.prod_name like :prdCode "
	        + "and tp.fee_frequency like :toPerm "
	        + "group by tp.organ_name "
	        + "order by tp.organ_code;",
			nativeQuery=true)
	List<StaffModel> getStaffCountWithPolicyDate(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm);
}