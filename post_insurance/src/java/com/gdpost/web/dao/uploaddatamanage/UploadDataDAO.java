package com.gdpost.web.dao.uploaddatamanage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.main.Policy;

public interface UploadDataDAO extends JpaRepository<Policy, Long>, JpaSpecificationExecutor<Policy> {

}
