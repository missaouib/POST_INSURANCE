package com.gdpost.web.dao.component;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.component.TuiBaoModel;

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
public interface TuiBaoModelDAO extends JpaRepository<TuiBaoModel, String>, JpaSpecificationExecutor<TuiBaoModel> {
	
	@Query(name="getProvTuiBaoWarningWithPolicyDateAndCsDateNoBankCode",
			value="select left(tp.organ_name,2) as organ_name,sum(policy_fee) as policy_fee,"
			+ "(select sum(t4.policy_fee) from t_policy t4 where t4.policy_date between :p1 and :p2 and left(t4.organ_name,2)=left(tp.organ_name,2)) as sum_policy_fee "
			+ "from t_policy tp, t_cs_report csr "
			+ "where tp.policy_no=csr.policy_no and csr.cs_code=\"CT\" and csr.cs_date-tp.policy_date>15 "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and csr.cs_date between :c1 and :c2 "
			+ "and tp.prod_code like :prdCode "
			+ "group by left(tp.organ_name,2) "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<TuiBaoModel> getProvTuiBaoWarningWithPolicyDateAndCsDateNoBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("c1")String csd1, @Param("c2")String csd2, @Param("prdCode")String prdCode);
	
	@Query(name="getProvTuiBaoWarningWithPolicyDateAndCsDate",
			value="select left(tp.organ_name,2) as organ_name,sum(policy_fee) as policy_fee,"
			+ "(select sum(t4.policy_fee) from t_policy t4, t_bank_code t5  where t4.bank_code=t5.cpi_code and left(t4.organ_name,2)=left(tp.organ_name,2) and t4.policy_date between :p1 and :p2 and t5.net_flag=:netFlag and t4.organ_code like :orgCode and t4.prod_code like :prdCode ) as sum_policy_fee "
			+ "from t_policy tp, t_cs_report csr,t_bank_code tbc "
			+ "where tp.policy_no=csr.policy_no and tp.bank_code=tbc.cpi_code and csr.cs_code=\"CT\" and csr.cs_date-tp.policy_date>15 "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and csr.cs_date between :c1 and :c2 "
			+ "and tbc.net_flag=:netFlag "
			+ "and tp.prod_code like :prdCode "
			+ "group by left(tp.organ_name,2) "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<TuiBaoModel> getProvTuiBaoWarningWithPolicyDateAndCsDate(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("c1")String csd1, @Param("c2")String csd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode);
	
	@Query(name="getTuiBaoWarningWithPolicyDateAndCsDateNoBankCode",
			value="select tp.organ_name as organ_name,sum(policy_fee) as policy_fee,"
			+ "(select sum(t4.policy_fee) from t_policy t4 where t4.policy_date between :p1 and :p2 and t4.organ_code=tp.organ_code) as sum_policy_fee "
			+ "from t_policy tp, t_cs_report csr "
			+ "where tp.policy_no=csr.policy_no and csr.cs_code=\"CT\" and csr.cs_date-tp.policy_date>15 "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and csr.cs_date between :c1 and :c2 "
			+ "and tp.prod_code like :prdCode "
			+ "group by tp.organ_name "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<TuiBaoModel> getTuiBaoWarningWithPolicyDateAndCsDateNoBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("c1")String csd1, @Param("c2")String csd2, @Param("prdCode")String prdCode);
	
	@Query(name="getTuiBaoWarningWithPolicyDateAndCsDate",
			value="select tp.organ_name as organ_name,sum(policy_fee) as policy_fee,"
			+ "(select sum(t4.policy_fee) from t_policy t4, t_bank_code t5  where t4.bank_code=t5.cpi_code and t4.organ_code=tp.organ_code and t4.policy_date between :p1 and :p2 and t5.net_flag=:netFlag and t4.organ_code like :orgCode and t4.prod_code like :prdCode ) as sum_policy_fee "
			+ "from t_policy tp, t_cs_report csr,t_bank_code tbc "
			+ "where tp.policy_no=csr.policy_no and tp.bank_code=tbc.cpi_code and csr.cs_code=\"CT\" and csr.cs_date-tp.policy_date>15 "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and csr.cs_date between :c1 and :c2 "
			+ "and tbc.net_flag=:netFlag "
			+ "and tp.prod_code like :prdCode "
			+ "group by tp.organ_name "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<TuiBaoModel> getTuiBaoWarningWithPolicyDateAndCsDate(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("c1")String csd1, @Param("c2")String csd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode);
}