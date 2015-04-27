package com.gdpost.web.service.impl.uploaddatamanage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.member.ITblMemberResourceDAO;
import com.gdpost.web.entity.member.TblMemberResource;
import com.gdpost.web.service.uploaddatamanage.MemberResourceService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class MemberResourceServiceImpl implements MemberResourceService {
	@Autowired
	private ITblMemberResourceDAO memberResourceDAO;

	@Override
	public TblMemberResource get(Long id) {
		return memberResourceDAO.findById(id);
	}

	@Override
	public void saveOrUpdate(TblMemberResource resource) {
		memberResourceDAO.save(resource);
	}

	@Override
	public void delete(Long id) {
		memberResourceDAO.delete(id);
	}

	@Override
	public List<TblMemberResource> findAll(Specification<TblMemberResource> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberResource> springDataPage = memberResourceDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}	
}
