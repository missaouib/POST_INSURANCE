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
	List<DocStatModel> getDocNotScanStat(String d1, String d2);
	
	List<DocStatModel> getSubDocNotScanStat(String organName, String d1, String d2);
	
	List<DocStatModel> getSumDocStat(String organName, String d1, String d2);
}
