package com.gdpost.web.dao.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gdpost.web.entity.member.TblMemberDataTemplateField;

/**
 * Interface for TblMemberDataTemplateFieldDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ITblMemberDataTemplateFieldDAO extends JpaRepository<TblMemberDataTemplateField, Long>, JpaSpecificationExecutor<TblMemberDataTemplateField> {

	/**
	 * Delete a persistent TblMemberDataTemplateField entity. This operation
	 * must be performed within the a database transaction context for the
	 * entity's data to be permanently deleted from the persistence store, i.e.,
	 * database. This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ITblMemberDataTemplateFieldDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TblMemberDataTemplateField entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(TblMemberDataTemplateField entity);

	public TblMemberDataTemplateField findById(Long id);

	/**
	 * Find all TblMemberDataTemplateField entities.
	 * 
	 * @return List<TblMemberDataTemplateField> all TblMemberDataTemplateField
	 *         entities
	 */
	public List<TblMemberDataTemplateField> findAll();
	
	public List<TblMemberDataTemplateField> findByTblMemberDataTemplateId(Long id);
}