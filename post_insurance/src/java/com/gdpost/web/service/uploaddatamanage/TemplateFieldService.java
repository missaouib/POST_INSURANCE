package com.gdpost.web.service.uploaddatamanage;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.member.TblMemberDataTemplate;
import com.gdpost.web.entity.member.TblMemberDataTemplateField;
import com.gdpost.web.util.dwz.Page;

public interface TemplateFieldService {
	TblMemberDataTemplateField get(Long id);

	TblMemberDataTemplateField saveOrUpdate(TblMemberDataTemplateField template);

	void delete(Long id);
	
	List<TblMemberDataTemplateField> findAll(Specification<TblMemberDataTemplateField> specification, Page page);
	
	List<TblMemberDataTemplateField> findAll(Specification<TblMemberDataTemplateField> specification, Sort sort);
	
	List<TblMemberDataTemplateField> getByTemplateId(Long id);
}
