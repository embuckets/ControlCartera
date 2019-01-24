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
import com.embuckets.controlcartera.entidades.DocumentoAsegurado_1;
import com.embuckets.controlcartera.entidades.TipoDocumentoAsegurado;
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
public class TipoDocumentoAseguradoJpaController implements Serializable {

    public TipoDocumentoAseguradoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoDocumentoAsegurado tipoDocumentoAsegurado) throws PreexistingEntityException, Exception {
        if (tipoDocumentoAsegurado.getDocumentoAseguradoList() == null) {
            tipoDocumentoAsegurado.setDocumentoAseguradoList(new ArrayList<DocumentoAsegurado_1>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DocumentoAsegurado_1> attachedDocumentoAseguradoList = new ArrayList<DocumentoAsegurado_1>();
            for (DocumentoAsegurado_1 documentoAseguradoListDocumentoAsegurado_1ToAttach : tipoDocumentoAsegurado.getDocumentoAseguradoList()) {
                documentoAseguradoListDocumentoAsegurado_1ToAttach = em.getReference(documentoAseguradoListDocumentoAsegurado_1ToAttach.getClass(), documentoAseguradoListDocumentoAsegurado_1ToAttach.getDocumentoAseguradoPK());
                attachedDocumentoAseguradoList.add(documentoAseguradoListDocumentoAsegurado_1ToAttach);
            }
            tipoDocumentoAsegurado.setDocumentoAseguradoList(attachedDocumentoAseguradoList);
            em.persist(tipoDocumentoAsegurado);
            for (DocumentoAsegurado_1 documentoAseguradoListDocumentoAsegurado_1 : tipoDocumentoAsegurado.getDocumentoAseguradoList()) {
                TipoDocumentoAsegurado oldTipoDocumentoAseguradoOfDocumentoAseguradoListDocumentoAsegurado_1 = documentoAseguradoListDocumentoAsegurado_1.getTipoDocumentoAsegurado();
                documentoAseguradoListDocumentoAsegurado_1.setTipoDocumentoAsegurado(tipoDocumentoAsegurado);
                documentoAseguradoListDocumentoAsegurado_1 = em.merge(documentoAseguradoListDocumentoAsegurado_1);
                if (oldTipoDocumentoAseguradoOfDocumentoAseguradoListDocumentoAsegurado_1 != null) {
                    oldTipoDocumentoAseguradoOfDocumentoAseguradoListDocumentoAsegurado_1.getDocumentoAseguradoList().remove(documentoAseguradoListDocumentoAsegurado_1);
                    oldTipoDocumentoAseguradoOfDocumentoAseguradoListDocumentoAsegurado_1 = em.merge(oldTipoDocumentoAseguradoOfDocumentoAseguradoListDocumentoAsegurado_1);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoDocumentoAsegurado(tipoDocumentoAsegurado.getTipodocumento()) != null) {
                throw new PreexistingEntityException("TipoDocumentoAsegurado " + tipoDocumentoAsegurado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoDocumentoAsegurado tipoDocumentoAsegurado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoDocumentoAsegurado persistentTipoDocumentoAsegurado = em.find(TipoDocumentoAsegurado.class, tipoDocumentoAsegurado.getTipodocumento());
            List<DocumentoAsegurado_1> documentoAseguradoListOld = persistentTipoDocumentoAsegurado.getDocumentoAseguradoList();
            List<DocumentoAsegurado_1> documentoAseguradoListNew = tipoDocumentoAsegurado.getDocumentoAseguradoList();
            List<String> illegalOrphanMessages = null;
            for (DocumentoAsegurado_1 documentoAseguradoListOldDocumentoAsegurado_1 : documentoAseguradoListOld) {
                if (!documentoAseguradoListNew.contains(documentoAseguradoListOldDocumentoAsegurado_1)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DocumentoAsegurado_1 " + documentoAseguradoListOldDocumentoAsegurado_1 + " since its tipoDocumentoAsegurado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<DocumentoAsegurado_1> attachedDocumentoAseguradoListNew = new ArrayList<DocumentoAsegurado_1>();
            for (DocumentoAsegurado_1 documentoAseguradoListNewDocumentoAsegurado_1ToAttach : documentoAseguradoListNew) {
                documentoAseguradoListNewDocumentoAsegurado_1ToAttach = em.getReference(documentoAseguradoListNewDocumentoAsegurado_1ToAttach.getClass(), documentoAseguradoListNewDocumentoAsegurado_1ToAttach.getDocumentoAseguradoPK());
                attachedDocumentoAseguradoListNew.add(documentoAseguradoListNewDocumentoAsegurado_1ToAttach);
            }
            documentoAseguradoListNew = attachedDocumentoAseguradoListNew;
            tipoDocumentoAsegurado.setDocumentoAseguradoList(documentoAseguradoListNew);
            tipoDocumentoAsegurado = em.merge(tipoDocumentoAsegurado);
            for (DocumentoAsegurado_1 documentoAseguradoListNewDocumentoAsegurado_1 : documentoAseguradoListNew) {
                if (!documentoAseguradoListOld.contains(documentoAseguradoListNewDocumentoAsegurado_1)) {
                    TipoDocumentoAsegurado oldTipoDocumentoAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado_1 = documentoAseguradoListNewDocumentoAsegurado_1.getTipoDocumentoAsegurado();
                    documentoAseguradoListNewDocumentoAsegurado_1.setTipoDocumentoAsegurado(tipoDocumentoAsegurado);
                    documentoAseguradoListNewDocumentoAsegurado_1 = em.merge(documentoAseguradoListNewDocumentoAsegurado_1);
                    if (oldTipoDocumentoAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado_1 != null && !oldTipoDocumentoAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado_1.equals(tipoDocumentoAsegurado)) {
                        oldTipoDocumentoAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado_1.getDocumentoAseguradoList().remove(documentoAseguradoListNewDocumentoAsegurado_1);
                        oldTipoDocumentoAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado_1 = em.merge(oldTipoDocumentoAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado_1);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tipoDocumentoAsegurado.getTipodocumento();
                if (findTipoDocumentoAsegurado(id) == null) {
                    throw new NonexistentEntityException("The tipoDocumentoAsegurado with id " + id + " no longer exists.");
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
            TipoDocumentoAsegurado tipoDocumentoAsegurado;
            try {
                tipoDocumentoAsegurado = em.getReference(TipoDocumentoAsegurado.class, id);
                tipoDocumentoAsegurado.getTipodocumento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoDocumentoAsegurado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DocumentoAsegurado_1> documentoAseguradoListOrphanCheck = tipoDocumentoAsegurado.getDocumentoAseguradoList();
            for (DocumentoAsegurado_1 documentoAseguradoListOrphanCheckDocumentoAsegurado_1 : documentoAseguradoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoDocumentoAsegurado (" + tipoDocumentoAsegurado + ") cannot be destroyed since the DocumentoAsegurado_1 " + documentoAseguradoListOrphanCheckDocumentoAsegurado_1 + " in its documentoAseguradoList field has a non-nullable tipoDocumentoAsegurado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoDocumentoAsegurado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoDocumentoAsegurado> findTipoDocumentoAseguradoEntities() {
        return findTipoDocumentoAseguradoEntities(true, -1, -1);
    }

    public List<TipoDocumentoAsegurado> findTipoDocumentoAseguradoEntities(int maxResults, int firstResult) {
        return findTipoDocumentoAseguradoEntities(false, maxResults, firstResult);
    }

    private List<TipoDocumentoAsegurado> findTipoDocumentoAseguradoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoDocumentoAsegurado.class));
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

    public TipoDocumentoAsegurado findTipoDocumentoAsegurado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoDocumentoAsegurado.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoDocumentoAseguradoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoDocumentoAsegurado> rt = cq.from(TipoDocumentoAsegurado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
