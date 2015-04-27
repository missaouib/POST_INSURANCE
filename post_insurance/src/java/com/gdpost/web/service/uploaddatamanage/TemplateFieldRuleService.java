package com.gdpost.web.service.uploaddatamanage;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.member.TblMemberDataTemplateFieldRule;
import com.gdpost.web.util.dwz.Page;

public interface TemplateFieldRuleService {
	TblMemberDataTemplateFieldRule get(Long id);

	void saveOrUpdate(TblMemberDataTemplateFieldRule template);

	void delete(Long id);
	
	List<TblMemberDataTemplateFieldRule> findAll(Specification<TblMemberDataTemplateFieldRule> specification, Page page);
	
	List<TblMemberDataTemplateFieldRule> findAll(Specification<TblMemberDataTemplateFieldRule> specification, Sort sort);
	
	TblMemberDataTemplateFieldRule findByTblMemberDataTemplateFieldId(Long id);
}
