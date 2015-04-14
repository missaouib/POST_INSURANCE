package com.sendtend.web.dao.member;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.sendtend.web.entity.member.TblMemberMessageCategory;

/**
 * Interface for TblMemberMessageCategoryDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface MemberMessageCategoryDAO extends JpaRepository<TblMemberMessageCategory, Long>, JpaSpecificationExecutor<TblMemberMessageCategory> {

	/**
	 * Delete a persistent TblMemberMessageCategory entity. This operation must
	 * be performed within the a database transaction context for the entity's
	 * data to be permanently deleted from the persistence store, i.e.,
	 * database. This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ITblMemberMessageCategoryDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TblMemberMessageCategory entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(TblMemberMessageCategory entity);

	public TblMemberMessageCategory findById(Long id);

	/**
	 * Find all TblMemberMessageCategory entities.
	 * 
	 * @return List<TblMemberMessageCategory> all TblMemberMessageCategory
	 *         entities
	 */
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.sendtend.web.entity.member.TblMemberMessageCategory")
		}
	)
	@Query("from TblMemberMessageCategory o")
	public List<TblMemberMessageCategory> findAllWithCache();
}