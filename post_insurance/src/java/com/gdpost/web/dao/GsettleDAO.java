/**
 * 
 */
package com.gdpost.web.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.component.Gsettle;

public interface GsettleDAO extends JpaRepository<Gsettle, Long>, JpaSpecificationExecutor<Gsettle> {

	Gsettle getByPolicyPolicyNo(String policyNo);
	
	Gsettle getByInsuredAndCaseDate(String insured, Date casedate);
}