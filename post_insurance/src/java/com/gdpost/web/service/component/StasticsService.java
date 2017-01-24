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
	
	List<CommonModel> getTuiBaoWarnningWithPolicyDate(String organCode, String d1, String d2);
	
	List<CommonModel> getTuiBaoWarnningWithCsDate(String organCode, String d1, String d2);
	
	List<CommonModel> getTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4);
	
}
