/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.embuckets.controlcartera.entidades.EstadoNotificacion;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.Recibo;
import com.embuckets.controlcartera.entidades.controladores.exceptions.IllegalOrphanException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class NotificacionReciboJpaController implements Serializable {

    public NotificacionReciboJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NotificacionRecibo notificacionRecibo) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Recibo reciboOrphanCheck = notificacionRecibo.getRecibo();
        if (reciboOrphanCheck != null) {
            NotificacionRecibo oldNotificacionReciboOfRecibo = reciboOrphanCheck.getNotificacionRecibo();
            if (oldNotificacionReciboOfRecibo != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Recibo " + reciboOrphanCheck + " already has an item of type NotificacionRecibo whose recibo column cannot be null. Please make another selection for the recibo field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoNotificacion estadonotificacion = notificacionRecibo.getEstadonotificacion();
            if (estadonotificacion != null) {
                estadonotificacion = em.getReference(estadonotificacion.getClass(), estadonotificacion.getEstadonotificacion());
                notificacionRecibo.setEstadonotificacion(estadonotificacion);
            }
            Recibo recibo = notificacionRecibo.getRecibo();
            if (recibo != null) {
                recibo = em.getReference(recibo.getClass(), recibo.getIdrecibo());
                notificacionRecibo.setRecibo(recibo);
            }
            em.persist(notificacionRecibo);
            if (estadonotificacion != null) {
                estadonotificacion.getNotificacionReciboList().add(notificacionRecibo);
                estadonotificacion = em.merge(estadonotificacion);
            }
            if (recibo != null) {
                recibo.setNotificacionRecibo(notificacionRecibo);
                recibo = em.merge(recibo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNotificacionRecibo(notificacionRecibo.getIdrecibo()) != null) {
                throw new PreexistingEntityException("NotificacionRecibo " + notificacionRecibo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NotificacionRecibo notificacionRecibo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NotificacionRecibo persistentNotificacionRecibo = em.find(NotificacionRecibo.class, notificacionRecibo.getIdrecibo());
            EstadoNotificacion estadonotificacionOld = persistentNotificacionRecibo.getEstadonotificacion();
            EstadoNotificacion estadonotificacionNew = notificacionRecibo.getEstadonotificacion();
            Recibo reciboOld = persistentNotificacionRecibo.getRecibo();
            Recibo reciboNew = notificacionRecibo.getRecibo();
            List<String> illegalOrphanMessages = null;
            if (reciboNew != null && !reciboNew.equals(reciboOld)) {
                NotificacionRecibo oldNotificacionReciboOfRecibo = reciboNew.getNotificacionRecibo();
                if (oldNotificacionReciboOfRecibo != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Recibo " + reciboNew + " already has an item of type NotificacionRecibo whose recibo column cannot be null. Please make another selection for the recibo field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estadonotificacionNew != null) {
                estadonotificacionNew = em.getReference(estadonotificacionNew.getClass(), estadonotificacionNew.getEstadonotificacion());
                notificacionRecibo.setEstadonotificacion(estadonotificacionNew);
            }
            if (reciboNew != null) {
                reciboNew = em.getReference(reciboNew.getClass(), reciboNew.getIdrecibo());
                notificacionRecibo.setRecibo(reciboNew);
            }
            notificacionRecibo = em.merge(notificacionRecibo);
            if (estadonotificacionOld != null && !estadonotificacionOld.equals(estadonotificacionNew)) {
                estadonotificacionOld.getNotificacionReciboList().remove(notificacionRecibo);
                estadonotificacionOld = em.merge(estadonotificacionOld);
            }
            if (estadonotificacionNew != null && !estadonotificacionNew.equals(estadonotificacionOld)) {
                estadonotificacionNew.getNotificacionReciboList().add(notificacionRecibo);
                estadonotificacionNew = em.merge(estadonotificacionNew);
            }
            if (reciboOld != null && !reciboOld.equals(reciboNew)) {
                reciboOld.setNotificacionRecibo(null);
                reciboOld = em.merge(reciboOld);
            }
            if (reciboNew != null && !reciboNew.equals(reciboOld)) {
                reciboNew.setNotificacionRecibo(notificacionRecibo);
                reciboNew = em.merge(reciboNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = notificacionRecibo.getIdrecibo();
                if (findNotificacionRecibo(id) == null) {
                    throw new NonexistentEntityException("The notificacionRecibo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NotificacionRecibo notificacionRecibo;
            try {
                notificacionRecibo = em.getReference(NotificacionRecibo.class, id);
                notificacionRecibo.getIdrecibo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notificacionRecibo with id " + id + " no longer exists.", enfe);
            }
            EstadoNotificacion estadonotificacion = notificacionRecibo.getEstadonotificacion();
            if (estadonotificacion != null) {
                estadonotificacion.getNotificacionReciboList().remove(notificacionRecibo);
                estadonotificacion = em.merge(estadonotificacion);
            }
            Recibo recibo = notificacionRecibo.getRecibo();
            if (recibo != null) {
                recibo.setNotificacionRecibo(null);
                recibo = em.merge(recibo);
            }
            em.remove(notificacionRecibo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NotificacionRecibo> findNotificacionReciboEntities() {
        return findNotificacionReciboEntities(true, -1, -1);
    }

    public List<NotificacionRecibo> findNotificacionReciboEntities(int maxResults, int firstResult) {
        return findNotificacionReciboEntities(false, maxResults, firstResult);
    }

    private List<NotificacionRecibo> findNotificacionReciboEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NotificacionRecibo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public NotificacionRecibo findNotificacionRecibo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NotificacionRecibo.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotificacionReciboCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NotificacionRecibo> rt = cq.from(NotificacionRecibo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
