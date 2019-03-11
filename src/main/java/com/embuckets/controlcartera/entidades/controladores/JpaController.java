/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.controladores.exceptions.PreexistingEntityException;
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author emilio
 */
public interface JpaController {

    void create(Object object) throws PreexistingEntityException, Exception;

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
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            em.getTransaction().begin();
            T merged = em.merge((T) object);
            em.getTransaction().commit();
            return merged;
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    void remove(Object object);

    String getControlledClassName();

    String getFindByIdNamedQuery();

    String getFindByIdParameter();

}
