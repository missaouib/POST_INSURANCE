package com.gdpost.web.service.impl.uploaddatamanage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.member.ITblMemberDataTemplateFieldRuleDAO;
import com.gdpost.web.entity.member.TblMemberDataTemplateFieldRule;
import com.gdpost.web.service.uploaddatamanage.TemplateFieldRuleService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class TemplateFieldRuleServiceImpl implements TemplateFieldRuleService {
	
	@Autowired
	private ITblMemberDataTemplateFieldRuleDAO ruleDAO;

	@Override
	public TblMemberDataTemplateFieldRule get(Long id) {
		return ruleDAO.findById(id);
	}

	@Override
	public void saveOrUpdate(TblMemberDataTemplateFieldRule template) {
		ruleDAO.save(template);
	}

	@Override
	public void delete(Long id) {
		ruleDAO.delete(id);
	}

	@Override
	public List<TblMemberDataTemplateFieldRule> findAll(
			Specification<TblMemberDataTemplateFieldRule> specification,
			Page page) {
		org.springframework.data.domain.Page<TblMemberDataTemplateFieldRule> springDataPage = ruleDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<TblMemberDataTemplateFieldRule> findAll(
			Specification<TblMemberDataTemplateFieldRule> specification,
			Sort sort) {
		return ruleDAO.findAll(specification, sort);
	}

	@Override
	public TblMemberDataTemplateFieldRule findByTblMemberDataTemplateFieldId(Long id) {
		return ruleDAO.findByTblMemberDataTemplateFieldId(id);
	}
}
