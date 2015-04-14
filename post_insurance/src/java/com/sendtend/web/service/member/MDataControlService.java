/**
 * 
 */
package com.sendtend.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.member.TblMemberDataControl;
import com.sendtend.web.util.dwz.Page;

public interface MDataControlService {
	TblMemberDataControl get(Long id);

	void saveOrUpdate(TblMemberDataControl dataControl);

	void delete(Long id);
	
	List<TblMemberDataControl> findAll(Page page);
	
	List<TblMemberDataControl> findByExample(Specification<TblMemberDataControl> specification, Page page);
}
