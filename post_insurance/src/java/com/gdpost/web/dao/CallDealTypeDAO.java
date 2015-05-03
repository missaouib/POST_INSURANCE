package com.gdpost.web.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gdpost.web.dao.member.EntityManagerHelper;
import com.gdpost.web.entity.basedata.CallDealType;

/**
 * A data access object (DAO) providing persistence and search support for
 * TCallDealType entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see com.gdpost.web.entity.basedata.CallDealType
 * @author MyEclipse Persistence Tools
 */
public class CallDealTypeDAO {

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	/**
	 * Perform an initial save of a previously unsaved TCallDealType entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * TCallDealTypeDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            TCallDealType entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(CallDealType entity) {
		EntityManagerHelper.log("saving TCallDealType instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent TCallDealType entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * TCallDealTypeDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TCallDealType entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(CallDealType entity) {
		EntityManagerHelper.log("deleting TCallDealType instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(CallDealType.class, entity.getId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved TCallDealType entity and return it or a copy
	 * of it to the sender. A copy of the TCallDealType entity parameter is
	 * returned when the JPA persistence mechanism has not previously been
	 * tracking the updated entity. This operation must be performed within the
	 * a database transaction context for the entity's data to be permanently
	 * saved to the persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = TCallDealTypeDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            TCallDealType entity to update
	 * @return TCallDealType the persisted TCallDealType entity instance, may
	 *         not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public CallDealType update(CallDealType entity) {
		EntityManagerHelper.log("updating TCallDealType instance", Level.INFO, null);
		try {
			CallDealType result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public CallDealType findById(Long id) {
		EntityManagerHelper.log("finding TCallDealType instance with id: " + id, Level.INFO, null);
		try {
			CallDealType instance = getEntityManager().find(CallDealType.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all TCallDealType entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the TCallDealType property to query
	 * @param value
	 *            the property value to match
	 * @return List<TCallDealType> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<CallDealType> findByProperty(String propertyName, final Object value) {
		EntityManagerHelper.log("finding TCallDealType instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from TCallDealType model where model." + propertyName + "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all TCallDealType entities.
	 * 
	 * @return List<TCallDealType> all TCallDealType entities
	 */
	@SuppressWarnings("unchecked")
	public List<CallDealType> findAll() {
		EntityManagerHelper.log("finding all TCallDealType instances", Level.INFO, null);
		try {
			final String queryString = "select model from TCallDealType model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}