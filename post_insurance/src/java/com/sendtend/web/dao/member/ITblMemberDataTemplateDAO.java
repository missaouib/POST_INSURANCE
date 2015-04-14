package com.sendtend.web.dao.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sendtend.web.entity.member.TblMemberDataTemplate;

/**
 * Interface for TblMemberDataTemplateDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ITblMemberDataTemplateDAO extends JpaRepository<TblMemberDataTemplate, Long>, JpaSpecificationExecutor<TblMemberDataTemplate> {

	/**
	 * Delete a persistent TblMemberDataTemplate entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ITblMemberDataTemplateDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TblMemberDataTemplate entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(TblMemberDataTemplate entity);

	public TblMemberDataTemplate findById(Long id);

	/**
	 * Find all TblMemberDataTemplate entities.
	 * 
	 * @return List<TblMemberDataTemplate> all TblMemberDataTemplate entities
	 */
	public List<TblMemberDataTemplate> findAll();
	
	@Query("FROM TblMemberDataTemplate WHERE member_id=:member_id AND status=1")
	public List<TblMemberDataTemplate> findAll(@Param(value = "member_id") long member_id);
}