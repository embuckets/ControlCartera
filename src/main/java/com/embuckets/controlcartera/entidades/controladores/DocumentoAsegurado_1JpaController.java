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
import com.embuckets.controlcartera.entidades.Asegurado;
import com.embuckets.controlcartera.entidades.DocumentoAseguradoPK;
import com.embuckets.controlcartera.entidades.DocumentoAsegurado_1;
import com.embuckets.controlcartera.entidades.TipoDocumentoAsegurado;
import com.embuckets.controlcartera.entidades.controladores.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.entidades.controladores.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class DocumentoAsegurado_1JpaController implements Serializable {

    public DocumentoAsegurado_1JpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DocumentoAsegurado_1 documentoAsegurado_1) throws PreexistingEntityException, Exception {
        if (documentoAsegurado_1.getDocumentoAseguradoPK() == null) {
            documentoAsegurado_1.setDocumentoAseguradoPK(new DocumentoAseguradoPK());
        }
        documentoAsegurado_1.getDocumentoAseguradoPK().setIdcliente(documentoAsegurado_1.getAsegurado().getIdcliente());
        documentoAsegurado_1.getDocumentoAseguradoPK().setTipodocumento(documentoAsegurado_1.getTipoDocumentoAsegurado().getTipodocumento());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asegurado asegurado = documentoAsegurado_1.getAsegurado();
            if (asegurado != null) {
                asegurado = em.getReference(asegurado.getClass(), asegurado.getIdcliente());
                documentoAsegurado_1.setAsegurado(asegurado);
            }
            TipoDocumentoAsegurado tipoDocumentoAsegurado = documentoAsegurado_1.getTipoDocumentoAsegurado();
            if (tipoDocumentoAsegurado != null) {
                tipoDocumentoAsegurado = em.getReference(tipoDocumentoAsegurado.getClass(), tipoDocumentoAsegurado.getTipodocumento());
                documentoAsegurado_1.setTipoDocumentoAsegurado(tipoDocumentoAsegurado);
            }
            em.persist(documentoAsegurado_1);
            if (asegurado != null) {
                asegurado.getDocumentoAseguradoList().add(documentoAsegurado_1);
                asegurado = em.merge(asegurado);
            }
            if (tipoDocumentoAsegurado != null) {
                tipoDocumentoAsegurado.getDocumentoAseguradoList().add(documentoAsegurado_1);
                tipoDocumentoAsegurado = em.merge(tipoDocumentoAsegurado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDocumentoAsegurado_1(documentoAsegurado_1.getDocumentoAseguradoPK()) != null) {
                throw new PreexistingEntityException("DocumentoAsegurado_1 " + documentoAsegurado_1 + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DocumentoAsegurado_1 documentoAsegurado_1) throws NonexistentEntityException, Exception {
        documentoAsegurado_1.getDocumentoAseguradoPK().setIdcliente(documentoAsegurado_1.getAsegurado().getIdcliente());
        documentoAsegurado_1.getDocumentoAseguradoPK().setTipodocumento(documentoAsegurado_1.getTipoDocumentoAsegurado().getTipodocumento());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DocumentoAsegurado_1 persistentDocumentoAsegurado_1 = em.find(DocumentoAsegurado_1.class, documentoAsegurado_1.getDocumentoAseguradoPK());
            Asegurado aseguradoOld = persistentDocumentoAsegurado_1.getAsegurado();
            Asegurado aseguradoNew = documentoAsegurado_1.getAsegurado();
            TipoDocumentoAsegurado tipoDocumentoAseguradoOld = persistentDocumentoAsegurado_1.getTipoDocumentoAsegurado();
            TipoDocumentoAsegurado tipoDocumentoAseguradoNew = documentoAsegurado_1.getTipoDocumentoAsegurado();
            if (aseguradoNew != null) {
                aseguradoNew = em.getReference(aseguradoNew.getClass(), aseguradoNew.getIdcliente());
                documentoAsegurado_1.setAsegurado(aseguradoNew);
            }
            if (tipoDocumentoAseguradoNew != null) {
                tipoDocumentoAseguradoNew = em.getReference(tipoDocumentoAseguradoNew.getClass(), tipoDocumentoAseguradoNew.getTipodocumento());
                documentoAsegurado_1.setTipoDocumentoAsegurado(tipoDocumentoAseguradoNew);
            }
            documentoAsegurado_1 = em.merge(documentoAsegurado_1);
            if (aseguradoOld != null && !aseguradoOld.equals(aseguradoNew)) {
                aseguradoOld.getDocumentoAseguradoList().remove(documentoAsegurado_1);
                aseguradoOld = em.merge(aseguradoOld);
            }
            if (aseguradoNew != null && !aseguradoNew.equals(aseguradoOld)) {
                aseguradoNew.getDocumentoAseguradoList().add(documentoAsegurado_1);
                aseguradoNew = em.merge(aseguradoNew);
            }
            if (tipoDocumentoAseguradoOld != null && !tipoDocumentoAseguradoOld.equals(tipoDocumentoAseguradoNew)) {
                tipoDocumentoAseguradoOld.getDocumentoAseguradoList().remove(documentoAsegurado_1);
                tipoDocumentoAseguradoOld = em.merge(tipoDocumentoAseguradoOld);
            }
            if (tipoDocumentoAseguradoNew != null && !tipoDocumentoAseguradoNew.equals(tipoDocumentoAseguradoOld)) {
                tipoDocumentoAseguradoNew.getDocumentoAseguradoList().add(documentoAsegurado_1);
                tipoDocumentoAseguradoNew = em.merge(tipoDocumentoAseguradoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DocumentoAseguradoPK id = documentoAsegurado_1.getDocumentoAseguradoPK();
                if (findDocumentoAsegurado_1(id) == null) {
                    throw new NonexistentEntityException("The documentoAsegurado_1 with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DocumentoAseguradoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DocumentoAsegurado_1 documentoAsegurado_1;
            try {
                documentoAsegurado_1 = em.getReference(DocumentoAsegurado_1.class, id);
                documentoAsegurado_1.getDocumentoAseguradoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documentoAsegurado_1 with id " + id + " no longer exists.", enfe);
            }
            Asegurado asegurado = documentoAsegurado_1.getAsegurado();
            if (asegurado != null) {
                asegurado.getDocumentoAseguradoList().remove(documentoAsegurado_1);
                asegurado = em.merge(asegurado);
            }
            TipoDocumentoAsegurado tipoDocumentoAsegurado = documentoAsegurado_1.getTipoDocumentoAsegurado();
            if (tipoDocumentoAsegurado != null) {
                tipoDocumentoAsegurado.getDocumentoAseguradoList().remove(documentoAsegurado_1);
                tipoDocumentoAsegurado = em.merge(tipoDocumentoAsegurado);
            }
            em.remove(documentoAsegurado_1);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DocumentoAsegurado_1> findDocumentoAsegurado_1Entities() {
        return findDocumentoAsegurado_1Entities(true, -1, -1);
    }

    public List<DocumentoAsegurado_1> findDocumentoAsegurado_1Entities(int maxResults, int firstResult) {
        return findDocumentoAsegurado_1Entities(false, maxResults, firstResult);
    }

    private List<DocumentoAsegurado_1> findDocumentoAsegurado_1Entities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DocumentoAsegurado_1.class));
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

    public DocumentoAsegurado_1 findDocumentoAsegurado_1(DocumentoAseguradoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DocumentoAsegurado_1.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentoAsegurado_1Count() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DocumentoAsegurado_1> rt = cq.from(DocumentoAsegurado_1.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
