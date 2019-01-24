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
import com.embuckets.controlcartera.entidades.DocumentoRecibo;
import com.embuckets.controlcartera.entidades.NotificacionRecibo;
import com.embuckets.controlcartera.entidades.Cobranza;
import com.embuckets.controlcartera.entidades.Poliza;
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
public class ReciboJpaController implements Serializable {

    public ReciboJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recibo recibo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DocumentoRecibo documentoRecibo = recibo.getDocumentoRecibo();
            if (documentoRecibo != null) {
                documentoRecibo = em.getReference(documentoRecibo.getClass(), documentoRecibo.getIdrecibo());
                recibo.setDocumentoRecibo(documentoRecibo);
            }
            NotificacionRecibo notificacionRecibo = recibo.getNotificacionRecibo();
            if (notificacionRecibo != null) {
                notificacionRecibo = em.getReference(notificacionRecibo.getClass(), notificacionRecibo.getIdrecibo());
                recibo.setNotificacionRecibo(notificacionRecibo);
            }
            Cobranza cobranza = recibo.getCobranza();
            if (cobranza != null) {
                cobranza = em.getReference(cobranza.getClass(), cobranza.getCobranza());
                recibo.setCobranza(cobranza);
            }
            Poliza idpoliza = recibo.getIdpoliza();
            if (idpoliza != null) {
                idpoliza = em.getReference(idpoliza.getClass(), idpoliza.getIdpoliza());
                recibo.setIdpoliza(idpoliza);
            }
            em.persist(recibo);
            if (documentoRecibo != null) {
                Recibo oldReciboOfDocumentoRecibo = documentoRecibo.getRecibo();
                if (oldReciboOfDocumentoRecibo != null) {
                    oldReciboOfDocumentoRecibo.setDocumentoRecibo(null);
                    oldReciboOfDocumentoRecibo = em.merge(oldReciboOfDocumentoRecibo);
                }
                documentoRecibo.setRecibo(recibo);
                documentoRecibo = em.merge(documentoRecibo);
            }
            if (notificacionRecibo != null) {
                Recibo oldReciboOfNotificacionRecibo = notificacionRecibo.getRecibo();
                if (oldReciboOfNotificacionRecibo != null) {
                    oldReciboOfNotificacionRecibo.setNotificacionRecibo(null);
                    oldReciboOfNotificacionRecibo = em.merge(oldReciboOfNotificacionRecibo);
                }
                notificacionRecibo.setRecibo(recibo);
                notificacionRecibo = em.merge(notificacionRecibo);
            }
            if (cobranza != null) {
                cobranza.getReciboList().add(recibo);
                cobranza = em.merge(cobranza);
            }
            if (idpoliza != null) {
                idpoliza.getReciboList().add(recibo);
                idpoliza = em.merge(idpoliza);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRecibo(recibo.getIdrecibo()) != null) {
                throw new PreexistingEntityException("Recibo " + recibo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Recibo recibo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recibo persistentRecibo = em.find(Recibo.class, recibo.getIdrecibo());
            DocumentoRecibo documentoReciboOld = persistentRecibo.getDocumentoRecibo();
            DocumentoRecibo documentoReciboNew = recibo.getDocumentoRecibo();
            NotificacionRecibo notificacionReciboOld = persistentRecibo.getNotificacionRecibo();
            NotificacionRecibo notificacionReciboNew = recibo.getNotificacionRecibo();
            Cobranza cobranzaOld = persistentRecibo.getCobranza();
            Cobranza cobranzaNew = recibo.getCobranza();
            Poliza idpolizaOld = persistentRecibo.getIdpoliza();
            Poliza idpolizaNew = recibo.getIdpoliza();
            List<String> illegalOrphanMessages = null;
            if (documentoReciboOld != null && !documentoReciboOld.equals(documentoReciboNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain DocumentoRecibo " + documentoReciboOld + " since its recibo field is not nullable.");
            }
            if (notificacionReciboOld != null && !notificacionReciboOld.equals(notificacionReciboNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain NotificacionRecibo " + notificacionReciboOld + " since its recibo field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (documentoReciboNew != null) {
                documentoReciboNew = em.getReference(documentoReciboNew.getClass(), documentoReciboNew.getIdrecibo());
                recibo.setDocumentoRecibo(documentoReciboNew);
            }
            if (notificacionReciboNew != null) {
                notificacionReciboNew = em.getReference(notificacionReciboNew.getClass(), notificacionReciboNew.getIdrecibo());
                recibo.setNotificacionRecibo(notificacionReciboNew);
            }
            if (cobranzaNew != null) {
                cobranzaNew = em.getReference(cobranzaNew.getClass(), cobranzaNew.getCobranza());
                recibo.setCobranza(cobranzaNew);
            }
            if (idpolizaNew != null) {
                idpolizaNew = em.getReference(idpolizaNew.getClass(), idpolizaNew.getIdpoliza());
                recibo.setIdpoliza(idpolizaNew);
            }
            recibo = em.merge(recibo);
            if (documentoReciboNew != null && !documentoReciboNew.equals(documentoReciboOld)) {
                Recibo oldReciboOfDocumentoRecibo = documentoReciboNew.getRecibo();
                if (oldReciboOfDocumentoRecibo != null) {
                    oldReciboOfDocumentoRecibo.setDocumentoRecibo(null);
                    oldReciboOfDocumentoRecibo = em.merge(oldReciboOfDocumentoRecibo);
                }
                documentoReciboNew.setRecibo(recibo);
                documentoReciboNew = em.merge(documentoReciboNew);
            }
            if (notificacionReciboNew != null && !notificacionReciboNew.equals(notificacionReciboOld)) {
                Recibo oldReciboOfNotificacionRecibo = notificacionReciboNew.getRecibo();
                if (oldReciboOfNotificacionRecibo != null) {
                    oldReciboOfNotificacionRecibo.setNotificacionRecibo(null);
                    oldReciboOfNotificacionRecibo = em.merge(oldReciboOfNotificacionRecibo);
                }
                notificacionReciboNew.setRecibo(recibo);
                notificacionReciboNew = em.merge(notificacionReciboNew);
            }
            if (cobranzaOld != null && !cobranzaOld.equals(cobranzaNew)) {
                cobranzaOld.getReciboList().remove(recibo);
                cobranzaOld = em.merge(cobranzaOld);
            }
            if (cobranzaNew != null && !cobranzaNew.equals(cobranzaOld)) {
                cobranzaNew.getReciboList().add(recibo);
                cobranzaNew = em.merge(cobranzaNew);
            }
            if (idpolizaOld != null && !idpolizaOld.equals(idpolizaNew)) {
                idpolizaOld.getReciboList().remove(recibo);
                idpolizaOld = em.merge(idpolizaOld);
            }
            if (idpolizaNew != null && !idpolizaNew.equals(idpolizaOld)) {
                idpolizaNew.getReciboList().add(recibo);
                idpolizaNew = em.merge(idpolizaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = recibo.getIdrecibo();
                if (findRecibo(id) == null) {
                    throw new NonexistentEntityException("The recibo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recibo recibo;
            try {
                recibo = em.getReference(Recibo.class, id);
                recibo.getIdrecibo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recibo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            DocumentoRecibo documentoReciboOrphanCheck = recibo.getDocumentoRecibo();
            if (documentoReciboOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Recibo (" + recibo + ") cannot be destroyed since the DocumentoRecibo " + documentoReciboOrphanCheck + " in its documentoRecibo field has a non-nullable recibo field.");
            }
            NotificacionRecibo notificacionReciboOrphanCheck = recibo.getNotificacionRecibo();
            if (notificacionReciboOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Recibo (" + recibo + ") cannot be destroyed since the NotificacionRecibo " + notificacionReciboOrphanCheck + " in its notificacionRecibo field has a non-nullable recibo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cobranza cobranza = recibo.getCobranza();
            if (cobranza != null) {
                cobranza.getReciboList().remove(recibo);
                cobranza = em.merge(cobranza);
            }
            Poliza idpoliza = recibo.getIdpoliza();
            if (idpoliza != null) {
                idpoliza.getReciboList().remove(recibo);
                idpoliza = em.merge(idpoliza);
            }
            em.remove(recibo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Recibo> findReciboEntities() {
        return findReciboEntities(true, -1, -1);
    }

    public List<Recibo> findReciboEntities(int maxResults, int firstResult) {
        return findReciboEntities(false, maxResults, firstResult);
    }

    private List<Recibo> findReciboEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Recibo.class));
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

    public Recibo findRecibo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Recibo.class, id);
        } finally {
            em.close();
        }
    }

    public int getReciboCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recibo> rt = cq.from(Recibo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
