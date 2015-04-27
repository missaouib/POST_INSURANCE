package com.gdpost.web.dao.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.member.TblMemberShop;

/**
 * Interface for TblMemberShopDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ITblMemberShopDAO extends JpaRepository<TblMemberShop, Long>, JpaSpecificationExecutor<TblMemberShop> {

	/**
	 * Delete a persistent TblMemberShop entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ITblMemberShopDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TblMemberShop entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(TblMemberShop entity);

	public TblMemberShop findById(Long id);

	/**
	 * Find all TblMemberShop entities.
	 * 
	 * @return List<TblMemberShop> all TblMemberShop entities
	 */
	public List<TblMemberShop> findAll();
}