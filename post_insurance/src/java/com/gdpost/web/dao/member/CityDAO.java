package com.gdpost.web.dao.member;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.basedata.City;

/**
 * A data access object (DAO) providing persistence and search support for City
 * entities. Transaction control of the save(), update() and delete() operations
 * must be handled externally by senders of these methods or must be manually
 * added to each of these methods for data to be persisted to the JPA datastore.
 * 
 * @see com.gdpost.web.entity.basedata.City
 * @author MyEclipse Persistence Tools
 */
public interface CityDAO extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {

	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.gdpost.web.entity.member.City")
		}
	)
	@Query(name="CityDAO.findByProvinceCodeWithCache", value="from City o where provinceCode=:provinceCode order by o.id ASC")
	List<City> findByProvinceCodeWithCache(@Param("provinceCode") String provinceCode);
	
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.gdpost.web.entity.member.City")
		}
	)
	@Query(name="CityDAO.findDistinctCityBycityCode", value="select distinct o from City o where o.cityCode=:cityCode")
	List<City> findDistinctCityBycityCode(@Param("cityCode") String cityCode);
}