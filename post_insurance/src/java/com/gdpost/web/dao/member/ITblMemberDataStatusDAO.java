package com.gdpost.web.dao.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gdpost.web.entity.member.TblMemberDataStatus;

/**
 * Interface for TblMemberDataStatusDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ITblMemberDataStatusDAO extends JpaRepository<TblMemberDataStatus, Long>, JpaSpecificationExecutor<TblMemberDataStatus> {

	@Modifying
	@Query("delete from TblMemberDataStatus where tblMember.id=:memberId and ny=:ny")
	int deleteByTblMemberIdAndNy(@Param("memberId") Long memberId, @Param("ny") Integer ny);

	public TblMemberDataStatus findById(Long id);

	/**
	 * Find all TblMemberDataStatus entities.
	 * 
	 * @return List<TblMemberDataStatus> all TblMemberDataStatus entities
	 */
	public List<TblMemberDataStatus> findAll();
}