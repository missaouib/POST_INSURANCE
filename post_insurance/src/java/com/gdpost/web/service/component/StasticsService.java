/**
 * 
 * ==========================================================
 * 保全管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.component;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.component.CheckModel;
import com.gdpost.web.entity.component.PolicyStatModel;
import com.gdpost.web.entity.component.QyCheckModel;
import com.gdpost.web.entity.component.StaffDtlModel;
import com.gdpost.web.entity.component.StaffModel;
import com.gdpost.web.entity.component.TuiBaoDtlModel;
import com.gdpost.web.entity.component.TuiBaoModel;
import com.gdpost.web.entity.component.UwDtlModel;
import com.gdpost.web.entity.component.UwModel;
import com.gdpost.web.entity.insurance.StasticsArea;
import com.gdpost.web.entity.insurance.StasticsCity;
import com.gdpost.web.util.dwz.Page;

public interface StasticsService {
	
	List<TuiBaoModel> getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String d3, String d4, String prdCode, String toPerm, String staffFlag, Integer duration);
	
	List<TuiBaoModel> getTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag, Integer duration);
	
	List<TuiBaoModel> getProvTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String d3, String d4, String prdCode, String toPerm, String staffFlag, Integer duration);
	
	List<TuiBaoModel> getProvTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag, Integer duration);
	
	List<TuiBaoDtlModel> getTuiBaoWarnningDetailWithBankCode(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag, String bankName, Integer duration);
	
	List<TuiBaoDtlModel> getTuiBaoCsDetailWithBankCode(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag, String bankName, Integer duration);
	
	List<TuiBaoModel> getNetTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String prdCode, String toPerm, String staffFlag, String bankName, Integer duration);
	
	List<TuiBaoModel> getNetTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag, String bankName, Integer duration);
	
	List<StaffModel> getStaffCountWithPolicyDateNoBankCode(String organCode, String d1, String d2, String prdCode, String toPerm, Integer duration);
	
	List<StaffModel> getStaffCountWithPolicyDate(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, Integer duration);
	
	List<StaffModel> getProvStaffCountWithPolicyDateNoBankCode(String organCode, String d1, String d2, String prdCode, String toPerm, Integer duration);
	
	List<StaffModel> getProvStaffCountWithPolicyDate(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, Integer duration);
	
	List<StaffDtlModel> getStaffDetailWithPolicyDate(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, Integer duration);
	
	List<UwModel> getProvUwStastics(String organCode, String d1, String d2);
	
	List<UwModel> getCityUwStastics(String organCode, String d1, String d2);
	
	List<UwModel> getNetUwStastics(String organCode, String d1, String d2);
	
	List<UwDtlModel> getUwDtlStastics(String organCode, String d1, String d2);
	
	List<UwModel> getProvLongUwStastics(String organCode, String d1, String d2);
	
	List<UwModel> getCityLongUwStastics(String organCode, String d1, String d2);
	
	List<UwModel> getNetLongUwStastics(String organCode, String d1, String d2);
	
	List<UwDtlModel> getLongUwDtlStastics(String organCode, String d1, String d2);
	
	List<PolicyStatModel> getPolicyProdStastics(String organCode, String d1, String d2, String prdCode, String toPerm, String staffFlag, String csFlag, String saleType, String status, Integer duration);
	
	List<PolicyStatModel> getPolicyProdStasticsWithBankCode(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, String staffFlag, String bankName, String csFlag, String saleType, String status, Integer duration);
	
	List<PolicyStatModel> getProvPolicyOrganStastics(String organCode, String d1, String d2, String prdCode, String toPerm, String staffFlag, String csFlag, String saleType, String status, Integer duration);
	
	List<PolicyStatModel> getPolicyOrganStastics(String organCode, String d1, String d2, String prdCode, String toPerm, String staffFlag, String csFlag, String saleType, String status, Integer duration);
	
	List<PolicyStatModel> getPolicyOrganNetStastics(String organCode, String d1, String d2, String prdCode, String toPerm, String staffFlag, String bankName, String csFlag, String saleType, String status, Integer duration);
	
	List<PolicyStatModel> getProvPolicyOrganStasticsWithBankCode(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, String staffFlag, String bankName, String csFlag, String saleType, String status, Integer duration);
	
	List<PolicyStatModel> getPolicyOrganStasticsWithBankCode(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, String staffFlag, String bankName, String csFlag, String saleType, String status, Integer duration);
	
	List<PolicyStatModel> getPolicyOrganNetStasticsWithBankCode(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, String staffFlag, String bankName, String csFlag, String saleType, String status, Integer duration);
	
	List<PolicyStatModel> getPolicyFeeFrequencyStastics(String organCode, String d1, String d2, String prdCode, String toPerm, String staffFlag, String csFlag, String saleType, String status, Integer duration);
	
	List<PolicyStatModel> getPolicyFeeFrequencyStasticsWithBankCode(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, String staffFlag, String bankName, String csFlag, String saleType, String status, Integer duration);
	
	List<QyCheckModel> getCheckWriteCityStastics(String d1, String d2, Integer duration, String toPerm);
	
	List<CheckModel> getCheckWritetasticsDtl(String orgCode, String d1, String d2, Integer duration, String toPerm);
	
	List<QyCheckModel> getCheckRecordCityStastics(String d1, String d2, Integer duration, String toPerm);
	
	List<CheckModel> getCheckRecordtasticsDtl(String orgCode, String d1, String d2, Integer duration, String toPerm);
	
	List<QyCheckModel> getCheckWriteAreaStastics(String organCode, String d1, String d2, Integer duration, String toPerm);
	
	List<QyCheckModel> getCheckRecordAreaStastics(String organCode, String d1, String d2, Integer duration, String toPerm);
	
	List<QyCheckModel> getCheckTruthCityStastics(String d1, String d2, Integer duration, String toPerm, String netFlag);
	
	List<CheckModel> getCheckTruthStasticsDtl(String organCode, String d1, String d2, Integer duration, String toPerm, String netFlag);
	
	List<QyCheckModel> getCheckTruthAreaStastics(String organCode, String d1, String d2, Integer duration, String toPerm, String netFlag);
	
	List<QyCheckModel> getPrintCityStastics(String d1, String d2);
	
	List<QyCheckModel> getPrintAreaStastics(String organCode, String d1, String d2);
	
	List<StasticsCity> findByCityStatByExample(Specification<StasticsCity> specification, Page page);
	
	List<StasticsArea> findByAreaStatByExample(Specification<StasticsArea> specification, Page page);
	
	List<QyCheckModel> getStatusCheckWriteCityStastics(String d1, String d2, String fixStatus, String netFlag);
	
	List<QyCheckModel> getStatusCheckWriteAreaStastics(String organCode, String d1, String d2, String fixStatus, String netFlag);
	
	List<QyCheckModel> getHfCityStastics(String d1, String d2, String netFlag);
	
	List<QyCheckModel> getHfAreaStastics(String organCode, String d1, String d2, String netFlag);

	List<TuiBaoModel> getAGWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String e1, String e2, String c1, String c2, String prdCode, String toPerm, String staffFlag, Integer duration);

	List<TuiBaoModel> getAGWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String e1, String e2, String c1, String c2, String flag, String prdCode, String toPerm, String staffFlag, Integer duration);

	List<TuiBaoModel> getProvAGWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String e1, String e2, String c1, String c2, String prdCode, String toPerm, String staffFlag, Integer duration);

	List<TuiBaoModel> getProvAGWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String e1, String e2, String c1, String c2, String flag, String prdCode, String toPerm, String staffFlag, Integer duration);

	List<TuiBaoModel> getNetAGWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String e1, String e2, String c1, String c2, String prdCode, String toPerm, String staffFlag, String bankName, Integer duration);

	List<TuiBaoModel> getNetAGWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String e1, String e2, String c1, String c2, String flag, String prdCode, String toPerm, String staffFlag, String bankName, Integer duration);
}
