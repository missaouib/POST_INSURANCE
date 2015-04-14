package com.sendtend.web.dao.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sendtend.web.entity.member.TblMemberDataTemplateFieldRule;

/**
 * Interface for TblMemberDataTemplateFieldRuleDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ITblMemberDataTemplateFieldRuleDAO extends JpaRepository<TblMemberDataTemplateFieldRule, Long>, JpaSpecificationExecutor<TblMemberDataTemplateFieldRule> {

	/**
	 * Delete a persistent TblMemberDataTemplateFieldRule entity. This operation
	 * must be performed within the a database transaction context for the
	 * entity's data to be permanently deleted from the persistence store, i.e.,
	 * database. This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ITblMemberDataTemplateFieldRuleDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TblMemberDataTemplateFieldRule entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(TblMemberDataTemplateFieldRule entity);

	public TblMemberDataTemplateFieldRule findById(Long id);

	/**
	 * Find all TblMemberDataTemplateFieldRule entities.
	 * 
	 * @return List<TblMemberDataTemplateFieldRule> all
	 *         TblMemberDataTemplateFieldRule entities
	 */
	public List<TblMemberDataTemplateFieldRule> findAll();
	
	public TblMemberDataTemplateFieldRule findByTblMemberDataTemplateFieldId(Long id);
}