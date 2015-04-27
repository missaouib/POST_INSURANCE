/**
 * 
 */
package com.gdpost.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gdpost.web.entity.member.TblMemberDataControl;
import com.gdpost.web.util.dwz.Page;

public interface MDataControlService {
	TblMemberDataControl get(Long id);

	void saveOrUpdate(TblMemberDataControl dataControl);

	void delete(Long id);
	
	List<TblMemberDataControl> findAll(Page page);
	
	List<TblMemberDataControl> findByExample(Specification<TblMemberDataControl> specification, Page page);
}
