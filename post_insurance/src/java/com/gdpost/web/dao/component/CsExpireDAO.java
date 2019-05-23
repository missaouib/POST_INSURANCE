package com.gdpost.web.dao.component;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.insurance.CsExpire;

/**
 * 
 */
public interface CsExpireDAO extends JpaRepository<CsExpire, Long>, JpaSpecificationExecutor<CsExpire> {

}