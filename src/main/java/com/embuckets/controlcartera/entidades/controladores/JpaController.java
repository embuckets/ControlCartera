/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Logging;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author emilio
 */
public interface JpaController {

    /**
     *
     */
    static final Logger logger = LogManager.getLogger(JpaController.class);

    /**
     * Inserta el objeto especificado a la base de datos
     * @param object
     * @throws Exception - si falla la operacion. Se hace un rollback a la transaccion
     */
    default void create(Object object) throws Exception {
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

    /**
     * Busca todas las entidades
     * @param <T> el tipo de entidades a buscar
     * @return lista de todas las entidades
     */
    default <T> List<T> getAll() {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            return em.createNamedQuery(this.getControlledClassName() + ".findAll").getResultList();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error(Logging.Exception_MESSAGE, ex);
        }
        return new ArrayList<>();
    }

    /**
     * Busca la entidad por identificador
     * @param <T> tipo de la entidad
     * @param id el identificador
     * @return la entidad del tipo especificado con el identificador especificado. Si no existe <code>null</code>
     */
    default <T> T getById(int id) {
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
            logger.error(Logging.Exception_MESSAGE, ex);
        }
        return null;
    }

    /**
     * Busca todos las entidades que tengan el identificador especificado
     * @param <T> el tipo de la entidad
     * @param id el identificador
     * @return lista de todas las entidades del tipo especificado con el identificador especificado
     */
    default <T> List<T> getAllById(int id) {
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
            logger.error(Logging.Exception_MESSAGE, ex);
        }
        return new ArrayList<>();
    }

    /**
     * Hace un merge del objeto especificado
     * @param <T> el tipo del objeto
     * @param object el objeto a actualizar
     * @return el objeto actualizado
     * @throws Exception - si falla la operacion. Se hace un rollback a la transaccion
     */
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

    /**
     * elimina el objeto con todas sus dependencias
     * @param object el objeto a eliminar
     * @throws Exception - si falla la operacion. Se hace un rollback a la transaccion
     */
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

    /**
     * 
     * @return nombre de la clase de la entidad que representa el controlador
     */
    String getControlledClassName();

    /**
     *
     * @return nombre del named query que busca a todas las entidades que representa el controlador
     */
    String getFindByIdNamedQuery();

    /**
     *
     * @return nombre de la columna identificadora de la entidad que representa el controlador
     */
    String getFindByIdParameter();

}
