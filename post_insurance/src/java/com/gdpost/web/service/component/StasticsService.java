/**
 * 
 * ==========================================================
 * 保全管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.component;

import java.util.List;

import com.gdpost.web.entity.component.PolicyStatModel;
import com.gdpost.web.entity.component.StaffDtlModel;
import com.gdpost.web.entity.component.StaffModel;
import com.gdpost.web.entity.component.TuiBaoDtlModel;
import com.gdpost.web.entity.component.TuiBaoModel;
import com.gdpost.web.entity.component.UwDtlModel;
import com.gdpost.web.entity.component.UwModel;

public interface StasticsService {
	
	List<TuiBaoModel> getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String d3, String d4, String prdCode, String toPerm, String staffFlag);
	
	List<TuiBaoModel> getTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag);
	
	List<TuiBaoModel> getProvTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String d3, String d4, String prdCode, String toPerm, String staffFlag);
	
	List<TuiBaoModel> getProvTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag);
	
	List<TuiBaoDtlModel> getTuiBaoWarnningDetailWithBankCode(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag, String bankName);
	
	List<TuiBaoDtlModel> getTuiBaoCsDetailWithBankCode(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag, String bankName);
	
	List<TuiBaoModel> getNetTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String prdCode, String toPerm, String staffFlag, String bankName);
	
	List<TuiBaoModel> getNetTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag, String bankName);
	
	List<StaffModel> getStaffCountWithPolicyDateNoBankCode(String organCode, String d1, String d2, String prdCode, String toPerm);
	
	List<StaffModel> getStaffCountWithPolicyDate(String organCode, String d1, String d2, String flag, String prdCode, String toPerm);
	
	List<StaffModel> getProvStaffCountWithPolicyDateNoBankCode(String organCode, String d1, String d2, String prdCode, String toPerm);
	
	List<StaffModel> getProvStaffCountWithPolicyDate(String organCode, String d1, String d2, String flag, String prdCode, String toPerm);
	
	List<StaffDtlModel> getStaffDetailWithPolicyDate(String organCode, String d1, String d2, String flag, String prdCode, String toPerm);
	
	List<UwModel> getProvUwStastics(String organCode, String d1, String d2);
	
	List<UwModel> getCityUwStastics(String organCode, String d1, String d2);
	
	List<UwModel> getNetUwStastics(String organCode, String d1, String d2);
	
	List<UwDtlModel> getUwDtlStastics(String organCode, String d1, String d2);
	
	List<UwModel> getProvLongUwStastics(String organCode, String d1, String d2);
	
	List<UwModel> getCityLongUwStastics(String organCode, String d1, String d2);
	
	List<UwModel> getNetLongUwStastics(String organCode, String d1, String d2);
	
	List<UwDtlModel> getLongUwDtlStastics(String organCode, String d1, String d2);
	
	List<PolicyStatModel> getPolicyProdStastics(String organCode, String d1, String d2, String prdCode, String toPerm, String staffFlag, String csFlag);
	
	List<PolicyStatModel> getPolicyProdStasticsWithBankCode(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, String staffFlag, String bankName, String csFlag);
	
	List<PolicyStatModel> getProvPolicyOrganStastics(String organCode, String d1, String d2, String prdCode, String toPerm, String staffFlag, String csFlag);
	
	List<PolicyStatModel> getPolicyOrganStastics(String organCode, String d1, String d2, String prdCode, String toPerm, String staffFlag, String csFlag);
	
	List<PolicyStatModel> getPolicyOrganNetStastics(String organCode, String d1, String d2, String prdCode, String toPerm, String staffFlag, String bankName, String csFlag);
	
	List<PolicyStatModel> getProvPolicyOrganStasticsWithBankCode(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, String staffFlag, String bankName, String csFlag);
	
	List<PolicyStatModel> getPolicyOrganStasticsWithBankCode(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, String staffFlag, String bankName, String csFlag);
	
	List<PolicyStatModel> getPolicyOrganNetStasticsWithBankCode(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, String staffFlag, String bankName, String csFlag);
	
	List<PolicyStatModel> getPolicyFeeFrequencyStastics(String organCode, String d1, String d2, String prdCode, String toPerm, String staffFlag, String csFlag);
	
	List<PolicyStatModel> getPolicyFeeFrequencyStasticsWithBankCode(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, String staffFlag, String bankName, String csFlag);
}
