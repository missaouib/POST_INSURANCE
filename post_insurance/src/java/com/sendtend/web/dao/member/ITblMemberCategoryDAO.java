package com.sendtend.web.dao.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sendtend.web.entity.member.TblMemberCategory;

/**
 * Interface for TblMemberCategoryDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ITblMemberCategoryDAO extends JpaRepository<TblMemberCategory, Long>, JpaSpecificationExecutor<TblMemberCategory> {
	
	public void delete(TblMemberCategory entity);


	public TblMemberCategory findById(Long id);

	/**
	 * Find all TblMemberCategory entities.
	 * 
	 * @return List<TblMemberCategory> all TblMemberCategory entities
	 */
	public List<TblMemberCategory> findAll();
}