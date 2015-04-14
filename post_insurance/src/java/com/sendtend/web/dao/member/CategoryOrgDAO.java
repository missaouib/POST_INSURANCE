package com.sendtend.web.dao.member;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.sendtend.web.entity.member.TblCategoryOrg;

/**
 * Interface for TblCategoryOrgDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface CategoryOrgDAO extends JpaRepository<TblCategoryOrg, Long>, JpaSpecificationExecutor<TblCategoryOrg> {

	public void delete(TblCategoryOrg entity);

	public TblCategoryOrg findById(Long id);

	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.sendtend.web.entity.member.TblCategoryOrg")
		}
	)
	@Query("from TblCategoryOrg o")
	public List<TblCategoryOrg> findAllWithCache();
	
	public TblCategoryOrg findByTblMemberMessageCategoryId(Long categoryId);
}