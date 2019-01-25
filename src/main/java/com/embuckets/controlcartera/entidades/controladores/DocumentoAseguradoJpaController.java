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
import com.embuckets.controlcartera.entidades.DocumentoAsegurado;
import com.embuckets.controlcartera.entidades.DocumentoAseguradoPK;
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
public class DocumentoAseguradoJpaController implements Serializable {

    public DocumentoAseguradoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DocumentoAsegurado documentoAsegurado) throws PreexistingEntityException, Exception {
        if (documentoAsegurado.getDocumentoAseguradoPK() == null) {
            documentoAsegurado.setDocumentoAseguradoPK(new DocumentoAseguradoPK());
        }
        documentoAsegurado.getDocumentoAseguradoPK().setIdcliente(documentoAsegurado.getAsegurado().getIdcliente());
        documentoAsegurado.getDocumentoAseguradoPK().setTipodocumento(documentoAsegurado.getTipoDocumentoAsegurado().getTipodocumento());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asegurado asegurado = documentoAsegurado.getAsegurado();
            if (asegurado != null) {
                asegurado = em.getReference(asegurado.getClass(), asegurado.getIdcliente());
                documentoAsegurado.setAsegurado(asegurado);
            }
            TipoDocumentoAsegurado tipoDocumentoAsegurado = documentoAsegurado.getTipoDocumentoAsegurado();
            if (tipoDocumentoAsegurado != null) {
                tipoDocumentoAsegurado = em.getReference(tipoDocumentoAsegurado.getClass(), tipoDocumentoAsegurado.getTipodocumento());
                documentoAsegurado.setTipoDocumentoAsegurado(tipoDocumentoAsegurado);
            }
            em.persist(documentoAsegurado);
            if (asegurado != null) {
                asegurado.getDocumentoAseguradoList().add(documentoAsegurado);
                asegurado = em.merge(asegurado);
            }
            if (tipoDocumentoAsegurado != null) {
                tipoDocumentoAsegurado.getDocumentoAseguradoList().add(documentoAsegurado);
                tipoDocumentoAsegurado = em.merge(tipoDocumentoAsegurado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDocumentoAsegurado(documentoAsegurado.getDocumentoAseguradoPK()) != null) {
                throw new PreexistingEntityException("DocumentoAsegurado " + documentoAsegurado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DocumentoAsegurado documentoAsegurado) throws NonexistentEntityException, Exception {
        documentoAsegurado.getDocumentoAseguradoPK().setIdcliente(documentoAsegurado.getAsegurado().getIdcliente());
        documentoAsegurado.getDocumentoAseguradoPK().setTipodocumento(documentoAsegurado.getTipoDocumentoAsegurado().getTipodocumento());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DocumentoAsegurado persistentDocumentoAsegurado = em.find(DocumentoAsegurado.class, documentoAsegurado.getDocumentoAseguradoPK());
            Asegurado aseguradoOld = persistentDocumentoAsegurado.getAsegurado();
            Asegurado aseguradoNew = documentoAsegurado.getAsegurado();
            TipoDocumentoAsegurado tipoDocumentoAseguradoOld = persistentDocumentoAsegurado.getTipoDocumentoAsegurado();
            TipoDocumentoAsegurado tipoDocumentoAseguradoNew = documentoAsegurado.getTipoDocumentoAsegurado();
            if (aseguradoNew != null) {
                aseguradoNew = em.getReference(aseguradoNew.getClass(), aseguradoNew.getIdcliente());
                documentoAsegurado.setAsegurado(aseguradoNew);
            }
            if (tipoDocumentoAseguradoNew != null) {
                tipoDocumentoAseguradoNew = em.getReference(tipoDocumentoAseguradoNew.getClass(), tipoDocumentoAseguradoNew.getTipodocumento());
                documentoAsegurado.setTipoDocumentoAsegurado(tipoDocumentoAseguradoNew);
            }
            documentoAsegurado = em.merge(documentoAsegurado);
            if (aseguradoOld != null && !aseguradoOld.equals(aseguradoNew)) {
                aseguradoOld.getDocumentoAseguradoList().remove(documentoAsegurado);
                aseguradoOld = em.merge(aseguradoOld);
            }
            if (aseguradoNew != null && !aseguradoNew.equals(aseguradoOld)) {
                aseguradoNew.getDocumentoAseguradoList().add(documentoAsegurado);
                aseguradoNew = em.merge(aseguradoNew);
            }
            if (tipoDocumentoAseguradoOld != null && !tipoDocumentoAseguradoOld.equals(tipoDocumentoAseguradoNew)) {
                tipoDocumentoAseguradoOld.getDocumentoAseguradoList().remove(documentoAsegurado);
                tipoDocumentoAseguradoOld = em.merge(tipoDocumentoAseguradoOld);
            }
            if (tipoDocumentoAseguradoNew != null && !tipoDocumentoAseguradoNew.equals(tipoDocumentoAseguradoOld)) {
                tipoDocumentoAseguradoNew.getDocumentoAseguradoList().add(documentoAsegurado);
                tipoDocumentoAseguradoNew = em.merge(tipoDocumentoAseguradoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DocumentoAseguradoPK id = documentoAsegurado.getDocumentoAseguradoPK();
                if (findDocumentoAsegurado(id) == null) {
                    throw new NonexistentEntityException("The documentoAsegurado with id " + id + " no longer exists.");
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
            DocumentoAsegurado documentoAsegurado;
            try {
                documentoAsegurado = em.getReference(DocumentoAsegurado.class, id);
                documentoAsegurado.getDocumentoAseguradoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documentoAsegurado with id " + id + " no longer exists.", enfe);
            }
            Asegurado asegurado = documentoAsegurado.getAsegurado();
            if (asegurado != null) {
                asegurado.getDocumentoAseguradoList().remove(documentoAsegurado);
                asegurado = em.merge(asegurado);
            }
            TipoDocumentoAsegurado tipoDocumentoAsegurado = documentoAsegurado.getTipoDocumentoAsegurado();
            if (tipoDocumentoAsegurado != null) {
                tipoDocumentoAsegurado.getDocumentoAseguradoList().remove(documentoAsegurado);
                tipoDocumentoAsegurado = em.merge(tipoDocumentoAsegurado);
            }
            em.remove(documentoAsegurado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DocumentoAsegurado> findDocumentoAseguradoEntities() {
        return findDocumentoAseguradoEntities(true, -1, -1);
    }

    public List<DocumentoAsegurado> findDocumentoAseguradoEntities(int maxResults, int firstResult) {
        return findDocumentoAseguradoEntities(false, maxResults, firstResult);
    }

    private List<DocumentoAsegurado> findDocumentoAseguradoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DocumentoAsegurado.class));
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

    public DocumentoAsegurado findDocumentoAsegurado(DocumentoAseguradoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DocumentoAsegurado.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentoAseguradoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DocumentoAsegurado> rt = cq.from(DocumentoAsegurado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
