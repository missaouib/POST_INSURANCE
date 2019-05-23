package com.gdpost.web.dao.model;

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
 * @see com.gdpost.web.entity.insurance.ConservationDtl
 * @author MyEclipse Persistence Tools
 */
public interface TuiBaoModelDAO extends JpaRepository<TuiBaoModel, String>, JpaSpecificationExecutor<TuiBaoModel> {
	
	@Query(name="getProvAllCityTuiBaoWarning",
			value="select left(tp.organ_name,2) as organ_name,sum(tp.total_fee) as sum_policy_fee, "
        + "t2.policy_fee as policy_fee, "
        + "t3.sum_cs_fee as sum_cs_fee "
        + "from t_policy tp "
        + "left JOIN "
        + "(select left(itp.organ_name,2) as organ_name,sum(itp.total_fee) as policy_fee "
        + "from t_policy itp, t_cs_report itcr "
        + "where itp.organ_code<>\"86440001\" and itp.attached_flag=0 and itp.policy_no=itcr.policy_no and itcr.cs_code=\"CT\" and itp.cs_flag<>1 "
        + "and itp.policy_date between :p1 and :p2 "
        + "and itcr.cs_date between :c1 and :c2  "
        + "and itp.duration >= :duration  "
        + "and itp.prod_code like :prdCode  "
        + "and itp.fee_frequency like :toPerm "
        + "and itp.staff_flag like :staffFlag "
        + "group by left(itp.organ_name,2) ) t2 "
        + "on t2.organ_name=left(tp.organ_name,2) "
        + "left join "
        + "(select left(itp.organ_name,2) as organ_name,sum(itcr.money) as sum_cs_fee "
        + "from t_policy itp, t_cs_report itcr "
        + "where itp.organ_code<>\"86440001\" and itp.policy_no=itcr.policy_no and itcr.cs_code=\"CT\" and itp.cs_flag<>1 and itp.attached_flag=0 "
        + "and itp.policy_date between :p1 and :p2 "
        + "and itp.duration >= :duration  "
        + "and itcr.cs_date between :c1 and :c2 "
        + "and itp.prod_code like :prdCode "
        + "and itp.fee_frequency like :toPerm "
        + "and itp.staff_flag like :staffFlag "
        + "group by left(itp.organ_name,2) ) t3 "
        + "on t3.organ_name=left(tp.organ_name,2) "
        + "where tp.organ_code<>\"86440001\" and tp.attached_flag=0 and tp.cs_flag<>1 "
        + "and tp.policy_date between :p1 and :p2 "
        + "and tp.duration >= :duration "
        + "and tp.organ_code like :orgCode "
        + "and tp.prod_code like :prdCode "
        + "and tp.fee_frequency like :toPerm "
        + "and tp.staff_flag like :staffFlag "
        + "group by left(tp.organ_name,2) "
        + "order by tp.organ_code;",
			nativeQuery=true)
	List<TuiBaoModel> getProvAllCityTuiBaoWarning(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("c1")String csd1, @Param("c2")String csd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("duration")Integer duration);
	
