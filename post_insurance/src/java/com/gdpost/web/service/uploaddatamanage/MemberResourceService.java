package com.gdpost.web.service.uploaddatamanage;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.member.TblMemberResource;
import com.gdpost.web.util.dwz.Page;

public interface MemberResourceService {
	TblMemberResource get(Long id);

	void saveOrUpdate(TblMemberResource resource);

	void delete(Long id);
	
	List<TblMemberResource> findAll(Specification<TblMemberResource> specification, Page page);
}
