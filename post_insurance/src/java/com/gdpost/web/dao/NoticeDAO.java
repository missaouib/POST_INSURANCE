package com.gdpost.web.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.main.Notice;
import com.gdpost.web.entity.main.Role;
import com.gdpost.web.entity.main.User;

/**
 * A data access object (DAO) providing persistence and search support for
 * TNotice entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see com.gdpost.web.entity.main.Notice
 * @author MyEclipse Persistence Tools
 */
public interface NoticeDAO extends JpaRepository<Notice, Long>, JpaSpecificationExecutor<Notice> {
	@Query(name="NoticeDAO.findValidList", value="select distinct o from Notice o left join o.organization org "
			+ "where ((o.organization is not null and o.organization.orgCode like :orgCode) or o.role in (:roles) or o.user=:user or o.sender=:sender) and o.invalidDate>=:invalidDate")
	Page<Notice> findValidList(@Param("orgCode") String orgCode, @Param("roles") List<Role> roles, @Param("user") User user, @Param("sender") User sender, @Param("invalidDate") Date invalidDate, Pageable pageable);
	
	@Query(name="NoticeDAO.findBetweenList", value="select distinct o from Notice o left join o.organization org "
			+ "where ((o.organization is not null and o.organization.orgCode like :orgCode) or o.role in (:roles) or o.user=:user or o.sender=:sender) and o.sendDate between :d1 and :d2")
	Page<Notice> findBetweenList(@Param("orgCode") String orgCode, @Param("roles") List<Role> roles, @Param("user") User user, @Param("sender") User sender, @Param("d1") Date d1, @Param("d2") Date d2, Pageable pageable);
}