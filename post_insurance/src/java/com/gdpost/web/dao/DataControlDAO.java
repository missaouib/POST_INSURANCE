/**
 * 
 */
package com.gdpost.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.main.DataControl;

public interface DataControlDAO extends JpaRepository<DataControl, Long>, JpaSpecificationExecutor<DataControl> {

}