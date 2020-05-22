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
					"count(distinct t2.policy_no) as check_counts, 0 as ontime_counts, " + 
					"sum(distinct t3.errCounts) as err_counts " + 
					"from t_policy t1 left join t_check_write t2 on t1.policy_no=t2.policy_no and t1.attached_flag=0 " + 
					"left join ( " + 
					"select left(it1.organ_code,6) as organ_code, " + 
					"count(distinct it2.policy_no) as errCounts " + 
					"from t_policy it1 " + 
					"left join t_check_write it2 on it1.policy_no=it2.policy_no and it1.attached_flag=0 and it2.need_fix=\"要整改\"  " + 
					"where it1.policy_date between :pd1 and :pd2  " + 
					"and it1.policy_no like \"8644%\"  " + 
					"and it1.duration >= :duration " + 
					"and it1.fee_frequency like :toPerm " + 
					"and it1.attached_flag=0  " + 
					"group by left(it1.organ_code,6) " + 
					") t3 on left(t1.organ_code,6)=t3.organ_code  " + 
					"where t1.policy_date between :pd1 and :pd2  " + 
					"and t1.policy_no like \"8644%\"  " + 
					"and t1.duration >= :duration " + 
					"and t1.fee_frequency like :toPerm " + 
					"and t1.attached_flag=0  " + 
					"group by left(t1.organ_code,6);",
			nativeQuery=true)
	List<QyCheckModel> getCheckWriteCityStat(@Param("pd1")String pd1, @Param("pd2")String pd2, @Param("duration")Integer duration, @Param("toPerm")String toPerm);
	
	@Query(name="getCheckRecordCityStat",
			value="select left(t1.organ_code,6) as organ_code," + 
					"count(distinct t1.policy_no) as policy_counts," + 
					"count(distinct t2.policy_no) as check_counts, 0 as ontime_counts, " + 
					"sum(distinct t3.errCounts) as err_counts " + 
					"from t_policy t1 left join t_check_record t2 on t1.policy_no=t2.policy_no and t1.attached_flag=0 " + 
					"left join ( " + 
					"select left(it1.organ_code,6) as organ_code, " + 
					"count(distinct it2.policy_no) as errCounts " + 
					"from t_policy it1 " + 
					"left join t_check_record it2 on it1.policy_no=it2.policy_no and it1.attached_flag=0 and it2.need_fix=\"要整改\"  " + 
					"where it1.policy_date between :pd1 and :pd2  " + 
					"and it1.policy_no like \"8644%\"  " + 
					"and it1.duration >= :duration " + 
					"and it1.fee_frequency like :toPerm " + 
					"and it1.attached_flag=0  " + 
					"group by left(it1.organ_code,6) " + 
					") t3 on left(t1.organ_code,6)=t3.organ_code  " + 
					"where t1.policy_date between :pd1 and :pd2  " + 
					"and t1.policy_no like \"8644%\"  " + 
					"and t1.duration >= :duration " + 
					"and t1.fee_frequency like :toPerm " + 
					"and t1.attached_flag=0  " + 
					"group by left(t1.organ_code,6);",
			nativeQuery=true)
	List<QyCheckModel> getCheckRecordCityStat(@Param("pd1")String pd1, @Param("pd2")String pd2, @Param("duration")Integer duration, @Param("toPerm")String toPerm);
	
	@Query(name="getCheckWriteAreaStat",
			value="select t1.organ_code as organ_code," + 
					"count(distinct t1.policy_no) as policy_counts," + 
					"count(distinct t2.policy_no) as check_counts, 0 as ontime_counts, " + 
					"sum(distinct t3.errCounts) as err_counts " + 
					"from t_policy t1 left join t_check_write t2 on t1.policy_no=t2.policy_no and t1.attached_flag=0 " + 
					"left join ( " + 
					"select it1.organ_code as organ_code, " + 
					"count(distinct it2.policy_no) as errCounts " + 
					"from t_policy it1 " + 
					"left join t_check_write it2 on it1.policy_no=it2.policy_no and it1.attached_flag=0 and it2.need_fix=\"要整改\"  " + 
					"where it1.policy_date between :pd1 and :pd2  " + 
					"and it1.policy_no like :orgCode  " + 
					"and it1.duration >= :duration " + 
					"and it1.fee_frequency like :toPerm " + 
					"and it1.attached_flag=0  " + 
					"group by it1.organ_code " + 
					") t3 on t1.organ_code=t3.organ_code  " + 
					"where t1.policy_date between :pd1 and :pd2  " + 
					"and t1.policy_no like :orgCode  " + 
					"and t1.duration >= :duration " + 
					"and t1.fee_frequency like :toPerm " + 
					"and t1.attached_flag=0  " + 
					"group by t1.organ_code;",
			nativeQuery=true)
	List<QyCheckModel> getCheckWriteAreaStat(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2, @Param("duration")Integer duration, @Param("toPerm")String toPerm);
	
	@Query(name="getCheckRecordAreaStat",
			value="select t1.organ_code as organ_code," + 
					"count(distinct t1.policy_no) as policy_counts," + 
					"count(distinct t2.policy_no) as check_counts, 0 as ontime_counts, " + 
					"sum(distinct t3.errCounts) as err_counts " + 
					"from t_policy t1 left join t_check_record t2 on t1.policy_no=t2.policy_no and t1.attached_flag=0 " + 
					"left join ( " + 
					"select it1.organ_code as organ_code, " + 
					"count(distinct it2.policy_no) as errCounts " + 
					"from t_policy it1 " + 
					"left join t_check_record it2 on it1.policy_no=it2.policy_no and it1.attached_flag=0 and it2.need_fix=\"要整改\"  " + 
					"where it1.policy_date between :pd1 and :pd2  " + 
					"and it1.policy_no like :orgCode  " + 
					"and it1.duration >= :duration " + 
					"and it1.fee_frequency like :toPerm " + 
					"and it1.attached_flag=0  " + 
					"group by it1.organ_code " + 
					") t3 on t1.organ_code=t3.organ_code  " + 
					"where t1.policy_date between :pd1 and :pd2  " + 
					"and t1.policy_no like :orgCode  " + 
					"and t1.duration >= :duration " + 
					"and t1.fee_frequency like :toPerm " + 
					"and t1.attached_flag=0  " + 
					"group by t1.organ_code;",
			nativeQuery=true)
	List<QyCheckModel> getCheckRecordAreaStat(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2, @Param("duration")Integer duration, @Param("toPerm")String toPerm);
	
	@Query(name="getCheckTruthCityStat",
			value="select left(t1.organ_code,6) as organ_code," + 
					"count(distinct t1.policy_no) as policy_counts," + 
					"count(distinct t1.policy_no) as check_counts, 0 as ontime_counts, " + 
					"count(distinct t2.policy_no) as err_counts " + 
					"from t_bank_code bc, t_policy t1 left join t_check_write t2 on t1.policy_no=t2.policy_no and t1.attached_flag=0 and t2.is_truth=true and t2.need_fix=\"要整改\" " + 
					"where t1.bank_code=bc.cpi_code and t1.attached_flag=0 and t1.policy_date between :pd1 and :pd2  " + 
					"and t1.duration >= :duration " + 
					"and t1.fee_frequency like :toPerm " + 
					"and bc.net_flag like :netFlag " + 
					" group by left(t1.organ_code,6);",
			nativeQuery=true)
	List<QyCheckModel> getCheckTruthCityStat(@Param("pd1")String pd1, @Param("pd2")String pd2, @Param("duration")Integer duration, @Param("toPerm")String toPerm, @Param("netFlag")String netFlag);
	
	@Query(name="getCheckTruthAreaStat",
			value="select t1.organ_code as organ_code," + 
					"count(distinct t1.policy_no) as policy_counts," +
					"count(distinct t1.policy_no) as check_counts, 0 as ontime_counts, " + 
					"count(distinct t2.policy_no) as err_counts " + 
					"from t_bank_code bc, t_policy t1 left join t_check_write t2 on t1.policy_no=t2.policy_no and t1.attached_flag=0 and t2.is_truth=true and t2.need_fix=\"要整改\" " + 
					"where t1.bank_code=bc.cpi_code and t1.attached_flag=0 and t1.organ_code like :orgCode and t1.policy_date between :pd1 and :pd2  " + 
					"and t1.duration >= :duration " + 
					"and t1.fee_frequency like :toPerm " + 
					"and bc.net_flag like :netFlag " + 
					" group by t1.organ_code;",
			nativeQuery=true)
	List<QyCheckModel> getCheckTruthAreaStat(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2, @Param("duration")Integer duration, @Param("toPerm")String toPerm, @Param("netFlag")String netFlag);
	
	@Query(name="getPrintCityStat",
			value="select left(pd.organ_code,6) as organ_code, " + 
					"COUNT(distinct pd.policy_no) as policy_counts, " + 
					"COUNT(distinct pr.policy_no) as check_counts, 0 as ontime_counts, " + 
					"sum(case when pr.policy_no is null then 0 else 1 end) as err_counts " + 
					"from t_policy pd " + 
					"left join t_policy_reprint_dtl pr on pd.policy_no = pr.policy_no and pd.attached_flag=0 " + 
					"where pd.policy_date between :pd1 and :pd2 and " + 
					//"pd.policy_no not like \"5244%\" and " + 
					"pd.policy_no not like \"8644%\" and " + 
					"pd.organ_code not like \"864400%\" and " + 
					"pd.attached_flag=0 " + 
					"group by left(pd.organ_code,6);",
			nativeQuery=true)
	List<QyCheckModel> getPrintCityStat(@Param("pd1")String pd1, @Param("pd2")String pd2);
	
	@Query(name="getPrintCityStat",
			value="select pd.organ_code as organ_code, " + 
					"COUNT(distinct pd.policy_no) as policy_counts, " + 
					"COUNT(distinct pr.policy_no) as check_counts, 0 as ontime_counts, " + 
					"sum(case when pr.policy_no is null then 0 else 1 end) as err_counts " + 
					"from t_policy pd " + 
					"left join t_policy_reprint_dtl pr on pd.policy_no = pr.policy_no and pd.attached_flag=0 " + 
					"where pd.policy_date between :pd1 and :pd2 and pd.organ_code like :orgCode and " + 
					//"pd.policy_no not like \"5244%\" and " + 
					"pd.policy_no not like \"8644%\" and " + 
					"pd.organ_code not like \"864400%\" and " + 
					"pd.attached_flag=0 " + 
					"group by pd.organ_code;",
			nativeQuery=true)
	List<QyCheckModel> getPrintAreaStat(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2);
	
	@Query(name="getStatusCheckWriteCityStat",
			value="select LEFT(tp.organ_name,2) as organ_code,0 as policy_counts, 0 as ontime_counts, COUNT(distinct cw.policy_no) as check_counts,COUNT(distinct cwt.policy_no) as err_counts " + 
					"from t_bank_code bc, t_check_write cw, t_policy tp left join t_check_write cwt on tp.policy_no=cwt.policy_no and tp.attached_flag=0 and cwt.need_fix=\"要整改\" and cwt.fix_status=:fixStatus " + 
					"where tp.bank_code=bc.cpi_code and cw.policy_no=tp.policy_no and tp.attached_flag=0 and tp.policy_date between :pd1 and :pd2 and cw.need_fix=\"要整改\" " + 
					"and bc.net_flag like :netFlag " + 
					" group by left(tp.organ_name,2) order by tp.organ_code;",
			nativeQuery=true)
	List<QyCheckModel> getStatusCheckWriteCityStat(@Param("pd1")String pd1, @Param("pd2")String pd2, @Param("fixStatus")String fixStatus, @Param("netFlag")String netFlag);
	
	@Query(name="getStatusCheckWriteAreaStat",
			value="select org.short_name as organ_code,0 as policy_counts, 0 as ontime_counts, COUNT(distinct cw.policy_no) as check_counts,COUNT(distinct cwt.policy_no) as err_counts " + 
					"from t_bank_code bc, t_check_write cw, t_organization org, t_policy tp left join t_check_write cwt on tp.policy_no=cwt.policy_no and tp.attached_flag=0 and cwt.need_fix=\"要整改\" and cwt.fix_status=:fixStatus " + 
					"where tp.bank_code=bc.cpi_code and tp.organ_code=org.org_code and cw.policy_no=tp.policy_no and tp.attached_flag=0 and tp.policy_date between :pd1 and :pd2  " + 
					"and tp.organ_code like :orgCode and cw.need_fix=\"要整改\" " +
					"and bc.net_flag like :netFlag " + 
					" group by org.short_name order by tp.organ_code;",
			nativeQuery=true)
	List<QyCheckModel> getStatusCheckWriteAreaStat(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2, @Param("fixStatus")String fixStatus, @Param("netFlag")String netFlag);
	
	@Query(name="getHfCityStat",
			value="select LEFT(tp.organ_name,2) as organ_code, 0 as ontime_counts, COUNT(cfl2.issue_no) as err_counts,COUNT(cfl1.issue_no) as check_counts,count(distinct tp.policy_no) as policy_counts " + 
					"from t_bank_code bc, t_organization org, t_policy tp " + 
					"left join t_call_fail_list cfl1 on tp.policy_no=cfl1.policy_no and tp.attached_flag=0 and cfl1.status<>\"二访成功\"  " + 
					"left join t_call_fail_list cfl2 on tp.policy_no=cfl2.policy_no and tp.attached_flag=0 and cfl2.status=\"已退保\"  " + 
					"where bc.cpi_code=tp.bank_code and tp.organ_code=org.org_code and tp.attached_flag=0 " +
					"and tp.policy_date between :pd1 and :pd2 and bc.net_flag like :netFlag " + 
					" group by LEFT(tp.organ_name,2) order by tp.organ_code;",
			nativeQuery=true)
	List<QyCheckModel> getHfCityStat(@Param("pd1")String pd1, @Param("pd2")String pd2, @Param("netFlag")String netFlag);
	
	@Query(name="getHfAreaStat",
			value="select org.short_name as organ_code, 0 as ontime_counts, COUNT(cfl2.issue_no) as err_counts,COUNT(cfl1.issue_no) as check_counts,count(distinct tp.policy_no) as policy_counts " + 
					"from t_bank_code bc, t_organization org,t_policy tp " + 
					"left join t_call_fail_list cfl1 on tp.policy_no=cfl1.policy_no and tp.attached_flag=0 and cfl1.status<>\"二访成功\"  " + 
					"left join t_call_fail_list cfl2 on tp.policy_no=cfl2.policy_no and tp.attached_flag=0 and cfl2.status=\"已退保\"  " + 
					"where bc.cpi_code=tp.bank_code and tp.organ_code=org.org_code and tp.attached_flag=0 " +
					"and tp.policy_date between :pd1 and :pd2 " + 
					"and bc.net_flag like :netFlag and tp.organ_code like :orgCode " +
					" group by org.short_name order by tp.organ_code;",
			nativeQuery=true)
	List<QyCheckModel> getHfAreaStat(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2, @Param("netFlag")String netFlag);

	@Query(name="getMultipleCheckTruthCityStat",
			value="select left(t1.organ_name,2) as organ_code," + 
					"count(distinct t1.policy_no) as policy_counts," + 
					"count(distinct t2.policy_no) as check_counts," + 
					"SUM(case when t2.fix_status=\"CloseStatus\" or t2.fix_status=\"CTStatus\" then 1 else 0 end) as err_counts," + 
					"sum(case when t2.reply_time is not null and datediff(t2.reply_time,t2.operate_time)<=10 then 1 else 0 end) as ontime_counts " + 
					"from t_bank_code bc, t_policy t1 left join t_check_write t2 on t1.policy_no=t2.policy_no and t1.attached_flag=0 and t2.is_truth=true and t2.need_fix=\"要整改\" " + 
					"where t1.bank_code=bc.cpi_code and t1.attached_flag=0 and t1.policy_date between :pd1 and :pd2  " + 
					"and t1.duration >= :duration " + 
					"and t1.fee_frequency like :toPerm " + 
					"and bc.net_flag like :netFlag " + 
					" group by left(t1.organ_name,2) "
					+ "order by t1.organ_code;",
			nativeQuery=true)
	List<QyCheckModel> getMultipleCheckTruthCityStat(@Param("pd1")String pd1, @Param("pd2")String pd2, @Param("duration")Integer duration, @Param("toPerm")String toPerm, @Param("netFlag")String netFlag);

	@Query(name="getMultipleCheckTruthAreaStat",
			value="select t1.organ_name as organ_code," + 
					"count(distinct t1.policy_no) as policy_counts," + 
					"count(distinct t2.policy_no) as check_counts," + 
					"SUM(case when t2.fix_status=\"CloseStatus\" or t2.fix_status=\"CTStatus\" then 1 else 0 end) as err_counts," + 
					"sum(case when t2.reply_time is not null and datediff(t2.reply_time,t2.operate_time)<=10 then 1 else 0 end) as ontime_counts " + 
					"from t_bank_code bc, t_policy t1 left join t_check_write t2 on t1.policy_no=t2.policy_no and t1.attached_flag=0 and t2.is_truth=true and t2.need_fix=\"要整改\" " + 
					"where t1.bank_code=bc.cpi_code and t1.attached_flag=0 and t1.organ_code like :orgCode and t1.policy_date between :pd1 and :pd2  " + 
					"and t1.duration >= :duration " + 
					"and t1.fee_frequency like :toPerm " + 
					"and bc.net_flag like :netFlag " + 
					"group by t1.organ_name "
					+ "order by t1.organ_code;",
			nativeQuery=true)
	List<QyCheckModel> getMultipleCheckTruthAreaStat(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2, @Param("duration")Integer duration, @Param("toPerm")String toPerm, @Param("netFlag")String netFlag);
}