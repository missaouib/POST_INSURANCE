package com.sendtend.web.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendtend.web.dao.member.ITblMemberDataStatusDAO;
import com.sendtend.web.entity.member.TblMemberDataStatus;
import com.sendtend.web.service.member.MemberDataStatusService;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.dwz.PageUtils;

@Service
@Transactional
public class MemberDataStatusServiceImpl implements MemberDataStatusService {

	@Autowired
	private ITblMemberDataStatusDAO memberDataStatusDAO;
	
	@Override
	public List<TblMemberDataStatus> findAll(
			Specification<TblMemberDataStatus> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberDataStatus> springDataPage = memberDataStatusDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public TblMemberDataStatus get(Long id) {
		return memberDataStatusDAO.findOne(id);
	}

}
