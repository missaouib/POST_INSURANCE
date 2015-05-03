package com.gdpost.web.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gdpost.web.dao.member.EntityManagerHelper;
import com.gdpost.web.entity.main.CallFail;

/**
 * A data access object (DAO) providing persistence and search support for
 * TCallFail entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see com.gdpost.web.entity.main.CallFail
 * @author MyEclipse Persistence Tools
 */
public class CallFailDAO {

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	/**
	 * Perform an initial save of a previously unsaved TCallFail entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * TCallFailDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            TCallFail entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(CallFail entity) {
		EntityManagerHelper.log("saving TCallFail instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent TCallFail entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * TCallFailDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TCallFail entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(CallFail entity) {
		EntityManagerHelper.log("deleting TCallFail instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(CallFail.class, entity.getId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved TCallFail entity and return it or a copy of it
	 * to the sender. A copy of the TCallFail entity parameter is returned when
	 * the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = TCallFailDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            TCallFail entity to update
	 * @return TCallFail the persisted TCallFail entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public CallFail update(CallFail entity) {
		EntityManagerHelper.log("updating TCallFail instance", Level.INFO, null);
		try {
			CallFail result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public CallFail findById(Long id) {
		EntityManagerHelper.log("finding TCallFail instance with id: " + id, Level.INFO, null);
		try {
			CallFail instance = getEntityManager().find(CallFail.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all TCallFail entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the TCallFail property to query
	 * @param value
	 *            the property value to match
	 * @return List<TCallFail> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<CallFail> findByProperty(String propertyName, final Object value) {
		EntityManagerHelper.log("finding TCallFail instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from TCallFail model where model." + propertyName + "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all TCallFail entities.
	 * 
	 * @return List<TCallFail> all TCallFail entities
	 */
	@SuppressWarnings("unchecked")
	public List<CallFail> findAll() {
		EntityManagerHelper.log("finding all TCallFail instances", Level.INFO, null);
		try {
			final String queryString = "select model from TCallFail model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}