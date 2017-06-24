/**
 * 
 * ==========================================================
 * 档案管理
 * ==========================================================
 * 
 */
package com.gdpost.web.service.insurance;

import java.util.List;

import com.gdpost.web.entity.component.DocStatModel;

public interface DaglService {
	List<DocStatModel> getDocNotScanStat();
	
	List<DocStatModel> getSubDocNotScanStat(String organName);
	
	List<DocStatModel> getSumDocStat(String organName, String d1, String d2);
}
