/**
 * 
 */
package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.component.SettleTask;

public interface SettleTaskDAO extends JpaRepository<SettleTask, Long>, JpaSpecificationExecutor<SettleTask> {

	SettleTask getByPolicyPolicyNo(String policyNo);

}