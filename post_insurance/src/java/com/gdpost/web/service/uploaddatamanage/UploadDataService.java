package com.gdpost.web.service.uploaddatamanage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.jpa.domain.Specification;

import System.Data.DataTable;

import com.gdpost.utils.TemplateHelper.Template.FileTemplate;
import com.gdpost.web.entity.main.Policy;

public interface UploadDataService {
	
	boolean handleData(FileTemplate template, HttpServletRequest request, long member_id, List<String> listFiles, 
			int currentNY, int lastNY, long operator_id, String operator_name, int operator_type, StringBuilder builder, String memo);
	
	boolean importData(FileTemplate template, HttpServletRequest request, DataTable dt, long member_id, int ny);
	
	boolean updateStatusData(FileTemplate template, HttpServletRequest request, DataTable dt);
	
	boolean clearImport(HttpServletRequest request, long member_id, int ny);
	
	boolean clearImportDone(HttpServletRequest request, long member_id, int ny);
	
	boolean setImportDone(HttpServletRequest request, long member_id, int ny, long operator_id, String operator_name, int operator_type, String memo);
	
	List<Policy> findAll(com.gdpost.web.util.dwz.Page page);
	
	List<Policy> findByExample(Specification<Policy> specification, com.gdpost.web.util.dwz.Page page);
	
}

