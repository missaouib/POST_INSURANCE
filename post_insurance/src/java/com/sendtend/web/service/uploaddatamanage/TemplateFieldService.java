package com.sendtend.web.service.uploaddatamanage;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.member.TblMemberDataTemplate;
import com.sendtend.web.entity.member.TblMemberDataTemplateField;
import com.sendtend.web.util.dwz.Page;

public interface TemplateFieldService {
	TblMemberDataTemplateField get(Long id);

	TblMemberDataTemplateField saveOrUpdate(TblMemberDataTemplateField template);

	void delete(Long id);
	
	List<TblMemberDataTemplateField> findAll(Specification<TblMemberDataTemplateField> specification, Page page);
	
	List<TblMemberDataTemplateField> findAll(Specification<TblMemberDataTemplateField> specification, Sort sort);
	
	List<TblMemberDataTemplateField> getByTemplateId(Long id);
}
