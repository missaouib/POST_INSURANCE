/**
 * 
 */
package com.gdpost.web.dao.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.member.TblMemberDataControl;

public interface MDataControlDAO extends JpaRepository<TblMemberDataControl, Long>, JpaSpecificationExecutor<TblMemberDataControl> {

}