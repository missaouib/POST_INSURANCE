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
			value="select left(tp.organ_name,2) as organ_name, count(tp.organ_name) as staff_count, sum(tp.policy_fee) as policy_fee, "
			+ "(select count(t4.organ_name) from t_policy t4 where t4.fee_frequency like :toPerm and t4.attached_flag=0 and t4.policy_date between :p1 and :p2 and left(t4.organ_name,2)=left(tp.organ_name,2)) as sum_staff_count, "
			+ "(select sum(t5.policy_fee) from t_policy t5 where t5.perm>1 and t5.policy_date between :p1 and :p2 and left(t5.organ_name,2)=left(tp.organ_name,2)) as sum_policy_fee "
			+ "from t_policy tp, t_policy_dtl tpd, t_staff ts "
			+ "where tp.policy_no=tpd.policy_no and tpd.holder_card_num=ts.id_card and tp.fee_frequency like :toPerm "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.prod_name like :prdCode "
			+ "group by left(tp.organ_name,2) "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<StaffModel> getProvStaffCountWithPolicyDateNoBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm);
	
	@Query(name="getProvStaffCountWithPolicyDate",
			value="select left(tp.organ_name,2) as organ_name, count(tp.organ_name) as staff_count, sum(tp.policy_fee) as policy_fee, "
			+ "(select count(t4.organ_name) from t_policy t4, t_bank_code t7 where t4.fee_frequency like :toPerm and t4.attached_flag=0 and t4.bank_code=t7.cpi_code and left(t4.organ_name,2)=left(tp.organ_name,2) and t4.policy_date between :p1 and :p2 and t7.net_flag=:netFlag and t4.organ_code like :orgCode and t4.prod_name like :prdCode) as sum_staff_count, "
			+ "(select sum(t5.policy_fee) from t_policy t5, t_bank_code t6 where t5.fee_frequency like :toPerm and t5.bank_code=t6.cpi_code and left(t5.organ_name,2)=left(tp.organ_name,2) and t5.policy_date between :p1 and :p2 and t6.net_flag=:netFlag and t5.organ_code like :orgCode and t5.prod_name like :prdCode ) as sum_policy_fee "
			+ "from t_policy tp, t_policy_dtl tpd, t_staff ts, t_bank_code tbc "
			+ "where tp.policy_no=tpd.policy_no and tpd.holder_card_num=ts.id_card and tp.bank_code=tbc.cpi_code and tp.fee_frequency like :toPerm "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tbc.net_flag=:netFlag "
			+ "and tp.prod_name like :prdCode "
			+ "group by left(tp.organ_name,2) "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<StaffModel> getProvStaffCountWithPolicyDate(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm);
	
	@Query(name="getStaffCountWithPolicyDateNoBankCode",
			value="select tp.organ_name as organ_name, count(tp.organ_name) as staff_count, sum(tp.policy_fee) as policy_fee, "
			+ "(select count(t4.organ_name) from t_policy t4 where t4.fee_frequency like :toPerm and t4.attached_flag=0 and t4.policy_date between :p1 and :p2 and t4.organ_code=tp.organ_code) as sum_staff_count, "
			+ "(select sum(t5.policy_fee) from t_policy t5 where t5.fee_frequency like :toPerm and t5.policy_date between :p1 and :p2 and t5.organ_code=tp.organ_code) as sum_policy_fee "
			+ "from t_policy tp, t_policy_dtl tpd, t_staff ts "
			+ "where tp.policy_no=tpd.policy_no and tpd.holder_card_num=ts.id_card and tp.fee_frequency like :toPerm "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.prod_name like :prdCode "
			+ "group by tp.organ_name "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<StaffModel> getStaffCountWithPolicyDateNoBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm);
	
	@Query(name="getStaffCountWithPolicyDate",
			value="select tp.organ_name as organ_name, count(tp.organ_name) as staff_count, sum(tp.policy_fee) as policy_fee, "
			+ "(select count(t4.organ_name) from t_policy t4, t_bank_code t7 where t4.fee_frequency like :toPerm and t4.attached_flag=0 and t4.bank_code=t7.cpi_code and t4.organ_code=tp.organ_code and t4.policy_date between :p1 and :p2 and t7.net_flag=:netFlag and t4.organ_code like :orgCode and t4.prod_name like :prdCode) as sum_staff_count, "
			+ "(select sum(t5.policy_fee) from t_policy t5, t_bank_code t6 where t5.fee_frequency like :toPerm and t5.bank_code=t6.cpi_code and t5.organ_code=tp.organ_code and t5.policy_date between :p1 and :p2 and t6.net_flag=:netFlag and t5.organ_code like :orgCode and t5.prod_name like :prdCode ) as sum_policy_fee "
			+ "from t_policy tp, t_policy_dtl tpd, t_staff ts, t_bank_code tbc "
			+ "where tp.policy_no=tpd.policy_no and tpd.holder_card_num=ts.id_card and tp.bank_code=tbc.cpi_code and tp.fee_frequency like :toPerm "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tbc.net_flag=:netFlag "
			+ "and tp.prod_name like :prdCode "
			+ "group by tp.organ_name "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<StaffModel> getStaffCountWithPolicyDate(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm);
}