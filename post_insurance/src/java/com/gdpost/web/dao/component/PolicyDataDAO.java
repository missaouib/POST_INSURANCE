package com.gdpost.web.dao.component;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.component.PolicyDataModel;
import com.gdpost.web.entity.component.PolicyStatModel;

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
					+ "'' as bank_account, tp.cs_flag, case when uw.policy_no is null then 'N' else 'Y' end as uw_flag "
					+ "from t_policy tp "
					+ "left join t_policy_dtl tpd on tp.policy_no=tpd.policy_no "
					+ "left join t_under_write uw on tp.policy_no=uw.policy_no "
					+ "where tp.policy_date between :pd1 and :pd2 and tp.organ_code like :orgCode "
					+ "and tp.organ_code<>'86440001' "
					+ "order by tp.organ_code, tp.policy_date; ",
			nativeQuery=true)
	List<PolicyDataModel> getPolicyDate(@Param("orgCode")String orgCode, @Param("pd1")String pd1, @Param("pd2")String pd2);
	
	@Query(name="getPolicyDateProdStat",
			value="select tp.prod_name as name,count(tp.id) as policy_count, sum(tp.total_fee) as policy_fee "
			+ "from t_policy tp "
			+ "where tp.cs_flag<>:csFlag and tp.attached_flag=0 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_name like :prdCode "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "group by tp.prod_name "
			+ "order by tp.prod_code;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateProdStat(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag);
	
	@Query(name="getPolicyDateProdStatWithBankCode",
			value="select tp.prod_name as name, count(tp.id) as policy_count, sum(tp.total_fee) as policy_fee "
			+ "from t_policy tp, t_bank_code tbc "
			+ "where tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>1 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_name like :prdCode "
			+ "and tbc.net_flag=:netFlag "
			+ "and tp.bank_name like :bankName "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "group by tp.prod_name "
			+ "order by tp.prod_code;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateProdStatWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName);
	
	@Query(name="getProvPolicyDateOrganStat",
			value="select left(tp.organ_name, 2) as name,count(tp.id) as policy_count, sum(tp.total_fee) as policy_fee "
			+ "from t_policy tp "
			+ "where tp.cs_flag<>:csFlag and tp.attached_flag=0 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_name like :prdCode "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "group by left(tp.organ_name, 2) "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<PolicyStatModel> getProvPolicyDateOrganStat(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag);
	
	@Query(name="getPolicyDateOrganStat",
			value="select tp.organ_name as name,count(tp.id) as policy_count, sum(tp.total_fee) as policy_fee "
			+ "from t_policy tp "
			+ "where tp.cs_flag<>:csFlag and tp.attached_flag=0 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_name like :prdCode "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "group by tp.organ_name "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateOrganStat(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag);
	
	@Query(name="getPolicyDateOrganNetStat",
			value="select tbc.name as name, count(tp.id) as policy_count, sum(tp.total_fee) as policy_fee "
			+ "from t_policy tp, t_bank_code tbc "
			+ "where tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>1 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_name like :prdCode "
			+ "and tp.bank_name like :bankName "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "group by tp.organ_name "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateOrganNetStat(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName);
	
	@Query(name="getProvPolicyDateOrganStatWithBankCode",
			value="select left(tp.organ_name,2) as name, count(tp.id) as policy_count, sum(tp.total_fee) as policy_fee "
			+ "from t_policy tp, t_bank_code tbc "
			+ "where tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>1 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_name like :prdCode "
			+ "and tbc.net_flag=:netFlag "
			+ "and tp.bank_name like :bankName "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "group by left(tp.organ_name,2) "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<PolicyStatModel> getProvPolicyDateOrganStatWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName);
	
	@Query(name="getPolicyDateOrganStatWithBankCode",
			value="select tp.organ_name as name, count(tp.id) as policy_count, sum(tp.total_fee) as policy_fee "
			+ "from t_policy tp, t_bank_code tbc "
			+ "where tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>1 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_name like :prdCode "
			+ "and tbc.net_flag=:netFlag "
			+ "and tp.bank_name like :bankName "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "group by tp.organ_name "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateOrganStatWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName);
	
	@Query(name="getPolicyDateOrganNetStatWithBankCode",
			value="select tbc.name as name, count(tp.id) as policy_count, sum(tp.total_fee) as policy_fee "
			+ "from t_policy tp, t_bank_code tbc "
			+ "where tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>1 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_name like :prdCode "
			+ "and tbc.net_flag=:netFlag "
			+ "and tp.bank_name like :bankName "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "group by tp.organ_name "
			+ "order by tp.organ_code;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateOrganNetStatWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName);
	
	
	@Query(name="getPolicyDateFeeTypeStat",
			value="select tp.fee_frequency as name,count(tp.id) as policy_count, sum(tp.total_fee) as policy_fee "
			+ "from t_policy tp "
			+ "where tp.cs_flag<>:csFlag and tp.attached_flag=0 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_name like :prdCode "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "group by tp.fee_frequency "
			+ "order by tp.fee_frequency;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateFeeTypeStat(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag);
	
	@Query(name="getPolicyDateFeeTypeStatWithBankCode",
			value="select tp.fee_frequency as name, count(tp.id) as policy_count, sum(tp.total_fee) as policy_fee "
			+ "from t_policy tp, t_bank_code tbc "
			+ "where tp.bank_code=tbc.cpi_code and tp.attached_flag=0 and tp.cs_flag<>1 "
			+ "and tp.policy_date between :p1 and :p2 "
			+ "and tp.organ_code like :orgCode "
			+ "and tp.prod_name like :prdCode "
			+ "and tbc.net_flag=:netFlag "
			+ "and tp.bank_name like :bankName "
			+ "and tp.fee_frequency like :toPerm "
			+ "and tp.staff_flag like :staffFlag "
			+ "group by tp.fee_frequency "
			+ "order by tp.fee_frequency;",
			nativeQuery=true)
	List<PolicyStatModel> getPolicyDateFeeTypeStatWithBankCode(@Param("orgCode")String orgCode, @Param("p1")String pd1, @Param("p2")String pd2, @Param("netFlag")String netFlag, @Param("prdCode")String prdCode, @Param("toPerm")String toPerm, @Param("staffFlag")String staffFlag, @Param("bankName")String bankName);
	
}