/**
 * 
 * ==========================================================
 * 保全管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.component;

import java.util.List;

import com.gdpost.web.entity.component.CommonModel;

public interface StasticsService {
	
	List<CommonModel> getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String d3, String d4, String prdCode);
	
	List<CommonModel> getTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode);
	
	List<CommonModel> getProvTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String d3, String d4, String prdCode);
	
	List<CommonModel> getProvTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode);
}
