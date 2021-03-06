package com.gdpost.web.dao.member;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.basedata.Province;

/**
 * A data access object (DAO) providing persistence and search support for
 * Province entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see com.gdpost.web.entity.basedata.Province
 * @author MyEclipse Persistence Tools
 */
public interface ProvinceDAO extends JpaRepository<Province, Long>, JpaSpecificationExecutor<Province> {
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.gdpost.web.entity.member.Province")
		}
	)
	@Query(name="ProvinceDAO.findAllWithCache", value="from Province o order by o.id ASC")
	List<Province> findAllWithCache();
	
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.gdpost.web.entity.member.Province")
		}
	)
	@Query(name="ProvinceDAO.findDistinctProvinceByProvinceCode", value="select distinct o from Province o where o.provinceCode=:provinceCode")
	List<Province> findDistinctProvinceByProvinceCode(@Param("provinceCode") String provinceCode);
}