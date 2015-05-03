package com.gdpost.web.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gdpost.web.dao.member.EntityManagerHelper;
import com.gdpost.web.entity.main.ConvictedInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * TConvictedInfo entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see com.gdpost.web.entity.main.ConvictedInfo
 * @author MyEclipse Persistence Tools
 */
public class ConvictedInfoDAO {

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	/**
	 * Perform an initial save of a previously unsaved TConvictedInfo entity.
	 * All subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * TConvictedInfoDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            TConvictedInfo entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ConvictedInfo entity) {
		EntityManagerHelper.log("saving TConvictedInfo instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent TConvictedInfo entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * TConvictedInfoDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TConvictedInfo entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ConvictedInfo entity) {
		EntityManagerHelper.log("deleting TConvictedInfo instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(ConvictedInfo.class, entity.getId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved TConvictedInfo entity and return it or a copy
	 * of it to the sender. A copy of the TConvictedInfo entity parameter is
	 * returned when the JPA persistence mechanism has not previously been
	 * tracking the updated entity. This operation must be performed within the
	 * a database transaction context for the entity's data to be permanently
	 * saved to the persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = TConvictedInfoDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            TConvictedInfo entity to update
	 * @return TConvictedInfo the persisted TConvictedInfo entity instance, may
	 *         not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ConvictedInfo update(ConvictedInfo entity) {
		EntityManagerHelper.log("updating TConvictedInfo instance", Level.INFO, null);
		try {
			ConvictedInfo result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public ConvictedInfo findById(Long id) {
		EntityManagerHelper.log("finding TConvictedInfo instance with id: " + id, Level.INFO, null);
		try {
			ConvictedInfo instance = getEntityManager().find(ConvictedInfo.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all TConvictedInfo entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the TConvictedInfo property to query
	 * @param value
	 *            the property value to match
	 * @return List<TConvictedInfo> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<ConvictedInfo> findByProperty(String propertyName, final Object value) {
		EntityManagerHelper.log("finding TConvictedInfo instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from TConvictedInfo model where model." + propertyName + "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all TConvictedInfo entities.
	 * 
	 * @return List<TConvictedInfo> all TConvictedInfo entities
	 */
	@SuppressWarnings("unchecked")
	public List<ConvictedInfo> findAll() {
		EntityManagerHelper.log("finding all TConvictedInfo instances", Level.INFO, null);
		try {
			final String queryString = "select model from TConvictedInfo model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}