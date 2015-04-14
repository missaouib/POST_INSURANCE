package com.sendtend.web.service.uploaddatamanage;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.member.TblMemberDataTemplate;
import com.sendtend.web.util.dwz.Page;

public interface TemplateService {
	TblMemberDataTemplate get(Long id);

	TblMemberDataTemplate saveOrUpdate(TblMemberDataTemplate template);

	void delete(Long id);
	
	List<TblMemberDataTemplate> findAll(Specification<TblMemberDataTemplate> specification, Page page);
	
	List<TblMemberDataTemplate> findAll(long member_id);
}
