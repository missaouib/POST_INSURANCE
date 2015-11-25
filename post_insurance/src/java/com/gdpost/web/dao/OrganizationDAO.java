/**
 * 
 */
package com.gdpost.web.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.main.Organization;

public interface OrganizationDAO extends JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.gdpost.web.entity.main.Organization")
		}
	)
	@Query("from Organization o order by o.priority ASC")
	List<Organization> findAllWithCache();
	
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.gdpost.web.entity.main.Organization")
		}
	)
	@Query("from Organization o where id=:id order by o.priority ASC")
	List<Organization> findAllByOrgIdWithCache(@Param("id")Long id);
	
	List<Organization> findByIdOrParent(Long id, Organization parent);
	
	List<Organization> findById(Long id);
	
	Organization getByName(String name);

	Organization getByOrgCode(String orgCode);
}