/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.embuckets.controlcartera.entidades.controladores;

import com.embuckets.controlcartera.entidades.ConductoCobro;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.embuckets.controlcartera.entidades.Poliza;
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
public class ConductoCobroJpaController implements Serializable {

    public ConductoCobroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConductoCobro conductoCobro) throws PreexistingEntityException, Exception {
        if (conductoCobro.getPolizaList() == null) {
            conductoCobro.setPolizaList(new ArrayList<Poliza>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Poliza> attachedPolizaList = new ArrayList<Poliza>();
            for (Poliza polizaListPolizaToAttach : conductoCobro.getPolizaList()) {
                polizaListPolizaToAttach = em.getReference(polizaListPolizaToAttach.getClass(), polizaListPolizaToAttach.getIdpoliza());
                attachedPolizaList.add(polizaListPolizaToAttach);
            }
            conductoCobro.setPolizaList(attachedPolizaList);
            em.persist(conductoCobro);
            for (Poliza polizaListPoliza : conductoCobro.getPolizaList()) {
                ConductoCobro oldConductocobroOfPolizaListPoliza = polizaListPoliza.getConductocobro();
                polizaListPoliza.setConductocobro(conductoCobro);
                polizaListPoliza = em.merge(polizaListPoliza);
                if (oldConductocobroOfPolizaListPoliza != null) {
                    oldConductocobroOfPolizaListPoliza.getPolizaList().remove(polizaListPoliza);
                    oldConductocobroOfPolizaListPoliza = em.merge(oldConductocobroOfPolizaListPoliza);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConductoCobro(conductoCobro.getConductocobro()) != null) {
                throw new PreexistingEntityException("ConductoCobro " + conductoCobro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConductoCobro conductoCobro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConductoCobro persistentConductoCobro = em.find(ConductoCobro.class, conductoCobro.getConductocobro());
            List<Poliza> polizaListOld = persistentConductoCobro.getPolizaList();
            List<Poliza> polizaListNew = conductoCobro.getPolizaList();
            List<String> illegalOrphanMessages = null;
            for (Poliza polizaListOldPoliza : polizaListOld) {
                if (!polizaListNew.contains(polizaListOldPoliza)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Poliza " + polizaListOldPoliza + " since its conductocobro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Poliza> attachedPolizaListNew = new ArrayList<Poliza>();
            for (Poliza polizaListNewPolizaToAttach : polizaListNew) {
                polizaListNewPolizaToAttach = em.getReference(polizaListNewPolizaToAttach.getClass(), polizaListNewPolizaToAttach.getIdpoliza());
                attachedPolizaListNew.add(polizaListNewPolizaToAttach);
            }
            polizaListNew = attachedPolizaListNew;
            conductoCobro.setPolizaList(polizaListNew);
            conductoCobro = em.merge(conductoCobro);
            for (Poliza polizaListNewPoliza : polizaListNew) {
                if (!polizaListOld.contains(polizaListNewPoliza)) {
                    ConductoCobro oldConductocobroOfPolizaListNewPoliza = polizaListNewPoliza.getConductocobro();
                    polizaListNewPoliza.setConductocobro(conductoCobro);
                    polizaListNewPoliza = em.merge(polizaListNewPoliza);
                    if (oldConductocobroOfPolizaListNewPoliza != null && !oldConductocobroOfPolizaListNewPoliza.equals(conductoCobro)) {
                        oldConductocobroOfPolizaListNewPoliza.getPolizaList().remove(polizaListNewPoliza);
                        oldConductocobroOfPolizaListNewPoliza = em.merge(oldConductocobroOfPolizaListNewPoliza);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = conductoCobro.getConductocobro();
                if (findConductoCobro(id) == null) {
                    throw new NonexistentEntityException("The conductoCobro with id " + id + " no longer exists.");
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
            ConductoCobro conductoCobro;
            try {
                conductoCobro = em.getReference(ConductoCobro.class, id);
                conductoCobro.getConductocobro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conductoCobro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Poliza> polizaListOrphanCheck = conductoCobro.getPolizaList();
            for (Poliza polizaListOrphanCheckPoliza : polizaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConductoCobro (" + conductoCobro + ") cannot be destroyed since the Poliza " + polizaListOrphanCheckPoliza + " in its polizaList field has a non-nullable conductocobro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(conductoCobro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ConductoCobro> findConductoCobroEntities() {
        return findConductoCobroEntities(true, -1, -1);
    }

    public List<ConductoCobro> findConductoCobroEntities(int maxResults, int firstResult) {
        return findConductoCobroEntities(false, maxResults, firstResult);
    }

    private List<ConductoCobro> findConductoCobroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConductoCobro.class));
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

    public ConductoCobro findConductoCobro(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConductoCobro.class, id);
        } finally {
            em.close();
        }
    }

    public int getConductoCobroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConductoCobro> rt = cq.from(ConductoCobro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
