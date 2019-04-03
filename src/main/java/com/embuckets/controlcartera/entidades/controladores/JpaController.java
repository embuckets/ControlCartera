/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.exceptions.PreexistingEntityException;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author emilio
 */
public interface JpaController {

    default void create(Object object) throws EntityExistsException, Exception {
        boolean isSubTransaction = false;
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            if (em.getTransaction().isActive()) {
                isSubTransaction = true;
            }
            if (!isSubTransaction) {
                em.getTransaction().begin();
            }
            em.persist(object);
            if (!isSubTransaction) {
                em.getTransaction().commit();
            }
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    default <T> List<T> getAll() {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            return em.createNamedQuery(this.getControlledClassName() + ".findAll").getResultList();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    default <T> T getById(int id) throws Exception {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            Query query = em.createNamedQuery(this.getControlledClassName() + "." + this.getFindByIdNamedQuery());
            query.setParameter(this.getFindByIdParameter(), id);
            return (T) query.getSingleResult();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    default <T> List<T> getAllById(int id) throws Exception {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            Query query = em.createNamedQuery(this.getControlledClassName() + "." + this.getFindByIdNamedQuery());
            query.setParameter(this.getFindByIdParameter(), id);
            return query.getResultList();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    default <T> T edit(Object object) throws Exception {
        boolean isSubTransaction = false;
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            if (em.getTransaction().isActive()) {
                isSubTransaction = true;
            }
            if (!isSubTransaction) {
                em.getTransaction().begin();
            }
            T merged = em.merge((T) object);
            if (!isSubTransaction) {
                em.getTransaction().commit();
            }
            return merged;
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    default void remove(Object object) throws Exception {
        boolean isSubTransaction = false;
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            if (em.getTransaction().isActive()) {
                isSubTransaction = true;
            }
            if (!isSubTransaction) {
                em.getTransaction().begin();
            }
            em.remove(object);
            if (!isSubTransaction) {
                em.getTransaction().commit();
            }
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    String getControlledClassName();

    String getFindByIdNamedQuery();

    String getFindByIdParameter();

}
