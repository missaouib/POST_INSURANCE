package com.gdpost.web.dao.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.member.TblMemberResource;

/**
 * Interface for TblMemberResourceDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ITblMemberResourceDAO extends JpaRepository<TblMemberResource, Long>, JpaSpecificationExecutor<TblMemberResource> {

	/**
	 * Delete a persistent TblMemberResource entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ITblMemberResourceDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TblMemberResource entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(TblMemberResource entity);

	public TblMemberResource findById(Long id);

	/**
	 * Find all TblMemberResource entities.
	 * 
	 * @return List<TblMemberResource> all TblMemberResource entities
	 */
	public List<TblMemberResource> findAll();
}