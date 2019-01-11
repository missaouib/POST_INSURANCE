package com.gdpost.web.dao.component;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.component.PolicyDataModel;

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
public interface PolicyDataDAO extends JpaRepository<PolicyDataModel, Long>, JpaSpecificationExecutor<PolicyDataModel> {
	
	@Query(name="getPolicyDate",
			value="select tp.id,tp.policy_no,tp.form_no,tp.holder, "
					+ "cast(aes_decrypt(unhex(tpd.holder_phone), 'GDPost') as char(100)) as holder_phone, "
					+ "cast(aes_decrypt(unhex(tpd.holder_mobile), 'GDPost') as char(100)) as holder_mobile, "
					+ "tpd.holder_card_type, "
					+ "cast(aes_decrypt(unhex(tpd.holder_card_num), 'GDPost') as char(100)) as holder_card_num, "
					+ "cast(aes_decrypt(unhex(tpd.insured), 'GDPost') as char(100)) as insured, "
					+ "'' as insured_phone,'' as insured_card_num, "
					+ "tp.prod_code,tp.prod_name,tp.policy_fee,tp.insured_amount,tp.fee_frequency, "
					+ "tp.perm,tp.policy_date,tp.plicy_valid_date,tp.status,tp.bank_code,'银行转账' as fee_type, "
					+ "'' as bank_account, tp.cs_flag, case when uw.policy_no is null then 'N' else 'Y' end as uw_flag, tpd.duration "
					+ "from t_policy tp "
					+ "left join t_policy_dtl tpd on tp.policy_no=tpd.policy_no "
					+ "left join t_under_write uw on tp.policy_no=uw.policy_no "
					+ "where tp.policy_date between :pd1 and :pd2 and tp.organ_code like :orgCode "
					+ "and tp.organ_code<>'86440001' "
					+ "order by tp.organ_code, tp.policy_date; ",
			nativeQuery=true)
	List<PolicyDataModel> getPolicyDate(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2);
}