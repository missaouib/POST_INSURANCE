package com.gdpost.web.service.impl.uploaddatamanage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.member.ITblMemberDataTemplateDAO;
import com.gdpost.web.entity.member.TblMemberDataTemplate;
import com.gdpost.web.service.uploaddatamanage.TemplateService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {
	
	@Autowired
	private ITblMemberDataTemplateDAO templateDAO;

	@Override
	public TblMemberDataTemplate get(Long id) {
		return templateDAO.findById(id);
	}

	@Override
	public TblMemberDataTemplate saveOrUpdate(TblMemberDataTemplate template) {
		return templateDAO.save(template);
	}

	@Override
	public void delete(Long id) {
		templateDAO.delete(id);
	}

	@Override
	public List<TblMemberDataTemplate> findAll(
			Specification<TblMemberDataTemplate> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberDataTemplate> springDataPage = templateDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<TblMemberDataTemplate> findAll(long member_id) {
		return templateDAO.findAll(member_id);
	}

}
