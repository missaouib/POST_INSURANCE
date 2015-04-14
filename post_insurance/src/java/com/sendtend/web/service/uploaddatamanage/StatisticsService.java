package com.sendtend.web.service.uploaddatamanage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.jpa.domain.Specification;

import System.Data.DataTable;

import com.sendtend.utils.UploadDataHelper.StatisticsType;
import com.sendtend.web.entity.member.TblMemberData;
import com.sendtend.web.util.dwz.Page;

public interface StatisticsService {
	List<TblMemberData> findAll(Specification<TblMemberData> specification, Page page);
	
	public DataTable getStatistics(HttpServletRequest request, long member_id, int ny, String dm, String pl, StatisticsType type);

	public DataTable getNY(HttpServletRequest request, long member_id);
	
	public DataTable getDM(HttpServletRequest request, long member_id);
	
	public DataTable getPL(HttpServletRequest request, long member_id);
	
	public int deleteUploadData(final Long memberId, final Integer ny);
}
