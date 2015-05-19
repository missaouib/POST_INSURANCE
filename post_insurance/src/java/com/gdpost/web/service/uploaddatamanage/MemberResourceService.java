package com.sendtend.web.service.uploaddatamanage;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.member.TblMemberResource;
import com.sendtend.web.util.dwz.Page;

public interface MemberResourceService {
	TblMemberResource get(Long id);

	void saveOrUpdate(TblMemberResource resource);

	void delete(Long id);
	
	List<TblMemberResource> findAll(Specification<TblMemberResource> specification, Page page);
}
