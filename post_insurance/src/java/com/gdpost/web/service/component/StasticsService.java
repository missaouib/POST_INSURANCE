/**
 * 
 * ==========================================================
 * 保全管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.component;

import java.util.List;

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
	
	List<TuiBaoModel> getNetTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String prdCode, String toPerm, String staffFlag, String bankName);
	
	List<StaffModel> getStaffCountWithPolicyDateNoBankCode(String organCode, String d1, String d2, String prdCode, String toPerm);
	
	List<StaffModel> getStaffCountWithPolicyDate(String organCode, String d1, String d2, String flag, String prdCode, String toPerm);
	
	List<StaffModel> getProvStaffCountWithPolicyDateNoBankCode(String organCode, String d1, String d2, String prdCode, String toPerm);
	
	List<StaffModel> getProvStaffCountWithPolicyDate(String organCode, String d1, String d2, String flag, String prdCode, String toPerm);
	
	List<StaffDtlModel> getStaffDetailWithPolicyDate(String organCode, String d1, String d2, String flag, String prdCode, String toPerm);
	
	List<UwModel> getProvUwStastics(String organCode, String d1, String d2);
	
	List<UwModel> getCityUwStastics(String organCode, String d1, String d2);
	
	List<UwDtlModel> getUwDtlStastics(String organCode, String d1, String d2);
}
