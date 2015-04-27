package com.gdpost.web.dao.member;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.member.TblMemberMessageAssign;

/**
 * Interface for TblMemberMessageAssignDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface MemberMessageAssignDAO extends JpaRepository<TblMemberMessageAssign, Long>, JpaSpecificationExecutor<TblMemberMessageAssign> {

	/**
	 * Delete a persistent TblMemberMessageAssign entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ITblMemberMessageAssignDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TblMemberMessageAssign entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(TblMemberMessageAssign entity);

	public TblMemberMessageAssign findById(Long id);

	/**
	 * Find all TblMemberMessageAssign entities.
	 * 
	 * @return List<TblMemberMessageAssign> all TblMemberMessageAssign entities
	 */
	public List<TblMemberMessageAssign> findAll();
	
	public org.springframework.data.domain.Page<TblMemberMessageAssign> findByUserIdOrOrgId(Long userId, Long orgId, Pageable pageable);
}