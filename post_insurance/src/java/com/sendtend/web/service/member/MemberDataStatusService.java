package com.sendtend.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.member.TblMemberDataStatus;
import com.sendtend.web.util.dwz.Page;

public interface MemberDataStatusService {
	List<TblMemberDataStatus> findAll(Specification<TblMemberDataStatus> specification, Page page);
	
	TblMemberDataStatus get(Long id);
}
