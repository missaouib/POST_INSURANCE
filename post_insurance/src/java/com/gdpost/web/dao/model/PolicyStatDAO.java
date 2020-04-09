package com.gdpost.web.dao.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.component.PolicyStatModel;

/**
 * A data access object (DAO) providing persistence and search support for
 * TConservationDtl entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see com.gdpost.web.entity.insurance.ConservationDtl
 * @author MyEclipse Persistence Tools
 */
public interface PolicyStatDAO extends JpaRepository<PolicyStatModel, Long>, JpaSpecificationExecutor<PolicyStatModel> {
	
	@Query(name="getPolicyDateProdStat",
			value="select tp.prod_name as stat_name,count(tp.id) as policy_count, sum(tp.total_fee)/10000 as policy_fee, SUM(case when tp.prod_code=\"120022\" then 1 else 0 end) as jzh_count, "
			+ "sum(case when tp.perm<TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d')) then (tp.policy_fee*perm) else (tp.policy_fee*(TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d'))+1)) end)/10000 as had_policy_fee "
			+ "from t_policy tp "
			+ "where tp.cs_flag<>:csFlag and tp.attached_flag=0 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.duration >= :duration "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_code like :prdCode "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "and tp.policy_no like :saleType "
			+ "and tp.status like :status "
			+ "group by tp.prod_name "
			+ "order by tp.prod_code;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateProdStat(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);
	
	@Query(name="getPolicyDateProdStatWithBankCode",
			value="select tp.prod_name as stat_name, count(tp.id) as policy_count, sum(tp.total_fee)/10000 as policy_fee, SUM(case when tp.prod_code=\"120022\" then 1 else 0 end) as jzh_count, "
					+ "sum(case when tp.perm<TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d')) then (tp.policy_fee*perm) else (tp.policy_fee*(TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d'))+1)) end)/10000 as had_policy_fee "
			+ "from t_policy tp, t_bank_code tbc "
			+ "where tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>:csFlag "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.duration >= :duration "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_code like :prdCode "
			+ "and tbc.net_flag=:netFlag "
			+ "and tp.bank_name like :bankName "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "and tp.policy_no like :saleType "
			+ "and tp.status like :status "
			+ "group by tp.prod_name "
			+ "order by tp.prod_code;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateProdStatWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);
	
	@Query(name="getProvPolicyDateOrganStat",
			value="select left(tp.organ_name, 2) as stat_name,count(tp.id) as policy_count, sum(tp.total_fee)/10000 as policy_fee, SUM(case when tp.prod_code=\"120022\" then 1 else 0 end) as jzh_count, "
					+ "sum(case when tp.perm<TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d')) then (tp.policy_fee*perm) else (tp.policy_fee*(TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d'))+1)) end)/10000 as had_policy_fee "
			+ "from t_policy tp "
			+ "where tp.cs_flag<>:csFlag and tp.attached_flag=0 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.duration >= :duration "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_code like :prdCode "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "and tp.policy_no like :saleType "
			+ "and tp.status like :status "
			+ "group by left(tp.organ_name, 2) "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<PolicyStatModel> getProvPolicyDateOrganStat(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);
	
	@Query(name="getPolicyDateOrganStat",
			value="select tp.organ_name as stat_name,count(tp.id) as policy_count, sum(tp.total_fee)/10000 as policy_fee, SUM(case when tp.prod_code=\"120022\" then 1 else 0 end) as jzh_count, "
					+ "sum(case when tp.perm<TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d')) then (tp.policy_fee*perm) else (tp.policy_fee*(TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d'))+1)) end)/10000 as had_policy_fee "
			+ "from t_policy tp "
			+ "where tp.cs_flag<>:csFlag and tp.attached_flag=0 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.duration >= :duration "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_code like :prdCode "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "and tp.policy_no like :saleType "
			+ "and tp.status like :status "
			+ "group by tp.organ_name "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateOrganStat(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);
	
	@Query(name="getPolicyDateOrganNetStat",
			value="select tbc.name as stat_name, count(tp.id) as policy_count, sum(tp.total_fee)/10000 as policy_fee, SUM(case when tp.prod_code=\"120022\" then 1 else 0 end) as jzh_count, "
					+ "sum(case when tp.perm<TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d')) then (tp.policy_fee*perm) else (tp.policy_fee*(TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d'))+1)) end)/10000 as had_policy_fee "
			+ "from t_policy tp, t_bank_code tbc "
			+ "where tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>:csFlag "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.duration >= :duration "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_code like :prdCode "
			+ "and tp.bank_name like :bankName "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "and tp.policy_no like :saleType "
			+ "and tp.status like :status "
			+ "group by tbc.name "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateOrganNetStat(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);
	
	@Query(name="getProvPolicyDateOrganStatWithBankCode",
			value="select left(tp.organ_name,2) as stat_name, count(tp.id) as policy_count, sum(tp.total_fee)/10000 as policy_fee, SUM(case when tp.prod_code=\"120022\" then 1 else 0 end) as jzh_count, "
					+ "sum(case when tp.perm<TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d')) then (tp.policy_fee*perm) else (tp.policy_fee*(TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d'))+1)) end)/10000 as had_policy_fee "
			+ "from t_policy tp, t_bank_code tbc "
			+ "where tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>:csFlag "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.duration >= :duration "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_code like :prdCode "
			+ "and tbc.net_flag=:netFlag "
			+ "and tp.bank_name like :bankName "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "and tp.policy_no like :saleType "
			+ "and tp.status like :status "
			+ "group by left(tp.organ_name,2) "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<PolicyStatModel> getProvPolicyDateOrganStatWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);
	
	@Query(name="getPolicyDateOrganStatWithBankCode",
			value="select tp.organ_name as stat_name, count(tp.id) as policy_count, sum(tp.total_fee)/10000 as policy_fee, SUM(case when tp.prod_code=\"120022\" then 1 else 0 end) as jzh_count, "
					+ "sum(case when tp.perm<TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d')) then (tp.policy_fee*perm) else (tp.policy_fee*(TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d'))+1)) end)/10000 as had_policy_fee "
			+ "from t_policy tp, t_bank_code tbc "
			+ "where tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>:csFlag "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.duration >= :duration "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_code like :prdCode "
			+ "and tbc.net_flag=:netFlag "
			+ "and tp.bank_name like :bankName "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "and tp.policy_no like :saleType "
			+ "and tp.status like :status "
			+ "group by tp.organ_name "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateOrganStatWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);
	
	@Query(name="getPolicyDateOrganNetStatWithBankCode",
			value="select tbc.name as stat_name, count(tp.id) as policy_count, sum(tp.total_fee)/10000 as policy_fee, SUM(case when tp.prod_code=\"120022\" then 1 else 0 end) as jzh_count, "
					+ "sum(case when tp.perm<TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d')) then (tp.policy_fee*perm) else (tp.policy_fee*(TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d'))+1)) end)/10000 as had_policy_fee "
			+ "from t_policy tp, t_bank_code tbc "
			+ "where tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>:csFlag "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.duration >= :duration "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_code like :prdCode "
			+ "and tbc.net_flag=:netFlag "
			+ "and tp.bank_name like :bankName "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "and tp.policy_no like :saleType "
			+ "and tp.status like :status "
			+ "group by tbc.name "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateOrganNetStatWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);
	
	
	@Query(name="getPolicyDateFeeTypeStat",
			value="select tp.fee_frequency as stat_name,count(tp.id) as policy_count, sum(tp.total_fee)/10000 as policy_fee, SUM(case when tp.prod_code=\"120022\" then 1 else 0 end) as jzh_count, "
					+ "sum(case when tp.perm<TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d')) then (tp.policy_fee*perm) else (tp.policy_fee*(TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d'))+1)) end)/10000 as had_policy_fee "
			+ "from t_policy tp "
			+ "where tp.cs_flag<>:csFlag and tp.attached_flag=0 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.duration >= :duration "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_code like :prdCode "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "and tp.policy_no like :saleType "
			+ "and tp.status like :status "
			+ "group by tp.fee_frequency "
			+ "order by tp.fee_frequency;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateFeeTypeStat(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);
	
	@Query(name="getPolicyDateFeeTypeStatWithBankCode",
			value="select tp.fee_frequency as stat_name, count(tp.id) as policy_count, sum(tp.total_fee)/10000 as policy_fee, SUM(case when tp.prod_code=\"120022\" then 1 else 0 end) as jzh_count, "
					+ "sum(case when tp.perm<TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d')) then (tp.policy_fee*perm) else (tp.policy_fee*(TIMESTAMPDIFF(YEAR,tp.policy_date,DATE_FORMAT(NOW(), '%Y-%m-%d'))+1)) end)/10000 as had_policy_fee "
			+ "from t_policy tp, t_bank_code tbc "
			+ "where tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>:csFlag "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.duration >= :duration "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_code like :prdCode "
			+ "and tbc.net_flag=:netFlag "
			+ "and tp.bank_name like :bankName "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "and tp.policy_no like :saleType "
			+ "and tp.status like :status "
			+ "group by tp.fee_frequency "
			+ "order by tp.fee_frequency;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateFeeTypeStatWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);
	
}