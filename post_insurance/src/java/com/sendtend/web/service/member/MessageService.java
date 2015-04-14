/**
 * 
 */
package com.sendtend.web.service.member;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.main.User;
import com.sendtend.web.entity.member.TblMemberMessage;
import com.sendtend.web.entity.member.TblMemberMessageAssign;
import com.sendtend.web.entity.member.TblMemberUser;
import com.sendtend.web.util.dwz.Page;

public interface MessageService {
	TblMemberMessage get(Long id);

	void saveOrUpdate(TblMemberMessage msg);

	void delete(Long id);
	
	List<TblMemberMessage> findAll(Page page);
	
	List<TblMemberMessage> findByExample(Specification<TblMemberMessage> specification, Page page);
	
	List<TblMemberMessageAssign> findAssignListByExample(Specification<TblMemberMessageAssign> specification, Page page);
	
	List<TblMemberMessageAssign> findAssignListByUserIdOrOrgId(Long userId, Long orgId, Page page);

	TblMemberMessage getByTitle(String name);
	
	int checkStatus(String userType, TblMemberUser memberUser, User user, int status);
	
	void saveOrUpdateAssign(TblMemberMessageAssign assign);

}
