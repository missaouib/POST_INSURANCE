/**
 * 
 */
package com.sendtend.web.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sendtend.web.entity.main.User;
import com.sendtend.web.util.dwz.Page;

public interface UserService {
	User get(Long id);

	void saveOrUpdate(User user);

	void delete(Long id);
	
	List<User> findAll(Page page);
	
	List<User> findByExample(Specification<User> specification, Page page);
	
	List<User> findByOrganizationId(Long orgId);
	
	void updatePwd(User user, String newPwd);
	
	void resetPwd(User user, String newPwd);
	
	User getByUsername(String username);
}