	@Query(name="getProvAllCityTuiBaoWarningWithBankCode",
			value="select left(tp.organ_name,2) as organ_name,sum(tp.total_fee) as sum_policy_fee, "
        + "t2.policy_fee as policy_fee, "
        + "t3.sum_cs_fee as sum_cs_fee "
        + "from t_bank_code tbc, t_policy tp "
        + "left join "
        + "(select left(itp.organ_name,2) as organ_name,sum(itp.total_fee) as policy_fee "
        + "from t_policy itp, t_cs_report itcr, t_bank_code itbc "
        + "where itp.organ_code<>\"86440001\" and itp.attached_flag=0 and itp.policy_no=itcr.policy_no and itp.bank_code=itbc.cpi_code and itcr.cs_code=\"CT\" and itp.cs_flag<>1  "
        + "and itp.policy_date between :p1 and :p2 "
        + "and itcr.cs_date between :c1 and :c2 "
        + "and itp.duration >= :duration  "
        + "and itp.prod_code like :prdCode "
        + "and itbc.net_flag=:netFlag "
        + "and itp.fee_frequency like :toPerm "
        + "and itp.staff_flag like :staffFlag "
        + "group by left(itp.organ_name,2) ) t2 "
        + "on t2.organ_name=LEFT(tp.organ_name,2) "
        + "left join "
        + "(select left(itp.organ_name,2) as organ_name, sum(itcr.money) as sum_cs_fee "
        + "from t_policy itp, t_cs_report itcr, t_bank_code itbc "
        + "where itp.organ_code<>\"86440001\" and itp.policy_no=itcr.policy_no and itp.bank_code=itbc.cpi_code and itcr.cs_code=\"CT\" and itp.cs_flag<>1 and itp.attached_flag=0 "
        + "and itp.policy_date between :p1 and :p2 "
        + "and itcr.cs_date between :c1 and :c2 "
        + "and itp.duration >= :duration  "
        + "and itp.prod_code like :prdCode "
        + "and itbc.net_flag=:netFlag "
        + "and itp.fee_frequency like :toPerm "
        + "and itp.staff_flag like :staffFlag "
        + "group by left(itp.organ_name,2) ) t3 "
        + "on t3.organ_name=LEFT(tp.organ_name,2) "
        + "where tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tbc.net_flag=:netFlag and tp.organ_code<>\"86440001\" and tp.cs_flag<>1 "
        + "and tp.policy_date between :p1 and :p2 "
        + "and tp.duration >= :duration "
        + "and tp.organ_code like :orgCode "
        + "and tp.prod_code like  :prdCode  "
        + "and tp.fee_frequency like :toPerm "
        + "and tp.staff_flag like :staffFlag "
        + "group by left(tp.organ_name,2) "
        + "order by tp.organ_code;",
			nativeQuery=true)
	List<TuiBaoModel> getProvAllCityTuiBaoWarningWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("c1")String csd1, @Param("c2")String csd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("duration")Integer duration);
	
	@Query(name="getTuiBaoWarningWithPolicyDateAndCsDateNoBankCode",
			value="select tp.organ_name,sum(tp.total_fee) as sum_policy_fee, "
			+ "(select sum(itp.total_fee) as policy_fee "
			+ "from t_policy itp, t_cs_report itcr "
			+ "where itp.policy_no=itcr.policy_no and itp.attached_flag=0 and itcr.cs_code=\"CT\" and itp.cs_flag<>1 "
			+ "and itp.organ_code=tp.organ_code "
			+ "and itp.policy_date between :p1 and :p2 "
			+ "and itcr.cs_date between :c1 and :c2 "
			+ "and itp.duration >= :duration  "
			+ "and itp.prod_code like :prdCode "
			+ "and itp.fee_frequency like :toPerm "
			+ "and itp.staff_flag like :staffFlag ) as policy_fee, "
			+ "(select sum(itcr.money) "
			+ "from t_policy itp, t_cs_report itcr "
			+ "where itp.policy_no=itcr.policy_no and itcr.cs_code=\"CT\" and itp.cs_flag<>1 and itp.attached_flag=0 "
			+ "and itp.organ_code=tp.organ_code "
			+ "and itp.policy_date between :p1 and :p2 "
			+ "and itcr.cs_date between :c1 and :c2 "
			+ "and itp.duration >= :duration  "
			+ "and itp.prod_code like :prdCode "
			+ "and itp.fee_frequency like :toPerm "
			+ "and itp.staff_flag like :staffFlag ) as sum_cs_fee "
			+ "from t_policy tp "
			+ "where tp.cs_flag<>1 and tp.attached_flag=0 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.duration >= :duration "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_code like :prdCode "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "group by tp.organ_name "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<TuiBaoModel> getTuiBaoWarningWithPolicyDateAndCsDateNoBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("c1")String csd1, @Param("c2")String csd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("duration")Integer duration);
	
	@Query(name="getTuiBaoWarningWithPolicyDateAndCsDate",
			value="select tp.organ_name,sum(tp.total_fee) as sum_policy_fee, "
			+ "(select sum(itp.total_fee) as policy_fee "
			+ "from t_policy itp, t_cs_report itcr, t_bank_code itbc "
			+ "where itp.policy_no=itcr.policy_no and itp.attached_flag=0 and itp.bank_code=itbc.cpi_code and itcr.cs_code=\"CT\" and itp.cs_flag<>1 "
			+ "and itp.organ_code=tp.organ_code "
			+ "and itp.policy_date between :p1 and :p2 "
			+ "and itcr.cs_date between :c1 and :c2 "
			+ "and itp.duration >= :duration  "
			+ "and itp.prod_code like :prdCode "
			+ "and itbc.net_flag=:netFlag "
			+ "and itp.fee_frequency like :toPerm "
			+ "and itp.staff_flag like :staffFlag ) as policy_fee, "
			+ "(select sum(itcr.money) "
			+ "from t_policy itp, t_cs_report itcr, t_bank_code itbc "
			+ "where itp.policy_no=itcr.policy_no and itp.bank_code=itbc.cpi_code and itcr.cs_code=\"CT\" and itp.cs_flag<>1 and itp.attached_flag=0 "
			+ "and itp.organ_code=tp.organ_code "
			+ "and itp.policy_date between :p1 and :p2 "
			+ "and itcr.cs_date between :c1 and :c2 "
			+ "and itp.duration >= :duration  "
			+ "and itp.prod_code like :prdCode "
			+ "and itbc.net_flag=:netFlag "
			+ "and itp.fee_frequency like :toPerm "
			+ "and itp.staff_flag like :staffFlag ) as sum_cs_fee "
			+ "from t_policy tp, t_bank_code tbc "
			+ "where tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>1 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.duration >= :duration "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_code like :prdCode "
			+ "and tbc.net_flag=:netFlag "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "group by tp.organ_name "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<TuiBaoModel> getTuiBaoWarningWithPolicyDateAndCsDate(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("c1")String csd1, @Param("c2")String csd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("duration")Integer duration);
	
	@Query(name="getNetTuiBaoWarningWithPolicyDateAndCsDate",
			value="select tp.bank_name as organ_name,sum(tp.total_fee) as sum_policy_fee, "
					+ "(select sum(itp.total_fee) as policy_fee "
					+ "from t_policy itp, t_cs_report itcr "
					+ "where itp.policy_no=itcr.policy_no and itp.attached_flag=0 and itcr.cs_code=\"CT\" and itp.cs_flag<>1 "
					+ "and itp.bank_name=tp.bank_name "
					+ "and itp.policy_date between :p1 and :p2 "
					+ "and itcr.cs_date between :c1 and :c2 "
					+ "and itp.duration >= :duration  "
					+ "and itp.prod_code like :prdCode "
					+ "and itp.fee_frequency like :toPerm "
					+ "and itp.staff_flag like :staffFlag ) as policy_fee, "
					+ "(select sum(itcr.money) "
					+ "from t_policy itp, t_cs_report itcr "
					+ "where itp.policy_no=itcr.policy_no and itcr.cs_code=\"CT\" and itp.cs_flag<>1 and itp.attached_flag=0 "
					+ "and itp.bank_name=tp.bank_name "
					+ "and itp.policy_date between :p1 and :p2 "
					+ "and itcr.cs_date between :c1 and :c2 "
					+ "and itp.duration >= :duration  "
					+ "and itp.prod_code like :prdCode "
					+ "and itp.fee_frequency like :toPerm "
					+ "and itp.staff_flag like :staffFlag ) as sum_cs_fee "
					+ "from t_policy tp "
					+ "where tp.cs_flag<>1 and tp.attached_flag=0 "
					+ "and tp.policy_date between :p1 and :p2 "
					+ "and tp.duration >= :duration "
					+ "and tp.organ_code like :orgCode "
					+ "and tp.bank_name like :bankName "
					+ "and tp.prod_code like :prdCode "
					+ "and tp.fee_frequency like :toPerm "
					+ "and tp.staff_flag like :staffFlag "
					+ "group by tp.bank_name "
					+ "order by tp.organ_code;",
			nativeQuery=true)
	List<TuiBaoModel> getNetTuiBaoWarningWithPolicyDateAndCsDate(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("c1")String csd1, @Param("c2")String csd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("duration")Integer duration);
	
	
	@Query(name="getNetTuiBaoWarningWithPolicyDateAndCsDate",
			value="select tp.bank_name as organ_name,sum(tp.total_fee) as sum_policy_fee, "
					+ "(select sum(itp.total_fee) as policy_fee "
					+ "from t_policy itp, t_cs_report itcr, t_bank_code itbc "
					+ "where itp.policy_no=itcr.policy_no and itp.bank_code=itbc.cpi_code and itp.attached_flag=0 and itcr.cs_code=\"CT\" and itp.cs_flag<>1 "
					+ "and itp.bank_name=tp.bank_name "
					+ "and itp.policy_date between :p1 and :p2 "
					+ "and itcr.cs_date between :c1 and :c2 "
					+ "and itbc.net_flag=:netFlag "
					+ "and itp.duration >= :duration  "
					+ "and itp.prod_code like :prdCode "
					+ "and itp.fee_frequency like :toPerm "
					+ "and itp.staff_flag like :staffFlag ) as policy_fee, "
					+ "(select sum(itcr.money) "
					+ "from t_policy itp, t_cs_report itcr, t_bank_code itbc "
					+ "where itp.policy_no=itcr.policy_no and itp.bank_code=itbc.cpi_code and itcr.cs_code=\"CT\" and itp.cs_flag<>1 and itp.attached_flag=0 "
					+ "and itp.bank_name=tp.bank_name "
					+ "and itp.policy_date between :p1 and :p2 "
					+ "and itcr.cs_date between :c1 and :c2 "
					+ "and itp.duration >= :duration  "
					+ "and itp.prod_code like :prdCode "
					+ "and itbc.net_flag=:netFlag "
					+ "and itp.fee_frequency like :toPerm "
					+ "and itp.staff_flag like :staffFlag ) as sum_cs_fee "
					+ "from t_policy tp, t_bank_code tbc "
					+ "where tp.bank_code=tbc.cpi_code and tp.cs_flag<>1 and tp.attached_flag=0 "
					+ "and tp.policy_date between :p1 and :p2 "
					+ "and tp.duration >= :duration "
					+ "and tp.organ_code like :orgCode "
					+ "and tp.bank_name like :bankName "
					+ "and tp.prod_code like :prdCode "
					+ "and tbc.net_flag=:netFlag "
					+ "and tp.fee_frequency like :toPerm "
					+ "and tp.staff_flag like :staffFlag "
					+ "group by tp.bank_name "
					+ "order by tp.organ_code;",
			nativeQuery=true)
	List<TuiBaoModel> getNetTuiBaoWarningWithPolicyDateAndCsDate(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("c1")String csd1, @Param("c2")String csd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("duration")Integer duration);
	
}