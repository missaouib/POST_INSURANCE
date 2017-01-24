package com.gdpost.web.dao.component;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.gdpost.web.entity.component.CommonModel;

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
public interface CommonModelDAO extends JpaRepository<CommonModel, Long>, JpaSpecificationExecutor<CommonModel> {
	@Query(value="select tp.organ_name as organ_name,sum(policy_fee) as policy_fee "
			+ "from t_policy tp, t_cs_report csr,t_bank_code tbc "
			+ "where tp.policy_no=csr.policy_no and csr.cs_date-tp.policy_date>15 and tp.bank_code=tbc.cpi_code "
			+ "and tp.organ_code like ?1 "
			+ "and tp.policy_date between ?2 and ?3 "
			+ "group by tp.organ_name;",
			nativeQuery=true)
	List<CommonModel> getTuiBaoWarningWithPolicyDate(String organCode, String pd1, String pd2);
	
	@Query(value="select tp.organ_name as organ_name,sum(policy_fee) as policy_fee "
			+ "from t_policy tp, t_cs_report csr,t_bank_code tbc "
			+ "where tp.policy_no=csr.policy_no and csr.cs_date-tp.policy_date>15 and tp.bank_code=tbc.cpi_code "
			+ "and tp.organ_code like ?1 "
			+ "and csr.cs_date between ?2 and ?3 "
			+ "group by tp.organ_name;",
			nativeQuery=true)
	List<CommonModel> getTuiBaoWarningWithCsDate(String organCode, String pd1, String pd2);
	
	@Query(value="select tp.organ_name as organ_name,sum(policy_fee) as policy_fee "
			+ "from t_policy tp, t_cs_report csr,t_bank_code tbc "
			+ "where tp.policy_no=csr.policy_no and csr.cs_date-tp.policy_date>15 and tp.bank_code=tbc.cpi_code "
			+ "and tp.organ_code like ?1 "
			+ "and tp.policy_date between ?2 and ?3 "
			+ "and csr.cs_date between ?4 and ?5 "
			+ "group by tp.organ_name;",
			nativeQuery=true)
	List<CommonModel> getTuiBaoWarningWithPolicyDateAndCsDate(String organCode, String pd1, String pd2, String csd1, String csd2);
}