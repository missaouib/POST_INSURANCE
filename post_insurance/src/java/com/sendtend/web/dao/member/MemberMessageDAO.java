package com.sendtend.web.dao.member;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.sendtend.web.entity.member.TblMemberMessage;

/**
 * Interface for TblMemberMessageDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface MemberMessageDAO extends JpaRepository<TblMemberMessage, Long>, JpaSpecificationExecutor<TblMemberMessage> {

	/**
	 * Delete a persistent TblMemberMessage entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ITblMemberMessageDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TblMemberMessage entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(TblMemberMessage entity);

	public TblMemberMessage findById(Long id);
	
	public TblMemberMessage findByTitle(String title);

	/**
	 * Find all TblMemberMessage entities.
	 * 
	 * @return List<TblMemberMessage> all TblMemberMessage entities
	 */
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.sendtend.web.entity.member.TblMemberMessage")
		}
	)
	@Query("from TblMemberMessage o order by o.status,o.createDate DESC")
	public List<TblMemberMessage> findAllWithCache();
	
	@Query("select count(id) from TblMemberMessage m where m.tblMemberMessageCategory.tblCategoryOrg.organization.id=:orgId and status=:status and parentId is null")
	public int getByOrgIdAndStatus(@Param(value = "orgId") Long orgId, @Param(value = "status") int status);
	
	@Query("select count(id) from TblMemberMessage m where m.tblMemberUser.id=:userId and status=:status and parentId is null")
	public int getByMemberUserIdAndStatus(@Param(value = "userId") Long userId, @Param(value = "status") int status);
}