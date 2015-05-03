package com.gdpost.web.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gdpost.web.dao.member.EntityManagerHelper;
import com.gdpost.web.entity.main.CategoryOrg;

/**
 * A data access object (DAO) providing persistence and search support for
 * TCategoryOrg entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see com.gdpost.web.entity.main.CategoryOrg
 * @author MyEclipse Persistence Tools
 */
public class CategoryOrgDAO {

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	/**
	 * Perform an initial save of a previously unsaved TCategoryOrg entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * TCategoryOrgDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            TCategoryOrg entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(CategoryOrg entity) {
		EntityManagerHelper.log("saving TCategoryOrg instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			EntityManagerHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent TCategoryOrg entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * TCategoryOrgDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            TCategoryOrg entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(CategoryOrg entity) {
		EntityManagerHelper.log("deleting TCategoryOrg instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(CategoryOrg.class, entity.getId());
			getEntityManager().remove(entity);
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved TCategoryOrg entity and return it or a copy of
	 * it to the sender. A copy of the TCategoryOrg entity parameter is returned
	 * when the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = TCategoryOrgDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            TCategoryOrg entity to update
	 * @return TCategoryOrg the persisted TCategoryOrg entity instance, may not
	 *         be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public CategoryOrg update(CategoryOrg entity) {
		EntityManagerHelper.log("updating TCategoryOrg instance", Level.INFO, null);
		try {
			CategoryOrg result = getEntityManager().merge(entity);
			EntityManagerHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public CategoryOrg findById(Long id) {
		EntityManagerHelper.log("finding TCategoryOrg instance with id: " + id, Level.INFO, null);
		try {
			CategoryOrg instance = getEntityManager().find(CategoryOrg.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all TCategoryOrg entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the TCategoryOrg property to query
	 * @param value
	 *            the property value to match
	 * @return List<TCategoryOrg> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<CategoryOrg> findByProperty(String propertyName, final Object value) {
		EntityManagerHelper.log("finding TCategoryOrg instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from TCategoryOrg model where model." + propertyName + "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all TCategoryOrg entities.
	 * 
	 * @return List<TCategoryOrg> all TCategoryOrg entities
	 */
	@SuppressWarnings("unchecked")
	public List<CategoryOrg> findAll() {
		EntityManagerHelper.log("finding all TCategoryOrg instances", Level.INFO, null);
		try {
			final String queryString = "select model from TCategoryOrg model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}