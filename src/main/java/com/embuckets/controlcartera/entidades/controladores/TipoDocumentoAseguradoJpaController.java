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
import com.embuckets.controlcartera.entidades.DocumentoAsegurado;
import com.embuckets.controlcartera.entidades.TipoDocumentoAsegurado;
import com.embuckets.controlcartera.exceptions.IllegalOrphanException;
import com.embuckets.controlcartera.exceptions.NonexistentEntityException;
import com.embuckets.controlcartera.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author emilio
 */
public class TipoDocumentoAseguradoJpaController implements Serializable, JpaController {

    public TipoDocumentoAseguradoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public TipoDocumentoAseguradoJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoDocumentoAsegurado tipoDocumentoAsegurado) throws PreexistingEntityException, Exception {
        if (tipoDocumentoAsegurado.getDocumentoAseguradoList() == null) {
            tipoDocumentoAsegurado.setDocumentoAseguradoList(new ArrayList<DocumentoAsegurado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DocumentoAsegurado> attachedDocumentoAseguradoList = new ArrayList<DocumentoAsegurado>();
            for (DocumentoAsegurado documentoAseguradoListDocumentoAseguradoToAttach : tipoDocumentoAsegurado.getDocumentoAseguradoList()) {
                documentoAseguradoListDocumentoAseguradoToAttach = em.getReference(documentoAseguradoListDocumentoAseguradoToAttach.getClass(), documentoAseguradoListDocumentoAseguradoToAttach.getDocumentoAseguradoPK());
                attachedDocumentoAseguradoList.add(documentoAseguradoListDocumentoAseguradoToAttach);
            }
            tipoDocumentoAsegurado.setDocumentoAseguradoList(attachedDocumentoAseguradoList);
            em.persist(tipoDocumentoAsegurado);
            for (DocumentoAsegurado documentoAseguradoListDocumentoAsegurado : tipoDocumentoAsegurado.getDocumentoAseguradoList()) {
                TipoDocumentoAsegurado oldTipoDocumentoAseguradoOfDocumentoAseguradoListDocumentoAsegurado = documentoAseguradoListDocumentoAsegurado.getTipoDocumentoAsegurado();
                documentoAseguradoListDocumentoAsegurado.setTipoDocumentoAsegurado(tipoDocumentoAsegurado);
                documentoAseguradoListDocumentoAsegurado = em.merge(documentoAseguradoListDocumentoAsegurado);
                if (oldTipoDocumentoAseguradoOfDocumentoAseguradoListDocumentoAsegurado != null) {
                    oldTipoDocumentoAseguradoOfDocumentoAseguradoListDocumentoAsegurado.getDocumentoAseguradoList().remove(documentoAseguradoListDocumentoAsegurado);
                    oldTipoDocumentoAseguradoOfDocumentoAseguradoListDocumentoAsegurado = em.merge(oldTipoDocumentoAseguradoOfDocumentoAseguradoListDocumentoAsegurado);
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
            List<DocumentoAsegurado> documentoAseguradoListOld = persistentTipoDocumentoAsegurado.getDocumentoAseguradoList();
            List<DocumentoAsegurado> documentoAseguradoListNew = tipoDocumentoAsegurado.getDocumentoAseguradoList();
            List<String> illegalOrphanMessages = null;
            for (DocumentoAsegurado documentoAseguradoListOldDocumentoAsegurado : documentoAseguradoListOld) {
                if (!documentoAseguradoListNew.contains(documentoAseguradoListOldDocumentoAsegurado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DocumentoAsegurado " + documentoAseguradoListOldDocumentoAsegurado + " since its tipoDocumentoAsegurado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<DocumentoAsegurado> attachedDocumentoAseguradoListNew = new ArrayList<DocumentoAsegurado>();
            for (DocumentoAsegurado documentoAseguradoListNewDocumentoAseguradoToAttach : documentoAseguradoListNew) {
                documentoAseguradoListNewDocumentoAseguradoToAttach = em.getReference(documentoAseguradoListNewDocumentoAseguradoToAttach.getClass(), documentoAseguradoListNewDocumentoAseguradoToAttach.getDocumentoAseguradoPK());
                attachedDocumentoAseguradoListNew.add(documentoAseguradoListNewDocumentoAseguradoToAttach);
            }
            documentoAseguradoListNew = attachedDocumentoAseguradoListNew;
            tipoDocumentoAsegurado.setDocumentoAseguradoList(documentoAseguradoListNew);
            tipoDocumentoAsegurado = em.merge(tipoDocumentoAsegurado);
            for (DocumentoAsegurado documentoAseguradoListNewDocumentoAsegurado : documentoAseguradoListNew) {
                if (!documentoAseguradoListOld.contains(documentoAseguradoListNewDocumentoAsegurado)) {
                    TipoDocumentoAsegurado oldTipoDocumentoAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado = documentoAseguradoListNewDocumentoAsegurado.getTipoDocumentoAsegurado();
                    documentoAseguradoListNewDocumentoAsegurado.setTipoDocumentoAsegurado(tipoDocumentoAsegurado);
                    documentoAseguradoListNewDocumentoAsegurado = em.merge(documentoAseguradoListNewDocumentoAsegurado);
                    if (oldTipoDocumentoAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado != null && !oldTipoDocumentoAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado.equals(tipoDocumentoAsegurado)) {
                        oldTipoDocumentoAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado.getDocumentoAseguradoList().remove(documentoAseguradoListNewDocumentoAsegurado);
                        oldTipoDocumentoAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado = em.merge(oldTipoDocumentoAseguradoOfDocumentoAseguradoListNewDocumentoAsegurado);
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
            List<DocumentoAsegurado> documentoAseguradoListOrphanCheck = tipoDocumentoAsegurado.getDocumentoAseguradoList();
            for (DocumentoAsegurado documentoAseguradoListOrphanCheckDocumentoAsegurado : documentoAseguradoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoDocumentoAsegurado (" + tipoDocumentoAsegurado + ") cannot be destroyed since the DocumentoAsegurado " + documentoAseguradoListOrphanCheckDocumentoAsegurado + " in its documentoAseguradoList field has a non-nullable tipoDocumentoAsegurado field.");
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

    @Override
    public void create(Object object) throws PreexistingEntityException, Exception {
        //DO NOTHING
    }

    @Override
    public <T> T edit(Object object) throws Exception {
        //DO NOTHING
        return null;
    }

    @Override
    public void remove(Object object) {
        //DO NOTHING
    }

    @Override
    public String getControlledClassName() {
        return TipoDocumentoAsegurado.class.getSimpleName();
    }

    @Override
    public String getFindByIdNamedQuery() {
        return "findByTipodocumento";
    }

    @Override
    public String getFindByIdParameter() {
        return "tipodocumento";
    }

}
