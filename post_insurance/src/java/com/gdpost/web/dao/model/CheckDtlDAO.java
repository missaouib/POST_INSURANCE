package com.gdpost.web.dao.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.component.CheckModel;

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
public interface CheckDtlDAO extends JpaRepository<CheckModel, String>, JpaSpecificationExecutor<CheckModel> {
	
	@Query(name="getCheckWriteStatDtl",
			value="select distinct it1.organ_code, it1.organ_name, " + 
					"it1.policy_no, it2.check_batch,it2.need_fix,it2.key_info,it2.fix_status,it2.fix_desc, it2.checker, it1.holder, it1.bank_name, cast(aes_decrypt(unhex(tpd.holder_MOBILE), 'GDPost') as char(100)) as holder_mobile , cast(aes_decrypt(unhex(tpd.holder_phone), 'GDPost') as char(100)) as holder_phone " + 
					"from t_policy it1, t_policy_dtl tpd, t_check_write it2 where it1.policy_no=tpd.policy_no and it1.policy_no=it2.policy_no and it2.need_fix=\"要整改\"  " + 
					"and it1.policy_date between :pd1 and :pd2  " + 
					"and it1.policy_no like \"8644%\"  " + 
					"and it1.organ_code like :orgCode  " + 
					"and it1.attached_flag=0;",
			nativeQuery=true)
	List<CheckModel> getCheckWriteStatDtl(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2);
	
	@Query(name="getCheckRecordStatDtl",
			value="select distinct it1.organ_code, it1.organ_name," + 
					"it1.policy_no, it2.check_batch,it2.need_fix,it2.key_info,it2.fix_status,it2.fix_desc, it2.checker, it1.holder, it1.bank_name, cast(aes_decrypt(unhex(tpd.holder_MOBILE), 'GDPost') as char(100)) as holder_mobile , cast(aes_decrypt(unhex(tpd.holder_phone), 'GDPost') as char(100)) as holder_phone " + 
					"from t_policy it1, t_policy_dtl tpd, t_check_record it2 where it1.policy_no=tpd.policy_no and it1.policy_no=it2.policy_no and it2.need_fix=\"要整改\"   " + 
					"and it1.policy_date between :pd1 and :pd2  " + 
					"and it1.policy_no like \"8644%\"  " + 
					"and it1.organ_code like :orgCode  " + 
					"and it1.attached_flag=0;",
			nativeQuery=true)
	List<CheckModel> getCheckRecordStatDtl(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2);
	
	@Query(name="getCheckTruthStatDtl",
			value="select distinct t1.organ_code, t1.organ_name,t1.policy_no, t2.check_batch,t2.need_fix,t2.checker,t2.key_info,t2.fix_status,t2.fix_desc, t1.holder, t1.bank_name, cast(aes_decrypt(unhex(tpd.holder_MOBILE), 'GDPost') as char(100)) as holder_mobile , cast(aes_decrypt(unhex(tpd.holder_phone), 'GDPost') as char(100)) as holder_phone " + 
					"from t_policy t1, t_policy_dtl tpd, t_check_write t2 where it1.policy_no=tpd.policy_no and t1.policy_no=t2.policy_no and t2.is_truth=true and t2.need_fix=\"要整改\"  " + 
					"and t1.attached_flag=0 and t1.organ_code like :orgCode and t1.policy_date between :pd1 and :pd2 ;",
			nativeQuery=true)
	List<CheckModel> getCheckTruthStatDtl(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2);
	
}