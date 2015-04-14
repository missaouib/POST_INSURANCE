package com.sendtend.web.service.uploaddatamanage;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.member.TblMemberDataTemplateFieldRule;
import com.sendtend.web.util.dwz.Page;

public interface TemplateFieldRuleService {
	TblMemberDataTemplateFieldRule get(Long id);

	void saveOrUpdate(TblMemberDataTemplateFieldRule template);

	void delete(Long id);
	
	List<TblMemberDataTemplateFieldRule> findAll(Specification<TblMemberDataTemplateFieldRule> specification, Page page);
	
	List<TblMemberDataTemplateFieldRule> findAll(Specification<TblMemberDataTemplateFieldRule> specification, Sort sort);
	
	TblMemberDataTemplateFieldRule findByTblMemberDataTemplateFieldId(Long id);
}
