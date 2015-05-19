package com.sendtend.web.service.uploaddatamanage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.jpa.domain.Specification;

import System.Data.DataTable;

import com.sendtend.web.entity.member.TblMemberData;
import com.sendtend.web.util.dwz.Page;

public interface UploadDataService {
	
	boolean handleData(int template, HttpServletRequest request, long member_id, List<String> listFiles, 
			int currentNY, int lastNY, long operator_id, String operator_name, int operator_type, StringBuilder builder, String memo);
	
	boolean importData(HttpServletRequest request, DataTable dt, long member_id, int ny);
	
	boolean clearImport(HttpServletRequest request, long member_id, int ny);
	
	boolean clearImportDone(HttpServletRequest request, long member_id, int ny);
	
	boolean setImportDone(HttpServletRequest request, long member_id, int ny, long operator_id, String operator_name, int operator_type, String memo);
	
	void saveOrUpdate(TblMemberData tblMemberData);

	boolean delete(HttpServletRequest request, int ny, Long member_id);
	
	List<TblMemberData> findAll(Page page);
	
	List<TblMemberData> findByExample(Specification<TblMemberData> specification, Page page);
	
	boolean checkImportNY(HttpServletRequest request, long member_id, int ny);
}
