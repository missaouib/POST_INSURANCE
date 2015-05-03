package com.gdpost.web.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gdpost.web.dao.member.EntityManagerHelper;
import com.gdpost.web.entity.main.ConservationDtl;

/**
 * A data access object (DAO) providing persistence and search support for
 * TConservationDtl entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see com.gdpost.web.entity.main.ConservationDtl
 * @author MyEclipse Persistence Tools
 */
public class ConservationDtlDAO {

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	/**
	 * Perform an initial save of a previously unsaved TConservationDtl entity.
	 * All subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * TConservationDtlDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            TConservationDtl entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ConservationDtl entity) {
		EntityManagerHelper.log("saving TConservationDtl instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent TConservationDtl entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * TConservationDtlDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TConservationDtl entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ConservationDtl entity) {
		EntityManagerHelper.log("deleting TConservationDtl instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(ConservationDtl.class, entity.getId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved TConservationDtl entity and return it or a
	 * copy of it to the sender. A copy of the TConservationDtl entity parameter
	 * is returned when the JPA persistence mechanism has not previously been
	 * tracking the updated entity. This operation must be performed within the
	 * a database transaction context for the entity's data to be permanently
	 * saved to the persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = TConservationDtlDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            TConservationDtl entity to update
	 * @return TConservationDtl the persisted TConservationDtl entity instance,
	 *         may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ConservationDtl update(ConservationDtl entity) {
		EntityManagerHelper.log("updating TConservationDtl instance", Level.INFO, null);
		try {
			ConservationDtl result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public ConservationDtl findById(Long id) {
		EntityManagerHelper.log("finding TConservationDtl instance with id: " + id, Level.INFO, null);
		try {
			ConservationDtl instance = getEntityManager().find(ConservationDtl.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all TConservationDtl entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the TConservationDtl property to query
	 * @param value
	 *            the property value to match
	 * @return List<TConservationDtl> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<ConservationDtl> findByProperty(String propertyName, final Object value) {
		EntityManagerHelper.log("finding TConservationDtl instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from TConservationDtl model where model." + propertyName + "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all TConservationDtl entities.
	 * 
	 * @return List<TConservationDtl> all TConservationDtl entities
	 */
	@SuppressWarnings("unchecked")
	public List<ConservationDtl> findAll() {
		EntityManagerHelper.log("finding all TConservationDtl instances", Level.INFO, null);
		try {
			final String queryString = "select model from TConservationDtl model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}