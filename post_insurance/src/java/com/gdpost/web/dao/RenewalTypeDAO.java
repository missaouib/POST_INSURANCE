package com.gdpost.web.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gdpost.web.dao.member.EntityManagerHelper;
import com.gdpost.web.entity.basedata.RenewalType;

/**
 * A data access object (DAO) providing persistence and search support for
 * TRenewalType entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see com.gdpost.web.entity.basedata.RenewalType
 * @author MyEclipse Persistence Tools
 */
public class RenewalTypeDAO {

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	/**
	 * Perform an initial save of a previously unsaved TRenewalType entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * TRenewalTypeDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            TRenewalType entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(RenewalType entity) {
		EntityManagerHelper.log("saving TRenewalType instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent TRenewalType entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * TRenewalTypeDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TRenewalType entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(RenewalType entity) {
		EntityManagerHelper.log("deleting TRenewalType instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(RenewalType.class, entity.getId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved TRenewalType entity and return it or a copy of
	 * it to the sender. A copy of the TRenewalType entity parameter is returned
	 * when the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = TRenewalTypeDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            TRenewalType entity to update
	 * @return TRenewalType the persisted TRenewalType entity instance, may not
	 *         be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public RenewalType update(RenewalType entity) {
		EntityManagerHelper.log("updating TRenewalType instance", Level.INFO, null);
		try {
			RenewalType result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public RenewalType findById(Long id) {
		EntityManagerHelper.log("finding TRenewalType instance with id: " + id, Level.INFO, null);
		try {
			RenewalType instance = getEntityManager().find(RenewalType.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all TRenewalType entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the TRenewalType property to query
	 * @param value
	 *            the property value to match
	 * @return List<TRenewalType> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<RenewalType> findByProperty(String propertyName, final Object value) {
		EntityManagerHelper.log("finding TRenewalType instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from TRenewalType model where model." + propertyName + "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all TRenewalType entities.
	 * 
	 * @return List<TRenewalType> all TRenewalType entities
	 */
	@SuppressWarnings("unchecked")
	public List<RenewalType> findAll() {
		EntityManagerHelper.log("finding all TRenewalType instances", Level.INFO, null);
		try {
			final String queryString = "select model from TRenewalType model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}