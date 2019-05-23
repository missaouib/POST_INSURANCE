package com.gdpost.web.dao.uploaddatamanage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.insurance.Policy;

public interface UploadDataDAO extends JpaRepository<Policy, Long>, JpaSpecificationExecutor<Policy> {

}
