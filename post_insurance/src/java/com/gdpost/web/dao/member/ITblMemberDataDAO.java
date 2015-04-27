package com.gdpost.web.dao.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.member.TblMemberData;

/**
 * Interface for TblMemberDataDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ITblMemberDataDAO extends JpaRepository<TblMemberData, Long>, JpaSpecificationExecutor<TblMemberData> {

	@Modifying
	@Query("delete from TblMemberData where tblMember.id=:memberId and ny=:ny")
	int deleteByTblMemberIdAndNy(@Param("memberId") Long memberId, @Param("ny") Integer ny);

	public TblMemberData findById(Long id);

	/**
	 * Find all TblMemberData entities.
	 * 
	 * @return List<TblMemberData> all TblMemberData entities
	 */
	public List<TblMemberData> findAll();
}