package com.gdpost.web.dao.component;

import java.util.List;

import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.gdpost.web.entity.component.CommonModel;

/**
 * A data access object (DAO) providing persistence and search support for
 * TConservationDtl entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see com.gdpost.web.entity.main.ConservationDtl
 * @author MyEclipse Persistence Tools
 */
public interface CommonModelDAO extends JpaRepository<CommonModel, Long>, JpaSpecificationExecutor<CommonModel> {
	@NamedNativeQueries{
		@NamedNativeQuery(
				query="",
				resultClass=CommonModel.class
				)
	}
	List<CommonModel> getTuiBaoWarning();
}