package com.gdpost.web.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gdpost.web.dao.member.EntityManagerHelper;
import com.gdpost.web.entity.main.RenewalFeeDtl;

/**
 * A data access object (DAO) providing persistence and search support for
 * TRenewalFeeDtl entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see com.gdpost.web.entity.main.RenewalFeeDtl
 * @author MyEclipse Persistence Tools
 */
public class RenewalFeeDtlDAO {

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	/**
	 * Perform an initial save of a previously unsaved TRenewalFeeDtl entity.
	 * All subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * TRenewalFeeDtlDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            TRenewalFeeDtl entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(RenewalFeeDtl entity) {
		EntityManagerHelper.log("saving TRenewalFeeDtl instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent TRenewalFeeDtl entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * TRenewalFeeDtlDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TRenewalFeeDtl entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(RenewalFeeDtl entity) {
		EntityManagerHelper.log("deleting TRenewalFeeDtl instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(RenewalFeeDtl.class, entity.getId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved TRenewalFeeDtl entity and return it or a copy
	 * of it to the sender. A copy of the TRenewalFeeDtl entity parameter is
	 * returned when the JPA persistence mechanism has not previously been
	 * tracking the updated entity. This operation must be performed within the
	 * a database transaction context for the entity's data to be permanently
	 * saved to the persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = TRenewalFeeDtlDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            TRenewalFeeDtl entity to update
	 * @return TRenewalFeeDtl the persisted TRenewalFeeDtl entity instance, may
	 *         not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public RenewalFeeDtl update(RenewalFeeDtl entity) {
		EntityManagerHelper.log("updating TRenewalFeeDtl instance", Level.INFO, null);
		try {
			RenewalFeeDtl result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public RenewalFeeDtl findById(Long id) {
		EntityManagerHelper.log("finding TRenewalFeeDtl instance with id: " + id, Level.INFO, null);
		try {
			RenewalFeeDtl instance = getEntityManager().find(RenewalFeeDtl.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all TRenewalFeeDtl entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the TRenewalFeeDtl property to query
	 * @param value
	 *            the property value to match
	 * @return List<TRenewalFeeDtl> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<RenewalFeeDtl> findByProperty(String propertyName, final Object value) {
		EntityManagerHelper.log("finding TRenewalFeeDtl instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from TRenewalFeeDtl model where model." + propertyName + "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all TRenewalFeeDtl entities.
	 * 
	 * @return List<TRenewalFeeDtl> all TRenewalFeeDtl entities
	 */
	@SuppressWarnings("unchecked")
	public List<RenewalFeeDtl> findAll() {
		EntityManagerHelper.log("finding all TRenewalFeeDtl instances", Level.INFO, null);
		try {
			final String queryString = "select model from TRenewalFeeDtl model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}