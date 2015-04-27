package com.gdpost.web.dao.uploaddatamanage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.member.TblMemberData;

public interface UploadDataDAO extends JpaRepository<TblMemberData, Long>, JpaSpecificationExecutor<TblMemberData> {

}
