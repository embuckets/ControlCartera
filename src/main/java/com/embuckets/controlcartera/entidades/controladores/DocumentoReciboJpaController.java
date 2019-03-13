/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.DocumentoRecibo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class DocumentoReciboJpaController implements Serializable, JpaController {

    public DocumentoReciboJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public DocumentoReciboJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DocumentoRecibo documentoRecibo) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Recibo reciboOrphanCheck = documentoRecibo.getRecibo();
        if (reciboOrphanCheck != null) {
            DocumentoRecibo oldDocumentoReciboOfRecibo = reciboOrphanCheck.getDocumentoRecibo();
            if (oldDocumentoReciboOfRecibo != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Recibo " + reciboOrphanCheck + " already has an item of type DocumentoRecibo whose recibo column cannot be null. Please make another selection for the recibo field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recibo recibo = documentoRecibo.getRecibo();
            if (recibo != null) {
                recibo = em.getReference(recibo.getClass(), recibo.getIdrecibo());
                documentoRecibo.setRecibo(recibo);
            }
            em.persist(documentoRecibo);
            if (recibo != null) {
                recibo.setDocumentoRecibo(documentoRecibo);
                recibo = em.merge(recibo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDocumentoRecibo(documentoRecibo.getIdrecibo()) != null) {
                throw new PreexistingEntityException("DocumentoRecibo " + documentoRecibo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DocumentoRecibo documentoRecibo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DocumentoRecibo persistentDocumentoRecibo = em.find(DocumentoRecibo.class, documentoRecibo.getIdrecibo());
            Recibo reciboOld = persistentDocumentoRecibo.getRecibo();
            Recibo reciboNew = documentoRecibo.getRecibo();
            List<String> illegalOrphanMessages = null;
            if (reciboNew != null && !reciboNew.equals(reciboOld)) {
                DocumentoRecibo oldDocumentoReciboOfRecibo = reciboNew.getDocumentoRecibo();
                if (oldDocumentoReciboOfRecibo != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Recibo " + reciboNew + " already has an item of type DocumentoRecibo whose recibo column cannot be null. Please make another selection for the recibo field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (reciboNew != null) {
                reciboNew = em.getReference(reciboNew.getClass(), reciboNew.getIdrecibo());
                documentoRecibo.setRecibo(reciboNew);
            }
            documentoRecibo = em.merge(documentoRecibo);
            if (reciboOld != null && !reciboOld.equals(reciboNew)) {
                reciboOld.setDocumentoRecibo(null);
                reciboOld = em.merge(reciboOld);
            }
            if (reciboNew != null && !reciboNew.equals(reciboOld)) {
                reciboNew.setDocumentoRecibo(documentoRecibo);
                reciboNew = em.merge(reciboNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = documentoRecibo.getIdrecibo();
                if (findDocumentoRecibo(id) == null) {
                    throw new NonexistentEntityException("The documentoRecibo with id " + id + " no longer exists.");
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
            DocumentoRecibo documentoRecibo;
            try {
                documentoRecibo = em.getReference(DocumentoRecibo.class, id);
                documentoRecibo.getIdrecibo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documentoRecibo with id " + id + " no longer exists.", enfe);
            }
            Recibo recibo = documentoRecibo.getRecibo();
            if (recibo != null) {
                recibo.setDocumentoRecibo(null);
                recibo = em.merge(recibo);
            }
            em.remove(documentoRecibo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DocumentoRecibo> findDocumentoReciboEntities() {
        return findDocumentoReciboEntities(true, -1, -1);
    }

    public List<DocumentoRecibo> findDocumentoReciboEntities(int maxResults, int firstResult) {
        return findDocumentoReciboEntities(false, maxResults, firstResult);
    }

    private List<DocumentoRecibo> findDocumentoReciboEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DocumentoRecibo.class));
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

    public DocumentoRecibo findDocumentoRecibo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DocumentoRecibo.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentoReciboCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DocumentoRecibo> rt = cq.from(DocumentoRecibo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public String getControlledClassName() {
        return DocumentoRecibo.class.getSimpleName();
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
