package com.gdpost.web.dao.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.component.TuiBaoDtlModel;

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
public interface TuiBaoDtlModelDAO extends JpaRepository<TuiBaoDtlModel, String>, JpaSpecificationExecutor<TuiBaoDtlModel> {
	
	@Query(name="getAllTuiBaoWarningDetailWithBankCode",
			value="select itp.id, itp.organ_name, itp.policy_no, itp.policy_date, itp.total_fee as policy_fee, itp.prod_name, itcr.cs_no, itcr.full_cs_code as cs_code, itcr.cs_date, itp.perm, itp.duration, "
			+ "(case itp.attached_flag when \"1\" then 0 else itcr.money end) as cs_fee, itp.holder, itbc.net_flag, itbc.full_name as bank_name, itbc.sel_bank_code as sel_bank_code, itp.staff_flag "
			+ "from t_policy itp, t_cs_report itcr, t_bank_code itbc "
			+ "where itp.policy_no=itcr.policy_no and itp.attached_flag=0 and itp.bank_code=itbc.cpi_code and itcr.cs_code=\"CT\" and itp.cs_flag<>1 "
			+ "and itp.organ_code like :orgCode "
			+ "and itp.policy_date between :p1 and :p2 "
			+ "and itcr.cs_date between :c1 and :c2 "
			+ "and itp.duration >= :duration "
			+ "and itp.prod_code like :prdCode "
			+ "and itp.bank_name like :bankName "
			+ "and itbc.net_flag like :netFlag "
			+ "and itp.fee_frequency like :toPerm "
			+ "and itp.staff_flag like :staffFlag "
			+ "order by itp.organ_code, itp.policy_no;",
			nativeQuery=true)
	List<TuiBaoDtlModel> getAllTuiBaoWarningDetailWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("c1")String csd1, @Param("c2")String csd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("duration")Integer duration);
	
	@Query(name="getAllTuiBaoCsDetailWithBankCode",
			value="select itp.id, itp.organ_name, itp.policy_no, itp.policy_date, itp.total_fee as policy_fee, itp.prod_name, itcr.cs_no, itcr.full_cs_code as cs_code, itcr.cs_date, itp.perm, itp.duration, "
			+ "(case itp.attached_flag when \"1\" then 0 else itcr.money end) as cs_fee, itp.holder, itbc.net_flag, itbc.full_name as bank_name, itbc.sel_bank_code as sel_bank_code, itp.staff_flag "
			+ "from t_policy itp, t_cs_report itcr, t_bank_code itbc "
			+ "where itp.policy_no=itcr.policy_no and itp.attached_flag=0 and itp.bank_code=itbc.cpi_code and itcr.cs_code=\"CT\" and itp.cs_flag=1 "
			+ "and itp.organ_code like :orgCode "
			+ "and itp.policy_date between :p1 and :p2 "
			+ "and itcr.cs_date between :c1 and :c2 "
			+ "and itp.duration >= :duration "
			+ "and itp.prod_code like :prdCode "
			+ "and itp.bank_name like :bankName "
			+ "and itbc.net_flag like :netFlag "
			+ "and itp.fee_frequency like :toPerm "
			+ "and itp.staff_flag like :staffFlag "
			+ "order by itp.organ_code, itp.policy_no;",
			nativeQuery=true)
	List<TuiBaoDtlModel> getAllTuiBaoCsDetailWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("c1")String csd1, @Param("c2")String csd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName, @Param("duration")Integer duration);
	
}