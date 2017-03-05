package com.gdpost.web.dao.component;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.component.CsAddr;

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
public interface CsAddrDAO extends JpaRepository<CsAddr, Long>, JpaSpecificationExecutor<CsAddr> {
	CsAddr getByCity(String city);
	
	@Query(value="select id, mail_addr as addr, '' as organ_code, '' as prov, mail_addr as city, '' as linker, '' as phone, '' as post_code from t_offsite_conservation where mail_addr like :addr", nativeQuery=true)
	List<CsAddr> findCsAddrByAddr(@Param("addr")String addr);
}