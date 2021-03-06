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
	
	@Query(name="getCheckModelWriteStatDtl",
			value="select distinct it1.policy_no as policy_no, it1.organ_code as organ_code, it1.organ_name as organ_name, " + 
					"it2.check_batch as check_batch,it2.need_fix as need_fix,it2.key_info as key_info,"
					+ "it2.fix_status as fix_status,it2.fix_desc as fix_desc, it1.bank_name as bank_name, it1.holder as holder, cast(aes_decrypt(unhex(tpd.holder_MOBILE), 'GDPost') as char(100)) as holder_mobile , cast(aes_decrypt(unhex(tpd.holder_phone), 'GDPost') as char(100)) as holder_phone, it2.checker as checker " + 
					"from t_policy it1, t_policy_dtl tpd, t_check_write it2 where it1.policy_no=tpd.policy_no and it1.policy_no=it2.policy_no and it1.attached_flag=0 and it2.need_fix=\"要整改\"  " + 
					"and it1.policy_date between :pd1 and :pd2  " + 
					"and it1.policy_no like \"8644%\"  " + 
					"and it1.organ_code like :orgCode  " + 
					"and it1.duration>=:duration " + 
					"and it1.fee_frequency like :toPerm ;",
			nativeQuery=true)
	List<CheckModel> getCheckModelWriteStatDtl(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2, @Param("duration")Integer duration, @Param("toPerm")String toPerm);
	
	@Query(name="getCheckModelRecordStatDtl",
			value="select distinct it1.policy_no as policy_no, it1.organ_code as organ_code, it1.organ_name as organ_name," + 
					"it2.check_batch as check_batch,it2.need_fix as need_fix, it2.key_info as key_info, "
					+ "it2.fix_status as fix_status,it2.fix_desc as fix_desc, it1.bank_name, it1.holder as holder, cast(aes_decrypt(unhex(tpd.holder_MOBILE), 'GDPost') as char(100)) as holder_mobile , cast(aes_decrypt(unhex(tpd.holder_phone), 'GDPost') as char(100)) as holder_phone, it2.checker as checker " + 
					"from t_policy it1, t_policy_dtl tpd, t_check_record it2 where it1.policy_no=tpd.policy_no and it1.policy_no=it2.policy_no and it1.attached_flag=0 and it2.need_fix=\"要整改\" " + 
					"and it1.policy_date between :pd1 and :pd2  " + 
					"and it1.policy_no like \"8644%\"  " + 
					"and it1.organ_code like :orgCode  " + 
					"and it1.duration>=:duration " + 
					"and it1.fee_frequency like :toPerm ;",
			nativeQuery=true)
	List<CheckModel> getCheckModelRecordStatDtl(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2, @Param("duration")Integer duration, @Param("toPerm")String toPerm);
	
	@Query(name="getCheckModelTruthStatDtl",
			value="select distinct t1.policy_no as policy_no, t1.organ_code as organ_code, t1.organ_name as organ_name, "
					+ "t2.check_batch as check_batch,t2.need_fix as need_fix,t2.key_info as key_info, "
					+ "t2.fix_status as fix_status, t2.fix_desc as fix_desc, t1.bank_name as bank_name, t1.holder as holder, cast(aes_decrypt(unhex(tpd.holder_MOBILE), 'GDPost') as char(100)) as holder_mobile , cast(aes_decrypt(unhex(tpd.holder_phone), 'GDPost') as char(100)) as holder_phone, t2.checker as checker " + 
					"from t_policy t1, t_policy_dtl tpd, t_check_write t2, t_bank_code bc where t1.policy_no=tpd.policy_no and t1.policy_no=t2.policy_no and t1.bank_code=bc.cpi_code and t2.is_truth=true and t2.need_fix=\"要整改\"  " + 
					"and t1.attached_flag=0 and t1.duration>=:duration and t1.fee_frequency like :toPerm  and t1.organ_code like :orgCode and t1.policy_date between :pd1 and :pd2 and bc.net_flag like :netFlag ;",
			nativeQuery=true)
	List<CheckModel> getCheckModelTruthStatDtl(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2, @Param("duration")Integer duration, @Param("toPerm")String toPerm, @Param("netFlag")String netFlag);
	
}