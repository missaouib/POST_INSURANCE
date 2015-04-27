package com.gdpost.web.dao.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.member.TblMemberDataControl;

/**
 * Interface for TblMemberDataControlDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ITblMemberDataControlDAO extends JpaRepository<TblMemberDataControl, Long>, JpaSpecificationExecutor<TblMemberDataControl> {

	/**
	 * Delete a persistent TblMemberDataControl entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ITblMemberDataControlDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TblMemberDataControl entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(TblMemberDataControl entity);

	public TblMemberDataControl findById(Long id);

	/**
	 * Find all TblMemberDataControl entities.
	 * 
	 * @return List<TblMemberDataControl> all TblMemberDataControl entities
	 */
	public List<TblMemberDataControl> findAll();
}