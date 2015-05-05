package com.gdpost.web.service.uploaddatamanage;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.jpa.domain.Specification;

import System.Data.DataTable;

import com.gdpost.web.entity.member.TblMemberData;
import com.gdpost.web.util.dwz.Page;

public interface UploadDataService {
	
	boolean handleData(HttpServletRequest request, List<String> listFiles, 
			long operator_id, String operator_name, int operator_type, StringBuilder builder, String memo);
	
	boolean patchImportData(HttpServletRequest request, InputStream is, 
			long operator_id, String operator_name);
	
	boolean importData(HttpServletRequest request, DataTable dt);
	
	boolean clearImport(HttpServletRequest request);
	
	boolean clearImportDone(HttpServletRequest request);
	
	boolean setImportDone(HttpServletRequest request, long operator_id, String operator_name, int operator_type, String memo);
	
	void saveOrUpdate(TblMemberData tblMemberData);

	boolean delete(HttpServletRequest request);
	
	List<TblMemberData> findAll(Page page);
	
	List<TblMemberData> findByExample(Specification<TblMemberData> specification, Page page);
	
	boolean checkImportNY(HttpServletRequest request);
}
