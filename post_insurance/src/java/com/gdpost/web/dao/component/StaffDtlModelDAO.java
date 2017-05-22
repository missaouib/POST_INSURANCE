package com.gdpost.web.dao.component;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.component.StaffDtlModel;

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
public interface StaffDtlModelDAO extends JpaRepository<StaffDtlModel, String>, JpaSpecificationExecutor<StaffDtlModel> {
	
	@Query(name="getProvAllStaffDetailWithBankCode",
			value="select tp.id, tp.holder, tp.organ_name, tp.policy_no, tp.policy_date, tp.total_fee as policy_fee, tp.prod_name, tp.fee_frequency, tp.perm, tbc.name as bank_name, tbc.net_flag "
			+ "from t_policy tp, t_policy_dtl tpd, t_staff ts, t_bank_code tbc "
			+ "where tp.cs_flag<>1 and tp.policy_no=tpd.policy_no and tpd.holder_card_num=ts.id_card and tp.bank_code=tbc.cpi_code and tp.fee_frequency like :toPerm "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tbc.net_flag like :netFlag "
			+ "and tp.prod_name like :prdCode "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<StaffDtlModel> getProvAllStaffDetailWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm);
	
}