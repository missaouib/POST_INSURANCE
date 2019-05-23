package com.gdpost.web.dao.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.component.QyCheckModel;

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
public interface CheckStasticsDAO extends JpaRepository<QyCheckModel, String>, JpaSpecificationExecutor<QyCheckModel> {
	
	@Query(name="getCheckWriteCityStat",
			value="select left(t1.organ_code,6) as organ_code," + 
					"count(distinct t1.policy_no) as policy_counts," + 
					"count(distinct t2.policy_no) as check_counts," + 
					"sum(distinct t3.errCounts) as err_counts " + 
					"from t_policy t1 left join t_check_write t2 on t1.policy_no=t2.policy_no " + 
					"left join ( " + 
					"select left(it1.organ_code,6) as organ_code, " + 
					"count(distinct it2.policy_no) as errCounts " + 
					"from t_policy it1 " + 
					"left join t_check_write it2 on it1.policy_no=it2.policy_no and it2.need_fix=\"要整改\"  " + 
					"where it1.policy_date between :pd1 and :pd2  " + 
					"and it1.policy_no like \"8644%\"  " + 
					"and it1.attached_flag=0  " + 
					"group by left(it1.organ_code,6) " + 
					") t3 on left(t1.organ_code,6)=t3.organ_code  " + 
					"where t1.policy_date between :pd1 and :pd2  " + 
					"and t1.policy_no like \"8644%\"  " + 
					"and t1.attached_flag=0  " + 
					"group by left(t1.organ_code,6);",
			nativeQuery=true)
	List<QyCheckModel> getCheckWriteCityStat(@Param("pd1")String pd1, @Param("pd2")String pd2);
	
	@Query(name="getCheckRecordCityStat",
			value="select left(t1.organ_code,6) as organ_code," + 
					"count(distinct t1.policy_no) as policy_counts," + 
					"count(distinct t2.policy_no) as check_counts," + 
					"sum(distinct t3.errCounts) as err_counts " + 
					"from t_policy t1 left join t_check_record t2 on t1.policy_no=t2.policy_no " + 
					"left join ( " + 
					"select left(it1.organ_code,6) as organ_code, " + 
					"count(distinct it2.policy_no) as errCounts " + 
					"from t_policy it1 " + 
					"left join t_check_record it2 on it1.policy_no=it2.policy_no and it2.need_fix=\"要整改\"  " + 
					"where it1.policy_date between :pd1 and :pd2  " + 
					"and it1.policy_no like \"8644%\"  " + 
					"and it1.attached_flag=0  " + 
					"group by left(it1.organ_code,6) " + 
					") t3 on left(t1.organ_code,6)=t3.organ_code  " + 
					"where t1.policy_date between :pd1 and :pd2  " + 
					"and t1.policy_no like \"8644%\"  " + 
					"and t1.attached_flag=0  " + 
					"group by left(t1.organ_code,6);",
			nativeQuery=true)
	List<QyCheckModel> getCheckRecordCityStat(@Param("pd1")String pd1, @Param("pd2")String pd2);
	
	@Query(name="getCheckWriteAreaStat",
			value="select t1.organ_code as organ_code," + 
					"count(distinct t1.policy_no) as policy_counts," + 
					"count(distinct t2.policy_no) as check_counts," + 
					"sum(distinct t3.errCounts) as err_counts " + 
					"from t_policy t1 left join t_check_write t2 on t1.policy_no=t2.policy_no " + 
					"left join ( " + 
					"select it1.organ_code as organ_code, " + 
					"count(distinct it2.policy_no) as errCounts " + 
					"from t_policy it1 " + 
					"left join t_check_write it2 on it1.policy_no=it2.policy_no and it2.need_fix=\"要整改\"  " + 
					"where it1.policy_date between :pd1 and :pd2  " + 
					"and it1.policy_no like :orgCode  " + 
					"and it1.attached_flag=0  " + 
					"group by it1.organ_code " + 
					") t3 on t1.organ_code=t3.organ_code  " + 
					"where t1.policy_date between :pd1 and :pd2  " + 
					"and t1.policy_no like :orgCode  " + 
					"and t1.attached_flag=0  " + 
					"group by t1.organ_code;",
			nativeQuery=true)
	List<QyCheckModel> getCheckWriteAreaStat(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2);
	
	@Query(name="getCheckRecordAreaStat",
			value="select t1.organ_code as organ_code," + 
					"count(distinct t1.policy_no) as policy_counts," + 
					"count(distinct t2.policy_no) as check_counts," + 
					"sum(distinct t3.errCounts) as err_counts " + 
					"from t_policy t1 left join t_check_record t2 on t1.policy_no=t2.policy_no " + 
					"left join ( " + 
					"select it1.organ_code as organ_code, " + 
					"count(distinct it2.policy_no) as errCounts " + 
					"from t_policy it1 " + 
					"left join t_check_record it2 on it1.policy_no=it2.policy_no and it2.need_fix=\"要整改\"  " + 
					"where it1.policy_date between :pd1 and :pd2  " + 
					"and it1.policy_no like :orgCode  " + 
					"and it1.attached_flag=0  " + 
					"group by it1.organ_code " + 
					") t3 on t1.organ_code=t3.organ_code  " + 
					"where t1.policy_date between :pd1 and :pd2  " + 
					"and t1.policy_no like :orgCode  " + 
					"and t1.attached_flag=0  " + 
					"group by t1.organ_code;",
			nativeQuery=true)
	List<QyCheckModel> getCheckRecordAreaStat(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2);
}