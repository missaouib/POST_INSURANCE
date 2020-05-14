package com.gdpost.web.dao.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.component.QyStatModel;

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
public interface QyStatDAO extends JpaRepository<QyStatModel, Long>, JpaSpecificationExecutor<QyStatModel> {
	
	@Query(name="getQyStatHbPrdRatio",
	value="select tp.prod_name as organ_name, " + 
			"sum(datediff(case when uw.client_receive_date is null then now() else uw.client_receive_date end,uw.sys_date)) as sumdays, " + 
			"count(uw.policy_no) as policy_counts, " + 
			"sum(case when datediff(uw.sign_date,uw.sys_date)<=5 then 1 else 0 END) as job5ds, " + 
			"sum(case when uw.client_receive_date is not null then 1 else 0 END) as huixiao_counts, " + 
			"sum(case when datediff(uw.client_receive_date,uw.bill_back_date)<=5 then 1 else 0 END) as huixiao5ds "
			+ "from t_policy tp, t_under_write uw "
			+ "where tp.policy_no=uw.policy_no and tp.attached_flag=0 and tp.cs_flag<>:csFlag "
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
List<QyStatModel> getQyStatHbPrdRatio(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);

@Query(name="getQYHBProdStatWithBankCode",
value="select tp.prod_name as organ_name, " + 
		"sum(datediff(case when uw.client_receive_date is null then now() else uw.client_receive_date end,uw.sys_date)) as sumdays, " + 
		"count(uw.policy_no) as policy_counts, " + 
		"sum(case when datediff(uw.sign_date,uw.sys_date)<=5 then 1 else 0 END) as job5ds, " + 
		"sum(case when uw.client_receive_date is not null then 1 else 0 END) as huixiao_counts, " + 
		"sum(case when datediff(uw.client_receive_date,uw.bill_back_date)<=5 then 1 else 0 END) as huixiao5ds "
	+ "from t_policy tp, t_under_write uw, t_bank_code tbc "
	+ "where tp.policy_no=uw.policy_no and tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>:csFlag "
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
List<QyStatModel> getQYHBProdStatWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);

@Query(name="getProvQYHBOrganStat",
value="select left(tp.organ_name, 2) as organ_name, " + 
		"sum(datediff(case when uw.client_receive_date is null then now() else uw.client_receive_date end,uw.sys_date)) as sumdays, " + 
		"count(uw.policy_no) as policy_counts, " + 
		"sum(case when datediff(uw.sign_date,uw.sys_date)<=5 then 1 else 0 END) as job5ds, " + 
		"sum(case when uw.client_receive_date is not null then 1 else 0 END) as huixiao_counts, " + 
		"sum(case when datediff(uw.client_receive_date,uw.bill_back_date)<=5 then 1 else 0 END) as huixiao5ds "
	+ "from t_policy tp, t_under_write uw "
	+ "where tp.policy_no=uw.policy_no and tp.attached_flag=0 tp.cs_flag<>:csFlag "
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
List<QyStatModel> getProvQYHBOrganStat(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);

@Query(name="getQYHBOrganStat",
value="select tp.organ_name as organ_name, " + 
		"sum(datediff(case when uw.client_receive_date is null then now() else uw.client_receive_date end,uw.sys_date)) as sumdays, " + 
		"count(uw.policy_no) as policy_counts, " + 
		"sum(case when datediff(uw.sign_date,uw.sys_date)<=5 then 1 else 0 END) as job5ds, " + 
		"sum(case when uw.client_receive_date is not null then 1 else 0 END) as huixiao_counts, " + 
		"sum(case when datediff(uw.client_receive_date,uw.bill_back_date)<=5 then 1 else 0 END) as huixiao5ds "
	+ "from t_policy tp, t_under_write uw "
	+ "where tp.policy_no=uw.policy_no and tp.cs_flag<>:csFlag and tp.attached_flag=0 "
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
List<QyStatModel> getQYHBOrganStat(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);

@Query(name="getQYHBOrganNetStat",
value="select tbc.name as organ_name, " + 
		"sum(datediff(case when uw.client_receive_date is null then now() else uw.client_receive_date end,uw.sys_date)) as sumdays, " + 
		"count(uw.policy_no) as policy_counts, " + 
		"sum(case when datediff(uw.sign_date,uw.sys_date)<=5 then 1 else 0 END) as job5ds, " + 
		"sum(case when uw.client_receive_date is not null then 1 else 0 END) as huixiao_counts, " + 
		"sum(case when datediff(uw.client_receive_date,uw.bill_back_date)<=5 then 1 else 0 END) as huixiao5ds "
	+ "from t_policy tp, t_under_write uw, t_bank_code tbc "
	+ "where tp.policy_no=uw.policy_no and tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>:csFlag "
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
List<QyStatModel> getQYHBOrganNetStat(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);

@Query(name="getProvQYHBOrganStatWithBankCode",
value="select left(tp.organ_name,2) as organ_name, " + 
		"sum(datediff(case when uw.client_receive_date is null then now() else uw.client_receive_date end,uw.sys_date)) as sumdays, " + 
		"count(uw.policy_no) as policy_counts, " + 
		"sum(case when datediff(uw.sign_date,uw.sys_date)<=5 then 1 else 0 END) as job5ds, " + 
		"sum(case when uw.client_receive_date is not null then 1 else 0 END) as huixiao_counts, " + 
		"sum(case when datediff(uw.client_receive_date,uw.bill_back_date)<=5 then 1 else 0 END) as huixiao5ds "
	+ "from t_policy tp, t_under_write uw, t_bank_code tbc "
	+ "where tp.policy_no=uw.policy_no and tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>:csFlag "
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
List<QyStatModel> getProvQYHBOrganStatWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);

@Query(name="getQYHBOrganStatWithBankCode",
value="select tp.organ_name as organ_name, " + 
		"sum(datediff(case when uw.client_receive_date is null then now() else uw.client_receive_date end,uw.sys_date)) as sumdays, " + 
		"count(uw.policy_no) as policy_counts, " + 
		"sum(case when datediff(uw.sign_date,uw.sys_date)<=5 then 1 else 0 END) as job5ds, " + 
		"sum(case when uw.client_receive_date is not null then 1 else 0 END) as huixiao_counts, " + 
		"sum(case when datediff(uw.client_receive_date,uw.bill_back_date)<=5 then 1 else 0 END) as huixiao5ds "
	+ "from t_policy tp, t_under_write uw, t_bank_code tbc "
	+ "where tp.policy_no=uw.policy_no and tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>:csFlag "
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
List<QyStatModel> getQYHBOrganStatWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);

@Query(name="getQYHBOrganNetStatWithBankCode",
value="select tbc.name as organ_name, " + 
		"sum(datediff(case when uw.client_receive_date is null then now() else uw.client_receive_date end,uw.sys_date)) as sumdays, " + 
		"count(uw.policy_no) as policy_counts, " + 
		"sum(case when datediff(uw.sign_date,uw.sys_date)<=5 then 1 else 0 END) as job5ds, " + 
		"sum(case when uw.client_receive_date is not null then 1 else 0 END) as huixiao_counts, " + 
		"sum(case when datediff(uw.client_receive_date,uw.bill_back_date)<=5 then 1 else 0 END) as huixiao5ds "
	+ "from t_policy tp, t_under_write uw, t_bank_code tbc "
	+ "where tp.policy_no=uw.policy_no and tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>:csFlag "
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
List<QyStatModel> getQYHBOrganNetStatWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);


@Query(name="getQYHBFeeTypeStat",
value="select tp.fee_frequency as organ_name, " + 
		"sum(datediff(case when uw.client_receive_date is null then now() else uw.client_receive_date end,uw.sys_date)) as sumdays, " + 
		"count(uw.policy_no) as policy_counts, " + 
		"sum(case when datediff(uw.sign_date,uw.sys_date)<=5 then 1 else 0 END) as job5ds, " + 
		"sum(case when uw.client_receive_date is not null then 1 else 0 END) as huixiao_counts, " + 
		"sum(case when datediff(uw.client_receive_date,uw.bill_back_date)<=5 then 1 else 0 END) as huixiao5ds "
	+ "from t_policy tp, t_under_write uw "
	+ "where tp.policy_no=uw.policy_no and tp.cs_flag<>:csFlag and tp.attached_flag=0 "
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
List<QyStatModel> getQYHBFeeTypeStat(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);

@Query(name="getQYHBFeeTypeStatWithBankCode",
value="select tp.fee_frequency as organ_name, " + 
		"sum(datediff(case when uw.client_receive_date is null then now() else uw.client_receive_date end,uw.sys_date)) as sumdays, " + 
		"count(uw.policy_no) as policy_counts, " + 
		"sum(case when datediff(uw.sign_date,uw.sys_date)<=5 then 1 else 0 END) as job5ds, " + 
		"sum(case when uw.client_receive_date is not null then 1 else 0 END) as huixiao_counts, " + 
		"sum(case when datediff(uw.client_receive_date,uw.bill_back_date)<=5 then 1 else 0 END) as huixiao5ds "
	+ "from t_policy tp, t_under_write uw, t_bank_code tbc "
	+ "where tp.policy_no=uw.policy_no and tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>:csFlag "
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
List<QyStatModel> getQYHBFeeTypeStatWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("csFlag")String csFlag, @Param("saleType")String saleType, @Param("status")String status, @Param("duration")Integer duration);

}