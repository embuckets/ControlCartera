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
import com.embuckets.controlcartera.entidades.globals.BaseDeDatos;
import com.embuckets.controlcartera.entidades.globals.Globals;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class NotificacionReciboJpaController implements Serializable, JpaController {

    public NotificacionReciboJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public NotificacionReciboJpaController() {
    }

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

    public List<NotificacionRecibo> getNotificacionesProximas() {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
//            Query query = em.createQuery("SELECT n FROM NotificacionRecibo n WHERE (MONTH(n.recibo.cubredesde) = :today OR MONTH(n.recibo.cubredesde) = :next) AND YEAR(n.recibo.cubredesde) = :year");
            Query query = em.createQuery("SELECT n FROM NotificacionRecibo n WHERE n.recibo.cubredesde BETWEEN :today AND :nextMonth");
            query.setParameter("today", LocalDate.now());
            query.setParameter("nextMonth", LocalDate.now().plusMonths(1));
//            query.setParameter("year", LocalDate.now().getYear());
            return query.getResultList();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    public List<NotificacionRecibo> getNotificacionesPendientes() {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            //TODO: ESTA MAL
            Query query = em.createQuery("SELECT n FROM NotificacionRecibo n WHERE n.recibo.cubredesde BETWEEN :today AND :nextMonth AND n.estadonotificacion.estadonotificacion = :pendiente");
            query.setParameter("today", LocalDate.now());
            query.setParameter("nextMonth", LocalDate.now().plusMonths(1));
            query.setParameter("pendiente", Globals.NOTIFICACION_ESTADO_PENDIENTE);
//            query.setParameter("year", LocalDate.now().getYear());
            return query.getResultList();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    public List<NotificacionRecibo> getNotificacionesPendientesDentroDePrimeros(int dias) {
        EntityManager em = null;
        try {
            em = BaseDeDatos.getInstance().getEntityManager();
            //TODO: ESTA MAL
            Query query = em.createQuery("SELECT n FROM NotificacionRecibo n WHERE (FUNCTION('timeDiff',SQL_TSI_DAY,n.recibo.cubredesde, CURRENT_TIMESTAMP) > 0 AND FUNCTION('timeDiff',SQL_TSI_DAY,n.recibo.cubredesde, CURRENT_TIMESTAMP) <= :dias) AND n.recibo.cobranza.cobranza = :pendiente");
            query.setParameter("dias", dias);
            query.setParameter("pendiente", Globals.NOTIFICACION_ESTADO_PENDIENTE);
            return query.getResultList();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    @Override
    public String getControlledClassName() {
        return NotificacionRecibo.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByIdrecibo";
    }

    @Override
    public String getFindByIdParameter() {
        return "idrecibo";
    }

}
