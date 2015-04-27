package com.gdpost.web.dao.member;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.member.Area;

/**
 * A data access object (DAO) providing persistence and search support for Area
 * entities. Transaction control of the save(), update() and delete() operations
 * must be handled externally by senders of these methods or must be manually
 * added to each of these methods for data to be persisted to the JPA datastore.
 * 
 * @see com.gdpost.web.entity.member.Area
 * @author MyEclipse Persistence Tools
 */
public interface AreaDAO extends JpaRepository<Area, Long>, JpaSpecificationExecutor<Area> {

	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.gdpost.web.entity.member.Area")
		}
	)
	@Query("from Area o where cityCode=:cityCode order by o.id ASC")
	List<Area> findByCityCodeWithCache(@Param("cityCode") String cityCode);
	
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.gdpost.web.entity.member.Area")
		}
	)
	@Query("select distinct o from Area o where o.areaCode=:areaCode")
	List<Area> findDistinctAreaByAreaCode(@Param("areaCode") String areaCode);
}