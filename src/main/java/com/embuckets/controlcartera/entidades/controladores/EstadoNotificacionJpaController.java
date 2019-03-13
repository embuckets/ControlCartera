/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.EstadoNotificacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.embuckets.controlcartera.entidades.NotificacionCumple;
import java.util.ArrayList;
import java.util.List;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.controladores.exceptions.IllegalOrphanException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class EstadoNotificacionJpaController implements Serializable, JpaController {

    public EstadoNotificacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EstadoNotificacionJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoNotificacion estadoNotificacion) throws PreexistingEntityException, Exception {
        if (estadoNotificacion.getNotificacionCumpleList() == null) {
            estadoNotificacion.setNotificacionCumpleList(new ArrayList<NotificacionCumple>());
        }
        if (estadoNotificacion.getNotificacionReciboList() == null) {
            estadoNotificacion.setNotificacionReciboList(new ArrayList<NotificacionRecibo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<NotificacionCumple> attachedNotificacionCumpleList = new ArrayList<NotificacionCumple>();
            for (NotificacionCumple notificacionCumpleListNotificacionCumpleToAttach : estadoNotificacion.getNotificacionCumpleList()) {
                notificacionCumpleListNotificacionCumpleToAttach = em.getReference(notificacionCumpleListNotificacionCumpleToAttach.getClass(), notificacionCumpleListNotificacionCumpleToAttach.getIdcliente());
                attachedNotificacionCumpleList.add(notificacionCumpleListNotificacionCumpleToAttach);
            }
            estadoNotificacion.setNotificacionCumpleList(attachedNotificacionCumpleList);
            List<NotificacionRecibo> attachedNotificacionReciboList = new ArrayList<NotificacionRecibo>();
            for (NotificacionRecibo notificacionReciboListNotificacionReciboToAttach : estadoNotificacion.getNotificacionReciboList()) {
                notificacionReciboListNotificacionReciboToAttach = em.getReference(notificacionReciboListNotificacionReciboToAttach.getClass(), notificacionReciboListNotificacionReciboToAttach.getIdrecibo());
                attachedNotificacionReciboList.add(notificacionReciboListNotificacionReciboToAttach);
            }
            estadoNotificacion.setNotificacionReciboList(attachedNotificacionReciboList);
            em.persist(estadoNotificacion);
            for (NotificacionCumple notificacionCumpleListNotificacionCumple : estadoNotificacion.getNotificacionCumpleList()) {
                EstadoNotificacion oldEstadonotificacionOfNotificacionCumpleListNotificacionCumple = notificacionCumpleListNotificacionCumple.getEstadonotificacion();
                notificacionCumpleListNotificacionCumple.setEstadonotificacion(estadoNotificacion);
                notificacionCumpleListNotificacionCumple = em.merge(notificacionCumpleListNotificacionCumple);
                if (oldEstadonotificacionOfNotificacionCumpleListNotificacionCumple != null) {
                    oldEstadonotificacionOfNotificacionCumpleListNotificacionCumple.getNotificacionCumpleList().remove(notificacionCumpleListNotificacionCumple);
                    oldEstadonotificacionOfNotificacionCumpleListNotificacionCumple = em.merge(oldEstadonotificacionOfNotificacionCumpleListNotificacionCumple);
                }
            }
            for (NotificacionRecibo notificacionReciboListNotificacionRecibo : estadoNotificacion.getNotificacionReciboList()) {
                EstadoNotificacion oldEstadonotificacionOfNotificacionReciboListNotificacionRecibo = notificacionReciboListNotificacionRecibo.getEstadonotificacion();
                notificacionReciboListNotificacionRecibo.setEstadonotificacion(estadoNotificacion);
                notificacionReciboListNotificacionRecibo = em.merge(notificacionReciboListNotificacionRecibo);
                if (oldEstadonotificacionOfNotificacionReciboListNotificacionRecibo != null) {
                    oldEstadonotificacionOfNotificacionReciboListNotificacionRecibo.getNotificacionReciboList().remove(notificacionReciboListNotificacionRecibo);
                    oldEstadonotificacionOfNotificacionReciboListNotificacionRecibo = em.merge(oldEstadonotificacionOfNotificacionReciboListNotificacionRecibo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoNotificacion(estadoNotificacion.getEstadonotificacion()) != null) {
                throw new PreexistingEntityException("EstadoNotificacion " + estadoNotificacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoNotificacion estadoNotificacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoNotificacion persistentEstadoNotificacion = em.find(EstadoNotificacion.class, estadoNotificacion.getEstadonotificacion());
            List<NotificacionCumple> notificacionCumpleListOld = persistentEstadoNotificacion.getNotificacionCumpleList();
            List<NotificacionCumple> notificacionCumpleListNew = estadoNotificacion.getNotificacionCumpleList();
            List<NotificacionRecibo> notificacionReciboListOld = persistentEstadoNotificacion.getNotificacionReciboList();
            List<NotificacionRecibo> notificacionReciboListNew = estadoNotificacion.getNotificacionReciboList();
            List<String> illegalOrphanMessages = null;
            for (NotificacionCumple notificacionCumpleListOldNotificacionCumple : notificacionCumpleListOld) {
                if (!notificacionCumpleListNew.contains(notificacionCumpleListOldNotificacionCumple)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain NotificacionCumple " + notificacionCumpleListOldNotificacionCumple + " since its estadonotificacion field is not nullable.");
                }
            }
            for (NotificacionRecibo notificacionReciboListOldNotificacionRecibo : notificacionReciboListOld) {
                if (!notificacionReciboListNew.contains(notificacionReciboListOldNotificacionRecibo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain NotificacionRecibo " + notificacionReciboListOldNotificacionRecibo + " since its estadonotificacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<NotificacionCumple> attachedNotificacionCumpleListNew = new ArrayList<NotificacionCumple>();
            for (NotificacionCumple notificacionCumpleListNewNotificacionCumpleToAttach : notificacionCumpleListNew) {
                notificacionCumpleListNewNotificacionCumpleToAttach = em.getReference(notificacionCumpleListNewNotificacionCumpleToAttach.getClass(), notificacionCumpleListNewNotificacionCumpleToAttach.getIdcliente());
                attachedNotificacionCumpleListNew.add(notificacionCumpleListNewNotificacionCumpleToAttach);
            }
            notificacionCumpleListNew = attachedNotificacionCumpleListNew;
            estadoNotificacion.setNotificacionCumpleList(notificacionCumpleListNew);
            List<NotificacionRecibo> attachedNotificacionReciboListNew = new ArrayList<NotificacionRecibo>();
            for (NotificacionRecibo notificacionReciboListNewNotificacionReciboToAttach : notificacionReciboListNew) {
                notificacionReciboListNewNotificacionReciboToAttach = em.getReference(notificacionReciboListNewNotificacionReciboToAttach.getClass(), notificacionReciboListNewNotificacionReciboToAttach.getIdrecibo());
                attachedNotificacionReciboListNew.add(notificacionReciboListNewNotificacionReciboToAttach);
            }
            notificacionReciboListNew = attachedNotificacionReciboListNew;
            estadoNotificacion.setNotificacionReciboList(notificacionReciboListNew);
            estadoNotificacion = em.merge(estadoNotificacion);
            for (NotificacionCumple notificacionCumpleListNewNotificacionCumple : notificacionCumpleListNew) {
                if (!notificacionCumpleListOld.contains(notificacionCumpleListNewNotificacionCumple)) {
                    EstadoNotificacion oldEstadonotificacionOfNotificacionCumpleListNewNotificacionCumple = notificacionCumpleListNewNotificacionCumple.getEstadonotificacion();
                    notificacionCumpleListNewNotificacionCumple.setEstadonotificacion(estadoNotificacion);
                    notificacionCumpleListNewNotificacionCumple = em.merge(notificacionCumpleListNewNotificacionCumple);
                    if (oldEstadonotificacionOfNotificacionCumpleListNewNotificacionCumple != null && !oldEstadonotificacionOfNotificacionCumpleListNewNotificacionCumple.equals(estadoNotificacion)) {
                        oldEstadonotificacionOfNotificacionCumpleListNewNotificacionCumple.getNotificacionCumpleList().remove(notificacionCumpleListNewNotificacionCumple);
                        oldEstadonotificacionOfNotificacionCumpleListNewNotificacionCumple = em.merge(oldEstadonotificacionOfNotificacionCumpleListNewNotificacionCumple);
                    }
                }
            }
            for (NotificacionRecibo notificacionReciboListNewNotificacionRecibo : notificacionReciboListNew) {
                if (!notificacionReciboListOld.contains(notificacionReciboListNewNotificacionRecibo)) {
                    EstadoNotificacion oldEstadonotificacionOfNotificacionReciboListNewNotificacionRecibo = notificacionReciboListNewNotificacionRecibo.getEstadonotificacion();
                    notificacionReciboListNewNotificacionRecibo.setEstadonotificacion(estadoNotificacion);
                    notificacionReciboListNewNotificacionRecibo = em.merge(notificacionReciboListNewNotificacionRecibo);
                    if (oldEstadonotificacionOfNotificacionReciboListNewNotificacionRecibo != null && !oldEstadonotificacionOfNotificacionReciboListNewNotificacionRecibo.equals(estadoNotificacion)) {
                        oldEstadonotificacionOfNotificacionReciboListNewNotificacionRecibo.getNotificacionReciboList().remove(notificacionReciboListNewNotificacionRecibo);
                        oldEstadonotificacionOfNotificacionReciboListNewNotificacionRecibo = em.merge(oldEstadonotificacionOfNotificacionReciboListNewNotificacionRecibo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = estadoNotificacion.getEstadonotificacion();
                if (findEstadoNotificacion(id) == null) {
                    throw new NonexistentEntityException("The estadoNotificacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoNotificacion estadoNotificacion;
            try {
                estadoNotificacion = em.getReference(EstadoNotificacion.class, id);
                estadoNotificacion.getEstadonotificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoNotificacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<NotificacionCumple> notificacionCumpleListOrphanCheck = estadoNotificacion.getNotificacionCumpleList();
            for (NotificacionCumple notificacionCumpleListOrphanCheckNotificacionCumple : notificacionCumpleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EstadoNotificacion (" + estadoNotificacion + ") cannot be destroyed since the NotificacionCumple " + notificacionCumpleListOrphanCheckNotificacionCumple + " in its notificacionCumpleList field has a non-nullable estadonotificacion field.");
            }
            List<NotificacionRecibo> notificacionReciboListOrphanCheck = estadoNotificacion.getNotificacionReciboList();
            for (NotificacionRecibo notificacionReciboListOrphanCheckNotificacionRecibo : notificacionReciboListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EstadoNotificacion (" + estadoNotificacion + ") cannot be destroyed since the NotificacionRecibo " + notificacionReciboListOrphanCheckNotificacionRecibo + " in its notificacionReciboList field has a non-nullable estadonotificacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estadoNotificacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoNotificacion> findEstadoNotificacionEntities() {
        return findEstadoNotificacionEntities(true, -1, -1);
    }

    public List<EstadoNotificacion> findEstadoNotificacionEntities(int maxResults, int firstResult) {
        return findEstadoNotificacionEntities(false, maxResults, firstResult);
    }

    private List<EstadoNotificacion> findEstadoNotificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoNotificacion.class));
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

    public EstadoNotificacion findEstadoNotificacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoNotificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoNotificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoNotificacion> rt = cq.from(EstadoNotificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public void remove(Object object) {
        //DO NOTHING
    }

    @Override
    public <T> T edit(Object object) throws Exception {
        //DO NOTHING
        return null;
    }

    @Override
    public void create(Object object) throws PreexistingEntityException, Exception {
        //DO NOTHING
    }

    @Override
    public String getControlledClassName() {
        return EstadoNotificacion.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByEstadonotificacion";
    }

    @Override
    public String getFindByIdParameter() {
        return "estadonotificacion";
    }

}
